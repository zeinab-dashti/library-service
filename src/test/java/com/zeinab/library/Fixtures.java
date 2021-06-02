package com.zeinab.library;

import com.zeinab.library.api.dto.BookDTO;
import com.zeinab.library.api.dto.BookDTOBuilder;

import java.util.Arrays;
import java.util.List;

public class Fixtures {

    static BookDTO book1 = new BookDTOBuilder().setProperties(
            "Book title 1",
            "Author 1",
            "AUDIO_BOOK",
            "01-01-2021").build();

    static BookDTO book2 = new BookDTOBuilder().setProperties(
            "Book title 2",
            "Author 1",
            "KINDLE",
            "02-02-2021").build();

    static BookDTO book3 = new BookDTOBuilder().setProperties(
            "Book title 3",
            "Author 3",
            "PAPERBACK",
            "03-03-2021").build();

    static List<BookDTO> books = Arrays.asList(book1, book2, book3);
}
