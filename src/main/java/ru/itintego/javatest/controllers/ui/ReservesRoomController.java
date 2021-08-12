package ru.itintego.javatest.controllers.ui;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.itintego.javatest.dto.ReserveDto;
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
    public ModelAndView reserveRoom(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("reserve_room_edit");
        ReserveDto reserveDto = new ReserveDto(reserveRoomRepository.getById(id));
        modelAndView.addObject("reserveRoom", reserveDto);
        modelAndView.addObject("header", "Мероприятие " + reserveDto.getDescription());
        return modelAndView;
    }

    @RequestMapping()
    public String home() {
        return "redirect:/rooms";
    }
}
