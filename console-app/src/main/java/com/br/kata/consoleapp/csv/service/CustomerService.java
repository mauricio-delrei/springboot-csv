package com.br.kata.consoleapp.csv.service;

import com.br.kata.consoleapp.csv.model.Customer;
import com.br.kata.consoleapp.csv.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public Customer save(Customer customer){
        var entity = repository.save(customer);
        return entity;
    }

}
