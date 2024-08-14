package com.example.lms.BookTest;


import com.example.lms.model.Author;
import com.example.lms.model.Book;
import com.example.lms.repositories.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

//@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class BookController {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    ObjectMapper objectMapper;


    public Book createABookForTesting(){
        Author author=new Author(1L,"Anoop","An indian writer");
        Book book=new Book(1L,"ComicBook",author,"Fiction","Macmillan",true);
        return book;
    }
    public static String URLPATH="/api/v1/books";

    @Test
    @WithMockUser(username = "anoop", roles = {"EMPLOYEE"})
    public void testCreateAndGetBook() throws Exception{
        Book book=createABookForTesting();
        String response=mockMvc.perform(MockMvcRequestBuilders.post(URLPATH)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(book)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.author.authorName").value("Anoop"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author.authorBio").value("An indian writer"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.bookName").value("ComicBook"))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        Long createdBookId=objectMapper.readTree(response).get("bookId").asLong();

        mockMvc.perform(MockMvcRequestBuilders.get(URLPATH+"/{id}",createdBookId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.bookName").value("ComicBook"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author.authorName").value("Anoop"))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "anoop", roles = {"EMPLOYEE"})
    public void testDeleteBook() throws Exception{
        Book book=createABookForTesting();
        String response=mockMvc.perform(MockMvcRequestBuilders.post(URLPATH)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(book)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        Long createdBookId=objectMapper.readTree(response).get("bookId").asLong();

        mockMvc.perform(MockMvcRequestBuilders.delete(URLPATH+"/{id}",createdBookId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "anoop", roles = {"EMPLOYEE"})
    public void testUpdateBook() throws Exception{
        Book book=createABookForTesting();
        String response=mockMvc.perform(MockMvcRequestBuilders.post(URLPATH)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        Long createdBookId=objectMapper.readTree(response).get("bookId").asLong();

        Author author=new Author(1L,"Anoop","An indian writer");
        Book updateBook=new Book(1L,"ComicBook",author,"Technology","RupaPublication",true);
        mockMvc.perform(MockMvcRequestBuilders.put(URLPATH+"/{id}",createdBookId)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(updateBook)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.categoryName").value("Technology"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.publisherName").value("RupaPublication"))
                .andDo(print());
    }



}
