package com.cfckata.loan.loan.response;

import java.math.BigDecimal;
import java.util.List;

public class LoanResponse {
    private String loanId;
    private String contractId;
    private BigDecimal applyAmount;
    private int totalMonth;
    private BigDecimal interestRate;
    private String withdrawBankAccount;
    private String repaymentBankAccount;
    private String repaymentType;
    private List<RepaymentPlanResponse> repaymentPlans;

    public LoanResponse() {
    }

    public LoanResponse(String loanId, String contractId, BigDecimal applyAmount, int totalMonth,
                        BigDecimal interestRate, String withdrawBankAccount, String repaymentBankAccount,
                        String repaymentType, List<RepaymentPlanResponse> repaymentPlans) {
        this.loanId = loanId;
        this.contractId = contractId;
        this.applyAmount = applyAmount;
        this.totalMonth = totalMonth;
        this.interestRate = interestRate;
        this.withdrawBankAccount = withdrawBankAccount;
        this.repaymentBankAccount = repaymentBankAccount;
        this.repaymentType = repaymentType;
        this.repaymentPlans = repaymentPlans;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
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

    public int getTotalMonth() {
        return totalMonth;
    }

    public void setTotalMonth(int totalMonth) {
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

    public List<RepaymentPlanResponse> getRepaymentPlans() {
        return repaymentPlans;
    }

    public void setRepaymentPlans(List<RepaymentPlanResponse> repaymentPlans) {
        this.repaymentPlans = repaymentPlans;
    }
}
