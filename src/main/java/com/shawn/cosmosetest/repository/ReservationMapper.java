package com.shawn.cosmosetest.repository;

import com.shawn.cosmosetest.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface ReservationMapper extends JpaRepository<Reservation, String> {


    List<Reservation> getReservationsByUserName(String userName);
}
