package com.userservice.model;

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
    private String id;

    @NonNull
    private String firstName;

    @NonNull
    private String lastName;

    @NonNull
    private String userName;

    @NonNull
    private String password;

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
