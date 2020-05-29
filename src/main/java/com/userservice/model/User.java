package com.userservice.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class User implements Cloneable {

    @Id
    @ApiModelProperty(notes = "The database generated User ID")
    private String id;

    @NonNull
    @ApiModelProperty(notes = "User FirstName ")
    private String firstName;

    @NonNull
    @ApiModelProperty(notes = "User lastName ")
    private String lastName;

    @NonNull
    @ApiModelProperty(notes = "User userName ")
    private String userName;

    @NonNull
    @ApiModelProperty(notes = "User password ")
    private String password;

    @ApiModelProperty(notes = "User address ")
    private Address address;

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException ignored) {
        }
        return null;
    }
}
