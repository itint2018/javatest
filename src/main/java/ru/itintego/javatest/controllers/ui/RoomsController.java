package ru.itintego.javatest.controllers.ui;

import org.slf4j.Logger;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.itintego.javatest.dto.IndexRoomDto;
import ru.itintego.javatest.dto.ReserveRoomDto;
import ru.itintego.javatest.models.ReserveRoom;
import ru.itintego.javatest.models.Room;
import ru.itintego.javatest.repositories.ReserveRoomRepository;
import ru.itintego.javatest.repositories.RoomRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/rooms")
public class RoomsController {
    private final RoomRepository roomRepository;
    private final ReserveRoomRepository reserveRoomRepository;
    private final Logger logger;

    public RoomsController(RoomRepository roomRepository, ReserveRoomRepository reserveRoomRepository, Logger logger) {
        this.roomRepository = roomRepository;
        this.reserveRoomRepository = reserveRoomRepository;
        this.logger = logger;
    }

    @Secured({"ROLE_ADMIN", "ROLE_MANAGER"})
    @RequestMapping("/new")
    public ModelAndView newRoom() {
        ModelAndView modelAndView = new ModelAndView("rooms_new");
        modelAndView.addObject("header", "Добавить комнату");
        return modelAndView;
    }

    @RequestMapping("/{id}")
    public ModelAndView room(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("room");
        Room room = roomRepository.getById(id);
        IndexRoomDto indexRoomDto = new IndexRoomDto(room);
        List<ReserveRoomDto> roomDetail = reserveRoomRepository.findAllByRoom(room).stream().map(ReserveRoomDto::new).collect(Collectors.toList());
        modelAndView.addObject("room", indexRoomDto);
        modelAndView.addObject("roomDetail", roomDetail);
        modelAndView.addObject("header", room.getName());
        return modelAndView;
    }

    @RequestMapping()
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

    @RequestMapping("/{id}/reserve")
    public ModelAndView reserveRoom(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("reserve_room");
        modelAndView.addObject("header", "Зарезервировать комнату");
        modelAndView.addObject("minDate", LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE));
        modelAndView.addObject("minTime", LocalTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME));
        return modelAndView;
    }
}
