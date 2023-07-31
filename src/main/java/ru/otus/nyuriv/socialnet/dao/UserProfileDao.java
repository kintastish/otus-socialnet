package ru.otus.nyuriv.socialnet.dao;

import ru.otus.nyuriv.socialnet.gen.jooq.socialnetdb.tables.pojos.UserProfiles;

import java.util.List;

public interface UserProfileDao {
    UserProfiles getProfile(String userId);

    List<UserProfiles> findProfiles(String firstName, String lastName);
}
