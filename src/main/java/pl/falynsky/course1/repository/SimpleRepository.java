package pl.falynsky.course1.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class SimpleRepository {

    public String getSomethingElseFromRepository(){
        log.info("Getting something else from repository");
        return "getSomethingElse";
    }
}
