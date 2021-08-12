package ru.itintego.javatest.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class AuthorizationRequest {
    private String login;
    private String pass;
}
