package com.cfckata.loan.contract.dao.dao;

import com.cfckata.loan.loan.dao.LoanDO;

public interface LoanDOMapper {
    int deleteByPrimaryKey(String id);

    int insert(LoanDO record);

    int insertSelective(LoanDO record);

    LoanDO selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(LoanDO record);

    int updateByPrimaryKey(LoanDO record);
}
