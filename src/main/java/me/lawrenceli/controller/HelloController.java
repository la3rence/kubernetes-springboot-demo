package me.lawrenceli.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    Logger logger = LoggerFactory.getLogger(HelloController.class);

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${my.config}")
    private String myConfig;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("/hello")
    public String hello() {
        logger.info("hello method...");
        return "Hello, Spring!";
    }

    @GetMapping("/config")
    public String config() {
        logger.info("get my config: {}", myConfig);
        return myConfig;
    }

    @GetMapping("/redis")
    public String redis() {
        long now = System.currentTimeMillis();
        logger.info("now: [{}], redis current host is [{}]", now, redisHost);
        stringRedisTemplate.opsForValue().set("now", String.valueOf(now));
        return stringRedisTemplate.opsForValue().get("now");
    }
}
