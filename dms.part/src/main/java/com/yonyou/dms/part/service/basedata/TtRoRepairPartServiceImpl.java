package com.yonyou.dms.part.service.basedata;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * 查询工单明细
 * @author chenwei
 *@date 2017年4月1日
 */
@Service
public class TtRoRepairPartServiceImpl implements TtRoRepairPartService{

    @Override
    public PageInfoDto checkTtRoRepairPart(Map<String, String> queryParams) throws ServiceBizException {
        // TODO Auto-generated method stub
        return null;
    }

    
    

    @Override
    public Map<String, Object> findDetailByClaimId(BigDecimal claimId) throws ServiceBizException {
        // TODO Auto-generated method stub
        return null;
    }

	
}
