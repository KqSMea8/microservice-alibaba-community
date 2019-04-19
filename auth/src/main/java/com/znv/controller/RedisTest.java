package com.znv.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@RestController
public class RedisTest {

    StringRedisTemplate template;

    ValueOperations<String, String> redisOperations;

    RedisTest (StringRedisTemplate template) {
        this.template = template;
        redisOperations = template.opsForValue();
    }

    @GetMapping("/getRedisKeys")
    @ResponseBody
    public Set<String> getRedisKeys(
            @RequestParam(value = "pattern") String pattern
    ) {
        return template.keys(pattern);
    }

    @GetMapping("/setRedisExpire")
    @ResponseBody
    public String setRedisExpire(
            @RequestParam(value = "key") String key,
            @RequestParam(value = "value") String value,
            @RequestParam(value = "timeoutseconds") int timeoutseconds
    ) {
        redisOperations.set(key, value, timeoutseconds, TimeUnit.SECONDS);
        return key + ":" + value;
    }

    @GetMapping("/setRedis")
    @ResponseBody
    public String setRedis(
            @RequestParam(value = "key") String key,
            @RequestParam(value = "value") String value
    ) {
        redisOperations.set(key, value);
        return key + ":" + value;
    }

    @GetMapping("/getRedis")
    @ResponseBody
    public String getRedis(
            @RequestParam(value = "key") String key
    ) {
        return redisOperations.get(key);
    }

    @GetMapping("/removeKey")
    @ResponseBody
    public void removeKey(
            @RequestParam(value = "key") String key
    ) {
        template.delete(key);
    }
}
