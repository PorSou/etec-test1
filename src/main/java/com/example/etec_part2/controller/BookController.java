package com.example.etec_part2.controller;

import com.example.etec_part2.dto.request.BookRequest;
import com.example.etec_part2.dto.response.BookResponse;
import com.example.etec_part2.exception.ApiResponse;
import com.example.etec_part2.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    public ApiResponse<BookResponse> create(
            @ModelAttribute BookRequest bookRequest,
            @RequestParam("file") MultipartFile file
    ){

        return new ApiResponse<>(
                true,
                201,
                "Create book successfully",
                bookService.create(bookRequest,file),
                LocalDateTime.now()
        );
    }

    @GetMapping
    public ApiResponse<List<BookResponse>> findAll(){
        return new
                ApiResponse<>(
                true,
                200,
                "Get All books successfully",
                bookService.findAll(),
                LocalDateTime.now());}

    @GetMapping("/{id}")
    public ApiResponse<BookResponse> findById(@PathVariable Long id){

        return new ApiResponse<>(
                true,
                200,
                "Get book successfully",
                bookService.findById(id),
                LocalDateTime.now()
        );
    }

    @PutMapping("/{id}")
    public BookResponse update(
            @PathVariable Long id,
            @ModelAttribute BookRequest bookRequest,
            @RequestParam(value = "file", required = false) MultipartFile file
    ){
        return bookService.update(id,bookRequest,file);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        bookService.delete(id);
    }

}
