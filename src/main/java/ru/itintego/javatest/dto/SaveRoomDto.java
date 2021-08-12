package ru.itintego.javatest.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class SaveRoomDto {
    //"name":"тест","description":"тест","countOfPlaces":"123"}
    String name;
    String description;
    Integer countOfPlaces;

}
