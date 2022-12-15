package com.br.kata.consoleapp.csv.repository;

import com.br.kata.consoleapp.csv.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {
}
