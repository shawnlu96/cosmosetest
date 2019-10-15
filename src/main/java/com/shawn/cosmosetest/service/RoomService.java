package com.shawn.cosmosetest.service;

import com.shawn.cosmosetest.entity.Room;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface RoomService {

    List<Room> searchRooms(String city, Date start, Date end, BigDecimal minPrice, BigDecimal maxPrice);
}
