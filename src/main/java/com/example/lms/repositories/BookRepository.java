package com.example.lms.repositories;

import com.example.lms.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book,Long> {
     Book findByBookId(long id);
}
