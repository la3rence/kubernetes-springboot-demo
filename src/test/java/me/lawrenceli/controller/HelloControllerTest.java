package me.lawrenceli.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class HelloControllerTest {

    Logger log = LoggerFactory.getLogger(HelloControllerTest.class);

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        log.debug("set up for mock mvc...");
        mockMvc = MockMvcBuilders.standaloneSetup(new HelloController()).build();
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
    @Disabled("not test: redis server required :p")
    void redis() {
        log.debug("test redis server io");
    }
}