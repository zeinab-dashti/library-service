package com.zeinab.library.api.dto;

import lombok.Data;

@Data
public class BookDTOBuilder {

    private final BookDTO bookDTO = new BookDTO();

    public BookDTOBuilder setProperties(
            String title, String author, String bookFormat, String publicationDate) {
        bookDTO.setTitle(title);
        bookDTO.setAuthor(author);
        bookDTO.setBookFormat(bookFormat);
        //bookDTO.setPublicationDate(publicationDate);

        return this;
    }

    public BookDTO build() {
        return bookDTO;
    }
}
