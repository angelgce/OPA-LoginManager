package com.pirate.arena.app.services;

import com.pirate.arena.app.request.RequestLogin;

public interface IServiceLogin {

    String generateToken(RequestLogin requestLogin);
    void isPasswordCorrect(RequestLogin requestLogin, String password);



}
