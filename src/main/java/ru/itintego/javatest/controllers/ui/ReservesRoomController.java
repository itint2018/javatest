package ru.itintego.javatest.controllers.ui;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itintego.javatest.models.ReserveRoom;
import ru.itintego.javatest.repositories.ReserveRoomRepository;

@Controller
@RequestMapping("/reserve_room")
public class ReservesRoomController {

    private final ReserveRoomRepository reserveRoomRepository;
    private final Logger logger;

    public ReservesRoomController(ReserveRoomRepository reserveRoomRepository, Logger logger) {
        this.reserveRoomRepository = reserveRoomRepository;
        this.logger = logger;
    }


    @RequestMapping("/{id}")
    public String toRoom(@PathVariable("id") Long id) {
        ReserveRoom byId = reserveRoomRepository.getById(id);
        return "/rooms/" + byId.getRoom().getId();

    }


    @RequestMapping()
    public String home() {
        return "redirect:/rooms";
    }
}
