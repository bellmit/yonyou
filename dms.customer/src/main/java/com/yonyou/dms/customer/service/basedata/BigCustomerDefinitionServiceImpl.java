
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.customer
*
* @File name : BigCustomerDefinitionServiceImpl.java
*
* @Author : yangjie
*
* @Date : 2016年12月15日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年12月15日    yangjie    1.0
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
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
* TODO description
* @author yangjie
* @date 2016年12月15日
*/

@Service
public class BigCustomerDefinitionServiceImpl implements BigCustomerDefinitionService {

    @Override
    public PageInfoDto findAllDefinition(Map<String, String> param) {
        StringBuilder sb = new StringBuilder("SELECT ta.ITEM_ID,ta.DEALER_CODE,td.BRAND_CODE,td.BRAND_NAME,ts.SERIES_CODE,ts.SERIES_NAME,ta.IS_VALID,case ta.IS_DELETE when 1 then 12781002 else 12781001 end as IS_DELETE ,ta.CREATED_AT,ta.PS_TYPE ");
        sb.append("FROM tm_big_customer_definition ta,tm_series ts,tm_brand td ");
        sb.append("WHERE ta.series_code = ts.series_code AND ta.brand_code = td.brand_code and ta.IS_VALID=10011001 ");
        List<Object> queryParam=new ArrayList<Object>();
        if(!StringUtils.isNullOrEmpty(param.get("type"))){
            sb.append(" and ta.PS_TYPE = ?");
            queryParam.add(Integer.parseInt(param.get("type")));
        }
        if(!StringUtils.isNullOrEmpty(param.get("brand"))){
            sb.append(" and ta.BRAND_CODE like ?");
            queryParam.add("%"+param.get("brand")+"%");
        }
        if(!StringUtils.isNullOrEmpty(param.get("series"))){
            sb.append(" and ts.SERIES_CODE like ?");
            queryParam.add("%"+param.get("series")+"%");
        }
        if(!StringUtils.isNullOrEmpty(param.get("isValid"))){
            sb.append(" and ta.IS_DELETE = ?");
            if("12781001".equals(param.get("isValid"))){
            	queryParam.add(0);
            }else if("12781002".equals(param.get("isValid"))){
            	queryParam.add(1);
            }
        }
        if(!StringUtils.isNullOrEmpty(param.get("begin"))&&!StringUtils.isNullOrEmpty(param.get("end"))){
            sb.append(" and ta.CREATED_AT between ? and ?");
            queryParam.add(param.get("begin"));
            queryParam.add(param.get("end"));
        }
        sb.append(" order by TA.IS_DELETE,TA.CREATED_AT DESC");
        PageInfoDto dto = DAOUtil.pageQuery(sb.toString(), queryParam);
        return dto;
    }

}
