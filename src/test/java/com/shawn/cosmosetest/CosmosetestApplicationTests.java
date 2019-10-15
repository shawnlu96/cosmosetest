package com.shawn.cosmosetest;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.shawn.cosmosetest.controller.CustomerController;
import com.shawn.cosmosetest.controller.ReservationController;
import com.shawn.cosmosetest.controller.RoomController;
import com.shawn.cosmosetest.entity.Customer;
import com.shawn.cosmosetest.entity.Reservation;
import com.shawn.cosmosetest.entity.Room;
import com.shawn.cosmosetest.repository.CustomerMapper;
import com.shawn.cosmosetest.repository.ReservationMapper;
import com.shawn.cosmosetest.service.CustomerService;
import com.shawn.cosmosetest.service.ReservationService;
import com.shawn.cosmosetest.service.RoomService;
import com.shawn.cosmosetest.tool.JsonDateValueProcessor;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.springframework.test.util.AssertionErrors.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=CosmosetestApplication.class)
@ActiveProfiles("test")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CosmosetestApplicationTests {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private RoomService roomService;
    @Autowired
    private ReservationMapper reservationMapper;
    private MockMvc mvc;

    @Autowired
    private CustomerController customerController;
    @Autowired
    private ReservationController reservationController;
    @Autowired
    private RoomController roomController;

    private long resId = 0;
    private RequestBuilder rb = null;
    Gson gson = new Gson();
    @Test
    public void AtestRegisterLogin() throws Exception{
        mvc = MockMvcBuilders.standaloneSetup(customerController).build();
        Customer c = new Customer();
        c.setUserName("test");
        c.setPassword("testpwd");

        System.out.println(gson.toJson(c));
        int originalCount = customerMapper.findAll().size();
        rb = post("/doRegister").contentType(MediaType.APPLICATION_JSON).content(gson.toJson(c));
        mvc.perform(rb).andExpect(status().isOk());
        rb = get("/doLogin").contentType(MediaType.APPLICATION_JSON).content(gson.toJson(c));
        mvc.perform(rb).andExpect(status().isOk()).andExpect(content().string(equalTo("success")));
        int currCount = customerMapper.findAll().size();
        assertTrue("user not added", currCount == originalCount+1);
    }

    static class AnotherReservation{
        public long ResId;
        public String userName;
        public String roomId;
        public boolean status;
        public long startDate;
        public long endDate;
    }
    @Test
    public void BtestReserveAndGetReservations() throws Exception{
        mvc = MockMvcBuilders.standaloneSetup(reservationController).build();
        ReservationController.ReserveInfo info = new ReservationController.ReserveInfo();
        int countBefore = reservationMapper.findAll().size();
        info.roomId = "1";
        info.userName = "test";
        info.start = new Date(2019-1900,10-1,1).getTime();
        info.end = new Date(2019-1900,10-1,30).getTime();
        rb = post("/reserve").contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(info));
        mvc.perform(rb).andExpect(status().isOk());
        int countAfter = reservationMapper.findAll().size();
        assertTrue("reservation failed", countAfter == countBefore+1);
        rb = get("/orders").param("userName",info.userName);
        ResultActions resultActions = mvc.perform(rb).andDo(print()).andExpect(status().isOk());
        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        List<AnotherReservation> reservationList = gson.fromJson(contentAsString,new TypeToken<List<AnotherReservation>>(){}.getType());
        resId=reservationList.get(0).ResId;
        assertTrue("success", reservationList.get(0).userName.equals("test"));
    }

    @Test
    public void CtestSearchRooms() throws Exception{
        mvc = MockMvcBuilders.standaloneSetup(roomController).build();
        RoomController.SearchCriteria s = new RoomController.SearchCriteria();
        s.city = "Shanghai";
        s.start = 1570204800000l;
        s.end = 1570377600000l;
        s.minPrice = 0;
        s.maxPrice = 1000;
        rb = get("/search").contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(s));
        ResultActions resultActions = mvc.perform(rb).andDo(print()).andExpect(status().isOk());
        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        List<Room> roomResults = gson.fromJson(contentAsString,new TypeToken<List<Room>>(){}.getType());
        assertTrue("wrong result", roomResults.size() == 2);
    }


}
