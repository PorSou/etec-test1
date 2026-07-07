package com.example.etec_part2.service.impl;

import com.example.etec_part2.dto.request.StudentRequest;
import com.example.etec_part2.dto.response.StudentResponse;
import com.example.etec_part2.entity.Student;
import com.example.etec_part2.exception.ResourceNotFoundException;
import com.example.etec_part2.repository.StudentRepository;
import com.example.etec_part2.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Override
    public StudentResponse create(StudentRequest studentRequest) {

        Student student = new Student();
        student.setName(studentRequest.getName());
        student.setAge(studentRequest.getAge());
        student.setEmail(studentRequest.getEmail());
        student.setGender(studentRequest.getGender());

        if(studentRepository.existsByEmail(studentRequest.getEmail())){
            throw new ResourceNotFoundException("Email is already exist!");
        }

        student = studentRepository.save(student);

        StudentResponse studentResponse = new StudentResponse();
        studentResponse.setId(student.getId());
        studentResponse.setName(student.getName());
        studentResponse.setAge(student.getAge());
        studentResponse.setGender(student.getGender());
        studentResponse.setEmail(student.getEmail());

        return studentResponse;
    }

    @Override
    public List<StudentResponse> findAll() {
        List<Student> studentList = studentRepository.findAll();

        List<StudentResponse> studentResponses = studentList.stream()
                .map(student -> {
                    StudentResponse res = new StudentResponse();
                    res.setId(student.getId());
                    res.setName(student.getName());
                    res.setAge(student.getAge());
                    res.setGender(student.getGender());
                    res.setEmail(student.getEmail());
                    return res;
                }).toList();

    return studentResponses;

    }

    @Override
    public StudentResponse findById(Long id) {
        Student student = studentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Student with id " + id + " not found.")
        );

        StudentResponse studentResponse = new StudentResponse();
        studentResponse.setId(student.getId());
        studentResponse.setName(student.getName());
        studentResponse.setAge(student.getAge());
        studentResponse.setGender(student.getGender());
        studentResponse.setEmail(student.getEmail());

        return studentResponse;
    }

    @Override
    public StudentResponse update(Long id, StudentRequest studentRequest) {

        Student student = studentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Student with id " + id + " not found."));

        student.setName(studentRequest.getName());
        student.setAge(studentRequest.getAge());
        student.setGender(studentRequest.getGender());
        student.setEmail(studentRequest.getEmail());

        student = studentRepository.save(student);

        StudentResponse studentResponse = new StudentResponse();
        studentResponse.setId(student.getId());
        studentResponse.setName(student.getName());
        studentResponse.setAge(student.getAge());
        studentResponse.setGender(student.getGender());
        studentResponse.setEmail(student.getEmail());

        return studentResponse;
    }

    @Override
    public void delete(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student with id " + id + " not found."));

        studentRepository.delete(student);
    }
}
