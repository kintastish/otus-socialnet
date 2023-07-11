package ru.otus.nyuriv.socialnet.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(exclude = "password")
public class UserRegistrationRequest {
    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("second_name")
    private String secondName;

    @JsonProperty("birthdate")
    private String birthdate;

    @JsonProperty("biography")
    private String biography;

    @JsonProperty("city")
    private String city;

    @JsonProperty("password")
    private String password;
}
