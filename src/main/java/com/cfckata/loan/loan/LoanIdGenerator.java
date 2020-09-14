package com.cfckata.loan.loan;

import org.springframework.stereotype.Service;

@Service
public class LoanIdGenerator {
    private static long currentId = 0;

    public String nextId() {
        long id = increase();
        return "JT-" + String.format("%010d", id);
    }

    private synchronized long increase() {
        return ++currentId;
    }

}
