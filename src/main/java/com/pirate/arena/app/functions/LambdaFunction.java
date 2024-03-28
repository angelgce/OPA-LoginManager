package com.pirate.arena.app.functions;

import com.pirate.arena.app.request.RequestLogin;
import com.pirate.arena.app.services.ServiceLogin;
import com.pirate.arena.app.services.ServiceToken;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;

@Configuration
@RequiredArgsConstructor
public class LambdaFunction {

    private final ServiceLogin serviceLogin;

    @Bean
    public Function<RequestLogin, ResponseEntity<Map<String, String>>> generateToken() {
        return value -> ResponseEntity.ok()
                .body(Collections.singletonMap("token", serviceLogin.generateToken(value)));
    }

}
