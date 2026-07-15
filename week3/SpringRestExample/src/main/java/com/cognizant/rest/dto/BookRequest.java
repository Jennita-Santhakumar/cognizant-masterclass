package com.cognizant.rest.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

/**
 * Input DTO. Deliberately separate from the Book entity so:
 *  - Hibernate/JPA annotations never leak into the API contract.
 *  - Fields the client shouldn't set (id) simply don't exist here.
 *  - Validation rules can live on the boundary without polluting the entity.
 */
public class BookRequest {

    @NotBlank(message = "title must not be blank")
    private String title;

    @NotBlank(message = "author must not be blank")
    private String author;

    @Min(value = 0, message = "publicationYear must be a positive number")
    private int publicationYear;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }
}
