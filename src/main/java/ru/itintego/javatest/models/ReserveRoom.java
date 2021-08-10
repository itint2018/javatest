package ru.itintego.javatest.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;


@Entity
@Table(name = "reserve_room")
@Setter
@Getter
@RequiredArgsConstructor
public class ReserveRoom extends SuperEntity {

    @OneToOne
    @JsonIgnoreProperties({"name", "countOfPlaces"})
    private Room room;
    @OneToOne
    @JsonIgnoreProperties({"login", "password", "lastName", "firstName", "roles"})
    private User user;
    @OneToOne
    @JsonIgnoreProperties({"login", "password", "lastName", "firstName", "roles"})
    private User proof;
    private LocalDateTime start;
    private LocalDateTime end;
    private String description;
}
