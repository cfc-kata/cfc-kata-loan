package com.cfckata.loan.loan.request;

import java.math.BigDecimal;

public class CreateLoanRequest {
    private String contractId;
    private BigDecimal applyAmount;
    private int totalMonth;
    private BigDecimal interestRate;
    private String withdrawBankAccount;
    private String repaymentBankAccount;
    private String repaymentType;

    public CreateLoanRequest() {
    }

    public CreateLoanRequest(String contractId, BigDecimal applyAmount, int totalMonth,
                             BigDecimal interestRate, String withdrawBankAccount, String repaymentBankAccount,
                             String repaymentType) {

        this.contractId = contractId;
        this.applyAmount = applyAmount;
        this.totalMonth = totalMonth;
        this.interestRate = interestRate;
        this.withdrawBankAccount = withdrawBankAccount;
        this.repaymentBankAccount = repaymentBankAccount;
        this.repaymentType = repaymentType;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public void setApplyAmount(BigDecimal applyAmount) {
        this.applyAmount = applyAmount;
    }

    public void setTotalMonth(int totalMonth) {
        this.totalMonth = totalMonth;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public void setWithdrawBankAccount(String withdrawBankAccount) {
        this.withdrawBankAccount = withdrawBankAccount;
    }

    public void setRepaymentBankAccount(String repaymentBankAccount) {
        this.repaymentBankAccount = repaymentBankAccount;
    }

    public void setRepaymentType(String repaymentType) {
        this.repaymentType = repaymentType;
    }

    public String getContractId() {
        return contractId;
    }

    public BigDecimal getApplyAmount() {
        return applyAmount;
    }

    public int getTotalMonth() {
        return totalMonth;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public String getWithdrawBankAccount() {
        return withdrawBankAccount;
    }

    public String getRepaymentBankAccount() {
        return repaymentBankAccount;
    }

    public String getRepaymentType() {
        return repaymentType;
    }
}
