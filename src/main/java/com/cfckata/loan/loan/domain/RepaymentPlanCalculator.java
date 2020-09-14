package com.cfckata.loan.loan.domain;

import java.util.List;

public interface RepaymentPlanCalculator {
    List<RepaymentPlan> calculateRepaymentPlan(Loan loan);
}
