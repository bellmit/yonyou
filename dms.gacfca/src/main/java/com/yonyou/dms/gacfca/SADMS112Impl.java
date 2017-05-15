package com.yonyou.dms.gacfca;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.javalite.activejdbc.LazyList;
import org.springframework.stereotype.Service;

import com.yonyou.dms.DTO.gacfca.OpRepairOrderDTO;
import com.yonyou.dms.DTO.gacfca.RoAddItemDetailDTO;
import com.yonyou.dms.DTO.gacfca.RoLabourDetailDTO;
import com.yonyou.dms.DTO.gacfca.RoRepairPartDetailDTO;
import com.yonyou.dms.DTO.gacfca.SalesPartDetailDTO;
import com.yonyou.dms.DTO.gacfca.SalesPartItemDetailDTO;
import com.yonyou.dms.common.Util.Utility;
import com.yonyou.dms.common.domains.PO.basedata.RepairOrderPO;
import com.yonyou.dms.common.domains.PO.basedata.RoAddItemPO;
import com.yonyou.dms.common.domains.PO.basedata.TmWechatBookingCardMessagePO;
import com.yonyou.dms.common.domains.PO.basedata.TtRoLabourPO;
import com.yonyou.dms.common.domains.PO.basedata.TtRoRepairPartPO;
import com.yonyou.dms.common.domains.PO.basedata.TtSalesPartItemPO;
import com.yonyou.dms.common.domains.PO.basedata.TtSalesPartPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.function.common.CommonConstants;

/**
 * @description 计划任务每日上报未结算的工单接口
 * @author Administrator
 *
 */
@Service
public class SADMS112Impl implements SADMS112 {

	final Logger logger = Logger.getLogger(SADMS112Impl.class);

	@Override
	public List<OpRepairOrderDTO> getSADMS112(String dealerCode) {
		logger.info("==========SADMS112Impl执行===========");
		try {
			Calendar ca = Calendar.getInstance();
			ca.setTime(new Date());
			String endDate = new SimpleDateFormat("yyyy-MM-dd").format(ca.getTime()).toString() + " 00:00:00";
			ca.add(Calendar.DATE,-1);
			String beginDate = new SimpleDateFormat("yyyy-MM-dd").format(ca.getTime()).toString()+ " 00:00:00";
			List<Map> submitList = queryDailyRepairOrderNo(dealerCode, beginDate, endDate);

			String roNo = null;
			String bookOrderNo = null;
			List<OpRepairOrderDTO> resultList = new ArrayList<OpRepairOrderDTO>();
			if (submitList != null && submitList.size() > 0) {
				for (Map<String,Object> bean : submitList) {
					roNo = bean.get("RO_NO").toString();
					bookOrderNo = bean.get("RESERVATION_ID").toString();
					if (Utility.testString(roNo)) {
						logger.debug("from RepairOrderPO DEALER_CODE = "+dealerCode+" and RO_NO = "+roNo+" and D_KEY = "+CommonConstants.D_KEY);
						List<RepairOrderPO> repairOrderList = RepairOrderPO.findBySQL("DEALER_CODE = ? and RO_NO = ? and D_KEY = ?", 
								dealerCode,roNo,CommonConstants.D_KEY);
						logger.debug("from TtRoLabourPO DEALER_CODE = "+dealerCode+" and RO_NO = "+roNo+" and D_KEY = "+CommonConstants.D_KEY);
						List<TtRoLabourPO> roLabourList = TtRoLabourPO.findBySQL("DEALER_CODE = ? and RO_NO = ? and D_KEY = ?", 
								dealerCode,roNo,CommonConstants.D_KEY);
						logger.debug("from TtRoRepairPartPO DEALER_CODE = "+dealerCode+" and RO_NO = "+roNo+" and D_KEY = "+CommonConstants.D_KEY);
						List<TtRoRepairPartPO> roRepairPartList = TtRoRepairPartPO.findBySQL("DEALER_CODE = ? and RO_NO = ? and D_KEY = ?", 
								dealerCode,roNo,CommonConstants.D_KEY);
						logger.debug("from RoAddItemPO DEALER_CODE = "+dealerCode+" and RO_NO = "+roNo+" and D_KEY = "+CommonConstants.D_KEY);
						List<RoAddItemPO> roAddItemList = RoAddItemPO.findBySQL("DEALER_CODE = ? and RO_NO = ? and D_KEY = ?", 
								dealerCode,roNo,CommonConstants.D_KEY);
						logger.debug("from TtSalesPartPO DEALER_CODE = "+dealerCode+" and RO_NO = "+roNo+" and D_KEY = "+CommonConstants.D_KEY);
						List<TtSalesPartPO> roAddRoSalesPartList = TtSalesPartPO.findBySQL("DEALER_CODE = ? and RO_NO = ? and D_KEY = ?", 
								dealerCode,roNo,CommonConstants.D_KEY);
						List<TmWechatBookingCardMessagePO> roWechatBooking = null;
						if(Utility.testString(bookOrderNo)){
							logger.debug("from TmWechatBookingCardMessagePO DEALER_CODE = "+dealerCode+" and RESERVE_ID = "+bookOrderNo+" and D_KEY = "+CommonConstants.D_KEY);
							roWechatBooking = TmWechatBookingCardMessagePO.findBySQL("DEALER_CODE = ? and RESERVE_ID = ? and D_KEY = ?", 
									dealerCode,bookOrderNo,CommonConstants.D_KEY);
						}
						RepairOrderPO orderPO =  repairOrderList.get(0);
						OpRepairOrderDTO orderVO = setRepairOrder( dealerCode, orderPO);
						if(roWechatBooking != null && roWechatBooking.size() > 0){
							TmWechatBookingCardMessagePO po = (TmWechatBookingCardMessagePO) roWechatBooking.get(0);
							if(po.getInteger("Card_Type").equals(50211002)||po.getInteger("Card_Type").equals(50211003)){
								orderVO.setCardAmount(po.getDouble("CARD_VALUE"));//代金券，抵用券
								orderVO.setCardNo(po.getString("CARD_NO"));
							}
							if(po.getInteger("CARD_TYPE").equals(50211001)){
								Double mul1 = Utility.mul(po.getInteger("CARD_VALUE").toString(), "0.1");
								Double sub1 = Utility.sub("1", mul1.toString());
								Double mul2 = Utility.mul(sub1.toString(), orderPO.getDouble("LABOUR_AMOUNT").toString());
								orderVO.setCardAmount(mul2);//抵扣券
								orderVO.setCardNo(po.getString("CARD_NO"));
							}
						}
						addRoLabour( dealerCode, orderVO, roLabourList);
						addRoRepairPart(dealerCode, orderVO, roRepairPartList);
						addRoAddItem(dealerCode, orderVO, roAddItemList);
						addRoSalesPart(dealerCode, orderVO, roAddRoSalesPartList);
						resultList.add(orderVO);
					}
				}
			}

			return resultList;
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(e);
			return null;
		}finally{
			logger.info("==========SADMS112Impl执行===========");
		}
	}

	/**
	 * 查询每日符合上报条件的工单号
	 * @param conn
	 * @param entityCode
	 * @param beginDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	public List<Map> queryDailyRepairOrderNo(String entityCode, String beginDate, String endDate) throws Exception {
		String sql = "SELECT DISTINCT A.RO_NO,B.RESERVATION_ID FROM TT_REPAIR_ORDER A  LEFT JOIN TT_BOOKING_ORDER B ON A.ENTITY_CODE=B.ENTITY_CODE AND A.BOOKING_ORDER_NO=B.BOOKING_ORDER_NO"						
				+	" WHERE 1 = 1 "				
				+   " AND A.ENTITY_CODE = '"
				+ entityCode
				+ "'"
				+ " and ((A.CREATE_AT>='"+beginDate+"' and A.CREATE_AT<='"+endDate+"') " 
				+ " OR(A.UPDATE_AT>='"+beginDate+"' and A.UPDATE_AT<='"+endDate+"')) "
				+ " AND A.D_KEY = "
				+ CommonConstants.D_KEY;

		logger.debug(sql);
		return DAOUtil.findAll(sql, null);
	}
	
	/**
	 * @description 将 repairOrderPO包装成 OpRepairOrderDTO
	 * @param con
	 * @param entityCode
	 * @param orderPO
	 * @return
	 * @throws Exception
	 */
	private OpRepairOrderDTO setRepairOrder(String entityCode, RepairOrderPO orderPO) throws Exception {
		OpRepairOrderDTO orderVO = new OpRepairOrderDTO();
		orderVO.setActivityTraceTag(orderPO.getInteger("ACTIVITY_TRACE_TAG"));
		orderVO.setAddItemAmount(orderPO.getDouble("ADD_ITEM_AMOUNT"));
		orderVO.setIsStatus(1);//非作废区分
		orderVO.setEntityCode(orderPO.getString("DEALER_CODE"));
		orderVO.setSoNo(orderPO.getString("SO_NO"));
		orderVO.setCustomerDesc(orderPO.getString("CUSTOMER_DESC"));
		orderVO.setTraceTag(orderPO.getInteger("TRACE_TAG"));
		orderVO.setIsTimeoutSubmit(orderPO.getInteger("IS_TIMEOUT_SUBMIT"));
		orderVO.setIsUpdateEndTimeSupposed(orderPO.getInteger("IS_UPDATE_END_TIME_SUPPOSED"));
		orderVO.setRoType(orderPO.getInteger("RO_TYPE"));
		orderVO.setSeriousSafetyStatus(orderPO.getInteger("SERIOUS_SAFETY_STATUS"));
		orderVO.setRoClaimStatus(orderPO.getInteger("RO_CLAIM_STATUS"));
		orderVO.setRemark(orderPO.getString("REMARK"));
		orderVO.setIsTakePartOld(orderPO.getInteger("IS_TAKE_PART_OLD"));
		orderVO.setLastMaintenanceMileage(orderPO.getDouble("LAST_MAINTENANCE_MILEAGE"));
		orderVO.setCompleteTime(orderPO.getDate("COMPLETE_TIME"));
		orderVO.setLabourAmount(orderPO.getDouble("LABOUR_AMOUNT"));
		orderVO.setIsPrint(orderPO.getInteger("IS_PRINT"));
		orderVO.setIsOldPart(orderPO.getInteger("IS_OLD_PART"));
		orderVO.setDelivererPhone(orderPO.getString("DELIVERER_PHONE"));
		orderVO.setBalanceAmount(orderPO.getDouble("BALANCE_AMOUNT"));
		orderVO.setServiceAdvisorAss(orderPO.getString("SERVICE_ADVISOR_ASS"));
		orderVO.setIsChangeOdograph(orderPO.getInteger("IS_CHANGE_ODOGRAPH"));
		orderVO.setRecommendCustomerName(orderPO.getString("RECOMMEND_CUSTOMER_NAME"));
		orderVO.setCheckedEnd(orderPO.getInteger("CHECKED_END"));
		orderVO.setSalesPartNo(orderPO.getString("sales_Part_No"));
		orderVO.setRecommendEmpName(orderPO.getString("RECOMMEND_EMPNAME"));
		orderVO.setVehicleTopDesc(orderPO.getInteger("VEHICLE_TOP_DESC"));
		orderVO.setOutMileage(orderPO.getDouble("OUT_MILEAGE"));
		orderVO.setOccurInsuranceNo(orderPO.getString("OCCUR_INSURANCE_NO"));
		orderVO.setIsTripleGuaranteeBef(orderPO.getInteger("IS_TRIPLE_GUARANTEE_BEF"));
		orderVO.setIsLargessMaintain(orderPO.getInteger("IS_LARGESS_MAINTAIN"));
		orderVO.setIsPurchaseMaintenance(orderPO.getInteger("IS_PURCHASE_MAINTENANCE"));
		orderVO.setIsCustomerInAsc(orderPO.getInteger("IS_CUSTOMER_IN_ASC"));
		orderVO.setDeliveryUser(orderPO.getString("DELIVERY_USER"));
		orderVO.setIsSeriousTrouble(orderPO.getInteger("IS_SERIOUS_TROUBLE"));
		orderVO.setDdcnUpdateDate(orderPO.getDate("DDCN_UPDATE_DATE"));
		orderVO.setInsuranceOver(orderPO.getInteger("INSURANCE_OVER"));
		orderVO.setOilRemain(orderPO.getInteger("OIL_REMAIN"));
		orderVO.setMemberNo(orderPO.getString("MEMBER_NO"));
		orderVO.setPrintSendTime(orderPO.getDate("PRINT_SEND_TIME"));
		orderVO.setSequenceNo(orderPO.getString("SEQUENCE_NO"));
		orderVO.setIsActivity(orderPO.getInteger("IS_ACTIVITY"));
		orderVO.setInReason(orderPO.getInteger("IN_REASON"));
		orderVO.setSalesPartAmount(orderPO.getDouble("SALES_PART_AMOUNT"));
		orderVO.setWaitPartTag(orderPO.getInteger("WAIT_PART_TAG"));
		orderVO.setChiefTechnician(orderPO.getString("CHIEF_TECHNICIAN"));
		orderVO.setReceiveAmount(orderPO.getDouble("RECEIVE_AMOUNT"));
		orderVO.setIsTripleGuarantee(orderPO.getInteger("IS_TRIPLE_GUARANTEE"));
//		orderVO.setRepairStatus(orderPO.getInteger("repair_Status"));
		orderVO.setForBalanceTime(orderPO.getDate("FOR_BALANCE_TIME"));
		orderVO.setTotalMileage(orderPO.getDouble("TOTAL_MILEAGE"));
		orderVO.setTroubleOccurDate(orderPO.getDate("TROUBLE_OCCUR_DATE"));
		orderVO.setIsWash(orderPO.getInteger("IS_WASH"));
		orderVO.setNotIntegral(orderPO.getInteger("NOT_INTEGRAL"));
//		orderVO.setDeliverBillMan(orderPO.getString("deliver_BillMan"));
		orderVO.setDeliverer(orderPO.getString("DELIVERER"));
		orderVO.setOwnerNo(orderPO.getString("OWNER_NO"));
		orderVO.setRoCreateDate(orderPO.getDate("RO_CREATE_DATE"));
		orderVO.setFirstEstimateAmount(orderPO.getDouble("FIRST_ESTIMATE_AMOUNT"));
		orderVO.setIsQs(orderPO.getInteger("IS_QS"));
		orderVO.setDelivererMobile(orderPO.getString("DELIVERER_MOBILE"));
		orderVO.setTestDriver(orderPO.getString("TEST_DRIVER"));
		orderVO.setIsAbandonActivity(orderPO.getInteger("IS_ABANDON_ACTIVITY"));
		orderVO.setLockUser(orderPO.getString("LOCK_USER"));
		orderVO.setMemActiTotalAmount(orderPO.getDouble("MEM_ACTI_TOTAL_AMOUNT"));
		orderVO.setEworkshopRemark(orderPO.getString("EWORKSHOP_REMARK"));
		orderVO.setSchemeStatus(orderPO.getInteger("SCHEME_STATUS"));
		orderVO.setInsurationNo(orderPO.getString("INSURATION_NO"));
		orderVO.setOverItemAmount(orderPO.getDouble("OVER_ITEM_AMOUNT"));
		orderVO.setTraceTime(orderPO.getInteger("TRACE_TIME"));
		orderVO.setModifyNum(orderPO.getInteger("MODIFY_NUM"));
		orderVO.setDsFactBalance(orderPO.getDouble("DS_FACT_BALANCE"));
		orderVO.setCaseClosedDate(orderPO.getDate("CASE_CLOSED_DATE"));
		orderVO.setOldpartTreatDate(orderPO.getDate("OLDPART_TREAT_DATE"));
		orderVO.setLicense(orderPO.getString("LICENSE"));
		orderVO.setRoStatus(orderPO.getInteger("RO_STATUS"));
		orderVO.setNoTraceReason(orderPO.getString("NO_TRACE_REASON"));
		orderVO.setRoChargeType(orderPO.getInteger("RO_CHARGE_TYPE"));
		orderVO.setEstimateNo(orderPO.getString("ESTIMATE_NO"));
		orderVO.setOwnerName(orderPO.getString("OWNER_NAME"));
		orderVO.setInBindingStatus(orderPO.getInteger("IN_BINDING_STATUS"));
		orderVO.setBookingOrderNo(orderPO.getString("BOOKING_ORDER_NO"));
		orderVO.setDelivererGender(orderPO.getInteger("DELIVERER_GENDER"));
		orderVO.setEngineNo(orderPO.getString("ENGINE_NO"));
		orderVO.setDeliverBillDate(orderPO.getDate("DELIVER_BILL_DATE"));
		orderVO.setSettlementStatus(orderPO.getInteger("SETTLEMENT_STATUS"));
		orderVO.setPrintCarTime(orderPO.getDate("PRINT_CAR_TIME"));
		orderVO.setRoTroubleDesc(orderPO.getString("RO_TROUBLE_DESC"));
		orderVO.setCompleteTag(orderPO.getInteger("COMPLETE_TAG"));
		orderVO.setDeliveryTag(orderPO.getInteger("DELIVERY_TAG")) ;
		orderVO.setRemark1(orderPO.getString("REMARK1")) ;
		orderVO.setRemark2(orderPO.getString("REMARK2"));
		orderVO.setLastMaintenanceDate(orderPO.getDate("LAST_MAINTENANCE_DATE"));
		orderVO.setSettlementRemark(orderPO.getString("SETTLEMENT_REMARK"));
		orderVO.setDerateAmount(orderPO.getDouble("DERATE_AMOUNT"));
		orderVO.setCustomerPreCheck(orderPO.getInteger("CUSTOMER_PRE_CHECK"));
		orderVO.setIsSystemQuote(orderPO.getInteger("IS_SYSTEM_QUOTE"));
		orderVO.setChangeMileage(orderPO.getDouble("CHANGE_MILEAGE"));
		orderVO.setTotalChangeMileage(orderPO.getDouble("TOTAL_CHANGE_MILEAGE"));
		orderVO.setOwnerProperty(orderPO.getInteger("OWNER_PROPERTY"));
		orderVO.setInsurationCode(orderPO.getString("INSURATION_CODE"));
		orderVO.setModel(orderPO.getString("MODEL")) ;
		orderVO.setRoNo(orderPO.getString("RO_NO"));
		orderVO.setNeedRoadTest(orderPO.getInteger("NEED_ROAD_TEST"));
		orderVO.setLabourPrice(orderPO.getFloat("LABOUR_PRICE"));
		orderVO.setLabourPositionCode(orderPO.getString("LABOUR_POSITION_CODE"));
		orderVO.setPrintRoTime(orderPO.getDate("PRINT_RO_TIME"));
		orderVO.setReceptionNo(orderPO.getString("RECEPTION_NO"));
		orderVO.setOldpartRemark(orderPO.getString("OLDPART_REMARK"));
		orderVO.setNotEnterWorkshop(orderPO.getInteger("NOT_ENTER_WORKSHOP"));
		orderVO.setEstimateBeginTime(orderPO.getDate("ESTIMATE_BEGIN_TIME"));
		orderVO.setQuoteEndAccurate(orderPO.getInteger("QUOTE_END_ACCURATE"));
		orderVO.setAddItemAmount(orderPO.getDouble("ADD_ITEM_AMOUNT"));
//		orderVO.setCancelledDate(orderPO.getDate("cancelled_Date"));
		orderVO.setVin(orderPO.getString("VIN"));
		orderVO.setRepairAmount(orderPO.getDouble("REPAIR_AMOUNT"));
		orderVO.setSeries(orderPO.getString("SERIES"));
		orderVO.setRepairPartAmount(orderPO.getDouble("REPAIR_PART_AMOUNT"));
		orderVO.setDeliveryDate(orderPO.getDate("DELIVERY_DATE"));
		orderVO.setOtherRepairType(orderPO.getString("OTHER_REPAIR_TYPE"));
		orderVO.setSubObbAmount(orderPO.getDouble("SUB_OBB_AMOUNT")) ;
		orderVO.setSettleColDate(orderPO.getDate("SETTLE_COL_DATE"));
		orderVO.setIsCloseRo(orderPO.getInteger("IS_CLOSE_RO"));
		orderVO.setSettlementAmount(orderPO.getDouble("SETTLEMENT_AMOUNT"));
		orderVO.setOldpartTreatMode(orderPO.getInteger("OLDPART_TREAT_MODE"));
		orderVO.setWaitInfoTag(orderPO.getInteger("WAIT_INFO_TAG"));
		orderVO.setEndTimeSupposed(orderPO.getDate("END_TIME_SUPPOSED"));
		orderVO.setIsSeasonCheck(orderPO.getInteger("IS_SEASON_CHECK")) ;
		orderVO.setFinishUser(orderPO.getString("FINISH_USER"));
		orderVO.setIsMaintain(orderPO.getInteger("IS_MAINTAIN"));
		orderVO.setIsTrace(orderPO.getInteger("IS_TRACE"));
		orderVO.setRoSplitStatus(orderPO.getInteger("RO_SPLIT_STATUS"));
		orderVO.setExplainedBalanceAccounts(orderPO.getInteger("EXPLAINED_BALANCE_ACCOUNTS"));
		orderVO.setServiceAdvisor(orderPO.getString("SERVICE_ADVISOR"));
		orderVO.setOutBindingStatus(orderPO.getInteger("OUT_BINDING_STATUS"));
		orderVO.setDsAmount(orderPO.getDouble("DS_AMOUNT"));
		orderVO.setRepairTypeCode(orderPO.getString("REPAIR_TYPE_CODE"));
		orderVO.setWorkshopStatus(orderPO.getInteger("WORKSHOP_STATUS"));
		orderVO.setEstimateAmount(orderPO.getDouble("ESTIMATE_AMOUNT"));
		orderVO.setPayment(orderPO.getInteger("PAYMENT"));
		orderVO.setInMileage(orderPO.getDouble("IN_MILEAGE"));
		orderVO.setBrand(orderPO.getString("BRAND"));
		orderVO.setConfigCode(orderPO.getString("CONFIG_CODE"));
		orderVO.setPrintRpTime(orderPO.getDate("PRINT_RP_TIME"));
//		orderVO.setIsStatus(orderPO.getInteger("is_Status"));
//		orderVO.setDeleteDate(orderPO.getDate("delete_Date"));
//		orderVO.setCardAmount(orderPO.getDouble("card_Amount"));
//		orderVO.setCardNo(orderPO.getString("card_No"));
		return orderVO;
	}
	
	/**
	 * @description 将TtRoLabourPO包装成RoLabourDetailDTO，并填充进OpRepairOrderDTO
	 * @param entityCode
	 * @param opRepairOrderDTO
	 * @param roLabourList
	 * @throws Exception
	 */
	private void addRoLabour(String entityCode, OpRepairOrderDTO opRepairOrderDTO, List<TtRoLabourPO> roLabourList) throws Exception {
		if (roLabourList != null && roLabourList.size() > 0) {
			LinkedList<RoLabourDetailDTO> resultList = new LinkedList<RoLabourDetailDTO>();
			for (TtRoLabourPO roLabourPO : roLabourList) {
				RoLabourDetailDTO roLabourDetailDTO = new RoLabourDetailDTO();
				roLabourDetailDTO.setModelLabourCode(roLabourPO.getString("MODEL_LABOUR_CODE"));
				roLabourDetailDTO.setClaimAmount(roLabourPO.getDouble("CLAIM_AMOUNT")) ;
				roLabourDetailDTO.setRemark(roLabourPO.getString("REMARK")) ;
				roLabourDetailDTO.setTechnician(roLabourPO.getString("TECHNICIAN")) ;
				roLabourDetailDTO.setActivityCode(roLabourPO.getString("ACTIVITY_CODE")) ;
				roLabourDetailDTO.setLabourAmount(roLabourPO.getDouble("LABOUR_AMOUNT")) ;
				roLabourDetailDTO.setNoWarrantyCondition(roLabourPO.getString("NO_WARRANTY_CONDITION"));
				roLabourDetailDTO.setInterReturn(roLabourPO.getInteger("INTER_RETURN"));
				roLabourDetailDTO.setAppName(roLabourPO.getString("APP_NAME")) ;
				roLabourDetailDTO.setInfixCode(roLabourPO.getString("INFIX_CODE")) ;
				roLabourDetailDTO.setAssignLabourHour(roLabourPO.getDouble("ASSIGN_LABOUR_HOUR")) ;
				roLabourDetailDTO.setLabourName(roLabourPO.getString("LABOUR_NAME"));
				roLabourDetailDTO.setAppCode(roLabourPO.getString("APP_CODE")) ;
				roLabourDetailDTO.setLocalLabourCode(roLabourPO.getString("LOCAL_LABOUR_CODE")) ;
				roLabourDetailDTO.setStdLabourHour(roLabourPO.getDouble("STD_LABOUR_HOUR")) ;
				roLabourDetailDTO.setPackageCode(roLabourPO.getString("PACKAGE_CODE"));
				roLabourDetailDTO.setCardId(roLabourPO.getLong("CARD_ID")) ;
				roLabourDetailDTO.setRoNo(roLabourPO.getString("RO_NO"));
				roLabourDetailDTO.setStdLabourHourSave(roLabourPO.getDouble("STD_LABOUR_HOUR_SAVE"));
				roLabourDetailDTO.setLabourPrice(roLabourPO.getFloat("LABOUR_PRICE"));
				roLabourDetailDTO.setChargePartitionCode(roLabourPO.getString("CHARGE_PARTITION_CODE"));
				roLabourDetailDTO.setDdcnUpdateDate(roLabourPO.getDate("DDCN_UPDATE_DATE")) ;
				roLabourDetailDTO.setLabourType(roLabourPO.getInteger("LABOUR_TYPE"));
				roLabourDetailDTO.setPosName(roLabourPO.getString("POS_NAME")) ;
				roLabourDetailDTO.setItemId(roLabourPO.getLong("ITEM_ID"));
				roLabourDetailDTO.setEntityCode(roLabourPO.getString("DEALER_CODE")) ;
				roLabourDetailDTO.setPosCode(roLabourPO.getString("POS_CODE"));
				roLabourDetailDTO.setManageSortCode(roLabourPO.getString("MANAGE_SORT_CODE"));
				roLabourDetailDTO.setIsTripleGuarantee(roLabourPO.getInteger("IS_TRIPLE_GUARANTEE"));
				roLabourDetailDTO.setClaimLabour(roLabourPO.getFloat("CLAIM_LABOUR"));
				roLabourDetailDTO.setSprayArea(roLabourPO.getDouble("SPRAY_AREA"));
				roLabourDetailDTO.setTroubleDesc(roLabourPO.getString("TROUBLE_DESC"));
				roLabourDetailDTO.setNeedlessRepair(roLabourPO.getInteger("NEEDLESS_REPAIR"));
				roLabourDetailDTO.setWorkerTypeCode(roLabourPO.getString("WORKER_TYPE_CODE"));
				roLabourDetailDTO.setDiscount(roLabourPO.getDouble("DISCOUNT"));
				roLabourDetailDTO.setPreCheck(roLabourPO.getInteger("PRE_CHECK"));
				roLabourDetailDTO.setReason(roLabourPO.getInteger("REASON"));
				roLabourDetailDTO.setLocalLabourName(roLabourPO.getString("LOCAL_LABOUR_NAME"));
				roLabourDetailDTO.setAssignTag(roLabourPO.getInteger("ASSIGN_TAG"));
				roLabourDetailDTO.setRepairTypeCode(roLabourPO.getString("REPAIR_TYPE_CODE"));
				roLabourDetailDTO.setRoLabourType(roLabourPO.getInteger("RO_LABOUR_TYPE"));
				roLabourDetailDTO.setLabourCode(roLabourPO.getString("LABOUR_CODE"));
				roLabourDetailDTO.setWarLevel(roLabourPO.getInteger("WAR_LEVEL"));
				roLabourDetailDTO.setAddTag(roLabourPO.getInteger("ADD_TAG")) ;
				roLabourDetailDTO.setConsignExterior(roLabourPO.getInteger("CONSIGN_EXTERIOR")) ;
				roLabourDetailDTO.setTroubleCause(roLabourPO.getString("TROUBLE_CAUSE")) ;
				resultList.add(roLabourDetailDTO);
			}
			opRepairOrderDTO.setRoLabourVoList(resultList);
		}
	}
	
	/**
	 * @description 将TtRoRepairPartPO包装成RoRepairPartDetailDTO，然后填充进OpRepairOrderDTO
	 * @param con
	 * @param entityCode
	 * @param opRepairOrderDTO
	 * @param roRepairPartList
	 * @throws Exception
	 */
	private void addRoRepairPart(String entityCode, OpRepairOrderDTO opRepairOrderDTO, List<TtRoRepairPartPO> roRepairPartList) throws Exception {
		LinkedList<RoRepairPartDetailDTO> resultList = new LinkedList<RoRepairPartDetailDTO>();
		if (roRepairPartList != null && roRepairPartList.size() > 0) {
			for (TtRoRepairPartPO ttRoRepairPartPO : roRepairPartList) {
				RoRepairPartDetailDTO roRepairPartDetailDTO = new RoRepairPartDetailDTO();
				roRepairPartDetailDTO.setPartCostAmount(ttRoRepairPartPO.getDouble("PART_COST_AMOUNT"));
				roRepairPartDetailDTO.setModelLabourCode(ttRoRepairPartPO.getString("MODEL_LABOUR_CODE"));
				roRepairPartDetailDTO.setIsMainPart(ttRoRepairPartPO.getInteger("IS_MAIN_PART"));
				roRepairPartDetailDTO.setPartName(ttRoRepairPartPO.getString("PART_NAME"));
				roRepairPartDetailDTO.setBatchNo(ttRoRepairPartPO.getInteger("BATCH_NO"));
				roRepairPartDetailDTO.setSendTime(ttRoRepairPartPO.getDate("SEND_TIME")) ;
				roRepairPartDetailDTO.setPartCostPrice(ttRoRepairPartPO.getDouble("PART_COST_PRICE"));
				roRepairPartDetailDTO.setOutStockNo(ttRoRepairPartPO.getString("OUT_STOCK_NO")) ;
				roRepairPartDetailDTO.setActivityCode(ttRoRepairPartPO.getString("ACTIVITY_CODE"));
				roRepairPartDetailDTO.setOtherPartCostPrice(ttRoRepairPartPO.getDouble("OTHER_PART_COST_PRICE")) ;
				roRepairPartDetailDTO.setPriceRate(ttRoRepairPartPO.getFloat("PRICE_RATE"));
				roRepairPartDetailDTO.setOtherPartCostAmount(ttRoRepairPartPO.getDouble("OTHER_PART_COST_AMOUNT"));
				roRepairPartDetailDTO.setPartQuantity(ttRoRepairPartPO.getFloat("PART_QUANTITY"));
				roRepairPartDetailDTO.setPartSalesPrice(ttRoRepairPartPO.getDouble("PART_SALES_PRICE"));
				roRepairPartDetailDTO.setInterReturn(ttRoRepairPartPO.getInteger("INTER_RETURN"));
				roRepairPartDetailDTO.setNonOneOff(ttRoRepairPartPO.getInteger("NON_ONE_OFF")) ;
				roRepairPartDetailDTO.setIsFinished(ttRoRepairPartPO.getInteger("IS_FINISHED"));
				roRepairPartDetailDTO.setLabourName(ttRoRepairPartPO.getString("LABOUR_NAME"));
				roRepairPartDetailDTO.setPartBatchNo(ttRoRepairPartPO.getString("PART_BATCH_NO"));
				roRepairPartDetailDTO.setDxpDate(ttRoRepairPartPO.getDate("DXP_DATE"));
				roRepairPartDetailDTO.setStoragePositionCode(ttRoRepairPartPO.getString("STORAGE_POSITION_CODE")) ;
				roRepairPartDetailDTO.setPackageCode(ttRoRepairPartPO.getString("PACKAGE_CODE"));
				roRepairPartDetailDTO.setCardId(ttRoRepairPartPO.getLong("CARD_ID"));
				roRepairPartDetailDTO.setRoNo(ttRoRepairPartPO.getString("RO_NO"));
				roRepairPartDetailDTO.setPartCategory(ttRoRepairPartPO.getInteger("PART_CATEGORY")) ;
				roRepairPartDetailDTO.setChargePartitionCode(ttRoRepairPartPO.getString("CHARGE_PARTITION_CODE"));
				roRepairPartDetailDTO.setDdcnUpdateDate(ttRoRepairPartPO.getDate("DDCN_UPDATE_DATE"));
				roRepairPartDetailDTO.setFromType(ttRoRepairPartPO.getInteger("FROM_TYPE"));
				roRepairPartDetailDTO.setOemLimitPrice(ttRoRepairPartPO.getDouble("OEM_LIMIT_PRICE"));
				roRepairPartDetailDTO.setUnitCode(ttRoRepairPartPO.getString("UNIT_CODE"));
				roRepairPartDetailDTO.setSender(ttRoRepairPartPO.getString("SENDER"));
				roRepairPartDetailDTO.setPosName(ttRoRepairPartPO.getString("POS_NAME"));
				roRepairPartDetailDTO.setItemId(ttRoRepairPartPO.getLong("ITEM_ID"));
				roRepairPartDetailDTO.setEntityCode(ttRoRepairPartPO.getString("DEALER_CODE")) ;
				roRepairPartDetailDTO.setPosCode(ttRoRepairPartPO.getString("POS_CODE"));
				roRepairPartDetailDTO.setManageSortCode(ttRoRepairPartPO.getString("MANAGE_SORT_CODE"));
				roRepairPartDetailDTO.setPartInfix(ttRoRepairPartPO.getString("PART_INFIX")) ;
				roRepairPartDetailDTO.setStorageCode(ttRoRepairPartPO.getString("STORAGE_CODE"));
				roRepairPartDetailDTO.setNeedlessRepair(ttRoRepairPartPO.getInteger("NEEDLESS_REPAIR"));
				roRepairPartDetailDTO.setDiscount(ttRoRepairPartPO.getDouble("DISCOUNT")) ;
				roRepairPartDetailDTO.setPreCheck(ttRoRepairPartPO.getInteger("PRE_CHECK"));
				roRepairPartDetailDTO.setPartSalesAmount(ttRoRepairPartPO.getDouble("PART_SALES_AMOUNT"));
				roRepairPartDetailDTO.setReason(ttRoRepairPartPO.getInteger("REASON"));
				roRepairPartDetailDTO.setReceiver(ttRoRepairPartPO.getString("RECEIVER")) ;
				roRepairPartDetailDTO.setPartNo(ttRoRepairPartPO.getString("PART_NO")) ;
				roRepairPartDetailDTO.setRepairTypeCode(ttRoRepairPartPO.getString("REPAIR_TYPE_CODE")) ;
				roRepairPartDetailDTO.setPrintBatchNo(ttRoRepairPartPO.getInteger("PRINT_BATCH_NO"));
				roRepairPartDetailDTO.setLabourCode(ttRoRepairPartPO.getString("LABOUR_CODE")) ;
				roRepairPartDetailDTO.setWarLevel(ttRoRepairPartPO.getInteger("WAR_LEVEL")) ;
				roRepairPartDetailDTO.setIsDiscount(ttRoRepairPartPO.getInteger("IS_DISCOUNT"));
				roRepairPartDetailDTO.setPriceType(ttRoRepairPartPO.getInteger("PRICE_TYPE"));
				roRepairPartDetailDTO.setAddTag(ttRoRepairPartPO.getInteger("ADD_TAG")) ;
				roRepairPartDetailDTO.setIsOldpartTreat(ttRoRepairPartPO.getInteger("IS_OLDPART_TREAT"));
				roRepairPartDetailDTO.setConsignExterior(ttRoRepairPartPO.getInteger("CONSIGN_EXTERIOR"));
				roRepairPartDetailDTO.setPrintRpTime(ttRoRepairPartPO.getDate("PRINT_RP_TIME"));
				resultList.add(roRepairPartDetailDTO);
			}
			opRepairOrderDTO.setRoRepairPartVoList(resultList);
		}
	}
	
	/**
	 * @description 将 RoAddItemPO包装成RoAddItemDetailDTO 然后填充进OpRepairOrderDTO
	 * @param entityCode
	 * @param orderVO
	 * @param roAddItemList
	 * @throws Exception
	 */
	private void addRoAddItem(String entityCode, OpRepairOrderDTO orderVO, List<RoAddItemPO> roAddItemList) throws Exception {
		if (roAddItemList != null && roAddItemList.size() > 0) {
			LinkedList<RoAddItemDetailDTO> resultList = new LinkedList<RoAddItemDetailDTO>();
			for (RoAddItemPO addItemPO : roAddItemList) {
				RoAddItemDetailDTO addItemVO = new RoAddItemDetailDTO();
				addItemVO.setAddItemAmount(addItemPO.getDouble("ADD_ITEM_AMOUNT"));
				addItemVO.setRemark(addItemPO.getString("REMARK"));
				addItemVO.setItemId(addItemPO.getLong("ITEM_ID"));
				addItemVO.setEntityCode(addItemPO.getString("DEALER_CODE"));
				addItemVO.setAddItemName(addItemPO.getString("ADD_ITEM_NAME"));
				addItemVO.setAddItemCode(addItemPO.getString("ADD_ITEM_CODE"));
				addItemVO.setActivityCode(addItemPO.getString("ACTIVITY_CODE"));
				addItemVO.setManageSortCode(addItemPO.getString("MANAGE_SORT_CODE"));
				addItemVO.setRoNo(addItemPO.getString("RO_NO"));
				addItemVO.setChargePartitionCode(addItemPO.getString("CHARGE_PARTITION_CODE"));
				addItemVO.setDdcnUpdateDate(addItemPO.getDate("DDCN_UPDATE_DATE"));
				addItemVO.setDiscount(addItemPO.getDouble("DISCOUNT"));
				resultList.add(addItemVO);
			}
			orderVO.setRoAddItemVoList(resultList);
		}
	}
	
	/**
	 * @description 先查询配件销售单，然后填充进OpRepairOrderDTO，
	 * @param dealerCode
	 * @param orderVO
	 * @param roAddRoSalesPartList
	 * @throws Exception
	 */
	private void addRoSalesPart(String dealerCode, OpRepairOrderDTO orderVO, List<TtSalesPartPO> roAddRoSalesPartList) throws Exception {
		if (roAddRoSalesPartList != null && roAddRoSalesPartList.size() > 0) {
			//转换配件销售单
			LinkedList<SalesPartDetailDTO> resultList = new LinkedList<SalesPartDetailDTO>();
			for (TtSalesPartPO addItemPO : roAddRoSalesPartList) {
				SalesPartDetailDTO addSalesPartVO = new SalesPartDetailDTO();
				addSalesPartVO.setEntityCode(addItemPO.getString("DEALER_CODE"));
				addSalesPartVO.setCustomerCode(addItemPO.getString("CUSTOMER_CODE"));
				addSalesPartVO.setSoNo(addItemPO.getString("SO_NO"));
				addSalesPartVO.setRemark1(addItemPO.getString("REMARK1"));
				addSalesPartVO.setCustomerName(addItemPO.getString("CUSTOMER_NAME"));
				addSalesPartVO.setVin(addItemPO.getString("VIN")) ;
				addSalesPartVO.setSalesPartAmount(addItemPO.getDouble("SALES_PART_AMOUNT"));
				addSalesPartVO.setSalesPartNo(addItemPO.getString("SALES_PART_NO"));
				addSalesPartVO.setRemark(addItemPO.getString("REMARK"));
				addSalesPartVO.setBalanceStatus(addItemPO.getInteger("BALANCE_STATUS"));
				addSalesPartVO.setLockUser(addItemPO.getString("LOCK_USER"));
				addSalesPartVO.setRoNo(addItemPO.getString("RO_NO"));
				addSalesPartVO.setDdcnUpdateDate(addItemPO.getDate("DDCN_UPDATE_DATE"));
				addSalesPartVO.setConsultant(addItemPO.getString("CONSULTANT"));
				resultList.add(addSalesPartVO);
				
				//根据配件销售单查询配件销售明细，并填充进工单
				LinkedList<SalesPartItemDetailDTO> resultListX = new LinkedList<SalesPartItemDetailDTO>();
				logger.debug("DEALER = "+dealerCode+" AND SALES_PART_NO = "+addItemPO+" AND D_KEY = "+CommonConstants.D_KEY);
				LazyList<TtSalesPartItemPO> roAddRoSalesPartItemList = TtSalesPartItemPO.findBySQL("DEALER = ? AND SALES_PART_NO = ? AND D_KEY = ?", 
							dealerCode, addItemPO.getString("Sales_Part_No"),CommonConstants.D_KEY);
				for (int j = 0; j < roAddRoSalesPartItemList.size(); j++) {
					TtSalesPartItemPO addSalesPartItemPO = (TtSalesPartItemPO) roAddRoSalesPartItemList.get(j);
					SalesPartItemDetailDTO addSalesPartItemVO = new SalesPartItemDetailDTO();
					
					addSalesPartItemVO.setPartCostAmount(addSalesPartItemPO.getDouble("PART_COST_AMOUNT"));
					addSalesPartItemVO.setSender(addSalesPartItemPO.getString("SENDER"));
					addSalesPartItemVO.setBatchNo(addSalesPartItemPO.getInteger("BATCH_NO"));
					addSalesPartItemVO.setPartName(addSalesPartItemPO.getString("PART_NAME"));
					addSalesPartItemVO.setSendTime(addSalesPartItemPO.getDate("SEND_TIME"));
					addSalesPartItemVO.setPartCostPrice(addSalesPartItemPO.getDouble("PART_COST_PRICE"));
					addSalesPartItemVO.setEntityCode(addSalesPartItemPO.getString("DEALER_CODE"));
					addSalesPartItemVO.setItemId(addSalesPartItemPO.getLong("ITEM_ID"));
					addSalesPartItemVO.setManageSortCode(addSalesPartItemPO.getString("MANAGESORT_CODE"));
					addSalesPartItemVO.setOtherPartCostPrice(addSalesPartItemPO.getDouble("OTHER_PART_COST_PRICE"));
					addSalesPartItemVO.setPriceRate(addSalesPartItemPO.getFloat("PRICE_RATE"));
					addSalesPartItemVO.setSalesDiscount(addSalesPartItemPO.getDouble("SALES_DISCOUNT")) ;
					addSalesPartItemVO.setSalesAmount(addSalesPartItemPO.getDouble("SALES_AMOUNT"));
					addSalesPartItemVO.setOtherPartCostAmount(addSalesPartItemPO.getDouble("OTHER_PART_COST_AMOUNT")) ;
					addSalesPartItemVO.setPartQuantity(addSalesPartItemPO.getFloat("PART_QUANTITY")) ;
					addSalesPartItemVO.setStorageCode(addSalesPartItemPO.getString("STORAGE_CODE")) ;
					addSalesPartItemVO.setFinishedDate(addSalesPartItemPO.getDate("FINISHED_DATE")) ;
					addSalesPartItemVO.setDiscount(addSalesPartItemPO.getDouble("DISCOUNT"));
					addSalesPartItemVO.setPartSalesPrice(addSalesPartItemPO.getDouble("PART_SALES_PRICE"));
					addSalesPartItemVO.setPartSalesAmount(addSalesPartItemPO.getDouble("PART_SALES_AMOUNT"));
					addSalesPartItemVO.setIsFinished(addSalesPartItemPO.getInteger("IS_FINISHED"));
					addSalesPartItemVO.setReceiver(addSalesPartItemPO.getString("RECEIVER")) ;
					addSalesPartItemVO.setPartBatchNo(addSalesPartItemPO.getString("PART_BATCH_NO"));
					addSalesPartItemVO.setSalesPartNo(addSalesPartItemPO.getString("SALES_PART_NO"));
					addSalesPartItemVO.setDxpDate(addSalesPartItemPO.getDate("DXP_DATE"));
					addSalesPartItemVO.setPartNo(addSalesPartItemPO.getString("PART_NO"));
					addSalesPartItemVO.setStoragePositionCode(addSalesPartItemPO.getString("STORAGE_POSITION_CODE"));
					addSalesPartItemVO.setIsDiscount(addSalesPartItemPO.getInteger("IS_DISCOUNT"));
					addSalesPartItemVO.setPriceType(addSalesPartItemPO.getInteger("PRICE_TYPE"));
					addSalesPartItemVO.setChargePartitionCode(addSalesPartItemPO.getString("CHARGE_PARTITION_CODE"));
					addSalesPartItemVO.setDdcnUpdateDate(addSalesPartItemPO.getDate("DDCN_UPDATE_DATE"));
					addSalesPartItemVO.setOldSalesPartNo(addSalesPartItemPO.getString("OLD_SALES_PART_NO"));
					addSalesPartItemVO.setOemLimitPrice(addSalesPartItemPO.getDouble("OEM_LIMIT_PRICE"));
					addSalesPartItemVO.setUnitCode(addSalesPartItemPO.getString("UNIT_CODE"));
					resultListX.add(addSalesPartItemVO);
				}
				orderVO.setSalesPartItemVoList(resultListX);
			}
			orderVO.setSalesPartVoList(resultList);
		}
	}
}
