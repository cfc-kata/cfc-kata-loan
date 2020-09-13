package com.cfckata.loan.contract;

import com.cfckata.common.JsonComparator;
import com.cfckata.common.RepositoryTest;
import com.cfckata.loan.contract.domain.Contract;
import com.cfckata.loan.contract.domain.ContractBuilder;
import com.cfckata.loan.contract.domain.ContractStatus;
import com.cfckata.loan.contract.domain.RepaymentType;
import com.cfckata.loan.customer.LoanCustomer;
import com.github.meixuesong.aggregatepersistence.Aggregate;
import com.github.meixuesong.aggregatepersistence.AggregateFactory;
import com.github.meixuesong.aggregatepersistence.DataObjectUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

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
                .setCommitment(new BigDecimal("1000.00"))
                .createContract();

        repository.save(AggregateFactory.createAggregate(contract));
        Aggregate<Contract> aggregate = repository.findById(contract.getId());

        JsonComparator.assertEqualsObjects(contract, aggregate.getRoot());

    }

    private String generateIdNumber(LocalDate now, int yearsOld) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMdd");
        return "123456" + df.format(now.minusYears(yearsOld)) + "1234";
    }

}
