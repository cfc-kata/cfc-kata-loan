package com.cfckata.loan.customer;

import java.io.Serializable;

public class LoanCustomer implements Serializable {
    private final String id;
    private final String name;
    private final String idNumber;
    private final String mobilePhone;

    public LoanCustomer(String id, String name, String idNumber, String mobilePhone) {
        this.id = id;
        this.name = name;
        this.idNumber = idNumber;
        this.mobilePhone = mobilePhone;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }


}
