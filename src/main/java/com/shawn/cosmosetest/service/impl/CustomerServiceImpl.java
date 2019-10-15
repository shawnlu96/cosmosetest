package com.shawn.cosmosetest.service.impl;

import com.shawn.cosmosetest.entity.Customer;
import com.shawn.cosmosetest.repository.CustomerMapper;
import com.shawn.cosmosetest.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerMapper customerMapper;
    @Override
    public void registerCustomer(Customer customer) {
        customerMapper.save(customer);
    }

    @Override
    public Customer getCustomerByPasswordAndUsername(String password, String userName) {
        return customerMapper.getByPasswordAndUserName(password, userName);
    }

}
