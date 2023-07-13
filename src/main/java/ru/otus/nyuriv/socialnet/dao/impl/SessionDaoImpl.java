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
        Sessions result = null;
        try (Connection conn = dataSource.getConnection()) {
            DSLContext dsl = DSL.using(conn, SQLDialect.MYSQL);
            result = dsl.transactionResult(conf -> {
                DSLContext ctx = DSL.using(conf);
                Sessions record = getUserSession(ctx, session.getUserId());
                if (record == null) {
                    checkExecutionResult(
                            ctx.insertInto(SESSIONS)
                                    .set(SESSIONS.USER_ID, session.getUserId())
                                    .set(SESSIONS.SESSION_ID, session.getSessionId())
                                    .set(SESSIONS.STARTED, session.getStarted())
                                    .set(SESSIONS.EXPIRING, session.getExpiring())
                                    .execute()
                    );
                } else {
                    checkExecutionResult(
                            ctx.update(SESSIONS)
                                    .set(SESSIONS.SESSION_ID, session.getSessionId())
                                    .set(SESSIONS.STARTED, session.getStarted())
                                    .set(SESSIONS.EXPIRING, session.getExpiring())
                                    .execute());
                }
                return getUserSession(ctx, session.getUserId());
            });
        } catch (Exception e) {
            log.error("Session getting error, user_id: {}", session, e);
        }
        return result;
    }

    private Sessions getUserSession(DSLContext ctx, String userId) {
        return ctx.selectFrom(SESSIONS)
                .where(SESSIONS.USER_ID.eq(userId))
                .fetchOneInto(Sessions.class);
    }

    private void checkExecutionResult(int count) {
        if (count != 1) {
            throw new IllegalStateException("Query execution error");
        }
    }
}
