package com.cms.customer_management.dto;

import com.cms.customer_management.entity.Customer;

public class ExcelRowCustomer {

    private int rowNumber;
    private Customer customer;
    private String error;

    public ExcelRowCustomer(int rowNumber, Customer customer, String error) {
        this.rowNumber = rowNumber;
        this.customer = customer;
        this.error = error;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getError() {
        return error;
    }
}
