package com.cfckata.loan.contract.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ContractTest {
    @Test
    void should_create_contract() {
        LocalDateTime now = LocalDateTime.now();

        String idNumber = generateIdNumber(now.toLocalDate(), 18);
        Contract contract = new ContractBuilder()
                .setCreatedAt(now)
                .setCustomer(new LoanCustomer("", "", idNumber, ""))
                .setInterestRate(BigDecimal.TEN)
                .setMaturityDate(now.plusYears(1).toLocalDate())
                .setCommitment(BigDecimal.valueOf(1000.00))
                .createContract();

        assertNotNull(contract);
    }

    @Test
    void customer_shoud_over_18_years_old() {
        LocalDateTime now = LocalDateTime.now();
        String idNumber = generateIdNumber(now.toLocalDate(), 17);

        ContractBuilder contractBuilder = new ContractBuilder()
                .setCreatedAt(now)
                .setCustomer(new LoanCustomer("", "", idNumber, ""))
                .setMaturityDate(now.toLocalDate().plusYears(1))
                .setCommitment(BigDecimal.TEN);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            contractBuilder.createContract();
        });

        assertThat(exception.getMessage()).contains("18 years");
    }

    @Test
    void maturity_date_should_less_then_2_year() {
        LocalDateTime now = LocalDateTime.now();
        String idNumber = generateIdNumber(now.toLocalDate(), 18);

        ContractBuilder contractBuilder = new ContractBuilder()
                .setCreatedAt(now)
                .setCustomer(new LoanCustomer("", "", idNumber, ""))
                .setInterestRate(BigDecimal.TEN)
                .setMaturityDate(now.plusYears(3).toLocalDate())
                .setCommitment(BigDecimal.valueOf(1000.00));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                    contractBuilder.createContract();
        });

        assertThat(exception.getMessage()).contains("Maturity date");
    }

    //年龄与额度的关系： [18-20]: 1万；(20-30]:5万；(30-50]:20万；(50-60]:3万；(60-70]:1万；(70-]:0；
    @ParameterizedTest
    @CsvSource({
            "17, 1, Fail",
            "18, 1, Success",
            "20, 1, Success",
            "20, 2, Fail",
            "21, 5, Success",
            "30, 5, Success",
            "30, 6, Fail",
            "31, 20, Success",
            "50, 20, Success",
            "50, 21, Fail",
            "51, 3, Success",
            "60, 3, Success",
            "60, 4, Fail",
            "61, 1, Success",
            "70, 1, Success",
            "70, 2, Fail",
            "71, 1, Fail"
    })
    void should_have_correct_commitment(int age, int commitment, String expectedResult) {
        if (expectedResult.equalsIgnoreCase("Success")) {
            Contract contract = createContract(age, commitment);
            assertNotNull(contract);
        } else {
            assertThrows(IllegalArgumentException.class, () -> {
                createContract(age, commitment);
            });
        }
    }

    @Test
    void test_age_calculate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate birthday = LocalDate.parse("2000-01-01", formatter);

        assertEquals(1, birthday.until(LocalDate.parse("2001-01-01", formatter)).getYears());
        assertEquals(0, birthday.until(LocalDate.parse("2000-12-31", formatter)).getYears());
        assertEquals(18, birthday.until(LocalDate.parse("2018-01-01", formatter)).getYears());
        assertEquals(18, birthday.until(LocalDate.parse("2018-12-31", formatter)).getYears());
        assertEquals(19, birthday.until(LocalDate.parse("2019-01-01", formatter)).getYears());
    }

    private Contract createContract(int age, int commitment) {
        LocalDateTime now = LocalDateTime.now();
        String idNumber = generateIdNumber(now.toLocalDate(), age);
        return new ContractBuilder()
                .setCreatedAt(now)
                .setCustomer(new LoanCustomer("", "", idNumber, ""))
                .setInterestRate(BigDecimal.TEN)
                .setMaturityDate(now.plusYears(1).toLocalDate())
                .setCommitment(BigDecimal.valueOf(commitment *  10000.00))
                .createContract();
    }


    private String generateIdNumber(LocalDate now, int yearsOld) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMdd");
        return "123456" + df.format(now.minusYears(yearsOld)) + "1234";
    }
}
