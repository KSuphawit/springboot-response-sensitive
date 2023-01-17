package com.example.responsesensitive.controllers;


import com.example.responsesensitive.annotations.SensitiveAPI;
import com.example.responsesensitive.models.BaseResponse;
import com.example.responsesensitive.models.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping("/include-sensitive")
    public BaseResponse<User> getUserIncludeSensitive() {
        User user = new User("John Doe", "johndoe@linecorp.com", "nationalId", "phoneNumber");
        return new BaseResponse<>(user);
    }

    @GetMapping("/exclude-sensitive")
    @SensitiveAPI
    public BaseResponse<User> getUserExcludeSensitive() {
        User user = new User("John Doe", "johndoe@linecorp.com", "nationalId", "phoneNumber");
        return new BaseResponse<>(user);
    }

    @GetMapping("/no-base/include-sensitive")
    public User getUserIncludeSensitiveWithoutBaseResponse() {
        return new User("John Doe", "johndoe@linecorp.com", "nationalId", "phoneNumber");
    }

    @GetMapping("/no-base/exclude-sensitive")
    @SensitiveAPI
    public User getUserExcludeSensitiveWithoutBaseResponse() {
        return new User("John Doe", "johndoe@linecorp.com", "nationalId", "phoneNumber");
    }

    @GetMapping("/void")
    public void voidResponse() {
    }
}
