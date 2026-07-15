package com.cognizant.library.service;

import com.cognizant.library.model.Author;
import com.cognizant.library.repository.AuthorRepository;

import java.util.List;
import java.util.Optional;

/**
 * Dependency arrives via constructor (final field, set once, never null),
 * not @Autowired on the field. See AppConfig for why.
 */
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author create(Author author) {
        return authorRepository.save(author);
    }

    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    public Optional<Author> findById(Long id) {
        return authorRepository.findById(id);
    }
}
