package com.cfckata.loan.loan.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RepaymentPlanDOMapper {
    int deleteByPrimaryKey(RepaymentPlanDOKey key);

    void deleteByLoanId(String loanId);

    int insert(RepaymentPlanDO record);

    void insertAll(List<RepaymentPlanDO> list);

    int insertSelective(RepaymentPlanDO record);

    RepaymentPlanDO selectByPrimaryKey(RepaymentPlanDOKey key);

    int updateByPrimaryKeySelective(RepaymentPlanDO record);

    int updateByPrimaryKey(RepaymentPlanDO record);

    List<RepaymentPlanDO> selectByLoanId(String loanId);
}
