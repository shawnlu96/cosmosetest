package com.shawn.cosmosetest.controller;

import com.shawn.cosmosetest.entity.Customer;
import com.shawn.cosmosetest.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping(value = "/doLogin", method = RequestMethod.GET)
    public String doLogin(@RequestBody Customer customer, Map<String, Object> map){
        Customer customer1 = customerService
                .getCustomerByPasswordAndUsername(customer.getPassword(), customer.getUserName());
        if(customer1==null){
            map.put("msg", "Login failed.");
            return "failed";
        }else{
            map.put("msg", "Login success.");
            return "success";
        }

    }

    @RequestMapping("/register")
    public String register(){
        return "register";
    }

    @RequestMapping(value = "/doRegister", method = RequestMethod.POST)
    public String doRegister(@RequestBody Customer customer, Map<String,Object> map){
        customerService.registerCustomer(customer);
        map.put("msg","Successfully registered.");
        return "success";
    }

}
