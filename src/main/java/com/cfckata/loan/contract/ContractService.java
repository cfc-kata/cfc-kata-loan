package com.cfckata.loan.contract;

import com.cfckata.loan.contract.domain.Contract;
import com.cfckata.loan.contract.domain.ContractBuilder;
import com.cfckata.loan.contract.domain.ContractStatus;
import com.cfckata.loan.contract.domain.RepaymentType;
import com.cfckata.loan.contract.request.CreateContractRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class ContractService {
    private ContractIdGenerator idGenerator;

    public ContractService(ContractIdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    public Contract createContract(CreateContractRequest request) {
        Contract contract = new ContractBuilder()
                .setId(idGenerator.generateId())
                .setCommitment(request.getCommitment())
                .setCreatedAt(LocalDateTime.now())
                .setCustomer(request.getLoanCustomer())
                .setInterestRate(request.getInterestRate())
                .setMaturityDate(LocalDate.parse(request.getMaturityDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .setRepaymentType(RepaymentType.valueOf(request.getRepaymentType()))
                .setStatus(ContractStatus.ACTIVE)
                .createContract();

        return contract;
    }
}