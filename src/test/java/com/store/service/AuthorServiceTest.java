package com.store.service;

import com.store.entity.Author;
import com.store.repository.AuthorRepo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;

@SpringBootTest
public class AuthorServiceTest {
    @InjectMocks
    AuthorService authorService;

    @Mock
    AuthorRepo authorRepo;

    @Test
    void testFindByEmail(){
        Optional<Author> authorMockito = Optional.of(new Author("Mahmoud","mahmoud@gmail.com"));

        Mockito.when(authorRepo.findByEmail(Mockito.anyString())).thenReturn(authorMockito);

        Optional<Author> author = authorService.findByEmail("dddd");

        assertTrue(author.isPresent());
    }
}
