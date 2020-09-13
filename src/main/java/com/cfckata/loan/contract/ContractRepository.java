package com.cfckata.loan.contract;

import com.cfckata.loan.contract.dao.ContractDO;
import com.cfckata.loan.contract.dao.ContractDOMapper;
import com.cfckata.loan.contract.domain.Contract;
import com.github.meixuesong.aggregatepersistence.Aggregate;
import com.github.meixuesong.aggregatepersistence.AggregateFactory;
import com.github.meixuesong.aggregatepersistence.DataObjectUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityNotFoundException;
import javax.persistence.OptimisticLockException;

@Repository
public class ContractRepository {
    private ContractDOMapper mapper;
    private ContractFactory contractFactory;

    public ContractRepository(ContractDOMapper mapper, ContractFactory contractFactory) {
        this.mapper = mapper;
        this.contractFactory = contractFactory;
    }

    public void save(Aggregate<Contract> aggregate) {
        if (aggregate.isNew()) {
            mapper.insert(contractFactory.createContractDO(aggregate.getRoot()));
        } else if (aggregate.isChanged()) {
            ContractDO delta = getDelta(aggregate);

            if (mapper.updateByPrimaryKeySelective(delta) != 1) {
                throw new OptimisticLockException(String.format("Update contract (%s) error, it's not found or changed by another user", aggregate.getRoot().getId()));
            }
        }
    }

    private ContractDO getDelta(Aggregate<Contract> aggregate) {
        ContractDO current = contractFactory.createContractDO(aggregate.getRoot());
        ContractDO old =  contractFactory.createContractDO(aggregate.getRootSnapshot());

        ContractDO delta = DataObjectUtils.getDelta(old, current);
        delta.setId(current.getId());
        delta.setVersion(current.getVersion());

        return delta;
    }

    public Aggregate<Contract> findById(String id) {
        ContractDO contractDO = mapper.selectByPrimaryKey(id);
        if (contractDO == null) {
            throw new EntityNotFoundException(String.format("Contract(id=%s) not found", id));
        }

        return AggregateFactory.createAggregate(contractFactory.createContract(contractDO));
    }
}
