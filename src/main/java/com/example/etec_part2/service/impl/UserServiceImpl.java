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

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private UserResponse map(User user){
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setName(user.getName());
        userResponse.setEmail(user.getEmail());

        if(user.getAddress() != null){
            AddressResponse addressResponse = new AddressResponse();
            addressResponse.setId(user.getAddress().getId());
            addressResponse.setStreet(user.getAddress().getStreet());
            addressResponse.setCity(user.getAddress().getCity());

            userResponse.setAddressResponse(addressResponse);
        }
        return userResponse;
    }

    @Override
    public UserResponse create(UserRequest request) {

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());

        if (request.getAddressRequest() != null) {

            Address address = new Address();
            address.setStreet(request.getAddressRequest().getStreet());
            address.setCity(request.getAddressRequest().getCity());

            user.setAddress(address);
        }

        User saved = userRepository.save(user);

        return map(saved);
    }

    @Override
    public List<UserResponse> findAll() {
        return userRepository.findAll()
                .stream()
                .map(this::map)
                .toList();
    }

    @Override
    public UserResponse findById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User with "+ id +" not found!"
                ));

        return map(user);
    }

    @Override
    public UserResponse update(Long id, UserRequest request) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(request.getName());
        user.setEmail(request.getEmail());

        if (request.getAddressRequest() != null) {

            Address address = user.getAddress();

            if (address == null) {
                address = new Address();
            }

            address.setStreet(request.getAddressRequest().getStreet());
            address.setCity(request.getAddressRequest().getCity());

            user.setAddress(address);
        }

        User saved = userRepository.save(user);

        return map(saved);
    }

    @Override
    public void delete(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User with "+ id +" not found!"
                ));

        userRepository.delete(user);
    }
}
