package com.cfckata.sample.sales.response;

import com.cfckata.sample.customer.Customer;

public class CustomerResponse {
    private String id;
    private String name;

    public CustomerResponse() {
    }

    public CustomerResponse(Customer customer) {
        this.id = customer.getId();
        this.name = customer.getName();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
