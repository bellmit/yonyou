
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.retail
*
* @File name : DecroDateService.java
*
* @Author : zsw
*
* @Date : 2016年9月5日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年9月5日    zsw    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.retail.service.ordermanage;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.retail.domains.DTO.ordermanage.DecorationDTO;

/**
* 装潢项目定义
* @author zsw
* @date 2016年9月5日
*/

public interface DecroDateService {

    public PageInfoDto queryDecroDate(Map<String, String> queryParam) throws ServiceBizException;
    
    public Long insertDecro(DecorationDTO decroDTO)throws ServiceBizException;///增加
    
    public void updateDecro(Long id,DecorationDTO decroDTO)throws ServiceBizException;///修改
    
    public void deleteDecroById(Long id)throws ServiceBizException;///删除
    
    public List<Map> getDecoraById(Long id)throws ServiceBizException;//明细
    
    public Map editDecoraById(Long id)throws ServiceBizException;//明细
    
    public List<Map> editDecoraByIdItem(Long id)throws ServiceBizException;
    
    public PageInfoDto searchSalesOrderItem(Long id)throws ServiceBizException;//销售订单装潢子表明细

}
