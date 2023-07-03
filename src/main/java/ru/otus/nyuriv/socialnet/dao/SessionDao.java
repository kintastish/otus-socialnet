package ru.otus.nyuriv.socialnet.dao;

import ru.otus.nyuriv.socialnet.gen.jooq.socialnetdb.tables.pojos.Sessions;

public interface SessionDao {
     Sessions createOrUpdateSession(Sessions session);
}
