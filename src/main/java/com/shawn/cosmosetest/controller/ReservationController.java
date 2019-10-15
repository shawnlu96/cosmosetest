package com.shawn.cosmosetest.controller;

import com.shawn.cosmosetest.entity.Reservation;
import com.shawn.cosmosetest.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @RequestMapping(value = "orders", method = RequestMethod.GET)
    public List<Reservation> viewCustomerReservation(String userName) {
        return reservationService.getReservationsByUserName(userName);
    }

    public static class ReserveInfo {
        public String userName;
        public String roomId;
        public long start;
        public long end;
        public ReserveInfo(){};

    }

    @RequestMapping(value = "reserve", method = RequestMethod.POST)
    public void reserve(@RequestBody ReserveInfo r) {
        reservationService.makeReservation(r.userName, r.roomId, new Date(r.start), new Date(r.end));
    }

    @RequestMapping(value = "cancel", method = RequestMethod.POST)
    public void cancel(String resId) {
        reservationService.cancelReservationById(Long.valueOf(resId));
    }
}
