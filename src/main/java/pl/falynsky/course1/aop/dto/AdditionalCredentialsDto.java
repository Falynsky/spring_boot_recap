package pl.falynsky.course1.aop.dto;

import pl.falynsky.course1.aop.AdditionalCredentials;

public class AdditionalCredentialsDto implements AdditionalCredentials {

    private String username;
    private String password;

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }
}
