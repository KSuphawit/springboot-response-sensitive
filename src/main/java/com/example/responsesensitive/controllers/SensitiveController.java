package com.example.responsesensitive.controllers;


import com.example.responsesensitive.annotations.SensitiveAPI;
import com.example.responsesensitive.models.Address;
import com.example.responsesensitive.models.BaseResponse;
import com.example.responsesensitive.models.Financial;
import com.example.responsesensitive.models.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/sensitive-infos")
@SensitiveAPI
public class SensitiveController {

    /**
     * @return
     * {
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
    @GetMapping("/user")
    public BaseResponse<User> getUser() {
        return new BaseResponse<>(User.mockUser());
    }


    /**
     * @return
     * {
     *     "data": [
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
    @GetMapping("/address")
    public BaseResponse<List<Address>> getAddress() {
        Address address1 = new Address("Bkk", "11111", "address1");
        Address address2 = new Address("Cnx", "22222", "address2");
        return new BaseResponse<>(Arrays.asList(address1, address2));
    }

    /**
     * @return
     * {
     *     "success": true
     * }
     **/
    @GetMapping("/financial")
    public BaseResponse<Financial> getFinancial() {
        Financial financial = new Financial("122345", 100);
        return new BaseResponse<>(true, financial);
    }
}
