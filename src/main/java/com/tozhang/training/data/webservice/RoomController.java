package com.tozhang.training.data.webservice;

import com.tozhang.training.data.entity.Guest;
import com.tozhang.training.data.entity.Room;
import com.tozhang.training.data.repository.RoomRepository;
import com.tozhang.training.data.service.GuestService;
import com.tozhang.training.data.service.RoomService;
import com.tozhang.training.data.service.SmsSender;
import com.tozhang.training.util.Output;
import com.tozhang.training.util.ServiceRuntimeException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.ws.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("api")
public class RoomController {
    private static final Logger logger = Logger.getLogger(RoomController.class);

    @Autowired
    private RoomRepository roomRepository;

    @GetMapping(value = "/rooms")
    public ResponseEntity<Object> getAllRooms() {
        List<Room> ls_room = roomRepository.findAll();
        return new Output().Correct(HttpStatus.OK,ls_room,"Successful");
    }

//    @GetMapping(value = "/rooms/{name}")
//    public ResponseEntity<Object> getroomByName(@PathVariable(value = "name") String name){
//        List<Room> ls_room = roomRepository.findByName(name);
//        if (ls_room != null)
//            return new Output().Correct(HttpStatus.OK,ls_room,"Successful");
//        else
//            return new Output().Wrong(HttpStatus.NOT_FOUND,"room not found");
//    }

    @GetMapping(value = "/rooms",params = "name")
    //@ResponseBody
    public ResponseEntity<Object> getroomByNameParam(@RequestParam(value = "name") String name){
        List<Room> ls_room = roomRepository.findByName(name);
        if (ls_room != null)
            return new Output().Correct(HttpStatus.OK,ls_room,"Successful");
        else
            return new Output().Wrong(HttpStatus.NOT_FOUND,"room not found");
    }
    @GetMapping(value = "/room",params = "number")
    //@ResponseBody
    public ResponseEntity<Object> getroomByNumberParam(@RequestParam(value = "number") String name){
        Room ls_room = roomRepository.findByNumber(name);
        if (ls_room != null)
            return new Output().Correct(HttpStatus.OK,ls_room,"Successful");
        else
            return new Output().Wrong(HttpStatus.NOT_FOUND,"room not found");
    }

    @GetMapping(value = "/room",params = "id")
    //@ResponseBody
    public ResponseEntity<Object> getroomByIdParam(@RequestParam(value = "id") Long id){
        Room ls_room = roomRepository.findOne(id);
        if (ls_room != null)
            return new Output().Correct(HttpStatus.OK,ls_room,"Successful");
        else
            throw new ServiceRuntimeException("Room by id "+id +" is not found");
    }

    @PostMapping(value = "/room")
    public Object createRoom(@Valid @RequestBody Map<String,String> payload){
        logger.info("Received POST request");
        HashMap<String,String> request = new HashMap<>(payload);
        Room newRoom = RoomService.create(request);

        roomRepository.save(newRoom);
        logger.info("guest successfully added");
        return new Output().Correct(HttpStatus.OK,newRoom,"successfully added");
    }
    @PutMapping("/rooms/{id}")
    public ResponseEntity updateRoom(@PathVariable(value = "id") Long roomId,
                                     @Valid @RequestBody Room room) {

        Room updateroom = roomRepository.findOne(roomId);

        if(updateroom==null) {
            logger.info("User not exist");
            throw new ServiceRuntimeException("Room not found");
        }
        else{
            updateroom.setName(room.getName());
            updateroom.setBedInfo(room.getBedInfo());
            updateroom.setNumber(room.getNumber());
            return new Output().Correct(HttpStatus.OK,updateroom,"successfully");
        }
    }

    //delete a guest
    @DeleteMapping("/rooms/{id}")
    public ResponseEntity<?> deleteRoom(@PathVariable(value = "id") Long noteId) {
        Room room = roomRepository.findOne(noteId);

        if (room == null) return new Output().Wrong(HttpStatus.BAD_REQUEST,"fail");
        else {
            roomRepository.delete(room);
            return new Output().Correct(HttpStatus.OK,"successful");
        }
    }
}
