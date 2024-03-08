package ru.omstu.monographreview.models;

import jakarta.persistence.PrePersist;

public class UserEntityListener {
    @PrePersist
    public void prePersist(User user){
        Author author = new Author();
        user.setAuthor(author);
        author.setUser(user);
    }
}
