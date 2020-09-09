package com.cfckata.sample.sales.request;

import java.math.BigDecimal;

public class CheckoutRequest {
    private String paymentType;
    private BigDecimal amount;

    public CheckoutRequest(String paymentType, BigDecimal amount) {
        this.paymentType = paymentType;
        this.amount = amount;
    }

    public CheckoutRequest() {
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
