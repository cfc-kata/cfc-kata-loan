package com.cfckata.loan.loan;

import com.cfckata.loan.loan.dao.LoanDO;
import com.cfckata.loan.loan.dao.LoanDOMapper;
import com.cfckata.loan.loan.dao.RepaymentPlanDO;
import com.cfckata.loan.loan.dao.RepaymentPlanDOKey;
import com.cfckata.loan.loan.dao.RepaymentPlanDOMapper;
import com.cfckata.loan.loan.domain.Loan;
import com.cfckata.loan.loan.domain.RepaymentPlan;
import com.github.meixuesong.aggregatepersistence.Aggregate;
import com.github.meixuesong.aggregatepersistence.AggregateFactory;
import com.github.meixuesong.aggregatepersistence.DataObjectUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityNotFoundException;
import javax.persistence.OptimisticLockException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class LoanRepository {
    private LoanDOMapper loanMapper;
    private RepaymentPlanDOMapper planMapper;
    private LoanFactory factory;

    public LoanRepository(LoanDOMapper loanMapper, RepaymentPlanDOMapper planMapper, LoanFactory factory) {
        this.loanMapper = loanMapper;
        this.planMapper = planMapper;
        this.factory = factory;
    }

    public void save(Aggregate<Loan> aggregate) {
        if (aggregate.isNew()) {
            loanMapper.insert(new LoanDO(aggregate.getRoot()));
            List<RepaymentPlanDO> planDOList = aggregate.getRoot().getRepaymentPlans().stream()
                    .map(p -> new RepaymentPlanDO(aggregate.getRoot().getId(), p))
                    .collect(Collectors.toList());
            planMapper.insertAll(planDOList);
        } else if (aggregate.isChanged()) {
            updateLoan(aggregate);

            Map<Aggregate.DeltaType, Collection<RepaymentPlan>> collectionDelta = aggregate.findCollectionDelta(Loan::getRepaymentPlans, RepaymentPlan::getNo);
            removeRepaymentPlans(aggregate, collectionDelta.get(Aggregate.DeltaType.REMOVED));
            updateRepaymentPlans(aggregate, collectionDelta.get(Aggregate.DeltaType.UPDATED));
            insertNewRepaymentPlans(aggregate, collectionDelta.get(Aggregate.DeltaType.NEW));
        }
    }

    private void insertNewRepaymentPlans(Aggregate<Loan> aggregate, Collection<RepaymentPlan> repaymentPlans) {
        List<RepaymentPlanDO> itemDOs = repaymentPlans.stream()
                .map(p -> new RepaymentPlanDO(aggregate.getRoot().getId(), p))
                .collect(Collectors.toList());
        if (!itemDOs.isEmpty()) {
            planMapper.insertAll(itemDOs);
        }
    }

    private void updateRepaymentPlans(Aggregate<Loan> aggregate, Collection<RepaymentPlan> repaymentPlans) {
        repaymentPlans.stream().forEach(item -> {
            RepaymentPlanDO newRepaymentPlanDO = new RepaymentPlanDO(aggregate.getRoot().getId(), item);
            RepaymentPlanDO oldDo = new RepaymentPlanDO(aggregate.getRoot().getId(), findOldRepaymentPlan(aggregate, item.getNo()));
            Set<String> changedFields = DataObjectUtils.getChangedFields(oldDo, newRepaymentPlanDO);
            if (planMapper.updateByPrimaryKeySelective(newRepaymentPlanDO, changedFields) != 1) {
                throw new OptimisticLockException(
                        String.format("Update repayment_plan (loan_id=%s, plan_no=%d) error, it's not found or changed by another user", aggregate.getRoot().getId(), item.getNo()));
            }
        });
    }

    private RepaymentPlan findOldRepaymentPlan(Aggregate<Loan> aggregate, Integer planNo) {
        for (RepaymentPlan repaymentPlan : aggregate.getRootSnapshot().getRepaymentPlans()) {
            if (repaymentPlan.getNo().equals(planNo)) {
                return repaymentPlan;
            }
        }
        return null;
    }

    private void removeRepaymentPlans(Aggregate<Loan> aggregate, Collection<RepaymentPlan> removedEntities) {
        String loanId = aggregate.getRoot().getId();
        removedEntities.stream().forEach(item -> {
            if (planMapper.deleteByPrimaryKey(new RepaymentPlanDOKey(loanId, item.getNo())) != 1) {
                throw new OptimisticLockException(
                        String.format("Delete repayment_plan (loan_id=%s, plan_no=%d) error, it's not found or changed by another user", loanId, item.getNo()));
            }
        });
    }

    private void updateLoan(Aggregate<Loan> aggregate) {
        LoanDO current = new LoanDO(aggregate.getRoot());
        LoanDO old = new LoanDO(aggregate.getRootSnapshot());

        LoanDO delta = DataObjectUtils.getDelta(old, current);
        delta.setVersion(current.getVersion());
        delta.setId(current.getId());
        if (loanMapper.updateByPrimaryKeySelective(delta) != 1) {
            throw new OptimisticLockException(String.format("Update loan (%s) error, it's not found or changed by another user", aggregate.getRoot().getId()));
        }
    }

    public Aggregate<Loan> findById(String id) {
        LoanDO loanDO = loanMapper.selectByPrimaryKey(id);
        if (loanDO == null) {
            throw new EntityNotFoundException(String.format("Loan(%s) not found.", id));
        }

        List<RepaymentPlanDO> repaymentPlanDOS = planMapper.selectByLoanId(id);
        Loan loan = factory.createLoan(loanDO, repaymentPlanDOS);

        return AggregateFactory.createAggregate(loan);
    }

    public void remove(Aggregate<Loan> aggregate) {
        if (loanMapper.delete(new LoanDO(aggregate.getRoot())) != 1) {
            throw new OptimisticLockException(String.format("Delete loan (%s) error, it's not found or changed by another user", aggregate.getRoot().getId()));
        }

        planMapper.deleteByLoanId(aggregate.getRoot().getId());
    }
}
