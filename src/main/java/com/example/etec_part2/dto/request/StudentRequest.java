package com.example.etec_part2.dto.request;

import com.example.etec_part2.enums.Gender;
import lombok.Data;

@Data
public class StudentRequest {

    private String name;

    private Gender gender;

    private Integer age;

    private String email;
}
