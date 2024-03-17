package pl.falynsky.course1.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final ObjectMapper objectMapper;
    private final RestAuthenticationSuccessHandler successHandler;

    private final RestAuthenticationFailureHandler failureHandler;

    public SecurityConfig(ObjectMapper objectMapper, RestAuthenticationSuccessHandler successHandler, RestAuthenticationFailureHandler failureHandler) {
        this.objectMapper = objectMapper;
        this.successHandler = successHandler;
        this.failureHandler = failureHandler;
    }

    @Bean
    public UserDetailsService users() {
        UserDetails user1 = User.withUsername("test")
                .password(passwordEncoder().encode("test"))
                .roles("USER")
                .build();
        UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder().encode("adminPass"))
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user1, admin);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/swagger-ui/index.html").permitAll()
                                .requestMatchers("/webjars/**").permitAll()
                                .requestMatchers("/swagger-resources/**").permitAll()
                                .anyRequest().authenticated())
                .addFilter(authenticationFilter(http))
                .exceptionHandling(e -> e.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .build();

    }

    public JsonObjectAuthenticationFilter authenticationFilter(HttpSecurity http) {
        JsonObjectAuthenticationFilter filter = new JsonObjectAuthenticationFilter(objectMapper);
        filter.setAuthenticationSuccessHandler(successHandler);
        filter.setAuthenticationFailureHandler(failureHandler);
        filter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        return filter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
