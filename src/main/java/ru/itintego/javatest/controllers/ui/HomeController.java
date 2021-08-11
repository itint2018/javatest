package ru.itintego.javatest.controllers.ui;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.itintego.javatest.dto.IndexRoomDto;
import ru.itintego.javatest.models.ReserveRoom;
import ru.itintego.javatest.models.Room;
import ru.itintego.javatest.repositories.ReserveRoomRepository;
import ru.itintego.javatest.repositories.RoomRepository;
import ru.itintego.javatest.repositories.UserRepository;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {

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

    @RequestMapping("/")
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView("index");
        List<IndexRoomDto> rooms = roomRepository.findAll().stream().map(IndexRoomDto::new).collect(Collectors.toList());
        rooms.forEach(room -> {
            Room byId = roomRepository.getById(room.getId());
            room.setCountOfUnproofedReserve(reserveRoomRepository.countAllUnproofedRooms(byId));
            room.setCountOfProofedReserveToday(reserveRoomRepository.countAllByRoom(byId));
            ReserveRoom byRoomAndAndStartIsAfter = reserveRoomRepository.findByRoomAndAndStartIsAfter(byId);
            if (byRoomAndAndStartIsAfter != null) {
                room.setFromTimeToTime(byRoomAndAndStartIsAfter.getStart().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT))
                        + "-" + byRoomAndAndStartIsAfter.getEnd().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)));
                room.setClear(false);
            } else {
                room.setClear(true);
            }
        });
        modelAndView.addObject("rooms", rooms);
        modelAndView.addObject("header", "Комнаты");
        return modelAndView;
    }

    @RequestMapping("/login")
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView("login");
        return modelAndView;
    }
}
