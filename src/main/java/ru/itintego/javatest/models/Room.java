package ru.itintego.javatest.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "rooms")
@Setter
@Getter
@RequiredArgsConstructor
public class Room extends SuperEntity {
    private String name;
    private Integer countOfPlaces;
    private String description;

    @ManyToMany
    @JoinTable(name = "ROOM_OPTIONS_ROOM",
            joinColumns = @JoinColumn(name = "ROOM_ID"),
            inverseJoinColumns = @JoinColumn(name = "OPTIONS_ROOM_ID"))
    @JsonManagedReference
    @JsonIgnoreProperties({"name", "rooms"})
    private Set<OptionsRoom> optionsRooms;

    @JsonIgnore
    public String getOptionsRoomString() {
        return optionsRooms.stream().map(OptionsRoom::getName).collect(Collectors.joining(", "));
    }
}
