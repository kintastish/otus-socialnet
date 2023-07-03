package ru.otus.nyuriv.socialnet.model;

import lombok.Data;

import java.time.Instant;

@Data
public class Session {
    private String userId;
    private String token;
    private Instant started;
    private Instant expiring;
}
