package com.store.repository;

import com.store.entity.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AuthorRepoTest {

    @Autowired
    AuthorRepo authorRepo;
    @Test
    void findByEmailNotFoundTest(){
        Optional<Author> author = authorRepo.findByEmail("msra@gmail.com");
        assertFalse(author.isPresent());
    }

    @Test
    void findByEmailTest(){
        Optional<Author> author = authorRepo.findByEmail("mra@gmail.com");
        assertTrue(author.isPresent());
    }
}
