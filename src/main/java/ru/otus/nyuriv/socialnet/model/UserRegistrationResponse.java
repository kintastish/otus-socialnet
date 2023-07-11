package ru.otus.nyuriv.socialnet.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class UserRegistrationResponse {
    @JsonProperty("user_id")
    private String userId;
}
