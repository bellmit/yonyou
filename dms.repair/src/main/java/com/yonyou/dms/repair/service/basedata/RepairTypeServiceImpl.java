
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.repair
 *
 * @File name : RepairTypeServiceImpl.java
 *
 * @Author : DuPengXin
 *
 * @Date : 2016年7月27日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年7月27日    DuPengXin   1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.repair.service.basedata;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.TmRepairTypePO;
import com.yonyou.dms.common.domains.PO.basedata.VsStockPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.repair.domains.DTO.basedata.RepairTypeDTO;

/**
 * @author DuPengXin
 * @date 2016年7月27日
 */
@Service
@SuppressWarnings("rawtypes")
public class RepairTypeServiceImpl implements RepairTypeService {


    /**
     * 查询
     * @author zhl
     * @date 2017年3月24日
     * @param queryParam
     * @return
     * (non-Javadoc)
     * @see com.yonyou.dms.repair.service.basedata.RepairTypeService#QueryRepairType(java.util.Map)
     */

    @Override
    public PageInfoDto QueryRepairType(Map<String, String> queryParam) throws ServiceBizException{
    	LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
    	String groupCode = Utility.getGroupEntity(loginInfo.getDealerCode(), "TM_REPAIR_TYPE");
        StringBuilder sqlSb = new StringBuilder("select REPAIR_TYPE_CODE,REPAIR_TYPE_NAME,LABOUR_PRICE,COEFFICIENT,SORT_TYPE,CASE WHEN IS_SPRAY_LABOUR = 12781001 THEN 10571001 END AS IS_SPRAY_LABOUR,CASE WHEN IS_GUARANTEE = 12781001 THEN 10571001  END AS IS_GUARANTEE,DEALER_CODE, ");      
        sqlSb.append(" CASE WHEN IS_INSURANCE = 12781001 THEN 10571001 END AS IS_INSURANCE,CASE WHEN IS_RESERVED = 12781001 THEN 10571001  END AS IS_RESERVED, ");
        sqlSb.append("	 CASE WHEN OEM_TAG = 12781001 THEN 10571001 END AS OEM_TAG,CASE WHEN IS_PRE_SERVICE = 12781001 THEN 10571001 END AS IS_PRE_SERVICE,CASE WHEN IS_VALID = 12781001 THEN 10571001 END AS IS_VALID  from  tm_repair_type where 1=1  and DEALER_CODE='"+groupCode+"'"  );
   
        List<Object> params = new ArrayList<Object>();
        if(!StringUtils.isNullOrEmpty(queryParam.get("repairTypeCode"))){
            sqlSb.append(" and REPAIR_TYPE_CODE like ?");
            params.add("%"+queryParam.get("repairTypeCode")+"%");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("repairTypeName"))){
            sqlSb.append(" and REPAIR_TYPE_NAME like ?");
            params.add("%"+queryParam.get("repairTypeName")+"%");
        }
        PageInfoDto pageInfoDto = DAOUtil.pageQuery(sqlSb.toString(),params);
        return pageInfoDto;
    }

    /**
     * 根据ID查询
     * @author DuPengXin
     * @date 2016年7月27日
     * @param id
     * @return
     * (non-Javadoc)
     * @see com.yonyou.dms.repair.service.basedata.RepairTypeService#findById(java.lang.Long)
     */

    public TmRepairTypePO findByCode(String id) throws ServiceBizException{
       	LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
      	String groupCode = Utility.getGroupEntity(loginInfo.getDealerCode(), "TM_REPAIR_TYPE");
        TmRepairTypePO repairtype=  TmRepairTypePO.findByCompositeKeys(groupCode, id);
        return repairtype; 
    }

    /**
     * 获取维修类型下拉框
     * @author rongzoujie
     * @date 2016年8月15日
     * @return
     * (non-Javadoc)
     * @see com.yonyou.dms.repair.service.basedata.RepairTypeService#queryRepairType()
     */
    @Override
    public List<Map> queryRepairType() throws ServiceBizException {
        StringBuilder sql = new StringBuilder("SELECT REPAIR_TYPE_CODE,REPAIR_TYPE_NAME,DEALER_CODE FROM tm_repair_type");
        List<Object> params = new ArrayList<Object>();
        return DAOUtil.findAll(sql.toString(), params);
    }

    @Override
    public List<Map> queryLabourPrice()  throws ServiceBizException{
        StringBuilder sql = new StringBuilder("SELECT LABOUR_PRICE,LABOUR_PRICE_CODE,DEALER_CODE FROM tm_labour_price");
        List<Object> params = new ArrayList<Object>();
        return DAOUtil.findAll(sql.toString(), params);
    }

	@Override
	public void updateRepairType(Map<String, String> map) throws ServiceBizException {
       	LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
      	String groupCode = Utility.getGroupEntity(loginInfo.getDealerCode(), "TM_REPAIR_TYPE");
		String name  =	FrameworkUtil.getLoginInfo().getUserName();
		System.out.println(groupCode+"----"+name);
		TmRepairTypePO repairtype= TmRepairTypePO.findByCompositeKeys(groupCode, map.get("repairTypeCode"));
	    if (repairtype != null) {
	    	TmRepairTypePO sto = TmRepairTypePO.findByCompositeKeys(groupCode, map.get("repairTypeCode"));
	    	if (!StringUtils.isNullOrEmpty(map.get("repairTypeCode"))) {
	    	 sto.setString("REPAIR_TYPE_CODE",  map.get("repairTypeCode"));}
	    	if (!StringUtils.isNullOrEmpty(map.get("labourprice"))) {
	    	 sto.setDouble("LABOUR_PRICE", map.get("labourprice"));}
	    	 sto.saveIt();
	    }
	}
}
