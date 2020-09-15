package com.cfckata.loan.loan;

import com.cfckata.common.ApiTest;
import com.cfckata.loan.contract.ContractService;
import com.cfckata.loan.loan.request.CreateLoanRequest;
import com.cfckata.loan.loan.response.CreateLoanResponse;
import com.cfckata.loan.loan.response.LoanResponse;
import com.cfckata.loan.proxy.UnionPayProxy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class LoanControllerTest extends ApiTest {
    @MockBean
    private ContractService contractService;
    @MockBean
    private UnionPayProxy proxy;

    @Override
    @BeforeEach
    public void setUp() throws Exception {
        super.setUp();
        when(contractService.preserveCommitment(anyString(), anyString(), any())).thenReturn(true);
        doNothing().when(proxy).pay(anyString(), any(), anyString());
    }

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
