package pl.falynsky.course1.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.falynsky.course1.repository.SimpleRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class SimpleService {

    private final SimpleRepository simpleRepository;

    public String getSomethingFormService(){
        log.info("Service: getSomethingFormService");
        return getSomething();
    }

    public String getSomethingElseFromService(){
        log.info("Service: getSomethingElseFromService");
        return simpleRepository.getSomethingElseFromRepository();
    }

    private String getSomething(){
        log.info("Service: getSomething");
        return "something";
    }
}
