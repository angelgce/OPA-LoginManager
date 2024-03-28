package com.pirate.arena.app.services;

import com.pirate.arena.app.request.RequestLogin;

import java.security.Key;

public interface IServiceToken {
    Key getSignInKey();

    String generateToken(RequestLogin requestLogin);

}
