package ru.itintego.javatest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.itintego.javatest.models.ReserveRoom;

import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
public class ReserveDto {
    private Long id;
    private String date;
    private String timeStart;
    private String timeEnd;
    private String description;


    public ReserveDto(ReserveRoom reserveRoom) {
        this.id = reserveRoom.getId();
        this.date = reserveRoom.getStart().format(DateTimeFormatter.ISO_LOCAL_DATE);
        this.description = reserveRoom.getDescription();
        this.timeStart = reserveRoom.getStart().format(DateTimeFormatter.ISO_LOCAL_TIME);
        this.timeEnd = reserveRoom.getEnd().format(DateTimeFormatter.ISO_LOCAL_TIME);
    }
}
