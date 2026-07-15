package com.cognizant.library.repository;

import com.cognizant.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    // Demonstrates the fix for the N+1 problem noted in Book.java:
    // JOIN FETCH pulls the Author back in the same query, so calling
    // getAuthor() on any result here does NOT trigger an extra SELECT.
    @Query("SELECT b FROM Book b JOIN FETCH b.author")
    List<Book> findAllWithAuthor();
}
