package com.example.etec_part2.controller;

import com.example.etec_part2.dto.request.UserRequest;
import com.example.etec_part2.dto.response.UserResponse;
import com.example.etec_part2.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public UserResponse create(
            @ModelAttribute UserRequest userRequest,
            @RequestParam("file") MultipartFile file
    ) {
        return userService.create(userRequest, file);
    }

    @GetMapping("/{id}")
    public UserResponse getById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @GetMapping
    public List<UserResponse> getAll() {
        return userService.findAll();
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public UserResponse update(
            @PathVariable Long id,
            @ModelAttribute UserRequest userRequest,
            @RequestParam(value = "file", required = false) MultipartFile file
    ) {
        return userService.update(id, userRequest, file);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        userService.delete(id);
        return "User deleted successfully";
    }
}