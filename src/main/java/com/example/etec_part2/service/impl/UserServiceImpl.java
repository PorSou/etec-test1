package com.example.etec_part2.service.impl;

import com.example.etec_part2.dto.request.UserRequest;
import com.example.etec_part2.dto.response.AddressResponse;
import com.example.etec_part2.dto.response.UserResponse;
import com.example.etec_part2.entity.Address;
import com.example.etec_part2.entity.User;
import com.example.etec_part2.exception.ResourceNotFoundException;
import com.example.etec_part2.repository.AddressRepository;
import com.example.etec_part2.repository.UserRepository;
import com.example.etec_part2.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    // helper: keep both sides of the one-to-one relationship in sync
    private void linkAddress(User user, Address address) {
        user.setAddress(address);
        address.setUser(user);
    }

    @Override
    public UserResponse create(UserRequest userRequest) {

        User user = new User();
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());

        if (userRequest.getAddressId() != null) {

            Address address = addressRepository.findById(userRequest.getAddressId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Address not found with id: " + userRequest.getAddressId()
                    ));

            // user.setAddress(address)
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
    public UserResponse update(Long id, UserRequest userRequest) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());

        if(userRequest.getAddressId() != null){
            Address address = addressRepository.findById(userRequest.getAddressId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Address not found with id: " + userRequest.getAddressId()
                    ));
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
