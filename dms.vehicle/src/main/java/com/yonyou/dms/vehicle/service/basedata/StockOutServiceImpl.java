
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.vehicle
 *
 * @File name : StockOutServiceImpl.java
 *
 * @Author : DuPengXin
 *
 * @Date : 2016年9月21日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年9月21日    DuPengXin   1.0
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.CustomerPO;
import com.yonyou.dms.common.domains.PO.basedata.PotentialCusPO;
import com.yonyou.dms.common.domains.PO.basedata.RepairOrderPO;
import com.yonyou.dms.common.domains.PO.basedata.SoInvoicePO;
import com.yonyou.dms.common.domains.PO.basedata.TmOwnerSubclassPO;
import com.yonyou.dms.common.domains.PO.basedata.TmVehicleSubclassPO;
import com.yonyou.dms.common.domains.PO.basedata.TtAccountsTransFlowPO;
import com.yonyou.dms.common.domains.PO.basedata.TtCusIntentPO;
import com.yonyou.dms.common.domains.PO.basedata.TtCustomerLinkmanInfoPO;
import com.yonyou.dms.common.domains.PO.basedata.TtOrderStatusUpdatePO;
import com.yonyou.dms.common.domains.PO.basedata.TtPoCusRelationPO;
import com.yonyou.dms.common.domains.PO.basedata.TtSalesCrPO;
import com.yonyou.dms.common.domains.PO.basedata.TtSalesPartPO;
import com.yonyou.dms.common.domains.PO.basedata.TtSalesPromotionPlanPO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsDeliveryItemPO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsStockDeliveryPO;
import com.yonyou.dms.common.domains.PO.basedata.VsProductPO;
import com.yonyou.dms.common.domains.PO.basedata.VsStockLogPO;
import com.yonyou.dms.common.domains.PO.basedata.VsStockPO;
import com.yonyou.dms.common.domains.PO.stockmanage.CarownerPO;
import com.yonyou.dms.common.domains.PO.stockmanage.VehiclePO;
import com.yonyou.dms.commonSales.domains.PO.stockmanage.SalesOrderPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.CommonNoService;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.vehicle.domains.DTO.basedata.StockOutListDTO;

/**
 * 整车出库实现类
 * 
 * @author DuPengXin
 * @date 2016年9月21日
 */
@Service
@SuppressWarnings({ "rawtypes", "unchecked", "static-access", "unused" })
public class StockOutServiceImpl implements StockOutService {

	@Autowired
	private CommonNoService commonNoService;

	@Override
	public PageInfoDto findAllOutItems(Map<String, String> queryParam) {
		StringBuffer sb = new StringBuffer();
		sb.append(
				"SELECT A.DEALER_CODE,B.VIN,B.PRODUCT_CODE,A.SD_NO,A.STOCK_OUT_TYPE,A.SHEET_CREATE_DATE,tu.USER_NAME as SHEET_CREATE_BY,A.IS_ALL_FINISHED,A.REMARK ");
		sb.append(
				" FROM TT_VS_STOCK_DELIVERY A LEFT JOIN TM_USER tu ON A.SHEET_CREATE_BY = tu.USER_ID AND A.DEALER_CODE = tu.DEALER_CODE LEFT JOIN TT_VS_DELIVERY_ITEM B ON A.DEALER_CODE=B.DEALER_CODE AND A.D_KEY=B.D_KEY AND A.SD_NO=B.SD_NO WHERE A.DEALER_CODE='"
						+ FrameworkUtil.getLoginInfo().getDealerCode());
		sb.append("' AND A.D_KEY=" + CommonConstants.D_KEY + " ");
		Utility.sqlToLike(sb, queryParam.get("SdNo"), "SD_NO", "A");
		Utility.sqlToEquals(sb, queryParam.get("outType"), "STOCK_OUT_TYPE", "A");
		Utility.sqlToDate(sb, queryParam.get("begin"), queryParam.get("end"), "SHEET_CREATE_DATE", "A");
		Utility.sqlToLike(sb, queryParam.get("vin"), "VIN", "B");
		if (!StringUtils.isNullOrEmpty(queryParam.get("IS_ALL_FINISHED"))) {
			sb.append(" AND A.IS_ALL_FINISHED = " + Integer.parseInt(queryParam.get("IS_ALL_FINISHED")));
		}
		sb.append(" ORDER BY A.IS_ALL_FINISHED DESC");
		return DAOUtil.pageQuery(sb.toString(), null);
	}

	@Override
	public PageInfoDto findAllVehicle(Map<String, String> queryParam) {
		StringBuffer sb = new StringBuffer();
		if ((DictCodeConstants.DICT_STOCK_OUT_TYPE_UNTREAD + "").equals(queryParam.get("outType"))) {
			sb = outTypeByReturn(queryParam);
		} else if ((DictCodeConstants.DICT_STOCK_OUT_TYPE_SALE + "").equals(queryParam.get("outType"))) {
			System.err.println(123);
			sb = outTypeBySales(queryParam);
		} else {
			return new PageInfoDto();
		}
		return DAOUtil.pageQuery(sb.toString(), null);
	}

	/**
	 * 采购退回入库SQL语句 TODO description
	 * 
	 * @author yangjie
	 * @date 2017年2月7日
	 * @return
	 */
	public StringBuffer outTypeByReturn(Map<String, String> queryParam) {
		StringBuffer sb = new StringBuffer();
		sb.append(
				"SELECT A.STOCK_STATUS,A.DEALER_CODE,12781002 IS_SELECTED,A.VIN,A.PRODUCT_CODE,A.STORAGE_CODE,B.STORAGE_NAME,A.STORAGE_POSITION_CODE,A.ENGINE_NO,A.KEY_NUMBER,A.PURCHASE_PRICE, ");
		sb.append(
				"A.ADDITIONAL_COST,A.DISPATCHED_STATUS,A.MAR_STATUS,A.IS_SECONDHAND,A.IS_VIP,A.IS_TEST_DRIVE_CAR,A.IS_CONSIGNED, ");
		sb.append(
				"A.IS_PROMOTION,A.IS_PURCHASE_RETURN,A.IS_PRICE_ADJUSTED,A.ADJUST_REASON,A.OLD_DIRECTIVE_PRICE,A.DIRECTIVE_PRICE,A.OEM_TAG,");
		sb.append(
				"A.REMARK,A.SO_NO FROM TM_VS_STOCK A LEFT JOIN TM_STORAGE B ON A.DEALER_CODE=B.DEALER_CODE AND A.STORAGE_CODE=B.STORAGE_CODE WHERE A.DEALER_CODE='"
						+ FrameworkUtil.getLoginInfo().getDealerCode());
		sb.append("' AND A.D_KEY=" + CommonConstants.D_KEY);
		sb.append(" AND A.IS_VIP=" + DictCodeConstants.IS_NOT);
		sb.append(" AND A.IS_CONSIGNED=" + DictCodeConstants.IS_NOT);
		sb.append(" AND A.STOCK_STATUS=" + DictCodeConstants.DICT_STORAGE_STATUS_IN_STORAGE);
		sb.append(" AND A.DISPATCHED_STATUS=" + DictCodeConstants.DICT_DISPATCHED_STATUS_NOT_DISPATCHED);
		sb.append(" AND A.STOCK_STATUS<>" + DictCodeConstants.DICT_STORAGE_STATUS_MOVE_OUT);
		sb.append(" AND A.VIN NOT IN(SELECT VIN FROM TT_VS_DELIVERY_ITEM WHERE DEALER_CODE='"
				+ FrameworkUtil.getLoginInfo().getDealerCode());
		sb.append("' AND D_KEY=" + CommonConstants.D_KEY);
		sb.append(" AND IS_FINISHED=" + DictCodeConstants.IS_NOT + ") ");
		Utility.sqlToLike(sb, queryParam.get("vin"), "VIN", "A");
		if (!StringUtils.isNullOrEmpty(queryParam.get("outType"))
				&& (DictCodeConstants.DICT_STOCK_OUT_TYPE_UNTREAD + "").equals(queryParam.get("outType"))) {
			sb.append(" AND A.OEM_TAG = " + DictCodeConstants.IS_YES + " ");
		}
		Utility.sqlToEquals(sb, queryParam.get("storageCode"), "STORAGE_CODE", "A");
		Utility.sqlToEquals(sb, queryParam.get("productCode"), "PRODUCT_CODE", "A");
		return sb;
	}

	/**
	 * 销售退回出库SQL语句 TODO description
	 * 
	 * @author yangjie
	 * @date 2017年2月7日
	 * @param queryParam
	 * @return
	 */
	public StringBuffer outTypeBySales(Map<String, String> queryParam) {
		StringBuffer sb = new StringBuffer();
		sb.append(
				"SELECT  A.DEALER_CODE,12781002 AS IS_SELECTED,A.VIN,A.PRODUCT_CODE,A.STORAGE_CODE,C.STORAGE_NAME,A.STORAGE_POSITION_CODE,B.PURCHASE_PRICE,");
		sb.append("B.ADDITIONAL_COST,B.STOCK_STATUS,B.DISPATCHED_STATUS, A.SO_NO,MAR_STATUS,B.IS_SECONDHAND,");
		sb.append("B.IS_VIP,IS_TEST_DRIVE_CAR,B.IS_CONSIGNED,B.IS_PROMOTION,B.IS_PURCHASE_RETURN,B.IS_PRICE_ADJUSTED,");
		sb.append("B.ADJUST_REASON,B.OLD_DIRECTIVE_PRICE, A.DIRECTIVE_PRICE,A.OEM_TAG,B.REMARK,A.VEHICLE_PRICE ");
		sb.append("from TT_SALES_ORDER  A LEFT JOIN  TM_VS_STOCK  B ON ");
		sb.append(
				" A.DEALER_CODE=B.DEALER_CODE AND A.D_KEY=B.D_KEY AND A.VIN=B.VIN LEFT JOIN TM_STORAGE C ON A.DEALER_CODE=C.DEALER_CODE AND A.STORAGE_CODE=C.STORAGE_CODE WHERE A.VIN NOT IN(SELECT VIN FROM TT_VS_DELIVERY_ITEM WHERE IS_FINISHED="
						+ DictCodeConstants.IS_NOT);
		sb.append("  AND DEALER_CODE='" + FrameworkUtil.getLoginInfo().getDealerCode());
		sb.append("' AND D_KEY=" + CommonConstants.D_KEY);
		sb.append(")  AND A.DEALER_CODE='" + FrameworkUtil.getLoginInfo().getDealerCode());
		sb.append("' AND A.D_KEY=" + CommonConstants.D_KEY);
		sb.append(" AND A.SO_STATUS=" + DictCodeConstants.DICT_SO_STATUS_HAVE_CONFIRMED);
		sb.append(" AND B.DISPATCHED_STATUS=" + DictCodeConstants.DICT_DISPATCHED_STATUS_DELIVERY_CONFIRM);
		sb.append(" AND B.STOCK_STATUS<>" + DictCodeConstants.DICT_STORAGE_STATUS_MOVE_OUT);
		sb.append(" AND B.STOCK_STATUS<>" + DictCodeConstants.DICT_STORAGE_STATUS_LEND_TO + " ");
		if (!StringUtils.isNullOrEmpty(queryParam.get("outType"))
				&& (DictCodeConstants.DICT_STOCK_OUT_TYPE_SALE + "").equals(queryParam.get("outType"))) {
			sb.append(" AND A.BUSINESS_TYPE = " + DictCodeConstants.DICT_SO_TYPE_GENERAL + " ");
		}
		Utility.sqlToLike(sb, queryParam.get("vin"), "VIN", "B");
		Utility.sqlToEquals(sb, queryParam.get("storageCode"), "STORAGE_CODE", "B");
		Utility.sqlToLike(sb, queryParam.get("productCode"), "PRODUCT_CODE", "B");
		return sb;
	}

	/**
	 * 保存出库信息
	 * 
	 * @author yangjie
	 * @date 2017年2月8日
	 * @param outListDTO
	 * @throws ServiceBizException
	 *             (non-Javadoc)
	 * @see com.yonyou.dms.vehicle.service.basedata.StockOutService#btnSave(com.yonyou.dms.vehicle.domains.DTO.basedata.StockOutListDTO)
	 */
	@Override
	public String btnSave(StockOutListDTO outListDTO) throws ServiceBizException {
		String code = FrameworkUtil.getLoginInfo().getDealerCode();
		String userId = FrameworkUtil.getLoginInfo().getUserId().toString();
		String no = "";// 获取出库单号
		List<String> vinss = findNotInDataBaseVIN(outListDTO);// 不在前台中的VIN值做删除操作
		// 先判断属于新增还是修改
		if (StringUtils.isNullOrEmpty(outListDTO.getSdNo())) {
			// 新增主表
			String sdNo = commonNoService.getSystemOrderNo(CommonConstants.SD_NO);// 生成出库单号
			TtVsStockDeliveryPO deliveryPO = new TtVsStockDeliveryPO();
			deliveryPO.setString("DEALER_CODE", code);
			deliveryPO.setString("SD_NO", sdNo);
			deliveryPO.setString("STOCK_OUT_TYPE", outListDTO.getOutType());
			deliveryPO.setDate("SHEET_CREATE_DATE", new Date());
			deliveryPO.setString("SHEET_CREATE_BY", userId);
			deliveryPO.setInteger("IS_ALL_FINISHED", DictCodeConstants.IS_NOT);
			deliveryPO.setString("REMARK", "");
			deliveryPO.saveIt();

			// 同步更新车辆的省市县
			for (Map map : outListDTO.getDms_table()) {
				if (!StringUtils.isNullOrEmpty(map.get("SO_NO"))) {
					SalesOrderPO orderPO = SalesOrderPO.findByCompositeKeys(code, map.get("SO_NO").toString());
					if (orderPO != null && orderPO.getInteger("D_KEY") == CommonConstants.D_KEY) {
						VehiclePO vehiclePO = VehiclePO.findByCompositeKeys(map.get("VIN").toString(), code);
						if (vehiclePO != null) {
							if (!StringUtils.isNullOrEmpty(orderPO.getString("PROVINCE"))) {
								vehiclePO.setString("PROVINCE", orderPO.getString("PROVINCE"));
							}
							if (!StringUtils.isNullOrEmpty(orderPO.getString("CITY"))) {
								vehiclePO.setString("CITY", orderPO.getString("CITY"));
							}
							if (!StringUtils.isNullOrEmpty(orderPO.getString("DISTRICT"))) {
								vehiclePO.setString("DISTRICT", orderPO.getString("DISTRICT"));
							}
							if (!StringUtils.isNullOrEmpty(orderPO.getString("PROVINCE"))
									&& !StringUtils.isNullOrEmpty(orderPO.getString("CITY"))
									&& !StringUtils.isNullOrEmpty(orderPO.getString("DISTRICT"))) {
								vehiclePO.saveIt();
							}
						}
					}
				}
				// 新增子表
				addOrUpdateOutItems(map, sdNo, 0);

				// 删除
				if(vinss.size()>0){
					for (String str : vinss) {
						String sql = "SELECT * FROM TT_VS_DELIVERY_ITEM WHERE SD_NO = ? AND DEALER_CODE = ? AND VIN = ?";
						List queryParam = new ArrayList();
						queryParam.add(outListDTO.getSdNo());
						queryParam.add(FrameworkUtil.getLoginInfo().getDealerCode());
						queryParam.add(str);
						List<Map> list = DAOUtil.findAll(sql, queryParam);
						if(list.size()>0){
							for (Map map2 : list) {
								deleteOutItems(map2);
							}
						}
					}
				}
			}

			no = sdNo;
		} else {
			// 修改主表
			TtVsStockDeliveryPO deliveryPO = TtVsStockDeliveryPO.findByCompositeKeys(code, outListDTO.getSdNo());
			deliveryPO.setString("OWNED_BY", userId);
			deliveryPO.setDate("SHEET_CREATE_DATE", new Date());
			deliveryPO.setString("SHEET_CREATE_BY", userId);
			deliveryPO.setString("STOCK_OUT_TYPE", outListDTO.getOutType());
			Boolean flag = true;
			List<Map> table = outListDTO.getDms_table();
			for (Map map2 : table) {
				if(!StringUtils.isNullOrEmpty(map2.get("IS_FINISHED"))&&Long.parseLong(map2.get("IS_FINISHED").toString())==DictCodeConstants.IS_YES){
				}else{
					flag = false;
				}
			}
			if(flag){//表示全部入账
				deliveryPO.setInteger("IS_ALL_FINISHED", DictCodeConstants.IS_YES);
			}else{
				deliveryPO.setInteger("IS_ALL_FINISHED", DictCodeConstants.IS_NOT);
			}
			deliveryPO.saveIt();

			int index = 0;
			// 判断出库单子表中是否已经存在此VIN值，如果存在做修改处理,如果不存在做新增处理
			for (Map map : outListDTO.getDms_table()) {
				if (map.get("IS_FINISHED") != null) {
					if (DictCodeConstants.IS_YES == Long.parseLong(map.get("IS_FINISHED").toString())) {
						index += 1;
					}
				} else {
					if (map != null) {
						List<Map> vinList = searchVINList(outListDTO.getSdNo());// 查询数据库中出库单明细
						if(vinList.size()>0){
							for (Map map2 : vinList) {
								if (map2.get("VIN").equals(map.get("VIN"))) {// 判断数据库中是否存在此VIN值
									// -存在
									// 修改子表
									addOrUpdateOutItems(map, outListDTO.getSdNo(), 1);
								}
							}
						}else{
							// 新增子表
							addOrUpdateOutItems(map, outListDTO.getSdNo(), 0);
						}
					}
				}

				// 删除
				if(vinss.size()>0){
					for (String str : vinss) {
						String sql = "SELECT * FROM TT_VS_DELIVERY_ITEM WHERE SD_NO = ? AND DEALER_CODE = ? AND VIN = ?";
						List queryParam = new ArrayList();
						queryParam.add(outListDTO.getSdNo());
						queryParam.add(FrameworkUtil.getLoginInfo().getDealerCode());
						queryParam.add(str);
						List<Map> list = DAOUtil.findAll(sql, queryParam);
						if(list.size()>0){
							for (Map map2 : list) {
								deleteOutItems(map2);
							}
						}
					}
				}
			}
			if (index == outListDTO.getDms_table().size()) {// 表示全部入账
				deliveryPO.setLong("IS_ALL_FINISHED", DictCodeConstants.IS_YES);
				deliveryPO.saveIt();
			}

			no = outListDTO.getSdNo();
		}

		return no;
	}

	/**
	 * 新增子表方法 TODO description
	 * 
	 * @author yangjie
	 * @date 2017年2月8日
	 * @param outListDTO
	 * @param no
	 * @param index
	 *            0 新增, 1 修改
	 */
	public void addOrUpdateOutItems(Map map, String no, int index) throws ServiceBizException {
		if (map.get("STORAGE_NAME") != null) {
			String sql = "SELECT DEALER_CODE,STORAGE_CODE FROM TM_STORAGE WHERE STORAGE_NAME = '"
					+ map.get("STORAGE_NAME").toString().trim() + "'";
			Map findStorage = DAOUtil.findAll(sql, null).get(0);
			map.put("STORAGE_CODE", findStorage.get("STORAGE_CODE"));// 通过仓库名称获取仓库代码
		}
		if (index == 0) {// 新增
			TtVsDeliveryItemPO itemPO = new TtVsDeliveryItemPO();
			itemPO.setString("DEALER_CODE", FrameworkUtil.getLoginInfo().getDealerCode());
			itemPO.setLong("OWNED_BY", FrameworkUtil.getLoginInfo().getUserId());
			if (!StringUtils.isNullOrEmpty(no)) {
				itemPO.setString("SD_NO", no);
			}
			if (!StringUtils.isNullOrEmpty(map.get("VIN"))) {
				itemPO.setString("VIN", map.get("VIN").toString());
			}
			if (!StringUtils.isNullOrEmpty(map.get("PRODUCT_CODE"))) {
				itemPO.setString("PRODUCT_CODE", map.get("PRODUCT_CODE").toString());
			}
			if (!StringUtils.isNullOrEmpty(map.get("STORAGE_CODE"))) {
				System.err.println(map.get("STORAGE_CODE").toString());
				itemPO.setString("STORAGE_CODE", map.get("STORAGE_CODE").toString());
			}
			if (!StringUtils.isNullOrEmpty(map.get("STORAGE_POSITION_CODE"))) {
				itemPO.setString("STORAGE_POSITION_CODE", map.get("STORAGE_POSITION_CODE").toString());
			}
			if (!StringUtils.isNullOrEmpty(map.get("PURCHASE_PRICE"))) {
				itemPO.setDouble("PURCHASE_PRICE", map.get("PURCHASE_PRICE"));
			}
			if (!StringUtils.isNullOrEmpty(map.get("ADDITIONAL_COST"))) {
				itemPO.setDouble("ADDITIONAL_COST", map.get("ADDITIONAL_COST"));
			}
			if (!StringUtils.isNullOrEmpty(map.get("VEHICLE_PRICE"))) {
				itemPO.setDouble("VEHICLE_PRICE", map.get("VEHICLE_PRICE"));
			}
			itemPO.setInteger("STOCK_STATUS", DictCodeConstants.DICT_STORAGE_STATUS_IN_STORAGE);
			if (!StringUtils.isNullOrEmpty(map.get("DISPATCHED_STATUS"))) {
				itemPO.setInteger("DISPATCHED_STATUS", map.get("DISPATCHED_STATUS"));
			}
			if (!StringUtils.isNullOrEmpty(map.get("SO_NO"))) {
				itemPO.setString("SO_NO", map.get("SO_NO").toString());
			}
			if (!StringUtils.isNullOrEmpty(map.get("MAR_STATUS"))) {
				itemPO.setInteger("MAR_STATUS", map.get("MAR_STATUS"));
			}
			if (!StringUtils.isNullOrEmpty(map.get("IS_SECONDHAND"))) {
				itemPO.setInteger("IS_SECONDHAND", map.get("IS_SECONDHAND"));
			}
			if (!StringUtils.isNullOrEmpty(map.get("IS_VIP"))) {
				itemPO.setInteger("IS_VIP", map.get("IS_VIP"));
			}
			if (!StringUtils.isNullOrEmpty(map.get("IS_TEST_DRIVE_CAR"))) {
				itemPO.setInteger("IS_TEST_DRIVE_CAR", map.get("IS_TEST_DRIVE_CAR"));
			}
			if (!StringUtils.isNullOrEmpty(map.get("IS_CONSIGNED"))) {
				itemPO.setInteger("IS_CONSIGNED", map.get("IS_CONSIGNED"));
			}
			if (!StringUtils.isNullOrEmpty(map.get("IS_PROMOTION"))) {
				itemPO.setInteger("IS_PROMOTION", map.get("IS_PROMOTION"));
			}
			if (!StringUtils.isNullOrEmpty(map.get("IS_PURCHASE_RETURN"))) {
				itemPO.setInteger("IS_PURCHASE_RETURN", map.get("IS_PURCHASE_RETURN"));
			}
			if (!StringUtils.isNullOrEmpty(map.get("IS_PRICE_ADJUSTED"))) {
				itemPO.setInteger("IS_PRICE_ADJUSTED", map.get("IS_PRICE_ADJUSTED"));
			}
			if (!StringUtils.isNullOrEmpty(map.get("ADJUST_REASON"))) {
				itemPO.setString("ADJUST_REASON", map.get("ADJUST_REASON").toString());
			}
			if (!StringUtils.isNullOrEmpty(map.get("OLD_DIRECTIVE_PRICE"))) {
				itemPO.setDouble("OLD_DIRECTIVE_PRICE", map.get("OLD_DIRECTIVE_PRICE"));
			}
			if (!StringUtils.isNullOrEmpty(map.get("DIRECTIVE_PRICE"))) {
				itemPO.setDouble("DIRECTIVE_PRICE", map.get("DIRECTIVE_PRICE"));
			}
			if (!StringUtils.isNullOrEmpty(map.get("OEM_TAG"))) {
				itemPO.setInteger("OEM_TAG", map.get("OEM_TAG"));
			}
			if (!StringUtils.isNullOrEmpty(map.get("REMARK"))) {
				itemPO.setString("REMARK", map.get("REMARK").toString());
			}
			if (!StringUtils.isNullOrEmpty(map.get("IS_FINISHED"))) {
				itemPO.setInteger("IS_FINISHED", DictCodeConstants.IS_NOT);
			}
			itemPO.saveIt();
		} else {// 修改
			if ((DictCodeConstants.DICT_STORAGE_STATUS_MOVE_OUT + "").equals(map.get("STOCK_STATUS").toString())
					&& (DictCodeConstants.DICT_DISPATCHED_STATUS_HAVE_DELIVER + "")
							.equals(map.get("DISPATCHED_STATUS").toString())) {
				// 库存状态为出库且配车状态为已交车,则不需要修改该明细记录
			} else {
				if (map.get("SD_NO") != null) {
					if (!StringUtils.isNullOrEmpty(map.get("IS_FINISHED"))
							&& map.get("IS_FINISHED").toString() != DictCodeConstants.IS_YES + "") {
						TtVsDeliveryItemPO itemPO = TtVsDeliveryItemPO.findByCompositeKeys(
								FrameworkUtil.getLoginInfo().getDealerCode(), map.get("SD_NO").toString(),
								map.get("VIN").toString());
						itemPO.setLong("OWNED_BY", FrameworkUtil.getLoginInfo().getUserId());
						if (!StringUtils.isNullOrEmpty(map.get("PRODUCT_CODE"))) {
							itemPO.setString("PRODUCT_CODE", map.get("PRODUCT_CODE").toString());
						}
						if (!StringUtils.isNullOrEmpty(map.get("STORAGE_CODE"))) {
							itemPO.setString("STORAGE_CODE", map.get("STORAGE_CODE").toString());
						}
						if (!StringUtils.isNullOrEmpty(map.get("STORAGE_POSITION_CODE"))) {
							itemPO.setString("STORAGE_POSITION_CODE", map.get("STORAGE_POSITION_CODE").toString());
						}
						if (!StringUtils.isNullOrEmpty(map.get("PURCHASE_PRICE"))) {
							itemPO.setDouble("PURCHASE_PRICE", map.get("PURCHASE_PRICE"));
						}
						if (!StringUtils.isNullOrEmpty(map.get("ADDITIONAL_COST"))) {
							itemPO.setDouble("ADDITIONAL_COST", map.get("ADDITIONAL_COST"));
						}
						if (!StringUtils.isNullOrEmpty(map.get("VEHICLE_PRICE"))) {
							itemPO.setDouble("VEHICLE_PRICE", map.get("VEHICLE_PRICE"));
						}
						itemPO.setInteger("STOCK_STATUS", DictCodeConstants.DICT_STORAGE_STATUS_IN_STORAGE);
						if (!StringUtils.isNullOrEmpty(map.get("DISPATCHED_STATUS"))) {
							itemPO.setInteger("DISPATCHED_STATUS", map.get("DISPATCHED_STATUS"));
						}
						if (!StringUtils.isNullOrEmpty(map.get("SO_NO"))) {
							itemPO.setString("SO_NO", map.get("SO_NO").toString());
						}
						if (!StringUtils.isNullOrEmpty(map.get("MAR_STATUS"))) {
							itemPO.setInteger("MAR_STATUS", map.get("MAR_STATUS"));
						}
						if (!StringUtils.isNullOrEmpty(map.get("IS_SECONDHAND"))) {
							itemPO.setInteger("IS_SECONDHAND", map.get("IS_SECONDHAND"));
						}
						if (!StringUtils.isNullOrEmpty(map.get("IS_VIP"))) {
							itemPO.setInteger("IS_VIP", map.get("IS_VIP"));
						}
						if (!StringUtils.isNullOrEmpty(map.get("IS_TEST_DRIVE_CAR"))) {
							itemPO.setInteger("IS_TEST_DRIVE_CAR", map.get("IS_TEST_DRIVE_CAR"));
						}
						if (!StringUtils.isNullOrEmpty(map.get("IS_CONSIGNED"))) {
							itemPO.setInteger("IS_CONSIGNED", map.get("IS_CONSIGNED"));
						}
						if (!StringUtils.isNullOrEmpty(map.get("IS_PROMOTION"))) {
							itemPO.setInteger("IS_PROMOTION", map.get("IS_PROMOTION"));
						}
						if (!StringUtils.isNullOrEmpty(map.get("IS_PURCHASE_RETURN"))) {
							itemPO.setInteger("IS_PURCHASE_RETURN", map.get("IS_PURCHASE_RETURN"));
						}
						if (!StringUtils.isNullOrEmpty(map.get("IS_PRICE_ADJUSTED"))) {
							itemPO.setInteger("IS_PRICE_ADJUSTED", map.get("IS_PRICE_ADJUSTED"));
						}
						if (!StringUtils.isNullOrEmpty(map.get("ADJUST_REASON"))) {
							itemPO.setString("ADJUST_REASON", map.get("ADJUST_REASON").toString());
						}
						if (!StringUtils.isNullOrEmpty(map.get("OLD_DIRECTIVE_PRICE"))) {
							itemPO.setDouble("OLD_DIRECTIVE_PRICE", map.get("OLD_DIRECTIVE_PRICE"));
						}
						if (!StringUtils.isNullOrEmpty(map.get("DIRECTIVE_PRICE"))) {
							itemPO.setDouble("DIRECTIVE_PRICE", map.get("DIRECTIVE_PRICE"));
						}
						if (!StringUtils.isNullOrEmpty(map.get("OEM_TAG"))) {
							itemPO.setInteger("OEM_TAG", map.get("OEM_TAG"));
						}
						if (!StringUtils.isNullOrEmpty(map.get("REMARK"))) {
							itemPO.setString("REMARK", map.get("REMARK").toString());
						}
						if (!StringUtils.isNullOrEmpty(map.get("IS_FINISHED"))) {
							itemPO.setInteger("IS_FINISHED", DictCodeConstants.IS_NOT);
						}
						itemPO.saveIt();
					}
				}
			}
		}
		// 删除操作
	}

	/**
	 * 删除操作 TODO description
	 * 
	 * @author yangjie
	 * @date 2017年2月8日
	 * @param map
	 */
	@Override
	public void deleteOutItems(Map map) throws ServiceBizException {
		// 删除主子表记录
		if (!StringUtils.isNullOrEmpty(map.get("VIN"))) {
			// 判断该VIN是否已经产生保有客户信息
			List<Map> list = checkOwnerInfo(map.get("VIN").toString());
			if (list.size() > 0) {
				// 已经产生保有客户信息了，不允许删除。
				new ServiceBizException("已经产生保有客户信息了，不允许删除。");
			} else if (!(DictCodeConstants.DICT_DISPATCHED_STATUS_HAVE_DELIVER + "")
					.equals(map.get("DISPATCHED_STATUS"))) {
				if (!StringUtils.isNullOrEmpty(map.get("SD_NO"))) {
					TtVsDeliveryItemPO po = TtVsDeliveryItemPO.findByCompositeKeys(
							FrameworkUtil.getLoginInfo().getDealerCode(), map.get("SD_NO").toString(),
							map.get("VIN").toString());
					po.delete();

					// 判断子表是否还有数据，如果没有删除主表记录
					String sql = "SELECT * FROM TT_VS_DELIVERY_ITEM WHERE SD_NO = '" + map.get("SD_NO").toString()
							+ "'";
					List<Map> all = DAOUtil.findAll(sql, null);
					if (all.size() == 0) {
						TtVsStockDeliveryPO po2 = TtVsStockDeliveryPO.findByCompositeKeys(
								FrameworkUtil.getLoginInfo().getDealerCode(), map.get("SD_NO").toString());
						po2.delete();
					}
				}
			}
		}
	}

	/**
	 * 在数据库中通过SDNO查询出库单明细信息 TODO description
	 * 
	 * @author yangjie
	 * @date 2017年2月8日
	 * @param sdNo
	 * @return
	 */
	public List<Map> searchVINList(String sdNo) {
		String sql = "select VIN,DEALER_CODE from TT_VS_DELIVERY_ITEM where IS_FINISHED=? AND SD_NO=?";
		List param = new ArrayList();
		param.add(DictCodeConstants.IS_NOT);
		param.add(sdNo);
		return DAOUtil.findAll(sql, param);
	}

	/**
	 * 出库时生成车主信息时，检测是否已经在保存保有客户信息时产生了车主记录
	 */
	public List<Map> checkOwnerInfo(String vin) {
		String entityCode = FrameworkUtil.getLoginInfo().getDealerCode();
		StringBuffer sql = new StringBuffer("");
		sql.append(" SELECT A.* FROM (" + CommonConstants.VM_VEHICLE + ") A,(" + CommonConstants.VM_OWNER
				+ ") B WHERE A.DEALER_CODE=B.DEALER_CODE AND A.OWNER_NO=B.OWNER_NO  AND A.VIN='" + vin
				+ "' AND A.DEALER_CODE='" + entityCode
				+ "' AND A.CUSTOMER_NO IS NOT NULL AND LENGTH(TRIM(A.CUSTOMER_NO)) <=12");
		return DAOUtil.findAll(sql.toString(), null);
	}

	/**
	 * 车辆出库操作
	 * 
	 * @author yangjie
	 * @date 2017年2月8日
	 * @param list
	 * @param sdNo
	 * @throws ServiceBizException
	 *             (non-Javadoc)
	 * @see com.yonyou.dms.vehicle.service.basedata.StockOutService#btnStockOut(java.util.List,
	 *      java.lang.String)
	 */
	@Override
	public void btnStockOut(List<Map> list, String sdNo, String outType) throws ServiceBizException {
		String entityCode = FrameworkUtil.getLoginInfo().getDealerCode();
		if (StringUtils.isNullOrEmpty(outType)) {
			Map findFirst = DAOUtil.findFirst("SELECT * FROM TT_VS_STOCK_DELIVERY WHERE SD_NO = '" + sdNo + "'", null);
			outType = findFirst.get("STOCK_OUT_TYPE").toString();
		}
		if ((DictCodeConstants.DICT_STOCK_OUT_TYPE_SALE + "").equals(outType)) {// 出库检测有没有潜在客户的证件号、联系人电话、联系人手机在保有客户中有重复的记录存在
			checkCustomer(list);
		}
		if (true) {
			Date dSalesDate;
			Date OutPutSalesDate;// 保有客户的开票登记日期
			String svin = "";
			String cusNo = "";

			String groupCodeProduct = Utility.getGroupEntity(entityCode, "TM_VS_PRODUCT");
			String groupCodeOwner = Utility.getGroupEntity(entityCode, "TM_OWNER");
			String groupCodeVehicle = Utility.getGroupEntity(entityCode, "TM_VEHICLE");
			OutPutSalesDate = null;
			long userId = FrameworkUtil.getLoginInfo().getUserId();
			String userName = FrameworkUtil.getLoginInfo().getUserId().toString();
			String defaultDayValue = Utility.getDefaultValue("2066");
			String defaultDayValuebx = Utility.getDefaultValue("1071");
			String defaultMileageValue = Utility.getDefaultValue("1070");// 保修结束里程
			String ctrlIsArrived = Utility.getDefaultValue("8055");
			String ascstr = "";

			Map ascinfopo = DAOUtil.findFirst(
					"SELECT ASC_SHORTNAME,DEALER_CODE FROM TM_ASC_BASICINFO WHERE DEALER_CODE = '" + entityCode + "'",
					null);
			if (ascinfopo != null) {
				ascstr = ascinfopo.get("ASC_SHORTNAME").toString();
			}

			if ((DictCodeConstants.DICT_STOCK_OUT_TYPE_UNTREAD + "").equals(outType)) {
				/**
				 * 采购退回出库
				 */
				for (Map map : list) {
					if (!StringUtils.isNullOrEmpty(map.get("VIN"))) {
						// 在库的车辆才能做出库操作
						VsStockPO vsStockPO = VsStockPO.findByCompositeKeys(entityCode, map.get("VIN").toString());
						if (vsStockPO != null && vsStockPO.getInteger("D_KEY") == CommonConstants.D_KEY && vsStockPO
								.getInteger("STOCK_STATUS") == DictCodeConstants.DICT_STORAGE_STATUS_IN_STORAGE) {
							// 修改车辆库存表 车辆状态为 出库
							// 回写车辆库存表里出库业务类型--采购退回出库
							vsStockPO.setLong("OWNED_BY", userId);
							vsStockPO.setInteger("STOCK_OUT_TYPE", Integer.parseInt(outType));
							vsStockPO.setString("SD_NO", sdNo);
							vsStockPO.setInteger("STOCK_STATUS", DictCodeConstants.DICT_STORAGE_STATUS_MOVE_OUT);
							vsStockPO.set("LATEST_STOCK_OUT_DATE", new Timestamp(System.currentTimeMillis()));
							vsStockPO.setInteger("LAST_STOCK_OUT_BY", userId);
							vsStockPO.set("FIRST_STOCK_OUT_DATE", new Timestamp(System.currentTimeMillis()));
							vsStockPO.saveIt();

								// 增加车辆库存日志信息
								insertToVehicleLog(map.get("VIN"), outType,
										map.get("PRODUCT_CODE"), map.get("STORAGE_CODE"),
										map.get("STORAGE_POSITION_CODE"),
										map.get("PURCHASE_PRICE"), map.get("ADDITIONAL_COST"),
										map.get("MAR_STATUS"),
										DictCodeConstants.DICT_DISPATCHED_STATUS_NOT_DISPATCHED + "");

							// 修改明细表里是否入帐,入帐人字段,配车状态为未配车,库存状态为出库
							if (!StringUtils.isNullOrEmpty(sdNo)) {
								TtVsDeliveryItemPO itemPO = TtVsDeliveryItemPO.findByCompositeKeys(entityCode, sdNo,
										map.get("VIN").toString());
								if (itemPO != null && itemPO.getInteger("D_KEY") == CommonConstants.D_KEY) {
									itemPO.setLong("OWNED_BY", userId);
									itemPO.setInteger("IS_FINISHED", DictCodeConstants.IS_YES);
									itemPO.setDate("FINISHED_DATE", new Date());
									itemPO.setString("FINISHED_BY", userName);
									itemPO.setInteger("DISPATCHED_STATUS",
											DictCodeConstants.DICT_DISPATCHED_STATUS_NOT_DISPATCHED);
									itemPO.setInteger("STOCK_STATUS", DictCodeConstants.DICT_STORAGE_STATUS_MOVE_OUT);
									itemPO.saveIt();
								}
							}

						} else {
							throw new ServiceBizException("库存不在库");
						}
					}
				}
			} else {
				/**
				 * 销售出库
				 */
				String messageapp = "";
				String flagg = "1";// 车辆出库主表是否全部入帐字段为是,0否
				for (Map map : list) {
					messageapp = "";
					// 客户信息是否到店参数控制
					if (ctrlIsArrived != null && DictCodeConstants.DICT_IS_YES.equals(ctrlIsArrived)) {
						// 客户信息为未到店不能做出库操作
						int customerIsNotArrived = showMsgCustomerIsNotArrived(entityCode, map.get("SO_NO").toString());
						// 0表示客户为未到店,1表示客户为已到店
						if (customerIsNotArrived == 0) {
							throw new ServiceBizException("该客户未到店，不能交车，请到展厅接待中维护到店信息!");
						}
					}
					// 在库的车辆才能做出库操作
					VsStockPO vsStockPO = VsStockPO.findByCompositeKeys(entityCode, map.get("VIN").toString());
					System.err.println("0-0-0");
					if (vsStockPO != null && vsStockPO.getInteger("D_KEY") == CommonConstants.D_KEY && vsStockPO
							.getInteger("STOCK_STATUS") == DictCodeConstants.DICT_STORAGE_STATUS_IN_STORAGE) {
						System.err.println("0-0-0-0");
						/**
						 * 通过车辆出库明细中的销售订单编号查销售订单验证当前出库的车vin和销售订单中的是否一致，为了校验销售订单发生变化
						 * 如果vin一致订单状态为已交车确认，则走正常销售出库； 否则
						 * (如果没有该订单，则删除出库主细里记录，返回前台提示信息
						 */
						if (!StringUtils.isNullOrEmpty(map.get("SO_NO"))
								&& !StringUtils.isNullOrEmpty(map.get("VIN"))) {
							System.err.println("0-0-0-1");
							String sql = "SELECT * FROM TT_SALES_ORDER WHERE SO_NO = ? AND VIN = ? AND D_KEY = "
									+ CommonConstants.D_KEY;
							List queryParam = new ArrayList();
							queryParam.add(map.get("SO_NO"));
							queryParam.add(map.get("VIN"));
							Map ordera = DAOUtil.findFirst(sql, queryParam);
							System.err.println("0-0-1");
							if (ordera == null || ordera.size() == 0) {
								// 销售订单已被修改过
								// 1.删除车辆出库单明细信息
								TtVsDeliveryItemPO itemPO = TtVsDeliveryItemPO.findByCompositeKeys(entityCode, sdNo,
										map.get("VIN").toString());
								if (itemPO != null && itemPO.getInteger("D_KEY") == CommonConstants.D_KEY) {
									itemPO.delete();
								}
							} else if (!StringUtils.isNullOrEmpty(ordera)) {
								// 如果该车订单状态不为已交车确认，则不能出库，返回给前台提示信息(可能取消订单、订单驳回)
								if (!(ordera.get("SO_STATUS").toString()
										.equals(DictCodeConstants.DICT_SO_STATUS_HAVE_CONFIRMED))) {
									if (!StringUtils.isNullOrEmpty(map.get("VIN"))) {
										TtVsDeliveryItemPO itemPO = TtVsDeliveryItemPO.findByCompositeKeys(entityCode,
												sdNo, map.get("VIN").toString());
										if (itemPO != null && itemPO.getInteger("D_KEY") == CommonConstants.D_KEY) {
											itemPO.delete();
											throw new ServiceBizException("订单已发生变化，不能出库，订单状态不为已交车确认");
										}
									}
								}
								System.err.println("0-1");
								// 修改车辆库存表 车辆状态为 出库 配车状态为 交车出库
								// 回写车辆库存表里出库业务类型,销售定单号
								VsStockPO stockPO = VsStockPO.findByCompositeKeys(entityCode,
										map.get("VIN").toString());
								if (stockPO != null && stockPO.getInteger("D_KEY") == CommonConstants.D_KEY) {
									stockPO.setLong("OWNED_BY", userId);
									stockPO.setInteger("STOCK_OUT_TYPE", Integer.parseInt(outType));
									stockPO.setString("SD_NO", sdNo);
									stockPO.setString("SO_NO", map.get("SO_NO").toString());// 销售订单编号
									stockPO.setInteger("STOCK_STATUS", DictCodeConstants.DICT_STORAGE_STATUS_MOVE_OUT);
									stockPO.set("LATEST_STOCK_OUT_DATE", new Timestamp(System.currentTimeMillis()));
									stockPO.setLong("LAST_STOCK_OUT_BY", userId);
									stockPO.set("FIRST_STOCK_OUT_DATE", new Timestamp(System.currentTimeMillis()));
									stockPO.setInteger("DISPATCHED_STATUS",
											DictCodeConstants.DICT_DISPATCHED_STATUS_HAVE_DELIVER);// 配车状态改为已交车出库
									stockPO.setInteger("STOCK_OUT_TYPE", Integer.parseInt(outType));
									stockPO.saveIt();

									// 修改销售定单字段//如果销售出库回写销售订单中是否上报写为否12781002
									SalesOrderPO orderPO = SalesOrderPO.findByCompositeKeys(entityCode,
											map.get("SO_NO").toString());
									if (orderPO != null && orderPO.getInteger("D_KEY") == CommonConstants.D_KEY) {
										orderPO.setDate("STOCK_OUT_DATE", new Timestamp(System.currentTimeMillis()));
										orderPO.setLong("STOCK_OUT_BY", userId);
										orderPO.setInteger("SO_STATUS",
												Integer.parseInt(DictCodeConstants.DICT_SO_STATUS_HAVE_OUT_STOCK));//
										orderPO.setInteger("IS_UPLOAD", Integer.parseInt(DictCodeConstants.DICT_IS_NO));
										orderPO.saveIt();

										// 修改QCSales中的出库日期1674

										// 记录销售订单的审核状态历史
										setOrderStatusPO(entityCode, map.get("SO_NO").toString(),
												DictCodeConstants.DICT_SO_STATUS_HAVE_OUT_STOCK, userId);
									}

									// 如果是销售出库则将客户资料表里客户级别改为基盘客户,客户意向改为D级,客户的意向状态改为完成
									if ((DictCodeConstants.DICT_STOCK_OUT_TYPE_SALE + "").equals(outType)) {
										// 回写车辆库存里so_no字段
										stockPO.setString("SO_NO", map.get("SO_NO").toString());// 销售订单编号
										stockPO.saveIt();

										// 1.修改潜在客户资料表里,INTENT_LEVEL改为N级
										PotentialCusPO cusPO = PotentialCusPO.findByCompositeKeys(entityCode,
												ordera.get("CUSTOMER_NO").toString());
										if (cusPO != null && cusPO.getInteger("D_KEY") == CommonConstants.D_KEY) {
											cusPO.setInteger("INTENT_LEVEL", DictCodeConstants.DICT_INTENT_LEVEL_D);
											cusPO.setDate("DDCN_UPDATE_DATE", new Date());
											cusPO.saveIt();
										}

										/**
										 * 把这个客户的跟进记录中没有跟进的记录删掉
										 */
										TtSalesPromotionPlanPO planPO = new TtSalesPromotionPlanPO();
										planPO.delete(
												"DEALER_CODE = ? AND CUSTOMER_NO = ? AND (PROM_RESULT IS NULL OR PROM_RESULT=0) ",
												entityCode, ordera.get("CUSTOMER_NO").toString());

										saveProPlanOrCr(entityCode, cusPO.getString("CUSTOMER_NO"),
												cusPO.getInteger("INTENT_LEVEL"), userId, "emp_no");

										/**
										 * 根据VIN判断是否有保有客户信息,没有则要新增保有客户信息
										 */
										String customerNoB = insertIntoCustomer(entityCode, map.get("VIN").toString(),
												map.get("SO_NO").toString(), ordera.get("CUSTOMER_NO").toString(),
												userId);

										/**
										 * 跟进CR关怀
										 */
										SystemTrack(customerNoB, map.get("VIN").toString(), entityCode, userId);

										// 2潜在客户意向表中是否完成意向为是
										TtCusIntentPO intentPOCon = TtCusIntentPO.findFirst(
												"CUSTOMER_NO = ? AND D_KEY = " + CommonConstants.D_KEY,
												ordera.get("CUSTOMER_NO").toString());
										if (intentPOCon != null) {
											intentPOCon.setLong("INTENT_FINISHED", DictCodeConstants.IS_YES);
											intentPOCon.saveIt();
										}

										/**
										 * 回写维修工单的车主编号，姓名，车主性质，判断lock_user是否为空，不为空的则不能出库，抛异常
										 * 回写配件销售单 客户名称，客户姓名
										 */
										/**
										 * 检测是否已经在保存保有客户信息中生成了车主记录
										 */
										System.err.println("第一个");
										List<Map> listOwner = checkOwnerInfo(map.get("VIN").toString());
										String Tag = "";
										if (listOwner == null || listOwner.size() == 0) {
											Tag = DictCodeConstants.DICT_IS_YES;
										}
										if (DictCodeConstants.DICT_IS_YES.equals(Tag.trim())) {
											String ownerNo = commonNoService
													.getSystemOrderNo(CommonConstants.OWNER_PREFIX);
											SalesOrderPO orderPOx2 = SalesOrderPO.findByCompositeKeys(entityCode,
													map.get("SO_NO").toString());
											String roNo = "";// 工单号
											String customerCode = "";// 客户编号
											String customerName = "";// 客户姓名
											String customerType = "";// 客户类型
											String salesPartNo = "";// 配件销售单号
											if (orderPOx2 != null
													&& orderPOx2.getInteger("D_KEY") == CommonConstants.D_KEY) {
												roNo = orderPOx2.getString("RO_NO");
												customerCode = orderPOx2.getString("CUSTOMER_NO");
												customerName = orderPOx2.getString("CUSTOMER_NAME");
												customerType = orderPOx2.getString("CUSTOMER_TYPE");
											}
											if (roNo != null && !"".equals(roNo.trim())) {
												RepairOrderPO ttRepairOrderPO = RepairOrderPO.findFirst("RO_NO = ?",
														roNo);
												if (ttRepairOrderPO != null) {
													salesPartNo = ttRepairOrderPO.getString("SALES_PART_NO");// 配件销售单号
													if (ttRepairOrderPO.getString("LOCK_USER") == null || ""
															.equals(ttRepairOrderPO.getString("LOCK_USER").trim())) {
														if (roNo != null && !"".equals(roNo.trim())) {
															// 回写维修工单
															ttRepairOrderPO.setInteger("OWNER_NO", ownerNo);
															ttRepairOrderPO.setString("OWNER_NAME", customerName);
															ttRepairOrderPO.setInteger("OWNER_PROPERTY",
																	Integer.parseInt(customerType));
															ttRepairOrderPO.saveIt();
														}
														// 回写配件销售订单
														if (salesPartNo != null && !"".equals(salesPartNo.trim())) {
															TtSalesPartPO salesPartPO = TtSalesPartPO
																	.findByCompositeKeys(entityCode, salesPartNo);
															if (salesPartPO != null && salesPartPO
																	.getInteger("D_KEY") == CommonConstants.D_KEY) {
																salesPartPO.setString("CUSTOMER_CODE", customerCode);
																salesPartPO.setString("CUSTOMER_NAME", customerName);
																salesPartPO.saveIt();
															}

														}

													}
												}
											}
											/**
											 * 根据客户编号在车辆资料表里查是否有记录,有则修改,无则增加,
											 * 销售出库车主信息全部为新增
											 */
											// 新增车主信息
											SalesOrderPO orderPOa = SalesOrderPO.findByCompositeKeys(entityCode,
													map.get("SO_NO").toString());
											if (orderPOa != null
													&& orderPOa.getInteger("D_KEY") == CommonConstants.D_KEY) {
												CarownerPO ownerPOb = new CarownerPO();
												ownerPOb.setString("DEALER_CODE", groupCodeOwner);
												CustomerPO customerPO2 = CustomerPO.findByCompositeKeys(entityCode,
														customerNoB);
												ownerPOb.setString("OWNER_NO", ownerNo);// 车住编号
												if (customerPO2 != null
														&& customerPO2.getInteger("D_KEY") == CommonConstants.D_KEY) {
													/**
													 * TM_CUSTOMER是售后 TM_OWNER售前
													 * 车主类型和客户类型字典常量应该一致 从售后转售前
													 */
													if (customerPO2.getString("CUSTOMER_TYPE")
															.equals(DictCodeConstants.DICT_CUSTOMER_TYPE_INDIVIDUAL)) {
														// 个人
														ownerPOb.setInteger("OWNER_PROPERTY", Integer.parseInt(
																DictCodeConstants.DICT_OWNER_PROPERTY_PERSONAL));
													}
													if (customerPO2.getString("CUSTOMER_TYPE").toString()
															.equals(DictCodeConstants.DICT_CUSTOMER_TYPE_COMPANY)) {
														// 公司
														ownerPOb.setInteger("OWNER_PROPERTY", Integer.parseInt(
																DictCodeConstants.DICT_OWNER_PROPERTY_COMPANY));
													}
													ownerPOb.setInteger("GENDER", customerPO2.getInteger("GENDER"));// 性别
													ownerPOb.setDate("BIRTHDAY", customerPO2.getDate("BIRTHDAY"));// 生日
													ownerPOb.setInteger("ZIP_CODE", customerPO2.getInteger("ZIP_CODE"));// 邮编
													ownerPOb.setInteger("PROVINCE", customerPO2.getInteger("PROVINCE"));// 省
													ownerPOb.setInteger("CITY", customerPO2.getInteger("CITY"));// 城市
													ownerPOb.setInteger("DISTRICT", customerPO2.getInteger("DISTRICT"));// 区县
													ownerPOb.setString("ADDRESS", customerPO2.getString("ADDRESS"));// 地址
													ownerPOb.setString("E_MAIL", customerPO2.getString("E_MAIL"));// EMAIL
													ownerPOb.setString("CERTIFICATE_NO",
															customerPO2.getString("CERTIFICATE_NO"));// 证件号码
													ownerPOb.setString("HOBBY", customerPO2.getString("HOBBY"));// 爱好
													ownerPOb.setInteger("INDUSTRY_FIRST",
															customerPO2.getInteger("INDUSTRY_FIRST"));// 行业大类一
													ownerPOb.setInteger("INDUSTRY_SECOND",
															customerPO2.getInteger("INDUSTRY_SECOND"));// 行业大类二
													ownerPOb.setString("PHONE",
															customerPO2.getString("CONTACTOR_PHONE"));// 电话
													ownerPOb.setString("MOBILE",
															customerPO2.getString("CONTACTOR_MOBILE"));// 移动电话
													ownerPOb.setInteger("FAMILY_INCOME",
															customerPO2.getInteger("FAMILY_INCOME"));// 家庭月收入
													ownerPOb.setInteger("OWNER_MARRIAGE",
															customerPO2.getInteger("OWNER_MARRIAGE"));// 婚姻状况
													ownerPOb.setInteger("EDU_LEVEL",
															customerPO2.getInteger("EDUCATION_LEVEL"));// 学历
													ownerPOb.setDate("FOUND_DATE", customerPO2.getDate("FOUND_DATE"));// 建档日期
													ownerPOb.setDate("SUBMIT_TIME", customerPO2.getDate("SUBMIT_TIME"));// 上报日期
													ownerPOb.setDate("DOWN_TIMESTAMP",
															customerPO2.getDate("DOWN_TIMESTAMP"));// 下发时序
													ownerPOb.setInteger("IS_UPLOAD", DictCodeConstants.IS_NOT);// 默认为否
													ownerPOb.setString("OWNER_NAME",
															customerPO2.getString("CUSTOMER_NAME"));// 车主名称
													ownerPOb.setString("CT_CODE", customerPO2.getString("CT_CODE"));
													ownerPOb.setInteger("IS_UPLOAD_GROUP", DictCodeConstants.IS_NOT);
												}
												ownerPOb.saveIt();

												// 二级网点业务
												List<Map> listEntity = Utility.getSubEntityList(
														ownerPOb.getString("DEALER_CODE"), "TM_OWNER");
												if (listEntity != null)
													for (Map entityPO : listEntity) {
														TmOwnerSubclassPO poSub = new TmOwnerSubclassPO();
														poSub.setString("MAIN_ENTITY",
																ownerPOb.getString("DEALER_CODE"));
														poSub.setString("DEALER_CODE",
																entityPO.get("CHILD_ENTITY").toString());
														poSub.setString("OWNER_NO", ownerPOb.getString("OWNER_NO"));
														// 不共享业务字段
														if (entityPO.get("CHILD_ENTITY").toString()
																.equals(entityCode)) {
															poSub.setDouble("PRE_PAY", ownerPOb.getDouble("PRE_PAY"));
															poSub.setDouble("ARREARAGE_AMOUNT",
																	ownerPOb.getDouble("ARREARAGE_AMOUNT"));
														}
														poSub.saveIt();
													}

												// 1.检查车辆资料表里是否存在
												VehiclePO tmVehiclePO2 = VehiclePO.findByCompositeKeys(
														map.get("VIN").toString(), groupCodeVehicle);
												dSalesDate = new Date();
												svin = map.get("VIN").toString();
												cusNo = map.get("SO_NO").toString();

												// 【车辆出库时销售日期有值就取销售日期，没有就查费用类型是购车费用的发票，有就取这个开票日期，没有就取当前日期】定义成【A】
												// ,车辆出库自动算保险和保修日期的开始日期就以【A】为准。
												if (svin != null && !svin.equals("")) {
													VehiclePO aTmVehiclePO = VehiclePO.findByCompositeKeys(svin,
															entityCode);
													if (aTmVehiclePO != null) {
														if (aTmVehiclePO.getDate("SALES_DATE") == null
																|| aTmVehiclePO.getDate("SALES_DATE").equals("")) {
															SoInvoicePO aSoInvoicePO = SoInvoicePO.first("so_no = ? and vin = ? AND d_key = "+ CommonConstants.D_KEY, cusNo, svin);
															if (aSoInvoicePO != null) {
																if (aSoInvoicePO.getDate("INVOICE_DATE") != null
																		&& !aSoInvoicePO.getDate("INVOICE_DATE")
																				.equals("")) {
																	dSalesDate = aSoInvoicePO.getDate("INVOICE_DATE");
																	OutPutSalesDate = dSalesDate;
																}
															}
														} else {
															dSalesDate = aTmVehiclePO.getDate("SALES_DATE");
														}
													}
												}
												VsStockPO sk = VsStockPO.findByCompositeKeys(entityCode,
														map.get("VIN").toString());
												if (sk != null && sk.getInteger("D_KEY") == CommonConstants.D_KEY
														&& tmVehiclePO2 != null) {
													// 有改车辆
													VsProductPO productPO = VsProductPO.findByCompositeKeys(
															groupCodeProduct, map.get("PRODUCT_CODE").toString());
													if (productPO != null
															&& productPO.getInteger("D_KEY") == CommonConstants.D_KEY) {
														tmVehiclePO2.setString("BRAND",
																productPO.getString("BRAND_CODE"));
														tmVehiclePO2.setString("SERIES",
																productPO.getString("SERIES_CODE"));
														tmVehiclePO2.setString("MODEL",
																productPO.getString("MODEL_CODE"));
														tmVehiclePO2.setString("COLOR",
																productPO.getString("COLOR_CODE"));
														tmVehiclePO2.setString("APACKAGE",
																productPO.getString("CONFIG_CODE"));
													}
													tmVehiclePO2.setString("YEAR_MODEL", sk.getString("MODEL_YEAR")); // add
																														// by
																														// lim
													tmVehiclePO2.setString("MODEL_YEAR", sk.getString("MODEL_YEAR"));
													tmVehiclePO2.setString("LICENSE", "无牌照");// 车牌号，转售后定义为售后无牌照的常量
													tmVehiclePO2.setString("CUSTOMER_NO", customerNoB);
													tmVehiclePO2.setString("CONSULTANT",
															FrameworkUtil.getLoginInfo().getUserName());
													if (OutPutSalesDate != null
															&& !OutPutSalesDate.toString().equals("")) {
														tmVehiclePO2.setDate("SALES_DATE", OutPutSalesDate);// 有开票登记时的开票登记日期让保有客户的日期在出库时，不设置为空
													}
													tmVehiclePO2.setDouble("VEHICLE_PRICE",
															orderPOa.getDouble("VEHICLE_PRICE"));
													tmVehiclePO2.setString("CONTRACT_NO",
															orderPOa.getString("CONTRACT_NO"));
													tmVehiclePO2.setDate("CONTRACT_DATE",
															orderPOa.getDate("CONTRACT_DATE"));
													tmVehiclePO2.setLong("IS_UPLOAD", DictCodeConstants.IS_NOT);
													tmVehiclePO2.setLong("IS_UPLOAD_GROUP", DictCodeConstants.IS_NOT);
													tmVehiclePO2.setString("OWNER_NO", ownerNo);
													if (map.get("WARRANTY_NUMBER") != null) {
														tmVehiclePO2.setString("WARRANTY_NUMBER",
																map.get("WARRANTY_NUMBER").toString());
													}
													tmVehiclePO2.setInteger("VEHICLE_PURPOSE",
															orderPOa.getInteger("VEHICLE_PURPOSE"));
													tmVehiclePO2.setLong("IS_SELF_COMPANY", DictCodeConstants.IS_YES);
													tmVehiclePO2.setString("SALES_AGENT_NAME", ascstr);
													// 保修结束里程
													if (defaultMileageValue != null
															&& !"".equals(defaultMileageValue.trim())
															&& Utility.getDouble(defaultMileageValue.trim()) > 0) {
														tmVehiclePO2.setDouble("WRT_END_MILEAGE",
																Utility.getDouble(defaultMileageValue.trim()));
													}
													// 保险
													if (defaultDayValue != null && !"".equals(defaultDayValue.trim())
															&& Integer.parseInt(defaultDayValue.trim()) > 0) {
														tmVehiclePO2.setDate("INSURANCE_BEGIN_DATE", dSalesDate);
														Calendar calendar = Calendar.getInstance();
														calendar.setTime(tmVehiclePO2.getDate("INSURANCE_BEGIN_DATE"));
														calendar.add(Calendar.DATE,
																Integer.parseInt(defaultDayValue.trim()));
														Date d = calendar.getTime();
														tmVehiclePO2.setDate("INSURANCE_END_DATE", d);
													}
													// 保修
													if (defaultDayValuebx != null
															&& !"".equals(defaultDayValuebx.trim())
															&& Integer.parseInt(defaultDayValuebx.trim()) > 0) {
														// 保修
														tmVehiclePO2.setDate("WRT_BEGIN_DATE", dSalesDate);// comg2011-0113
														Calendar calendar1 = Calendar.getInstance();
														calendar1.setTime(tmVehiclePO2.getDate("WRT_BEGIN_DATE"));
														calendar1.add(Calendar.DATE,
																Integer.parseInt(defaultDayValuebx.trim()));
														Date d1 = calendar1.getTime();
														tmVehiclePO2.setDate("WRT_END_DATE", d1);
													}
													TmVehicleSubclassPO poSubUpdate = TmVehicleSubclassPO
															.findFirst("vin = ?", tmVehiclePO2.getString("VIN"));
													poSubUpdate.setString("CONSULTANT",
															tmVehiclePO2.getString("CONSULTANT"));
													poSubUpdate.setInteger("IS_SELF_COMPANY",
															tmVehiclePO2.getInteger("IS_SELF_COMPANY"));
													poSubUpdate.setDate("FIRST_IN_DATE",
															tmVehiclePO2.getDate("FIRST_IN_DATE"));
													poSubUpdate.setString("CHIEF_TECHNICIAN",
															tmVehiclePO2.getString("CHIEF_TECHNICIAN"));
													poSubUpdate.setString("SERVICE_ADVISOR",
															tmVehiclePO2.getString("SERVICE_ADVISOR"));
													poSubUpdate.setString("INSURANCE_ADVISOR",
															tmVehiclePO2.getString("INSURANCE_ADVISOR"));
													poSubUpdate.setString("MAINTAIN_ADVISOR",
															tmVehiclePO2.getString("MAINTAIN_ADVISOR"));
													poSubUpdate.setDate("LAST_MAINTAIN_DATE",
															tmVehiclePO2.getDate("LAST_MAINTAIN_DATE"));
													poSubUpdate.setDouble("LAST_MAINTAIN_MILEAGE",
															tmVehiclePO2.getDouble("LAST_MAINTAIN_MILEAGE"));
													poSubUpdate.setDate("LAST_MAINTENANCE_DATE",
															tmVehiclePO2.getDate("LAST_MAINTENANCE_DATE"));
													poSubUpdate.setDouble("LAST_MAINTENANCE_MILEAGE",
															tmVehiclePO2.getDouble("LAST_MAINTENANCE_MILEAGE"));
													poSubUpdate.setDouble("PRE_PAY", tmVehiclePO2.getDouble("PRE_PAY"));
													poSubUpdate.setDouble("ARREARAGE_AMOUNT",
															tmVehiclePO2.getDouble("ARREARAGE_AMOUNT"));
													poSubUpdate.setDouble("DISCOUNT_EXPIRE_DATE",
															tmVehiclePO2.getDate("DISCOUNT_EXPIRE_DATE"));
													poSubUpdate.setString("DISCOUNT_MODE_CODE",
															tmVehiclePO2.getString("DISCOUNT_MODE_CODE"));
													poSubUpdate.setInteger("IS_SELF_COMPANY_INSURANCE",
															tmVehiclePO2.getInteger("IS_SELF_COMPANY_INSURANCE"));
													poSubUpdate.setDate("ADJUST_DATE",
															tmVehiclePO2.getDate("ADJUST_DATE"));
													poSubUpdate.setString("ADJUSTER",
															tmVehiclePO2.getString("ADJUSTER"));
													poSubUpdate.setInteger("IS_VALID",
															tmVehiclePO2.getInteger("IS_VALID"));
													poSubUpdate.setString("OWNER_NO",
															tmVehiclePO2.getString("OWNER_NO"));
													poSubUpdate.setInteger("NO_VALID_REASON",
															tmVehiclePO2.getInteger("NO_VALID_REASON"));
													poSubUpdate.saveIt();

													tmVehiclePO2.setDouble("WRT_BEGIN_MILEAGE", null);
													tmVehiclePO2.saveIt();

												} else {
													// 插入车辆表里客户编号字段
													tmVehiclePO2 = new VehiclePO();
													tmVehiclePO2.setString("DEALER_CODE", groupCodeVehicle);
													tmVehiclePO2.setString("VIN", map.get("VIN").toString());
													tmVehiclePO2.setString("CUSTOMER_NO", customerNoB);
													tmVehiclePO2.setString("OWNER_NO", ownerNo);
													VsProductPO productPO = VsProductPO.findByCompositeKeys(
															groupCodeProduct, map.get("PRODUCT_CODE").toString());
													if (productPO != null
															&& productPO.getInteger("D_KEY") == CommonConstants.D_KEY) {
														tmVehiclePO2.setString("BRAND",
																productPO.getString("BRAND_CODE"));
														tmVehiclePO2.setString("SERIES",
																productPO.getString("SERIES_CODE"));
														tmVehiclePO2.setString("MODEL",
																productPO.getString("MODEL_CODE"));
														tmVehiclePO2.setString("COLOR",
																productPO.getString("COLOR_CODE"));
														tmVehiclePO2.setString("APACKAGE",
																productPO.getString("CONFIG_CODE"));
													}
													tmVehiclePO2.setString("YEAR_MODEL", sk.getString("MODEL_YEAR")); // add
																														// by
																														// lim
													tmVehiclePO2.setString("MODEL_YEAR", sk.getString("MODEL_YEAR"));
													tmVehiclePO2.setString("LICENSE", "无牌照");// 车牌号，转售后定义为售后无牌照的常量
													tmVehiclePO2.setString("CONSULTANT",
															FrameworkUtil.getLoginInfo().getUserName());
													tmVehiclePO2.setDouble("VEHICLE_PRICE",
															orderPOa.getDouble("VEHICLE_PRICE"));
													tmVehiclePO2.setString("CONTRACT_NO",
															orderPOa.getString("CONTRACT_NO"));
													tmVehiclePO2.setDate("CONTRACT_DATE",
															orderPOa.getDate("CONTRACT_DATE"));
													tmVehiclePO2.setDate("FOUND_DATE", new Date());
													tmVehiclePO2.setDate("PRODUCT_DATE",
															sk.getDate("MANUFACTURE_DATE"));
													tmVehiclePO2.setString("ENGINE_NO", sk.getString("ENGINE_NO"));
													tmVehiclePO2.setString("KEY_NUMBER", sk.getString("KEY_NUMBER"));
													tmVehiclePO2.setString("PRODUCTING_AREA",
															sk.getString("PRODUCTING_AREA"));
													if (OutPutSalesDate != null
															&& !OutPutSalesDate.toString().equals("")) {
														tmVehiclePO2.setDate("SALES_DATE", OutPutSalesDate);
														// 有开票登记时的开票登记日期让保有客户的日期在出库时，不设置为空
													}
													tmVehiclePO2.setLong("IS_UPLOAD", DictCodeConstants.IS_NOT);
													tmVehiclePO2.setLong("IS_UPLOAD_GROUP", DictCodeConstants.IS_NOT);
													tmVehiclePO2.setInteger("VEHICLE_PURPOSE",
															orderPOa.getInteger("VEHICLE_PURPOSE"));
													tmVehiclePO2.setLong("IS_SELF_COMPANY", DictCodeConstants.IS_YES);
													tmVehiclePO2.setString("SALES_AGENT_NAME", ascstr);
													if (!StringUtils.isNullOrEmpty(map.get("WARRANTY_NUMBER"))) {
														tmVehiclePO2.setString("WARRANTY_NUMBER",
																map.get("WARRANTY_NUMBER").toString());
													}
													// 保修结束里程
													if (defaultMileageValue != null
															&& !"".equals(defaultMileageValue.trim())
															&& Utility.getDouble(defaultMileageValue.trim()) > 0) {
														tmVehiclePO2.setDouble("WRT_END_MILEAGE",
																Utility.getDouble(defaultMileageValue.trim()));
													}
													// 保险
													if (defaultDayValue != null && !"".equals(defaultDayValue.trim())
															&& Integer.parseInt(defaultDayValue.trim()) > 0) {
														tmVehiclePO2.setDate("INSURANCE_BEGIN_DATE", dSalesDate);// comg2011-01-13
														Calendar calendar = Calendar.getInstance();
														calendar.setTime(tmVehiclePO2.getDate("INSURANCE_BEGIN_DATE"));
														calendar.add(Calendar.DATE,
																Integer.parseInt(defaultDayValue.trim()));
														Date d = calendar.getTime();
														tmVehiclePO2.setDate("INSURANCE_END_DATE", d);
													}
													// 保修
													if (defaultDayValuebx != null
															&& !"".equals(defaultDayValuebx.trim())
															&& Integer.parseInt(defaultDayValuebx.trim()) > 0) {
														// 保修
														tmVehiclePO2.setDate("WRT_BEGIN_DATE", dSalesDate);
														Calendar calendar1 = Calendar.getInstance();
														calendar1.setTime(tmVehiclePO2.getDate("WRT_BEGIN_DATE"));
														calendar1.add(Calendar.DATE,
																Integer.parseInt(defaultDayValuebx.trim()));
														Date d1 = calendar1.getTime();
														tmVehiclePO2.setDate("WRT_END_DATE", d1);
													}
													tmVehiclePO2.setString("PRODUCT_CODE",
															map.get("PRODUCT_CODE").toString());
													tmVehiclePO2.saveIt();

													// 二级网点业务
													List<Map> listEntityVehicle = Utility.getSubEntityList(
															tmVehiclePO2.getString("DEALER_CODE"), "TM_VEHICLE");
													if (listEntityVehicle != null && listEntityVehicle.size() > 0) {
														for (Map entityPO : listEntityVehicle) {
															TmVehicleSubclassPO poSub = new TmVehicleSubclassPO();
															poSub.setString("MAIN_ENTITY",
																	tmVehiclePO2.getString("DEALER_CODE"));
															poSub.setString("DEALER_CODE",
																	entityPO.get("CHILD_ENTITY").toString());
															poSub.setString("OWNER_NO",
																	tmVehiclePO2.getString("OWNER_NO"));
															poSub.setString("VIN", tmVehiclePO2.getString("VIN"));
															// 不共享业务字段
															if (entityPO.get("CHILD_ENTITY").toString()
																	.equals(entityCode)) {
																poSub.setString("CONSULTANT",
																		tmVehiclePO2.getString("CONSULTANT"));
																poSub.setInteger("IS_SELF_COMPANY",
																		tmVehiclePO2.getInteger("IS_SELF_COMPANY"));
																poSub.setDate("FIRST_IN_DATE",
																		tmVehiclePO2.getDate("FIRST_IN_DATE"));
																poSub.setString("CHIEF_TECHNICIAN",
																		tmVehiclePO2.getString("CHIEF_TECHNICIAN"));
																poSub.setString("SERVICE_ADVISOR",
																		tmVehiclePO2.getString("SERVICE_ADVISOR"));
																poSub.setString("INSURANCE_ADVISOR",
																		tmVehiclePO2.getString("INSURANCE_ADVISOR"));
																poSub.setString("MAINTAIN_ADVISOR",
																		tmVehiclePO2.getString("MAINTAIN_ADVISOR"));
																poSub.setDate("LAST_MAINTAIN_DATE",
																		tmVehiclePO2.getDate("LAST_MAINTAIN_DATE"));
																poSub.setDouble("LAST_MAINTAIN_MILEAGE", tmVehiclePO2
																		.getDouble("LAST_MAINTAIN_MILEAGE"));
																poSub.setDate("LAST_MAINTENANCE_DATE",
																		tmVehiclePO2.getDate("LAST_MAINTENANCE_DATE"));
																poSub.setDouble("LAST_MAINTENANCE_MILEAGE", tmVehiclePO2
																		.getDouble("LAST_MAINTENANCE_MILEAGE"));
																poSub.setDouble("PRE_PAY",
																		tmVehiclePO2.getDouble("PRE_PAY"));
																poSub.setDouble("ARREARAGE_AMOUNT",
																		tmVehiclePO2.getDouble("ARREARAGE_AMOUNT"));
																poSub.setDate("DISCOUNT_EXPIRE_DATE",
																		tmVehiclePO2.getDate("DISCOUNT_EXPIRE_DATE"));
																poSub.setString("DISCOUNT_MODE_CODE",
																		tmVehiclePO2.getString("DISCOUNT_MODE_CODE"));
																poSub.setInteger("IS_SELF_COMPANY_INSURANCE",
																		tmVehiclePO2.getInteger(
																				"IS_SELF_COMPANY_INSURANCE"));
																poSub.setDate("ADJUST_DATE",
																		tmVehiclePO2.getDate("ADJUST_DATE"));
																poSub.setString("ADJUSTER",
																		tmVehiclePO2.getString("ADJUSTER"));
																poSub.setInteger("IS_VALID",
																		tmVehiclePO2.getInteger("IS_VALID"));
																poSub.setInteger("NO_VALID_REASON",
																		tmVehiclePO2.getInteger("NO_VALID_REASON"));
															}
															poSub.saveIt();
														}
													}
												}
											}
										} else {
											// 交车确认或者保存保佑客户信息，已经生成了车主资料
											VehiclePO vehiclePO = VehiclePO
													.findByCompositeKeys(map.get("Vin").toString(), groupCodeVehicle);
											if (vehiclePO != null) {
												CustomerPO tmCustomerPO = CustomerPO.findByCompositeKeys(entityCode,
														vehiclePO.getString("CUSTOMER_NO"));
												if (tmCustomerPO != null
														&& tmCustomerPO.getInteger("D_KEY") == CommonConstants.D_KEY) {
													CarownerPO ownerPOcc = CarownerPO.findByCompositeKeys(
															vehiclePO.getString("OWNER_NO"), groupCodeOwner);

													if (tmCustomerPO.getInteger("CUSTOMER_TYPE").toString()
															.equals(DictCodeConstants.DICT_CUSTOMER_TYPE_INDIVIDUAL)) {
														// 个人
														ownerPOcc.setInteger("OWNER_PROPERTY", Integer.parseInt(
																DictCodeConstants.DICT_OWNER_PROPERTY_PERSONAL));
													}
													if (tmCustomerPO.getInteger("CUSTOMER_TYPE").toString()
															.equals(DictCodeConstants.DICT_CUSTOMER_TYPE_COMPANY)) {
														// 公司
														ownerPOcc.setInteger("OWNER_PROPERTY", Integer.parseInt(
																DictCodeConstants.DICT_OWNER_PROPERTY_COMPANY));
													}
													ownerPOcc.setInteger("GENDER", tmCustomerPO.getInteger("GENDER"));// 性别
													ownerPOcc.setDate("BIRTHDAY", tmCustomerPO.getDate("BIRTHDAY"));// 生日
													ownerPOcc.setString("ZIP_CODE", tmCustomerPO.getString("ZIP_CODE"));// 邮编
													ownerPOcc.setInteger("PROVINCE",
															tmCustomerPO.getInteger("PROVINCE"));// 省
													ownerPOcc.setInteger("CITY", tmCustomerPO.getInteger("CITY"));// 城市
													ownerPOcc.setInteger("DISTRICT",
															tmCustomerPO.getInteger("DISTRICT"));// 区县
													ownerPOcc.setString("ADDRESS", tmCustomerPO.getString("ADDRESS"));// 地址
													ownerPOcc.setString("E_MAIL", tmCustomerPO.getString("E_MAIL"));// EMAIL
													ownerPOcc.setString("CERTIFICATE_NO",
															tmCustomerPO.getString("CERTIFICATE_NO"));// 证件号码
													ownerPOcc.setString("HOBBY", tmCustomerPO.getString("HOBBY"));// 爱好
													ownerPOcc.setInteger("INDUSTRY_FIRST",
															tmCustomerPO.getInteger("INDUSTRY_FIRST"));// 行业大类一
													ownerPOcc.setInteger("INDUSTRY_SECOND",
															tmCustomerPO.getInteger("INDUSTRY_SECOND"));// 行业大类二
													ownerPOcc.setString("PHONE",
															tmCustomerPO.getString("CONTACTOR_PHONE"));// 电话
													ownerPOcc.setString("MOBILE",
															tmCustomerPO.getString("CONTACTOR_MOBILE"));// 移动电话
													ownerPOcc.setInteger("FAMILY_INCOME",
															tmCustomerPO.getInteger("FAMILY_INCOME"));// 家庭月收入
													ownerPOcc.setInteger("OWNER_MARRIAGE",
															tmCustomerPO.getInteger("OWNER_MARRIAGE"));// 婚姻状况
													ownerPOcc.setInteger("EDU_LEVEL",
															tmCustomerPO.getInteger("EDUCATION_LEVEL"));// 学历
													ownerPOcc.setDate("FOUND_DATE", tmCustomerPO.getDate("FOUND_DATE"));// 建档日期
													ownerPOcc.setDate("SUBMIT_TIME",
															tmCustomerPO.getDate("SUBMIT_TIME"));// 上报日期
													ownerPOcc.setDate("DOWN_TIMESTAMP",
															tmCustomerPO.getDate("DOWN_TIMESTAMP"));// 下发时序
													ownerPOcc.setLong("IS_UPLOAD", DictCodeConstants.IS_NOT);// 默认为否
													ownerPOcc.setString("OWNER_NAME",
															tmCustomerPO.getString("CUSTOMER_NAME"));// 车主名称
													ownerPOcc.setString("CT_CODE", tmCustomerPO.getString("CT_CODE"));
													ownerPOcc.setLong("IS_UPLOAD_GROUP", DictCodeConstants.IS_NOT);
													ownerPOcc.saveIt();
												}
											} // 2709
										} // 2711
									} // 2714
									System.err.println("1-1-1");
									// 日志
									insertToVehicleLog(map.get("VIN"), outType, map.get("PRODUCT_CODE"),
											map.get("STORAGE_CODE"), map.get("STORAGE_POSITION_CODE"),
											map.get("PURCHASE_PRICE"), map.get("ADDITIONAL_COST"), map.get("MAR_STATUS"),
											DictCodeConstants.DICT_DISPATCHED_STATUS_HAVE_DELIVER + "");
									System.err.println("1-1");
									// 修改明细表里是否入帐,入帐人字段,配车状态为已交车出库，库存状态为出库
									TtVsDeliveryItemPO deliveryItemPOa = TtVsDeliveryItemPO.findByCompositeKeys(
											entityCode, map.get("SD_NO").toString(), map.get("VIN").toString());
									deliveryItemPOa.setLong("IS_FINISHED", DictCodeConstants.IS_YES);
									deliveryItemPOa.setDate("FINISHED_DATE", new Date());
									deliveryItemPOa.setString("FINISHED_BY", userName);
									deliveryItemPOa.setInteger("DISPATCHED_STATUS",
											DictCodeConstants.DICT_DISPATCHED_STATUS_HAVE_DELIVER);
									deliveryItemPOa.setInteger("STOCK_STATUS",
											DictCodeConstants.DICT_STORAGE_STATUS_MOVE_OUT);
									deliveryItemPOa.saveIt();
									System.err.println("1-2");
								}
							}
						}
					} else {
						throw new ServiceBizException("该VIN码{" + map.get("VIN").toString() + "}车辆库存状态不为在库");
					}

					System.err.println("1");

				} // 2858
				String flaggg = "0";
				String sql = "SELECT * FROM TT_VS_DELIVERY_ITEM WHERE SD_NO = ? AND D_KEY = " + CommonConstants.D_KEY;
				List queryParam = new ArrayList();
				queryParam.add(sdNo);
				List<Map> checkList = DAOUtil.findAll(sql, queryParam);
				if (checkList != null && checkList.size() > 0) {
					for (Map itemPO : checkList) {
						if (DictCodeConstants.DICT_IS_NO.equals(itemPO.get("IS_FINISHED").toString())) {
							flaggg = "1";
							break;
						}
					}
				}

				System.err.println(2);

				System.err.println(flaggg);

				if ("0".equals(flaggg)) {
					// 修改主表是否全部入帐字段为是
					TtVsStockDeliveryPO deliveryPO = TtVsStockDeliveryPO.findByCompositeKeys(entityCode, sdNo);
					if (deliveryPO != null && deliveryPO.getInteger("D_KEY") == CommonConstants.D_KEY) {
						deliveryPO.setLong("IS_ALL_FINISHED", DictCodeConstants.IS_YES);
						deliveryPO.saveIt();
					}
				}
			}
			/*
			 * 同保存出库单相同
			 */
			String flagg = "0";
			String sql = "SELECT * FROM TT_VS_DELIVERY_ITEM WHERE SD_NO = ? AND D_KEY = " + CommonConstants.D_KEY;
			List queryParam = new ArrayList();
			queryParam.add(sdNo);
			List<Map> checkList = DAOUtil.findAll(sql, queryParam);
			if (checkList != null && checkList.size() > 0) {
				for (Map itemPO : checkList) {
					if (DictCodeConstants.DICT_IS_NO.equals(itemPO.get("IS_FINISHED").toString())) {
						flagg = "1";
						break;
					}
				}
			}

			if ("0".equals(flagg)) {
				System.err.println("asd");
				// 修改主表是否全部入帐字段为是
				TtVsStockDeliveryPO deliveryPO = TtVsStockDeliveryPO.findByCompositeKeys(entityCode, sdNo);
				if (deliveryPO != null && deliveryPO.getInteger("D_KEY") == CommonConstants.D_KEY) {
					deliveryPO.setLong("IS_ALL_FINISHED", DictCodeConstants.IS_YES);
					deliveryPO.saveIt();
				}
			}
		}
		// DSG1001

		/**
		 * 车辆出库操作产生凭证
		 */
		String defaultValue = Utility.getDefaultValue("8901");
		if (defaultValue != null && (DictCodeConstants.IS_YES + "").equals(defaultValue)) {
			defaultValue = Utility.getDefaultValue("1003");
			float cess = Float.parseFloat(defaultValue);
			for (Map map : list) {
				TtVsDeliveryItemPO vdi = TtVsDeliveryItemPO.findByCompositeKeys(entityCode, map.get("SD_NO").toString(),
						map.get("VIN").toString());
				if (vdi != null && vdi.getInteger("D_KEY") == CommonConstants.D_KEY) {
					TtAccountsTransFlowPO po = new TtAccountsTransFlowPO();
					Long transId = commonNoService.getId("Tt_Accounts_Trans_Flow");
					po.setLong("TRANS_ID", transId);
					po.setString("ORG_CODE", entityCode);
					po.setString("DEALER_CODE", entityCode);
					po.setDate("TRANS_DATE", new Date());
					po.setInteger("TRANS_TYPE", "15880008");
					po.setDouble("TAX_AMOUNT", vdi.getDouble("DIRECTIVE_PRICE"));
					po.setDouble("NET_AMOUNT", vdi.getDouble("DIRECTIVE_PRICE") * (1F - cess));
					if (vdi.getString("SO_NO") != null && !vdi.get("SO_NO").equals(""))
						po.setString("SUB_BUSINESS_NO", vdi.getString("SO_NO"));
					po.setString("BUSINESS_NO", vdi.getString("SD_NO"));
					po.setLong("IS_VALID", DictCodeConstants.IS_YES);
					po.setInteger("EXEC_NUM", 0);
					po.setInteger("EXEC_STATUS", "16980001");// 未生产
					po.saveIt();
				}
			}
		}
	}

	/**
	 * 出库子表信息
	 * 
	 * @author yangjie
	 * @date 2017年2月15日
	 * @param sdNo
	 * @param vin
	 * @param productCode
	 * @return (non-Javadoc)
	 * @see com.yonyou.dms.vehicle.service.basedata.StockOutService#findAllOutDetails(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	public List<Map> findAllOutDetails(String sdNo, String vin, String productCode) {
		String entityCode = FrameworkUtil.getLoginInfo().getDealerCode();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT '" + sdNo + "' AS sdNo,'" + vin);
		sql.append(
				"' AS outType,a.DEALER_CODE,0 AS IS_SELECTED,a.SD_NO,a.VIN,a.PRODUCT_CODE,a.STORAGE_CODE,C.STORAGE_NAME,a.STORAGE_POSITION_CODE,a.PURCHASE_PRICE,a.ADDITIONAL_COST,");
		sql.append(
				"a.VEHICLE_PRICE,a.STOCK_STATUS,a.DISPATCHED_STATUS,a.SO_NO,a.MAR_STATUS,a.IS_SECONDHAND,a.IS_VIP,a.IS_TEST_DRIVE_CAR,");
		sql.append(
				"a.IS_CONSIGNED,a.IS_PROMOTION,a.IS_PURCHASE_RETURN,a.IS_PRICE_ADJUSTED,a.ADJUST_REASON,a.OLD_DIRECTIVE_PRICE,");
		sql.append(
				"a.DIRECTIVE_PRICE,a.OEM_TAG,a.REMARK,a.IS_FINISHED,a.FINISHED_DATE,a.FINISHED_BY,b.WARRANTY_NUMBER ");
		sql.append(" FROM TT_VS_DELIVERY_ITEM a ");
		sql.append(" join tm_vs_stock b on  a.vin=b.vin and a.DEALER_CODE=b.DEALER_CODE ");
		sql.append(
				" LEFT JOIN TM_STORAGE C ON a.DEALER_CODE=C.DEALER_CODE AND a.STORAGE_CODE=C.STORAGE_CODE WHERE a.DEALER_CODE='"
						+ entityCode + "' AND a.D_KEY=" + CommonConstants.D_KEY + "  ");
		Utility.sqlToLike(sql, sdNo, "SD_NO", "a");
		if (!vin.equals("0")) {// 前台有传参数0,以示区别
			Utility.sqlToEquals(sql, vin, "VIN", "a");
		}
		// Utility.sqlToLike(sql, productCode, "PRODUCT_CODE", "a");
		return DAOUtil.findAll(sql.toString(), null);
	}

	/**
	 * 查询行业大类
	 * 
	 * @author yangjie
	 * @date 2017年2月14日
	 * @return (non-Javadoc)
	 * @see com.yonyou.dms.vehicle.service.basedata.StockOutService#findIndustryFirst()
	 */
	@Override
	public List<Map> findIndustryFirst() {
		String sql = "SELECT * FROM TC_CODE WHERE TYPE_NAME = '行业大类'";
		List<Map> findAll = Base.findAll(sql);
		return findAll;
	}

	/**
	 * 查询行业小类
	 * 
	 * @author yangjie
	 * @date 2017年2月14日
	 * @param firsh
	 * @return (non-Javadoc)
	 * @see com.yonyou.dms.vehicle.service.basedata.StockOutService#findIndustrySecond(java.lang.String)
	 */
	@Override
	public List<Map> findIndustrySecond(String firsh) {
		String sql = "SELECT * FROM TC_CODE WHERE TYPE_NAME = '" + firsh + "'";
		return Base.findAll(sql);
	}

	/**
	 * 查询保有客户信息
	 * 
	 * @author yangjie
	 * @date 2017年2月14日
	 * @param Vin
	 * @param SoNo
	 * @return (non-Javadoc)
	 * @see com.yonyou.dms.vehicle.service.basedata.StockOutService#findCustomerInfo(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public Map findCustomerInfo(String Vin, String SoNo) {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		String groupCodeVehicle = Utility.getGroupEntity(dealerCode, "TM_VEHICLE");
		List<Map> list = checkOwnerInfo(Vin);
		Map queryCustomerInfoByVin = new HashMap();
		String flag = "";
		if (list != null && list.size() > 0) {
			// 保有客户
			String sql = "SELECT * FROM TM_VEHICLE WHERE DEALER_CODE = ? AND VIN = ?";
			List queryParam = new ArrayList();
			queryParam.add(groupCodeVehicle);
			queryParam.add(Vin);
			List<Map> alist = DAOUtil.findAll(sql, queryParam);
			alist = Utility.getVehicleSubclassList(dealerCode, alist);
			Map vehiclePO = alist.get(0);
			CustomerPO customerPO = CustomerPO.findByCompositeKeys(dealerCode, vehiclePO.get("CUSTOMER_NO").toString());
			if (customerPO != null && customerPO.getInteger("D_KEY") == CommonConstants.D_KEY) {
				queryCustomerInfoByVin = queryCustomerInfoByVin(Vin, dealerCode,
						vehiclePO.get("CUSTOMER_NO").toString(), DictCodeConstants.DICT_IS_YES, SoNo);
			}
			flag = DictCodeConstants.DICT_IS_YES;
		} else {
			// 潜在客户
			String sql = "select * from tt_sales_order where vin = ? and so_no = ?";
			List queryParam = new ArrayList();
			queryParam.add(Vin);
			queryParam.add(SoNo);
			List<Map> salesList = DAOUtil.findAll(sql, queryParam);
			if (salesList != null && salesList.size() > 0) {
				Map map = salesList.get(0);
				queryCustomerInfoByVin = queryCustomerInfoByVin(Vin, dealerCode, map.get("CUSTOMER_NO").toString(),
						DictCodeConstants.DICT_IS_NO, SoNo);
			}
			flag = DictCodeConstants.DICT_IS_NO;
		}
		return queryCustomerInfoByVin;
	}

	/************************************************************** 出库操作相关 ***************************************************************/

	// 新增订单审核历史
	public TtOrderStatusUpdatePO setOrderStatusPO(String ENTITY_CODE, String SO_NO, String SO_STATUS, long user_Id)
			throws ServiceBizException {
		TtOrderStatusUpdatePO apo = new TtOrderStatusUpdatePO();
		SalesOrderPO apoquery = SalesOrderPO.findByCompositeKeys(ENTITY_CODE, SO_NO);
		if (apoquery != null && apoquery.getInteger("D_KEY") == CommonConstants.D_KEY) {
			if (apoquery.getInteger("BUSINESS_TYPE") != null && (apoquery.getInteger("BUSINESS_TYPE").toString()
					.equals(DictCodeConstants.DICT_SO_TYPE_ENTRUST_DELIVERY)
					|| apoquery.getInteger("BUSINESS_TYPE").toString().equals(DictCodeConstants.DICT_SO_TYPE_GENERAL))

			) {
				apo.setString("DEALER_CODE", ENTITY_CODE);
				apo.setString("SO_NO", SO_NO);
				apo.setInteger("SO_STATUS", Integer.parseInt(SO_STATUS));
				apo.setLong("OWNED_BY", user_Id);
				apo.setInteger("IS_UPLOAD", Integer.parseInt(DictCodeConstants.DICT_IS_NO));
				apo.setDate("ALTERATION_TIME", new Date());
				apo.setInteger("D_KEY", CommonConstants.D_KEY);
				apo.saveIt();
			}
		}

		return apo;
	}

	/**
	 * 自动生成CR关怀
	 */
	public void SystemTrack(String cusNo, String vin, String entitycode, long userid) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		/**
		 * 先把该客户以前生成的没有跟进结果的跟进记录删除
		 */
		TtSalesPromotionPlanPO planPO = new TtSalesPromotionPlanPO();
		planPO.delete("DEALER_CODE = ? AND CUSTOMER_NO = ? AND (PROM_RESULT IS NULL OR PROM_RESULT=0) ", entitycode,
				cusNo);
		//
		CustomerPO tmcus2 = CustomerPO.findByCompositeKeys(entitycode, cusNo);
		String groupCode = Utility.getGroupEntity(entitycode, "TM_TRACKING_TASK");
		String sql = "SELECT * FROM TM_TRACKING_TASK WHERE DEALER_CODE = ? AND CUSTOMER_STATUS = ?";
		List queryParam = new ArrayList();
		queryParam.add(groupCode);
		queryParam.add(DictCodeConstants.DICT_CUSTOMER_STATUS_TENURE);
		List<Map> list22 = DAOUtil.findAll(sql, queryParam);

		// 如果保有客户已经生产关怀，系统就在不在自动生成。
		int ic = 0;
		if (list22 != null && list22.size() > 0) {
			for (int ig = 0; ig < list22.size(); ig++) {
				ic += 1;
			}
		}
		int ics = 0;
		if (cusNo != null && !"".equals(cusNo) && vin != null && !"".equals(vin)) {
			sql = "SELECT * FROM TT_SALES_CR WHERE CUSTOMER_NO = ? AND VIN = ? AND CREATE_TYPE = ? AND D_KEY = "
					+ CommonConstants.D_KEY;
			queryParam = new ArrayList();
			queryParam.add(cusNo);
			queryParam.add(vin);
			queryParam.add(DictCodeConstants.DICT_CREATE_TYPE_SYSTEM);
			List<Map> salcrpo22list = DAOUtil.findAll(sql, queryParam);

			if (salcrpo22list != null && salcrpo22list.size() > 0) {
				for (int ih = 0; ih < salcrpo22list.size(); ih++) {
					ics += 1;
				}
			}
		}

		if (list22 != null && ic > 0 && ics == 0) {
			for (Map tt2 : list22) {
				String dates = new String();

				if (tt2.get("INTERVAL_DAYS") != null && !"".equals(tt2.get("INTERVAL_DAYS").toString())
						&& !tt2.get("INTERVAL_DAYS").toString().equals("0")) {
					c.setTime(new Date());
					c.add(c.DAY_OF_WEEK, Integer.parseInt(tt2.get("INTERVAL_DAYS").toString()));
					dates = format.format(c.getTime()).toString();

				}
				TtSalesCrPO salcrpo = new TtSalesCrPO();
				salcrpo.setString("DEALER_CODE", entitycode);
				salcrpo.setString("CUSTOMER_NO", cusNo);
				if (dates != null && !"".equals(dates)) {
					salcrpo.setDate("SCHEDULE_DATE", strToDate(dates));
				}
				salcrpo.setInteger("CREATE_TYPE", Integer.parseInt(DictCodeConstants.DICT_CREATE_TYPE_SYSTEM));
				if (tt2.get("TASK_CONTENT") != null && !"".equals(tt2.get("TASK_CONTENT").toString())) {
					salcrpo.setString("CR_CONTEXT", tt2.get("TASK_CONTENT").toString());// 关怀内容
				}

				if (tt2.get("EXECUTE_TYPE") != null && !"".equals(tt2.get("EXECUTE_TYPE").toString())) {
					salcrpo.setInteger("CR_TYPE", tt2.get("EXECUTE_TYPE"));// 关怀方式
				}
				if (tt2.get("TASK_NAME") != null && !"".equals(tt2.get("TASK_NAME").toString())) {
					salcrpo.setString("CR_NAME", tt2.get("TASK_NAME").toString());// 关怀名称
				}
				if (tt2.get("TASK_ID") != null && !"".equals(tt2.get("TASK_ID").toString())) {
					salcrpo.setInteger("TASK_ID", tt2.get("TASK_ID"));// 关怀ID
				}

				// 联系人
				sql = "SELECT * FROM TT_CUSTOMER_LINKMAN_INFO WHERE CUSTOMER_NO = ? AND D_KEY = "
						+ CommonConstants.D_KEY;
				queryParam = new ArrayList();
				queryParam.add(cusNo);
				List<Map> listcustomer = DAOUtil.findAll(sql, queryParam);
				if (listcustomer != null && listcustomer.size() > 0) {
					for (Map customer2 : listcustomer) {

						if (customer2.get("PHONE") != null && !"".equals(customer2.get("PHONE").toString())) {
							salcrpo.setString("LINK_PHONE", customer2.get("PHONE").toString());
						}
						if (customer2.get("MOBILE") != null && !"".equals(customer2.get("MOBILE").toString())) {
							salcrpo.setString("LINK_MOBILE", customer2.get("Mobile").toString());
						}
						if (customer2.get("CONTACTOR_NAME") != null
								&& !"".equals(customer2.get("CONTACTOR_NAME").toString())) {
							salcrpo.setString("CR_LINKER", customer2.get("CONTACTOR_NAME").toString());
						}
					}
				}
				if (tt2.get("TASK_CONTENT") != null && !"".equals(tt2.get("TASK_CONTENT").toString())) {
					salcrpo.setString("CR_CONTEXT", tt2.get("TASK_CONTENT").toString());
				}

				salcrpo.setString("VIN", vin);
				salcrpo.setString("SOLD_BY", tmcus2.getString("SOLD_BY"));
				salcrpo.setString("OWNED_BY", tmcus2.getString("SOLD_BY"));
				salcrpo.saveIt();
			}
		}

	}

	/**
	 * 根据跟进任务定义中意向级别，及客户信息的意向级别和客户状态在跟进活动中自动生成跟进记录记录 entityCode cusNo
	 * intentLevel 意向级别 userId
	 */
	public void saveProPlanOrCr(String entityCode, String cusNo, int intentLevel, Long userId, String empNO) {
		/**
		 * 先把该客户以前生成的没有跟进结果的跟进记录删除
		 */
		TtSalesPromotionPlanPO planPO = new TtSalesPromotionPlanPO();
		planPO.delete("DEALER_CODE = ? AND CUSTOMER_NO = ? AND (PROM_RESULT IS NULL OR PROM_RESULT=0) ", entityCode,
				cusNo);
		//
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();

		PotentialCusPO tmcus2 = PotentialCusPO.findByCompositeKeys(entityCode, cusNo);
		String groupCode = Utility.getGroupEntity(entityCode, "TM_TRACKING_TASK");
		// 在新增客户资料主表手工创建跟进活动
		String sql = "SELECT * FROM TM_TRACKING_TASK WHERE DEALER_CODE = ? AND INTENT_LEVEL = ? AND CUSTOMER_STATUS = ? AND IS_VALID = "
				+ DictCodeConstants.IS_YES;
		List queryParam = new ArrayList();
		queryParam.add(groupCode);
		queryParam.add(intentLevel);
		queryParam.add(DictCodeConstants.DICT_CUSTOMER_STATUS_LATENCY);
		List<Map> list22 = DAOUtil.findAll(sql, queryParam);
		/**
		 * 根据客户编号查询未完成的意向ID
		 */
		if (list22 != null && list22.size() > 0) {
			for (Map tt2 : list22) {
				String dates = new String();
				if (tt2.get("INTERVAL_DAYS") != null && !"".equals(tt2.get("INTERVAL_DAYS").toString())
						&& !tt2.get("INTERVAL_DAYS").toString().equals("0")) {
					c.setTime(new Date());
					c.add(c.DAY_OF_WEEK, Integer.parseInt(tt2.get("INTERVAL_DAYS").toString()));
					dates = format.format(c.getTime()).toString();
				}
				TtSalesPromotionPlanPO spro = new TtSalesPromotionPlanPO();
				spro.setString("DEALER_CODE", entityCode);
				spro.setString("INTENT_ID", tmcus2.getString("INTENT_ID"));
				spro.setString("CUSTOMER_NO", cusNo);
				/*
				 * 添加了跟进任务定义表的主键
				 */
				spro.setInteger("TASK_ID", tt2.get("TASK_ID"));
				if (null != tmcus2.getString("CUSTOMER_NAME") && !("".equals(tmcus2.get("CUSTOMER_NAME").toString()))) {
					spro.setString("CUSTOMER_NAME", tmcus2.getString("CUSTOMER_NAME"));
				}
				// 促进前级别
				spro.setInteger("PRIOR_GRADE", tt2.get("INTENT_LEVEL"));

				if (dates != null && !"".equals(dates)) {
					spro.setDate("SCHEDULE_DATE", strToDate(dates));
				}
				// 跟进任务中的任务内容添加到跟进活动内容
				if (tt2.get("TASK_CONTENT") != null && !"".equals(tt2.get("TASK_CONTENT").toString())) {
					spro.setString("PROM_CONTENT", tt2.get("TASK_CONTENT").toString());
				}
				// 跟进任务中的执行方式添加到跟进活动跟进方式
				if (tt2.get("EXECUTE_TYPE") != null && !"".equals(tt2.get("EXECUTE_TYPE").toString())
						&& !tt2.get("EXECUTE_TYPE").toString().equals("0")) {
					spro.setInteger("PROM_WAY", tt2.get("EXECUTE_TYPE"));
				}
				// 创建方式为系统创建
				spro.setInteger("CREATE_TYPE", Integer.parseInt(DictCodeConstants.DICT_CREATE_TYPE_SYSTEM));

				// 联系人
				sql = "select * from tt_po_cus_linkman where customer_no = ? and d_key = " + CommonConstants.D_KEY;
				queryParam = new ArrayList();
				queryParam.add(cusNo);
				List<Map> listcustomer = DAOUtil.findAll(sql, queryParam);
				if (listcustomer != null) {
					Map customer2 = listcustomer.get(0);
					if (customer2.get("CONTACTOR_NAME") != null
							&& !"".equals(customer2.get("CONTACTOR_NAME").toString())) {
						spro.setString("CONTACTOR_NAME", customer2.get("CONTACTOR_NAME").toString());
					}
					if (customer2.get("PHONE") != null && !"".equals(customer2.get("PHONE").toString())) {
						spro.setString("PHONE", customer2.get("PHONE").toString());
					}
					if (customer2.get("MOBILE") != null && !"".equals(customer2.get("MOBILE").toString())) {
						spro.setString("MOBILE", customer2.get("MOBILE").toString());
					}
				}
				spro.setInteger("IS_AUDITING", Integer.parseInt(DictCodeConstants.DICT_IS_NO));
				spro.setInteger("SOLD_BY", tmcus2.getInteger("SOLD_BY"));
				spro.setInteger("OWNED_BY", tmcus2.getInteger("SOLD_BY"));
				spro.saveIt();// 新增销售活动
			}
		}

	}

	public List<Map> checkOwnerInfo(String entityCode, String vin) {
		StringBuffer sql = new StringBuffer("");
		sql.append(" SELECT A.* FROM (" + CommonConstants.VM_VEHICLE + ") A,(" + CommonConstants.VM_OWNER);
		sql.append(") B WHERE A.DEALER_CODE=B.DEALER_CODE AND A.OWNER_NO=B.OWNER_NO  AND A.VIN='" + vin);
		sql.append("' AND A.DEALER_CODE='" + entityCode);
		sql.append("' AND A.CUSTOMER_NO IS NOT NULL AND LENGTH(TRIM(A.CUSTOMER_NO)) <=12");
		return DAOUtil.findAll(sql.toString(), null);
	}

	/**
	 * 判断是否有保有客户信息根据vin从TM_VEHICLE 没有则新增,维护潜在客户保有客户关系表
	 * 
	 * @throws Exception
	 */
	public String insertIntoCustomer(String entityCode, String vin, String soNo, String customerNoP, long userId)
			throws ServiceBizException {
		List<Map> list = checkOwnerInfo(entityCode, vin);
		String groupCodeVehicle = Utility.getGroupEntity(entityCode, "TM_VEHICLE");

		String customerNO = "";// 保有客户编号;1.新生成的。2.已经存在的
		if (list != null && list.size() > 0) {
			// 有保有客户信息
			String sql = "SELECT * FROM TM_VEHICLE WHERE VIN = ? AND DEALER_CODE = ?";
			List queryParam = new ArrayList();
			queryParam.add(vin);
			queryParam.add(groupCodeVehicle);
			List<Map> vehicleList = DAOUtil.findAll(sql, queryParam);
			vehicleList = Utility.getVehicleSubclassList(entityCode, vehicleList);

			if (vehicleList != null && vehicleList.size() > 0) {
				customerNO = vehicleList.get(0).get("CUSTOMER_NO").toString();
			}
		} else {
			// 没有保有客户信息,新增保有客户信息,根据出库单里的销售订单，查销售订单中的客户编号,把潜在客户信息带出保存为保有客户信息
			SalesOrderPO orderPO = SalesOrderPO.findByCompositeKeys(entityCode, soNo);
			if (orderPO != null && orderPO.getInteger("D_KEY") == CommonConstants.D_KEY) {
				PotentialCusPO customerPOp = PotentialCusPO.findByCompositeKeys(entityCode,
						orderPO.getString("CUSTOMER_NO"));
				if (customerPOp != null && customerPOp.getInteger("D_KEY") == CommonConstants.D_KEY) {
					CustomerPO customerPOinsert = new CustomerPO();
					customerNO = commonNoService.getSystemOrderNo(CommonConstants.CUSTOMER_PREFIX);
					customerPOinsert.setString("CUSTOMER_NO", customerNO);
					customerPOinsert.setString("DEALER_CODE", entityCode);
					customerPOinsert.setString("CUSTOMER_NAME", customerPOp.getString("CUSTOMER_NAME"));
					customerPOinsert.setString("LARGE_CUSTOMER_NO", customerPOp.getString("LARGE_CUSTOMER_NO"));
					customerPOinsert.setString("SOD_CUSTOMER_ID", customerPOp.getString("SOD_CUSTOMER_ID"));
					customerPOinsert.setInteger("CUSTOMER_TYPE", customerPOp.getInteger("CUSTOMER_TYPE"));
					customerPOinsert.setInteger("GENDER", customerPOp.getInteger("GENDER"));
					customerPOinsert.setDate("BIRTHDAY", customerPOp.getDate("BIRTHDAY"));
					customerPOinsert.setString("ZIP_CODE", customerPOp.getString("ZIP_CODE"));
					customerPOinsert.setInteger("COUNTRY_CODE", customerPOp.getInteger("COUNTRY_CODE"));
					customerPOinsert.setInteger("PROVINCE", customerPOp.getInteger("PROVINCE"));
					customerPOinsert.setInteger("CITY", customerPOp.getInteger("CITY"));
					customerPOinsert.setInteger("DISTRICT", customerPOp.getInteger("DISTRICT"));
					customerPOinsert.setString("ADDRESS", customerPOp.getString("ADDRESS"));
					customerPOinsert.setString("CONTACTOR_PHONE", customerPOp.getString("CONTACTOR_PHONE"));
					customerPOinsert.setString("CONTACTOR_MOBILE", customerPOp.getString("CONTACTOR_MOBILE"));
					customerPOinsert.setString("FAX", customerPOp.getString("FAX"));
					customerPOinsert.setString("E_MAIL", customerPOp.getString("E_MAIL"));
					customerPOinsert.setInteger("BEST_CONTACT_TYPE", customerPOp.getInteger("BEST_CONTACT_TYPE"));
					customerPOinsert.setInteger("CT_CODE", customerPOp.getInteger("CT_CODE"));
					customerPOinsert.setString("CERTIFICATE_NO", customerPOp.getString("CERTIFICATE_NO"));
					customerPOinsert.setInteger("EDUCATION_LEVEL", customerPOp.getInteger("EDUCATION_LEVEL"));
					customerPOinsert.setInteger("OWNER_MARRIAGE", customerPOp.getInteger("OWNER_MARRIAGE"));
					customerPOinsert.setString("HOBBY", customerPOp.getString("HOBBY"));
					customerPOinsert.setInteger("INDUSTRY_FIRST", customerPOp.getInteger("INDUSTRY_FIRST"));
					customerPOinsert.setInteger("INDUSTRY_SECOND", customerPOp.getInteger("INDUSTRY_SECOND"));
					customerPOinsert.setString("ORGAN_TYPE", customerPOp.getString("ORGAN_TYPE"));
					customerPOinsert.setInteger("ORGAN_TYPE_CODE", customerPOp.getInteger("ORGAN_TYPE_CODE"));
					customerPOinsert.setInteger("VOCATION_TYPE", customerPOp.getInteger("VOCATION_TYPE"));
					customerPOinsert.setString("POSITION_NAME", customerPOp.getString("POSITION_NAME"));
					customerPOinsert.setInteger("FAMILY_INCOME", customerPOp.getInteger("FAMILY_INCOME"));
					customerPOinsert.setInteger("AGE_STAGE", customerPOp.getInteger("AGE_STAGE"));
					customerPOinsert.setInteger("IS_CRPVIP", customerPOp.getInteger("IS_CRPVIP"));
					customerPOinsert.setInteger("IS_FIRST_BUY", customerPOp.getInteger("IS_FIRST_BUY"));
					customerPOinsert.setInteger("HAS_DRIVER_LICENSE", customerPOp.getInteger("HAS_DRIVER_LICENSE"));
					customerPOinsert.setInteger("IS_PERSON_DRIVE_CAR", customerPOp.getInteger("IS_PERSON_DRIVE_CAR"));
					customerPOinsert.setInteger("BUY_PURPOSE", customerPOp.getInteger("BUY_PURPOSE"));
					customerPOinsert.setInteger("CHOICE_REASON", customerPOp.getInteger("CHOICE_REASON"));
					customerPOinsert.setString("BUY_REASON", customerPOp.getString("BUY_REASON"));
					customerPOinsert.setInteger("CUS_SOURCE", customerPOp.getInteger("CUS_SOURCE"));
					customerPOinsert.setInteger("MEDIA_TYPE", customerPOp.getInteger("MEDIA_TYPE"));
					customerPOinsert.setString("CAMPAIGN_CODE", customerPOp.getString("CAMPAIGN_CODE"));
					customerPOinsert.setInteger("INTENT_LEVEL", customerPOp.getInteger("INTENT_LEVEL"));
					customerPOinsert.setInteger("INIT_LEVEL", customerPOp.getInteger("INIT_LEVEL"));
					customerPOinsert.setInteger("FAIL_CONSULTANT", customerPOp.getInteger("FAIL_CONSULTANT"));
					customerPOinsert.setInteger("DELAY_CONSULTANT", customerPOp.getInteger("DELAY_CONSULTANT"));
					customerPOinsert.setDate("CONSULTANT_TIME", customerPOp.getDate("CONSULTANT_TIME"));
					customerPOinsert.setLong("SOLD_BY", orderPO.getLong("SOLD_BY"));// 销售顾问
					customerPOinsert.setString("DCRC_SERVICE", customerPOp.getString("DCRC_SERVICE"));
					customerPOinsert.setInteger("IS_WHOLESALER", customerPOp.getInteger("IS_WHOLESALER"));
					customerPOinsert.setInteger("IS_DIRECT", customerPOp.getInteger("IS_DIRECT"));
					customerPOinsert.setInteger("IS_UPLOAD", customerPOp.getInteger("IS_UPLOAD"));
					customerPOinsert.setString("RECOMMEND_EMP_NAME", customerPOp.getString("RECOMMEND_EMP_NAME"));
					customerPOinsert.setDouble("GATHERED_SUM", customerPOp.getDouble("GATHERED_SUM"));
					customerPOinsert.setDouble("ORDER_PAYED_SUM", customerPOp.getDouble("ORDER_PAYED_SUM"));
					customerPOinsert.setDouble("CON_PAYED_SUM", customerPOp.getDouble("CON_PAYED_SUM"));
					customerPOinsert.setDouble("USABLE_AMOUNT", customerPOp.getDouble("USABLE_AMOUNT"));
					customerPOinsert.setDouble("UN_WRITEOFF_SUM", customerPOp.getDouble("UN_WRITEOFF_SUM"));
					customerPOinsert.setString("MODIFY_REASON", customerPOp.getString("MODIFY_REASON"));
					customerPOinsert.setInteger("IS_REPORTED", customerPOp.getInteger("IS_REPORTED"));
					customerPOinsert.setString("REPORT_REMARK", customerPOp.getString("REPORT_REMARK"));
					customerPOinsert.setDate("REPORT_DATETIME", customerPOp.getDate("REPORT_DATETIME"));
					customerPOinsert.setInteger("REPORT_STATUS", customerPOp.getInteger("REPORT_STATUS"));
					customerPOinsert.setString("REPORT_AUDITING_REMARK",
							customerPOp.getString("REPORT_AUDITING_REMARK"));
					customerPOinsert.setString("REPORT_ABORT_REASON", customerPOp.getString("REPORT_ABORT_REASON"));
					customerPOinsert.setDate("DOWN_TIMESTAMP", customerPOp.getDate("DOWN_TIMESTAMP"));
					customerPOinsert.setDate("SUBMIT_TIME", customerPOp.getDate("SUBMIT_TIME"));
					customerPOinsert.setDate("FOUND_DATE", customerPOp.getDate("FOUND_DATE"));
					customerPOinsert.setString("REMARK", customerPOp.getString("REMARK"));
					customerPOinsert.setLong("OWNED_BY", orderPO.getLong("SOLD_BY"));// 销售顾问
					customerPOinsert.setInteger("D_KEY", CommonConstants.D_KEY);
					customerPOinsert.saveIt();
					// 把潜在客户联系人信息更新到保有客户联系人表
					insertCustomerLinkMan(entityCode, customerNoP, customerNO, userId);
					// 维护保有客户和潜在客户关系表
					TtPoCusRelationPO relationPO = new TtPoCusRelationPO();
					relationPO.setString("DEALER_CODE", entityCode);
					relationPO.setString("CUSTOMER_NO", customerNO);// 保佑客户编号
					relationPO.setString("PO_CUSTOMER_NO", customerNoP);// 潜在客户编号
					relationPO.setString("VIN", vin);
					relationPO.setString("SO_NO", soNo);
					relationPO.saveIt();
				}
			}
		}
		return customerNO;
	}

	/**
	 * 新增保佑客户信息，需要把潜在客户联系人信息表内容，写入新增的保佑客户信息联系人表
	 */
	public void insertCustomerLinkMan(String entityCode, String customerNo, String customerNoBao, long userId)
			throws ServiceBizException {
		String sql = "SELECT * FROM TT_PO_CUS_LINKMAN WHERE CUSTOMER_NO = ? AND D_KEY = " + CommonConstants.D_KEY;
		List queryParam = new ArrayList();
		queryParam.add(customerNo);
		List<Map> list = DAOUtil.findAll(sql, queryParam);
		TtCustomerLinkmanInfoPO infoPO = null;
		if (list != null && list.size() > 0) {
			for (Map linkmanPO2 : list) {
				infoPO = new TtCustomerLinkmanInfoPO();
				infoPO.setString("DEALER_CODE", linkmanPO2.get("DEALER_CODE").toString());
				infoPO.setString("CUSTOMER_NO", customerNoBao);
				infoPO.setInteger("D_KEY", CommonConstants.D_KEY);
				if (!StringUtils.isNullOrEmpty(linkmanPO2.get("COMPANY"))) {
					infoPO.setString("COMPANY", linkmanPO2.get("COMPANY").toString());// 单位名称
				}
				if (!StringUtils.isNullOrEmpty(linkmanPO2.get("CONTACTOR_DEPARTMENT"))) {
					infoPO.setString("CONTACTOR_DEPARTMENT", linkmanPO2.get("CONTACTOR_DEPARTMENT").toString());// 所属部门
				}
				if (!StringUtils.isNullOrEmpty(linkmanPO2.get("POSITION_NAME"))) {
					infoPO.setString("POSITION_NAME", linkmanPO2.get("POSITION_NAME").toString());// 职务名称
				}
				if (!StringUtils.isNullOrEmpty(linkmanPO2.get("CONTACTOR_NAME"))) {
					infoPO.setString("CONTACTOR_NAME", linkmanPO2.get("CONTACTOR_NAME"));// 联系人名称
				}
				if (!StringUtils.isNullOrEmpty(linkmanPO2.get("IS_DEFAULT_CONTACTOR"))) {
					infoPO.setInteger("IS_DEFAULT_CONTACTOR", linkmanPO2.get("IS_DEFAULT_CONTACTOR"));// 默认联系人
				}
				if (!StringUtils.isNullOrEmpty(linkmanPO2.get("BEST_CONTACT_TIME"))) {
					infoPO.setInteger("BEST_CONTACT_TIME", linkmanPO2.get("BEST_CONTACT_TIME"));// 最佳联系时间
				}
				if (!StringUtils.isNullOrEmpty(linkmanPO2.get("BEST_CONTACT_TYPE"))) {
					infoPO.setInteger("BEST_CONTACT_TYPE", linkmanPO2.get("BEST_CONTACT_TYPE"));// 最佳联系方式
				}
				if (!StringUtils.isNullOrEmpty(linkmanPO2.get("CONTACTOR_TYPE"))) {
					infoPO.setInteger("CONTACTOR_TYPE", linkmanPO2.get("CONTACTOR_TYPE"));// 联系人类型
				}
				if (!StringUtils.isNullOrEmpty(linkmanPO2.get("GENDER"))) {
					infoPO.setInteger("GENDER", linkmanPO2.get("GENDER"));// 性别
				}
				if (!StringUtils.isNullOrEmpty(linkmanPO2.get("PHONE"))) {
					infoPO.setString("PHONE", linkmanPO2.get("PHONE").toString());// 电话
				}
				if (!StringUtils.isNullOrEmpty(linkmanPO2.get("MOBILE"))) {
					infoPO.setString("MOBILE", linkmanPO2.get("MOBILE").toString());// 手机
				}
				if (!StringUtils.isNullOrEmpty(linkmanPO2.get("FAX"))) {
					infoPO.setString("FAX", linkmanPO2.get("FAX").toString());// 传真
				}
				if (!StringUtils.isNullOrEmpty(linkmanPO2.get("E_MAIL"))) {
					infoPO.setString("E_MAIL", linkmanPO2.get("E_MAIL").toString());// E-mail
				}
				if (!StringUtils.isNullOrEmpty(linkmanPO2.get("REMARK"))) {
					infoPO.setString("REMARK", linkmanPO2.get("REMARK").toString());// 备注
				}
				infoPO.saveIt();
			}
		}
	}

	/**
	 * 客户信息未到店提示
	 */
	public int showMsgCustomerIsNotArrived(String entityCode, String soNo) throws ServiceBizException {
		// 客户信息为未到店不能做出库操作
		SalesOrderPO selSalesOrderPO = SalesOrderPO.findByCompositeKeys(entityCode, soNo);
		if (selSalesOrderPO != null && selSalesOrderPO.getInteger("D_KEY") == CommonConstants.D_KEY) {
			PotentialCusPO selPotenCusPO = PotentialCusPO.findByCompositeKeys(entityCode,
					selSalesOrderPO.getString("CUSTOMER_NO"));
			if (selPotenCusPO != null && selPotenCusPO.getInteger("D_KEY") == CommonConstants.D_KEY) {
				if (!DictCodeConstants.DICT_IS_YES.equals(selPotenCusPO.getString("IS_TEH_SHOP"))) {
					return 0;
				} else {
					return 1;
				}
			}
		}
		return 1;
	}

	/**
	 * 新增到日志信息 TODO description
	 * 
	 * @author yangjie
	 * @date 2017年2月3日
	 * @param vin
	 * @param stockOutType
	 * @param productCode
	 * @param storageCode
	 * @param storagePositionCode
	 * @param purchasePrice
	 * @param additionCost
	 * @param marStatus
	 * @param dispatch
	 * @throws ServiceBizException
	 */
	public void insertToVehicleLog(Object vin, Object stockOutType, Object productCode, Object storageCode,
			Object storagePositionCode, Object purchasePrice, Object additionCost, Object marStatus, String dispatch)
			throws ServiceBizException {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		VsStockLogPO logPO = new VsStockLogPO();
		logPO.setString("DEALER_CODE", dealerCode);
		logPO.setInteger("D_KEY", CommonConstants.D_KEY);
		logPO.setString("VIN", vin);
		VsProductPO productPO = VsProductPO.findByCompositeKeys(dealerCode, productCode);
		if (productPO != null && productPO.getInteger("D_KEY") == CommonConstants.D_KEY) {
			logPO.setDouble("DIRECTIVE_PRICE", productPO.getDouble("DIRECTIVE_PRICE"));// 销售指导价
			logPO.setDouble("PURCHASE_PRICE", productPO.getDouble("LATEST_PURCHASE_PRICE"));// 最新采购价格
		}
		if (!StringUtils.isNullOrEmpty(productCode)) {
			logPO.setString("PRODUCT_CODE", productCode);
		}
		logPO.setDate("OPERATE_DATE", new Date());
		logPO.setInteger("OPERATION_TYPE", Integer.parseInt(stockOutType.toString()));
		if (!StringUtils.isNullOrEmpty(storageCode)) {
			logPO.setString("STORAGE_CODE", storageCode);
		}
		if (!StringUtils.isNullOrEmpty(storagePositionCode)) {
			logPO.setString("STORAGE_POSITION_CODE", storagePositionCode);
		}
		if (!StringUtils.isNullOrEmpty(purchasePrice)) {
			logPO.setDouble("PURCHASE_PRICE", Double.parseDouble(purchasePrice.toString()));
		}
		if (!StringUtils.isNullOrEmpty(additionCost)) {
			logPO.setDouble("ADDITIONAL_COST", Double.parseDouble(additionCost.toString()));
		}
		logPO.setInteger("STOCK_STATUS", DictCodeConstants.DICT_STORAGE_STATUS_MOVE_OUT);
		if (!StringUtils.isNullOrEmpty(marStatus)) {
			logPO.setInteger("MAR_STATUS", marStatus);
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
			logPO.setString("ADJUST_REASON", stockPO.getString("ADJUST_REASON"));// 调价原因
			logPO.setDouble("OLD_DIRECTIVE_PRICE", stockPO.getDouble("OLD_DIRECTIVE_PRICE"));// 愿销售指导价
			logPO.setDouble("DIRECTIVE_PRICE", stockPO.getDouble("DIRECTIVE_PRICE"));// 销售价
			logPO.setInteger("OEM_TAG", stockPO.getInteger("OEM_TAG"));// OEM_TAG标记
		}
		if ((DictCodeConstants.DICT_STOCK_OUT_TYPE_UNTREAD+"").equals(stockOutType.toString())) {// 新车采购
			logPO.setInteger("OPERATION_TYPE", DictCodeConstants.DICT_VEHICLE_STORAGE_TYPE_RETURN_OUT);
		}else if ((DictCodeConstants.DICT_STOCK_OUT_TYPE_SALE+"").equals(stockOutType.toString())) {
			logPO.setInteger("OPERATION_TYPE", DictCodeConstants.DICT_VEHICLE_STORAGE_TYPE_SALE_OUT);
		}
		logPO.setString("OPERATED_BY", FrameworkUtil.getLoginInfo().getUserId().toString());
		if (purchasePrice != null) {
			logPO.setDouble("PURCHASE_PRICE", Double.parseDouble(purchasePrice.toString()));
		}
		logPO.setInteger("DISPATCHED_STATUS", Integer.parseInt(dispatch));
		logPO.saveIt();
	}

	/**
	 * 车辆出库前,根据vin校验是否潜在客户 TODO description
	 * 
	 * @author yangjie
	 * @date 2017年2月8日
	 * @param list
	 * @return
	 */
	/**
	 * @param list
	 * @return
	 * @throws ServiceBizException
	 */
	public void checkCustomer(List<Map> list) throws ServiceBizException {
		/**
		 * 校验是否已经生成保有客户信息,如果没有生成保佑客户信息则继续执行
		 */
		Boolean flag = false;
		for (Map map : list) {
			List<Map> list2 = checkOwnerInfo(map.get("VIN").toString());
			if (list2.size() == 0) {// 没有生成保佑客户信息
				flag = true;
			}
			if (flag) {
				SalesOrderPO spo = SalesOrderPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),
						map.get("SO_NO"));
				if (DictCodeConstants.DICT_SO_TYPE_GENERAL.equals(spo.getInteger("BUSINESS_TYPE").toString())) {
					if (null != spo.getInteger("BUSINESS_TYPE") && DictCodeConstants.DICT_SO_TYPE_GENERAL
							.equals(spo.getInteger("BUSINESS_TYPE").toString())) {
						String pcustomerNo = spo.getString("CUSTOMER_NO");
						if (null != pcustomerNo && !"".equals(pcustomerNo.trim())) {
							PotentialCusPO pcu = PotentialCusPO
									.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(), pcustomerNo);
							CustomerPO customerPO = new CustomerPO();
							if (null != pcu.getString("CONTACTOR_PHONE")
									&& !"".equals(pcu.getString("CONTACTOR_PHONE"))) {
								customerPO = CustomerPO.findFirst("CONTACTOR_PHONE = ?",
										pcu.getString("CONTACTOR_PHONE"));
								if (customerPO != null) {
									throw new ServiceBizException(
											"保有客户已经存在,VIN号为" + map.get("VIN").toString() + "的联系电话已经存在");
								}
							}
							if (null != pcu.getString("CONTACTOR_MOBILE")
									&& !"".equals(pcu.getString("CONTACTOR_MOBILE"))) {
								customerPO = CustomerPO.findFirst("CONTACTOR_MOBILE = ?",
										pcu.getString("CONTACTOR_MOBILE"));
								if (customerPO != null) {
									throw new ServiceBizException(
											"保有客户已经存在,VIN号为" + map.get("VIN").toString() + "的联系手机已经存在");
								}
							}
							if (null != pcu.getString("CERTIFICATE_NO")
									&& !"".equals(pcu.getString("CERTIFICATE_NO"))) {
								customerPO = CustomerPO.findFirst("CERTIFICATE_NO = ?",
										pcu.getString("CERTIFICATE_NO"));
								if (customerPO != null) {
									throw new ServiceBizException(
											"保有客户已经存在,VIN号为" + map.get("VIN").toString() + "的证件已经存在");
								}
							}
						}
					}
				}
			}
		}
	}

	/**
	 * 车辆出库：根据vin查询客户信息(潜在客户保有客户)和车辆信息
	 * 
	 * @throws Exception
	 */
	public Map queryCustomerInfoByVin(String vin, String entityCode, String customerNo, String flag, String soNo) {
		StringBuffer sql = new StringBuffer("");
		if (flag.equals(DictCodeConstants.DICT_IS_YES)) {
			// 保佑
			sql.append(
					" SELECT D.CUSTOMER_NO AS PO_CUSTOMER_NO,D.INTENT_LEVEL,'' AS SO_NO,TL.CONTACTOR_NAME AS LINKED_NAME,A.VIN,A.MODEL,A.SERIES,A.BRAND,A.SERVICE_ADVISOR as ZS_MANAGER,A.COLOR,A.APACKAGE,D.CUSTOMER_NAME AS P_CUSTOMER_NAME,D.CONTACTOR_PHONE AS P_CONTACTOR_PHONE,D.CONTACTOR_MOBILE AS P_CONTACTOR_MOBILE,  ");
			sql.append(" A.LICENSE_DATE AS LICENSE_DATE,A.LICENSE,A.SALES_DATE,A.SALES_MILEAGE,");
			sql.append("B.*, E.PRODUCT_CODE  FROM (" + CommonConstants.VM_VEHICLE + ") A ");
			sql.append("LEFT JOIN TM_CUSTOMER B ON A.DEALER_CODE=B.DEALER_CODE AND A.CUSTOMER_NO=B.CUSTOMER_NO ");
			sql.append(
					"LEFT JOIN TT_PO_CUS_RELATION C ON A.CUSTOMER_NO=C.CUSTOMER_NO AND A.DEALER_CODE=C.DEALER_CODE ");
			sql.append(
					"LEFT JOIN TM_POTENTIAL_CUSTOMER D ON A.DEALER_CODE=D.DEALER_CODE AND C.PO_CUSTOMER_NO=D.CUSTOMER_NO ");
			sql.append(" LEFT JOIN TM_VS_STOCK E ON A.VIN= E.VIN AND A.DEALER_CODE=E.DEALER_CODE  ");
			sql.append(" LEFT JOIN TT_PO_CUS_LINKMAN TL ON TL.IS_DEFAULT_CONTACTOR=" + DictCodeConstants.DICT_IS_YES);
			sql.append(" AND D.CUSTOMER_NO=TL.CUSTOMER_NO AND D.DEALER_CODE=TL.DEALER_CODE ");
			sql.append("WHERE A.DEALER_CODE='" + entityCode + "' AND A.VIN='" + vin + "' AND B.CUSTOMER_NO='"
					+ customerNo + "' ");
		}
		if (flag.equals(DictCodeConstants.DICT_IS_NO)) {
			// 潜在
			sql.append(
					" SELECT B.CUSTOMER_NO AS PO_CUSTOMER_NO,A.SO_NO,TL.CONTACTOR_NAME AS LINKED_NAME,A.VIN,A.BRAND_CODE AS BRAND,A.SERVICE_ADVISOR as ZS_MANAGER,A.SERIES_CODE AS SERIES,A.MODEL_CODE AS MODEL,A.CONFIG_CODE AS APACKAGE,A.COLOR_CODE AS COLOR,B.CUSTOMER_NAME AS P_CUSTOMER_NAME,B.CONTACTOR_PHONE AS P_CONTACTOR_PHONE,B.CONTACTOR_MOBILE AS P_CONTACTOR_MOBILE,");
			sql.append("A.LICENSE,A.SALES_DATE,A.LICENSE_DATE,A.SALES_MILEAGE,B.*,A.PRODUCT_CODE  " + "FROM (");
			sql.append(
					"SELECT D.MODEL_CODE,D.BRAND_CODE,D.SERIES_CODE,D.CONFIG_CODE,E.SERVICE_ADVISOR ,COALESCE(D.COLOR_CODE,C.COLOR_CODE) as COLOR_CODE,C.SO_NO,C.VIN,C.LICENSE,C.CUSTOMER_NO, C.PRODUCT_CODE,");
			sql.append(
					"C.DEALER_CODE,C.CREATED_AT AS SALES_DATE,0 AS SALES_MILEAGE,'' AS LICENSE_DATE  FROM TT_SALES_ORDER C LEFT JOIN (");
			sql.append(CommonConstants.VM_VS_PRODUCT + ") D ON C.DEALER_CODE=D.DEALER_CODE ");
			sql.append("AND C.PRODUCT_CODE=D.PRODUCT_CODE LEFT JOIN (" + CommonConstants.VM_VEHICLE);
			sql.append(
					") E ON C.DEALER_CODE=E.DEALER_CODE AND C.VIN=E.VIN ) A LEFT JOIN TM_POTENTIAL_CUSTOMER B ON A.DEALER_CODE=B.DEALER_CODE ");
			sql.append("AND A.CUSTOMER_NO=B.CUSTOMER_NO LEFT JOIN TT_PO_CUS_LINKMAN TL ON TL.IS_DEFAULT_CONTACTOR="
					+ DictCodeConstants.DICT_IS_YES);
			sql.append(" AND B.CUSTOMER_NO=TL.CUSTOMER_NO AND B.DEALER_CODE=TL.DEALER_CODE  ");
			sql.append("WHERE A.DEALER_CODE='" + entityCode + "' ");
			Utility.sqlToEquals(sql, vin, "VIN", "A");
			Utility.sqlToEquals(sql, customerNo, "CUSTOMER_NO", "B");
			Utility.sqlToEquals(sql, soNo, "SO_NO", "A");
		}
		return DAOUtil.findFirst(sql.toString(), null);
	}

	/**
	 * 校验保佑客户表里是否有该信息(根据电话,手机,证件号码) TODO description
	 * 
	 * @author yangjie
	 * @date 2017年2月15日
	 * @param tel
	 * @param mobile
	 * @param cer
	 * @param customerNo
	 * @return
	 */
	public int checkCustomerInfo(String tel, String mobile, String certifNo, String customerNo) {
		String entityCode = FrameworkUtil.getLoginInfo().getDealerCode();
		if (customerNo != null && !"".equals(customerNo.trim())) {
			StringBuffer sql = new StringBuffer("");
			sql.append(" SELECT * FROM TM_CUSTOMER WHERE CUSTOMER_NO<>'" + customerNo + "' AND DEALER_CODE='"
					+ entityCode + "' AND D_KEY=" + CommonConstants.D_KEY + "  AND (1!=1 ");
			if (tel != null && !"".equals(tel)) {
				sql.append(" OR CONTACTOR_PHONE='" + tel + "'");
			}
			if (mobile != null && !"".equals(mobile)) {
				sql.append(" OR CONTACTOR_MOBILE='" + mobile + "' ");
			}
			if (certifNo != null && !"".equals(certifNo)) {
				sql.append(" OR CERTIFICATE_NO='" + certifNo + "'");
			}
			sql.append(" ) ");
			List<Map> list = DAOUtil.findAll(sql.toString(), null);
			if (list != null && list.size() > 0) {
				// 有保佑客户信息 已存在保有客户信息,是否修改!
			}
		} else {
			List<CustomerPO> phoneList = new ArrayList();
			List<CustomerPO> mobileList = new ArrayList();
			List<CustomerPO> cerList = new ArrayList();
			if (tel != null && !"".equals(tel)) {
				phoneList = CustomerPO.findBySQL("CONTACTOR_PHONE = ? AND D_KEY = ?", tel, CommonConstants.D_KEY);
			}
			if (mobile != null && !"".equals(mobile)) {
				mobileList = CustomerPO.findBySQL("CONTACTOR_MOBILE = ? AND D_KEY = ?", tel, CommonConstants.D_KEY);
			}
			if (certifNo != null && !"".equals(certifNo.trim())) {
				cerList = CustomerPO.findBySQL("CERTIFICATE_NO = ? AND D_KEY = ?", certifNo, CommonConstants.D_KEY);
			}
			if (phoneList.size() > 0 || mobileList.size() > 0 || cerList.size() > 0) {
				// 联系人电话,手机或证件已存在!
			}
		}
		return 0;
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
	public List<String> findNotInListVIN(StockOutListDTO dto) {
		String sql = "SELECT VIN,DEALER_CODE FROM TT_VS_DELIVERY_ITEM WHERE SD_NO = '" + dto.getSdNo() + "'";
		List<Map> list = DAOUtil.findAll(sql, null);// 查询数据库中此入库单相关所有的VIN和DEALER_CODE
		List<String> para = new ArrayList<String>();// 用于保存数据库中此入库单相关所有的VIN
		List<String> result = new ArrayList<String>();// 用于保存前台传过来的VIN而不存在于数据库中的VIN
		for (Map map : list) {
			para.add(map.get("VIN").toString());
		}
		for (Map map : dto.getDms_table()) {
			if (para.size() > 0 && map.get("VIN") != null) {
				if (!para.contains(map.get("VIN").toString())) {// 传到后台的VIN值不存在数据库中
					result.add(map.get("VIN").toString());
				}
			} else {
				result.add(map.get("VIN").toString());// 因为数据库中此入库单明细中没有数据,所以将前台传过来的数据视为result里的项
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
	public List<String> findNotInDataBaseVIN(StockOutListDTO dto) {
		String sql = "SELECT VIN,DEALER_CODE FROM TT_VS_DELIVERY_ITEM WHERE SD_NO = '" + dto.getSdNo() + "'";
		List<Map> list = DAOUtil.findAll(sql, null);// 查询数据库中此入库单相关所有的VIN和DEALER_CODE
		List<String> para = new ArrayList<String>();// 用于保存数据库中此入库单相关所有的VIN
		List<String> data = new ArrayList<String>();// 用于保存前台此入库单相关所有的VIN
		List<String> result = new ArrayList<String>();// 用于保存数据库中的VIN而不存在于前台传过来的VIN
		for (Map map : dto.getDms_table()) {
			if (map.get("VIN") != null) {
				data.add(map.get("VIN").toString());
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
	 * String类型转Date类型
	 * 
	 * @param time
	 * @return
	 */
	public Date strToDate(String time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date parse = null;
		try {
			parse = sdf.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return parse;
	}

	public String strToDate2(Date time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String parse = null;
		parse = sdf.format(time);
		return parse;
	}

	@Override
	public PageInfoDto findAllOutDetails2(String sdNo, String vin, String productCode) {
		String entityCode = FrameworkUtil.getLoginInfo().getDealerCode();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT '" + sdNo + "' AS sdNo," + vin);
		sql.append(
				" AS outType,a.DEALER_CODE,0 AS IS_SELECTED,a.SD_NO,a.VIN,a.PRODUCT_CODE,a.STORAGE_CODE,C.STORAGE_NAME,a.STORAGE_POSITION_CODE,a.PURCHASE_PRICE,a.ADDITIONAL_COST,");
		sql.append(
				"a.VEHICLE_PRICE,a.STOCK_STATUS,a.DISPATCHED_STATUS,a.SO_NO,a.MAR_STATUS,a.IS_SECONDHAND,a.IS_VIP,a.IS_TEST_DRIVE_CAR,");
		sql.append(
				"a.IS_CONSIGNED,a.IS_PROMOTION,a.IS_PURCHASE_RETURN,a.IS_PRICE_ADJUSTED,a.ADJUST_REASON,a.OLD_DIRECTIVE_PRICE,");
		sql.append(
				"a.DIRECTIVE_PRICE,a.OEM_TAG,a.REMARK,a.IS_FINISHED,a.FINISHED_DATE,a.FINISHED_BY,b.WARRANTY_NUMBER ");
		sql.append(" FROM TT_VS_DELIVERY_ITEM a ");
		sql.append(" join tm_vs_stock b on  a.vin=b.vin and a.DEALER_CODE=b.DEALER_CODE ");
		sql.append(
				" LEFT JOIN TM_STORAGE C ON a.DEALER_CODE=C.DEALER_CODE AND a.STORAGE_CODE=C.STORAGE_CODE WHERE a.DEALER_CODE='");
		sql.append(entityCode + "' AND a.D_KEY=" + CommonConstants.D_KEY + "  ");
		Utility.sqlToLike(sql, sdNo, "SD_NO", "a");
		// Utility.sqlToLike(sql, vin, "VIN", "a");
		// Utility.sqlToLike(sql, productCode, "PRODUCT_CODE", "a");
		return DAOUtil.pageQuery(sql.toString(), null);
	}

	@Override
	public List<Map> findAllOutDetailsForBatch(String sdNo, String vin, Object object) {
		String entityCode = FrameworkUtil.getLoginInfo().getDealerCode();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT '" + sdNo + "' AS sdNo," + vin);
		sql.append(
				" AS outType,a.DEALER_CODE,0 AS IS_SELECTED,a.SD_NO,a.VIN,a.PRODUCT_CODE,a.STORAGE_CODE,C.STORAGE_NAME,a.STORAGE_POSITION_CODE,a.PURCHASE_PRICE,a.ADDITIONAL_COST,");
		sql.append(
				"a.VEHICLE_PRICE,a.STOCK_STATUS,a.DISPATCHED_STATUS,a.SO_NO,a.MAR_STATUS,a.IS_SECONDHAND,a.IS_VIP,a.IS_TEST_DRIVE_CAR,");
		sql.append(
				"a.IS_CONSIGNED,a.IS_PROMOTION,a.IS_PURCHASE_RETURN,a.IS_PRICE_ADJUSTED,a.ADJUST_REASON,a.OLD_DIRECTIVE_PRICE,");
		sql.append(
				"a.DIRECTIVE_PRICE,a.OEM_TAG,a.REMARK,a.IS_FINISHED,a.FINISHED_DATE,a.FINISHED_BY,b.WARRANTY_NUMBER ");
		sql.append(" FROM TT_VS_DELIVERY_ITEM a ");
		sql.append(" join tm_vs_stock b on  a.vin=b.vin and a.DEALER_CODE=b.DEALER_CODE ");
		sql.append(
				" LEFT JOIN TM_STORAGE C ON a.DEALER_CODE=C.DEALER_CODE AND a.STORAGE_CODE=C.STORAGE_CODE WHERE a.DEALER_CODE='");
		sql.append(entityCode + "' AND a.D_KEY=" + CommonConstants.D_KEY + "  ");
		Utility.sqlToLike(sql, sdNo, "SD_NO", "a");
		sql.append(" AND a.VIN IN (" + vin + ")");
		// Utility.sqlToLike(sql, productCode, "PRODUCT_CODE", "a");
		return DAOUtil.findAll(sql.toString(), null);
	}

	@Override
	public List<Map> findPrintAbout(String vins) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT TA1.*, D.PHONE,CASE WHEN D.BUSINESS_TYPE =" + DictCodeConstants.DICT_SO_TYPE_ALLOCATION);
		sql.append(
				" THEN D.CONSIGNEE_NAME ELSE D.CUSTOMER_NAME END AS CUSTOMER_NAME,G.CUSTOMER_NAME AS SEC_CUSTOMER_NAME ,G.FETCHER_PHONE  FROM (");
		sql.append(
				" select  a.*,E.COLOR_NAME,F.STORAGE_NAME   from  ( SELECT A.DIRECTIVE_PRICE,A.PRODUCT_CODE,A.VIN,C.MODEL_NAME  ");
		sql.append(" ,A.DEALER_CODE,A.SO_NO,b.COLOR_CODE,a.storage_code,a.purchase_price,a.latest_stock_out_date");
		sql.append("  FROM TM_VS_STOCK A, (" + CommonConstants.VM_VS_PRODUCT + ") B,(" + CommonConstants.VM_MODEL
				+ ") C ");
		sql.append(
				" WHERE A.DEALER_CODE = B.DEALER_CODE AND A.DEALER_CODE = C.DEALER_CODE AND A.PRODUCT_CODE = B.PRODUCT_CODE ");
		sql.append(
				" AND B.MODEL_CODE = C.MODEL_CODE AND B.brand_CODE = C.brand_CODE AND B.series_CODE = C.series_CODE ");
		sql.append(" AND A.VIN IN (" + vins + ")");
		sql.append("  ) a  left join  (" + CommonConstants.VM_COLOR
				+ ") E   on a.DEALER_CODE = E.DEALER_CODE  and   a.COLOR_CODE = E.COLOR_CODE  left join  ("
				+ CommonConstants.VM_STORAGE
				+ ") F   on F.DEALER_CODE = A.DEALER_CODE  and   a.STORAGE_CODE = F.STORAGE_CODE      ");
		sql.append(") TA1");
		sql.append(
				" LEFT JOIN TT_SALES_ORDER D  ON TA1.DEALER_CODE = D.DEALER_CODE AND D.SO_NO = TA1.SO_NO   LEFT JOIN TT_second_SALES_ORDER G  ON TA1.DEALER_CODE = G.DEALER_CODE AND G.SEC_SO_NO = TA1.SO_NO ");
		return DAOUtil.findAll(sql.toString(), null);
	}
}
