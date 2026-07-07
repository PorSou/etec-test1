package com.example.etec_part2.service;

import com.example.etec_part2.dto.request.BookRequest;
import com.example.etec_part2.dto.response.BookResponse;

public interface BookService {

    BookResponse create(BookRequest bookRequest);

}
