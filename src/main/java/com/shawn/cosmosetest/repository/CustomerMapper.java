package com.shawn.cosmosetest.repository;

import com.shawn.cosmosetest.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerMapper extends JpaRepository<Customer, String> {
    Customer getByPasswordAndUserName(String password, String userName);

}
