package ru.itintego.javatest.dto;

import lombok.Data;
import ru.itintego.javatest.models.ReserveRoom;
import ru.itintego.javatest.models.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@Data
public class ReserveRoomDto {
    String start;
    String end;
    String proof;
    String user;
    String description;

    public ReserveRoomDto(ReserveRoom reserveRoom) {
        this.start = parseLocalDateTime(reserveRoom.getStart());
        this.end = parseLocalDateTime(reserveRoom.getEnd());
        this.proof = parseUser(reserveRoom.getProof());
        this.user = parseUser(reserveRoom.getUser());
        this.description = reserveRoom.getDescription();
    }

    public static String parseLocalDateTime(LocalDateTime end) {
        return end.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));
    }

    public static String parseUser(User user) {
        if (user != null)
            return "[" + user.getLogin() + "] " + user.getLastName() + " " + user.getFirstName();
        else
            return "Не известен";
    }
}
