/**
 * 
 */
package com.yonyou.dms.repair.service.basedata;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.TmMicromsgBookingOrderPO;
import com.yonyou.dms.common.domains.PO.basedata.TtBookingOrderLabourPO;
import com.yonyou.dms.common.domains.PO.basedata.TtBookingOrderPO;
import com.yonyou.dms.common.domains.PO.basedata.TtBookingOrderPartPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.CommonNoService;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * @author yangjie
 *
 */
@Service
@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
public class OwnerWeChatBookingManageServiceImpl implements OwnerWeChatBookingManageService {

	@Autowired
	private CommonNoService commonNoService;

	private Timestamp newDate = new Timestamp(System.currentTimeMillis());// 带时分秒当前时间

	@Override
	public PageInfoDto findAll(Map<String, String> queryParam) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT");
		sql.append(" A.DEALER_CODE,");
		sql.append(" A.RESERVATION_ID,");
		sql.append(" A.RESERVATION_DATE,");
		sql.append(" A.RESERVATION_PERIOD,");
		sql.append(" A.LINKMAN_NAME,");
		sql.append(" A.LINKMAN_MOBILE,");
		sql.append(" A.LINKMAN_PHONE,");
		sql.append(" A.MILEAGE,");
		sql.append(" '' as PACKAGE_CODE,");
		sql.append(" B.LICENSE,");
		sql.append(" tb.BRAND_NAME AS BRAND,");
		sql.append(" ts.SERIES_NAME AS SERIES,");
		sql.append(" tm.MODEL_NAME AS MODEL,");
		sql.append(" A.VIN,");
		sql.append(" A.RESERVATION_STATUS,");
		sql.append(" A.REMARK,");
		sql.append(" A.CREATED_AT,");
		sql.append(" A.RESERV_CONFIRM_DATE,");
		sql.append(" A.IS_PRE_GENERATED,");
		sql.append(" A.PACKAGE_NAME");
		sql.append(" FROM TM_MICROMSG_BOOKING_ORDER A");
		sql.append(" LEFT JOIN (" + CommonConstants.VM_VEHICLE);
		sql.append(") B ON A.DEALER_CODE=B.DEALER_CODE");
		sql.append(" AND A.VIN=B.VIN");
		sql.append(" LEFT JOIN TM_BRAND tb ON tb.DEALER_CODE = B.DEALER_CODE AND tb.BRAND_CODE = B.BRAND");
		sql.append(
				" LEFT JOIN TM_SERIES ts ON ts.DEALER_CODE = B.DEALER_CODE AND ts.BRAND_CODE = B.BRAND AND ts.SERIES_CODE = B.SERIES");
		sql.append(
				" LEFT JOIN TM_MODEL tm ON tm.DEALER_CODE = B.DEALER_CODE AND tm.BRAND_CODE = B.BRAND AND tm.SERIES_CODE = B.SERIES AND tm.MODEL_CODE = B.MODEL");
		sql.append(" WHERE A.DEALER_CODE='" + FrameworkUtil.getLoginInfo().getDealerCode() + "'");
		sql.append(" AND A.D_KEY=" + CommonConstants.D_KEY);
		sql.append(" AND A.IS_VALID=" + DictCodeConstants.IS_YES);
		Utility.sqlToLike(sql, queryParam.get("RESERVATION_ID"), "RESERVATION_ID", "A");
		Utility.sqlToEquals(sql, queryParam.get("BRAND"), "BRAND", "B");
		Utility.sqlToEquals(sql, queryParam.get("SERIES"), "SERIES", "B");
		Utility.sqlToEquals(sql, queryParam.get("MODEL"), "MODEL", "B");
		Utility.sqlToLike(sql, queryParam.get("LICENSE"), "LICENSE", "B");
		Utility.sqlToLike(sql, queryParam.get("VIN"), "VIN", "A");
		Utility.sqlToLike(sql, queryParam.get("LINKMAN_NAME"), "LINKMAN_NAME", "A");
		Utility.sqlToLike(sql, queryParam.get("LINKMAN_PHONE"), "LINKMAN_PHONE", "A");
		Utility.sqlToLike(sql, queryParam.get("LINKMAN_MOBILE"), "LINKMAN_MOBILE", "A");
		Utility.sqlToDate(sql, queryParam.get("BEGIN"), queryParam.get("END"), "RESERVATION_DATE", "A");
		Utility.sqlToEquals(sql, queryParam.get("RESERVATION_STATUS"), "RESERVATION_STATUS", "A");
		return DAOUtil.pageQuery(sql.toString(), null);
	}

	@Override
	public PageInfoDto findAllWeChatCards(String id) {
		StringBuffer sql = new StringBuffer();
		sql.append(
				"SELECT DEALER_CODE,CARD_NO,CARD_TYPE,CARD_NAME,SUB_TYPE,CARD_VALUE,BUY_AMOUNT,ISSUE_DATE,START_DATE,END_DATE,USE_DEALER,USE_PROJECT,USE_STATUS,RESERVE_ID from TM_WECHAT_BOOKING_CARD_MESSAGE WHERE 1=1 ");
		Utility.sqlToEquals(sql, id, "RESERVE_ID", null);
		return DAOUtil.pageQuery(sql.toString(), null);
	}

	@Override
	public List<Map> findAllResType() {
		String sql = "SELECT DEALER_CODE,BOOKING_TYPE_CODE,BOOKING_TYPE_NAME FROM TM_BOOKING_TYPE";
		return DAOUtil.findAll(sql, null);
	}

	@Override
	public List<Map> findEmployee() {
		StringBuffer sb = new StringBuffer();
		sb.append(
				"SELECT te.DEALER_CODE,te.EMPLOYEE_NO,te.EMPLOYEE_NAME  FROM TM_EMPLOYEE te,TM_USER tu WHERE te.DEALER_CODE=tu.DEALER_CODE AND te.EMPLOYEE_NO=tu.EMPLOYEE_NO AND tu.IS_SERVICE_ADVISOR=?");
		List param = new ArrayList();
		param.add(DictCodeConstants.IS_YES);
		return DAOUtil.findAll(sb.toString(), param);
	}

	/**
	 * BOOKING_TYPE_CODE,SERVICE_ADVISOR,RESERVATION_DATE,RESERVATION_PERIOD
	 */
	@Override
	public void btnComfirm(String id, Map<String, String> param) throws ServiceBizException {
		String bookingSource = DictCodeConstants.DICT_PRECONTRACT_MICROMSG;// 写死的固定代码，前台固定值
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		String orderNo = commonNoService.getSystemOrderNo(CommonConstants.BO_NO);
		String empNo = FrameworkUtil.getLoginInfo().getEmployeeNo();
		TmMicromsgBookingOrderPO mboPO = TmMicromsgBookingOrderPO.findByCompositeKeys(id, dealerCode);
		if (!StringUtils.isNullOrEmpty(mboPO)) {
			// 判断该车辆是否存在
			List<Map> queryVehicle = checkVehicleExist(mboPO);
			// SEDMS023 如果返回值小于0,抛异常 '该VIN在总部不存在，请核对输入的17位VIN码是否正确'
			/**
			 * 微信预约入口新增预约单
			 */
			TtBookingOrderPO bookingOrderDb = new TtBookingOrderPO();
			String reservationId = id;
			if (queryVehicle.size() > 0) {
				Map map = queryVehicle.get(0);
				bookingOrderDb.setString("LICENSE", map.get("LICENSE").toString());
				bookingOrderDb.setString("VIN", mboPO.getString("VIN"));
				bookingOrderDb.setInteger("BOOKING_SOURCE", bookingSource);
				bookingOrderDb.setString("BOOKING_TYPE_CODE", param.get("BOOKING_TYPE_CODE"));
				bookingOrderDb.setString("SERVICE_ADVISOR", param.get("SERVICE_ADVISOR"));
				if (!StringUtils.isNullOrEmpty(map.get("CHIEF_TECHNICIAN"))) {
					bookingOrderDb.setString("CHIEF_TECHNICIAN", map.get("CHIEF_TECHNICIAN").toString());
				}
				try {
					bookingOrderDb.setDate("BOOKING_COME_TIME",
							Utility.parseString2Date(param.get("BOOKING_COME_TIME"), null));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (!StringUtils.isNullOrEmpty(map.get("OWNER_NAME"))) {
					bookingOrderDb.setString("OWNER_NAME", map.get("OWNER_NAME").toString());
				}
				if (!StringUtils.isNullOrEmpty(mboPO.getString("LINKMAN_NAME"))) {
					bookingOrderDb.setString("CONTACTOR_NAME", mboPO.getString("LINKMAN_NAME").toString());
				}
				if (!StringUtils.isNullOrEmpty(mboPO.getString("LINKMAN_PHONE"))) {
					bookingOrderDb.setString("CONTACTOR_MOBILE", mboPO.getString("LINKMAN_PHONE").toString());
				}
				if (!StringUtils.isNullOrEmpty(mboPO.getString("LINKMAN_MOBILE"))) {
					bookingOrderDb.setString("CONTACTOR_MOBILE", mboPO.getString("LINKMAN_MOBILE").toString());
				}
				if (!StringUtils.isNullOrEmpty(mboPO.getString("MILEAGE"))) {
					bookingOrderDb.setString("IN_MILEAGE", mboPO.getString("MILEAGE").toString());
				}
				if (!StringUtils.isNullOrEmpty(map.get("BRAND"))) {
					bookingOrderDb.setString("BRAND", map.get("BRAND").toString());
				}
				if (!StringUtils.isNullOrEmpty(map.get("SERIES"))) {
					bookingOrderDb.setString("SERIES", map.get("SERIES").toString());
				}
				if (!StringUtils.isNullOrEmpty(map.get("MODEL"))) {
					bookingOrderDb.setString("MODEL", map.get("MODEL").toString());
				}
				// bookingOrderDb.setLabourPrice(new
				// Float(actionContext.getDoubleValue("TT_BOOKING_ORDER.LABOUR_PRICE")));
				// bookingOrderDb.setRecommendEmpName(actionContext.getStringValue("TT_BOOKING_ORDER.RECOMMEND_EMP_NAME"));
				// bookingOrderDb.setRecommendCustomerName(actionContext.getStringValue("TT_BOOKING_ORDER.RECOMMEND_CUSTOMER_NAME"));
				// bookingOrderDb.setEstimateAmount(actionContext.getDoubleValue("TT_BOOKING_ORDER.ESTIMATE_AMOUNT"));
				// bookingOrderDb.setNeedAscDrive(actionContext.getIntValue("TT_BOOKING_ORDER.NEED_ASC_DRIVE"));0
				// bookingOrderDb.setDriveAdd(actionContext.getStringValue("TT_BOOKING_ORDER.DRIVE_ADD"));
				if (!StringUtils.isNullOrEmpty(mboPO.getString("REMARK"))) {
					bookingOrderDb.setString("REMARK", mboPO.getString("REMARK").toString());
				}
				// bookingOrderDb.setIsLackPart(actionContext.getIntValue("TT_BOOKING_ORDER.IS_LACK_PART"));0
				// bookingOrderDb.setReminder(actionContext.getStringValue("TT_BOOKING_ORDER.REMINDER"));
				// if(Utility.testString(actionContext.getStringValue("TT_BOOKING_ORDER.LABOUR_POSITION_CODE")))
				// bookingOrderDb.setLabourPositionCode(actionContext.getStringValue("TT_BOOKING_ORDER.LABOUR_POSITION_CODE"));
				// bookingOrderDb.setRoTroubleDesc(actionContext.getStringValue("TT_BOOKING_ORDER.RO_TROUBLE_DESC"));
				// bookingOrderDb.setCustomerDesc(actionContext.getStringValue("TT_BOOKING_ORDER.CUSTOMER_DESC"));
				// 微信预约ID
				bookingOrderDb.setString("RESERVATION_ID", reservationId);
				bookingOrderDb.setString("DEALER_CODE", dealerCode);
				bookingOrderDb.setString("BOOKING_ORDER_NO", orderNo);
				bookingOrderDb.setInteger("BOOKING_ORDER_STATUS",
						Integer.parseInt(DictCodeConstants.DICT_BOS_NOT_ENTER));
				bookingOrderDb.setString("OPERATOR", FrameworkUtil.getLoginInfo().getEmployeeNo());
				bookingOrderDb.setLong("IS_MICROMSG_BOOKING", DictCodeConstants.IS_YES);
				// 更新车主微信预约信息
				if (!StringUtils.isNullOrEmpty(id)) {
					// DMS预约确认后将预约确认时间反馈DCS
					int printOut = 0;
					if (!StringUtils.isNullOrEmpty(printOut) && printOut == 1) {
						bookingOrderDb.saveIt();
						TmMicromsgBookingOrderPO micromsgBookingOrderPO = TmMicromsgBookingOrderPO
								.findByCompositeKeys(id, dealerCode);
						if (!StringUtils.isNullOrEmpty(micromsgBookingOrderPO)
								&& micromsgBookingOrderPO.getInteger("D_KEY") == CommonConstants.D_KEY) {
							micromsgBookingOrderPO.setLong("IS_PRE_GENERATED", DictCodeConstants.IS_YES);
							micromsgBookingOrderPO.set("RESERV_CONFIRM_DATE", newDate);
							micromsgBookingOrderPO.saveIt();
						}
					}
				}
				// 根据微信预约id找到对应的保养套餐代码
				String sql = "SELECT * FROM TT_WX_BOOKING_PACKAGE_DETAIL WHERE DEALER_CODE = ? AND RESERVATION_ID = ? AND D_KEY = ?";
				List queryParam = new ArrayList();
				queryParam.add(dealerCode);
				queryParam.add(id);
				queryParam.add(CommonConstants.D_KEY);
				List<Map> packCode = DAOUtil.findAll(sql, queryParam);
				if (packCode.size() > 0) {// 循环数组并将查询出来的信息增加
					for (Map map2 : packCode) {
						// 根据预约id找到对应的预约单
						List<TtBookingOrderPO> orderList = TtBookingOrderPO.findBySQL(
								"SELECT * FROM TT_BOOKING_ORDER WHERE RESERVATION_ID = ? AND DEALER_CODE = ? AND D_KEY = ?",
								id, dealerCode, CommonConstants.D_KEY);
						if (orderList.size() > 0) {
							TtBookingOrderPO orderPo = orderList.get(0);
							// 根据套餐代码找到对应的维修项目
							sql = "SELECT * FROM TT_WX_MAINTAIN_PACKAGE_LABOUR WHERE DEALER_CODE = ? AND D_KEY = ? AND PACKAGE_CODE = ?";
							queryParam = new ArrayList();
							queryParam.add(dealerCode);
							queryParam.add(CommonConstants.D_KEY);
							queryParam.add(map2.get("PACKAGE_CODE"));
							List<Map> list = DAOUtil.findAll(sql, queryParam);
							if (list.size() > 0) {
								for (Map map3 : list) {
									TtBookingOrderLabourPO orderLabour = new TtBookingOrderLabourPO();
									orderLabour.setString("DEALER_CODE", dealerCode);
									orderLabour.setString("BOOKING_ORDER_NO", orderPo.getString("BOOKING_ORDER_NO"));
									if (!StringUtils.isNullOrEmpty(map3.get("LABOUR_CODE"))) {
										orderLabour.setString("LABOUR_CODE", map3.get("LABOUR_CODE").toString());
									}
									if (!StringUtils.isNullOrEmpty(map3.get("LABOUR_NAME"))) {
										orderLabour.setString("LABOUR_NAME", map3.get("LABOUR_NAME").toString());
									}
									if (!StringUtils.isNullOrEmpty(map3.get("LABOUR_NAME"))) {
										orderLabour.setDouble("LABOUR_PRICE",
												Double.parseDouble(map3.get("LABOUR_PRICE").toString()));
									}
									if (!StringUtils.isNullOrEmpty(map3.get("LABOUR_AMOUNT"))) {
										orderLabour.setDouble("LABOUR_AMOUNT",
												Double.parseDouble(map3.get("LABOUR_AMOUNT").toString()));
									}
									if (!StringUtils.isNullOrEmpty(map3.get("STD_LABOUR_HOUR"))) {
										orderLabour.setDouble("STD_LABOUR_HOUR",
												Double.parseDouble(map3.get("STD_LABOUR_HOUR").toString()));
									}
									if (!StringUtils.isNullOrEmpty(map3.get("MAINTAIN_PACKAGE_CODE"))) {
										orderLabour.setDouble("MAINTAIN_PACKAGE_CODE",
												Double.parseDouble(map2.get("MAINTAIN_PACKAGE_CODE").toString()));
									}
									orderLabour.saveIt();
								}
							}
							// 根据套餐代码找到对应的维修项目
							sql = "SELECT * FROM TT_WX_MAINTAIN_PACKAGE_PART WHERE DEALER_CODE = ? AND D_KEY = ? AND PACKAGE_CODE = ?";
							queryParam = new ArrayList();
							queryParam.add(dealerCode);
							queryParam.add(CommonConstants.D_KEY);
							queryParam.add(map2.get("PACKAGE_CODE"));
							List<Map> partList = DAOUtil.findAll(sql, queryParam);
							if (partList.size() > 0) {
								for (Map map3 : partList) {
									TtBookingOrderPartPO orderPart = new TtBookingOrderPartPO();
									orderPart.setString("DEALER_CODE", dealerCode);
									orderPart.setString("BOOKING_ORDER_NO", orderPo.getString("BOOKING_ORDER_NO"));
									if (!StringUtils.isNullOrEmpty(map3.get("PART_NO"))) {
										orderPart.setString("PART_NO", map3.get("PART_NO").toString());
									}
									if (!StringUtils.isNullOrEmpty(map3.get("PART_NAME"))) {
										orderPart.setString("PART_NAME", map3.get("PART_NAME").toString());
									}
									if (!StringUtils.isNullOrEmpty(map3.get("LABOUR_CODE"))) {
										orderPart.setString("LABOUR_CODE", map3.get("LABOUR_CODE").toString());
									}
									if (!StringUtils.isNullOrEmpty(map3.get("PART_QUANTITY"))) {
										orderPart.setDouble("BOOKING_QUANTITY",
												Double.parseDouble(map3.get("PART_QUANTITY").toString()));
									}
									if (!StringUtils.isNullOrEmpty(map3.get("PART_SALES_PRICE"))) {
										orderPart.setDouble("SALES_PRICE",
												Double.parseDouble(map3.get("PART_SALES_PRICE").toString()));
									}
									if (!StringUtils.isNullOrEmpty(map3.get("PACKAGE_CODE"))) {
										orderPart.setString("MAINTAIN_PACKAGE_CODE",
												map3.get("PACKAGE_CODE").toString());
									}
									if (!StringUtils.isNullOrEmpty(map3.get("STORAGE_CODE"))) {
										orderPart.setString("STORAGE_CODE", map3.get("STORAGE_CODE").toString());
									}
									orderPart.saveIt();
								}
							}
						}
					}
				}
			}
		} else {
			throw new ServiceBizException("操作异常，预约确认操作不成功!");
		}
	}

	/**
	 * 校验车辆信息是否存在
	 * 
	 * @param po
	 * @throws ServiceBizException
	 */
	public List<Map> checkVehicleExist(TmMicromsgBookingOrderPO po) throws ServiceBizException {
		StringBuffer sb = new StringBuffer();
		sb.append(
				"SELECT A.*,B.OWNER_PROPERTY,B.OWNER_NAME,B.CONTACTOR_ZIP_CODE,B.CONTACTOR_EMAIL,B.CONTACTOR_ADDRESS,B.ADDRESS,B.PHONE,B.MOBILE,");
		sb.append(
				"(case when B.CONTACTOR_NAME IS NULL or B.CONTACTOR_NAME='' then B.OWNER_NAME else B.CONTACTOR_NAME end) AS CONTACTOR_NAME,");
		sb.append(
				"(case when B.CONTACTOR_PHONE IS NULL or B.CONTACTOR_PHONE='' then B.PHONE else B.CONTACTOR_PHONE end) AS CONTACTOR_PHONE,");
		sb.append(
				"(case when B.CONTACTOR_MOBILE IS NULL or B.CONTACTOR_MOBILE='' then B.MOBILE else B.CONTACTOR_MOBILE end) AS CONTACTOR_MOBILE,");
		sb.append(
				"B.GENDER,B.CT_CODE,B.CERTIFICATE_NO,B.CONTACTOR_GENDER,B.E_MAIL,B.BIRTHDAY,'' as IS_MILEAGE_MODIFY,'' as sell_date ");
		sb.append(" ,'' as DELIVERER_DDD_CODE,'' as CHANGE_MILEAGE ");
		sb.append(" FROM (" + CommonConstants.VM_VEHICLE);
		sb.append(") A,(" + CommonConstants.VM_OWNER);
		sb.append(") B WHERE A.OWNER_NO = B.OWNER_NO ");
		Utility.sqlToLike(sb, po.getString("LICENSE"), "LICENSE", "A");
		Utility.sqlToLike(sb, po.getString("VIN"), "VIN", "A");
		List<Map> findAll = DAOUtil.findAll(sb.toString(), null);
		if (findAll.size() <= 0) {
			throw new ServiceBizException("该车辆信息不存在，请同步总部信息!");
		} else {
			return findAll;
		}
	}

	@Override
	public List<Map> findAllForExcel() {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT");
		sql.append(" A.DEALER_CODE,");
		sql.append(" A.RESERVATION_ID,");
		sql.append(" A.RESERVATION_DATE,");
		sql.append(" A.RESERVATION_PERIOD,");
		sql.append(" A.LINKMAN_NAME,");
		sql.append(" A.LINKMAN_MOBILE,");
		sql.append(" A.LINKMAN_PHONE,");
		sql.append(" A.MILEAGE,");
		sql.append(" '' as PACKAGE_CODE,");
		sql.append(" B.LICENSE,");
		sql.append(" tb.BRAND_NAME AS BRAND,");
		sql.append(" ts.SERIES_NAME AS SERIES,");
		sql.append(" tm.MODEL_NAME AS MODEL,");
		sql.append(" A.VIN,");
		sql.append(" A.RESERVATION_STATUS,");
		sql.append(" A.REMARK,");
		sql.append(" A.CREATED_AT,");
		sql.append(" A.RESERV_CONFIRM_DATE,");
		sql.append(" A.IS_PRE_GENERATED,");
		sql.append(" A.PACKAGE_NAME");
		sql.append(" FROM TM_MICROMSG_BOOKING_ORDER A");
		sql.append(" LEFT JOIN (" + CommonConstants.VM_VEHICLE);
		sql.append(") B ON A.DEALER_CODE=B.DEALER_CODE");
		sql.append(" AND A.VIN=B.VIN");
		sql.append(" LEFT JOIN TM_BRAND tb ON tb.DEALER_CODE = B.DEALER_CODE AND tb.BRAND_CODE = B.BRAND");
		sql.append(
				" LEFT JOIN TM_SERIES ts ON ts.DEALER_CODE = B.DEALER_CODE AND ts.BRAND_CODE = B.BRAND AND ts.SERIES_CODE = B.SERIES");
		sql.append(
				" LEFT JOIN TM_MODEL tm ON tm.DEALER_CODE = B.DEALER_CODE AND tm.BRAND_CODE = B.BRAND AND tm.SERIES_CODE = B.SERIES AND tm.MODEL_CODE = B.MODEL");
		sql.append(" WHERE A.DEALER_CODE='" + FrameworkUtil.getLoginInfo().getDealerCode() + "'");
		sql.append(" AND A.D_KEY=" + CommonConstants.D_KEY);
		sql.append(" AND A.IS_VALID=" + DictCodeConstants.IS_YES);
		return DAOUtil.findAll(sql.toString(), null);
	}

	@Override
	public List<Map> findEmployees() {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT te.DEALER_CODE,te.EMPLOYEE_NO,te.EMPLOYEE_NAME FROM TM_EMPLOYEE te ");
		return DAOUtil.findAll(sb.toString(),new ArrayList());
	}

}
