package com.nikh.challenge.unit.service;

import com.nikh.challenge.review.dao.UserMapper;
import com.nikh.challenge.review.dto.UserInfoBean;
import com.nikh.challenge.review.error.exception.AuthException;
import com.nikh.challenge.review.service.AuthService;
import com.nikh.challenge.review.service.AuthServiceImpl;
import org.junit.Before;
import org.junit.Test;

import static  org.junit.jupiter.api.Assertions.*;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.*;

import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.eq;

@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:test.properties")
@SpringBootTest(classes = {UserMapper.class, AuthServiceImpl.class})
public class AuthServiceTest {

    @MockBean
    UserMapper userMapper;

    @Autowired
    private AuthService authService;

    @Value("${auth.jwt.token.correct}")
    String correctToken;

    @Value("${auth.jwt.token.incorrect}")
    String incorrectToken;

    @Before
    public void init() {
        UserInfoBean user = new UserInfoBean();
        user.setUserId(1);
        user.setUsername("admin");
        user.setPassword("admin");
        //authService = new AuthServiceImpl(userMapper);
        when(userMapper.getUser("admin", "admin")).thenReturn(user);
        when(userMapper.getUser(any(), not(eq("admin")))).thenReturn(null);
    }

    @Test
    public void testAuthSuccess() {
        assertDoesNotThrow(() -> authService.authorize(correctToken));
    }

    @Test
    public void testAuthFailed() {
        assertThrows(AuthException.class, () -> authService.authorize(incorrectToken));
    }
}
