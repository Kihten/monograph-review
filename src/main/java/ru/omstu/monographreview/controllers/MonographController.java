package ru.omstu.monographreview.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.omstu.monographreview.dto.MonographListingDTO;
import ru.omstu.monographreview.dto.MonographsFilesDTO;
import ru.omstu.monographreview.dto.RequestFile;
import ru.omstu.monographreview.models.Monograph;
import ru.omstu.monographreview.services.FileService;
import ru.omstu.monographreview.services.MonographService;
import ru.omstu.monographreview.util.FileValidator;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class MonographController {

    @Autowired
    private MonographService monographService;
    @Autowired
    private FileValidator fileValidator;
    @Autowired
    private FileService fileService;

    @GetMapping("/monographs")
    public String viewMonographs(Model model,
                                 @PageableDefault(page = 0, size = 10, sort = "id") Pageable pageable,
                                 @RequestParam(required = false, defaultValue = "my") String keyword,
                                 @RequestParam(required = false) String sort) {
        Page<MonographListingDTO> monographsPage = monographService.getMonographs(pageable, keyword);
        model.addAttribute("monographs", monographsPage.getContent());
        model.addAttribute("currentPage", monographsPage.getNumber());
        model.addAttribute("totalPage", monographsPage.getTotalPages());
        model.addAttribute("totalItems", monographsPage.getTotalElements());
        model.addAttribute("sort", sort);
        model.addAttribute("keyword", keyword);

        return "monograph/monographs";
    }

    @GetMapping("/monographs/add")
    public String viewAddMonographForm(Model model) {
        model.addAttribute("monographFiles", new MonographsFilesDTO());
        return "monograph/monograph-add";
    }

    @PostMapping("/monographs/add")
    public String uploadMonographFiles(@Valid @ModelAttribute("monographFiles") MonographsFilesDTO monographsFiles,
                                       BindingResult bindingResult,
                                       Model model) {
        fileValidator.validate(monographsFiles, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute(monographsFiles);
            return "monograph/monograph-add";
        }

        Monograph monograph = monographService.saveMonographWithName(monographsFiles.getName());
        monographService.uploadServiceNote(monograph, monographsFiles.getServiceNote());
        monographService.uploadTitle(monograph, monographsFiles.getTitlePage());
        monographService.uploadContent(monograph, monographsFiles.getContentPage());
        monographService.uploadText(monograph, monographsFiles.getText());
        monographService.uploadExpertConclusion(monograph, monographsFiles.getExpertConclusion());
        return "redirect:/monographs/" + monograph.getId();
    }

    @GetMapping("/monographs/{id}")
    public String viewMonograph(@PathVariable("id") long id, Model model) {
        Monograph monograph = monographService.getMonographById(id);
        model.addAttribute("monograph", monograph);
        Map<String, RequestFile> modelAttributes = new HashMap<>();
        try {
            modelAttributes.put("serviceNote", fileService.getFile(monograph.getServiceNote()));
            modelAttributes.put("titlePage", fileService.getFile(monograph.getTitle()));
            modelAttributes.put("content", fileService.getFile(monograph.getContent()));
            modelAttributes.put("text", fileService.getFile(monograph.getText()));
            modelAttributes.put("expertConclusion", fileService.getFile(monograph.getExpertConclusion()));
            if (monograph.getPlagiarismCheck() != null) {
                modelAttributes.put("plagiarismCheck", fileService.getFile(monograph.getPlagiarismCheck()));
            }

            model.addAllAttributes(modelAttributes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "monograph/monograph-details";
    }

    @PutMapping("/monographs/{monographId}/uploadServiceNote")
    public String uploadServiceNote(@PathVariable("monographId") Long monographId,
                                    @RequestParam("file") MultipartFile file) {
        Monograph monograph = monographService.getMonographById(monographId);
        monographService.uploadServiceNote(monograph, file);
        return "redirect:/monographs/{monographId}";
    }

    @PutMapping("/monographs/{monographId}/uploadTitlePage")
    public String uploadTitlePage(@PathVariable("monographId") Long monographId,
                                  @RequestParam("file") MultipartFile file,
                                  RedirectAttributes attributes) {
        if (file.isEmpty()) {
            attributes.addFlashAttribute("message", "Выберите файл для загрузки");
        }
        Monograph monograph = monographService.getMonographById(monographId);
        monographService.uploadTitle(monograph, file);
        attributes.addFlashAttribute("message", "Файл был успешно загружен");
        return "redirect:/monographs/{monographId}";
    }

    @PutMapping("{monographId}/uploadContent")
    public String uploadContent(@PathVariable("monographId") Long monographId,
                                @RequestParam("file") MultipartFile file) {
        Monograph monograph = monographService.getMonographById(monographId);
        monographService.uploadContent(monograph, file);
        return "redirect:/monographs/{monographId}";
    }

    @PutMapping("{monographId}/uploadText")
    public String uploadText(@PathVariable("monographId") Long monographId,
                             @RequestParam("file") MultipartFile file) {
        Monograph monograph = monographService.getMonographById(monographId);
        monographService.uploadText(monograph, file);
        return "redirect:/monographs/{monographId}";
    }

    @PutMapping("{monographId}/uploadExpertConclusion")
    public String uploadExpertConclusion(@PathVariable("monographId") Long monographId,
                                         @RequestParam("file") MultipartFile file) {
        Monograph monograph = monographService.getMonographById(monographId);
        monographService.uploadExpertConclusion(monograph, file);
        return "redirect:/monographs/{monographId}";
    }

    @PostMapping("monographs/{monographId}/uploadPlagiarismCheck")
    public String uploadPlagiarismCheck(@PathVariable("monographId") Long monographId,
                                        @RequestParam("plagiarismCheck") MultipartFile file) {
        Monograph monograph = monographService.getMonographById(monographId);
        monographService.uploadPlagiarismCheck(monograph, file);
        return "redirect:/monographs/{monographId}";
    }
}