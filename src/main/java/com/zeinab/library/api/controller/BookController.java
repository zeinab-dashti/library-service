package com.zeinab.library.api.controller;


import com.zeinab.library.api.dto.BookDTO;
import com.zeinab.library.model.Book;
import com.zeinab.library.service.BookService;
import io.swagger.annotations.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/books")
@Api(tags = "Book Controller")
public class BookController {

    @Autowired
    BookService bookService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    @ApiOperation(value = "Save a book")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid input book"),
            @ApiResponse(code = 200, message = "Save book done successfully")
    })
    public ResponseEntity<?> createBook(
            @ApiParam(value = "Save Book Object", required = true)
            @RequestBody BookDTO bookDTO) {
        try {
            Book createdBook = bookService.createBook(modelMapper.map(bookDTO, Book.class));
            return new ResponseEntity<>(createdBook.getId(), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Invalid input", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{bookId}")
    @ApiOperation(value = "Get a book")
    @ApiResponses({
            @ApiResponse(code = 404, message = "book not found"),
            @ApiResponse(code = 200, message = "Get book done successfully")
    })
    public ResponseEntity<?> getBookById(@ApiParam(value = "Identifier of a specified book", required = true)
                                             @PathVariable long bookId) {
        Optional<Book> book = bookService.getBookById(bookId);
        if (book.isPresent())
            return new ResponseEntity<>(modelMapper.map(book.get(), BookDTO.class), HttpStatus.OK);
        else
            return new ResponseEntity<>(String.format("Book with ID %s not found.", bookId), HttpStatus.NOT_FOUND);
    }

    @GetMapping
    @ApiOperation(value = "Get list of books")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Get all books done successfully")
    })
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        List<BookDTO> responseBody = books
                .stream()
                .map(book -> modelMapper.map(book, BookDTO.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @DeleteMapping("/{bookId}")
    @ApiOperation(value = "Delete a book")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Book not found"),
            @ApiResponse(code = 200, message = "Deleted Book done successfully")
    })
    public ResponseEntity<?> deleteBookBiId(@ApiParam(value = "Identifier of a specified book", required = true)
                                                @PathVariable long bookId) {
            bookService.deleteBookById(bookId);
            return new ResponseEntity<>(String.format("Book with ID %s deleted successfully.", bookId), HttpStatus.OK);
    }
}
