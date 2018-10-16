package com.tozhang.training.data.service;

import com.tozhang.training.data.entity.Guest;
import com.tozhang.training.data.entity.Room;

import java.util.Map;

public class RoomService {
    public static Room create(Map request) {
        Room room = new Room();
        room.setName(request.get("name").toString());
        room.setBedInfo(request.get("bedinfo").toString());
        room.setNumber(request.get("number").toString());
        return room;
    }
}
