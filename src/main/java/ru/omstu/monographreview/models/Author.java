package ru.omstu.monographreview.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "author")
public class Author {

    @Id
    private Long id;

    @OneToOne
    @MapsId
    private User user;

    @OneToMany(mappedBy = "author")
    private List<Monograph> monographs = new ArrayList<>();
}
