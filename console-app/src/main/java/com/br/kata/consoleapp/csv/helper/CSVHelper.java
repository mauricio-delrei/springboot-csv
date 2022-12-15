package com.br.kata.consoleapp.csv.helper;

import com.br.kata.consoleapp.csv.model.Customer;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVHelper {
    public static String TYPE = "text/csv";
    static String[] HEADERs = { "Id","Customer Ref", "Customer Name", "Address Line 1", "Address Line 2",
    "Town","County","Country","Postcode"};


    public static boolean hasCSVFormat(MultipartFile file) {

        if (!TYPE.equals(file.getContentType())) {
            return false;
        }

        return true;
    }

    public static List<Customer> csvToCostumers(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

            List<Customer> customers = new ArrayList<Customer>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                Customer customer = new Customer(
                        Long.parseLong(csvRecord.get("Id")),
                        csvRecord.get("customerRef"),
                        csvRecord.get("customerName"),
                        csvRecord.get("addressLine1"),
                        csvRecord.get("addressLine2"),
                        csvRecord.get("town"),
                        csvRecord.get("county"),
                        csvRecord.get("country"),
                        csvRecord.get("postcode"));

                customers.add(customer);
            }

            return customers;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }

    public static ByteArrayInputStream customersToCSV(List<Customer> customers) {
        final CSVFormat format = CSVFormat.DEFAULT.withHeader(HEADERs);
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format)) {
            for (Customer customer : customers) {
                List<String> data = Arrays.asList(
                        String.valueOf(customer.getId()),
                        customer.getCustomerRef(),
                        customer.getCustomerName(),
                        customer.getAddressLine1(),
                        customer.getAddressLine2(),
                        customer.getTown(),
                        customer.getCounty(),
                        customer.getCountry(),
                        customer.getPostcode()
                );

                csvPrinter.printRecord(data);
            }

            csvPrinter.flush();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
        }
    }
}
