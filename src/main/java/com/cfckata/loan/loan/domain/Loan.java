package com.cfckata.loan.loan.domain;

import com.cfckata.loan.contract.domain.RepaymentType;
import com.github.meixuesong.aggregatepersistence.Versionable;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class Loan implements Versionable, Serializable {
    private String id;
    private String contractId;
    private BigDecimal applyAmount;
    private int totalMonth;
    private BigDecimal interestRate;
    private String withdrawBankAccount;
    private String repaymentBank;
    private RepaymentType repaymentType;
    private List<RepaymentPlan> repaymentPlans;
    private int version;

    Loan(String id, String contractId, BigDecimal applyAmount, int totalMonth,
                BigDecimal interestRate, String withdrawBankAccount, String repaymentBank,
                RepaymentType repaymentType, List<RepaymentPlan> repaymentPlans, int version) {
        this.id = id;
        this.contractId = contractId;
        this.applyAmount = applyAmount;
        this.totalMonth = totalMonth;
        this.interestRate = interestRate;
        this.withdrawBankAccount = withdrawBankAccount;
        this.repaymentBank = repaymentBank;
        this.repaymentType = repaymentType;
        this.repaymentPlans = repaymentPlans;
        this.version = version;
    }

    @Override
    public int getVersion() {
        return 0;
    }

    public String getId() {
        return id;
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

    public String getRepaymentBank() {
        return repaymentBank;
    }

    public RepaymentType getRepaymentType() {
        return repaymentType;
    }

    public List<RepaymentPlan> getRepaymentPlans() {
        return repaymentPlans;
    }
}
