package ru.otus.nyuriv.socialnet.dao;

import ru.otus.nyuriv.socialnet.gen.jooq.socialnetdb.tables.pojos.Users;

public interface UsersDao {
    Users getUser(String id);
}
