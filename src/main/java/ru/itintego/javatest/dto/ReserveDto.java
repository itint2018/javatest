package ru.itintego.javatest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReserveDto {
    private String date;
    private String timeStart;
    private String timeEnd;
    private String description;
}
