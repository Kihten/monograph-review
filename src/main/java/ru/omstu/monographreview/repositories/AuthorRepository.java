package ru.omstu.monographreview.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.omstu.monographreview.models.Author;

import java.util.Optional;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {
    @Override
    Optional<Author> findById(Long id);
}
