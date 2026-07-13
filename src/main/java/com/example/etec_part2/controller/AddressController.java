package com.example.etec_part2.controller;

import com.example.etec_part2.dto.request.AddressRequest;
import com.example.etec_part2.dto.response.AddressResponse;
import com.example.etec_part2.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public AddressResponse create(
            @ModelAttribute AddressRequest addressRequest,
            @RequestParam("file") MultipartFile file
    ) {
        return addressService.create(addressRequest, file);
    }

    @GetMapping("/{id}")
    public AddressResponse getById(@PathVariable Long id) {
        return addressService.findById(id);
    }

    @GetMapping
    public List<AddressResponse> getAll() {
        return addressService.findAll();
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public AddressResponse update(
            @PathVariable Long id,
            @ModelAttribute AddressRequest addressRequest,
            @RequestParam(value = "file", required = false) MultipartFile file
    ) {
        return addressService.update(id, addressRequest, file);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        addressService.delete(id);
        return "Address deleted successfully";
    }
}