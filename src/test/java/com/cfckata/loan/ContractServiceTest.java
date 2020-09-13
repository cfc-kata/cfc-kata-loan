package com.cfckata.loan;

import com.cfckata.loan.contract.ContractIdGenerator;
import com.cfckata.loan.contract.ContractService;
import com.cfckata.loan.contract.domain.Contract;
import com.cfckata.loan.contract.request.CreateContractRequest;
import com.cfckata.loan.customer.LoanCustomer;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ContractServiceTest {

    @Test
    void should_create_contract() {
        String customerId = "BJ001";
        CreateContractRequest request = new CreateContractRequest(
                new LoanCustomer(customerId, "张三", "101010200001012356", "18812345678"),
                new BigDecimal("9.9"), "DEBX", "2022-05-01", new BigDecimal("9000.00"));

        ContractService service = new ContractService(new ContractIdGenerator());
        Contract contract = service.createContract(request);
        assertNotNull(contract.getId());
        assertEquals(customerId, contract.getCustomer().getId());
    }
}
