package com.cognizant.library.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * "One" side of a one-to-many relationship: one Author -> many Books.
 */
@Entity
@Table(name = "authors")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    // mappedBy = "author" means Book owns the foreign key (books.author_id).
    // cascade ALL: saving/removing an Author cascades to their Books, since
    // a Book in this domain doesn't make sense without its Author.
    // FetchType.LAZY (see Book.java for the reasoning) is the JPA default
    // for @OneToMany, kept explicit here for clarity.
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Book> books = new ArrayList<>();

    protected Author() {
        // JPA requires a no-arg constructor
    }

    public Author(String name) {
        this.name = name;
    }

    public void addBook(Book book) {
        books.add(book);
        book.setAuthor(this);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Book> getBooks() {
        return books;
    }
}
