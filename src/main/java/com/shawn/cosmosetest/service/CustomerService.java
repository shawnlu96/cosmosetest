package com.shawn.cosmosetest.service;

import com.shawn.cosmosetest.entity.Customer;

public interface CustomerService {

    void registerCustomer(Customer customer);

    Customer getCustomerByPasswordAndUsername(String password, String userName);
}
