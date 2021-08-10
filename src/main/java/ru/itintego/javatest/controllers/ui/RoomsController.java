package ru.itintego.javatest.controllers.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.itintego.javatest.dto.ReserveRoomDto;
import ru.itintego.javatest.models.Room;
import ru.itintego.javatest.repositories.ReserveRoomRepository;
import ru.itintego.javatest.services.RoomService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/rooms/")
public class RoomsController {
    private final RoomService roomService;
    private final ReserveRoomRepository reserveRoomRepository;

    public RoomsController(RoomService roomService, ReserveRoomRepository reserveRoomRepository) {
        this.roomService = roomService;
        this.reserveRoomRepository = reserveRoomRepository;
    }

    @RequestMapping("new")
    public ModelAndView newRoom() {
        ModelAndView modelAndView = new ModelAndView("rooms_new");
        return modelAndView;
    }

    @RequestMapping("{id}")
    public ModelAndView room(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("room");
        Room room = roomService.findById(id);
        List<ReserveRoomDto> roomDetail = reserveRoomRepository.findAllByRoom(room).stream().map(ReserveRoomDto::new).collect(Collectors.toList());
        modelAndView.addObject("room", room);
        modelAndView.addObject("roomDetail", roomDetail);
        return modelAndView;
    }

    @RequestMapping()
    public ModelAndView rooms() {
        ModelAndView modelAndView = new ModelAndView("rooms");
        List<Room> rooms = roomService.findAll();
        modelAndView.addObject("rooms", rooms);
        return modelAndView;
    }
}
