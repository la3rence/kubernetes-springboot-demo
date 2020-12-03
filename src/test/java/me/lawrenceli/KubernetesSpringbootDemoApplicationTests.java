package me.lawrenceli;

import me.lawrenceli.controller.HelloController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class KubernetesSpringbootDemoApplicationTests {

    @Autowired
    private HelloController helloController;

    @Test
    void contextLoads() {
        String hello = helloController.hello();
        assert "Hello, Spring!".equals(hello);
    }

}
