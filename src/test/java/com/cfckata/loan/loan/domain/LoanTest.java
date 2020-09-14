package com.cfckata.loan.loan.domain;

import com.cfckata.loan.contract.domain.RepaymentType;
import com.github.meixuesong.aggregatepersistence.Versionable;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

class LoanTest {

    @Test
    /**
     * The expected result comes from https://www.cmbchina.com/CmbWebPubInfo/Cal_Loan_Per.aspx?chnl=dkjsq
     */
    void should_calculate_repayment_plan() {
        Loan loan = new LoanBuilder()
                .setId("JT-001")
                .setContractId("HT-001")
                .setCreatedAt(LocalDateTime.now())
                .setApplyAmount(new BigDecimal("10000"))
                .setInterestRate(new BigDecimal("9.9"))
                .setRepaymentBankAccount("AAA")
                .setRepaymentType(RepaymentType.valueOf("DEBX"))
                .setTotalMonth(12)
                .setVersion(Versionable.NEW_VERSION)
                .setWithdrawBankAccount("BBB")
                .setRepaymentPlans(new ArrayList<>())
                .createLoan();

        assertThat(loan.getRepaymentPlans().size()).isEqualTo(12);

        RepaymentPlan firstMonth = loan.getRepaymentPlans().get(0);
        assertThat(firstMonth.getPayableAmount()).isCloseTo(new BigDecimal("878.69"), Offset.offset(new BigDecimal("0.01")));
        assertThat(firstMonth.getPayableCapital()).isCloseTo(new BigDecimal("796.20"), Offset.offset(new BigDecimal("0.01")));
        assertThat(firstMonth.getPayableInterest()).isCloseTo(new BigDecimal("82.50"), Offset.offset(new BigDecimal("0.01")));

        RepaymentPlan lastMonth = loan.getRepaymentPlans().get(11);
        assertThat(lastMonth.getPayableAmount()).isCloseTo(new BigDecimal("878.69"), Offset.offset(new BigDecimal("0.01")));
        assertThat(lastMonth.getPayableCapital()).isCloseTo(new BigDecimal("871.51"), Offset.offset(new BigDecimal("0.1")));
        assertThat(lastMonth.getPayableInterest()).isCloseTo(new BigDecimal("7.19"), Offset.offset(new BigDecimal("0.1")));

        BigDecimal totalCapital = BigDecimal.ZERO;
        BigDecimal totalInterest = BigDecimal.ZERO;
        for (RepaymentPlan repaymentPlan : loan.getRepaymentPlans()) {
            assertThat(repaymentPlan.getPayableAmount()).isCloseTo(repaymentPlan.getPayableCapital().add(repaymentPlan.getPayableInterest()), Offset.offset(new BigDecimal("0.001")));
            totalCapital = totalCapital.add(repaymentPlan.getPayableCapital());
            totalInterest = totalInterest.add(repaymentPlan.getPayableInterest());
        }
        assertThat(totalCapital).isCloseTo(new BigDecimal("10000"), Offset.offset(new BigDecimal("0.001")));
        assertThat(totalInterest).isCloseTo(new BigDecimal("544.33"), Offset.offset(new BigDecimal("1")));
    }
}
