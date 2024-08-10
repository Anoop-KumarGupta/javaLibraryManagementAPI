package com.example.lms.controller;


import com.example.lms.model.Book;
import com.example.lms.service.BookServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    @Autowired
    private BookServices bookServices;


    // getting All books details
    @GetMapping
    public List<Book> getAllBooks(){
        return bookServices.getAllBooks();
    }

    // getting one Book Details
    @GetMapping
    @RequestMapping("{id}")
    public Book getOneBook(@PathVariable Long id){
        return bookServices.getOneBook(id);
    }


    // directly creating book details -- will remove it later
    @PostMapping
    public Book createBookDetails(@RequestBody final Book book){
        return bookServices.createBookDetails(book);
    }


    // Deleting a book using its id
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public String deleteOneBook(@PathVariable Long id){
        return bookServices.deleteOneBook(id);
    }

    @RequestMapping(value = "{id}",method = RequestMethod.PUT)
    public Book updateBookDetails(@PathVariable Long id, @RequestBody Book bookDetails){
        return bookServices.updateBookDetails(id, bookDetails);
    }

}

