package ru.otus.nyuriv.socialnet.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.nyuriv.socialnet.dao.UserProfileDao;
import ru.otus.nyuriv.socialnet.exception.NotFoundException;
import ru.otus.nyuriv.socialnet.gen.jooq.socialnetdb.tables.pojos.UserProfiles;
import ru.otus.nyuriv.socialnet.model.UserProfileResponse;
import ru.otus.nyuriv.socialnet.service.UserProfileService;

import java.time.LocalDate;

@Service
@Slf4j
public class UserProfileServiceImpl implements UserProfileService {
    private final UserProfileDao dao;

    @Autowired
    public UserProfileServiceImpl(UserProfileDao dao) {
        this.dao = dao;
    }

    @Override
    public UserProfileResponse getUserProfile(String userId) {
        UserProfiles profile = dao.getProfile(userId);
        if (profile == null) {
            throw new NotFoundException("User with id " + userId + " not found");
        }
        return convert(profile);
    }

    private UserProfileResponse convert(UserProfiles profile) {
        LocalDate birthdate = profile.getBirthdate();
        int age = calcAge(birthdate);
        UserProfileResponse resp = new UserProfileResponse();
        resp.setId(profile.getId());
        resp.setFirstName(profile.getFirstName());
        resp.setSecondName(profile.getSecondName());
        resp.setAge(age);
        resp.setBirthdate(birthdate.toString());
        resp.setBiography(profile.getBiography());
        resp.setCity(profile.getCity());
        return resp;
    }

    private int calcAge(LocalDate birthdate) {
        LocalDate now = LocalDate.now();
        int age = now.getYear() - birthdate.getYear();
        int monthDiff = now.getMonthValue() - birthdate.getMonthValue();
        if (monthDiff < 0) {
            return age;
        }
        if ((monthDiff > 0) || (now.getDayOfMonth() > birthdate.getDayOfMonth())) {
            age++;
        }
        return age;
    }
}
