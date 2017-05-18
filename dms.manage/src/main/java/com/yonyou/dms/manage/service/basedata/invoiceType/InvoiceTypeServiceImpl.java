
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.manage
*
* @File name : InvoiceTypeServiceImpl.java
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.manage.domains.DTO.basedata.InvoiceYTypeDTO;
import com.yonyou.dms.manage.domains.PO.basedata.InvoiceTypePO;


/**
* TODO description
* @author yangjie
* @date 2016年12月19日
*/

@Service
public class InvoiceTypeServiceImpl implements InvoiceTypeService {

    /**
    * @author yangjie
    * @date 2016年12月19日
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.manage.service.basedata.invoiceType.InvoiceTypeService#findAll()
    */

    @Override
    public PageInfoDto findAll(Map<String, String> param) throws ServiceBizException {
        StringBuilder sb = new StringBuilder("SELECT DEALER_CODE,INVOICE_TYPE_CODE,INVOICE_TYPE_NAME FROM TM_INVOICE_TYPE where 1=1");
        List<Object> queryParam=new ArrayList<Object>();
        if(!StringUtils.isNullOrEmpty(param.get("code"))){
            sb.append(" and INVOICE_TYPE_CODE like ?");
            queryParam.add("%"+param.get("code")+"%");
        }
        if(!StringUtils.isNullOrEmpty(param.get("name"))){
            sb.append(" and INVOICE_TYPE_NAME like ?");
            queryParam.add("%"+param.get("name")+"%");
        }
        PageInfoDto dto = DAOUtil.pageQuery(sb.toString(), queryParam);
        return dto;
    }

    @Override
    public InvoiceTypePO findById(String id) throws ServiceBizException {
        return InvoiceTypePO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),id);
    }

    @Override
    public void addType(InvoiceYTypeDTO dto) throws ServiceBizException {
        InvoiceTypePO po = new InvoiceTypePO();
        check(dto);
        dtoToPo(dto, po);
        po.saveIt();
    }

    @Override
    public void editType(String id, InvoiceYTypeDTO dto) throws ServiceBizException {
        InvoiceTypePO po = InvoiceTypePO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),id);
        dtoToPo(dto, po);
        po.saveIt();
    }

    @Override
    public void delType(String id) throws ServiceBizException {
        InvoiceTypePO po = InvoiceTypePO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),id);
        po.delete();
    }

    /**
     * 新增时后台判断主键是否存在
    * TODO description
    * @author yangjie
    * @date 2016年12月19日
    * @param dto
     */
    public void check(InvoiceYTypeDTO dto){
        List<Object> params = new ArrayList<Object>();
        StringBuilder sqlSb = new StringBuilder("SELECT DEALER_CODE,INVOICE_TYPE_CODE,INVOICE_TYPE_NAME FROM TM_INVOICE_TYPE WHERE INVOICE_TYPE_CODE=?");
        params.add(dto.getCode());
        if(DAOUtil.findAll(sqlSb.toString(), params).size()>0){
            throw new ServiceBizException("类型代码已存在!");
        }
    }
    
    /**
     * 数据传递对象赋值给持久化对象
    * TODO description
    * @author yangjie
    * @date 2016年12月19日
    * @param dto
    * @param po
     */
    public void dtoToPo(InvoiceYTypeDTO dto,InvoiceTypePO po){
        po.setString("INVOICE_TYPE_CODE", dto.getCode());
        po.setString("INVOICE_TYPE_NAME", dto.getName());
    }
}
