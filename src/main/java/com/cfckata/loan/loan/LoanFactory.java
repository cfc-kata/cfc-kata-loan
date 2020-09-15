package com.cfckata.loan.loan;

import com.cfckata.common.LocalDateTimeUtils;
import com.cfckata.loan.contract.domain.RepaymentType;
import com.cfckata.loan.loan.dao.LoanDO;
import com.cfckata.loan.loan.dao.RepaymentPlanDO;
import com.cfckata.loan.loan.domain.Loan;
import com.cfckata.loan.loan.domain.LoanBuilder;
import com.cfckata.loan.loan.domain.RepaymentPlan;
import com.cfckata.loan.loan.request.CreateLoanRequest;
import com.github.meixuesong.aggregatepersistence.Versionable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public Loan createLoan(LoanDO loanDO, List<RepaymentPlanDO> planDOList) {
        LoanBuilder builder = createLoanBuilder(loanDO);
        builder.setRepaymentPlans(createPlans(planDOList));

        return builder.createLoan();
    }

    private List<RepaymentPlan> createPlans(List<RepaymentPlanDO> planDOList) {
        return planDOList.stream().map(d -> d.toEntity()).collect(Collectors.toList());
    }

    private LoanBuilder createLoanBuilder(LoanDO loanDO) {
        LoanBuilder builder = new LoanBuilder()
                .setId(loanDO.getId())
                .setContractId(loanDO.getContractId())
                .setCreatedAt(LocalDateTimeUtils.toLocalDateTime(loanDO.getCreatedAt()))
                .setApplyAmount(loanDO.getApplyAmount())
                .setTotalMonth(loanDO.getTotalMonth())
                .setInterestRate(loanDO.getInterestRate())
                .setWithdrawBankAccount(loanDO.getWithdrawBankAccount())
                .setRepaymentBankAccount(loanDO.getRepaymentBankAccount())
                .setRepaymentType(RepaymentType.valueOf(loanDO.getRepaymentType()))
                .setVersion(loanDO.getVersion());

        return builder;
    }

}
