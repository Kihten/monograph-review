package ru.omstu.monographreview.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "author")
public class Author extends BaseEntity {


//    @OneToMany
//    private Iterable<Monograph> monographs;
}
