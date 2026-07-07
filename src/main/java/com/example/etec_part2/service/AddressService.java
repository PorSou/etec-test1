package com.example.etec_part2.service;

import com.example.etec_part2.dto.request.AddressRequest;
import com.example.etec_part2.dto.response.AddressResponse;

import java.util.List;

public interface AddressService {

     AddressResponse create(AddressRequest addressRequest);

     List<AddressResponse> findAll();

     AddressResponse findById(Long id);

     AddressResponse update(Long id, AddressRequest addressRequest);

     void delete(Long id);

}
