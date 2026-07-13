package com.example.etec_part2.service;

import com.example.etec_part2.dto.request.AddressRequest;
import com.example.etec_part2.dto.response.AddressResponse;
import org.springframework.web.multipart.MultipartFile;

import java.nio.channels.MulticastChannel;
import java.util.List;

public interface AddressService {

     AddressResponse create(AddressRequest addressRequest, MultipartFile file);

     List<AddressResponse> findAll();

     AddressResponse findById(Long id);

     AddressResponse update(Long id, AddressRequest addressRequest, MultipartFile file);

     void delete(Long id);

}
