package com.example.etec_part2.service;

import com.example.etec_part2.dto.request.UserRequest;
import com.example.etec_part2.dto.response.UserResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    UserResponse create(UserRequest userRequest);

    List<UserResponse> findAll();

    UserResponse findById(Long id);

    UserResponse update(Long id, UserRequest userRequest);

    void delete(Long id);

}
