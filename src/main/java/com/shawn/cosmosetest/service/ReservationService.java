package com.shawn.cosmosetest.service;

import com.shawn.cosmosetest.entity.Reservation;

import java.util.Date;
import java.util.List;

public interface ReservationService {

    void makeReservation(String userName, String roomId, Date start, Date end);
    List<Reservation> getReservationsByUserName(String userName);
    void cancelReservationById(long resId);
}
