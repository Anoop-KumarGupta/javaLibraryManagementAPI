package com.example.lms.repositories;

import com.example.lms.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author,Long> {
    Author findByAuthorId(Long id);
}
