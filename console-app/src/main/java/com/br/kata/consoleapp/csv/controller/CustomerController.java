package com.br.kata.consoleapp.csv.controller;

import com.br.kata.consoleapp.csv.model.Customer;
import com.br.kata.consoleapp.csv.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<Customer> save(@RequestBody Customer customer){
        var entity = customerService.save(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(entity);
    }

}
