package com.br.kata.consoleapp.csv.service;

import com.br.kata.consoleapp.csv.helper.CSVHelper;
import com.br.kata.consoleapp.csv.model.Customer;
import com.br.kata.consoleapp.csv.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.List;

@Service
public class CustomerCSVDownloadService {

    private final CustomerRepository customerRepository;

    public CustomerCSVDownloadService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    public ByteArrayInputStream load() {

        List<Customer> customers = customerRepository.findAll();

        ByteArrayInputStream in = CSVHelper.customersToCSV(customers);

        return in;
    }

}
