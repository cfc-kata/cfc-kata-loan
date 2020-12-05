package com.cfckata.loan.loan.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Mapper
@Repository
public interface RepaymentPlanDOMapper {
    int deleteByPrimaryKey(RepaymentPlanDOKey key);

    void deleteByLoanId(String loanId);

    int insert(RepaymentPlanDO record);

    void insertAll(List<RepaymentPlanDO> list);

    int insertSelective(RepaymentPlanDO record);

    RepaymentPlanDO selectByPrimaryKey(RepaymentPlanDOKey key);

    int updateByPrimaryKeySelective(@Param("repaymentPlanDO") RepaymentPlanDO record, @Param("changedFields") Set<String> changedFields);

    int updateByPrimaryKey(@Param("repaymentPlanDO") RepaymentPlanDO record, @Param("changedFields") Set<String> changedFields);

    List<RepaymentPlanDO> selectByLoanId(String loanId);
}
