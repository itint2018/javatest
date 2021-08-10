package ru.itintego.javatest.controllers.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itintego.javatest.models.OptionsRoom;
import ru.itintego.javatest.repositories.OptionsRoomRepository;
import ru.itintego.javatest.repositories.RoomRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/api/options_room")
public class OptionsRoomController implements DataController<OptionsRoom, Long> {
    private final OptionsRoomRepository optionsRoomRepository;
    private final RoomRepository roomRepository;

    public OptionsRoomController(OptionsRoomRepository optionsRoomRepository, RoomRepository roomRepository) {
        this.optionsRoomRepository = optionsRoomRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public List<OptionsRoom> findAll() {
        return optionsRoomRepository.findAll();
    }

    @Override
    public OptionsRoom findById(Long aLong) {
        return optionsRoomRepository.getById(aLong);
    }

    @Override
    public OptionsRoom save(OptionsRoom optionsRoom) {
        return optionsRoomRepository.save(optionsRoom);
    }

    @Override
    public OptionsRoom update(Long aLong, OptionsRoom optionsRoom) {
        if (optionsRoomRepository.existsById(aLong))
            return optionsRoomRepository.save(optionsRoom);
        else throw new EntityNotFoundException();
    }

    @Override
    public void delete(Long aLong) {
        optionsRoomRepository.deleteById(aLong);
    }

    @GetMapping("/room/{id}")
    public List<OptionsRoom> getOptionsRoomWhereRoomId(@PathVariable("id") Long id) {
        return optionsRoomRepository.findAllByRooms(roomRepository.getById(id));
    }
}
