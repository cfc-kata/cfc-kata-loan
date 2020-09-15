package com.cfckata.loan.loan.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

public class RepaymentPlan implements Serializable {
    private Integer no;
    private LocalDate payableDate;
    private BigDecimal payableAmount;
    private BigDecimal payableInterest;
    private BigDecimal payableCapital;
    private RepaymentPlanStatus status;

    public RepaymentPlan(Integer no, LocalDate payableDate, BigDecimal payableAmount,
                         BigDecimal payableInterest, BigDecimal payableCapital, RepaymentPlanStatus status) {
        this.no = no;
        this.payableDate = payableDate;
        this.payableAmount = payableAmount;
        this.payableInterest = payableInterest;
        this.payableCapital = payableCapital;
        this.status = status;
    }

    public Integer getNo() {
        return no;
    }

    public LocalDate getPayableDate() {
        return payableDate;
    }

    public BigDecimal getPayableAmount() {
        return payableAmount;
    }

    public BigDecimal getPayableInterest() {
        return payableInterest;
    }

    public BigDecimal getPayableCapital() {
        return payableCapital;
    }

    public RepaymentPlanStatus getStatus() {
        return status;
    }

    void setStatus(RepaymentPlanStatus status) {
        this.status = status;
    }
}
