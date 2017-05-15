
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : PartCustomerDealerServiceImpl.java
*
* @Author : chenwei
*
* @Date : 2017年3月29日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年3月29日    Administrator    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.part.service.basedata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
* 业务往来客户经销商 实现类
* @author chenwei
* @date 2017年3月29日
*/
@Service
public class PartCustomerDealerServiceImpl implements PartCustomerDealerService {
    
    /**
     * 业务往来客户经销商列表的方法
     * @author chenwei
     * @date 2017年3月28日
     * @param queryParam
     * @return
     * @throws ServiceBizException
     * (non-Javadoc)
     * @see com.yonyou.dms.customer.service.basedata.BusinessCustomerService#queryCustomersDict(java.util.Map)
     */
    @Override
    public List<Map> queryPartCustomerDealerList(Map<String, String> queryParam) throws ServiceBizException {
        StringBuffer sqlSb = new StringBuffer("select DEALER_CODE,BRAND_CODE,DEALER2_CODE,DEALER_SHORT_NAME,DEALER_FULL_NAME  from TM_PART_CUSTOMER_DEALER where 1=1 ");
        List<Object> params = new ArrayList<Object>();
        sqlSb.append(" and DEALER_CODE = ? ");
        params.add(FrameworkUtil.getLoginInfo().getDealerCode());
        if(queryParam.size()>0 && !StringUtils.isNullOrEmpty(queryParam.get("brandCode"))){
            sqlSb.append(" and BRAND_CODE=? ");
            params.add(queryParam.get("brandCode"));
            System.out.println("BRAND_CODE========================="+queryParam.get("brandCode"));
        }
        //执行查询操作
        List<Map> result = DAOUtil.findAll(sqlSb.toString(),params);
        return result;
    }
    
    
    
    
}
