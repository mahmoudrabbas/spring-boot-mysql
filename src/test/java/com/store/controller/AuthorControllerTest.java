package com.store.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.cfg.MapperBuilder;
import com.store.entity.Author;
import com.store.service.AuthorService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthorControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthorService authorService;

    @Test
    void testFindByEmailEndPoint() throws Exception {
        String email = "mra@gmail.com";
        Author mockAuthor = new Author("Mahmoud", email);

        Mockito.when(authorService.findByEmail(email)).thenReturn(Optional.of(mockAuthor));

        mockMvc.perform(get("/authors/email/{email}", email).contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void testCreateAuthorEndPoint() throws Exception {
        Author mockAuthor = new Author("Mahmoud", "mra@gmail.com");

        Mockito.when(authorService.save(Mockito.any(Author.class))).thenReturn(mockAuthor);

        mockMvc.perform(post("/authors/add").contentType("application/json").content(objectMapper.writeValueAsBytes(mockAuthor)))
                .andExpect(status().isOk());
    }
}
