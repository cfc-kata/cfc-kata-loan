package com.cfckata.loan.loan.response;

import com.cfckata.loan.loan.domain.Loan;

public class CreateLoanResponse {
    private String loanId;

    public CreateLoanResponse() {
    }

    public CreateLoanResponse(String loanId) {
        this.loanId = loanId;
    }

    public CreateLoanResponse(Loan loan) {
        this.loanId = loan.getId();
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }
}
