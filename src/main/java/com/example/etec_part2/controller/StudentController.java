package com.example.etec_part2.controller;

import com.example.etec_part2.dto.request.StudentRequest;
import com.example.etec_part2.dto.response.StudentResponse;
import com.example.etec_part2.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    public StudentResponse create(@RequestBody StudentRequest studentRequest){
        return studentService.create(studentRequest);
    }

    @GetMapping
    public List<StudentResponse> findAll(){
        return studentService.findAll();
    }

    @GetMapping("/{id}")
    public StudentResponse findById(@PathVariable Long id){
        return studentService.findById(id);
    }

    @PutMapping("/{id}")
    public StudentResponse update(@PathVariable Long id,@RequestBody StudentRequest studentRequest){
        return studentService.update(id, studentRequest);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        studentService.delete(id);
    }
}
