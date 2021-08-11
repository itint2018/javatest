package ru.itintego.javatest.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@MappedSuperclass
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
}
