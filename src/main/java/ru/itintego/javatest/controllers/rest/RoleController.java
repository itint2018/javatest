package ru.itintego.javatest.controllers.rest;

import org.springframework.web.bind.annotation.*;
import ru.itintego.javatest.models.Role;
import ru.itintego.javatest.repositories.RoleRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController implements DataController<Role, Long> {
    private final RoleRepository roleRepository;

    public RoleController(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    @GetMapping()
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    @GetMapping("{id}")
    public Role findById(@PathVariable("id") Long aLong) {
        return roleRepository.getById(aLong);
    }

    @Override
    @PostMapping
    public Role save(@RequestBody Role role) {
        return roleRepository.save(role);
    }

    @Override
    @PutMapping("{id}")
    public Role update(@PathVariable("id") Long aLong, @RequestBody Role role) {
        if (roleRepository.existsById(aLong))
            return roleRepository.save(role);
        else throw new EntityNotFoundException();
    }

    @Override
    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Long aLong) {
        roleRepository.deleteById(aLong);
    }
}
