package ru.itintego.javatest.controllers.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.itintego.javatest.models.Room;
import ru.itintego.javatest.services.RoomService;

@Controller("/rooms")
public class RoomsController {
    private final RoomService roomService;

    public RoomsController(RoomService roomService) {
        this.roomService = roomService;
    }

    @RequestMapping("/new")
    public ModelAndView newRoom() {
        ModelAndView modelAndView = new ModelAndView("rooms_new");
        return modelAndView;
    }

    @RequestMapping("{id}")
    public ModelAndView rooms(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("room");
        Room room = roomService.findById(id);
        modelAndView.addObject("room", room);
        return modelAndView;
    }
}
