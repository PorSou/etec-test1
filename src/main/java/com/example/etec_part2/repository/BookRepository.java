package com.example.etec_part2.repository;

import com.example.etec_part2.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book,Long> {
}
