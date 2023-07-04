package ru.otus.nyuriv.socialnet.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import ru.otus.nyuriv.socialnet.config.AppSettings;
import ru.otus.nyuriv.socialnet.exception.UnauthorizedException;
import ru.otus.nyuriv.socialnet.model.LoginRequest;
import ru.otus.nyuriv.socialnet.model.Session;
import ru.otus.nyuriv.socialnet.model.TokenResponse;
import ru.otus.nyuriv.socialnet.model.User;
import ru.otus.nyuriv.socialnet.service.LoginService;
import ru.otus.nyuriv.socialnet.service.SessionService;
import ru.otus.nyuriv.socialnet.service.UserService;
import ru.otus.nyuriv.socialnet.util.PasswordUtil;

import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Objects;

import static ru.otus.nyuriv.socialnet.util.ValidationUtil.checkNotEmpty;

@Service
@Slf4j
public class LoginServiceImpl implements LoginService {
    private final AppSettings settings;
    private final UserService userService;
    private final SessionService sessionService;

    @Autowired
    public LoginServiceImpl(AppSettings settings, UserService userService, SessionService sessionService) {
        this.settings = settings;
        this.userService = userService;
        this.sessionService = sessionService;
    }

    @Override
    public TokenResponse login(LoginRequest request) {
        checkNotEmpty(request.getId(), "id");
        checkNotEmpty(request.getPassword(), "password");
        User user = userService.getUser(request.getId());
        if (user == null) {
            throwUnauthorizedException(request.getId());
        }
        String providedHash = PasswordUtil.hash(request.getPassword(), settings.getSecretKey());
        if (!Objects.equals(providedHash, user.getPasswordHash())) {
            throwUnauthorizedException(request.getId());
        }
        Session session = sessionService.createSession(user.getId());
        String token = session.getToken();
        return TokenResponse.of(token);
    }

    private void throwUnauthorizedException(String userId) {
        throw new UnauthorizedException("Wrong credentials " + userId);
    }

}
