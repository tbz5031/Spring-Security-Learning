package com.tozhang.training.data.service;

import com.tozhang.training.data.entity.Room;
import com.tozhang.training.util.Constant;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RoomService {

    public Room create(Map payload) {
        Room room = new Room();
        room.setName(payload.get(Constant.Param.roomName).toString());
        room.setBedInfo(payload.get(Constant.Param.bedInfo).toString());
        room.setNumber(payload.get(Constant.Param.roomNum).toString());
        room.setStatus(Constant.Param.roomReady);
        return room;
    }

    public Room update(Map payload, Room room){
        if(payload.containsKey(Constant.Param.roomName)) room.setName(payload.get(Constant.Param.roomName).toString());
        if(payload.containsKey(Constant.Param.bedInfo)) room.setBedInfo(payload.get(Constant.Param.bedInfo).toString());
        if(payload.containsKey(Constant.Param.roomNum)) room.setNumber(payload.get(Constant.Param.roomNum).toString());
        if(payload.containsKey(Constant.Param.roomStatus)) room.setNumber(payload.get(Constant.Param.roomStatus).toString());
        return room;
    }
}
