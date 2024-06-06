package pl.falynsky.course1.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.falynsky.course1.AdditionalAuthentication;
import pl.falynsky.course1.aop.dto.AdditionalCredentialsDto;
import pl.falynsky.course1.service.SimpleService;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class SimpleController {

    private final SimpleService simpleService;
    private final String redisHost;
    private final int redisPort;

    SimpleController(
            SimpleService simpleService,
            @Value("${spring.data.redis.host}") String redisHost,
            @Value("${spring.data.redis.port}") int redisPort
    ) {
        this.simpleService = simpleService;
        this.redisHost = redisHost;
        this.redisPort = redisPort;
    }

    @GetMapping("/something")
    public String getSomething() {

        JedisPool pool = new JedisPool(redisHost, redisPort);
        Map<String, String> x = new HashMap<>();
        try (Jedis jedis = pool.getResource()) {
            // Store & Retrieve a simple string
            jedis.set("foo", "bar");
            System.out.println(jedis.get("foo")); // prints bar

            // Store & Retrieve a HashMap
            Map<String, String> hash = new HashMap<>();
            ;
            hash.put("name", "John");
            hash.put("surname", "Smith");
            hash.put("company", "Redis");
            hash.put("age", "29");
            jedis.hset("user-session:123", hash);
            x = jedis.hgetAll("user-session:123");
            System.out.println(x);
        }
        Object test = "dsad";
        log.info("Controller: getSomething");

        if (test instanceof String stringTest) {
            stringTest.toLowerCase();
        } else if (test instanceof Boolean booleanTest) {
            booleanTest.compareTo(true);
        }

        return x.toString();
//        return simpleService.getSomethingFormService();
//        try {
//            throw new CustomException();
//        } catch (CustomException exc) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Foo Not Found", exc);
//        }
    }

    @PostMapping("/somethingElse")
    @AdditionalAuthentication
    public String getSomethingElse(@RequestBody AdditionalCredentialsDto additionalCredentialsDto) {
        log.info("Controller: getSomethingElse");
        return simpleService.getSomethingElseFromService();
    }
}
