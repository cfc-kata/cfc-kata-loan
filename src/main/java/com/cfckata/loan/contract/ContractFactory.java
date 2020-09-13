package com.cfckata.loan.contract;

import com.cfckata.loan.contract.dao.ContractDO;
import com.cfckata.loan.contract.domain.Contract;
import com.cfckata.loan.contract.domain.ContractBuilder;
import com.cfckata.loan.contract.domain.ContractStatus;
import com.cfckata.loan.contract.domain.RepaymentType;
import com.cfckata.loan.customer.LoanCustomer;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class ContractFactory {
    public ContractDO createContractDO(Contract contract) {
        ContractDO contractDO = new ContractDO();
        contractDO.setId(contract.getId());
        contractDO.setCreateTime(toDate(contract.getCreatedAt()));
        contractDO.setCommitment(contract.getCommitment());
        contractDO.setCustomerId(contract.getCustomer().getId());
        contractDO.setCustomerName(contract.getCustomer().getName());
        contractDO.setCustomerIdNumber(contract.getCustomer().getIdNumber());
        contractDO.setCustomerPhone(contract.getCustomer().getMobilePhone());
        contractDO.setInterestRate(contract.getInterestRate());
        contractDO.setMaturityDate(toDate(contract.getMaturityDate().atStartOfDay()));
        contractDO.setRepaymentType(contract.getRepaymentType().name());
        contractDO.setStatus(contract.getStatus().name());
        contractDO.setVersion(contract.getVersion());

        return contractDO;
    }

    public Contract createContract(ContractDO contractDO) {
        return new ContractBuilder()
                .setId(contractDO.getId())
                .setCommitment(contractDO.getCommitment())
                .setMaturityDate(contractDO.getMaturityDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                .setInterestRate(contractDO.getInterestRate())
                .setStatus(ContractStatus.valueOf(contractDO.getStatus()))
                .setRepaymentType(RepaymentType.valueOf(contractDO.getRepaymentType()))
                .setCustomer(new LoanCustomer(contractDO.getCustomerId(), contractDO.getCustomerName(), contractDO.getCustomerIdNumber(), contractDO.getCustomerPhone()))
                .setCreatedAt(contractDO.getCreateTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
                .createContract();
    }

    private Date toDate(LocalDateTime dateTime) {
        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
