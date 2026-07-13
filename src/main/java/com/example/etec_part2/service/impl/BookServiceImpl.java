package com.example.etec_part2.service.impl;

import com.example.etec_part2.dto.request.BookRequest;
import com.example.etec_part2.dto.response.BookResponse;
import com.example.etec_part2.entity.Book;
import com.example.etec_part2.exception.ResourceNotFoundException;
import com.example.etec_part2.repository.BookRepository;
import com.example.etec_part2.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public BookResponse create(BookRequest bookRequest, MultipartFile file) {

        String originalFileName = file.getOriginalFilename();
        String fileName = UUID.randomUUID() + "_" + originalFileName;
        Path path = Paths.get("upload");

        String imageUrl = "http://localhost:8080/upload/" + fileName;

        try{
            if(!Files.exists(path)){
                Files.createDirectories(path);
            }

            Files.copy(file.getInputStream(), path.resolve(Objects.requireNonNull(fileName)));

        } catch (IOException e) {
            e.getStackTrace();
            throw new ResourceNotFoundException("Fail to upload file" + e);
        }
        Book book = new Book();
        book.setImage(imageUrl);
        book.setTitle(bookRequest.getTitle());
        book.setAuthor(bookRequest.getAuthor());
        book.setCategory(bookRequest.getCategory());
        book.setPrice(bookRequest.getPrice());

        book = bookRepository.save(book);

        BookResponse bookResponse = new BookResponse();
        bookResponse.setId(book.getId());
        bookResponse.setTitle(book.getTitle());
        bookResponse.setAuthor(book.getAuthor());
        bookResponse.setCategory(book.getCategory());
        bookResponse.setPrice(book.getPrice());
        bookResponse.setImage(book.getImage());

        return bookResponse;
    }

    @Override
    public List<BookResponse> findAll() {

        return bookRepository.findAll().stream().map(book -> {
            BookResponse bookResponse = new BookResponse();

            bookResponse.setId(book.getId());
            bookResponse.setTitle(book.getTitle());
            bookResponse.setAuthor(book.getAuthor());
            bookResponse.setCategory(book.getCategory());
            bookResponse.setPrice(book.getPrice());
            bookResponse.setImage(book.getImage());

            return bookResponse;
        }).toList();
    }

    @Override
    public BookResponse findById(Long id) {

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Book with  " + id + "  not found"
                ));

        BookResponse bookResponse = new BookResponse();
        bookResponse.setId(book.getId());
        bookResponse.setTitle(book.getTitle());
        bookResponse.setAuthor(book.getAuthor());
        bookResponse.setCategory(book.getCategory());
        bookResponse.setPrice(book.getPrice());
        bookResponse.setImage(book.getImage());

        return bookResponse;
    }

    @Override
    public BookResponse update(Long id, BookRequest bookRequest, MultipartFile file) {

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Book with id " + id + " not found"
                ));

        if(file != null && !file.isEmpty()){
            String originalFileName = file.getOriginalFilename();
            String fileName = UUID.randomUUID() + "_" + originalFileName;
            Path path = Paths.get("upload");

            try{

                if(!Files.exists(path)){
                    Files.createDirectories(path);
                }

                Files.copy(file.getInputStream(), path.resolve(fileName));

                book.setImage(fileName);

            }catch (IOException e){
                throw new ResourceNotFoundException("Fail to upload file" + e);
            }
        }

        book.setTitle(bookRequest.getTitle());
        book.setAuthor(bookRequest.getAuthor());
        book.setCategory(bookRequest.getCategory());
        book.setPrice(bookRequest.getPrice());

        book = bookRepository.save(book);

        BookResponse bookResponse = new BookResponse();
        bookResponse.setId(book.getId());
        bookResponse.setTitle(book.getTitle());
        bookResponse.setAuthor(book.getAuthor());
        bookResponse.setCategory(book.getCategory());
        bookResponse.setPrice(book.getPrice());
        bookResponse.setImage(book.getImage());

        return bookResponse;
    }

    @Override
    public void delete(Long id) {

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found."));

        bookRepository.delete(book);
    }
}
