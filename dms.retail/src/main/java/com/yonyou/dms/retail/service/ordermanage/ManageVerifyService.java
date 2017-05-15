
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.retail
*
* @File name : SoAuditService.java
*
* @Author : xukl
*
* @Date : 2016年9月28日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年9月28日    xukl    1.0
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
import com.yonyou.dms.retail.domains.DTO.ordermanage.SoAuditingDTO;

/**
* 经理审核接口
* @author xukl
* @date 2016年9月28日
*/

public interface ManageVerifyService {
    public PageInfoDto querySalesOrdersAudit(Map<String, String> queryParam) throws ServiceBizException;
    public void auditSalesOrder(SoAuditingDTO auditDto) throws ServiceBizException;
    public PageInfoDto queryAudtiDetail(String id) throws ServiceBizException;
    public List<Map> manageAudit(String id,String by) throws ServiceBizException;
    public List<Map> commidAudit(String id,String by) throws ServiceBizException;
    public void saveCommitAudit(SoAuditingDTO auditDto) throws ServiceBizException;
    
 

}
