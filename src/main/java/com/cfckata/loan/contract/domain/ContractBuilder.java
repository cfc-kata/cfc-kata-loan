package com.cfckata.loan.contract.domain;

import com.cfckata.loan.customer.LoanCustomer;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ContractBuilder {
    private String id;
    private LoanCustomer customer;
    private BigDecimal interestRate;
    private RepaymentType repaymentType;
    private LocalDate maturityDate;
    private BigDecimal commitment;
    private LocalDateTime createdAt = LocalDateTime.now();
    private ContractStatus status = ContractStatus.ACTIVE;

    public ContractBuilder setId(String id) {
        this.id = id;
        return this;
    }

    public ContractBuilder setCustomer(LoanCustomer customer) {
        this.customer = customer;
        return this;
    }

    public ContractBuilder setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
        return this;
    }

    public ContractBuilder setRepaymentType(RepaymentType repaymentType) {
        this.repaymentType = repaymentType;
        return this;
    }

    public ContractBuilder setMaturityDate(LocalDate maturityDate) {
        this.maturityDate = maturityDate;
        return this;
    }

    public ContractBuilder setCommitment(BigDecimal commitment) {
        this.commitment = commitment;
        return this;
    }

    public ContractBuilder setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public ContractBuilder setStatus(ContractStatus status) {
        this.status = status;
        return this;
    }

    public Contract createContract() {
        Contract contract = new Contract(id, customer, interestRate, repaymentType, maturityDate, commitment, createdAt, status);
        contract.validate();
        return contract;
    }
}
