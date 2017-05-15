/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : LabourPriceService1.java
*
 * @Author : zhengcong
 *
 * @Date : 2017年3月24日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2017年3月24日    zhengcong    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
package com.yonyou.dms.repair.service.basedata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.DTO.basedata.ChargeTypeDTO;
import com.yonyou.dms.common.domains.PO.basedata.ChargeTypePO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.exception.ServiceBizException;


/**
 * 
*收费类别serviceimpl 
 * @author zhengcong
 * @date 2017年3月24日
 */
@Service
@SuppressWarnings({"rawtypes","unchecked"})
public class ChargeTypeServiceImpl implements ChargeTypeService{
	
	private String ismanage="0";
    /**
     * 根据传入信息查询
 * @author zhengcong
 * @date 2017年3月24日
     */
    @Override
    public PageInfoDto queryFee(Map<String, String> queryParam)throws ServiceBizException {
        StringBuffer sqlsb = new StringBuffer(
                "select DEALER_CODE, MANAGE_SORT_CODE, MANAGE_SORT_NAME, LABOUR_RATE, REPAIR_PART_RATE, ");
        sqlsb.append("SALES_PART_RATE, ADD_ITEM_RATE, LABOUR_AMOUNT_RATE, OVERHEAD_EXPENSES_RATE, IS_MANAGING from tm_manage_type  where 1=1 ");
        List<Object> params = new ArrayList<Object>();
//        if(!StringUtils.isNullOrEmpty(queryParam.get("LABOUR_PRICE_CODE"))) {
//            sqlsb.append(" and LABOUR_PRICE_CODE like ?");
//            params.add("%"+queryParam.get("LABOUR_PRICE_CODE")+"%");
//        }
        sqlsb.append(" and IS_MANAGING = ?");
        params.add(queryParam.get("IS_MANAGING"));

        ismanage=queryParam.get("IS_MANAGING");

        PageInfoDto pageInfoDto = DAOUtil.pageQuery(sqlsb.toString(),params);
        return pageInfoDto;
    }
    
    
    /**
     * 根据CODE查询
     * @author zhengcong
     * @date 2017年3月24日
     */
	@Override
	public Map<String, String> findByCode(String MANAGE_SORT_CODE,Object IS_MANAGING) throws ServiceBizException {
    StringBuilder sqlsb = new StringBuilder("select DEALER_CODE, MANAGE_SORT_CODE, MANAGE_SORT_NAME, LABOUR_RATE, REPAIR_PART_RATE, "); 
    sqlsb.append("SALES_PART_RATE, ADD_ITEM_RATE, LABOUR_AMOUNT_RATE, OVERHEAD_EXPENSES_RATE, IS_MANAGING from tm_manage_type  where 1=1 ");
    sqlsb.append("and dealer_code ='" + FrameworkUtil.getLoginInfo().getDealerCode() + "' ");
    List queryParam = new ArrayList();
    sqlsb.append(" and MANAGE_SORT_CODE = ? ");   
    queryParam.add(MANAGE_SORT_CODE);
    sqlsb.append(" and IS_MANAGING = ? ");
    queryParam.add(IS_MANAGING);
    return DAOUtil.findFirst(sqlsb.toString(), queryParam);
}


	/**
	 * 更新
     * @author zhengcong
     * @date 2017年3月25日
	 */

	@Override
	public void modifyByCode(String MANAGE_SORT_CODE,Object IS_MANAGING,ChargeTypeDTO ctdto) throws ServiceBizException{
		ChargeTypePO ctpo=ChargeTypePO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),MANAGE_SORT_CODE,IS_MANAGING);
		ctpo.setString("MANAGE_SORT_NAME", ctdto.getManageSortName());   
		ctpo.setDouble("LABOUR_AMOUNT_RATE", ctdto.getLabourAmountRate());
		ctpo.setDouble("REPAIR_PART_RATE", ctdto.getRepairPartRate());
		ctpo.setDouble("SALES_PART_RATE", ctdto.getSalesPartRate());
		ctpo.setDouble("ADD_ITEM_RATE", ctdto.getAddItemRate());
		ctpo.setDouble("LABOUR_RATE", ctdto.getLabourRate());
		ctpo.setDouble("OVERHEAD_EXPENSES_RATE", ctdto.getOverheadExpensesRate());
		ctpo.saveIt();
	}

	/**
	 * 新增
     * @author zhengcong
     * @date 2017年3月25日
	 */
	@Override
	public void addFee(ChargeTypeDTO ctdto)throws ServiceBizException {		
		StringBuffer sb= new StringBuffer("select DEALER_CODE,MANAGE_SORT_CODE, MANAGE_SORT_NAME,IS_MANAGING from tm_manage_type where 1=1 ");
		sb.append(" and dealer_code ='" + FrameworkUtil.getLoginInfo().getDealerCode() + "' ");
//		System.out.println(ismanage+"/"+ismanage.equals("12781001"));
		if(ismanage.equals("12781001")) {
		sb.append(" and IS_MANAGING=12781001 ");				
		}else if(ismanage.equals("12781002")){
		sb.append(" and IS_MANAGING=12781002 ");	
		}else {
		    throw new ServiceBizException("未获取到ismange值，请重操作");
		}
		sb.append(" and MANAGE_SORT_CODE= ? ");
	    List<Object> list=new ArrayList<Object>();
	    list.add(ctdto.getManageSortCode());

	    List<Map> map=DAOUtil.findAll(sb.toString(), list);
        if(map.size()>0 ){
	        throw new ServiceBizException("代码不允许重复");
	    }else{
	   
	    	ChargeTypePO ctpo=new ChargeTypePO();
	    	ctpo.setString("DEALER_CODE", FrameworkUtil.getLoginInfo().getDealerCode());
	    	ctpo.setString("MANAGE_SORT_CODE", ctdto.getManageSortCode()); 
			ctpo.setString("MANAGE_SORT_NAME", ctdto.getManageSortName());   
			ctpo.setDouble("LABOUR_AMOUNT_RATE", ctdto.getLabourAmountRate());
			ctpo.setDouble("REPAIR_PART_RATE", ctdto.getRepairPartRate());
			ctpo.setDouble("SALES_PART_RATE", ctdto.getSalesPartRate());
			ctpo.setDouble("ADD_ITEM_RATE", ctdto.getAddItemRate());
			ctpo.setDouble("LABOUR_RATE", ctdto.getLabourRate());
			ctpo.setDouble("OVERHEAD_EXPENSES_RATE", ctdto.getOverheadExpensesRate());
			if(ismanage.equals("12781001")) {
				ctpo.setInteger("IS_MANAGING", 12781001);				
				}else if(ismanage.equals("12781002")){
				ctpo.setInteger("IS_MANAGING", 12781002);	
				}else {
				    throw new ServiceBizException("没有获取到ismange值，请关闭本页面后重操作");
				}

			ctpo.saveIt();
		
	    }
	}


}
