package com.br.kata.consoleapp.csv.service;

import com.br.kata.consoleapp.csv.helper.CSVHelper;
import com.br.kata.consoleapp.csv.model.Customer;
import com.br.kata.consoleapp.csv.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class CustomerCSVUploadService {

    private final CustomerRepository customerRepository;

    public CustomerCSVUploadService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void save(MultipartFile file){
        try {
            List<Customer> tutorials = CSVHelper.csvToCostumers(file.getInputStream());
            customerRepository.saveAll(tutorials);
        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
}
