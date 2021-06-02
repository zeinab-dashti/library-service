package com.zeinab.library;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zeinab.library.api.controller.BookController;
import com.zeinab.library.api.dto.BookDTO;
import com.zeinab.library.api.dto.BookDTOBuilder;
import com.zeinab.library.model.Book;
import com.zeinab.library.model.BookFormat;
import com.zeinab.library.service.BookService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = BookController.class)
public class LibraryUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper objectMapper;

    private static String API_URL;
    private static Book book1, book2;
    private static List<Book> books;
    private static BookDTO bookDTO1 = new BookDTO();
    private static BookDTO bookDTO2 = new BookDTO();

    @BeforeAll
    public static void setUp() {

        API_URL = "/api/v1/books";

        book1 = new Book(1L, "title 1", "author 1", "01-01-2021", BookFormat.AUDIO_BOOK);
        book2 = new Book(2L, "title 2", "author 2", "02-02-2021", BookFormat.PAPERBACK);
        books = Arrays.asList(book1, book2);

        bookDTO1 = new BookDTOBuilder()
                .setProperties("title 1", "author 1", "AUDIO_BOOK", "01-01-2021")
                .build();

        bookDTO2 = new BookDTOBuilder()
                .setProperties("title 2", "author 2", "PAPERBACK", "02-02-2021")
                .build();
    }

    @Test
    void whenGetBooks_thenReturns200() throws Exception {
        doReturn(books).when(bookService).getAllBooks();

        when(modelMapper.map(book1, BookDTO.class)).thenReturn(bookDTO1);
        when(modelMapper.map(book2, BookDTO.class)).thenReturn(bookDTO2);

        mockMvc.perform(get(API_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

    }

    @Test
    void whenGetBook_thenReturns200() throws Exception {
        doReturn(Optional.of(book1)).when(bookService).getBookById(book1.getId());
        when(modelMapper.map(book1, BookDTO.class)).thenReturn(bookDTO1);

        MvcResult mvcResult = mockMvc.perform(get(API_URL + "/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        BookDTO response = objectMapper.readValue(contentAsString, BookDTO.class);
        assertEquals(bookDTO1, response);
    }

    @Test
    void whenGetBookByIdNotFound() throws Exception {
        doReturn(Optional.empty())
                .when(bookService).getBookById(10);

        mockMvc.perform(get(API_URL + "/{id}", 10))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenCreateBook_thenReturns201() throws Exception {
        doReturn(book1).when(bookService).createBook(any());
        when(modelMapper.map(bookDTO1, Book.class)).thenReturn(book1);
        when(modelMapper.map(book1, BookDTO.class)).thenReturn(bookDTO1);

        MvcResult mvcResult = mockMvc.perform(post(API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJSON(bookDTO1)))
                .andExpect(status().isCreated())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        long response = objectMapper.readValue(contentAsString, Long.class);
        assertEquals(1L, response);
    }

    @Test
    void whenDelete_thenReturns200() throws Exception {

        doNothing().when(bookService).deleteBookById(book1.getId());

        MvcResult mvcResult = mockMvc.perform(delete(API_URL +"/" +book1.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        Mockito.verify(bookService,times(1)).deleteBookById(1);
        assertEquals(String.format("Book with ID %s deleted successfully.", book1.getId()),
                mvcResult.getResponse().getContentAsString());
    }

    private String toJSON(Object object) throws JsonProcessingException {
        return new ObjectMapper()
                .writer()
                .withDefaultPrettyPrinter()
                .writeValueAsString(object);
    }
}

