package com.nikh.challenge.review.controller;


import com.nikh.challenge.review.service.AuthService;

public abstract class SecurityController {

    protected final AuthService authService;

    protected SecurityController(AuthService authService) {
        this.authService = authService;
    }

    protected boolean authorize(String token) {
        return authService.authorize(token);
    }
}
