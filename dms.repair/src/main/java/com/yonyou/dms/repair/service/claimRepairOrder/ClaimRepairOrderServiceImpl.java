/**
 * 
 */
package com.yonyou.dms.repair.service.claimRepairOrder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import com.yonyou.dms.common.domains.PO.basedata.TtRoTimeoutCausePO;
import com.yonyou.dms.common.domains.PO.basedata.TtRoTimeoutDetailPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * @author sqh
 *
 */
@Service
public class ClaimRepairOrderServiceImpl implements ClaimRepairOrderService{

	@Override
	public PageInfoDto queryClaimRepairOrder(Map<String, String> queryParam) throws ServiceBizException {
		StringBuffer sb = new StringBuffer();
		List<Object> queryList = new ArrayList<Object>();
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		sb.append(" SELECT A.RO_TROUBLE_DESC,A.RO_CLAIM_STATUS,'' AS HAVE_CLAIM_TAG,db.dealer_shortname,A.DEALER_CODE,A.RO_NO,A.RO_TYPE,A.RO_CREATE_DATE, A.REPAIR_TYPE_CODE,A.TEST_DRIVER, ");
		sb.append(" A.MODIFY_NUM,A.SERVICE_ADVISOR,tms.user_name SERVICE_ADVISOR_NAME, A.SERVICE_ADVISOR_ASS,A.RO_STATUS,A.LOCK_USER,A.CHIEF_TECHNICIAN, A.IN_MILEAGE,A.OUT_MILEAGE,A.DELIVERER,A.DELIVERER_PHONE,A.DELIVERER_MOBILE, ");
		sb.append(" A.INSURATION_CODE,A.ESTIMATE_NO,A.OWNER_NO, A.OWNER_NAME, A.OWNER_PROPERTY, A.LICENSE,A.IS_UPDATE_END_TIME_SUPPOSED, A.VIN,A.ENGINE_NO, A.BRAND, A.SERIES, A.MODEL,tb.brand_name,ts.series_name,tm.MODEL_NAME, A.END_TIME_SUPPOSED, A.FOR_BALANCE_TIME, ");
		sb.append(" A.DELIVERY_DATE,A.COMPLETE_TIME, A.RECOMMEND_CUSTOMER_NAME, A.REMARK,A.REMARK1,A.LABOUR_AMOUNT, A.REPAIR_PART_AMOUNT,A.SALES_PART_AMOUNT,A.ADD_ITEM_AMOUNT, ");
		sb.append(" A.OVER_ITEM_AMOUNT,A.REPAIR_AMOUNT,A.BALANCE_AMOUNT,A.RECEIVE_AMOUNT, V.SALES_DATE,A.LAST_MAINTENANCE_DATE,A.LAST_MAINTENANCE_MILEAGE, BA.BALANCE_CLOSE,BA.BALANCE_CLOSE_TIME, A.DELIVERY_TAG, ");
		sb.append(" BA.PAY_OFF,BA.BALANCE_HANDLER,BA.PRINT_BALANCE_TIME, CASE A.QUOTE_END_ACCURATE WHEN 12781001 THEN 12781001 ELSE 12781002 END AS QUOTE_END_ACCURATE, ");
		sb.append(" CASE  WHEN (BA.IS_RED=12781002 AND  BA.PRINT_BALANCE_TIME IS NOT NULL  AND   BA.PRINT_BALANCE_TIME< A.END_TIME_SUPPOSED AND A.IS_UPDATE_END_TIME_SUPPOSED=12781002 ");
		sb.append(" AND TIMESTAMPDIFF(HOUR,TIMESTAMP (A.END_TIME_SUPPOSED),TIMESTAMP (BA.PRINT_BALANCE_TIME)) < TIMESTAMPDIFF(HOUR,TIMESTAMP (A.END_TIME_SUPPOSED),TIMESTAMP (A.ESTIMATE_BEGIN_TIME))  * 0.3) then 12781001 ");
		sb.append(" else 12781002 end as complete_on_time, Coalesce(A.CUSTOMER_PRE_CHECK,12781002) AS CUSTOMER_PRE_CHECK, Coalesce(A.CHECKED_END,12781002) AS CHECKED_END,Coalesce(A.EXPLAINED_BALANCE_ACCOUNTS,12781002) AS EXPLAINED_BALANCE_ACCOUNTS,");	
		sb.append(" ' ' as E_MAIL, ' ' as ADDRESS, ' ' as ZIP_CODE, ' ' as CONTACTOR_NAME, ' ' as CONTACTOR_ADDRESS,'' as CERTIFICATE_NO,'' as CT_CODE, ' ' as CONTACTOR_ZIP_CODE, ");
		sb.append("  ' ' as CONTACTOR_EMAIL,A.COMPLETE_TAG,A.WAIT_INFO_TAG,A.WAIT_PART_TAG, ' ' as PROVINCE, ' ' as CITY, ' ' as DISTRICT, ' ' as VIP_NO,TECH.TECHNICIAN AS TECHNICIAN_UNITE,TECH.LABOUR_NAME AS LABOUR_NAME_UNITE, ");	
		sb.append(" uc.EMPLOYEE_NAME as LOCK_USER_NAME, A.EWORKSHOP_REMARK, A.NOT_INTEGRAL as IS_MEMBER, A.IS_ABANDON_ACTIVITY, A.SCHEME_STATUS, A.IS_TIMEOUT_SUBMIT FROM TT_REPAIR_ORDER A ");
		sb.append(" INNER JOIN (" + CommonConstants.VM_VEHICLE + ") V ON A.DEALER_CODE = V.DEALER_CODE AND A.VIN = V.VIN and v.DEALER_CODE=A.DEALER_CODE ");
		sb.append("  LEFT JOIN tm_user tms ON tms.employee_no = A.SERVICE_ADVISOR ");
		sb.append("  LEFT JOIN ( SELECT mb.brand_code,mb.brand_name,mb.dealer_code FROM tm_brand mb ) tb ON A.DEALER_CODE=tb.DEALER_CODE AND V.BRAND=tb.BRAND_CODE ");
        sb.append("  LEFT JOIN TM_DEALER_BASICINFO db ON A.dealer_code=db.dealer_code  ");
        sb.append(" LEFT JOIN (SELECT s.series_code,s.series_name,s.dealer_code,s.BRAND_CODE FROM tm_series s) ts  ON A.DEALER_CODE = ts.DEALER_CODE  AND V.SERIES = ts.SERIES_CODE  AND ts.brand_code=tb.brand_code");
        sb.append(" LEFT JOIN  (SELECT  m.DEALER_CODE, m.MODEL_NAME, m.MODEL_CODE, m.SERIES_CODE  FROM tm_model m) tm  ON A.DEALER_CODE = tm.DEALER_CODE  AND V.MODEL = TM.MODEL_CODE  AND ts.brand_code=tb.brand_code AND tm.series_code=ts.series_code");
		sb.append(" LEFT JOIN TT_BALANCE_ACCOUNTS BA ON A.DEALER_CODE = BA.DEALER_CODE AND BA.RO_NO = A.RO_NO and a.D_KEY = ba.D_KEY AND BA.IS_RED = " +DictCodeConstants.DICT_IS_NO + " ");
		sb.append(" LEFT JOIN TT_TECHNICIAN_I TECH ON TECH.DEALER_CODE=A.DEALER_CODE AND TECH.RO_NO=A.RO_NO AND TECH.D_KEY=" + CommonConstants.D_KEY);
		sb.append(" left join TM_EMPLOYEE uc on uc.DEALER_CODE=a.DEALER_CODE and uc.EMPLOYEE_NO=a.LOCK_USER WHERE A.D_KEY=" + CommonConstants.D_KEY + " ");
		if (!StringUtils.isNullOrEmpty(dealerCode)) {
			sb.append(" AND A.DEALER_CODE= ? ");
			queryList.add(dealerCode);
		}
		
		sb.append(" AND " + Utility.getShareEntityCon(dealerCode, "UNIFIED_VIEW", "A"));
		sb.append(Utility.getLikeCond("A", "LICENSE", queryParam.get("license"), "AND"));
		sb.append(Utility.getLikeCond("A", "VIN", queryParam.get("vin"), "AND"));
		sb.append(Utility.getLikeCond("A", "RO_NO", queryParam.get("roNO"), "AND"));
		sb.append(Utility.getLikeCond("A", "OWNER_NO", queryParam.get("ownerNo"), "AND"));
		sb.append(Utility.getLikeCond("A", "OWNER_NAME", queryParam.get("ownerName"), "AND"));
		sb.append(" and (A.RO_STATUS = " + DictCodeConstants.DICT_RO_STATUS_TYPE_ON_REPAIR + " ");
		sb.append(" or A.RO_STATUS = " + DictCodeConstants.DICT_RO_STATUS_TYPE_FOR_BALANCE + ") ");
		sb.append(" and A.RO_TYPE = " + DictCodeConstants.DICT_RPT_CLAIM + " ");
		if (!StringUtils.isNullOrEmpty(queryParam.get("serviceAdvisor"))) {
			sb.append(" and A.SERVICE_ADVISOR =  ? ");
			queryList.add(queryParam.get("serviceAdvisor"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("model"))) {
			sb.append(" and A.MODEL =  ? ");
			queryList.add(queryParam.get("model"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("brand"))) {
			sb.append(" and A.BRAND =  ? ");
			queryList.add(queryParam.get("brand"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("series"))) {
			sb.append(" and A.SERIES =  ? ");
			queryList.add(queryParam.get("series"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("repairTypeCode"))) {
			sb.append(" and A.REPAIR_TYPE_CODE =  ? ");
			queryList.add(queryParam.get("repairTypeCode"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("queryDateType"))) {
			sb.append(Utility.getDateCond("", queryParam.get("queryDateType"), queryParam.get("beginTime"), queryParam.get("endTime")));
		}
		sb.append(" ORDER BY RO_NO ASC  ");
		System.out.print(sb.toString());
		PageInfoDto pageInfoDto = DAOUtil.pageQuery(sb.toString(), queryList);
		return pageInfoDto;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> queryClaimRepairOrders(Map<String, String> queryParam) throws ServiceBizException {
		StringBuffer sb = new StringBuffer();
		List<Object> queryList = new ArrayList<Object>();
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		sb.append(" SELECT A.RO_TROUBLE_DESC,A.RO_CLAIM_STATUS,'' AS HAVE_CLAIM_TAG,db.dealer_shortname,A.DEALER_CODE,A.RO_NO,A.RO_TYPE,A.RO_CREATE_DATE, A.REPAIR_TYPE_CODE,A.TEST_DRIVER, ");
		sb.append(" A.MODIFY_NUM,A.SERVICE_ADVISOR,tms.user_name SERVICE_ADVISOR_NAME, A.SERVICE_ADVISOR_ASS,A.RO_STATUS,A.LOCK_USER,A.CHIEF_TECHNICIAN, A.IN_MILEAGE,A.OUT_MILEAGE,A.DELIVERER,A.DELIVERER_PHONE,A.DELIVERER_MOBILE, ");
		sb.append(" A.INSURATION_CODE,A.ESTIMATE_NO,A.OWNER_NO, A.OWNER_NAME, A.OWNER_PROPERTY, A.LICENSE,A.IS_UPDATE_END_TIME_SUPPOSED, A.VIN,A.ENGINE_NO, A.BRAND, A.SERIES, A.MODEL,tb.brand_name,ts.series_name,tm.MODEL_NAME, A.END_TIME_SUPPOSED, A.FOR_BALANCE_TIME, ");
		sb.append(" A.DELIVERY_DATE,A.COMPLETE_TIME, A.RECOMMEND_CUSTOMER_NAME, A.REMARK,A.REMARK1,A.LABOUR_AMOUNT, A.REPAIR_PART_AMOUNT,A.SALES_PART_AMOUNT,A.ADD_ITEM_AMOUNT, ");
		sb.append(" A.OVER_ITEM_AMOUNT,A.REPAIR_AMOUNT,A.BALANCE_AMOUNT,A.RECEIVE_AMOUNT, V.SALES_DATE,A.LAST_MAINTENANCE_DATE,A.LAST_MAINTENANCE_MILEAGE, BA.BALANCE_CLOSE,BA.BALANCE_CLOSE_TIME, A.DELIVERY_TAG, ");
		sb.append(" BA.PAY_OFF,BA.BALANCE_HANDLER,BA.PRINT_BALANCE_TIME, CASE A.QUOTE_END_ACCURATE WHEN 12781001 THEN 12781001 ELSE 12781002 END AS QUOTE_END_ACCURATE, ");
		sb.append(" CASE  WHEN (BA.IS_RED=12781002 AND  BA.PRINT_BALANCE_TIME IS NOT NULL  AND   BA.PRINT_BALANCE_TIME< A.END_TIME_SUPPOSED AND A.IS_UPDATE_END_TIME_SUPPOSED=12781002 ");
		sb.append(" AND TIMESTAMPDIFF(HOUR,TIMESTAMP (A.END_TIME_SUPPOSED),TIMESTAMP (BA.PRINT_BALANCE_TIME)) < TIMESTAMPDIFF(HOUR,TIMESTAMP (A.END_TIME_SUPPOSED),TIMESTAMP (A.ESTIMATE_BEGIN_TIME))  * 0.3) then 12781001 ");
		sb.append(" else 12781002 end as complete_on_time, Coalesce(A.CUSTOMER_PRE_CHECK,12781002) AS CUSTOMER_PRE_CHECK, Coalesce(A.CHECKED_END,12781002) AS CHECKED_END,Coalesce(A.EXPLAINED_BALANCE_ACCOUNTS,12781002) AS EXPLAINED_BALANCE_ACCOUNTS,");	
		sb.append(" ' ' as E_MAIL, ' ' as ADDRESS, ' ' as ZIP_CODE, ' ' as CONTACTOR_NAME, ' ' as CONTACTOR_ADDRESS,'' as CERTIFICATE_NO,'' as CT_CODE, ' ' as CONTACTOR_ZIP_CODE, ");
		sb.append("  ' ' as CONTACTOR_EMAIL,A.COMPLETE_TAG,A.WAIT_INFO_TAG,A.WAIT_PART_TAG, ' ' as PROVINCE, ' ' as CITY, ' ' as DISTRICT, ' ' as VIP_NO,TECH.TECHNICIAN AS TECHNICIAN_UNITE,TECH.LABOUR_NAME AS LABOUR_NAME_UNITE, ");	
		sb.append(" uc.EMPLOYEE_NAME as LOCK_USER_NAME, A.EWORKSHOP_REMARK, A.NOT_INTEGRAL as IS_MEMBER, A.IS_ABANDON_ACTIVITY, A.SCHEME_STATUS, A.IS_TIMEOUT_SUBMIT FROM TT_REPAIR_ORDER A ");
		sb.append(" INNER JOIN (" + CommonConstants.VM_VEHICLE + ") V ON A.DEALER_CODE = V.DEALER_CODE AND A.VIN = V.VIN and v.DEALER_CODE=A.DEALER_CODE ");
		sb.append("  LEFT JOIN tm_user tms ON tms.employee_no = A.SERVICE_ADVISOR ");
		sb.append("  LEFT JOIN ( SELECT mb.brand_code,mb.brand_name,mb.dealer_code FROM tm_brand mb ) tb ON A.DEALER_CODE=tb.DEALER_CODE AND V.BRAND=tb.BRAND_CODE ");
        sb.append("  LEFT JOIN TM_DEALER_BASICINFO db ON A.dealer_code=db.dealer_code  ");
        sb.append(" LEFT JOIN (SELECT s.series_code,s.series_name,s.dealer_code,s.BRAND_CODE FROM tm_series s) ts  ON A.DEALER_CODE = ts.DEALER_CODE  AND V.SERIES = ts.SERIES_CODE  AND ts.brand_code=tb.brand_code");
        sb.append(" LEFT JOIN  (SELECT  m.DEALER_CODE, m.MODEL_NAME, m.MODEL_CODE, m.SERIES_CODE  FROM tm_model m) tm  ON A.DEALER_CODE = tm.DEALER_CODE  AND V.MODEL = TM.MODEL_CODE  AND ts.brand_code=tb.brand_code AND tm.series_code=ts.series_code");
		sb.append(" LEFT JOIN TT_BALANCE_ACCOUNTS BA ON A.DEALER_CODE = BA.DEALER_CODE AND BA.RO_NO = A.RO_NO and a.D_KEY = ba.D_KEY AND BA.IS_RED = " +DictCodeConstants.DICT_IS_NO + " ");
		sb.append(" LEFT JOIN TT_TECHNICIAN_I TECH ON TECH.DEALER_CODE=A.DEALER_CODE AND TECH.RO_NO=A.RO_NO AND TECH.D_KEY=" + CommonConstants.D_KEY);
		sb.append(" left join TM_EMPLOYEE uc on uc.DEALER_CODE=a.DEALER_CODE and uc.EMPLOYEE_NO=a.LOCK_USER WHERE A.D_KEY=" + CommonConstants.D_KEY + " ");
		if (!StringUtils.isNullOrEmpty(dealerCode)) {
			sb.append(" AND A.DEALER_CODE= ? ");
			queryList.add(dealerCode);
		}
		
		sb.append(" AND " + Utility.getShareEntityCon(dealerCode, "UNIFIED_VIEW", "A"));
		sb.append(Utility.getLikeCond("A", "LICENSE", queryParam.get("license"), "AND"));
		sb.append(Utility.getLikeCond("A", "VIN", queryParam.get("vin"), "AND"));
		sb.append(Utility.getLikeCond("A", "RO_NO", queryParam.get("roNO"), "AND"));
		sb.append(Utility.getLikeCond("A", "OWNER_NO", queryParam.get("ownerNo"), "AND"));
		sb.append(Utility.getLikeCond("A", "OWNER_NAME", queryParam.get("ownerName"), "AND"));
		sb.append(" and (A.RO_STATUS = " + DictCodeConstants.DICT_RO_STATUS_TYPE_ON_REPAIR + " ");
		sb.append(" or A.RO_STATUS = " + DictCodeConstants.DICT_RO_STATUS_TYPE_FOR_BALANCE + ") ");
		sb.append(" and A.RO_TYPE = " + DictCodeConstants.DICT_RPT_CLAIM + " ");
		if (!StringUtils.isNullOrEmpty(queryParam.get("serviceAdvisor"))) {
			sb.append(" and A.SERVICE_ADVISOR =  ? ");
			queryList.add(queryParam.get("serviceAdvisor"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("model"))) {
			sb.append(" and A.MODEL =  ? ");
			queryList.add(queryParam.get("model"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("brand"))) {
			sb.append(" and A.BRAND =  ? ");
			queryList.add(queryParam.get("brand"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("series"))) {
			sb.append(" and A.SERIES =  ? ");
			queryList.add(queryParam.get("series"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("repairTypeCode"))) {
			sb.append(" and A.REPAIR_TYPE_CODE =  ? ");
			queryList.add(queryParam.get("repairTypeCode"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("queryDateType"))) {
			sb.append(Utility.getDateCond("", queryParam.get("queryDateType"), queryParam.get("beginTime"), queryParam.get("endTime")));
		}
		sb.append(" ORDER BY RO_NO ASC  ");
		List<Map> list = DAOUtil.findAll(sb.toString(), queryList);
		return list;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> QueryRoTimeoutCause(String id) throws ServiceBizException {
		StringBuffer sb = new StringBuffer();
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		sb.append(" SELECT A.DEALER_CODE,A.RO_NO,A.OWNED_BY,A.WORK_STATUS, A.REMARK,A.VER,A.ITEM_ID FROM TT_RO_TIMEOUT_CAUSE A WHERE 1 = 1  ");
		sb.append(" AND A.DEALER_CODE = "+ dealerCode +" AND A.D_KEY = "+ CommonConstants.D_KEY);
		if (!StringUtils.isNullOrEmpty(id)) {
			sb.append(" AND A.RO_NO = '"  + id + "'");
		}
		List<Map> list = DAOUtil.findAll(sb.toString(), null);
		
		return list;
		
	}

	@Override
	public PageInfoDto QueryRoTimeoutDetail(String id) throws ServiceBizException {
		StringBuffer str = new StringBuffer();
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		str.append(" SELECT A.DEALER_CODE,A.ORDER_DATE,A.PART_NO,A.PART_NAME,A.PART_QUANTITY,A.REMARK,A.PURCHASE_ORDER_NO,A.VER,A.ITEM_ID FROM TT_RO_TIMEOUT_DETAIL A WHERE 1 = 1 ");
		str.append(" AND A.DEALER_CODE = "+ dealerCode +" AND A.D_KEY = "+ CommonConstants.D_KEY);
		if (!StringUtils.isNullOrEmpty(id)) {
			str.append(" AND A.RO_NO = '" + id +"'");
		}
		PageInfoDto pageInfoDto = DAOUtil.pageQuery(str.toString(), null);
		return pageInfoDto;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> queryRoShortPartDetail(String id) throws ServiceBizException {
		//配件缺料记录表
		StringBuffer sql = new StringBuffer();
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		sql.append(" SELECT A.PART_NO, A.PART_NAME,A.SHORT_QUANTITY  FROM TT_SHORT_PART A WHERE 1 = 1 ");
		sql.append(" AND A.DEALER_CODE = "+ dealerCode +" AND A.D_KEY = "+ CommonConstants.D_KEY);
		if (!StringUtils.isNullOrEmpty(id)) {
		sql.append(" AND A.RO_NO = '"  + id + "'");
				}
		List<Map> partList = DAOUtil.findAll(sql.toString(), null);
		return partList;
	}

	@Override
	public PageInfoDto queryRoTimeoutPartDetail(String id) throws ServiceBizException {
		StringBuffer sql = new StringBuffer();
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		List<Object> queryList = new ArrayList<Object>();
		sql.append(" SELECT A.DEALER_CODE,A.STORAGE_CODE, A.IS_MAIN_PART,A.PART_NO, A.PART_NAME,A.PART_QUANTITY, A.UNIT_CODE, A.PART_COST_PRICE, A.PART_SALES_PRICE,A.PART_COST_AMOUNT, A.PART_SALES_AMOUNT ");
		sql.append(" FROM TT_RO_REPAIR_PART A LEFT JOIN TM_MEMBER_CARD m on A.DEALER_CODE=m.DEALER_CODE and A.CARD_ID=m.CARD_ID LEFT JOIN TM_PART_STOCK B ON A.DEALER_CODE = B.DEALER_CODE ");
		sql.append(" AND A.D_KEY = B.D_KEY AND A.STORAGE_CODE = B.STORAGE_CODE AND A.PART_NO = B.PART_NO left join (" + CommonConstants.VM_PART_INFO + ") C on  C.DEALER_CODE = B.DEALER_CODE ");
		sql.append(" AND C.D_KEY = B.D_KEY AND C.PART_NO = B.PART_NO LEFT JOIN TT_ACTIVITY l ON A.DEALER_CODE = l.DEALER_CODE AND A.ACTIVITY_CODE = l.ACTIVITY_CODE ");
		sql.append(" LEFT JOIN TT_RO_LABOUR RL ON A.DEALER_CODE = RL.DEALER_CODE AND A.RO_NO = RL.RO_NO AND A.ITEM_ID_LABOUR= RL.ITEM_ID  ");
		sql.append(" LEFT JOIN TM_REPAIR_TYPE RT ON RL.DEALER_CODE = RT.DEALER_CODE AND RL.REPAIR_TYPE_CODE = RT.REPAIR_TYPE_CODE ");
		sql.append(" WHERE A.D_KEY = " + CommonConstants.D_KEY + " AND A.RO_NO=?  ");
		queryList.add(id);
		sql.append(" AND A.DEALER_CODE =? ");
		queryList.add(dealerCode);
		PageInfoDto pageInfoDto = DAOUtil.pageQuery(sql.toString(), queryList);
		return pageInfoDto;
	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	public void MaintainRoTimeoutCauseAndDetail(Map timeoutDTO) {
		
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
//		Object vb=(((List)timeoutDTO.get("dms_reason")));
//		Map map=(Map)vb;
//		System.err.println("----|||||||||||-------"+map.get("PART_NAME"));
//		List<Map> list2 = JSONUtil.jsonToList(timeoutDTO.get("dms_reason").toString().replace("=", ":"), Map.class);
		String sql = "select * from tm_user where user_id = " + timeoutDTO.get("ownedBy") + " and dealer_code = " +dealerCode;
		List<Map> user = DAOUtil.findAll(sql, null);
		if(timeoutDTO.get("roNo") == null && timeoutDTO.get("roNo") == ""){
			throw new ServiceBizException("工单号为空！");
		}
		 
		List<Map> list = QueryRoTimeoutCause(timeoutDTO.get("roNo").toString());
		if(list != null && list.size()>0){
		 
			TtRoTimeoutCausePO timeoutCausePO = TtRoTimeoutCausePO.findById(list.get(0).get("ITEM_ID"));
			if(timeoutCausePO != null){
				timeoutCausePO.setString("RO_NO", timeoutDTO.get("roNo"));
				timeoutCausePO.setInteger("WORK_STATUS", timeoutDTO.get("workStatus"));
				timeoutCausePO.setString("OWNED_BY", user.get(0).get("EMPLOYEE_NO"));
				timeoutCausePO.setString("REMARK", timeoutDTO.get("remark"));
				timeoutCausePO.saveIt();
			}else{
				TtRoTimeoutCausePO ttimeoutCausePO = new TtRoTimeoutCausePO();
				ttimeoutCausePO.setString("RO_NO", timeoutDTO.get("roNo"));
				ttimeoutCausePO.setInteger("WORK_STATUS", timeoutDTO.get("workStatus"));
				ttimeoutCausePO.setString("OWNED_BY", user.get(0).get("EMPLOYEE_NO"));
				ttimeoutCausePO.setString("REMARK", timeoutDTO.get("remark"));
				ttimeoutCausePO.saveIt();
			}
		}
		for(int i=0; i<((List)timeoutDTO.get("dms_reason")).size();i++){
			List<Object> queryList = new ArrayList<Object>();
			Object vb=(((List)timeoutDTO.get("dms_reason")).get(i));
			Map map=(Map)vb;
			StringBuffer str = new StringBuffer();
			str.append(" select * from TT_RO_TIMEOUT_DETAIL where ro_no = ? ");
			queryList.add(timeoutDTO.get("roNo"));
			str.append(" and part_no = ? ");
			queryList.add(map.get("PART_NO"));
			str.append(" and dealer_code = "+ dealerCode);
			List<Map> detailList = DAOUtil.findAll(str.toString(), queryList);
			if(detailList == null || detailList.size() < 1){
				TtRoTimeoutDetailPO ttRoTimeoutDetailPO = new TtRoTimeoutDetailPO();
				ttRoTimeoutDetailPO.setString("RO_NO", timeoutDTO.get("roNo"));
				ttRoTimeoutDetailPO.setDate("ORDER_DATE", map.get("ORDER_DATE"));
				ttRoTimeoutDetailPO.setString("PART_NO", map.get("PART_NO"));
				ttRoTimeoutDetailPO.setString("PART_NAME", map.get("PART_NAME"));
				ttRoTimeoutDetailPO.setDouble("PART_QUANTITY", map.get("PART_QUANTITY"));
				ttRoTimeoutDetailPO.setString("PURCHASE_ORDER_NO",map.get("PURCHASE_ORDER_NO"));
				ttRoTimeoutDetailPO.setString("REMARK",map.get("REMARK"));
				ttRoTimeoutDetailPO.setDate("SEND_TIME", new Date());
				ttRoTimeoutDetailPO.saveIt();
			}else{
				TtRoTimeoutDetailPO ttRoTimeoutDetailPO = TtRoTimeoutDetailPO.findById(detailList.get(0).get("ITEM_ID"));
				ttRoTimeoutDetailPO.setString("PURCHASE_ORDER_NO",map.get("PURCHASE_ORDER_NO"));
				ttRoTimeoutDetailPO.setString("REMARK",map.get("REMARK"));
				ttRoTimeoutDetailPO.setDate("ORDER_DATE", map.get("ORDER_DATE"));
				ttRoTimeoutDetailPO.saveIt();
			}
		}
		if(timeoutDTO.get("items") != null){
			String[] items = timeoutDTO.get("items").toString().split(",");
			for(int a=0;a<items.length; a++){
				if(items[a] != null){
					List<Object> queryList = new ArrayList<Object>();
					StringBuffer str = new StringBuffer();
					str.append(" select * from TT_RO_TIMEOUT_DETAIL where ro_no = ? ");
					queryList.add(timeoutDTO.get("roNo"));
					str.append(" and part_no = ? ");
					queryList.add(items[a]);
					str.append(" and dealer_code = "+ dealerCode);
					List<Map> detailList = DAOUtil.findAll(str.toString(), queryList);
					TtRoTimeoutDetailPO ttRoTimeoutDetailPO = TtRoTimeoutDetailPO.findById(detailList.get(0).get("ITEM_ID"));
					ttRoTimeoutDetailPO.delete();
				}
			}
		}
		
	}

}
