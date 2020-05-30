package com.userservice.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Address {

    @ApiModelProperty(notes = "User houseNumber ")
    private int houseNumber;

    @ApiModelProperty(notes = "User streetNo ")
    private int streetNo;

    @ApiModelProperty(notes = "User streetName ")
    private String streetName;

    @ApiModelProperty(notes = "User city ")
    private String city;

    @ApiModelProperty(notes = "User state ")
    private String state;

    @ApiModelProperty(notes = "User country ")
    private String country;

    @ApiModelProperty(notes = "User pincode ")
    private int pincode;
}