
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.customer
 *
 * @File name : CustomerResoServiceImpl.java
 *
* @Author : zhengcong
*
* @Date : 2017年3月22日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2017年3月22日    zhengcong    1.0
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

import com.yonyou.dms.customer.domains.DTO.basedata.CustomerResoDTO;
import com.yonyou.dms.customer.domains.PO.basedata.CustomerResoPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * 业务往来客户
 * 
 * @author zhanshiwei
 * @date 2016年7月11日
 */
@Service
@SuppressWarnings({"rawtypes","unchecked"})
public class CustomerResoServiceImpl implements CustomerResoService {

    /**
     * 根据查询条件查询业务往来客户信息
     * 
     * @author zhengcong
     * @date 2017年3月22日
     */
    @Override
    public PageInfoDto queryContCustomer(Map<String, String> queryParam) throws ServiceBizException {

        StringBuilder sb = new StringBuilder("select DEALER_CODE,CUSTOMER_TYPE_CODE,CUSTOMER_TYPE_NAME from  TM_CUSTOMER_TYPE where 1=1 ");
        List<Object> queryList = new ArrayList<Object>();
        if (!StringUtils.isNullOrEmpty(queryParam.get("CUSTOMER_TYPE_CODE"))) {
            sb.append(" and CUSTOMER_TYPE_CODE like ?");
            queryList.add("%" + queryParam.get("CUSTOMER_TYPE_CODE") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("CUSTOMER_TYPE_NAME"))) {
            sb.append(" and CUSTOMER_TYPE_NAME like ?");
            queryList.add("%" + queryParam.get("CUSTOMER_TYPE_NAME") + "%");
        }
        PageInfoDto id = DAOUtil.pageQuery(sb.toString(), queryList);
        return id;
    }

    
    
	/**
	 * 新增
     * @author zhengcong
     * @date 2017年3月22日
	 */
	@Override
	public void addCustomerReso(CustomerResoDTO cudto)throws ServiceBizException {		
		StringBuffer sb= new StringBuffer("select DEALER_CODE,CUSTOMER_TYPE_CODE,CUSTOMER_TYPE_NAME from  TM_CUSTOMER_TYPE where 1=1 ");
		sb.append("and dealer_code ='" + FrameworkUtil.getLoginInfo().getDealerCode() + "' ");
		sb.append("and CUSTOMER_TYPE_CODE=?");
	    List<Object> list=new ArrayList<Object>();
	    list.add(cudto.getCustomer_type_code());
	    List<Map> map=DAOUtil.findAll(sb.toString(), list);
        if(map.size()>0 ){
	        throw new ServiceBizException("代码不允许重复");
	    }else{
	    	
	    	CustomerResoPO cupo=new CustomerResoPO();
	    	cupo.setString("DEALER_CODE", FrameworkUtil.getLoginInfo().getDealerCode());
	    	cupo.setString("CUSTOMER_TYPE_CODE", cudto.getCustomer_type_code());
		    cupo.setString("CUSTOMER_TYPE_NAME", cudto.getCustomer_type_name());
		    cupo.saveIt();
		
	    }
	}

  
    
    /**
     * 根据customer_type_code查询业务往来客户信息
     * @author zhengcong
     * @date 2017年3月22日
     */
	@Override
	public Map<String, String> findByCustomerTypeCode(String CUSTOMER_TYPE_CODE) throws ServiceBizException {
    StringBuilder sb = new StringBuilder("select DEALER_CODE,CUSTOMER_TYPE_CODE,CUSTOMER_TYPE_NAME from  TM_CUSTOMER_TYPE where 1=1 "); 
    sb.append("and dealer_code ='" + FrameworkUtil.getLoginInfo().getDealerCode() + "' ");
    sb.append(" and CUSTOMER_TYPE_CODE = ? ");
    List queryParam = new ArrayList();
    queryParam.add(CUSTOMER_TYPE_CODE);
    return DAOUtil.findFirst(sb.toString(), queryParam);
}


	/**
	 * 更新
     * @author zhengcong
     * @date 2017年3月21日
	 */

	@Override
	public void modifyByCustomerTypeCode(String CUSTOMER_TYPE_CODE,CustomerResoDTO cudto) throws ServiceBizException{
		CustomerResoPO cupo=CustomerResoPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),CUSTOMER_TYPE_CODE);
		    cupo.setString("CUSTOMER_TYPE_NAME", cudto.getCustomer_type_name());
		    cupo.saveIt();
	}


}
