package com.cognizant.rest.controller;

import com.cognizant.rest.dto.BookRequest;
import com.cognizant.rest.dto.BookResponse;
import com.cognizant.rest.repository.BookRepository;
import com.cognizant.rest.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookRepository bookRepository) {
        this.bookService = new BookService(bookRepository);
    }

    @PostMapping
    public ResponseEntity<BookResponse> create(@Valid @RequestBody BookRequest request) {
        BookResponse created = bookService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public List<BookResponse> findAll() {
        return bookService.findAll();
    }

    // Throws ResourceNotFoundException -> caught by GlobalExceptionHandler -> 404,
    // not an unhandled exception -> 500.
    @GetMapping("/{id}")
    public BookResponse findById(@PathVariable Long id) {
        return bookService.findById(id);
    }
}
