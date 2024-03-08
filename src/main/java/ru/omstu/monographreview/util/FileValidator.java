package ru.omstu.monographreview.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.omstu.monographreview.dto.MonographsFilesDTO;

@Component
public class FileValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return MonographsFilesDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MonographsFilesDTO monographsFiles = (MonographsFilesDTO) target;
        if (monographsFiles.getServiceNote().isEmpty())
            errors.rejectValue("serviceNote", null, "Служебная записка не загружена");
        if (monographsFiles.getTitlePage().isEmpty())
            errors.rejectValue("titlePage", null, "Титульная страница не загружена");
        if (monographsFiles.getContentPage().isEmpty())
            errors.rejectValue("contentPage", null, "Содержание монографии не загружено");
        if (monographsFiles.getText().isEmpty())
            errors.rejectValue("text", null, "Текст монографии не загружен");
        if (monographsFiles.getExpertConclusion().isEmpty())
            errors.rejectValue("expertConclusion", null, "Экспертное заключение не загружено");
    }
}
