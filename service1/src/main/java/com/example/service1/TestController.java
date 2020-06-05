package com.example.service1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/v1/test")
    public String test(){
        return "OK - 2";
    }
}
