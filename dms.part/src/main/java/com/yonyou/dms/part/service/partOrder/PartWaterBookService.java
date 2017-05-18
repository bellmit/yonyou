
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : PartWaterBookService.java
*
* @Author : zhansw
*
* @Date : 2017年5月15日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年5月15日    zhansw    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.part.service.partOrder;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
*配件流水账
* @author zhansw
* @date 2017年5月15日
*/

public interface PartWaterBookService {
  public PageInfoDto queryPartStockInfoUnifiedView(Map<String,String> queryParam) throws ServiceBizException;
  public  PageInfoDto queryPartDetail(Map<String,String> queryParam)  throws ServiceBizException;
  List<Map> queryPartStockInfoUnifiedViewList(Map<String, String> queryParam) throws ServiceBizException;
  List<Map> exportPartWaterBookItem (Map<String, String> queryParam) throws ServiceBizException;
}
