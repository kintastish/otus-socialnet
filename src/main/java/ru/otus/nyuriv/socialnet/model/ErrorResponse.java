package ru.otus.nyuriv.socialnet.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ErrorResponse {
    @JsonProperty("message")
    private String message;

    @JsonProperty("request_id")
    private String requestId;

    @JsonProperty("code")
    private int code;

}
