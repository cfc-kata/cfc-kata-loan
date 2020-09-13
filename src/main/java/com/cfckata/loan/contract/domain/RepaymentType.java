package com.cfckata.loan.contract.domain;

public enum RepaymentType {
    DEBJ("等额本金"),
    DEBX("等额本息");

    final String displayName;

    RepaymentType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
