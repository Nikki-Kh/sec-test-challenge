package com.nikh.challenge.review.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nikh.challenge.review.dao.UserMapper;
import com.nikh.challenge.review.error.exception.AuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import static java.nio.charset.StandardCharsets.UTF_8;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService{

    @Value("${auth.jwt.secret}")
    private String secret;

    private final UserMapper userMapper;

    @Override
    public void authorize(String token) {
        if (token == null || !verifyUser(getPayload(token))) {
            throw new AuthException();
        }
    }

    private JsonObject getPayload(String token) {
        final Algorithm algorithm = Algorithm.
                HMAC256(secret);

        final JWTVerifier verifier = JWT.require(algorithm)
                .build();

        final DecodedJWT jwt = verifier.verify(token);
        String payloadStr = new String(Base64Utils.decode(jwt.getPayload().getBytes(UTF_8)));
        return JsonParser.parseString(payloadStr).getAsJsonObject();
    }

    private boolean verifyUser(JsonObject userInfo) {
        if (userInfo != null && userInfo.has("username") && userInfo.has("password")) {
            String username = userInfo.get("username").getAsString();
            String pass = userInfo.get("password").getAsString();
            return userMapper.getUser(username, pass) != null;
        }
        return false;
    }
}
