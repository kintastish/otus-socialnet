package ru.otus.nyuriv.socialnet.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.otus.nyuriv.socialnet.model.UserProfile;
import ru.otus.nyuriv.socialnet.model.UserRegistrationRequest;
import ru.otus.nyuriv.socialnet.model.UserRegistrationResponse;
import ru.otus.nyuriv.socialnet.service.RegisteringService;

@RestController
@RequestMapping("user")
public class UserApi {
    private final RegisteringService registeringService;

    @Autowired
    public UserApi(RegisteringService registeringService) {
        this.registeringService = registeringService;
    }

    @PostMapping("register")
    public UserRegistrationResponse registerUser(@RequestBody UserRegistrationRequest request) {
        return registeringService.register(request);
    }

    @GetMapping("get/{id}")
    public UserProfile getUserProfile(@PathVariable("id") String id) {
        return new UserProfile();
    }
}
