package ru.otus.nyuriv.socialnet.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.otus.nyuriv.socialnet.model.UserProfileResponse;
import ru.otus.nyuriv.socialnet.model.UserRegistrationRequest;
import ru.otus.nyuriv.socialnet.model.UserRegistrationResponse;
import ru.otus.nyuriv.socialnet.service.RegisteringService;
import ru.otus.nyuriv.socialnet.service.UserProfileService;

@RestController
@RequestMapping("user")
public class UserApi {
    private final RegisteringService registeringService;
    private final UserProfileService userProfileService;

    @Autowired
    public UserApi(RegisteringService registeringService, UserProfileService userProfileService) {
        this.registeringService = registeringService;
        this.userProfileService = userProfileService;
    }

    @PostMapping("register")
    public UserRegistrationResponse registerUser(@RequestBody UserRegistrationRequest request) {
        return registeringService.register(request);
    }

    @GetMapping("get/{id}")
    public UserProfileResponse getUserProfile(@PathVariable("id") String id) {
        return userProfileService.getUserProfile(id);
    }
}
