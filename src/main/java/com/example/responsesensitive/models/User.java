package com.example.responsesensitive.models;

import com.example.responsesensitive.annotations.Sensitive;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
    @JsonProperty("name")
    private String name;
    @JsonProperty("email")
    private String email;
    @JsonProperty("nationalId")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Sensitive
    private String nationalId;
    @JsonProperty("phoneNumber")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Sensitive
    private String phoneNumber;

    public User() {
    }

    public User(String name, String email, String nationalId, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.nationalId = nationalId;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", nationalId='" + nationalId + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
