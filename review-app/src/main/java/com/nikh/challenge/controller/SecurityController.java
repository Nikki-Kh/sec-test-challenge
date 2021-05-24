package com.nikh.challenge.controller;


import com.nikh.challenge.service.AuthService;

public abstract class SecurityController {

    protected final AuthService authService;

    protected SecurityController(AuthService authService) {
        this.authService = authService;
    }

    protected boolean authorize(String token) {
        return authService.authorize(token);
    }
}
