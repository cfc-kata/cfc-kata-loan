package com.cfckata.loan.loan.dao;

public class RepaymentPlanDOKey {
    private String loanId;

    private Integer planNo;

    public RepaymentPlanDOKey() {
    }

    public RepaymentPlanDOKey(String loanId, Integer planNo) {
        this.loanId = loanId;
        this.planNo = planNo;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public Integer getPlanNo() {
        return planNo;
    }

    public void setPlanNo(Integer planNo) {
        this.planNo = planNo;
    }
}
