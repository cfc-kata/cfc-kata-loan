package com.cfckata.loan.contract.dao.dao;

import com.cfckata.loan.loan.dao.RepaymentPlanDO;
import com.cfckata.loan.loan.dao.RepaymentPlanDOKey;

public interface RepaymentPlanDOMapper {
    int deleteByPrimaryKey(RepaymentPlanDOKey key);

    int insert(RepaymentPlanDO record);

    int insertSelective(RepaymentPlanDO record);

    RepaymentPlanDO selectByPrimaryKey(RepaymentPlanDOKey key);

    int updateByPrimaryKeySelective(RepaymentPlanDO record);

    int updateByPrimaryKey(RepaymentPlanDO record);
}
