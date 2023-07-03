package ru.otus.nyuriv.socialnet.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.otus.nyuriv.socialnet.dao.UsersDao;
import ru.otus.nyuriv.socialnet.gen.jooq.socialnetdb.Tables;
import ru.otus.nyuriv.socialnet.gen.jooq.socialnetdb.tables.pojos.Users;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.List;

@Component
@Slf4j
public class UsersDaoImpl implements UsersDao {
    private static final ru.otus.nyuriv.socialnet.gen.jooq.socialnetdb.tables.Users USERS = Tables.USERS;
    private final DataSource dataSource;

    @Autowired
    public UsersDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Users getUser(String id) {
        Users res = null;
        try (Connection conn = dataSource.getConnection()) {
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);
            List<Users> records = ctx.selectFrom(USERS)
                    .where(USERS.ID.eq(id))
                    .fetchInto(Users.class);
            //пока индексов нет возвращаем первый из полученных
            if (!records.isEmpty()) {
                res = records.get(0);
            }
        } catch (Exception e) {
            log.error("User getting error, id: {}", id, e);
        }
        return res;
    }
}
