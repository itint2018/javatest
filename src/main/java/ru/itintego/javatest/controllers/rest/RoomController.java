package ru.itintego.javatest.controllers.rest;

import org.slf4j.Logger;
import org.springframework.web.bind.annotation.*;
import ru.itintego.javatest.dto.ReserveDto;
import ru.itintego.javatest.models.ReserveRoom;
import ru.itintego.javatest.models.Room;
import ru.itintego.javatest.repositories.RoomRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController implements DataController<Room, Long> {
    private final RoomRepository roomRepository;
    private final Logger logger;

    public RoomController(RoomRepository roomRepository, Logger logger) {
        this.roomRepository = roomRepository;
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
    public Room save(Room room) {
        return roomRepository.save(room);
    }

    @RequestMapping(value = "/{id}/reserve/", method = RequestMethod.POST)
    public ReserveRoom save(@PathVariable("id") Integer id, @RequestBody ReserveDto reserveDto) {
        logger.info("reserve dto {}, id {}", reserveDto, id);
        return new ReserveRoom();
    }

    @Override
    public Room update(Long aLong, Room room) {
        if (roomRepository.existsById(aLong))
            return roomRepository.save(room);
        else throw new EntityNotFoundException();
    }

    @Override
    public void delete(Long aLong) {
        roomRepository.deleteById(aLong);
    }
}
