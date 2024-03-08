package ru.omstu.monographreview.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne()
    private User user;
    private String body;
    private LocalDateTime created_at;

}
