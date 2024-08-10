package com.example.lms.repositories;

import com.example.lms.model.Issued;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssuedRepository extends JpaRepository<Issued,Long> {
    Issued findByIssuedId(Long id);
}
