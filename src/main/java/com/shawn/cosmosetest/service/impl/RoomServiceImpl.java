package com.shawn.cosmosetest.service.impl;

import com.shawn.cosmosetest.entity.Room;
import com.shawn.cosmosetest.repository.RoomMapper;
import com.shawn.cosmosetest.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomMapper roomMapper;

    @Override
    public List<Room> searchRooms(String city, Date start, Date end, BigDecimal minPrice, BigDecimal maxPrice) {
        return roomMapper.findVacantRooms(city, minPrice, maxPrice, start, end);
    }
}
