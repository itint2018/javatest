package ru.itintego.javatest.dto;

import lombok.Data;

@Data
public class SaveUserDto {
    private String login;
    private String password;
    private String lastName;
    private String firstName;
}
