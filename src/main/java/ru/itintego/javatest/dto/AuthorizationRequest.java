package ru.itintego.javatest.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class AuthorizationRequest {
    private String username;
    private String password;
}
