package com.pirate.arena.app.services;

import com.pirate.arena.app.request.RequestLogin;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.SignatureAlgorithm;

import java.security.Key;
import java.util.Date;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ServiceToken implements IServiceToken {
    private final Environment environment;
    private final String SECRET_KEY = "413F4428472B4B6250655367566B5970337336763979244226452948404D6351";
    private final String EXP_TIME = "300000";

    @Override
    public Key getSignInKey() {
        Optional<String> key = Optional.ofNullable(environment.getProperty("SECRET_KEY"));
        if (key.isEmpty())
            key = Optional.ofNullable(SECRET_KEY);
        byte[] keyByte = Decoders.BASE64.decode(key.get());
        return Keys.hmacShaKeyFor(keyByte);
    }

    @Override
    public String generateToken(RequestLogin requestLogin) {
        Optional<String> key = Optional.ofNullable(environment.getProperty("EXP_TIME"));
        if (key.isEmpty())
            key = Optional.ofNullable(EXP_TIME);
        return Jwts.builder()
                .setSubject(requestLogin.email())
                .setExpiration(new Date(System.currentTimeMillis() + Long.valueOf(key.get())))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

}
