package ru.itintego.javatest.services;

import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface DataService<T, ID> {

    List<T> findAll();

    T findById(ID id);

    T save(T t);

    T update(ID id, T t);

    void delete(ID id);
}
