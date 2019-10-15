package com.shawn.cosmosetest.service.impl;

import com.shawn.cosmosetest.entity.Reservation;
import com.shawn.cosmosetest.repository.ReservationMapper;
import com.shawn.cosmosetest.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationMapper reservationMapper;

    @Override
    public void makeReservation(String userName, String roomId, Date start, Date end) {
        Reservation r = new Reservation();
        r.setUserName(userName);
        r.setRoomId(roomId);
        r.setStartDate(start);
        r.setEndDate(end);
        r.setStatus(true);
        reservationMapper.save(r);
    }

    @Override
    public List<Reservation> getReservationsByUserName(String userName) {
        return reservationMapper.getReservationsByUserName(userName);
    }

    @Override
    public void cancelReservationById(long resId) {
        Reservation reservation = reservationMapper.getOne(String.valueOf(resId));
        reservation.setStatus(false);
        reservationMapper.save(reservation);
    }
}
