package com.cfckata.loan.loan.domain;

import com.cfckata.loan.contract.domain.RepaymentType;
import com.github.meixuesong.aggregatepersistence.Versionable;

import java.math.BigDecimal;
import java.util.List;

public class LoanBuilder {
    private String id;
    private String contractId;
    private BigDecimal applyAmount;
    private int totalMonth;
    private BigDecimal interestRate;
    private String withdrawBankAccount;
    private String repaymentBank;
    private RepaymentType repaymentType;
    private List<RepaymentPlan> repaymentPlans;
    private int version = Versionable.NEW_VERSION;

    public LoanBuilder setId(String id) {
        this.id = id;
        return this;
    }

    public LoanBuilder setContractId(String contractId) {
        this.contractId = contractId;
        return this;
    }

    public LoanBuilder setApplyAmount(BigDecimal applyAmount) {
        this.applyAmount = applyAmount;
        return this;
    }

    public LoanBuilder setTotalMonth(int totalMonth) {
        this.totalMonth = totalMonth;
        return this;
    }

    public LoanBuilder setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
        return this;
    }

    public LoanBuilder setWithdrawBankAccount(String withdrawBankAccount) {
        this.withdrawBankAccount = withdrawBankAccount;
        return this;
    }

    public LoanBuilder setRepaymentBank(String repaymentBank) {
        this.repaymentBank = repaymentBank;
        return this;
    }

    public LoanBuilder setRepaymentType(RepaymentType repaymentType) {
        this.repaymentType = repaymentType;
        return this;
    }

    public LoanBuilder setRepaymentPlans(List<RepaymentPlan> repaymentPlans) {
        this.repaymentPlans = repaymentPlans;
        return this;
    }

    public LoanBuilder setVersion(int version) {
        this.version = version;
        return this;
    }

    public Loan createLoan() {
        Loan loan = new Loan(id, contractId, applyAmount, totalMonth, interestRate, withdrawBankAccount,
                repaymentBank, repaymentType, repaymentPlans, version);

        return loan;
    }
}
