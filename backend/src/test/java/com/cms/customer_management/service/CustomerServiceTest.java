package com.cms.customer_management.service;

import com.cms.customer_management.entity.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    // Generates valid OLD Sri Lankan NIC (9 digits + V)
    private String validNic() {
        int number = ThreadLocalRandom.current().nextInt(100000000, 999999999);
        return number + "V";
    }

    @Test
    void createCustomer_success() {
        Customer customer = new Customer();
        customer.setName("John");
        customer.setNic(validNic()); // VALID NIC
        customer.setDateOfBirth(LocalDate.of(1995, 5, 10));

        Customer saved = customerService.createCustomer(customer);

        assertNotNull(saved.getId());
    }

    @Test
    void createCustomer_invalidNic_shouldFail() {
        Customer customer = new Customer();
        customer.setName("Invalid");
        customer.setNic("ABC123"); //  invalid
        customer.setDateOfBirth(LocalDate.of(1990, 1, 1));

        assertThrows(
                IllegalArgumentException.class,
                () -> customerService.createCustomer(customer)
        );
    }
}
