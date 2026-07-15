package com.cognizant.library.service;

import com.cognizant.library.model.Author;
import com.cognizant.library.repository.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Because AuthorService takes its dependency through the constructor, it can
 * be tested here with a plain Mockito mock and zero Spring context -- proof
 * that the constructor-injection decision in AppConfig pays off in practice.
 */
class AuthorServiceTest {

    private AuthorRepository authorRepository;
    private AuthorService authorService;

    @BeforeEach
    void setUp() {
        authorRepository = Mockito.mock(AuthorRepository.class);
        authorService = new AuthorService(authorRepository);
    }

    @Test
    void createDelegatesToRepositorySave() {
        Author author = new Author("Chinua Achebe");
        when(authorRepository.save(author)).thenReturn(author);

        Author result = authorService.create(author);

        assertEquals("Chinua Achebe", result.getName());
    }

    @Test
    void findByIdReturnsEmptyWhenAuthorMissing() {
        when(authorRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Author> result = authorService.findById(99L);

        assertTrue(result.isEmpty());
    }

    @Test
    void findAllReturnsWhatRepositoryReturns() {
        Author a1 = new Author("Author One");
        when(authorRepository.findAll()).thenReturn(List.of(a1));

        List<Author> result = authorService.findAll();

        assertEquals(1, result.size());
        assertEquals("Author One", result.get(0).getName());
    }
}
