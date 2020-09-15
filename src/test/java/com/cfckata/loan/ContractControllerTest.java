package com.cfckata.loan;

import com.cfckata.common.ApiTest;
import com.cfckata.exception.ErrorResponse;
import com.cfckata.loan.contract.request.CreateContractRequest;
import com.cfckata.loan.contract.response.ContractResponse;
import com.cfckata.loan.contract.response.CreateContractResponse;
import com.cfckata.loan.contract.domain.LoanCustomer;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class ContractControllerTest extends ApiTest {

    @Test
    void should_create_contract() {
        CreateContractRequest request = new CreateContractRequest(
                new LoanCustomer("BJ001", "张三", "101010200001012356", "18812345678"),
                new BigDecimal("9.9"), "DEBX", "2022-05-01", new BigDecimal("9000.00"));

        ResponseEntity<CreateContractResponse> responseEntity = this.restTemplate.postForEntity(baseUrl + "/contracts/", request, CreateContractResponse.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getBody().getContractId()).isNotEmpty();
    }

    @Test
    void should_failed_to_create_contract_when_commitment_is_too_large() {
        CreateContractRequest request = new CreateContractRequest(
                new LoanCustomer("BJ001", "张三", "101010200001012356", "18812345678"),
                new BigDecimal("9.9"), "DEBX", "2022-05-01", new BigDecimal("9000000000.00"));

        ResponseEntity<ErrorResponse> responseEntity = this.restTemplate.postForEntity(baseUrl + "/contracts/", request, ErrorResponse.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @Sql(scripts = "classpath:sql/contract-test-before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:sql/contract-test-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void should_query_contract_detail() {
        String contractId = "HT-001";

        ResponseEntity<ContractResponse> responseEntity = this.restTemplate.getForEntity(baseUrl + "/contracts/" + contractId, ContractResponse.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().getId()).isEqualTo(contractId);
    }
}
