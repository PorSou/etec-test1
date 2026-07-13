package com.example.etec_part2.service;

import com.example.etec_part2.dto.request.BookRequest;
import com.example.etec_part2.dto.response.BookResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BookService {

    BookResponse create(BookRequest bookRequest, MultipartFile file);

    List<BookResponse> findAll();

    BookResponse findById(Long id);

    BookResponse update(Long id, BookRequest bookRequest, MultipartFile file);

    void delete(Long id);

}
