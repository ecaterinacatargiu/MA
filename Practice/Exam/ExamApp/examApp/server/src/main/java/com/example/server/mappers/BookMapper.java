package com.example.server.mappers;

import com.example.server.model.Book;
import com.example.server.model.dto.BookDTO;
import org.mapstruct.Mapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookDTO bookToDTO(Book book);

    Book bookFromDTO(BookDTO bookTO);

    List<BookDTO> bookDTOList(List<Book> books);
}
