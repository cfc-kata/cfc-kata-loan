package com.cfckata.loan.contract.request;

import com.cfckata.loan.customer.LoanCustomer;

import java.math.BigDecimal;

public class CreateContractRequest {
    private LoanCustomer loanCustomer;
    private BigDecimal interestRate;
    private String repaymentType;
    private String maturityDate;
    private BigDecimal commitment;

    public CreateContractRequest() {
    }

    public CreateContractRequest(LoanCustomer loanCustomer, BigDecimal interestRate, String repaymentType, String maturityDate, BigDecimal commitment) {
        this.loanCustomer = loanCustomer;
        this.interestRate = interestRate;
        this.repaymentType = repaymentType;
        this.maturityDate = maturityDate;
        this.commitment = commitment;
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

    public LoanCustomer getLoanCustomer() {
        return loanCustomer;
    }

    public void setLoanCustomer(LoanCustomer loanCustomer) {
        this.loanCustomer = loanCustomer;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public void setRepaymentType(String repaymentType) {
        this.repaymentType = repaymentType;
    }

    public void setMaturityDate(String maturityDate) {
        this.maturityDate = maturityDate;
    }

    public void setCommitment(BigDecimal commitment) {
        this.commitment = commitment;
    }
}
