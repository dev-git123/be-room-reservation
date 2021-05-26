package com.reservation.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.UUID;

@Entity
public class Room {
    @Id
    @GeneratedValue
    UUID id;
    String roomNo;
    String name;
    String level;
    String buildingNo;
    int capacity;

    @OneToMany(mappedBy = "room")
    @JsonBackReference
    List<Booking> bookings;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String number) {
        this.name = number;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getBuildingNo() {
        return buildingNo;
    }

    public void setBuildingNo(String buildNo) {
        this.buildingNo = buildNo;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }
}
