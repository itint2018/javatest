package ru.itintego.javatest.controllers.ui;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.itintego.javatest.models.Room;
import ru.itintego.javatest.repositories.RoomRepository;
import ru.itintego.javatest.services.RoomService;

import java.util.List;

@Controller
public class HomeController {

    private final RoomService roomService;
    private final Logger logger;

    @Autowired
    public HomeController(RoomService roomService, Logger logger) {
        this.roomService = roomService;
        this.logger = logger;
    }

    @RequestMapping("/")
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView("index");
        List<Room> rooms = roomService.findAll();
        modelAndView.addObject("rooms", rooms);
        logger.info("Getting from repo {}", rooms);
        return modelAndView;
    }
}
