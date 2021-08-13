package ru.itintego.javatest.controllers.rest;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itintego.javatest.models.ReserveRoom;
import ru.itintego.javatest.models.User;
import ru.itintego.javatest.repositories.ReserveRoomRepository;
import ru.itintego.javatest.services.UserDetailsImpl;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/api/reserve_room")
public class ReserveRoomController implements DataController<ReserveRoom, Long> {
    private final ReserveRoomRepository reserveRoomRepository;


    public ReserveRoomController(ReserveRoomRepository reserveRoomRepository) {
        this.reserveRoomRepository = reserveRoomRepository;
    }

    @Override
    public List<ReserveRoom> findAll() {
        return reserveRoomRepository.findAll();
    }

    @Override
    public ReserveRoom findById(Long aLong) {
        return reserveRoomRepository.getById(aLong);
    }

    @GetMapping("{id}/proof")
    public ReserveRoom proof(@PathVariable Long id) {
        ReserveRoom byId = reserveRoomRepository.getById(id);
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDetails.getUser();
        byId.setProof(user);
        ReserveRoom save = reserveRoomRepository.save(byId);
        return save;
    }

    @Override
    public ReserveRoom save(ReserveRoom reserveRoom) {
        return reserveRoomRepository.save(reserveRoom);
    }

    @Override
    public ReserveRoom update(Long aLong, ReserveRoom reserveRoom) {
        if (reserveRoomRepository.existsById(aLong))
            return reserveRoomRepository.save(reserveRoom);
        else throw new EntityNotFoundException();
    }

    @Override
    public void delete(Long aLong) {
        reserveRoomRepository.deleteById(aLong);
    }
}
