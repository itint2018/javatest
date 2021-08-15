package ru.itintego.javatest.controllers.rest;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itintego.javatest.dto.SaveUserDto;
import ru.itintego.javatest.models.Role;
import ru.itintego.javatest.models.User;
import ru.itintego.javatest.repositories.RoleRepository;
import ru.itintego.javatest.repositories.UserRepository;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@Secured({"ROLE_MANAGER", "ROLE_ADMIN"})
public class UserController implements DataController<User, Long> {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserController(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long aLong) {
        return userRepository.getById(aLong);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @RequestMapping("/new")
    public User save(@Valid @RequestBody SaveUserDto saveUserDto) {
        Set<Role> collect = saveUserDto.getRole().stream().map(roleRepository::findByName).collect(Collectors.toSet());
        User user = new User();
        user.setFirstName(saveUserDto.getFirstName());
        user.setLastName(saveUserDto.getLastName());
        user.setLogin(saveUserDto.getLogin());
        user.setPassword(saveUserDto.getPass());
        user.setEnabled(true);
        user.setRoles(collect);
        return userRepository.save(user);
    }

    @Override
    public User update(Long aLong, User user) {
        if (userRepository.existsById(aLong))
            return userRepository.save(user);
        else throw new EntityNotFoundException();
    }

    @Override
    public void delete(Long aLong) {
        userRepository.deleteById(aLong);
    }
}
