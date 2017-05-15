/** 
 *Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 *
 * @Author : zhengcong
 *
 * @Date : 2017年5月3日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2017年5月3日    zhengcong    1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */
package com.yonyou.dms.repair.service.advice;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.TtSuggestMaintainLabourPO;
import com.yonyou.dms.common.domains.PO.basedata.TtSuggestMaintainPartPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.repair.domains.DTO.advice.RepairAdviceLabourDTO;
import com.yonyou.dms.repair.domains.DTO.advice.RepairAdvicePartDTO;

/**
 * 工单维修建议serviceImpl
 * 
 * @author zhengcong	
 * @date 2017年5月3日
 */

@Service
@SuppressWarnings({"rawtypes","unchecked"})
public class RepairAdviceServiceImpl implements RepairAdviceService{
	
	
	/**
	 * 工单-建议配件查询
	 * 
	 * @author zhengcong
	 * @date 2017年5月3日
	 */
	@Override
	public PageInfoDto queryPart(String vin)throws ServiceBizException{
        
		StringBuffer sqlsb = new StringBuffer("select SUGGEST_MAINTAIN_PART_ID, DEALER_CODE, VIN, RO_NO, PART_NO, PART_NAME,SUGGEST_DATE,  ");
		sqlsb.append(" SALES_PRICE, QUANTITY, AMOUNT, REASON, IS_VALID, REMARK,D_KEY, VER  from TT_SUGGEST_MAINTAIN_PART  ");
		sqlsb.append(" where DEALER_CODE='"+FrameworkUtil.getLoginInfo().getDealerCode()+"'  ");
		sqlsb.append(" and d_key="+CommonConstants.D_KEY);
		sqlsb.append(" AND IS_VALID ="+DictCodeConstants.DICT_IS_YES);
		sqlsb.append(" AND VIN = '"+vin.substring(3)+"' ");

		PageInfoDto pageInfoDto = DAOUtil.pageQuery(sqlsb.toString(), null);
		return pageInfoDto;

	}

	/**
	 * 工单-建议维修项目查询
	 * 
	 * @author zhengcong
	 * @date 2017年5月3日
	 */
	@Override
	public PageInfoDto queryLabour(String vin)throws ServiceBizException{

		StringBuffer sqlsb = new StringBuffer("select SUGGEST_MAINTAIN_LABOUR_ID, DEALER_CODE, VIN, RO_NO, LABOUR_CODE,  ");
		sqlsb.append(" LABOUR_NAME, STD_LABOUR_HOUR, LABOUR_PRICE, LABOUR_AMOUNT, REASON,      ");
		sqlsb.append(" SUGGEST_DATE, IS_VALID, REMARK, D_KEY, VER,MODEL_LABOUR_CODE   ");
		sqlsb.append(" from TT_SUGGEST_MAINTAIN_LABOUR  ");
		sqlsb.append(" where DEALER_CODE='"+FrameworkUtil.getLoginInfo().getDealerCode()+"'  ");
		sqlsb.append(" and d_key="+CommonConstants.D_KEY);
		sqlsb.append(" AND IS_VALID ="+DictCodeConstants.DICT_IS_YES);
		sqlsb.append(" AND VIN = '"+vin.substring(3)+"' ");

		PageInfoDto pageInfoDto = DAOUtil.pageQuery(sqlsb.toString(), null);
		return pageInfoDto;

	}
	
	
	/**
	 * 工单-建议配件导入查询
	 * 
	 * @author zhengcong
	 * @date 2017年5月6日
	 */
	@Override
	public PageInfoDto queryPartImport(String vin)throws ServiceBizException{
        
		StringBuffer sqlsb = new StringBuffer("SELECT 12781002 IS_SELECTED,A.DEALER_CODE,B.STORAGE_CODE,  B.COST_PRICE PART_COST_PRICE,  ");
		sqlsb.append(" ROUND(B.COST_PRICE*A.QUANTITY,2) PART_COST_AMOUNT, B.SALES_PRICE,B.CLAIM_PRICE, ");
		sqlsb.append(" B.LATEST_PRICE,B.COST_PRICE,  B.STORAGE_POSITION_CODE, ");
		sqlsb.append(" A.SALES_PRICE PART_SALES_PRICE,A.QUANTITY PART_QUANTITY,   ");
		sqlsb.append(" A.AMOUNT PART_SALES_AMOUNT, A.SUGGEST_MAINTAIN_PART_ID,  ");
		sqlsb.append(" A.PART_NO,A.PART_NAME,A.VER,B.D_KEY,A.REMARK   ");
		sqlsb.append(" FROM TT_SUGGEST_MAINTAIN_PART A  LEFT JOIN TM_PART_STOCK B  ");
		sqlsb.append(" ON A.DEALER_CODE = B.DEALER_CODE   ");
		sqlsb.append(" AND A.PART_NO = B.PART_NO   ");
		sqlsb.append(" WHERE A.VIN = '"+vin.substring(3)+"'  ");
		sqlsb.append(" AND A.IS_VALID ="+DictCodeConstants.DICT_IS_YES);
		sqlsb.append(" AND A.DEALER_CODE = '"+FrameworkUtil.getLoginInfo().getDealerCode()+"'  ");
		sqlsb.append(" AND A.D_KEY ="+CommonConstants.D_KEY);

		PageInfoDto pageInfoDto = DAOUtil.pageQuery(sqlsb.toString(), null);
		return pageInfoDto;

	}
	
	
	/**
	 * 工单-建议项目导入查询
	 * 
	 * @author zhengcong
	 * @date 2017年5月6日
	 */
	@Override
	public PageInfoDto queryLabourImport(String vin,String code)throws ServiceBizException{
        
		StringBuffer sqlsb = new StringBuffer("SELECT 12781002 IS_SELECTED,A.DEALER_CODE,A.LABOUR_CODE,A.LABOUR_NAME,  ");
		sqlsb.append(" A.MODEL_LABOUR_CODE,B.LOCAL_LABOUR_CODE,B.LOCAL_LABOUR_NAME, ");
		sqlsb.append(" B.STD_LABOUR_HOUR,B.OEM_LABOUR_HOUR,A.VER,B.ASSIGN_LABOUR_HOUR, ");
		sqlsb.append(" B.WORKER_TYPE_CODE,A.LABOUR_PRICE,A.SUGGEST_MAINTAIN_LABOUR_ID,A.REMARK   ");
		sqlsb.append(" FROM TT_SUGGEST_MAINTAIN_LABOUR A  ");
		sqlsb.append(" LEFT JOIN ("+CommonConstants.VM_REPAIR_ITEM+") B ON   ");
		sqlsb.append(" A.DEALER_CODE = B.DEALER_CODE AND A.LABOUR_CODE = B.LABOUR_CODE   ");
		sqlsb.append(" AND B.MODEL_LABOUR_CODE = '"+code.substring(4)+"'  ");
		sqlsb.append(" WHERE A.VIN = '"+vin.substring(3)+"'  ");
		sqlsb.append(" AND A.IS_VALID ="+DictCodeConstants.DICT_IS_YES);
		sqlsb.append(" AND A.DEALER_CODE = '"+FrameworkUtil.getLoginInfo().getDealerCode()+"'  ");
		sqlsb.append(" AND A.D_KEY ="+CommonConstants.D_KEY);

		PageInfoDto pageInfoDto = DAOUtil.pageQuery(sqlsb.toString(), null);
		return pageInfoDto;

	}

	
	/**
	 * 新增、修改、删除配件-保存
     * @author zhengcong
     * @date 2017年5月3日
	 */
	@Override
	public void pSaveData(RepairAdvicePartDTO dataDTO)throws ServiceBizException {	
		
		List<Map> list = dataDTO.getAdvice_part();
		String[] delrows;
        if (list!=null&&list.size()>0) {       	
        	for (Map map : list) {
        	if(!StringUtils.isNullOrEmpty(map.get("rowKey"))) {
      		   if(map.get("rowKey").equals("A")) {
        		TtSuggestMaintainPartPO dataPO=new TtSuggestMaintainPartPO();
        		dataPO.setString("DEALER_CODE", FrameworkUtil.getLoginInfo().getDealerCode());
        		dataPO.setString("RO_NO",map.get("RO_NO"));
        		dataPO.setString("VIN",map.get("VIN"));
        		dataPO.setString("PART_NO",map.get("PART_NO"));
        		dataPO.setString("PART_NAME",map.get("PART_NAME"));
        		dataPO.setDate("SUGGEST_DATE",map.get("SUGGEST_DATE"));
        		dataPO.setString("REMARK",map.get("REMARK"));
        		dataPO.setDouble("SALES_PRICE",map.get("SALES_PRICE"));
        		dataPO.setDouble("QUANTITY",map.get("QUANTITY"));
        		dataPO.setDouble("AMOUNT",map.get("AMOUNT"));
        		dataPO.setInteger("REASON",map.get("REASON"));
        		dataPO.setInteger("IS_VALID",DictCodeConstants.DICT_IS_YES);
        		dataPO.setInteger("D_KEY",CommonConstants.D_KEY);
        		dataPO.saveIt();
      		   }	
        	}
        	}	
        }
        
        if(!StringUtils.isNullOrEmpty(dataDTO.getDelPID())) { 
        	delrows=dataDTO.getDelPID().split(",");
        	for (int i = 0; i < delrows.length; i++) {
        		int rowid=Integer.parseInt(delrows[i]);
        		TtSuggestMaintainPartPO dataPO=TtSuggestMaintainPartPO.findById(rowid);
        		dataPO.delete();        		
        	}	
        }	
	}	
	
	/**
	 * 新增、修改、删除项目-保存
     * @author zhengcong
     * @date 2017年5月3日
	 */
	@Override
	public void lSaveData(RepairAdviceLabourDTO dataDTO)throws ServiceBizException {	
		
		List<Map> list = dataDTO.getAdvice_labour();
		String[] delrows;
        if (list!=null&&list.size()>0) {       	
        	for (Map map : list) {
        	if(!StringUtils.isNullOrEmpty(map.get("rowKey"))) {
      		   if(map.get("rowKey").equals("A")) {
      			TtSuggestMaintainLabourPO dataPO=new TtSuggestMaintainLabourPO();
        		dataPO.setString("DEALER_CODE", FrameworkUtil.getLoginInfo().getDealerCode());
        		dataPO.setString("RO_NO",map.get("RO_NO"));
        		dataPO.setString("VIN",map.get("VIN"));
        		dataPO.setString("LABOUR_CODE",map.get("LABOUR_CODE"));
        		dataPO.setString("LABOUR_NAME",map.get("LABOUR_NAME"));
        		dataPO.setDate("SUGGEST_DATE",map.get("SUGGEST_DATE"));
        		dataPO.setString("REMARK",map.get("REMARK"));
        		dataPO.setDouble("STD_LABOUR_HOUR",map.get("STD_LABOUR_HOUR"));
        		dataPO.setDouble("LABOUR_PRICE",map.get("LABOUR_PRICE"));
        		dataPO.setDouble("LABOUR_AMOUNT",map.get("LABOUR_AMOUNT"));
        		dataPO.setInteger("REASON",map.get("REASON"));
        		dataPO.setInteger("IS_VALID",DictCodeConstants.DICT_IS_YES);
        		dataPO.setInteger("D_KEY",CommonConstants.D_KEY);
        		dataPO.setString("MODEL_LABOUR_CODE",map.get("MODEL_LABOUR_CODE"));
        		dataPO.saveIt();
      		   }
        		}
        	}	
        }
        
        if(!StringUtils.isNullOrEmpty(dataDTO.getDelLID())) { 
        	delrows=dataDTO.getDelLID().split(",");
        	for (int i = 0; i < delrows.length; i++) {
        		int rowid=Integer.parseInt(delrows[i]);
        		TtSuggestMaintainLabourPO dataPO=TtSuggestMaintainLabourPO.findById(rowid);
        		dataPO.delete();        		
        	}	
        }	
	}	
	

}
