package ru.itintego.javatest.controllers.ui;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.itintego.javatest.models.User;
import ru.itintego.javatest.repositories.UserRepository;

import java.util.List;

@Controller
@RequestMapping("/admin")
@Secured({"ROLE_ADMIN", "ROLE_MANAGER"})
public class AdminController {

    private final UserRepository userRepository;

    public AdminController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping()
    public ModelAndView adminHome() {
        ModelAndView modelAndView = new ModelAndView("admin");
        List<User> all = userRepository.findAll();
        modelAndView.addObject("users", all);
        return modelAndView;
    }

    @RequestMapping("/new")
    public ModelAndView addUser() {
        ModelAndView modelAndView = new ModelAndView("users_new");
        return modelAndView;
    }
}
