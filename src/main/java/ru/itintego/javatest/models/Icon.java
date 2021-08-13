package ru.itintego.javatest.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "icons")
public class Icon {
    @Id
    private String name;
}
