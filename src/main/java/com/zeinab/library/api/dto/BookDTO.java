package com.zeinab.library.api.dto;


import lombok.Data;

@Data
public class BookDTO {

    private String title;
    private String author;
    private String bookFormat;
    private String publicationDate;
}
