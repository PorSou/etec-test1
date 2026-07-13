package com.example.etec_part2.service.impl;

import com.example.etec_part2.dto.request.AddressRequest;
import com.example.etec_part2.dto.response.AddressResponse;
import com.example.etec_part2.entity.Address;
import com.example.etec_part2.exception.ResourceNotFoundException;
import com.example.etec_part2.repository.AddressRepository;
import com.example.etec_part2.service.AddressService;
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
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    @Override
    public AddressResponse create(AddressRequest addressRequest, MultipartFile file) {

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path path = Paths.get("upload");
        String imageUrl = "http://localhost:8080/upload/" + fileName;

        try{

            if(!Files.exists(path)){
                Files.createDirectories(path);
            }

            Files.copy(file.getInputStream(), path.resolve(fileName));

        }catch (IOException e){
            throw new ResourceNotFoundException("File to upload file" + e);
        }

        Address address = new Address();
        address.setStreet(addressRequest.getStreet());
        address.setCity(addressRequest.getCity());
        address.setImage(imageUrl);

        Address saved = addressRepository.save(address);

        AddressResponse addressResponse = new AddressResponse();
        addressResponse.setId(saved.getId());
        addressResponse.setStreet(saved.getStreet());
        addressResponse.setCity(saved.getCity());
        addressResponse.setImage(saved.getImage());

        return addressResponse;
    }

    @Override
    public List<AddressResponse> findAll() {
        List<Address> addresses = addressRepository.findAll();
        List<AddressResponse> responses = new ArrayList<>();

        for(Address address : addresses){
            AddressResponse addressResponse = new AddressResponse();
            addressResponse.setId(address.getId());
            addressResponse.setStreet(address.getStreet());
            addressResponse.setCity(address.getCity());
            addressResponse.setImage(address.getImage());
            responses.add(addressResponse);
        }

        return responses;
    }

    @Override
    public AddressResponse findById(Long id) {

        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with id: " + id));

        AddressResponse response = new AddressResponse();
        response.setId(address.getId());
        response.setStreet(address.getStreet());
        response.setCity(address.getCity());
        response.setImage(address.getImage());

        return response;
    }

    @Override
    public AddressResponse update(Long id, AddressRequest addressRequest, MultipartFile file) {

        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with id: " + id));

        address.setStreet(addressRequest.getStreet());
        address.setCity(addressRequest.getCity());

        if(file != null && !file.isEmpty()){
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path path = Paths.get("upload");
            String imageUrl = "http://localhost:8080/upload/" + fileName;

            try{

                if(!Files.exists(path)){
                    Files.createDirectories(path);
                }

                Files.copy(file.getInputStream(), path.resolve(fileName));

            }catch (IOException e){
                throw new ResourceNotFoundException("File to upload file" + e);
            }

            address.setImage(imageUrl);
        }

        Address updated = addressRepository.save(address);

        AddressResponse addressResponse = new AddressResponse();
        addressResponse.setId(updated.getId());
        addressResponse.setStreet(updated.getStreet());
        addressResponse.setCity(updated.getCity());
        addressResponse.setImage(updated.getImage());

        return addressResponse;
    }

    @Override
    public void delete(Long id) {

        Address address = addressRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Address with "+ id +" not found"));

        addressRepository.delete(address);
    }
}
