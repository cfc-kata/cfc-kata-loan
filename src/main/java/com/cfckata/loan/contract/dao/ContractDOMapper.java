package com.cfckata.loan.contract.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ContractDOMapper {
    int deleteByPrimaryKey(String id);

    int insert(ContractDO record);

    int insertSelective(ContractDO record);

    ContractDO selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ContractDO record);

    int updateByPrimaryKey(ContractDO record);
}
