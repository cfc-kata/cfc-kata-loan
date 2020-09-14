package com.cfckata.loan.contract;

import com.cfckata.loan.contract.domain.Contract;
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

    private ContractService contractService;

    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateContractResponse createContract(@RequestBody CreateContractRequest request) {
        Contract contract = contractService.createContract(request);
        return new CreateContractResponse(contract.getId());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ContractResponse queryContract(@PathVariable String id) {
        Contract contract = contractService.findContractById(id);

        return new ContractResponse(contract);
    }
}
