package me.lawrenceli.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    Logger logger = LoggerFactory.getLogger(HelloController.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("/hello")
    public String hello() {
        logger.info("hello method...");
        return "Hello, Spring!";
    }

    @GetMapping("/redis")
    public String redis() {
        long now = System.currentTimeMillis();
        logger.info("redis method: " + now);
        stringRedisTemplate.opsForValue().set("now", String.valueOf(now));
        return stringRedisTemplate.opsForValue().get("now");
    }
}
