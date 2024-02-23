package ru.omstu.monographreview.models;

import jakarta.persistence.*;

import java.io.File;

@Entity
//@Data
@Table(name = "monograph")
//@Document
public class Monograph extends BaseEntity {

    private String name;
    private File monographText;
}
