package com.cfckata.loan.contract.domain;

public enum ContractStatus {
    PENDING("待审批"),
    ACTIVE("激活"),
    DEACTIVE("吊销");

    final String displayName;

    ContractStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
