package com.example.etec_part2.controller;

import com.example.etec_part2.dto.request.UserRequest;
import com.example.etec_part2.dto.response.UserResponse;
import com.example.etec_part2.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public UserResponse create(@RequestBody UserRequest userRequest){
        return userService.create(userRequest);
    }

    @GetMapping
    public List<UserResponse> findAll(){
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public UserResponse findById(@PathVariable Long id){
        return userService.findById(id);
    }

    @PutMapping("/{id}")
    public UserResponse update(@PathVariable Long id,@RequestBody UserRequest userRequest){
        return userService.update(id,userRequest);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        userService.delete(id);
    }

}
