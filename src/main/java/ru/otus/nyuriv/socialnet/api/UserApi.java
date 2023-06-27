package ru.otus.nyuriv.socialnet.api;

import org.springframework.web.bind.annotation.*;
import ru.otus.nyuriv.socialnet.model.UserProfile;
import ru.otus.nyuriv.socialnet.model.UserRegistrationRequest;
import ru.otus.nyuriv.socialnet.model.UserRegistrationResponse;

@RestController
@RequestMapping("user")
public class UserApi {
    @PostMapping("register")
    public UserRegistrationResponse registerUser(@RequestBody UserRegistrationRequest request) {
        return new UserRegistrationResponse();
    }

    @GetMapping("get/{id}")
    public UserProfile getUserProfile(@PathVariable("id") String id) {
        return new UserProfile();
    }
}
