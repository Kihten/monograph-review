package ru.omstu.monographreview.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;
import ru.omstu.monographreview.models.enumeration.MonographStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "monograph")
public class Monograph {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Enumerated(EnumType.STRING)
    private MonographStatus status;
    @Column(name = "service_note_id")
    private String serviceNote;
    @Column(name = "title_id")
    private String title;
    @Column(name = "content_id")
    private String content;
    @Column(name = "text_id")
    private String text;
    @Column(name = "expert_conclusion_id")
    private String expertConclusion;
    @Column(name = "plagiarism_check_id")
    private String plagiarismCheck;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToMany
    @JoinTable(
            name = "monographs_reviewers",
            joinColumns = @JoinColumn(name = "monograph_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id")
    )
    private List<User> reviewers = new ArrayList<>();

    @CreationTimestamp
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    private LocalDateTime updatedAt;
}
