package com.example.service1;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "service-1", path = "/service1")
public interface Service1Api {
    @GetMapping("/v1/feign")
    String feign();
}
