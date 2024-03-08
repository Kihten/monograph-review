package ru.omstu.monographreview.models;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;


@Entity
@Table(name = "\"role\"")
@Data
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name", nullable = false, unique = true)
    private String name;
    @Column(name = "description", nullable = false, unique = true)
    private String description;
    @Transient
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    @Override
    public String getAuthority() {
        return getName();
    }
}
