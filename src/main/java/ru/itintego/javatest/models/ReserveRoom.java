package ru.itintego.javatest.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;


@Entity
@Table(name = "reserve_room")
@Setter
@Getter
@RequiredArgsConstructor
public class ReserveRoom extends SuperEntity {

    @JoinColumn
    @OneToOne(cascade = {javax.persistence.CascadeType.REMOVE})
    @JsonIgnoreProperties({"countOfPlaces"})
    private Room room;
    @OneToOne
    @JsonIgnoreProperties({"login", "password", "roles", "session"})
    private User user;
    @OneToOne
    @JsonIgnoreProperties({"login", "password", "roles", "session"})
    private User proof;
    private LocalDateTime start;
    private LocalDateTime end;
    private String description;
}
