package ru.otus.nyuriv.socialnet.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.nyuriv.socialnet.model.LoginRequest;
import ru.otus.nyuriv.socialnet.model.TokenResponse;
import ru.otus.nyuriv.socialnet.service.LoginService;

@RestController
@RequestMapping("login")
public class LoginApi {
    private final LoginService loginService;

    @Autowired
    public LoginApi(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping
    public TokenResponse login(@RequestBody LoginRequest request) {
        return new TokenResponse();
    }
}
