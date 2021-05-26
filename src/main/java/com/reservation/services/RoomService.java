package com.reservation.services;


import com.reservation.models.Booking;
import com.reservation.models.Room;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.reservation.repostiories.RoomRepository;
import javax.annotation.PostConstruct;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class RoomService {
    private Logger logger =  LogManager.getLogger(RoomService.class);
    @Autowired
    RoomRepository roomRepository;

    @PostConstruct
    public void init(){
        Room room1 = new Room();
        room1.setRoomNo("R01");
        room1.setName("Mocha");
        room1.setBuildingNo("Building A");
        room1.setCapacity(8);
        room1.setLevel("level 1");

        Room room2 = new Room();
        room2.setRoomNo("R02");
        room2.setName("Cappuccino");
        room2.setBuildingNo("Building A");
        room2.setCapacity(15);
        room2.setLevel("level 2");

        insertRoom(room1);
        insertRoom(room2);

        logger.info("Initializing room in memory db... ");
    }

    public Room insertRoom(Room room){
        logger.info("Persisting room info ... ");
       return roomRepository.save(room);
    }

    public List<Room> getRooms(){
        logger.info("find all room info ... ");
        return roomRepository.findAll();
    }

    public Room findById(UUID id) {
        logger.info("find room by id ... " + id);
        return roomRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Room not found for Id "+id));
    }

    public void deleteById(UUID id){
        logger.info("delete room by id... " + id);
        try{
            roomRepository.deleteById(id);
        }catch(Exception e){
            e.printStackTrace();
            logger.debug("Cannot successfully remove room id " + id);

            throw new EntityNotFoundException("Room not found for Id "+id);
        }
    }

public List<Room> findAvailableRooms(LocalDateTime startTime, LocalDateTime endTime){
    logger.info("Find all available rooms from start time " + startTime +" to end time "+ endTime);
    List<Room> rooms = getRooms();
    List<Room> availableRooms = new ArrayList<>();
    for(Room room : rooms){
        boolean isAvailable = true;
        List<Booking> bookings = room.getBookings();

        for(Booking booking: bookings){
            if(startTime.equals(booking.getStartDateTime()) ||
                    endTime.equals(booking.getEndDateTime()) ||
                    (startTime.isAfter(booking.getStartDateTime()) && endTime.isBefore(booking.getEndDateTime())) ||
                    (startTime.isBefore(booking.getStartDateTime()) && endTime.isAfter(booking.getEndDateTime())) ||
                    isWithinRange(startTime, booking.getStartDateTime(), booking.getEndDateTime()) ||
                    isWithinRange(endTime, booking.getStartDateTime(), booking.getEndDateTime())
            ){

                isAvailable = false;
                break;
            }
        }
        if(isAvailable)
            availableRooms.add(room);
    }
    return availableRooms;
}
    boolean isWithinRange(LocalDateTime input , LocalDateTime bookedstartDate , LocalDateTime bookedendDate) {
        return input.isAfter(bookedstartDate) && input.isBefore(bookedendDate);
    }
}
