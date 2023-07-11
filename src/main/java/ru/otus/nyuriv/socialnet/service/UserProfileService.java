package ru.otus.nyuriv.socialnet.service;

import ru.otus.nyuriv.socialnet.model.UserProfileResponse;

public interface UserProfileService {
    UserProfileResponse getUserProfile(String userId);
}
