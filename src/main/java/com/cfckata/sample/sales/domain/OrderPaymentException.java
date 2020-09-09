package com.cfckata.sample.sales.domain;

public class OrderPaymentException extends RuntimeException {
    public OrderPaymentException(String message) {
        super(message);
    }
}
