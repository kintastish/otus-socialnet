package ru.otus.nyuriv.socialnet.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.nyuriv.socialnet.model.LoginRequest;
import ru.otus.nyuriv.socialnet.model.TokenResponse;

@RestController
@RequestMapping("login")
public class LoginApi {
    @PostMapping
    public TokenResponse login(@RequestBody LoginRequest request) {
        return new TokenResponse();
    }
}
