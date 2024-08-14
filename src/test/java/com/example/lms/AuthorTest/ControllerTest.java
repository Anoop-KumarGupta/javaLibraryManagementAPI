package com.example.lms.AuthorTest;

import com.example.lms.model.Author;
import com.example.lms.repositories.AuthorRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class ControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private ObjectMapper objectMapper;



    @Test
    @WithMockUser(username = "anoop", roles = {"EMPLOYEE"})
    public void testCreateAndGetAuthor() throws Exception {
        // Creating an author
        Author author=new Author(null,"John Smith","Nice writer");
        String response=mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/authors")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(author)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        Long createdAuthorId = objectMapper.readTree(response).get("authorId").asLong();

        // Retrieve the author data
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/authors/{id}",createdAuthorId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.authorName").value("John Smith"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.authorBio").value("Nice writer"))
                .andDo(print());
    }


    @Test
    @WithMockUser(username = "anoop", roles = {"EMPLOYEE"})
    public void testDeleteAuthor() throws Exception {
            Author author=new Author(null,"John Smith","Nice writer");
            String response=mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/authors")
                            .contentType("application/json")
                            .content(objectMapper.writeValueAsString(author)))
                    .andExpect(MockMvcResultMatchers.status().isCreated())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();
        Long createdAuthorId = objectMapper.readTree(response).get("authorId").asLong();


            mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/authors/{id}",createdAuthorId))
                    .andExpect(MockMvcResultMatchers.status().isNoContent())
                    .andDo(print());
    }

    @Test
    @WithMockUser(username = "anoop", roles = {"EMPLOYEE"})
    public void testUpdateAuthor() throws Exception{
        // creating a user
        Author author=new Author(null,"Anup","Intern");
        String response=mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/authors")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(author)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn().getResponse().getContentAsString();
        Long createdAuthorId = objectMapper.readTree(response).get("authorId").asLong();

        // updating the user
        Author authorUpdatedDetails=new Author(null,"Anoop","Good Writer");
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/authors/{id}",createdAuthorId)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(authorUpdatedDetails)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.authorName").value("Anoop"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Anoop, this name has been changed successfully"));
    }
}
//---things to consider-----------//
//Use @WithMockUser to specify user details and roles for your tests.
//Configure test-specific security settings if necessary.
//Disable security for tests if the security aspect is not relevant.
//Ensure that the security context is correctly set up for each test.