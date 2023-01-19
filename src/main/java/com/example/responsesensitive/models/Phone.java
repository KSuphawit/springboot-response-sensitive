package com.example.responsesensitive.models;

import com.example.responsesensitive.annotations.Sensitive;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Phone {

    @JsonProperty("network")
    private String network;

    @JsonProperty("phoneNumber")
    @Sensitive
    private String phoneNumber;

    public Phone(String network, String phoneNumber) {
        this.network = network;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Phone{" +
                "network='" + network + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
