package com.cfckata.customer;

public class CustomerDO {
    private String id;
    private String name;

    public Customer toCustomer() {
        Customer customer = new Customer(id, name);

        return customer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
