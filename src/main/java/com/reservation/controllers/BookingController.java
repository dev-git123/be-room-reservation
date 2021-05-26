package com.reservation.controllers;

import com.reservation.models.Booking;
import com.reservation.models.BookingDTO;
import com.reservation.services.BookingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/meetingRoom")
@CrossOrigin("*")
public class BookingController {
    private static final Logger logger = LoggerFactory.getLogger(BookingController.class);
    private BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/booking")
    @ResponseStatus(HttpStatus.OK)
    public List<Booking> find(@RequestParam(name="employeeNo", required=false) String employeeNo) {
        return bookingService.findAll(employeeNo);
    }

    @PostMapping("/booking")
    @ResponseStatus(HttpStatus.OK)
    public  Booking bookRoom(@RequestBody BookingDTO bookingDTO ) throws Exception {

        return bookingService.doBooking(bookingDTO);
    }

    @DeleteMapping("/booking/{bookingId}")
    @ResponseStatus(HttpStatus.OK)
    public void remove(@PathVariable UUID bookingId){

        bookingService.deleteById(bookingId);
    }
}
