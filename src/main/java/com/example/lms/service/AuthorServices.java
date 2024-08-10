package com.example.lms.service;

import com.example.lms.customThread.ChunkReaderThread;
import com.example.lms.dto.AuthorDTO;
import com.example.lms.dto.AuthorResponeDto;
import com.example.lms.errorHandling.GlobalNotFoundException;
import com.example.lms.model.Author;
import com.example.lms.repositories.AuthorRepository;
import com.opencsv.CSVReader;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class AuthorServices {
    @Autowired
    private AuthorRepository authorRepository;

    @GetMapping
    public List<Author> getAllAuthors(){
        return authorRepository.findAll();
    }

    @GetMapping
    @RequestMapping("{id}")
    public Author getOneAuthor(@PathVariable Long id){
        if(authorRepository.findByAuthorId(id)==null){
            throw new GlobalNotFoundException("Author does exist!");
        }
        return authorRepository.findByAuthorId(id);
    }

    // creating a new author details
    @PostMapping
    public Author createAuthorDetails(@RequestBody final Author author) throws Exception {
        try{
            return authorRepository.saveAndFlush(author);
        }catch(Exception e){
            throw new Exception("Admin Not created, because of the mentioned reasons. Please Try again! Error message: "+e.getMessage());
        }
    }


    // Deleting an Author using his id
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public String deleteOneAuthor(@PathVariable Long id){
        Author author=authorRepository.findByAuthorId(id);
        if(author==null){
            return "author does not exist.";
        }
        authorRepository.deleteById(id);
        return "author deleted Successfully";
    }

    // Searching by id
    @RequestMapping(value = "{id}",method = RequestMethod.PUT)
    public AuthorResponeDto updateAuthorDetails(@PathVariable Long id, @RequestBody final AuthorDTO authorDto){
        Author existingAuthorDetails=authorRepository.findByAuthorId(id);
        if(existingAuthorDetails==null){
            throw new GlobalNotFoundException("Author does not exist.");
        }
        existingAuthorDetails.setAuthorName(authorDto.getAuthorName());
        authorRepository.saveAndFlush(existingAuthorDetails);
        AuthorResponeDto authorResponeDto=new AuthorResponeDto();
        authorResponeDto.setAuthorName(authorDto.getAuthorName());
        authorResponeDto.setMessage(authorResponeDto.getAuthorName()+", this name has been changed successfully");
        return authorResponeDto;
    }

    // reading csv file using multithreading concept and uploading it to the database
    public String getCsvData() throws Exception {
        final String CSV_FILE_PATH = "src/main/resources/csv/import/author.csv";
        final int CHUNK_SIZE = 5;
        try {
            List<Future<Void>> futures = new ArrayList<>();
            ExecutorService executorService = Executors.newFixedThreadPool(2);
            long totalLines = getLineCount(CSV_FILE_PATH);
            int numberOfChunks = (int) Math.ceil((double) totalLines / CHUNK_SIZE);
            for (int i = 0; i < numberOfChunks; i++) {
                int startLine = i * CHUNK_SIZE;
                int endLine = Math.min(startLine + CHUNK_SIZE, (int) totalLines);
                ChunkReaderThread task = new ChunkReaderThread(CSV_FILE_PATH, startLine, endLine, authorRepository);
                futures.add(executorService.submit(task));
            }
            for (Future<Void> future : futures) {
                future.get();
            }
            executorService.shutdown();
        } catch (Exception e) {
            throw new Exception("Could not able to perform the operation due to following reason: "+e.getMessage(),e);
        }
        return "Data has been successfully uploaded into the database";
    }
    public long getLineCount(String filePath) throws Exception {
        long lineCount = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/csv/import/author.csv"))) {
            while (reader.readLine() != null) {
                lineCount++;
            }
        } catch (Exception e) {
            throw new Exception("Could not able to perform the operation due to following reason: "+e.getMessage(),e);
        }
        return lineCount;
    }
}
