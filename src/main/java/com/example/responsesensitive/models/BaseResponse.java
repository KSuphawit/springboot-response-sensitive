package com.example.responsesensitive.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;


public class BaseResponse<T> implements Serializable {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("success")
    private Boolean success;

    @JsonProperty("data")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public BaseResponse() {
        super();
    }

    public BaseResponse(T data) {
        this.data = data;
    }

    public BaseResponse(Boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public T getData() {
        return data;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "success=" + success +
                ", data=" + data +
                '}';
    }
}
