package ru.itintego.javatest.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Table(name = "role")
@Entity
@Getter
@Setter
@RequiredArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
public class Role extends SuperEntity {

    private String name;

    @ManyToMany(mappedBy = "roles")
    @JsonBackReference
    @JsonIgnoreProperties({"login", "password", "lastName", "firstName", "roles"})
    private List<User> user;

}