package ru.itintego.javatest.controllers.rest;

import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface DataController<T, ID> {
    @GetMapping
    List<T> findAll();

    @GetMapping("{id}")
    T findById(@PathVariable("id") ID id);

    @PostMapping
    T save(@RequestBody T t);

    @PutMapping("{id}")
    T update(@PathVariable("id") ID id, @RequestBody T t);

    @DeleteMapping("{id}")
    void delete(@PathVariable("id") ID id);
}
