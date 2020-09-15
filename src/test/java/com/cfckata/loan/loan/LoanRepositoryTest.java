package com.cfckata.loan.loan;

import com.cfckata.common.JsonComparator;
import com.cfckata.common.RepositoryTest;
import com.cfckata.loan.contract.domain.RepaymentType;
import com.cfckata.loan.loan.domain.Loan;
import com.cfckata.loan.loan.domain.LoanBuilder;
import com.cfckata.loan.loan.domain.RepaymentPlan;
import com.github.meixuesong.aggregatepersistence.Aggregate;
import com.github.meixuesong.aggregatepersistence.AggregateFactory;
import com.github.meixuesong.aggregatepersistence.Versionable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LoanRepositoryTest extends RepositoryTest {
    @Autowired
    private LoanRepository repository;
    private Loan loan;

    @BeforeEach
    void setUp() {
        loan = new LoanBuilder()
                .setId("JT-001")
                .setContractId("HT-001")
                .setCreatedAt(LocalDateTime.now())
                .setApplyAmount(new BigDecimal("10000.00"))
                .setInterestRate(new BigDecimal("9.90"))
                .setRepaymentBankAccount("AAA")
                .setRepaymentType(RepaymentType.valueOf("DEBX"))
                .setTotalMonth(12)
                .setVersion(Versionable.NEW_VERSION)
                .setWithdrawBankAccount("BBB")
                .setRepaymentPlans(new ArrayList<>())
                .createLoan();
    }

    @Test
    void should_save_new_loan() {
        repository.save(AggregateFactory.createAggregate(loan));
        Aggregate<Loan> aggregate = repository.findById(loan.getId());

        JsonComparator.assertEqualsObjects(loan, aggregate.getRoot(),
                new JsonComparator.IgnoreFieldSettings().addField(Loan.class, "version")
                        .addField(RepaymentPlan.class, "newPlan")
                        .getClassIgnoreFieldNames());
    }

    @Test
    void should_update_repayment_plan() {
        repository.save(AggregateFactory.createAggregate(loan));
        Aggregate<Loan> expected = repository.findById(loan.getId());

        expected.getRoot().payPlan(1);
        repository.save(expected);

        Aggregate<Loan> actual = repository.findById(loan.getId());
        JsonComparator.assertEqualsObjects(expected.getRoot(), actual.getRoot(),
                new JsonComparator.IgnoreFieldSettings().addField(Loan.class, "version")
                        .addField(RepaymentPlan.class, "newPlan")
                        .getClassIgnoreFieldNames());
    }

    @Test
    void should_insert_new_repayment_plan() {
        repository.save(AggregateFactory.createAggregate(loan));
        Aggregate<Loan> expected = repository.findById(loan.getId());

        expected.getRoot().changeTotalMonth(24);
        repository.save(expected);

        Aggregate<Loan> actual = repository.findById(loan.getId());
        assertEquals(24, actual.getRoot().getRepaymentPlans().size());
        JsonComparator.assertEqualsObjects(expected.getRoot(), actual.getRoot(),
                new JsonComparator.IgnoreFieldSettings().addField(Loan.class, "version")
                        .getClassIgnoreFieldNames());
    }

    @Test
    void should_delete_loan() {
        String loanId = loan.getId();
        Aggregate<Loan> aggregate = AggregateFactory.createAggregate(loan);
        repository.save(aggregate);
        aggregate = repository.findById(loanId);
        repository.remove(aggregate);

        assertThrows(EntityNotFoundException.class, () -> {
            repository.findById(loanId);
        });
    }
}
