package ru.itintego.javatest.controllers.rest;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
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
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@Secured({"ROLE_MANAGER", "ROLE_ADMIN"})
public class UserController implements DataController<User, Long> {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final String defaultPassword;
    private final MessageDigest hd5Hash;

    public UserController(UserRepository userRepository,
                          RoleRepository roleRepository,
                          @Value("${it.intego.meeting_room.default_password}") String defaultPassword,
                          @Qualifier("md5Hash") MessageDigest hd5Hash) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.defaultPassword = defaultPassword;
        this.hd5Hash = hd5Hash;
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
        User user = new User();
        Set<Role> collect = Collections.emptySet();
        if (saveUserDto.getRole() != null) {
            collect = saveUserDto.getRole().stream().map(roleRepository::findByName).collect(Collectors.toSet());
        }
        user.setFirstName(saveUserDto.getFirstName());
        user.setLastName(saveUserDto.getLastName());
        user.setLogin(saveUserDto.getLogin());
        if (saveUserDto.getPass() != null)
            user.setPassword(saveUserDto.getPass());
        else
            user.setPassword(new String(hd5Hash.digest(defaultPassword.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8));
        user.setEnabled(true);
        if (!collect.isEmpty())
            user.setRoles(collect);
        else user.setRoles(Set.of(roleRepository.findByName("ROLE_EMPLOYEE")));
        return userRepository.save(user);
    }

    @RequestMapping("/{id}/edit")
    public User edit(@Valid @RequestBody SaveUserDto saveUserDto, @PathVariable Long id) {
        User user = userRepository.getById(id);
        Set<Role> collect = Collections.emptySet();
        if (saveUserDto.getRole() != null) {
            collect = saveUserDto.getRole().stream().map(roleRepository::findByName).collect(Collectors.toSet());
        }
        user.setFirstName(saveUserDto.getFirstName());
        user.setLastName(saveUserDto.getLastName());
        user.setLogin(saveUserDto.getLogin());
        if (saveUserDto.getPass() != null)
            user.setPassword(saveUserDto.getPass());
        user.setEnabled(true);
        if (!collect.isEmpty())
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
    public Map<String, String> delete(Long aLong) {
        userRepository.deleteById(aLong);
        return Collections.singletonMap("status", "ok");
    }
}
