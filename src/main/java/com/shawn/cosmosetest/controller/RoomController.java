package com.shawn.cosmosetest.controller;

import com.shawn.cosmosetest.entity.Room;
import com.shawn.cosmosetest.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@RestController
public class RoomController {
    @Autowired
    private RoomService roomService;

    public static class SearchCriteria {
        public String city;
        public long start;
        public long end;
        public double minPrice;
        public double maxPrice;
    }

    @RequestMapping(value = "search", method = RequestMethod.GET)
    public List<Room> search(@RequestBody SearchCriteria s) {
        return roomService.searchRooms(s.city, new Date(s.start), new Date(s.end),
                new BigDecimal(s.minPrice), new BigDecimal(s.maxPrice));
    }
}
