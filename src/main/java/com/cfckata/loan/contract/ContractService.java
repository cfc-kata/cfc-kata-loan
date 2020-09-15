package com.cfckata.loan.contract;

import com.cfckata.loan.contract.domain.Contract;
import com.cfckata.loan.contract.domain.ContractBuilder;
import com.cfckata.loan.contract.domain.ContractStatus;
import com.cfckata.loan.contract.domain.RepaymentType;
import com.cfckata.loan.contract.request.CreateContractRequest;
import com.github.meixuesong.aggregatepersistence.AggregateFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class ContractService {
    private ContractIdGenerator idGenerator;
    private ContractRepository repository;

    public ContractService(ContractIdGenerator idGenerator, ContractRepository repository) {
        this.idGenerator = idGenerator;
        this.repository = repository;
    }

    public Contract createContract(CreateContractRequest request) {
        Contract contract = new ContractBuilder()
                .setId(idGenerator.generateId())
                .setCommitment(request.getCommitment())
                .setCreatedAt(LocalDateTime.now())
                .setCustomer(request.getCustomer())
                .setInterestRate(request.getInterestRate())
                .setMaturityDate(LocalDate.parse(request.getMaturityDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .setRepaymentType(RepaymentType.valueOf(request.getRepaymentType()))
                .setStatus(ContractStatus.ACTIVE)
                .createContract();

        repository.save(AggregateFactory.createAggregate(contract));

        return findContractById(contract.getId());
    }

    public Contract findContractById(String id) {
        return repository.findById(id).getRoot();
    }

    /**
     * 预留额度。在放款前要预留额度，如果预留成功才可以继续放款。
     * @param contractId 合同号
     * @param loanId 借条号
     * @param applyAmount 预留金额
     * @return
     */
    public boolean preserveCommitment(String contractId, String loanId, BigDecimal applyAmount) {
        return false;
    }

    /**
     * 释放预留的额度。当借条已经还完，或者放款过程异常，需要回复之前预留的额度
     * @param loanId 借条号
     */
    public void cancelPreserveCommitment(String loanId) {

    }
}
