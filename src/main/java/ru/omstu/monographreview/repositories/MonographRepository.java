package ru.omstu.monographreview.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.omstu.monographreview.models.Monograph;

@Repository
public interface MonographRepository extends JpaRepository<Monograph, Long> {
    Page<Monograph> findAllByAuthorId(Long authorId, Pageable pageable);
    Page<Monograph> findAllByReviewersId(Long userId, Pageable pageable);
}
