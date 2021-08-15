package ru.itintego.javatest.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@RequiredArgsConstructor
public class SaveUserDto {
    @NotNull
    private String login;
    @NotNull
    private String pass;
    @NotNull
    private String lastName;
    @NotNull
    private String firstName;
    @NotNull
    private List<String> role;
}
