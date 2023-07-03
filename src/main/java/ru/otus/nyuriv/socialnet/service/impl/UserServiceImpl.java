package ru.otus.nyuriv.socialnet.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.nyuriv.socialnet.dao.UsersDao;
import ru.otus.nyuriv.socialnet.gen.jooq.socialnetdb.tables.pojos.Users;
import ru.otus.nyuriv.socialnet.gen.jooq.socialnetdb.tables.records.UsersRecord;
import ru.otus.nyuriv.socialnet.model.User;
import ru.otus.nyuriv.socialnet.service.UserService;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UsersDao usersDao;

    @Autowired
    public UserServiceImpl(UsersDao usersDao) {
        this.usersDao = usersDao;
    }

    @Override
    public User getUser(String id) {
        Users record = usersDao.getUser(id);
        return convert(record);
    }

    private User convert(Users record) {
        return record == null ? null : new User(record.getId(), record.getPwdHash());
    }
}
