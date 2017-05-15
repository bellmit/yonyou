/** 
 *Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Author : zhengcong
 *
 * @Date : 2017年4月20日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2017年4月20日    zhengcong    1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */
package com.yonyou.dms.repair.service.accessoryItem;


import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 仓库实现类
 * 
 * @author zhengcong
 * @date 2017年4月20日
 */
@Service
public class AccessoryItemServiceImpl implements AccessoryItemService {


	  /**
	  * 查询附加项目下拉列
	 * @author zhengcong
	 * @date 2017年4月20日
	 */
	 	
	 @Override
	 public List<Map> aiList() throws ServiceBizException {
		 StringBuffer sqlsb = new StringBuffer(" select DEALER_CODE,ADD_ITEM_CODE,ADD_ITEM_NAME,ADD_ITEM_PRICE,IS_DOWN  ");
		 sqlsb.append(" from TM_BALANCE_MODE_ADD_ITEM where 1=1 and IS_VALID="+DictCodeConstants.DICT_IS_YES);	
		 
		 List<Map>  dataList=DAOUtil.findAll(sqlsb.toString(),null);
	      return dataList;
	  }

	 
	  /**
		  * 查询收费区分下拉列
		 * @author zhengcong
		 * @date 2017年4月20日
		 */
		 	
		 @Override
		 public List<Map> cpList() throws ServiceBizException {
			 StringBuffer sqlsb = new StringBuffer(" select DEALER_CODE,CHARGE_PARTITION_CODE,CHARGE_PARTITION_NAME,  ");
			 sqlsb.append(" concat(CHARGE_PARTITION_CODE,'    |    ',CHARGE_PARTITION_NAME) COMBINE_NAME ");	
			 sqlsb.append(" from TM_CHARGE_PARTITION where 1=1 ");
			 List<Map>  dataList=DAOUtil.findAll(sqlsb.toString(),null);
		      return dataList;
		  }



  

}