package com.cfckata.loan.loan;

import com.cfckata.exception.BusinessException;
import com.cfckata.loan.contract.ContractService;
import com.cfckata.loan.loan.domain.Loan;
import com.cfckata.loan.loan.request.CreateLoanRequest;
import com.cfckata.loan.proxy.UnionPayFailedException;
import com.cfckata.loan.proxy.UnionPayProxy;
import com.github.meixuesong.aggregatepersistence.Aggregate;
import com.github.meixuesong.aggregatepersistence.AggregateFactory;
import org.springframework.stereotype.Service;

@Service
public class LoanService {
    private LoanFactory factory;
    private LoanRepository repository;
    private ContractService contractService;
    private UnionPayProxy unionPayProxy;

    public LoanService(LoanFactory factory, LoanRepository repository, ContractService contractService, UnionPayProxy unionPayProxy) {
        this.factory = factory;
        this.repository = repository;
        this.contractService = contractService;
        this.unionPayProxy = unionPayProxy;
    }

    public Loan createLoan(CreateLoanRequest request) {
        Loan loan = factory.createLoan(request);

        boolean preserved = contractService.preserveCommitment(loan.getContractId(), loan.getId(), loan.getApplyAmount());
        if (!preserved) {
            throw new BusinessException("合同额度错误码", "合同额度已经用完。");
        }

        try {
            unionPayProxy.pay(loan.getId(), loan.getApplyAmount(), loan.getWithdrawBankAccount());
        } catch (UnionPayFailedException e) {
            contractService.cancelPreserveCommitment(loan.getId());
            throw new BusinessException("银联支付失败错误码", "银联支付失败");
        }

        repository.save(AggregateFactory.createAggregate(loan));

        return repository.findById(loan.getId()).getRoot();
    }

    public Loan findById(String id) {
        return repository.findById(id).getRoot();
    }
}
