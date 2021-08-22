package me.lawrenceli.controller;

import me.lawrenceli.KubernetesSpringbootDemoApplication;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {KubernetesSpringbootDemoApplication.class})
class HelloControllerTest {

    Logger log = LoggerFactory.getLogger(HelloControllerTest.class);

    private MockMvc mockMvc;

    @Autowired
    HelloController helloController;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @BeforeEach
    void setUp() {
        log.debug("set up for mock mvc ...");
        mockMvc = MockMvcBuilders.standaloneSetup(helloController).build();
    }

    @AfterEach
    void tearDown() {
        log.debug("tear down...");
    }

    @Test
    @DisplayName("should return the specific string and 200 response")
    void hello() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/hello");
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Hello, Spring!"));
    }


    @Test
    void config() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/config");
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.content().string("v0"));
    }

    /**
     * if redis server isn't provided, use @Disabled("not test: redis server required")
     */
    @Test
    @DisplayName("should connect to redis and get 200 response")
    void redis() throws Exception {
        log.debug("test redis server");
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/redis");
        ResultActions resultActions = mockMvc.perform(requestBuilder);
        String now = stringRedisTemplate.opsForValue().get("now");
        if (null != now) {
            resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().string(now));
        } else {
            Assertions.fail();
        }
    }
}