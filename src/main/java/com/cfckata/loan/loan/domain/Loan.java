package com.cfckata.loan.loan.domain;

import com.cfckata.exception.BusinessException;
import com.cfckata.loan.contract.domain.RepaymentType;
import com.github.meixuesong.aggregatepersistence.Versionable;

import javax.persistence.Transient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class Loan implements Versionable, Serializable {
    private String id;
    private LocalDateTime createdAt;
    private String contractId;
    private BigDecimal applyAmount;
    private Integer totalMonth;
    private BigDecimal interestRate;
    private String withdrawBankAccount;
    private String repaymentBank;
    private RepaymentType repaymentType;
    private List<RepaymentPlan> repaymentPlans;
    @Transient
    private RepaymentPlanCalculator calculator;
    private int version;

    Loan(String id, LocalDateTime createdAt, String contractId, BigDecimal applyAmount, int totalMonth,
         BigDecimal interestRate, String withdrawBankAccount, String repaymentBank,
         RepaymentType repaymentType, List<RepaymentPlan> repaymentPlans, int version) {
        this.id = id;
        this.createdAt = createdAt;
        this.contractId = contractId;
        this.applyAmount = applyAmount;
        this.totalMonth = totalMonth;
        this.interestRate = interestRate;
        this.withdrawBankAccount = withdrawBankAccount;
        this.repaymentBank = repaymentBank;
        this.repaymentType = repaymentType;
        this.repaymentPlans = repaymentPlans;

        if (this.repaymentType == RepaymentType.DEBX) {
            calculator = new AverageCapitalPlusInterestCalculator();
        } else {
            throw new BusinessException("100001", "当前只支持等额本息");
        }

        this.version = version;
    }

    void validate() {
        if (createdAt == null) {
            throw new IllegalArgumentException("CreatedAt is required.");
        }
    }

    void calculateRepaymentPlan() {
        this.repaymentPlans = calculator.calculateRepaymentPlan(this);
    }

    @Override
    public int getVersion() {
        return 0;
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getContractId() {
        return contractId;
    }

    public BigDecimal getApplyAmount() {
        return applyAmount;
    }

    public Integer getTotalMonth() {
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