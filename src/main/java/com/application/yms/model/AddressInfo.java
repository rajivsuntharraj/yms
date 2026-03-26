package com.application.yms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressInfo {
    private String postal_code;
    private String address1;
    private String country;
    private String state;
    private String city;
}
