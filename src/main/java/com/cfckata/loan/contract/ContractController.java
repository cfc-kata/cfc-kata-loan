package com.cfckata.loan.contract;

import com.cfckata.loan.contract.request.CreateContractRequest;
import com.cfckata.loan.contract.response.ContractResponse;
import com.cfckata.loan.contract.response.CreateContractResponse;
import com.cfckata.loan.customer.LoanCustomer;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/contracts")
public class ContractController {
    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateContractResponse createContract(@RequestBody CreateContractRequest request) {
        return new CreateContractResponse("001");
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ContractResponse queryContract(@PathVariable String id) {
        ContractResponse response = new ContractResponse(id,
                new LoanCustomer("BJ001", "张三", "101010200001012356", "18812345678"),
                new BigDecimal("9.9"), "DEBX", "2022-05-01", new BigDecimal("9000.00"), "ACTIVE");

        return response;
    }
}
