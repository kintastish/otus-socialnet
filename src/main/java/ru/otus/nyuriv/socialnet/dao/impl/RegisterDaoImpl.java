package ru.otus.nyuriv.socialnet.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.otus.nyuriv.socialnet.dao.RegisterDao;
import ru.otus.nyuriv.socialnet.gen.jooq.socialnetdb.Tables;
import ru.otus.nyuriv.socialnet.gen.jooq.socialnetdb.tables.pojos.UserProfiles;
import ru.otus.nyuriv.socialnet.model.RegistrationModel;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.concurrent.atomic.AtomicReference;

@Component
@Slf4j
public class RegisterDaoImpl implements RegisterDao {
    private static final ru.otus.nyuriv.socialnet.gen.jooq.socialnetdb.tables.Users USERS = Tables.USERS;
    private static final ru.otus.nyuriv.socialnet.gen.jooq.socialnetdb.tables.UserProfiles PROFILES = Tables.USER_PROFILES;
    private final DataSource dataSource;

    @Autowired
    public RegisterDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public UserProfiles createUserAndProfile(RegistrationModel registrationModel) {
        AtomicReference<UserProfiles> result = new AtomicReference<>();
        try (Connection conn = dataSource.getConnection()) {
            DSLContext dsl = DSL.using(conn, SQLDialect.MYSQL);
            dsl.transaction(conf -> {
                DSLContext ctx = DSL.using(conf);
                int count = ctx.insertInto(USERS)
                        .set(USERS.ID, registrationModel.getId())
                        .set(USERS.PWD_HASH, registrationModel.getPasswordHash())
                        .execute();
                if (count != 1) {
                    throw new IllegalStateException("User not created");
                }
                UserProfiles profile = ctx.insertInto(PROFILES)
                        .set(PROFILES.ID, registrationModel.getId())
                        .set(PROFILES.FIRST_NAME, registrationModel.getFirstName())
                        .set(PROFILES.SECOND_NAME, registrationModel.getSecondName())
                        .set(PROFILES.BIRTHDATE, registrationModel.getBirthdate())
                        .set(PROFILES.BIOGRAPHY, registrationModel.getBiography())
                        .set(PROFILES.CITY, registrationModel.getCity())
                        .returning()
                        .fetchOneInto(UserProfiles.class);
                result.set(profile);
            });
        } catch (Exception e) {
            log.error("User creation failed, model: {}", registrationModel, e);
        }
        return result.get();
    }
}
