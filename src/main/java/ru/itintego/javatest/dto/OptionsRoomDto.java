package ru.itintego.javatest.dto;

import lombok.Data;
import ru.itintego.javatest.models.OptionsRoom;

@Data
public class OptionsRoomDto {
    private String name;
    private String icon;
    private String iconPack;

    public OptionsRoomDto(OptionsRoom optionsRoom) {
        this.name = optionsRoom.getName();
        String[] s = optionsRoom.getIcon().split(" ");
        this.iconPack = s[0];
        this.icon = s[1];
    }
}
