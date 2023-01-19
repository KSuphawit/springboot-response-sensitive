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

    /**
     * @return
     * {
     *     "data": {
     *         "name": "John Doe",
     *         "email": "johndoe@linecorp.com",
     *         "nationalId": "nationalId",
     *         "phone": {
     *             "network": "AIS",
     *             "phoneNumber": "0812345678"
     *         },
     *         "financial": {
     *             "bankAccount": "122345",
     *             "salary": 100
     *         },
     *         "addresses": [
     *             {
     *                 "province": "Bkk",
     *                 "postCode": "11111",
     *                 "address": "address1"
     *             },
     *             {
     *                 "province": "Cnx",
     *                 "postCode": "22222",
     *                 "address": "address2"
     *             }
     *         ]
     *     }
     * }
     **/
    @GetMapping("/include-sensitive")
    public BaseResponse<User> getUserIncludeSensitive() {
        return new BaseResponse<>(User.mockUser());
    }

    /**
     * @return
     *{
     *     "data": {
     *         "name": "John Doe",
     *         "email": "johndoe@linecorp.com",
     *         "phone": {
     *             "network": "AIS"
     *         },
     *         "addresses": [
     *             {
     *                 "province": "Bkk",
     *                 "postCode": "11111"
     *             },
     *             {
     *                 "province": "Cnx",
     *                 "postCode": "22222"
     *             }
     *         ]
     *     }
     * }
     **/
    @GetMapping("/exclude-sensitive")
    @SensitiveAPI
    public BaseResponse<User> getUserExcludeSensitive() {
        return new BaseResponse<>(User.mockUser());
    }

    /**
     * @return
     * {
     *     "name": "John Doe",
     *     "email": "johndoe@linecorp.com",
     *     "nationalId": "nationalId",
     *     "phone": {
     *         "network": "AIS",
     *         "phoneNumber": "0812345678"
     *     },
     *     "financial": {
     *         "bankAccount": "122345",
     *         "salary": 100
     *     },
     *     "addresses": [
     *         {
     *             "province": "Bkk",
     *             "postCode": "11111",
     *             "address": "address1"
     *         },
     *         {
     *             "province": "Cnx",
     *             "postCode": "22222",
     *             "address": "address2"
     *         }
     *     ]
     * }
     **/
    @GetMapping("/no-base/include-sensitive")
    public User getUserIncludeSensitiveWithoutBaseResponse() {
        return User.mockUser();
    }

    /**
     * @return
     * {
     *     "name": "John Doe",
     *     "email": "johndoe@linecorp.com",
     *     "phone": {
     *         "network": "AIS"
     *     },
     *     "addresses": [
     *         {
     *             "province": "Bkk",
     *             "postCode": "11111"
     *         },
     *         {
     *             "province": "Cnx",
     *             "postCode": "22222"
     *         }
     *     ]
     * }
     **/
    @GetMapping("/no-base/exclude-sensitive")
    @SensitiveAPI
    public User getUserExcludeSensitiveWithoutBaseResponse() {
        return User.mockUser();
    }

    /**
     * @return
     * {
     *     "success": true
     * }
     **/
    @GetMapping("/void")
    public void voidResponse() {
    }
}