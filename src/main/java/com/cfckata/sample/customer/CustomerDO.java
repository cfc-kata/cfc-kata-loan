package com.cfckata.sample.customer;

public class CustomerDO {
    private String id;
    private String name;

    public Customer toCustomer() {
        return new Customer(id, name);
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
