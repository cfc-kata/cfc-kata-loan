package com.cfckata.order.proxy;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PayProxy {

    public void pay(String orderId, BigDecimal amount) {
        //Call exteranl service
    }
}
