package com.cfckata.loan.loan;

import com.cfckata.common.SpringServiceTest;
import com.cfckata.exception.BusinessException;
import com.cfckata.loan.contract.ContractService;
import com.cfckata.loan.loan.domain.Loan;
import com.cfckata.loan.loan.request.CreateLoanRequest;
import com.cfckata.loan.proxy.UnionPayFailedException;
import com.cfckata.loan.proxy.UnionPayProxy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class LoanServiceTest extends SpringServiceTest {
    @Autowired
    private LoanService loanService;
    @Autowired
    private LoanRepository loanRepository;
    @MockBean
    private UnionPayProxy unionPayProxy;
    @MockBean
    private ContractService contractService;

    @Test
    void should_create_loan() throws Exception {
        CreateLoanRequest request = new CreateLoanRequest("HT-0000000001", new BigDecimal("3000"),
                12, new BigDecimal("9.9"), "QK_card", "HK_card", "DEBX");

        doNothing().when(unionPayProxy).pay(anyString(), any(), anyString());
        when(contractService.preserveCommitment(anyString(), anyString(), any())).thenReturn(true);

        Loan loan = loanService.createLoan(request);

        assertThat(loan).isNotNull();
    }

    @Test
    void should_fail_when_contract_commitment_is_not_enough() throws Exception{
        CreateLoanRequest request = new CreateLoanRequest("HT-0000000001", new BigDecimal("3000"),
                12, new BigDecimal("9.9"), "QK_card", "HK_card", "DEBX");

        doNothing().when(unionPayProxy).pay(anyString(), any(), anyString());
        when(contractService.preserveCommitment(anyString(), anyString(), any())).thenReturn(false);

        BusinessException businessException = assertThrows(BusinessException.class, () -> {
            loanService.createLoan(request);
        });

        String expectedErrorCode = "合同额度错误码";
        assertEquals(expectedErrorCode, businessException.getErrorCode());
    }

    @Test
    void should_fail_to_create_loan_and_restore_contract_commitment_when_union_pay_failed() throws Exception{
        CreateLoanRequest request = new CreateLoanRequest("HT-0000000001", new BigDecimal("3000"),
                12, new BigDecimal("9.9"), "QK_card", "HK_card", "DEBX");

        when(contractService.preserveCommitment(anyString(), anyString(), any())).thenReturn(true);
        doThrow(UnionPayFailedException.class).when(unionPayProxy).pay(anyString(), any(), anyString());

        BusinessException businessException = assertThrows(BusinessException.class, () -> {
            loanService.createLoan(request);
        });
        String expectedErrorCode = "银联支付失败错误码";
        assertEquals(expectedErrorCode, businessException.getErrorCode());

        verify(contractService, times(1)).cancelPreserveCommitment(anyString());
    }
}
