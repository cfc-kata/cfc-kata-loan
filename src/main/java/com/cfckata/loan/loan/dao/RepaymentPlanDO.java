package com.cfckata.loan.loan.dao;

import com.cfckata.common.LocalDateTimeUtils;
import com.cfckata.loan.loan.domain.RepaymentPlan;
import com.cfckata.loan.loan.domain.RepaymentPlanStatus;

import java.math.BigDecimal;
import java.util.Date;

public class RepaymentPlanDO extends RepaymentPlanDOKey {
    private Date payableDate;

    private BigDecimal payableAmount;

    private BigDecimal payableInterest;

    private BigDecimal payableCapital;

    private String status;

    public RepaymentPlanDO() {
    }

    public RepaymentPlanDO(String loanId, RepaymentPlan repaymentPlan) {
        setLoanId(loanId);
        setPlanNo(repaymentPlan.getNo());
        payableDate = LocalDateTimeUtils.toDate(repaymentPlan.getPayableDate().atStartOfDay());
        payableAmount = repaymentPlan.getPayableAmount();
        payableInterest = repaymentPlan.getPayableInterest();
        payableCapital = repaymentPlan.getPayableCapital();
        status = repaymentPlan.getStatus().name();
    }

    public Date getPayableDate() {
        return payableDate;
    }

    public void setPayableDate(Date payableDate) {
        this.payableDate = payableDate;
    }

    public BigDecimal getPayableAmount() {
        return payableAmount;
    }

    public void setPayableAmount(BigDecimal payableAmount) {
        this.payableAmount = payableAmount;
    }

    public BigDecimal getPayableInterest() {
        return payableInterest;
    }

    public void setPayableInterest(BigDecimal payableInterest) {
        this.payableInterest = payableInterest;
    }

    public BigDecimal getPayableCapital() {
        return payableCapital;
    }

    public void setPayableCapital(BigDecimal payableCapital) {
        this.payableCapital = payableCapital;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public RepaymentPlan toEntity() {
        return new RepaymentPlan(
                getPlanNo(),
                LocalDateTimeUtils.toLocalDateTime(getPayableDate()).toLocalDate(),
                getPayableAmount(),
                getPayableInterest(),
                getPayableCapital(),
                RepaymentPlanStatus.valueOf(getStatus())
        );
    }
}
