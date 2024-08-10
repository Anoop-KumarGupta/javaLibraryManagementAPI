package com.example.lms.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="admin")
@Table(name="Admin")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="admin_id")
    private long adminId;

    @Column(name="admin_name")
    private String adminName;

    @Column(name="admin_position")
    private String adminPosition;
}
