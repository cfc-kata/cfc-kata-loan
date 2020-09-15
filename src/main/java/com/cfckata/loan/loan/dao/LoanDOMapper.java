package com.cfckata.loan.loan.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface LoanDOMapper {
    int deleteByPrimaryKey(String id);

    int insert(LoanDO record);

    int insertSelective(LoanDO record);

    LoanDO selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(LoanDO record);

    int updateByPrimaryKey(LoanDO record);

    int delete(LoanDO loanDO);
}
