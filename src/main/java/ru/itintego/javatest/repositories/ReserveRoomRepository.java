package ru.itintego.javatest.repositories;

import ru.itintego.javatest.models.ReserveRoom;
import ru.itintego.javatest.models.Room;

import java.util.List;

public interface ReserveRoomRepository extends SuperRepository<ReserveRoom> {
    List<ReserveRoom> findAllByRoom(Room room);
}