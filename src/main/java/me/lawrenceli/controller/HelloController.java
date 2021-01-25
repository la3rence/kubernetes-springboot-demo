package me.lawrenceli.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class HelloController {

    Logger logger = LoggerFactory.getLogger(HelloController.class);

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${version}")
    private String version;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("/hello")
    public String hello() {
        logger.info("hello method...");
        // 尝试发出 HTTP 请求，测试 Pod 是否可以访问外网：
        return restTemplate.getForObject("https://lawrenceli.me/", String.class);
    }

    @GetMapping("/v")
    public String v() {
        logger.info("version: [{}]", version);
        return version;
    }

    @GetMapping("/redis")
    public String redis() {
        long now = System.currentTimeMillis();
        logger.info("now: [{}], redis current host is [{}]", now, redisHost);
        stringRedisTemplate.opsForValue().set("now", String.valueOf(now));
        return stringRedisTemplate.opsForValue().get("now");
    }
}
