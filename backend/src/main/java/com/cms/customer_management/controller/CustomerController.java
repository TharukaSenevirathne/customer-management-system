package com.cms.customer_management.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import com.cms.customer_management.dto.ExcelUploadResponse;
import com.cms.customer_management.entity.Customer;
import com.cms.customer_management.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


@RestController
@RequestMapping("/api/customers")
@CrossOrigin(origins = "http://localhost:3000")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    //  CREATE CUSTOMER (FOR TESTING NIC UNIQUE)
    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        Customer savedCustomer = customerService.createCustomer(customer);
        return ResponseEntity.ok(savedCustomer);
    }

    // EXCEL UPLOAD
    @PostMapping("/upload")
    public ResponseEntity<ExcelUploadResponse> uploadCustomers(
            @RequestParam("file") MultipartFile file) {

        ExcelUploadResponse response =
                customerService.uploadCustomersFromExcel(file);

        return ResponseEntity.ok(response);
    }

@GetMapping
public Page<Customer> getAllCustomers(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "id") String sortBy,
        @RequestParam(defaultValue = "asc") String direction) {

    Sort sort = direction.equalsIgnoreCase("desc")
            ? Sort.by(sortBy).descending()
            : Sort.by(sortBy).ascending();

    Pageable pageable = PageRequest.of(page, size, sort);

    return customerService.getAllCustomers(pageable);
}

@PutMapping("/{id}")
public ResponseEntity<Customer> updateCustomer(
        @PathVariable Long id,
        @RequestBody Customer customer) {

    Customer updated = customerService.updateCustomer(id, customer);
    return ResponseEntity.ok(updated);
}




}
