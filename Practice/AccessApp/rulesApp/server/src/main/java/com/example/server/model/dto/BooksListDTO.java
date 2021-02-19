package com.example.server.model.dto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BooksListDTO {
    private List<BookDTO> books;
}
