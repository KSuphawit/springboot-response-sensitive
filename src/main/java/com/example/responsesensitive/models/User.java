package com.example.responsesensitive.models;

import com.example.responsesensitive.annotations.Sensitive;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
    @JsonProperty("name")
    private String name;

    @JsonProperty("email")
    private String email;

    @JsonProperty("nationalId")
    @Sensitive
    private String nationalId;

    @JsonProperty("phone")
    private Phone phone;

    @JsonProperty("financial")
    private Financial financial;

    @JsonProperty("addresses")
    private List<Address> addresses;

    public User(String name, String email, String nationalId, Phone phone, Financial financial, List<Address> addresses) {
        this.name = name;
        this.email = email;
        this.nationalId = nationalId;
        this.phone = phone;
        this.financial = financial;
        this.addresses = addresses;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", nationalId='" + nationalId + '\'' +
                ", phone=" + phone +
                ", financial=" + financial +
                ", addresses=" + addresses +
                '}';
    }

    public static User mockUser() {
        Address address1 = new Address("Bkk", "11111", "address1");
        Address address2 = new Address("Cnx", "22222", "address2");
        Phone phone = new Phone("AIS", "0812345678");
        Financial financial = new Financial("122345", 100);
        return new User("John Doe", "johndoe@linecorp.com", "nationalId", phone, financial, Arrays.asList(address1, address2));
    }
}
