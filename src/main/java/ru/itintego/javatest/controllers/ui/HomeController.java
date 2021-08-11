package ru.itintego.javatest.controllers.ui;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.itintego.javatest.repositories.ReserveRoomRepository;
import ru.itintego.javatest.repositories.RoomRepository;
import ru.itintego.javatest.repositories.UserRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class HomeController {

    private final SavedRequestAwareAuthenticationSuccessHandler handler = new SavedRequestAwareAuthenticationSuccessHandler();

    private final RoomRepository roomRepository;
    private final ReserveRoomRepository reserveRoomRepository;
    private final UserRepository userRepository;
    private final Logger logger;

    @Autowired
    public HomeController(RoomRepository roomRepository, ReserveRoomRepository reserveRoomRepository, UserRepository userRepository, Logger logger) {
        this.roomRepository = roomRepository;
        this.reserveRoomRepository = reserveRoomRepository;
        this.userRepository = userRepository;
        this.logger = logger;
    }

    @RequestMapping
    public String home() {
        return "redirect:/rooms";
    }

    @GetMapping("/auth")
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.addObject("header", "Войти");
        return modelAndView;
    }

    @PostMapping("/login_page")
    public void performLogin(HttpServletRequest request, HttpServletResponse response) {

    }
}
