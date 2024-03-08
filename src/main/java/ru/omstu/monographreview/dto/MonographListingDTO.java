package ru.omstu.monographreview.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;
import ru.omstu.monographreview.models.enumeration.MonographStatus;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class MonographListingDTO {

    private Long id;
    private String name;
    private MonographStatus status;
    private String authorFullName;

    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    private LocalDateTime createdAt;

    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    private LocalDateTime updatedAt;
}
