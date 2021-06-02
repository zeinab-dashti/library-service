package com.zeinab.library.service;

import com.zeinab.library.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    Optional<Book> getBookById(long id);
    List<Book> getAllBooks();
    Book createBook(Book book);
    void deleteBookById(long id);
}
