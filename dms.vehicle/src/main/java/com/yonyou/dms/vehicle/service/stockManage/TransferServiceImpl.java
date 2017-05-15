
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.vehicle
*
* @File name : TransferServiceImpl.java
*
* @Author : yangjie
*
* @Date : 2017年1月8日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年1月8日    yangjie    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.vehicle.service.stockManage;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.javalite.activejdbc.Base;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.TtVsStockTransferPO;
import com.yonyou.dms.common.domains.PO.basedata.VsProductPO;
import com.yonyou.dms.common.domains.PO.basedata.VsStockLogPO;
import com.yonyou.dms.common.domains.PO.basedata.VsStockPO;
import com.yonyou.dms.commonSales.domains.PO.stockmanage.SalesOrderPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.domains.PO.stockManage.VsTransferItemPO;

/**
 * TODO description
 * 
 * @author yangjie
 * @date 2017年1月8日
 */

@Service
@SuppressWarnings({ "rawtypes", "unchecked" })
public class TransferServiceImpl implements TransferService {

	/**
	 * @author yangjie
	 * @date 2017年1月9日
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 *             (non-Javadoc)
	 * @see com.yonyou.dms.vehicle.service.stockManage.TransferService#findAllRepository(java.util.Map)
	 */

	private static List<Map> findOldLocationList;// 保存修改前库位信息

	@Override
	public PageInfoDto findAllRepository(Map<String, String> queryParam) throws ServiceBizException {
		StringBuffer sb = new StringBuffer(
				"SELECT A.DEALER_CODE,A.ST_NO,A.SHEET_CREATE_DATE,tu.USER_NAME as SHEET_CREATED_BY,A.IS_ALL_FINISHED FROM TT_VS_STOCK_TRANSFER A ");
		sb.append("LEFT JOIN TM_USER tu ON A.SHEET_CREATED_BY = tu.USER_ID AND A.DEALER_CODE = tu.DEALER_CODE where A.dealer_code='" + FrameworkUtil.getLoginInfo().getDealerCode() + "'");
		sb.append(" and d_key=" + DictCodeConstants.D_KEY + " ");
		sqlToLike(sb, queryParam.get("stNo"), "ST_NO", "A");
		sqlToDate(sb, queryParam.get("begin"), queryParam.get("end"), "SHEET_CREATE_DATE", "A");
		sqlToEquals(sb, queryParam.get("isAllFinished"), "IS_ALL_FINISHED", "A");
		return DAOUtil.pageQuery(sb.toString(), null);
	}

	/**
	 * @author yangjie
	 * @date 2017年1月9日
	 * @param stNo
	 * @return
	 * @throws ServiceBizException
	 *             (non-Javadoc)
	 * @see com.yonyou.dms.vehicle.service.stockManage.TransferService#findAllRepositoryDetails(java.lang.String)
	 */

	@Override
	public List<Map> findAllRepositoryDetails(String stNo) throws ServiceBizException {
		StringBuffer sb = new StringBuffer();
		sb.append(
				"SELECT A.D_KEY,A.DEALER_CODE AS DEALER_CODE,0 AS IS_SELECTED,A.VIN as VIN,A.OUT_STORAGE as OUT_STORAGE,A.OUT_POSITION,TSE.STORAGE_NAME AS IN_STORAGE,A.IN_POSITION as IN_POSITION,A.ST_NO,A.REMARK as REMARK,A.TRANSACTOR as TRANSACTOR,A.TAKE_CAR_MAN as TAKE_CAR_MAN,");
		sb.append(
				"A.TRANSFER_DATE as TRANSFER_DATE,C.KEY_NUMBER,C.CERTIFICATE_NUMBER,C.HAS_CERTIFICATE,C.STOCK_STATUS as STOCK_STATUS,");
		sb.append(
				"A.PURCHASE_PRICE,tb.BRAND_NAME as BRAND_NAME,ts.SERIES_NAME AS SERIES_NAME,tm.MODEL_NAME AS MODEL_NAME,tc.CONFIG_NAME AS CONFIG_NAME,tco.COLOR_NAME AS COLOR_NAME,B.BRAND_CODE,B.SERIES_CODE,B.MODEL_CODE,B.CONFIG_CODE,B.COLOR_CODE,A.IS_FINISHED as IS_FINISHED,");
		sb.append(
				"A.FINISHED_DATE,A.FINISHED_BY,C.PRODUCT_CODE,B.PRODUCT_NAME,C.ADDITIONAL_COST as ADDITIONAL_COST,C.MAR_STATUS as MAR_STATUS,C.DIRECTIVE_PRICE ");
		sb.append(" FROM TT_VS_TRANSFER_ITEM A LEFT JOIN TM_STORAGE TSE ON A.IN_STORAGE=TSE.STORAGE_CODE");
		sb.append(" LEFT JOIN TM_VS_STOCK C ON A.DEALER_CODE=C.DEALER_CODE AND A.D_KEY=C.D_KEY AND A.VIN=C.VIN ");
		sb.append(" LEFT JOIN (" + CommonConstants.VM_VS_PRODUCT);
		sb.append(") B ON C.DEALER_CODE=B.DEALER_CODE AND C.D_KEY=B.D_KEY AND C.PRODUCT_CODE=B.PRODUCT_CODE ");
		sb.append(
				" LEFT JOIN tm_brand tb ON b.BRAND_CODE=tb.BRAND_CODE  AND b.DEALER_CODE = tb.DEALER_CODE LEFT JOIN tm_series ts ON b.SERIES_CODE=ts.SERIES_CODE  AND b.DEALER_CODE = ts.DEALER_CODE ");
		sb.append(
				" LEFT JOIN tm_model tm ON b.MODEL_CODE=tm.MODEL_CODE AND b.DEALER_CODE = tm.DEALER_CODE  LEFT JOIN tm_configuration tc ON b.CONFIG_CODE=tc.CONFIG_CODE AND b.DEALER_CODE = tc.DEALER_CODE ");
		sb.append(" LEFT JOIN tm_color tco ON b.COLOR_CODE=tco.COLOR_CODE  AND b.DEALER_CODE = tco.DEALER_CODE ");
		sb.append(" where A.DEALER_CODE='" + FrameworkUtil.getLoginInfo().getDealerCode() + "'");
		sb.append(" and A.D_KEY=" + DictCodeConstants.D_KEY + " ");
		sqlToLike(sb, stNo, "ST_NO", null);
		return DAOUtil.findAll(sb.toString(), null);
	}

	/**
	 * @author yangjie
	 * @date 2017年1月9日
	 * @param stNo
	 * @param vin
	 * @param map
	 *            (non-Javadoc)
	 * @see com.yonyou.dms.vehicle.service.stockManage.TransferService#batchEditRepository(java.lang.String,
	 *      java.lang.String, java.util.Map)
	 */

	@Override
	public void batchEditRepository(String stNo, String vin, Map<String, Object> map) {
		VsTransferItemPO po = VsTransferItemPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(), stNo,
				vin);
		MapToPoForEdit(po, map);
		po.saveIt();
	}

	/**
	 * @author yangjie
	 * @date 2017年1月9日
	 * @param queryParam
	 * @return (non-Javadoc)
	 * @see com.yonyou.dms.vehicle.service.stockManage.TransferService#findAllVehicleInfo(java.util.Map)
	 */

	@Override
	public PageInfoDto findAllVehicleInfo(Map<String, String> queryParam) {
		StringBuffer sb = new StringBuffer();
		sb.append(
				"SELECT A.DEALER_CODE AS DEALER_CODE,ab.STORAGE_NAME as OUT_STORAGE,tb.BRAND_NAME AS BRAND_NAME,ts.SERIES_NAME AS SERIES_NAME,tm.MODEL_NAME AS MODEL_NAME,tc.CONFIG_NAME AS CONFIG_NAME,tco.COLOR_NAME AS COLOR_NAME,0 AS IS_SELECTED,A.VIN,B.BRAND_CODE,B.SERIES_CODE,B.MODEL_CODE,B.CONFIG_CODE,");
		sb.append("B.COLOR_CODE,B.PRODUCT_TYPE, A.STOCK_STATUS,A.KEY_NUMBER,A.CERTIFICATE_NUMBER,");
		sb.append("A.HAS_CERTIFICATE,A.STORAGE_CODE,A.PRODUCT_CODE,A.STORAGE_POSITION_CODE,");
		sb.append("A.DIRECTIVE_PRICE,A.PURCHASE_PRICE,A.ADDITIONAL_COST,A.MAR_STATUS");
		sb.append(" FROM TM_VS_STOCK A LEFT JOIN (" + CommonConstants.VM_VS_PRODUCT);
		sb.append(") B ON A.DEALER_CODE=B.DEALER_CODE AND A.D_KEY=B.D_KEY AND A.PRODUCT_CODE=B.PRODUCT_CODE");
		sb.append(
				" LEFT JOIN tm_brand tb  ON b.BRAND_CODE = tb.BRAND_CODE AND b.DEALER_CODE = tb.DEALER_CODE LEFT JOIN tm_series ts ON b.SERIES_CODE = ts.SERIES_CODE AND b.BRAND_CODE = ts.BRAND_CODE AND b.DEALER_CODE = ts.DEALER_CODE LEFT JOIN tm_model tm ON b.MODEL_CODE = tm.MODEL_CODE AND b.SERIES_CODE = tm.SERIES_CODE AND b.BRAND_CODE = tm.BRAND_CODE AND b.DEALER_CODE = tm.DEALER_CODE LEFT JOIN tm_configuration tc ON b.CONFIG_CODE = tc.CONFIG_CODE  AND b.MODEL_CODE = tc.MODEL_CODE AND b.SERIES_CODE = tc.SERIES_CODE AND b.BRAND_CODE = tc.BRAND_CODE AND b.DEALER_CODE = tc.DEALER_CODE LEFT JOIN tm_color tco ON b.COLOR_CODE = tco.COLOR_CODE AND b.DEALER_CODE = tco.DEALER_CODE ");
		sb.append(" LEFT JOIN (" + CommonConstants.VM_STORAGE
				+ ") ab ON ab.STORAGE_CODE=A.STORAGE_CODE AND A.DEALER_CODE = ab.DEALER_CODE");
		sb.append(" WHERE A.DEALER_CODE='" + FrameworkUtil.getLoginInfo().getDealerCode() + "'  AND A.D_KEY="
				+ DictCodeConstants.D_KEY + " AND A.STOCK_STATUS='" + DictCodeConstants.DISPATCHED_STATUS_INSTOCK
				+ "'  AND A.DISPATCHED_STATUS = '" + DictCodeConstants.DICT_DISPATCHED_STATUS_NOT_DISPATCHED + "' ");
		sqlToLike(sb, queryParam.get("vin"), "VIN", "A");
		sqlToLike(sb, queryParam.get("storageCode"), "STORAGE_CODE", "A");
		sqlToLike(sb, queryParam.get("brandCode"), "BRAND_CODE", "B");
		sqlToEquals(sb, queryParam.get("outPosition"), "STORAGE_POSITION_CODE", "A");
		return DAOUtil.pageQuery(sb.toString(), null);
	}

	/**
	 * @author yangjie
	 * @date 2017年1月9日
	 * @param map
	 *            (non-Javadoc)
	 * @see com.yonyou.dms.vehicle.service.stockManage.TransferService#addInfoToItem(java.util.Map)
	 */
	@Override
	public void addInfoToList(Map<String, Object> map) {
		TtVsStockTransferPO po = new TtVsStockTransferPO();
		po.setString("ST_NO", map.get("stNo"));
		System.out.println(map.get("stNo"));
		po.setString("SHEET_CREATED_BY", FrameworkUtil.getLoginInfo().getUserId());
		po.setDate("SHEET_CREATE_DATE", new Date());
		po.setString("REMARK", map.get("remark"));
		System.out.println(FrameworkUtil.getLoginInfo().getDealerCode());
		po.setString("DEALER_CODE", FrameworkUtil.getLoginInfo().getDealerCode());
		po.setInteger("D_KEY", CommonConstants.D_KEY);
		po.setLong("IS_ALL_FINISHED", DictCodeConstants.IS_NOT);
		po.saveIt();
		System.out.println(po.getString("CREATED_AT"));
	}

	@Override
	public void addInfoToItem(Map<String, Object> map) {
		VsTransferItemPO vti = new VsTransferItemPO();
		MapToPoForAddTI(vti, map);// 移库单明细
		vti.saveIt();
	}

	/**
	 * @author yangjie
	 * @date 2017年1月9日
	 * @param stNo
	 * @param vin
	 *            (non-Javadoc)
	 * @see com.yonyou.dms.vehicle.service.stockManage.TransferService#deleteItem(java.lang.String,
	 *      java.lang.String)
	 */

	@Override
	public void deleteItem(String stNo, String vin) {
		VsTransferItemPO po = VsTransferItemPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(), stNo,
				vin);
		po.delete();
	}

	/**
	 * @author yangjie
	 * @date 2017年1月9日
	 * @param stNo
	 *            (non-Javadoc)
	 * @see com.yonyou.dms.vehicle.service.stockManage.TransferService#deleteTransfer(java.lang.String)
	 */

	@Override
	public void deleteTransfer(String stNo) {
		String sql = "select dealer_code,st_no,vin from TT_VS_TRANSFER_ITEM where st_no=? and dealer_code="
				+ FrameworkUtil.getLoginInfo().getDealerCode();
		List<String> param = new ArrayList<String>();
		param.add(stNo);
		List<Map> list = DAOUtil.findAll(sql, param);
		for (Map map : list) {
			VsTransferItemPO po = VsTransferItemPO.findByCompositeKeys(map.get("dealer_code"), map.get("st_no"),
					map.get("vin"));
			po.delete();
		}
		TtVsStockTransferPO st = TtVsStockTransferPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),
				stNo);
		st.delete();
	}

	/**
	 * @author yangjie
	 * @date 2017年1月9日
	 * @param map
	 *            (non-Javadoc)
	 * @see com.yonyou.dms.vehicle.service.stockManage.TransferService#btnTransfer(java.util.Map)
	 */

	@Override
	public void btnTransfer(Map<String, Object> map) {

		// 销售订单信息修改
		SalesOrderPO so = SalesOrderPO.findFirst("VIN=?", map.get("vin").toString());
		if (so != null) {
			MapToPoForSalesOrder(so, map);
			so.saveIt();
		}

		// 车辆库存表信息修改
		VsStockPO vs = VsStockPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),
				map.get("vin").toString());
		if (vs != null) {
			MapToPoForVehicleStock(vs, map);
			vs.saveIt();
		}
		
		map.put("OPERATION_TYPE", DictCodeConstants.DICT_VEHICLE_STORAGE_TYPE_MOVE_POSITION);

		// 新增日志信息
		VsStockLogPO log = new VsStockLogPO();
		MapToPoForVsStockLog(log, map);
		log.saveIt();

		System.out.println(map.get("stNo").toString() + "====================" + map.get("vin").toString());

		// 子表入账改为是
		VsTransferItemPO fi = VsTransferItemPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),
				map.get("stNo").toString(), map.get("vin").toString());
		fi.setInteger("IS_FINISHED", DictCodeConstants.IS_YES);
		fi.setDate("TRANSFER_DATE", new Date());
		fi.set("FINISHED_DATE", new Timestamp(System.currentTimeMillis()));
		
		fi.saveIt();
	}

	/**
	 * 下拉框用，查询所有经办人 TODO description
	 * 
	 * @author yangjie
	 * @date 2017年1月10日
	 * @return
	 */
	@Override
	public List<Map> findAllEmp() {
		StringBuffer sb = new StringBuffer();
		sb.append(
				"SELECT DISTINCT A.WORKER_TYPE_CODE,C.ORG_CODE,A.DEALER_CODE,A.E_MAIL,A.DOWN_TAG,A.PHONE,A.TECHNICIAN_GRADE,A.EMPLOYEE_NO,A.EMPLOYEE_NAME,A.POSITION_CODE,A.LABOUR_POSITION_CODE,A.IS_VALID,A.BIRTHDAY,A.IS_TAKE_PART,A.IS_SERVICE_ADVISOR, ");
		sb.append(
				" A.MOBILE,A.WORKGROUP_CODE,A.RESUME,A.TRAINING,A.ZIP_CODE,A.LABOUR_FACTOR,A.IS_TRACER,A.FOUND_DATE,A.IS_TEST_DRIVER,A.GENDER,A.CERTIFICATE_ID,A.IS_TECHNICIAN,A.IS_CHECKER,A.IS_CONSULTANT,A.ADDRESS,A.IS_INSURANCE_ADVISOR,A.IS_MAINTAIN_ADVISOR,B.ORG_NAME AS DEPT_NAME ");
		sb.append(
				" FROM TM_EMPLOYEE  A LEFT JOIN tm_organization b ON  a.DEALER_CODE = b.DEALER_CODE AND a.ORG_CODE = b.ORG_CODE ");
		sb.append(
				" LEFT JOIN TM_USER C ON  a.DEALER_CODE = C.DEALER_CODE AND a.EMPLOYEE_NO = C.EMPLOYEE_NO AND C.USER_STATUS ="
						+ DictCodeConstants.IS_YES + " ");
		sb.append(" WHERE A.IS_VALID = " + DictCodeConstants.IS_YES
				+ "  AND A.DEALER_CODE IN ( SELECT SHARE_ENTITY FROM (" + CommonConstants.VM_ENTITY_SHARE_WITH);
		sb.append(" ) k WHERE k.DEALER_CODE ='" + FrameworkUtil.getLoginInfo().getDealerCode()
				+ "' AND k.biz_code ='UNIFIED_VIEW' ) ");
		sb.append(" AND (( DOWN_TAG = " + DictCodeConstants.IS_YES + " AND FROM_ENTITY <> '"
				+ FrameworkUtil.getLoginInfo().getDealerCode() + "')  OR ( DOWN_TAG = " + DictCodeConstants.IS_NOT
				+ "  AND A.FROM_ENTITY IS NULL)) ");
		return DAOUtil.findAll(sb.toString(), null);
	}

	/**
	 * 查询所有VIN TODO description
	 * 
	 * @author yangjie
	 * @date 2017年1月10日
	 * @return
	 */
	@Override
	public List<Map> findAllVIN(String[] vins) throws ServiceBizException {
		StringBuffer sb = new StringBuffer();
		sb.append(
				"SELECT A.DEALER_CODE AS DEALER_CODE,ab.STORAGE_NAME as OUT_STORAGE,tb.BRAND_NAME AS BRAND_NAME,ts.SERIES_NAME AS SERIES_NAME,tm.MODEL_NAME AS MODEL_NAME,tc.CONFIG_NAME AS CONFIG_NAME,tco.COLOR_NAME AS COLOR_NAME,0 AS IS_SELECTED,A.VIN,B.BRAND_CODE,B.SERIES_CODE,B.MODEL_CODE,B.CONFIG_CODE,");
		sb.append("B.COLOR_CODE,B.PRODUCT_TYPE, A.STOCK_STATUS,A.KEY_NUMBER,A.CERTIFICATE_NUMBER,");
		sb.append("A.HAS_CERTIFICATE,A.STORAGE_CODE,A.PRODUCT_CODE,A.STORAGE_POSITION_CODE,");
		sb.append("A.DIRECTIVE_PRICE,A.PURCHASE_PRICE,A.ADDITIONAL_COST,A.MAR_STATUS");
		sb.append(" FROM TM_VS_STOCK A LEFT JOIN (" + CommonConstants.VM_VS_PRODUCT);
		sb.append(") B ON A.DEALER_CODE=B.DEALER_CODE AND A.D_KEY=B.D_KEY AND A.PRODUCT_CODE=B.PRODUCT_CODE");
		sb.append(
				" LEFT JOIN tm_brand tb ON b.BRAND_CODE=tb.BRAND_CODE  AND b.DEALER_CODE = tb.DEALER_CODE LEFT JOIN tm_series ts ON b.SERIES_CODE=ts.SERIES_CODE AND b.DEALER_CODE = ts.DEALER_CODE ");
		sb.append(
				" LEFT JOIN tm_model tm ON b.MODEL_CODE=tm.MODEL_CODE  AND b.DEALER_CODE = tm.DEALER_CODE  LEFT JOIN tm_configuration tc ON b.CONFIG_CODE=tc.CONFIG_CODE  AND b.DEALER_CODE = tc.DEALER_CODE ");
		sb.append(" LEFT JOIN tm_color tco ON b.COLOR_CODE=tco.COLOR_CODE  AND b.DEALER_CODE = tco.DEALER_CODE LEFT JOIN (" + CommonConstants.VM_STORAGE
				+ ") ab ON ab.STORAGE_CODE=A.STORAGE_CODE AND A.DEALER_CODE = ab.DEALER_CODE");
		sb.append(" WHERE A.DEALER_CODE='" + FrameworkUtil.getLoginInfo().getDealerCode() + "'  AND A.D_KEY="
				+ DictCodeConstants.D_KEY + " AND A.STOCK_STATUS='" + DictCodeConstants.DISPATCHED_STATUS_INSTOCK
				+ "'  AND A.DISPATCHED_STATUS='" + DictCodeConstants.NOT_DISPATCHED_STATUS + "' ");
		sb.append(" AND A.VIN IN (");
		for (int i = 0; i < vins.length; i++) {
			sb.append("'" + vins[i] + "'");
			if (i != vins.length - 1) {
				sb.append(",");
			}
		}
		sb.append(")");
		List<Map> all = DAOUtil.findAll(sb.toString(), null);
		if (all.size() > 0) {
			return all;
		} else {
			throw new ServiceBizException("只有未配车的车辆才允许移库操作");
		}
	}

	public Map findAllVIN2(String vin) {
		StringBuffer sb = new StringBuffer();
		sb.append(
				"SELECT A.DEALER_CODE AS DEALER_CODE,ab.STORAGE_NAME as OUT_STORAGE,tb.BRAND_NAME AS BRAND_NAME,ts.SERIES_NAME AS SERIES_NAME,tm.MODEL_NAME AS MODEL_NAME,tc.CONFIG_NAME AS CONFIG_NAME,tco.COLOR_NAME AS COLOR_NAME,0 AS IS_SELECTED,A.VIN,B.BRAND_CODE,B.SERIES_CODE,B.MODEL_CODE,B.CONFIG_CODE,");
		sb.append("B.COLOR_CODE,B.PRODUCT_TYPE, A.STOCK_STATUS,A.KEY_NUMBER,A.CERTIFICATE_NUMBER,");
		sb.append("A.HAS_CERTIFICATE,A.STORAGE_CODE,A.PRODUCT_CODE,A.STORAGE_POSITION_CODE,");
		sb.append("A.DIRECTIVE_PRICE,A.PURCHASE_PRICE,A.ADDITIONAL_COST,A.MAR_STATUS");
		sb.append(" FROM TM_VS_STOCK A LEFT JOIN (" + CommonConstants.VM_VS_PRODUCT);
		sb.append(") B ON A.DEALER_CODE=B.DEALER_CODE AND A.D_KEY=B.D_KEY AND A.PRODUCT_CODE=B.PRODUCT_CODE");
		sb.append(
				" LEFT JOIN tm_brand tb ON b.BRAND_CODE=tb.BRAND_CODE LEFT JOIN tm_series ts ON b.SERIES_CODE=ts.SERIES_CODE ");
		sb.append(
				" LEFT JOIN tm_model tm ON b.MODEL_CODE=tm.MODEL_CODE LEFT JOIN tm_configuration tc ON b.CONFIG_CODE=tc.CONFIG_CODE ");
		sb.append(" LEFT JOIN tm_color tco ON b.COLOR_CODE=tco.COLOR_CODE LEFT JOIN (" + CommonConstants.VM_STORAGE
				+ ") ab ON ab.STORAGE_CODE=A.STORAGE_CODE AND A.DEALER_CODE = ab.DEALER_CODE");
		sb.append(" WHERE A.DEALER_CODE='" + FrameworkUtil.getLoginInfo().getDealerCode() + "'  AND A.D_KEY="
				+ DictCodeConstants.D_KEY + " AND A.STOCK_STATUS='" + DictCodeConstants.DISPATCHED_STATUS_INSTOCK
				+ "' ");
		sb.append(" AND A.VIN='" + vin + "'");

		return DAOUtil.findFirst(sb.toString(), null);
	}

	@Override
	public void editItemByIn(Map<String, Object> map) {
		VsTransferItemPO po = VsTransferItemPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),
				map.get("ST_NO").toString(), map.get("VIN").toString());
		po.setString("IN_STORAGE", map.get("IN_STORAGE"));
		po.setString("IN_POSITION", map.get("IN_POSITION"));
		po.saveIt();
	}

	@Override
	public void addItemByIn(Map<String, Object> map) {
		addInfoToItem(map);
	}

	/**
	 * 根据ST_NO查询所有VIN TODO description
	 * 
	 * @author yangjie
	 * @date 2017年1月10日
	 * @return
	 */
	@Override
	public List<Map> findAllItem(String stNo) {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT DEALER_CODE,ST_NO,VIN FROM TT_VS_TRANSFER_ITEM");
		sb.append(" WHERE ST_NO='" + stNo + "'");

		return DAOUtil.findAll(sb.toString(), null);
	}

	/**
	 * 将map集合存入PO对象 --存入移库单 TODO Transfer_Item
	 * 
	 * @author yangjie
	 * @date 2017年1月8日
	 * @param po
	 * @param map
	 */
	public void MapToPoForEdit(VsTransferItemPO po, Map<String, Object> map) {
		po.setString("IN_STORAGE", map.get("inStorage"));
		po.setString("IN_POSITION", map.get("inPosition"));
		po.setString("TAKE_CAR_MAN", map.get("takeCarMan"));
		po.setString("TRANSACTOR", map.get("transactor"));
		po.setString("REMARK", map.get("remark"));
	}

	/**
	 * 移库单明细新增操作Map存入PO TODO Transfer_Item
	 * 
	 * @author yangjie
	 * @date 2017年1月8日
	 */
	public void MapToPoForAddTI(VsTransferItemPO po, Map<String, Object> map) {
		po.setString("ST_NO", map.get("stNo"));
		po.setString("DEALER_CODE", FrameworkUtil.getLoginInfo().getDealerCode());
		po.setString("VIN", map.get("vin"));
		po.setString("PRODUCT_CODE", map.get("productCode"));
		po.setDouble("PURCHASE_PRICE", map.get("purchasePrice"));
		po.setInteger("MAR_STATUS", map.get("marStatus"));
		po.setString("IN_STORAGE", map.get("inStorage"));
		po.setString("IN_POSITION", map.get("inPosition"));
		po.setString("TRANSACTOR", findUserName(map.get("transactor").toString()));
		po.setString("REMARK", map.get("remark"));
		po.setString("TAKE_CAR_MAN", map.get("takeCarMan"));
		po.setDouble("ADDITIONAL_COST", map.get("additionalCost"));
		po.setDouble("DIRECTIVE_PRICE", map.get("directivePrice"));
		po.setString("OUT_STORAGE", map.get("outStorage"));
		po.setString("OUT_POSITION", map.get("outPosition"));
	}

	/**
	 * 销售订单修改仓库信息Map存入PO方法 TODO description
	 * 
	 * @author yangjie
	 * @date 2017年1月9日
	 * @param po
	 * @param map
	 */
	public void MapToPoForSalesOrder(SalesOrderPO po, Map<String, Object> map) {

		String storageCode = map.get("storageCode").toString();

		if (map.get("storageCode") != null && map.get("storageCode") != "") {
			po.setString("STORAGE_CODE", storageCode);
		}
		po.setString("STORAGE_POSITION_CODE", map.get("storagePositionCode"));
	}

	/**
	 * 车辆库存表修改仓库信息Map存入PO方法 TODO description
	 * 
	 * @author yangjie
	 * @date 2017年1月9日
	 * @param po
	 * @param map
	 */
	public void MapToPoForVehicleStock(VsStockPO po, Map<String, Object> map) {
		po.setString("STORAGE_CODE", map.get("storageCode"));
		po.setString("STORAGE_POSITION_CODE", map.get("storagePositionCode"));
	}

	/**
	 * 日志表新增Map存入PO TODO description
	 * 
	 * @author yangjie
	 * @date 2017年1月9日
	 * @param po
	 * @param map
	 */
	public void MapToPoForVsStockLog(VsStockLogPO po, Map<String, Object> map) {
		po.setString("DEALER_CODE", FrameworkUtil.getLoginInfo().getDealerCode());
		po.setString("VIN", map.get("vin"));
		po.setString("PRODUCT_CODE", map.get("productCode"));
		po.setDate("OPERATE_DATE", new Date());
		po.setInteger("OPERATION_TYPE", CommonConstants.DICT_VEHICLE_STORAGE_TYPE_MOVE_STOCK);
		if (map.get("storageCode") != null && map.get("storageCode") != "") {
			po.setString("STORAGE_CODE", map.get("storageCode"));
		}
		po.setString("STORAGE_POSITION_CODE", map.get("storagePositionCode"));
		po.setDouble("PURCHASE_PRICE", map.get("purchasePrice"));
		po.setInteger("STOCK_STATUS", DictCodeConstants.DISPATCHED_STATUS_INSTOCK);
		po.setInteger("MAR_STATUS", map.get("marStatus"));
		po.setLong("OPERATED_BY", FrameworkUtil.getLoginInfo().getUserId());

		VsStockPO vs = VsStockPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(), map.get("vin"));
		if (vs != null) {
			po.setDouble("ADDITIONAL_COST", vs.getDouble("ADDITIONAL_COST"));
			po.setInteger("IS_SECONDHAND", vs.getInteger("IS_SECONDHAND"));
			po.setInteger("IS_VIP", vs.getInteger("IS_VIP"));
			po.setInteger("IS_TEST_DRIVE_CAR", vs.getInteger("IS_TEST_DRIVE_CAR"));
			po.setInteger("IS_CONSIGNED", vs.getInteger("IS_CONSIGNED"));
			po.setInteger("IS_PROMOTION", vs.getInteger("IS_PROMOTION"));
			po.setInteger("IS_PURCHASE_RETURN", vs.getInteger("IS_PURCHASE_RETURN"));
			po.setInteger("IS_PRICE_ADJUSTED", vs.getInteger("IS_PRICE_ADJUSTED"));
			po.setString("ADJUST_REASON", vs.getString("ADJUST_REASON"));
			po.setDouble("OLD_DIRECTIVE_PRICE", vs.getDouble("OLD_DIRECTIVE_PRICE"));
			po.setDouble("DIRECTIVE_PRICE", vs.getDouble("DIRECTIVE_PRICE"));
			po.setInteger("OEM_TAG", vs.getInteger("OEM_TAG"));
			po.setInteger("DISPATCHED_STATUS", vs.getInteger("DISPATCHED_STATUS"));
			po.setInteger("D_KEY", vs.getInteger("D_KEY"));
		}

		VsProductPO pro = VsProductPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),
				map.get("productCode"));
		if (pro != null) {
			po.setDouble("DIRECTIVE_PRICE", pro.getDouble("DIRECTIVE_PRICE"));
			po.setDouble("PURCHASE_PRICE", pro.getDouble("LATEST_PURCHASE_PRICE"));
		}
		//po.setInteger("OPERATION_TYPE", DictCodeConstants.VEHICLE_STORAGE_TYPE_MOVE_STOCK);

	}

	/**
	 * @author yangjie
	 * @date 2017年1月9日
	 * @param storageName
	 * @return (non-Javadoc)
	 * @see com.yonyou.dms.vehicle.service.stockManage.TransferService#findStorageCode(java.lang.String)
	 */

	@Override
	public Integer findStorageCode(String storageName) {
		StringBuffer sb = new StringBuffer();
		List<String> query = new ArrayList<String>();
		sb.append("select A.STORAGE_CODE as code from (" + CommonConstants.VM_STORAGE + ") A where DEALER_CODE="
				+ FrameworkUtil.getLoginInfo().getDealerCode());
		sb.append(" and A.STORAGE_NAME=?");
		query.add(storageName);
		Map map = DAOUtil.findFirst(sb.toString(), query);
		Integer code = Integer.parseInt(map.get("code").toString());
		return code;
	}

	/**
	 * SQL判断IN关键字 TODO description
	 * 
	 * @author yangjie
	 * @date 2017年1月8日
	 * @param sb
	 * @param ids
	 * @param field
	 * @param alias
	 */
	public void sqlToIn(StringBuffer sb, String ids, String field, String alias) {
		if (StringUtils.isNotBlank(ids)) {
			String[] items = ids.split(",");
			sb.append("AND ");
			if (StringUtils.isNotBlank(alias)) {
				sb.append(alias + "." + field);
			} else {
				sb.append(field);
			}
			sb.append(" IN (");
			for (int i = 0; i < items.length; i++) {
				if (StringUtils.isNotBlank(items[i])) {
					sb.append("'" + items[i] + "'");
					if (i != items.length - 1) {
						sb.append(",");
					}
				}
			}
		}
	}

	/**
	 * TODO 拼接sql语句模糊查询
	 * 
	 * @author yangjie
	 * @date 2016年12月28日
	 * @param param
	 *            查询条件
	 * @param field
	 *            查询的字段
	 * @param alias
	 *            表的别名
	 * @return
	 */
	public void sqlToLike(StringBuffer sb, String param, String field, String alias) {
		if (StringUtils.isNotBlank(param)) {
			sb.append("AND ");
			if (StringUtils.isNotBlank(alias)) {
				sb.append(alias + "." + field);
			} else {
				sb.append(field);
			}
			sb.append(" LIKE '%" + param + "%' ");
		}
	}

	/**
	 * TODO 拼接sql语句等量查询
	 * 
	 * @author yangjie
	 * @date 2016年12月28日
	 * @param param
	 *            查询条件
	 * @param field
	 *            查询的字段
	 * @param alias
	 *            表的别名
	 * @return
	 */
	public void sqlToEquals(StringBuffer sb, String param, String field, String alias) {
		if (StringUtils.isNotBlank(param)) {
			sb.append(" AND ");
			if (StringUtils.isNotBlank(alias)) {
				sb.append(alias + "." + field);
			} else {
				sb.append(field);
			}
			sb.append(" = '" + param + "' ");
		}
	}

	/**
	 * TODO 拼接sql语句时间查询(单个字段)
	 * 
	 * @author yangjie
	 * @date 2016年12月28日
	 * @param begin
	 *            开始时间
	 * @param end
	 *            结束时间
	 * @param field
	 *            查询的字段
	 * @param alias
	 *            表的别名
	 * @return
	 */
	public void sqlToDate(StringBuffer sb, String begin, String end, String field, String alias) {
		if (StringUtils.isNotBlank(begin) && StringUtils.isNotBlank(end)) {
			sb.append(" AND ");
			if (StringUtils.isNotBlank(alias)) {
				sb.append(alias + "." + field);
			} else {
				sb.append(field);
			}
			sb.append(" between '" + begin + "' AND '" + end + "' ");
		} else if (StringUtils.isNotBlank(begin) && !StringUtils.isNotBlank(end)) {
			sb.append("AND ");
			if (StringUtils.isNotBlank(alias)) {
				sb.append(alias + "." + field);
			} else {
				sb.append(field);
			}
			sb.append(" >= '" + begin + "' ");
		} else if (!StringUtils.isNotBlank(begin) && StringUtils.isNotBlank(end)) {
			sb.append("AND ");
			if (StringUtils.isNotBlank(alias)) {
				sb.append(alias + "." + field);
			} else {
				sb.append(field);
			}
			sb.append(" <= '" + end + "' ");
		}
	}

	/**
	 * 移位
	 * 
	 * @author yangjie
	 * @date 2017年1月12日
	 * @param vins
	 * @param location
	 *            (non-Javadoc)
	 * @see com.yonyou.dms.vehicle.service.stockManage.TransferService#editLocation(java.lang.String[],
	 *      java.lang.String)
	 */
	@Override
	public void editLocation(String[] vins, String location) {
		List<Map> setList = new ArrayList<Map>();
		Map me;// 用于保存vin和修改前库位信息
		for (int i = 0; i < vins.length; i++) {

			// 销售订单信息修改
			SalesOrderPO so = SalesOrderPO.findFirst("VIN=?", vins[i]);
			if (so != null) {
				so.setString("STORAGE_POSITION_CODE", location);
				so.saveIt();
			}

			// 车辆库存表信息修改
			VsStockPO vs = VsStockPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(), vins[i]);
			me = new HashMap();
			me.put("VIN", vins[i]);
			me.put("OLD_STORAGE_POSITION_CODE", vs.getString("STORAGE_POSITION_CODE"));
			setList.add(me);

			if (vs != null) {
				vs.setString("STORAGE_POSITION_CODE", location);
				vs.saveIt();
			}

			Map param = new HashMap();
			param.put("storagePositionCode", location);
			param.put("vin", vins[i]);
			Map map2 = findAllVIN2(vins[i]);
			if (map2.get("PRODUCT_CODE") != null && map2.get("PRODUCT_CODE") != "") {
				param.put("productCode", map2.get("PRODUCT_CODE").toString());
			}
			if (map2.get("PURCHASE_PRICE") != null && map2.get("PURCHASE_PRICE") != "") {
				param.put("purchasePrice", map2.get("PURCHASE_PRICE").toString());
			}
			if (map2.get("MAR_STATUS") != null && map2.get("MAR_STATUS") != "") {
				param.put("marStatus", map2.get("MAR_STATUS").toString());
			}
			param.put("OPERATION_TYPE", DictCodeConstants.DICT_VEHICLE_STORAGE_TYPE_MOVE_POSITION);
			
			// 新增日志信息
			VsStockLogPO log = new VsStockLogPO();
			MapToPoForVsStockLog(log, param);
			log.saveIt();
		}
		findOldLocationList = setList;
	}

	/**
	 * 更新主表信息
	 */
	@Override
	public void refreshMainInfo(String stNo) {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		// 1,先查询子表是否都已经入账
		List<VsTransferItemPO> find = VsTransferItemPO.find("ST_NO = ?", stNo);
		Boolean flag = true;
		for (VsTransferItemPO vsTransferItemPO : find) {
			if (vsTransferItemPO.getLong("IS_FINISHED") == DictCodeConstants.IS_NOT) {
				flag = false;
			}
		}
		if (flag) {
			TtVsStockTransferPO tvst = TtVsStockTransferPO.findByCompositeKeys(dealerCode, stNo);
			tvst.setLong("IS_ALL_FINISHED", DictCodeConstants.IS_YES);
			tvst.saveIt();
		}
	}

	/**
	 * 打印前显示集合信息
	 */
	@Override
	public Map printFirstShow(String sNo) {
		String sql = "SELECT ST_NO,UPDATED_AT FROM TT_VS_STOCK_TRANSFER WHERE ST_NO = '" + sNo + "'";
		return Base.findAll(sql).get(0);
	}

	/**
	 * 移位打印显示
	 */
	@Override
	public List<Map> findAllPrintLocation(String[] vins) {
		String vin = "";
		for (int i = 0; i < vins.length; i++) {
			if (i == vins.length - 1) {
				vin += "'" + vins[i] + "'";
			} else {
				vin += "'" + vins[i] + "',";
			}
		}
		StringBuffer sb = new StringBuffer();
		sb.append(
				"SELECT A.DEALER_CODE AS DEALER_CODE,ab.STORAGE_NAME as OUT_STORAGE,tb.BRAND_NAME AS BRAND_NAME,ts.SERIES_NAME AS SERIES_NAME,tm.MODEL_NAME AS MODEL_NAME,tc.CONFIG_NAME AS CONFIG_NAME,tco.COLOR_NAME AS COLOR_NAME,0 AS IS_SELECTED,A.VIN,B.BRAND_CODE,B.SERIES_CODE,B.MODEL_CODE,B.CONFIG_CODE,");
		sb.append("B.COLOR_CODE,B.PRODUCT_TYPE, A.STOCK_STATUS,A.KEY_NUMBER,A.CERTIFICATE_NUMBER,");
		sb.append("A.HAS_CERTIFICATE,A.STORAGE_CODE,A.PRODUCT_CODE,B.PRODUCT_NAME,A.STORAGE_POSITION_CODE,");
		sb.append(
				"A.DIRECTIVE_PRICE,A.PURCHASE_PRICE,A.ADDITIONAL_COST,A.MAR_STATUS,'' AS OLD_STORAGE_POSITION_CODE,A.UPDATED_AT");
		sb.append(" FROM TM_VS_STOCK A LEFT JOIN (" + CommonConstants.VM_VS_PRODUCT);
		sb.append(") B ON A.DEALER_CODE=B.DEALER_CODE AND A.D_KEY=B.D_KEY AND A.PRODUCT_CODE=B.PRODUCT_CODE");
		sb.append(
				" LEFT JOIN tm_brand tb ON b.BRAND_CODE=tb.BRAND_CODE LEFT JOIN tm_series ts ON b.SERIES_CODE=ts.SERIES_CODE ");
		sb.append(
				" LEFT JOIN tm_model tm ON b.MODEL_CODE=tm.MODEL_CODE LEFT JOIN tm_configuration tc ON b.CONFIG_CODE=tc.CONFIG_CODE ");
		sb.append(" LEFT JOIN tm_color tco ON b.COLOR_CODE=tco.COLOR_CODE LEFT JOIN (" + CommonConstants.VM_STORAGE
				+ ") ab ON ab.STORAGE_CODE=A.STORAGE_CODE AND A.DEALER_CODE = ab.DEALER_CODE");
		sb.append(" WHERE A.DEALER_CODE='" + FrameworkUtil.getLoginInfo().getDealerCode() + "'  AND A.D_KEY="
				+ DictCodeConstants.D_KEY + " AND A.STOCK_STATUS='" + DictCodeConstants.DISPATCHED_STATUS_INSTOCK
				+ "' ");
		sb.append(" AND A.VIN IN (" + vin + ")");
		List<Map> findAll = DAOUtil.findAll(sb.toString(), null);
		for (Map map : findAll) {
			for (Map map1 : findOldLocationList) {
				if (map.get("VIN").toString().equals(map1.get("VIN").toString())) {
					map.put("OLD_STORAGE_POSITION_CODE", map1.get("OLD_STORAGE_POSITION_CODE").toString());
				}
			}
			if (map.get("MAR_STATUS") != null) {// 质损
				if (Integer.parseInt(map.get("MAR_STATUS").toString()) == DictCodeConstants.MAR_STATUS_YES) {// 质损
					map.put("MAR_STATUS", "质损");
				} else {// 正常
					map.put("MAR_STATUS", "正常");
				}
			} else {
				map.put("MAR_STATUS", "");
			}
			if (map.get("COLOR_NAME") == null) {
				map.put("COLOR_NAME", "");
			}
		}
		return findAll;
	}

	@Override
	public PageInfoDto findAllVehicleInfoForLocal(Map<String, String> queryParam) {
		StringBuffer sb = new StringBuffer();
		sb.append(
				"SELECT  0 AS IS_SELECTED,B.VIN,tms.STORAGE_NAME as OUT_STORAGE,B.STORAGE_POSITION_CODE,B.STOCK_STATUS,");
		sb.append(
				"C.BRAND_CODE,tb.BRAND_NAME as BRAND_NAME,C.SERIES_CODE,ts.SERIES_NAME as SERIES_NAME,C.MODEL_CODE,tm.MODEL_NAME as MODEL_NAME,C.CONFIG_CODE,tc.CONFIG_NAME as CONFIG_NAME,C.COLOR_CODE,tcc.COLOR_NAME as COLOR_NAME ,B.DEALER_CODE ");
		sb.append("FROM TM_VS_STOCK B ");
		sb.append("LEFT JOIN (" + CommonConstants.VM_VS_PRODUCT
				+ ") C ON B.D_KEY=C.D_KEY AND B.DEALER_CODE=C.DEALER_CODE AND B.PRODUCT_CODE=C.PRODUCT_CODE ");
		sb.append(
				" LEFT JOIN tm_brand tb  ON c.BRAND_CODE = tb.BRAND_CODE AND c.DEALER_CODE = tb.DEALER_CODE LEFT JOIN tm_series ts ON c.SERIES_CODE = ts.SERIES_CODE AND c.BRAND_CODE = ts.BRAND_CODE AND c.DEALER_CODE = ts.DEALER_CODE LEFT JOIN tm_model tm ON c.MODEL_CODE = tm.MODEL_CODE AND c.SERIES_CODE = tm.SERIES_CODE AND c.BRAND_CODE = tm.BRAND_CODE AND c.DEALER_CODE = tm.DEALER_CODE LEFT JOIN tm_configuration tc ON c.CONFIG_CODE = tc.CONFIG_CODE  AND c.MODEL_CODE = tc.MODEL_CODE AND c.SERIES_CODE = tc.SERIES_CODE AND c.BRAND_CODE = tc.BRAND_CODE AND c.DEALER_CODE = tc.DEALER_CODE LEFT JOIN tm_color tcc ON c.COLOR_CODE = tcc.COLOR_CODE AND c.DEALER_CODE = tcc.DEALER_CODE ");
		sb.append(
				" LEFT JOIN tm_storage tms ON B.STORAGE_CODE = tms.STORAGE_CODE AND b.DEALER_CODE = tms.DEALER_CODE ");
		sb.append("WHERE B.STOCK_STATUS=" + DictCodeConstants.DICT_STORAGE_STATUS_IN_STORAGE);
		sb.append(" AND B.DEALER_CODE='" + FrameworkUtil.getLoginInfo().getDealerCode() + "' AND B.D_KEY="
				+ CommonConstants.D_KEY + " ");
		sb.append(" AND B.STOCK_STATUS='" + DictCodeConstants.DISPATCHED_STATUS_INSTOCK + "' ");
		sqlToEquals(sb, queryParam.get("storageCode"), "STORAGE_CODE", "B");
		sqlToLike(sb, queryParam.get("outPosition"), "STORAGE_POSITION_CODE", "B");
		sqlToLike(sb, queryParam.get("vin"), "VIN", "B");
		return DAOUtil.pageQuery(sb.toString(), null);
	}
	
	public String findUserName(String uid){
		String sql = "SELECT dealer_code,user_name FROM tm_user where user_id = ?";
		List queryParam = new ArrayList();
		queryParam.add(uid);
		Map map = DAOUtil.findFirst(sql, queryParam);
		if(map.size()>0){
			return map.get("user_name").toString();
		}else{
			return null;
		}
	}

	/**
	 * 修改主表是否入账
	 */
	@Override
	public void editList(String stNo) {
		TtVsStockTransferPO po = TtVsStockTransferPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),stNo);
		po.setLong("IS_ALL_FINISHED", DictCodeConstants.IS_NOT);
		po.saveIt();
	}
}
