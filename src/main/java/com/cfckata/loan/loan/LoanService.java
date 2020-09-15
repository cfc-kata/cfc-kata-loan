package com.cfckata.loan.loan;

import com.cfckata.loan.loan.domain.Loan;
import com.cfckata.loan.loan.request.CreateLoanRequest;
import com.github.meixuesong.aggregatepersistence.AggregateFactory;
import org.springframework.stereotype.Service;

@Service
public class LoanService {

    private LoanFactory factory;
    private LoanRepository repository;

    public LoanService(LoanFactory factory, LoanRepository repository) {
        this.factory = factory;
        this.repository = repository;
    }

    public Loan createLoan(CreateLoanRequest request) {
        Loan loan = factory.createLoan(request);

        repository.save(AggregateFactory.createAggregate(loan));

        return repository.findById(loan.getId()).getRoot();
    }
}
