package ru.omstu.monographreview.models.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RequestFileType {
    SERVICE_NOTE("Служебная записка"),
    TITLE("Титульный лист"),
    CONTENT("Содержание"),
    TEXT("Текст монографии"),
    EXPERT_CONCLUSION("Экспертное заключение"),
    PLAGIARISM_CHECK("Справка о наличии заимствований");

    private final String text;
}
