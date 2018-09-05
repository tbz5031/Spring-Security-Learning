package com.tozhang.training.data.webservice;

import com.tozhang.training.data.entity.Room;
import com.tozhang.training.data.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RoomController {
    @Autowired
    private RoomRepository repository;

    @RequestMapping(value="/rooms", method= RequestMethod.GET)
    List<Room> findAll(@RequestParam(required=false) String roomNumber){
       List<Room> rooms = new ArrayList<>();
        if(null==roomNumber){
            Iterable<Room> results = this.repository.findAll();
            results.forEach(room-> {rooms.add(room);});
        }else{
            Room room = this.repository.findByNumber(roomNumber);
            if(null!=room) {
                rooms.add(room);
            }
        }
        return rooms;
    }

//    @RequestMapping(value="/room/{roomNumber}",method = RequestMethod.GET)
//    Room findbyID(@RequestParam String roomNumber){
//        Room room = new Room();
//        room = this.repository.findByNumber(roomNumber);
//        return room;
//    }


}
