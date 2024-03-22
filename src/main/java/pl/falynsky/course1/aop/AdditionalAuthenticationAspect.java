package pl.falynsky.course1.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import pl.falynsky.course1.AdditionalAuthentication;

@Aspect
@Component
@Slf4j
public class AdditionalAuthenticationAspect {

    @Before("@annotation(authentication) && args(credentials,..)")
    public void before(
            AdditionalAuthentication authentication,
            AdditionalCredentials credentials
    ){
        log.info("AdditionalAuthenticationAspect");
        String username = credentials.getUsername();
        log.info("  username:" + username);
        log.info("  password:" + credentials.getPassword());

        if(username.equals("test")){
            throw new IllegalArgumentException("We say NO for test user");
        }
    }
}
