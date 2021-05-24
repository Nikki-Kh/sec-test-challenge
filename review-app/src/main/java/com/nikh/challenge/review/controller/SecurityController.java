package com.nikh.challenge.review.controller;


import com.nikh.challenge.review.service.AuthService;

public abstract class SecurityController {

    protected final AuthService authService;

    protected SecurityController(AuthService authService) {
        this.authService = authService;
    }

    protected void authorize(String token) {
        authService.authorize(token);
    }
}
