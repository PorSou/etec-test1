package com.example.etec_part2.service;

import com.example.etec_part2.dto.request.StudentRequest;
import com.example.etec_part2.dto.response.StudentResponse;

import java.util.List;

public interface StudentService {

    StudentResponse create(StudentRequest studentRequest);

    List<StudentResponse> findAll();

    StudentResponse findById(Long id);

    StudentResponse update(Long id, StudentRequest studentRequest);

    void delete(Long id);

}
