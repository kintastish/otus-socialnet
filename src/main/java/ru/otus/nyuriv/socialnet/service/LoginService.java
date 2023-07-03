package ru.otus.nyuriv.socialnet.service;

import org.springframework.web.bind.annotation.RequestBody;
import ru.otus.nyuriv.socialnet.model.LoginRequest;
import ru.otus.nyuriv.socialnet.model.TokenResponse;

public interface LoginService {
    TokenResponse login(@RequestBody LoginRequest request);
}
