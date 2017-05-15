
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.customer
*
* @File name : SalesTraceResultSearchService.java
*
* @Author : Administrator
*
* @Date : 2017年1月10日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年1月10日    Administrator    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.customer.service.customerManage;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.customer.domains.DTO.customerManage.ColumnsDTO;
import com.yonyou.dms.customer.domains.DTO.customerManage.TableHeaderDTO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
* TODO description
* @author Administrator
* @date 2017年1月10日
*/

public interface SalesTraceResultSearchService {
    
    public PageInfoDto querySalesTraceResultInput(Map<String, String> queryParam) throws ServiceBizException;
    
    @SuppressWarnings("rawtypes")
	List<Map> querySafeToExport(Map<String, String> queryParam) throws ServiceBizException;//使用excel导出
    
    List<TableHeaderDTO>  getHeaderList() throws ServiceBizException;//获取追加表头
    
    List<ColumnsDTO>  getColumnsList(Integer answerGroupNo) throws ServiceBizException;//获取追加列名
    
    @SuppressWarnings("rawtypes")
	List<Map> getQuestionAndAnswers(Map<String, String> queryParam) throws ServiceBizException;//获取答案
    
    @SuppressWarnings("rawtypes")
	List<Map> queryTtQuestionDetail(Map<String, String> queryParam) throws ServiceBizException;//跟踪任务问题及明细
    
    @SuppressWarnings("rawtypes")
	List<Map> queryAnswerAndQuestionAll(Map<String, String> queryParam) throws ServiceBizException;//查询所有问卷问题，答案
}
