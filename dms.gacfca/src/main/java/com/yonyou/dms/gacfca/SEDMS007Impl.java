package com.yonyou.dms.gacfca;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.javalite.activejdbc.LazyList;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.util.Utility;
import com.hp.hpl.sparta.ParseException;
import com.yonyou.dms.DTO.gacfca.BalanceAccountsDTO;
import com.yonyou.dms.DTO.gacfca.BalanceCouponDTO;
import com.yonyou.dms.DTO.gacfca.BalancePayobjDTO;
import com.yonyou.dms.DTO.gacfca.InsProposalCardListDTO;
import com.yonyou.dms.DTO.gacfca.RepairOrderDTO;
import com.yonyou.dms.DTO.gacfca.RoAddItemDTO;
import com.yonyou.dms.DTO.gacfca.RoLabourDTO;
import com.yonyou.dms.DTO.gacfca.RoPartSalesDTO;
import com.yonyou.dms.DTO.gacfca.RoRepairPartDTO;
import com.yonyou.dms.common.domains.PO.basedata.TtBalanceAccountsPO;
import com.yonyou.dms.common.domains.PO.basedata.TtBalanceAddItemPO;
import com.yonyou.dms.common.domains.PO.basedata.TtBalanceLabourPO;
import com.yonyou.dms.common.domains.PO.basedata.TtBalanceRepairPartPO;
import com.yonyou.dms.common.domains.PO.basedata.TtBalanceSalesPartPO;
import com.yonyou.dms.common.domains.PO.basedata.EmployeePo;
import com.yonyou.dms.common.domains.PO.basedata.RepairOrderPO;
import com.yonyou.dms.common.domains.PO.basedata.TmBalanceModeAddItemPO;
import com.yonyou.dms.common.domains.PO.basedata.TmBrandPo;
import com.yonyou.dms.common.domains.PO.basedata.TmPartInfoPO;
import com.yonyou.dms.common.domains.PO.basedata.TmPartStockPO;
import com.yonyou.dms.common.domains.PO.basedata.TmRepairItemPO;
import com.yonyou.dms.common.domains.PO.basedata.TmRepairTypePO;
import com.yonyou.dms.common.domains.PO.basedata.TtActivityPO;
import com.yonyou.dms.common.domains.PO.basedata.TtBookingOrderPO;
import com.yonyou.dms.common.domains.PO.basedata.TtShortPartPO;
import com.yonyou.dms.common.domains.PO.stockmanage.CarownerPO;
import com.yonyou.dms.common.domains.PO.stockmanage.VehiclePO;
import com.yonyou.dms.function.common.CommonConstants;

/**
 * @description 经销商上报车辆维修信息（维修结算上报）
 * @author Administrator
 *
 */
@Service
public class SEDMS007Impl implements SEDMS007 {
	final Logger logger = Logger.getLogger(SEDMS007Impl.class);

	@Override
	public LinkedList<RepairOrderDTO> getSEDMS007(String dealerCode, Long userId, String roNo, String balanceNo, String refund) {
		logger.info("==========SEDMS007Impl执行===========");
		try {
			if (roNo == null || "".equals(roNo.trim())) {
				logger.debug("非工单结算，退出");
				return null;
			}
			String groupCode = Utility.getGroupEntity(dealerCode, "TM_BRAND");
			logger.debug("DEALER_CODE = "+dealerCode+" and RO_NO = "+roNo+" and D_KEY = "+CommonConstants.D_KEY);
			LazyList<RepairOrderPO> repairOrderList = RepairOrderPO.findBySQL("DEALER_CODE = ? and RO_NO = ? and D_KEY = ? ",
					dealerCode,roNo,CommonConstants.D_KEY);
			if (repairOrderList == null || repairOrderList.size() == 0) {
				throw new Exception("工单不存在");
			}
			//add by Bill Tang 2013-01-25 start
			//非OEM品牌的工单不上报
			//获取工单上的VIN对应的品牌
			String brandCode = (repairOrderList.get(0)).getString("BRAND");
			//查询品牌信息是否为OEM
			TmBrandPo brandCnd = new TmBrandPo();
			brandCnd.setString("DEALER_CODE",Utility.getGroupEntity(dealerCode,"TM_BRAND"));
			brandCnd.setString("BRAND_CODE",brandCode);
			brandCnd.setInteger("OEM_TAG",Integer.valueOf(CommonConstants.DICT_IS_YES));//是OEM品牌	
			logger.debug("from TmBrandPo DEALER_CODE = "+Utility.getGroupEntity(dealerCode,"TM_BRAND")+" and BRAND_CODE = "+brandCode+" and OEM_TAG = "+Integer.valueOf(CommonConstants.DICT_IS_YES));
			List<TmBrandPo> brandList = TmBrandPo.findBySQL("DEALER_CODE = ? and BRAND_CODE = ? and OEM_TAG = ?",
					Utility.getGroupEntity(dealerCode,"TM_BRAND"),brandCode,Integer.valueOf(CommonConstants.DICT_IS_YES));
			if(brandList==null || brandList.size()==0){
				logger.debug("非OEM品牌代码("+brandCode+")的工单不能上报！");
				return null;
			}
			LinkedList<RepairOrderDTO> resultList = new LinkedList<RepairOrderDTO>();
			RepairOrderPO repairOrderPO = repairOrderList.get(0);
			RepairOrderDTO repairOrderDTO = setRepairOrder(groupCode,dealerCode, repairOrderPO,refund);

			List<TtBalanceRepairPartPO> roRepairPartList = getRoRepairPart(groupCode, roNo,balanceNo);	//结算单维修配件
			List<TtBalanceSalesPartPO> roPartSalesList = getRoPartSalesList( dealerCode, balanceNo); 	//配件销售材料明细
			List<TtShortPartPO> roAddShortPartList = getShortPartList(dealerCode, roNo);				//配件缺料记录表
			addBalancePayobj(dealerCode,balanceNo,roNo, repairOrderDTO);
			addBalanceCoupon(dealerCode,balanceNo,roNo, repairOrderDTO);
			addBalance(dealerCode, balanceNo, repairOrderDTO);
			addRoLabour(dealerCode, balanceNo,roNo, repairOrderDTO);		//维修单项目明细
			addRoRepairPart(groupCode, repairOrderDTO, roRepairPartList, repairOrderPO, getRoLabour(dealerCode,balanceNo,roNo), roAddShortPartList);
			addRoAddItem(dealerCode, balanceNo,roNo, repairOrderDTO);
			addRoPartSales(dealerCode, roNo, repairOrderDTO, roPartSalesList);
			addRoCardMessage(dealerCode,balanceNo, repairOrderDTO);
			resultList.add(repairOrderDTO);
			updateRoCLaimStatus(dealerCode, repairOrderPO.getString("Ro_No"),userId);   //更新 工单修改信息
			return resultList;
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(e);
			return null;
		}finally{
			logger.info("==========SEDMS007Impl结束===========");
		}
	}
	
	/**
	 * @description 查询工单，并填充进RepairOrderDTO
	 * @param dealerCode
	 * @param enCode
	 * @param repairOrderPO
	 * @param refund
	 * @return
	 * @throws Exception
	 */
	private RepairOrderDTO setRepairOrder(String dealerCode,String enCode, RepairOrderPO repairOrderPO,String refund) throws Exception {
		RepairOrderDTO repairOrderDTO = new RepairOrderDTO();
		repairOrderDTO.setDealerCode(enCode);//微信需要
		repairOrderDTO.setRoNo(repairOrderPO.getString("Ro_No"));//微信需要
		if (CommonConstants.DICT_RPT_CLAIM.equals(String.valueOf(repairOrderPO.getInteger("Ro_Type")))) {
			repairOrderDTO.setIsClaim(Integer.parseInt(CommonConstants.DICT_IS_YES));
		} else {
			repairOrderDTO.setIsClaim(Integer.parseInt(CommonConstants.DICT_IS_NO));
		}
		String repairCode = repairOrderPO.getString("Repair_Type_Code");
		// 服务活动算作保修 保养套餐作为保养
		if ("SCBY".equals(repairCode) || "CGBY".equals(repairCode) || "DQBY".equals(repairCode)) {
			repairOrderDTO.setRoType(40041001);
			repairOrderDTO.setRepairTypeCode("40011001");//微信需要
		} else {
			repairOrderDTO.setRoType(40041002);
			if ("SQWX".equals(repairCode)) {
				repairOrderDTO.setRepairTypeCode("40011002");
			} else if ("FWHD".equals(repairCode)) {
				repairOrderDTO.setRepairTypeCode("40011004");
			} else if ("WBFX".equals(repairCode)) {
				repairOrderDTO.setRepairTypeCode("40011003");
			} else {
				repairOrderDTO.setRepairTypeCode("40011001");
			}
		}
		if("1".equals(refund)){
			repairOrderDTO.setRefund("12781001");
		}else{
			repairOrderDTO.setRefund("12781002");	
		}
		if (Utility.testString(repairOrderPO.getInteger("Ro_Type"))){
			repairOrderDTO.setNewOrderType(repairOrderPO.getInteger("Ro_Type").toString());
		}
		repairOrderDTO.setNewRepairType(repairOrderPO.getString("RepairTypeCode"));
		repairOrderDTO.setRepairTypeName(getRepairCodeByName(dealerCode, repairOrderPO.getString("Repair_Type_Code")));
		repairOrderDTO.setServiceAdvisor(getEmployeeName(dealerCode, repairOrderPO.getString("Service_Advisor")));//微信需要
		repairOrderDTO.setRoStatus(repairOrderPO.getInteger("Ro_Status")==12551010 ? 40021002 : 40021001);
		repairOrderDTO.setRoCreateDate(repairOrderPO.getDate("Ro_Create_Date"));//微信需要
		repairOrderDTO.setVin(repairOrderPO.getString("Vin"));//微信需要
		repairOrderDTO.setInMileage(repairOrderPO.getDouble("In_Mileage"));
		//出厂里程 //微信需要
		repairOrderDTO.setOutMileage(repairOrderPO.getDouble("Out_Mileage"));
		repairOrderDTO.setDeliverer(repairOrderPO.getString("Deliverer"));
		//add by Bill Tang 2013-01-29 start
		//		orderVO.setDelivererPhone(orderPO.getDelivererPhone());
		repairOrderDTO.setDelivererPhone(repairOrderPO.getString("Deliverer_Mobile")==null ? repairOrderPO.getString("Deliverer_Phone") : repairOrderPO.getString("Deliverer_Mobile"));
		//add by Bill Tang 2013-01-29 end
		repairOrderDTO.setForBalanceTime(repairOrderPO.getDate("For_Balance_Time"));
		//		orderVO.setLabourPrice(labourPrice);
		repairOrderDTO.setLabourPrice(repairOrderPO.getFloat("Labour_Price"));
		repairOrderDTO.setLabourAmount(repairOrderPO.getDouble("Labour_Amount"));
		repairOrderDTO.setSeries(repairOrderPO.getString("Series"));
		repairOrderDTO.setModel(repairOrderPO.getString("Model"));//新增车型代码
		repairOrderDTO.setLicense(repairOrderPO.getString("License"));
		repairOrderDTO.setDeliveryDate(repairOrderPO.getDate("Delivery_Date"));
		repairOrderDTO.setRepairPartAmount(repairOrderPO.getDouble("Repair_Part_Amount"));
		repairOrderDTO.setAdditemAmount(repairOrderPO.getDouble("Add_Item_Amount"));
		repairOrderDTO.setRemark(repairOrderPO.getString("Remark"));
		//		orderVO.setDownTimestamp(new Date());
		//		orderVO.setIsValid(Integer.parseInt(DictDataConstant.DICT_VALIDS_VALID));
		if (Utility.testString(repairOrderPO.getString("Booking_Order_No"))){
			repairOrderDTO.setBookingOrderNo(repairOrderPO.getString("Booking_Order_No"));
			//根据预约单找到微信预约id
			String resId = getResIdByOrderNo( dealerCode, repairOrderPO.getString("Booking_Order_No"));
			if(Utility.testString(resId)){
				repairOrderDTO.setReservationId(resId);
			}

		}
		if(Utility.testString(repairOrderPO.getString("Booking_Order_No"))){//如果预约单号不为空 则是预约的
			repairOrderDTO.setIsOrder(10011001);//是预约的
		}else{
			repairOrderDTO.setIsOrder(10011002);//不是预约的
		}
		CarownerPO ownerPO = getTmOwner( dealerCode, repairOrderPO.getString("Owner_No"));
		VehiclePO vehiclePO = getTmVehicle(dealerCode, repairOrderPO.getString("Vin"));
		repairOrderDTO.setCustomerAddress(ownerPO.getString("Address"));
		repairOrderDTO.setFaultDate(repairOrderPO.getDate("Trouble_Occur_Date"));
		repairOrderDTO.setRepairDate(repairOrderPO.getDate("Ro_Create_Date"));

		repairOrderDTO.setMileage(vehiclePO.getDouble("Mileage"));//新增行驶路程
		repairOrderDTO.setFinishDate(repairOrderPO.getDate("Complete_Time"));//added by liufeilu  in 20130603

		//add by jll 2014-08-12 添加"故障描述""顾客描述上报"
		repairOrderDTO.setRoTroubleDesc(repairOrderPO.getString("Ro_Trouble_Desc"));
		repairOrderDTO.setCustomerDesc(repairOrderPO.getString("Customer_Desc"));
		if (repairOrderDTO.getFaultDate() != null && repairOrderDTO.getRepairDate() != null) {
			long betweenDays = (repairOrderDTO.getRepairDate().getTime() - repairOrderDTO.getFaultDate().getTime()) / 1000 / 24/ 60 / 60;
			if (betweenDays > 0) {
				repairOrderDTO.setBetweenDays((int) betweenDays);
			}
		}
		return repairOrderDTO;
	}

	/**
	 * 根据repairTypeCode获取repairTypeName
	 * @param dealerCode
	 * @param repairTypeCode
	 * @return
	 */
	private String getRepairCodeByName(String dealerCode, String repairTypeCode) {
		String returnValue = null;
		if (repairTypeCode != null && !"".equals(repairTypeCode.trim())) {
			logger.debug("from TmRepairTypePO DEALER_CODE = "+dealerCode+" and REPAIR_TYPE_CODE = "+repairTypeCode);
			LazyList<TmRepairTypePO> tmRepairTypePOs = TmRepairTypePO.findBySQL("DEALER_CODE = ? and REPAIR_TYPE_CODE = ?", dealerCode,repairTypeCode);
			if (tmRepairTypePOs != null && !tmRepairTypePOs.isEmpty()) {
				TmRepairTypePO repairTypePO =  tmRepairTypePOs.get(0);
				returnValue = repairTypePO.getString("REPAIR_TYPE_NAME");
			}
		}
		return returnValue;
	}

	/**
	 * @description 根据employeeNo查询employeeName
	 * @param con
	 * @param dealerCode
	 * @param employeeNo
	 * @return
	 */
	private String getEmployeeName(String dealerCode, String employeeNo) {
		String returnValue = null;
		if (employeeNo != null && !"".equals(employeeNo.trim())) {
			logger.debug("from EmployeePo DEALER_CODE = "+dealerCode+" and EMPLOYEE_NO = "+employeeNo);
			LazyList<EmployeePo> employeePos = EmployeePo.findBySQL("DEALER_CODE = ? and EMPLOYEE_NO = ?", dealerCode,employeeNo);
			if (employeePos != null && !employeePos.isEmpty()) {
				EmployeePo employeePO = employeePos.get(0);
				returnValue = employeePO.getString("EMPLOYEE_NAME");
			}
		}
		return returnValue;
	}

	/**
	 * @description 根据orderNo， dealerCode 查询Reservation_Id
	 * @param dealerCode
	 * @param orderNo
	 * @return
	 */
	private String getResIdByOrderNo(String dealerCode,String orderNo){
		String resId = null;
		logger.debug("from TtBookingOrderPO BOOKING_ORDER_NO = "+orderNo+" and DEALER_CODE = "+dealerCode+" and D_KEY = "+CommonConstants.D_KEY);
		LazyList<TtBookingOrderPO> ttBookingOrderPO = TtBookingOrderPO.findBySQL("BOOKING_ORDER_NO = ? and DEALER_CODE = ? and D_KEY = ?", 
				orderNo,dealerCode,CommonConstants.D_KEY);
		if(ttBookingOrderPO!=null && !ttBookingOrderPO.isEmpty()){
			TtBookingOrderPO orderPo = ttBookingOrderPO.get(0);
			resId = orderPo.getString("RESERVATION_ID");
		}
		return resId;
	}

	/**
	 * @description 查询维修单项目明细
	 * @param dealerCode
	 * @param roNo
	 * @param balanceNo
	 * @return
	 */
	private List<TtBalanceLabourPO> getRoLabour(String dealerCode,String balanceNo, String roNo) {
		logger.debug("from TtBalanceLabourPO DEALER_CODE = "+dealerCode+" and RO_NO = "+roNo+" and BALANCE_NO = "+balanceNo+" and D_KEY = "+CommonConstants.D_KEY);
		return TtBalanceLabourPO.findBySQL("DEALER_CODE = ? and RO_NO = ? and BALANCE_NO = ? and D_KEY = ?", dealerCode,roNo,balanceNo,CommonConstants.D_KEY);
	}

	/**
	 * @description　查询结算单
	 * @param dealerCode
	 * @param balanceNo
	 * @return
	 */
	private List<TtBalanceAccountsPO> getBalanceAccount(String dealerCode, String balanceNo)  {	
		logger.debug("from TtBalanceAccountsPO DEALER_CODE = "+dealerCode+" and BALANCE_NO = "+balanceNo+" and D_KEY = "+CommonConstants.D_KEY);
		return TtBalanceAccountsPO.findBySQL("DEALER_CODE = ? and BALANCE_NO = ? and D_KEY = ?", dealerCode,balanceNo,CommonConstants.D_KEY);
	}

	/**
	 * @description 查询结算单维修配件 
	 * @param dealerCode
	 * @param roNo
	 * @param balanceNo
	 * @return
	 */
	private List<TtBalanceRepairPartPO> getRoRepairPart(String dealerCode, String roNo,String balanceNo) {
		return TtBalanceRepairPartPO.findBySQL("DEALER_CODE = ? and RO_NO =? and BALANCE_NO = ? and D_KEY = ?", dealerCode,roNo,balanceNo,CommonConstants.D_KEY);
	}

	/**
	 * @description 查询结算单附加项目明细
	 * @param dealerCode
	 * @param roNo
	 * @param balanceNo
	 * @return
	 */
	private List<TtBalanceAddItemPO> getRoAddItemList(String dealerCode, String roNo,String balanceNo) {
		return TtBalanceAddItemPO.findBySQL("DEALER_CODE = ? and RO_NO = ? and BALANCE_NO = ? and D_KEY = ?", dealerCode,roNo,balanceNo,CommonConstants.D_KEY);
	}

	/**
	 * @description 查询结算单销售配件明细 
	 * @param dealerCode
	 * @param balanceNo
	 * @return
	 */
	private List<TtBalanceSalesPartPO> getRoPartSalesList(String dealerCode, String balanceNo) {
		return TtBalanceSalesPartPO.findBySQL("DEAER_CODE = ? and BALANCE_NO = ? and D_KEY = ?", dealerCode,balanceNo,CommonConstants.D_KEY);
	}

	/**
	 * @description 查询配件缺料记录表
	 * @param dealerCode
	 * @param roNo
	 * @return
	 */
	private List<TtShortPartPO> getShortPartList(String dealerCode, String roNo) {
		logger.debug("from TtShortPartPO DEALER_CODE = "+dealerCode+" and SHEET_NO = "+roNo+" and D_KEY = "+CommonConstants.D_KEY);
		return TtShortPartPO.findBySQL("DEALER_CODE = ? and SHEET_NO = ? and D_KEY = ?", dealerCode,roNo,CommonConstants.D_KEY);
	}

	/**
	 * @description 根据主键查询VehiclePO
	 * @param dealerCode
	 * @param vin
	 * @return
	 */
	private VehiclePO getTmVehicle(String dealerCode, String vin) {
		return VehiclePO.findByCompositeKeys(vin,dealerCode);
	}

	/**
	 * @description 根据主键查询Carowner
	 * @param dealerCode
	 * @param ownerNo
	 * @return
	 */
	private CarownerPO getTmOwner(String dealerCode, String ownerNo) {
		return CarownerPO.findByCompositeKeys(ownerNo,dealerCode);
	}

	/**
	 * @description 查询 结算单收费对象列表 ，然后将查询出来的数据填充进 RepairOrderDTO
	 * @param dealerCode
	 * @param balanceNo
	 * @param ownerNo
	 * @param repairOrderDTO
	 * @throws Exception
	 */
	private void addBalancePayobj(String dealerCode,String balanceNo,String ownerNo, RepairOrderDTO repairOrderDTO) throws Exception {
		List<String> conditions = new ArrayList<>();
		conditions.add(dealerCode);
		conditions.add(balanceNo);
		conditions.add(ownerNo);
		logger.debug("select a.PAYMENT_OBJECT_NAME,a.REAL_TOTAL_AMOUNT from tt_Balance_Payobj a where a.dealer_code="+dealerCode+" and a.BALANCE_NO="+balanceNo+" and a.PAYMENT_OBJECT_CODE="+ownerNo);
		List<Map> balancePayobjList = DAOUtil.findAll("select a.PAYMENT_OBJECT_NAME,a.REAL_TOTAL_AMOUNT from tt_Balance_Payobj a where a.dealer_code=? and a.BALANCE_NO=? and a.PAYMENT_OBJECT_CODE=?" , conditions);
		if (balancePayobjList != null && !balancePayobjList.isEmpty()) {
			LinkedList<BalancePayobjDTO> resultList = new LinkedList<BalancePayobjDTO>();
			for (Map<String,Object> map : balancePayobjList) {
				BalancePayobjDTO balancePayobjDTO = new BalancePayobjDTO();			
				balancePayobjDTO.setBalaPayobjId(String.valueOf(map.get("PAYMENT_OBJECT_NAME")));
				balancePayobjDTO.setRealTotalAmount(Double.parseDouble(map.get("REAL_TOTAL_AMOUNT").toString()));				
				resultList.add(balancePayobjDTO);
			}
			repairOrderDTO.setBalancePayobjVoList(resultList);
		}
	}

	/**
	 * @description 先查询费用结算优惠券，然后添加进RepairOrderDTO
	 * @param dealerCode
	 * @param balanceNo
	 * @param roNo
	 * @param repairOrderDTO
	 * @throws Exception
	 */
	private void addBalanceCoupon(String dealerCode,String balanceNo,String roNo, RepairOrderDTO repairOrderDTO) throws Exception {
		List<String> conditions = new ArrayList<String>();
		conditions.add(dealerCode);
		conditions.add(balanceNo);
		conditions.add(dealerCode);
		conditions.add(roNo);
		String sql = "select A.USE_STATUS,A.CARD_NO from TM_BALANCE_BOOKING_CARD_MESSAGE  a " +
				" where a.dealer_code=? and a.BALANCE_NO=? and a.is_wx_card= 12781001 and a.USE_STATUS=50221003 union all select a.USE_STATUS,A.CARD_NO from TM_WECHAT_BOOKING_CARD_MESSAGE a " +
				"  LEFT JOIN Tt_Booking_Order C ON A.dealer_code=C.dealer_code AND A.RESERVE_ID=C.RESERVATION_ID" +
				" left join tt_repair_order b on C.dealer_code=b.dealer_code and C.booking_order_no=b.booking_order_no " +
				" where a.dealer_code=? AND b.ro_no=? and b.booking_order_no is not null AND A.USE_STATUS !=50221003 ";
		logger.debug(sql + conditions.toString());
		List<Map> balanceCoupons = DAOUtil.findAll(sql, conditions);
		if (balanceCoupons != null && balanceCoupons.size() > 0) {
			LinkedList<BalanceCouponDTO> resultList = new LinkedList<BalanceCouponDTO>();
			for (Map<String,Object> map : balanceCoupons) {
				BalanceCouponDTO BalanceCouponVO = new BalanceCouponDTO();			
				BalanceCouponVO.setUseStatus(Integer.parseInt(map.get("USE_STATUS").toString()));
				BalanceCouponVO.setCardNo(String.valueOf(map.get("CARD_NO")));				
				resultList.add(BalanceCouponVO);
			}
			repairOrderDTO.setWachatList(resultList);
		}
	}

	/**
	 * @description 先查询费用结算优惠券，如果有可用的优惠券，就计算优惠金额和详细信息，填充进RepairOrderDTO
	 * @param dealerCode
	 * @param balanceNo
	 * @param roNo
	 * @param repairOrderDTO
	 * @throws Exception
	 */
	private void addBalance(String dealerCode,String balanceNo,RepairOrderDTO repairOrderDTO) throws Exception {
		SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<String> lisconditions = new ArrayList<>();
		lisconditions.add(dealerCode);
		lisconditions.add(balanceNo);
		String sql = "select USE_STATUS,CARD_NO ,SUB_TYPE from TM_BALANCE_BOOKING_CARD_MESSAGE  " +
				" where DEALER_CODE=? and BALANCE_NO=? and IS_WX_CARD= 12781002 and USE_STATUS=50221003";
		List<Map> balanceAccountList = DAOUtil.findAll(sql, lisconditions);

		if (balanceAccountList != null && balanceAccountList.size() > 0) {
			LinkedList resultList = new LinkedList();
			for (int i = 0; i < balanceAccountList.size(); i++) {
				Map<String,Object> balancePO = balanceAccountList.get(i);

				BalanceAccountsDTO balanceAccountsDTO = new BalanceAccountsDTO();
				String sql1 = "select b.USER_NAME from tt_Balance_ACCOUNTS a  " +
						"INNER join tm_user b on a.dealer_code=b.dealer_code and a.Balance_Handler=b.employee_no " +
						" where a.dealer_code='"+dealerCode+"' and a.Balance_Handler='"+balancePO.get("BALANCE_HANDLER").toString()+"'";
				List<Map> listown = DAOUtil.findAll(sql1, null);

				Map<String,Object> bean = listown.get(0);	
				balanceAccountsDTO.setBalanceHandler(bean.get("USER_NAME").toString());
				balanceAccountsDTO.setBalanceTime(sm.parse(balancePO.get("BALANCE_TIME").toString()));
				balanceAccountsDTO.setInvoiceNo(balancePO.get("INVOICE_NO").toString());
				balanceAccountsDTO.setInvoiceTypeCode(balancePO.get("INVOICE_TYPE_CODE").toString()); 
				balanceAccountsDTO.setBalanceNo(balancePO.get("BALANCE_NO").toString());//新增结算单号
				if(balancePO.get("GCS_SYS_REPAIR_NO") != null && balancePO.get("GCS_SYS_REPAIR_NO").toString().trim().length() > 0)
					balanceAccountsDTO.setGcsSysRepairNo(balancePO.get("GCS_SYS_REPAIR_NO").toString());
				//增量新增以下结算信息
				balanceAccountsDTO.setRoNo(balancePO.get("RO_NO").toString());//工单号
				balanceAccountsDTO.setSalesPartNo(balancePO.get("SALES_PART_NO").toString());//配件销售单号
				balanceAccountsDTO.setBalanceModeCode(balancePO.get("BALANCE_MODE_CODE").toString());//结算模式代码
				balanceAccountsDTO.setDiscountModeCode(balancePO.get("DISCOUNT_MODE_CODE").toString());//优惠模式代码
				balanceAccountsDTO.setLabourAmount(Double.parseDouble(balancePO.get("LABOUR_AMOUNT").toString()));//工时费
				balanceAccountsDTO.setRepairPartAmount(Double.parseDouble(balancePO.get("REPAIR_PART_AMOUNT").toString()));//维修材料费
				balanceAccountsDTO.setSalesPartAmount(Double.parseDouble(balancePO.get("SALES_PART_AMOUNT").toString()));//销售材料费
				balanceAccountsDTO.setAddItemAmount(Double.parseDouble(balancePO.get("ADD_ITEM_AMOUNT").toString()));//附加项目费
				balanceAccountsDTO.setOverItemAmount(Double.parseDouble(balancePO.get("OVER_ITEM_AMOUNT").toString()));//辅料管理费
				balanceAccountsDTO.setSumAmount(Double.parseDouble(balancePO.get("SUM_AMOUNT").toString()));//汇总金额
				balanceAccountsDTO.setTotalAmount(Double.parseDouble(balancePO.get("TOTAL_AMOUNT").toString()));//总金额
				balanceAccountsDTO.setTax(Float.parseFloat(balancePO.get("TAX").toString()));//税率
				balanceAccountsDTO.setTaxAmount(Double.parseDouble(balancePO.get("TAX_AMOUNT").toString()));//税额
				balanceAccountsDTO.setNetAmount(Double.parseDouble(balancePO.get("NET_AMOUNT").toString()));//不含税金额
				balanceAccountsDTO.setReceiveAmount(Double.parseDouble(balancePO.get("RECEIVE_AMOUNT").toString()));//收款金额
				balanceAccountsDTO.setSubObbAmount(Double.parseDouble(balancePO.get("SUBOBB_AMOUNT").toString()));//去零金额
				balanceAccountsDTO.setDerateAmount(Double.parseDouble(balancePO.get("DERATE_AMOUNT").toString()));//减免金额
				balanceAccountsDTO.setIsRed(Integer.parseInt(balancePO.get("IS_RED").toString()));//是否被负结算
				balanceAccountsDTO.setRepairPartCost(Double.parseDouble(balancePO.get("REPAIR_PART_COST").toString()));//维修材料成本
				balanceAccountsDTO.setSalesPartCost(Double.parseDouble(balancePO.get("SALES_PART_COST").toString()));//销售材料成本
				resultList.add(balanceAccountsDTO);
			}
			repairOrderDTO.setBalanceAccountVoList(resultList);
		}
	}

	/**
	 * @description 查询 维修项目，并保存到RepairOrderDTO
	 * @param dealerCode
	 * @param balanceNo
	 * @param roNo
	 * @param repairOrderDTO
	 * @throws Exception
	 */
	private void addRoLabour(String dealerCode,String balanceNo,String roNo, RepairOrderDTO repairOrderDTO) throws Exception {
		List<TtBalanceLabourPO> roLabourList = getRoLabour(dealerCode,balanceNo,roNo);
		if (roLabourList != null && roLabourList.size() > 0) {
			LinkedList resultList = new LinkedList();
			for (int i = 0; i < roLabourList.size(); i++) {
				TtBalanceLabourPO labourPO = (TtBalanceLabourPO) roLabourList.get(i);
				// 工时数是0的保养套餐维修项目不用上传
				if ("0000000000".equals(labourPO.getString("LABOUR_CODE"))) {
					continue;
				}
				RoLabourDTO labourVO = new RoLabourDTO();	
				logger.debug("from TmRepairItemPO DEALER_CODE = "+dealerCode+" and DOWN_TAG = "+Utility.getInt(CommonConstants.DICT_IS_YES)+" and LABOUR_CODE = "+labourPO.getString("LABOUR_CODE")+" and MODEL_LABOUT_CODE = "+labourPO.getString("MODEL_LABOUR_CODE"));
				List <TmRepairItemPO> listitem = TmRepairItemPO.findBySQL("DEALER_CODE = ? and DOWN_TAG = ? and LABOUR_CODE = ? and MODEL_LABOUT_CODE = ?",
						dealerCode,Utility.getInt(CommonConstants.DICT_IS_YES),labourPO.getString("LABOUR_CODE"),labourPO.getString("MODEL_LABOUR_CODE"));

				if(listitem!=null && listitem.size()>0){
					labourVO.setOemTag(Utility.getInt(CommonConstants.DICT_IS_YES));
				} else {
					labourVO.setOemTag(Utility.getInt(CommonConstants.DICT_IS_NO));
				}
				//				加上oem标志
				labourVO.setLabourCode(labourPO.getString("LABOUR_CODE"));
				labourVO.setLabourName(labourPO.getString("LABOUR_NAME"));
				labourVO.setLabourAmount(labourPO.getDouble("LABOUR_AMOUNT"));
				labourVO.setLabourPrice(labourPO.getFloat("LABOUR_PRICE"));
				labourVO.setStdLabourHour(labourPO.getDouble("STD_LABOUR_HOUR"));
				labourVO.setRemark(labourPO.getString("REMARK"));
				labourVO.setActivityCode(labourPO.getString("ACTIVITY_CODE"));
				labourVO.setDiscount(labourPO.getFloat("DISCOUNT"));//2015/02/11配件活动需要上报折扣
				if(Utility.testString(labourPO.getString("TROUBLE_CAUSE")))//上报维修项目的故障原因
					labourVO.setTroubleCause(labourPO.getString("TROUBLE_CAUSE"));
				if (CommonConstants.DICT_IS_YES.equals(String.valueOf(repairOrderDTO.getIsClaim()))) {
					if (labourPO.getString("ACTIVITY_CODE") != null && !"".equals(labourPO.getString("ACTIVITY_CODE").trim())) {
						if (repairOrderDTO.getPackageCode() == null || "".equals(repairOrderDTO.getPackageCode().trim())) {
							repairOrderDTO.setPackageCode(labourPO.getString("ACTIVITY_CODE"));
							LazyList<TtActivityPO> ttActivityPOs = TtActivityPO.findBySQL("DEALER_CODE = ? and ACTIVITY_CODE = ? and D_KEY = ?", 
									dealerCode,labourPO.getString("ACTIVITY_CODE"),CommonConstants.D_KEY);
							repairOrderDTO.setPackageName(ttActivityPOs.get(0).getString("ACTIVITY_NAME"));
						}
					}
				}
				resultList.add(labourVO);
			}
			repairOrderDTO.setLabourVoList(resultList);
		}
	}

	/**
	 * @description 查询附加项目，并填充进RepairOrderDTO
	 * @param dealerCode
	 * @param balanceNo
	 * @param roNo
	 * @param orderVO
	 * @throws Exception
	 */
	private void addRoAddItem(String dealerCode,String balanceNo,String roNo, RepairOrderDTO orderVO) throws Exception {
		List<TtBalanceAddItemPO> roAddItemList = getRoAddItemList(dealerCode, roNo,balanceNo);
		if (roAddItemList != null && roAddItemList.size() > 0) {
			LinkedList resultList = new LinkedList();
			for (int i = 0; i < roAddItemList.size(); i++) {
				TtBalanceAddItemPO addItemPO = roAddItemList.get(i);
				//增加判断，如果不是上端下发就不上报
				RoAddItemDTO addItemVO = new RoAddItemDTO();

				TmBalanceModeAddItemPO tmBalanceModeAddItemPO = TmBalanceModeAddItemPO.findByCompositeKeys(addItemPO.getString("AddI_tem_Code"),dealerCode);
				if(CommonConstants.DICT_IS_YES.equals(tmBalanceModeAddItemPO.getInteger("Is_Valid").toString())){
					addItemVO.setOemTag(Utility.getInt(CommonConstants.DICT_IS_NO));
				}else{
					addItemVO.setOemTag(Utility.getInt(CommonConstants.DICT_IS_YES));
				}
				addItemVO.setAddItemCode(addItemPO.getString("Add_Item_Code"));
				addItemVO.setAddItemName(addItemPO.getString("Add_Item_Name"));
				addItemVO.setAddItemAmount(addItemPO.getDouble("Add_Item_Amount"));
				addItemVO.setRemark(addItemPO.getString("Remark"));
				if(addItemPO.getString("Activity_Code")!=null&&!"".equals(addItemPO.getString("Activity_Code")));
				addItemVO.setActivityCode(addItemPO.getString("Activity_Code"));  //added by liufeilu in 20130603
				resultList.add(addItemVO);
			}
			orderVO.setAddItemVoList(resultList);
		}
	}
	
	/**
	 * @description 
	 * @param dealerCode
	 * @param orderVO
	 * @param roRepairPartList
	 * @param orderPO
	 * @param roLabourList
	 * @param roAddShortPartList
	 */
	private void addRoRepairPart(String dealerCode, RepairOrderDTO orderVO, List<TtBalanceRepairPartPO> roRepairPartList,RepairOrderPO orderPO, List roLabourList, List roAddShortPartList) {
		LinkedList resultList = new LinkedList();
		if (roRepairPartList != null && roRepairPartList.size() > 0) {
			for (TtBalanceRepairPartPO partPO : roRepairPartList) {
				RoRepairPartDTO roRepairPartDTO = new RoRepairPartDTO();
				logger.debug("from TmPartInfoPO DEALER_CODE = "+dealerCode+" and PART_NO = "+partPO.getString("Part_No")+" and D_KEY = "+CommonConstants.D_KEY);
				List<TmPartInfoPO> list2 = TmPartInfoPO.findBySQL("DEALER_CODE = ? and PART_NO = ? and D_KEY = ?", dealerCode,partPO.getString("Part_No"),CommonConstants.D_KEY);
				if(list2 != null && !list2.isEmpty()){
					TmPartInfoPO partInfoPO2 = list2.get(0);
					roRepairPartDTO.setOemTag(partInfoPO2.getInteger("OEM_TAG"));
				}//add  lim oem 标志传去
				//增量索赔价格
				logger.debug("from TmPartStockPO DEALER_CODE = "+dealerCode+" and PART_NO = "+partPO.getString("PART_NO")+" and D_KEY = "+CommonConstants.D_KEY+" and STORAGE_CODE = "+partPO.getString("STORAGE_CODE"));
				List<TmPartStockPO> listPS = TmPartStockPO.findBySQL("DEALER_CODE = ? and PART_NO = ? and D_KEY = ? and STORAGE_CODE = ?", 
						dealerCode,partPO.getString("PART_NO"),CommonConstants.D_KEY,partPO.getString("STORAGE_CODE"));
				if(listPS != null && !listPS.isEmpty()){
					TmPartStockPO tmPSPo2 = listPS.get(0);
					if(tmPSPo2.getDouble("CLAIM_PRICE")!=null)
						roRepairPartDTO.setPartTaxClaimPrice(tmPSPo2.getDouble("CLAIM_PRICE"));
				};
				roRepairPartDTO.setPartSalesAmount(partPO.getDouble("PART_SALES_AMOUNT"));
				roRepairPartDTO.setPartNo(partPO.getString("PART_NO"));
				roRepairPartDTO.setPartName(partPO.getString("PART_NAME"));
				roRepairPartDTO.setPartQuantity(partPO.getFloat("PART_QUANTITY"));
				roRepairPartDTO.setPartSalesPrice(partPO.getDouble("PART_SALES_PRICE"));
				roRepairPartDTO.setPartCostPrice(partPO.getDouble("PART_COST_PRICE"));//配件成本单价
				if(Utility.testString(partPO.getDouble("PART_COST_PRICE"))){
					roRepairPartDTO.setTaxPartCostPrice(partPO.getDouble("PART_COST_PRICE")*1.17);//含税成本单价
				}else{
					roRepairPartDTO.setTaxPartCostPrice(0D);//含税成本单价
				}
				//增量新加仓库代码与库位代码
				roRepairPartDTO.setStorageCode(partPO.getString("STORAGE_CODE"));//仓库代码
				roRepairPartDTO.setStoragePositionCode(partPO.getString("STORAGE_POSITION_CODE"));//库位代码
				roRepairPartDTO.setPartCostAmount(partPO.getDouble("PART_COST_AMOUNT"));//配件成本金额
				roRepairPartDTO.setLabourCode(partPO.getString("LABOUR_CODE"));
				roRepairPartDTO.setIsMain(partPO.getInteger("IS_MAIN_PART"));
				roRepairPartDTO.setPackageQuantity(partPO.getInteger("PART_QUANTITY").doubleValue());
				roRepairPartDTO.setActivityCode(partPO.getString("ACTIVITY_CODE")); //added by liufeilu in 20130603
				roRepairPartDTO.setDiscount(partPO.getFloat("DISCOUNT"));// added by lj in 20150211 配件活动上报需要折扣
				if(roAddShortPartList != null && !roAddShortPartList.isEmpty()){
					roRepairPartDTO.setLackMaterial("10041002");
					for(int j=0; j<roAddShortPartList.size(); j++	){
						TtShortPartPO shortPartPO = (TtShortPartPO) roAddShortPartList.get(j);
						if (shortPartPO.getString("PART_NO").equals(partPO.getString("PART_NO"))){
							roRepairPartDTO.setLackMaterial("10041001");
							break;
						}
					}
				} else {
					roRepairPartDTO.setLackMaterial("10041002");
				}
				resultList.add(roRepairPartDTO);
			}
			orderVO.setRepairPartVoList(resultList);
		}
	}
	
	/**
	 * @description 查询 配件详细信息，并填充进RepairOrderDTO
	 * @param dealerCode
	 * @param roNo
	 * @param orderVO
	 * @param roPartSalesList
	 * @throws Exception
	 */
	private void addRoPartSales(String dealerCode, String roNo, RepairOrderDTO orderVO, List<TtBalanceSalesPartPO> roPartSalesList) throws Exception {
		if (roPartSalesList != null && roPartSalesList.size() > 0) {
			LinkedList resultList = new LinkedList();
			for (TtBalanceSalesPartPO salesPartPO : roPartSalesList) {
				RoPartSalesDTO partSalesVO = new RoPartSalesDTO();
				partSalesVO.setRoNo(roNo);
				partSalesVO.setSalesPartNo(salesPartPO.getString("SALES_PART_NO"));
				partSalesVO.setStorageCode(salesPartPO.getString("STORAGE_CODE"));
				partSalesVO.setPartNo(salesPartPO.getString("PART_NO"));
				partSalesVO.setPartName(salesPartPO.getString("PART_NAME"));
				partSalesVO.setPartQuantity(salesPartPO.getFloat("PART_QUANTITY"));
				partSalesVO.setPartCostPrice(salesPartPO.getDouble("PART_COST_PRICE"));
				partSalesVO.setPartSalesPrice(salesPartPO.getDouble("PART_SALES_PRICE"));
				partSalesVO.setPartCostAmount(salesPartPO.getDouble("PART_COST_AMOUNT"));
				partSalesVO.setPartSalesAmount(salesPartPO.getDouble("PART_SALES_AMOUNT"));
				partSalesVO.setDiscountAmount(salesPartPO.getDouble("DISCOUNT_AMOUNT"));
				partSalesVO.setRealReceiveAmount(salesPartPO.getDouble("REAL_RECEIVE_AMOUNT"));
				partSalesVO.setDiscount(salesPartPO.getFloat("DISCOUNT"));
				partSalesVO.setStoragePositionCode(salesPartPO.getString("STORAGE_POSITION_CODE"));
				partSalesVO.setBalanceNo(salesPartPO.getString("BALANCE_NO"));
				partSalesVO.setManageSortCode(salesPartPO.getString("MANAGE_SORT_CODE"));
				partSalesVO.setChargePartitionCode(salesPartPO.getString("CHARGE_PARTITION_CODE"));
				//是否下发
				logger.debug("from TmPartInfoPO DEALER_CODE = "+dealerCode+" and PART_NO = "+salesPartPO.getString("PART_NO")+" and D_KEY = "+CommonConstants.D_KEY);
				List<TmPartInfoPO> list5 = TmPartInfoPO.findBySQL("DEALER_CODE = ? and PART_NO = ? and D_KEY = ? ", 
						dealerCode, salesPartPO.getString("PART_NO"),CommonConstants.D_KEY);
				if(list5 != null && list5.size() > 0){
					TmPartInfoPO partInfoPO5 = list5.get(0);
					partSalesVO.setOemTag(partInfoPO5.getInteger("OEM_TAG"));
				}
				resultList.add(partSalesVO);
			}
			orderVO.setPartSalesVoList(resultList);
		}
	}
	
	/**
	 * @description 查询优惠券信息，关联运营商，然后填充进工单信息
	 * @param dealerCode
	 * @param balanceNo
	 * @param repairOrderDTO
	 * @throws Exception
	 */
	private void addRoCardMessage(String dealerCode,String balanceNo, RepairOrderDTO repairOrderDTO) throws Exception {
		List<String> lisconditions = new ArrayList<>();
		lisconditions.add(dealerCode);
		lisconditions.add(balanceNo);
		String sql = "select USE_STATUS,CARD_NO ,SUB_TYPE from TM_BALANCE_BOOKING_CARD_MESSAGE  " +
				" where dealer_code=? and BALANCE_NO=? and is_wx_card= 12781002 and USE_STATUS=50221003";
		logger.debug(sql+lisconditions.toString());
		List<Map> cardMessage = DAOUtil.findAll(sql, lisconditions);

		if(cardMessage != null && cardMessage.size() > 0){
			LinkedList resultList = new LinkedList();
			for(Map<String,Object> bean : cardMessage){
				InsProposalCardListDTO ipcvo = new InsProposalCardListDTO();
				ipcvo.setCardID(bean.get("CARD_NO").toString());
				ipcvo.setCardType(bean.get("SUB_TYPE").toString());
				ipcvo.setUseStatus(Integer.parseInt(bean.get("USE_STATUS").toString()));
				resultList.add(ipcvo);
			}
			repairOrderDTO.setInsProposalCardListVOList(resultList);
		}
	}
	
	/**
	 * @description 更新工单
	 * @param dealerCode
	 * @param roNo
	 * @param userId
	 * @throws ParseException
	 * @throws NumberFormatException
	 * @throws java.text.ParseException
	 */
	private void updateRoCLaimStatus(String dealerCode, String roNo,Long userId) throws ParseException, NumberFormatException, java.text.ParseException {
		RepairOrderPO.update("update_id = ?, update_at = ?", "dealer_Code = ? and ro_no = ? and ro_type = ? and d_key = ?", 
				userId,Utility.getCurrentDateTime(),dealerCode,roNo,Integer.parseInt(CommonConstants.DICT_RPT_CLAIM),CommonConstants.D_KEY);
		
	}
}
