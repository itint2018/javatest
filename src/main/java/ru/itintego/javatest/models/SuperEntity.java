package ru.itintego.javatest.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.itintego.javatest.jpa_events.JpaEventListener;

import javax.persistence.*;

@MappedSuperclass
@Setter
@Getter
@RequiredArgsConstructor
@ToString
@EntityListeners(JpaEventListener.class)
public class SuperEntity {
    @Column(nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
