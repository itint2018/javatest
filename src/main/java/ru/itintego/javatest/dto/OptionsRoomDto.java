package ru.itintego.javatest.dto;

import lombok.Data;
import ru.itintego.javatest.models.OptionsRoom;

@Data
public class OptionsRoomDto {
    private String name;
    private String icon;

    public OptionsRoomDto(OptionsRoom optionsRoom) {
        this.name = optionsRoom.getName();
        this.icon = optionsRoom.getIcon();
    }
}
