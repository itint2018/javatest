package ru.itintego.javatest.controllers.rest;

import org.slf4j.Logger;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.itintego.javatest.dto.ReserveDto;
import ru.itintego.javatest.models.ReserveRoom;
import ru.itintego.javatest.models.Room;
import ru.itintego.javatest.models.User;
import ru.itintego.javatest.repositories.ReserveRoomRepository;
import ru.itintego.javatest.repositories.RoomRepository;
import ru.itintego.javatest.repositories.UserRepository;
import ru.itintego.javatest.services.UserDetailsImpl;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/reserve_room")
public class ReserveRoomController implements DataController<ReserveRoom, Long> {
    private final ReserveRoomRepository reserveRoomRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final Logger logger;


    public ReserveRoomController(ReserveRoomRepository reserveRoomRepository, RoomRepository roomRepository, UserRepository userRepository, Logger logger) {
        this.reserveRoomRepository = reserveRoomRepository;
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
        this.logger = logger;
    }

    @Override
    public List<ReserveRoom> findAll() {
        return reserveRoomRepository.findAll(Sort.by(Sort.Direction.DESC, "start"));
    }

    @GetMapping("/unproofed")
    public List<ReserveRoom> findAllByStart() {
        return reserveRoomRepository.findAllOrderByStart(LocalDateTime.now());
    }

    @Override
    public ReserveRoom findById(Long aLong) {
        return reserveRoomRepository.getById(aLong);
    }

    @Secured("ROLE_MANAGER")
    @GetMapping("{id}/proof")
    public ReserveRoom proof(@PathVariable Long id) {
        ReserveRoom byId = reserveRoomRepository.getById(id);
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDetails.getUser();
        byId.setProof(user);
        ReserveRoom save = reserveRoomRepository.save(byId);
        return save;
    }

    @PostMapping
    @RequestMapping(value = "/{id}/edit")
    public ReserveRoom save(@PathVariable("id") Long id, @RequestBody ReserveDto reserveDto) {
        ReserveRoom reserveRoom = reserveRoomRepository.getById(id);
        Room byId = reserveRoom.getRoom();
        LocalTime startTime = LocalTime.parse(reserveDto.getTimeStart());
        LocalTime endTime = LocalTime.parse(reserveDto.getTimeEnd());
        if (endTime.isBefore(startTime)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "End time is before start time");
        }
        LocalDateTime startDateTime = LocalDate.parse(reserveDto.getDate()).atTime(startTime);
        LocalDateTime endDateTime = LocalDate.parse(reserveDto.getDate()).atTime(endTime);
        Optional<User> activeUser = userRepository.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
        reserveRoom.setStart(startDateTime);
        reserveRoom.setEnd(endDateTime);
        reserveRoom.setDescription(reserveDto.getDescription());
        reserveRoom.setUser(activeUser.orElseThrow(EntityNotFoundException::new));
        ReserveRoom save = reserveRoomRepository.save(reserveRoom);
        return save;
    }

    @Secured("ROLE_MANAGER")
    @Override
    public ReserveRoom save(ReserveRoom reserveRoom) {
        return reserveRoomRepository.save(reserveRoom);
    }

    @Override
    @Secured("ROLE_MANAGER")
    public ReserveRoom update(Long aLong, ReserveRoom reserveRoom) {
        if (reserveRoomRepository.existsById(aLong))
            return reserveRoomRepository.save(reserveRoom);
        else throw new EntityNotFoundException();
    }

    @Override
    @Secured("ROLE_MANAGER")
    public Map<String, String> delete(Long aLong) {
        reserveRoomRepository.deleteById(aLong);
        return Collections.singletonMap("status", "ok");
    }
}
