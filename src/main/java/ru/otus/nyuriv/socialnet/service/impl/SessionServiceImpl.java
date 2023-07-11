package ru.otus.nyuriv.socialnet.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.nyuriv.socialnet.config.AppSettings;
import ru.otus.nyuriv.socialnet.dao.SessionDao;
import ru.otus.nyuriv.socialnet.gen.jooq.socialnetdb.tables.pojos.Sessions;
import ru.otus.nyuriv.socialnet.model.Session;
import ru.otus.nyuriv.socialnet.service.SessionService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@Slf4j
public class SessionServiceImpl implements SessionService {
    private final AppSettings settings;
    private final SessionDao sessionDao;

    @Autowired
    public SessionServiceImpl(AppSettings settings, SessionDao sessionDao) {
        this.settings = settings;
        this.sessionDao = sessionDao;
    }

    @Override
    public Session createSession(String userId) {
        Sessions session = new Sessions();
        session.setUserId(userId);
        session.setSessionId(UUID.randomUUID().toString());
        LocalDateTime now = LocalDateTime.now();
        session.setStarted(now);
        session.setExpiring(now.plus(settings.getSessionLifetimeMinutes(), ChronoUnit.MINUTES));
        Sessions createdSession = sessionDao.createOrUpdateSession(session);
        return convert(createdSession);
    }

    private Session convert(Sessions createdSession) {
        Session session = new Session();
        session.setUserId(createdSession.getUserId());
        session.setToken(createdSession.getSessionId());
        session.setStarted(createdSession.getStarted().toInstant(ZoneOffset.UTC));
        session.setExpiring(createdSession.getExpiring().toInstant(ZoneOffset.UTC));
        return session;
    }
}
