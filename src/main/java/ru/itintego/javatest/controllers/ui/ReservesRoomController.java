package ru.itintego.javatest.controllers.ui;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.itintego.javatest.models.ReserveRoom;
import ru.itintego.javatest.repositories.ReserveRoomRepository;

import javax.servlet.http.HttpServletRequest;

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
    public ModelAndView toRoom(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) {
        httpServletRequest.setAttribute("parent", "/reserve_room");
        ReserveRoom byId = reserveRoomRepository.getById(id);
        return new ModelAndView("forward:/rooms/" + byId.getRoom().getId() + "/" + byId.getId());

    }


    @RequestMapping()
    public String home(Model model, HttpServletRequest httpServletRequest) {
        if (httpServletRequest.isUserInRole("ROLE_MANAGER")) {
            return "reserve_room_list";
        } else {
            return "redirect:/rooms";
        }

    }
}
