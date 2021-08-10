package ru.itintego.javatest.repositories;

import org.springframework.data.jpa.repository.Query;
import ru.itintego.javatest.models.OptionsRoom;
import ru.itintego.javatest.models.Room;

import java.util.List;

public interface OptionsRoomRepository extends SuperRepository<OptionsRoom> {

    List<OptionsRoom> findAllByRooms(Room room);
}