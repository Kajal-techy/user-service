package com.userservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document
public class Address {

    private int houseNumber;

    private int streetNo;

    private String streetName;

    private String city;

    private String state;

    private String country;

    private int pincode;
}