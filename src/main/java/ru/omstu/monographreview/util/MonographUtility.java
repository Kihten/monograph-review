package ru.omstu.monographreview.util;

import ru.omstu.monographreview.dto.MonographListingDTO;
import ru.omstu.monographreview.models.Author;
import ru.omstu.monographreview.models.Monograph;
import ru.omstu.monographreview.models.User;

public class MonographUtility {
    public static MonographListingDTO convertToListingDTO(Monograph monograph){
        return new MonographListingDTO()
                .setId(monograph.getId())
                .setName(monograph.getName())
                .setStatus(monograph.getStatus())
                .setAuthorFullName(createFullName(monograph.getAuthor()));
    }

    private static String createFullName(Author author) {
        User user = author.getUser();
        return user.getLastName() + " " + user.getFirstName();
    }
}
