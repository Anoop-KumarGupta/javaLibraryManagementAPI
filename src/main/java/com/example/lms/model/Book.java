package com.example.lms.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="book")
@Table(name="Book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private long bookId;

    @Column(name = "book_name")
    private String bookName;

    @ManyToOne
    @JoinColumn(name="author_id")
    private Author author;


    @Column(name = "category_name")
    private String categoryName;


    @Column(name = "publisher_name")
    private String publisherName;

    @Column(name = "available")
    private boolean available;

}
