package com.example.etec_part2.controller;

import com.example.etec_part2.dto.request.AddressRequest;
import com.example.etec_part2.dto.response.AddressResponse;
import com.example.etec_part2.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping
    public AddressResponse create(@RequestBody AddressRequest addressRequest){
        return addressService.create(addressRequest);
    }

    @GetMapping
    public List<AddressResponse> findAll(){
        return addressService.findAll();
    }

    @GetMapping("/{id}")
    public AddressResponse findById(@PathVariable Long id){
        return addressService.findById(id);
    }

    @PutMapping("/{id}")
    public AddressResponse update(@PathVariable Long id, @RequestBody AddressRequest addressRequest){
        return addressService.update(id,addressRequest);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        addressService.delete(id);
    }

}
