package com.yonyou.dms.gacfca;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.javalite.activejdbc.LazyList;
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
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * @description 计划任务上报超过48小时未结算的索赔工单
 * @author Administrator
 *
 */
@Service
public class SADMS012Impl implements SADMS012 {
	
	final Logger logger = Logger.getLogger(SADMS012Impl.class);

	/**
	 *  @description 计划任务上报超过48小时未结算的索赔工单
	 *  @param dealerCode
	 */
	@Override
	public LinkedList<RoNotSettleAccountsRepairOrderDTO> getSADMS012(String dealerCode) throws ServiceBizException {
		logger.info("==========SADMS012Impl执行===========");
		try {
			if(StringUtils.isEmpty(dealerCode)){
				logger.debug("dealerCode 为空，方法中断");
				return null;
			}
			String sql = getSql(dealerCode);
			//根据 dealerCode 查询所有的 在修或已提交 工单编号
			List<Map> resultMaps = OemDAOUtil.findAll(sql, null);
			LinkedList<RoNotSettleAccountsRepairOrderDTO> resultList = new LinkedList<RoNotSettleAccountsRepairOrderDTO>();
			String roNo = null;
			if(resultMaps != null && !resultMaps.isEmpty()){
				for (Map map : resultMaps) {
					roNo = map.get("RO_NO").toString();
					if(Utility.testString(roNo)){
						//查询 维修工单
						logger.debug("from RepairOrderPO DEALER_CODE = "+dealerCode+" and RO_NO = "+roNo+" and D_KEY = "+CommonConstants.D_KEY);
						LazyList<RepairOrderPO> repairOrderPOs = RepairOrderPO.findBySQL("DEALER_CODE = ? and RO_NO = ? and D_KEY = ?", 
								dealerCode,roNo,CommonConstants.D_KEY);
						RepairOrderPO repairOrderPO = null;
						if(repairOrderPOs != null && !repairOrderPOs.isEmpty()){
							repairOrderPO = repairOrderPOs.get(0);
						}
						RoNotSettleAccountsRepairOrderDTO roNotSettleAccountsRepairOrderDTO = setRepairOrder(dealerCode, repairOrderPO);
						//查询维修工单项目明细
						logger.debug("from TtRoLabourPO DEALER_CODE = "+dealerCode+" and RO_NO = "+roNo+" and D_KEY = "+CommonConstants.D_KEY);
						LazyList<TtRoLabourPO> ttRoLabourPOs = TtRoLabourPO.findBySQL("DEALER_CODE = ? and RO_NO = ? and D_KEY = ? ", 
								dealerCode, roNo, CommonConstants.D_KEY);
						//查询维修工单配件明细
						logger.debug("from TtRoRepairPartPO DEALER_CODE = "+dealerCode+" and RO_NO = "+roNo+" and D_KEY = "+CommonConstants.D_KEY);
						LazyList<TtRoRepairPartPO> ttRoRepairPartPOs = TtRoRepairPartPO.findBySQL("DEALER_CODE = ? and RO_NO = ? and D_KEY = ? ", 
								dealerCode, roNo, CommonConstants.D_KEY);
						//查询 附加项目
						logger.debug("from RoAddItemPO DEALER_CODE = "+dealerCode+" and RO_NO = "+roNo+" and D_KEY = "+CommonConstants.D_KEY);
						LazyList<RoAddItemPO> roAddItemPOs = RoAddItemPO.findBySQL("DEALER_CODE = ? and RO_NO = ? and D_KEY = ? ", 
								dealerCode, roNo, CommonConstants.D_KEY);
						//查询 配件缺料明细
						logger.debug("from TtShortPartPO DEALER_CODE = "+dealerCode+" and RO_NO = "+roNo+" and D_KEY = "+CommonConstants.D_KEY);
						LazyList<TtShortPartPO> ttShortPartPOs = TtShortPartPO.findBySQL("DEALER_CODE = ? and RO_NO = ? and D_KEY = ? ", 
								dealerCode, roNo, CommonConstants.D_KEY);
						//查询 未结算索赔工单超时说明
						logger.debug("from TtRoTimeoutCausePO DEALER_CODE = "+dealerCode+" and RO_NO = "+roNo+" and D_KEY = "+CommonConstants.D_KEY);
						LazyList<TtRoTimeoutCausePO> ttRoTimeoutCausePOs = TtRoTimeoutCausePO.findBySQL("DEALER_CODE = ? and RO_NO = ? and D_KEY = ? ", 
								dealerCode, roNo, CommonConstants.D_KEY);
						//将数据填充进 RoNotSettleAccountsRepairOrderDTO 用于返回
						addRoLabour(dealerCode, roNotSettleAccountsRepairOrderDTO, ttRoLabourPOs);
						addRoRepairPart(dealerCode, roNotSettleAccountsRepairOrderDTO, ttRoRepairPartPOs, repairOrderPO, ttRoLabourPOs, ttShortPartPOs);
						addRoAddItem(dealerCode, roNotSettleAccountsRepairOrderDTO, roAddItemPOs);
						addNotSettleAccountsReason( dealerCode, roNotSettleAccountsRepairOrderDTO, ttRoTimeoutCausePOs);
						resultList.add(roNotSettleAccountsRepairOrderDTO);
						
						//更新  工单数据
						logger.debug("upadte RepairOrderPO set IS_TIMEOUT_SUBMIT = "+CommonConstants.DICT_IS_YES+",UPDATE_AT = "+new Date()
								+ " DEALER_CODE = "+dealerCode+" and RO_NO = "+roNo+" and D_KEY = "+CommonConstants.D_KEY);
						RepairOrderPO.update("IS_TIMEOUT_SUBMIT = ?,UPDATE_AT = ?", 
								"DEALER_CODE = ? and RO_NO = ? and RO_TYPE = ? and D_KEY = ?", 
								Integer.parseInt(CommonConstants.DICT_IS_YES),new Date(),dealerCode,roNo,Integer.parseInt(CommonConstants.DICT_RPT_CLAIM),CommonConstants.D_KEY);
					}
				}
			}
			return resultList;
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(e);
			return null;
		}finally{
			logger.info("==========SADMS012Impl结束===========");
		}
	}

	/**
	 * @description 填充 RoNotSettleAccountsRepairOrderDTO 的  TtRoTimeoutCausePO(未结算索赔工单超时说明主表 ) 集合
	 * @param con
	 * @param dealerCode
	 * @param roNotSettleAccountsRepairOrderDTO
	 * @param ttRoTimeoutCausePO
	 * @throws Exception
	 */
	private void addNotSettleAccountsReason(String dealerCode, RoNotSettleAccountsRepairOrderDTO roNotSettleAccountsRepairOrderDTO, List<TtRoTimeoutCausePO> ttRoTimeoutCausePO) throws Exception {
		if (ttRoTimeoutCausePO != null && !ttRoTimeoutCausePO.isEmpty()) {
			TtRoTimeoutCausePO timeoutCausePO = ttRoTimeoutCausePO.get(0);
			String employeeName = null;

			LinkedList<RoNotSettleAccountsReasonDTO> resultList = new LinkedList<RoNotSettleAccountsReasonDTO>();
			logger.debug("from TtRoTimeoutDetailPO DEALER_CODE = "+dealerCode+" and ITEM_ID = "+timeoutCausePO.getInteger("ITEM_ID")+" and D_KEY = "+CommonConstants.D_KEY);
			LazyList<TtRoTimeoutDetailPO> ttRoTimeoutDetailPOs = TtRoTimeoutDetailPO.findBySQL("DEALER_CODE = ? and ITEM_ID = ? and D_KEY = ?",
					dealerCode,timeoutCausePO.getInteger("ITEM_ID"),CommonConstants.D_KEY);
			if (ttRoTimeoutDetailPOs != null && !ttRoTimeoutDetailPOs.isEmpty()) {
				employeeName = getEmployeeName(dealerCode, timeoutCausePO.getString("OWNED_BY"));
				for (TtRoTimeoutDetailPO timeoutDetailPO : ttRoTimeoutDetailPOs) {
					RoNotSettleAccountsReasonDTO roNotSettleAccountsReasonDTO = new RoNotSettleAccountsReasonDTO();
					roNotSettleAccountsReasonDTO.setRoNo(timeoutCausePO.getString("RO_NO"));
					roNotSettleAccountsReasonDTO.setOperator(employeeName);
					if (Utility.testString(timeoutCausePO.getInteger("WORK_STATUS"))) {
						roNotSettleAccountsReasonDTO.setWaitWorkReason(String.valueOf(timeoutCausePO.getInteger("WORK_STATUS")));
					}
					if (Utility.testString(timeoutCausePO.getString("REMARK"))) {
						roNotSettleAccountsReasonDTO.setWaitWorkRemark(timeoutCausePO.getString("REMARK"));
					}
					roNotSettleAccountsReasonDTO.setWaitMaterialPartCode(timeoutDetailPO.getString("PART_NO"));
					roNotSettleAccountsReasonDTO.setWaitMaterialPartName(timeoutDetailPO.getString("PART_NAME"));
					roNotSettleAccountsReasonDTO.setWaitMaterialPartQuantity(timeoutDetailPO.getFloat("PART_QUANTITY"));
					roNotSettleAccountsReasonDTO.setWaitMaterialOrderNo(timeoutDetailPO.getString("PURCHASE_ORDER_NO"));
					roNotSettleAccountsReasonDTO.setWaitMaterialOrderDate(timeoutDetailPO.getDate("ORDER_DATE"));
					if (Utility.testString(timeoutDetailPO.getString("REMARK"))) {
						roNotSettleAccountsReasonDTO.setWaitMaterialRemark(timeoutDetailPO.getString("REMARK"));
					}
					resultList.add(roNotSettleAccountsReasonDTO);
				}
				roNotSettleAccountsRepairOrderDTO.setNotSettleAccountsReason(resultList);
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
				roNotSettleAccountsRepairOrderDTO.setNotSettleAccountsReason(resultList);
			}
		}
	}

	/**
	 * @description 填充 RoNotSettleAccountsRepairOrderDTO 的  RoAddItemPO(附加项目) 集合
	 * @param dealerCode
	 * @param orderVO
	 * @param roAddItemList
	 * @throws Exception
	 */
	private void addRoAddItem( String dealerCode, RoNotSettleAccountsRepairOrderDTO orderVO, List<RoAddItemPO> roAddItemList) throws Exception {
		if (roAddItemList != null && !roAddItemList.isEmpty()) {
			LinkedList<RoNotSettleAccountsAddItemDTO> resultList = new LinkedList<RoNotSettleAccountsAddItemDTO>();
			for (RoAddItemPO ttRoAddItemPO : roAddItemList) {
				//增加判断，如果不是上端下发就不上报
				RoNotSettleAccountsAddItemDTO addItemVO = new RoNotSettleAccountsAddItemDTO();

				TmBalanceModeAddItemPO tmBalanceModeAddItemPO = TmBalanceModeAddItemPO.findByCompositeKeys(dealerCode,ttRoAddItemPO.getString("Add_Item_Code"));

				if(tmBalanceModeAddItemPO !=null 
						&& CommonConstants.DICT_IS_YES.equals(tmBalanceModeAddItemPO.getInteger("IS_VALID").toString())){
					addItemVO.setOemTag(Utility.getInt(CommonConstants.DICT_IS_YES));
				}else{
					addItemVO.setOemTag(Utility.getInt(CommonConstants.DICT_IS_NO));
				}
				addItemVO.setAddItemCode(ttRoAddItemPO.getString("ADD_ITEM_CODE"));
				addItemVO.setAddItemName(ttRoAddItemPO.getString("ADD_ITEM_NAME"));
				addItemVO.setAddItemAmount(ttRoAddItemPO.getDouble("ADD_ITEM_AMOUNT"));
				addItemVO.setRemark(ttRoAddItemPO.getString("REMARK"));
				addItemVO.setActivityCode(ttRoAddItemPO.getString("ACTIVITY_CODE"));  //added by liufeilu in 20130603
				resultList.add(addItemVO);
			}
			orderVO.setAddItemVoList(resultList);
		}
	}

	/**
	 * @description 填充 RoNotSettleAccountsRepairOrderDTO 的  TtRoRepairPartPO(工单维修配件明细) 集合
	 * @param dealerCode
	 * @param roNotSettleAccountsRepairOrderDTO
	 * @param roRepairPartList
	 * @param repairOrderPO
	 * @param roLabourList
	 * @param roAddShortPartList
	 */
	private void addRoRepairPart(String dealerCode, RoNotSettleAccountsRepairOrderDTO roNotSettleAccountsRepairOrderDTO, 
			List<TtRoRepairPartPO> roRepairPartList,RepairOrderPO repairOrderPO, List<TtRoLabourPO> roLabourList, List<TtShortPartPO> roAddShortPartList) {
		LinkedList<RoNotSettleAccountsRepairPartDTO> resultList = new LinkedList<RoNotSettleAccountsRepairPartDTO>();
		if (roRepairPartList != null && !roRepairPartList.isEmpty()) {
			for (TtRoRepairPartPO ttRoRepairPartPO : roRepairPartList) {
				RoNotSettleAccountsRepairPartDTO roNotSettleAccountsRepairPartDTO = new RoNotSettleAccountsRepairPartDTO();
				logger.debug("from TmPartInfoPO DEALER_CODE = "+dealerCode+" and PART_NO = "+ttRoRepairPartPO.getString("PART_NO")+" and D_KEY = " + CommonConstants.D_KEY);
				List<TmPartInfoPO> list2 = TmPartInfoPO.findBySQL("DEALER_CODE = ? and PART_NO = ? and D_KEY = ?",
						dealerCode,ttRoRepairPartPO.getString("PART_NO"),CommonConstants.D_KEY);
				if(list2 != null && !list2.isEmpty()){
					TmPartInfoPO partInfoPO2 = list2.get(0);
					roNotSettleAccountsRepairPartDTO.setOemTag(partInfoPO2.getInteger("OEM_TAG"));
				}
				//add  lim oem 标志传去
				roNotSettleAccountsRepairPartDTO.setPartNo(ttRoRepairPartPO.getString("PART_NO"));
				roNotSettleAccountsRepairPartDTO.setPartName(ttRoRepairPartPO.getString("PART_NAME"));
				roNotSettleAccountsRepairPartDTO.setPartQuantity(ttRoRepairPartPO.getFloat("PART_QUANTITY"));
				roNotSettleAccountsRepairPartDTO.setPartSalesPrice(ttRoRepairPartPO.getDouble("PART_SALES_PRICE"));
				roNotSettleAccountsRepairPartDTO.setLabourCode(ttRoRepairPartPO.getString("LABOUR_CODE"));
				roNotSettleAccountsRepairPartDTO.setIsMain(ttRoRepairPartPO.getInteger("IS_MAIN_PART"));
				roNotSettleAccountsRepairPartDTO.setPackageQuantity(ttRoRepairPartPO.getDouble("PART_QUANTITY"));
				roNotSettleAccountsRepairPartDTO.setActivityCode(ttRoRepairPartPO.getString("ACTIVITY_CODE")); //added by liufeilu in 20130603
				if(roAddShortPartList != null && !roAddShortPartList.isEmpty()){
					roNotSettleAccountsRepairPartDTO.setLackMaterial("10041002");
					for(int j=0; j<roAddShortPartList.size(); j++	){
						TtShortPartPO shortPartPO = (TtShortPartPO) roAddShortPartList.get(j);
						if (shortPartPO.getString("PART_NO").equals(ttRoRepairPartPO.getString("PART_NO"))){
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
	 * @description 
	 * @param dealerCode
	 * @param orderVO
	 * @param roLabourList
	 * @throws Exception
	 */
	private void addRoLabour(String dealerCode, RoNotSettleAccountsRepairOrderDTO roNotSettleAccountsRepairOrderDTO, List<TtRoLabourPO> ttRoLabourPOs) throws Exception {
		if (ttRoLabourPOs != null && !ttRoLabourPOs.isEmpty()) {
			LinkedList<RoNotSettleAccountsLabourDTO> resultList = new LinkedList<RoNotSettleAccountsLabourDTO>();
			String activityCode = "";
			for(TtRoLabourPO ttRoLabourPO : ttRoLabourPOs){
				// 工时数是0的保养套餐维修项目不用上传
				if ("0000000000".equals(ttRoLabourPO.getString("LABOUR_CODE"))) {
					continue;
				}
				activityCode = ttRoLabourPO.getString("ACTIVITY_CODE");
				RoNotSettleAccountsLabourDTO roNotSettleAccountsLabourDTO = new RoNotSettleAccountsLabourDTO();
				
				logger.debug("from TmRepairItemPO DEALER_CODE = "+dealerCode+" and DOWN_TAG = "+Utility.getInt(CommonConstants.DICT_IS_YES)+" and LABOUR_CODE = "+ttRoLabourPO.getString("LABOUR_CODE")+" and MODEL_LABOUR_CODE = ?");
				LazyList<TmRepairItemPO> tmRepairItemPOs = TmRepairItemPO.findBySQL("DEALER_CODE = ? and DOWN_TAG = ? and LABOUR_CODE = ? and MODEL_LABOUR_CODE = ?",
						dealerCode,Utility.getInt(CommonConstants.DICT_IS_YES),ttRoLabourPO.getString("LABOUR_CODE"),
						ttRoLabourPO.getString("MODEL_LABOUR_CODE"));

				if(tmRepairItemPOs != null && tmRepairItemPOs.isEmpty()){
					roNotSettleAccountsLabourDTO.setOemTag(Utility.getInt(CommonConstants.DICT_IS_YES));
				} else {
					roNotSettleAccountsLabourDTO.setOemTag(Utility.getInt(CommonConstants.DICT_IS_NO));
				}
				roNotSettleAccountsLabourDTO.setLabourCode(ttRoLabourPO.getString("LABOUR_CODE"));
				roNotSettleAccountsLabourDTO.setLabourName(ttRoLabourPO.getString("LABOUR_NAME"));
				roNotSettleAccountsLabourDTO.setStdLabourHour(ttRoLabourPO.getDouble("STD_LABOUR_HOUR"));
				roNotSettleAccountsLabourDTO.setRemark(ttRoLabourPO.getString("REMARK"));
				roNotSettleAccountsLabourDTO.setActivityCode(ttRoLabourPO.getString("ACTIVITY_CODE"));
				if (CommonConstants.DICT_IS_YES.equals(String.valueOf(roNotSettleAccountsRepairOrderDTO.getIsClaim()))
						&& activityCode != null && !activityCode.trim().isEmpty()
						&& (roNotSettleAccountsRepairOrderDTO.getPackageCode() == null 
						|| roNotSettleAccountsRepairOrderDTO.getPackageCode().trim().isEmpty())) {

					roNotSettleAccountsRepairOrderDTO.setPackageCode(activityCode);
					logger.debug("from TtActivityPO DEALER_CODE = "+dealerCode+" and ACTIVITY_CODE = "+activityCode+" and D_KEY = "+CommonConstants.D_KEY);
					LazyList<TtActivityPO> ttActivityPOs = TtActivityPO.findBySQL("DEALER_CODE = ? and ACTIVITY_CODE = ? and D_KEY = ?",
							dealerCode, activityCode,CommonConstants.D_KEY);
					if(ttActivityPOs != null && !ttActivityPOs.isEmpty()){
						roNotSettleAccountsRepairOrderDTO.setPackageName(ttActivityPOs.get(0).getString("ACTIVITY_NAME"));
					}
				}
				resultList.add(roNotSettleAccountsLabourDTO);
			}
			roNotSettleAccountsRepairOrderDTO.setLabourVoList(resultList);
		}
	}


	/**
	 * @description  将 RepairOrderPO 类型包装成 RoNotSettleAccountsRepairOrderDTO类型
	 * @param dealerCode
	 * @param repairOrderPO
	 * @return
	 */
	private RoNotSettleAccountsRepairOrderDTO setRepairOrder(String dealerCode, RepairOrderPO repairOrderPO){
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
			switch(repairCode){
			case "SQWX":
				roNotSettleAccountsRepairOrderDTO.setRepairTypeCode("40011002");
				break;
			case "FWHD":
				roNotSettleAccountsRepairOrderDTO.setRepairTypeCode("40011004");
				break;
			case "WBFX":
				roNotSettleAccountsRepairOrderDTO.setRepairTypeCode("40011003");
				break;
			default:
				roNotSettleAccountsRepairOrderDTO.setRepairTypeCode("40011001");
				break;

			}
		}
		if (Utility.testString(repairOrderPO.getInteger("RO_TYPE"))){
			roNotSettleAccountsRepairOrderDTO.setNewOrderType(repairOrderPO.getInteger("RO_TYPE").toString());
		}
		roNotSettleAccountsRepairOrderDTO.setNewRepairType(repairOrderPO.getString("REPAIR_TYPE_CODE"));
		roNotSettleAccountsRepairOrderDTO.setRepairTypeName(getRepairTypeNameByRepairTypeCode(dealerCode,repairOrderPO.getString("REPAIR_TYPE_CODE")));
		roNotSettleAccountsRepairOrderDTO.setServiceAdvisor(getEmployeeNameByServiceAdvisor(dealerCode, repairOrderPO.getString("SERVICE_ADVISOR")));//微信需要
		roNotSettleAccountsRepairOrderDTO.setRoStatus(repairOrderPO.getInteger("RO_STATUS") ==12551010 ? 40021002 : 40021001);
		roNotSettleAccountsRepairOrderDTO.setRoCreateDate(repairOrderPO.getDate("RO_CREATE_DATE"));//微信需要
		roNotSettleAccountsRepairOrderDTO.setVin(repairOrderPO.getString("VIN"));//微信需要
		roNotSettleAccountsRepairOrderDTO.setInMileage(repairOrderPO.getDouble("IN_MILEAGE"));
		//出厂里程 //微信需要
		roNotSettleAccountsRepairOrderDTO.setOutMileage(repairOrderPO.getDouble("OUT_MILEAGE"));
		roNotSettleAccountsRepairOrderDTO.setDeliverer(repairOrderPO.getString("DELIVERER"));
		//add by Bill Tang 2013-01-29 start
		//		orderVO.setDelivererPhone(orderPO.getDelivererPhone());
		roNotSettleAccountsRepairOrderDTO.setDelivererPhone(repairOrderPO.getString("DELIVERER_MOBILE")!=null?repairOrderPO.getString("DELIVERER_MOBILE"):repairOrderPO.getString("DELIVERER_PHONE"));
		//add by Bill Tang 2013-01-29 end
		roNotSettleAccountsRepairOrderDTO.setForBalanceTime(repairOrderPO.getDate("FOR_BALANCE_TIME"));
		//		orderVO.setLabourPrice(labourPrice);
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
		CarownerPO ownerPO = CarownerPO.findByCompositeKeys(dealerCode,repairOrderPO.getString("OWNER_NO"));
		if (ownerPO != null) {
			roNotSettleAccountsRepairOrderDTO.setCustomerAddress(ownerPO.getString("ADDRESS"));
		}
		TmVehiclePO vehiclePO = TmVehiclePO.findByCompositeKeys(dealerCode,repairOrderPO.getString("VIN"));
		if (vehiclePO != null) {
			roNotSettleAccountsRepairOrderDTO.setRepairDate(vehiclePO.getDate("RO_CREATE_DATE"));
		}
		roNotSettleAccountsRepairOrderDTO.setFaultDate(repairOrderPO.getDate("TROUBLE_OCCUR_DATE"));
		roNotSettleAccountsRepairOrderDTO.setFinishDate(repairOrderPO.getDate("COMPLEATTE_TIME"));//added by liufeilu  in 20130603

		if (roNotSettleAccountsRepairOrderDTO.getFaultDate() != null && roNotSettleAccountsRepairOrderDTO.getRepairDate() != null) {
			long betweenDays = (roNotSettleAccountsRepairOrderDTO.getRepairDate().getTime() - roNotSettleAccountsRepairOrderDTO.getFaultDate().getTime()) / 1000 / 24/ 60 / 60;
			if (betweenDays > 0) {
				roNotSettleAccountsRepairOrderDTO.setBetweenDays((int) betweenDays);
			}
		}
		return roNotSettleAccountsRepairOrderDTO;
	}

	/**
	 * @description 根据 ServiceAdvisor,dealerCode 获取 EMPLOYEENAME
	 * @param dealerCode
	 * @param ServiceAdvisor
	 * @return
	 */
	private String getEmployeeNameByServiceAdvisor(String dealerCode,String ServiceAdvisor){
		String returnValue = null;
		if (ServiceAdvisor != null && !ServiceAdvisor.trim().isEmpty()) {
			EmployeePo employeePo = EmployeePo.findByCompositeKeys(dealerCode,ServiceAdvisor);
			returnValue = employeePo!=null ? employeePo.getString("EMPLOYEE_NAME") : "";
		}
		return returnValue;
	}

	/**
	 * @description 根据dealerCode, employeeNo获取employeeName
	 * @param dealerCode
	 * @param ownedBy
	 * @return
	 */
	private String getEmployeeName(String dealerCode, String ownedBy){
		String returnValue = null;
		if (ownedBy != null && !"".equals(ownedBy.trim())) {
			EmployeePo employeePo = EmployeePo.findByCompositeKeys(dealerCode,ownedBy);
			if (employeePo != null) {
				returnValue = employeePo.getString("EMPLOYEE_NAME");
			}
		}
		return returnValue;
	}

	/**
	 * @description 根据 RepairTypeCode,dealerCode 获取 RepairTypeName
	 * @param RepairTypeCode
	 * @return
	 */
	private String getRepairTypeNameByRepairTypeCode(String dealerCode,String RepairTypeCode){
		String RepairTypeName = null;
		if (RepairTypeCode != null && !RepairTypeCode.trim().isEmpty()) {
			TmRepairTypePO tmRepairTypePO = TmRepairTypePO.findByCompositeKeys(dealerCode, RepairTypeCode);
			RepairTypeName = tmRepairTypePO!=null ? tmRepairTypePO.getString("REPAIR_TYPE_NAME") : "";
		}
		return RepairTypeName;
	}

	/**
	 * @description 拼接  专用业务查询sql语句
	 * @param dealerCode
	 * @return
	 */
	private String getSql(String dealerCode){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		//计算出距离48小时之前的时间
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.HOUR, -48);
		String resultDate = simpleDateFormat.format(calendar.getTime());

		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT DISTINCT A.RO_NO ");
		sb.append(" FROM TT_REPAIR_ORDER A ");
		sb.append(" INNER JOIN TM_VEHICLE B ON A.DEALER_CODE = B.DEALER_CODE AND A.VIN = B.VIN");
		sb.append(" WHERE A.DEALER_CODE = '");
		sb.append(dealerCode);
		sb.append("' AND B.SALES_DATE >= '2013-08-01 00:00:00'　and (A.RO_STATUS = ");
		sb.append(CommonConstants.DICT_RO_STATUS_TYPE_ON_REPAIR);
		sb.append(" or A.RO_STATUS = ");
		sb.append(CommonConstants.DICT_RO_STATUS_TYPE_FOR_BALANCE);
		sb.append(") AND A.RO_TYPE = ");
		sb.append(CommonConstants.DICT_RPT_CLAIM);
		sb.append(" and A.IS_TRIPLE_GUARANTEE_BEF = ");
		sb.append(CommonConstants.DICT_IS_YES);
		sb.append(" and A.IS_TIMEOUT_SUBMIT = ");
		sb.append(CommonConstants.DICT_IS_NO);
		sb.append(" and A.RO_CREATE_DATE >= ");
		sb.append(resultDate);
		sb.append(" AND A.D_KEY = ");
		sb.append(CommonConstants.D_KEY);
		sb.append(CommonConstants.WITH_UR);
		logger.debug(sb.toString());
		return sb.toString();
	}
}
