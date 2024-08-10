package com.example.lms.repositories;

import com.example.lms.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin,Long> {
    Admin findByAdminId(Long id);
}
