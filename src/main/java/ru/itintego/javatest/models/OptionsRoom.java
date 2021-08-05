package ru.itintego.javatest.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Table(name = "options_room")
@Entity
@Setter
@Getter
@RequiredArgsConstructor
public class OptionsRoom extends SuperEntity {

    private String name;

    @ManyToMany(mappedBy = "optionsRooms")
    @JsonBackReference
    private Set<Room> rooms = new HashSet<>();

}