package com.cfckata.order.domain;

public class OrderPaymentException extends RuntimeException {
    public OrderPaymentException(String message) {
        super(message);
    }
}
