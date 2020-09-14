package com.cfckata.loan.loan;

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
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateLoanResponse createContract(@RequestBody CreateLoanRequest request) {
        return new CreateLoanResponse("JT-001");
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LoanResponse queryLoan(@PathVariable String id) {
        return new LoanResponse("JT-001", "HT-001", new BigDecimal("3000"), 12, new BigDecimal("9.9"),
                "QK_CARD", "KK_CARD", "DEBX",
                Arrays.asList(new RepaymentPlanResponse("PLANID", 1, "2020-01-01",
                        new BigDecimal("366"), new BigDecimal("300"), new BigDecimal("66"),
                        "PLAN")));
    }

}
