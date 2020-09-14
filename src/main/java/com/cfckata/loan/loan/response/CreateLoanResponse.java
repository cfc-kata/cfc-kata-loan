package com.cfckata.loan.loan.response;

public class CreateLoanResponse {
    private String loanId;

    public CreateLoanResponse() {
    }

    public CreateLoanResponse(String loanId) {
        this.loanId = loanId;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }
}
