package com.example.etec_part2.service.impl;

import com.example.etec_part2.dto.request.BookRequest;
import com.example.etec_part2.dto.response.BookResponse;
import com.example.etec_part2.repository.BookRepository;
import com.example.etec_part2.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public BookResponse create(BookRequest bookRequest) {
        return null;
    }
}
