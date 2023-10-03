package com.javahk.project.ecom.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.javahk.project.ecom.entity.Customer;

public interface CustomerOperation {

    @PostMapping(value = "ecom/customer")
    @ResponseStatus(value = HttpStatus.OK)
    Customer addCustomer(@RequestBody Customer customer);

    @GetMapping(value = "ecom/data/customers")
    @ResponseStatus(value = HttpStatus.OK)
    List<Customer> getAllCustomers();
}
