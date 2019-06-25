package com.tozhang.training.data.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="RESERVATION")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RESERVATION_ID")
    private long id;
    @Column(name = "ROOM_ID")
    private long roomID;
    @Column(name = "GUEST_ID")
    private long guestID;
    @Column(name = "RES_DATE")
    private Date date;
    @Column(name = "CHECK_OUT_DATE")
    private Date checkOutDate;
    @Column(name = "CHECK_IN_DATE")
    private Date checkInDate;


    public long getId() {
        return id;
    }

    public long getRoomID() {
        return roomID;
    }

    public long getGuestID() {
        return guestID;
    }

    public Date getDate() {
        return date;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setRoomID(long roomID) {
        this.roomID = roomID;
    }

    public void setGuestID(long guestID) {
        this.guestID = guestID;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        checkOutDate = checkOutDate;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        checkInDate = checkInDate;
    }
}
