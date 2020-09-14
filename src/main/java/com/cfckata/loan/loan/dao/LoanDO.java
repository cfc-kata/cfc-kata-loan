package com.cfckata.loan.loan.dao;

import java.math.BigDecimal;
import java.util.Date;

public class LoanDO {
    private String id;

    private Date createdAt;

    private String contractId;

    private BigDecimal applyAmount;

    private Integer totalMonth;

    private BigDecimal interestRate;

    private String withdrawBankAccount;

    private String repaymentBankAccount;

    private String repaymentType;

    private Integer version;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public BigDecimal getApplyAmount() {
        return applyAmount;
    }

    public void setApplyAmount(BigDecimal applyAmount) {
        this.applyAmount = applyAmount;
    }

    public Integer getTotalMonth() {
        return totalMonth;
    }

    public void setTotalMonth(Integer totalMonth) {
        this.totalMonth = totalMonth;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public String getWithdrawBankAccount() {
        return withdrawBankAccount;
    }

    public void setWithdrawBankAccount(String withdrawBankAccount) {
        this.withdrawBankAccount = withdrawBankAccount;
    }

    public String getRepaymentBankAccount() {
        return repaymentBankAccount;
    }

    public void setRepaymentBankAccount(String repaymentBankAccount) {
        this.repaymentBankAccount = repaymentBankAccount;
    }

    public String getRepaymentType() {
        return repaymentType;
    }

    public void setRepaymentType(String repaymentType) {
        this.repaymentType = repaymentType;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
