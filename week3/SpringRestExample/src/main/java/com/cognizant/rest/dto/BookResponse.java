package com.cognizant.rest.dto;

import com.cognizant.rest.model.Book;

/**
 * Output DTO. Keeps the wire format stable even if the Book entity's
 * internal shape changes later.
 */
public class BookResponse {

    private final Long id;
    private final String title;
    private final String author;
    private final int publicationYear;

    public BookResponse(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.publicationYear = book.getPublicationYear();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getPublicationYear() {
        return publicationYear;
    }
}
