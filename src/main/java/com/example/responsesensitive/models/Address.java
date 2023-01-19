package com.example.responsesensitive.models;

import com.example.responsesensitive.annotations.Sensitive;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Address {

    @JsonProperty("province")
    private String province;

    @JsonProperty("postCode")
    private String postCode;

    @JsonProperty("address")
    @Sensitive
    private String address;

    public Address(String province, String postCode, String address) {
        this.province = province;
        this.postCode = postCode;
        this.address = address;
    }

    @Override
    public String toString() {
        return "Address{" +
                "province='" + province + '\'' +
                ", postCode='" + postCode + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
