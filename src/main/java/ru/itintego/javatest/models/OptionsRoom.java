package ru.itintego.javatest.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Table(name = "options_room")
@Entity
@Setter
@Getter
@RequiredArgsConstructor
public class OptionsRoom extends SuperEntity {

    private String name;
    private String icon;

    @ManyToMany(mappedBy = "optionsRooms")
    @JsonBackReference
    private Set<Room> rooms = new HashSet<>();

}