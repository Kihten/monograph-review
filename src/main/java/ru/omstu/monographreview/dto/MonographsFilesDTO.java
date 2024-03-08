package ru.omstu.monographreview.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class MonographsFilesDTO {

    @NotBlank
    private String name;
    private MultipartFile serviceNote;
    private MultipartFile titlePage;
    private MultipartFile contentPage;
    private MultipartFile text;
    private MultipartFile expertConclusion;
}
