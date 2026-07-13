package com.example.etec_part2.service.impl;

import com.example.etec_part2.dto.request.UserRequest;
import com.example.etec_part2.dto.response.AddressResponse;
import com.example.etec_part2.dto.response.UserResponse;
import com.example.etec_part2.entity.Address;
import com.example.etec_part2.entity.User;
import com.example.etec_part2.exception.ResourceNotFoundException;
import com.example.etec_part2.repository.UserRepository;
import com.example.etec_part2.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

//    private UserResponse map(User user){
//        UserResponse userResponse = new UserResponse();
//        userResponse.setId(user.getId());
//        userResponse.setName(user.getName());
//        userResponse.setEmail(user.getEmail());
//
//        if(user.getAddress() != null){
//            AddressResponse addressResponse = new AddressResponse();
//            addressResponse.setId(user.getAddress().getId());
//            addressResponse.setStreet(user.getAddress().getStreet());
//            addressResponse.setCity(user.getAddress().getCity());
//            addressResponse.setImage(user.getAddress().getImage());
//
//            userResponse.setAddressResponse(addressResponse);
//        }
//        return userResponse;
//    }

    // helper: keep both sides of the one-to-one relationship in sync
    private void linkAddress(User user, Address address) {
        user.setAddress(address);
        address.setUser(user);
    }

    @Override
    public UserResponse create(UserRequest request, MultipartFile file) {

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path path = Paths.get("upload");
        String imageUrl = "http://localhost:8080/upload/" + fileName;

        try{

            if(!Files.exists(path)){
                Files.createDirectories(path);
            }

            Files.copy(file.getInputStream(), path.resolve(fileName));

        }catch (IOException e){
            throw new ResourceNotFoundException("Fail to upload file" + e);
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());

        if (request.getAddressRequest() != null) {

            Address address = new Address();
            address.setStreet(request.getAddressRequest().getStreet());
            address.setCity(request.getAddressRequest().getCity());
            address.setImage(imageUrl);

//            user.setAddress(address);
            linkAddress(user,address);
        }

        User saved = userRepository.save(user);

        UserResponse userResponse = new UserResponse();
        userResponse.setId(saved.getId());
        userResponse.setName(saved.getName());
        userResponse.setEmail(saved.getEmail());

        if(saved.getAddress() != null){
            AddressResponse addressResponse = new AddressResponse();
            addressResponse.setId(saved.getAddress().getId());
            addressResponse.setStreet(saved.getAddress().getStreet());
            addressResponse.setCity(saved.getAddress().getCity());
            addressResponse.setImage(saved.getAddress().getImage());

            userResponse.setAddress(addressResponse);

        }

        return userResponse;


    }

    @Override
    public List<UserResponse> findAll() {
        List<User> users = userRepository.findAll();
        List<UserResponse> responses = new ArrayList<>();

        for (User user : users) {
            UserResponse response = new UserResponse();
            response.setId(user.getId());
            response.setName(user.getName());
            response.setEmail(user.getEmail());

            if (user.getAddress() != null) {
                AddressResponse addressResponse = new AddressResponse();
                addressResponse.setId(user.getAddress().getId());
                addressResponse.setStreet(user.getAddress().getStreet());
                addressResponse.setCity(user.getAddress().getCity());
                addressResponse.setImage(user.getAddress().getImage());
                response.setAddress(addressResponse);
            }

            responses.add(response);
        }

        return responses;
    }

    @Override
    public UserResponse findById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());

        if (user.getAddress() != null) {
            AddressResponse addressResponse = new AddressResponse();
            addressResponse.setId(user.getAddress().getId());
            addressResponse.setStreet(user.getAddress().getStreet());
            addressResponse.setCity(user.getAddress().getCity());
            addressResponse.setImage(user.getAddress().getImage());
            response.setAddress(addressResponse);
        }

        return response;
    }

    @Override
    public UserResponse update(Long id, UserRequest request, MultipartFile file) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        user.setName(request.getName());
        user.setEmail(request.getEmail());

        if (request.getAddressRequest() != null) {
            Address address = user.getAddress();
            if (address == null) {
                address = new Address();
            }
            address.setStreet(request.getAddressRequest().getStreet());
            address.setCity(request.getAddressRequest().getCity());

            if(file != null && !file.isEmpty()){
                String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
                Path path = Paths.get("upload");
                String imageUrl = "http://localhost:8080/upload/" + fileName;

                try{
                    if(!Files.exists(path)){
                        Files.createDirectories(path);
                    }
                    Files.copy(file.getInputStream(), path.resolve(fileName));

                }catch(IOException e){
                    throw new ResourceNotFoundException("Fail upload image " + e);
                }
                address.setImage(imageUrl);
            }

//            user.setAddress(address);
            linkAddress(user,address);
        }

        User updated = userRepository.save(user);

        UserResponse response = new UserResponse();
        response.setId(updated.getId());
        response.setName(updated.getName());
        response.setEmail(updated.getEmail());

        if (updated.getAddress() != null) {
            AddressResponse addressResponse = new AddressResponse();
            addressResponse.setId(updated.getAddress().getId());
            addressResponse.setStreet(updated.getAddress().getStreet());
            addressResponse.setCity(updated.getAddress().getCity());
            addressResponse.setImage(updated.getAddress().getImage());
            response.setAddress(addressResponse);
        }

        return response;
    }

    @Override
    public void delete(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        userRepository.delete(user);
    }
}
