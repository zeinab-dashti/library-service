package com.zeinab.library.service;

import com.zeinab.library.model.Book;
import com.zeinab.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    BookRepository bookRepository;

    @Override
    public Optional<Book> getBookById(long bookId) { return bookRepository.findById(bookId); }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public void deleteBookById(long bookId) { bookRepository.deleteById(bookId); }
}
