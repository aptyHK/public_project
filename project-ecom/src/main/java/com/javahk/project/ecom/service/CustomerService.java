package com.javahk.project.ecom.service;

import java.util.List;

import com.javahk.project.ecom.entity.Customer;

public interface CustomerService {
    List<Customer> getAllCustomers();

    Customer addCustomer(Customer stock);
}
