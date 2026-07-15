package com.cognizant.rest.service;

import com.cognizant.rest.dto.BookRequest;
import com.cognizant.rest.dto.BookResponse;
import com.cognizant.rest.exception.ResourceNotFoundException;
import com.cognizant.rest.model.Book;
import com.cognizant.rest.repository.BookRepository;

import java.util.List;

public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public BookResponse create(BookRequest request) {
        Book saved = bookRepository.save(
                new Book(request.getTitle(), request.getAuthor(), request.getPublicationYear())
        );
        return new BookResponse(saved);
    }

    public List<BookResponse> findAll() {
        return bookRepository.findAll().stream().map(BookResponse::new).toList();
    }

    public BookResponse findById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No book with id " + id));
        return new BookResponse(book);
    }
}
