package com.reservation.services;

import com.reservation.models.Booking;
import com.reservation.models.BookingDTO;
import com.reservation.models.Employee;
import com.reservation.models.Room;
import com.reservation.repostiories.BookingRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service("BookingService")
@Transactional
public class BookingService {
    private Logger logger =  LogManager.getLogger(BookingService.class);
    private BookingRepository bookingRepository;
    private EmployeeService employeeService;
    private RoomService roomService;

    public BookingService(BookingRepository bookingRepository, EmployeeService employeeService, RoomService roomService) {
        this.bookingRepository = bookingRepository;
        this.employeeService = employeeService;
        this.roomService = roomService;
    }

    public List<Booking> findAll(String employeeId){
        if(employeeId == null || employeeId.isEmpty()){
            logger.info("Find all bookings ... ");
            return bookingRepository.findAll();
        }else{
            logger.info("Find bookings of employee "+ employeeId);
            employeeService.findByEmployeeNo(employeeId);
        }
        return bookingRepository.findAllByEmployeeNo(employeeId);
    }

    public Booking doBooking(BookingDTO bookingDTO) throws Exception {
        logger.info("do booking... ");

        LocalDateTime start = LocalDateTime.parse(bookingDTO.getStartDateTime(), DateTimeFormatter.ISO_DATE_TIME).withSecond(0).withNano(0);
        LocalDateTime end =  LocalDateTime.parse(bookingDTO.getEndDateTime(),DateTimeFormatter.ISO_DATE_TIME).withSecond(0).withNano(0);
        List<Room> availableRooms = roomService.findAvailableRooms(start,end);
        Optional<Room> validRoom = availableRooms.stream().filter(x -> x.getId().equals(bookingDTO.getRoomId())).findFirst();
        if(!validRoom.isPresent()){
            logger.info("Room is already reserved... ");
            throw new Exception("This room is already reserved! Please Try again!");
        }

        Booking booking = new Booking();
        booking.setStartDateTime(start);
        booking.setEndDateTime(end);

        LocalDateTime currentServerTime = LocalDateTime.now();
        String generated = String.format("B-%s%d%d%d%d",currentServerTime.getMonthValue(),
                currentServerTime.getDayOfMonth(),currentServerTime.getHour(), currentServerTime.getMinute(),currentServerTime.getSecond());
        booking.setBookingNo(generated);

        Employee employee =  employeeService.findByEmployeeNo(bookingDTO.getEmployeeNo());
        booking.setEmployee(employee);

        Room room = roomService.findById(bookingDTO.getRoomId());
        booking.setRoom(room);

        return bookingRepository.save(booking);
    }

    public void deleteById(UUID id){
        logger.info("delete booking by id... " + id);
        try{
            bookingRepository.deleteById(id);
        }catch(Exception e){
            e.printStackTrace();
            logger.info("Cannot successfully remove booking id " + id);

            throw new EntityNotFoundException("Booking not found for Id "+id);
        }
    }


}
