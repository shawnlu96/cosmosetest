package com.shawn.cosmosetest.repository;

import com.shawn.cosmosetest.entity.Room;
import com.shawn.cosmosetest.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface RoomMapper extends JpaRepository<Room, String> {
    @Query(value =
            "select * from room r where r.city = 'Shanghai' and r.price >= 200 and r.price <= 400 and not exists (select a.room_id from reservation a where a.room_id = r.room_id and a.status = 1 and (a.start_date >= 1570204800000 and a.start_date <= 1570377600000) or (a.end_date >= 1570204800000 and a.end_date <= 1570377600000))",nativeQuery = true)
    List<Room> findVacantRooms(
            @Param("city") String city,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate
    );
}
