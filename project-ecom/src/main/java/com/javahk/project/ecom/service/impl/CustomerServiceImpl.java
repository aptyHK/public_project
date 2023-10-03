package com.javahk.project.ecom.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javahk.project.ecom.entity.Customer;
import com.javahk.project.ecom.repository.CustomerRepository;
import com.javahk.project.ecom.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {
    
    @Autowired
    CustomerRepository customerRepository;

    @Override
    public Customer addCustomer(Customer customer) {
        customerRepository.save(customer);
		return customer;
      //return new Customer();
        //return stockRepository.save(stock); // insert into
    }

    @Override
    public List<Customer> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers;
    }
}
