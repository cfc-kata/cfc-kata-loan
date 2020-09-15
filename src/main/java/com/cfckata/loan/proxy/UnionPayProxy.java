package com.cfckata.loan.proxy;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UnionPayProxy {
    public void pay(String loanId, BigDecimal applyAmount, String bankAccount) throws UnionPayFailedException{

    }
}
