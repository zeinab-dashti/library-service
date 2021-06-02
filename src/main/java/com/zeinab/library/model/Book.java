package com.zeinab.library.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id @GeneratedValue
    private Long id;
    private String title;
    private String author;
    private String publicationDate;

    @Enumerated
    private BookFormat bookFormat;

}
