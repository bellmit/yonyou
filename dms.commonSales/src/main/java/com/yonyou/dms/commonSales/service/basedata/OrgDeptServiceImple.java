
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.commonSales
*
* @File name : OrgDeptServiceImple.java
*
* @Author : zhanshiwei
*
* @Date : 2016年12月9日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年12月9日    zhanshiwei    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.commonSales.service.basedata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
* 组织
* @author zhanshiwei
* @date 2016年12月9日
*/
@Service
public class OrgDeptServiceImple implements OrgDeptService{

    @Override
    public Long getOrganizationIdByEmployeeNo(String employeeNo) throws ServiceBizException {
        String sql="select org_1.ORGDEPT_ID,org_1.DEALER_CODE  FROM tm_organization org_1  ,TM_EMPLOYEE em_1 WHERE org_1.ORG_CODE=em_1.ORG_CODE and em_1.EMPLOYEE_NO=? and org_1.DEALER_CODE=em_1.DEALER_CODE";
        List<Object> queryParam = new ArrayList<Object>();
        queryParam.add(employeeNo);
        Map result=DAOUtil.findFirst(sql, queryParam);
        return result!=null?Long.parseLong(result.get("ORGDEPT_ID").toString()):null;
    }

}
