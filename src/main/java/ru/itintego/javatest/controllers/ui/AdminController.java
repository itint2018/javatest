package ru.itintego.javatest.controllers.ui;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.itintego.javatest.models.Role;
import ru.itintego.javatest.models.User;
import ru.itintego.javatest.repositories.RoleRepository;
import ru.itintego.javatest.repositories.UserRepository;

import java.util.List;

@Controller
@RequestMapping({"/admin", "/users"})

public class AdminController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public AdminController(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Secured({"ROLE_ADMIN", "ROLE_MANAGER"})
    @RequestMapping()
    public ModelAndView adminHome() {
        ModelAndView modelAndView = new ModelAndView("admin");
        List<User> all = userRepository.findAll();
        modelAndView.addObject("users", all);
        return modelAndView;
    }

    @Secured({"ROLE_ADMIN", "ROLE_MANAGER"})
    @RequestMapping("/new")
    public ModelAndView addUser() {
        ModelAndView modelAndView = new ModelAndView("users_new");
        List<Role> all = roleRepository.findAll();
        modelAndView.addObject("roles", all);
        return modelAndView;
    }


    @Secured({"ROLE_ADMIN", "ROLE_MANAGER"})
    @RequestMapping("/{id}")
    public ModelAndView userEdit(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("users_edit");
        List<Role> all = roleRepository.findAll();
        User byId = userRepository.getById(id);
        modelAndView.addObject("role", all);
        modelAndView.addObject("user", byId);
        return modelAndView;
    }

}
