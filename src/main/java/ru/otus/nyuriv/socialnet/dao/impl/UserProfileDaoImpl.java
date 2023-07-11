package ru.otus.nyuriv.socialnet.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.otus.nyuriv.socialnet.dao.UserProfileDao;
import ru.otus.nyuriv.socialnet.gen.jooq.socialnetdb.Tables;
import ru.otus.nyuriv.socialnet.gen.jooq.socialnetdb.tables.pojos.UserProfiles;

import javax.sql.DataSource;
import java.sql.Connection;

@Component
@Slf4j
public class UserProfileDaoImpl implements UserProfileDao {
    private static final ru.otus.nyuriv.socialnet.gen.jooq.socialnetdb.tables.UserProfiles PROFILES = Tables.USER_PROFILES;
    private final DataSource dataSource;

    @Autowired
    public UserProfileDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public UserProfiles getProfile(String userId) {
        UserProfiles profile = null;
        try (Connection conn = dataSource.getConnection()) {
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);
            profile = ctx.selectFrom(PROFILES)
                    .where(PROFILES.ID.eq(userId))
                    .fetchOneInto(UserProfiles.class);
        } catch (Exception e) {
            log.error("Getting user profile error, userId: {}", userId, e);
        }
        return profile;
    }
}
