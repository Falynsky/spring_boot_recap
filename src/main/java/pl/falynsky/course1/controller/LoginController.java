package pl.falynsky.course1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pl.falynsky.course1.config.LoginCredentials;

@Controller
public class LoginController {

    @PostMapping("/login")
    public void login(@RequestBody LoginCredentials loginCredentials) {
    }
}
