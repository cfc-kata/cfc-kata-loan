package com.cfckata.loan.loan.domain;

import com.cfckata.loan.contract.domain.RepaymentType;
import com.github.meixuesong.aggregatepersistence.Versionable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class LoanBuilder {
    private String id;
    private LocalDateTime createdAt;
    private String contractId;
    private BigDecimal applyAmount;
    private int totalMonth;
    private BigDecimal interestRate;
    private String withdrawBankAccount;
    private String repaymentBankAccount;
    private RepaymentType repaymentType;
    private List<RepaymentPlan> repaymentPlans = new ArrayList<>();
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

    public LoanBuilder setRepaymentBankAccount(String repaymentBankAccount) {
        this.repaymentBankAccount = repaymentBankAccount;
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

    public LoanBuilder setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt.withNano((createdAt.getNano() / (1000 * 1000)) * 1000 * 1000);
        return this;
    }

    public Loan createLoan() {
        Loan loan = new Loan(id, createdAt, contractId, applyAmount, totalMonth, interestRate, withdrawBankAccount,
                repaymentBankAccount, repaymentType, repaymentPlans, version);

        loan.validate();

        if (repaymentPlans.size() == 0) {
            loan.calculateRepaymentPlan();
        }

        return loan;
    }
}
