package ru.otus.nyuriv.socialnet.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserProfile {
    @JsonProperty("id")
    private String id;

    @JsonProperty("first_name")
    private String first_name;

    @JsonProperty("second_name")
    private String second_name;

    @JsonProperty("age")
    private int age;

    @JsonProperty("birthdate")
    private String birthdate;

    @JsonProperty("biography")
    private String biography;

    @JsonProperty("city")
    private String city;

}
