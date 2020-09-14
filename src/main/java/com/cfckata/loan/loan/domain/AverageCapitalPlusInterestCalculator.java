package com.cfckata.loan.loan.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * 还款计划计算器：等额本息
 */
public class AverageCapitalPlusInterestCalculator implements RepaymentPlanCalculator {
    private static final BigDecimal MONTH_IN_A_YEAR = new BigDecimal("12");
    private static final BigDecimal RATE_PERCENT = new BigDecimal("100");

    /**
     * 等额本息 —— 计算各个月份的还款额
     *
     * @param loan 借条
     * @return 还款计划
     */
    @Override
    public List<RepaymentPlan> calculateRepaymentPlan(Loan loan) {
        RoundingMode roundingMode = RoundingMode.HALF_UP;
        BigDecimal monthRate = loan.getInterestRate().divide(MONTH_IN_A_YEAR).divide(RATE_PERCENT);
        BigDecimal payableAmount = calculateAmountPerMonth(loan.getTotalMonth(), loan.getApplyAmount(), roundingMode, monthRate);

        BigDecimal totalInterest = BigDecimal.ZERO;
        BigDecimal totalCapital = BigDecimal.ZERO;

        List<RepaymentPlan> results = new ArrayList<>();
        for (int i = 0; i < loan.getTotalMonth(); i++) {
            BigDecimal payableInterest;
            BigDecimal payableCapital;

            if (loan.getTotalMonth().equals(i + 1)) {
                payableInterest = payableAmount.multiply(new BigDecimal(loan.getTotalMonth())).subtract(loan.getApplyAmount()).subtract(totalInterest);
                payableCapital = loan.getApplyAmount().subtract(totalCapital);
            } else {
                BigDecimal currentMonthRate = monthRate.add(new BigDecimal(1)).pow(i);
                payableInterest = loan.getApplyAmount().multiply(monthRate).subtract(payableAmount).multiply(currentMonthRate).add(payableAmount).setScale(2, roundingMode);
                payableCapital = payableAmount.subtract(payableInterest);
            }

            totalInterest = totalInterest.add(payableInterest);
            totalCapital = totalCapital.add(payableCapital);

            results.add(new RepaymentPlan(i + 1, loan.getCreatedAt().toLocalDate().plusMonths(i + 1L),
                    payableAmount, payableInterest, payableCapital, RepaymentPlanStatus.PLAN));
        }

        return results;
    }

    /**
     * 等额本息 —— 计算每期还款本息和
     * 每月还款额 = 贷款本金×[月利率×(1+月利率) ^ 还款月数]÷{[(1+月利率) ^ 还款月数]-1}
     * https://wiki.mbalib.com/wiki/%E7%AD%89%E9%A2%9D%E6%9C%AC%E6%81%AF%E8%BF%98%E6%AC%BE%E6%B3%95
     *
     * @param periods      期数，单位：月
     * @param applyAmount  贷款金额
     * @param roundingMode 小数位处理
     * @param monthRate    月利率
     * @return 每期还款额，包括本金和利息
     */
    private BigDecimal calculateAmountPerMonth(Integer periods, BigDecimal applyAmount, RoundingMode roundingMode, BigDecimal monthRate) {
        BigDecimal tmp = monthRate.add(BigDecimal.ONE).pow(periods);
        BigDecimal result = (applyAmount.multiply(monthRate).multiply(tmp));
        result = result.divide(tmp.subtract(BigDecimal.ONE), 2, roundingMode);

        return result;
    }
}
