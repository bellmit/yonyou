package com.yonyou.dms.gacfca;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;
import org.springframework.stereotype.Service;

import com.yonyou.dms.DTO.gacfca.RoNotSettleAccountsAddItemDTO;
import com.yonyou.dms.DTO.gacfca.RoNotSettleAccountsLabourDTO;
import com.yonyou.dms.DTO.gacfca.RoNotSettleAccountsReasonDTO;
import com.yonyou.dms.DTO.gacfca.RoNotSettleAccountsRepairOrderDTO;
import com.yonyou.dms.DTO.gacfca.RoNotSettleAccountsRepairPartDTO;
import com.yonyou.dms.common.Util.Utility;
import com.yonyou.dms.common.domains.PO.basedata.EmployeePo;
import com.yonyou.dms.common.domains.PO.basedata.RepairOrderPO;
import com.yonyou.dms.common.domains.PO.basedata.RoAddItemPO;
import com.yonyou.dms.common.domains.PO.basedata.TmBalanceModeAddItemPO;
import com.yonyou.dms.common.domains.PO.basedata.TmPartInfoPO;
import com.yonyou.dms.common.domains.PO.basedata.TmRepairItemPO;
import com.yonyou.dms.common.domains.PO.basedata.TmRepairTypePO;
import com.yonyou.dms.common.domains.PO.basedata.TmVehiclePO;
import com.yonyou.dms.common.domains.PO.basedata.TtActivityPO;
import com.yonyou.dms.common.domains.PO.basedata.TtRoLabourPO;
import com.yonyou.dms.common.domains.PO.basedata.TtRoRepairPartPO;
import com.yonyou.dms.common.domains.PO.basedata.TtRoTimeoutCausePO;
import com.yonyou.dms.common.domains.PO.basedata.TtRoTimeoutDetailPO;
import com.yonyou.dms.common.domains.PO.basedata.TtShortPartPO;
import com.yonyou.dms.common.domains.PO.stockmanage.CarownerPO;
import com.yonyou.dms.function.common.CommonConstants;

/**
 * @description 经销商上报未结算的索赔工单
 * @author Administrator
 *
 */
@Service
public class SEDMS012CoudImpl implements SEDMS012Coud {
	final Logger logger = Logger.getLogger(SEDMS012CoudImpl.class);

	@Override
	public List<RoNotSettleAccountsRepairOrderDTO> getSEDMS012(String dealerCode, Long userId, String roNo,String roNoInRepair) {
		logger.info("==========SEDMS012Impl执行===========");
		List<RoNotSettleAccountsRepairOrderDTO> resultList = new ArrayList<RoNotSettleAccountsRepairOrderDTO>();
		try {
			//填写超时原因时上报逻辑判定
			if (Utility.testString(roNo)) {
				logger.debug("from RepairOrderPO DEALER_CODE = "+dealerCode+" and RO_NO = "+roNo+" and D_KEY = "+CommonConstants.D_KEY);
				LazyList<RepairOrderPO> repairOrderList = RepairOrderPO.findBySQL("DEALER_CODE = ? and RO_NO = ? and D_KEY = ?",
						dealerCode,roNo,CommonConstants.D_KEY);
				RepairOrderPO orderPO =  repairOrderList.get(0);
				RoNotSettleAccountsRepairOrderDTO roNotSettleAccountsRepairOrderDTO = setRepairOrder(dealerCode, orderPO);
				logger.debug("from TtRoLabourPO DEALER_CODE = "+dealerCode+" and RO_NO = "+roNo+" and D_KEY = "+CommonConstants.D_KEY);
				List<TtRoLabourPO> roLabourList =	TtRoLabourPO.findBySQL("DEALER_CODE = ? and RO_NO = ? and D_KEY = ?", dealerCode,roNo,CommonConstants.D_KEY);
				logger.debug("from TtRoRepairPartPO DEALER_CODE = "+dealerCode+" and RO_NO = "+roNo+" and D_KEY = "+CommonConstants.D_KEY);
				List<TtRoRepairPartPO> roRepairPartList = TtRoRepairPartPO.findBySQL("DEALER_CODE = ? and RO_NO = ? and D_KEY = ?", dealerCode,roNo,CommonConstants.D_KEY);
				logger.debug("from RoAddItemPO DEALER_CODE = "+dealerCode+" and RO_NO = "+roNo+" and D_KEY = "+CommonConstants.D_KEY);
				List<RoAddItemPO> roAddItemList = RoAddItemPO.findBySQL("DEALER_CODE = ? and RO_NO = ? and D_KEY = ?",  dealerCode,roNo,CommonConstants.D_KEY);
				logger.debug("from TtShortPartPO DEALER_CODE = "+dealerCode+" and RO_NO = "+roNo+" and D_KEY = "+CommonConstants.D_KEY);
				List<TtShortPartPO> roAddShortPartList = TtShortPartPO.findBySQL("DEALER_CODE = ? and RO_NO = ? and D_KEY = ?",  dealerCode,roNo,CommonConstants.D_KEY);
				logger.debug("from TtRoTimeoutCausePO DEALER_CODE = "+dealerCode+" and RO_NO = "+roNo+" and D_KEY = "+CommonConstants.D_KEY);
				List<TtRoTimeoutCausePO> roTimeoutCauseList = TtRoTimeoutCausePO.findBySQL("DEALER_CODE = ? and RO_NO = ? and D_KEY = ?",  dealerCode,roNo,CommonConstants.D_KEY);
				addRoLabour(dealerCode, roNotSettleAccountsRepairOrderDTO, roLabourList);
				addRoRepairPart(dealerCode, roNotSettleAccountsRepairOrderDTO, roRepairPartList, orderPO, roAddShortPartList);
				addRoAddItem(dealerCode, roNotSettleAccountsRepairOrderDTO, roAddItemList);
				addNotSettleAccountsReason(dealerCode, roNotSettleAccountsRepairOrderDTO, roTimeoutCauseList);
				resultList.add(roNotSettleAccountsRepairOrderDTO);
				if (repairOrderList != null && repairOrderList.size() > 0) {
					RepairOrderPO.update("IS_TIMEOUT_SUBMIT = ?, UPDATE_AT = ?",
							"DEALER_CODE = ? and RO_NO = ? and RO_TYPE = ? and D_KEY = ? ", 
							Integer.parseInt(CommonConstants.DICT_IS_YES),Utility.getCurrentDateTime(),
							dealerCode,roNo,Integer.parseInt(CommonConstants.DICT_RPT_CLAIM),CommonConstants.D_KEY);
				}
			} else {
				logger.debug("工单号为空，退出");
			}
			return resultList;
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(e);
			return null;
		}finally{
			logger.info("==========SEDMS012Impl结束===========");
		}
	}

	/**
	 * @description 将工单PO包装成 RoNotSettleAccountsRepairOrderDTO
	 * @param con
	 * @param dealerCode
	 * @param repairOrderPO
	 * @return
	 * @throws Exception
	 */
	private RoNotSettleAccountsRepairOrderDTO setRepairOrder(String dealerCode, RepairOrderPO repairOrderPO) throws Exception {
		RoNotSettleAccountsRepairOrderDTO roNotSettleAccountsRepairOrderDTO = new RoNotSettleAccountsRepairOrderDTO();
		roNotSettleAccountsRepairOrderDTO.setEntityCode(dealerCode);//微信需要
		roNotSettleAccountsRepairOrderDTO.setRoNo(repairOrderPO.getString("RO_NO"));//微信需要
		if (CommonConstants.DICT_RPT_CLAIM.equals(String.valueOf(repairOrderPO.getInteger("RO_TYPE")))) {
			roNotSettleAccountsRepairOrderDTO.setIsClaim(Integer.parseInt(CommonConstants.DICT_IS_YES));
		} else {
			roNotSettleAccountsRepairOrderDTO.setIsClaim(Integer.parseInt(CommonConstants.DICT_IS_NO));
		}
		String repairCode = repairOrderPO.getString("REPAIR_TYPE_CODE");
		// 服务活动算作保修 保养套餐作为保养
		if ("SCBY".equals(repairCode) || "CGBY".equals(repairCode) || "DQBY".equals(repairCode)) {
			roNotSettleAccountsRepairOrderDTO.setRoType(40041001);
			roNotSettleAccountsRepairOrderDTO.setRepairTypeCode("40011001");//微信需要
		} else {
			roNotSettleAccountsRepairOrderDTO.setRoType(40041002);
			if ("SQWX".equals(repairCode)) {
				roNotSettleAccountsRepairOrderDTO.setRepairTypeCode("40011002");
			} else if ("FWHD".equals(repairCode)) {
				roNotSettleAccountsRepairOrderDTO.setRepairTypeCode("40011004");
			} else if ("WBFX".equals(repairCode)) {
				roNotSettleAccountsRepairOrderDTO.setRepairTypeCode("40011003");
			} else {
				roNotSettleAccountsRepairOrderDTO.setRepairTypeCode("40011001");
			}
		}
		if (Utility.testString(repairOrderPO.getInteger("RO_TYPE"))){
			roNotSettleAccountsRepairOrderDTO.setNewOrderType(repairOrderPO.getInteger("RO_TYPE").toString());
		}
		roNotSettleAccountsRepairOrderDTO.setNewRepairType(repairOrderPO.getString("REPAIR_TYPE_CODE"));
		roNotSettleAccountsRepairOrderDTO.setRepairTypeName(getRepairCodeByName(dealerCode, repairOrderPO.getString("REPAIR_TYPE_CODE")));
		roNotSettleAccountsRepairOrderDTO.setServiceAdvisor(getEmployeeName(dealerCode, repairOrderPO.getString("SERVICE_ADVISOR")));//微信需要
		roNotSettleAccountsRepairOrderDTO.setRoStatus(repairOrderPO.getInteger("RO_STATUS")==12551010 ? 40021002 : 40021001);
		roNotSettleAccountsRepairOrderDTO.setRoCreateDate(repairOrderPO.getDate("RO_CREATE_DATE"));//微信需要
		roNotSettleAccountsRepairOrderDTO.setVin(repairOrderPO.getString("VIN"));//微信需要
		roNotSettleAccountsRepairOrderDTO.setInMileage(repairOrderPO.getDouble("IN_MILEAGE"));
		//出厂里程 //微信需要
		roNotSettleAccountsRepairOrderDTO.setOutMileage(repairOrderPO.getDouble("OUT_MILEAGE"));
		roNotSettleAccountsRepairOrderDTO.setDeliverer(repairOrderPO.getString("DELIVERER"));
		roNotSettleAccountsRepairOrderDTO.setDelivererPhone(repairOrderPO.getString("DELIVERER_MOBILE")==null?repairOrderPO.getString("DELIVERER_PHONE"):repairOrderPO.getString("DELIVERER_MOBILE"));
		roNotSettleAccountsRepairOrderDTO.setForBalanceTime(repairOrderPO.getDate("FOR_BALANCE_TIME"));
		roNotSettleAccountsRepairOrderDTO.setLabourPrice(repairOrderPO.getFloat("LABOUR_PRICE"));
		roNotSettleAccountsRepairOrderDTO.setLabourAmount(repairOrderPO.getDouble("LABOUR_AMOUNT"));
		roNotSettleAccountsRepairOrderDTO.setRepairPartAmount(repairOrderPO.getDouble("REPAIR_PART_AMOUNT"));
		roNotSettleAccountsRepairOrderDTO.setAdditemAmount(repairOrderPO.getDouble("ADD_ITEM_AMOUNT"));
		roNotSettleAccountsRepairOrderDTO.setRemark(repairOrderPO.getString("REMARK"));
		roNotSettleAccountsRepairOrderDTO.setDownTimestamp(new Date());
		roNotSettleAccountsRepairOrderDTO.setIsValid(Integer.parseInt(CommonConstants.DICT_VALIDS_VALID));
		if (Utility.testString(repairOrderPO.getString("BOOKING_ORDER_NO"))){
			roNotSettleAccountsRepairOrderDTO.setBookingOrderNo(repairOrderPO.getString("BOOKING_ORDER_NO"));
		}
		//获取车主信息
		CarownerPO ownerPO = CarownerPO.findByCompositeKeys( repairOrderPO.getString("OWNER_NO"),dealerCode);
		if (ownerPO != null) {
			roNotSettleAccountsRepairOrderDTO.setCustomerAddress(ownerPO.getString("ADDRESS"));
		}
		//获取车辆信息
		TmVehiclePO vehiclePO = TmVehiclePO.findByCompositeKeys(repairOrderPO.getString("VIN"),dealerCode);
		if (vehiclePO != null) {
			roNotSettleAccountsRepairOrderDTO.setRepairDate(vehiclePO.getDate("RO_CREATE_DATE"));
		}
		roNotSettleAccountsRepairOrderDTO.setFaultDate(repairOrderPO.getDate("TROUBLE_OCCUR_DATE"));
		roNotSettleAccountsRepairOrderDTO.setFinishDate(repairOrderPO.getDate("COMPLETE_TIME"));//added by liufeilu  in 20130603

		if (roNotSettleAccountsRepairOrderDTO.getFaultDate() != null && roNotSettleAccountsRepairOrderDTO.getRepairDate() != null) {
			long betweenDays = (roNotSettleAccountsRepairOrderDTO.getRepairDate().getTime() - roNotSettleAccountsRepairOrderDTO.getFaultDate().getTime()) / 1000 / 24/ 60 / 60;
			if (betweenDays > 0) {
				roNotSettleAccountsRepairOrderDTO.setBetweenDays((int) betweenDays);
			}
		}
		return roNotSettleAccountsRepairOrderDTO;
	}

	/**
	 * @description 根据id查询维修类型名称
	 * @param dealerCode
	 * @param repairTypeCode
	 * @return
	 */
	private String getRepairCodeByName(String dealerCode, String repairTypeCode) {
		String returnValue = null;
		if (repairTypeCode != null && !"".equals(repairTypeCode.trim())) {
			logger.debug("from TmRepairTypePO DEALER_CODE = "+dealerCode+" and REPAIR_TYPE_CODE = "+repairTypeCode);
			LazyList<TmRepairTypePO> list = TmRepairTypePO.findBySQL("DEALER_CODE = ? and REPAIR_TYPE_CODE = ?", dealerCode,repairTypeCode);
			if (list != null && !list.isEmpty()) {
				TmRepairTypePO repairTypePO = (TmRepairTypePO) list.get(0);
				returnValue = repairTypePO.getString("REPAIR_TYPE_NAME");
			}
		}
		return returnValue;
	}

	/**
	 * @description 查询员工信息,返回员工姓名
	 * @param dealerCode
	 * @param employeeNo
	 * @return
	 */
	private String getEmployeeName(String dealerCode, String employeeNo) {
		String returnValue = null;
		if (employeeNo != null && !"".equals(employeeNo.trim())) {
			logger.debug("from EmployeePo DEALER_CODE = "+dealerCode+" and EMPLOYEE_NO = "+employeeNo);
			LazyList<EmployeePo> list = EmployeePo.findBySQL("DEALER_CODE = ? and EMPLOYEE_NO = ?", dealerCode,employeeNo);
			if (list != null && list.size() > 0) {
				EmployeePo employeePO = list.get(0);
				returnValue = employeePO.getString("EMPLOYEE_NAME");
			}
		}
		return returnValue;
	}

	/**
	 * @description 将维修项目明细包装到RoNotSettleAccountsLabourDTO中
	 * @param dealerCode
	 * @param orderVO
	 * @param roLabourList
	 * @throws Exception
	 */
	private void addRoLabour(String dealerCode, RoNotSettleAccountsRepairOrderDTO orderVO, List<TtRoLabourPO> roLabourList) throws Exception {
		if (roLabourList != null && roLabourList.size() > 0) {
			LinkedList<RoNotSettleAccountsLabourDTO> resultList = new LinkedList<RoNotSettleAccountsLabourDTO>();
			for (TtRoLabourPO labourPO : roLabourList) {
				// 工时数是0的保养套餐维修项目不用上传
				if ("0000000000".equals(labourPO.getString("LABOUR_CODE"))) {
					continue;
				}
				RoNotSettleAccountsLabourDTO roNotSettleAccountsLabourDTO = new RoNotSettleAccountsLabourDTO();
				logger.debug("from TmRepairItemPO DEALER_CODE = "+dealerCode+" and DOWN_TAG = "+Utility.getInt(CommonConstants.DICT_IS_YES)+" and LABOUR_CODE = "+labourPO.getString("Labour_Code")+" and MODEL_LABOUR_CODE = "+labourPO.getString("MODEL_LABOUR_CODE"));
				List <TmRepairItemPO> listitem  = TmRepairItemPO.findBySQL("DEALER_CODE = ? and DOWN_TAG = ? and LABOUR_CODE = ? and MODEL_LABOUR_CODE = ?", 
						dealerCode,Utility.getInt(CommonConstants.DICT_IS_YES),labourPO.getString("LABOUR_CODE"),labourPO.getString("MODEL_LABOUR_CODE"));
				if(listitem!=null && listitem.size()>0){
					roNotSettleAccountsLabourDTO.setOemTag(Utility.getInt(CommonConstants.DICT_IS_YES));
				} else {
					roNotSettleAccountsLabourDTO.setOemTag(Utility.getInt(CommonConstants.DICT_IS_NO));
				}
				//				加上oem标志
				roNotSettleAccountsLabourDTO.setLabourCode(labourPO.getString("LABOUR_CODE"));
				roNotSettleAccountsLabourDTO.setLabourName(labourPO.getString("LABOUR_NAME"));
				roNotSettleAccountsLabourDTO.setStdLabourHour(labourPO.getDouble("STD_LABOUR_HOUR"));
				roNotSettleAccountsLabourDTO.setRemark(labourPO.getString("REMARK"));
				roNotSettleAccountsLabourDTO.setActivityCode(labourPO.getString("ACTIVITY_CODE"));
				if (CommonConstants.DICT_IS_YES.equals(String.valueOf(orderVO.getIsClaim()))) {
					if (labourPO.getString("ACTIVITY_CODE") != null && !"".equals(labourPO.getString("ACTIVITY_CODE").trim())) {
						if (orderVO.getPackageCode() == null || "".equals(orderVO.getPackageCode().trim())) {
							orderVO.setPackageCode(labourPO.getString("ACTIVITY_CODE"));
							logger.debug("from TtActivityPO DEALER_CODE = "+dealerCode+" and ACTIVITY_CODE = "+labourPO.getString("ACTIVITY_CODE")+" and D_KEY = "+CommonConstants.D_KEY);
							LazyList<TtActivityPO> ttActivityPOs = TtActivityPO.findBySQL("DEALER_CODE = ? and ACTIVITY_CODE = ? and D_KEY = ?",
									dealerCode,labourPO.getString("ACTIVITY_CODE"),CommonConstants.D_KEY);
							if(ttActivityPOs != null)
								orderVO.setPackageName(ttActivityPOs.get(0).getString("ACTIVITY_NAME"));
						}
					}
				}
				resultList.add(roNotSettleAccountsLabourDTO);
			}
			orderVO.setLabourVoList(resultList);
		}
	}

	/**
	 * @description 查询 配件缺料记录表，并填充进RoNotSettleAccountsRepairOrderDTO
	 * @param dealerCode
	 * @param roNotSettleAccountsRepairOrderDTO
	 * @param roRepairPartList
	 * @param orderPO
	 * @param roAddShortPartList
	 */
	private void addRoRepairPart(String dealerCode, RoNotSettleAccountsRepairOrderDTO roNotSettleAccountsRepairOrderDTO, List<TtRoRepairPartPO> roRepairPartList,RepairOrderPO orderPO,List<TtShortPartPO> roAddShortPartList) {

		LinkedList<RoNotSettleAccountsRepairPartDTO> resultList = new LinkedList<RoNotSettleAccountsRepairPartDTO>();
		if (roRepairPartList != null && roRepairPartList.size() > 0) {
			for (int i = 0; i < roRepairPartList.size(); i++) {
				TtRoRepairPartPO partPO = (TtRoRepairPartPO) roRepairPartList.get(i);
				RoNotSettleAccountsRepairPartDTO roNotSettleAccountsRepairPartDTO = new RoNotSettleAccountsRepairPartDTO();
				logger.debug("from TmPartInfoPO DEALER_CODE = "+dealerCode+" and PART_NO = "+partPO.get("Part_No")+" and D_KEY = "+CommonConstants.D_KEY);
				List<TmPartInfoPO> list2 = TmPartInfoPO.findBySQL("DEALER_CODE = ? and PART_NO = ? and D_KEY = ?", 
						dealerCode,partPO.get("PART_NO"),CommonConstants.D_KEY);
				if(list2 != null && !list2.isEmpty()){
					TmPartInfoPO partInfoPO2 = list2.get(0);
					roNotSettleAccountsRepairPartDTO.setOemTag(partInfoPO2.getInteger("OEM_TAG"));
				}//add  lim oem 标志传去

				roNotSettleAccountsRepairPartDTO.setPartNo(partPO.getString("PART_NO"));
				roNotSettleAccountsRepairPartDTO.setPartName(partPO.getString("PART_NAME"));
				roNotSettleAccountsRepairPartDTO.setPartQuantity(partPO.getFloat("PART_QUANTITY"));
				roNotSettleAccountsRepairPartDTO.setPartSalesPrice(partPO.getDouble("PART_SALES_PRICE"));
				roNotSettleAccountsRepairPartDTO.setLabourCode(partPO.getString("LABOUR_CODE"));
				roNotSettleAccountsRepairPartDTO.setIsMain(partPO.getInteger("IS_MAIN_PART"));
				roNotSettleAccountsRepairPartDTO.setPackageQuantity(partPO.getDouble("PART_QUANTITY"));
				roNotSettleAccountsRepairPartDTO.setActivityCode(partPO.getString("ACTIVITY_CODE")); //added by liufeilu in 20130603
				if(roAddShortPartList != null && !roAddShortPartList.isEmpty()){
					roNotSettleAccountsRepairPartDTO.setLackMaterial("10041002");
					for(TtShortPartPO shortPartPO : roAddShortPartList){
						if (shortPartPO.getString("PART_NO").equals(partPO.getString("PART_NO"))){
							roNotSettleAccountsRepairPartDTO.setLackMaterial("10041001");
							break;
						}
					}
				} else {
					roNotSettleAccountsRepairPartDTO.setLackMaterial("10041002");
				}
				resultList.add(roNotSettleAccountsRepairPartDTO);
			}
			roNotSettleAccountsRepairOrderDTO.setRepairPartVoList(resultList);
		}
	}

	/**
	 * @description 将附加项目包装进RoNotSettleAccountsRepairOrderDTO
	 * @param dealerCode
	 * @param roNotSettleAccountsRepairOrderDTO
	 * @param roAddItemList
	 * @throws Exception
	 */
	private void addRoAddItem(String dealerCode, RoNotSettleAccountsRepairOrderDTO roNotSettleAccountsRepairOrderDTO, List<RoAddItemPO> roAddItemList) throws Exception {
		if (roAddItemList != null && roAddItemList.size() > 0) {
			LinkedList<RoNotSettleAccountsAddItemDTO> resultList = new LinkedList<RoNotSettleAccountsAddItemDTO>();
			for (RoAddItemPO addItemPO : roAddItemList) {
				//增加判断，如果不是上端下发就不上报
				RoNotSettleAccountsAddItemDTO addItemVO = new RoNotSettleAccountsAddItemDTO();
				logger.debug("from TmBalanceModeAddItemPO DEALER_CODE = "+dealerCode+" and ADD_ITEM_CODE = "+addItemPO.getString("ADD_ITEM_CODE"));
				List<TmBalanceModeAddItemPO> list = TmBalanceModeAddItemPO.findBySQL("DEALER_CODE = ? and ADD_ITEM_CODE = ?", dealerCode,addItemPO.getString("ADD_ITEM_CODE"));
				if(list != null && !list.isEmpty() && CommonConstants.DICT_IS_YES.equals(list.get(0).getInteger("IS_VALID").toString())){
					addItemVO.setOemTag(Utility.getInt(CommonConstants.DICT_IS_NO));
				}else{
					addItemVO.setOemTag(Utility.getInt(CommonConstants.DICT_IS_YES));
				}
				addItemVO.setAddItemCode(addItemPO.getString("ADD_ITEM_CODE"));
				addItemVO.setAddItemName(addItemPO.getString("ADD_ITEM_NAME"));
				addItemVO.setAddItemAmount(addItemPO.getDouble("ADD_ITEM_AMOUNT"));
				addItemVO.setRemark(addItemPO.getString("REMARK"));
				addItemVO.setActivityCode(addItemPO.getString("ACTIVITY_CODE"));  //added by liufeilu in 20130603
				resultList.add(addItemVO);
			}
			roNotSettleAccountsRepairOrderDTO.setAddItemVoList(resultList);
		}
	}

	/**
	 * @description 查询未结算超时工单，并填充进RoNotSettleAccountsRepairOrderDTO
	 * @param dealerCode
	 * @param orderVO
	 * @param roTimeoutCauseList
	 * @throws Exception
	 */
	private void addNotSettleAccountsReason(String dealerCode, RoNotSettleAccountsRepairOrderDTO orderVO, List<TtRoTimeoutCausePO> roTimeoutCauseList) throws Exception {
		LinkedList<RoNotSettleAccountsReasonDTO> resultList = new LinkedList<RoNotSettleAccountsReasonDTO>();
		if (roTimeoutCauseList != null && roTimeoutCauseList.size() > 0) {
			TtRoTimeoutCausePO timeoutCausePO = roTimeoutCauseList.get(0);
			String employeeName = null;
			logger.debug("from TtRoTimeoutDetailPO DEALER_CODE = "+dealerCode+" and ITEM_ID = "+timeoutCausePO.getLong("ITEM_ID")+" and D_KEY = "+CommonConstants.D_KEY);
			List<TtRoTimeoutDetailPO> roTimeoutDetailList = TtRoTimeoutDetailPO.findBySQL("DEALER_CODE = ? and ITEM_ID = ? and D_KEY = ?",
					dealerCode, timeoutCausePO.getLong("ITEM_ID"),CommonConstants.D_KEY);
			if (roTimeoutDetailList != null && !roTimeoutDetailList.isEmpty()) {
				employeeName = getEmployeeName(dealerCode, timeoutCausePO.getString("OWNED_BY"));
				for (TtRoTimeoutDetailPO timeoutDetailPO : roTimeoutDetailList) {
					RoNotSettleAccountsReasonDTO roNotSettleAccountsReasonVO = new RoNotSettleAccountsReasonDTO();
					roNotSettleAccountsReasonVO.setRoNo(timeoutCausePO.getString("RO_NO"));
					roNotSettleAccountsReasonVO.setOperator(employeeName);
					if (Utility.testString(timeoutCausePO.getInteger("WORK_STATUS"))) {
						roNotSettleAccountsReasonVO.setWaitWorkReason(String.valueOf(timeoutCausePO.getInteger("WORK_STATUS")));
					}
					if (Utility.testString(timeoutCausePO.getString("REMARK"))) {
						roNotSettleAccountsReasonVO.setWaitWorkRemark(timeoutCausePO.getString("REMARK"));
					}
					roNotSettleAccountsReasonVO.setWaitMaterialPartCode(timeoutDetailPO.getString("PART_NO"));
					roNotSettleAccountsReasonVO.setWaitMaterialPartName(timeoutDetailPO.getString("PART_NAME"));
					roNotSettleAccountsReasonVO.setWaitMaterialPartQuantity(timeoutDetailPO.getFloat("PART_QUANTITY"));
					roNotSettleAccountsReasonVO.setWaitMaterialOrderNo(timeoutDetailPO.getString("PURCHASE_ORDER_NO"));
					roNotSettleAccountsReasonVO.setWaitMaterialOrderDate(timeoutDetailPO.getDate("ORDER_DATE"));
					if (Utility.testString(timeoutDetailPO.getString("REMARK"))) {
						roNotSettleAccountsReasonVO.setWaitMaterialRemark(timeoutDetailPO.getString("REMARK"));
					}
					resultList.add(roNotSettleAccountsReasonVO);
				}
				orderVO.setNotSettleAccountsReason(resultList);
			} else {
				employeeName = getEmployeeName(dealerCode, timeoutCausePO.getString("OWNED_BY"));
				RoNotSettleAccountsReasonDTO roNotSettleAccountsReasonVO = new RoNotSettleAccountsReasonDTO();
				roNotSettleAccountsReasonVO.setRoNo(timeoutCausePO.getString("RO_NO"));
				roNotSettleAccountsReasonVO.setOperator(employeeName);
				if (Utility.testString(timeoutCausePO.getInteger("WORK_STATUS"))) {
					roNotSettleAccountsReasonVO.setWaitWorkReason(String.valueOf(timeoutCausePO.getInteger("WORK_STATUS")));
				}
				if (Utility.testString(timeoutCausePO.getString("REMARK"))) {
					roNotSettleAccountsReasonVO.setWaitWorkRemark(timeoutCausePO.getString("REMARK"));
				}
				resultList.add(roNotSettleAccountsReasonVO);
				orderVO.setNotSettleAccountsReason(resultList);
			}
		}
	}
}
