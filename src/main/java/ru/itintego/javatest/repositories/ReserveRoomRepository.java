package ru.itintego.javatest.repositories;

import org.springframework.data.jpa.repository.Query;
import ru.itintego.javatest.models.ReserveRoom;
import ru.itintego.javatest.models.Room;

import java.util.List;

public interface ReserveRoomRepository extends SuperRepository<ReserveRoom> {
    List<ReserveRoom> findAllByRoom(Room room);

    @Query("select count(r) from ReserveRoom r where r.proof.id = 0 and r.room = ?1 and r.start > current_timestamp ")
    Long countAllUnproofedRooms(Room room);

    @Query("select count(r) from ReserveRoom r where r.proof.id <> 0 and r.room = ?1 and r.start > current_timestamp")
    Long countAllByRoom(Room room);

    @Query("select r from ReserveRoom r where r.room = ?1 and r.start < current_date and r.end > current_timestamp ")
    ReserveRoom findByRoomAndAndStartIsAfter(Room room);
}