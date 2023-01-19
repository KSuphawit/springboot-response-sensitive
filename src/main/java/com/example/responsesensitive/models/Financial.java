package com.example.responsesensitive.models;

import com.example.responsesensitive.annotations.Sensitive;
import com.fasterxml.jackson.annotation.JsonProperty;

@Sensitive
public class Financial {
    @JsonProperty("bankAccount")
    private String bankAccount;

    @JsonProperty("salary")
    private int salary;

    public Financial(String bankAccount, int salary) {
        this.bankAccount = bankAccount;
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Financial{" +
                "bankAccount='" + bankAccount + '\'' +
                ", salary=" + salary +
                '}';
    }
}
