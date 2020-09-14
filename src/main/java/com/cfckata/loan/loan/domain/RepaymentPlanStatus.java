package com.cfckata.loan.loan.domain;

public enum RepaymentPlanStatus {
    PLAN("计划中"),
    PAID("以还款"),
    OVERDUE("逾期");

    private String displayName;

    RepaymentPlanStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
