package com.example.etec_part2.dto.response;

import com.example.etec_part2.enums.Gender;
import lombok.Data;

@Data
public class StudentResponse {

    private Long id;

    private String name;

    private Gender gender;

    private Integer age;

    private String email;


}
