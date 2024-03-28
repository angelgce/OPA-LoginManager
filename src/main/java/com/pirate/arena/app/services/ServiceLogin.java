package com.pirate.arena.app.services;


import com.amazonaws.services.dynamodbv2.document.Item;
import com.pirate.arena.app.dynamoDB.ServiceQueries;
import com.pirate.arena.app.exceptions.BadRequestException;
import com.pirate.arena.app.request.RequestLogin;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class ServiceLogin extends ServiceValidateRequest implements IServiceLogin {

    private final ServiceQueries serviceQueries;
    private final ServiceToken serviceToken;

    @Override
    public String generateToken(RequestLogin requestLogin) {
        validateInputs(Optional.ofNullable(requestLogin));
        Optional<Item> user = serviceQueries.getUserByEmail(requestLogin.email());
        if (user.isEmpty())
            throw new BadRequestException
                    ("[Error creating token] User not found. Request: ".concat(requestLogin.toString()));
        isPasswordCorrect(requestLogin, user.get().getString("password"));
        log.info("[Token created] Request: ".concat(requestLogin.email()));
        return serviceToken.generateToken(requestLogin);
    }

    @Override
    public void isPasswordCorrect(RequestLogin requestLogin, String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(requestLogin.password(), password))
            throw new BadRequestException("[Error creating token] Incorrect password. Request: ".concat(requestLogin.email()));

    }


}
