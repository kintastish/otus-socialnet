package ru.otus.nyuriv.socialnet.dao;

import ru.otus.nyuriv.socialnet.gen.jooq.socialnetdb.tables.pojos.UserProfiles;
import ru.otus.nyuriv.socialnet.model.RegistrationModel;

public interface RegisterDao {
    UserProfiles createUserAndProfile(RegistrationModel registrationModel);
}
