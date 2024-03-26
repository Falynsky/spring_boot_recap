package pl.falynsky.course1.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginCredentials {

    private String username;
    private String password;
}
