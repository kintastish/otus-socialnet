package ru.otus.nyuriv.socialnet.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.otus.nyuriv.socialnet.dao.SessionDao;
import ru.otus.nyuriv.socialnet.gen.jooq.socialnetdb.Tables;
import ru.otus.nyuriv.socialnet.gen.jooq.socialnetdb.tables.pojos.Sessions;
import ru.otus.nyuriv.socialnet.gen.jooq.socialnetdb.tables.records.SessionsRecord;

import javax.sql.DataSource;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Component
@Slf4j
public class SessionDaoImpl implements SessionDao {
    private static final ru.otus.nyuriv.socialnet.gen.jooq.socialnetdb.tables.Sessions SESSIONS = Tables.SESSIONS;
    private final DataSource dataSource;

    @Autowired
    public SessionDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Sessions createOrUpdateSession(Sessions session) {
        AtomicReference<Sessions> res = new AtomicReference<>();
        try (Connection conn = dataSource.getConnection()) {
            DSLContext dsl = DSL.using(conn, SQLDialect.MYSQL);
            dsl.transaction(conf -> {
                DSLContext ctx = DSL.using(conf);
                List<Sessions> records = ctx.selectFrom(SESSIONS)
                        .where(SESSIONS.USER_ID.eq(session.getUserId()))
                        .fetchInto(Sessions.class);
                if (records.isEmpty()) {
                    Sessions inserted = ctx.insertInto(SESSIONS)
                            .set(SESSIONS.USER_ID, session.getUserId())
                            .set(SESSIONS.SESSION_ID, session.getSessionId())
                            .set(SESSIONS.STARTED, session.getStarted())
                            .set(SESSIONS.EXPIRING, session.getExpiring())
                            .returning(SESSIONS.asterisk())
                            .fetchOneInto(Sessions.class);
                    res.set(inserted);
                } else {
                    Sessions updated = ctx.update(SESSIONS)
                            .set(SESSIONS.SESSION_ID, session.getSessionId())
                            .set(SESSIONS.STARTED, session.getStarted())
                            .set(SESSIONS.EXPIRING, session.getExpiring())
                            .returning(SESSIONS.asterisk())
                            .fetchOneInto(Sessions.class);
                    res.set(updated);
                }
            });
        } catch (Exception e) {
            log.error("Session getting error, user_id: {}", session, e);
        }
        return res.get();
    }
}
