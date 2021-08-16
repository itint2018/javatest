package ru.itintego.javatest.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class SaveRoomDto {
    //"name":"тест","description":"тест","countOfPlaces":"123"}
    String name;
    String description;
    Integer countOfPlaces;

    private List<Long> optionsRoom;
}
