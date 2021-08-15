package ru.itintego.javatest.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@RequiredArgsConstructor
public class SaveUserDto {
    @NotNull
    @NotEmpty
    private String login;
    private String pass;
    @NotNull
    @NotEmpty
    private String lastName;
    @NotNull
    @NotEmpty
    private String firstName;
    private List<String> role;
}
