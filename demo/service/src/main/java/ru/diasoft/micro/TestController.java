package ru.diasoft.micro;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/test-get")
    public String testGet(){
        return "hello from test controller";
    }
}
