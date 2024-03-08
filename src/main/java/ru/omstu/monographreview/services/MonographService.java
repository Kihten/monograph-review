package ru.omstu.monographreview.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.omstu.monographreview.dto.MonographListingDTO;
import ru.omstu.monographreview.models.Author;
import ru.omstu.monographreview.models.Monograph;
import ru.omstu.monographreview.models.enumeration.MonographStatus;
import ru.omstu.monographreview.models.enumeration.RequestFileType;
import ru.omstu.monographreview.repositories.MonographRepository;
import ru.omstu.monographreview.util.MonographUtility;

import java.io.IOException;

@Service
public class MonographService {

    @Autowired
    private MonographRepository monographRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private FileService fileService;

    public Monograph saveMonographWithName(String name) {
        Monograph monograph = new Monograph();
        Author author = userService.getCurrentAuthor();
        if (author != null) {
            monograph.setAuthor(author);
        }

        monograph.setName(name);
        monograph.setStatus(MonographStatus.APPLICATION_CHECK);

        monographRepository.save(monograph);
        return monograph;
    }

    public Monograph getMonographById(Long id) {
        return monographRepository.findById(id).orElse(null);
    }

    public Page<MonographListingDTO> getMonographs(Pageable pageable, String keyword) {
        Long userId = userService.getCurrentUser().getId();
        Page<Monograph> monographs;
        switch (keyword){
            case "all" -> monographs = monographRepository.findAll(pageable);
            case "reviewed" -> monographs = monographRepository.findAllByReviewersId(userId, pageable);
            default -> monographs = monographRepository.findAllByAuthorId(userId, pageable);
        }

        return monographs.map(MonographUtility::convertToListingDTO);
    }

    public void uploadServiceNote(Monograph monograph, MultipartFile file) {
        try {
            String fileId = fileService.uploadFile(file, RequestFileType.SERVICE_NOTE);

            monograph.setServiceNote(fileId);
            monographRepository.save(monograph);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void uploadTitle(Monograph monograph, MultipartFile file) {
        try {
            String fileId = fileService.uploadFile(file, RequestFileType.TITLE);

            monograph.setTitle(fileId);
            monographRepository.save(monograph);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void uploadContent(Monograph monograph, MultipartFile file) {
        try {
            String fileId = fileService.uploadFile(file, RequestFileType.CONTENT);

            monograph.setContent(fileId);
            monographRepository.save(monograph);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void uploadText(Monograph monograph, MultipartFile file) {
        try {
            String fileId = fileService.uploadFile(file, RequestFileType.TEXT);

            monograph.setText(fileId);
            monographRepository.save(monograph);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void uploadExpertConclusion(Monograph monograph, MultipartFile file) {
        try {
            String fileId = fileService.uploadFile(file, RequestFileType.EXPERT_CONCLUSION);

            monograph.setExpertConclusion(fileId);
            monographRepository.save(monograph);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void uploadPlagiarismCheck(Monograph monograph, MultipartFile file) {
        try {
            String fileId = fileService.uploadFile(file, RequestFileType.PLAGIARISM_CHECK);

            monograph.setPlagiarismCheck(fileId);
            monograph.setStatus(MonographStatus.REVIEWER_SELECTION);
            monographRepository.save(monograph);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addAuthorToMonograph() {

    }

    public void addReviewerToMonograph() {

    }
}
