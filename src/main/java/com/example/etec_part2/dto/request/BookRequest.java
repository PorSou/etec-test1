package com.example.etec_part2.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookRequest {

    private String title;

    private String author;

    private String category;

    private Double price;

    private String image;

}
