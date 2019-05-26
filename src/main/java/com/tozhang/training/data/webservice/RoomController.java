package com.tozhang.training.data.webservice;

import com.tozhang.training.data.entity.Room;
import com.tozhang.training.data.repository.RoomRepository;
import com.tozhang.training.data.security.JWTService;
import com.tozhang.training.data.security.SecurityConstants;
import com.tozhang.training.data.service.RoomService;

import com.tozhang.training.util.Constant;
import com.tozhang.training.util.IDMResponse;
import com.tozhang.training.util.ServiceRuntimeException;
import com.tozhang.training.util.UtilTools;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/room")
public class RoomController {
    private static final Logger logger = LogManager.getLogger(RoomController.class);

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    RoomService roomService = new RoomService();

    @Autowired
    JWTService jwtService = new JWTService();

    @PostMapping(value = "/create")
    public Object createRoom(@Valid @RequestBody Map<String,String> payload,
                             @RequestHeader Map<String,String> header){
        logger.info("Received creating room request");
        HashMap<String,String> request = new HashMap<>(payload);
        UtilTools utilTools = new UtilTools();
        if(utilTools.checkParameters(request, Constant.RequiredParams.postRoom)){
            if(jwtService.jwtValidator(header,request, SecurityConstants.AdminSECRET)){
                logger.info("Authentication passed");
                if(roomRepository.findByNumber(request.get(Constant.Param.roomNum))==null){
                    Room room = roomService.create(request);
                    roomRepository.save(room);
                    return new IDMResponse().Correct(HttpStatus.OK,room,"New Room is Successfully added ");
                }else{
                    return new IDMResponse().Wrong(HttpStatus.BAD_REQUEST,"this room is already exists");
                }

            }else{
                return new IDMResponse().Wrong(HttpStatus.BAD_REQUEST,"Invalid Token");
            }

        }else{
            return new IDMResponse().Wrong(HttpStatus.BAD_REQUEST,"Missing Parameters");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateRoom(@PathVariable(value = "id") String roomNum,
                                     @Valid @RequestBody Map<String,String> payload,
                                     @RequestHeader Map<String,String> header) {
        logger.info("Start to update room");
        HashMap<String, String> request = new HashMap<>(payload);
        UtilTools utilTools = new UtilTools();
        if (utilTools.checkParameters(request, Constant.RequiredParams.updateRoom)) {
            if (jwtService.jwtValidator(header, request, SecurityConstants.AdminSECRET)) {
                logger.info("Authentication passed");
                try {
                    Room updateroom = roomRepository.findByNumber(roomNum);
                    roomService.update(payload, updateroom);
                    roomRepository.save(updateroom);
                    return new IDMResponse().Correct(HttpStatus.OK, updateroom, "Room Successfully updated");
                } catch (NullPointerException ne) {
                    return new IDMResponse().Correct(HttpStatus.BAD_REQUEST, "could not find room :" + roomNum);
                }
            } else {
                return new IDMResponse().Wrong(HttpStatus.BAD_REQUEST, "Invalid Token");
            }
        } else {
            return new IDMResponse().Wrong(HttpStatus.BAD_REQUEST, "Missing Parameters");
        }
    }


    //delete a room
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRoom(@PathVariable(value = "id") String roomNum,
                                        @Valid @RequestParam Map<String,String> payload,
                                        @RequestHeader Map<String,String> header) {
        logger.info("Start to delete room");
        HashMap<String, String> request = new HashMap<>(payload);
        UtilTools utilTools = new UtilTools();
        if (utilTools.checkParameters(request, Constant.RequiredParams.deleteRoom)) {
            if (jwtService.jwtValidator(header, request, SecurityConstants.AdminSECRET)) {
                logger.info("Authentication passed");
                try {
                    Room updateroom = roomRepository.findByNumber(roomNum);
                    roomRepository.delete(updateroom);
                    return new IDMResponse().Correct(HttpStatus.OK, updateroom, "Room Successfully deleted");
                } catch (NullPointerException ne) {
                    return new IDMResponse().Correct(HttpStatus.BAD_REQUEST, "could not find room :" + roomNum);
                }
            } else {
                return new IDMResponse().Wrong(HttpStatus.BAD_REQUEST, "Invalid Token");
            }
        } else {
            return new IDMResponse().Wrong(HttpStatus.BAD_REQUEST, "Missing Parameters");
        }
    }









    @GetMapping(value = "/rooms")
    public ResponseEntity<Object> getAllRooms() {
        List<Room> ls_room = roomRepository.findAll();
        return new IDMResponse().Correct(HttpStatus.OK,ls_room,"Successful");
    }

    @GetMapping(value = "/rooms",params = "name")
    public ResponseEntity<Object> getroomByNameParam(@RequestParam(value = "name") String name){
        List<Room> ls_room = roomRepository.findByName(name);
        if (ls_room != null)
            return new IDMResponse().Correct(HttpStatus.OK,ls_room,"Successful");
        else
            return new IDMResponse().Wrong(HttpStatus.NOT_FOUND,"room not found");
    }
    @GetMapping(value = "/room",params = "number")
    public ResponseEntity<Object> getroomByNumberParam(@RequestParam(value = "number") String id){
        Room ls_room = roomRepository.findByNumber(id);
        if (ls_room != null)
            return new IDMResponse().Correct(HttpStatus.OK,ls_room,"Successful");
        else
            return new IDMResponse().Wrong(HttpStatus.NOT_FOUND,"room not found");
    }

    @GetMapping(value = "/room",params = "id")
    //@ResponseBody
    public ResponseEntity<Object> getroomByIdParam(@RequestParam(value = "name") String id){
        Room ls_room = roomRepository.findByNumber(id);
        if (ls_room != null)
            return new IDMResponse().Correct(HttpStatus.OK,ls_room,"Successful");
        else
            throw new ServiceRuntimeException("Room by id "+id +" is not found");
    }


}
