package com.cfckata.loan.loan;

import com.cfckata.loan.loan.domain.Loan;
import com.cfckata.loan.loan.request.CreateLoanRequest;
import org.springframework.stereotype.Service;

@Service
public class LoanService {

    private LoanFactory factory;

    public LoanService(LoanFactory factory) {
        this.factory = factory;
    }

    public Loan createLoan(CreateLoanRequest request) {
        Loan loan = factory.createLoan(request);

        return loan;
    }
}
