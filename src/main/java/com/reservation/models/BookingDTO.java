package com.reservation.models;

import org.springframework.format.annotation.DateTimeFormat;
import java.util.UUID;

public class BookingDTO {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    String startDateTime;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    String endDateTime;

    UUID roomId;

    String employeeNo;

    public String getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }

    public UUID getRoomId() {
        return roomId;
    }

    public void setRoomId(UUID roomId) {
        this.roomId = roomId;
    }

    public String getEmployeeNo() {
        return employeeNo;
    }

    public void setEmployeeNo(String employeeNo) {
        this.employeeNo = employeeNo;
    }
}
