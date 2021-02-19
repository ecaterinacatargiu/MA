package com.example.server.model.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BookDTO {
    private Long id;
    private String title;
    private String author;
    private String year;
    private String description;
    private String rating;
}
