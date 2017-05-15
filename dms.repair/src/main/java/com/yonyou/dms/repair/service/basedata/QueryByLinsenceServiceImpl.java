/**
 * 
 */
package com.yonyou.dms.repair.service.basedata;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.DTO.customer.OwnerDTO;
import com.yonyou.dms.common.domains.PO.basedata.EmployeePo;
import com.yonyou.dms.common.domains.PO.basedata.RepairOrderPO;
import com.yonyou.dms.common.domains.PO.basedata.RoAddItemPO;
import com.yonyou.dms.common.domains.PO.basedata.RoManagePO;
import com.yonyou.dms.common.domains.PO.basedata.TmLocalLabourMessagePO;
import com.yonyou.dms.common.domains.PO.basedata.TmMarOrderPO;
import com.yonyou.dms.common.domains.PO.basedata.TmMemberCardActivityPO;
import com.yonyou.dms.common.domains.PO.basedata.TmPartStockPO;
import com.yonyou.dms.common.domains.PO.basedata.TtBookingOrderPO;
import com.yonyou.dms.common.domains.PO.basedata.TtLogInfoPO;
import com.yonyou.dms.common.domains.PO.basedata.TtMemCardActiDetailPO;
import com.yonyou.dms.common.domains.PO.basedata.TtMemberLabourFlowPO;
import com.yonyou.dms.common.domains.PO.basedata.TtMemberLabourPO;
import com.yonyou.dms.common.domains.PO.basedata.TtMemberPartFlowPO;
import com.yonyou.dms.common.domains.PO.basedata.TtMemberPartPO;
import com.yonyou.dms.common.domains.PO.basedata.TtOccurInsuranceRegisterPO;
import com.yonyou.dms.common.domains.PO.basedata.TtPartObligatedItemPO;
import com.yonyou.dms.common.domains.PO.basedata.TtRoLabourPO;
import com.yonyou.dms.common.domains.PO.basedata.TtRoRepairPartPO;
import com.yonyou.dms.common.domains.PO.basedata.TtSalesPartPO;
import com.yonyou.dms.common.domains.PO.basedata.UserPO;
import com.yonyou.dms.common.domains.PO.customer.OwnerMemoPO;
import com.yonyou.dms.common.domains.PO.monitor.OperateLogPO;
import com.yonyou.dms.common.domains.PO.stockmanage.CarownerPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.function.utils.security.MD5Util;

/**
 * @author sqh
 *
 */
@Service
public class QueryByLinsenceServiceImpl implements QueryByLinsenceService {

	@Override
	public PageInfoDto queryByLinsence(Map<String, String> queryParam) throws ServiceBizException {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append(
				" SELECT A.*,B.OWNER_PROPERTY,B.OWNER_NAME,B.CONTACTOR_ZIP_CODE,B.CONTACTOR_EMAIL,B.CONTACTOR_ADDRESS,B.ADDRESS,B.PHONE,B.MOBILE,tb.brand_name,ts.series_name,tm.MODEL_NAME, ");
		sql.append(
				" (case when B.CONTACTOR_NAME IS NULL or B.CONTACTOR_NAME='' then B.OWNER_NAME else B.CONTACTOR_NAME end) AS CONTACTOR_NAME, ");
		sql.append(
				" (case when B.CONTACTOR_PHONE IS NULL or B.CONTACTOR_PHONE='' then B.PHONE else B.CONTACTOR_PHONE end) AS CONTACTOR_PHONE, ");
		sql.append(
				" (case when B.CONTACTOR_MOBILE IS NULL or B.CONTACTOR_MOBILE='' then B.MOBILE else B.CONTACTOR_MOBILE end) AS CONTACTOR_MOBILE, ");
		sql.append(
				" B.GENDER,B.CT_CODE,B.CERTIFICATE_NO,B.CONTACTOR_GENDER,B.E_MAIL,B.BIRTHDAY,'' as IS_MILEAGE_MODIFY,'' as sell_date ");
		sql.append(" ,'' as DELIVERER_DDD_CODE,'' as CHANGE_MILEAGE FROM (" + CommonConstants.VM_VEHICLE
				+ ") A left join (" + CommonConstants.VM_OWNER
				+ ") B on A.OWNER_NO = B.OWNER_NO and A.DEALER_CODE = B.DEALER_CODE ");
		// if (DictDataConstant.DICT_IS_YES.equals(vinflag))
		// {
		// sql += " AND (A.VIN LIKE '%" + license + "%' OR A.LICENSE LIKE '%" +
		// license
		// + "%') ";
		// }
		sql.append(
				"  LEFT JOIN ( SELECT mb.brand_code,mb.brand_name,mb.dealer_code FROM tm_brand mb ) tb ON A.DEALER_CODE=tb.DEALER_CODE AND A.BRAND=tb.BRAND_CODE ");
		sql.append(
				" LEFT JOIN (SELECT s.series_code,s.series_name,s.dealer_code,s.BRAND_CODE FROM tm_series s) ts  ON A.DEALER_CODE = ts.DEALER_CODE  AND A.SERIES = ts.SERIES_CODE  AND ts.brand_code=tb.brand_code");
		sql.append(
				" LEFT JOIN  (SELECT  m.DEALER_CODE, m.MODEL_NAME, m.MODEL_CODE, m.SERIES_CODE  FROM tm_model m) tm  ON A.DEALER_CODE = tm.DEALER_CODE  AND A.MODEL = TM.MODEL_CODE  AND ts.brand_code=tb.brand_code AND tm.series_code=ts.series_code WHERE  ");
		sql.append(" A.DEALER_CODE = ? ");
		params.add(FrameworkUtil.getLoginInfo().getDealerCode());
		sql.append(Utility.getLikeCond("A", "LICENSE", queryParam.get("license"), "AND"));
		sql.append(Utility.getLikeCond("A", "VIN", queryParam.get("vin"), "AND"));
		sql.append(Utility.getLikeCond("B", "OWNER_NAME", queryParam.get("ownerName"), "AND"));
		System.out.print(sql.toString());
		PageInfoDto pageInfoDto = DAOUtil.pageQuery(sql.toString(), params);
		return pageInfoDto;
	}

	@Override
	public PageInfoDto queryTripleInfoByVin(Map<String, String> queryParam) throws ServiceBizException {
		StringBuffer sql = new StringBuffer();
		List<Object> queryList = new ArrayList<Object>();
		sql.append(
				" SELECT n.WARN_ITEM_NO,n.WARN_ITEM_NAME,n.WARN_TIMES,pa.WARN_STANDARD,pa.LEGAL_STANDARD,COALESCE(PA.YELLOW_STANDARD,0) YELLOW_STANDARD,COALESCE(PA.ORANGE_STANDARD,0) ORANGE_STANDARD, ");
		sql.append(
				" COALESCE(PA.RED_STANDARD,0) RED_STANDARD FROM tt_threepack_warn_dcs n,tt_threepack_warn_para_dcs pa WHERE n.WARN_ITEM_NO=pa.ITEM_NO and n.VIN = ? ");
		queryList.add(queryParam.get("vin"));
		sql.append(" and (COALESCE(n.WARN_TIMES,0))>=COALESCE(PA.YELLOW_STANDARD,0) ");
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql.toString(), queryList);
		return pageInfoDto;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> queryOwnerNOByVin(String vin) throws ServiceBizException {
		StringBuffer sql = new StringBuffer();
		List<Object> queryList = new ArrayList<Object>();
		sql.append(" SELECT A.*,B.OWNER_NAME,B.OWNER_PROPERTY,C.MEMO_INFO FROM (" + CommonConstants.VM_VEHICLE + ") A LEFT JOIN (" + CommonConstants.VM_OWNER
				+ ") B ON A.DEALER_CODE = B. DEALER_CODE AND A.OWNER_NO = B.OWNER_NO ");
		sql.append(" LEFT JOIN TM_VEHICLE_MEMO C ON C.DEALER_CODE = A.DEALER_CODE AND C.VIN = A.VIN WHERE A.VIN = ? ");
		queryList.add(vin);
		sql.append(" AND A.DEALER_CODE = ? ");
		queryList.add(FrameworkUtil.getLoginInfo().getDealerCode());
		List<Map> list = DAOUtil.findAll(sql.toString(), queryList);
		return list;
	}

	@Override
	public PageInfoDto querywechatcardmessageRO(Map<String, String> queryParam) throws ServiceBizException {
		StringBuffer sql = new StringBuffer();
		List<Object> queryList = new ArrayList<Object>();
		if (!StringUtils.isNullOrEmpty(queryParam.get("reserveId"))) {
			sql.append(
					" select A.CARD_NO,A.DEALER_CODE,A.CARD_TYPE,A.CARD_NAME,A.SUB_TYPE,A.CARD_VALUE,A.BUY_AMOUNT,A.ISSUE_DATE,A.START_DATE,A.END_DATE,A.USE_DEALER,A.USE_PROJECT,A.USE_STATUS,A.RESERVE_ID,'微信卡券' as IS_INSURANCE from TM_WECHAT_BOOKING_CARD_MESSAGE A INNER JOIN TT_BOOKING_ORDER B ON A.DEALER_CODE=B.DEALER_CODE AND A.RESERVE_ID=B.RESERVATION_ID ");
			sql.append(" WHERE A.DEALER_CODE= ? ");
			queryList.add(FrameworkUtil.getLoginInfo().getDealerCode());
			sql.append(" AND B.BOOKING_ORDER_NO = ? ");
			queryList.add(queryParam.get("reserveId"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("reserveId"))
				&& !StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
			sql.append(" union all ");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
			sql.append(
					" select DEALER_CODE,CARD_ID as CARD_NO,CARD_TYPE,CARD_NAME,'' as SUB_TYPE,CARD_VALUE,0.0 as BUY_AMOUNT,ISSUE_DATE,START_DATE,END_DATE,USE_DEALER,USE_PROJECT,USE_STATUS,'' as RESERVE_ID,'保险卡券' as IS_INSURANCE from Tm_Vehicle_Insurance_Coupon ");
			sql.append(" where DEALER_CODE= ? ");
			queryList.add(FrameworkUtil.getLoginInfo().getDealerCode());
			sql.append(" AND USE_STATUS=50221001 and VIN = ? ");
			queryList.add(queryParam.get("vin"));
		}

		PageInfoDto pageInfoDto = DAOUtil.pageQuery((sql == null ? null : sql.toString()), queryList);
		return pageInfoDto;
	}

	@Override
	public PageInfoDto QueryOwnerByNoOrSpell(Map<String, String> queryParam) throws ServiceBizException {
		StringBuffer sql = new StringBuffer();
		List<Object> queryList = new ArrayList<Object>();
		sql.append(" SELECT B.LICENSE,A.*,B.VIN FROM (" + CommonConstants.VM_OWNER + ") A LEFT JOIN (" + CommonConstants.VM_VEHICLE + ") B ON A.DEALER_CODE=B.DEALER_CODE AND A.OWNER_NO=B.OWNER_NO WHERE A.DEALER_CODE= ? ");
		queryList.add(FrameworkUtil.getLoginInfo().getDealerCode());
		sql.append(Utility.getLikeCond("A", "OWNER_NO", queryParam.get("ownerNo"), "AND"));
		sql.append(Utility.getLikeCond("A", "OWNER_SPELL", queryParam.get("ownerSpell"), "AND"));
		sql.append(Utility.getLikeCond("A", "OWNER_NAME",  queryParam.get("ownerName"), "AND"));
		sql.append(Utility.getLikeCond("B", "LICENSE", queryParam.get("license"), "AND"));
		
		PageInfoDto pageInfoDto = DAOUtil.pageQuery((sql == null ? null : sql.toString()), queryList);
		return pageInfoDto;
	}

	@Override
	public List<Map> queryByLinsence2(Map<String, String> queryParam) throws ServiceBizException {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append(
				" SELECT A.*,B.OWNER_PROPERTY,B.OWNER_NAME,B.CONTACTOR_ZIP_CODE,B.CONTACTOR_EMAIL,B.CONTACTOR_ADDRESS,B.ADDRESS,B.PHONE,B.MOBILE,tb.brand_name,ts.series_name,tm.MODEL_NAME, ");
		sql.append(
				" (case when B.CONTACTOR_NAME IS NULL or B.CONTACTOR_NAME='' then B.OWNER_NAME else B.CONTACTOR_NAME end) AS CONTACTOR_NAME, ");
		sql.append(
				" (case when B.CONTACTOR_PHONE IS NULL or B.CONTACTOR_PHONE='' then B.PHONE else B.CONTACTOR_PHONE end) AS CONTACTOR_PHONE, ");
		sql.append(
				" (case when B.CONTACTOR_MOBILE IS NULL or B.CONTACTOR_MOBILE='' then B.MOBILE else B.CONTACTOR_MOBILE end) AS CONTACTOR_MOBILE, ");
		sql.append(
				" B.GENDER,B.CT_CODE,B.CERTIFICATE_NO,B.CONTACTOR_GENDER,B.E_MAIL,B.BIRTHDAY,'' as IS_MILEAGE_MODIFY,'' as sell_date ");
		sql.append(" ,'' as DELIVERER_DDD_CODE,'' as CHANGE_MILEAGE FROM (" + CommonConstants.VM_VEHICLE
				+ ") A left join (" + CommonConstants.VM_OWNER
				+ ") B on A.OWNER_NO = B.OWNER_NO and A.DEALER_CODE = B.DEALER_CODE ");
		// if (DictDataConstant.DICT_IS_YES.equals(vinflag))
		// {
		// sql += " AND (A.VIN LIKE '%" + license + "%' OR A.LICENSE LIKE '%" +
		// license
		// + "%') ";
		// }
		sql.append(
				"  LEFT JOIN ( SELECT mb.brand_code,mb.brand_name,mb.dealer_code FROM tm_brand mb ) tb ON A.DEALER_CODE=tb.DEALER_CODE AND A.BRAND=tb.BRAND_CODE ");
		sql.append(
				" LEFT JOIN (SELECT s.series_code,s.series_name,s.dealer_code,s.BRAND_CODE FROM tm_series s) ts  ON A.DEALER_CODE = ts.DEALER_CODE  AND A.SERIES = ts.SERIES_CODE  AND ts.brand_code=tb.brand_code");
		sql.append(
				" LEFT JOIN  (SELECT  m.DEALER_CODE, m.MODEL_NAME, m.MODEL_CODE, m.SERIES_CODE  FROM tm_model m) tm  ON A.DEALER_CODE = tm.DEALER_CODE  AND A.MODEL = TM.MODEL_CODE  AND ts.brand_code=tb.brand_code AND tm.series_code=ts.series_code WHERE  ");
		sql.append(" A.DEALER_CODE = ? ");
		params.add(FrameworkUtil.getLoginInfo().getDealerCode());
		sql.append(Utility.getLikeCond("A", "LICENSE", queryParam.get("license"), "AND"));
		sql.append(Utility.getLikeCond("A", "VIN", queryParam.get("vin"), "AND"));
		sql.append(Utility.getLikeCond("B", "OWNER_NAME", queryParam.get("ownerName"), "AND"));
		System.out.print(sql.toString());
		List<Map> list = DAOUtil.findAll(sql.toString(), params);
		return list;
	}

	@Override
	public void addOwner(OwnerDTO ownerDTO) throws ServiceBizException {
		CarownerPO carownerPO = new CarownerPO();
		carownerPO.setInteger("OWNER_PROPERTY", ownerDTO.getOwnerProperty());
		carownerPO.setString("OWNER_NO", ownerDTO.getOwnerNo());
		carownerPO.setString("OWNER_NAME", ownerDTO.getOwnerName());
		carownerPO.setString("OWNER_SPELL", ownerDTO.getOwnerSpell());
		carownerPO.setInteger("GENDER", ownerDTO.getGender());
		carownerPO.setInteger("CT_CODE", ownerDTO.getCtCode());
		carownerPO.setString("CERTIFICATE_NO", ownerDTO.getCertificateNo());
		carownerPO.setInteger("PROVINCE", ownerDTO.getProvince());
		carownerPO.setInteger("CITY", ownerDTO.getCity());
		carownerPO.setInteger("DISTRICT", ownerDTO.getDistrict());
		carownerPO.setString("ADDRESS", ownerDTO.getAddress());
		carownerPO.setInteger("ZIP_CODE", ownerDTO.getZipCode());
		carownerPO.setInteger("INDUSTRY_FIRST", ownerDTO.getIndustryFirst());
		carownerPO.setInteger("INDUSTRY_SECOND", ownerDTO.getIndustrySecond());
		carownerPO.setString("TAX_NO", ownerDTO.getTaxNo());
		carownerPO.setDouble("PRE_PAY", ownerDTO.getPrePay());
		carownerPO.setDouble("ARREARAGE_AMOUNT", ownerDTO.getArrearageAmount());
		carownerPO.setInteger("CUS_RECEIVE_SORT", ownerDTO.getCusReceiveSort());
		carownerPO.setString("DECISION_MARKER_NAME", ownerDTO.getDecisionMarkerName());
		carownerPO.setString("DECISION_MARKER_GENDER", ownerDTO.getDecisionMarkerGender());
		carownerPO.setDate("DECISION_MARKER_BIRTHDAY", ownerDTO.getDecisionMarkerBirthday());
		carownerPO.setString("DECISION_MARKER_MOBILE", ownerDTO.getDecisionMarkerMobile());
		carownerPO.setString("DECISION_MARKER_PHONE", ownerDTO.getDecisionMarkerPhone());
		carownerPO.setString("DECISION_MARKER_E_MAIL", ownerDTO.getDecisionMarkerEMail());
		carownerPO.setString("DECISION_MARKER_ADDRESS", ownerDTO.getDecisionMarkerAddress());
		carownerPO.setString("HOBBY", ownerDTO.getHobby() == null ? null : ownerDTO.getHobby().toString());
		carownerPO.setString("SECOND_ADDRESS", ownerDTO.getSecondAddress());
		carownerPO.setString("PHONE", ownerDTO.getPhone());
		carownerPO.setString("E_MAIL", ownerDTO.getEMail());
		carownerPO.setDate("BIRTHDAY", ownerDTO.getBirthday());
		carownerPO.setInteger("MOBILE", ownerDTO.getMobile());
		carownerPO.setString("FAMILY_INCOME", ownerDTO.getFamilyIncome());
		carownerPO.setString("OWNER_MARRIAGE", ownerDTO.getOwnerMarriage());
		carownerPO.setString("EDU_LEVEL", ownerDTO.getEduLevel());
		carownerPO.setString("CONTACTOR_NAME", ownerDTO.getContactorName());
		carownerPO.setInteger("CONTACTOR_GENDER", ownerDTO.getContactorGender());
		carownerPO.setString("CONTACTOR_PHONE", ownerDTO.getContactorPhone());
		carownerPO.setString("CONTACTOR_EMAIL", ownerDTO.getContactorEmail());
		carownerPO.setString("CONTACTOR_FAX", ownerDTO.getContactorFax());
		carownerPO.setString("CONTACTOR_MOBILE", ownerDTO.getContactorMobile());
		carownerPO.setInteger("CONTACTOR_PROVINCE", ownerDTO.getContactorProvince());
		carownerPO.setInteger("CONTACTOR_CITY", ownerDTO.getContactorCity());
		carownerPO.setInteger("CONTACTOR_DISTRICT", ownerDTO.getContactorDistrict());
		carownerPO.setString("CONTACTOR_ADDRESS", ownerDTO.getContactorAddress());
		carownerPO.setInteger("CONTACTOR_ZIP_CODE", ownerDTO.getContactorZipCode());
		carownerPO.setInteger("CONTACTOR_HOBBY_CONTACT", ownerDTO.getContactorHobbyContact());
		carownerPO.setInteger("CONTACTOR_VOCATION_TYPE", ownerDTO.getContactorVocationType());
		carownerPO.setInteger("CONTACTOR_POSITION", ownerDTO.getContactorPosition());
		carownerPO.setString("REMARK", ownerDTO.getRemark());
		carownerPO.saveIt();
		OwnerMemoPO ownerMemoPO = new OwnerMemoPO();
		ownerMemoPO.setString("OWNER_NO", ownerDTO.getOwnerNo());
		ownerMemoPO.setString("MEMO_INFO", ownerDTO.getOwnerMemo());
		ownerMemoPO.saveIt();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void deleteRepairOrder(Map<String, String> queryParam) throws Exception {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		String roNo = queryParam.get("roNo");
		String ObligatedNo = "";
		Integer roStatus = 0;
		Integer isCloseRO = 0;
		Integer isDelete = 12781001;
		//1.自动解除配件预留
		RepairOrderPO orderPO = RepairOrderPO.findByCompositeKeys(dealerCode,roNo);
		String sql = "SELECT * FROM TT_PART_OBLIGATED WHERE dealer_code = " + dealerCode + " AND RO_NO= " + roNo; 
		List<Map> list = DAOUtil.findAll(sql, null);
		if (list != null && list.size() > 0){
			for(int t = 0; t < list.size(); t++){
				ObligatedNo = (list.get(t).get("OBLIGATED_NO") == null ? null : list.get(t).get("OBLIGATED_NO").toString());
				if (ObligatedNo.equals("")){
					continue;
				}
				if (!StringUtils.isNullOrEmpty(list.get(t).get("OBLIGATED_CLOSE"))) {
					if(Integer.parseInt(list.get(t).get("OBLIGATED_CLOSE").toString()) == 12781001){
						continue;
					}
				}
				String sql1 = "SELECT * FROM tt_part_obligated_item WHERE dealer_code = " + dealerCode + " AND RO_NO= " + roNo; 
				List<Map> list1 = DAOUtil.findAll(sql1, null);
				if (list1 != null && list1.size() > 0){
					for(int i = 0; i < list1.size(); i++){
						if(Integer.parseInt(list1.get(i).get("IS_OBLIGATED").toString()) != 12781001){
							continue;	
						}
						//更新配件预留明细
						String st = " QUANTITY = 0.0, COST_AMOUNT = 0.0, COST_PRICE = 0.0, IS_OBLIGATED = 12781002 ";
						String str = "ITEM_ID = ? " ;
						List<Object> lis = new ArrayList<Object>();
						lis.add(list1.get(i).get("ITEM_ID"));
						TtPartObligatedItemPO.update(st, str, lis.toArray());
						String sql2 = "SELECT * FROM TM_PART_STOCK WHERE dealer_code = " + dealerCode + " AND PART_NO= " + list1.get(i).get("PART_NO") + " AND STORAGE_CODE= " + list1.get(i).get("STORAGE_CODE"); 
						List<Map> list2 = DAOUtil.findAll(sql2, null);
						List<Object> resultList = new ArrayList<Object>();
						if(list2 != null){
							Double lock = Double.valueOf(list2.get(0).get("STOCK_QUANTITY").toString());
							if(list1.get(i).get("STOCK_QUANTITY") != null){
								if(lock >= Double.valueOf(list1.get(i).get("STOCK_QUANTITY").toString())){
									resultList.add(lock-Double.valueOf(list1.get(i).get("STOCK_QUANTITY").toString()));
								}else{
									resultList.add(lock);
								}
								resultList.add(list1.get(i).get("PART_NO"));
							}
							//更新配件库存
							String stock = " STOCK_QUANTITY = ? ";
							String stock2 = "PART_NO = ? " ;
							TmPartStockPO.update(stock, stock2, resultList.toArray());
						}
					}
				}
			}
		}
//		if(orderPO != null){
//			roStatus = orderPO.getInteger("RO_STATUS");
//			isCloseRO = orderPO.getInteger("IS_CLOSE_RO");
//		}
//		if (roStatus != null
//				&& roStatus != 12551001) {
//			throw new ServiceBizException("工单已竣工或者已提交结算,不能再编辑！");
//		
//		}
//		TraceTaskPO traceTaskPO = TraceTaskPO.findByCompositeKeys(dealerCode,roNo);
//		if(traceTaskPO != null){
//			throw new ServiceBizException("工单已做了跟踪回访，无法删除！");
//		}
//		String sql = " select count(PART_QUANTITY) as partQuantity from TT_RO_REPAIR_PART where IS_FINISHED = 12781001 and dealer_code= " + dealerCode + " and ro_no=" + roNo;
//		List<Map> list = DAOUtil.findAll(sql, null);
//		if(Integer.parseInt(list.get(0).get("partQuantity").toString()) == 1 ){
//			throw new ServiceBizException("入帐维修配件数量大于零，不能作废！");
//		}
//		String sql1 = " SELECT SUM(LEND_QUANTITY) as sum FROM TT_PART_WORKSHOP_ITEM WHERE dealer_code = " + dealerCode + "  AND RO_NO= " + roNo;
//		List<Map> list1 = DAOUtil.findAll(sql1, null);
//		if(Integer.parseInt(list1.get(0).get("sum").toString()) != 0 ){
//			throw new ServiceBizException("存在车间借料，不能作废！");
//		}
//		TtRoAssignPO ttRoAssignPO = TraceTaskPO.findByCompositeKeys(dealerCode,roNo); 
//		String sql2 = " SELECT * FROM TT_RO_ASSIGN WHERE dealer_code = " + dealerCode + " AND RO_NO= " + roNo;
//		List<Map> list2 = DAOUtil.findAll(sql2, null);
//		if(list2 != null && list2.size() > 0){
//			throw new ServiceBizException("有派工信息，不能作废！");
//		}
		//2.删除维修工单
		//将对应的预约单状态修改为取消进厂
		if(orderPO != null ){
			Long usedLabourCount = 0L;
			String flag = "2";
			String flag2 = "1"; //1代表作废  0代表其他
			if(orderPO.getString("BOOKING_ORDER_NO") != null && orderPO.getString("BOOKING_ORDER_NO") != ""){
				
				String repair = " select * from TT_REPAIR_ORDER where dealer_code = "+ dealerCode + " AND BOOKING_ORDER_NO= " + orderPO.getString("BOOKING_ORDER_NO");
				List<Map> repairList = DAOUtil.findAll(repair, null);
				String booking = " select * from TT_BOOKING_ORDER where dealer_code = "+ dealerCode + " AND BOOKING_ORDER_NO= " + orderPO.getString("BOOKING_ORDER_NO");
				List<Map> bookingList = DAOUtil.findAll(booking, null);
				if(repairList.size() == 1 && bookingList.size() == 1){
					String book = "BOOKING_ORDER_STATUS = 12541001 ";
					String book1 = "dealer_code = ? AND BOOKING_ORDER_NO = ? ";
					List<Object> resultList = new ArrayList<Object>();
					resultList.add(dealerCode);
					resultList.add(orderPO.getString("BOOKING_ORDER_NO"));
					TtBookingOrderPO.update(book, book1, resultList.toArray());
					String roLabour = " select * from TT_RO_LABOUR where dealer_code = "+ dealerCode + " AND RO_NO= " + roNo;	
					List<Map> roLabourList = DAOUtil.findAll(roLabour, null);
					if(roLabourList !=null && roLabourList.size() > 0){
						for(int i=0; i< roLabourList.size(); i++){
							//删除维修项目流水
							if(roLabourList.get(i).get("CARD_ID") != null && Integer.parseInt(roLabourList.get(i).get("CARD_ID").toString()) != 0 && !StringUtils.isNullOrEmpty(list.get(i).get("ACTIVITY_CODE"))){
								if(roLabourList.get(i).get("LABOUR_CODE") != null){
									String labourCnd = " select * from TT_MEMBER_LABOUR where dealer_code = "+ dealerCode + " AND CARD_ID= " + roLabourList.get(i).get("CARD_ID") + " AND ACTIVITY_CODE= " + list.get(i).get("ACTIVITY_CODE");	
									List<Map> labourCndList = DAOUtil.findAll(labourCnd, null);
									if(labourCndList != null && labourCndList.size()>0){
										if(!"".equals(flag)){
											usedLabourCount = Long.parseLong(labourCndList.get(i).get("USED_LABOUR_COUNT").toString())-1L;
										}else{
											usedLabourCount = Long.parseLong(labourCndList.get(i).get("USED_LABOUR_COUNT").toString())+1L;
										}
										String usedLabour = " USED_LABOUR_COUNT = ? ";
										String usedLabour2 = "dealer_code = ? AND CARD_ID = ? AND ACTIVITY_CODE = ?"; 
										List<Object> params = new ArrayList<Object>();
										params.add(usedLabourCount);
										params.add(dealerCode);
										params.add(roLabourList.get(i).get("CARD_ID"));
										params.add(list.get(i).get("ACTIVITY_CODE"));
										TtMemberLabourPO.update(usedLabour, usedLabour2, params.toArray());
										//进行工单作废操作时，需要删除流水
										if("2".equals(flag)){
											String deleteLabour = " delete from TT_MEMBER_LABOUR_FLOW where  dealer_code = "+ dealerCode + " AND CARD_ID= " + roLabourList.get(i).get("CARD_ID") + " AND RO_NO= " + roNo;
											TtMemberLabourFlowPO.delete(deleteLabour);
										}else{
											String Labour = " select * from TT_MEMBER_LABOUR_FLOW where  dealer_code = "+ dealerCode + " AND CARD_ID= " + roLabourList.get(i).get("CARD_ID") + " AND RO_NO= " + roNo;
											List<Map> labourList = DAOUtil.findAll(Labour, null);
											if(labourList != null && labourList.size() > 0 && "".equals(flag)){
												String Lab = " THIS_USE_COUNT= ?, USED_LABOUR_COUNT= ? ";
												String Lab1 = "dealer_code = ? AND CARD_ID= ? AND RO_NO= ? ";
												List<Object> labours = new ArrayList<Object>();
												String thisUseCount = labourList.get(0).get("THIS_USE_COUNT") == null ? "0" : labourList.get(0).get("THIS_USE_COUNT").toString();
												labours.add(Long.parseLong(thisUseCount) + 1L);
												labours.add(usedLabourCount);
												labours.add(dealerCode);
												labours.add(roLabourList.get(i).get("CARD_ID"));
												labours.add(roNo);
												TtMemberLabourFlowPO.update(Lab, Lab1, labours);
											}else{
												TtMemberLabourFlowPO ttMemberLabourFlowPO = new TtMemberLabourFlowPO();
//												ttMemberLabourFlowPO.setString("DEALER_CODE", dealerCode);
												ttMemberLabourFlowPO.setString("RO_NO", roLabourList.get(i).get("RO_NO"));
												ttMemberLabourFlowPO.setInteger("CARD_ID", roLabourList.get(i).get("CARD_ID"));
												ttMemberLabourFlowPO.setString("LABOUR_CODE", roLabourList.get(i).get("LABOUR_CODE"));
												ttMemberLabourFlowPO.setString("LABOUR_NAME", roLabourList.get(i).get("LABOUR_NAME"));
												ttMemberLabourFlowPO.setLong("SERVICE_LABOUR_COUNT", roLabourList.get(i).get("SERVICE_LABOUR_COUNT"));
												ttMemberLabourFlowPO.setLong("USED_LABOUR_COUNT", usedLabourCount);
												if("".equals(flag)){
													ttMemberLabourFlowPO.setLong("THIS_USE_COUNT", 1L);
												}else{
												ttMemberLabourFlowPO.setLong("THIS_USE_COUNT", -1L);
												}
												ttMemberLabourFlowPO.setString("CHARGE_PARTITION_CODE", roLabourList.get(i).get("CHARGE_PARTITION_CODE"));
												ttMemberLabourFlowPO.setString("MODEL_LABOUR_CODE", roLabourList.get(i).get("MODEL_LABOUR_CODE"));
												ttMemberLabourFlowPO.setDouble("STD_LABOUR_HOUR", roLabourList.get(i).get("STD_LABOUR_HOUR"));
												ttMemberLabourFlowPO.setDouble("ASSIGN_LABOUR_HOUR", roLabourList.get(i).get("ASSIGN_LABOUR_HOUR"));
												ttMemberLabourFlowPO.setDouble("LABOUR_AMOUNT", roLabourList.get(i).get("LABOUR_AMOUNT"));
												ttMemberLabourFlowPO.setDouble("LABOUR_PRICE", roLabourList.get(i).get("LABOUR_PRICE"));
												ttMemberLabourFlowPO.setString("OPERATOR", FrameworkUtil.getLoginInfo().getUserName());
												ttMemberLabourFlowPO.setDate("OPERATE_TIME", Utility.getCurrentDateTime());
												ttMemberLabourFlowPO.setDouble("DISCOUNT", roLabourList.get(i).get("DISCOUNT"));
												ttMemberLabourFlowPO.saveIt();
											}
										}
									}
								}else{
									if("2".equals(flag) && Utility.testString(roNo)){
										String deleteLabour = "delete from TT_MEMBER_LABOUR_FLOW where  dealer_code = "+ dealerCode + " AND RO_NO= " + roNo;
										TtMemberLabourFlowPO.delete(deleteLabour);
									}
								}
					
						}
							//删除工单会员活动流水
							if(roLabourList.get(i).get("CARD_ID") != null && Integer.parseInt(roLabourList.get(i).get("CARD_ID").toString()) != 0 
									&& Utility.testString(list.get(i).get("ACTIVITY_CODE") == null ? null : list.get(i).get("ACTIVITY_CODE").toString())){
								String actiDetail = " select * from TT_MEM_CARD_ACTI_DETAIL where dealer_code = "+ dealerCode + " AND CARD_ID= " + roLabourList.get(i).get("CARD_ID") + " AND MEMBER_ACTIVITY_CODE= " + list.get(i).get("ACTIVITY_CODE");	
								List<Map> actiDetailList = DAOUtil.findAll(actiDetail, null);
								 if(actiDetailList!=null && actiDetailList.size()>0){
									 //更新会员卡活动已用次数
									String TmMemberCardActivity = " USED_TICKET_COUNT= ? ";
									String TmMemberCardActivity1 = "  dealer_code = ? AND CARD_ID= ? AND MEMBER_ACTIVITY_CODE= ? ";
									List<Object> activity = new ArrayList<Object>();
									activity.add(Integer.parseInt(actiDetailList.get(0).get("USED_TICKET_COUNT").toString()) - 1);
									activity.add(dealerCode);
									activity.add(roLabourList.get(i).get("CARD_ID"));
									activity.add(list.get(i).get("ACTIVITY_CODE"));
									TmMemberCardActivityPO.update(TmMemberCardActivity, TmMemberCardActivity1, activity.toArray());
									 //删除会员活动流水
									 String delete = " delete from TT_MEM_CARD_ACTI_DETAIL where dealer_code = "+ dealerCode + " AND CARD_ID= " + roLabourList.get(i).get("CARD_ID") + " AND MEMBER_ACTIVITY_CODE= " + list.get(i).get("ACTIVITY_CODE");	
									 TtMemCardActiDetailPO.delete(delete);
								 }
							}
							String deleteTtRoRepairPart = " DELETE from TT_RO_REPAIR_PART where dealer_code = "+ dealerCode + " AND RO_NO= " + roNo;
							TtRoRepairPartPO.delete(deleteTtRoRepairPart);
							//子表：工单维修配件明细，主表：维修工单
							RepairOrderPO poDmsUpdate = RepairOrderPO.findByCompositeKeys(dealerCode,roNo);
							poDmsUpdate.saveIt();
					}
				}else{
					if("2".equals(flag) && Utility.testString(roNo)){
						String deleteLabour = "delete from TT_MEMBER_LABOUR_FLOW where  dealer_code = "+ dealerCode + " AND RO_NO= " + roNo;
						TtMemberLabourFlowPO.delete(deleteLabour);
					}
				}
					String roLabour3 = " delete from TT_RO_LABOUR where dealer_code = "+ dealerCode + " AND RO_NO= " + roNo;
					TtRoLabourPO.delete(roLabour3);
					RepairOrderPO poDmsUpdate = RepairOrderPO.findByCompositeKeys(dealerCode,roNo);
					poDmsUpdate.saveIt();
					String sql1 = " select * from TT_RO_REPAIR_PART where  dealer_code= " + dealerCode + " and ro_no=" + roNo;
					List<Map> list1 = DAOUtil.findAll(sql1, null);
					if(list1 != null && list1.size()>0){
						for(int i=0; i< list1.size(); i++){
						OperateLogPO operateLogPO = new OperateLogPO();
						operateLogPO.setString("DEALER_CODE", dealerCode);
						operateLogPO.setString("OPERATOR", FrameworkUtil.getLoginInfo().getUserName());
						operateLogPO.setInteger("OPERATE_TYPE", 12061002);
						operateLogPO.setString("OPERATE_CONTENT", "因工单作废而删除维修配件：工单号："+ list1.get(i).get("RO_NO") + ";" + list1.get(i).get("STORAGE_CODE") + ";"
								+ list1.get(i).get("PART_NO") + ";"+ list1.get(i).get("PART_NAME") + ";"+ list1.get(i).get("PART_BATCH_NO") + ";"+ list1.get(i).get("PART_QUANTITY") + ";"
								+ list1.get(i).get("PART_COST_PRICE") + ";"+ list1.get(i).get("PART_SALES_PRICE") + ";"+ list1.get(i).get("SENDER") + ";");
						operateLogPO.setString("REMARK", "RepairOrderPO");
						operateLogPO.setString("DEALER_CODE", dealerCode);
						operateLogPO.saveIt();
						
						}
					}
					List<Map> list2 = DAOUtil.findAll(sql1, null);
					if(list2 != null && list2.size()>0){
						for(int i=0; i< list2.size(); i++){
							if(list2.get(i).get("CARD_ID") != null && Integer.parseInt(list2.get(i).get("CARD_ID").toString()) != 0 && !StringUtils.isNullOrEmpty(list2.get(i).get("ACTIVITY_CODE"))){
								if(list2.get(i).get("PART_NO") != null){
									String part = " select * from TT_MEMBER_PART where dealer_code = "+ dealerCode + " AND CARD_ID= " + list2.get(i).get("CARD_ID") + " AND PART_NO= " + list2.get(i).get("PART_NO");	
									List<Map> partList = DAOUtil.findAll(part, null);
									if(partList != null && partList.size()>0){
										Double usedPartCount = 0.0;
										//删除退回标识（用于会员删除时回退）
										if(!"".equals(flag)){
											usedPartCount = Double.parseDouble(partList.get(0).get("USED_PART_COUNT").toString()) - Double.parseDouble(list2.get(i).get("PART_QUANTITY").toString());
										}else{
											usedPartCount = Double.parseDouble(partList.get(0).get("USED_PART_COUNT").toString()) + Double.parseDouble(list2.get(i).get("PART_QUANTITY").toString());
										}
										String part1 = " USED_PART_COUNT= ? ";
										String part2 = " dealer_code = ? AND CARD_ID= ? AND PART_NO= ? ";
										List<Object> MemberPart = new ArrayList<Object>();
										MemberPart.add(usedPartCount);
										MemberPart.add(dealerCode);
										MemberPart.add(list2.get(i).get("CARD_ID"));
										MemberPart.add(list2.get(i).get("PART_NO"));
										TtMemberPartPO.update(part1, part2, MemberPart.toArray());
										//进行工单作废操作时，需要删除流水
										if("2".equals(flag) && "0".equals(flag2)){
											String deleteMemberPart = " delete from TT_MEMBER_PART_FLOW where dealer_code = "+ dealerCode + " AND CARD_ID= " + list2.get(i).get("CARD_ID") + " AND PART_NO= " + list2.get(i).get("PART_NO") + " AND RO_NO= " + roNo;
											TtMemberPartFlowPO.delete(deleteMemberPart);
										}else if("2".equals(flag) && "1".equals(flag2)){
											String deleteMemberPart = " delete from TT_MEMBER_PART_FLOW where dealer_code = "+ dealerCode + " AND CARD_ID= " + list2.get(i).get("CARD_ID") + " AND PART_NO= " + list2.get(i).get("PART_NO") + " AND RO_NO= " + roNo;
											TtMemberPartFlowPO.delete(deleteMemberPart);
										}else{
											String MemberParts = " select * from TT_MEMBER_PART_FLOW where dealer_code = "+ dealerCode + " AND CARD_ID= " + list2.get(i).get("CARD_ID") + " AND PART_NO= " + list2.get(i).get("PART_NO") + " AND RO_NO= " + roNo;
											List<Map> partFlowList = DAOUtil.findAll(MemberParts, null);
											if(partFlowList != null && partFlowList.size() >0 && "".equals(flag)){
												String partFlow1 = " THIS_USE_QUANTITY= ?, USED_PART_QUANTITY= ? ";
												String partFlow2 = " dealer_code = ? AND CARD_ID= ? AND PART_NO= ? ";
												List<Object> partFlow = new ArrayList<Object>();
												partFlow.add(Double.parseDouble(partFlowList.get(0).get("THIS_USE_QUANTITY").toString()) + Double.parseDouble(list2.get(i).get("PART_QUANTITY").toString()));
												partFlow.add(usedPartCount);
												partFlow.add(dealerCode);
												partFlow.add(list2.get(i).get("CARD_ID"));
												partFlow.add(list2.get(i).get("PART_NO"));
												TtMemberPartFlowPO.update(partFlow1, partFlow2, partFlow.toArray());
											}else{
												TtMemberPartFlowPO ttMemberPartFlowPO = new TtMemberPartFlowPO();
												ttMemberPartFlowPO.setString("RO_NO", roNo);
												ttMemberPartFlowPO.setString("CARD_ID", list2.get(i).get("CARD_ID"));
												ttMemberPartFlowPO.setString("STORAGE_CODE", list2.get(i).get("STORAGE_CODE"));
												ttMemberPartFlowPO.setString("PART_NO", list2.get(i).get("PART_NO"));
												ttMemberPartFlowPO.setString("PART_NAME", list2.get(i).get("PART_NAME"));
												ttMemberPartFlowPO.setDouble("USED_PART_QUANTITY", usedPartCount);
												ttMemberPartFlowPO.setDouble("PART_QUANTITY", partList.get(0).get("PART_QUANTITY"));
												ttMemberPartFlowPO.setString("CHARGE_PARTITION_CODE", list2.get(i).get("CHARGE_PARTITION_CODE"));
												ttMemberPartFlowPO.setString("UNIT_NAME", list2.get(i).get("UNIT_NAME"));
												if("".equals(flag)){
													ttMemberPartFlowPO.setDouble("THIS_USE_QUANTITY", list2.get(i).get("PART_QUANTITY"));
												}else{
													ttMemberPartFlowPO.setDouble("THIS_USE_QUANTITY", -Double.parseDouble(list2.get(i).get("PART_QUANTITY").toString()));
												}
											
												ttMemberPartFlowPO.setDouble("PART_SALES_PRICE", list2.get(i).get("PART_SALES_PRICE"));
												ttMemberPartFlowPO.setDouble("PART_COST_PRICE", list2.get(i).get("PART_COST_PRICE"));
												ttMemberPartFlowPO.setDouble("PART_SALES_AMOUNT", list2.get(i).get("PART_SALES_AMOUNT"));
												ttMemberPartFlowPO.setDouble("PART_COST_AMOUNT", list2.get(i).get("PART_COST_AMOUNT"));
												ttMemberPartFlowPO.setString("OPERATOR", FrameworkUtil.getLoginInfo().getUserName());
												ttMemberPartFlowPO.setDate("OPERATE_TIME", Utility.getCurrentDateTime());
												ttMemberPartFlowPO.setDouble("DISCOUNT", list2.get(i).get("DISCOUNT"));
												ttMemberPartFlowPO.setInteger("IS_MAIN_PART", list2.get(i).get("IS_MAIN_PART"));
												ttMemberPartFlowPO.setString("LABOUR_CODE", list2.get(i).get("LABOUR_CODE"));
												ttMemberPartFlowPO.saveIt();
											}
										
										}
									}
								}else{
									if("2".equals(flag) && Utility.testString(roNo)){
										String part = " select * from TT_MEMBER_PART_FLOW where dealer_code = "+ dealerCode + " AND RO_NO= " + roNo;	
										List<Map> partList = DAOUtil.findAll(part, null);
										if(partList != null && partList.size()>0){
											for(int j=0; j< partList.size(); j++){
												String part1 = " select * from TT_MEMBER_PART where dealer_code = "+ dealerCode + " AND CARD_ID= " + partList.get(j).get("CARD_ID") + " AND PART_NO= " + partList.get(j).get("PART_NO");	
												List<Map> partList1 = DAOUtil.findAll(part1, null);
												if(partList1 != null && partList1.size()>0){
													String part2 = " USED_PART_COUNT= ? ";
													String part3 = " dealer_code = ? AND CARD_ID= ? AND PART_NO= ? ";
													List<Object> MemberPart = new ArrayList<Object>();
													MemberPart.add(Double.parseDouble(partList1.get(0).get("USED_PART_COUNT").toString()) - Double.parseDouble(partList.get(j).get("THIS_USE_QUANTITY").toString()));
													MemberPart.add(dealerCode);
													MemberPart.add(partList.get(j).get("CARD_ID"));
													MemberPart.add(partList.get(j).get("PART_NO"));
													TtMemberPartPO.update(part2, part3, MemberPart.toArray());
												}
												
											}
										}
										String deleteMemberPart = " delete from TT_MEMBER_PART_FLOW where dealer_code = "+ dealerCode + " AND RO_NO= " + roNo;
										TtMemberPartFlowPO.delete(deleteMemberPart);
										
									}
								}
							}
							//删除工单会员活动流水
							if(list2.get(i).get("CARD_ID") != null && Integer.parseInt(list2.get(i).get("CARD_ID").toString()) != 0 && Utility.testString(list2.get(i).get("ACTIVITY_CODE").toString())){
								String part = " select * from TT_MEM_CARD_ACTI_DETAIL where dealer_code = "+ dealerCode + " AND CARD_ID= " + list2.get(i).get("CARD_ID") + " AND MEMBER_ACTIVITY_CODE= " + list2.get(i).get("ACTIVITY_CODE");	
								List<Map> partList = DAOUtil.findAll(part, null);
								 if(partList!=null && partList.size()>0){
									 //更新会员卡活动已用次数
										String TmMemberCardActivity = " USED_TICKET_COUNT= ? ";
										String TmMemberCardActivity1 = "  dealer_code = ? AND CARD_ID= ? AND MEMBER_ACTIVITY_CODE= ? ";
										List<Object> activity = new ArrayList<Object>();
										activity.add(Integer.parseInt(partList.get(0).get("USED_TICKET_COUNT").toString()) - 1);
										activity.add(dealerCode);
										activity.add(list2.get(i).get("CARD_ID"));
										activity.add(list2.get(i).get("ACTIVITY_CODE"));
										TmMemberCardActivityPO.update(TmMemberCardActivity, TmMemberCardActivity1, activity.toArray());
										String delete = " delete from TT_MEM_CARD_ACTI_DETAIL where dealer_code = "+ dealerCode + " AND CARD_ID= " + list2.get(i).get("CARD_ID") + " AND MEMBER_ACTIVITY_CODE= " + list2.get(i).get("ACTIVITY_CODE");	
										TtMemCardActiDetailPO.delete(delete);
								 }
							}
						}
					}else{
						if("2".equals(flag) && Utility.testString(roNo)){
							String part = " select * from TT_MEMBER_PART_FLOW where dealer_code = "+ dealerCode + " AND RO_NO= " + roNo;	
							List<Map> partList = DAOUtil.findAll(part, null);
							if(partList != null && partList.size()>0){
								for(int j=0; j< partList.size(); j++){
									String part1 = " select * from TT_MEMBER_PART where dealer_code = "+ dealerCode + " AND CARD_ID= " + partList.get(j).get("CARD_ID") + " AND PART_NO= " + partList.get(j).get("PART_NO");	
									List<Map> partList1 = DAOUtil.findAll(part1, null);
									if(partList1 != null && partList1.size()>0){
										String part2 = " USED_PART_COUNT= ? ";
										String part3 = " dealer_code = ? AND CARD_ID= ? AND PART_NO= ? ";
										List<Object> MemberPart = new ArrayList<Object>();
										MemberPart.add(Double.parseDouble(partList1.get(0).get("USED_PART_COUNT").toString()) - Double.parseDouble(partList.get(j).get("THIS_USE_QUANTITY").toString()));
										MemberPart.add(dealerCode);
										MemberPart.add(partList.get(j).get("CARD_ID"));
										MemberPart.add(partList.get(j).get("PART_NO"));
										TtMemberPartPO.update(part2, part3, MemberPart.toArray());
									}
									
								}
							}
							String deleteMemberPart = " delete from TT_MEMBER_PART_FLOW where dealer_code = "+ dealerCode + " AND RO_NO= " + roNo;
							TtMemberPartFlowPO.delete(deleteMemberPart);
							
						}
					}
					String deleteTtRoRepairPart = " DELETE from TT_RO_REPAIR_PART where dealer_code = "+ dealerCode + " AND RO_NO= " + roNo;
					TtRoRepairPartPO.delete(deleteTtRoRepairPart);
					RepairOrderPO poDmsUpdate1 = RepairOrderPO.findByCompositeKeys(dealerCode,roNo);
					poDmsUpdate1.saveIt();
					String sale = " SO_NO= '' ";
					String sales = " dealer_code = ? AND RO_NO= ? ";
					List<Object> salesList = new ArrayList<Object>();
					salesList.add(dealerCode);
					salesList.add(roNo);
					TtSalesPartPO.update(sale, sales, salesList.toArray());
					String roAddItem = " delete from TT_RO_ADD_ITEM where dealer_code = "+ dealerCode + " AND RO_NO= " + roNo;
					RoAddItemPO.delete(roAddItem);
					String overDb = " delete from TT_RO_MANAGE where dealer_code = "+ dealerCode + " AND RO_NO= " + roNo;
					RoManagePO.delete(overDb);
					String repairOrder = " delete from TT_REPAIR_ORDER where dealer_code = "+ dealerCode + " AND RO_NO= " + roNo;
					RepairOrderPO.delete(repairOrder);
					
			}
		}
	} 
	//3.工单作废后删除质损单中工单信息
		String roAddItem = " select * from TM_MAR_ORDER where dealer_code = "+ dealerCode + " AND RO_NO= " + roNo;
		List<Map> result = DAOUtil.findAll(roAddItem, null);
		if(result != null && result.size()> 0){
			String marOrder = " SO_NO= '' ";
			String marOrder1 = " dealer_code = ? AND RO_NO= ? ";
			List<Object> marOrderList = new ArrayList<Object>();
			marOrderList.add(dealerCode);
			marOrderList.add(roNo);
			TmMarOrderPO.update(marOrder, marOrder1, marOrderList.toArray());
		}
		
		//4.出险信息登记更新
		String sqls = "select * from TT_OCCUR_INSURANCE_REGISTER where dealer_code = "+ dealerCode + " AND RO_NO= " + roNo;
		List<Map> results = DAOUtil.findAll(sqls, null);
		if(results != null && results.size() >0){
			TtOccurInsuranceRegisterPO ttOccurInsuranceRegisterPO = TtOccurInsuranceRegisterPO.findByCompositeKeys(results.get(0).get("OCCUR_INSURANCE_NO"),dealerCode);
			if(ttOccurInsuranceRegisterPO != null){
				ttOccurInsuranceRegisterPO.setInteger("PAYMENT", 0);
				ttOccurInsuranceRegisterPO.setInteger("TRACE_STATUS", 16061001);
				ttOccurInsuranceRegisterPO.setInteger("DEAL_STATUS", 0);
				ttOccurInsuranceRegisterPO.setDate("DELIVER_BILL_DATE", Utility.getTimeStamp(""));
				ttOccurInsuranceRegisterPO.setString("DELIVER_BILL_MAN", "");
				ttOccurInsuranceRegisterPO.setInteger("IS_OLD_PART", 0);
				ttOccurInsuranceRegisterPO.setDate("SETTLE_COL_DATE", Utility.getTimeStamp(""));
				ttOccurInsuranceRegisterPO.setDate("CASE_CLOSED_DATE", Utility.getTimeStamp(""));
				ttOccurInsuranceRegisterPO.setInteger("SETTLEMENT_STATUS", 0);
				ttOccurInsuranceRegisterPO.setDouble("DS_FACT_BALANCE", 0.0);
				ttOccurInsuranceRegisterPO.setString("SETTLEMENT_REMARK", "");
				ttOccurInsuranceRegisterPO.setInteger("OLDPART_TREAT_MODE", 0);
				ttOccurInsuranceRegisterPO.setString("OLDPART_REMARK", "");
				ttOccurInsuranceRegisterPO.setDate("OLDPART_TREAT_DATE", Utility.getTimeStamp(""));
				ttOccurInsuranceRegisterPO.setString("RO_NO", "");
				ttOccurInsuranceRegisterPO.saveIt();
			}
		}
		
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> querySalesPartByRoNO(String id) throws ServiceBizException {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append(" SELECT 1 AS SEL_FLAG,1 AS IS_DXP,'' AS SOURCE_NO,'' AS DXP_UPDATE_STATUS,'' AS SOURCE_ITEM, 12781002 AS IS_SELECTED,A.DISCOUNT AS DISCOUNT_COPY,A.ITEM_ID,A.DEALER_CODE,A.SALES_PART_NO,A.STORAGE_CODE,A.STORAGE_POSITION_CODE,A.PART_NO,A.PART_BATCH_NO,A.PART_NAME,A.PART_QUANTITY,A.BATCH_NO,A.DISCOUNT ");
		sql.append(" ,A.PRICE_TYPE,A.PRICE_RATE,A.OEM_LIMIT_PRICE,A.CHARGE_PARTITION_CODE,A.MANAGE_SORT_CODE,A.UNIT_CODE,A.PART_COST_PRICE,A.PART_SALES_PRICE,A.PART_COST_AMOUNT ");
		sql.append(" ,A.IS_FINISHED,A.FINISHED_DATE,A.SENDER,A.RECEIVER,A.SEND_TIME,A.D_KEY,A.UPDATED_AT,A.UPDATED_BY,A.CREATED_AT,A.CREATED_BY,A.VER,A.IS_DISCOUNT,A.DXP_DATE ");
		sql.append(" ,A.OTHER_PART_COST_PRICE,A.OTHER_PART_COST_AMOUNT,A.SALES_AMOUNT PART_SALES_AMOUNT,A.SALES_DISCOUNT,A.OLD_SALES_PART_NO,C.DOWN_TAG FROM TT_SALES_PART_ITEM A LEFT JOIN TT_SALES_PART B ON ");
		sql.append(" A.SALES_PART_NO=B.SALES_PART_NO LEFT JOIN TM_PART_INFO C ON A.DEALER_CODE = C.DEALER_CODE AND A.PART_NO = C.PART_NO where A.DEALER_CODE= ? ");
		params.add(FrameworkUtil.getLoginInfo().getDealerCode());
		sql.append(" AND B.RO_NO= ? ");
		params.add(id);
		sql.append(" AND B.DEALER_CODE= ? ");
		params.add(FrameworkUtil.getLoginInfo().getDealerCode());
		sql.append(" AND A.D_KEY="+ CommonConstants.D_KEY + " AND B.D_KEY=" + CommonConstants.D_KEY + " ORDER BY A.ITEM_ID");
		List<Map> list = DAOUtil.findAll(sql.toString(), params);
		return list;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> conferDiscountMode(Map<String, String> queryParam) {
		String employeeNo = null;
		//检查当前操作人密码是否正确
		String password = null;
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		Long userId = FrameworkUtil.getLoginInfo().getUserId();
		UserPO userPO = UserPO.findByCompositeKeys(dealerCode,userId);
		
		if(userPO != null){
		 employeeNo = userPO.getString("EMPLOYEE_NO");
		}
//		EmployeePo employeePo = EmployeePo.findByCompositeKeys(dealerCode,employeeNo);
		if(userPO == null){
			throw new ServiceBizException("查询错误！");
		}
		password = userPO.getString("PASSWORD");
		boolean validation = false;
		if (queryParam.get("password") != null){
			validation=MD5Util.validPassword(queryParam.get("password"), password);
		}
		if(validation == false){
			throw new ServiceBizException("密码不正确！");
		}
		
		//查询当前操作人，在选项权限中的折扣率
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append(" select DEALER_CODE,min(coalesce(LABOUR_AMOUNT_DISCOUNT,0)) as LABOUR_AMOUNT_DISCOUNT,min(coalesce(REPAIR_PART_DISCOUNT,0)) as REPAIR_PART_DISCOUNT, ");
		sql.append(" min(coalesce(SALES_PART_DISCOUNT,0)) as SALES_PART_DISCOUNT,min(coalesce(ADD_ITEM_DISCOUNT,0)) as ADD_ITEM_DISCOUNT,min(coalesce(OVER_ITEM_DISCOUNT,0)) as OVER_ITEM_DISCOUNT ");
		sql.append(" from( select discount_mode_code as newcode,a.DEALER_CODE,a.LABOUR_AMOUNT_DISCOUNT,a.REPAIR_PART_DISCOUNT,a.SALES_PART_DISCOUNT,a.ADD_ITEM_DISCOUNT,a.OVER_ITEM_DISCOUNT ");
		sql.append(" from (" + CommonConstants.VM_DISCOUNT_MODE +") A  inner join ( select REPLACE(option_code,'5010','') as option_code,DEALER_CODE from  TT_USER_OPTION_MAPPING where user_id= ? ");
		params.add(userId);
		sql.append(" and option_code like '5010%'  )B  on A.discount_mode_code=b.option_code and a.DEALER_CODE=b.DEALER_CODE and a.DEALER_CODE= ? ");
		params.add(dealerCode);
		sql.append(" )a group by DEALER_CODE ");
		List<Map> list = DAOUtil.findAll(sql.toString(), params);
		//添加日志信息
		TtLogInfoPO ttLogInfoPO = new TtLogInfoPO();
		ttLogInfoPO.setString("MAIN_BILL_NO", "");
		ttLogInfoPO.setString("BUS_TABLE_NAME", "TT_REPAIR_ORDER");
		ttLogInfoPO.setInteger("LOG_TYPE", 10001001);
		ttLogInfoPO.setString("TABLE_VIEW_NAME", "优惠折扣授权");
		ttLogInfoPO.setInteger("LOG_OPERATOR", userId);
		if(employeeNo != null){
		ttLogInfoPO.setString("REMARK", employeeNo +" 授权 " + FrameworkUtil.getLoginInfo().getUserName() + " 更高的优惠折扣模式权限");
		}
		ttLogInfoPO.saveIt();
		
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public int CheckActivityOem(Map<String, String> queryParam) throws ServiceBizException {
		
		int count = 0;
		if(queryParam.get("activityCode") != null){
		String[] activityCodes = queryParam.get("activityCode").split(",");
		if(activityCodes != null && activityCodes.length>0){
			String temp = "";
			for (int i = 0; i < activityCodes.length; i++) {
				temp = temp + "'" + activityCodes[i] + "'";
				if (i != activityCodes.length - 1) {
					temp = temp + ",";
				}
			}
			String sql = "select * from tt_activity where entity_code='" + FrameworkUtil.getLoginInfo().getDealerCode() + "' and activity_code in (" + temp + ")";
			List<Map> list = DAOUtil.findAll(sql, null);
			if (list != null && list.size() > 0) {
				for (int j = 0; j < list.size(); j++) {
					if(list.get(j).get("DOWN_TAG") != null){
					if (Integer.parseInt(list.get(j).get("DOWN_TAG").toString()) == Integer.parseInt(DictCodeConstants.DICT_IS_YES)) {
						count++;
					}
					}
				}
			}
		}
	}
		return count;
}

	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> queryIsRestrict(Map<String, String> queryParam) {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		String sql = " select * from TM_LOCAL_LABOUR_MESSAGE where dealer_code = " + dealerCode;
		List<Map> list = DAOUtil.findAll(sql, null);
		
		return list;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> queryOEMTAG(String vin) throws ServiceBizException {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT BRAND FROM TM_VEHICLE WHERE ENTITY_CODE= " + dealerCode);
		if (!StringUtils.isNullOrEmpty(vin)){
			sql.append(" and VIN= " + vin);
		}
		List<Map> list = DAOUtil.findAll(sql.toString(), null);
		return list;
	}

	@Override
	public String queryLabourCode(Map<String, String> queryParam) throws ServiceBizException {
		String[] labourCode = null;
		String[] modelLabourCode = null;
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		if(queryParam.get("labourCode") != null){
			labourCode = queryParam.get("labourCode").split(",");
		}
		if(queryParam.get("modelLabourCode") != null){
			modelLabourCode = queryParam.get("modelLabourCode").split(",");
		}
		String hasArrived = null;
		if(!StringUtils.isNullOrEmpty(labourCode) && !StringUtils.isNullOrEmpty(modelLabourCode))
		{for(int i = 0; i < labourCode.length; i++){
			StringBuffer sql = new StringBuffer();
			sql.append(" select * from TM_REPAIR_ITEM where dealer_code = " + dealerCode);
			if(!StringUtils.isNullOrEmpty(labourCode[i])){
			sql.append(" and LABOUR_CODE = " + labourCode[i]);
			}
			if(!StringUtils.isNullOrEmpty(modelLabourCode[i])){
				sql.append(" and MODEL_LABOUR_CODE = " + modelLabourCode[i]);
				}
			List<Map> list = DAOUtil.findAll(sql.toString(), null);
			if (list != null && list.size() > 0) {
			  if (list.get(0).get("DOWN_TAG") !=null && list.get(0).get("DOWN_TAG").equals(12781001)){
				  hasArrived="12781001";
			  }else {
				  hasArrived="12781002";
				  break;
			  }
			}
			else {
				  hasArrived="12781002";
				  break;
				  }
		}
		
	}
		return hasArrived;
	}
}
