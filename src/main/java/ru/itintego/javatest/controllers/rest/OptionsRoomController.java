package ru.itintego.javatest.controllers.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itintego.javatest.models.OptionsRoom;
import ru.itintego.javatest.repositories.OptionsRoomRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/api/options_room")
public class OptionsRoomController implements DataController<OptionsRoom, Long> {
    private final OptionsRoomRepository optionsRoomRepository;

    public OptionsRoomController(OptionsRoomRepository optionsRoomRepository) {
        this.optionsRoomRepository = optionsRoomRepository;
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
}
