package mk.ukim.finki.restcoutriesapp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class TestSecurityConfig {
    @Bean
    public SecurityFilterChain testSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf((csrf) ->
                        csrf
                        .ignoringRequestMatchers("/countires"))
                        .authorizeRequests()
                        .anyRequest().permitAll();

        return http.build();
    }
}
