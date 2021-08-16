package ru.itintego.javatest.controllers.ui;

import org.slf4j.Logger;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.itintego.javatest.dto.IndexRoomDto;
import ru.itintego.javatest.dto.ReserveDto;
import ru.itintego.javatest.dto.ReserveRoomDto;
import ru.itintego.javatest.models.OptionsRoom;
import ru.itintego.javatest.models.ReserveRoom;
import ru.itintego.javatest.models.Room;
import ru.itintego.javatest.repositories.OptionsRoomRepository;
import ru.itintego.javatest.repositories.ReserveRoomRepository;
import ru.itintego.javatest.repositories.RoomRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/rooms")
@Secured({"ROLE_MANAGER", "ROLE_EMPLOYEE"})
public class RoomsController {
    private final RoomRepository roomRepository;
    private final ReserveRoomRepository reserveRoomRepository;
    private final OptionsRoomRepository optionsRoomRepository;
    private final Logger logger;

    public RoomsController(RoomRepository roomRepository, ReserveRoomRepository reserveRoomRepository, OptionsRoomRepository optionsRoomRepository, Logger logger) {
        this.roomRepository = roomRepository;
        this.reserveRoomRepository = reserveRoomRepository;
        this.optionsRoomRepository = optionsRoomRepository;
        this.logger = logger;
    }

    @Secured({"ROLE_MANAGER"})
    @RequestMapping("/new")
    public ModelAndView newRoom() {
        ModelAndView modelAndView = new ModelAndView("rooms_new");
        modelAndView.addObject("header", "Добавить комнату");
        List<OptionsRoom> all = optionsRoomRepository.findAll();
        modelAndView.addObject("optionsRoom", all);
        return modelAndView;
    }

    @RequestMapping("/{id}")
    public ModelAndView room(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("room");
        Room room = roomRepository.getById(id);
        IndexRoomDto indexRoomDto = new IndexRoomDto(room);
        fillingIndexRoomDto(indexRoomDto);
        List<ReserveRoomDto> roomDetail = reserveRoomRepository.findAllByRoom(room).stream()
                .sorted(Comparator.comparing(ReserveRoom::getStart))
                .filter(reserveRoom1 -> reserveRoom1.getStart().isAfter(LocalDateTime.now()))
                .map(ReserveRoomDto::new)
                .collect(Collectors.toList());
        modelAndView.addObject("room", indexRoomDto);
        modelAndView.addObject("roomDetail", roomDetail);
        modelAndView.addObject("header", room.getName());
        return modelAndView;
    }

    @Secured({"ROLE_MANAGER"})
    @RequestMapping("/{id}/edit")
    public ModelAndView editRoom(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("room_edit");
        Room byId = roomRepository.getById(id);
        modelAndView.addObject("header", "Изменить");
        modelAndView.addObject("room", byId);
        List<OptionsRoom> all = optionsRoomRepository.findAll();
        modelAndView.addObject("optionsRoom", all);
        return modelAndView;
    }

    @RequestMapping()
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView("index");
        List<IndexRoomDto> rooms = roomRepository.findAll().stream().map(IndexRoomDto::new).collect(Collectors.toList());
        rooms.forEach(this::fillingIndexRoomDto);
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

    @RequestMapping("/{roomId}/{reserveId}")
    public ModelAndView reserveRoom(@PathVariable("roomId") Long roomId, @PathVariable("reserveId") Long reserveId) {
        ModelAndView modelAndView = new ModelAndView("reserve_room_edit");
        ReserveDto reserveDto = new ReserveDto(reserveRoomRepository.getById(reserveId));
        modelAndView.addObject("reserveRoom", reserveDto);
        modelAndView.addObject("header", "Мероприятие " + reserveDto.getDescription());
        return modelAndView;
    }

    private void fillingIndexRoomDto(IndexRoomDto room) {
        Room byId = roomRepository.getById(room.getId());
        room.setCountOfUnproofedReserve(reserveRoomRepository.countAllUnproofedRooms(byId, LocalDateTime.now()));
        room.setCountOfProofedReserveToday(reserveRoomRepository.countAllByRoom(byId, LocalDateTime.now()));
        ReserveRoom byRoomAndAndStartIsAfter = reserveRoomRepository.findByRoomAndAndStartIsAfter(byId, LocalDateTime.now());
        if (byRoomAndAndStartIsAfter != null) {
            room.setFromTimeToTime(byRoomAndAndStartIsAfter.getStart().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT))
                    + "-" + byRoomAndAndStartIsAfter.getEnd().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)));
            room.setClear(false);
        } else {
            room.setClear(true);
        }
    }
}
