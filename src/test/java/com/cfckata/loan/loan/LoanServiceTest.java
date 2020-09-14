package com.cfckata.loan.loan;

import com.cfckata.loan.loan.domain.Loan;
import com.cfckata.loan.loan.request.CreateLoanRequest;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LoanServiceTest {
    @Test
    void should_create_loan() {
        CreateLoanRequest request = new CreateLoanRequest("HT-0000000001", new BigDecimal("3000"),
                12, new BigDecimal("9.9"), "QK_card", "HK_card", "DEBX");

        LoanService service = new LoanService(new LoanFactory(new LoanIdGenerator()));
        Loan loan = service.createLoan(request);

        assertNotNull(loan);
    }
}
