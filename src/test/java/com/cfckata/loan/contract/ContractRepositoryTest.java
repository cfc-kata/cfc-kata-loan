package com.cfckata.loan.contract;

import com.cfckata.common.JsonComparator;
import com.cfckata.common.RepositoryTest;
import com.cfckata.loan.contract.domain.Contract;
import com.cfckata.loan.contract.domain.ContractBuilder;
import com.cfckata.loan.contract.domain.ContractStatus;
import com.cfckata.loan.contract.domain.RepaymentType;
import com.cfckata.loan.contract.domain.LoanCustomer;
import com.github.meixuesong.aggregatepersistence.Aggregate;
import com.github.meixuesong.aggregatepersistence.AggregateFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ContractRepositoryTest extends RepositoryTest {
    @Autowired
    private ContractRepository repository;

    @Test
    void should_save_contract() {
        LocalDateTime now = LocalDateTime.now();

        String idNumber = generateIdNumber(now.toLocalDate(), 18);
        Contract contract = new ContractBuilder()
                .setId("HT-001")
                .setCreatedAt(now)
                .setCustomer(new LoanCustomer("", "", idNumber, ""))
                .setInterestRate(new BigDecimal("9.99"))
                .setRepaymentType(RepaymentType.DEBJ)
                .setStatus(ContractStatus.ACTIVE)
                .setMaturityDate(now.plusYears(1).toLocalDate())
                .setCommitment(new BigDecimal("1000"))
                .createContract();

        repository.save(AggregateFactory.createAggregate(contract));
        Aggregate<Contract> aggregate = repository.findById(contract.getId());

        JsonComparator.assertEqualsObjects(contract, aggregate.getRoot(),
                new JsonComparator.IgnoreFieldSettings().addField(Contract.class, "version").getClassIgnoreFieldNames());
    }

    @Test
    @Sql(scripts = "classpath:sql/contract-test-before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:sql/contract-test-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void should_increase_version() {
        String contractId = "HT-001";
        Aggregate<Contract> aggregate = repository.findById(contractId);
        int version = aggregate.getRoot().getVersion();
        aggregate.getRoot().setCommitment(new BigDecimal("666"));

        repository.save(aggregate);

        Aggregate<Contract> aggregateAfter = repository.findById(contractId);
        assertThat(aggregateAfter.getRoot().getVersion()).isEqualTo(version + 1);
    }

    @Test
    @Sql(scripts = "classpath:sql/contract-test-before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:sql/contract-test-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void should_remove_contract() {
        String contractId = "HT-001";
        Aggregate<Contract> aggregate = repository.findById(contractId);
        aggregate.getRoot().setCommitment(new BigDecimal("666"));

        repository.remove(aggregate);

        assertThrows(EntityNotFoundException.class, () -> {
            repository.findById(contractId);
        });
    }

    private String generateIdNumber(LocalDate now, int yearsOld) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMdd");
        return "123456" + df.format(now.minusYears(yearsOld)) + "1234";
    }

}
