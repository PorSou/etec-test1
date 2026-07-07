package com.example.etec_part2.service.impl;

import com.example.etec_part2.dto.request.AddressRequest;
import com.example.etec_part2.dto.response.AddressResponse;
import com.example.etec_part2.entity.Address;
import com.example.etec_part2.exception.ResourceNotFoundException;
import com.example.etec_part2.repository.AddressRepository;
import com.example.etec_part2.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    private AddressResponse map(Address a){
        AddressResponse addressResponse = new AddressResponse();
        addressResponse.setId(a.getId());
        addressResponse.setStreet(a.getStreet());
        addressResponse.setCity(a.getCity());
        return addressResponse;
    }

    @Override
    public AddressResponse create(AddressRequest addressRequest) {

        Address address = new Address();
        address.setStreet(addressRequest.getStreet());
        address.setCity(addressRequest.getCity());

        return map(addressRepository.save(address));
    }

    @Override
    public List<AddressResponse> findAll() {
        return addressRepository.findAll()
                .stream()
                .map(this::map)
                .toList();
    }

    @Override
    public AddressResponse findById(Long id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Address with "+ id + " not found"));
        return map(address);
    }

    @Override
    public AddressResponse update(Long id, AddressRequest addressRequest) {

        Address address = addressRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Address with "+ id +" not found"));

        address.setStreet(addressRequest.getStreet());
        address.setCity(addressRequest.getCity());

        return map(addressRepository.save(address));
    }

    @Override
    public void delete(Long id) {

        Address address = addressRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Address with "+ id +" not found"));

        addressRepository.delete(address);
    }
}
