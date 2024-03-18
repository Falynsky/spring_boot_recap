package pl.falynsky.course1.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.falynsky.course1.AdditionalAuthentication;
import pl.falynsky.course1.aop.dto.AdditionalCredentialsDto;
import pl.falynsky.course1.service.SimpleService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class SimpleController {

    private final SimpleService simpleService;

    @GetMapping("/something")
    public String getSomething(){
        log.info("Controller: getSomething");
        return simpleService.getSomethingFormService();
    }

    @PostMapping("/somethingElse")
    @AdditionalAuthentication
    public String getSomethingElse(@RequestBody AdditionalCredentialsDto additionalCredentialsDto) {
        log.info("Controller: getSomethingElse");
        return simpleService.getSomethingElseFromService();
    }
}