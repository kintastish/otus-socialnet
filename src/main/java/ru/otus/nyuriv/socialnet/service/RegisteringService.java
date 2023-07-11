package ru.otus.nyuriv.socialnet.service;

import ru.otus.nyuriv.socialnet.model.UserRegistrationRequest;
import ru.otus.nyuriv.socialnet.model.UserRegistrationResponse;

public interface RegisteringService {
    UserRegistrationResponse register(UserRegistrationRequest request);
}
