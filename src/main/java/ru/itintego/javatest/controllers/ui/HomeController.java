package ru.itintego.javatest.controllers.ui;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.itintego.javatest.repositories.IconRepository;
import ru.itintego.javatest.repositories.ReserveRoomRepository;
import ru.itintego.javatest.repositories.RoomRepository;
import ru.itintego.javatest.repositories.UserRepository;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@Controller
public class HomeController {

    private final SavedRequestAwareAuthenticationSuccessHandler handler = new SavedRequestAwareAuthenticationSuccessHandler();

    private final RoomRepository roomRepository;
    private final ReserveRoomRepository reserveRoomRepository;
    private final UserRepository userRepository;
    private final IconRepository iconRepository;
    private final Logger logger;

    @Autowired
    public HomeController(RoomRepository roomRepository, ReserveRoomRepository reserveRoomRepository, UserRepository userRepository, IconRepository iconRepository, Logger logger) {
        this.roomRepository = roomRepository;
        this.reserveRoomRepository = reserveRoomRepository;
        this.userRepository = userRepository;
        this.iconRepository = iconRepository;
        this.logger = logger;
    }

    @RequestMapping
    public String home(HttpServletRequest request) {
        if (request.isUserInRole("EMPLOYEE")) {
            return "redirect:/rooms";
        } else if (request.isUserInRole("MANAGER")) {
            return "redirect:/rooms";
        }
        if (request.isUserInRole("ADMIN")) {
            return "redirect:/admin";
        } else {
            return "redirect:/error";
        }

    }

    @GetMapping("/auth")
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.addObject("header", "Войти");
        return modelAndView;
    }

    @RequestMapping("/logout")
    public String logout(HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) {
        Cookie[] cookies = httpServletRequest.getCookies();
        Cookie cookie1 = Arrays.stream(cookies).filter(cookie -> cookie.getName().equals("idSession")).findFirst().orElseThrow();
        cookie1.setMaxAge(-1);
        cookie1.setValue("");
        httpServletResponse.addCookie(cookie1);
        return "redirect:/auth";
    }
}
