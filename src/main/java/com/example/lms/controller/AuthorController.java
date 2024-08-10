package com.example.lms.controller;


import com.example.lms.dto.AuthorDTO;
import com.example.lms.dto.AuthorResponeDto;
import com.example.lms.model.Author;
import com.example.lms.service.AuthorServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/authors")
public class AuthorController {
    @Autowired
    private AuthorServices authorServices;

    @GetMapping
    public List<Author> getAllAuthors(){
        return authorServices.getAllAuthors();
    }

    @GetMapping
    @RequestMapping("{id}")
    public Author getOneAuthor(@PathVariable Long id){
        return authorServices.getOneAuthor(id);
    }

    // reading csv data and uploading to database
    @GetMapping
    @RequestMapping("/csvData")
    public String getCsvData() throws Exception {
        return authorServices.getCsvData();
    }

    // creating a new author details
    @PostMapping
    public ResponseEntity<Author> createAuthorDetails(@RequestBody final Author author) throws Exception {
        Author createdAuthor= authorServices.createAuthorDetails(author);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAuthor);
    }


    // Deleting an Author using his id
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteOneAuthor(@PathVariable Long id){
        String message= authorServices.deleteOneAuthor(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(message);
    }

    @RequestMapping(value = "{id}",method = RequestMethod.PUT)
    public ResponseEntity<AuthorResponeDto> updateAuthorDetails(@PathVariable Long id, @RequestBody AuthorDTO authorDTO){
        AuthorResponeDto authorResponeDto= authorServices.updateAuthorDetails(id,authorDTO);
        return ResponseEntity.status(HttpStatus.OK).body(authorResponeDto);
    }
}
