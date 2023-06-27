package ru.otus.nyuriv.socialnet.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserRegistrationResponse {
    @JsonProperty("user_id")
    private String userId;
}
