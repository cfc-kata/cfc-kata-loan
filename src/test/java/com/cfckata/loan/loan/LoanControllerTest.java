package com.cfckata.loan.loan;

import com.cfckata.common.ApiTest;
import com.cfckata.loan.loan.request.CreateLoanRequest;
import com.cfckata.loan.loan.response.CreateLoanResponse;
import com.cfckata.loan.loan.response.LoanResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class LoanControllerTest extends ApiTest {
    @Test
    void should_create_loan() {
        CreateLoanRequest request = new CreateLoanRequest("HT-0000000001", new BigDecimal("3000"),
                12, new BigDecimal("9.9"), "QK_card", "HK_card", "DEBX");

        ResponseEntity<CreateLoanResponse> responseEntity = this.restTemplate.postForEntity(baseUrl + "/loans/", request, CreateLoanResponse.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getBody().getLoanId()).isNotEmpty();
    }

    @Test
    void should_query_loan_detail() {
        String loanId = "JT-001";

        ResponseEntity<LoanResponse> responseEntity = this.restTemplate.getForEntity(baseUrl + "/loans/" + loanId, LoanResponse.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().getLoanId()).isEqualTo(loanId);
    }

}
