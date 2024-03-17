package pl.falynsky.course1.service;

import org.springframework.stereotype.Service;

@Service
public class HelloService {

    public String hello() {
        return "Hello, World!";
    }
}
