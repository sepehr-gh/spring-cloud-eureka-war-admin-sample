package com.example.service1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    private final Service1Api service1Api;

    @Autowired
    public TestController(Service1Api service1Api) {
        this.service1Api = service1Api;
    }

    @GetMapping("/v1/test")
    public String test(){
        return "Feign output: " + service1Api.feign();
    }

    @GetMapping("/v1/feign")
    public String feign(){
        return "Feign output";
    }
}
