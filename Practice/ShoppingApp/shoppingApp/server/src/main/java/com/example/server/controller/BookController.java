package com.example.server.controller;

import com.example.server.mappers.BookMapper;
import com.example.server.model.Book;
import com.example.server.model.dto.BookDTO;
import com.example.server.model.dto.BooksListDTO;
import com.example.server.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class BookController {

    @Autowired
    private final BookMapper bookMapper;

    private final BookService bookService;

    public BookController(BookMapper bookMapper, BookService bookService)
    {
        this.bookMapper = bookMapper;
        this.bookService = bookService;
    }

    @RequestMapping(value = "/books", method = RequestMethod.GET)
    public BooksListDTO getAllBooks() {

        return BooksListDTO.builder()
                .books(bookMapper.bookDTOList(bookService.getBooks()))
                .build();
    }

    @RequestMapping(value = "/books", method = RequestMethod.POST)
    public BookDTO saveBook(@RequestBody BookDTO bookDTO) {
        return bookMapper.bookToDTO(
                bookService.addBook(bookMapper.bookFromDTO(
                        bookDTO
                )));
    }

    @RequestMapping(value = "/books/{id}", method = RequestMethod.DELETE)
    public Book deleteBook(@PathVariable Long id) {
        return bookService.deleteBook(id);
    }


    @RequestMapping(value = "/books", method = RequestMethod.PUT)
    public BookDTO updateItem(@RequestBody BookDTO bookDTO) {
        return bookMapper.bookToDTO(bookService.updateBook(
                bookMapper.bookFromDTO(bookDTO)));
    }
}
