
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : PartPriceAdjustService.java
*
* @Author : zhongshiwei
*
* @Date : 2016年7月17日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年7月17日    zhongshiwei    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.part.service.basedata;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.common.domains.PO.basedata.TmPartStockPO;
import com.yonyou.dms.commonAS.domains.DTO.basedata.PartStockDTO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.part.domains.DTO.basedata.PartPriceAdjustDTO;

/**
 * 配件价格调整接口
* @author zhongshiwei
* @date 2016年7月17日
*/
 
public interface PartPriceAdjustService  {
    
    public List<Map> findById(String id,String ids) throws ServiceBizException;
    
    public List<Map> queryExport(Map<String,String> queryParam) throws ServiceBizException;//导出
    
    public PageInfoDto partPriceAdjustSQL(Map<String, String> queryParam) throws ServiceBizException;///查询
    
    public void updatePartPriceAdjust(String id,String ids,String idd,Map<String, String> param) throws ServiceBizException;///修改

    public void modifyPrice(Map queryParam)throws ServiceBizException;//批量修改
    
    public List<Map> querySql(Map<String, String> queryParam) throws ServiceBizException;;

    public void imports(PartPriceAdjustDTO rowDto) throws ServiceBizException;//导入
}
