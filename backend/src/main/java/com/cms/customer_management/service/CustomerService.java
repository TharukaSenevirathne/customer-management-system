package com.cms.customer_management.service;

import com.cms.customer_management.dto.ExcelRowCustomer;
import com.cms.customer_management.dto.ExcelUploadResponse;
import com.cms.customer_management.entity.Customer;
import com.cms.customer_management.repository.CustomerRepository;
import com.cms.customer_management.util.ExcelCustomerHelper;
import com.cms.customer_management.util.NicValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    //  CREATE CUSTOMER
    public Customer createCustomer(Customer customer) {
        if (!NicValidator.isValid(customer.getNic())) {
            throw new IllegalArgumentException("Invalid NIC format");
        }
        return customerRepository.save(customer);
    }

    // UPDATE CUSTOMER
    public Customer updateCustomer(Long id, Customer updated) {
        Customer existing = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        existing.setName(updated.getName());
        existing.setDateOfBirth(updated.getDateOfBirth());

        return customerRepository.save(existing);
    }

    // PAGINATED LIST
    public Page<Customer> getAllCustomers(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }

    // EXCEL BULK CREATE + UPDATE
    public ExcelUploadResponse uploadCustomersFromExcel(MultipartFile file) {

        List<ExcelRowCustomer> rows =
                ExcelCustomerHelper.excelToCustomers(file);

        int saved = 0;
        int updated = 0;
        int skipped = 0;

        ExcelUploadResponse response = new ExcelUploadResponse();

        for (ExcelRowCustomer row : rows) {

            if (row.getError() != null) {
                skipped++;
                response.getErrors()
                        .add("Row " + row.getRowNumber() + ": " + row.getError());
                continue;
            }

            Customer incoming = row.getCustomer();

            Optional<Customer> existing =
                    customerRepository.findByNic(incoming.getNic());

            if (existing.isPresent()) {
                // UPDATE
                Customer c = existing.get();
                c.setName(incoming.getName());
                c.setDateOfBirth(incoming.getDateOfBirth());
                customerRepository.save(c);
                updated++;
            } else {
                // CREATE
                customerRepository.save(incoming);
                saved++;
            }
        }

        response.setSaved(saved);
        response.setUpdated(updated);
        response.setSkipped(skipped);

        return response;
    }
}
