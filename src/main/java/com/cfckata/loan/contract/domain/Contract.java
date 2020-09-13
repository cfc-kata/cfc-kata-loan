package com.cfckata.loan.contract.domain;

import com.cfckata.loan.customer.LoanCustomer;
import com.github.meixuesong.aggregatepersistence.Versionable;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class Contract implements Versionable, Serializable {
    private String id;
    private LoanCustomer customer;
    private BigDecimal interestRate;
    private RepaymentType repaymentType;
    private LocalDate maturityDate;
    private BigDecimal commitment;
    private LocalDateTime createdAt;
    private ContractStatus status;
    private int version;

    final static List<CommitmentRange> ranges = Arrays.asList(
            new CommitmentRange(0, 17, 0),
            new CommitmentRange(18, 20, 1),
            new CommitmentRange(21, 30, 5),
            new CommitmentRange(31, 50, 20),
            new CommitmentRange(51, 60, 3),
            new CommitmentRange(61, 70, 1),
            new CommitmentRange(71, 20000, 0)
    );

    public Contract() {
    }

    Contract(String id, LoanCustomer customer, BigDecimal interestRate, RepaymentType repaymentType, LocalDate maturityDate, BigDecimal commitment, LocalDateTime createdAt, ContractStatus status, int version) {
        this.id = id;
        this.customer = customer;
        this.interestRate = interestRate;
        this.repaymentType = repaymentType;
        this.maturityDate = maturityDate;
        this.commitment = commitment;
        this.createdAt = createdAt;
        this.status = status;
        this.version = version;
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

    public RepaymentType getRepaymentType() {
        return repaymentType;
    }

    public LocalDate getMaturityDate() {
        return maturityDate;
    }

    public BigDecimal getCommitment() {
        return commitment;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public ContractStatus getStatus() {
        return status;
    }

    public void setCommitment(BigDecimal commitment) {
        BigDecimal old = this.commitment;
        this.commitment = commitment;
        try {
            validateCommitment();
        } catch (Exception e) {
            this.commitment = old;
            throw e;
        }
    }

    public void validate() {
        validateNullFields();
        validateCustomerAge();
        validateMaturityDate();
        validateCommitment();
    }

    private void validateNullFields() {
        if (customer == null || customer.getIdNumber() == null) {
            throw new IllegalArgumentException("Customer ID number is required");
        }

        if (customer.getIdNumber().length() != 18) {
            throw new IllegalArgumentException("Customer ID number format is incorrect");
        }

        if (createdAt == null) {
            throw new IllegalArgumentException("CreatedAt is required");
        }

        if (maturityDate == null) {
            throw new IllegalArgumentException("Maturity date is required");
        }

        if (commitment == null) {
            throw new IllegalArgumentException("Commitment is required");
        }
    }

    private void validateCommitment() {
        if (commitment.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Commitment should greater than zero");
        }

        int age = getBirthday().until(createdAt.toLocalDate()).getYears();
        for (CommitmentRange range : ranges) {
            range.validateAgeCommitment(age, commitment);
        }
    }

    private void validateMaturityDate() {
        LocalDate maxMaturity = createdAt.toLocalDate().plusYears(2);
        if (maturityDate.isAfter(maxMaturity)) {
            throw new IllegalArgumentException("Maturity date should less than 2 years");
        }
    }

    private void validateCustomerAge() {
        LocalDate minCreatedDate = getBirthday().plusYears(18);
        if (createdAt.toLocalDate().isBefore(minCreatedDate)) {
            throw new IllegalArgumentException("Customer should be over 18 years old");
        }
    }

    private LocalDate getBirthday() {
        return LocalDate.parse(customer.getIdNumber().substring(6, 14), DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    @Override
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    private static class CommitmentRange {
        private final int ageFrom;
        private final int ageTo;
        private final BigDecimal maxCommitment;

        public CommitmentRange(int ageFrom, int ageTo, int maxCommitment) {
            this.ageFrom = ageFrom;
            this.ageTo = ageTo;
            this.maxCommitment = BigDecimal.valueOf(maxCommitment * 10000.00);
        }

        public void validateAgeCommitment(int age, BigDecimal commitment) {
            if (age >= ageFrom && age <= ageTo && commitment.compareTo(maxCommitment) > 0) {
                throw new IllegalArgumentException(
                        String.format("The customer is %d years old, and his/her commitment should less than %s", age, maxCommitment));
            }
        }
    }
}
