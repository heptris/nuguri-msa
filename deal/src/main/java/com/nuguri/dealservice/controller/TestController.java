package com.nuguri.dealservice.controller;

import com.nuguri.dealservice.config.TestConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/app/deal")
public class TestController {
    private final TestConfig testConfig;

    @GetMapping("/config")
    public ResponseEntity<String> config(){
        System.out.println(testConfig);
        System.out.println("test");
        return ResponseEntity.ok(testConfig.toString());
    }
}
