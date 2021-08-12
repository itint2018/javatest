package ru.itintego.javatest.dto;

import lombok.Data;
import ru.itintego.javatest.models.ReserveRoom;
import ru.itintego.javatest.models.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Objects;

@Data
public class ReserveRoomDto {
    Long id;
    String date;
    String start;
    String end;
    String proof;
    String user;
    String description;


    public Boolean isProofed() {
        return !Objects.equals(proof, "Не известен");
    }

    public ReserveRoomDto(ReserveRoom reserveRoom) {
        this.id = reserveRoom.getId();
        this.date = reserveRoom.getStart().format(DateTimeFormatter.ISO_LOCAL_DATE);
        this.start = parseLocalDateTime(reserveRoom.getStart());
        this.end = parseLocalDateTime(reserveRoom.getEnd());
        this.proof = parseUser(reserveRoom.getProof());
        this.user = parseUser(reserveRoom.getUser());
        this.description = reserveRoom.getDescription();
    }

    public static String parseLocalDateTime(LocalDateTime end) {
        return end.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT));
    }

    public static String parseUser(User user) {
        if (user != null)
            return user.getLastName() + " " + user.getFirstName().charAt(0) + ".";
        else
            return "Не известен";
    }
}
