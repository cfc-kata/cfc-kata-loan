package com.cfckata.loan.contract.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RepaymentTypeTest {

    @Test
    void enum_test() {
        RepaymentType type = RepaymentType.valueOf("DEBX");
        assertEquals("等额本息", type.getDisplayName());
        assertEquals("DEBX", type.name());
    }
}
