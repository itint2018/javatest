package ru.itintego.javatest.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@Setter
@Getter
@ToString
@RequiredArgsConstructor
public class User extends SuperEntity {

    @Column(name = "login", length = 100)
    private String login;

    @Column(name = "password", length = 100)
    @ToString.Exclude
    @JsonIgnore
    private String password;

    @Column(name = "last_name", length = 100)
    private String lastName;

    @Column(name = "first_name", length = 100)
    private String firstName;
    @Column(name = "enabled")
    private Boolean enabled;

    @Column(name = "session")
    @JsonIgnore
    @ToString.Exclude
    private String session;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "USER_ROLE",
            joinColumns = @JoinColumn(name = "USER_id"),
            inverseJoinColumns = @JoinColumn(name = "ROLE_id"))
    @JsonManagedReference
    @JsonIgnoreProperties({"name", "users"})
    Set<Role> roles = new HashSet<>();


    public String rolesSet() {
        return roles.stream().map(Role::getName).collect(Collectors.joining(", "));
    }
}
