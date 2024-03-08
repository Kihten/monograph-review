package ru.omstu.monographreview.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import ru.omstu.monographreview.models.enumeration.RequestFileType;

@Data
public class RequestFile {
    private String id;
    private String fileName;
    private byte[] data;
    @Enumerated(EnumType.STRING)
    private RequestFileType type;
}
