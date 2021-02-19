package com.example.server.model;

import lombok.*;

import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@Data
@Entity
@Getter
@Setter
public class Book extends BaseEntity {
    private String title;
    private String author;
    private String year;
    private String description;
    private String rating;
}