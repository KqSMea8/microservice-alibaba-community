package com.znv.community.gate.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class TestController {

    @Value("${auth.onoff}")
    boolean isOnoff;

    @GetMapping("/testNacosConfig")
    String getNacosConfig() {
        return String.valueOf(isOnoff);
    }
}
