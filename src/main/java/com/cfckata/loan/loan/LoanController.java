package com.cfckata.loan.loan;

import com.cfckata.loan.loan.domain.Loan;
import com.cfckata.loan.loan.request.CreateLoanRequest;
import com.cfckata.loan.loan.response.CreateLoanResponse;
import com.cfckata.loan.loan.response.LoanResponse;
import com.cfckata.loan.loan.response.RepaymentPlanResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Arrays;

@RestController
@RequestMapping("/loans")
public class LoanController {

    private LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateLoanResponse createContract(@RequestBody CreateLoanRequest request) {
        Loan loan = loanService.createLoan(request);

        return new CreateLoanResponse(loan);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LoanResponse queryLoan(@PathVariable String id) {
        Loan loan = loanService.findById(id);

        return new LoanResponse(loan);
    }

}
