package com.cfckata.loan.loan;

import com.cfckata.loan.contract.domain.RepaymentType;
import com.cfckata.loan.loan.domain.Loan;
import com.cfckata.loan.loan.domain.LoanBuilder;
import com.cfckata.loan.loan.request.CreateLoanRequest;
import com.github.meixuesong.aggregatepersistence.Versionable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class LoanFactory {
    private LoanIdGenerator idGenerator;

    public LoanFactory(LoanIdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    public Loan createLoan(CreateLoanRequest request) {
        return new LoanBuilder()
                .setId(idGenerator.nextId())
                .setContractId(request.getContractId())
                .setApplyAmount(request.getApplyAmount())
                .setInterestRate(request.getInterestRate())
                .setRepaymentBankAccount(request.getRepaymentBankAccount())
                .setRepaymentType(RepaymentType.valueOf(request.getRepaymentType()))
                .setTotalMonth(request.getTotalMonth())
                .setVersion(Versionable.NEW_VERSION)
                .setWithdrawBankAccount(request.getWithdrawBankAccount())
                .setRepaymentPlans(new ArrayList<>())
                .setCreatedAt(LocalDateTime.now())
                .createLoan();
    }
}
