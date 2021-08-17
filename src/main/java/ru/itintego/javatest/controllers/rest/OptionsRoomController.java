package ru.itintego.javatest.controllers.rest;

import org.springframework.web.bind.annotation.*;
import ru.itintego.javatest.dto.OptionsRoomDto;
import ru.itintego.javatest.models.OptionsRoom;
import ru.itintego.javatest.repositories.OptionsRoomRepository;
import ru.itintego.javatest.repositories.RoomRepository;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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


    @PostMapping("/new")
    public OptionsRoom save(@RequestBody @Valid OptionsRoomDto optionsRoomDto) {
        OptionsRoom optionsRoom = new OptionsRoom();
        optionsRoom.setName(optionsRoomDto.getName());
        optionsRoom.setIcon(optionsRoomDto.getIcon());
        return optionsRoomRepository.save(optionsRoom);
    }

    @Override
    public OptionsRoom update(Long aLong, OptionsRoom optionsRoom) {
        if (optionsRoomRepository.existsById(aLong))
            return optionsRoomRepository.save(optionsRoom);
        else throw new EntityNotFoundException();
    }

    @Override
    public Map<String, String> delete(Long aLong) {
        optionsRoomRepository.deleteById(aLong);
        return Collections.singletonMap("status", "ok");
    }

    @GetMapping("/room/{id}")
    public List<OptionsRoom> getOptionsRoomWhereRoomId(@PathVariable("id") Long id) {
        return optionsRoomRepository.findAllByRooms(roomRepository.getById(id));
    }
}
