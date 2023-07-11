package ru.otus.nyuriv.socialnet.dao;

import ru.otus.nyuriv.socialnet.gen.jooq.socialnetdb.tables.pojos.UserProfiles;

public interface UserProfileDao {
    UserProfiles getProfile(String userId);
}
