package ru.itintego.javatest.dto;

import lombok.Data;
import ru.itintego.javatest.models.Room;

import java.util.Set;
import java.util.stream.Collectors;

@Data
public class IndexRoomDto {
    Long id;
    String name;
    Integer countOfPlaces;
    String description;
    Set<OptionsRoomDto> optionsRooms;
    Long countOfUnproofedReserve;
    Long countOfProofedReserveToday;
    Boolean clear;
    String fromTimeToTime;

    public IndexRoomDto(Room room) {
        this.id = room.getId();
        this.name = room.getName();
        this.countOfPlaces = room.getCountOfPlaces();
        this.description = room.getDescription();
        this.optionsRooms = room.getOptionsRooms().stream().map(OptionsRoomDto::new).collect(Collectors.toSet());
    }
}
