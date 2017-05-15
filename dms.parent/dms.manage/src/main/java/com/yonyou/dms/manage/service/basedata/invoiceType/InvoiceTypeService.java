
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.manage
*
* @File name : InvoiceTypeService.java
*
* @Author : yangjie
*
* @Date : 2016年12月19日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年12月19日    yangjie    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.manage.service.basedata.invoiceType;

import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.manage.domains.DTO.basedata.InvoiceYTypeDTO;
import com.yonyou.dms.manage.domains.PO.basedata.InvoiceTypePO;

/**
 * TODO description
 * 
 * @author yangjie
 * @date 2016年12月19日
 */

public interface InvoiceTypeService {

    PageInfoDto findAll(Map<String, String> param) throws ServiceBizException;//遍历所有类型
    
    InvoiceTypePO findById(String id) throws ServiceBizException;//通过id查询类型
    
    void addType(InvoiceYTypeDTO dto) throws ServiceBizException;//增加类型
    
    void editType(String id,InvoiceYTypeDTO dto) throws ServiceBizException;//编辑类型
    
    void delType(String id) throws ServiceBizException;//删除类型
}
