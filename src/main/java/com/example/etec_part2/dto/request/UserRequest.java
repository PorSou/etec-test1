package com.example.etec_part2.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {

    private String name;

    private String email;

    private AddressRequest addressRequest;
}
