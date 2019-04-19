package com.znv.community.gate.feign;

import com.alibaba.fastjson.JSONObject;
import com.znv.community.gate.fallback.TokenServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "auth",fallback = TokenServiceFallback.class)
public interface ITokenService {

    @GetMapping("/verifyAuthority/verifyToken")
    public JSONObject verifyToken(@RequestParam(value = "token", required = true) String token);
}
