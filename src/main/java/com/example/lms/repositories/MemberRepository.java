package com.example.lms.repositories;

import com.example.lms.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {
    Member findByMemberId(Long id);
}
