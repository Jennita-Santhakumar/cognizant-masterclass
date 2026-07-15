package com.cognizant.library.model;

import jakarta.persistence.*;

/**
 * "Many" side of the relationship: each Book belongs to exactly one Author.
 */
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String isbn;

    // FetchType.LAZY: the Author is only loaded from the DB when
    // book.getAuthor() is actually called, not automatically whenever a
    // Book is fetched.
    //
    // Trade-off documented here on purpose (Hibernate lazy vs eager):
    //   - LAZY avoids pulling in data you don't need (good default, keeps
    //     "get all books" queries cheap).
    //   - But if you loop over a list of Books and call getAuthor() on each
    //     one *outside* the original transaction/session, Hibernate either
    //     throws LazyInitializationException, or - if you fetch it inside
    //     an open session - fires one extra SELECT per book. That's the
    //     classic N+1 query problem: 1 query for the books, N queries for
    //     N authors, instead of 1 query total.
    //   - The fix, when you know you'll need the author, is a JOIN FETCH
    //     in the repository query (see BookRepository.findAllWithAuthor)
    //     rather than switching the mapping to EAGER globally, since EAGER
    //     pays that join cost on every single load whether you need it or not.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    protected Book() {
    }

    public Book(String title, String isbn) {
        this.title = title;
        this.isbn = isbn;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getIsbn() {
        return isbn;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
