package ru.otus.nyuriv.socialnet.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserRegistrationRequest {
    @JsonProperty("first_name")
    private String first_name;

    @JsonProperty("second_name")
    private String second_name;

    @JsonProperty("birthdate")
    private String birthdate;

    @JsonProperty("biography")
    private String biography;

    @JsonProperty("city")
    private String city;

    @JsonProperty("password")
    private String password;
}
