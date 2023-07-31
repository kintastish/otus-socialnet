package ru.otus.nyuriv.socialnet.service;

import ru.otus.nyuriv.socialnet.model.UserProfileResponse;

import java.util.List;

public interface UserProfileService {
    UserProfileResponse getUserProfile(String userId);

    List<UserProfileResponse> findProfiles(String firstName, String lastName);
}
