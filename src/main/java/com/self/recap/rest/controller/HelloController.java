package com.self.recap.rest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // @RestController + @ResponseBody (for each method)
@RequestMapping("/api")
public class HelloController {

    @GetMapping("/sayHello")
    public String sayHello() {
        return "Hello world";
    }

}
