package com.tozhang.training.data.repository;

import com.tozhang.training.data.entity.Guest;
import com.tozhang.training.data.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room,Long> {
    Room findByNumber(String number);
    List<Room> findByName(String name);

}
