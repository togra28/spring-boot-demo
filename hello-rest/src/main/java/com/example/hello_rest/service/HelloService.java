package com.example.hello_rest.service;

import org.springframework.stereotype.Service;

@Service
public class HelloService {

    public String sayHello() {
        return "Hello Spring REST!";
    }
}
