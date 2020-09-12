package com.cfckata.loan.contract.response;

import com.cfckata.loan.customer.LoanCustomer;

import java.math.BigDecimal;

public class ContractResponse {
    private String id;
    private final LoanCustomer loanCustomer;
    private final BigDecimal interestRate;
    private final String repaymentType;
    private final String maturityDate;
    private final BigDecimal commitment;
    private final String status;

    public ContractResponse(String id, LoanCustomer loanCustomer, BigDecimal interestRate, String repaymentType, String maturityDate, BigDecimal commitment, String status) {
        this.id = id;
        this.loanCustomer = loanCustomer;
        this.interestRate = interestRate;
        this.repaymentType = repaymentType;
        this.maturityDate = maturityDate;
        this.commitment = commitment;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
