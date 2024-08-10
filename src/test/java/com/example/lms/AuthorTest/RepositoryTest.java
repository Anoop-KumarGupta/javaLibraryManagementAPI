package com.example.lms.AuthorTest;


import com.example.lms.model.Author;
import com.example.lms.repositories.AuthorRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RepositoryTest {
    @Autowired
    private AuthorRepository authorRepository;

    @Test
    public void testSaveAndFindAuthor() {
        Author author = new Author(1L,"John Doe","Bad writer");
        authorRepository.save(author);
        Author foundAuthor = authorRepository.findById(author.getAuthorId()).orElse(null);
        assertNotNull(foundAuthor);
        assertEquals("John Doe", foundAuthor.getAuthorName());
    }
}




