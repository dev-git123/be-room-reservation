package com.reservation.controllers;

import com.reservation.models.Room;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.reservation.services.RoomService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/rooms")
@CrossOrigin("*")
public class RoomController {

    private RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Room create(@RequestBody Room room) {
        return roomService.insertRoom(room);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Room> find() {
        return roomService.getRooms();
    }

    @GetMapping("/{roomId}")
    @ResponseStatus(HttpStatus.OK)
    public Room findRoomById(@PathVariable UUID roomId) {
        return roomService.findById(roomId);
    }

    @DeleteMapping("/{roomId}")
    @ResponseStatus(HttpStatus.OK)
    public void remove(@PathVariable UUID roomId){
        roomService.deleteById(roomId);
    }

    @GetMapping("/available")
    @ResponseStatus(HttpStatus.OK)
    public List<Room> findAvailableRooms(@RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
                                         @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        List<Room> rooms = roomService.findAvailableRooms(from.withSecond(0).withNano(0), to.withSecond(0).withNano(0));
        return rooms;
    }
}
