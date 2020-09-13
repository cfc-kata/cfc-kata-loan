package com.cfckata.loan.contract;

import org.springframework.stereotype.Service;

@Service
public class ContractIdGenerator {
    private static long currentId = 0;

    public String generateId() {
        long id = increase();
        return "HT-" + String.format("%010d", id);
    }

    private synchronized long increase() {
        return ++currentId;
    }
}
