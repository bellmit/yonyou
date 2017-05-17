/**
 * 
 */
package com.yonyou.dms.repair.service.order;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.DB;
import org.javalite.activejdbc.LazyList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.DTO.basedata.RepairOrderDetailsDTO;
import com.yonyou.dms.common.domains.PO.basedata.AccountPeriodPO;
import com.yonyou.dms.common.domains.PO.basedata.RepairOrderPO;
import com.yonyou.dms.common.domains.PO.basedata.RoAddItemPO;
import com.yonyou.dms.common.domains.PO.basedata.TmMemberCardActivityPO;
import com.yonyou.dms.common.domains.PO.basedata.TmModelPO;
import com.yonyou.dms.common.domains.PO.basedata.TmPartStockPO;
import com.yonyou.dms.common.domains.PO.basedata.TmRepairItemPO;
import com.yonyou.dms.common.domains.PO.basedata.TmVehicleSubclassPO;
import com.yonyou.dms.common.domains.PO.basedata.TtActivityPO;
import com.yonyou.dms.common.domains.PO.basedata.TtBookingOrderPO;
import com.yonyou.dms.common.domains.PO.basedata.TtBookingOrderPartPO;
import com.yonyou.dms.common.domains.PO.basedata.TtMemCardActiDetailPO;
import com.yonyou.dms.common.domains.PO.basedata.TtMemberActivityPO;
import com.yonyou.dms.common.domains.PO.basedata.TtMemberLabourFlowPO;
import com.yonyou.dms.common.domains.PO.basedata.TtMemberLabourPO;
import com.yonyou.dms.common.domains.PO.basedata.TtMemberPartFlowPO;
import com.yonyou.dms.common.domains.PO.basedata.TtMemberPartPO;
import com.yonyou.dms.common.domains.PO.basedata.TtOccurInsuranceRegisterPO;
import com.yonyou.dms.common.domains.PO.basedata.TtPartPeriodReportPO;
import com.yonyou.dms.common.domains.PO.basedata.TtRoLabourPO;
import com.yonyou.dms.common.domains.PO.basedata.TtRoRepairPartPO;
import com.yonyou.dms.common.domains.PO.basedata.TtRoTimeoutDetailPO;
import com.yonyou.dms.common.domains.PO.monitor.OperateLogPO;
import com.yonyou.dms.common.domains.PO.stockmanage.CarownerPO;
import com.yonyou.dms.common.domains.PO.stockmanage.VehiclePO;
import com.yonyou.dms.commonAS.domains.PO.basedata.PartFlowPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.function.utils.security.MD5Util;

/**
 * @author yangjie 工单保存按钮
 */
@Service
@SuppressWarnings({ "unchecked", "rawtypes" })
public class SaveRepairOrderServiceImpl implements SaveRepairOrderService {

	private static final Logger logger = LoggerFactory.getLogger(SaveRepairOrderServiceImpl.class);

	private static List<Map> ttTripleInfo = new ArrayList<Map>();// 预警信息

	/**
	 * 工单保存主方法
	 */
	@Override
	public void btnSave(RepairOrderDetailsDTO dto) {
		// 删除PART_CODE为空的ttTripleInfo
		if (!CommonUtils.isNullOrEmpty(ttTripleInfo)) {
			Iterator<Map> it = ttTripleInfo.iterator();
			while (it.hasNext()) {
				Map x = it.next();
				if (StringUtils.isNullOrEmpty(x.get("PART_CODE"))) {
					it.remove();
				}
			}
		}
		dto.setTtTripleInfo(ttTripleInfo);
		minusRoObligated(dto);
		updateOwnerAndVehicle(dto);
		addRepairOrder(dto);
		maintainRoAddItem(dto);
		maintainRoLabour(dto);
		maintainRoRepairPart(dto);
		// 保养套餐
		// 派工单
		// ----------------------------------------------------
		@SuppressWarnings("unused")
		List<Map> checkShortPart = checkShortPart(dto);
		updateBookingOrderStatusByRepairOrder(dto);
		checkLabourAmount(dto);
		//加锁
		Utility.updateByLocker("TT_REPAIR_ORDER", FrameworkUtil.getLoginInfo().getUserId().toString(), "RO_NO", dto.getRoNo(), "LOCK_USER");
	}

	/**
	 * 用于验证应收工时费和维修项目工时费是否相等
	 */
	public void checkLabourAmount(RepairOrderDetailsDTO dto) throws ServiceBizException {
		List<Map> list = queryLabourAmount(dto.getRoNo());
		Double la1 = 0.00;
		Double la2 = 0.00;
		if (list != null && list.size() > 0) {
			for (Map map : list) {
				la1 = Double.parseDouble(map.get("LABOUR_AMOUNT").toString());
				la2 = Double.parseDouble(map.get("LABOUR_AMOUNTB").toString());
			}
			if ((la1.doubleValue() - la2.doubleValue()) < 0.00001
					|| (la2.doubleValue() - la1.doubleValue()) < 0.00001) {

			} else {
				handleOperateLog("客户接待工时费不正常:工单号【" + dto.getRoNo() + "】", "",
						Integer.parseInt(DictCodeConstants.DICT_ASCLOG_OPERATION_RECEIVE),
						FrameworkUtil.getLoginInfo().getEmployeeNo());
				throw new ServiceBizException("工时费, 应收工时费和维修项目工时费不相等，请联系相关人员");
			}
		}
	}

	public List<Map> queryLabourAmount(String roNo) {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT LABOUR_AMOUNT,LABOUR_AMOUNTB")
				.append(" FROM TT_REPAIR_ORDER A LEFT JOIN (SELECT DEALER_CODE,RO_NO,SUM(CASE WHEN(NEEDLESS_REPAIR =")
				.append(DictCodeConstants.DICT_IS_YES)
				.append(") OR(CHARGE_PARTITION_CODE <> '') THEN 0.0 ELSE LABOUR_AMOUNT * DISCOUNT END) LABOUR_AMOUNTB FROM TT_RO_LABOUR WHERE")
				.append(" DEALER_CODE='").append(FrameworkUtil.getLoginInfo().getDealerCode()).append("' AND RO_NO='")
				.append(roNo)
				.append("' GROUP BY DEALER_CODE,RO_NO ) B ON A.DEALER_CODE=B.DEALER_CODE AND A.RO_NO=B.RO_NO")
				.append(" WHERE A.DEALER_CODE='").append(FrameworkUtil.getLoginInfo().getDealerCode())
				.append("' AND (1=2 ");
		if (!StringUtils.isNullOrEmpty(roNo))
			sb.append(" OR A.RO_NO='").append(roNo).append("' ");
		sb.append(" )");
		return DAOUtil.findAll(sb.toString(), null);
	}

	/**
	 * 新增后更新预约单的状态
	 */
	public void updateBookingOrderStatusByRepairOrder(RepairOrderDetailsDTO dto) {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		if (!StringUtils.isNullOrEmpty(dto.getBookingOrderNo())) {
			TtBookingOrderPO ttBookingOrderPO = TtBookingOrderPO.findByCompositeKeys(dealerCode,
					dto.getBookingOrderNo());
			if (!StringUtils.isNullOrEmpty(ttBookingOrderPO)
					&& ttBookingOrderPO.getInteger("D_KEY") == CommonConstants.D_KEY) {
				ttBookingOrderPO.setInteger("BOOKING_ORDER_STATUS", getBookingOrderStatus(
						dto.getCreateDate().toString(), ttBookingOrderPO.getDate("BOOKING_COME_TIME")));
			}
			ttBookingOrderPO.saveIt();
			logger.debug("通过预约单号webservice请求线程开始");
			logger.debug("通过预约单号webservice请求线程结束");
		}
	}

	/**
	 * 根椐开工单的时间和预约进厂时间得到预约单的状态
	 * 
	 * @param roCreateDate
	 * @param bookingComeTime
	 * @return
	 */
	private int getBookingOrderStatus(String roCreateDate, Date bookingComeTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int bookingOrderStatus = Integer.parseInt(DictCodeConstants.DICT_BOS_ENTER_ON_TIME);
		int bookingComeBefore = Integer
				.parseInt(Utility.getDefaultValue(String.valueOf(CommonConstants.DEFAULT_PARA_BOOKING_COME_BEFORE)));
		int bookingComeAfter = Integer
				.parseInt(Utility.getDefaultValue(String.valueOf(CommonConstants.DEFAULT_PARA_BOOKING_COME_AFTER)));
		long min = 0L;
		long mini = 0L;
		try {
			min = sdf.parse(roCreateDate).getTime() - bookingComeTime.getTime();
			mini = bookingComeTime.getTime() - sdf.parse(roCreateDate).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// 开单时间与预约进厂时间的差大于系统参数设置的提前进厂时间

		/*
		 * logger.debug("mini :"+mini); logger.debug("mini :"+mini/60000);
		 * logger.debug("bookingComeBefore :"+bookingComeBefore);
		 * logger.debug("min :"+min); logger.debug("min :"+min/60000);
		 * logger.debug("bookingComeAfter :"+bookingComeAfter);
		 */

		// add by sf 2010-07-20 取整不对 .
		if (mini / 60000.0 > bookingComeBefore) {
			bookingOrderStatus = Integer.parseInt(DictCodeConstants.DICT_BOS_ADVANCE_ENTER);
		}
		// 预约进厂时间与开单时间的差大于系统参数设置的延迟进厂时间
		if (min / 60000.0 > bookingComeAfter) {
			bookingOrderStatus = Integer.parseInt(DictCodeConstants.DICT_BOS_DELAY_ENTER);
		}
		return bookingOrderStatus;
	}

	public List<Map> checkShortPart(RepairOrderDetailsDTO dto) throws ServiceBizException {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		String sheetNos = dto.getRoNo();
		List<Map> result = new ArrayList<Map>();
		if (!StringUtils.isNullOrEmpty(sheetNos)) {
			String storageCode = "STORAGE_CODE";
			String quantity = "QUANTITY";
			String storagePositionCode = "STORAGE_POSITION_CODE";
			String partNo = "PART_NO";
			String partName = "PART_NAME";
			if (sheetNos.substring(0, 2).equals("RO")) {
				quantity = "PART_QUANTITY";
				List<TtRoRepairPartPO> listPart = TtRoRepairPartPO.find(
						"DEALER_CODE = ? AND RO_NO = ? AND D_KEY = ? AND IS_FINISHED", dealerCode, sheetNos,
						CommonConstants.D_KEY, DictCodeConstants.IS_NOT);
				if (listPart.size() > 0) {
					HashMap<String, Float> partMap = new HashMap<String, Float>();
					HashMap<String, Float> ShortPartMap = new HashMap<String, Float>();
					for (TtRoRepairPartPO partBean : listPart) {
						String aStorageCode = "";
						String aStoragePositionCode = "";
						String aPartNo = "";
						String aPartName = "";
						Float aQuantity = partBean.getFloat(quantity);
						if (partBean.getString(storageCode) != null)
							aStorageCode = partBean.getString(storageCode);
						if (partBean.getString(storagePositionCode) != null)
							aStoragePositionCode = partBean.getString(storagePositionCode);
						if (partBean.getString(partNo) != null)
							aPartNo = partBean.getString(partNo);
						if (partBean.getString(partName) != null)
							aPartName = partBean.getString(partName);
						if (partMap.get(
								aStorageCode + ":" + aStoragePositionCode + ":" + aPartNo + ":" + aPartName) == null
								&& ShortPartMap.get(aStorageCode + ":" + aStoragePositionCode + ":" + aPartNo + ":"
										+ aPartName) == null) {
							StringBuffer sb = new StringBuffer();
							sb.append("DEALER_CODE = '" + dealerCode + "' AND STORAGE_CODE = '")
									.append(partBean.getString(storageCode) + "' ");
							if (Utility.testString(partBean.getString(storagePositionCode)))
								sb.append(" AND STORAGE_POSITION_CODE = '")
										.append(partBean.getString(storagePositionCode) + "' ");
							sb.append(" AND PART_NO = '" + partBean.getString(partNo) + "' AND D_KEY = ")
									.append(CommonConstants.D_KEY);
							List<TmPartStockPO> listPartStock = TmPartStockPO.find(sb.toString());
							if (listPartStock != null && listPartStock.size() > 0) {
								TmPartStockPO partStock = (TmPartStockPO) listPartStock.get(0);
								float vaildQuantity = partStock.getFloat("STOCK_QUANTITY")
										+ partStock.getFloat("BORROW_QUANTITY") - partStock.getFloat("LEND_QUANTITY")
										- partStock.getFloat("LOCKED_QUANTITY");
								if (vaildQuantity < aQuantity) {
									ShortPartMap.put(
											aStorageCode + ":" + aStoragePositionCode + ":" + aPartNo + ":" + aPartName,
											aQuantity - vaildQuantity);
								} else {
									partMap.put(
											aStorageCode + ":" + aStoragePositionCode + ":" + aPartNo + ":" + aPartName,
											vaildQuantity - aQuantity);
								}
							}
						} else {
							if (ShortPartMap.get(aStorageCode + ":" + aStoragePositionCode + ":" + aPartNo + ":"
									+ aPartName) != null) {
								float PartQuantity = ShortPartMap.get(
										aStorageCode + ":" + aStoragePositionCode + ":" + aPartNo + ":" + aPartName)
										+ aQuantity;
								ShortPartMap.put(
										aStorageCode + ":" + aStoragePositionCode + ":" + aPartNo + ":" + aPartName,
										PartQuantity);
							} else {
								float vaildQuantity = partMap.get(
										aStorageCode + ":" + aStoragePositionCode + ":" + aPartNo + ":" + aPartName);
								if (vaildQuantity < aQuantity) {
									ShortPartMap.put(
											aStorageCode + ":" + aStoragePositionCode + ":" + aPartNo + ":" + aPartName,
											aQuantity - vaildQuantity);
								} else {
									partMap.put(
											aStorageCode + ":" + aStoragePositionCode + ":" + aPartNo + ":" + aPartName,
											vaildQuantity - aQuantity);
								}
							}
						}
					}
					Iterator iter = ShortPartMap.entrySet().iterator();
					while (iter.hasNext()) {
						Map.Entry entry = (Map.Entry) iter.next();
						String key = (String) entry.getKey();
						Float val = (Float) entry.getValue();
						String[] array = key.split(":");
						Map bean = new HashMap();
						bean.put("STORAGE_CODE", array[0]);
						bean.put("STORAGE_POSITION_CODE", array[1]);
						bean.put("PART_NO", array[2]);
						bean.put("PART_NAME", array[3]);
						bean.put("QUANTITY", val);
						bean.put("IS_SELECT", DictCodeConstants.DICT_IS_YES);
						result.add(bean);
					}
				}
			}
		}
		return result;
	}

	/**
	 * 业务描述：新增．修改．删除工单维修配件（保存漆辅料）
	 * 
	 * @param dto
	 * @throws ServiceBizException
	 */
	public void maintainRoRepairPart(RepairOrderDetailsDTO dto) throws ServiceBizException {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		String group_code = Utility.getGroupEntity(dealerCode, "TM_MEMBER_CARD_ACTIVITY");
		List<Map<String, String>> dms_part = dto.getDms_part();
		for (Map<String, String> map : dms_part) {
			String status = map.get("rowKey");
			if (!StringUtils.isNullOrEmpty(status)) {
				// 判断配件名称不能为空
				if ("".equals(map.get("PART_NAME")))
					throw new ServiceBizException("代码为【" + map.get("PART_NAME") + "】的配件名称为空!");
				if ("0".equals(map.get("CARD_ID"))) {
					map.put("CARD_ID", "");
					logger.debug("-------cardId---------" + map.get("CARD_ID"));
				}
				if ("A".equals(status)) {
					TtRoRepairPartPO roRepairPart = new TtRoRepairPartPO();
					roRepairPart.setString("STORAGE_CODE", map.get("STORAGE_CODE"));
					roRepairPart.setString("STORAGE_POSITION_CODE", map.get("STORAGE_POSITION_CODE"));
					roRepairPart.setString("CHARGE_PARTITION_CODE", map.get("CHARGE_PARTITION_CODE"));
					roRepairPart.setString("UNIT_CODE", map.get("UNIT_CODE"));
					roRepairPart.setString("PART_NAME", map.get("PART_NAME"));
					roRepairPart.setString("PART_NO", map.get("PART_NO"));
					if (!StringUtils.isNullOrEmpty(map.get("LABOUR_NAME")))
						roRepairPart.setString("LABOUR_NAME", map.get("LABOUR_NAME"));
					if (StringUtils.isNullOrEmpty(map.get("LABOUR_CODE")))
						map.put("LABOUR_CODE", "");
					roRepairPart.setString("LABOUR_CODE", map.get("LABOUR_CODE"));
					if (!StringUtils.isNullOrEmpty(map.get("MODEL_LABOUR_CODE")))
						roRepairPart.setString("MODEL_LABOUR_CODE", map.get("MODEL_LABOUR_CODE"));
					if (!StringUtils.isNullOrEmpty(map.get("LABOUR_CODE"))) {
						// 根据工单号,项目代码查询维修项目ID
						List<TtRoLabourPO> labourList = queryRoLabourByCodeAndRoNo(map.get("LABOUR_CODE"),
								map.get("MODEL_LABOUR_CODE"), map.get("PACKAGE_CODE"), dto.getRoNo(),
								map.get("LABOUR_NAME"));
						if (labourList != null && labourList.size() > 0) {
							TtRoLabourPO labourPO = (TtRoLabourPO) labourList.get(0);
							if (labourPO != null) {
								roRepairPart.setLong("ITEM_ID_LABOUR", labourPO.getLong("ITEM_ID"));
							}
						}
					}
					if (!StringUtils.isNullOrEmpty(map.get("PACKAGE_CODE")))
						roRepairPart.setString("PACKAGE_CODE", map.get("PACKAGE_CODE"));
					if (!StringUtils.isNullOrEmpty(map.get("IS_DISCOUNT")))
						roRepairPart.setInteger("IS_DISCOUNT", Integer.parseInt(map.get("IS_DISCOUNT")));
					if (StringUtils.isNullOrEmpty(map.get("REPAIR_TYPE_CODE")))
						map.put("REPAIR_TYPE_CODE", "");
					roRepairPart.setString("REPAIR_TYPE_CODE", map.get("REPAIR_TYPE_CODE"));
					if (!StringUtils.isNullOrEmpty(map.get("IS_MAIN_PART")))
						roRepairPart.setInteger("IS_MAIN_PART", Integer.parseInt(map.get("IS_MAIN_PART")));
					roRepairPart.setString("RO_NO", dto.getRoNo());
					if (!StringUtils.isNullOrEmpty(map.get("PART_QUANTITY")))
						roRepairPart.setFloat("PART_QUANTITY", Float.parseFloat(map.get("PART_QUANTITY")));
					if (!StringUtils.isNullOrEmpty(map.get("DISCOUNT")))
						roRepairPart.setFloat("DISCOUNT", Float.parseFloat(map.get("DISCOUNT")));
					if (!StringUtils.isNullOrEmpty(map.get("OEM_LIMIT_PRICE")))
						roRepairPart.setDouble("OEM_LIMIT_PRICE", Double.parseDouble(map.get("OEM_LIMIT_PRICE")));
					if (!StringUtils.isNullOrEmpty(map.get("ACTIVITY_CODE")))
						roRepairPart.setString("ACTIVITY_CODE", map.get("ACTIVITY_CODE"));
					roRepairPart.setLong("IS_FINISHED", DictCodeConstants.IS_NOT);
					if (!StringUtils.isNullOrEmpty(map.get("PART_SALES_PRICE")))
						roRepairPart.setDouble("PART_SALES_PRICE", Double.parseDouble(map.get("PART_SALES_PRICE")));
					if (!StringUtils.isNullOrEmpty(map.get("CONSIGN_EXTERIOR")))
						roRepairPart.setInteger("CONSIGN_EXTERIOR", Integer.parseInt(map.get("CONSIGN_EXTERIOR")));
					roRepairPart.setDouble("PART_SALES_AMOUNT", Double.parseDouble(map.get("PART_SALES_AMOUNT")));
					if (!StringUtils.isNullOrEmpty(map.get("PART_COST_PRICE")))
						roRepairPart.setDouble("PART_COST_PRICE", Double.parseDouble(map.get("PART_COST_PRICE")));
					if (!StringUtils.isNullOrEmpty(map.get("PART_COST_AMOUNT")))
						roRepairPart.setDouble("PART_COST_AMOUNT", Double.parseDouble(map.get("PART_COST_AMOUNT")));
					if (!StringUtils.isNullOrEmpty(map.get("PRE_CHECK")))
						roRepairPart.setInteger("PRE_CHECK", Integer.parseInt(map.get("PRE_CHECK")));
					if (!StringUtils.isNullOrEmpty(map.get("NEEDLESS_REPAIR")))
						roRepairPart.setInteger("NEEDLESS_REPAIR", Integer.parseInt(map.get("NEEDLESS_REPAIR")));
					if (!StringUtils.isNullOrEmpty(map.get("INTER_RETURN")))
						roRepairPart.setInteger("INTER_RETURN", Integer.parseInt(map.get("INTER_RETURN")));
					if (!StringUtils.isNullOrEmpty(map.get("PRICE_TYPE")))
						roRepairPart.setInteger("PRICE_TYPE", Integer.parseInt(map.get("PRICE_TYPE")));
					if (!StringUtils.isNullOrEmpty(map.get("PRICE_RATE")))
						roRepairPart.setFloat("PRICE_RATE", Float.parseFloat(map.get("PRICE_RATE")));
					if (!StringUtils.isNullOrEmpty(map.get("MANAGE_SORT_CODE")))
						roRepairPart.setString("MANAGE_SORT_CODE", map.get("MANAGE_SORT_CODE"));
					if (!StringUtils.isNullOrEmpty(map.get("REASON")))
						roRepairPart.setInteger("REASON", Integer.parseInt(map.get("REASON")));
					if (!StringUtils.isNullOrEmpty(map.get("POS_CODE")))
						roRepairPart.setString("POS_CODE", map.get("POS_CODE"));
					if (!StringUtils.isNullOrEmpty(map.get("POS_NAME")))
						roRepairPart.setString("POS_NAME", map.get("POS_NAME"));
					if (!StringUtils.isNullOrEmpty(map.get("PART_INFIX")))
						roRepairPart.setString("PART_INFIX", map.get("PART_INFIX"));
					if (!StringUtils.isNullOrEmpty(map.get("WAR_LEVEL")))
						roRepairPart.setInteger("WAR_LEVEL", Integer.parseInt(map.get("WAR_LEVEL")));
					// 配件类别
					if (!StringUtils.isNullOrEmpty(map.get("PART_CATEGORY")))
						roRepairPart.setInteger("PART_CATEGORY", Integer.parseInt(map.get("PART_CATEGORY")));
					if (!StringUtils.isNullOrEmpty(map.get("MAIN_PAKCAGE_CODE")))
						roRepairPart.setString("MAIN_PAKCAGE_CODE", map.get("MAIN_PAKCAGE_CODE"));
					if (!StringUtils.isNullOrEmpty(map.get("CARD_ID")))
						roRepairPart.setLong("CARD_ID", Long.parseLong(map.get("CARD_ID")));
					roRepairPart.setString("DEALER_CODE", dealerCode);
					roRepairPart.saveIt();
				} else if ("U".equals(status)) {
					TtRoRepairPartPO roRepairPart = TtRoRepairPartPO.findFirst(
							"ITEM_ID = ? AND DEALER_CODE = ? AND RO_NO = ? AND D_KEY = ?", map.get("ITEM_ID"),
							dealerCode, dto.getRoNo(), CommonConstants.D_KEY);
					roRepairPart.setString("STORAGE_CODE", map.get("STORAGE_CODE"));
					roRepairPart.setString("STORAGE_POSITION_CODE", map.get("STORAGE_POSITION_CODE"));
					roRepairPart.setString("CHARGE_PARTITION_CODE", map.get("CHARGE_PARTITION_CODE"));
					// 增加记录日志
					TtRoRepairPartPO roRepairPartOld = TtRoRepairPartPO.findFirst(
							"ITEM_ID = ? AND DEALER_CODE = ? AND RO_NO = ? AND D_KEY = ?", map.get("ITEM_ID"),
							dealerCode, dto.getRoNo(), CommonConstants.D_KEY);
					if (!StringUtils.isNullOrEmpty(roRepairPartOld)) {
						String chargePartitioncodeOld = roRepairPartOld.getString("CHARGE_PARTITION_CODE");
						// 如果新的收费区分有修改则记录日志
						if (chargePartitioncodeOld != null) {
							if (!chargePartitioncodeOld.equals(map.get("CHARGE_PARTITION_CODE"))) {
								String content = "工单号：" + dto.getRoNo() + " 配件代码：" + map.get("PART_NO") + " 收费区分由【"
										+ chargePartitioncodeOld + "】" + "修改为：【" + map.get("CHARGE_PARTITION_CODE")
										+ "】";
								logger.debug(content);
								handleOperateLog(content, "",
										Integer.parseInt(DictCodeConstants.DICT_ASCLOG_PART_MANAGE),
										FrameworkUtil.getLoginInfo().getEmployeeNo());
							}
						}
					}

					roRepairPart.setString("UNIT_CODE", map.get("UNIT_CODE"));
					roRepairPart.setString("PART_NAME", map.get("PART_NAME"));
					roRepairPart.setString("PART_NO", map.get("PART_NO"));
					if (map.get("LABOUR_CODE") == null || "".equals(map.get("LABOUR_CODE"))) {
						map.put("LABOUR_CODE", "");
						map.put("LABOUR_NAME", "");
						roRepairPart.setString("ITEM_ID_LABOUR", null);
					}
					if (Utility.testString(map.get("LABOUR_CODE"))) {
						// 根据工单号,项目代码查询维修项目ID
						List<TtRoLabourPO> labourList = queryRoLabourByCodeAndRoNo(map.get("LABOUR_CODE"),
								map.get("MODEL_LABOUR_CODE"), map.get("PACKAGE_CODE"), dto.getRoNo(),
								map.get("LABOUR_NAME"));
						if (labourList != null && labourList.size() > 0) {
							TtRoLabourPO labourPO = (TtRoLabourPO) labourList.get(0);
							if (labourPO != null) {
								roRepairPart.setLong("ITEM_ID_LABOUR", labourPO.getLong("ITEM_ID"));
							}
						}
					}
					roRepairPart.setString("LABOUR_CODE", map.get("LABOUR_CODE"));
					roRepairPart.setString("LABOUR_NAME", map.get("LABOUR_NAME"));
					if ((map.get("MODEL_LABOUR_CODE") != null) && Utility.testString(map.get("MODEL_LABOUR_CODE")))
						roRepairPart.setString("MODEL_LABOUR_CODE", map.get("MODEL_LABOUR_CODE"));
					if (!StringUtils.isNullOrEmpty(map.get("PACKAGE_CODE")))
						roRepairPart.setString("PACKAGE_CODE", map.get("PACKAGE_CODE"));
					if (!StringUtils.isNullOrEmpty(map.get("IS_DISCOUNT")))
						roRepairPart.setInteger("IS_DISCOUNT", Integer.parseInt(map.get("IS_DISCOUNT")));
					if (map.get("REPAIR_TYPE_CODE") == null)
						map.put("REPAIR_TYPE_CODE", "");
					roRepairPart.setString("REPAIR_TYPE_CODE", map.get("REPAIR_TYPE_CODE"));
					if (!StringUtils.isNullOrEmpty(map.get("IS_MAIN_PART")))
						roRepairPart.setString("IS_MAIN_PART", map.get("IS_MAIN_PART"));
					roRepairPart.setString("RO_NO", dto.getRoNo());
					if (!StringUtils.isNullOrEmpty(map.get("PART_QUANTITY")))
						roRepairPart.setString("PART_QUANTITY", map.get("PART_QUANTITY"));
					if (!StringUtils.isNullOrEmpty(map.get("Discount")))
						roRepairPart.setFloat("DISCOUNT", Float.parseFloat(map.get("Discount")));
					if (!StringUtils.isNullOrEmpty(map.get("OEM_LIMIT_PRICE")))
						roRepairPart.setDouble("OEM_LIMIT_PRICE", Double.parseDouble(map.get("OEM_LIMIT_PRICE")));
					if (!StringUtils.isNullOrEmpty(map.get("ACTIVITY_CODE")))
						roRepairPart.setString("ACTIVITY_CODE", map.get("ACTIVITY_CODE"));
					if (!StringUtils.isNullOrEmpty(map.get("PART_SALES_PRICE")))
						roRepairPart.setDouble("PART_SALES_PRICE", Double.parseDouble(map.get("PART_SALES_PRICE")));

					if (!StringUtils.isNullOrEmpty(map.get("CONSIGN_EXTERIOR")))
						roRepairPart.setInteger("CONSIGN_EXTERIOR", Integer.parseInt(map.get("CONSIGN_EXTERIOR")));
					roRepairPart.setDouble("PART_SALES_AMOUNT", Double.parseDouble(map.get("PART_SALES_AMOUNT")));
					// 如果已经入账了的配件不允许再修改其成本单价
					if (!StringUtils.isNullOrEmpty(map.get("PART_COST_PRICE"))) {
						if (!DictCodeConstants.DICT_IS_YES.equals(map.get("IS_FINISHED").trim())) {
							roRepairPart.setDouble("PART_COST_PRICE", Double.parseDouble(map.get("PART_COST_PRICE")));
						}
					}
					if (!StringUtils.isNullOrEmpty(map.get("POS_CODE")))
						roRepairPart.setString("POS_CODE", map.get("POS_CODE"));
					if (!StringUtils.isNullOrEmpty(map.get("POS_NAME")))
						roRepairPart.setString("POS_NAME", map.get("POS_NAME"));
					if (!StringUtils.isNullOrEmpty(map.get("PART_INFIX")))
						roRepairPart.setString("PART_INFIX", map.get("PART_INFIX"));
					if (!StringUtils.isNullOrEmpty(map.get("WAR_LEVEL")))
						roRepairPart.setInteger("WAR_LEVEL", Integer.parseInt(map.get("WAR_LEVEL")));
					// 配件类别
					if (!StringUtils.isNullOrEmpty(map.get("PART_CATEGORY")))
						roRepairPart.setInteger("PART_CATEGORY", Integer.parseInt(map.get("PART_CATEGORY")));
					if (!StringUtils.isNullOrEmpty(map.get("PART_COST_AMOUNT"))) {
						// 如果已经入账了的配件不允许再修改其成本单价
						if (!DictCodeConstants.DICT_IS_YES.equals(map.get("IS_FINISHED").trim())) {
							roRepairPart.setDouble("PART_COST_AMOUNT", Double.parseDouble(map.get("PART_COST_AMOUNT")));
						}
						if (!StringUtils.isNullOrEmpty(map.get("PRE_CHECK")))
							roRepairPart.setInteger("PRE_CHECK", Integer.parseInt(map.get("PRE_CHECK")));
						if (!StringUtils.isNullOrEmpty(map.get("NEEDLESS_REPAIR")))
							roRepairPart.setInteger("NEEDLESS_REPAIR", Integer.parseInt(map.get("NEEDLESS_REPAIR")));
						if (!StringUtils.isNullOrEmpty(map.get("INTER_RETURN")))
							roRepairPart.setInteger("INTER_RETURN", Integer.parseInt(map.get("INTER_RETURN")));
						if (!StringUtils.isNullOrEmpty(map.get("PRICE_TYPE")))
							roRepairPart.setInteger("PRICE_TYPE", Integer.parseInt(map.get("PRICE_TYPE")));
						if (!StringUtils.isNullOrEmpty(map.get("PRICE_RATE")))
							roRepairPart.setFloat("PRICE_RATE", Float.parseFloat(map.get("PRICE_RATE")));
						if (!StringUtils.isNullOrEmpty(map.get("MANAGE_SORT_CODE")))
							roRepairPart.setString("MANAGE_SORT_CODE", map.get("MANAGE_SORT_CODE"));
						if (!StringUtils.isNullOrEmpty(map.get("REASON")))
							roRepairPart.setInteger("REASON", Integer.parseInt(map.get("REASON")));
						if (!StringUtils.isNullOrEmpty(map.get("CARD_ID")))
							roRepairPart.setLong("CARD_ID", Long.parseLong(map.get("CARD_ID")));
						if (!StringUtils.isNullOrEmpty(map.get("MAINTAIN_PACKAGE_CODE"))) {
							roRepairPart.setString("MAINTAIN_PACKAGE_CODE", map.get("MAINTAIN_PACKAGE_CODE"));
						}
						if (DictCodeConstants.DICT_IS_YES.equals(map.get("IS_FINISHED"))) {
							TtRoRepairPartPO roRepairPart1 = TtRoRepairPartPO.findByCompositeKeys(dealerCode,
									map.get("ITEM_ID"));
							if (roRepairPart1 != null && roRepairPart1.getDouble("PART_SALES_PRICE") != Double
									.parseDouble(map.get("PART_SALES_PRICE"))) {
								List<PartFlowPO> flow = PartFlowPO.find(
										"REPAIR_PART_ID = ? AND SHEET_NO = ? AND DEALER_CODE = ? AND D_KEY = ? ",
										roRepairPart1.getLong("ITEM_ID"), roRepairPart1.getString("RO_NO"), dealerCode,
										CommonConstants.D_KEY);
								if (flow.size() > 0) {
									for (PartFlowPO partFlowPO : flow) {
										double partRate = Utility.add("1", Utility.getDefaultValue(
												String.valueOf(CommonConstants.DEFAULT_PARA_PART_RATE)));
										String rate = Double.toString(partRate);// 541
										partFlowPO.setLong("FLOW_ID", null);
										partFlowPO.setString("UPDATED_BY", null);
										partFlowPO.set("UPDATED_AT", null);
										partFlowPO.setString("CREATED_BY", FrameworkUtil.getLoginInfo().getUserId());
										partFlowPO.set("CREATED_AT", new Timestamp(System.currentTimeMillis()));
										partFlowPO.set("OPERATE_DATE", new Timestamp(System.currentTimeMillis()));
										partFlowPO.setDouble("IN_OUT_TAXED_PRICE",
												Utility.getDouble(map.get("PART_SALES_PRICE"))
														- roRepairPart1.getDouble("PART_SALES_PRICE"));
										partFlowPO.setFloat("IN_OUT_TAXED_AMOUNT",
												partFlowPO.getDouble("IN_OUT_TAXED_PRICE")
														* roRepairPart1.getFloat("PART_QUANTITY"));
										partFlowPO.setDouble("IN_OUT_NET_AMOUNT", Utility.div(
												String.valueOf(partFlowPO.getString("IN_OUT_TAXED_AMOUNT")), rate)); // 不含税金额
										partFlowPO.setDouble("IN_OUT_NET_PRICE", Utility.div(
												Double.toString(partFlowPO.getDouble("IN_OUT_TAXED_PRICE")), rate, 4)); // 不含税价格
										partFlowPO.setFloat("STOCK_OUT_QUANTITY", new Float(0));
										partFlowPO.setDouble("COST_AMOUNT", 0.00);
										partFlowPO.setString("OPERATOR", FrameworkUtil.getLoginInfo().getEmployeeNo());
										partFlowPO.saveIt();
										double amount = 0;
										amount = partFlowPO.getDouble("IN_OUT_TAXED_AMOUNT");
										// 会计月报表
										AccountPeriodPO cycle = AccountPeriodPO
												.findFirst("SYSDATE() BETWEEN BEGIN_DATE AND END_DATE");
										TtPartPeriodReportPO period = new TtPartPeriodReportPO();
										period.setString("PART_BATCH_NO", roRepairPart1.getString("PART_BATCH_NO"));
										period.setString("PART_NO", map.get("PART_NO"));
										period.setString("PART_NAME", map.get("PART_NAME"));
										period.setString("STORAGE_CODE", map.get("STORAGE_CODE"));
										period.setFloat("OUT_QUANTITY", Float.parseFloat("0"));// 收费区分调整，数量为0
										period.setDouble("OUT_AMOUNT", amount);
										period.setDouble("STOCK_OUT_COST_AMOUNT", Utility.getDouble("0"));
										period.setDouble("REPAIR_OUT_COST_AMOUNT", Utility.getDouble("0"));
										period.setFloat("REPAIR_OUT_COUNT", Float.parseFloat("0"));
										period.setDouble("REPAIR_OUT_SALE_AMOUNT", amount); // 金额的差价
										period.set("CREATED_BY", FrameworkUtil.getLoginInfo().getUserId());
										period.set("UPDATED_BY", FrameworkUtil.getLoginInfo().getUserId());
										addOrUpdateReport(period.getString("PART_BATCH_NO"), map.get("PART_NO"),
												map.get("STORAGE_CODE"), period, cycle);
									}
								}
							}
						}
					}
					roRepairPart.saveIt();
				} else if ("D".equals(status)) {
					// 入账的配件不允许删除(活动的配件在前台做判断)637
					TtRoRepairPartPO partPO = TtRoRepairPartPO.findByCompositeKeys(dealerCode, map.get("ITEM_ID"));
					if (!StringUtils.isNullOrEmpty(partPO) && partPO.getInteger("D_KEY") == CommonConstants.D_KEY) {
						if (DictCodeConstants.DICT_IS_YES.equals(partPO.getString("IS_FINISHED").trim())
								&& "".equals(partPO.getString("ACTIVITY_CODE").trim())) {
							throw new ServiceBizException("已经入账,不允许删除!");
						}
					}
					TtRoRepairPartPO.delete("ITEM_ID = ? AND DEALER_CODE = ? AND RO_NO = ? AND D_KEY = ?",
							Long.parseLong(map.get("ITEM_ID")), dealerCode, dto.getRoNo(), CommonConstants.D_KEY);
					TtRoTimeoutDetailPO.delete("DEALER_CODE = ? AND RO_NO = ? AND D_KEY = ? AND PART_NO = ?",
							dealerCode, dto.getRoNo(), CommonConstants.D_KEY, map.get("PART_NO"));
					// 主表：工单维修配件明细，主表：维修工单
					RepairOrderPO poDmsUpdate = RepairOrderPO.findByCompositeKeys(dealerCode, dto.getRoNo());
					if (!StringUtils.isNullOrEmpty(poDmsUpdate)
							&& poDmsUpdate.getInteger("D_KEY") == CommonConstants.D_KEY) {
						poDmsUpdate.saveIt();
					}
				}
			}
		}
		Boolean partCardId = ckeckFieldNotNull(dto.getDms_part(), "CARD_ID");
		if (partCardId) {
			/**
			 * 会员流水变更 产生步骤： (1) 查询工单中的会员卡配件项目 (2)计算工单中的各会员配件总和
			 * (3)查询配件流水中的该工单的所有配件流水 (4)查询配件流水中的该工单的所有配件流水总和 (5)
			 * (2)-(4)得到本次工单更新的会员配件信息 (6) 更新会员卡配件项目的可用配件数量
			 * 并校验可用配件数量与配件总数的比较(主要用于并发校验，不通过则回滚) (7)删除该工单所有流水记录
			 * (8)根据工单中的会员配件项目新增流水记录 注意：第五步的时候要考虑到1.在(2)中不存在但是在(4)中存在的配件
			 * 2.在(4)中不存在但是在(2)中存在的配件
			 */
			// 开始第一步(1)
			// 查询工单中的会员卡配件项目
			List<TtRoRepairPartPO> memberPartList = queryMemberPart(dto.getRoNo());
			// 计算工单中的各会员配件总和
			List<Map> memberPartListSum = queryMemberPartSum(dto.getRoNo());
			// 查询配件流水中的该工单的所有配件流水
			List<Map> memberPartFlowList = queryMemberPartFlow(dto.getRoNo());
			// 查询配件流水中的该工单的所有配件流水总和
			List<Map> memberPartFlowListSum = queryMemberPartFlowSum(dto.getRoNo());
			if (memberPartList != null && memberPartList.size() > 0 && memberPartFlowList.size() <= 0) {
				// 新增工单
				if (memberPartListSum != null && memberPartListSum.size() > 0) {
					for (Map map2 : memberPartListSum) {
						List<TtMemberPartPO> mp2 = TtMemberPartPO.where(
								"DEALER_CODE = ? AND CARD_ID = ? AND PART_NO = ?", group_code,
								map2.get("CARD_ID").toString(), map2.get("PART_NO"));
						for (TtMemberPartPO ttMemberPartPO : mp2) {
							ttMemberPartPO.setFloat("USED_PART_COUNT", ttMemberPartPO.getFloat("USED_PART_COUNT")
									+ Float.parseFloat(map2.get("PART_QUANTITY").toString()));
							ttMemberPartPO.saveIt();
							// 更新完总账后 判断总账中的已用配件数量和总配件数量的大小
							logger.debug("已用：" + ttMemberPartPO.getFloat("USED_PART_COUNT") + "总数："
									+ ttMemberPartPO.getFloat("PART_QUANTITY"));
							if (ttMemberPartPO.getFloat("USED_PART_COUNT") > ttMemberPartPO.getFloat("PART_QUANTITY")) {
								throw new ServiceBizException("可用数量不够,请重新操作!");
							}
						}
					}
				}
				// 新增流水
				for (TtRoRepairPartPO memberPartPO : memberPartList) {
					TtMemberPartPO tp = TtMemberPartPO.findFirst("DEALER_CODE = ? AND CARD_ID = ? AND PART_NO = ?",
							group_code, memberPartPO.getString("CARD_ID"), memberPartPO.getString("PART_NO"));
					// 开始插入配件项目流水记录
					TtMemberPartFlowPO memberPartFlowPO = new TtMemberPartFlowPO();
					memberPartFlowPO.setString("DEALER_CODE", group_code);
					memberPartFlowPO.setString("CARD_ID", memberPartPO.getString("CARD_ID"));
					memberPartFlowPO.setString("RO_NO", dto.getRoNo());
					memberPartFlowPO.setString("STORAGE_CODE", memberPartPO.getString("STORAGE_CODE") != null
							? memberPartPO.getString("STORAGE_CODE") : "");
					memberPartFlowPO.setString("PART_NO", memberPartPO.getString("PART_NO"));
					memberPartFlowPO.setString("PART_NAME", memberPartPO.getString("PART_NAME"));
					memberPartFlowPO.setString("LABOUR_CODE", memberPartPO.getString("LABOUR_CODE"));
					memberPartFlowPO.setFloat("PART_QUANTITY", tp.getFloat("PART_QUANTITY"));
					memberPartFlowPO.setFloat("THIS_USE_QUANTITY", memberPartPO.getFloat("PART_QUANTITY"));
					memberPartFlowPO.setFloat("USED_PART_QUANTITY", tp.getFloat("USED_PART_COUNT"));
					memberPartFlowPO.setString("OPERATOR", FrameworkUtil.getLoginInfo().getEmployeeNo());
					memberPartFlowPO.set("OPERATE_TIME", new Timestamp(System.currentTimeMillis()));
					if (tp.getInteger("BUSINESS_TYPE") != null) {
						memberPartFlowPO.setInteger("BUSINESS_TYPE", tp.getInteger("BUSINESS_TYPE"));
					}
					memberPartFlowPO.setString("UNIT_NAME",
							memberPartPO.getString("UNIT_CODE") != null ? memberPartPO.getString("UNIT_CODE") : "");
					memberPartFlowPO.setString("CHARGE_PARTITION_CODE",
							memberPartPO.getString("CHARGE_PARTITION_CODE") != null
									? memberPartPO.getString("CHARGE_PARTITION_CODE") : "");
					memberPartFlowPO.setDouble("PART_COST_PRICE", memberPartPO.getDouble("PART_COST_PRICE") != null
							? memberPartPO.getDouble("PART_COST_PRICE") : 0.00);
					memberPartFlowPO.setDouble("PART_SALES_PRICE", memberPartPO.getDouble("PART_SALES_PRICE") != null
							? memberPartPO.getDouble("PART_SALES_PRICE") : 0.00);
					memberPartFlowPO.setDouble("PART_COST_AMOUNT", memberPartPO.getDouble("PART_COST_AMOUNT") != null
							? memberPartPO.getDouble("PART_COST_AMOUNT") : 0.00);
					memberPartFlowPO.setDouble("PART_SALES_AMOUNT", memberPartPO.getDouble("PART_SALES_AMOUNT") != null
							? memberPartPO.getDouble("PART_SALES_AMOUNT") : 0.00);
					memberPartFlowPO.setFloat("DISCOUNT", memberPartPO.getFloat("DISCOUNT"));
					memberPartFlowPO.saveIt();
				}
			} else if (memberPartList.size() <= 0 && memberPartFlowList != null && memberPartFlowList.size() > 0) {
				for (Map memberPartFlowPO : memberPartFlowListSum) {
					// 计算总账
					for (TtRoRepairPartPO memberPartPO : memberPartList) {
						List<TtMemberPartPO> mp2 = TtMemberPartPO.find(
								"DEALER_CODE = ? AND CARD_ID = ? AND PART_NO = ?", group_code,
								memberPartPO.getString("CARD_ID"), memberPartPO.getString("PART_NO"));
						for (TtMemberPartPO ttMemberPartPO : mp2) {
							ttMemberPartPO.setFloat("USED_PART_COUNT",
									Float.parseFloat(memberPartFlowPO.get("USED_PART_QUANTITY").toString())
											- Float.parseFloat(memberPartFlowPO.get("THIS_USE_QUANTITY").toString()));
							ttMemberPartPO.saveIt();
						}
					}
				}
				// 删除该工单的所有流水
				if (!StringUtils.isNullOrEmpty(dto.getRoNo())) {
					TtMemberPartFlowPO.delete("DEALER_CODE = ? AND RO_NO = ?", dealerCode, dto.getRoNo());
				}
				handleOperateLog("删除会员卡配件流水", "TT_MEMBER_PART_FLOW,RO_NO=" + dto.getRoNo(),
						Integer.parseInt(DictCodeConstants.DICT_ASCLOG_MEMBER_MANAGE),
						FrameworkUtil.getLoginInfo().getEmployeeNo());
			} else if (memberPartList != null && memberPartList.size() > 0 && memberPartFlowList != null
					&& memberPartFlowList.size() > 0) {
				// 编辑工单、维修领料、工单作废
				// 工单-流水 对比
				for (Map map : memberPartListSum) {
					// 定义变量 isFound 默认为否 当roPartNo和flowPartNo相同时为是
					String isFound = DictCodeConstants.DICT_IS_NO;
					String roPartNo = map.get("PART_NO").toString();
					TtMemberPartPO tm1 = TtMemberPartPO.findFirst("DEALER_CODE = ? AND CARD_ID = ? AND PART_NO = ?",
							group_code, map.get("CARD_ID"), map.get("PART_NO"));
					for (Map memberPartFlowPO : memberPartFlowListSum) {
						// 流水中的配件代码
						String flowPartNo = memberPartFlowPO.get("PART_NO").toString();
						// 如果工单中的配件在流水中已存在，则计算差值
						if (roPartNo.equals(flowPartNo)) {
							// 更新总账
							TtMemberPartPO mp2 = TtMemberPartPO.findFirst(
									"DEALER_CODE = ? AND CARD_ID = ? AND PART_NO = ?", group_code, map.get("CARD_ID"),
									roPartNo);
							mp2.setFloat("USED_PART_COUNT",
									mp2.getFloat("USED_PART_COUNT")
											+ Float.parseFloat(map.get("PART_QUANTITY").toString())
											- Float.parseFloat(memberPartFlowPO.get("UsedPartQuantity").toString()));
							mp2.saveIt();
							// 更新完总账后 判断总账中的已用配件数量和总配件数量的大小
							if (mp2.getFloat("USED_PART_COUNT") > mp2.getFloat("PART_QUANTITY")) {
								throw new ServiceBizException("可用数量不够,请重新操作!");
							}
							// 删除配件流水
							handleOperateLog("删除会员卡配件流水",
									"TT_MEMBER_PART_FLOW,ITEM_ID=" + memberPartFlowPO.get("ITEM_ID"),
									Integer.parseInt(DictCodeConstants.DICT_ASCLOG_MEMBER_MANAGE),
									FrameworkUtil.getLoginInfo().getEmployeeNo());
							TtMemberPartFlowPO.delete("DEALER_CODE = ? AND ITEM_ID = ? ", group_code,
									memberPartFlowPO.get("ITEM_ID"));
							isFound = DictCodeConstants.DICT_IS_YES;
							break;
						}
					}
					if (isFound.equals(DictCodeConstants.DICT_IS_NO)) {
						TtMemberPartPO mp2 = TtMemberPartPO.findFirst("DEALER_CODE = ? AND CARD_ID = ? AND PART_NO = ?",
								group_code, map.get("CARD_ID"), roPartNo);
						mp2.setFloat("USED_PART_COUNT", mp2.getFloat("USED_PART_COUNT")
								+ Float.parseFloat(map.get("PART_QUANTITY").toString()));
						mp2.saveIt();
						// 更新完总账后 判断总账中的已用配件数量和总配件数量的大小
						logger.debug("已用：" + mp2.getFloat("USED_PART_COUNT") + "总数：" + mp2.getFloat("PART_QUANTITY"));
						if (mp2.getFloat("USED_PART_COUNT") > mp2.getFloat("PART_QUANTITY")) {
							throw new ServiceBizException("可用数量不够,请重新操作!");
						}
					}
					// 新增配件流水
					TtMemberPartFlowPO flow = new TtMemberPartFlowPO();
					// 开始插入配件项目流水记录
					flow.setString("DEALER_CODE", group_code);
					flow.setLong("CARD_ID", Long.parseLong(map.get("CARD_ID").toString()));
					flow.setString("RO_NO", dto.getRoNo());
					flow.setString("STORAGE_CODE", map.get("STORAGE_CODE").toString());
					flow.setString("PART_NO", map.get("PART_NO").toString());
					flow.setString("PART_NAME", map.get("PART_NAME").toString());
					flow.setString("LABOUR_CODE", map.get("LABOUR_CODE").toString());
					flow.setFloat("PART_QUANTITY", tm1.getFloat("PART_QUANTITY"));
					flow.setFloat("THIS_USE_QUANTITY", Float.parseFloat(map.get("PART_QUANTITY").toString()));
					flow.setFloat("USED_PART_QUANTITY", tm1.getFloat("USED_PART_COUNT"));
					flow.setString("OPERATOR", FrameworkUtil.getLoginInfo().getEmployeeNo());
					flow.set("OPERATE_TIME", new Timestamp(System.currentTimeMillis()));
					if (tm1.getInteger("BUSINESS_TYPE") != null) {
						flow.setInteger("BUSINESS_TYPE", tm1.getInteger("BUSINESS_TYPE"));
					}
					flow.setString("UNIT_NAME", map.get("UNIT_CODE") != null ? map.get("UNIT_CODE").toString() : "");
					flow.setString("CHARGE_PARTITION_CODE", map.get("CHARGE_PARTITION_CODE") != null
							? map.get("CHARGE_PARTITION_CODE").toString() : "");
					if (!StringUtils.isNullOrEmpty(map.get("PART_COST_PRICE"))) {
						flow.setDouble("PART_COST_PRICE", Double.parseDouble(map.get("PART_COST_PRICE").toString()));
					} else {
						flow.setDouble("PART_COST_PRICE", 0.00);
					}
					if (!StringUtils.isNullOrEmpty(map.get("PART_SALES_PRICE"))) {
						flow.setDouble("PART_SALES_PRICE", Double.parseDouble(map.get("PART_SALES_PRICE").toString()));
					} else {
						flow.setDouble("PART_SALES_PRICE", 0.00);
					}
					if (!StringUtils.isNullOrEmpty(map.get("PART_COST_AMOUNT"))) {
						flow.setDouble("PART_COST_AMOUNT", Double.parseDouble(map.get("PART_COST_AMOUNT").toString()));
					} else {
						flow.setDouble("PART_COST_AMOUNT", 0.00);
					}
					if (!StringUtils.isNullOrEmpty(map.get("PART_SALES_AMOUNT"))) {
						flow.setDouble("PART_SALES_AMOUNT",
								Double.parseDouble(map.get("PART_SALES_AMOUNT").toString()));
					} else {
						flow.setDouble("PART_SALES_AMOUNT", 0.00);
					}

					flow.setFloat("DISCOUNT", Float.parseFloat(map.get("DISCOUNT").toString()));
					flow.saveIt();
				}
				// 流水-工单 对比
				for (Map memberPartFlowPO : memberPartFlowListSum) {
					String isFound2 = DictCodeConstants.DICT_IS_YES;
					// 流水中的配件代码
					String flowPartNo = memberPartFlowPO.get("PART_NO").toString();
					TtMemberPartPO tm2 = TtMemberPartPO.findFirst("DEALER_CODE = ? AND CARD_ID = ? AND PART_NO = ?",
							group_code, memberPartFlowPO.get("CARD_ID"), memberPartFlowPO.get("PART_NO"));
					for (Map partBean : memberPartListSum) {
						// 工单中的配件代码
						String roPartNo = partBean.get("PART_NO").toString();
						if (flowPartNo.equals(roPartNo)) {
							isFound2 = DictCodeConstants.DICT_IS_NO;
							break;
						}
					}
					if (isFound2.equals(DictCodeConstants.DICT_IS_YES)) {
						TtMemberPartPO mp2 = TtMemberPartPO.findFirst("DEALER_CODE = ? AND CARD_ID = ? AND PART_NO = ?",
								group_code, memberPartFlowPO.get("CARD_ID"), memberPartFlowPO.get("PART_NO"));
						mp2.setFloat("USED_PART_COUNT", tm2.getFloat("USED_PART_COUNT")
								- Float.parseFloat(memberPartFlowPO.get("THIS_USE_QUANTITY").toString()));
						mp2.saveIt();
						// 删除配件流水
						handleOperateLog("删除会员卡配件流水", "TT_MEMBER_PART_FLOW,ITEM_ID=" + memberPartFlowPO.get("ITEM_ID"),
								Integer.parseInt(DictCodeConstants.DICT_ASCLOG_MEMBER_MANAGE),
								FrameworkUtil.getLoginInfo().getEmployeeNo());
						TtMemberPartFlowPO.delete("DEALER_CODE = ? AND ITEM_ID = ?", group_code,
								memberPartFlowPO.get("ITEM_ID"));
					}
				}
			}
		}
		// 判断配件是否存在 被隔离的现象
		String errPartDel = checkPartStockIsDeleteBySheetNo1("TT_RO_REPAIR_PART", "RO_NO", dto.getRoNo(),
				"IS_FINISHED");
		if (!errPartDel.equals(""))
			throw new ServiceBizException("配件库存已经删除,请更换重新操作!");
		// * 判断工单的关单状态是否正确. 配件从不修->领料关单->修改为 修 原来是没有办法处理
		// * 现在增加判断这种情况.
		LazyList<TtRoRepairPartPO> listPart = TtRoRepairPartPO.find(
				"DEALER_CODE = ? AND RO_NO = ? AND NEEDLESS_REPAIR = ? AND IS_FINISHED = ? AND D_KEY = ?", dealerCode,
				dto.getRoNo(), DictCodeConstants.DICT_IS_NO, DictCodeConstants.DICT_IS_NO, CommonConstants.D_KEY);
		if (listPart != null && listPart.size() > 0) {
			RepairOrderPO repairPO = RepairOrderPO.findFirst("DEALER_CODE = ? AND RO_NO = ? AND D_KEY = ?", dealerCode,
					dto.getRoNo(), CommonConstants.D_KEY);
			repairPO.setLong("IS_CLOSE_RO", DictCodeConstants.IS_NOT);
			logger.debug("更新工单关单状态");
			repairPO.saveIt();
		}
		// 会员活动逻辑
		Boolean labourCardId = ckeckFieldNotNull(dto.getDms_table(), "CARD_ID");
		Boolean labourActivityCode = ckeckFieldNotNull(dto.getDms_table(), "ACTIVITY_CODE");
		Boolean partActivityCode = ckeckFieldNotNull(dto.getDms_part(), "ACTIVITY_CODE");
		if (labourCardId && labourActivityCode && partCardId && partActivityCode) {
			logger.debug("======工单中包含会员活动===================");
			// 获取工单维修项目和配件中的会员活动总和
			List<Map> roActiList = queryRoMemberActivity(dto.getRoNo());
			// 获取工单流水会员活动总和
			List<Map> deActiList = queryActiDetailSum(dto.getRoNo());
			TmMemberCardActivityPO md2 = null;
			TtMemberActivityPO activityPO = null;
			TtMemCardActiDetailPO actiDetailPO = null;
			if (roActiList != null && roActiList.size() > 0 && deActiList.size() <= 0) {
				// 工单新增
				for (Map map : roActiList) {
					// 计算总账
					md2 = TmMemberCardActivityPO.findFirst("DEALER_CODE = ? AND CARD_ID = ? MEMBER_ACTIVITY_CODE = ?",
							group_code, map.get("CARD_ID"), map.get("ACTIVITY_CODE"));
					md2.setInteger("USED_TICKET_COUNT", md2.getInteger("USED_TICKET_COUNT") + 1);
					md2.saveIt();
					// 更新完总账后 判断总账中的已用活动次数和总购买次数的大小
					logger.debug(
							"已用：" + md2.getInteger("USED_TICKET_COUNT") + "总数：" + md2.getInteger("PURCHASE_COUNT"));
					if (md2.getInteger("USED_TICKET_COUNT") > md2.getInteger("PURCHASE_COUNT")) {
						throw new ServiceBizException("可用次数不够,请重新操作!");
					}
					// 生成活动使用明细
					activityPO = TtMemberActivityPO.findFirst("DEALER_CODE = ? AND MEMBER_ACTIVITY_CODE = ?",
							group_code, map.get("ACTIVITY_CODE"));
					actiDetailPO = new TtMemCardActiDetailPO();
					actiDetailPO.setString("DEALER_CODE", group_code);
					actiDetailPO.setLong("CARD_ID", Long.parseLong(map.get("CARD_ID").toString()));
					actiDetailPO.setString("MEMBER_ACTIVITY_CODE", map.get("ACTIVITY_CODE").toString());
					actiDetailPO.setString("MEMBER_ACTIVITY_NAME", activityPO.getString("MEMBER_ACTIVITY_NAME"));
					actiDetailPO.setInteger("USE_ACTI_COUNT", 1);
					actiDetailPO.setString("OPERATOR", FrameworkUtil.getLoginInfo().getEmployeeNo());
					actiDetailPO.set("OPERATE_TIME", new Timestamp(System.currentTimeMillis()));
					actiDetailPO.setInteger("D_KEY", CommonConstants.D_KEY);
					actiDetailPO.setString("RO_NO", dto.getRoNo());
					actiDetailPO.saveIt();
				}
			} else if (roActiList.size() <= 0 && deActiList != null && deActiList.size() > 0) {
				// 工单编辑，全部删除工单中的会员活动
				for (Map map : deActiList) {
					md2 = TmMemberCardActivityPO.findFirst(
							"DEALER_CODE = ? AND CARD_ID = ? AND MEMBER_ACTIVITY_CODE = ?", dealerCode,
							map.get("CARD_ID"), map.get("MEMBER_ACTIVITY_CODE"));
					logger.debug("更新前该会员活动已用次数为 " + md2.getInteger("USED_TICKET_COUNT"));
					md2.setInteger("USED_TICKET_COUNT", md2.getInteger("USED_TICKET_COUNT") - 1);
					md2.saveIt();
					handleOperateLog("删除会员活动流水", "TT_MEM_CARD_ACTI_DETAIL,ITEM_ID=" + map.get("ITEM_ID"),
							Integer.parseInt(DictCodeConstants.DICT_ASCLOG_MEMBER_MANAGE),
							FrameworkUtil.getLoginInfo().getEmployeeNo());
					// 删除该工单的所有会员活动流水
					TtMemCardActiDetailPO.delete("DEALER_CODE = ? AND ITEM_ID = ?", map.get("DEALER_CODE"),
							map.get("ITEM_ID"));
				}
			} else if (roActiList != null && roActiList.size() > 0 && deActiList != null && deActiList.size() > 0) {
				// 编辑工单、维修领料
				// 工单-流水 对比
				for (Map map : roActiList) {
					// 工单中的会员活动编号
					String roActiCode = map.get("ACTIVITY_CODE").toString();
					List<TtMemCardActiDetailPO> flowList = TtMemCardActiDetailPO.find(
							"DEALER_CODE = ? AND RO_NO = ? AND MEMBER_ACTIVITY_CODE = ?", group_code, dto.getRoNo(),
							roActiCode);
					if (flowList != null && flowList.size() > 0) {
						continue;
					} else {
						// 否则，工单中的会员活动为新增
						md2 = TmMemberCardActivityPO.findFirst(
								"DEALER_CODE = ? AND CARD_ID = ? AND MEMBER_ACTIVITY_CODE = ?", group_code,
								map.get("CARD_ID"), map.get("ACTIVITY_CODE"));
						md2.setInteger("USED_TICKET_COUNT", md2.getInteger("USED_TICKET_COUNT") + 1);
						md2.saveIt();
						// 更新完总账后 判断总账中的已用活动次数和总购买次数的大小
						logger.debug(
								"已用：" + md2.getInteger("USED_TICKET_COUNT") + "总数：" + md2.getInteger("PURCHASE_COUNT"));
						if (md2.getInteger("USED_TICKET_COUNT") > md2.getInteger("PURCHASE_COUNT")) {
							throw new ServiceBizException("可用次数不够,请重新操作!");
						}
						// 生成活动使用明细
						activityPO = TtMemberActivityPO.findFirst("DEALER_CODE = ? AND MEMBER_ACTIVITY_CODE = ?",
								group_code, map.get("ACTIVITY_CODE"));
						actiDetailPO = new TtMemCardActiDetailPO();
						actiDetailPO.setString("DEALER_CODE", group_code);
						actiDetailPO.setLong("CARD_ID", Long.parseLong(map.get("CARD_ID").toString()));
						actiDetailPO.setString("MEMBER_ACTIVITY_CODE", map.get("ACTIVITY_CODE").toString());
						actiDetailPO.setString("MEMBER_ACTIVITY_NAME", activityPO.getString("MEMBER_ACTIVITY_NAME"));
						actiDetailPO.setInteger("USE_ACTI_COUNT", 1);
						actiDetailPO.setString("OPERATOR", FrameworkUtil.getLoginInfo().getEmployeeNo());
						actiDetailPO.set("OPERATE_TIME", new Timestamp(System.currentTimeMillis()));
						actiDetailPO.setInteger("D_KEY", CommonConstants.D_KEY);
						actiDetailPO.setString("RO_NO", dto.getRoNo());
						actiDetailPO.saveIt();
						logger.debug("====工单中添加新的会员活动===============");
					}
				}
				// 流水-工单 对比
				for (Map map : deActiList) {
					// 流水中的配件代码
					String flowActiCode = map.get("MEMBER_ACTIVITY_CODE").toString();
					List<Map> newRolist = queryRoMemberActivityByCode(dto.getRoNo(), flowActiCode);
					if (newRolist != null && newRolist.size() > 0) {
						// 如果流水中的会员活动编号在工单中已存在，则跳过循环
						continue;
					} else {
						// 工单中删除会员活动、更新该活动的活动已用次数
						md2 = TmMemberCardActivityPO.findFirst(
								"DEALER_CODE = ? AND CARD_ID = ? AND MEMBER_ACTIVITY_CODE = ?", dealerCode,
								map.get("CARD_ID"), map.get("MEMBER_ACTIVITY_CODE"));
						md2.setInteger("USED_TICKET_COUNT", md2.getInteger("USED_TICKET_COUNT") - 1);
						md2.saveIt();
						handleOperateLog("删除会员活动流水", "TT_MEM_CARD_ACTI_DETAIL,ITEM_ID=" + map.get("ITEM_ID"),
								Integer.parseInt(DictCodeConstants.DICT_ASCLOG_MEMBER_MANAGE),
								FrameworkUtil.getLoginInfo().getEmployeeNo());
						// 否则删除流水中的会员活动
						TtMemCardActiDetailPO.delete("DEALER_CODE = ? AND ITEM_ID = ?", map.get("DEALER_CODE"),
								map.get("ITEM_ID"));
						logger.debug("=====删除工单中的会员活动，更新已用次数并删除流水==================");
					}
				}
			}

		}
	}

	/**
	 * 根据工单号和活动编号确定参与维修的会员活动
	 * 
	 * @param roNo
	 * @param activityCode
	 * @return
	 */
	public List<Map> queryRoMemberActivityByCode(String roNo, String activityCode) {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT DISTINCT ACTIVITY_CODE,CARD_ID FROM ( ")
				.append(" SELECT A.ACTIVITY_CODE,A.CARD_ID FROM  TT_RO_REPAIR_PART A  WHERE A.DEALER_CODE='")
				.append(FrameworkUtil.getLoginInfo().getDealerCode()).append("' AND ").append(" A.RO_NO='").append(roNo)
				.append("' AND A.ACTIVITY_CODE IS NOT NULL AND A.ACTIVITY_CODE!='' AND A.CARD_ID IS NOT NULL AND A.CARD_ID!=0 ");
		if (!StringUtils.isNullOrEmpty(activityCode))
			sb.append(" AND A.ACTIVITY_CODE= '").append(activityCode).append("' ");
		sb.append(" UNION SELECT B.ACTIVITY_CODE,B.CARD_ID FROM  TT_RO_LABOUR B  WHERE B.DEALER_CODE='")
				.append(FrameworkUtil.getLoginInfo().getDealerCode()).append("' AND ").append("  B.RO_NO='")
				.append(roNo)
				.append("' AND B.ACTIVITY_CODE IS NOT NULL AND B.ACTIVITY_CODE!='' AND B.CARD_ID IS NOT NULL AND B.CARD_ID!=0 ");
		if (!StringUtils.isNullOrEmpty(activityCode))
			sb.append(" AND B.ACTIVITY_CODE= '").append(activityCode).append("' ");
		sb.append(") ");
		return DAOUtil.findAll(sb.toString(), null);
	}

	/**
	 * 获取工单流水会员活动总和
	 * 
	 * @param roNo
	 * @return
	 */
	public List<Map> queryActiDetailSum(String roNo) {
		StringBuffer sb = new StringBuffer();
		sb.append("select A.* from (" + CommonConstants.VM_MEM_CARD_ACTI_DETAIL + ") A where A.DEALER_CODE='")
				.append(FrameworkUtil.getLoginInfo().getDealerCode()).append("'  ");
		if (!StringUtils.isNullOrEmpty(roNo)) {
			sb.append(" and A.ro_no='").append(roNo).append("' ");
		}
		return DAOUtil.findAll(sb.toString(), null);
	}

	/**
	 * 根据工单号和cardid确定参与维修的会员活动
	 * 
	 * @param roNo
	 * @return
	 */
	public List<Map> queryRoMemberActivity(String roNo) {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT DISTINCT ACTIVITY_CODE,CARD_ID FROM ( ")
				.append(" SELECT A.ACTIVITY_CODE,A.CARD_ID FROM  TT_RO_REPAIR_PART A  WHERE  A.DEALER_CODE='")
				.append(FrameworkUtil.getLoginInfo().getDealerCode()).append("' AND  A.RO_NO='").append(roNo)
				.append("' AND A.ACTIVITY_CODE IS NOT NULL AND A.ACTIVITY_CODE!='' AND A.CARD_ID IS NOT NULL AND A.CARD_ID!=0 ")
				.append(" UNION SELECT B.ACTIVITY_CODE,B.CARD_ID FROM  TT_RO_LABOUR B  WHERE B.DEALER_CODE='")
				.append(FrameworkUtil.getLoginInfo().getDealerCode()).append("' AND   B.RO_NO='").append(roNo)
				.append("' AND B.ACTIVITY_CODE IS NOT NULL AND B.ACTIVITY_CODE!='' AND B.CARD_ID IS NOT NULL AND B.CARD_ID!=0 ")
				.append(") ");
		return DAOUtil.findAll(sb.toString(), null);
	}

	/**
	 * 通过相关单号查询 对应配件是否删除
	 * 
	 * @param sheetTable
	 * @param sheetFieldName
	 * @param sheetNo
	 * @param IS_FINISHED
	 * @return
	 */
	public String checkPartStockIsDeleteBySheetNo1(String sheetTable, String sheetFieldName, String sheetNo,
			String IS_FINISHED) {
		String returnPartNo = "";
		// 开关未打开直接退出
		if (Utility.getDefaultValue("8575").equals(DictCodeConstants.DICT_IS_NO)) {
			return "";
		} else {
			StringBuffer sb = new StringBuffer();
			sb.append("select B.D_KEY,A.PART_NO,A.PART_NAME,A.STORAGE_CODE from ").append(sheetTable).append(" A ")
					.append(" LEFT JOIN tm_part_stock B ON (a.DEALER_CODE = b.DEALER_CODE and a.part_no = b.part_no and a.STORAGE_CODE = b.STORAGE_CODE) ")
					.append(" WHERE A.").append(sheetFieldName).append(" = '").append(sheetNo).append("' ")
					.append(" and A.").append(IS_FINISHED).append(" = ").append(DictCodeConstants.DICT_IS_NO)
					.append(" and A.DEALER_CODE = '").append(FrameworkUtil.getLoginInfo().getDealerCode())
					.append("' AND B.D_KEY = ").append(CommonConstants.DEFAULT_PARA_PART_DELETE_KEY);
			List<Map> returnList = DAOUtil.findAll(sb.toString(), null);
			if (returnList != null && returnList.size() > 0) {
				for (Map map : returnList) {
					if (returnPartNo.equals("")) {
						returnPartNo = map.get("PART_NO").toString();
					} else {
						returnPartNo = returnPartNo + ", " + map.get("PART_NO").toString();
					}
				}
			}
			return "";
		}
	}

	/**
	 * 查询配件流水明细总和
	 * 
	 * @param roNo
	 * @return
	 */
	public List<Map> queryMemberPartFlowSum(String roNo) {
		StringBuffer sb = new StringBuffer();
		sb.append("select CARD_ID,DEALER_CODE,ITEM_ID,RO_NO,STORAGE_CODE,PART_NAME,PART_NO,")
				.append(" PART_COST_AMOUNT,PART_COST_PRICE,PART_SALES_AMOUNT,PART_SALES_PRICE,PART_QUANTITY,")
				.append(" CHARGE_PARTITION_CODE,UNIT_NAME,DISCOUNT,IS_MAIN_PART,")
				.append(" OPERATE_TIME,OPERATOR,LABOUR_CODE,CREATED_BY,CREATED_AT,D_KEY,UPDATED_BY,UPDATED_BY,VER,")
				.append(" sum(USED_PART_QUANTITY) USED_PART_QUANTITY,sum(THIS_USE_QUANTITY) THIS_USE_QUANTITY,BUSINESS_TYPE ")
				.append(" from VM_MEMBER_PART_FLOW where 1=1");
		if (!StringUtils.isNullOrEmpty(roNo)) {
			sb.append(" AND F.RO_NO='").append(roNo).append("' ");
		}
		sb.append(" group by CARD_ID,DEALER_CODE,ITEM_ID,RO_NO,STORAGE_CODE,PART_NAME,PART_NO,")
				.append(" PART_COST_AMOUNT,PART_COST_PRICE,PART_SALES_AMOUNT,PART_SALES_PRICE,PART_QUANTITY,")
				.append(" CHARGE_PARTITION_CODE,UNIT_NAME,DISCOUNT,IS_MAIN_PART,")
				.append(" OPERATE_TIME,OPERATOR,LABOUR_CODE,CREATED_BY,CREATED_AT,D_KEY,UPDATED_BY,UPDATED_BY,VER,BUSINESS_TYPE ");
		return DAOUtil.findAll(sb.toString(), null);
	}

	/**
	 * 查询配件流水明细
	 * 
	 * @param roNo
	 * @return
	 */
	public List<Map> queryMemberPartFlow(String roNo) {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT F.* FROM (" + CommonConstants.VM_MEMBER_PART_FLOW + ") F WHERE 1=1 ");
		if (!StringUtils.isNullOrEmpty(roNo)) {
			sb.append(" AND F.RO_NO='").append(roNo).append("' ");
		}
		return DAOUtil.findAll(sb.toString(), null);
	}

	/**
	 * 查询工单中会员配件项目总和
	 * 
	 * @param roNo
	 * @return
	 */
	public List<Map> queryMemberPartSum(String roNo) {
		StringBuffer sb = new StringBuffer();
		sb.append(" select ITEM_ID,DEALER_CODE,RO_NO,PART_NO,PART_NAME,")
				.append(" STORAGE_CODE,CHARGE_PARTITION_CODE,STORAGE_POSITION_CODE,")
				.append(" UNIT_CODE,PART_BATCH_NO,MANAGE_SORT_CODE,OUT_STOCK_NO,PRICE_TYPE,IS_MAIN_PART,")
				.append(" PRICE_RATE,OEM_LIMIT_PRICE,PART_COST_PRICE,PART_SALES_PRICE,")
				.append(" PART_COST_AMOUNT,PART_SALES_AMOUNT,SENDER,RECEIVER,SEND_TIME,IS_FINISHED,BATCH_NO,")
				.append(" ACTIVITY_CODE,PRE_CHECK,DISCOUNT,INTER_RETURN,NEEDLESS_REPAIR,CONSIGN_EXTERIOR,")
				.append(" PRINT_RP_TIME,PRINT_BATCH_NO,LABOUR_CODE,MODEL_LABOUR_CODE,PACKAGE_CODE,")
				.append(" IS_DISCOUNT,REPAIR_TYPE_CODE,NON_ONE_OFF,ADD_TAG,D_KEY,CREATED_BY,CREATED_AT,")
				.append(" UPDATED_BY,UPDATED_AT,VER,REASON,CARD_ID,FROM_TYPE,LABOUR_NAME,ITEM_ID_LABOUR,SUM(PART_QUANTITY) PART_QUANTITY,DXP_DATE")
				.append(" FROM TT_RO_REPAIR_PART WHERE ")
				.append("CARD_ID IS NOT NULL AND CARD_ID!=0 AND (ACTIVITY_CODE IS NULL OR ACTIVITY_CODE='')  ");
		if (!StringUtils.isNullOrEmpty(roNo)) {
			sb.append(" AND RO_NO='").append(roNo).append("' ");
		}
		sb.append(" group by ITEM_ID,DEALER_CODE,RO_NO,PART_NO,PART_NAME,STORAGE_CODE,CHARGE_PARTITION_CODE,")
				.append(" STORAGE_POSITION_CODE,UNIT_CODE,PART_BATCH_NO,MANAGE_SORT_CODE,OUT_STOCK_NO,PRICE_TYPE,IS_MAIN_PART,")
				.append(" PRICE_RATE,OEM_LIMIT_PRICE,PART_COST_PRICE,PART_SALES_PRICE,")
				.append(" PART_COST_AMOUNT,PART_SALES_AMOUNT,SENDER,RECEIVER,SEND_TIME,IS_FINISHED,BATCH_NO,")
				.append(" ACTIVITY_CODE,PRE_CHECK,DISCOUNT,INTER_RETURN,NEEDLESS_REPAIR,CONSIGN_EXTERIOR,")
				.append(" PRINT_RP_TIME,PRINT_BATCH_NO,LABOUR_CODE,MODEL_LABOUR_CODE,PACKAGE_CODE,")
				.append(" IS_DISCOUNT,REPAIR_TYPE_CODE,NON_ONE_OFF,ADD_TAG,D_KEY,CREATED_BY,CREATED_AT,")
				.append(" UPDATED_BY,UPDATED_AT,VER,REASON,CARD_ID,FROM_TYPE,LABOUR_NAME,ITEM_ID_LABOUR,DXP_DATE");
		return DAOUtil.findAll(sb.toString(), null);
	}

	/**
	 * 查询工单中会员配件项目明细
	 * 
	 * @param roNo
	 * @return
	 */
	public List<TtRoRepairPartPO> queryMemberPart(String roNo) {
		StringBuffer sb = new StringBuffer();
		sb.append(
				"DEALER_CODE = ? AND CARD_ID IS NOT NULL AND CARD_ID!=0 AND  (ACTIVITY_CODE IS NULL OR ACTIVITY_CODE='')  ");
		if (!StringUtils.isNullOrEmpty(roNo)) {
			sb.append(" AND RO_NO='").append(roNo).append("' ");
		}
		return TtRoRepairPartPO.find(sb.toString(), FrameworkUtil.getLoginInfo().getDealerCode());
	}

	/**
	 * 根据工单号,项目代码,维修组合,查询维修项目ID
	 * 
	 * @param labourCode
	 * @param modelLabourCode
	 * @param packageCode
	 * @param roNo
	 * @param labourName
	 * @return
	 */
	public List<TtRoLabourPO> queryRoLabourByCodeAndRoNo(String labourCode, String modelLabourCode, String packageCode,
			String roNo, String labourName) {
		StringBuffer sb = new StringBuffer();
		sb.append("DEALER_CODE = '").append(FrameworkUtil.getLoginInfo().getDealerCode()).append("'");
		if (!StringUtils.isNullOrEmpty(labourCode))
			sb.append(" AND LABOUR_CODE = '").append(labourCode).append("'");
		if (!StringUtils.isNullOrEmpty(roNo))
			sb.append(" AND RO_NO = '").append(roNo).append("'");
		if (!StringUtils.isNullOrEmpty(labourName))
			sb.append(" AND LABOUR_NAME = '").append(labourName).append("'");
		return TtRoLabourPO.find(sb.toString());
	}

	/**
	 * 新增.修改.删除工单维修项目
	 * 
	 * @param dto
	 */
	public void maintainRoLabour(RepairOrderDetailsDTO dto) throws ServiceBizException {
		List<Map<String, String>> roLabours = dto.getDms_table();
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		String groupCode = Utility.getGroupEntity(dealerCode, "TM_MEMBER");
		Boolean isQs = false;
		for (Map<String, String> map : roLabours) {
			String status = map.get("rowKey");
			if (!StringUtils.isNullOrEmpty(status)) {
				if ("0".equals(map.get("CARD_ID"))) {
					logger.debug("-------cardId---------" + map.get("CARD_ID"));
					map.put("CARD_ID", "");
				}
				if (status.equals("A")) {
					TtRoLabourPO roLabour = new TtRoLabourPO();
					roLabour.setString("MANAGE_SORT_CODE", map.get("MANAGE_SORT_CODE"));
					roLabour.setString("LABOUR_CODE", map.get("LABOUR_CODE"));
					roLabour.setString("LABOUR_NAME", map.get("LABOUR_NAME"));
					roLabour.setString("ACTIVITY_CODE", map.get("ACTIVITY_CODE"));
					roLabour.setString("MODEL_LABOUR_CODE", map.get("MODEL_LABOUR_CODE"));
					roLabour.setString("PACKAGE_CODE", map.get("PACKAGE_CODE"));
					if (!StringUtils.isNullOrEmpty(map.get("STD_LABOUR_HOUR")))
						roLabour.setDouble("STD_LABOUR_HOUR", Double.parseDouble(map.get("STD_LABOUR_HOUR")));
					if (!StringUtils.isNullOrEmpty(map.get("STD_LABOUR_HOUR_SAVE")))
						roLabour.setDouble("STD_LABOUR_HOUR_SAVE", Double.parseDouble(map.get("STD_LABOUR_HOUR_SAVE")));
					if (!StringUtils.isNullOrEmpty(map.get("LABOUR_ASSIGN_LABOUR_HOUR")))
						roLabour.setDouble("LABOUR_ASSIGN_LABOUR_HOUR",
								Double.parseDouble(map.get("LABOUR_ASSIGN_LABOUR_HOUR")));
					roLabour.setString("RO_NO", dto.getRoNo());
					if (!StringUtils.isNullOrEmpty(map.get("LABOUR_AMOUNT")))
						roLabour.setDouble("LABOUR_AMOUNT", Double.parseDouble(map.get("LABOUR_AMOUNT")));
					if (!StringUtils.isNullOrEmpty(map.get("REPAIR_TYPE_CODE")))
						roLabour.setString("REPAIR_TYPE_CODE", dto.getRepairType());

					if (!StringUtils.isNullOrEmpty(map.get("POS_CODE")))
						roLabour.setString("POS_CODE", map.get("POS_CODE"));
					if (!StringUtils.isNullOrEmpty(map.get("POS_NAME")))
						roLabour.setString("POS_NAME", map.get("POS_NAME"));
					if (!StringUtils.isNullOrEmpty(map.get("APP_CODE")))
						roLabour.setString("APP_CODE", map.get("APP_CODE"));
					if (!StringUtils.isNullOrEmpty(map.get("APP_NAME")))
						roLabour.setString("APP_NAME", map.get("APP_NAME"));
					if (!StringUtils.isNullOrEmpty(map.get("IS_TRIPLE_GUARANTEE")))
						roLabour.setString("IS_TRIPLE_GUARANTEE", map.get("IS_TRIPLE_GUARANTEE"));
					if (!StringUtils.isNullOrEmpty(map.get("NO_WARRANTY_CONDITION")))
						roLabour.setString("NO_WARRANTY_CONDITION", map.get("NO_WARRANTY_CONDITION"));
					if (!StringUtils.isNullOrEmpty(map.get("WAR_LEVEL")))
						roLabour.setString("WAR_LEVEL", map.get("WAR_LEVEL"));
					if (!StringUtils.isNullOrEmpty(map.get("INFIX_CODE")))
						roLabour.setString("INFIX_CODE", map.get("INFIX_CODE"));
					if (!StringUtils.isNullOrEmpty(map.get("LABOUR_TYPE")))
						roLabour.setString("LABOUR_TYPE", map.get("LABOUR_TYPE"));

					roLabour.setString("LOCAL_LABOUR_CODE", map.get("LOCAL_LABOUR_CODE"));
					roLabour.setString("LOCAL_LABOUR_NAME", map.get("LOCAL_LABOUR_NAME"));
					roLabour.setString("WORKER_TYPE_CODE", map.get("WORKER_TYPE_CODE"));
					roLabour.setString("TECHNICIAN", map.get("TECHNICIAN"));
					roLabour.setString("TROUBLE_CAUSE", map.get("TROUBLE_CAUSE"));
					roLabour.setString("TROUBLE_DESC", map.get("TROUBLE_DESC"));

					if (!StringUtils.isNullOrEmpty(map.get("ACTIVITY_CODE")))
						roLabour.setString("ACTIVITY_CODE", map.get("ACTIVITY_CODE"));
					roLabour.setString("DEALER_CODE", dealerCode);
					if (!StringUtils.isNullOrEmpty(map.get("ASSIGN_TAG"))) {
						if (map.get("ASSIGN_TAG").equals(DictCodeConstants.DICT_IS_YES)) {
							Boolean flag = checkIsAssign(map.get("ITEM_ID"), dto.getRoNo());
							if (!flag) {
								throw new ServiceBizException("已派工项目没有对应派工信息，请联系客服或重新派工！");
							}
						}
						roLabour.setString("ASSIGN_TAG", map.get("ASSIGN_TAG"));
					}
					if (!StringUtils.isNullOrEmpty(map.get("DISCOUNT")))
						roLabour.setFloat("DISCOUNT", Float.parseFloat(map.get("DISCOUNT")));
					if (!StringUtils.isNullOrEmpty(map.get("LABOUR_PRICE")))
						roLabour.setFloat("LABOUR_PRICE", Float.parseFloat(map.get("LABOUR_PRICE")));
					if (!StringUtils.isNullOrEmpty(map.get("REASON")))
						roLabour.setInteger("REASON", Integer.parseInt(map.get("REASON")));
					if (!StringUtils.isNullOrEmpty(map.get("CLAIM_LABOUR")))
						roLabour.setFloat("CLAIM_LABOUR", Float.parseFloat(map.get("CLAIM_LABOUR")));
					if (!StringUtils.isNullOrEmpty(map.get("CLAIM_AMOUNT")))
						roLabour.setDouble("CLAIM_AMOUNT", Double.parseDouble(map.get("CLAIM_AMOUNT")));
					if (!StringUtils.isNullOrEmpty(map.get("CONSIGN_EXTERIOR")))
						roLabour.setInteger("CONSIGN_EXTERIOR", Integer.parseInt(map.get("CONSIGN_EXTERIOR")));
					if (!StringUtils.isNullOrEmpty(map.get("PRE_CHECK")))
						roLabour.setInteger("PRE_CHECK", Integer.parseInt(map.get("PRE_CHECK")));
					if (!StringUtils.isNullOrEmpty(map.get("NEEDLESS_REPAIR")))
						roLabour.setInteger("NEEDLESS_REPAIR", Integer.parseInt(map.get("NEEDLESS_REPAIR")));
					if (!StringUtils.isNullOrEmpty(map.get("INTER_RETURN")))
						roLabour.setInteger("interReturn", Integer.parseInt(map.get("interReturn")));
					if (!StringUtils.isNullOrEmpty(map.get("CARD_ID")))
						roLabour.setLong("CARD_ID", Long.parseLong(map.get("CARD_ID")));
					if (!StringUtils.isNullOrEmpty(map.get("SPRAY_AREA"))
							&& Double.parseDouble(map.get("SPRAY_AREA")) > 0) {
						roLabour.setLong("SPRAY_AREA", Double.parseDouble(map.get("SPRAY_AREA")));
					} else {
						roLabour.setLong("SPRAY_AREA", 0.00);
					}
					if (!StringUtils.isNullOrEmpty(map.get("MAIN_PAKCAGE_CODE")))
						roLabour.setString("MAINTAIN_PACKAGE_CODE", map.get("MAIN_PAKCAGE_CODE"));
					roLabour.setString("REMARK", map.get("REMARK"));
					roLabour.setString("CHARGE_PARTITION_CODE", map.get("CHARGE_PARTITION_CODE"));
					roLabour.saveIt();
				} else if (status.equals("U")) {
					TtRoLabourPO roLabour = TtRoLabourPO.findFirst(
							"ITEM_ID = ? AND　DEALER_CODE = ? AND RO_NO = ? AND D_KEY = ?", map.get("ITEM_ID"),
							dealerCode, dto.getRoNo(), CommonConstants.D_KEY);
					roLabour.setString("MANAGE_SORT_CODE", map.get("MANAGE_SORT_CODE"));
					roLabour.setString("LABOUR_CODE", map.get("LABOUR_CODE"));
					roLabour.setString("LABOUR_NAME", map.get("LABOUR_NAME"));
					if (!StringUtils.isNullOrEmpty("ACTIVITY_CODE"))
						roLabour.setString("ACTIVITY_CODE", map.get("ACTIVITY_CODE"));
					if (!StringUtils.isNullOrEmpty(map.get("STD_LABOUR_HOUR"))) {
						roLabour.setLong("STD_LABOUR_HOUR", Double.parseDouble(map.get("STD_LABOUR_HOUR")));
					} else {
						roLabour.setLong("STD_LABOUR_HOUR", 0.00);
					}
					if (!StringUtils.isNullOrEmpty(map.get("STD_LABOUR_HOUR_SAVE"))) {
						roLabour.setLong("STD_LABOUR_HOUR_SAVE", Double.parseDouble(map.get("STD_LABOUR_HOUR_SAVE")));
					} else {
						roLabour.setLong("STD_LABOUR_HOUR_SAVE", 0.00);
					}
					if (!StringUtils.isNullOrEmpty(map.get("ASSIGN_LABOUR_HOUR"))) {
						roLabour.setLong("ASSIGN_LABOUR_HOUR", Double.parseDouble(map.get("ASSIGN_LABOUR_HOUR")));
					} else {
						roLabour.setLong("ASSIGN_LABOUR_HOUR", 0.00);
					}
					roLabour.setString("RO_NO", dto.getRoNo());
					if (!StringUtils.isNullOrEmpty(map.get("LABOUR_AMOUNT"))) {
						roLabour.setLong("LABOUR_AMOUNT", Double.parseDouble(map.get("LABOUR_AMOUNT")));
					} else {
						roLabour.setLong("LABOUR_AMOUNT", 0.00);
					}
					if (!StringUtils.isNullOrEmpty(map.get("REPAIR_TYPE_CODE")))
						roLabour.setString("REPAIR_TYPE_CODE", dto.getRepairType());
					if (!StringUtils.isNullOrEmpty(map.get("POS_CODE")))
						roLabour.setString("POS_CODE", map.get("POS_CODE"));
					if (!StringUtils.isNullOrEmpty(map.get("POS_NAME")))
						roLabour.setString("POS_NAME", map.get("POS_NAME"));
					if (!StringUtils.isNullOrEmpty(map.get("APP_CODE")))
						roLabour.setString("APP_CODE", map.get("APP_CODE"));
					if (!StringUtils.isNullOrEmpty(map.get("APP_NAME")))
						roLabour.setString("APP_NAME", map.get("APP_NAME"));
					if (!StringUtils.isNullOrEmpty(map.get("IS_TRIPLE_GUARANTEE")))
						roLabour.setString("IS_TRIPLE_GUARANTEE", map.get("IS_TRIPLE_GUARANTEE"));
					// 去掉不为空的判断，否则不能更新非三包条件为空
					roLabour.setString("NO_WARRANTY_CONDITION", map.get("NO_WARRANTY_CONDITION"));
					if (!StringUtils.isNullOrEmpty(map.get("WAR_LEVEL")))
						roLabour.setInteger("WAR_LEVEL", Integer.parseInt(map.get("WAR_LEVEL")));
					if (!StringUtils.isNullOrEmpty(map.get("INFIX_CODE")))
						roLabour.setString("INFIX_CODE", map.get("INFIX_CODE"));
					if (!StringUtils.isNullOrEmpty(map.get("LABOUR_TYPE")))
						roLabour.setInteger("LABOUR_TYPE", Integer.parseInt(map.get("LABOUR_TYPE")));
					roLabour.setString("LOCAL_LABOUR_CODE", map.get("LOCAL_LABOUR_CODE"));
					roLabour.setString("ACTIVITY_CODE", map.get("ACTIVITY_CODE"));
					roLabour.setString("MODEL_LABOUR_CODE", map.get("MODEL_LABOUR_CODE"));
					roLabour.setString("PACKAGE_CODE", map.get("PACKAGE_CODE"));
					roLabour.setString("LOCAL_LABOUR_NAME", map.get("LOCAL_LABOUR_NAME"));
					roLabour.setString("WORKER_TYPE_CODE", map.get("WORKER_TYPE_CODE"));
					if (!StringUtils.isNullOrEmpty(map.get("MAIN_PAKCAGE_CODE")))
						roLabour.setString("MAIN_PAKCAGE_CODE", map.get("MAIN_PAKCAGE_CODE"));
					roLabour.setString("TROUBLE_CAUSE", map.get("TROUBLE_CAUSE"));
					roLabour.setString("TROUBLE_DESC", map.get("TROUBLE_DESC"));
					roLabour.setString("DEALER_CODE", dealerCode);
					if (!StringUtils.isNullOrEmpty(map.get("REASON")))
						roLabour.setString("REASON", map.get("REASON"));
					if (!StringUtils.isNullOrEmpty(map.get("CLAIM_LABOUR")))
						roLabour.setFloat("CLAIM_LABOUR", Float.parseFloat(map.get("CLAIM_LABOUR")));
					if (!StringUtils.isNullOrEmpty(map.get("CLAIM_AMOUNT")))
						roLabour.setDouble("CLAIM_AMOUNT", Double.parseDouble(map.get("CLAIM_AMOUNT")));
					/**
					 * 判断是否向WORKSHOP
					 */
					String flag = Utility.getDefaultValue("2201");
					if (!StringUtils.isNullOrEmpty(flag) && flag.equals(DictCodeConstants.DICT_IS_YES)) {
						roLabour.setInteger("ASSIGN_TAG", map.get("ASSIGN_TAG"));// 不向EWORKSHOP发消息
						roLabour.setString("TECHNICIAN", map.get("TECHNICIAN"));
					}
					if (!StringUtils.isNullOrEmpty(map.get("DISCOUNT")))
						roLabour.setFloat("DISCOUNT", Float.parseFloat(map.get("DISCOUNT")));
					if (!StringUtils.isNullOrEmpty(map.get("LABOUR_PRICE")))
						roLabour.setFloat("LABOUR_PRICE", Float.parseFloat(map.get("LABOUR_PRICE")));
					if (!StringUtils.isNullOrEmpty(map.get("CONSIGN_EXTERIOR")))
						roLabour.setInteger("CONSIGN_EXTERIOR", Integer.parseInt(map.get("CONSIGN_EXTERIOR")));
					if (!StringUtils.isNullOrEmpty(map.get("PRE_CHECK")))
						roLabour.setInteger("PRE_CHECK", Integer.parseInt(map.get("PRE_CHECK")));
					if (!StringUtils.isNullOrEmpty(map.get("NEEDLESS_REPAIR")))
						roLabour.setInteger("NEEDLESS_REPAIR", Integer.parseInt(map.get("NEEDLESS_REPAIR")));
					if (!StringUtils.isNullOrEmpty(map.get("INTER_RETURN")))
						roLabour.setInteger("INTER_RETURN", Integer.parseInt(map.get("INTER_RETURN")));
					if (!StringUtils.isNullOrEmpty(map.get("CARD_ID")))
						roLabour.setLong("CARD_ID", Long.parseLong(map.get("CARD_ID")));
					if (!StringUtils.isNullOrEmpty(map.get("REMARK")))
						roLabour.setString("REMARK", map.get("REMARK"));
					roLabour.setString("CHARGE_PARTITION_CODE", map.get("CHARGE_PARTITION_CODE"));
					if (!StringUtils.isNullOrEmpty(map.get("SPRAY_AREA"))) {
						roLabour.setLong("SPRAY_AREA", Double.parseDouble(map.get("SPRAY_AREA")));
					} else {
						roLabour.setLong("SPRAY_AREA", 0.00);
					}
					roLabour.saveIt();
					// 如果修改了维修项目名称，则将相关联的配件记录中的维修项目名称一起更新
					if (!StringUtils.isNullOrEmpty(map.get("LABOUR_NAME"))) {
						List<TtRoRepairPartPO> partPO = TtRoRepairPartPO.find(
								"DEALER_CODE = ? AND RO_NO =? AND ITEM_ID_LABOUR = ? AND D_KEY = ?", dealerCode,
								dto.getRoNo(), roLabour.getLong("ITEM_ID"), CommonConstants.D_KEY);
						if (partPO.size() > 0) {
							for (TtRoRepairPartPO ttRoRepairPartPO : partPO) {
								if (ttRoRepairPartPO.getString("LABOUR_NAME").equals(map.get("LABOUR_NAME"))) {
									ttRoRepairPartPO.setString("LABOUR_NAME", map.get("LABOUR_NAME"));
									ttRoRepairPartPO.saveIt();
								}
							}
						}
					}
				} else if (status.equals("D")) {
					// 添加维修材料外键后删除项目需先判断该项目是否作为维修材料的外键
					TtRoRepairPartPO.update("ITEM_ID_LABOUR = NULL", "DEALER_CODE = ? AND ITEM_ID = ? AND RO_NO = ?",
							dealerCode, map.get("ITEM_ID"), dto.getRoNo());

					TtRoLabourPO.delete("ITEM_ID = ? AND DEALER_CODE = ? AND RO_NO = ? AND D_KEY = ?",
							map.get("ITEM_ID"), dealerCode, dto.getRoNo(), CommonConstants.D_KEY);

					// 子表：工单维系项目明细，主表：维修工单
					RepairOrderPO poDmsUpdate = RepairOrderPO.findByCompositeKeys(dealerCode, dto.getRoNo());
					if (!StringUtils.isNullOrEmpty(poDmsUpdate)
							&& poDmsUpdate.getInteger("D_KEY") == CommonConstants.D_KEY) {
						poDmsUpdate.saveIt();
					}
					// 删除维修项目日志
					handleOperateLog("客户接待删除的维修项目:工单号【" + dto.getRoNo() + "】删除维修项目，维修项目代码【" + map.get("LABOUR_CODE")
							+ "】，维修项目名称为【" + map.get("LABOUR_NAME") + "】，标准工时为【" + map.get("STD_LABOUR_HOUR") + "】", "",
							Integer.parseInt(DictCodeConstants.DICT_ASCLOG_OPERATION_RECEIVE),
							FrameworkUtil.getLoginInfo().getEmployeeNo());
				}

				List<TtRoLabourPO> listLa = TtRoLabourPO.find("RO_NO = ? AND D_KEY = ? AND DAELER_CODE = ? ",
						dto.getRoNo(), CommonConstants.D_KEY, dealerCode);
				if (listLa.size() > 0) {
					for (TtRoLabourPO labourPO : listLa) {
						String isOem = DictCodeConstants.DICT_IS_NO;
						TmRepairItemPO itemPO = TmRepairItemPO.findByCompositeKeys(dealerCode,
								labourPO.getString("LABOUR_CODE"), labourPO.getString("MODEL_LABOUR_CODE"));
						if (!StringUtils.isNullOrEmpty(itemPO)) {
							isOem = DictCodeConstants.DICT_IS_YES;
						}
						if (!StringUtils.isNullOrEmpty(labourPO.getString("LABOUR_CODE"))
								&& (labourPO.getString("LABOUR_CODE").equals("QS30")
										|| labourPO.getString("LABOUR_CODE").equals("QS50"))
								&& isOem.equals(DictCodeConstants.DICT_IS_YES)) {
							isQs = true;
							break;
						}
					}
				}
			}
		}

		/**
		 * 会员维修项目流水变更 产生步骤： (1) 查询工单中的会员卡维修项目(不需要) (2)计算工单中的各会员维修项目总和
		 * (3)查询维修项目流水中的该工单的所有维修项目流水(不需要) (4)查询维修项目流水中的该工单的所有维修项目流水总和 (5)
		 * (2)-(4)得到本次工单更新的会员维修项目信息 (6) 更新会员卡维修项目的可用维修项目数量
		 * 并校验可用项目数与维修项目总数的比较(主要用于并发校验，不通过则回滚) (7)删除该工单所有项目流水记录
		 * (8)根据工单中的会员配件项目新增项目流水记录 注意：第五步的时候要考虑到1.在(2)中不存在但是在(4)中存在的配件
		 * 2.在(4)中不存在但是在(2)中存在的配件
		 */
		if (ckeckFieldNotNull(roLabours, "CARD_ID")) {
			// 会员维修项目和维修材料需要去除会员活动包含的项目和材料
			// 计算工单中的各会员项目总和
			List<TtRoLabourPO> memberLabourSum = TtRoLabourPO.find(
					"DEALER_CODE = ? AND RO_NO = ? AND CARD_ID IS NOT NULL AND CARD_ID!=0 AND (ACTIVITY_CODE IS NULL OR ACTIVITY_CODE = '' ) ",
					dealerCode, dto.getRoNo());
			// 查询项目流水中的该工单的所有项目流水总和
			List<Map> memberLabourFlowSum = queryMemberLabourFlowSum(dto.getRoNo());
			if (memberLabourSum != null && memberLabourSum.size() > 0 && memberLabourFlowSum.size() <= 0) {
				// 新增工单
				for (TtRoLabourPO labourPO : memberLabourSum) {
					// 计算总账
					List<TtMemberLabourPO> list = TtMemberLabourPO.find(
							"DEALER_CODE = ? AND CARD_ID = ? AND LABOUR_CODE = ?", dealerCode,
							labourPO.getLong("CARD_ID"), labourPO.getString("LABOUR_CODE"));
					for (TtMemberLabourPO ttMemberLabourPO : list) {
						ttMemberLabourPO.setInteger("USED_LABOUR_COUNT",
								ttMemberLabourPO.getInteger("USED_LABOUR_COUNT") + 1);
						ttMemberLabourPO.saveIt();
						// 更新完总账后 判断总账中的已用项目数量和总项目数量的大小
						if (ttMemberLabourPO.getInteger("USED_LABOUR_COUNT") > ttMemberLabourPO
								.getInteger("SERVICE_LABOUR_COUNT")) {
							throw new ServiceBizException(ttMemberLabourPO.getString("LABOUR_NAME") + "可用次数不够,请重新操作!");
						}
					}
					// 新增项目流水
					TtMemberLabourFlowPO flow = new TtMemberLabourFlowPO();
					flow.setString("DEALER_CODE", groupCode);
					flow.setLong("CARD_ID", labourPO.getLong("CARD_ID"));
					flow.setString("RO_NO", dto.getRoNo());
					flow.setString("LABOUR_CODE", labourPO.getString("LABOUR_CODE"));
					flow.setString("LABOUR_NAME", labourPO.getString("LABOUR_NAME"));
					flow.setInteger("SERVICE_LABOUR_COUNT", labourPO.getInteger("SERVICE_LABOUR_COUNT"));
					flow.setLong("THIS_USE_COUNT", 1l);
					flow.setInteger("USED_LABOUR_COUNT", labourPO.getInteger("USED_LABOUR_COUNT"));
					flow.setDouble("STD_LABOUR_HOUR", labourPO.getDouble("STD_LABOUR_HOUR"));
					flow.setDouble("ASSIGN_LABOUR_HOUR", labourPO.getDouble("ASSIGN_LABOUR_HOUR"));
					flow.setFloat("LABOUR_PRICE", labourPO.getFloat("LABOUR_PRICE"));
					flow.setDouble("LABOUR_AMOUNT", labourPO.getDouble("LABOUR_AMOUNT"));
					flow.set("OPERATE_TIME", new Timestamp(System.currentTimeMillis()));
					flow.setString("OPERATOR", FrameworkUtil.getLoginInfo().getEmployeeNo());
					// 获取会员卡维修项目的业务类型并插入到流水中
					if (!StringUtils.isNullOrEmpty(labourPO.getInteger("BUSINESS_TYPE"))) {
						flow.setInteger("BUSINESS_TYPE", labourPO.getInteger("BUSINESS_TYPE"));
					}
					flow.setString("CHARGE_PARTITION_CODE", labourPO.getString("CHARGE_PARTITION_CODE") != null
							? labourPO.getString("CHARGE_PARTITION_CODE") : "");
					flow.setString("MODEL_LABOUR_CODE", labourPO.getString("MODEL_LABOUR_CODE") != null
							? labourPO.getString("MODEL_LABOUR_CODE") : "");
					flow.setString("DISCOUNT", labourPO.getString("DISCOUNT"));
					flow.saveIt();
				}
			} else if (memberLabourSum.size() <= 0 && memberLabourFlowSum != null && memberLabourFlowSum.size() > 0) {// 751
				for (Map flow : memberLabourFlowSum) {
					// 计算总账
					logger.debug("更新前该维修项目已用次数为 " + flow.get("USED_LABOUR_COUNT"));
					List<TtMemberLabourPO> find = TtMemberLabourPO.find(
							"DEALER_CODE = ? AND CARD_ID = ? AND LABOUR_CODE = ?", dealerCode,
							flow.get("CARD_ID").toString(), flow.get("LABOUR_CODE").toString());
					for (TtMemberLabourPO ttMemberLabourPO : find) {
						ttMemberLabourPO.setLong("USED_LABOUR_COUNT",
								Long.parseLong(flow.get("USED_LABOUR_COUNT").toString()) - 1L);
						ttMemberLabourPO.saveIt();
					}
				}
				// 删除该工单的所有项目流水
				TtMemberLabourFlowPO.delete("DEALER_CODE = ? AND RO_NO = ?", dealerCode, dto.getRoNo());
				handleOperateLog("删除会员卡项目流水", "TT_MEMBER_LABOUR_FLOW,RO_NO=" + dto.getRoNo(),
						Integer.parseInt(DictCodeConstants.DICT_ASCLOG_MEMBER_MANAGE),
						FrameworkUtil.getLoginInfo().getEmployeeNo());

			} else if (memberLabourSum != null && memberLabourSum.size() > 0 && memberLabourFlowSum != null
					&& memberLabourFlowSum.size() > 0) {
				// 编辑工单、维修领料、工单作废
				for (TtRoLabourPO labourPO : memberLabourSum) {
					// 定义变量 isFound 默认为否 当roLabourCode和flowLabourCode相同时为是
					String isFound = DictCodeConstants.DICT_IS_NO;
					// 工单中的项目代码
					String roLabourCode = labourPO.getString("LABOUR_CODE");
					for (Map flow : memberLabourFlowSum) {
						// 流水中的项目代码
						String flowLabourCode = flow.get("LABOUR_CODE").toString();
						// 如果工单中的项目在流水中已存在，则计算差值
						if (roLabourCode.equals(flowLabourCode)) {
							// 更新总账(这种情况下 总账无需更新、流水无需修改)
							isFound = DictCodeConstants.DICT_IS_YES;
							break;
						}
					}
					if (isFound.equals(DictCodeConstants.DICT_IS_NO)) {
						List<TtMemberLabourPO> find = TtMemberLabourPO.find(
								"DEALER_CODE = ? AND CARD_ID = ? AND LABOUR_CODE = ?", dealerCode,
								labourPO.getString("CARD_ID"), roLabourCode);
						for (TtMemberLabourPO ttMemberLabourPO : find) {
							logger.debug("已用：" + ttMemberLabourPO.get("USED_LABOUR_COUNT"));
							ttMemberLabourPO.setLong("USED_LABOUR_COUNT", labourPO.getLong("USED_LABOUR_COUNT") + 1L);
							ttMemberLabourPO.saveIt();
							// 更新完总账后 判断总账中的已用项目次数和总项目次数的大小
							logger.debug("已用：" + ttMemberLabourPO.getLong("USED_LABOUR_COUNT") + "总数："
									+ ttMemberLabourPO.getLong("SERVICE_LABOUR_COUNT"));
							if (ttMemberLabourPO.getLong("USED_LABOUR_COUNT") > ttMemberLabourPO
									.getLong("SERVICE_LABOUR_COUNT"))
								throw new ServiceBizException("可用次数不够,请重新操作!");
							// 新增项目流水(只需新增在工单中存在 在流水中不存在的项目流水)
							TtMemberLabourFlowPO flowPO = new TtMemberLabourFlowPO();
							// 开始插入配件项目流水记录
							flowPO.setString("DEALER_CODE", groupCode);
							flowPO.setString("CARD_ID", labourPO.getString("CARD_ID"));
							flowPO.setString("RO_NO", dto.getRoNo());
							flowPO.setString("LABOUR_CODE", labourPO.getString("LABOUR_CODE"));
							flowPO.setString("LABOUR_NAME", labourPO.getString("LABOUR_NAME"));
							flowPO.setLong("SERVICE_LABOUR_COUNT", ttMemberLabourPO.getLong("SERVICE_LABOUR_COUNT"));
							flowPO.setLong("THIS_USE_COUNT", 1l);
							flowPO.setString("USED_LABOUR_COUNT", ttMemberLabourPO.getLong("USED_LABOUR_COUNT"));
							flowPO.setDouble("STD_LABOUR_HOUR", labourPO.getDouble("STD_LABOUR_HOUR"));
							flowPO.setDouble("ASSIGN_LABOUR_HOUR", labourPO.getDouble("ASSIGN_LABOUR_HOUR"));
							flowPO.setFloat("LABOUR_PRICE", labourPO.getFloat("LABOUR_PRICE"));
							flowPO.setDouble("LABOUR_AMOUNT", labourPO.getDouble("LABOUR_AMOUNT"));
							flowPO.set("OPERATE_TIME", new Timestamp(System.currentTimeMillis()));
							flowPO.setString("OPERATOR", FrameworkUtil.getLoginInfo().getEmployeeNo());
							if (ttMemberLabourPO.getInteger("BUSINESS_TYPE") != null) {
								flowPO.setInteger("BUSINESS_TYPE", ttMemberLabourPO.getInteger("BUSINESS_TYPE"));
							}
							// flow.setChargePartitionCode(labourPO.getChargePartitionCode()!=null?labourPO.getChargePartitionCode():"");
							// flow.setModelLabourCode(labourPO.getModelLabourCode()!=null?labourPO.getModelLabourCode():"");
							// flow.setCreateBy(userId);
							// flow.setCreateDate(Utility.getCurrentDateTime());
							// flow.setDiscount(labourPO.getDiscount());
							flowPO.saveIt();
						}
					}
				}
				// 流水-工单 对比
				for (Map flow : memberLabourFlowSum) {
					String isFound2 = DictCodeConstants.DICT_IS_YES;
					// 流水中的项目代码
					String flowLabourCode = flow.get("LABOUR_CODE").toString();
					for (TtRoLabourPO labourPO : memberLabourSum) {
						// 工单中的项目代码
						String roLabourCode = labourPO.getString("LABOUR_CODE");
						if (flowLabourCode.equals(roLabourCode)) {
							isFound2 = DictCodeConstants.DICT_IS_NO;
							break;

						}
					}
					if (isFound2.equals(DictCodeConstants.DICT_IS_YES)) {
						List<TtMemberLabourPO> find = TtMemberLabourPO.find(
								"DEALER_CODE = ? AND CARD_ID = ? AND LABOUR_CODE = ?", dealerCode,
								flow.get("CARD_ID").toString(), flow.get("LABOUR_CODE").toString());
						for (TtMemberLabourPO ttMemberLabourPO : find) {
							ttMemberLabourPO.setLong("USED_LABOUR_COUNT",
									Long.parseLong(flow.get("USED_LABOUR_COUNT").toString()) - 1L);
							ttMemberLabourPO.saveIt();
						}
						// 删除项目流水
						handleOperateLog("删除会员卡项目流水", "TT_MEMBER_LABOUR_FLOW,ITEM_ID=" + flow.get("ITEM_ID"),
								Integer.parseInt(DictCodeConstants.DICT_ASCLOG_MEMBER_MANAGE),
								FrameworkUtil.getLoginInfo().getEmployeeNo());
						TtMemberLabourFlowPO.delete("DEALER_CODE = ? AND ITEM_ID = ?", groupCode,
								flow.get("ITEM_ID").toString());

					}
				}
			}
		}
		String defaultValue = Utility.getDefaultValue("2091");
		if (DictCodeConstants.DICT_IS_YES.equals(defaultValue)) {
			RepairOrderPO orderPO = RepairOrderPO.findByCompositeKeys(dealerCode, dto.getRoNo());
			if (!StringUtils.isNullOrEmpty(orderPO) && orderPO.getInteger("D_KEY") == CommonConstants.D_KEY) {
				if (isQs) {
					orderPO.setLong("IS_QS", DictCodeConstants.IS_YES);
				} else {
					orderPO.setLong("IS_QS", DictCodeConstants.IS_NOT);
				}
				orderPO.saveIt();
			}
		}
	}

	/**
	 * 查询项目流水明细总和
	 * 
	 * @param roNo
	 * @return
	 */
	public List<Map> queryMemberLabourFlowSum(String roNo) {
		StringBuffer sb = new StringBuffer();
		sb.append(" select CARD_ID,DEALER_CODE,ITEM_ID,RO_NO,LABOUR_CODE,LABOUR_NAME,SERVICE_LABOUR_COUNT,")
				.append(" CHARGE_PARTITION_CODE,MODEL_LABOUR_CODE,STD_LABOUR_HOUR,ASSIGN_LABOUR_HOUR,LABOUR_AMOUNT,")
				.append(" LABOUR_PRICE,OPERATOR,OPERATE_TIME,DISCOUNT,CREATED_BY,CREATED_AT,D_KEY,UPDATED_BY,UPDATED_AT,")
				.append(" VER,sum(THIS_USE_COUNT) THIS_USE_COUNT , sum(USED_LABOUR_COUNT) USED_LABOUR_COUNT,BUSINESS_TYPE ")
				.append(" from " + CommonConstants.VM_MEMBER_LABOUR_FLOW + " where dealer_code='")
				.append(FrameworkUtil.getLoginInfo().getDealerCode()).append("'  ");
		if (!StringUtils.isNullOrEmpty(roNo)) {
			sb.append(" and RO_NO='").append(roNo).append("' ");
		}
		sb.append(" group by CARD_ID,DEALER_CODE,ITEM_ID,RO_NO,LABOUR_CODE,LABOUR_NAME,SERVICE_LABOUR_COUNT,")
				.append(" CHARGE_PARTITION_CODE,MODEL_LABOUR_CODE,STD_LABOUR_HOUR,ASSIGN_LABOUR_HOUR,LABOUR_AMOUNT,")
				.append(" LABOUR_PRICE,OPERATOR,OPERATE_TIME,DISCOUNT,CREATED_BY,CREATED_AT,D_KEY,UPDATED_BY,UPDATED_AT,")
				.append(" VER,BUSINESS_TYPE ");
		return DAOUtil.findAll(sb.toString(), null);
	}

	/**
	 * 检测是否派工
	 * 
	 * @param itemId
	 * @param roNo
	 * @return
	 */
	public Boolean checkIsAssign(String itemId, String roNo) {
		List queryParam = new ArrayList();
		queryParam.add(itemId);
		queryParam.add(roNo);
		List<Map> list = DAOUtil.findAll("SELECT * FROM TT_RO_ASSIGN WHERE ITEM_ID = ? AND RO_NO = ? ", queryParam);
		if (list.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 新增.修改.删除工单附加项目
	 * 
	 * @param dto
	 */
	public void maintainRoAddItem(RepairOrderDetailsDTO dto) {
		List<Map<String, String>> subjoinItem = dto.getDms_subjoinItem();// 附加项目
		for (Map<String, String> map : subjoinItem) {
			String status = map.get("rowKey");
			if (!StringUtils.isNullOrEmpty(status)) {
				if (status.equals("A")) {// 表示新增
					RoAddItemPO addItem = new RoAddItemPO();
					addItem.setString("ADD_ITEM_CODE", map.get("ADD_ITEM_CODE"));
					addItem.setString("REMARK", map.get("REMARK"));
					addItem.setString("ACTIVITY_CODE", map.get("ACTIVITY_CODE"));
					addItem.setString("ADD_ITEM_NAME", map.get("ADD_ITEM_NAME"));
					addItem.setString("RO_NO", dto.getRoNo());
					if (!StringUtils.isNullOrEmpty(map.get("ADD_ITEM_AMOUNT")))
						addItem.setDouble("ADD_ITEM_AMOUNT", map.get("ADD_ITEM_AMOUNT"));
					addItem.setString("MANAGE_SORT_CODE", map.get("MANAGE_SORT_CODE"));
					addItem.setString("CHARGE_PARTITION_CODE", map.get("CHARGE_PARTITION_CODE"));
					if (!StringUtils.isNullOrEmpty(map.get("DISCOUNT")))
						addItem.setFloat("DISCOUNT", map.get("DISCOUNT"));
					addItem.setString("DEALER_CODE", FrameworkUtil.getLoginInfo().getDealerCode());
					addItem.saveIt();
				} else if (status.equals("U")) {// 修改
					List<RoAddItemPO> list = RoAddItemPO.find(
							"ITEM_ID = ? AND DEALER_CODE = ? AND RO_NO = ? AND D_KEY = ?", map.get("ITEM_ID"),
							FrameworkUtil.getLoginInfo().getDealerCode(), map.get("RO_NO"), CommonConstants.D_KEY);
					if (list.size() > 0) {
						for (RoAddItemPO roAddItemPO : list) {
							roAddItemPO.setString("ADD_ITEM_CODE", map.get("ADD_ITEM_CODE"));
							roAddItemPO.setString("REMARK", map.get("REMARK"));
							roAddItemPO.setString("ACTIVITY_CODE", map.get("ACTIVITY_CODE"));
							roAddItemPO.setString("ADD_ITEM_NAME", map.get("ADD_ITEM_NAME"));
							roAddItemPO.setString("RO_NO", dto.getRoNo());
							if (!StringUtils.isNullOrEmpty(map.get("ADD_ITEM_AMOUNT")))
								roAddItemPO.setDouble("ADD_ITEM_AMOUNT", map.get("ADD_ITEM_AMOUNT"));
							roAddItemPO.setString("MANAGE_SORT_CODE", map.get("MANAGE_SORT_CODE"));
							roAddItemPO.setString("CHARGE_PARTITION_CODE", map.get("CHARGE_PARTITION_CODE"));
							if (!StringUtils.isNullOrEmpty(map.get("DISCOUNT")))
								roAddItemPO.setFloat("DISCOUNT", map.get("DISCOUNT"));
							roAddItemPO.saveIt();
						}
					}
				} else if (status.equals("D")) {// 删除
					List<RoAddItemPO> list = RoAddItemPO.find(
							"ITEM_ID = ? AND DEALER_CODE = ? AND RO_NO = ? AND D_KEY = ?", map.get("ITEM_ID"),
							FrameworkUtil.getLoginInfo().getDealerCode(), map.get("RO_NO"), CommonConstants.D_KEY);
					if (list.size() > 0) {
						for (RoAddItemPO roAddItemPO : list) {
							roAddItemPO.delete();
						}
					}
					// 子表：工单附加项目明细，主表：维修工单
					RepairOrderPO orderPO = RepairOrderPO
							.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(), dto.getRoNo());
					if (!StringUtils.isNullOrEmpty(orderPO) && orderPO.getInteger("D_KEY") == CommonConstants.D_KEY) {
						orderPO.saveIt();
					}
				}
			}
		}
	}

	/**
	 * 新建工单导入预约单解预留处理
	 */
	public int minusRoObligated(RepairOrderDetailsDTO dto) {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		String bookingOrderNo = dto.getBookingOrderNo();// 预约单号
		TtBookingOrderPartPO bookingOrderPartPO = TtBookingOrderPartPO.findFirst(
				"D_KEY = ? AND BOOKING_ORDER_NO = ? AND DEALER_CODE = ?", CommonConstants.D_KEY, bookingOrderNo,
				dealerCode);
		if (!StringUtils.isNullOrEmpty(bookingOrderPartPO)) {
			bookingOrderPartPO.setLong("IS_OBLIGATED", DictCodeConstants.IS_NOT);
			bookingOrderPartPO.saveIt();
			minusObligate(bookingOrderPartPO.getString("PART_NO"), bookingOrderPartPO.getString("BOOKING_COUNT"),
					bookingOrderPartPO.getString("IS_OBLIGATE"), bookingOrderPartPO.getString("STORAGE_CODE"), "D");
			return 1;
		} else {
			return 0;
		}
	}

	/**
	 * 新增工单
	 */
	public void addRepairOrder(RepairOrderDetailsDTO dto) throws ServiceBizException {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		// 工单绑定预约单之前校验预约单是否仍然为有效状态（未进厂）
		if (!StringUtils.isNullOrEmpty(dto.getBookingOrderNo())) {
			TtBookingOrderPO tbop = TtBookingOrderPO.findByCompositeKeys(dealerCode, dto.getBookingOrderNo());
			if (!StringUtils.isNullOrEmpty(tbop) && tbop.getInteger("D_KEY") == CommonConstants.D_KEY) {
				if (!StringUtils.isNullOrEmpty(tbop.getString("BOOKING_ORDER_STATUS"))
						&& !DictCodeConstants.DICT_BOS_NOT_ENTER.equals(tbop.getString("BOOKING_ORDER_STATUS"))) {
					throw new ServiceBizException("此预约单状态已改变,不能绑定工单!");
				}
			} else {
				throw new ServiceBizException("此预约单不存在,请重新确认后操作!");
			}
		}
		RepairOrderPO repairOrder = new RepairOrderPO();
		repairOrder.setString("BOOKING_ORDER_NO", dto.getBookingOrderNo());
		repairOrder.setString("RO_TYPE", dto.getRoType());
		repairOrder.setString("REPAIR_TYPE_CODE", dto.getRepairType());
		repairOrder.setString("OTHER_REPAIR_TYPE", dto.getOtherRepairType());
		repairOrder.setString("VEHICLE_TOP_DESC", dto.getCarTopType());
		repairOrder.setString("SEQUENCE_NO", dto.getSequenceNo());
		repairOrder.setString("PRIMARY_RO_NO", dto.getPriRoNo());
		repairOrder.setString("INSURATION_NO", dto.getInsure());
		repairOrder.setString("INSURATION_CODE", dto.getInsurationCode());
		repairOrder.setString("IS_CUSTOMER_IN_ASC", dto.getCheckIsFactory());
		repairOrder.setString("IS_SEASON_CHECK", dto.getCheckIsQuality());
		repairOrder.setString("OIL_REMAIN", dto.getOilRemain());
		if (!StringUtils.isNullOrEmpty(dto.getCheckIsWash())) {
			repairOrder.setLong("IS_WASH", DictCodeConstants.IS_YES);
		} else {
			repairOrder.setLong("IS_WASH", DictCodeConstants.IS_NOT);
		}
		if (!StringUtils.isNullOrEmpty(dto.getCheckIsTrace())) {
			repairOrder.setLong("IS_TRACE", DictCodeConstants.IS_YES);
		} else {
			repairOrder.setLong("IS_TRACE", DictCodeConstants.IS_NOT);
		}
		if (!StringUtils.isNullOrEmpty(dto.getCheckIsGiveUp())) {
			repairOrder.setLong("IS_ABANDON_ACTIVITY", DictCodeConstants.IS_YES);
		} else {
			repairOrder.setLong("IS_ABANDON_ACTIVITY", DictCodeConstants.IS_NOT);
		}
		repairOrder.setString("TRACE_TIME", dto.getTraceTime());
		repairOrder.setString("NO_TRACE_REASON", dto.getNoTraceReason());
		if (!StringUtils.isNullOrEmpty(dto.getCheckIsRoad())) {
			repairOrder.setLong("NEED_ROAD_TEST", DictCodeConstants.IS_YES);
		} else {
			repairOrder.setLong("NEED_ROAD_TEST", DictCodeConstants.IS_NOT);
		}
		repairOrder.setString("RECOMMEND_EMP_NAME", dto.getReCommendEmp());
		repairOrder.setString("RECOMMEND_CUSTOMER_NAME", dto.getReCommendCustomer());
		repairOrder.setString("SERVICE_ADVISOR", dto.getServiceAdvisor());
		repairOrder.set("RO_CREATE_DATE", dto.getCreateDate());
		if (!StringUtils.isNullOrEmpty(dto.getEndTimeSupposed())) {
			repairOrder.set("END_TIME_SUPPOSED", dto.getEndTimeSupposed());
		}
		repairOrder.setString("CHIEF_TECHNICIAN", dto.getTechnician());
		repairOrder.setString("OWNER_NAME", dto.getOwnerName());
		repairOrder.setString("OWNER_NO", dto.getOwnerNo());
		repairOrder.setString("OWNER_PROPERTY", dto.getOwnerProperty());
		repairOrder.setString("LICENSE", dto.getLicense());
		repairOrder.setString("VIN", dto.getVin());
		repairOrder.setString("ENGINE_NO", dto.getEngineNo());
		repairOrder.setString("BRAND", dto.getBrand());
		repairOrder.setString("SERIES", dto.getSeries());
		repairOrder.setString("CONFIG_CODE", dto.getConfig());
		repairOrder.setString("MODEL", dto.getModel());
		repairOrder.setString("IN_MILEAGE", dto.getInMileage());
		repairOrder.setString("OUT_MILEAGE", dto.getOutMileage());
		if (!StringUtils.isNullOrEmpty(dto.getIsChangeOdograph())) {
			repairOrder.setLong("IS_CHANGE_ODOGRAPH", DictCodeConstants.IS_YES);
		} else {
			repairOrder.setLong("IS_CHANGE_ODOGRAPH", DictCodeConstants.IS_NOT);
		}
		repairOrder.setDouble("CHANGE_MILEAGE", dto.getChangeMileage());
		// repairOrder.setDouble("TOTAL_MILEAGE",);
		repairOrder.setDouble("TOTAL_CHANGE_MILEAGE", dto.getTotalChangeMileage());
		repairOrder.setString("DELIVERER", dto.getDeliverer());
		repairOrder.setString("DELIVERER_GENDER", dto.getDelivererGender());// getIntValue
		repairOrder.setString("DELIVERER_PHONE", dto.getDelivererPhone());
		repairOrder.setString("DELIVERER_MOBILE", dto.getDelivererMobile());
		repairOrder.setDouble("LABOUR_PRICE", Double.parseDouble(dto.getLabourPrice()));
		repairOrder.setDouble("LABOUR_AMOUNT", Double.parseDouble(dto.getLabourAmount()));
		repairOrder.setDouble("REPAIR_PART_AMOUNT", Double.parseDouble(dto.getRepairPartAmount()));
		repairOrder.setDouble("SALES_PART_AMOUNT", Double.parseDouble(dto.getSalePartAmount()));
		repairOrder.setDouble("ADD_ITEM_AMOUNT", Double.parseDouble(dto.getAddItemAmount()));
		repairOrder.setDouble("OVER_ITEM_AMOUNT", Double.parseDouble(dto.getOverItemAmount()));
		repairOrder.setDouble("REPAIR_AMOUNT", Double.parseDouble(dto.getTotalAmount()));
		repairOrder.setDouble("ESTIMATE_AMOUNT", Double.parseDouble(dto.getEstimateAmount()));
		repairOrder.setString("REMARK", dto.getRemark());
		repairOrder.setString("REMARK1", dto.getRemark1());
		repairOrder.setString("Remark2", dto.getRemark2());
		repairOrder.setString("TRACE_TAG", dto.getTraceTag());
		if (!StringUtils.isNullOrEmpty(dto.getCheckIsActivity())) {
			repairOrder.setLong("IS_ACTIVITY", DictCodeConstants.IS_YES);
		} else {
			repairOrder.setLong("IS_ACTIVITY", DictCodeConstants.IS_NOT);
		}
		repairOrder.setString("LABOUR_POSITION_CODE", dto.getLabourPositionCode());
		repairOrder.setString("CUSTOMER_DESC", dto.getCustomerDesc());
		repairOrder.setString("IN_REASON", dto.getInReason());
		// repairOrder.setInteger("IS_PURCHASE_MAINTENANCE",);
		// repairOrder.setInteger("IS_TRIPLE_GUARANTEE",Utility.getInt(actionContext.getStringValue("TT_REPAIR_ORDER.IS_TRIPLE_GUARANTEE")));
		// repairOrder.setInteger("IS_TRIPLE_GUARANTEE_BEF",Utility.getInt(actionContext.getStringValue("TT_REPAIR_ORDER.IS_TRIPLE_GUARANTEE")));
		// repairOrder.setInteger("SERIOUS_SAFETY_STATUS",Utility.getInt(actionContext.getStringValue("TT_REPAIR_ORDER.SERIOUS_SAFETY_STATUS")));
		// 只要维修细项中包含索赔项，就在工单中的是否做过索赔设置成是 236
		List<Map<String, String>> listLabour = dto.getDms_table();// 维修项目
		List<Map<String, String>> listPart = dto.getDms_part();// 维修配件
		if (listLabour.size() > 0 && listPart.size() > 0) {
			if (checkChargePartitionCode(listLabour) || checkChargePartitionCode(listPart))
				repairOrder.setLong("IS_TRIPLE_GUARANTEE_BEF", DictCodeConstants.IS_YES);
		}
		if (Integer.parseInt(dto.getNotIntegral()) == 0) {
			repairOrder.setLong("NOT_INTEGRAL", DictCodeConstants.IS_NOT);
		} else {
			repairOrder.setLong("NOT_INTEGRAL", Integer.parseInt(dto.getNotIntegral()));
		}
		if (!StringUtils.isNullOrEmpty(dto.getEstimateBeginTime())) {
			repairOrder.set("ESTIMATE_BEGIN_TIME", dto.getEstimateBeginTime());
		}
		repairOrder.setString("DEALER_CODE", dealerCode);
		repairOrder.setString("SERVICE_ADVISOR_ASS", FrameworkUtil.getLoginInfo().getEmployeeNo());
		repairOrder.setLong("COMPLETE_TAG", DictCodeConstants.IS_NOT);
		repairOrder.setString("RO_STATUS", DictCodeConstants.DICT_RO_STATUS_TYPE_ON_REPAIR);
		repairOrder.setString("RO_NO", dto.getRoNo());
		repairOrder.setString("IS_TAKE_PART_OLD", dto.getCheckIsTake());
		repairOrder.setString("IS_LARGESS_MAINTAIN", dto.getIsLargessMaintain());
		repairOrder.setString("RO_TROUBLE_DESC", dto.getRoTroubleDesc());
		// 工单的开单时间和创建时间一定要在同一天内（自然天），不是的话，不能保存
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String createDate = sdf.format(dto.getCreateDate());
		String nowDate = sdf.format(new Date());
		if (!nowDate.equals(createDate) && !StringUtils.isNullOrEmpty(dto.getRoNo())) {
			throw new ServiceBizException("工单的开单时间和创建时间不在同一天内!");
		}
		if (!StringUtils.isNullOrEmpty(dto.getTroubleOccurDate())) {
			repairOrder.set("TROUBLE_OCCUR_DATE", dto.getTroubleOccurDate());
		}
		repairOrder.setString("SCHEME_STATUS", dto.getSchemeStatus());
		// 增加进厂时微信是否绑定
		// 根据vin号 找到车辆信息中的车辆微信是否绑定
		VehiclePO tv = VehiclePO.findByCompositeKeys(dto.getVin(), dealerCode);
		if (!StringUtils.isNullOrEmpty(tv)) {
			repairOrder.set("IN_BINDING_STATUS", tv.getInteger("IS_BINDING"));
			repairOrder.set("OUT_BINDING_STATUS", tv.getInteger("IS_BINDING"));
		}
		repairOrder.saveIt();

		dto.setUpdateStatus("U");

		updateVehiceIsReturn(dto);
		updateRepairIsReturn(dto);
		updateVehiceLossIsReturn(dto);
		updateNewVehiceIsReturn(dto);
	}

	/**
	 * 如果该车在提醒后X天内Y小时外回厂做工单则认为该车为回厂车
	 * 
	 * @param dto
	 */
	public void updateVehiceIsReturn(RepairOrderDetailsDTO dto) {
		if (StringUtils.isNullOrEmpty(dto.getRoNo()) && StringUtils.isNullOrEmpty(dto.getVin()))
			return;
		// 定期保养提醒X天内，回访为提醒回访
		String remainDay = Utility.getDefaultValue("1084");
		// 预约与当前时间的最小时间间隔
		String bookingSpaceHour = Utility.getDefaultValue("1053");
		// 预约与当前时间的最小时间间隔_分钟
		String bookingSpaceMinute = Utility.getDefaultValue("1056");

		StringBuffer sb = new StringBuffer();
		sb.append("UPDATE TT_TERMLY_MAINTAIN_REMIND SET IS_SUCCESS_REMIND=").append(DictCodeConstants.IS_YES)
				.append(", RO_NO='").append(dto.getRoNo()).append("'  WHERE D_KEY = ").append(CommonConstants.D_KEY)
				.append(" AND DEALER_CODE = '").append(FrameworkUtil.getLoginInfo().getDealerCode())
				.append("' AND VIN ='").append(dto.getVin()).append("'   AND LAST_TAG = ")
				.append(DictCodeConstants.IS_YES).append("  AND  REMIND_STATUS = ")
				.append(DictCodeConstants.DICT_VEHICLE_REMINDER_STATUS_SUCCESS);
		// 小于规定的小时数
		sb.append(" AND timestamp(REMIND_DATE) < ")
				.append(dateAdd(dateAdd("now()", "hour", -Integer.parseInt(bookingSpaceHour)), "minute",
						-Integer.parseInt(bookingSpaceMinute)));
		// 大于规定的天数之内
		sb.append(" AND ").append(dateAdd("REMIND_DATE", "day", Integer.parseInt(remainDay))).append(">now()");

		Base.exec(sb.toString());
	}

	/**
	 * 如果该车在提醒后X天内Y小时外回厂做工单则认为该车为回厂车
	 * 
	 * @param dto
	 */
	public void updateRepairIsReturn(RepairOrderDetailsDTO dto) {
		if (StringUtils.isNullOrEmpty(dto.getRoNo()) && StringUtils.isNullOrEmpty(dto.getVin()))
			return;
		// 保修到期提醒X天内，回访为提醒回访
		String remainDay = Utility.getDefaultValue("1225");
		// 预约与当前时间的最小时间间隔
		String bookingSpaceHour = Utility.getDefaultValue("1053");
		// 预约与当前时间的最小时间间隔_分钟
		String bookingSpaceMinute = Utility.getDefaultValue("1056");
		StringBuffer sb = new StringBuffer();
		sb.append("UPDATE TT_REPAIR_EXPIRE_REMIND SET IS_RETURN_FACTORY=").append(DictCodeConstants.IS_YES)
				.append(", RO_NO='").append(dto.getRoNo()).append("'  WHERE D_KEY = ").append(CommonConstants.D_KEY)
				.append(" AND DEALER_CODE = '").append(FrameworkUtil.getLoginInfo().getDealerCode())
				.append("' AND VIN ='").append(dto.getVin()).append("' ");
		// 小于规定的小时数
		sb.append(" AND timestamp(REMIND_DATE) < ")
				.append(dateAdd(dateAdd("now()", "hour", -Integer.parseInt(bookingSpaceHour)), "minute",
						-Integer.parseInt(bookingSpaceMinute)));
		// 大于规定的天数之内
		sb.append(" AND ").append(dateAdd("REMIND_DATE", "day", Integer.parseInt(remainDay))).append(">now()");

		Base.exec(sb.toString());
	}

	/**
	 * 如果该车在提醒后X天内Y小时外回厂做工单则认为该车为回厂车(针对客户流失报警)
	 * 
	 * @param dto
	 */
	public void updateVehiceLossIsReturn(RepairOrderDetailsDTO dto) {
		if (StringUtils.isNullOrEmpty(dto.getRoNo()) && StringUtils.isNullOrEmpty(dto.getVin()))
			return;
		// 客户流失报警提醒X天内，回访为提醒回访
		String remainDay = Utility.getDefaultValue("1094");
		// 预约与当前时间的最小时间间隔
		String bookingSpaceHour = Utility.getDefaultValue("1053");
		// 预约与当前时间的最小时间间隔_分钟
		String bookingSpaceMinute = Utility.getDefaultValue("1056");
		StringBuffer sb = new StringBuffer();
		sb.append("UPDATE TT_VEHICLE_LOSS_REMIND SET IS_RETURN_FACTORY =").append(DictCodeConstants.IS_YES)
				.append(", RO_NO='").append(dto.getRoNo()).append("'  WHERE D_KEY = ").append(CommonConstants.D_KEY)
				.append(" AND DEALER_CODE = '").append(FrameworkUtil.getLoginInfo().getDealerCode())
				.append("' AND VIN ='").append(dto.getVin()).append("'  AND LAST_TAG = ")
				.append(DictCodeConstants.IS_YES).append(" AND  REMIND_STATUS = ")
				.append(DictCodeConstants.DICT_VEHICLE_REMINDER_STATUS_SUCCESS);
		// 小于规定的小时数
		sb.append(" AND timestamp(REMIND_DATE) < ")
				.append(dateAdd(dateAdd("now()", "hour", -Integer.parseInt(bookingSpaceHour)), "minute",
						-Integer.parseInt(bookingSpaceMinute)));
		// 大于规定的天数之内
		sb.append(" AND ").append(dateAdd("REMIND_DATE", "day", Integer.parseInt(remainDay))).append(">now()");

		Base.exec(sb.toString());
	}

	/**
	 * 如果该车在提醒后X天内Y小时外回厂做工单则认为该车为回厂车(针对新车提醒)
	 * 
	 * @param dto
	 */
	public void updateNewVehiceIsReturn(RepairOrderDetailsDTO dto) {
		if (StringUtils.isNullOrEmpty(dto.getRoNo()) && StringUtils.isNullOrEmpty(dto.getVin()))
			return;
		// 新车提醒X天内，回访为提醒回访
		String remainDay = Utility.getDefaultValue("1095");
		String bookingSpaceHour = Utility.getDefaultValue("1053");
		String bookingSpaceMinute = Utility.getDefaultValue("1056");
		StringBuffer sb = new StringBuffer();
		sb.append("UPDATE TT_NEWVEHICLE_REMIND SET IS_RETURN_FACTORY =").append(DictCodeConstants.IS_YES)
				.append(" , RO_NO='").append(dto.getRoNo()).append("'  WHERE D_KEY = ").append(CommonConstants.D_KEY)
				.append(" AND DEALER_CODE = '").append(FrameworkUtil.getLoginInfo().getDealerCode())
				.append("' AND VIN ='").append(dto.getVin()).append("'  AND LAST_TAG = ")
				.append(DictCodeConstants.IS_YES).append(" AND  REMIND_STATUS = ")
				.append(DictCodeConstants.DICT_VEHICLE_REMINDER_STATUS_SUCCESS);
		// 小于规定的小时数
		sb.append(" AND timestamp(REMIND_DATE) < ")
				.append(dateAdd(dateAdd("now()", "hour", -Integer.parseInt(bookingSpaceHour)), "minute",
						-Integer.parseInt(bookingSpaceMinute)));
		// 大于规定的天数之内
		sb.append(" AND ").append(dateAdd("REMIND_DATE", "day", Integer.parseInt(remainDay))).append(">now()");

		Base.exec(sb.toString());
	}

	/**
	 * 数据库字段对时间字段的修改
	 * 
	 * @param field
	 * @param type
	 * @param num
	 * @return
	 */
	public String dateAdd(String field, String type, Integer num) {
		return "date_add(" + field + ",interval " + num + " " + type;
	}

	/**
	 * 判断收费区分字段是否含有S
	 */
	public Boolean checkChargePartitionCode(List<Map<String, String>> list) {
		Boolean flag = false;
		if (list.size() > 0) {
			for (Map<String, String> map : list) {
				String code = map.get("CHARGE_PARTITION_CODE");// 收费区分
				if ("S".equalsIgnoreCase(code)) {
					flag = true;
				}
			}
		}
		return flag;
	}

	/**
	 * 检验集合的字段中是否有值
	 * 
	 * @param list
	 * @param field
	 * @return
	 */
	public Boolean ckeckFieldNotNull(List<Map<String, String>> list, String field) {
		StringBuffer sb = new StringBuffer("");
		for (Map<String, String> map : list) {
			if (!StringUtils.isNullOrEmpty(map.get(field))) {
				sb.append(map.get(field)).append(" ");
			}
		}
		if (!"".equals(sb.toString())) {
			return true;
		} else {
			return false;
		}
	}

	public Boolean ckeckFieldNotNull2(List<Map> list, String field) {
		StringBuffer sb = new StringBuffer("");
		for (Map map : list) {
			if (!StringUtils.isNullOrEmpty(map.get(field))) {
				sb.append(map.get(field)).append(" ");
			}
		}
		if (!"".equals(sb.toString())) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 解预留操作
	 * 
	 * @param partNo
	 * @param bookingCount
	 * @param isObligate
	 * @param storageCode
	 * @param status
	 * @throws ServiceBizException
	 */
	public void minusObligate(String partNo, String bookingCount, String isObligate, String storageCode, String status)
			throws ServiceBizException {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		TmPartStockPO partStockPO = null;
		String lockCount = "";
		if (!StringUtils.isNullOrEmpty(partNo)) {
			if (!StringUtils.isNullOrEmpty(status) && status.equals("A")) {
				if (Integer.parseInt(isObligate) == DictCodeConstants.STATUS_IS_YES) {
					partStockPO = TmPartStockPO.findByCompositeKeys(dealerCode, partNo, storageCode);
					if (!StringUtils.isNullOrEmpty(partStockPO)
							&& partStockPO.getInteger("D_KEY") == CommonConstants.D_KEY) {
						lockCount = partStockPO.getString("LOCKED_QUANTITY");
					}
					partStockPO.setFloat("LOCKED_QUANTITY",
							new Double(Utility.add(lockCount, bookingCount)).floatValue());
					partStockPO.saveIt();
				}
			} else if (!StringUtils.isNullOrEmpty(status) && status.equals("D")) {
				if (Integer.parseInt(isObligate) == DictCodeConstants.STATUS_IS_YES) {
					partStockPO = TmPartStockPO.findByCompositeKeys(dealerCode, partNo, storageCode);
					if (!StringUtils.isNullOrEmpty(partStockPO)
							&& partStockPO.getInteger("D_KEY") == CommonConstants.D_KEY) {
						lockCount = partStockPO.getString("LOCKED_QUANTITY");
					}
					partStockPO.setFloat("LOCKED_QUANTITY",
							new Double(Utility.add(lockCount, bookingCount)).floatValue());
					if (StringUtils.isNullOrEmpty(partNo)) {
						throw new ServiceBizException("丢失主键值");
					}
					partStockPO.saveIt();
				}
			}
		}
	}

	/**
	 * 操作时工单更新车主车辆信息
	 */
	public int updateOwnerAndVehicle(RepairOrderDetailsDTO dto) throws ServiceBizException {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		String groupCodeOwner = Utility.getGroupEntity(dealerCode, "TM_OWNER");
		String groupCodeVehicle = Utility.getGroupEntity(dealerCode, "TM_VEHICLE");
		// 下次保养里程 和保养日期 保存前增加逻辑判断,
		// 如果TM_VEHICLE 表中下此保养日期 大于当前计算的 下次保养日期 就不更新TM_VEHICLE 表中 这两个字段
		List<Map> listVehicle = Base.findAll("SELECT * FROM TM_VEHICLE WHERE VIN = ? AND DEALER_CODE = ? ",
				dto.getVin(), groupCodeVehicle);
		listVehicle = Utility.getVehicleSubclassList(dealerCode, listVehicle);
		// 更新标志
		boolean updateMaintainTag = true;
		// 获取前台下次保养日期
		if (!StringUtils.isNullOrEmpty(dto.getNextMaintainDate())) {
			if (listVehicle.size() > 0) {
				Map tmVehicleConPO1 = listVehicle.get(0);
				try {
					Date vehicleMaintainDate = Utility
							.parseString2Date(tmVehicleConPO1.get("NEXT_MAINTAIN_DATE").toString(), null);
					// 逻辑调整 如果RO_CREATE_DATE > DB.RO_CREATE_DATE(数据库中) 则 更新后台 否则
					// 如果前台计算的下次保养日期比后台大则更新
					Date roCreateDateVehicle = null;
					if (!StringUtils.isNullOrEmpty(tmVehicleConPO1.get("RO_CREATE_DATE"))) {
						roCreateDateVehicle = Utility.parseString2Date(tmVehicleConPO1.get("RO_CREATE_DATE").toString(),
								null);
					}
					if (!tmVehicleConPO1.get("LICENSE").toString().equals(dto.getLicense())) {
						handleOperateLog(
								"VIN为：" + dto.getVin() + " 的车辆车牌号由：" + tmVehicleConPO1.get("LICENSE").toString()
										+ " 修改为:" + dto.getLicense(),
								"", Integer.parseInt(DictCodeConstants.DICT_ASCLOG_OPERATION_RECEIVE),
								FrameworkUtil.getLoginInfo().getEmployeeNo());
					}
					logger.debug("VIN:" + dto.getVin());
					logger.debug("下次保养日期 :" + vehicleMaintainDate);
					logger.debug("界面下次保养日期 :" + dto.getNextMaintainDate());
					if (StringUtils.isNullOrEmpty(dto.getRoNo())) {
						logger.debug(" 新建工单 : 更新后台数据 :");
						updateMaintainTag = true;
					} else {
						logger.debug("编辑工单 :" + dto.getRoNo());
						// 判断RO_CREATE_DATE 是否存在
						if (!StringUtils.isNullOrEmpty(roCreateDateVehicle)) {
							logger.debug("数据库中存在 RO_CREATE_DATE :" + roCreateDateVehicle);
							Date roCreateDateDate = dto.getCreateDate();
							if (roCreateDateVehicle.getTime() <= roCreateDateDate.getTime()) {
								logger.debug("数据库中小于前台界面 或者 等于 更新后台 ");
								updateMaintainTag = true;
							} else {
								logger.debug("数据库中 大于 前台界面 不做更新 ");
								updateMaintainTag = false;
							}
						} else {
							// 如果后台返回 为空 更新
							if (StringUtils.isNullOrEmpty(vehicleMaintainDate)) {
								updateMaintainTag = true;
							} else {
								if (dto.getNextMaintainDate().getTime() < vehicleMaintainDate.getTime()) {
									logger.debug("原有逻辑 界面下次保养日期保养里程小于 数据库中的不更新: ");
									updateMaintainTag = false;
								}
							}
						}
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		// 如果1074 参数设置 为 12781001 或者 为空则默认为报存
		String isEditMaintainDate = Utility.getDefaultValue("1074");
		if (StringUtils.isNullOrEmpty(isEditMaintainDate) || DictCodeConstants.DICT_IS_YES.equals(isEditMaintainDate)) {
			updateMaintainTag = true;
		}

		// 如果车主编号为空就不修改车主和车辆信息
		if (StringUtils.isNullOrEmpty(dto.getOwnerNo())) {
			return 1;
		}
		boolean change = false;
		VehiclePO vehiclePO = VehiclePO.findByCompositeKeys(dto.getVin(), dealerCode);
		VehiclePO tmVehiclePO = VehiclePO.findByCompositeKeys(dto.getVin(), groupCodeVehicle);// 用于修改操作
		// 判定vin在tm_vehicle中是否存在，如果存在一一比对相关值是否改变
		if (!StringUtils.isNullOrEmpty(vehiclePO)) {
			if (!StringUtils.isNullOrEmpty(dto.getLastMaintainDate())) {
				tmVehiclePO.set("LAST_MAINTAIN_DATE", dto.getLastMaintainDate());
				if (!StringUtils.isNullOrEmpty(vehiclePO.getDate("LAST_MAINTAIN_DATE"))
						&& vehiclePO.getDate("LAST_MAINTAIN_DATE").getTime() != dto.getLastMaintainDate().getTime()) {
					change = true;
				}
			}
			// 上次维修里程
			if (!StringUtils.isNullOrEmpty(dto.getLastRepairMileage())) {
				logger.debug("更新里程 :" + dto.getLastRepairMileage());
				tmVehiclePO.setString("LAST_MAINTAIN_MILEAGE", dto.getLastRepairMileage());
				if (!StringUtils.isNullOrEmpty(vehiclePO.getString("LAST_MAINTAIN_MILEAGE")) && Double
						.parseDouble(dto.getLastRepairMileage()) != vehiclePO.getDouble("LAST_MAINTAIN_MILEAGE")) {
					change = true;
				}
			}
			if (!StringUtils.isNullOrEmpty(dto.getNextMaintainDate()) && updateMaintainTag) {
				tmVehiclePO.set("NEXT_MAINTAIN_DATE", dto.getNextMaintainDate());
				tmVehiclePO.setString("NEXT_MAINTAIN_MILEAGE", dto.getNextMaintainMileage());
				tmVehiclePO.set("RO_CREATE_DATE", dto.getCreateDate());
				if ((!StringUtils.isNullOrEmpty(vehiclePO.getDate("NEXT_MAINTAIN_DATE"))
						&& vehiclePO.getDate("NEXT_MAINTAIN_DATE").getTime() != dto.getNextMaintainDate().getTime())
						|| (!StringUtils.isNullOrEmpty(vehiclePO.getString("NEXT_MAINTAIN_MILEAGE"))
								&& !vehiclePO.getString("NEXT_MAINTAIN_MILEAGE").equals(dto.getNextMaintainMileage()))
						|| (!StringUtils.isNullOrEmpty(vehiclePO.getDate("RO_CREATE_DATE"))
								&& vehiclePO.getDate("RO_CREATE_DATE").getTime() != dto.getCreateDate().getTime())) {
					change = true;
				}
			}
			if (!StringUtils.isNullOrEmpty(dto.getLicenseDate())) {
				tmVehiclePO.setDate("LICENSE_DATE", dto.getLicenseDate());
			}
			tmVehiclePO.setString("LICENSE", dto.getLicense());
			tmVehiclePO.setString("ENGINE_NO", dto.getEngineNo());
			tmVehiclePO.setString("BRAND", dto.getBrand());
			tmVehiclePO.setString("SERIES", dto.getSeries());
			tmVehiclePO.setString("MODEL", dto.getModel());
			// 车型修改后 同时更新发动机排量 和燃油类型
			if (!StringUtils.isNullOrEmpty(dto.getModel())) {
				// 根据车型获取燃油类型，发动机排量
				TmModelPO modelPO = TmModelPO.findFirst("MODEL_CODE = ? AND DEALER_CODE = ?", dto.getModel(),
						dealerCode);
				if (!StringUtils.isNullOrEmpty(modelPO)) {
					tmVehiclePO.setString("OIL_TYPE", modelPO.getString("OIL_TYPE"));
					tmVehiclePO.setString("PAIQI_QUANTITY", modelPO.getString("EXHAUST_QUANTITY"));
				}
			}
			tmVehiclePO.setDouble("MILEAGE", Double.parseDouble(dto.getInMileage()));
			tmVehiclePO.setString("DELIVERER", dto.getDeliverer());
			tmVehiclePO.setString("DELIVERER_GENDER", dto.getDelivererGender());
			tmVehiclePO.setString("DELIVERER_PHONE", dto.getDelivererPhone());
			tmVehiclePO.setString("DELIVERER_MOBILE", dto.getDelivererMobile());
			tmVehiclePO.setString("COLOR", dto.getColor());
			// tmVehiclePO.setString("MODEL_YEAR",dto.getmode);
			if (!StringUtils.isNullOrEmpty(dto.getFirstInDate())) {
				tmVehiclePO.set("FIRST_IN_DATE", dto.getFirstInDate());
			}
			if (!StringUtils.isNullOrEmpty(dto.getMemberNo())) {
				tmVehiclePO.setString("MEMBER_NO", dto.getMemberNo());
			}
			if ((!StringUtils.isNullOrEmpty(vehiclePO.getDate("LICENSE_DATE"))
					&& !StringUtils.isNullOrEmpty(dto.getLicenseDate())
					&& dto.getLicenseDate().getTime() != vehiclePO.getDate("LICENSE_DATE").getTime())
					|| (!StringUtils.isNullOrEmpty(vehiclePO.getString("LICENSE"))
							&& !vehiclePO.getString("LICENSE").equals(dto.getLicense()))
					|| (!StringUtils.isNullOrEmpty(vehiclePO.getString("ENGINE_NO"))
							&& !vehiclePO.getString("ENGINE_NO").equals(dto.getEngineNo()))
					|| (!StringUtils.isNullOrEmpty(vehiclePO.getString("BRAND"))
							&& !vehiclePO.getString("BRAND").equals(dto.getBrand()))
					|| (!StringUtils.isNullOrEmpty(vehiclePO.getString("SERIES"))
							&& !vehiclePO.getString("SERIES").equals(dto.getSeries()))
					|| (!StringUtils.isNullOrEmpty(vehiclePO.getString("MODEL"))
							&& !vehiclePO.getString("MODEL").equals(dto.getModel()))
					|| (!StringUtils.isNullOrEmpty(vehiclePO.getString("MILEAGE"))
							&& !vehiclePO.getString("MILEAGE").equals(dto.getInMileage()))
					|| (!StringUtils.isNullOrEmpty(vehiclePO.getString("DELIVERER"))
							&& !vehiclePO.getString("DELIVERER").equals(dto.getDeliverer()))
					|| (!StringUtils.isNullOrEmpty(vehiclePO.getString("DELIVERER_GENDER"))
							&& !vehiclePO.getString("DELIVERER_GENDER").equals(dto.getDelivererGender()))
					|| (!StringUtils.isNullOrEmpty(vehiclePO.getString("DELIVERER_PHONE"))
							&& !vehiclePO.getString("DELIVERER_PHONE").equals(dto.getDelivererPhone()))
					|| (!StringUtils.isNullOrEmpty(vehiclePO.getString("DELIVERER_MOBILE"))
							&& !vehiclePO.getString("DELIVERER_MOBILE").equals(dto.getDelivererMobile()))
					|| (!StringUtils.isNullOrEmpty(vehiclePO.getString("COLOR"))
							&& !vehiclePO.getString("COLOR").equals(dto.getColor()))
					||
			// (!StringUtils.isNullOrEmpty(vehiclePO.getString("MODEL_YEAR"))&&!vehiclePO.getString("MODEL_YEAR").equals())||
					(!StringUtils.isNullOrEmpty(vehiclePO.getDate("FIRST_IN_DATE"))
							&& vehiclePO.getDate("FIRST_IN_DATE").getTime() != dto.getFirstInDate().getTime())
					|| (!StringUtils.isNullOrEmpty(vehiclePO.getString("MEMBER_NO"))
							&& !vehiclePO.getString("MEMBER_NO").equals(dto.getMemberNo()))) {
				change = true;
			}
			// vin号为空的话不更新车辆信息
			if (!StringUtils.isNullOrEmpty(dto.getVin())) {
				List<Map> list = Base.findAll("SELECT * FROM TM_VEHICLE WHERE VIN = ? AND DEALER_CODE = ?",
						dto.getVin(), groupCodeVehicle);
				list = Utility.getVehicleSubclassList(dealerCode, list);
				if (list.size() > 0) {
					if (!StringUtils.isNullOrEmpty(dto.getRoNo())) {// 新增工单的情况
						Map t = list.get(0);
						if (!StringUtils.isNullOrEmpty(dto.getSalesDate())) {
							if (!StringUtils.isNullOrEmpty(t.get("CUSTOMER_NO"))
									&& DictCodeConstants.DICT_IS_YES.equals(t.get("IS_UPLOAD").toString())) {
							} else {
								tmVehiclePO.set("SALES_DATE", dto.getSalesDate());
								if (!StringUtils.isNullOrEmpty(vehiclePO.getDate("SALES_DATE"))
										&& vehiclePO.getDate("SALES_DATE").getTime() != dto.getSalesDate().getTime()) {
									change = true;
								}
							}
						}

						double totolChangeMileage = 0;
						if (!StringUtils.isNullOrEmpty(t.get("TOTAL_CHANGE_MILEAGE"))) {
							totolChangeMileage = Double.parseDouble(t.get("TOTAL_CHANGE_MILEAGE").toString());
						}
						tmVehiclePO.setDouble("TOTAL_CHANGE_MILEAGE",
								totolChangeMileage + Double.parseDouble(dto.getChangeMileage()));
						if (!StringUtils.isNullOrEmpty(vehiclePO.getDouble("TOTAL_CHANGE_MILEAGE"))
								&& (totolChangeMileage + Double.parseDouble(dto.getChangeMileage())) != vehiclePO
										.getDouble("TOTAL_CHANGE_MILEAGE")) {
							change = true;
						}
						if (DictCodeConstants.DICT_IS_YES.equals(dto.getIsChangeOdograph())) {// 如果勾选了换表，
							tmVehiclePO.set("CHANGE_DATE", new Timestamp(System.currentTimeMillis()));
							change = true;
						}
						tmVehiclePO.setLong("IS_UPLOAD_GROUP", DictCodeConstants.IS_NOT);
						if (StringUtils.isNull(dto.getVin()))
							throw new ServiceBizException("主键丢失");
						// 二级网点业务-车辆子表更新
						updateSubclassPO(dto.getVin(), tmVehiclePO);

						// 判断下次保养日期是否发生改变, 如果有则增加修改记录
						VehiclePO vehiclePOCon = VehiclePO.findFirst("VIN = ? AND DEALER_CODE = ?", dto.getVin(),
								dealerCode);
						if (!StringUtils.isNullOrEmpty(vehiclePOCon)) {
							String nextMaintainDate1 = null;
							String nextMaintainDate2 = null;
							if (!StringUtils.isNullOrEmpty(vehiclePOCon.getString("NEXT_MAINTAIN_DATE"))) {
								nextMaintainDate1 = vehiclePOCon.getString("NEXT_MAINTAIN_DATE");
							}
							if (!StringUtils.isNullOrEmpty(tmVehiclePO.getString("NEXT_MAINTAIN_DATE"))) {
								nextMaintainDate2 = tmVehiclePO.getString("NEXT_MAINTAIN_DATE");
							}
							logger.debug("更新前 下次保养日期 :" + nextMaintainDate1);
							logger.debug("需要更新下次保养日期 :" + nextMaintainDate2);
							if (StringUtils.isNullOrEmpty(vehiclePOCon.getString("NEXT_MAINTAIN_DATE"))
									|| !vehiclePOCon.getString("NEXT_MAINTAIN_DATE")
											.equals(tmVehiclePO.getString("NEXT_MAINTAIN_DATE"))) {
								logger.debug("不相等记录日志 :");
								tmVehiclePO.setDate("SYSTEM_LAST_MAINTENANCE_DATE",
										vehiclePOCon.getDate("NEXT_MAINTAIN_DATE"));
								if (StringUtils.isNullOrEmpty(vehiclePOCon.getString("SYSTEM_REMARK"))) {
									try {
										tmVehiclePO.setString("SYSTEM_REMARK", "工单下保前:" + nextMaintainDate1 + ", 调整后: "
												+ nextMaintainDate2 + ",时间:" + Utility.getCurrentTimestamp());
									} catch (Exception e) {
										e.printStackTrace();
									}
								} else {
									logger.debug("长度  :" + vehiclePOCon.getString("SYSTEM_REMARK").length());
									if (vehiclePOCon.getString("SYSTEM_REMARK").length() > 660) {
										String subStr = vehiclePOCon.getString("SYSTEM_REMARK").substring(0, 300);
										try {
											tmVehiclePO.setString("SYSTEM_REMARK",
													"工单下保前:" + nextMaintainDate1 + ", 调整后: " + nextMaintainDate2
															+ ",时间:" + Utility.getCurrentTimestamp() + " &&" + subStr);
										} catch (Exception e) {
											e.printStackTrace();
										}
									} else {
										try {
											tmVehiclePO.setString("SYSTEM_REMARK",
													"工单下保前:" + nextMaintainDate1 + ", 调整后: " + nextMaintainDate2
															+ ",时间:" + Utility.getCurrentTimestamp() + " &&"
															+ vehiclePOCon.getString("SYSTEM_REMARK"));
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
								}
								tmVehiclePO.set("SYSTEM_UPDATE_DATE", new Timestamp(System.currentTimeMillis()));
								if (!StringUtils.isNullOrEmpty(vehiclePOCon.getDate("NEXT_MAINTAIN_DATE"))
										&& !StringUtils.isNullOrEmpty(tmVehiclePO.getDate("NEXT_MAINTAIN_DATE"))
										&& tmVehiclePO.getDate("NEXT_MAINTAIN_DATE").getTime() != vehiclePOCon
												.getDate("NEXT_MAINTAIN_DATE").getTime()) {
									change = true;
								}
							}
						}

						if (change) {
							tmVehiclePO.set("UPDATED_AT", new Timestamp(System.currentTimeMillis()));
						} else {
							tmVehiclePO.manageTime(false);// 设置不更新UPDATED_AT字段
						}
						tmVehiclePO.saveIt();
					} else {// 工单修改
						RepairOrderPO repairPO = RepairOrderPO.findByCompositeKeys(dealerCode, dto.getRoNo());
						if (!StringUtils.isNullOrEmpty(repairPO)
								&& repairPO.getInteger("D_KEY") == CommonConstants.D_KEY) {
							Map t = list.get(0);
							if (!StringUtils.isNullOrEmpty(dto.getSalesDate())) {
								if (!StringUtils.isNullOrEmpty(t.get("CUSTOMER_NO"))
										&& DictCodeConstants.DICT_IS_YES.equals(t.get("IS_UPLOAD").toString())) {
								} else {
									tmVehiclePO.set("SALES_DATE", dto.getSalesDate());
									if (!StringUtils.isNullOrEmpty(vehiclePO.getDate("SALES_DATE")) && vehiclePO
											.getDate("SALES_DATE").getTime() != dto.getSalesDate().getTime()) {
										change = true;
									}
								}
							}
							// 销售转工单的时候，销售里程的值是NULL
							if (!StringUtils.isNullOrEmpty(repairPO.getDouble("CHANGE_MILEAGE")))
								repairPO.setDouble("CHANGE_MILEAGE", 0.0);
							if (repairPO.getDouble("CHANGE_MILEAGE") != Double.parseDouble(dto.getChangeMileage())) {
								tmVehiclePO.set("CHANGE_DATE", new Timestamp(System.currentTimeMillis()));
								change = true;
							}
							double totolChangeMileage = 0;
							if (!StringUtils.isNullOrEmpty(t.get("TOTAL_CHANGE_MILEAGE"))) {
								totolChangeMileage = Double.parseDouble(t.get("TOTAL_CHANGE_MILEAGE").toString());
							}
							tmVehiclePO.setDouble("TOTAL_CHANGE_MILEAGE",
									totolChangeMileage - repairPO.getDouble("CHANGE_MILEAGE")
											+ Double.parseDouble(dto.getChangeMileage()));
							if (!StringUtils.isNullOrEmpty(vehiclePO.getDouble("TOTAL_CHANGE_MILEAGE"))
									&& totolChangeMileage - repairPO.getDouble("CHANGE_MILEAGE")
											+ Double.parseDouble(dto.getChangeMileage()) != vehiclePO
													.getDouble("TOTAL_CHANGE_MILEAGE")) {
								change = true;
							}

							tmVehiclePO.setLong("IS_UPLOAD_GROUP", DictCodeConstants.IS_NOT);
							if (StringUtils.isNull(dto.getVin()))
								throw new ServiceBizException("主键丢失");

							// 二级网点业务-车辆子表更新
							updateSubclassPO(dto.getVin(), tmVehiclePO);

							// 判断下次保养日期是否发生改变, 如果有则增加修改记录
							VehiclePO vehiclePOCon = VehiclePO.findFirst("VIN = ? AND DEALER_CODE = ?", dto.getVin(),
									dealerCode);
							if (!StringUtils.isNullOrEmpty(vehiclePOCon)) {
								String nextMaintainDate1 = null;
								String nextMaintainDate2 = null;
								if (!StringUtils.isNullOrEmpty(vehiclePOCon.getString("NEXT_MAINTAIN_DATE"))) {
									nextMaintainDate1 = vehiclePOCon.getString("NEXT_MAINTAIN_DATE");
								}
								if (!StringUtils.isNullOrEmpty(tmVehiclePO.getString("NEXT_MAINTAIN_DATE"))) {
									nextMaintainDate2 = tmVehiclePO.getString("NEXT_MAINTAIN_DATE");
								}
								logger.debug("更新前 下次保养日期 :" + nextMaintainDate1);
								logger.debug("需要更新下次保养日期 :" + nextMaintainDate2);
								if (StringUtils.isNullOrEmpty(vehiclePOCon.getString("NEXT_MAINTAIN_DATE"))
										|| !vehiclePOCon.getString("NEXT_MAINTAIN_DATE")
												.equals(tmVehiclePO.getString("NEXT_MAINTAIN_DATE"))) {
									logger.debug("不相等记录日志 :");
									tmVehiclePO.setDate("SYSTEM_LAST_MAINTENANCE_DATE",
											vehiclePOCon.getDate("NEXT_MAINTAIN_DATE"));
									if (StringUtils.isNullOrEmpty(vehiclePOCon.getString("SYSTEM_REMARK"))) {
										try {
											tmVehiclePO.setString("SYSTEM_REMARK",
													"工单下保前:" + nextMaintainDate1 + ", 调整后: " + nextMaintainDate2
															+ ",时间:" + Utility.getCurrentTimestamp());
										} catch (Exception e) {
											e.printStackTrace();
										}
									} else {
										logger.debug("长度(修改工单)  :" + vehiclePOCon.getString("SYSTEM_REMARK").length());
										if (vehiclePOCon.getString("SYSTEM_REMARK").length() > 660) {
											String subStr = vehiclePOCon.getString("SYSTEM_REMARK").substring(0, 300);
											try {
												tmVehiclePO.setString("SYSTEM_REMARK",
														"工单下保前:" + nextMaintainDate1 + ", 调整后: " + nextMaintainDate2
																+ ",时间:" + Utility.getCurrentTimestamp() + " &&"
																+ subStr);
											} catch (Exception e) {
												e.printStackTrace();
											}
										} else {
											try {
												tmVehiclePO.setString("SYSTEM_REMARK",
														"工单下保前:" + nextMaintainDate1 + ", 调整后: " + nextMaintainDate2
																+ ",时间:" + Utility.getCurrentTimestamp() + " &&"
																+ vehiclePOCon.getString("SYSTEM_REMARK"));
											} catch (Exception e) {
												// TODO Auto-generated catch
												// block
												e.printStackTrace();
											}
										}
									}
									tmVehiclePO.set("SYSTEM_UPDATE_DATE", new Timestamp(System.currentTimeMillis()));
									if (!StringUtils.isNullOrEmpty(vehiclePOCon.getDate("NEXT_MAINTAIN_DATE"))
											&& !StringUtils.isNullOrEmpty(tmVehiclePO.getDate("NEXT_MAINTAIN_DATE"))
											&& tmVehiclePO.getDate("NEXT_MAINTAIN_DATE").getTime() != vehiclePOCon
													.getDate("NEXT_MAINTAIN_DATE").getTime()) {
										change = true;
									}
								}
							}

							if (change) {
								tmVehiclePO.set("UPDATED_AT", new Timestamp(System.currentTimeMillis()));
							} else {
								tmVehiclePO.manageTime(false);// 设置不更新UPDATED_AT字段
							}
							tmVehiclePO.saveIt();
						}
					}
				} else {
					if (!StringUtils.isNullOrEmpty(dto.getSalesDate()))
						tmVehiclePO.setDate("SALES_DATE", dto.getSalesDate());
					tmVehiclePO.setDouble("TOTAL_CHANGE_MILEAGE", dto.getChangeMileage());

					if (DictCodeConstants.DICT_IS_YES.equals(dto.getIsChangeOdograph())) {
						tmVehiclePO.set("CHANGE_DATE", new Timestamp(System.currentTimeMillis()));
					}
					tmVehiclePO.set("FOUND_DATE", new Timestamp(System.currentTimeMillis()));
					tmVehiclePO.setString("VIN", dto.getVin());
					tmVehiclePO.setString("OWNER_NO", dto.getOwnerNo());
					// 增加保存主机厂返回的产品代码
					if (!StringUtils.isNullOrEmpty(dto.getProductCode()))
						tmVehiclePO.setString("PRODUCT_CODE", dto.getProductCode());
					tmVehiclePO.setString("DEALER_CODE", groupCodeVehicle);
					tmVehiclePO.setLong("IS_UPLOAD_GROUP", DictCodeConstants.IS_NOT);
					if (!StringUtils.isNullOrEmpty(dto.getIsWar())) {
						tmVehiclePO.setInteger("IS_TRIPLE_GUARANTEE", Integer.parseInt(dto.getIsWar()));
					}
					if (!StringUtils.isNullOrEmpty(dto.getWarMileage())) {
						tmVehiclePO.setDouble("WAR_MILEAGE", Double.parseDouble(dto.getWarMileage()));
					}
					if (!StringUtils.isNullOrEmpty(dto.getWarMonth())) {
						tmVehiclePO.setDouble("WAR_MONTHS", Double.parseDouble(dto.getWarMonth()));
					}
					// 同步车辆信息后更新车辆信息
					if (!StringUtils.isNullOrEmpty(dto.getWarEndDate())) {
						tmVehiclePO.set("WAR_END_DATE", dto.getWarEndDate());
					}
					tmVehiclePO.saveIt();

					List<Map> listEntityVehicle = Utility.getSubEntityList(tmVehiclePO.getString("DEALER_CODE"),
							"TM_VEHICLE");
					if (listEntityVehicle.size() > 0)
						for (Map map : listEntityVehicle) {
							addSuvclassPO(tmVehiclePO, dealerCode, map);
						}
				}
			}
			CarownerPO tmOwnerPO = CarownerPO.findByCompositeKeys(dto.getOwnerNo());
			if (!StringUtils.isNullOrEmpty(tmOwnerPO)) {
				tmOwnerPO.setString("OWNER_NAME", dto.getOwnerName());
				tmOwnerPO.setLong("IS_UPLOAD", DictCodeConstants.IS_NOT);
				tmOwnerPO.setLong("IS_UPLOAD_GROUP", DictCodeConstants.IS_NOT);
				// 会员编号
				if (!StringUtils.isNullOrEmpty(dto.getMemberNo())) {
					tmOwnerPO.setString("MEMBER_NO", dto.getMemberNo());
				}
				if (StringUtils.isNullOrEmpty(dto.getOwnerNo()))
					throw new ServiceBizException("丢失主键值");
				if (DictCodeConstants.DICT_IS_YES.equals(dto.getIsUpdateOwner())) {
					tmOwnerPO.saveIt();
				}
			}
		} else {
			if (!StringUtils.isNullOrEmpty(dto.getLastMaintainDate())) {
				tmVehiclePO.set("LAST_MAINTAIN_DATE", dto.getLastMaintainDate());
			}
			// 上次维修里程
			if (!StringUtils.isNullOrEmpty(dto.getLastRepairMileage())) {
				logger.debug("更新里程 :" + dto.getLastRepairMileage());
				tmVehiclePO.setDouble("LAST_MAINTAIN_MILEAGE", Double.parseDouble(dto.getLastRepairMileage()));
			}
			if (!StringUtils.isNullOrEmpty(dto.getNextMaintainDate())
					&& !StringUtils.isNullOrEmpty(dto.getNextMaintainMileage()) && updateMaintainTag) {
				tmVehiclePO.set("NEXT_MAINTAIN_DATE", dto.getNextMaintainDate());
				tmVehiclePO.setDouble("NEXT_MAINTAIN_MILEAGE", dto.getNextMaintainMileage());
				tmVehiclePO.set("RO_CREATE_DATE", dto.getCreateDate());
			}
			if (!StringUtils.isNullOrEmpty(dto.getLicenseDate()))
				tmVehiclePO.set("LICENSE_DATE", dto.getLicenseDate());
			tmVehiclePO.setString("LICENSE", dto.getLicense());
			tmVehiclePO.setString("ENGINE_NO", dto.getEngineNo());
			tmVehiclePO.setString("BRAND", dto.getBrand());
			tmVehiclePO.setString("SERIES", dto.getSeries());
			tmVehiclePO.setString("MODEL", dto.getModel());
			tmVehiclePO.setDouble("MILEAGE", Double.parseDouble(dto.getInMileage()));
			tmVehiclePO.setString("DELIVERER", dto.getDeliverer());
			tmVehiclePO.setString("DELIVERER_GENDER", dto.getDelivererGender());
			tmVehiclePO.setString("DELIVERER_PHONE", dto.getDelivererPhone());
			tmVehiclePO.setString("DELIVERER_MOBILE", dto.getDelivererMobile());
			tmVehiclePO.setString("COLOR", dto.getColor());
			// tmVehiclePO.setString("MODEL_YEAR",dto.getmodel);
			if (!StringUtils.isNullOrEmpty(dto.getFirstInDate()))
				tmVehiclePO.set("FIRST_IN_DATE", dto.getFirstInDate());
			// 会员编号
			if (!StringUtils.isNullOrEmpty(dto.getMemberNo()))
				tmVehiclePO.setString("MEMBER_NO", dto.getMemberNo());
			// vin号为空的话不更新车辆信息
			if (!StringUtils.isNullOrEmpty(dto.getVin())) {
				List<Map> list = Base.findAll("SELECT * FROM TM_VEHICLE WHERE VIN = ? AND DEALER_CODE = ? ",
						dto.getVin(), groupCodeVehicle);
				list = Utility.getVehicleSubclassList(dealerCode, list);
				if (list.size() > 0) {
					if (StringUtils.isNullOrEmpty(dto.getRoNo())) {// 新增工单的情况
						Map t = list.get(0);
						if (!StringUtils.isNullOrEmpty(dto.getSalesDate())) {
							if (!StringUtils.isNullOrEmpty(t.get("CUSTOMER_NO"))
									&& DictCodeConstants.DICT_IS_YES.equals(t.get("IS_UPLOAD").toString())) {
							} else {
								tmVehiclePO.set("SALES_DATE", dto.getSalesDate());
							}
						}
						double totolChangeMileage = 0;
						if (!StringUtils.isNullOrEmpty(t.get("TOTAL_CHANGE_MILEAGE"))) {
							totolChangeMileage = Double.parseDouble(t.get("TOTAL_CHANGE_MILEAGE").toString());
						}
						tmVehiclePO.setDouble("TOTAL_CHANGE_MILEAGE",
								totolChangeMileage + Double.parseDouble(dto.getChangeMileage()));
						if (DictCodeConstants.DICT_IS_YES.equals(dto.getIsChangeOdograph())) {// 如果勾选了换表，
							tmVehiclePO.set("CHANGE_DATE", new Timestamp(System.currentTimeMillis()));
						}
						tmVehiclePO.setLong("IS_UPLOAD_GROUP", DictCodeConstants.IS_NOT);
						if (StringUtils.isNullOrEmpty(dto.getVin()))
							throw new ServiceBizException("丢失主键值");
						// 二级网点业务-车辆子表更新
						updateSubclassPO(dto.getVin(), tmVehiclePO);
						// 判断下次保养日期是否发生改变, 如果有则增加修改记录
						VehiclePO vehiclePOCon = VehiclePO.findFirst("VIN = ? AND DEALER_CODE = ?", dto.getVin(),
								dealerCode);
						if (!StringUtils.isNullOrEmpty(vehiclePOCon)) {
							String nextMaintainDate1 = null;
							String nextMaintainDate2 = null;
							if (!StringUtils.isNullOrEmpty(vehiclePOCon.getString("NEXT_MAINTAIN_DATE"))) {
								nextMaintainDate1 = vehiclePOCon.getString("NEXT_MAINTAIN_DATE");
							}
							if (!StringUtils.isNullOrEmpty(tmVehiclePO.getString("NEXT_MAINTAIN_DATE"))) {
								nextMaintainDate2 = tmVehiclePO.getString("NEXT_MAINTAIN_DATE");
							}
							logger.debug("更新前 下次保养日期 :" + nextMaintainDate1);
							logger.debug("需要更新下次保养日期 :" + nextMaintainDate2);
							if (StringUtils.isNullOrEmpty(vehiclePOCon.getString("NEXT_MAINTAIN_DATE"))
									|| !vehiclePOCon.getString("NEXT_MAINTAIN_DATE")
											.equals(tmVehiclePO.getString("NEXT_MAINTAIN_DATE"))) {
								logger.debug("不相等记录日志 :");
								tmVehiclePO.setDate("SYSTEM_LAST_MAINTENANCE_DATE",
										vehiclePOCon.getDate("NEXT_MAINTAIN_DATE"));
								if (StringUtils.isNullOrEmpty(vehiclePOCon.getString("SYSTEM_REMARK"))) {
									try {
										tmVehiclePO.setString("SYSTEM_REMARK", "工单下保前:" + nextMaintainDate1 + ", 调整后: "
												+ nextMaintainDate2 + ",时间:" + Utility.getCurrentTimestamp());
									} catch (Exception e) {
										e.printStackTrace();
									}
								} else {
									logger.debug("长度  :" + vehiclePOCon.getString("SYSTEM_REMARK").length());
									if (vehiclePOCon.getString("SYSTEM_REMARK").length() > 660) {
										String subStr = vehiclePOCon.getString("SYSTEM_REMARK").substring(0, 300);
										try {
											tmVehiclePO.setString("SYSTEM_REMARK",
													"工单下保前:" + nextMaintainDate1 + ", 调整后: " + nextMaintainDate2
															+ ",时间:" + Utility.getCurrentTimestamp() + " &&" + subStr);
										} catch (Exception e) {
											e.printStackTrace();
										}
									} else {
										try {
											tmVehiclePO.setString("SYSTEM_REMARK",
													"工单下保前:" + nextMaintainDate1 + ", 调整后: " + nextMaintainDate2
															+ ",时间:" + Utility.getCurrentTimestamp() + " &&"
															+ vehiclePOCon.getString("SYSTEM_REMARK"));
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
								}
								tmVehiclePO.set("SYSTEM_UPDATE_DATE", new Timestamp(System.currentTimeMillis()));
							}
						}
						tmVehiclePO.saveIt();
					} else {// 工单修改
						RepairOrderPO repairPO = RepairOrderPO.findByCompositeKeys(dealerCode, dto.getRoNo());
						if (!StringUtils.isNullOrEmpty(repairPO)) {
							Map t = list.get(0);
							if (!StringUtils.isNullOrEmpty(dto.getSalesDate())) {
								if (!StringUtils.isNullOrEmpty(t.get("CUSTOMER_NO"))
										&& DictCodeConstants.DICT_IS_YES.equals(t.get("IS_UPLOAD").toString())) {
								} else {
									tmVehiclePO.set("SALES_DATE", dto.getSalesDate());
								}
							}
							// 销售转工单的时候，销售里程的值是NULL
							if (!StringUtils.isNullOrEmpty(repairPO.getDouble("CHANGE_MILEAGE")))
								repairPO.setDouble("CHANGE_MILEAGE", 0.0);
							if (repairPO.getDouble("CHANGE_MILEAGE") != Double.parseDouble(dto.getChangeMileage())) {
								tmVehiclePO.set("CHANGE_DATE", new Timestamp(System.currentTimeMillis()));
							}
							double totolChangeMileage = 0;
							if (!StringUtils.isNullOrEmpty(t.get("TOTAL_CHANGE_MILEAGE"))) {
								totolChangeMileage = Double.parseDouble(t.get("TOTAL_CHANGE_MILEAGE").toString());
							}
							tmVehiclePO.setDouble("TOTAL_CHANGE_MILEAGE",
									totolChangeMileage - repairPO.getDouble("CHANGE_MILEAGE")
											+ Double.parseDouble(dto.getChangeMileage()));
							tmVehiclePO.setLong("IS_UPLOAD_GROUP", DictCodeConstants.IS_NOT);
							if (StringUtils.isNull(dto.getVin()))
								throw new ServiceBizException("主键丢失");
							// 二级网点业务-车辆子表更新
							updateSubclassPO(dto.getVin(), tmVehiclePO);
							// 判断下次保养日期是否发生改变, 如果有则增加修改记录
							VehiclePO vehiclePOCon = VehiclePO.findFirst("VIN = ? AND DEALER_CODE = ?", dto.getVin(),
									dealerCode);
							if (!StringUtils.isNullOrEmpty(vehiclePOCon)) {
								String nextMaintainDate1 = null;
								String nextMaintainDate2 = null;
								if (!StringUtils.isNullOrEmpty(vehiclePOCon.getString("NEXT_MAINTAIN_DATE"))) {
									nextMaintainDate1 = vehiclePOCon.getString("NEXT_MAINTAIN_DATE");
								}
								if (!StringUtils.isNullOrEmpty(tmVehiclePO.getString("NEXT_MAINTAIN_DATE"))) {
									nextMaintainDate2 = tmVehiclePO.getString("NEXT_MAINTAIN_DATE");
								}
								logger.debug("更新前 下次保养日期 :" + nextMaintainDate1);
								logger.debug("需要更新下次保养日期 :" + nextMaintainDate2);
								if (StringUtils.isNullOrEmpty(vehiclePOCon.getString("NEXT_MAINTAIN_DATE"))
										|| !vehiclePOCon.getString("NEXT_MAINTAIN_DATE")
												.equals(tmVehiclePO.getString("NEXT_MAINTAIN_DATE"))) {
									logger.debug("不相等记录日志 :");
									tmVehiclePO.setDate("SYSTEM_LAST_MAINTENANCE_DATE",
											vehiclePOCon.getDate("NEXT_MAINTAIN_DATE"));
									if (StringUtils.isNullOrEmpty(vehiclePOCon.getString("SYSTEM_REMARK"))) {
										try {
											tmVehiclePO.setString("SYSTEM_REMARK",
													"工单下保前:" + nextMaintainDate1 + ", 调整后: " + nextMaintainDate2
															+ ",时间:" + Utility.getCurrentTimestamp());
										} catch (Exception e) {
											e.printStackTrace();
										}
									} else {
										logger.debug("长度(修改工单)  :" + vehiclePOCon.getString("SYSTEM_REMARK").length());
										if (vehiclePOCon.getString("SYSTEM_REMARK").length() > 660) {
											String subStr = vehiclePOCon.getString("SYSTEM_REMARK").substring(0, 300);
											try {
												tmVehiclePO.setString("SYSTEM_REMARK",
														"工单下保前:" + nextMaintainDate1 + ", 调整后: " + nextMaintainDate2
																+ ",时间:" + Utility.getCurrentTimestamp() + " &&"
																+ subStr);
											} catch (Exception e) {
												e.printStackTrace();
											}
										} else {
											try {
												tmVehiclePO.setString("SYSTEM_REMARK",
														"工单下保前:" + nextMaintainDate1 + ", 调整后: " + nextMaintainDate2
																+ ",时间:" + Utility.getCurrentTimestamp() + " &&"
																+ vehiclePOCon.getString("SYSTEM_REMARK"));
											} catch (Exception e) {
												// TODO Auto-generated catch
												// block
												e.printStackTrace();
											}
										}
									}
									tmVehiclePO.set("SYSTEM_UPDATE_DATE", new Timestamp(System.currentTimeMillis()));
								}
							}
							tmVehiclePO.saveIt();
						}
					}
				} else {
					if (!StringUtils.isNullOrEmpty(dto.getSalesDate()))
						tmVehiclePO.set("SALES_DATE", dto.getSalesDate());
					tmVehiclePO.setDouble("TOTAL_CHANGE_MILEAGE", dto.getChangeMileage());
					if (DictCodeConstants.DICT_IS_YES.equals(dto.getIsChangeOdograph()))
						tmVehiclePO.set("CHANGE_DATE", new Timestamp(System.currentTimeMillis()));
					tmVehiclePO.set("FOUND_DATE", new Timestamp(System.currentTimeMillis()));
					tmVehiclePO.setString("VIN", dto.getVin());
					tmVehiclePO.setString("OWNER_NO", dto.getOwnerNo());
					// 增加保存主机厂返回的产品代码
					if (!StringUtils.isNullOrEmpty(dto.getProductCode()))
						tmVehiclePO.setString("PRODUCT_CODE", dto.getProductCode());
					tmVehiclePO.setString("DEALER_CODE", groupCodeVehicle);
					tmVehiclePO.setLong("IS_UPLOAD_GROUP", DictCodeConstants.IS_NOT);
					if (!StringUtils.isNullOrEmpty(dto.getIsWar())) {
						tmVehiclePO.setInteger("IS_TRIPLE_GUARANTEE", Integer.parseInt(dto.getIsWar()));
					}
					if (!StringUtils.isNullOrEmpty(dto.getWarMileage())) {
						tmVehiclePO.setDouble("WAR_MILEAGE", Double.parseDouble(dto.getWarMileage()));
					}
					if (!StringUtils.isNullOrEmpty(dto.getWarMonth())) {
						tmVehiclePO.setDouble("WAR_MONTHS", Double.parseDouble(dto.getWarMonth()));
					}
					// 同步车辆信息后更新车辆信息
					if (!StringUtils.isNullOrEmpty(dto.getWarEndDate())) {
						tmVehiclePO.set("WAR_END_DATE", dto.getWarEndDate());
					}
					tmVehiclePO.saveIt();
					// 二级网点业务
					List<Map> listEntityVehicle = Utility.getSubEntityList(tmVehiclePO.getString("DEALER_CODE"),
							"TM_VEHICLE");
					if (listEntityVehicle.size() > 0)
						for (Map map : listEntityVehicle) {
							addSuvclassPO(tmVehiclePO, dealerCode, map);
						}
				}
			}

			CarownerPO tmOwnerPO = CarownerPO.findByCompositeKeys(dto.getOwnerNo(), groupCodeOwner);
			tmOwnerPO.setString("OWNER_NAME", dto.getOwnerName());
			tmOwnerPO.setLong("IS_UPLOAD", DictCodeConstants.IS_NOT);
			tmOwnerPO.setLong("IS_UPLOAD_GROUP", DictCodeConstants.IS_NOT);
			// 会员编号
			if (!StringUtils.isNullOrEmpty(dto.getMemberNo()))
				tmOwnerPO.setString("MEMBER_NO", dto.getMemberNo());
			if (StringUtils.isNullOrEmpty(dto.getOwnerNo()))
				throw new ServiceBizException("丢失主键值");
			if (!StringUtils.isNullOrEmpty(dto.getIsUpdateOwner())
					&& DictCodeConstants.DICT_IS_YES.equals(dto.getIsUpdateOwner()))
				tmOwnerPO.saveIt();
		}
		return 1;
	}

	/**
	 * 配件发料价格修改进行的日志操作
	 * 
	 * @param operateType
	 * @param operator
	 */
	public void handleOperateLog(String content, String remark, int operateType, String operator)
			throws ServiceBizException {
		OperateLogPO logPO = new OperateLogPO();
		logPO.setString("OPERATE_CONTENT", content.trim());
		logPO.setInteger("OPERATE_TYPE", operateType);
		logPO.setString("OPERATOR", operator);
		logPO.setString("DEALER_CODE", FrameworkUtil.getLoginInfo().getDealerCode());
		logPO.setString("REMARK", remark);
		logPO.saveIt();
	}

	/**
	 * 二级网点业务-车辆子表更新
	 * 
	 * @param vin
	 * @param tmVehiclePO
	 */
	public void updateSubclassPO(String vin, VehiclePO tmVehiclePO) throws ServiceBizException {
		TmVehicleSubclassPO poSub = TmVehicleSubclassPO.findFirst("VIN = ? AND DEALER_CODE = ?", vin,
				FrameworkUtil.getLoginInfo().getDealerCode());
		poSub.setString("CONSULTANT", tmVehiclePO.getString("CONSULTANT"));
		poSub.setInteger("IS_SELF_COMPANY", tmVehiclePO.getInteger("IS_SELF_COMPANY"));
		poSub.setDate("FIRST_IN_DATE", tmVehiclePO.getDate("FIRST_IN_DATE"));
		poSub.setString("CHIEF_TECHNICIAN", tmVehiclePO.getString("CHIEF_TECHNICIAN"));
		poSub.setString("SERVICE_ADVISOR", tmVehiclePO.getString("SERVICE_ADVISOR"));
		poSub.setString("INSURANCE_ADVISOR", tmVehiclePO.getString("INSURANCE_ADVISOR"));
		poSub.setString("MAINTAIN_ADVISOR", tmVehiclePO.getString("MAINTAIN_ADVISOR"));
		poSub.setDate("LAST_MAINTAIN_DATE", tmVehiclePO.getDate("LAST_MAINTAIN_DATE"));
		poSub.setDouble("LAST_MAINTAIN_MILEAGE", tmVehiclePO.getDouble("LAST_MAINTAIN_MILEAGE"));
		poSub.setDate("LAST_MAINTENANCE_DATE", tmVehiclePO.getDate("LAST_MAINTENANCE_DATE"));
		poSub.setDouble("LAST_MAINTENANCE_MILEAGE", tmVehiclePO.getDouble("LAST_MAINTENANCE_MILEAGE"));
		poSub.setDouble("PRE_PAY", tmVehiclePO.getDouble("PRE_PAY"));
		poSub.setDouble("ARREARAGE_AMOUNT", tmVehiclePO.getDouble("ARREARAGE_AMOUNT"));
		poSub.setDate("DISCOUNT_EXPIRE_DATE", tmVehiclePO.getDate("DISCOUNT_EXPIRE_DATE"));
		poSub.setString("DISCOUNT_MODE_CODE", tmVehiclePO.getString("DISCOUNT_MODE_CODE"));
		poSub.setInteger("IS_SELF_COMPANY_INSURANCE", tmVehiclePO.getInteger("IS_SELF_COMPANY_INSURANCE"));
		poSub.setDate("ADJUST_DATE", tmVehiclePO.getDate("ADJUST_DATE"));
		poSub.setString("ADJUSTER", tmVehiclePO.getString("ADJUSTER"));
		poSub.setInteger("IS_VALID", tmVehiclePO.getInteger("IS_VALID"));
		poSub.setString("OWNER_NO", tmVehiclePO.getString("OWNER_NO"));
		poSub.setInteger("NO_VALID_REASON", tmVehiclePO.getInteger("NO_VALID_REASON"));
		poSub.saveIt();
	}

	/**
	 * 二级网点业务-车辆子表新增
	 * 
	 * @param vehiclePO
	 * @param dealerCode
	 * @param entityPO
	 */
	public void addSuvclassPO(VehiclePO vehiclePO, String dealerCode, Map entityPO) throws ServiceBizException {
		TmVehicleSubclassPO poSub = new TmVehicleSubclassPO();
		poSub.setString("MAIN_ENTITY", vehiclePO.getString("DEALER_CODE"));
		poSub.setString("DEALER_CODE", entityPO.get("CHILD_ENTITY").toString());
		poSub.setString("OWNER_NO", vehiclePO.getString("OWNER_NO"));
		poSub.setString("VIN", vehiclePO.getString("VIN"));

		// 不共享业务字段
		if (dealerCode.equals(entityPO.get("CHILD_ENTITY").toString())) {
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
			poSub.setInteger("IS_SELF_COMPANY_INSURANCE", vehiclePO.getInteger("IS_SELF_COMPANY_INSURANCE"));
			poSub.setDate("ADJUST_DATE", vehiclePO.getDate("ADJUST_DATE"));
			poSub.setString("ADJUSTER", vehiclePO.getString("ADJUSTER"));
			poSub.setInteger("IS_VALID", vehiclePO.getInteger("IS_VALID"));
			poSub.setInteger("NO_VALID_REASON", vehiclePO.getInteger("NO_VALID_REASON"));
		}
		poSub.saveIt();
	}

	/**
	 * 会计月报表
	 * 
	 * @param partPeriodReportPO
	 * @param cycle
	 */
	public void addOrUpdateReport(String partBatchNo, String partNo, String storageCode, TtPartPeriodReportPO db,
			AccountPeriodPO account) {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		TtPartPeriodReportPO dbd = TtPartPeriodReportPO.findByCompositeKeys(dealerCode, Utility.getMonth(),
				Utility.getYear(), storageCode, partBatchNo, partNo);
		if (StringUtils.isNullOrEmpty(dbd)) {// 新增
			StringBuffer sb = new StringBuffer();
			sb.append(
					" INSERT INTO TT_PART_PERIOD_REPORT (REPORT_YEAR, REPORT_MONTH,STORAGE_CODE, PART_BATCH_NO,PART_NO,DEALER_CODE,PART_NAME,  IN_QUANTITY, STOCK_IN_AMOUNT, BUY_IN_COUNT")
					.append(", BUY_IN_AMOUNT,ALLOCATE_IN_COUNT, ALLOCATE_IN_AMOUNT, OTHER_IN_COUNT,OTHER_IN_AMOUNT, PROFIT_IN_COUNT")
					.append(", PROFIT_IN_AMOUNT, OUT_QUANTITY, STOCK_OUT_COST_AMOUNT, OUT_AMOUNT, REPAIR_OUT_COUNT, REPAIR_OUT_COST_AMOUNT")
					.append(",REPAIR_OUT_SALE_AMOUNT, SALE_OUT_COUNT, SALE_OUT_COST_AMOUNT,SALE_OUT_SALE_AMOUNT, INNER_OUT_COUNT")
					.append(", INNER_OUT_COST_AMOUNT,INNER_OUT_SALE_AMOUNT, ALLOCATE_OUT_COUNT,ALLOCATE_OUT_COST_AMOUNT, ALLOCATE_OUT_SALE_AMOUNT, OTHER_OUT_COUNT, OTHER_OUT_COST_AMOUNT, OTHER_OUT_SALE_AMOUNT")
					.append(", LOSS_OUT_COUNT, LOSS_OUT_AMOUNT,TRANSFER_IN_COUNT,TRANSFER_IN_AMOUNT,TRANSFER_OUT_COUNT,TRANSFER_OUT_COST_AMOUNT, OPEN_QUANTITY, OPEN_PRICE")
					.append(", OPEN_AMOUNT,UPHOLSTER_OUT_COUNT,UPHOLSTER_OUT_COST_AMOUNT,UPHOLSTER_OUT_SALE_AMOUNT, CLOSE_QUANTITY,CLOSE_PRICE,CLOSE_AMOUNT ) ")
					.append("VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?,  ?, ?, ?,  ?, ?, ?,   ?, ?, ?,  ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,  ?, ?, ?)");
			new DB(TtPartPeriodReportPO.getMetaModel().getDbName()).exec(sb.toString(),
					Utility.fullSpaceBuffer2(account.getString("B_YEAR"), 4),
					Utility.fullSpaceBuffer2(account.getString("PERIODS"), 2), db.getString("STORAGE_CODE"),
					db.getString("PART_BATCH_NO"), db.getString("PART_NO"), dealerCode, db.getString("PART_NAME"),
					Math.round(db.getFloat("IN_QUANTITY") * 100) * 0.01,
					Math.round(db.getDouble("STOCK_IN_AMOUNT") * 100) * 0.01,
					Math.round(db.getFloat("BUY_IN_COUNT") * 100) * 0.01,
					Math.round(db.getDouble("BUY_IN_AMOUNT") * 100) * 0.01,
					Math.round(db.getFloat("ALLOCATE_IN_COUNT") * 100) * 0.01,
					Math.round(db.getDouble("ALLOCATE_IN_AMOUNT") * 100) * 0.01,
					Math.round(db.getFloat("OTHER_IN_COUNT") * 100) * 0.01,
					Math.round(db.getDouble("OTHER_IN_AMOUNT") * 100) * 0.01,
					Math.round(db.getFloat("PROFIT_IN_COUNT") * 100) * 0.01,
					Math.round(db.getDouble("PROFIT_IN_AMOUNT") * 100) * 0.01,
					Math.round(db.getFloat("OUT_QUANTITY") * 100) * 0.01,
					Math.round(db.getDouble("STOCK_OUT_COST_AMOUNT") * 100) * 0.01,
					Math.round(db.getDouble("OUT_AMOUNT") * 100) * 0.01,
					Math.round(db.getFloat("REPAIR_OUT_COUNT") * 100) * 0.01,
					Math.round(db.getDouble("REPAIR_OUT_COST_AMOUNT") * 100) * 0.01,
					Math.round(db.getDouble("REPAIR_OUT_SALE_AMOUNT") * 100) * 0.01,
					Math.round(db.getFloat("SALE_OUT_COUNT") * 100) * 0.01,
					Math.round(db.getDouble("SALE_OUT_COST_AMOUNT") * 100) * 0.01,
					Math.round(db.getDouble("SALE_OUT_SALE_AMOUNT") * 100) * 0.01,
					Math.round(db.getFloat("INNER_OUT_COUNT") * 100) * 0.01,
					Math.round(db.getDouble("INNER_OUT_COST_AMOUNT") * 100) * 0.01,
					Math.round(db.getDouble("INNER_OUT_SALE_AMOUNT") * 100) * 0.01,
					Math.round(db.getFloat("ALLOCATE_OUT_COUNT") * 100) * 0.01,
					Math.round(db.getDouble("ALLOCATE_OUT_COST_AMOUNT") * 100) * 0.01,
					Math.round(db.getDouble("ALLOCATE_OUT_SALE_AMOUNT") * 100) * 0.01,
					Math.round(db.getFloat("OTHER_OUT_COUNT") * 100) * 0.01,
					Math.round(db.getDouble("OTHER_OUT_COST_AMOUNT") * 100) * 0.01,
					Math.round(db.getDouble("OTHER_OUT_SALE_AMOUNT") * 100) * 0.01,
					Math.round(db.getFloat("LOSS_OUT_COUNT") * 100) * 0.01,
					Math.round(db.getDouble("LOSS_OUT_AMOUNT") * 100) * 0.01,
					Math.round(db.getFloat("TRANSFER_IN_COUNT") * 100) * 0.01,
					Math.round(db.getDouble("TRANSFER_IN_AMOUNT") * 100) * 0.01,
					Math.round(db.getFloat("TRANSFER_OUT_COUNT") * 100) * 0.01,
					Math.round(db.getDouble("TRANSFER_OUT_COST_AMOUNT") * 100) * 0.01,
					Math.round(db.getFloat("OPEN_QUANTITY") * 100) * 0.01,
					Math.round(db.getDouble("OPEN_PRICE") * 10000) * 0.0001,
					Math.round(db.getDouble("OPEN_AMOUNT") * 100) * 0.01,
					Math.round(db.getFloat("UPHOLSTER_OUT_COUNT") * 100) * 0.01,
					Math.round(db.getDouble("UPHOLSTER_OUT_COST_AMOUNT") * 100) * 0.01,
					Math.round(db.getDouble("UPHOLSTER_OUT_SALE_AMOUNT") * 100) * 0.01,
					getSubF(getAddF(db.getFloat("OPEN_QUANTITY"), db.getFloat("IN_QUANTITY")),
							db.getFloat("OUT_QUANTITY")),
					Math.round(db.getDouble("CLOSE_PRICE") * 10000) * 0.0001,
					getSubD(getAddD(db.getDouble("OPEN_AMOUNT"), db.getDouble("STOCK_IN_AMOUNT")),
							db.getDouble("STOCK_OUT_COST_AMOUNT")));
		} else {// 更新
			StringBuffer updates = new StringBuffer();
			updates.append(" UPDATE TT_PART_PERIOD_REPORT SET ");
			updates.append("IN_QUANTITY = CASE WHEN IN_QUANTITY IS NULL THEN 0 ELSE IN_QUANTITY END + ?,")
					.append(" STOCK_IN_AMOUNT = CASE WHEN STOCK_IN_AMOUNT IS NULL THEN 0 ELSE STOCK_IN_AMOUNT END + ?,")
					.append(" BUY_IN_COUNT = CASE WHEN BUY_IN_COUNT IS NULL THEN 0 ELSE BUY_IN_COUNT END + ?,")
					.append(" BUY_IN_AMOUNT = CASE WHEN BUY_IN_AMOUNT IS NULL THEN 0 ELSE BUY_IN_AMOUNT END + ?,")
					.append(" ALLOCATE_IN_COUNT = CASE WHEN ALLOCATE_IN_COUNT IS NULL THEN 0 ELSE ALLOCATE_IN_COUNT END + ?,")
					.append(" ALLOCATE_IN_AMOUNT = CASE WHEN ALLOCATE_IN_AMOUNT IS NULL THEN 0 ELSE ALLOCATE_IN_AMOUNT END + ?,")
					.append(" OTHER_IN_COUNT = CASE WHEN OTHER_IN_COUNT IS NULL THEN 0 ELSE OTHER_IN_COUNT END + ?,")
					.append(" OTHER_IN_AMOUNT = CASE WHEN OTHER_IN_AMOUNT IS NULL THEN 0 ELSE OTHER_IN_AMOUNT END + ?,")
					.append(" PROFIT_IN_COUNT = CASE WHEN PROFIT_IN_COUNT IS NULL THEN 0 ELSE PROFIT_IN_COUNT END + ?,")
					.append(" PROFIT_IN_AMOUNT = CASE WHEN PROFIT_IN_AMOUNT IS NULL THEN 0 ELSE PROFIT_IN_AMOUNT END + ?,")
					.append(" OUT_QUANTITY = CASE WHEN OUT_QUANTITY IS NULL THEN 0 ELSE OUT_QUANTITY END + ?,")
					.append(" STOCK_OUT_COST_AMOUNT = CASE WHEN STOCK_OUT_COST_AMOUNT IS NULL THEN 0 ELSE STOCK_OUT_COST_AMOUNT END + ?,")
					.append(" OUT_AMOUNT = CASE WHEN OUT_AMOUNT IS NULL THEN 0 ELSE OUT_AMOUNT END + ?,")
					.append(" REPAIR_OUT_COUNT = CASE WHEN REPAIR_OUT_COUNT IS NULL THEN 0 ELSE REPAIR_OUT_COUNT END + ?,")
					.append(" REPAIR_OUT_COST_AMOUNT = CASE WHEN REPAIR_OUT_COST_AMOUNT IS NULL THEN 0 ELSE REPAIR_OUT_COST_AMOUNT END + ?,")
					.append(" REPAIR_OUT_SALE_AMOUNT = CASE WHEN REPAIR_OUT_SALE_AMOUNT IS NULL THEN 0 ELSE REPAIR_OUT_SALE_AMOUNT END + ?,")
					.append(" SALE_OUT_COUNT = CASE WHEN SALE_OUT_COUNT IS NULL THEN 0 ELSE SALE_OUT_COUNT END + ?,")
					.append(" SALE_OUT_COST_AMOUNT = CASE WHEN SALE_OUT_COST_AMOUNT IS NULL THEN 0 ELSE SALE_OUT_COST_AMOUNT END + ?,")
					.append(" SALE_OUT_SALE_AMOUNT = CASE WHEN SALE_OUT_SALE_AMOUNT IS NULL THEN 0 ELSE SALE_OUT_SALE_AMOUNT END + ?,")
					.append(" INNER_OUT_COUNT = CASE WHEN INNER_OUT_COUNT IS NULL THEN 0 ELSE INNER_OUT_COUNT END + ?,")
					.append(" INNER_OUT_COST_AMOUNT = CASE WHEN INNER_OUT_COST_AMOUNT IS NULL THEN 0 ELSE INNER_OUT_COST_AMOUNT END + ?,")
					.append(" INNER_OUT_SALE_AMOUNT = CASE WHEN INNER_OUT_SALE_AMOUNT IS NULL THEN 0 ELSE INNER_OUT_SALE_AMOUNT END + ?,")
					.append(" ALLOCATE_OUT_COUNT = CASE WHEN ALLOCATE_OUT_COUNT IS NULL THEN 0 ELSE ALLOCATE_OUT_COUNT END + ?,")
					.append(" ALLOCATE_OUT_COST_AMOUNT = CASE WHEN ALLOCATE_OUT_COST_AMOUNT IS NULL THEN 0 ELSE ALLOCATE_OUT_COST_AMOUNT END + ?,")
					.append(" ALLOCATE_OUT_SALE_AMOUNT = CASE WHEN ALLOCATE_OUT_SALE_AMOUNT IS NULL THEN 0 ELSE ALLOCATE_OUT_SALE_AMOUNT END + ?,")
					.append(" OTHER_OUT_COUNT = CASE WHEN OTHER_OUT_COUNT IS NULL THEN 0 ELSE OTHER_OUT_COUNT END + ?,")
					.append(" OTHER_OUT_COST_AMOUNT = CASE WHEN OTHER_OUT_COST_AMOUNT IS NULL THEN 0 ELSE OTHER_OUT_COST_AMOUNT END + ?,")
					.append(" OTHER_OUT_SALE_AMOUNT = CASE WHEN OTHER_OUT_SALE_AMOUNT IS NULL THEN 0 ELSE OTHER_OUT_SALE_AMOUNT END + ?,")
					.append(" LOSS_OUT_COUNT = CASE WHEN LOSS_OUT_COUNT IS NULL THEN 0 ELSE LOSS_OUT_COUNT END + ?,")
					.append(" LOSS_OUT_AMOUNT = CASE WHEN LOSS_OUT_AMOUNT IS NULL THEN 0 ELSE LOSS_OUT_AMOUNT END + ?,")
					.append(" TRANSFER_IN_COUNT = CASE WHEN TRANSFER_IN_COUNT IS NULL THEN 0 ELSE TRANSFER_IN_COUNT END + ?,")
					.append(" TRANSFER_IN_AMOUNT = CASE WHEN TRANSFER_IN_AMOUNT IS NULL THEN 0 ELSE TRANSFER_IN_AMOUNT END + ?,")
					.append(" TRANSFER_OUT_COUNT = CASE WHEN TRANSFER_OUT_COUNT IS NULL THEN 0 ELSE TRANSFER_OUT_COUNT END + ?,")
					.append(" TRANSFER_OUT_COST_AMOUNT = CASE WHEN TRANSFER_OUT_COST_AMOUNT IS NULL THEN 0 ELSE TRANSFER_OUT_COST_AMOUNT END + ?,")
					.append(" UPHOLSTER_OUT_COUNT = CASE WHEN UPHOLSTER_OUT_COUNT IS NULL THEN 0 ELSE UPHOLSTER_OUT_COUNT END + ?,")
					.append(" UPHOLSTER_OUT_COST_AMOUNT = CASE WHEN UPHOLSTER_OUT_COST_AMOUNT IS NULL THEN 0 ELSE UPHOLSTER_OUT_COST_AMOUNT END + ?,")
					.append(" UPHOLSTER_OUT_SALE_AMOUNT = CASE WHEN UPHOLSTER_OUT_SALE_AMOUNT IS NULL THEN 0 ELSE UPHOLSTER_OUT_SALE_AMOUNT END + ?,")
					.append(" CLOSE_QUANTITY =  CASE WHEN CLOSE_QUANTITY IS NULL THEN 0 ELSE CLOSE_QUANTITY  END + ?-?,")
					.append(" CLOSE_PRICE = ?,")
					.append(" CLOSE_AMOUNT =  CASE WHEN CLOSE_AMOUNT IS NULL THEN 0 ELSE CLOSE_AMOUNT  END + ?-?,");
			StringBuffer conditions = new StringBuffer();
			conditions.append(" WHERE DEALER_CODE = ?").append(" AND REPORT_YEAR = ?").append(" AND REPORT_MONTH = ?")
					.append(" AND STORAGE_CODE = ?").append(" AND PART_BATCH_NO = ?").append(" AND PART_NO = ?")
					.append(" AND D_KEY = ").append(CommonConstants.D_KEY);
			TtPartPeriodReportPO.update(updates.toString(), conditions.toString(),
					Math.round(db.getFloat("IN_QUANTITY") * 100) * 0.01,
					Math.round(db.getDouble("STOCK_IN_AMOUNT") * 100) * 0.01,
					Math.round(db.getFloat("BUY_IN_COUNT") * 100) * 0.01,
					Math.round(db.getDouble("BUY_IN_AMOUNT") * 100) * 0.01,
					Math.round(db.getFloat("ALLOCATE_IN_COUNT") * 100) * 0.01,
					Math.round(db.getDouble("ALLOCATE_IN_AMOUNT") * 100) * 0.01,
					Math.round(db.getFloat("OTHER_IN_COUNT") * 100) * 0.01,
					Math.round(db.getDouble("OTHER_IN_AMOUNT") * 100) * 0.01,
					Math.round(db.getFloat("PROFIT_IN_COUNT") * 100) * 0.01,
					Math.round(db.getDouble("PROFIT_IN_AMOUNT") * 100) * 0.01,
					Math.round(db.getFloat("OUT_QUANTITY") * 100) * 0.01,
					Math.round(db.getDouble("STOCK_OUT_COST_AMOUNT") * 100) * 0.01,
					Math.round(db.getDouble("OUT_AMOUNT") * 100) * 0.01,
					Math.round(db.getFloat("REPAIR_OUT_COUNT") * 100) * 0.01,
					Math.round(db.getDouble("REPAIR_OUT_COST_AMOUNT") * 100) * 0.01,
					Math.round(db.getDouble("REPAIR_OUT_SALE_AMOUNT") * 100) * 0.01,
					Math.round(db.getFloat("SALE_OUT_COUNT") * 100) * 0.01,
					Math.round(db.getDouble("SALE_OUT_COST_AMOUNT") * 100) * 0.01,
					Math.round(db.getDouble("SALE_OUT_SALE_AMOUNT") * 100) * 0.01,
					Math.round(db.getFloat("INNER_OUT_COUNT") * 100) * 0.01,
					Math.round(db.getDouble("INNER_OUT_COST_AMOUNT") * 100) * 0.01,
					Math.round(db.getDouble("INNER_OUT_SALE_AMOUNT") * 100) * 0.01,
					Math.round(db.getFloat("ALLOCATE_OUT_COUNT") * 100) * 0.01,
					Math.round(db.getDouble("ALLOCATE_OUT_COST_AMOUNT") * 100) * 0.01,
					Math.round(db.getDouble("ALLOCATE_OUT_SALE_AMOUNT") * 100) * 0.01,
					Math.round(db.getFloat("OTHER_OUT_COUNT") * 100) * 0.01,
					Math.round(db.getDouble("OTHER_OUT_COST_AMOUNT") * 100) * 0.01,
					Math.round(db.getDouble("OTHER_OUT_SALE_AMOUNT") * 100) * 0.01,
					Math.round(db.getFloat("LOSS_OUT_COUNT") * 100) * 0.01,
					Math.round(db.getDouble("LOSS_OUT_AMOUNT") * 100) * 0.01,
					Math.round(db.getFloat("TRANSFER_IN_COUNT") * 100) * 0.01,
					Math.round(db.getDouble("TRANSFER_IN_AMOUNT") * 100) * 0.01,
					Math.round(db.getFloat("TRANSFER_OUT_COUNT") * 100) * 0.01,
					Math.round(db.getDouble("TRANSFER_OUT_COST_AMOUNT") * 100) * 0.01,
					Math.round(db.getFloat("UPHOLSTER_OUT_COUNT") * 100) * 0.01,
					Math.round(db.getDouble("UPHOLSTER_OUT_COST_AMOUNT") * 100) * 0.01,
					Math.round(db.getDouble("UPHOLSTER_OUT_SALE_AMOUNT") * 100) * 0.01,
					Math.round(db.getFloat("IN_QUANTITY") * 100) * 0.01,
					Math.round(db.getFloat("OUT_QUANTITY") * 100) * 0.01,
					Math.round(db.getDouble("CLOSE_PRICE") * 10000) * 0.0001,
					Math.round(db.getDouble("STOCK_IN_AMOUNT") * 100) * 0.01,
					Math.round(db.getDouble("STOCK_OUT_COST_AMOUNT") * 100) * 0.01, dealerCode,
					Utility.fullSpaceBuffer2(account.getString("B_YEAR"), 4),
					Utility.fullSpaceBuffer2(account.getString("PERIODS"), 2), storageCode, partBatchNo, partNo);
		}
	}

	private static Double getAddD(Double v1, Double v2) {
		String s1 = "0";
		String s2 = "0";
		if (v1 != null)
			s1 = v1.toString();
		if (v2 != null)
			s2 = v2.toString();
		return Utility.round(new Double(Utility.add(s1, s2)).toString(), 2);
	}

	private static Double getSubD(Double v1, Double v2) {
		String s1 = "0";
		String s2 = "0";
		if (v1 != null)
			s1 = v1.toString();
		if (v2 != null)
			s2 = v2.toString();
		return Utility.round(new Double(Utility.sub(s1, s2)).toString(), 2);
	}

	private static Double getAddF(Float v1, Float v2) {
		String s1 = "0";
		String s2 = "0";
		if (v1 != null)
			s1 = v1.toString();
		if (v2 != null)
			s2 = v2.toString();
		double d = Utility.round(new Double(Utility.add(s1, s2)).toString(), 2);
		return d;
	}

	private static Double getSubF(Double v1, Float v2) {
		String s1 = "0";
		String s2 = "0";
		if (v1 != null)
			s1 = v1.toString();
		if (v2 != null)
			s2 = v2.toString();
		double d = Utility.round(new Double(Utility.sub(s1, s2)).toString(), 2);
		return d;
	}

	@Override
	public List<Map> getAllEnterableActivityInfo(Map<String, String> query) {
		String vinSix = "";
		List<Map> list = new ArrayList<Map>();
		List<Map> list1 = new ArrayList<Map>();
		if (!StringUtils.isNullOrEmpty(query.get("vin"))) {
			if (query.get("vin").length() == 17) {
				vinSix = query.get("vin").substring(11, 17);
			} else {
				throw new ServiceBizException("VIN号不满足17位,请重新选择!");
			}
		}
		// 此处需使用视图，否则会导致子站无法查询主站数据.
		StringBuffer sb = new StringBuffer();
		String sqla = " and vin='" + query.get("vin") + "' ";
		String sqlb = CommonConstants.VT_ACTIVITY_VEHICLE;
		sb.append("SELECT 1 FROM (").append(sqlb).append(") WHERE DEALER_CODE = '")
				.append(FrameworkUtil.getLoginInfo().getDealerCode()).append("' and D_Key= ")
				.append(CommonConstants.D_KEY).append(sqla).append(" limit 1");
		List<Map> findAll = DAOUtil.findAll(sb.toString(), null);
		if (findAll.size() > 0) {
			query.put("isRecallActivity", "");
			query.put("size", "1");
			query.put("vinSix", vinSix);
			list = queryActivityMonitorFilterRoNo(query);
		} else {
			sqla = " and MODEL_CODE='" + query.get("model") + "' AND SERIES_CODE ='" + query.get("series") + "' "
					+ " AND CONFIG_CODE ='" + query.get("configCode") + "' AND '" + vinSix
					+ "' BETWEEN BEGIN_VIN AND END_VIN "
					+ " AND CURRENT TIMESTAMP  BETWEEN MANUFACTURE_DATE_BEGIN AND MANUFACTURE_DATE_END ";
			sqlb = CommonConstants.VT_ACTIVITY_MODEL;
			sb = new StringBuffer();
			sb.append("SELECT 1 FROM (").append(sqlb).append(") WHERE DEALER_CODE = '")
					.append(FrameworkUtil.getLoginInfo().getDealerCode()).append("' and D_Key= ")
					.append(CommonConstants.D_KEY).append(sqla).append(" limit 1");
			List<Map> listModel = Base.findAll(sb.toString());
			if (listModel != null && listModel.size() > 0) {
				query.put("isRecallActivity", "");
				query.put("size", "2");
				query.put("vinSix", vinSix);
				list = queryActivityMonitorFilterRoNo(query);
			} else {
				query.put("isRecallActivity", "");
				query.put("size", "3");
				query.put("vinSix", vinSix);
				list = queryActivityMonitorFilterRoNo(query);
			}
		}
		// 根据开关来判断是否执行程序9009
		String defautParam9009 = Utility.getDefaultValue(String.valueOf(CommonConstants.ZHAOHUI_SERVICE_IS_OPEN));
		// String defautParam9010 =
		// Utility.getDefaultValue(String.valueOf(CommonConstants.ZHAOHUI_SERVICE_ITEM_IS_OPEN));
		if (list != null && list.size() > 0) {
			for (Map map : list) {
				String activityCode = map.get("ACTIVITY_CODE").toString();
				Integer isrecallService = Integer.parseInt(map.get("IS_RECALL_SERVICE").toString());
				// 从召回活动接口来的活动通过查询联邦上端表的数据，是否符合可以参加的活动
				if (Utility.testString(isrecallService) && 12781001 == isrecallService
						&& Utility.testString(defautParam9009)
						&& DictCodeConstants.DICT_IS_YES.equals(defautParam9009)) {
					if (Utility.testString(defautParam9009) && DictCodeConstants.DICT_IS_YES.equals(defautParam9009)) {
						sqla = " a.vin='" + query.get("vin") + "' and  b.RECALL_NO='" + activityCode + "' ";
						sb = new StringBuffer();
						sb.append(
								"SELECT 1 FROM TT_RECALL_VEHICLE a left join  TT_RECALL_SERVICE b on a.RECALL_ID=b.RECALL_ID  WHERE ")
								.append(sqla).append(" limit 1");
						List<Map> vehicleList = Base.findAll(sb.toString());
						// 是否有活动车辆，有则加入
						if (vehicleList != null && vehicleList.size() > 0) {
							list1.add(map);
						}
					}
				} else {
					list1.add(map);
				}
			}
		}
		return list1;
	}

	/**
	 * 查询服务活动主数据
	 * 
	 * @param query
	 * @return
	 */
	public List<Map> queryActivityMonitorFilterRoNo(Map<String, String> query) {
		StringBuffer sb = new StringBuffer();
		sb.append(
				"select 12781002 AS IS_SELECT,a.IS_REPEAT_ATTEND,a.IS_PART_ACTIVITY,A.ACTIVITY_KIND,A.PARTS_IS_MODIFY,a.IS_RECALL_ACTIVITY,a.DEALER_CODE, a.ACTIVITY_CODE, a.ACTIVITY_NAME, a.ACTIVITY_TYPE, a.BEGIN_DATE, a.END_DATE, a.RELEASE_DATE, ")
				.append("a.RELEASE_TAG, a.LABOUR_AMOUNT, a.ADD_ITEM_AMOUNT,a.FROM_ENTITY, a.DOWN_TAG, a.DOWN_TIMESTAMP, a.REPAIR_PART_AMOUNT, a.ACTIVITY_AMOUNT, a.IS_VALID,a.VER , ")
				.append("a.BRAND,a.SERIES,a.MODEL,a.MILEAGE_BEGIN,a.MILEAGE_END,a.MEMBER_LEVEL_ALLOWED,'' AS LABOUR_CODE,'' AS PART_CODE, A.ACTIVITY_PROPERTY,A.IS_RECALL_SERVICE")
				.append(" from( select distinct ")
				.append("a.IS_REPEAT_ATTEND,a.IS_PART_ACTIVITY,A.ACTIVITY_KIND,A.PARTS_IS_MODIFY,a.DEALER_CODE, a.ACTIVITY_CODE, a.ACTIVITY_NAME, a.ACTIVITY_TYPE, a.BEGIN_DATE, a.END_DATE, a.RELEASE_DATE, ")
				.append("a.RELEASE_TAG, a.LABOUR_AMOUNT, a.ADD_ITEM_AMOUNT,a.FROM_ENTITY, a.DOWN_TAG, a.DOWN_TIMESTAMP, a.REPAIR_PART_AMOUNT, a.ACTIVITY_AMOUNT, a.IS_VALID,a.VER ,")
				.append("coalesce(a.BRAND,'') as BRAND,a.IS_RECALL_ACTIVITY ")
				.append(",coalesce(a.SERIES,'') as SERIES,coalesce(a.MODEL,'') as MODEL,coalesce(a.MILEAGE_BEGIN,0) as MILEAGE_BEGIN,coalesce(a.MILEAGE_END,0) as MILEAGE_END,")
				.append("a.SALES_DATE_BEGIN,a.SALES_DATE_END,A.MEMBER_LEVEL_ALLOWED,A.MODEL_YEAR,A.MAINTAIN_BEGIN_DAY,A.MAINTAIN_END_DAY,A.CONFIG_CODE, A.ACTIVITY_PROPERTY,A.IS_RECALL_SERVICE")
				.append("from VT_ACTIVITY A  where  a.DEALER_CODE='")
				.append(FrameworkUtil.getLoginInfo().getDealerCode()).append("' and IS_PART_ACTIVITY=")
				.append(DictCodeConstants.DICT_IS_NO).append(" AND RELEASE_TAG = ")
				.append(DictCodeConstants.DICT_ACTIVITY_RELEASE_TAG_RELEASED).append(" AND IS_VALID =")
				.append(DictCodeConstants.DICT_IS_YES).append(" AND ? BETWEEN BEGIN_DATE AND END_DATE ")
				.append(" AND ( a.VEHICLE_PURPOSE is null OR a.VEHICLE_PURPOSE = 0 ")
				.append(" OR   EXISTS ( SELECT 1 FROM VM_VEHICLE v WHERE VIN = '").append(query.get("vin"))
				.append("' AND v.VEHICLE_PURPOSE = a.VEHICLE_PURPOSE ) ) ");
		if ((Integer.parseInt(query.get("size")) == 2) || (Integer.parseInt(query.get("size")) == 3)) {
			sb.append(
					" and ( not exists (select 1 from VT_ACTIVITY_MODEL b where a.DEALER_CODE = b.DEALER_CODE and a.activity_Code = b.activity_code ")
					.append(" and b.DEALER_CODE='").append(FrameworkUtil.getLoginInfo().getDealerCode())
					.append("')  or exists (select 1 from TT_ACTIVITY_MODEL b,VT_ACTIVITY A where a.DEALER_CODE = b.DEALER_CODE and  a.activity_Code = b.activity_code ")
					.append(" and b.DEALER_CODE='").append(FrameworkUtil.getLoginInfo().getDealerCode())
					.append("' and b.MODEL_CODE='").append(query.get("model")).append("' AND B.SERIES_CODE  = '")
					.append(query.get("series")).append("' AND ('").append(query.get("vinSix"))
					.append("' BETWEEN B.BEGIN_VIN AND B.END_VIN OR (B.BEGIN_VIN IS NULL AND B.END_VIN IS NULL) )")
					.append(" AND (CURRENT TIMESTAMP  >= B.MANUFACTURE_DATE_BEGIN AND CURRENT TIMESTAMP <= B.MANUFACTURE_DATE_END OR (B.MANUFACTURE_DATE_BEGIN IS NULL AND B.MANUFACTURE_DATE_END IS NULL))  ))");
		}
		if ((Integer.parseInt(query.get("size")) == 1) || (Integer.parseInt(query.get("size")) == 3)) {
			sb.append(
					" and ( not exists (select 1 from VT_ACTIVITY_VEHICLE c where a.DEALER_CODE = c.DEALER_CODE and   a.activity_Code = c.activity_code ")
					.append(" and c.DEALER_CODE='").append(FrameworkUtil.getLoginInfo().getDealerCode()).append("') ")
					.append(" or exists (select 1 from VT_ACTIVITY_VEHICLE c where a.DEALER_CODE = c.DEALER_CODE and  a.activity_Code = c.activity_code ")
					.append(" and c.DEALER_CODE='").append(FrameworkUtil.getLoginInfo().getDealerCode())
					.append("' and c.VIN = '").append(query.get("vin")).append("') )");
		}
		sb.append(" and (A.MODEL_YEAR='' or A.MODEL_YEAR IS NULL OR EXISTS(SELECT 1 FROM VM_VEHICLE V WHERE VIN='")
				.append(query.get("vin")).append("' AND V.MODEL_YEAR=A.MODEL_YEAR)) ").append(")a where ")
				.append("((BRAND ='' or BRAND='").append(query.get("brand")).append("')and( SERIES ='' or SERIES='")
				.append(query.get("series")).append("')and( CONFIG_CODE =''  OR CONFIG_CODE IS NULL  or CONFIG_CODE='")
				.append(query.get("configCode")).append("')and(MODEL ='' or MODEL='").append(query.get("model"))
				.append("')) and (((MILEAGE_BEGIN  =0 and MILEAGE_END  =0) or ( ");
		if (!StringUtils.isNullOrEmpty(query.get("mileage"))) {
			sb.append(" MILEAGE_BEGIN<=").append(query.get("mileage")).append(" and MILEAGE_END>=")
					.append(query.get("mileage")).append(" ");
		} else {
			sb.append(" 1=1 ");
		}
		sb.append(" )) ");
		String sqlSalesdate = "";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		if (!StringUtils.isNullOrEmpty(query.get("salesdate"))) {
			try {
				String dateString = formatter.format(Utility.parseString2Date(query.get("salesdate"), "yyyy-MM-dd"));
				sqlSalesdate = " or (SALES_DATE_BEGIN<=timestamp('" + dateString + "')"
						+ " and SALES_DATE_END>=timestamp('" + dateString + "')";
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		sb.append(
				" and (((A.MAINTAIN_BEGIN_DAY IS NULL OR A.MAINTAIN_BEGIN_DAY=0.00) AND (A.MAINTAIN_END_DAY IS NULL OR A.MAINTAIN_END_DAY=0.00)) OR ")
				.append(" EXISTS(SELECT 1 FROM VM_VEHICLE V WHERE VIN='").append(query.get("vin")).append("' ")
				.append(" AND V.SALES_DATE IS NOT NULL AND DMSTIMEDIFF(8,CURRENT TIMESTAMP,V.SALES_DATE) >= A.MAINTAIN_BEGIN_DAY*24 AND DMSTIMEDIFF(8,CURRENT TIMESTAMP,V.SALES_DATE) <=A.MAINTAIN_END_DAY*24))) ")
				.append(" and(SALES_DATE_BEGIN is null or SALES_DATE_END is null ").append(sqlSalesdate)
				.append(" ) and ( (").append(" IS_REPEAT_ATTEND=").append(DictCodeConstants.DICT_IS_YES)
				.append(") or (not exists(select 1 from TT_REPAIR_ORDER AA left join TT_RO_LABOUR BB on AA.DEALER_CODE=BB.DEALER_CODE and AA.RO_NO=BB.RO_NO and BB.activity_code<>''   where bb.ACTIVITY_CODE=a.ACTIVITY_CODE  and AA.DEALER_CODE='")
				.append(FrameworkUtil.getLoginInfo().getDealerCode()).append("' and VIN='").append(query.get("vin"))
				.append("' and IS_ACTIVITY =").append(DictCodeConstants.DICT_IS_YES);
		if (!StringUtils.isNullOrEmpty(query.get("roNo"))) {
			sb.append(" and AA.RO_NO<> '").append(query.get("roNo")).append("'");
		}
		sb.append(" UNION ALL select 1 from TT_REPAIR_ORDER  AA")
				.append(" left join TT_RO_REPAIR_PART BB on AA.DEALER_CODE=BB.DEALER_CODE and AA.RO_NO=BB.RO_NO and BB.activity_code<>'' ")
				.append("  where bb.ACTIVITY_CODE=a.ACTIVITY_CODE and").append(" AA.DEALER_CODE='")
				.append(FrameworkUtil.getLoginInfo().getDealerCode()).append("' and VIN='").append(query.get("vin"))
				.append("' and IS_ACTIVITY =").append(DictCodeConstants.DICT_IS_YES);
		if (!StringUtils.isNullOrEmpty(query.get("roNo"))) {
			sb.append(" and AA.RO_NO<> '").append(query.get("roNo")).append("'");
		}
		sb.append(" UNION ALL ").append(" select 1 from TT_REPAIR_ORDER  AA")
				.append(" left join TT_RO_ADD_ITEM BB on AA.DEALER_CODE=BB.DEALER_CODE and AA.RO_NO=BB.RO_NO and BB.activity_code<>'' ")
				.append(" where bb.ACTIVITY_CODE=a.ACTIVITY_CODE and").append(" AA.DEALER_CODE='")
				.append(FrameworkUtil.getLoginInfo().getDealerCode()).append("' and VIN='").append(query.get("vin"))
				.append("' ");
		if (!StringUtils.isNullOrEmpty(query.get("roNo"))) {
			sb.append(" and AA.RO_NO<> '").append(query.get("roNo")).append("'");
		}
		sb.append(") ) )").append(" and NOT EXISTS( select 1 from VT_ACTIVITY_RESULT WHERE VIN='")
				.append(query.get("vin")).append("' and activity_code=a.activity_code) ");
		if (!StringUtils.isNullOrEmpty(query.get("cardTypeCode"))) {
			sb.append(" and ")
					.append(" ( (a.MEMBER_LEVEL_ALLOWED IS NOT NULL AND a.MEMBER_LEVEL_ALLOWED!='' AND a.MEMBER_LEVEL_ALLOWED LIKE '%")
					.append(query.get("cardTypeCode")).append("%' ) ")
					.append(" OR (a.MEMBER_LEVEL_ALLOWED IS NULL OR a.MEMBER_LEVEL_ALLOWED='')   )   ");
		} else {
			sb.append(" and (a.MEMBER_LEVEL_ALLOWED IS NULL OR a.MEMBER_LEVEL_ALLOWED='') ");
		}
		if (!StringUtils.isNullOrEmpty(query.get("isRecallActivity"))) {
			sb.append(" and a.IS_RECALL_ACTIVITY=").append(query.get("isRecallActivity")).append(" ");
		}
		List param = new ArrayList();
		param.add(new Date());
		return DAOUtil.findAll(sb.toString(), param);
	}

	@Override
	public void btn3BaoAccredit(Map<String, String> param) throws ServiceBizException {
		// 账号密码校验
		checkPassword(param);
		String sql = "select * from tm_user_ctrl where CTRL_CODE=? and USER_ID=?";
		List<Object> queryParam = new ArrayList<>();
		queryParam.add("10470000");
		queryParam.add(param.get("username"));
		List<Map> powerList = DAOUtil.findAll(sql, queryParam);
		if (CommonUtils.isNullOrEmpty(powerList) || powerList.size() == 0) {
			throw new ServiceBizException("没有三包预警解锁权限");
		}
	}

	/**
	 * 校验登录
	 * 
	 * @param param
	 */
	public void checkPassword(Map<String, String> param) {
		String password = param.get("password");
		StringBuilder sql = new StringBuilder(
				"SELECT tu.USER_CODE,tdb.DEALER_CODE,te.GENDER,te.EMPLOYEE_NO,tu.PASSWORD,te.EMPLOYEE_NAME,te.MOBILE, tdb.DEALER_SHORTNAME, tdb.DEALER_NAME, tdb.DEALER_ID, tu.USER_ID,tu.USER_CODE,org.ORG_CODE,org.ORG_NAME,org.ORGDEPT_ID FROM tm_employee te INNER JOIN tm_user tu ON te.EMPLOYEE_NO = tu.EMPLOYEE_NO and te.DEALER_CODE = tu.DEALER_CODE LEFT  JOIN tm_dealer_basicinfo tdb ON tdb.DEALER_CODE=te.DEALER_CODE left join TM_ORGANIZATION org on org.ORG_CODE=tu.ORG_CODE and tdb.DEALER_CODE = org.DEALER_CODE WHERE te.IS_VALID='"
						+ DictCodeConstants.STATUS_IS_YES + "' AND tu.USER_STATUS='"
						+ DictCodeConstants.DICT_IN_USE_START + "'");
		List<Object> queryParam = new ArrayList<Object>();
		sql.append("  and tu.USER_CODE  = ?");
		queryParam.add(param.get("username"));
		sql.append(" and tdb.DEALER_CODE = ?");
		queryParam.add(param.get("dealerCode"));
		List<Map> listMap = DAOUtil.findAll(sql.toString(), queryParam);
		// 如果查询通过
		if (!CommonUtils.isNullOrEmpty(listMap)) {
			Map userInfo = listMap.get(0);
			String passwordMD5 = (String) userInfo.get("PASSWORD");// 密码
			boolean validation = MD5Util.validPassword(password, passwordMD5);
			if (validation == false) {
				throw new ServiceBizException("密码不正确");
			}
			System.out.println("输入密码====" + password);
		}
	}

	@Override
	public Map getTripleInfo(Map<String, String> param) {
		Map result = new HashMap();
		String[] partNo = param.get("partNo").split(",");
		String[] activityCode = param.get("activityCode").split(",");
		String alarm = "3"; // 默认三包不报警
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		String ageLimit = String.valueOf(Integer.valueOf(Utility.getDefaultValue("1903")) * 365);// 三包年限
		String mileage = Utility.getDefaultValue("1904");// 三包里程（公里）
		String sql = "SELECT 1 FROM TT_THREEPACK_NOVEHICLE_DCS WHERE VIN= ? ";
		List<Map> findAll = Base.findAll(sql, param.get("vin"));
		if (findAll.size() > 0) {
			// 如果查到了说明不是三包车
			result.put("tripleResult", "3");
		} else {
			// 如果查不到说明是三包车（因为查的是非三包车的表）
			VehiclePO vepo = VehiclePO.findByCompositeKeys(param.get("vin"), dealerCode);
			if (vepo == null) {
				result.put("tripleResult", "3");
			}
			// 如果该车辆车辆性质“公司”客户，车辆用途包括出租车，警车，租赁公司的 11931004 3 5
			if (vepo.getInteger("VEHICLE_PURPOSE") != null
					&& (vepo.getInteger("VEHICLE_PURPOSE") == 11931003 || vepo.getInteger("VEHICLE_PURPOSE") == 11931004
							|| vepo.getInteger("VEHICLE_PURPOSE") == 11931005)) {
				result.put("tripleResult", "3");
			}
			if (!StringUtils.isNullOrEmpty(vepo.getString("OWNER_NO"))) {
				CarownerPO owpo = CarownerPO.findByCompositeKeys(vepo.getString("OWNER_NO"), dealerCode);
				if (owpo != null && owpo.getInteger("OWNER_PROPERTY") != null
						&& owpo.getInteger("OWNER_PROPERTY") == 11901001) {
					result.put("tripleResult", "3");
				}
			}

			// 将配件拼接成'a','b','c'这样子的形式方便传参直接拼sql
			String partNoList = "";
			if (partNo != null && partNo.length > 0) {
				for (int i = 0; i < partNo.length; i++) {
					if (activityCode != null && Utility.testString(activityCode[i])) {
						TtActivityPO activityPO = TtActivityPO.findFirst(
								"DEALER_CODE = ? AND ACTIVITY_CODE = ? AND D_KEY = ? AND DOWN_TAG = ?", dealerCode,
								activityCode[i], CommonConstants.D_KEY, DictCodeConstants.IS_YES);
						if (activityPO != null && activityPO.getString("ACTIVITY_KIND") != null
								&& (DictCodeConstants.DICT_ACTIVITY_KIND_TSB
										.equals(activityPO.getString("ACTIVITY_KIND"))
										|| DictCodeConstants.DICT_ACTIVITY_KIND_CSN
												.equals(activityPO.getString("ACTIVITY_KIND"))
										|| DictCodeConstants.DICT_ACTIVITY_KIND_RETURN
												.equals(activityPO.getString("ACTIVITY_KIND")))) {
							continue;
						}
					}
					if (partNoList.equals("")) {
						partNoList = partNoList + "'" + partNo[i] + "'";
					} else {
						partNoList = partNoList + ",'" + partNo[i] + "'";
					}
				}
			}
			// 优化后的sql分为2步：1、先判断VIN是否符合三包条件，如果符合 再执行后面的查询sql，否则直接跳过
			StringBuffer sb = new StringBuffer(" SELECT 1 FROM TM_cgcsl_VEHICLE VHL WHERE 1=1 ");
			if (!StringUtils.isNullOrEmpty(param.get("vin"))) {
				sb.append(" AND VHL.VIN='").append(param.get("vin")).append("' ");
			}
			if (!StringUtils.isNullOrEmpty(param.get("vin"))) {
				sb.append(" AND coalesce (VHL.MILEAGE, 0) <= ").append(mileage).append(" ");
			}
			if (!StringUtils.isNullOrEmpty(param.get("vin"))) {
				sb.append(
						" AND 730 > (DAYS (CURRENT DATE) - DAYS (VHL.PURCHASE_DATE))  AND to_char (VHL.PURCHASE_DATE, 'YYYY-MM-DD') > '2013-07-31' ");
			}
			List<Map> checkList = Base.findAll(sb.toString());
			if (checkList != null && checkList.size() > 0) {
				List<Map> list = new ArrayList<Map>();
				if (Utility.testString(partNoList)) {
					list = queryTripleInfo(param.get("vin"), partNoList, ageLimit, mileage);
				} else {
					// 如果工单中没有添加配件或者添加的配件不在预警范围内(服务活动)则只显示时间相关的三包预警结果
					list = queryTripleTimeInfo(param.get("vin"), ageLimit, mileage);
				}
				if (list != null && list.size() > 0) {
					// 如果有预警信息则返回
					for (Map map : list) {
						// 循环判断有没有报警信息
						if ((Long) map.get("WARNTIMES") >= (Long) map.get("WARN_STANDARD")) {
							alarm = "1";// 标示三包报警
							break;
						} else {
							alarm = "0";// 未达预警
						}
					}
				} else {
					alarm = "0";// 未达预警
				}
				if (alarm.equals("1")) {
					//// 标示三包预警
					List<Map> listAccredit = queryEntityTriple();
					Integer Accredit = (Integer) listAccredit.get(0).get("ACURA_GHAS_TYPE");
					if (Accredit != null && Accredit.equals(10791001)) {
						// 授权经销商
						result.put("tripleResult", "1");
					} else {
						// 非授权经销商
						result.put("tripleResult", "2");
					}
					ttTripleInfo = list;
					result.put("ttTripleInfo", list);
				} else if (alarm.equals("0")) {
					// 未达预警
					result.put("tripleResult", "0");
				} else {
					//// 不需要预警
					result.put("tripleResult", "3");
				}
			}
		}
		return result;
	}

	/**
	 * 校验经销商是不是授权经销商（三包）
	 * 
	 * @return
	 */
	public List<Map> queryEntityTriple() {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT td.ACURA_GHAS_TYPE ")
				.append(" FROM TT_DEALER_RELATION tdr, tm_cgcsl_company tc, tm_cgcsl_dealer td ")
				.append(" WHERE tdr.DCS_CODE = tc.COMPANY_CODE ").append(" AND tc.COMPANY_ID = td.COMPANY_ID ")
				.append(" AND td.DEALER_TYPE = 10771002 ").append(" AND tdr.DMS_CODE = '")
				.append(FrameworkUtil.getLoginInfo().getDealerCode()).append("' ");
		return Base.findAll(sb.toString());
	}

	/**
	 * 根据VIN 三包里程、三包日期获取三包预警的日期信息
	 * 
	 * @param vin
	 * @param ageLimit
	 * @param mileage
	 * @return
	 */
	public List<Map> queryTripleTimeInfo(String vin, String ageLimit, String mileage) {
		StringBuffer sb = new StringBuffer();
		sb.append(
				" SELECT  '' AS PART_CODE, 0 AS ptmum , W.WARN_ITEM_NAME,COALESCE(W.WARN_TIMES,0) + 1 AS warntimes,PA.WARN_STANDARD,PA.LEGAL_STANDARD, COALESCE(PA.YELLOW_STANDARD,0) YELLOW_STANDARD, COALESCE(PA.ORANGE_STANDARD,0) ORANGE_STANDARD, COALESCE(PA.RED_STANDARD,0) RED_STANDARD")
				.append(" FROM   tm_cgcsl_vehicle_DCS E,  TT_THREEPACK_WARN_DCS W,  TT_THREEPACK_WARN_PARA_DCS PA ")
				.append(" WHERE  E.VIN = W.VIN  AND W.WARN_ITEM_NO = PA.ITEM_NO  AND W.WARN_ITEM_NO = '500'  AND E.VIN NOT IN  ( ")
				.append(" SELECT  ne.VIN  FROM  TT_THREEPACK_NOVEHICLE_DCS ne   WHERE  ne.IS_DEL = 0 ")
				.append(" )  AND E.VIN = '").append(vin)
				.append("'  and (COALESCE(W.WARN_TIMES,0) + 1)>=COALESCE(PA.YELLOW_STANDARD,0) ");
		return Base.findAll(sb.toString());
	}

	/**
	 * 工单保存时校验三包信息
	 * 
	 * @param vin
	 * @param partList
	 * @param ageLimit
	 * @param mileage
	 * @return
	 */
	public List<Map> queryTripleInfo(String vin, String partList, String ageLimit, String mileage) {
		StringBuffer sb = new StringBuffer();
		sb.append("WITH ")
				.append(" warn ( VIN, PART_CODE, WARN_ITEM_NAME, WARN_ITEMS, WARN_STANDARD, LEGAL_STANDARD,YELLOW_STANDARD,ORANGE_STANDARD,RED_STANDARD ) AS ")
				.append(" ( ");
		sb.append("SELECT DISTINCT '").append(vin)
				.append("' as VIN, Y.PART_CODE, CASE WHEN Y.ITEM_NAME IS NULL THEN '' ELSE Y.ITEM_NAME END WARN_ITEM_NAME, ")
				.append("CASE WHEN X.WARN_TIMES IS NULL THEN 0 ELSE X.WARN_TIMES END WARN_TIMES, ")
				.append("PA.WARN_STANDARD, ").append(" PA.LEGAL_STANDARD, ")
				.append(" COALESCE(PA.YELLOW_STANDARD,0) YELLOW_STANDARD,")
				.append("COALESCE(PA.ORANGE_STANDARD,0) ORANGE_STANDARD,")
				.append(" COALESCE(PA.RED_STANDARD,0) RED_STANDARD").append(" FROM ")
				.append("(SELECT PTR.PART_CODE, ITEM.ITEM_NO, ITEM.ITEM_NAME,PTR.ITEM_ID  FROM    TT_THREEPACK_PTITEM_RELATION_DCS PTR  LEFT JOIN TT_THREEPACK_ITEM_DCS ITEM ON PTR.ITEM_ID = ITEM.ID ");
		if (Utility.testString(partList)) {
			sb.append(" WHERE PTR.PART_CODE IN (" + partList + ") ");
		}
		sb.append(" AND PTR.IS_DEL = 0         ) Y ").append(" LEFT JOIN  TT_WR_REPAIR_PART_dcs PR  ")
				.append("  ON   Y.PART_CODE = PR.PART_CODE ")
				.append(" LEFT JOIN TT_THREEPACK_WARN_PARA_DCS PA ON Y.ITEM_ID = PA.ITEM_ID ")
				.append(" LEFT JOIN (SELECT E.vin,W.WARN_TIMES,W.WARN_ITEM_NO,W.WARN_ITEM_NAME ")
				.append("  FROM    TM_cgcsl_VEHICLE_DCS E LEFT JOIN TT_THREEPACK_WARN_DCS W  ON E.VIN = W.VIN ")
				.append(" WHERE 1=1 ").append(" AND E.VIN = '").append(vin).append("' ) X ")
				.append("   ON PA.ITEM_NO = X.WARN_ITEM_NO").append(" WHERE 1=1  ");
		if (Utility.testString(partList)) {
			sb.append("  and Y.PART_CODE IN (" + partList + ") ");
		}
		sb.append(" AND Y.PART_CODE NOT IN ( ").append(" AND Y.PART_CODE NOT IN  nt.PART_CODE ")
				.append(" FROM  TT_THREEPACK_NOPART_DCS nt ").append(" WHERE   nt.is_del = 0 )");
		sb.append(" UNION ALL  SELECT  E.VIN,  PR.PART_CODE, ")
				.append(" W.WARN_ITEM_NAME,  W.WARN_TIMES, PA.WARN_STANDARD, PA.LEGAL_STANDARD, ")
				.append(" COALESCE(PA.YELLOW_STANDARD,0) YELLOW_STANDARD,")
				.append(" COALESCE(PA.ORANGE_STANDARD,0) ORANGE_STANDARD,")
				.append(" COALESCE(PA.RED_STANDARD,0) RED_STANDARD FROM ")
				.append(" tm_cgcsl_vehicle_DCS E, TT_THREEPACK_WARN_DCS W, ")
				.append(" TT_PT_PART_BASE_DCS PR,  TT_THREEPACK_WARN_PARA_DCS PA  WHERE ")
				.append("  E.VIN = W.VIN  AND W.WARN_ITEM_NO = PA.ITEM_NO ").append("  AND W.WARN_ITEM_NO = '400' ");
		if (Utility.testString(partList)) {
			sb.append(" AND PR.PART_CODE IN (" + partList + ") ");
		}
		sb.append("  AND PR.PART_CODE NOT IN  (    SELECT     nt.PART_CODE     FROM ")
				.append("   TT_THREEPACK_NOPART_DCS nt  WHERE   nt.IS_DEL = 0 ) ")
				.append("  AND PR.PART_CODE NOT IN  (   SELECT  PN.PART_CODE   FROM  TT_THREEPACK_PTITEM_RELATION_DCS PN  WHERE  PN.IS_DEL = 0 ")
				.append(" )  AND E.VIN NOT IN  ( ")
				.append(" SELECT  ne.VIN  FROM  TT_THREEPACK_NOVEHICLE_DCS ne  WHERE  ne.IS_DEL = 0   ) ")
				.append(" AND E.VIN = '").append(vin)
				.append("'  UNION ALL  SELECT   E.VIN, '' AS PART_CODE,   W.WARN_ITEM_NAME,   W.WARN_TIMES,")
				.append(" PA.WARN_STANDARD, PA.LEGAL_STANDARD,   COALESCE(PA.YELLOW_STANDARD,0) YELLOW_STANDARD,")
				.append("  COALESCE(PA.ORANGE_STANDARD,0) ORANGE_STANDARD,")
				.append(" COALESCE(PA.RED_STANDARD,0) RED_STANDARD FROM ")
				.append(" tm_cgcsl_vehicle_DCS E, TT_THREEPACK_WARN_DCS W,").append(" TT_THREEPACK_WARN_PARA_DCS PA ")
				.append(" WHERE   E.VIN = W.VIN ").append(" AND W.WARN_ITEM_NO = PA.ITEM_NO ")
				.append(" AND W.WARN_ITEM_NO = '500' ").append(" AND E.VIN NOT IN ( ")
				.append(" SELECT  ne.VIN  FROM   TT_THREEPACK_NOVEHICLE_DCS ne ").append(" WHERE  ne.IS_DEL = 0  ) ")
				.append(" AND E.VIN = '").append(vin).append("'  ) SELECT  warn.PART_CODE, ")
				.append(" COALESCE(repair.PTMUN,0) + 1 AS ptmum, ").append(" warn.WARN_ITEM_NAME,")
				.append(" COALESCE(warn.WARN_ITEMS,0) + 1 AS warntimes,").append(" warn.WARN_STANDARD, ")
				.append(" warn.LEGAL_STANDARD, ").append(" warn.YELLOW_STANDARD,").append(" warn.ORANGE_STANDARD,")
				.append(" warn.RED_STANDARD ").append("FROM  warn LEFT JOIN  (  SELECT  wr.VIN,  pt.part_code,")
				.append("  COUNT (pt.PART_CODE) AS ptmun ")
				.append(" FROM  TM_CGCSL_VEHICLE_DCS vhl,   TT_WR_REPAIR_DCS wr,  tt_wr_repair_part_DCS pt   WHERE ")
				.append(" vhl.VIN = wr.vin ").append(" AND wr.REPAIR_ID = pt.REPAIR_ID ")
				.append("  AND wr.IS_CLAIM = 10041001 ").append(" AND wr.ORDER_TYPE =40041002  AND wr.vin = '")
				.append(vin).append("'  AND PT.ITEM_NO IS NOT NULL ")
				.append(" AND pt.PART_CODE NOT IN (  SELECT ACTPART.PART_CODE ")
				.append(" FROM TT_WR_ACTIVITY_DCS ACT, TT_WR_ACTIVITY_PART_DCS ACTPART ")
				.append(" WHERE ACT.ACTIVITY_ID = ACTPART.ACTIVITY_ID ")
				.append(" AND ACT.ACTIVITY_CODE = pt.ACTIVITY_CODE ")
				.append(" AND ACT.IS_DEL = 0  AND (ACT.ACTIVITY_TYPE = '40151002' ")
				.append(" OR ACT.ACTIVITY_TYPE = '40151003' ").append(" OR ACT.ACTIVITY_TYPE = '40151004')) ")
				.append(" AND pt.PART_CODE NOT IN ( ").append(" SELECT nt.PART_CODE ")
				.append(" FROM TT_THREEPACK_NOPART_DCS nt ").append(" WHERE nt.is_del = 0) ")
				.append(" AND vhl.VIN NOT IN (  SELECT ne.VIN").append("  FROM TT_THREEPACK_NOVEHICLE_DCS ne ")
				.append(" WHERE ne.IS_DEL = 0) ").append(" GROUP BY ").append(" wr.VIN,  pt.PART_CODE ")
				.append(" ) AS repair ").append("ON  repair.PART_CODE = warn.PART_CODE ")
				.append("AND repair.VIN = warn.vin ");
		sb.append(" WHERE (COALESCE(warn.WARN_ITEMS,0) + 1)>=COALESCE(warn.YELLOW_STANDARD,0)");
		return Base.findAll(sb.toString());
	}

	@Override
	public void occurInsuranceAbout(Map<String, String> param) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append(" select A.* from TT_OCCUR_INSURANCE_REGISTER A where A.DEALER_CODE=? ");
		sql.append(" and A.VIN=?"); // 需求变了 要都查出来，已关联工单的是默认勾上
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		params.add(dealerCode);
		params.add(param.get("vin"));
		if ((!StringUtils.isNullOrEmpty(param.get("RONO")))) {
			sql.append(" and (A.RO_NO=? or A.ro_no is null or a.ro_no='' )");
			params.add(param.get("RONO"));
		} else {
			sql.append(" and (A.ro_no is null or a.ro_no='' )");
		}
		List<Map> findAll = DAOUtil.findAll(sql.toString(), params);
		String isRepairOrder = "";
		if (findAll.size() > 0
				|| (!StringUtils.isNullOrEmpty(param.get("no2")) && StringUtils.isNullOrEmpty(param.get("no1")))) {
			for (Map map : findAll) {
				map.put("TRACE_STATUS", "16061003");
				map.put("DEAL_STATUS", "16071001");
			}
			isRepairOrder = "Y";// 传一个标志 是工单界面的修改
		}
		// 如果是工单界面的保存，先把原来关联过的单子的状态 重置回去，再把这次关联的改成次工单的
		if (Utility.testString(isRepairOrder)) {
			TtOccurInsuranceRegisterPO registerNew = TtOccurInsuranceRegisterPO.findFirst(
					"VIN = ? AND RO_NO = ? AND DEALER_CPDE = ? AND D_KEY = ?", param.get("vin"), param.get("roNo"),
					FrameworkUtil.getLoginInfo().getDealerCode(), CommonConstants.D_KEY);
			if (!StringUtils.isNullOrEmpty(registerNew)) {
				registerNew.setString("RO_NO", "");
				registerNew.setInteger("TRACE_STATUS",
						Integer.parseInt(DictCodeConstants.DICT_OUT_DANGER_TRACE_STATUS_TREATING));
				registerNew.saveIt();
			}
		}
		if (ckeckFieldNotNull2(findAll, "OCCUR_INSURANCE_NO")) {
			logger.debug("执行更新操作");
			for (Map map : findAll) {
				if (!StringUtils.isNullOrEmpty(map.get("OCCUR_INSURANCE_NO"))) {
					TtOccurInsuranceRegisterPO register = TtOccurInsuranceRegisterPO.findByCompositeKeys(
							map.get("OCCUR_INSURANCE_NO"), FrameworkUtil.getLoginInfo().getDealerCode());
					if (!StringUtils.isNullOrEmpty(register)) {
						register.setString("LICENSE", map.get("LICENSE").toString());
						register.setString("VIN", map.get("VIN").toString());
						register.setString("BRAND", map.get("BRAND").toString());
						register.setString("SERIES", map.get("SERIES").toString());
						register.setString("MODEL", map.get("MODEL").toString());
						register.setInteger("MODEL_YEAR", Integer.parseInt(map.get("MODEL_YEAR").toString()));
						register.setString("ENGINE_NO", map.get("ENGINE_NO").toString());
						register.setString("RECORD_MAN", map.get("RECORD_MAN").toString());
						register.setString("OWNER_NAME", map.get("OWNER_NAME").toString());
						register.setString("PHONE", map.get("PHONE").toString());
						register.setString("MOBILE", map.get("MOBILE").toString());
						register.setLong("SERVICE_ADVISOR", Long.parseLong(map.get("SERVICE_ADVISOR").toString()));
						register.set("OCCUR_TIME", map.get("OCCUR_TIME"));
						register.set("OCCUR_SITE", map.get("OCCUR_SITE").toString());
						register.setString("REPORT_MAN", map.get("REPORT_MAN").toString());
						register.setString("REPORT_MAN_PHONE", map.get("REPORT_MAN_PHONE").toString());
						register.setString("REPORT_MAN_MOBILE", map.get("REPORT_MAN_MOBILE").toString());
						register.setInteger("ACCIDENT_TYPE", Integer.parseInt(map.get("ACCIDENT_TYPE").toString()));
						register.setInteger("IS_REPORTED", Integer.parseInt(map.get("IS_REPORTED").toString()));
						register.setInteger("IS_SELF_INSURANCE",
								Integer.parseInt(map.get("IS_SELF_INSURANCE").toString()));
						register.setInteger("IS_OUT_DATE", Integer.parseInt(map.get("IS_OUT_DATE").toString()));
						register.setString("INSURANCE_COMPANY", map.get("INSURANCE_COMPANY").toString());
						register.set("REPORT_TIME", map.get("REPORT_TIME"));
						register.set("PRECONTRACT_TIME", map.get("PRECONTRACT_TIME"));
						register.setString("ACCIDENT_PROCESS", map.get("ACCIDENT_PROCESS"));
						register.setInteger("CUS_SOURCE", Integer.parseInt(map.get("CUS_SOURCE").toString()));
						register.setString("RO_NO", map.get("RO_NO").toString());
						register.setInteger("PAYMENT", Integer.parseInt(map.get("PAYMENT").toString()));
						register.setInteger("TRACE_STATUS", Integer.parseInt(map.get("TRACE_STATUS").toString()));
						if (!StringUtils.isNullOrEmpty(map.get("INSURANCE_STYLE"))) {
							register.setString("INSURANCE_STYLE", map.get("INSURANCE_STYLE").toString());
						}
						if (!StringUtils.isNullOrEmpty(map.get("IS_SELF_COMPANY_INSURANCE"))) {
							register.setInteger("IS_SELF_COMPANY_INSURANCE",
									Integer.parseInt(map.get("IS_SELF_COMPANY_INSURANCE").toString()));
						}
						if (!StringUtils.isNullOrEmpty(map.get("INSURATION_CODE"))) {
							register.setString("INSURATION_CODE", map.get("INSURATION_CODE").toString());
						}
						if (!StringUtils.isNullOrEmpty(map.get("INSURATION_REPAIR_TYPE"))) {
							register.setInteger("INSURATION_REPAIR_TYPE",Integer.parseInt(map.get("INSURATION_REPAIR_TYPE").toString()));
						}
						if (!StringUtils.isNullOrEmpty(map.get("INSURANCE_TYPE_CODE"))) {
							register.setString("INSURANCE_TYPE_CODE_SELECTED",map.get("INSURANCE_TYPE_CODE").toString());
						}
						register.setString("OCCUR_INSURANCE_NO",map.get("OCCUR_INSURANCE_NO").toString());
						register.setString("DEALER_CODE",FrameworkUtil.getLoginInfo().getDealerCode());
						register.setInteger("D_KEY",CommonConstants.D_KEY);
						register.saveIt();
					}
				}
			}
		}
	}

	@Override
	public void saveSettlementOldpart(Map<String, String> param) {
		/*QUERY_SETTLEMENT_AND_OLD_PART_INFO
		SAVE_SETTLEMENT_OLDPART*/
	}
}
