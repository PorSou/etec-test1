package com.example.etec_part2.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookResponse {

    private Long id;

    private String title;

    private String author;

    private String category;

    private Double price;

    private String image;

}
