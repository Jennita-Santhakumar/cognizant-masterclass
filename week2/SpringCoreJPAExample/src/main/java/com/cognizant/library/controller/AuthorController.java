package com.cognizant.library.controller;

import com.cognizant.library.model.Author;
import com.cognizant.library.model.Book;
import com.cognizant.library.repository.BookRepository;
import com.cognizant.library.service.AuthorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Minimal CRUD surface over the one-to-many Author/Book relationship.
 * Depth here is the point, not REST polish (that's Week 3's job).
 */
@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    private final AuthorService authorService;
    private final BookRepository bookRepository;

    public AuthorController(AuthorService authorService, BookRepository bookRepository) {
        this.authorService = authorService;
        this.bookRepository = bookRepository;
    }

    @PostMapping
    public Author create(@RequestBody Author author) {
        return authorService.create(author);
    }

    @GetMapping
    public List<Author> findAll() {
        return authorService.findAll();
    }

    @PostMapping("/{authorId}/books")
    public Author addBook(@PathVariable Long authorId, @RequestBody Book book) {
        Author author = authorService.findById(authorId)
                .orElseThrow(() -> new IllegalArgumentException("No author with id " + authorId));
        author.addBook(book);
        authorService.create(author);
        return author;
    }

    @GetMapping("/books-with-author")
    public List<Book> booksWithAuthorJoinFetch() {
        // Uses the JOIN FETCH query -> one SQL statement, no N+1.
        return bookRepository.findAllWithAuthor();
    }
}
