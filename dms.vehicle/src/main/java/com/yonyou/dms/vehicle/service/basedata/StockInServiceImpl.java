
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.vehicle
 *
 * @File name : StockInServiceImpl.java
 *
 * @Author : yll
 *
 * @Date : 2016年9月18日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年9月18日    yll    1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.vehicle.service.basedata;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.CustomerPO;
import com.yonyou.dms.common.domains.PO.basedata.PotentialCusPO;
import com.yonyou.dms.common.domains.PO.basedata.TmMonitorCarMaintePO;
import com.yonyou.dms.common.domains.PO.basedata.TmVehicleStockOrderPO;
import com.yonyou.dms.common.domains.PO.basedata.TmVehicleSubclassPO;
import com.yonyou.dms.common.domains.PO.basedata.TtAccountsTransFlowPO;
import com.yonyou.dms.common.domains.PO.basedata.TtOrderStatusUpdatePO;
import com.yonyou.dms.common.domains.PO.basedata.TtPoCusRelationPO;
import com.yonyou.dms.common.domains.PO.basedata.TtSalesCrPO;
import com.yonyou.dms.common.domains.PO.basedata.VehiclePdiResultPO;
import com.yonyou.dms.common.domains.PO.basedata.VsProductPO;
import com.yonyou.dms.common.domains.PO.basedata.VsStockLogPO;
import com.yonyou.dms.common.domains.PO.basedata.VsStockPO;
import com.yonyou.dms.common.domains.PO.stockmanage.CarownerPO;
import com.yonyou.dms.common.domains.PO.stockmanage.TtPaymentInfoPO;
import com.yonyou.dms.common.domains.PO.stockmanage.TtVsShippingNotifyPO;
import com.yonyou.dms.common.domains.PO.stockmanage.TtVsStockEntryPO;
import com.yonyou.dms.common.domains.PO.stockmanage.VehiclePO;
import com.yonyou.dms.common.domains.PO.stockmanage.VsInspectionMarPO;
import com.yonyou.dms.common.domains.PO.stockmanage.VsStockEntryItemPO;
import com.yonyou.dms.commonSales.domains.PO.stockmanage.SalesOrderPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.CommonNoService;
import com.yonyou.dms.framework.service.FileStoreService;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.gacfca.SADMS002;
import com.yonyou.dms.vehicle.domains.DTO.basedata.InspectionAboutDTO;
import com.yonyou.dms.vehicle.domains.DTO.basedata.InspectionMarDTO;
import com.yonyou.dms.vehicle.domains.DTO.stockManage.StockInDTO;
import com.yonyou.dms.vehicle.domains.DTO.stockManage.StockInImportDTO;
import com.yonyou.dms.vehicle.domains.DTO.stockManage.StockInItemsDTO;

/**
 * 整车入库service实现类 TODO description
 * 
 * @author yangjie
 * @date 2017年1月17日
 */
@Service
@SuppressWarnings({ "rawtypes", "null", "unchecked", "unused" })
public class StockInServiceImpl implements StockInService {

	private static final Logger logger = LoggerFactory.getLogger(StockInServiceImpl.class);
    @Autowired
    SADMS002 SADMS002;
	@Autowired
	private CommonNoService commonNoService;
	@Autowired
	private FileStoreService fileStoreService;

	/**
	 * @author yangjie
	 * @date 2017年1月20日
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 *             (non-Javadoc)
	 * @see com.yonyou.dms.vehicle.service.basedata.StockInService#QueryStockIn(java.util.Map)
	 */

	@Override
	public PageInfoDto QueryStockIn(Map<String, String> queryParam) throws ServiceBizException {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam, params);
		return DAOUtil.pageQuery(sql, params);

	}

	/**
	 * 整车入库的信息导出方法
	 * 
	 * @author yll
	 * @date 2016年9月20日
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 *             (non-Javadoc)
	 * @see com.yonyou.dms.vehicle.service.basedata.StockInService#queryStockInForExport(java.util.Map)
	 */
	@Override
	public List<Map> queryStockInForExport(Map<String, String> queryParam) throws ServiceBizException {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam, params);
		List<Map> resultList = DAOUtil.findAll(sql, params);
		return resultList;
	}

	/**
	 * 车辆入库-入库单
	 * 
	 * @author yll
	 * @date 2016年9月20日
	 * @param queryParam
	 * @param params
	 * @return
	 */
	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		StringBuffer sqlSb = new StringBuffer(
				"SELECT DISTINCT A.DEALER_CODE,A.SE_NO,A.STOCK_IN_TYPE,A.SHEET_CREATE_DATE,tu.USER_NAME as SHEET_CREATED_BY,A.REMARK,A.IS_ALL_FINISHED ");
		sqlSb.append(
				" FROM TT_VS_STOCK_ENTRY A LEFT JOIN TT_VS_STOCK_ENTRY_ITEM B ON A.DEALER_CODE=B.DEALER_CODE AND A.D_KEY=B.D_KEY AND A.SE_NO=B.SE_NO ");
		sqlSb.append(
				"LEFT JOIN TM_USER tu ON A.SHEET_CREATED_BY = tu.USER_ID AND A.DEALER_CODE = tu.DEALER_CODE LEFT JOIN TM_DEALER_BASICINFO C ON B.SEND_ENTITY_CODE=C.DEALER_CODE ");
		sqlSb.append(" WHERE A.DEALER_CODE='" + FrameworkUtil.getLoginInfo().getDealerCode() + "' AND A.D_KEY="
				+ CommonConstants.D_KEY + " ");
		if (!StringUtils.isNullOrEmpty(queryParam.get("seNO"))) {
			sqlSb.append(" AND A.SE_NO like ? ");
			params.add("%" + queryParam.get("seNO") + "%");
		}
		System.err.println(queryParam.get("begin"));
		System.err.println(queryParam.get("end"));
		Utility.sqlToDate(sqlSb, queryParam.get("begin"), queryParam.get("end"), "SHEET_CREATE_DATE", "A");
		if (!StringUtils.isNullOrEmpty(queryParam.get("finished"))) {
			sqlSb.append(" AND A.IS_ALL_FINISHED = ?  ");
			params.add(queryParam.get("finished"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("inType"))) {
			sqlSb.append(" AND A.STOCK_IN_TYPE = ?  ");
			params.add(queryParam.get("inType"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
			sqlSb.append(" AND B.vin like ?  ");
			params.add("%" + queryParam.get("vin") + "%");
		}
		return sqlSb.toString();
	}

	/**
	 * @author yangjie
	 * @date 2017年1月20日
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 *             (non-Javadoc)
	 * @see com.yonyou.dms.vehicle.service.basedata.StockInService#queryStockInDetails(java.util.Map)
	 */

	@Override
	public List<Map> queryStockInDetails(Map<String, String> queryParam) throws ServiceBizException {
		StringBuffer sb = new StringBuffer("SELECT '" + queryParam.get("sNo") + "' AS sNo,'" + queryParam.get("inType")
				+ "' AS inType,'" + queryParam.get("createdBy") + "' AS createdBy,A.DEALER_CODE,"
				+ DictCodeConstants.IS_NOT
				+ " AS IS_SELECTED,A.VS_PURCHASE_DATE,A.IS_DIRECT,A.SE_NO,A.VIN,A.PRODUCT_CODE,A.STORAGE_CODE AS A_S_C,C.STORAGE_NAME AS STORAGE_CODE,A.STORAGE_POSITION_CODE,A.ENGINE_NO,A.KEY_NUMBER,");
		sb.append(
				" A.HAS_CERTIFICATE,A.CERTIFICATE_NUMBER,A.MANUFACTURE_DATE, A.FACTORY_DATE,A.PO_NO,A.VENDOR_CODE,A.VENDOR_NAME,A.PURCHASE_PRICE,A.ADDITIONAL_COST,A.CONSIGNER_CODE,A.CONSIGNER_NAME,A.SHIPPING_DATE,");
		sb.append(
				"A.SHIPPER_LICENSE,A.SHIPPER_NAME, A.DELIVERYMAN_NAME,A.DELIVERYMAN_PHONE,A.ARRIVING_DATE,A.SHIPPING_ORDER_NO,");
		sb.append(
				"A.SHIPPING_ADDRESS,A.ARRIVED_DATE,A.INSPECTION_RESULT,tu.USER_NAME as INSPECTOR,A.INSPECTION_DATE,A.MAR_STATUS, A.PRODUCTING_AREA,");
		sb.append(
				"A.OEM_TAG,A.IS_UPLOAD,A.SUBMIT_TIME,A.REMARK,A.IS_FINISHED,A.FINISHED_DATE,A.FINISHED_BY,A.INSPECTION_CONSIGNED,");
		sb.append(
				"A.EXHAUST_QUANTITY,A.VSN,A.DISCHARGE_STANDARD,a.FINANCIAL_BILL_NO,a.SUPERVISE_TYPE,a.MODEL_YEAR,COALESCE(A.COLOR_CODE,B.COLOR_CODE) AS COLOR_CODE,COALESCE(D.COLOR_NAME,E.COLOR_NAME) AS COLOR_NAME  ");
		sb.append(
				" FROM TT_VS_STOCK_ENTRY_ITEM A LEFT JOIN TM_USER tu ON A.INSPECTOR = tu.USER_ID AND A.DEALER_CODE = tu.DEALER_CODE LEFT JOIN ("
						+ CommonConstants.VM_VS_PRODUCT);
		sb.append(
				") B ON A.DEALER_CODE=B.DEALER_CODE AND A.D_KEY=B.D_KEY AND A.PRODUCT_CODE=B.PRODUCT_CODE LEFT JOIN TM_STORAGE C ON A.STORAGE_CODE=C.STORAGE_CODE AND  A.DEALER_CODE = C.DEALER_CODE LEFT JOIN TM_COLOR D ON A.COLOR_CODE=D.COLOR_CODE AND  A.DEALER_CODE =D.DEALER_CODE  LEFT JOIN TM_COLOR E ON B.COLOR_CODE=E.COLOR_CODE AND  A.DEALER_CODE = E.DEALER_CODE ");
		sb.append(" WHERE A.DEALER_CODE='" + FrameworkUtil.getLoginInfo().getDealerCode());
		sb.append("' AND A.D_KEY=" + CommonConstants.D_KEY);
		if (!StringUtils.isNullOrEmpty(queryParam.get("sNo"))) {
			sb.append(" AND A.SE_NO = '" + queryParam.get("sNo") + "' ");
		}
		Utility.sqlToEquals(sb, queryParam.get("vin"), "VIN", "A");
		List<Map> info = DAOUtil.findAll(sb.toString(), null);
		return info;
	}

	/**
	 * @author yangjie
	 * @date 2017年1月20日
	 * @param query
	 * @param flag
	 * @return (non-Javadoc)
	 * @see com.yonyou.dms.vehicle.service.basedata.StockInService#findAllVehicle(java.util.Map,
	 *      java.lang.Boolean)
	 */

	@Override
	public PageInfoDto findAllVehicle(Map<String, String> query, Boolean flag) {
		StringBuffer sb = new StringBuffer();
		if (flag) {
			System.err.println(1);
			sb = queryByBuyNew(query);
		} else {
			System.err.println(2);
			sb = queryByReturn(query);
		}
		List<Object> queryParam = new ArrayList<Object>();

		PageInfoDto pageQuery = DAOUtil.pageQuery(sb.toString(), queryParam);
		return pageQuery;
	}

	/**
	 * 采购入库 TODO description
	 * 
	 * @author yangjie
	 * @date 2017年1月17日
	 * @param query
	 * @return
	 */
	public StringBuffer queryByBuyNew(Map<String, String> query) {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT B.DEALER_CODE," + DictCodeConstants.IS_NOT + " AS IS_SELECTED," + DictCodeConstants.IS_YES
				+ " AS IS_SELECT,A.IS_DIRECT,DATE(A.VS_PURCHASE_DATE) AS VS_PURCHASE_DATE,A.VIN,A.PRODUCT_CODE,A.ENGINE_NO,A.KEY_NUMBER,A.HAS_CERTIFICATE,A.CERTIFICATE_NUMBER,DATE(A.MANUFACTURE_DATE) MANUFACTURE_DATE,DATE(A.FACTORY_DATE) FACTORY_DATE,A.PO_NO,");
		sb.append(
				"  A.VENDOR_CODE,A.VENDOR_NAME,A.PURCHASE_PRICE,A.ADDITIONAL_COST,A.IS_ALLOCATED,A.IS_CONSIGNED,A.CONSIGNER_CODE,A.CONSIGNER_NAME,A.IS_CONSIGNING,A.CONSIGNEE_CODE,A.CONSIGNEE_NAME,DATE(A.SHIPPING_DATE) AS SHIPPING_DATE,A.SHIPPER_NAME,A.SHIPPER_LICENSE,A.EC_ORDER_NO,A.DELIVERYMAN_NAME,A.DELIVERYMAN_PHONE,DATE(A.ARRIVING_DATE) AS ARRIVING_DATE,A.SHIPPING_ORDER_NO,A.SHIPPING_ADDRESS,a.SUPERVISE_TYPE,a.FINANCIAL_BILL_NO,A.MODEL_YEAR,A.REMARK,A.OEM_TAG,B.COLOR_CODE ");
		sb.append(
				" FROM TT_VS_SHIPPING_NOTIFY A LEFT JOIN TM_VS_PRODUCT B ON B.PRODUCT_CODE = A.PRODUCT_CODE AND A.DEALER_CODE = B.DEALER_CODE WHERE A.VIN NOT IN (SELECT VIN FROM TM_VS_STOCK ");
		sb.append(" WHERE DEALER_CODE = '" + FrameworkUtil.getLoginInfo().getDealerCode() + "'  AND D_KEY = "
				+ CommonConstants.D_KEY
				+ ") AND A.VIN NOT IN (SELECT VIN FROM TT_VS_STOCK_ENTRY_ITEM WHERE IS_FINISHED = "
				+ DictCodeConstants.IS_NOT + " AND DEALER_CODE = '" + FrameworkUtil.getLoginInfo().getDealerCode()
				+ "' AND D_KEY = " + CommonConstants.D_KEY + ") ");
		sb.append("  AND A.DEALER_CODE = '" + FrameworkUtil.getLoginInfo().getDealerCode() + "' AND A.D_KEY = "
				+ CommonConstants.D_KEY + " AND A.IS_ALLOCATED = " + DictCodeConstants.IS_NOT + " AND A.IS_CONSIGNED = "
				+ DictCodeConstants.IS_NOT + " ");
		Utility.sqlToLike(sb, query.get("vin"), "VIN", "A");
		Utility.sqlToLike(sb, query.get("productCode"), "PRODUCT_CODE", "A");
		return sb;
	}

	/**
	 * 销售退回 TODO description
	 * 
	 * @author yangjie
	 * @date 2017年1月17日
	 * @param query
	 * @return
	 */
	public StringBuffer queryByReturn(Map<String, String> query) {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT B.DEALER_CODE," + DictCodeConstants.IS_NOT + " AS IS_SELECTED," + DictCodeConstants.IS_YES
				+ " AS IS_SELECT,A.EXHAUST_QUANTITY,A.DISCHARGE_STANDARD,A.MODEL_YEAR,A.IS_DIRECT,A.VS_PURCHASE_DATE,B.VIN,B.PRODUCT_CODE,B.STORAGE_CODE,B.STORAGE_POSITION_CODE,A.ENGINE_NO,A.KEY_NUMBER,A.HAS_CERTIFICATE,A.CERTIFICATE_NUMBER,A.MANUFACTURE_DATE,DATE(A.FACTORY_DATE) FACTORY_DATE,A.PO_NO,A.VENDOR_CODE,A.VENDOR_NAME,A.PURCHASE_PRICE,A.ADDITIONAL_COST,A.CONSIGNER_CODE,A.CONSIGNER_NAME,A.STOCK_IN_TYPE,A.SE_NO,A.STOCK_OUT_TYPE,A.SD_NO,A.STOCK_STATUS,A.DISPATCHED_STATUS,A.SO_NO,A.MAR_STATUS,A.IS_SECONDHAND,A.IS_VIP,A.IS_TEST_DRIVE_CAR,A.IS_CONSIGNED,A.IS_PROMOTION,A.IS_PURCHASE_RETURN,A.IS_PRICE_ADJUSTED,A.ADJUST_REASON,A.OLD_DIRECTIVE_PRICE,A.DIRECTIVE_PRICE,A.FIRST_STOCK_IN_DATE,A.LATEST_STOCK_IN_DATE,A.EC_ORDER_NO,A.LAST_STOCK_IN_BY,A.FIRST_STOCK_OUT_DATE,A.LATEST_STOCK_OUT_DATE,A.LAST_STOCK_OUT_BY,A.OEM_TAG,A.REMARK,a.FINANCIAL_BILL_NO,a.SUPERVISE_TYPE,COALESCE(A.COLOR_CODE, C.COLOR_CODE) AS COLOR_CODE ");
		sb.append(
				" FROM TM_VS_STOCK A LEFT JOIN TT_SALES_ORDER B ON B.DEALER_CODE = A.DEALER_CODE AND B.D_KEY = A.D_KEY AND B.VIN = A.VIN LEFT JOIN TM_VS_PRODUCT C ON C.PRODUCT_CODE = A.PRODUCT_CODE AND A.DEALER_CODE = C.DEALER_CODE ");
		sb.append(" WHERE B.DEALER_CODE = A.DEALER_CODE AND B.D_KEY = A.D_KEY AND B.VIN = A.VIN AND B.SO_STATUS = "
				+ DictCodeConstants.DICT_SO_STATUS_WAIT_UNTREAT_IN_STOCK + " AND B.BUSINESS_TYPE = "
				+ DictCodeConstants.DICT_SO_TYPE_RERURN + " AND B.DEALER_CODE = '"
				+ FrameworkUtil.getLoginInfo().getDealerCode() + "' AND B.D_KEY = 0 AND A.STOCK_STATUS = "
				+ DictCodeConstants.DICT_STORAGE_STATUS_MOVE_OUT + " ");
		Utility.sqlToLike(sb, query.get("vin"), "VIN", "A");
		Utility.sqlToLike(sb, query.get("productCode"), "PRODUCT_CODE", "b");
		return sb;
	}

	/**
	 * @author yangjie
	 * @date 2017年1月20日
	 * @param param
	 * @throws ServiceBizException
	 *             (non-Javadoc)
	 * @see com.yonyou.dms.vehicle.service.basedata.StockInService#btnSave(com.yonyou.dms.vehicle.domains.DTO.stockManage.StockInDTO)
	 */

	@Override
	public StockInDTO btnSave(StockInDTO param) throws ServiceBizException {
		if (checkBtnSave(param)) {// 验证通过
			if (StringUtils.isNullOrEmpty(param.getSeNo())) {
				String no = commonNoService.getSystemOrderNo(CommonConstants.SD_NO);// 生成主键
				/* 入库单新增操作 */
				TtVsStockEntryPO se = new TtVsStockEntryPO();
				se.setString("DEALER_CODE", FrameworkUtil.getLoginInfo().getDealerCode());
				se.setString("SE_NO", no);
				se.setInteger("STOCK_IN_TYPE", param.getInType());
				se.setDate("SHEET_CREATE_DATE", new Date());
				se.setString("SHEET_CREATED_BY", FrameworkUtil.getLoginInfo().getUserId());
				se.setString("REMARK", param.getRemark());
				se.setInteger("D_KEY", CommonConstants.D_KEY);
				se.setInteger("IS_ALL_FINISHED", DictCodeConstants.IS_NOT);
				se.setString("OWNED_BY", FrameworkUtil.getLoginInfo().getUserId());
				se.saveIt();// 新增入库单
				param.setSeNo(no);// 将生成的SE_NO保存到DTO中，用于入库单明细新增操作
			} else {
				/* 入库单修改操作 */
				TtVsStockEntryPO se = TtVsStockEntryPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),
						param.getSeNo());
				se.setString("OWNED_BY", FrameworkUtil.getLoginInfo().getUserId());
				se.setInteger("STOCK_IN_TYPE", param.getInType());
				se.setString("SHEET_CREATED_BY", FrameworkUtil.getLoginInfo().getUserId());
				se.setDate("SHEET_CREATE_DATE", new Date());
				List<StockInItemsDTO> list = param.getDms_table();
				Boolean flag = true;
				for (StockInItemsDTO map : list) {
					if(!StringUtils.isNullOrEmpty(map.getIs_finished())&&map.getIs_finished()==DictCodeConstants.IS_YES){
					}else{
						flag = false;
					}
				}
				if(flag){//表示全部入账
					se.setInteger("IS_ALL_FINISHED", DictCodeConstants.IS_YES);
				}else{
					se.setInteger("IS_ALL_FINISHED", DictCodeConstants.IS_NOT);
				}
				se.setString("REMARK", param.getRemark());
				se.saveIt();// 修改入库单
			}

			/* 入库单明细操作 */
			itemsFunction(param, param.getInType());
			return param;
		} else {
			return null;
		}
	}

	/**
	 * 入库明细单新增 /修改TODO description
	 * 
	 * @author yangjie
	 * @date 2017年1月19日
	 * @param param
	 * @throws ServiceBizException
	 */
	public void itemsFunction(StockInDTO dto, Integer inType) throws ServiceBizException {
		List<String> vins = findNotInListVIN(dto);// 不在数据库中的VIN值做新增操作
		List<String> vinss = findNotInDataBaseVIN(dto);// 不在前台中的VIN值做删除操作
		for (StockInItemsDTO sii : dto.getDms_table()) {
			List<Map> code = this.findStorageCode();
			for (Map map : code) {// 将前台传过来的仓库名称转化为仓库代码
				if (map.get("STORAGE_NAME").toString().trim().equals(sii.getStorage_code().trim())) {
					sii.setStorage_code(map.get("STORAGE_CODE").toString());
				}
			}
			if (!vinss.contains(sii.getVin())) {// 做删除操作的数据
				//deleteItems(sii);
				if (vins.contains(sii.getVin())) {// 属于新增的数据。做新增操作
					sii.setSe_no(dto.getSeNo());
					addItems(sii, true, inType);
				} else {// 属于修改的数据，做修改操作
					addItems(sii, false, inType);
				}
			}
		}
		if(vinss.size()>0){
			for (String str : vinss) {
				VsStockEntryItemPO po = new VsStockEntryItemPO();
				po.setString("SE_NO", dto.getSeNo());
				po.setString("DEALER_CODE", FrameworkUtil.getLoginInfo().getDealerCode());
				po.setString("VIN", str);
				po.delete();
			}
			String sql = "SELECT * FROM TT_VS_STOCK_ENTRY_ITEM WHERE SE_NO = '" + dto.getSeNo() + "'";
			List<Map> all = DAOUtil.findAll(sql, null);
			if (all.size() == 0) {// 如果子表没数据，删除主表
				TtVsStockEntryPO vse = TtVsStockEntryPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),
						dto.getSeNo());
				vse.delete();
			}
		}
	}

	public void deleteItems(StockInItemsDTO dto) throws ServiceBizException {
		// 删除验收质损明细
		VsInspectionMarPO.delete("D_KEY = ? AND SE_NO = ? AND VIN = ?", CommonConstants.D_KEY, dto.getSe_no(),
				dto.getVin());
		VsStockEntryItemPO po = VsStockEntryItemPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),
				dto.getSe_no(), dto.getVin());
		po.delete();
		String sql = "SELECT * FROM TT_VS_STOCK_ENTRY_ITEM WHERE SE_NO = " + dto.getSe_no();
		List<Map> all = DAOUtil.findAll(sql, null);
		if (all.size() == 0) {// 如果子表没数据，删除主表
			TtVsStockEntryPO vse = TtVsStockEntryPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),
					dto.getSe_no());
			vse.delete();
		}
	}

	/**
	 * 新增/修改入库单方法 TODO description
	 * 
	 * @author yangjie
	 * @date 2017年1月19日
	 * @param dto
	 * @param flag
	 *            true 新增 false 修改
	 */
	public void addItems(StockInItemsDTO dto, Boolean flag, Integer inType) throws ServiceBizException {
		if (flag) {// 新增方法
			String vin = dto.getVin();
			List<VsStockPO> find = VsStockPO.find("VIN = ?", vin);
			if (find.size() > 0&&inType!=DictCodeConstants.DICT_STOCK_IN_TYPE_SALE_UNTREAD) {
				throw new ServiceBizException("vin码是" + vin + "车辆库存中已经存在,不能新增");
			} else {
				VsStockEntryItemPO po = new VsStockEntryItemPO();
				dtoToPOAdd(dto, po, inType);
				po.saveIt();
			}
		} else {
			if (dto.getIs_finished() != DictCodeConstants.IS_YES) {// 表示未入账入账
				VsStockEntryItemPO po = VsStockEntryItemPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),
						dto.getSe_no(), dto.getVin());
				dtoToPOEdit(dto, po);
				po.saveIt();
			}
		}
	}

	/**
	 * 修改方法DTO数据存入PO TODO description
	 * 
	 * @author yangjie
	 * @date 2017年1月20日
	 * @param dto
	 * @param po
	 */
	public void dtoToPOEdit(StockInItemsDTO dto, VsStockEntryItemPO po) {
		po.setString("OWNED_BY", FrameworkUtil.getLoginInfo().getUserId());
		po.setDate("VS_PURCHASE_DATE", dto.getVs_purchase_date());
		po.setString("PRODUCT_CODE", dto.getProduct_code());
		po.setString("STORAGE_CODE", dto.getStorage_code());
		po.setString("STORAGE_POSITION_CODE", dto.getStorage_position_code());
		po.setString("ENGINE_NO", dto.getEngine_no());
		po.setString("KEY_NUMBER", dto.getKey_number());
		po.setInteger("HAS_CERTIFICATE", dto.getHas_certificate());
		po.setString("CERTIFICATE_NUMBER", dto.getCertificate_number());
		po.setDate("MANUFACTURE_DATE", dto.getManufacture_date());
		po.setDate("FACTORY_DATE", dto.getFactory_date());
		po.setString("PO_NO", dto.getPo_no());
		po.setString("VENDOR_CODE", dto.getVendor_code());
		po.setString("VENDOR_NAME", dto.getVendor_name());
		po.setDouble("PURCHASE_PRICE", dto.getPurchase_price());
		po.setDouble("ADDITIONAL_COST", dto.getAdditional_cost());
		po.setString("CONSIGNER_CODE", dto.getConsigner_code());
		po.setString("CONSIGNER_NAME", dto.getConsigner_name());
		po.setDate("SHIPPING_DATE", dto.getShipping_date());
		po.setString("SHIPPER_LICENSE", dto.getShipper_license());
		po.setString("SHIPPER_NAME", dto.getShipper_name());
		po.setString("DELIVERYMAN_NAME", dto.getDeliveryman_name());
		po.setString("DELIVERYMAN_PHONE", dto.getDeliveryman_phone());
		po.setDate("ARRIVED_DATE", dto.getArrived_date());
		po.setString("SHIPPING_ORDER_NO", dto.getShipping_order_no());
		po.setString("SHIPPING_ADDRESS", dto.getShipping_address());
		po.setDate("ARRIVING_DATE", dto.getArriving_date());
		po.setInteger("INSPECTION_RESULT", dto.getInspection_result());
		po.setString("INSPECTOR", dto.getInspector());
		po.setDate("INSPECTION_DATE", dto.getInspection_date());
		po.setInteger("MAR_STATUS", dto.getMar_status());
		po.setInteger("OEM_TAG", dto.getOem_tag());
		po.setInteger("IS_UPLOAD", dto.getIs_upload());
		po.setDate("SUBMIT_TIME", dto.getSubmit_time());
		po.setString("FINANCIAL_BILL_NO", dto.getFinancial_bill_no());
		po.setInteger("SUPERVISE_TYPE", dto.getSupervise_type());
		po.setString("MODEL_YEAR", dto.getModel_year());
		po.setString("REMARK", dto.getRemark());
		po.setString("EXHAUST_QUANTITY", dto.getExhaust_quantity());
		po.setString("VSN", dto.getVsn());
		po.setString("COLOR_CODE", dto.getColor_code());
		po.setInteger("DISCHARGE_STANDARD", dto.getDischarge_standard());
		if (dto.getIs_finished() == null) {
			po.setInteger("IS_FINISHED", DictCodeConstants.IS_NOT);
		} else {
			po.setInteger("IS_FINISHED", dto.getIs_finished());
		}
		po.setInteger("INSPECTION_CONSIGNED", dto.getInspection_consigned());
		po.setString("DELIVERY_TYPE", dto.getDelivery_type());
		po.setInteger("IS_DIRECT", dto.getIs_direct());
	}

	/**
	 * 新增方法DTO数据存入PO TODO description
	 * 
	 * @author yangjie
	 * @date 2017年1月19日
	 * @param dto
	 * @param po
	 */
	public void dtoToPOAdd(StockInItemsDTO dto, VsStockEntryItemPO po, Integer inType) {
		po.setString("OWNED_BY", FrameworkUtil.getLoginInfo().getUserId());
		po.setInteger("DISCHARGE_STANDARD", dto.getDischarge_standard());
		po.setInteger("HAS_CERTIFICATE", dto.getHas_certificate());
		po.setInteger("INSPECTION_RESULT", dto.getInspection_result());
		po.setInteger("SUPERVISE_TYPE", dto.getSupervise_type());
		po.setInteger("IS_FINISHED", dto.getIs_finished());
		po.setInteger("IS_DIRECT", dto.getIs_direct());
		po.setInteger("IS_UPLOAD", dto.getIs_upload());
		po.setInteger("MAR_STATUS", dto.getMar_status());
		po.setInteger("OEM_TAG", dto.getOem_tag());
		po.setInteger("INSPECTION_CONSIGNED", dto.getInspection_consigned());
		po.setString("CERTIFICATE_NUMBER", dto.getCertificate_number());
		po.setString("CONSIGNER_CODE", dto.getConsigner_code());
		po.setString("CONSIGNER_NAME", dto.getConsigner_name());
		po.setString("DELIVERY_TYPE", dto.getDelivery_type());
		po.setString("DELIVERYMAN_NAME", dto.getDeliveryman_name());
		po.setString("DELIVERYMAN_PHONE", dto.getDeliveryman_phone());
		po.setString("ENGINE_NO", dto.getEngine_no());
		po.setString("EXHAUST_QUANTITY", dto.getExhaust_quantity());
		po.setString("FINANCIAL_BILL_NO", dto.getFinancial_bill_no());
		po.setString("INSPECTOR", dto.getInspector());
		po.setString("KEY_NUMBER", dto.getKey_number());
		po.setString("PO_NO", dto.getPo_no());
		po.setString("PRODUCT_CODE", dto.getProduct_code());
		po.setString("PRODUCTING_AREA", dto.getProducting_area());
		po.setString("REMARK", dto.getRemark());
		po.setString("SE_NO", dto.getSe_no());
		po.setString("SHIPPER_LICENSE", dto.getShipper_license());
		po.setString("SHIPPER_NAME", dto.getShipper_name());
		po.setString("SHIPPING_ADDRESS", dto.getShipping_address());
		po.setString("SHIPPING_ORDER_NO", dto.getShipping_order_no());
		po.setString("STORAGE_CODE", dto.getStorage_code());
		po.setString("STORAGE_POSITION_CODE", dto.getStorage_position_code());
		po.setString("VENDOR_CODE", dto.getVendor_code());
		po.setString("VENDOR_NAME", dto.getVendor_name());
		po.setString("VIN", dto.getVin());
		po.setString("VSN", dto.getVsn());
		po.setString("COLOR_CODE", dto.getColor_code());
		po.setString("MODEL_YEAR", dto.getModel_year());
		po.setDouble("ADDITIONAL_COST", dto.getAdditional_cost());
		po.setDouble("PURCHASE_PRICE", dto.getPurchase_price());
		po.setDate("ARRIVED_DATE", dto.getArrived_date());
		po.setDate("ARRIVING_DATE", dto.getArriving_date());
		po.setDate("FACTORY_DATE", dto.getFactory_date());
		po.setDate("FINISHED_DATE", dto.getFinished_date());
		po.setDate("INSPECTION_DATE", dto.getInspection_date());
		po.setDate("MANUFACTURE_DATE", dto.getManufacture_date());
		po.setDate("SHIPPING_DATE", dto.getShipping_date());
		po.setDate("SUBMIT_TIME", dto.getSubmit_time());
		po.setDate("VS_PURCHASE_DATE", dto.getVs_purchase_date());
	}

	/**
	 * 查询不在数据库中的VIN值 TODO description
	 * 
	 * @author yangjie
	 * @date 2017年1月19日
	 * @param param
	 * @param seNo
	 * @return 不在数据库明细表中的VIN值
	 */
	public List<String> findNotInListVIN(StockInDTO dto) {
		String sql = "SELECT VIN,DEALER_CODE FROM TT_VS_STOCK_ENTRY_ITEM WHERE SE_NO = '" + dto.getSeNo() + "'";
		List<Map> list = DAOUtil.findAll(sql, null);// 查询数据库中此入库单相关所有的VIN和DEALER_CODE
		List<String> para = new ArrayList<String>();// 用于保存数据库中此入库单相关所有的VIN
		List<String> result = new ArrayList<String>();// 用于保存前台传过来的VIN而不存在于数据库中的VIN
		for (Map map : list) {
			para.add(map.get("VIN").toString());
		}
		for (StockInItemsDTO map : dto.getDms_table()) {
			if (para.size() > 0) {
				if (!para.contains(map.getVin())) {// 传到后台的VIN值不存在数据库中
					result.add(map.getVin());
				}
			} else {
				result.add(map.getVin());// 因为数据库中此入库单明细中没有数据,所以将前台传过来的数据视为result里的项
			}
		}
		return result;
	}

	/**
	 * 查询需要在数据库删除的VIN值 TODO description
	 * 
	 * @author yangjie
	 * @date 2017年1月22日
	 * @param dto
	 * @return
	 */
	public List<String> findNotInDataBaseVIN(StockInDTO dto) {
		String sql = "SELECT VIN,DEALER_CODE FROM TT_VS_STOCK_ENTRY_ITEM WHERE SE_NO = '" + dto.getSeNo() + "'";
		List<Map> list = DAOUtil.findAll(sql, null);// 查询数据库中此入库单相关所有的VIN和DEALER_CODE
		List<String> para = new ArrayList<String>();// 用于保存数据库中此入库单相关所有的VIN
		List<String> data = new ArrayList<String>();// 用于保存前台此入库单相关所有的VIN
		List<String> result = new ArrayList<String>();// 用于保存数据库中的VIN而不存在于前台传过来的VIN
		for (StockInItemsDTO sii : dto.getDms_table()) {
			if (sii.getVin() != null) {
				data.add(sii.getVin());
			}
		}
		for (Map map : list) {
			if (map.get("VIN") != null) {
				para.add(map.get("VIN").toString());
			}
		}
		if (dto.getDms_table().size() > 0) {
			for (String str : para) {
				if (!data.contains(str)) {// 传到后台的VIN值存在数据库中而不存在前台,做删除操作的VIN
					result.add(str);
				}
			}
		} else {
			result = para;// 因为前台传过来的数据中没有数据,所以将数据库中此入库单明细的数据视为result里的项
		}
		return result;
	}

	/**
	 * 入库信息验证 TODO description
	 * 
	 * @author yangjie
	 * @date 2017年1月19日
	 * @param param
	 * @return true 验证通过
	 * @throws ServiceBizException
	 */
	public boolean checkBtnSave(StockInDTO param) throws ServiceBizException {
		List<String> res = new ArrayList<String>();// 用来保存验证不通过的VENDOR_CODE
		Boolean flag = true;
		// 获取每个入库车辆信息
		for (StockInItemsDTO map : param.getDms_table()) {
			// 是否需要检验供应单位
			Long result = Long.parseLong(Utility.getDefaultValue("8095"));
			if (DictCodeConstants.IS_YES == result) {// 代表需要验证
				Map<String, String> query = new HashMap<String, String>();
				if (map.getVendor_code() != null && map.getVendor_name() != null) {
					query.put("CUSTOMER_CODE", map.getVendor_code());
					query.put("CUSTOMER_NAME", map.getVendor_name());
					if (!checkVendor(query)) {// 验证VENDOR_CODE未通过
						res.add(map.getVendor_code());
						flag = false;
					} else {// 验证VENDOR_CODE通过
						if (map.getVin() != null&&param.getInType()!=DictCodeConstants.DICT_STOCK_IN_TYPE_SALE_UNTREAD) {// 判断VIN在入库单是否存在
							if (checkVIN(map.getVin())) {// 是
								throw new ServiceBizException("VIN已存在!");
							}
							// 对采购入库的VIN进行验证
							if (checkInStock(map.getVin())) {
								throw new ServiceBizException("入库单已存在此VIN!");
							}
						}
					}
				}
			}
		}
		if (!flag) {
			StringBuffer sb = new StringBuffer();
			sb.append("[");
			for (int i = 0; i < res.size(); i++) {
				if (i != res.size() - 1) {
					sb.append(res.get(i) + ",");
				} else {
					sb.append(res.get(i));
				}
			}
			sb.append("]");
			throw new ServiceBizException("序号为" + sb.toString() + "的记录中VENDOR_CODE或VENDOR_NAME不正确，请核实相关信息！");
		} else {
			return true;
		}
	}

	/**
	 * 在库车辆检查 TODO description
	 * 
	 * @author yangjie
	 * @date 2017年1月19日
	 * @param vin
	 * @return true 存在 false 不存在
	 */
	public Boolean checkInStock(String vin) {
		String sql = "SELECT A.* FROM TM_VS_STOCK A WHERE A.STOCK_IN_TYPE IN ("
				+ DictCodeConstants.DICT_STOCK_IN_TYPE_BUY_NEW_VEHICLE + ") AND A.STOCK_OUT_TYPE NOT IN ("
				+ DictCodeConstants.DICT_STOCK_OUT_TYPE_UNTREAD + ") AND VIN = '" + vin + "' AND DEALER_CODE='"
				+ FrameworkUtil.getLoginInfo().getDealerCode() + "'";
		List<Map> list = DAOUtil.findAll(sql, null);
		if (list.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断VIN在入库单明细中是否重复 TODO description
	 * 
	 * @author yangjie
	 * @date 2017年1月19日
	 * @param vin
	 * @return true 重复 false 不重复
	 */
	public Boolean checkVIN(String vin) {
		String sql = "SELECT * FROM TT_VS_STOCK_ENTRY_ITEM WHERE VIN LIKE ?";
		List<String> query = new ArrayList<String>();
		query.add("'%" + vin + "%'");
		List<Map> list = DAOUtil.findAll(sql, query);
		if (list.size() > 0) {// 代表重复
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断CUSTOMER_CODE是否存在VENDOR_CODE TODO description
	 * 
	 * @author yangjie
	 * @date 2017年1月19日
	 * @param map
	 * @return true 存在 false 不存在
	 */
	public Boolean checkVendor(Map<String, String> map) {
		StringBuffer sb = new StringBuffer();
		sb.append(
				" SELECT A.BAL_OBJ_CODE,A.BAL_OBJ_NAME,A.ACCOUNT_AGE,A.ARREARAGE_AMOUNT,A.CUS_RECEIVE_SORT,A.CUSTOMER_CODE, A.DEALER_CODE, A.CUSTOMER_TYPE_CODE, A.CUSTOMER_NAME, A.PRE_PAY,");
		sb.append(" A.CUSTOMER_SPELL, A.CUSTOMER_SHORT_NAME, A.ADDRESS, A.ZIP_CODE, A.CONTACTOR_NAME, ");
		sb.append(" A.CONTACTOR_PHONE, A.CONTACTOR_MOBILE, A.FAX, A.CONTRACT_NO, A.AGREEMENT_BEGIN_DATE, ");
		sb.append(" A.AGREEMENT_END_DATE, A.PRICE_ADD_RATE, A.PRICE_RATE, A.SALES_BASE_PRICE_TYPE,A.LEAD_TIME,");
		sb.append(" A.CREDIT_AMOUNT, A.TOTAL_ARREARAGE_AMOUNT, A.ACCOUNT_TERM, A.TOTAL_ARREARAGE_TERM, ");
		sb.append(" A.OEM_TAG, A.LEAD_TIME, A.CREATE_BY, A.CREATE_DATE, A.UPDATE_BY, A.UPDATE_DATE, A.VER, ");
		sb.append(" B.DEALER_CODE, B.DEALER_SHORT_NAME as DEALER_NAME,D.VIN  ");
		sb.append(" from VM_PART_CUSTOMER A ");
		sb.append(" left join TM_PART_CUSTOMER C on A.DEALER_CODE=C.DEALER_CODE and A.CUSTOMER_CODE=C.CUSTOMER_CODE ");
		sb.append(
				" left join TM_PART_CUSTOMER_DEALER B on C.DEALER_CODE=B.DEALER_CODE and C.DEALER_CODE=B.DEALER_CODE ");
		sb.append(" LEFT JOIN VM_OWNER E ON A.DEALER_CODE=E.DEALER_CODE AND A.CUSTOMER_CODE=E.CUSTOMER_CODE ");
		sb.append(" LEFT JOIN VM_VEHICLE D ON E.DEALER_CODE=D.DEALER_CODE AND E.OWNER_NO=D.OWNER_NO ");
		sb.append(" WHERE  A.DEALER_CODE = '" + FrameworkUtil.getLoginInfo().getDealerName() + "'");
		Utility.sqlToLike(sb, map.get("CUSTOMER_CODE"), "CUSTOMER_CODE", "A");
		Utility.sqlToLike(sb, map.get("CUSTOMER_NAME"), "CUSTOMER_NAME", "A");
		Utility.sqlToEquals(sb, map.get("CUSTOMER_TYPE_CODE"), "CUSTOMER_TYPE_CODE", "A");
		Utility.sqlToLike(sb, map.get("CUSTOMER_SPELL"), "CUSTOMER_SPELL", "A");
		sb.append(" ORDER BY A.CUSTOMER_CODE ");
		List<Map> list = DAOUtil.findAll(sb.toString(), null);
		if (list.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 查询入库信息(验证时)
	 * 
	 * @author yangjie
	 * @date 2017年1月22日
	 * @param vin
	 * @return (non-Javadoc)
	 * @see com.yonyou.dms.vehicle.service.basedata.StockInService#findStockInInfo(java.lang.String)
	 */
	@Override
	public Map findStockInInfo(String vin) {
		String sql = "select SE_NO,DEALER_CODE,VIN,ENGINE_NO,CERTIFICATE_NUMBER,KEY_NUMBER,DELIVERYMAN_NAME,DELIVERYMAN_PHONE,SHIPPER_LICENSE,SHIPPER_NAME,SHIPPING_ADDRESS,INSPECTION_RESULT,INSPECTOR,MAR_STATUS from TT_VS_STOCK_ENTRY_ITEM where vin = '"
				+ vin + "'";
		return DAOUtil.findFirst(sql, null);
	}

	
    /**
    * @author LiGaoqi
    * @date 2017年5月9日
    * @param vin
    * @param seNo
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.vehicle.service.basedata.StockInService#findByVinAndSeNo(java.lang.String, java.lang.String)
    */
    	
    @Override
    public Map findByVinAndSeNo(String vin, String seNo) throws ServiceBizException {
        String sql = "select SE_NO,DEALER_CODE,VIN,ENGINE_NO,CERTIFICATE_NUMBER,KEY_NUMBER,DELIVERYMAN_NAME,DELIVERYMAN_PHONE,SHIPPER_LICENSE,SHIPPER_NAME,SHIPPING_ADDRESS,INSPECTION_RESULT,INSPECTOR,MAR_STATUS from TT_VS_STOCK_ENTRY_ITEM where SE_NO='"+seNo+"' AND VIN = '"
                + vin + "'";
        return DAOUtil.findFirst(sql, null);
    }

    /**
	 * 查询所有质损信息
	 * 
	 * @author yangjie
	 * @date 2017年1月22日
	 * @param vin
	 * @return (non-Javadoc)
	 * @see com.yonyou.dms.vehicle.service.basedata.StockInService#findAllInpectionList(java.lang.String)
	 */
	@Override
	public List<Map> findAllInpectionList(String vin) {
		String sql = "select * from TT_VS_INSPECTION_MAR where vin = '" + vin + "'";
		return DAOUtil.findAll(sql, null);
	}

	/**
	 * 删除质损信息
	 * 
	 * @author yangjie
	 * @date 2017年1月22日
	 * @param itemId
	 * @throws ServiceBizException
	 *             (non-Javadoc)
	 * @see com.yonyou.dms.vehicle.service.basedata.StockInService#delInpectionInfo(java.lang.String)
	 */
	@Override
	public void delInpectionInfo(String itemId) throws ServiceBizException {
		VsInspectionMarPO po = VsInspectionMarPO.findById(itemId);
		po.delete();
	}

	/**
	 * 新增或修改质损信息
	 * 
	 * @author yangjie
	 * @date 2017年1月22日
	 * @param dto
	 * @throws ServiceBizException
	 *             (non-Javadoc)
	 * @see com.yonyou.dms.vehicle.service.basedata.StockInService#addOrEditInpectionInfo(com.yonyou.dms.vehicle.domains.DTO.basedata.InspectionMarDTO)
	 */
	@Override
	public void addOrEditInpectionInfo(InspectionAboutDTO dto) throws ServiceBizException {
		VsStockEntryItemPO vei = VsStockEntryItemPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),
				dto.getSeNo(), dto.getVin());
		vei.setString("INSPECTOR", FrameworkUtil.getLoginInfo().getUserId());
		vei.setInteger("MAR_STATUS", dto.getMarStatus());
		vei.setInteger("INSPECTION_RESULT", dto.getInspectionResult());
		vei.setDate("INSPECTION_DATE", new Date());
		vei.saveIt();// 保存验收信息
		if (dto.getInspectionResult() != DictCodeConstants.INSPECTION_RESULT_SUCCESS) {// 验收未通过
			for (InspectionMarDTO itm : dto.getDms_tab()) {
				System.err.println(itm.getItemId());
				if (itm.getItemId() == null || itm.getItemId() == 0) {// 新增
					System.err.println(1);
					VsInspectionMarPO po = new VsInspectionMarPO();
					inspectionPOToDTO(po, itm);
					po.setString("VIN", dto.getVin());
					po.setString("DEALER_CODE", FrameworkUtil.getLoginInfo().getDealerCode());
					po.setString("SE_NO", dto.getSeNo());
					po.setInteger("D_KEY", CommonConstants.D_KEY);
					po.setString("OWNED_BY", FrameworkUtil.getLoginInfo().getUserId());
					po.saveIt();
				} else {// 修改
					System.err.println(2);
					VsInspectionMarPO po = VsInspectionMarPO.findById(itm.getItemId());
					inspectionPOToDTO(po, itm);
					po.saveIt();
				}

			}
		} else {// 验收通过
//			LazyList<VsInspectionMarPO> findBySQL = VsInspectionMarPO
//					.findBySQL("SELECT * FROM TT_VS_INSPECTION_MAR WHERE  SE_NO = ?", dto.getSeNo());
			/*for (VsInspectionMarPO vsInspectionMarPO : findBySQL) {
				vsInspectionMarPO.delete();
			}*/
		}
	}

	/**
	 * 入库
	 * 
	 * @author yangjie
	 * @date 2017年2月3日
	 * @param items
	 * @throws ServiceBizException
	 *             (non-Javadoc)
	 * @see com.yonyou.dms.vehicle.service.basedata.StockInService#btnStockIn(java.util.List)
	 *      items中的字段包括： InType(入库类型), Vin, SeNo, ExhaustQuantity, ShippingDate,
	 *      Vsn, ColorCode, DischargeStandard, ProductCode, StorageCode,
	 *      StoragePositionCode, PurchasePrice, AdditionCost,
	 *      MarStatus,EngineNo,KeyNumber,HasCertificate,
	 *      CertificateNumber,ManufactureDate,FactoryDate,VendorCode,VendorName,AdditionalCost,ConsignerCode,ConsignerName,
	 *      ProductingArea,Remark,PurchaseDate,SuperviseType,PoNo
	 */
	@Override
	public void btnStockIn(List<Map> items, String seNo, String inType, String createdBy) throws ServiceBizException {
	    
		if (StringUtils.isNullOrEmpty(inType)) {
		    System.out.println("222222");
			inType = Base.firstCell("SELECT STOCK_IN_TYPE FROM TT_VS_STOCK_ENTRY WHERE SE_NO = ?", seNo).toString();
		}
		System.out.println(inType);
		String code = FrameworkUtil.getLoginInfo().getDealerCode();
//		String sss = "SELECT DEALER_CODE,USER_ID FROM tm_user WHERE USER_NAME = ?";
//		List qqq = new ArrayList();
//		qqq.add(createdBy);
//		List<Map> map4 = DAOUtil.findAll(sss, qqq);
//		Long sheetCreatedBy = (Long) map4.get(0).get("USER_ID");
		Long sheetCreatedBy = FrameworkUtil.getLoginInfo().getUserId();
		String checkPDI = checkPDI(items);// PDI校验
		if (!StringUtils.isNullOrEmpty(checkPDI)) {
			throw new ServiceBizException("[" + checkPDI.substring(0, checkPDI.length() - 1) + "]未做PDI检查不能入库");
		}
		checkIn(items);// 入库校验
System.out.println("ka");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (items.size() > 0) {
			String customerNo = "";// 客户编号
			if (inType.equals("13071005")) {
			    System.out.println("ka1");
				// 销售退回入库
				// 1根据VIN修改车辆库存里 库存状态 在库,入库业务类型,入库单编号,最后入库日期,最后入库人
				// 2修改销售定单主表里 单据状态为13011070 退回已入库
				// 3向车辆库存日志insert
				// 4修改车辆明细里入帐日期,入帐人,和是否入帐字段
				for (Map map : items) {
					if (inType != null) {
						// 判断主键是否为空
						if (map.get("VIN") != null) {
						    
							VsStockPO stockPO = VsStockPO.findByCompositeKeys(code, map.get("VIN").toString());
							stockPO.setInteger("OWNED_BY", FrameworkUtil.getLoginInfo().getUserId());
							try {
								stockPO.setDate("LATEST_STOCK_IN_DATE", sdf.parse(sdf.format(new Date())));
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							stockPO.setLong("LAST_STOCK_IN_BY", sheetCreatedBy);
							stockPO.setString("SE_NO", map.get("SE_NO").toString());
							stockPO.setInteger("STOCK_IN_TYPE", inType);
							stockPO.setInteger("STOCK_STATUS", DictCodeConstants.DICT_STORAGE_STATUS_IN_STORAGE);
							stockPO.setInteger("DISPATCHED_STATUS",
									DictCodeConstants.DICT_DISPATCHED_STATUS_NOT_DISPATCHED);
							if (!StringUtils.isNullOrEmpty(map.get("EXHAUST_QUANTITY"))) {
								stockPO.setString("EXHAUST_QUANTITY", map.get("EXHAUST_QUANTITY").toString());
							}
							if (!StringUtils.isNullOrEmpty(map.get("PRODUCT_CODE"))) {
								stockPO.setString("PRODUCT_CODE", map.get("PRODUCT_CODE").toString());// 2017年3月8日
																										// 更新库存表产品代码编号
							}
							if (!StringUtils.isNullOrEmpty(map.get("SHIPPING_DATE"))) {
								stockPO.setDate("SHIPPING_DATE", map.get("SHIPPING_DATE"));
							}
							if (!StringUtils.isNullOrEmpty(map.get("VSN"))) {
								stockPO.setString("VSN", map.get("VSN").toString());
							}
							if (!StringUtils.isNullOrEmpty(map.get("COLOR_CODE"))) {
								stockPO.setString("Color_Code", map.get("COLOR_CODE").toString());
							}
							if (!StringUtils.isNullOrEmpty(map.get("DISCHARGE_STANDARD"))) {
								stockPO.setInteger("Discharge_Standard", map.get("DISCHARGE_STANDARD"));
							}
							stockPO.saveIt();

							inWayVIN(map.get("VIN").toString());

							/**
							 * 销售退回会对应2张销售订单ili 新业务根据 vin和业务类型查到唯一的销售订单编号
							 * 1.修改现在订单中 订单状态为（退回已入库） 2.将原订单状态改为已退回
							 * 3.将原销售订单中订单已收和代办已收写入退回订单
							 * 4.重新计算退回订单，订单欠款和代办欠款，如果订单欠款和代办欠款都小于等于0则修改是否结清为是
							 * 5.更新潜在客户级别
							 * 6.如果改客户还有其他有效订单，客户状态不改，如果没有其他有效订单则把改客户的状态从保有客护改为潜在客户
							 */
							String sql = "SELECT * FROM TT_SALES_ORDER where dealer_code = ? and d_key = ? and vin = ? and business_type = ? and so_status = ?";
							List queryParam = new ArrayList();
							queryParam.add(code);
							queryParam.add(CommonConstants.D_KEY);
							queryParam.add(map.get("VIN").toString());
							queryParam.add(DictCodeConstants.DICT_SO_TYPE_RERURN);
							queryParam.add(DictCodeConstants.DICT_SO_STATUS_WAIT_UNTREAT_IN_STOCK);

							// 查销售订单号
							List<Map> orderItemA = DAOUtil.findAll(sql, queryParam);
							String soNO = "";
							String soNoOld = "";
							double orderPayAmount = 0;// 原订单订单已收
							double conPayAmount = 0; // 原订单代办已收

							if (orderItemA.size() > 0) {
								Map orderPO = orderItemA.get(0);
								soNO = orderPO.get("SO_NO").toString();
								soNoOld = orderPO.get("OLD_SO_NO").toString();
								customerNo = orderPO.get("CUSTOMER_NO").toString();
								SalesOrderPO salesOrderPO = SalesOrderPO.findByCompositeKeys(code, soNoOld);
								if (salesOrderPO != null && salesOrderPO.getInteger("D_KEY") == CommonConstants.D_KEY) {
									salesOrderPO.setInteger("SO_STATUS", DictCodeConstants.DICT_SO_STATUS_HAVE_UNTREAD);
									logger.debug("//把原销售订单中单句状态改为已退回");
									salesOrderPO.saveIt();
									logger.debug("销售订单中 原销售订单号:" + soNoOld);
									// 记录原销售订单的审核历史
									setOrderStatusPO(code, soNoOld, DictCodeConstants.DICT_SO_STATUS_HAVE_UNTREAD);
								}
							}
System.out.println(soNoOld);
System.out.println("====================================1");
							// 判断主键和ENTITY_CODE是否为空
							if (soNO != null && code != null && soNoOld != null) {
								// 取原销售订单订单已收代办已收
								SalesOrderPO orderPO = SalesOrderPO.findByCompositeKeys(code, soNoOld);
								if (orderPO != null && orderPO.getInteger("D_KEY") == CommonConstants.D_KEY) {
								    if(!StringUtils.isNullOrEmpty(orderPO.getString("ORDER_PAYED_AMOUNT")))
								      
									orderPayAmount = orderPO.getDouble("ORDER_PAYED_AMOUNT");
								    if(!StringUtils.isNullOrEmpty(orderPO.getString("CON_PAYED_AMOUNT")))
									conPayAmount = orderPO.getDouble("CON_PAYED_AMOUNT");
								}
								SalesOrderPO orderPOj = SalesOrderPO.findByCompositeKeys(code, soNO);
								orderPOj.setDate("RETURN_IN_DATE", new Date());
								orderPOj.setInteger("RETURN_IN_BY", FrameworkUtil.getLoginInfo().getUserId());
								orderPOj.setInteger("SO_STATUS", DictCodeConstants.DICT_SO_STATUS_HAVE_OUT_STOCK);// 单据状态
																													// 已完成，财务审核不能使之状态该为已完成
								orderPOj.setInteger("IS_UPLOAD", DictCodeConstants.DICT_IS_NO);
								// orderPOj.setConPayedAmount(conPayAmount);
								// orderPOj.setOrderPayedAmount(orderPayAmount);
								// 修改现在的销售订单状态为已完成
								// 把原退回订单的订单已收和代办已收写如新退回订单中
								logger.debug("//修改现在的销售订单状态为退回已入库//把原退回订单的订单已收和代办已收写如新退回订单中");
								orderPOj.saveIt();
								// 取出计算退回订单中SO_NO订单欠款代办欠款
								SalesOrderPO ttSalesOrderPO = SalesOrderPO.findByCompositeKeys(code, soNO);
								double orderArrearageAmount = 0;// 订单欠款
																// 退回订单中重新计算的
								double conArrearageAmount = 0;// 代办欠款
								if (ttSalesOrderPO != null
										&& ttSalesOrderPO.getInteger("D_KEY") == CommonConstants.D_KEY) {
								    if(!StringUtils.isNullOrEmpty(ttSalesOrderPO.getString("ORDER_RECEIVABLE_SUM"))&&!StringUtils.isNullOrEmpty(ttSalesOrderPO.getString("ORDER_PAYED_AMOUNT"))&&!StringUtils.isNullOrEmpty(ttSalesOrderPO.getString("ORDER_DERATED_SUM")))							        
									orderArrearageAmount = ttSalesOrderPO.getDouble("ORDER_RECEIVABLE_SUM")
											- ttSalesOrderPO.getDouble("ORDER_PAYED_AMOUNT")
											- ttSalesOrderPO.getDouble("ORDER_DERATED_SUM");
								    if(!StringUtils.isNullOrEmpty(ttSalesOrderPO.getString("CON_RECEIVABLE_SUM"))&&!StringUtils.isNullOrEmpty(ttSalesOrderPO.getString("CON_PAYED_AMOUNT")))                                   
	                                    
									conArrearageAmount = ttSalesOrderPO.getDouble("CON_RECEIVABLE_SUM")
											- ttSalesOrderPO.getDouble("CON_PAYED_AMOUNT");
								}
								/*************************************** 365 ********************************************/
								if (!StringUtils.isNullOrEmpty(customerNo)) {
									// 判断该客户是否还有有效订单
									PotentialCusPO customerPO = PotentialCusPO.findByCompositeKeys(code, customerNo);
									// 不存在有效订单,修改意向级别为n级，//存在有效订单,不需要修改//维护潜在客户和保佑客户关系表,推车标记为12781001
									customerPO.setInteger("INTENT_LEVEL", DictCodeConstants.DICT_INTENT_LEVEL_N);
									logger.debug("没有有效订单修改意向级别为N级");
									customerPO.saveIt();
								}

								String sb = "SELECT * FROM TT_PO_CUS_RELATION WHERE DEALER_CODE = ? AND VIN = ? AND SO_NO = ?";
								List param = new ArrayList();
								param.add(code);
								param.add(map.get("VIN").toString());
								param.add(soNoOld);// 根据原订单号
								List<Map> list = DAOUtil.findAll(sb, param);
								if (list.size() > 0) {
									for (Map map2 : list) {
										TtPoCusRelationPO cusRelationPO = TtPoCusRelationPO.findByCompositeKeys(
												map2.get("DEALER_CODE").toString(), map2.get("ITEM_ID").toString());
										cusRelationPO.setInteger("VEHI_RETURN", DictCodeConstants.IS_YES);
										if (!StringUtils.isNullOrEmpty(soNoOld)) {
											cusRelationPO.saveIt();
										}
									}
								}
								/**
								 * 结束
								 */

								// 销售退回，把车辆表里该车的客户编号置为空
								List<Map> listEntityVehicle = Utility.getSubEntityList(code, "TM_VEHICLE");
								System.out.println(listEntityVehicle.size());
								if (listEntityVehicle.size() > 0) {
									for (Map map2 : listEntityVehicle) {
										// 二级网点业务，先更私有信息表
										String sql2 = "SELECT * FROM TM_VEHICLE_SUBCLASS WHERE DEALER_CODE = ? AND VIN = ?";
										List query = new ArrayList();
										query.add(code);
										query.add(map.get("VIN").toString());
										List<Map> findAll = DAOUtil.findAll(sql2, query);
										if (findAll.size() > 0) {
											for (Map map3 : findAll) {
												if (!StringUtils.isNullOrEmpty(map3.get("VIN"))
														|| !StringUtils.isNullOrEmpty(map3.get("DEALER_CODE"))
														|| !StringUtils.isNullOrEmpty(map3.get("OWNER_NO"))
														|| !StringUtils.isNullOrEmpty(map3.get("MAIN_ENTITY"))) {
													TmVehicleSubclassPO subclassPO = TmVehicleSubclassPO
															.findByCompositeKeys(map3.get("VIN").toString(),
																	map3.get("DEALER_CODE").toString(),
																	map3.get("OWNER_NO").toString(),
																	map3.get("MAIN_ENTITY").toString());
													subclassPO.setString("SALES_AGENT_NAME", null);
													subclassPO.setString("CONSULTANT", null);
													subclassPO.setDate("SALES_DATE", null);
													subclassPO.setInteger("IS_SELF_COMPANY", null);
													subclassPO.setDate("LAST_MAINTAIN_DATE", null);
													subclassPO.setDate("SUBMIT_TIME", null);
													subclassPO.setInteger("IS_UPLOAD", DictCodeConstants.IS_NOT);
													subclassPO.setInteger("SUBMIT_STATUS", null);
													subclassPO.setString("EXCEPTION_CAUSE", null);
													subclassPO.setString("CUSTOMER_NO", null);
													subclassPO.saveIt();
												}
											}
										}

										VehiclePO vehiclePO = VehiclePO.findByCompositeKeys(map.get("VIN").toString(),
												code);
										vehiclePO.setString("SALES_AGENT_NAME", null);
										vehiclePO.setString("CONSULTANT", null);
										vehiclePO.setDate("SALES_DATE", null);
										vehiclePO.setInteger("IS_SELF_COMPANY", null);
										vehiclePO.setDate("LAST_MAINTAIN_DATE", null);
										vehiclePO.setDate("SUBMIT_TIME", null);
										vehiclePO.setInteger("IS_UPLOAD", DictCodeConstants.IS_NOT);
										vehiclePO.setInteger("SUBMIT_STATUS", null);
										vehiclePO.setString("EXCEPTION_CAUSE", null);
										vehiclePO.setString("CUSTOMER_NO", null);
										vehiclePO.saveIt();
									}
								}

								// 维护潜在客户保佑客户关系表,销售退回把推车标记改为是
								String sql1 = "SELECT * FROM TT_PO_CUS_RELATION WHERE DEALER_CODE = ? AND VIN = ? AND SO_NO = ?";
								List query = new ArrayList();
								query.add(code);
								query.add(map.get("VIN").toString());
								query.add(soNO);
								List<Map> findAll = DAOUtil.findAll(sql1, query);
								if (findAll.size() > 0) {
									for (Map map2 : findAll) {
										TtPoCusRelationPO cusRelationPO = TtPoCusRelationPO.findByCompositeKeys(
												map2.get("DEALER_CODE").toString(), map2.get("ITEM_ID"));
										cusRelationPO.setInteger("VEHI_RETURN", DictCodeConstants.IS_YES);
										if (!StringUtils.isNullOrEmpty(soNO)) {
											cusRelationPO.saveIt();
										}
									}
								}

								VsStockLogPO logPO = new VsStockLogPO();
								logPO.setString("DEALER_CODE", code);
								logPO.setInteger("D_KEY", CommonConstants.D_KEY);
								logPO.setString("VIN", map.get("VIN").toString());
								if (map.get("PRODUCT_CODE") != null) {
									logPO.setString("PRODUCT_CODE", map.get("PRODUCT_CODE").toString());
								}
								// 根据产品代码查询销售指导价
								VsProductPO vsProductPO = VsProductPO.findByCompositeKeys(code,
										map.get("PRODUCT_CODE").toString());
								if (vsProductPO != null && vsProductPO.getInteger("D_KEY") == CommonConstants.D_KEY) {
									logPO.setDouble("DIRECTIVE_PRICE", vsProductPO.get("DIRECTIVE_PRICE"));// 销售指导价
								}
								logPO.setDate("OPERATE_DATE", new Date());
								logPO.setInteger("OPERATION_TYPE",
										Integer.parseInt(DictCodeConstants.DICT_VEHICLE_STORAGE_TYPE_RETURN_IN));
								if (map.get("STORAGE_CODE") != null) {
									logPO.setString("STORAGE_CODE", map.get("STORAGE_CODE").toString());
								}
								if (map.get("STORAGE_POSITION_CODE") != null) {
									logPO.setString("STORAGE_POSITION_CODE",
											map.get("STORAGE_POSITION_CODE").toString());
								}
								if (map.get("PURCHASE_PRICE") != null) {
									logPO.setDouble("PURCHASE_PRICE", map.get("PURCHASE_PRICE"));
								}
								if (map.get("ADDITION_COST") != null) {
									logPO.setDouble("ADDITION_COST", map.get("ADDITION_COST"));
								}
								logPO.setInteger("OPERATED_BY", FrameworkUtil.getLoginInfo().getUserId());
								logPO.setInteger("STOCK_STATUS", DictCodeConstants.DICT_STORAGE_STATUS_IN_STORAGE);
								logPO.setInteger("MAR_STATUS", map.get("MAR_STATUS"));

								// 查询车辆库存
								if (map.get("VIN") != null) {
									VsStockPO vsStockPO = VsStockPO.findByCompositeKeys(code,
											map.get("VIN").toString());
									if (vsStockPO != null && vsStockPO.getInteger("D_KEY") == CommonConstants.D_KEY) {
										logPO.setInteger("IS_SECONDHAND", vsStockPO.get("IS_SECONDHAND"));// 2手
										logPO.setInteger("IS_VIP", vsStockPO.get("IS_VIP"));// VIP预留
										logPO.setInteger("IS_TEST_DRIVE_CAR", vsStockPO.get("IS_TEST_DRIVE_CAR"));// 试乘
										logPO.setInteger("IS_CONSIGNED", vsStockPO.get("IS_CONSIGNED"));// 受托交车
										logPO.setInteger("IS_PROMOTION", vsStockPO.get("IS_PROMOTION"));// 促销
										logPO.setInteger("IS_PURCHASE_RETURN", vsStockPO.get("IS_PURCHASE_RETURN"));// 采购退回
										logPO.setInteger("IS_PRICE_ADJUSTED", vsStockPO.get("IS_PRICE_ADJUSTED"));// 是否调价
										logPO.setString("ADJUST_REASON", vsStockPO.get("ADJUST_REASON"));// 调价原因
										logPO.setDouble("OLD_DIRECTIVE_PRICE", vsStockPO.get("OLD_DIRECTIVE_PRICE"));// 愿销售指导价
										logPO.setDouble("DIRECTIVE_PRICE", vsStockPO.get("DIRECTIVE_PRICE"));// 销售价
										logPO.setInteger("OEM_TAG", vsStockPO.get("OEM_TAG"));// OEM_TAG标记
										logPO.setDouble("PURCHASE_PRICE", vsStockPO.get("PURCHASE_PRICE"));// 采购价格
										logPO.setDouble("DIRECTIVE_PRICE", vsStockPO.get("DIRECTIVE_PRICE"));// 销售指导价
										logPO.setDouble("OLD_DIRECTIVE_PRICE", vsStockPO.get("OLD_DIRECTIVE_PRICE"));// 原销售指导价
									}
								}
								logPO.setInteger("OPERATION_TYPE",
										Integer.parseInt(DictCodeConstants.DICT_VEHICLE_STORAGE_TYPE_RETURN_IN));
								logPO.setString("OWNED_BY", FrameworkUtil.getLoginInfo().getUserId());
								logPO.setDouble("PURCHASE_PRICE", map.get("PURCHASE_PRICE"));
								if (vsProductPO != null && vsProductPO.getInteger("D_KEY") == CommonConstants.D_KEY) {
								    if(!StringUtils.isNullOrEmpty(vsProductPO.getString("DIRECTIVE_PRICE")))
									logPO.setDouble("DIRECTIVE_PRICE", vsProductPO.getDouble("DIRECTIVE_PRICE"));// 销售指导价
                                    if(!StringUtils.isNullOrEmpty(vsProductPO.getString("LATEST_PURCHASE_PRICE")))
									logPO.setDouble("LATEST_PURCHASE_PRICE", vsProductPO.getDouble("LATEST_PURCHASE_PRICE"));// 最新采购价格
								}
								logPO.setInteger("DISPATCHED_STATUS",
										DictCodeConstants.DICT_DISPATCHED_STATUS_NOT_DISPATCHED);
								logPO.saveIt();

								if (map.get("SE_NO") != null) {
								    System.out.println("-=============================2");
									VsStockEntryItemPO itemPO = VsStockEntryItemPO.findByCompositeKeys(code,
											map.get("SE_NO").toString(), map.get("VIN").toString());
									
									if (itemPO != null && itemPO.getInteger("D_KEY") == CommonConstants.D_KEY) {
									    System.out.println("-=============================3");
										itemPO.setString("OWNED_BY", FrameworkUtil.getLoginInfo().getUserId());
										itemPO.setInteger("IS_FINISHED", DictCodeConstants.IS_YES);
										itemPO.set("FINISHED_DATE", new Timestamp(System.currentTimeMillis()));
										itemPO.setInteger("FINISHED_BY", FrameworkUtil.getLoginInfo().getUserId());
										itemPO.saveIt();
									}
								}

								if (map.get("VIN") != null) {
									deleteCustomerCR(map.get("VIN").toString());
								}

								sql1 = "SELECT * FROM TT_PO_CUS_RELATION WHERE DEALER_CODE = ? AND PO_CUSTOMER_NO = ? AND D_KEY = "
										+ CommonConstants.D_KEY;
								List ans = new ArrayList();
								ans.add(code);
								ans.add(customerNo);
								List<Map> all = DAOUtil.findAll(sql1, ans);
								if (all.size() > 0) {
									for (Map map2 : all) {
										CustomerPO customerPO = CustomerPO.findByCompositeKeys(
												map2.get("DEALER_CODE").toString(), map2.get("CUSTOMER_NO").toString());
										if (customerPO != null
												&& customerPO.getInteger("D_KEY") == CommonConstants.D_KEY) {
											customerPO.saveIt();
										}
									}
								}
							}
						} else {
							throw new ServiceBizException("操作失败:vin不能为空！");
						}
					}
				}

				String flagg = "1";
				String sql = "select * from tt_vs_stock_entry_item where se_no = ? and d_key = "
						+ CommonConstants.D_KEY;
				List para = new ArrayList();
				para.add(seNo);
				List<Map> findAll = DAOUtil.findAll(sql, para);
				if (findAll.size() > 0) {
					for (Map map2 : findAll) {
						if (map2.get("IS_FINISHED") != null
								&& DictCodeConstants.IS_NOT == Long.parseLong(map2.get("IS_FINISHED").toString())) {
							flagg = "0";
							break;
						}
					}
				}
				if ("1".equals(flagg)) {
					// 修改入库主表是否全部入帐为是
					TtVsStockEntryPO stockEntryPO = TtVsStockEntryPO.findByCompositeKeys(code, seNo);
					if (stockEntryPO != null && stockEntryPO.getInteger("D_KEY") == CommonConstants.D_KEY) {
						stockEntryPO.setInteger("IS_ALL_FINISHED", DictCodeConstants.IS_YES);
						stockEntryPO.setInteger("OWNED_BY", FrameworkUtil.getLoginInfo().getUserId());
						if (!StringUtils.isNullOrEmpty(seNo)) {
							stockEntryPO.saveIt();
						}
					}
				}
			} else {
			    System.out.println("ka2");
				// 新车采购入库
				Double totalPurchasePrice = 0D;// 整车采购价总额
				String vendorCodeTemp = null;
				// 1根据VIN在车辆库存里查是否有相同的VIN如果有则return 0;没有就向车辆库存里insert
				// 2修改整车库存-发货通知里一些状态字段(现在还没)
				// 3向车辆库存日志insert
				// 4修改车辆明细里入帐日期,入帐人,和是否入帐字段
				// 5新增车辆表记录
				for (Map map : items) {
					// 判断主键和ENTITY_CODE是否为空
					if (map.get("VIN") != null) {
						updateTmVehicle(map.get("Vin").toString());
						VsStockPO stockPO = VsStockPO.findByCompositeKeys(code, map.get("VIN").toString());
						if (stockPO == null) {// 车辆库存中没有此VIN车辆
							// 发货通知里 状态未加
							// 向车辆库存表新增记录
							VsStockPO vsStockPO = new VsStockPO();
							vsStockPO.setString("DEALER_CODE", code);
							if (map.get("VIN") != null) {
								vsStockPO.setString("VIN", map.get("VIN").toString());
							}
							if (map.get("COLOR_CODE") != null) {
								vsStockPO.setString("COLOR_CODE", map.get("COLOR_CODE").toString());
							}
							if (map.get("PRODUCT_CODE") != null) {
								vsStockPO.setString("PRODUCT_CODE", map.get("PRODUCT_CODE").toString());
							}
							if (map.get("STORAGE_CODE") != null) {
								vsStockPO.setString("STORAGE_CODE", map.get("STORAGE_CODE").toString());
							}
							if (map.get("STORAGE_POSITION_CODE") != null) {
								vsStockPO.setString("STORAGE_POSITION_CODE",
										map.get("STORAGE_POSITION_CODE").toString());
							}
							if (map.get("ENGINE_NO") != null) {
								vsStockPO.setString("ENGINE_NO", map.get("ENGINE_NO").toString());
							}
							if (map.get("KEY_NUMBER") != null) {
								vsStockPO.setString("KEY_NUMBER", map.get("KEY_NUMBER").toString());
							}
							if (map.get("HAS_CERTIFICATE") != null) {
								vsStockPO.setInteger("HAS_CERTIFICATE", map.get("HAS_CERTIFICATE"));
							}
							if (map.get("CERTIFICATE_NUMBER") != null) {
								vsStockPO.setString("CERTIFICATE_NUMBER", map.get("CERTIFICATE_NUMBER").toString());
							}
							if (map.get("MANUFACTURE_DATE") != null) {
								vsStockPO.setDate("MANUFACTURE_DATE", map.get("MANUFACTURE_DATE"));
							}
							if (map.get("FACTORY_DATE") != null) {
								vsStockPO.setDate("FACTORY_DATE", map.get("FACTORY_DATE"));
							}
							if (map.get("VENDOR_CODE") != null) {
								vsStockPO.setString("VENDOR_CODE", map.get("VENDOR_CODE").toString());
								vendorCodeTemp = map.get("VENDOR_CODE").toString();
							}
							if (map.get("VENDOR_NAME") != null) {
								vsStockPO.setString("VENDOR_NAME", map.get("VENDOR_NAME").toString());
							}
							if (map.get("PURCHASE_PRICE") != null) {
								vsStockPO.setDouble("PURCHASE_PRICE", map.get("PURCHASE_PRICE"));// 采购价格
								totalPurchasePrice += Double.valueOf(map.get("PURCHASE_PRICE").toString() == null
										? "0.0" : map.get("PURCHASE_PRICE").toString());
							}
							if (map.get("ADDITIONAL_COST") != null) {
								vsStockPO.setDouble("ADDITIONAL_COST", map.get("ADDITIONAL_COST"));
							}
							if (map.get("CONSIGNER_CODE") != null) {
								vsStockPO.setString("CONSIGNER_CODE", map.get("CONSIGNER_CODE").toString());
							}
							if (map.get("CONSIGNER_NAME") != null) {
								vsStockPO.setString("CONSIGNER_NAME", map.get("CONSIGNER_NAME").toString());
							}
							if (map.get("PRODUCTING_AREA") != null) {
								vsStockPO.setString("PRODUCTING_AREA", map.get("PRODUCTING_AREA").toString());
							}
							if (map.get("FINANCIAL_BILL_NO") != null) {
								vsStockPO.setString("FINANCIAL_BILL_NO", map.get("FINANCIAL_BILL_NO").toString());
							}
							if (map.get("MODEL_YEAR") != null) {
								vsStockPO.setString("MODEL_YEAR", map.get("MODEL_YEAR").toString());
							}
							// begin ....监管车
							if (map.get("SUPERVISE_TYPE") != null) {
								vsStockPO.setInteger("SUPERVISE_TYPE", map.get("SUPERVISE_TYPE"));
								if (DictCodeConstants.DICT_SUPERVISE_VEHICLE_TRANSFER
										.equals(map.get("SUPERVISE_TYPE").toString())) {
									if (map.get("PRODUCT_CODE") != null) {
										VsProductPO prpo = VsProductPO.findByCompositeKeys(code,
												map.get("PRODUCT_CODE").toString());
										if (prpo != null && prpo.get("BRAND_CODE") != null
												&& prpo.get("SERIES_CODE") != null) {
											TmMonitorCarMaintePO monitorCarMaintePO = TmMonitorCarMaintePO
													.findByCompositeKeys(code, prpo.getString("BRAND_CODE"),
															prpo.getString("SERIES_CODE"));
											if (monitorCarMaintePO != null && monitorCarMaintePO
													.getInteger("D_KEY") == CommonConstants.D_KEY) {
												int cycleDay = monitorCarMaintePO.getInteger("MONITORING_CYCLE");
												Calendar cal = Calendar.getInstance();
												cal.add(Calendar.DAY_OF_MONTH, cycleDay);
												Date dueDate = cal.getTime();
												vsStockPO.setDate("DUE_DATE", dueDate);
											} else {// 出错。没有维护监控周期
												throw new ServiceBizException("未维护该品牌车系的中转监控周期,无法入库此中转监控车!");
											}
										}
									}
								}

							} // 监管车类型，若是中转监控车的话还要加到期日期
							vsStockPO.setInteger("STOCK_IN_TYPE", Integer.parseInt(inType));// 入库业务类型
							vsStockPO.setInteger("STOCK_STATUS", DictCodeConstants.DICT_STORAGE_STATUS_IN_STORAGE);// 库存状态
							if (map.get("MAR_STATUS") != null) {
								vsStockPO.setInteger("MAR_STATUS", map.get("MAR_STATUS"));
							}
							vsStockPO.setInteger("IS_SECONDHAND", DictCodeConstants.IS_NOT);
							vsStockPO.setInteger("IS_VIP", DictCodeConstants.IS_NOT);
							vsStockPO.setInteger("IS_TEST_DRIVE_CAR", DictCodeConstants.IS_NOT);
							vsStockPO.setInteger("IS_PROMOTION", DictCodeConstants.IS_NOT);
							vsStockPO.setInteger("IS_PURCHASE_RETURN", DictCodeConstants.IS_NOT);
							vsStockPO.setLong("LAST_STOCK_IN_BY", sheetCreatedBy);
							vsStockPO.set("FIRST_STOCK_IN_DATE", new Timestamp(System.currentTimeMillis()));
							try {
								vsStockPO.setDate("LATEST_STOCK_IN_DATE", sdf.parse(sdf.format(new Date())));
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							if (map.get("REMARK") != null) {
								vsStockPO.setString("REMARK", map.get("REMARK").toString());
							}
							vsStockPO.setInteger("DISPATCHED_STATUS",
									DictCodeConstants.DICT_DISPATCHED_STATUS_NOT_DISPATCHED);// 默认入库时改成未配车
							// 如果已配车的在途车 库存为已配车
							String sql = "SELECT * FROM TT_VS_SHIPPING_NOTIFY WHERE DEALER_CODE = '" + code
									+ "' AND VIN ='" + map.get("VIN").toString() + "' "
									+ " AND VIN IN(SELECT VIN FROM TT_SALES_ORDER WHERE DEALER_CODE='" + code
									+ "' AND SO_STATUS IN(13011010,13011015,13011020,13011025) ) "
									+ " AND VIN not in (select vin From TM_VS_STOCK where DEALER_CODE = '" + code
									+ "')";
							List<Map> all = DAOUtil.findAll(sql, null);
							if (all.size() > 0) {
								vsStockPO.setInteger("DISPATCHED_STATUS",
										DictCodeConstants.DICT_DISPATCHED_STATUS_HAVE_DISPATCHED);// 改成已配车
							}
							if (map.get("OEM_TAG") != null) {
								vsStockPO.setInteger("OEM_TAG", map.get("OEM_TAG"));
							}
							// 入库的时候，电商订单号也要带入车辆库存中
							TtVsShippingNotifyPO notifyPO = TtVsShippingNotifyPO.findByCompositeKeys(code,
									map.get("VIN").toString());
							if (notifyPO != null && notifyPO.getInteger("D_KEY") == CommonConstants.D_KEY) {
								if (notifyPO.getString("EC_ORDER_NO") != null) {
									vsStockPO.setString("EC_ORDER_NO", notifyPO.getString("EC_ORDER_NO"));
									List<Map> qsolist = querySalesOrder(notifyPO.getString("EC_ORDER_NO"));
									if (qsolist.size() > 0) {
										Map qsobean = qsolist.get(0);
										String qsosoNo = qsobean.get("SO_NO").toString();
										String qsosovin = qsobean.get("VIN").toString();
										String qsosoProductCode = qsobean.get("PRODUCT_CODE").toString();
										sql = "SELECT * FROM TT_VS_SHIPPING_NOTIFY WHERE DEALER_CODE = '" + code
												+ "' AND VIN ='" + map.get("VIN").toString() + "' "
												+ " AND VIN IN(SELECT VIN FROM TT_SALES_ORDER WHERE DEALER_CODE='"
												+ code + "' AND SO_STATUS IN(13011010,13011015,13011020,13011025) ) "
												+ " AND VIN not in (select vin From TM_VS_STOCK where DEALER_CODE = '"
												+ code + "')";
										all = DAOUtil.findAll(sql, null);
										if (all.size() > 0) {

										} else {
											if (!StringUtils.isNullOrEmpty(qsosoNo)
													&& StringUtils.isNullOrEmpty(qsosovin)) {
												SalesOrderPO oldTso = SalesOrderPO.findByCompositeKeys(code, qsosoNo);
												if (oldTso != null
														&& oldTso.getInteger("D_KEY") == CommonConstants.D_KEY) {
													oldTso.setString("VIN", map.get("VIN").toString());
													oldTso.setString("PRODUCT_CODE",
															map.get("PRODUCT_CODE").toString());
													oldTso.setString("STORAGE_CODE",
															map.get("STORAGE_CODE").toString());
													oldTso.setDate("DISPATCHED_DATE", new Date());
													oldTso.setString("COLOR_CODE", map.get("COLOR_CODE").toString());
													oldTso.setLong("DISPATCHED_BY", 1l);
													oldTso.saveIt();
													vsStockPO.setString("SO_NO", qsosoNo);
													vsStockPO.setInteger("DISPATCHED_STATUS",
															DictCodeConstants.DICT_DISPATCHED_STATUS_HAVE_DISPATCHED);
												}
											}
										}
									}
								}
							}

							vsStockPO.setLong("OWNED_BY", FrameworkUtil.getLoginInfo().getUserId());
							vsStockPO.setDate("VS_PURCHASE_DATE", map.get("VS_PURCHASE_DATE"));// 新增入库采购日期
							vsStockPO.setString("SE_NO", seNo);

							if (map.get("EXHAUST_QUANTITY") != null) {
								vsStockPO.setString("EXHAUST_QUANTITY", map.get("EXHAUST_QUANTITY").toString());
							}

							if (map.get("VSN") != null) {
								vsStockPO.setString("VSN", map.get("VSN").toString());
							}

							if (map.get("DISCHARGE_STANDARD") != null) {
								vsStockPO.setInteger("DISCHARGE_STANDARD", map.get("DISCHARGE_STANDARD"));// 排放标准
							}

							// 主数据销售指导价
							VsProductPO productPO = VsProductPO.findByCompositeKeys(code,
									map.get("PRODUCT_CODE").toString());

							if (productPO != null && productPO.getInteger("D_KEY") == CommonConstants.D_KEY) {
								vsStockPO.setDouble("DIRECTIVE_PRICE", productPO.getDouble("DIRECTIVE_PRICE"));// 销售指导价格
							}

							if (map.get("IS_DIRECT") != null) {
								vsStockPO.setInteger("IS_DIRECT", map.get("IS_DIRECT"));// 是否直销
							}

							vsStockPO.saveIt();
							ChangePurchaseStatus(map.get("VIN").toString(), code, 1);

							// 增加库存日志
							insertToVehicleLog(map.get("VIN"), inType, map.get("PRODUCT_CODE"), map.get("STORAGE_CODE"),
									map.get("STORAGE_POSITION_CODE"), map.get("PURCHASE_PRICE"),
									map.get("ADDITIONAL_COST"), map.get("MAR_STATUS"),
									DictCodeConstants.DICT_DISPATCHED_STATUS_NOT_DISPATCHED + "");

							VsStockEntryItemPO stockEntryItemPO = VsStockEntryItemPO.findByCompositeKeys(code,
									map.get("SE_NO").toString(), map.get("VIN").toString());
							if (stockEntryItemPO != null) {
								stockEntryItemPO.setInteger("IS_FINISHED", DictCodeConstants.IS_YES);
								stockEntryItemPO.set("FINISHED_DATE", new Timestamp(System.currentTimeMillis()));
								stockEntryItemPO.setLong("FINISHED_BY", FrameworkUtil.getLoginInfo().getUserId());
								stockEntryItemPO.saveIt();
							}
							insertToVehicle(map.get("VIN"), map.get("PRODUCT_CODE"), map.get("ENGINE_NO"), map.get("KEY_NUMBER"), map.get("PURCHASE_PRICE"), map.get("FACTORY_DATE"), map.get("PRODUCTING_AREA"), map.get("EXHAUST_QUANTITY"), map.get("VSN"), map.get("DISCHARGE_STANDARD"), map.get("MODEL_YEAR"));

						} else {// 车辆库存中有此VIN的车辆信息则不做入库操作
							if (stockPO != null && stockPO.getInteger("D_KEY") == CommonConstants.D_KEY
									&& stockPO.getInteger(
											"STOCK_STATUS") == DictCodeConstants.DICT_STORAGE_STATUS_MOVE_OUT
									&& stockPO.getInteger(
											"STOCK_OUT_TYPE") == DictCodeConstants.DICT_STOCK_OUT_TYPE_UNTREAD) {// 采购入库退回车辆，再次入库（库存状态为出库，出库类型为采购出库）
								VsStockPO vsStockPO = VsStockPO.findByCompositeKeys(code, map.get("VIN").toString());
								if (vsStockPO != null && vsStockPO.getInteger("D_KEY") == CommonConstants.D_KEY) {
									vsStockPO.setLong("OWNED_BY", FrameworkUtil.getLoginInfo().getUserId());
									try {
										vsStockPO.setDate("LATEST_STOCK_IN_DATE", sdf.parse(sdf.format(new Date())));
									} catch (ParseException e) {
										e.printStackTrace();
									}
									vsStockPO.setLong("LAST_STOCK_IN_BY", FrameworkUtil.getLoginInfo().getUserId());
									vsStockPO.setString("SE_NO", map.get("SE_NO").toString());
									vsStockPO.setInteger("STOCK_IN_TYPE", Integer.parseInt(inType));// 入库业务类型
									vsStockPO.setInteger("STOCK_STATUS",
											DictCodeConstants.DICT_STORAGE_STATUS_IN_STORAGE);// 库存状态
									vsStockPO.setInteger("DISPATCHED_STATUS",
											DictCodeConstants.DICT_DISPATCHED_STATUS_NOT_DISPATCHED);// 采购退回车辆改为未配车
									vsStockPO.setString("EXHAUST_QUANTITY", map.get("EXHAUST_QUANTITY").toString());// 排气量
									if (map.get("SHIPPING_DATE") != null) {
										vsStockPO.setDate("DELIVERY_TIME", map.get("SHIPPING_DATE"));
									}
									vsStockPO.setString("VSN", map.get("VSN").toString());
									vsStockPO.setString("COLOR_CODE", map.get("COLOR_CODE").toString());// 颜色代码
									vsStockPO.setInteger("DISCHARGE_STANDARD", map.get("DISCHARGE_STANDARD"));// 标准排气量
									vsStockPO.setString("PRODUCT_CODE", map.get("PRODUCT_CODE").toString());// 产品代码
									vsStockPO.setString("STORAGE_CODE", map.get("STORAGE_CODE").toString());// 仓库代码
									if (map.get("ENGINE_NO") != null) {
										vsStockPO.setString("ENGINE_NO", map.get("ENGINE_NO").toString());// 发动机编号
									}
									if (map.get("STORAGE_POSITION_CODE") != null) {
										vsStockPO.setString("STORAGE_POSITION_CODE",
												map.get("STORAGE_POSITION_CODE").toString());// 库位代码
									}
									if (map.get("KEY_NUMBER") != null) {
										vsStockPO.setString("KEY_NUMBER", map.get("KEY_NUMBER").toString());// 钥匙编号
									}
									if (map.get("CERTIFICATE_NUMBER") != null) {
										vsStockPO.setString("CERTIFICATE_NUMBER",
												map.get("CERTIFICATE_NUMBER").toString());// 合格证号
									}
									if (map.get("SUPERVISE_TYPE") != null) {
										vsStockPO.setString("SUPERVISE_TYPE", map.get("SUPERVISE_TYPE").toString());
									}
									if (map.get("FINANCIAL_BILL_NO") != null) {
										vsStockPO.setString("FINANCIAL_BILL_NO",
												map.get("FINANCIAL_BILL_NO").toString());
									}
									if (map.get("MODEL_YEAR") != null) {
										vsStockPO.setString("MODEL_YEAR", map.get("MODEL_YEAR").toString());
									}
									if (map.get("PURCHASE_PRICE") != null) {
										vsStockPO.setDouble("PURCHASE_PRICE", map.get("PURCHASE_PRICE"));// 采购价格
									}
									if (map.get("ADDITIONAL_COST") != null) {
										vsStockPO.setDouble("ADDITIONAL_COST", map.get("ADDITIONAL_COST"));// 附加成本
									}
									if (map.get("MANUFACTURE_DATE") != null) {
										vsStockPO.setDate("MANUFACTURE_DATE", map.get("MANUFACTURE_DATE"));// 生产日期
									}
									if (map.get("FACTORY_DATE") != null) {
										vsStockPO.setDate("FACTORY_DATE", map.get("FACTORY_DATE"));// 出厂日期
									}
									if (map.get("PURCHASE_DATE") != null) {
										vsStockPO.setDate("VS_PURCHASE_DATE", map.get("PURCHASE_DATE"));// 采购日期
									}
									if (map.get("PO_NO") != null) {
										vsStockPO.setString("PO_NO", map.get("PO_NO").toString());// 采购订单编号
									}
									if (map.get("PRODUCTING_AREA") != null) {
										vsStockPO.setString("PRODUCTING_AREA", map.get("PRODUCTING_AREA").toString());// 产地
									}
									if (map.get("VENDOR_CODE") != null) {
										vsStockPO.setString("VENDOR_CODE", map.get("VENDOR_CODE").toString());// 供应单位代码
									}
									if (map.get("VENDOR_NAME") != null) {
										vsStockPO.setString("VENDOR_NAME", map.get("VENDOR_NAME").toString());// 供应单位名称
									}
									vsStockPO.saveIt();

									// 日志
									insertToVehicleLog(map.get("VIN").toString(), inType,
											map.get("PRODUCT_CODE").toString(), map.get("STORAGE_CODE").toString(),
											map.get("STORAGE_POSITION_CODE").toString(),
											map.get("PURCHASE_PRICE").toString(), map.get("ADDITIONAL_COST").toString(),
											map.get("MAR_STATUS").toString(),
											DictCodeConstants.DICT_DISPATCHED_STATUS_NOT_DISPATCHED);

									// 更新入库表
									VsStockEntryItemPO entryItemPO = VsStockEntryItemPO.findByCompositeKeys(code,
											map.get("SE_NO").toString(), map.get("VIN").toString());
									if (entryItemPO != null
											&& entryItemPO.getInteger("D_KEY") == CommonConstants.D_KEY) {
										entryItemPO.setString("OWNED_BY",
												FrameworkUtil.getLoginInfo().getUserId() + "");
										entryItemPO.setInteger("IS_FINISHED", DictCodeConstants.IS_YES);
										entryItemPO.set("FINISHED_DATE", new Timestamp(System.currentTimeMillis()));
										entryItemPO.setInteger("FINISHED_BY", FrameworkUtil.getLoginInfo().getUserId());
										entryItemPO.saveIt();
									}
								}

							}
						}

					} else {
						throw new ServiceBizException("操作失败:vin不能为空！");
					}
				}

				String flagg = "1";
				String sql = "select * from tt_vs_stock_entry_item where se_no = ? and d_key = "
						+ CommonConstants.D_KEY;
				List para = new ArrayList();
				para.add(seNo);
				List<Map> findAll = DAOUtil.findAll(sql, para);
				if (findAll.size() > 0) {
					for (Map map2 : findAll) {
						if (map2.get("IS_FINISHED") != null
								&& DictCodeConstants.IS_NOT == Long.parseLong(map2.get("IS_FINISHED").toString())) {
							flagg = "0";
							break;
						}
					}
				}

				// 生成应付款单据--整车采购入库
				if (!StringUtils.isNullOrEmpty(inType)
						&& (DictCodeConstants.DICT_STOCK_IN_TYPE_BUY_NEW_VEHICLE + "").equals(inType)) {
					TtPaymentInfoPO paymentInfoPO = new TtPaymentInfoPO();
					paymentInfoPO.setString("DEALER_CODE", code);
					paymentInfoPO.setString("BUSINESS_NO", seNo);
					if ((DictCodeConstants.DICT_STOCK_IN_TYPE_BUY_NEW_VEHICLE + "").equals(inType)) {
						paymentInfoPO.setInteger("BUSINESS_TYPE",
								DictCodeConstants.DICT_PAY_BUSINESS_TYPE_VEHICLE_STOCK_IN);
					}
					if (!StringUtils.isNullOrEmpty(vendorCodeTemp)) {
						paymentInfoPO.setString("CUSTOMER_NO", vendorCodeTemp);
					}
					paymentInfoPO.saveIt();
				}

				if ("1".equals(flagg.trim())) {
					// 修改入库主表是否全部入帐为是
					// 判断主键和DEALER_CODE是否为空
					if (seNo != null && code != null) {
						TtVsStockEntryPO entryPO = TtVsStockEntryPO.findByCompositeKeys(code, seNo);
						if (entryPO != null && entryPO.getInteger("D_KEY") == CommonConstants.D_KEY) {
							entryPO.setInteger("OWNED_BY", FrameworkUtil.getLoginInfo().getUserId());
							entryPO.setInteger("IS_ALL_FINISHED", DictCodeConstants.IS_YES);
							entryPO.saveIt();
						}
					}
				}
			}
			// 最后判断中转监管车是否超出了最大额度，若超出，则 回滚操作 提示无法入库。
			String warrantynumber = Utility.getDefaultValue(CommonConstants.DEFAULT_MONITOR_CAR_TOTAL);
			for (Map map : items) {
				if (map.get("SuperviseType") != null && DictCodeConstants.DICT_SUPERVISE_VEHICLE_TRANSFER
						.equals(map.get("SuperviseType").toString()))// 如果是中转车
																		// 就执行下面的
				{
					int isMore = queryIsSuperviseMore(code, warrantynumber);
					if (isMore == 0) {
						throw new ServiceBizException("中转监控车超出最大额度,无法入库!");
					}
				}
			}
		}
		/**
		 * 车辆入库操作生成凭证
		 */
		String value = Utility.getDefaultValue("8901");
		if ((DictCodeConstants.IS_YES + "").equals(value)) {
			value = Utility.getDefaultValue("1003");
			double cess = Double.parseDouble(value);
			for (Map map : items) {
				VsStockEntryItemPO entryItemPO = VsStockEntryItemPO.findByCompositeKeys(code, seNo,
						map.get("Vin").toString());
				if (entryItemPO != null && entryItemPO.getInteger("D_KEY") == CommonConstants.D_KEY) {
					TtAccountsTransFlowPO po = new TtAccountsTransFlowPO();
					Long transId = commonNoService.getId("Tt_Accounts_Trans_Flow");
					po.setLong("TRANS_ID", transId);
					po.setString("ORG_CODE", code);
					po.setString("DEALER_CODE", code);
					po.setDate("TRANS_DATE", new Date());
					po.setInteger("TRANS_TYPE", Integer.parseInt(DictCodeConstants.DICT_BUSINESS_TYPE_STOCK_ENTRY_IN));
					po.setDouble("TAX_AMOUNT", entryItemPO.getDouble("PURCHASE_PRICE"));
					po.setDouble("NET_AMOUNT", Utility.round(entryItemPO.getString("PURCHASE_PRICE"), 2));
					po.setString("BUSINESS_NO", entryItemPO.getString("SE_NO"));
					po.setString("SUB_BUSINESS_NO", entryItemPO.getString("PO_NO"));// 采购订单编号
					po.setLong("IsValid", DictCodeConstants.IS_YES);
					po.setInteger("EXEC_NUM", 0);
					po.setInteger("EXEC_STATUS", DictCodeConstants.DICT_EXEC_STATUS_NOT_EXEC);// 未生产
					po.saveIt();
				}
			}
		}
		
		String flag=SADMS002.getSADMS002(seNo,items);
		if("0".equals(flag)){
		    throw new ServiceBizException("车辆验收信息上报失败");
		}
		/**
		 * 业务描述：车辆验收信息上报 SADMS002
		 */

		/**
		 * 销售退回时将消息上报 SalesReturnUpload
		 */

		// 开关校验
	/*	value = Utility.getDefaultValue("1233");
		if ((DictCodeConstants.IS_NOT + "").equals(value)) {// 产品
			if ((DictCodeConstants.DICT_STOCK_IN_TYPE_SALE_UNTREAD + "").equals(inType)) {// 销售退回入库
				throw new ServiceBizException("该车退回审核通过，需要在系统外通知主机厂该车的具体信息");
			}
		}*/
	}

	/**
	 * 质损明细DTO转PO TODO description
	 * 
	 * @author yangjie
	 * @date 2017年1月22日
	 * @param po
	 * @param dto
	 */
	public void inspectionPOToDTO(VsInspectionMarPO po, InspectionMarDTO dto) {
		po.setInteger("MAR_DEGREE", dto.getMarDegree());
		po.setInteger("MAR_POSITION", dto.getMarPosition());
		po.setInteger("MAR_TYPE", dto.getMarType());
		po.setString("MAR_REMARK", dto.getMarRemark());
	}

	/**
	 * pdi检查 TODO description
	 * 
	 * @author yangjie
	 * @date 2017年2月3日
	 * @param items
	 *            vin,productCode
	 * @return
	 */
	public String checkPDI(List<Map> items) {
		String vins = "";
		if (items.size() > 0) {
			for (Map map : items) {
				if ((map.get("VIN") != null && map.get("VIN") != "")
						&& (map.get("PRODUCT_CODE") != null && map.get("PRODUCT_CODE") != "")) {
					VsProductPO vsProductPO = VsProductPO.findByCompositeKeys(
							FrameworkUtil.getLoginInfo().getDealerCode(), map.get("PRODUCT_CODE").toString());
					if (vsProductPO != null) {
						if (vsProductPO.getInteger("D_KEY") == CommonConstants.D_KEY
								&& vsProductPO.getInteger("IS_MUST_PDI") == DictCodeConstants.IS_YES) {
							VehiclePdiResultPO po = VehiclePdiResultPO.findByCompositeKeys(
									FrameworkUtil.getLoginInfo().getDealerCode(), map.get("VIN").toString());
							if (po == null) {
								vins += map.get("VIN").toString() + ",";
							}
						}
					}
				}
			}
		}
		if (vins != "") {
			return vins;
		} else {
			return null;
		}
	}

	/**
	 * 入库校验 TODO description
	 * 
	 * @author yangjie
	 * @date 2017年2月3日
	 * @param items
	 * @throws ServiceBizException
	 */
	public void checkIn(List<Map> items) throws ServiceBizException {
		if (items.size() > 0) {
			for (Map map : items) {
				if (map.get("INSPECTION_RESULT") != null && map.get("INSPECTION_RESULT") != "") {
					if (Integer.parseInt(map.get("INSPECTION_RESULT").toString()) == 13351001) {// 验收未通过
						throw new ServiceBizException("验收结果为验收已通过的才能入库!");
					}
				}
				if (map.get("STORAGE_CODE") == null || map.get("STORAGE_CODE") == "") {
					throw new ServiceBizException("仓库不能为空 !");
				}
				if (map.get("IS_FINISHED") != null && map.get("IS_FINISHED") != "") {
					if (Integer.parseInt(map.get("IS_FINISHED").toString()) == 13351001) {// 已入账
						throw new ServiceBizException("已入账的不能入库!");
					}
				}
			}
		}
	}

	/************************************************************** 车辆入库操作相关方法 ***********************************************************/

	public int ChangePurchaseStatus(String vin, String entityCode, int type) throws ServiceBizException {
		int listsize = 0;
		int totalNumber = 0;
		int orderTag = 0;
		TtVsShippingNotifyPO shippingNotifyPO = new TtVsShippingNotifyPO();
		TmVehicleStockOrderPO orderPO = new TmVehicleStockOrderPO();
		VsStockPO stockPO = new VsStockPO();
		shippingNotifyPO.setString("VIN", vin);
		shippingNotifyPO.setString("DEALER_CODE", entityCode);
		// 判断vin号是否在发货通知中
		TtVsShippingNotifyPO notifyPO = TtVsShippingNotifyPO.findByCompositeKeys(entityCode, vin);
		if (notifyPO != null && notifyPO.getInteger("D_KEY") == CommonConstants.D_KEY) {
			String poNo = notifyPO.getString("PO_NO");
			if (poNo != null && !poNo.trim().equals("")) {
				/**
				 * 查看采购订单里订购几辆车
				 */
				TmVehicleStockOrderPO stockOrderPO = TmVehicleStockOrderPO.findByCompositeKeys(entityCode, poNo);
				if (stockOrderPO != null && stockOrderPO.getInteger("D_KEY") == CommonConstants.D_KEY) {
					totalNumber = orderPO.getInteger("PURCHASE_COUNT");
					orderTag = orderPO.getInteger("ORDER_STATUS");
				}
				/**
				 * 此采购订单号再发货通知单中存在几辆车
				 */
				String sql = "SELECT * FROM TT_VS_SHIPPING_NOTIFY WHERE PO_NO = ? AND D_KEY = ?";
				List queryParam = new ArrayList();
				queryParam.add(poNo);
				queryParam.add(CommonConstants.D_KEY);
				List<Map> listVehicle = DAOUtil.findAll(sql, queryParam);
				if (listVehicle.size() > 0) {
					int VehicleSize = listVehicle.size();
					for (Map map : listVehicle) {
						sql = "select * from TM_VS_STOCK";
						List<Map> liststock = DAOUtil.findAll(sql, null);
						if (liststock.size() > 0) {
							listsize = listsize + 1;
						}
					}
				}
				if (listsize != 0) {
					if (orderTag != Integer.parseInt(DictCodeConstants.DICT_Vehicle_Purchase_Order_Status_Completed)) {
						TmVehicleStockOrderPO vehicleStockOrderPO = TmVehicleStockOrderPO
								.findByCompositeKeys(entityCode, poNo);
						if (vehicleStockOrderPO != null) {
							if (listsize >= totalNumber) {
								vehicleStockOrderPO.setInteger("ORDER_STATUS", Integer
										.parseInt(DictCodeConstants.DICT_Vehicle_Purchase_Order_Status_All_Arrival));
							} else {
								vehicleStockOrderPO.setInteger("ORDER_STATUS", Integer.parseInt(
										DictCodeConstants.DICT_Vehicle_Purchase_Order_Status_Part_Of_Arrival));
							}
							vehicleStockOrderPO.saveIt();
						}
					}
				}
			}

		}
		return listsize;
	}

	/**
	 * 新增订单审核历史 TODO description
	 * 
	 * @author yangjie
	 * @date 2017年2月4日
	 * @param ENTITY_CODE
	 * @param SO_NO
	 * @param SO_STATUS
	 * @throws ServiceBizException
	 */
	public void setOrderStatusPO(String ENTITY_CODE, String SO_NO, String SO_STATUS) throws ServiceBizException {
		TtOrderStatusUpdatePO apo = new TtOrderStatusUpdatePO();
		SalesOrderPO apoquery = SalesOrderPO.findByCompositeKeys(ENTITY_CODE, SO_NO);
		if (apoquery != null) {
			if (apoquery.get("BUSINESS_TYPE") != null
					&& (apoquery.get("BUSINESS_TYPE").toString().equals(DictCodeConstants.DICT_SO_TYPE_ENTRUST_DELIVERY)
							|| apoquery.get("BUSINESS_TYPE").toString().equals(DictCodeConstants.DICT_SO_TYPE_GENERAL))

			) {
				apo.setString("DEALER_CODE", ENTITY_CODE);
				apo.setString("SO_NO", SO_NO);
				apo.setInteger("SO_STATUS", SO_STATUS);
				apo.setString("OWNED_BY", FrameworkUtil.getLoginInfo().getUserId());
				apo.setInteger("IS_UPLOAD", DictCodeConstants.IS_NOT);
				apo.setDate("ALTERATION_TIME", new Date());
				apo.setInteger("D_KEY", CommonConstants.D_KEY);
				apo.saveIt();
			}
		}
	}

	/**
	 * 各种入库新增TM_VEHICLE 校验车主表示是否有默认车主编号888888888 TODO description
	 * 
	 * @author yangjie
	 * @date 2017年2月3日
	 * @param vin
	 * @param productCode
	 * @param engineNo
	 * @param keyNumber
	 * @param purPrice
	 * @param factoryDate
	 * @param productingArea
	 * @param exhaustQuantity
	 * @param vsn
	 * @param dischargeStandard
	 * @param modelYear
	 * @throws ServiceBizException
	 */
	public void insertToVehicle(Object vin, Object productCode, Object engineNo, Object keyNumber, Object purPrice,
			Object factoryDate, Object productingArea, Object exhaustQuantity, Object vsn, Object dischargeStandard,
			Object modelYear) throws ServiceBizException {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();

		String groupCodeProduct = Utility.getGroupEntity(dealerCode, "TM_VS_PRODUCT");
		String groupCodeOwner = Utility.getGroupEntity(dealerCode, "TM_OWNER");
		String groupCodeVehicle = Utility.getGroupEntity(dealerCode, "TM_VEHICLE");

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		CarownerPO carownerPO = CarownerPO.findByCompositeKeys(CommonConstants.DEALER_DEFAULT_OWNER_NO, groupCodeOwner);

		String ownerNo = "";
		if (carownerPO != null) {
			ownerNo = carownerPO.get("OWNER_NO").toString();
		}
		if (ownerNo != null && !"".equals(ownerNo)) {
			VehiclePO vehiclePO = VehiclePO.findByCompositeKeys(vin.toString(), groupCodeVehicle);
			/******************************************************************************************/
			// Utility.getVehicleSubclassList(dealerCode, list);
			if (StringUtils.isNullOrEmpty(vehiclePO)) {
				vehiclePO = new VehiclePO();
				vehiclePO.setString("DEALER_CODE", groupCodeVehicle);
				vehiclePO.setString("VIN", vin.toString());
				vehiclePO.setString("OWNER_NO", ownerNo);
				VsProductPO productPO = VsProductPO.findByCompositeKeys(groupCodeProduct, productCode.toString());
				if (productPO != null && productPO.getInteger("D_KEY") == CommonConstants.D_KEY) {
					vehiclePO.setString("BRAND", productPO.getString("BRAND_CODE"));
					vehiclePO.setString("SERIES", productPO.getString("SERIES_CODE"));
					vehiclePO.setString("MODEL", productPO.getString("MODEL_CODE"));
					vehiclePO.setString("COLOR", productPO.getString("COLOR_CODE"));
					vehiclePO.setString("APACKAGE", productPO.getString("CONFIG_CODE"));
					if (!StringUtils.isNullOrEmpty(productPO.getString("MODEL_CODE"))) {
						String sql = "SELECT * FROM TM_MODEL WHERE DEALER_CODE = '" + dealerCode
								+ "' AND MODEL_CODE = '" + productPO.getString("MODEL_CODE").toString() + "'";
						Map modelPo = DAOUtil.findFirst(sql, null);
						if (modelPo.size() > 0) {
							vehiclePO.setString("OIL_TYPE", modelPo.get("OIL_TYPE"));
							vehiclePO.setString("PAIQI_QUANTITY", modelPo.get("EXHAUST_QUANTITY"));
						}
					}
				}
				vehiclePO.setString("LICENSE", "无牌照");// 车牌号，转售后定义为售后无牌照的常量
				if (!StringUtils.isNullOrEmpty(purPrice)) {
					vehiclePO.setDouble("VEHICLE_PRICE", Double.parseDouble(purPrice.toString()));// 采购价格
				}
				vehiclePO.setDate("FOUND_DATE", new Date());
				if (!StringUtils.isNullOrEmpty(factoryDate)) {
					try {
						vehiclePO.setDate("PRODUCT_DATE", dateFormat.parse(factoryDate.toString()));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				if(!StringUtils.isNullOrEmpty(engineNo)){
					vehiclePO.setString("ENGINE_NO", engineNo.toString());// 发动机号
				}
				if (!StringUtils.isNullOrEmpty(keyNumber)) {
					vehiclePO.setString("KEY_NUMBER", keyNumber.toString());// 钥匙编号
				}
				if (!StringUtils.isNullOrEmpty(productingArea)) {
					vehiclePO.setString("PRODUCTING_AREA", productingArea.toString());// 产地
				}
				if (!StringUtils.isNullOrEmpty(exhaustQuantity)) {
					vehiclePO.setString("EXHAUST_QUANTITY", exhaustQuantity.toString());// 排气量
				}
				if (!StringUtils.isNullOrEmpty(vsn)) {
					vehiclePO.setString("VSN", vsn.toString());// vsn
				}
				if (!StringUtils.isNullOrEmpty(dischargeStandard)) {
					vehiclePO.setInteger("DISCHARGE_STANDARD", Integer.parseInt(dischargeStandard.toString()));// 排放标准
				}
				if (!StringUtils.isNullOrEmpty(modelYear)) {
					vehiclePO.setString("MODEL_YEAR", modelYear.toString());
					vehiclePO.setString("YEAR_MODEL", modelYear.toString());
				}
				vehiclePO.setInteger("IS_UPLOAD", DictCodeConstants.IS_NOT);
				vehiclePO.setInteger("IS_UPLOAD_GROUP", DictCodeConstants.IS_NOT);
				vehiclePO.setInteger("IS_SELF_COMPANY", DictCodeConstants.IS_YES);
				vehiclePO.setInteger("IS_VALID", DictCodeConstants.IS_YES);
				vehiclePO.saveIt();

				List<Map> listEntityVehicle = Utility.getSubEntityList(vehiclePO.getString("DEALER_CODE"),
						"TM_VEHICLE");
				if (listEntityVehicle != null && listEntityVehicle.size() > 0) {
					for (Map entityPO : listEntityVehicle) {
						TmVehicleSubclassPO poSub = new TmVehicleSubclassPO();
						poSub.setString("MAIN_ENTITY", vehiclePO.getString("DEALER_CODE"));
						poSub.setString("DEALER_CODE", entityPO.get("CHILD_ENTITY"));
						poSub.setString("OWNER_NO", vehiclePO.getString("OWNER_NO"));
						poSub.setString("VIN", vehiclePO.getString("VIN"));

						// comg begin 20110513 //二级网点业务-车辆子表Insert
						poSub.setString("CONSULTANT", vehiclePO.getString("CONSULTANT"));
						poSub.setInteger("IS_SELF_COMPANY", vehiclePO.getInteger("IS_SELF_COMPANY"));
						poSub.setDate("FIRST_IN_DATE", vehiclePO.getDate("FIRST_IN_DATE"));
						poSub.setString("CHIEF_TECHNICIAN", vehiclePO.getString("CHIEF_TECHNICIAN"));
						poSub.setString("SERVICE_ADVISOR", vehiclePO.getString("SERVICE_ADVISOR"));
						poSub.setString("INSURANCE_ADVISOR", vehiclePO.getString("INSURANCE_ADVISOR"));
						poSub.setString("MAINTAIN_ADVISOR", vehiclePO.getString("MAINTAIN_ADVISOR"));
						poSub.setDate("LAST_MAINTAIN_DATE", vehiclePO.getDate("LAST_MAINTAIN_DATE"));
						poSub.setDouble("LAST_MAINTAIN_MILEAGE", vehiclePO.getDouble("LAST_MAINTAIN_MILEAGE"));
						poSub.setDate("LAST_MAINTENANCE_DATE", vehiclePO.getDate("LAST_MAINTENANCE_DATE"));
						poSub.setDouble("LAST_MAINTENANCE_MILEAGE", vehiclePO.getDouble("LAST_MAINTENANCE_MILEAGE"));
						poSub.setDouble("PRE_PAY", vehiclePO.getDouble("PRE_PAY"));
						poSub.setDouble("ARREARAGE_AMOUNT", vehiclePO.getDouble("ARREARAGE_AMOUNT"));
						poSub.setDate("DISCOUNT_EXPIRE_DATE", vehiclePO.getDate("DISCOUNT_EXPIRE_DATE"));
						poSub.setString("DISCOUNT_MODE_CODE", vehiclePO.getString("DISCOUNT_MODE_CODE"));
						poSub.setInteger("IS_SELF_COMPANY_INSURANCE",
								vehiclePO.getInteger("IS_SELF_COMPANY_INSURANCE"));
						poSub.setDate("ADJUST_DATE", vehiclePO.getDate("ADJUST_DATE"));
						poSub.setString("ADJUSTER", vehiclePO.getString("ADJUSTER"));
						poSub.setInteger("IS_VALID", vehiclePO.getInteger("IS_VALID"));
						poSub.setInteger("NO_VALID_REASON", vehiclePO.getInteger("NO_VALID_REASON"));
						// comg end 20110513 二级经销商服务
						poSub.saveIt();
					}
				}
			}

		}
	}

	/**
	 * 新增到日志信息 TODO description
	 * 
	 * @author yangjie
	 * @date 2017年2月3日
	 * @param vin
	 * @param stockInType
	 * @param productCode
	 * @param storageCode
	 * @param storagePositionCode
	 * @param purchasePrice
	 * @param additionCost
	 * @param marStatus
	 * @param dispatch
	 * @throws ServiceBizException
	 */
	public void insertToVehicleLog(Object vin, Object stockInType, Object productCode, Object storageCode,
			Object storagePositionCode, Object purchasePrice, Object additionCost, Object marStatus, Object dispatch)
			throws ServiceBizException {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		VsStockLogPO logPO = new VsStockLogPO();
		logPO.setString("DEALER_CODE", dealerCode.toString());
		logPO.setInteger("D_KEY", CommonConstants.D_KEY);
		logPO.setString("VIN", vin.toString());
		VsProductPO productPO = VsProductPO.findByCompositeKeys(dealerCode, productCode);
		if (productPO != null && productPO.getInteger("D_KEY") == CommonConstants.D_KEY) {
			logPO.setDouble("DIRECTIVE_PRICE", productPO.getDouble("DIRECTIVE_PRICE"));// 销售指导价
			// logPO.setDouble("LATEST_PURCHASE_PRICE",
			// productPO.getDouble("LATEST_PURCHASE_PRICE"));// 最新采购价格
		}
		if (!StringUtils.isNullOrEmpty(productCode)) {
			logPO.setString("PRODUCT_CODE", productCode);
		}
		logPO.setDate("OPERATE_DATE", new Date());
		// logPO.setInteger("OPERATION_TYPE",
		// Integer.parseInt(stockInType.toString()));
		if (!StringUtils.isNullOrEmpty(storageCode)) {
			logPO.setString("STORAGE_CODE", storageCode.toString());
		}
		if (!StringUtils.isNullOrEmpty(storagePositionCode)) {
			logPO.setString("STORAGE_POSITION_CODE", storagePositionCode.toString());
		}
		if (!StringUtils.isNullOrEmpty(purchasePrice)) {
			logPO.setDouble("PURCHASE_PRICE", Double.parseDouble(purchasePrice.toString()));
		}
		if (!StringUtils.isNullOrEmpty(additionCost)) {
			logPO.setDouble("ADDITIONAL_COST", Double.parseDouble(additionCost.toString()));
		}
		logPO.setInteger("STOCK_STATUS", DictCodeConstants.DICT_STORAGE_STATUS_IN_STORAGE);
		if (!StringUtils.isNullOrEmpty(marStatus)) {
			logPO.setInteger("MAR_STATUS", marStatus.toString());
		}
		if (!StringUtils.isNullOrEmpty(vin)) {
			VsStockPO stockPO = VsStockPO.findByCompositeKeys(dealerCode, vin);
			logPO.setInteger("IS_SECONDHAND", stockPO.getInteger("IS_SECONDHAND"));
			logPO.setInteger("IS_VIP", stockPO.getInteger("IS_VIP"));
			logPO.setInteger("IS_TEST_DRIVE_CAR", stockPO.getInteger("IS_TEST_DRIVE_CAR"));// 试乘
			logPO.setInteger("IS_CONSIGNED", stockPO.getInteger("IS_CONSIGNED"));// 受托交车
			logPO.setInteger("IS_PROMOTION", stockPO.getInteger("IS_PROMOTION"));// 促销
			logPO.setInteger("IS_PURCHASE_RETURN", stockPO.getInteger("IS_PURCHASE_RETURN"));// 采购退回
			logPO.setInteger("IS_PRICE_ADJUSTED", stockPO.getInteger("IS_PRICE_ADJUSTED"));// 是否调价
			logPO.setString("ADJUST_REASON", stockPO.getInteger("ADJUST_REASON"));// 调价原因
			logPO.setDouble("OLD_DIRECTIVE_PRICE", stockPO.getDouble("OLD_DIRECTIVE_PRICE"));// 愿销售指导价
			logPO.setDouble("DIRECTIVE_PRICE", stockPO.getDouble("DIRECTIVE_PRICE"));// 销售价
			logPO.setInteger("OEM_TAG", stockPO.getInteger("OEM_TAG"));// OEM_TAG标记
		}
		logPO.setInteger("OPERATION_TYPE", Integer.parseInt(DictCodeConstants.DICT_VEHICLE_STORAGE_TYPE_BUY));
		logPO.setString("OPERATED_BY", FrameworkUtil.getLoginInfo().getUserId().toString());
		logPO.setDouble("PURCHASE_PRICE", Double.parseDouble(purchasePrice.toString()));
		logPO.setInteger("DISPATCHED_STATUS", Integer.parseInt(dispatch.toString()));
		logPO.saveIt();
	}

	/**
	 * 更新车辆表里is_self_company字段为是 TODO description
	 * 
	 * @author yangjie
	 * @date 2017年2月3日
	 * @param vin
	 * @throws ServiceBizException
	 */
	public void updateTmVehicle(String vin) throws ServiceBizException {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		VehiclePO vehiclePO = VehiclePO.findByCompositeKeys(vin, dealerCode);
		if (vehiclePO != null) {
			vehiclePO.setInteger("IS_SELF_COMPANY", DictCodeConstants.IS_YES);
			vehiclePO.saveIt();
		}
	}

	/**
	 * 删除销售CR关怀 信息 TODO description
	 * 
	 * @author yangjie
	 * @date 2017年2月3日
	 * @param vin
	 * @throws ServiceBizException
	 */

	public void deleteCustomerCR(String vin) throws ServiceBizException {
		TtSalesCrPO po = TtSalesCrPO.findFirst("VIN = ? AND DEALER_CODE = ? AND CR_RESULT IS NULL", vin,
				FrameworkUtil.getLoginInfo().getDealerCode());
		po.delete();
	}

	/**
	 * 如果是已经配车的在途车 入库的时候需要将配车状态修改为已配车 TODO description
	 * 
	 * @author yangjie
	 * @date 2017年2月3日
	 * @param vin
	 * @throws ServiceBizException
	 */
	public void inWayVIN(String vin) throws ServiceBizException {
		String sql = "SELECT vin,dealer_code FROM TT_SALES_ORDER WHERE dealer_code='" + FrameworkUtil.getLoginInfo().getDealerCode()
				+ "' AND SO_STATUS IN(13011010,13011015,13011020,13011025)";
		List<Map> list = DAOUtil.findAll(sql, null);
		if (list.contains(vin)) {// 判断vin是否存在list中
			VsStockPO po = VsStockPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(), vin);
			po.setInteger("DISPATCHED_STATUS", DictCodeConstants.DICT_DISPATCHED_STATUS_HAVE_DISPATCHED);
			po.saveIt();
		}
	}

	/**
	 * 获取call订单 TODO description
	 * 
	 * @author yangjie
	 * @date 2017年2月3日
	 * @param esCustomerNo
	 * @return
	 * @throws ServiceBizException
	 */
	public List<Map> querySalesOrder(String esCustomerNo) throws ServiceBizException {
		StringBuffer sb = new StringBuffer(
				"SELECT a.dealer_code,a.so_no,a.vin, a.PRODUCT_CODE,b.brand_code, b.series_code,b.model_code FROM Tt_Sales_Order a LEFT JOIN ("
						+ CommonConstants.VM_VS_PRODUCT);
		sb.append(
				") b ON a.dealer_code=b.dealer_code AND  A.PRODUCT_CODE = B.PRODUCT_CODE AND A.D_KEY = B.D_KEY WHERE ");
		sb.append(" a.dealer_code='" + FrameworkUtil.getLoginInfo().getDealerCode() + "' AND a.D_KEY="
				+ CommonConstants.D_KEY + " AND a.EC_ORDER_NO='" + esCustomerNo + "'");
		sb.append(
				" AND a.DELIVERY_MODE_ELEC=16001002 AND a.SO_STATUS IN(13011015,13011020,13011025,13011050,13011040,13011045,13011050)  AND EC_ORDER="
						+ DictCodeConstants.IS_YES);
		List<Map> list = DAOUtil.findAll(sb.toString(), null);

		return list;
	}

	/**
	 * 判断中转监控车有没有超出总额 超出则返回0 else 1 limeng
	 * 
	 * @throws Exception
	 */

	public int queryIsSuperviseMore(String entityCode, String warrantynumber) throws ServiceBizException {
		int num;
		int isMore = 1;
		StringBuffer sql = new StringBuffer("");
		List list = new ArrayList();
		sql.append(" select sum1+sum2  as COUNT from "
				+ " (select count(a.vin) as sum1 from TT_VS_SHIPPING_NOTIFY a where a.dealer_code ='" + entityCode
				+ "' and " + " a.supervise_type=" + DictCodeConstants.DICT_SUPERVISE_VEHICLE_TRANSFER
				+ " and a.vin not in " + " (select b.vin from tm_vs_stock b  where b.dealer_code ='" + entityCode
				+ "' ))a1," + " (select count(c.vin) as sum2 from tm_vs_stock c where c.dealer_code ='" + entityCode
				+ "' and c.STOCK_STATUS=" + DictCodeConstants.DICT_STORAGE_STATUS_IN_STORAGE
				+ " and c.balance_date is null  and c.supervise_type="
				+ DictCodeConstants.DICT_SUPERVISE_VEHICLE_TRANSFER + ") a2");

		Map map = DAOUtil.findFirst(sql.toString(), null);
		num = (int) map.get("COUNT");
		if (num > Integer.parseInt(warrantynumber))
			isMore = 0;
		return isMore;
	}

	/********************************************************** 导入部分 ****************************************************************/

	/**
	 * 导入
	 * 
	 * @author yangjie
	 * @date 2017年2月6日
	 * @param dto
	 * @throws ServiceBizException
	 *             (non-Javadoc)
	 * @see com.yonyou.dms.vehicle.service.basedata.StockInService#addInfo(com.yonyou.dms.vehicle.domains.DTO.stockManage.StockInItemsDTO)
	 */
	@Override
	public void addInfo(StockInImportDTO dto) throws ServiceBizException {
		List<VsStockEntryItemPO> check = VsStockEntryItemPO.find("VIN = ?", dto.getVin());
		if (check.size() > 0) {
			throw new ServiceBizException("vin码是" + dto.getVin() + "入库单中已经存在,不能新增");
		} else {
			List<VsStockPO> find = VsStockPO.find("VIN = ?", dto.getVin());
			if (find.size() > 0) {
				throw new ServiceBizException("vin码是" + dto.getVin() + "车辆库存中已经存在,不能新增");
			} else {
				TtVsStockEntryPO spo = TtVsStockEntryPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),dto.getSe_no());
				TtVsStockEntryPO se = new TtVsStockEntryPO();
				if (StringUtils.isNullOrEmpty(spo)) {
					se.setString("DEALER_CODE", FrameworkUtil.getLoginInfo().getDealerCode());
					se.setString("SE_NO", dto.getSe_no());
					se.setInteger("STOCK_IN_TYPE", DictCodeConstants.DICT_STOCK_IN_TYPE_BUY_NEW_VEHICLE);
					se.setDate("SHEET_CREATE_DATE", new Date());
					se.setString("SHEET_CREATED_BY", FrameworkUtil.getLoginInfo().getUserId());
					se.setString("REMARK", "系统导入,自动新增");
					se.setInteger("D_KEY", CommonConstants.D_KEY);
					se.setInteger("IS_ALL_FINISHED", DictCodeConstants.IS_NOT);
					se.setString("OWNED_BY", FrameworkUtil.getLoginInfo().getUserId());
					se.saveIt();// 新增入库单
				}
				VsStockEntryItemPO itemPO = new VsStockEntryItemPO();
				StockInItemsDTO ite = new StockInItemsDTO();
				// 设置对象属性
				importToItem(dto, ite);
				dtoToPOAdd(ite, itemPO, DictCodeConstants.DICT_STOCK_IN_TYPE_BUY_NEW_VEHICLE);
				itemPO.saveIt();// 新增入库子表信息
			}
		}
		// //插入附件信息
		// Long billId = itemPO.getLongId();
		// //插入附件信息
		// fileStoreService.addFileUploadInfo(dto.getDmsFileIds(), billId,
		// DictCodeConstants.FILE_TYPE_CAR_INFO_VEHICLEIN);
	}

	/**
	 * 删除入库单信息
	 */
	@Override
	public void delDetailsInfo(String seNo, String vin) throws ServiceBizException {
		List<VsInspectionMarPO> find = VsInspectionMarPO.find("SE_NO = ? AND VIN = ?", seNo, vin);
		if (find.size() > 0) {
			for (VsInspectionMarPO vsInspectionMarPO : find) {
				vsInspectionMarPO.delete();// 删除质损信息
			}
		}
		VsStockEntryItemPO itemPo = VsStockEntryItemPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),
				seNo, vin);
		if (itemPo != null) {
			itemPo.delete();// 删除子表信息
		}
		List<VsStockEntryItemPO> list = VsStockEntryItemPO.find("SE_NO = ?", seNo);
		if (list.size() == 0) {// 代表子表没有数据,删除主表
			TtVsStockEntryPO po = TtVsStockEntryPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),
					seNo);
			if (po != null) {
				po.delete();
			}
		}
	}

	/**
	 * 查询仓库代码
	 * 
	 * @param storageName
	 * @return
	 */
	@Override
	public List<Map> findStorageCode() {
		String sql = "SELECT DEALER_CODE,STORAGE_CODE,STORAGE_NAME FROM TM_STORAGE";
		return DAOUtil.findAll(sql, null);
	}

	/**
	 * 将导入的dto存入保存的dto
	 * 
	 * @param imp
	 * @param ite
	 */
	public void importToItem(StockInImportDTO imp, StockInItemsDTO ite) {
		ite.setDischarge_standard(imp.getDischarge_standard());
		ite.setHas_certificate(imp.getHas_certificate());
		ite.setInspection_result(imp.getInspection_result());
		// po.setInteger("SUPERVISE_TYPE", imp.getSupervise_type());
		ite.setIs_finished(Integer.parseInt(DictCodeConstants.DICT_IS_NO));
		// po.setInteger("IS_DIRECT", imp.getIs_direct());
		// po.setInteger("IS_UPLOAD", imp.getIs_upload());
		ite.setMar_status(imp.getMar_status());
		ite.setOem_tag(imp.getOem_tag());
		// po.setInteger("INSPECTION_CONSIGNED", imp.getInspection_consigned());
		ite.setCertificate_number(imp.getCertificate_number());
		ite.setConsigner_code(imp.getConsigner_code());
		ite.setConsigner_name(imp.getConsigner_name());
		// po.setString("DELIVERY_TYPE", imp.getDelivery_type());
		ite.setDeliveryman_name(imp.getDeliveryman_name());
		ite.setDeliveryman_phone(imp.getDeliveryman_phone());
		ite.setEngine_no(imp.getEngine_no());
		ite.setExhaust_quantity(imp.getExhaust_quantity());
		// po.setString("FINANCIAL_BILL_NO", imp.getFinancial_bill_no());
		ite.setInspector(imp.getInspector());
		ite.setKey_number(imp.getKey_number());
		ite.setPo_no(imp.getPo_no());
		ite.setProduct_code(imp.getProduct_code());
		ite.setProducting_area(imp.getProducting_area());
		ite.setRemark(imp.getRemark());
		ite.setSe_no(imp.getSe_no());
		ite.setShipper_license(imp.getShipper_license());
		ite.setShipper_name(imp.getShipper_name());
		ite.setShipping_address(imp.getShipping_address());
		ite.setShipping_order_no(imp.getShipping_order_no());
		ite.setStorage_code(imp.getStorage_code());
		ite.setStorage_position_code(imp.getStorage_position_code());
		ite.setVendor_code(imp.getVendor_code());
		ite.setVendor_name(imp.getVendor_name());
		ite.setVin(imp.getVin());
		ite.setVsn(imp.getVsn());
		// po.setString("COLOR_CODE", imp.getColor_code());
		ite.setModel_year(imp.getModel_year());
		ite.setAdditional_cost(imp.getAdditional_cost());
		ite.setPurchase_price(imp.getPurchase_price());
		if (imp.getArrived_date() != null) {
			ite.setArrived_date(imp.getArrived_date());
		}
		if (imp.getArriving_date() != null) {
			ite.setArriving_date(imp.getArriving_date());
		}
		if (imp.getFactory_date() != null) {
			ite.setFactory_date(imp.getFactory_date());
		}
		// po.setDate("FINISHED_DATE", imp.getFinished_date());
		if (imp.getInspection_date() != null) {
			ite.setInspection_date(imp.getInspection_date());
		}
		if (imp.getManufacture_date() != null) {
			ite.setManufacture_date(imp.getManufacture_date());
		}
		if (imp.getShipping_date() != null) {
			ite.setShipping_date(imp.getShipping_date());
		}
		// po.setDate("SUBMIT_TIME", imp.getSubmit_time());
		if (imp.getVs_purchase_date() != null) {
			ite.setVs_purchase_date(imp.getVs_purchase_date());
		}
	}

	/**
	 * 批量验收用查询入库子表
	 */
	@Override
	public PageInfoDto findAllDetailsForInspect(Map<String, String> queryParam) {
		StringBuilder sb = new StringBuilder("SELECT A.DEALER_CODE," + DictCodeConstants.IS_NOT
				+ " AS IS_SELECTED,A.VS_PURCHASE_DATE,A.IS_DIRECT,A.SE_NO,A.VIN,A.PRODUCT_CODE,A.STORAGE_CODE,C.STORAGE_NAME,A.STORAGE_POSITION_CODE,A.ENGINE_NO,A.KEY_NUMBER,");
		sb.append(
				" A.HAS_CERTIFICATE,A.CERTIFICATE_NUMBER,A.MANUFACTURE_DATE, A.FACTORY_DATE,A.PO_NO,A.VENDOR_CODE,A.VENDOR_NAME,A.PURCHASE_PRICE,A.ADDITIONAL_COST,A.CONSIGNER_CODE,A.CONSIGNER_NAME,A.SHIPPING_DATE,");
		sb.append(
				"A.SHIPPER_LICENSE,A.SHIPPER_NAME, A.DELIVERYMAN_NAME,A.DELIVERYMAN_PHONE,A.ARRIVING_DATE,A.SHIPPING_ORDER_NO,");
		sb.append(
				"A.SHIPPING_ADDRESS,A.ARRIVED_DATE,A.INSPECTION_RESULT,A.INSPECTOR,A.INSPECTION_DATE,A.MAR_STATUS, A.PRODUCTING_AREA,");
		sb.append(
				"A.OEM_TAG,A.IS_UPLOAD,A.SUBMIT_TIME,A.REMARK,A.IS_FINISHED,A.FINISHED_DATE,A.FINISHED_BY,A.INSPECTION_CONSIGNED,");
		sb.append(
				"A.EXHAUST_QUANTITY,A.VSN,A.DISCHARGE_STANDARD,a.FINANCIAL_BILL_NO,a.SUPERVISE_TYPE,a.MODEL_YEAR,COALESCE(A.COLOR_CODE,B.COLOR_CODE) AS COLOR_CODE,COALESCE(D.COLOR_NAME,E.COLOR_NAME) AS COLOR_NAME  ");
		sb.append(" FROM TT_VS_STOCK_ENTRY_ITEM A LEFT JOIN (" + CommonConstants.VM_VS_PRODUCT);
		sb.append(
				") B ON A.DEALER_CODE=B.DEALER_CODE AND A.D_KEY=B.D_KEY AND A.PRODUCT_CODE=B.PRODUCT_CODE LEFT JOIN TM_STORAGE C ON A.STORAGE_CODE=C.STORAGE_CODE LEFT JOIN TM_COLOR D ON A.COLOR_CODE=D.COLOR_CODE LEFT JOIN TM_COLOR E ON B.COLOR_CODE=E.COLOR_CODE ");
		sb.append(" WHERE A.DEALER_CODE='" + FrameworkUtil.getLoginInfo().getDealerCode());
		sb.append("' AND A.D_KEY=" + CommonConstants.D_KEY);
		if (!StringUtils.isNullOrEmpty(queryParam.get("seNo"))) {
			sb.append(" AND A.SE_NO = '" + queryParam.get("seNo") + "'");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
			sb.append(" AND A.VIN like '%" + queryParam.get("vin") + "%'");
		}
		System.err.println(sb.toString());
		return DAOUtil.pageQuery(sb.toString(), null);
	}

	/**
	 * 批量验收
	 */
	@Override
	public void btnAllInspect(Map<String, String> map) throws ServiceBizException {
		String[] vin = map.get("vins").split(";");
		for (int i = 0; i < vin.length; i++) {
			VsStockEntryItemPO vei = VsStockEntryItemPO
					.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(), map.get("seNo"), vin[i]);
			vei.setString("INSPECTOR", FrameworkUtil.getLoginInfo().getUserId());
			vei.setInteger("MAR_STATUS", map.get("trafficMarStatus"));
			vei.setInteger("INSPECTION_RESULT", map.get("inspectionResult"));
			vei.setDate("INSPECTION_DATE", new Date());
			vei.saveIt();// 保存验收信息
		}
	}

	@Override
	public List<Map> queryStockInDetailsForBatch(Map<String, String> queryParam) {
		StringBuffer sb = new StringBuffer("SELECT '" + queryParam.get("sNo") + "' AS sNo,'" + queryParam.get("inType")
				+ "' AS inType,'" + queryParam.get("createdBy") + "' AS createdBy,A.DEALER_CODE,"
				+ DictCodeConstants.IS_NOT
				+ " AS IS_SELECTED,A.VS_PURCHASE_DATE,A.IS_DIRECT,A.SE_NO,A.VIN,A.PRODUCT_CODE,A.STORAGE_CODE AS A_S_C,C.STORAGE_NAME AS STORAGE_CODE,A.STORAGE_POSITION_CODE,A.ENGINE_NO,A.KEY_NUMBER,");
		sb.append(
				" A.HAS_CERTIFICATE,A.CERTIFICATE_NUMBER,A.MANUFACTURE_DATE, A.FACTORY_DATE,A.PO_NO,A.VENDOR_CODE,A.VENDOR_NAME,A.PURCHASE_PRICE,A.ADDITIONAL_COST,A.CONSIGNER_CODE,A.CONSIGNER_NAME,A.SHIPPING_DATE,");
		sb.append(
				"A.SHIPPER_LICENSE,A.SHIPPER_NAME, A.DELIVERYMAN_NAME,A.DELIVERYMAN_PHONE,A.ARRIVING_DATE,A.SHIPPING_ORDER_NO,");
		sb.append(
				"A.SHIPPING_ADDRESS,A.ARRIVED_DATE,A.INSPECTION_RESULT,A.INSPECTOR,A.INSPECTION_DATE,A.MAR_STATUS, A.PRODUCTING_AREA,");
		sb.append(
				"A.OEM_TAG,A.IS_UPLOAD,A.SUBMIT_TIME,A.REMARK,A.IS_FINISHED,A.FINISHED_DATE,A.FINISHED_BY,A.INSPECTION_CONSIGNED,");
		sb.append(
				"A.EXHAUST_QUANTITY,A.VSN,A.DISCHARGE_STANDARD,a.FINANCIAL_BILL_NO,a.SUPERVISE_TYPE,a.MODEL_YEAR,COALESCE(A.COLOR_CODE,B.COLOR_CODE) AS COLOR_CODE,COALESCE(D.COLOR_NAME,E.COLOR_NAME) AS COLOR_NAME  ");
		sb.append(" FROM TT_VS_STOCK_ENTRY_ITEM A LEFT JOIN (" + CommonConstants.VM_VS_PRODUCT);
		sb.append(
				") B ON A.DEALER_CODE=B.DEALER_CODE AND A.D_KEY=B.D_KEY AND A.PRODUCT_CODE=B.PRODUCT_CODE LEFT JOIN TM_STORAGE C ON A.STORAGE_CODE=C.STORAGE_CODE LEFT JOIN TM_COLOR D ON A.COLOR_CODE=D.COLOR_CODE LEFT JOIN TM_COLOR E ON B.COLOR_CODE=E.COLOR_CODE ");
		sb.append(" WHERE A.DEALER_CODE='" + FrameworkUtil.getLoginInfo().getDealerCode());
		sb.append("' AND A.D_KEY=" + CommonConstants.D_KEY);
		if (!StringUtils.isNullOrEmpty(queryParam.get("sNo"))) {
			sb.append(" AND A.SE_NO = '" + queryParam.get("sNo") + "' ");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
			sb.append(" AND A.VIN in (" + queryParam.get("vin") + ") ");
		}
		List<Map> info = DAOUtil.findAll(sb.toString(), null);
		return info;
	}

	@Override
	public List<Map> findPrintAbout(String seNo, String vins) {
		StringBuffer sb = new StringBuffer(
				" SELECT DISTINCT A.PURCHASE_PRICE,1 AS AMOUNT, A.VIN  ,A.PRODUCT_CODE,A.PRODUCT_NAME,a.DEALER_CODE,G.storage_NAME,a.finished_date,");
		sb.append("a.vendor_name,C.MODEL_NAME,D.SERIES_NAME,E.BRAND_NAME,F.COLOR_NAME ");
		sb.append(
				"FROM (SELECT A.*,B.MODEL_CODE,B.SERIES_CODE,B.BRAND_CODE,B.PRODUCT_NAME FROM TT_VS_STOCK_ENTRY_ITEM A ");
		sb.append("LEFT JOIN (" + CommonConstants.VM_VS_PRODUCT
				+ ") B ON A.DEALER_CODE=B.DEALER_CODE AND A.PRODUCT_CODE=B.PRODUCT_CODE ) A ");
		sb.append("LEFT JOIN (" + CommonConstants.VM_MODEL
				+ ") C ON A.DEALER_CODE=C.DEALER_CODE AND A.MODEL_CODE=C.MODEL_CODE ");
		sb.append("LEFT JOIN (" + CommonConstants.VM_SERIES
				+ ") D ON A.DEALER_CODE=D.DEALER_CODE AND A.SERIES_CODE=D.SERIES_CODE ");
		sb.append("LEFT JOIN (" + CommonConstants.VM_BRAND
				+ ")  E ON A.DEALER_CODE =E.DEALER_CODE AND A.BRAND_CODE=E.BRAND_CODE  ");
		sb.append("LEFT JOIN (" + CommonConstants.VM_COLOR
				+ ") F ON A.DEALER_CODE=F.DEALER_CODE AND A.COLOR_CODE=F.COLOR_CODE  ");
		sb.append("LEFT JOIN (" + CommonConstants.VM_STORAGE
				+ ") G ON A.DEALER_CODE=G.DEALER_CODE AND A.STORAGE_CODE=G.STORAGE_CODE WHERE  1=1 ");
		if (!StringUtils.isNullOrEmpty(seNo)) {
			sb.append(" AND A.SE_NO = '" + seNo + "'");
		}
		if (!StringUtils.isNullOrEmpty(vins)) {
			sb.append(" AND A.VIN IN (" + vins + ") ");
		}
		return DAOUtil.findAll(sb.toString(), null);
	}

	/**
	 * 查询排放标准
	 */
	@Override
	public List<Map> findDischargeStandard() {
		String sql = "SELECT CODE_ID,CODE_CN_DESC FROM TC_CODE WHERE STATUS = '12781001' AND TYPE = "
				+ DictCodeConstants.DISCHARGE_STANDARD;
		return Base.findAll(sql);
	}

	@Override
	public String checkPDI(String vin, String productCode) throws ServiceBizException {
		List<Map> items = new ArrayList<Map>();
		Map map = new HashMap();
		String[] split = vin.split(",");
		String[] split2 = productCode.split(",");
		for (int i = 0; i < split2.length; i++) {
			map.put("VIN", split[i]);
			map.put("PRODUCT_CODE", split2[i]);
			items.add(map);
		}
		String pdi = checkPDI(items);
		if (!StringUtils.isNullOrEmpty(pdi)) {
			String[] strings = pdi.split(",");
			if (strings.length > 1) {
				return pdi;
			} else if (strings.length == 1) {
				return strings[0];
			} else {
				return "";
			}
		} else {
			return "";
		}
	}

	@Override
	public String findProductCode(String vin, String prodectCode) {
		String sql = "SELECT DEALER_CODE,PRODUCT_CODE FROM TT_VS_STOCK_ENTRY_ITEM WHERE VIN = ? AND SE_NO = ?";
		List queryParam = new ArrayList();
		queryParam.add(vin);
		queryParam.add(prodectCode);
		List<Map> findAll = DAOUtil.findAll(sql, queryParam);
		if (findAll.size() > 0) {
			Map map = findAll.get(0);
			return map.get("PRODUCT_CODE").toString();
		} else {
			return "";
		}
	}
}
