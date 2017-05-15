
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.vehicle
 *
 * @File name : InvtrMaTnceServiceImpl.java
 *
 * @Author : yll
 *
 * @Date : 2016年9月14日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年9月14日    yll    1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.vehicle.service.basedata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.DateUtil;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.vehicle.domains.DTO.basedata.InspectPdiDto;
import com.yonyou.dms.vehicle.domains.DTO.basedata.InvtrMaTnceDTO;
import com.yonyou.dms.vehicle.domains.PO.basedata.InspectPdiPO;

/**
 * InvtrMaTnceServiceImpl
 * @author yll
 * @date 2016年9月14日
 */
@Service
public class InvtrMaTnceServiceImpl implements InvtrMaTnceService {



	/**
	 * 查询（销售订单选择VIN号）
	 *  @author xukl
	 * @date 2016年9月14日
	 * @param queryParam
	 * @return
	 * (non-Javadoc)
	 * @see com.yonyou.dms.vehicle.service.basedata.InvtrMaTnceService#qryVIN(java.util.Map)
	 */

	@Override
	public PageInfoDto qryVIN(Map<String, String> queryParam) {
		StringBuilder sb = new StringBuilder("SELECT tvs.VS_STOCK_ID, tvs.DEALER_CODE, tms.STORAGE_NAME, tms.STORAGE_CODE, tvs.OWN_STOCK_STATUS, tvs.FIRST_STOCK_IN_DATE, tvs.VIN, vwp.BRAND_NAME, vwp.BRAND_CODE, vwp.BRAND_ID, vwp.SERIES_NAME, vwp.SERIES_CODE, vwp.SERIES_ID, vwp.MODEL_NAME, vwp.MODEL_CODE, vwp.MODEL_ID, vwp.CONFIG_NAME, vwp.CONFIG_CODE, vwp.PACKAGE_ID, vwp.COLOR_ID, vwp.COLOR_CODE, vwp.COLOR_NAME, vwp.PRODUCT_NAME, vwp.PRODUCT_CODE, tvs.CERTIFICATE_NUMBER, tvs.ENGINE_NO, tvs.PRODUCT_ID, tvp.DIRECTIVE_PRICE AS VEHICLE_PRICE,tvp.DIRECTIVE_PRICE FROM TM_VS_STOCK tvs LEFT JOIN vw_productinfo vwp ON vwp.PRODUCT_ID = tvs.PRODUCT_ID LEFT JOIN tm_store tms ON tms.STORAGE_CODE = tvs.STORAGE_CODE AND tms.DEALER_CODE = tvs.DEALER_CODE left JOIN    tm_vs_product tvp on tvp.PRODUCT_ID=tvs.PRODUCT_ID  WHERE 1 = 1 AND tvs.DISPATCHED_STATUS = ? ");
		List<Object> params = new ArrayList<Object>();
		params.add(DictCodeConstants.NOT_DISPATCHED_STATUS);
		if(!StringUtils.isNullOrEmpty(queryParam.get("vin"))){
			sb.append(" and tvs.VIN like ? ");
			params.add("%"+queryParam.get("vin")+"%");
		}
		if(!StringUtils.isNullOrEmpty(queryParam.get("storageCode"))){
			sb.append(" and tvs.STORAGE_CODE = ?");
			params.add(queryParam.get("storageCode"));
		}
		if(!StringUtils.isNullOrEmpty(queryParam.get("ownStockStatus"))){
			sb.append(" and tvs.OWN_STOCK_STATUS = ?");
			params.add(Integer.parseInt(queryParam.get("ownStockStatus")));
		}
		if(!StringUtils.isNullOrEmpty(queryParam.get("brand"))){
			sb.append(" and vwp.BRAND_CODE = ? ");
			params.add(queryParam.get("brand"));
		}
		if(!StringUtils.isNullOrEmpty(queryParam.get("series"))){
			sb.append(" and vwp.SERIES_CODE = ?");
			params.add(queryParam.get("series"));
		}
		if(!StringUtils.isNullOrEmpty(queryParam.get("model"))){
			sb.append(" and vwp.MODEL_CODE = ?");
			params.add(queryParam.get("model"));
		}
		if(!StringUtils.isNullOrEmpty(queryParam.get("confi"))){
			sb.append(" and vwp.CONFIG_CODE = ? ");
			params.add(queryParam.get("confi"));
		}
		if(!StringUtils.isNullOrEmpty(queryParam.get("productCode"))){
			sb.append(" and vwp.PRODUCT_CODE = ? ");
			params.add(queryParam.get("productCode"));
		}


		PageInfoDto pageInfoDto = DAOUtil.pageQuery(sb.toString(),params);
		return pageInfoDto;
	}



	@Override
	public PageInfoDto QueryInvtrMaTnceData(Map<String, String> queryParam) throws ServiceBizException {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam,params);
		PageInfoDto pageInfoDto = DAOUtil.pageQuery(sql,params);
		return pageInfoDto;
	}

	/**
	 * 根据id查询库存信息
	 * @author yll
	 * @date 2016年9月26日
	 * @param id
	 * @return
	 * @throws ServiceBizException
	 * (non-Javadoc)
	 * @see com.yonyou.dms.vehicle.service.basedata.InvtrMaTnceService#getInvtrMaTnceById(java.lang.Long)
	 */
	@Override
	public Map getInvtrMaTnceById(Long id) throws ServiceBizException {
		StringBuilder sqlSb = new StringBuilder("SELECT tvs.VS_STOCK_ID, tvs.DEALER_CODE, tvs.VIN, tb.BRAND_NAME, ts.SERIES_NAME, tm.MODEL_NAME, tp.CONFIG_NAME, tc.COLOR_NAME, tvs.ENGINE_NO, tvs.KEY_NO, a.CODE_CN_DESC AS DISCHARGE_STANDARD, tvs.EXHAUST_QUANTITY, vp.PRODUCT_CODE, vp.PRODUCT_NAME, tvs.PRODUCTING_AREA, tvs.PRODUCT_DATE AS MANUFACTURE_DATE, tvs.FACTORY_DATE, tvs.REMARK, tvs.HAS_CERTIFICATE, tvs.CERTIFICATE_NUMBER, tvs.CERTIFICATE_LOCUS, b.CODE_CN_DESC AS OEM_TAG, c.CODE_CN_DESC AS IS_DIRECT, tvs.STORAGE_CODE, tvs.STORAGE_POSITION_CODE, d.CODE_CN_DESC AS OWN_STOCK_STATUS, e.CODE_CN_DESC AS DISPATCHED_STATUS, tvs.IS_LOCK, tvs.TRAFFIC_MAR_STATUS, tvs.IS_TEST_DRIVE, f.CODE_CN_DESC AS ENTRY_TYPE, tvs.FIRST_STOCK_IN_DATE, tvs.LATEST_STOCK_IN_DATE, tvs.LAST_STOCK_IN_BY, g.CODE_CN_DESC AS DELIVERY_TYPE, tvs.FIRST_STOCK_OUT_DATE, tvs.LATEST_STOCK_OUT_DATE, tvs.LAST_STOCK_OUT_BY, tvs.VSN, tvs.PURCHASE_PRICE, tvs.OEM_DIRECTIVE_PRICE, tvs.DIRECTIVE_PRICE, tvs.WHOLESALE_DIRECTIVE_PRICE FROM tm_vs_stock tvs LEFT JOIN vw_productinfo vp ON tvs.PRODUCT_ID = vp.PRODUCT_ID LEFT JOIN tm_brand tb ON tb.BRAND_CODE = tvs.BRAND_CODE AND tb.DEALER_CODE=tvs.DEALER_CODE LEFT JOIN tm_series ts ON ts.SERIES_CODE = tvs.SERIES_CODE AND ts.DEALER_CODE=tvs.DEALER_CODE LEFT JOIN tm_model tm ON tm.MODEL_CODE = tvs.MODEL_CODE AND tm.DEALER_CODE=tvs.DEALER_CODE LEFT JOIN tm_package tp ON tp.CONFIG_CODE = tvs.CONFIG_CODE AND tp.DEALER_CODE=tvs.DEALER_CODE LEFT JOIN tm_color tc ON tvs.COLOR = tc.COLOR_ID LEFT JOIN tc_code a ON a.CODE_ID = tvs.DISCHARGE_STANDARD LEFT JOIN tc_code b ON b.CODE_ID = tvs.OEM_TAG LEFT JOIN tc_code c ON c.CODE_ID = tvs.IS_DIRECT LEFT JOIN tc_code d ON d.CODE_ID = tvs.OWN_STOCK_STATUS LEFT JOIN tc_code e ON e.CODE_ID = tvs.DISPATCHED_STATUS LEFT JOIN tc_code f ON f.CODE_ID = tvs.ENTRY_TYPE LEFT JOIN tc_code g ON g.CODE_ID = tvs.DELIVERY_TYPE WHERE 1 = 1");
		List<Object> params = new ArrayList<Object>();
		sqlSb.append(" and tvs.VS_STOCK_ID=?");
		params.add(id);
		Map map = DAOUtil.findFirst(sqlSb.toString(), params);
		return map;

	}

	/**
	 * 车辆库存的pdi检查
	 * @author yll
	 * @date 2016年9月26日
	 * @param id
	 * @param InvtrMaTnceDTO
	 * (non-Javadoc)
	 * @see com.yonyou.dms.vehicle.service.basedata.InvtrMaTnceService#modifyPDI(java.lang.Long, com.yonyou.dms.vehicle.domains.DTO.basedata.InvtrMaTnceDTO)
	 */


	@Override
	public void modifyPDI(String vin,InvtrMaTnceDTO InvtrMaTnceDTO) {
		Integer maxInspectNo=1;
		StringBuilder sqlSb = new StringBuilder("SELECT PDI_INSPECT_ID,DEALER_CODE,max(INSPECT_NO) as INSPECT_NO FROM tt_pdi_inspect where 1=1");
		List<Object> params = new ArrayList<Object>();
		sqlSb.append(" and VIN=?");
		params.add(vin);
		List<Map> map = DAOUtil.findAll(sqlSb.toString(), params);
		if(!CommonUtils.isNullOrEmpty(map)){
			maxInspectNo=Integer.parseInt((String) map.get(0).get("INSPECT_NO"))+1;
		}
		InspectPdiPO.delete("VIN=?",vin);
		for(InspectPdiDto inspectPdiDto:InvtrMaTnceDTO.getPdiList()){
			InspectPdiPO inspectPdiPo=new InspectPdiPO();
			inspectPdiPo.setString("VIN", InvtrMaTnceDTO.getVin());
			inspectPdiPo.setInteger("INSPECT_TYPE", DictCodeConstants.STOCK_KC_PDI);
			inspectPdiPo.setString("INSPECT_NO",maxInspectNo );
			inspectPdiPo.setString("INSPECT_CATEGORY", inspectPdiDto.getInspectCateGory());
			inspectPdiPo.setString("INSPECT_ITEM", inspectPdiDto.getInspectItem());
			inspectPdiPo.setInteger("IS_PROBLEM", inspectPdiDto.getIsProblem());
			inspectPdiPo.setString("INSPECT_DESCRIBE", inspectPdiDto.getInspectDesCribe());
			inspectPdiPo.setString("INSPECT_PERSON", InvtrMaTnceDTO.getInspectPerson());
			inspectPdiPo.setTimestamp("INSPECT_DATE", InvtrMaTnceDTO.getInspectDate());
			inspectPdiPo.saveIt();
		}

	}
	/**
	 * 修改库存相关信息
	 * @author yll
	 * @date 2016年10月7日
	 * @param id
	 * @param InvtrMaTnceDTO
	 * (non-Javadoc)
	 * @see com.yonyou.dms.vehicle.service.basedata.InvtrMaTnceService#modifyInvtrMaTnce(java.lang.Long, com.yonyou.dms.vehicle.domains.DTO.basedata.InvtrMaTnceDTO)
	 */
	@Override
	public void modifyInvtrMaTnce(Long id, InvtrMaTnceDTO InvtrMaTnceDTO) {
	/*	InvtrMaTncePO invtrMaTncePO =InvtrMaTncePO.findById(id);

		//设置库存信息
		invtrMaTncePO.setString("KEY_NO",InvtrMaTnceDTO.getKeyNumber());
		invtrMaTncePO.setString("ENGINE_NO",InvtrMaTnceDTO.getEngineNO());
		invtrMaTncePO.setString("CERTIFICATE_NUMBER", InvtrMaTnceDTO.getCertificateNumber());//合格证号
		invtrMaTncePO.setString("REMARK", InvtrMaTnceDTO.getRemark());
		invtrMaTncePO.setInteger("IS_LOCK", InvtrMaTnceDTO.getIsLock());
		invtrMaTncePO.setInteger("TRAFFIC_MAR_STATUS", InvtrMaTnceDTO.getTrafficMarStatus());
		invtrMaTncePO.setInteger("IS_TEST_DRIVE", InvtrMaTnceDTO.getIsTestDrive());

		invtrMaTncePO.saveIt();*/
	}

	/**
	 * 导出
	 * @author yll
	 * @date 2016年10月17日
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 * (non-Javadoc)
	 * @see com.yonyou.dms.vehicle.service.basedata.InvtrMaTnceService#queryInvtrMaTnceForExport(java.util.Map)
	 */
	@Override
	public List<Map> queryInvtrMaTnceForExport(Map<String, String> queryParam) throws ServiceBizException {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam,params);
		List<Map> resultList = DAOUtil.findAll(sql,params);
		return resultList;
	}
	/**
	 * 
	 * 获取sql
	 * @author yll
	 * @date 2016年10月21日
	 * @param queryParam
	 * @param params
	 * @return
	 */
	private String getQuerySql(Map<String,String> queryParam,List<Object> params){
		StringBuilder sqlSb = new StringBuilder("SELECT tvs.VS_STOCK_ID, tvs.DEALER_CODE, tvs.VIN, tb.BRAND_NAME, ts.SERIES_NAME, tm.MODEL_NAME, tp.CONFIG_NAME, tc.COLOR_NAME, tvs.ENGINE_NO, tvs.KEY_NO, a.CODE_CN_DESC AS DISCHARGE_STANDARD, tvs.EXHAUST_QUANTITY, vp.PRODUCT_CODE, vp.PRODUCT_NAME, tvs.PRODUCTING_AREA, tvs.PRODUCT_DATE AS MANUFACTURE_DATE, tvs.FACTORY_DATE, tvs.REMARK, tvs.HAS_CERTIFICATE, tvs.CERTIFICATE_NUMBER, tvs.CERTIFICATE_LOCUS, b.CODE_CN_DESC AS OEM_TAG, c.CODE_CN_DESC AS IS_DIRECT, tst.STORAGE_NAME, tvs.STORAGE_POSITION_CODE, d.CODE_CN_DESC AS OWN_STOCK_STATUS, e.CODE_CN_DESC AS DISPATCHED_STATUS, tvs.IS_LOCK, tvs.TRAFFIC_MAR_STATUS, tvs.IS_TEST_DRIVE, f.CODE_CN_DESC AS ENTRY_TYPE, tvs.FIRST_STOCK_IN_DATE, tvs.LATEST_STOCK_IN_DATE, tvs.LAST_STOCK_IN_BY, g.CODE_CN_DESC AS DELIVERY_TYPE, tvs.FIRST_STOCK_OUT_DATE, tvs.LATEST_STOCK_OUT_DATE, tvs.LAST_STOCK_OUT_BY, tvs.VSN, tvs.PURCHASE_PRICE, tvs.OEM_DIRECTIVE_PRICE, tvs.DIRECTIVE_PRICE, tvs.WHOLESALE_DIRECTIVE_PRICE FROM tm_vs_stock tvs LEFT JOIN ("+CommonConstants.VM_VS_PRODUCT+") vp ON tvs.PRODUCT_ID = vp.PRODUCT_ID LEFT JOIN tm_brand tb ON tb.BRAND_CODE = tvs.BRAND_CODE and tb.DEALER_CODE=tvs.DEALER_CODE LEFT JOIN tm_series ts ON ts.SERIES_CODE = tvs.SERIES_CODE and ts.DEALER_CODE=tvs.DEALER_CODE LEFT JOIN tm_model tm ON tm.MODEL_CODE = tvs.MODEL_CODE and tm.DEALER_CODE=tvs.DEALER_CODE LEFT JOIN tm_package tp ON tp.CONFIG_CODE = tvs.CONFIG_CODE and tp.DEALER_CODE=tvs.DEALER_CODE LEFT JOIN tm_color tc ON tvs.COLOR = tc.COLOR_ID LEFT JOIN tm_store tst ON tvs.STORAGE_CODE = tst.STORAGE_CODE and tst.DEALER_CODE=tvs.DEALER_CODE LEFT JOIN tc_code a ON a.CODE_ID = tvs.DISCHARGE_STANDARD LEFT JOIN tc_code b ON b.CODE_ID = tvs.OEM_TAG LEFT JOIN tc_code c ON c.CODE_ID = tvs.IS_DIRECT LEFT JOIN tc_code d ON d.CODE_ID = tvs.OWN_STOCK_STATUS LEFT JOIN tc_code e ON e.CODE_ID = tvs.DISPATCHED_STATUS LEFT JOIN tc_code f ON f.CODE_ID = tvs.ENTRY_TYPE LEFT JOIN tc_code g ON g.CODE_ID = tvs.DELIVERY_TYPE WHERE 1 = 1");
		if(!StringUtils.isNullOrEmpty(queryParam.get("storageCode"))){
			sqlSb.append(" and tvs.STORAGE_CODE = ?");
			params.add(queryParam.get("storageCode"));
		}
		if(!StringUtils.isNullOrEmpty(queryParam.get("ownStockStatus"))){
			sqlSb.append(" and tvs.OWN_STOCK_STATUS = ?");
			params.add(queryParam.get("ownStockStatus"));
		}
		if(!StringUtils.isNullOrEmpty(queryParam.get("dispatched"))){
			sqlSb.append(" and tvs.DISPATCHED_STATUS = ?");
			params.add(queryParam.get("dispatched"));
		}
		if(!StringUtils.isNullOrEmpty(queryParam.get("trafficMarStatus"))){
			sqlSb.append(" and tvs.TRAFFIC_MAR_STATUS = ?");
			params.add(queryParam.get("trafficMarStatus"));
		}
		//少品牌车系车型配置
		if(!StringUtils.isNullOrEmpty(queryParam.get("brandName"))){
			sqlSb.append(" and vp.BRAND_CODE = ?");
			params.add(queryParam.get("brandName"));
		}
		if(!StringUtils.isNullOrEmpty(queryParam.get("seriesName"))){
			sqlSb.append(" and vp.SERIES_CODE = ?");
			params.add(queryParam.get("seriesName"));
		}
		if(!StringUtils.isNullOrEmpty(queryParam.get("modelName"))){
			sqlSb.append(" and vp.MODEL_CODE = ?");
			params.add(queryParam.get("modelName"));
		}
		if(!StringUtils.isNullOrEmpty(queryParam.get("configName"))){
			sqlSb.append(" and vp.CONFIG_CODE = ?");
			params.add(queryParam.get("configName"));
		}
		//
		if(!StringUtils.isNullOrEmpty(queryParam.get("color"))){
			sqlSb.append(" and vp.COLOR_ID = ?");
			params.add(queryParam.get("color"));
		}

		if(!StringUtils.isNullOrEmpty(queryParam.get("productCode"))){
			sqlSb.append(" and vp.PRODUCT_CODE like ?");
			params.add("%"+queryParam.get("productCode")+"%");
		}
		if(!StringUtils.isNullOrEmpty(queryParam.get("vin"))){
			sqlSb.append(" and tvs.VIN like ?");
			params.add("%"+queryParam.get("vin")+"%");
		}
		if(!StringUtils.isNullOrEmpty(queryParam.get("OemTag"))){
			sqlSb.append(" and tvs.OEM_TAG = ?");
			params.add(queryParam.get("OemTag"));
		}
		if(!StringUtils.isNullOrEmpty(queryParam.get("entryType"))){
			sqlSb.append(" and tvs.ENTRY_TYPE = ?");
			params.add(queryParam.get("entryType"));
		}
		if(!StringUtils.isNullOrEmpty(queryParam.get("lastStockInDateFrom"))){
			sqlSb.append(" and tvs.LATEST_STOCK_IN_DATE>=? ");
			params.add(DateUtil.parseDefaultDate(queryParam.get("lastStockInDateFrom")));
		}
		if(!StringUtils.isNullOrEmpty(queryParam.get("lastStockInDateTo"))){
			sqlSb.append(" and tvs.LATEST_STOCK_IN_DATE<? ");
			params.add(DateUtil.addOneDay(queryParam.get("lastStockInDateTo")));
		}
		if(!StringUtils.isNullOrEmpty(queryParam.get("deliveryType"))){
			sqlSb.append(" and tvs.DELIVERY_TYPE  = ?");
			params.add(queryParam.get("deliveryType"));
		}
		if(!StringUtils.isNullOrEmpty(queryParam.get("lastStockOutDateFrom"))){
			sqlSb.append(" and tvs.LATEST_STOCK_OUT_DATE>=? ");
			params.add(DateUtil.parseDefaultDate(queryParam.get("lastStockOutDateFrom")));
		}
		if(!StringUtils.isNullOrEmpty(queryParam.get("lastStockOutDateTo"))){
			sqlSb.append(" and tvs.LATEST_STOCK_OUT_DATE<? ");
			params.add(DateUtil.addOneDay(queryParam.get("lastStockOutDateTo")));
		}

		sqlSb.append(" order by tvs.BRAND_CODE asc,tvs.SERIES_CODE asc,tvs.MODEL_CODE asc,tvs.PRODUCT_ID asc");
		return sqlSb.toString();
	}
	/**
	 * 
	 * 获取sql（在途）
	 * @author yll
	 * @date 2016年10月21日
	 * @param queryParam
	 * @param params
	 * @return
	 */
	private String getQuerySqlZT(Map<String,String> queryParam,List<Object> params){
		//此sql含有中文
		StringBuilder sqlSb = new StringBuilder("SELECT tve.DEALER_CODE, tve.VIN, tb.BRAND_NAME, ts.SERIES_NAME, tm.MODEL_NAME, tp.CONFIG_NAME, tc.COLOR_NAME, tve.ENGINE_NO, tve.KEY_NO, a.CODE_CN_DESC AS DISCHARGE_STANDARD, tve.EXHAUST_QUANTITY, vp.PRODUCT_CODE, vp.PRODUCT_NAME, tve.PRODUCTING_AREA, tve.PRODUCT_DATE AS MANUFACTURE_DATE, tve.FACTORY_DATE, tve.REMARK, tve.HAS_CERTIFICATE, tve.CERTIFICATE_NUMBER, tve.CERTIFICATE_LOCUS, b.CODE_CN_DESC AS OEM_TAG, c.CODE_CN_DESC AS IS_DIRECT, tve.STORAGE_CODE, tve.STORAGE_POSITION_CODE, '在途' AS OWN_STOCK_STATUS, tve.TRAFFIC_MAR_STATUS FROM tt_vs_entry tve LEFT JOIN vw_productinfo vp ON tve.PRODUCT_ID = vp.PRODUCT_ID LEFT JOIN tm_brand tb ON tb.BRAND_CODE = tve.BRAND_CODE LEFT JOIN tm_series ts ON ts.SERIES_CODE = tve.SERIES_CODE LEFT JOIN tm_model tm ON tm.MODEL_CODE = tve.MODEL_CODE LEFT JOIN tm_package tp ON tp.CONFIG_CODE = tve.CONFIG_CODE LEFT JOIN tm_color tc ON tve.COLOR = tc.COLOR_ID LEFT JOIN tc_code a ON a.CODE_ID = tve.DISCHARGE_STANDARD LEFT JOIN tc_code b ON b.CODE_ID = tve.OEM_TAG LEFT JOIN tc_code c ON c.CODE_ID = tve.IS_DIRECT where 1=1");
		sqlSb.append(" and tve.IS_ENTRY=?");
		params.add(DictCodeConstants.STATUS_IS_NOT);
		return sqlSb.toString();
	}


	/**
	 * 修改配置
	 * @author yll
	 * @date 2016年10月21日
	 * @param id
	 * @param InvtrMaTnceDTO
	 * (non-Javadoc)
	 * @see com.yonyou.dms.vehicle.service.basedata.InvtrMaTnceService#modifyConfiguration(java.lang.Long, com.yonyou.dms.vehicle.domains.DTO.basedata.InvtrMaTnceDTO)
	 */
	@Override
	public void modifyConfiguration(Long id, InvtrMaTnceDTO InvtrMaTnceDTO) {
/*		InvtrMaTncePO invtrMaTncePO =InvtrMaTncePO.findById(id);*/
		Long productId=InvtrMaTnceDTO.getProductId();
		//Long sid=invtrMaTncePO.getLong("VEHICLE_ID");
		//Long sid =Long.parseLong( stockInPO.toMap().get("VS_SHIPPING_ID").toString());
		//VehiclePO vehiclePO=VehiclePO.findById(sid);
		//MasterDataPO masterDataPO=MasterDataPO.findById(productId);
		//设置车辆信息

		//vehiclePO.setString("PRODUCT_CODE",InvtrMaTnceDTO.getProductCode());
		//vehiclePO.setString("PRODUCT_ID",productId);
		//vehiclePO.setInteger("BRAND_CODE", InvtrMaTnceDTO.getBrandCode());
		//vehiclePO.setInteger("SERIES_CODE",InvtrMaTnceDTO.getSeriesCode());
		//vehiclePO.setInteger("MODEL_CODE", InvtrMaTnceDTO.getModelCode());
		//vehiclePO.setInteger("CONFIG_CODE", masterDataPO.get("PACKAGE_ID"));
		//vehiclePO.setString("COLOR",InvtrMaTnceDTO.getColor());

		/*//设置库存信息
		invtrMaTncePO.setString("CERTIFICATE_NUMBER", InvtrMaTnceDTO.getCertificateNumber());//合格证号
		invtrMaTncePO.setString("REMARK", InvtrMaTnceDTO.getRemark());
		invtrMaTncePO.setInteger("IS_LOCK", InvtrMaTnceDTO.getIsLock());
		invtrMaTncePO.setInteger("TRAFFIC_MAR_STATUS", InvtrMaTnceDTO.getTrafficMarStatus());
		invtrMaTncePO.setInteger("IS_TEST_DRIVE", InvtrMaTnceDTO.getIsTestDrive());*/
		//invtrMaTncePO.setString("PRODUCT_CODE",InvtrMaTnceDTO.getProductCode());
		/*invtrMaTncePO.setString("PRODUCT_ID",productId);
		invtrMaTncePO.setInteger("BRAND_CODE", InvtrMaTnceDTO.getBrandCode());
		invtrMaTncePO.setInteger("SERIES_CODE",InvtrMaTnceDTO.getSeriesCode());
		invtrMaTncePO.setInteger("MODEL_CODE", InvtrMaTnceDTO.getModelCode());
		invtrMaTncePO.setInteger("CONFIG_CODE", InvtrMaTnceDTO.getConfigCode());
		invtrMaTncePO.setString("COLOR",InvtrMaTnceDTO.getColor());
		invtrMaTncePO.saveIt();*/
		//invtrMaTncePO.saveIt();
	}



}
