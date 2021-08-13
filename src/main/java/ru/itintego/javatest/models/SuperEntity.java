package ru.itintego.javatest.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.itintego.javatest.jpa_events.JpaEventListener;

import javax.persistence.*;

@MappedSuperclass
@EntityListeners({JpaEventListener.class})
@Setter
@Getter
@RequiredArgsConstructor
@ToString
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class SuperEntity {
    @Column(nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Transient
    private String clazz;
}
