
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.customer
*
* @File name : BigCusAmountServiceImpl.java
*
* @Author : yangjie
*
* @Date : 2016年12月16日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年12月16日    yangjie    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.customer.service.basedata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.utils.common.StringUtils;


/**
* TODO description
* @author yangjie
* @date 2016年12月16日
*/

@Service
public class BigCusAmountServiceImpl implements BigCusAmountService {

    /**
    * @author yangjie
    * @date 2016年12月16日
    * @return
    * (non-Javadoc)
    * @see com.yonyou.dms.customer.service.basedata.BigCusAmountService#findAllAmount()
    */

    @Override
    public PageInfoDto findAllAmount(Map<String, String> param) {
        StringBuilder sb = new StringBuilder();
        String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
        sb.append(" SELECT  NUMBER,EMPLOYEE_TYPE,PS_TYPE,DEALER_CODE FROM tm_big_customer_AMOUNT where " +
				" IS_VALID=10011001 AND IS_DELETE=0 and DEALER_CODE='"+dealerCode+"' "
					);
        List<Object> queryParam=new ArrayList<Object>();
        PageInfoDto dto = DAOUtil.pageQuery(sb.toString(), queryParam);
        return dto;
    }

}
