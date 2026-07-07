package com.example.etec_part2.entity;

import com.example.etec_part2.enums.Gender;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(length = 20)
    private Integer age;

    @Column(unique = true)
    private String email;

}
