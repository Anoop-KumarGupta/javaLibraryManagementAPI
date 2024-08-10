package com.example.lms.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="issued")
@Table(name="Issued")
public class Issued {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "issued_id")
    private long issuedId;

    @OneToOne
    @JoinTable(name="book_id")
    private Book book;

    @ManyToOne
    @JoinTable(name="member_id")
    private Member member;

    @Column(name = "issued_date")
    private String issuedDate;

    @Column(name = "issued_till")
    private String issuedTill;

}
