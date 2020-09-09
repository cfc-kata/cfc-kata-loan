package com.cfckata.sample.sales.domain;

import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class SalesOrderTest {

    @Test
    public void should_be_PAID_when_checkout() {
        SalesOrder order = new SalesOrder();
        order.setStatus(OrderStatus.NEW);

        order.checkout(new Payment(PaymentType.CASH, BigDecimal.ZERO));

        assertThat(order.getStatus()).isEqualTo(OrderStatus.PAID);
    }

    @Test(expected = OrderPaymentException.class)
    public void should_failed_when_pay_a_not_new_order() {
        SalesOrder order = new SalesOrder();
        order.setStatus(OrderStatus.PAID);

        order.checkout(new Payment(PaymentType.CASH, new BigDecimal("10.00")));
    }


}
