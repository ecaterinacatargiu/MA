package com.example.server.service;


import com.example.server.model.Book;
import com.example.server.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepo)
    {
        this.bookRepository = bookRepo;
    }

    public List<Book> getBooks()
    {
        return bookRepository.findAll();
    }

    @Transactional
    public Book addBook(Book book)
    {
        return bookRepository.save(book);
    }

    @Transactional
    public Book deleteBook(Long bookID)
    {
        AtomicReference<Book> bookResult = new AtomicReference<>(new Book());
        bookResult.get().setId(-1l);
        bookRepository.findById(bookID).ifPresent(book -> {
            bookRepository.delete(book);
            bookResult.set(book);
        });
        return bookResult.get();
    }

    @Transactional
    public Book updateBook(Book book) {
        bookRepository.findById(book.getId()).ifPresentOrElse(
                (oldBook) -> {
                    oldBook.setTitle(book.getTitle());
                    oldBook.setAuthor(book.getAuthor());
                    oldBook.setYear(book.getYear());
                    oldBook.setDescription(book.getDescription());
                    oldBook.setRating(book.getRating());},
                () -> { book.setId(-1L); });
        return book;
    }
}

