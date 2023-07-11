package ru.otus.nyuriv.socialnet.model;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@Data
@ToString(exclude = "passwordHash")
public class RegistrationModel {
    private String id;
    private String firstName;
    private String secondName;
    private LocalDate birthdate;
    private String biography;
    private String city;
    private String passwordHash;
}
