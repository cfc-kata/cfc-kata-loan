package com.cfckata.loan.loan.response;

import java.math.BigDecimal;

public class RepaymentPlanResponse {
    private String planId;
    private Integer periodNo;
    private String payableDate;
    private BigDecimal payableAmount;
    private BigDecimal payableInterest;
    private BigDecimal payableCapital;
    private String status;

    public RepaymentPlanResponse() {
    }

    public RepaymentPlanResponse(String planId, Integer periodNo, String payableDate, BigDecimal payableAmount,
                                 BigDecimal payableInterest, BigDecimal payableCapital, String status) {
        this.planId = planId;
        this.periodNo = periodNo;
        this.payableDate = payableDate;
        this.payableAmount = payableAmount;
        this.payableInterest = payableInterest;
        this.payableCapital = payableCapital;
        this.status = status;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public Integer getPeriodNo() {
        return periodNo;
    }

    public void setPeriodNo(Integer periodNo) {
        this.periodNo = periodNo;
    }

    public String getPayableDate() {
        return payableDate;
    }

    public void setPayableDate(String payableDate) {
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
}
