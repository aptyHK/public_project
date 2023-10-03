package com.javahk.project.ecom.controller.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javahk.project.ecom.controller.CustomerOperation;
import com.javahk.project.ecom.entity.Customer;
import com.javahk.project.ecom.service.CustomerService;

@RestController
@RequestMapping(value = "/api/v1")
public class CustomerController implements CustomerOperation {

    @Autowired
    CustomerService customerService;

    @Override
    public Customer addCustomer(Customer customer) {
        return customerService.addCustomer(customer);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }
}
