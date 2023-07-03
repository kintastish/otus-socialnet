package ru.otus.nyuriv.socialnet.service;

import ru.otus.nyuriv.socialnet.model.Session;

public interface SessionService {
    Session createSession(String userId);
}
