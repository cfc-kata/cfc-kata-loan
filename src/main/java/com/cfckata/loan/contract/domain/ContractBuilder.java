package com.cfckata.loan.contract.domain;

import com.github.meixuesong.aggregatepersistence.Versionable;

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
    private int version = Versionable.NEW_VERSION;

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
        //DB only support milliseconds
        LocalDateTime time = keepMilliseconds(createdAt);
        this.createdAt = time;
        return this;
    }

    public ContractBuilder setVersion(int version) {
        this.version = version;
        return this;
    }

    /**
     * replace nano with milliseconds
     * @param localDateTime
     * @return
     */
    private LocalDateTime keepMilliseconds(LocalDateTime localDateTime) {
        return localDateTime.withNano((localDateTime.getNano() / (1000 * 1000)) * 1000 * 1000);
    }

    public ContractBuilder setStatus(ContractStatus status) {
        this.status = status;
        return this;
    }

    public Contract createContract() {
        Contract contract = new Contract(id, customer, interestRate, repaymentType, maturityDate, commitment, createdAt, status, version);
        contract.validate();
        return contract;
    }
}
