package ru.itintego.javatest.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.itintego.javatest.models.OptionsRoom;

import javax.validation.constraints.NotEmpty;

@Data
@RequiredArgsConstructor
public class OptionsRoomDto {
    @NotEmpty
    private String name;
    private String icon;

    public OptionsRoomDto(OptionsRoom optionsRoom) {
        this.name = optionsRoom.getName();
        this.icon = optionsRoom.getIcon();
    }
}
