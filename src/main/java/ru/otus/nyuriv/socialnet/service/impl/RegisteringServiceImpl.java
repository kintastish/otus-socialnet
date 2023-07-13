package ru.otus.nyuriv.socialnet.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.nyuriv.socialnet.config.AppSettings;
import ru.otus.nyuriv.socialnet.dao.RegisterDao;
import ru.otus.nyuriv.socialnet.exception.BadRequestException;
import ru.otus.nyuriv.socialnet.gen.jooq.socialnetdb.tables.pojos.UserProfiles;
import ru.otus.nyuriv.socialnet.model.RegistrationModel;
import ru.otus.nyuriv.socialnet.model.UserRegistrationRequest;
import ru.otus.nyuriv.socialnet.model.UserRegistrationResponse;
import ru.otus.nyuriv.socialnet.service.RegisteringService;
import ru.otus.nyuriv.socialnet.util.PasswordUtil;

import java.time.LocalDate;
import java.util.UUID;

import static ru.otus.nyuriv.socialnet.util.ValidationUtil.*;

@Service
@Slf4j
public class RegisteringServiceImpl implements RegisteringService {
    private final AppSettings settings;
    private final RegisterDao registerDao;

    @Autowired
    public RegisteringServiceImpl(AppSettings settings, RegisterDao registerDao) {
        this.settings = settings;
        this.registerDao = registerDao;
    }

    @Override
    public UserRegistrationResponse register(UserRegistrationRequest request) {
        checkRequest(request);
        String hashed = PasswordUtil.hash(request.getPassword(), settings.getSecretKey());
        RegistrationModel model = createModel(request, hashed);
        if (model.getBirthdate().isAfter(LocalDate.now())) {
            throw new BadRequestException("Wrong date");
        }
        UserProfiles profile = registerDao.createUserAndProfile(model);
        if (profile == null) {
            throw new IllegalStateException("Registration failed");
        }
        return UserRegistrationResponse.of(profile.getId());
    }

    private RegistrationModel createModel(UserRegistrationRequest request, String hashed) {
        RegistrationModel model = new RegistrationModel();
        model.setId(UUID.randomUUID().toString());
        model.setFirstName(request.getFirstName());
        model.setSecondName(request.getSecondName());
        model.setBirthdate(parseDate(request.getBirthdate()));
        model.setBiography(request.getBiography());
        model.setCity(request.getCity());
        model.setPasswordHash(hashed);
        return model;
    }

    private void checkRequest(UserRegistrationRequest request) {
        checkNotEmpty(request.getFirstName(), "first_name");
        checkNotEmpty(request.getSecondName(), "second_name");
        checkNotEmpty(request.getBirthdate(), "birthdate");
        checkNotEmpty(request.getBiography(), "biography");
        checkNotEmpty(request.getCity(), "city");
        checkNotEmpty(request.getPassword(), "password");
    }

    private LocalDate parseDate(String s) {
        try {
            return LocalDate.parse(s);
        } catch (Exception e) {
            throw new BadRequestException("date '" + s + "'");
        }
    }

}
