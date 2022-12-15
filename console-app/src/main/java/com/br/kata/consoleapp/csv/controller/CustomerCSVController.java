package com.br.kata.consoleapp.csv.controller;

import com.br.kata.consoleapp.csv.helper.CSVHelper;
import com.br.kata.consoleapp.csv.message.ResponseMessage;
import com.br.kata.consoleapp.csv.model.Customer;
import com.br.kata.consoleapp.csv.service.CustomerCSVDownloadService;
import com.br.kata.consoleapp.csv.service.CustomerCSVUploadService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/csv")

public class CustomerCSVController {

    private final CustomerCSVUploadService customerCSVUploadService;

    private final CustomerCSVDownloadService customerCSVDownloadService;



    public CustomerCSVController(CustomerCSVUploadService customerCSVUploadService,
                                 CustomerCSVDownloadService customerCSVDownloadService) {
        this.customerCSVUploadService = customerCSVUploadService;
        this.customerCSVDownloadService = customerCSVDownloadService;

    }


    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";

        if (CSVHelper.hasCSVFormat(file)) {
            try {
                customerCSVUploadService.save(file);

                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            } catch (Exception e) {
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
            }
        }
        message = "Please upload a csv file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> getFile() {
        String filename = "customers.csv";
        InputStreamResource file = new InputStreamResource(customerCSVDownloadService.load());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/csv"))
                .body(file);
    }
    @GetMapping("/customers")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        try {
            List<Customer> customers = customerCSVUploadService.getAllCustomers();

            if (customers.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(customers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
