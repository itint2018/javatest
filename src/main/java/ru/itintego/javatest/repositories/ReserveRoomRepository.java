package ru.itintego.javatest.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.itintego.javatest.models.ReserveRoom;
import ru.itintego.javatest.models.Room;

import java.time.LocalDateTime;
import java.util.List;

public interface ReserveRoomRepository extends SuperRepository<ReserveRoom> {
    List<ReserveRoom> findAllByRoom(Room room);

    @Query("select r from ReserveRoom r where r.start > ?1 order by r.start")
    List<ReserveRoom> findAllOrderByStart(LocalDateTime localDateTime);

    @Query("select count(r) from ReserveRoom r where (r.proof.id = 0 or r.proof is null) and r.room = ?1 and r.start > ?2")
    Long countAllUnproofedRooms(Room room, LocalDateTime start);

    @Query("select count(r) from ReserveRoom r where (r.proof.id = 0 or r.proof is not null) and r.room = ?1 and r.start > ?2")
    Long countAllByRoom(Room room, LocalDateTime start);

    @Query("select r from ReserveRoom r where r.room = ?1 and r.start <= ?2 and r.end >= ?2 and r.proof is not null")
    ReserveRoom findByRoomAndAndStartIsAfter(Room room, LocalDateTime localDateTime);

    @Query("select count(r) from ReserveRoom r where (r.start >= ?1 and r.start < ?2 or r.end > ?1 and r.end <= ?2) and r.room = ?3")
    Long countAllByStartBetweenAndEndBetweenAndRoom(LocalDateTime start, LocalDateTime end, Room room);


    @Modifying
    @Query("delete from ReserveRoom r where r.room = ?1")
    void deleteAllByRoom(Room room);
}