package ru.itintego.javatest.controllers.rest;

import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.itintego.javatest.dto.ReserveDto;
import ru.itintego.javatest.dto.SaveRoomDto;
import ru.itintego.javatest.models.OptionsRoom;
import ru.itintego.javatest.models.ReserveRoom;
import ru.itintego.javatest.models.Room;
import ru.itintego.javatest.models.User;
import ru.itintego.javatest.repositories.OptionsRoomRepository;
import ru.itintego.javatest.repositories.ReserveRoomRepository;
import ru.itintego.javatest.repositories.RoomRepository;
import ru.itintego.javatest.repositories.UserRepository;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rooms")
public class RoomController implements DataController<Room, Long> {
    private final RoomRepository roomRepository;
    private final ReserveRoomRepository reserveRoomRepository;
    private final OptionsRoomRepository optionsRoomRepository;
    private final UserRepository userRepository;
    private final Logger logger;

    public RoomController(RoomRepository roomRepository, ReserveRoomRepository reserveRoomRepository, OptionsRoomRepository optionsRoomRepository, UserRepository userRepository, Logger logger) {
        this.roomRepository = roomRepository;
        this.reserveRoomRepository = reserveRoomRepository;
        this.optionsRoomRepository = optionsRoomRepository;
        this.userRepository = userRepository;
        this.logger = logger;
    }

    @Override
    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    @Override
    public Room findById(Long aLong) {
        return roomRepository.getById(aLong);
    }

    @Override
    @Secured({"ROLE_MANAGER"})
    public Room save(Room room) {
        return roomRepository.save(room);
    }


    @Secured({"ROLE_MANAGER"})
    @PostMapping("/new")
    public Room save(@RequestBody SaveRoomDto saveRoomDto) {
        Set<OptionsRoom> optionsRoomSet = Collections.emptySet();
        if (saveRoomDto.getOptionsRoom() != null)
            optionsRoomSet = saveRoomDto.getOptionsRoom().stream().map(optionsRoomRepository::getById).collect(Collectors.toSet());
        logger.info("Saved room {}", saveRoomDto);
        Room room = new Room();
        room.setName(saveRoomDto.getName());
        room.setDescription(saveRoomDto.getDescription());
        room.setCountOfPlaces(saveRoomDto.getCountOfPlaces());
        room.setOptionsRooms(optionsRoomSet);
        roomRepository.save(room);
        logger.info("Save room to repository {}", room);
        Room save = roomRepository.getById(room.getId());
        logger.info("Save room to repository {}", save);
        return save;
    }

    @Secured({"ROLE_MANAGER"})
    @PostMapping("/{id}/edit")
    public Room edit(@RequestBody SaveRoomDto saveRoomDto, @PathVariable Long id) {
        logger.info("Saved room {}", saveRoomDto);
        Room edit = roomRepository.getById(id);
        edit.setName(saveRoomDto.getName());
        edit.setDescription(saveRoomDto.getDescription());
        edit.setCountOfPlaces(saveRoomDto.getCountOfPlaces());
        Room save = roomRepository.save(edit);
        logger.info("Save room to repository {}", edit);
        return save;
    }

    @PostMapping
    @RequestMapping(value = "/{id}/reserve")
    public ReserveRoom save(@PathVariable("id") Long id, @RequestBody ReserveDto reserveDto) {
        logger.info("reserve dto {}, id {}", reserveDto, id);
        Room byId = roomRepository.getById(id);
        LocalTime startTime = LocalTime.parse(reserveDto.getTimeStart());
        LocalTime endTime = LocalTime.parse(reserveDto.getTimeEnd());
        if (endTime.isBefore(startTime)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "End time is before start time");
        }
        LocalDateTime startDateTime = LocalDate.parse(reserveDto.getDate()).atTime(startTime);
        LocalDateTime endDateTime = LocalDate.parse(reserveDto.getDate()).atTime(endTime);
        Optional<User> activeUser = userRepository.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
        Long aLong = reserveRoomRepository.countAllByStartBetweenAndEndBetweenAndRoom(startDateTime, endDateTime, byId);
        if (aLong != 0) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Start date or end date is intersected");
        }
        ReserveRoom reserveRoom = new ReserveRoom();
        reserveRoom.setStart(startDateTime);
        reserveRoom.setEnd(endDateTime);
        reserveRoom.setRoom(byId);
        reserveRoom.setDescription(reserveDto.getDescription());
        reserveRoom.setUser(activeUser.orElseThrow(EntityNotFoundException::new));
        ReserveRoom save = reserveRoomRepository.save(reserveRoom);
        return save;
    }

    @Override
    @Secured({"ROLE_MANAGER"})
    public Room update(Long aLong, Room room) {
        if (roomRepository.existsById(aLong))
            return roomRepository.save(room);
        else throw new EntityNotFoundException();
    }


    @Transactional
    @Secured({"ROLE_MANAGER"})
    public Map<String, String> delete(Long aLong) {
        Room byId = roomRepository.getById(aLong);
        reserveRoomRepository.deleteAllByRoom(byId);
        roomRepository.deleteById(aLong);
        return Collections.singletonMap("status", "ok");
    }
}
