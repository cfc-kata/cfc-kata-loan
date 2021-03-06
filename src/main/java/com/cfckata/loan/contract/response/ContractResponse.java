package com.cfckata.loan.contract.response;

import com.cfckata.loan.contract.domain.Contract;
import com.cfckata.loan.contract.domain.LoanCustomer;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

public class ContractResponse {
    private String id;
    private LoanCustomer customer;
    private BigDecimal interestRate;
    private String repaymentType;
    private String maturityDate;
    private BigDecimal commitment;
    private String status;

    public ContractResponse(String id, LoanCustomer customer, BigDecimal interestRate, String repaymentType, String maturityDate, BigDecimal commitment, String status) {
        this.id = id;
        this.customer = customer;
        this.interestRate = interestRate;
        this.repaymentType = repaymentType;
        this.maturityDate = maturityDate;
        this.commitment = commitment;
        this.status = status;
    }

    public ContractResponse(Contract contract) {
        this.id = contract.getId();
        this.customer = contract.getCustomer();
        this.interestRate = contract.getInterestRate();
        this.repaymentType = contract.getRepaymentType().name();
        this.maturityDate = contract.getMaturityDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.commitment = contract.getCommitment();
        this.status = contract.getStatus().name();
    }

    public String getId() {
        return id;
    }

    public LoanCustomer getCustomer() {
        return customer;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public String getRepaymentType() {
        return repaymentType;
    }

    public String getMaturityDate() {
        return maturityDate;
    }

    public BigDecimal getCommitment() {
        return commitment;
    }

    public String getStatus() {
        return status;
    }
}
