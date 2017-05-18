/**
 * 
 */
package com.yonyou.dms.customer.service.checkCarAlarm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

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
public class CheckCarAlarmServiceImpl implements CheckCarAlarmService{

	@Override
	public PageInfoDto queryCheckCarAlarm(Map<String, String> queryParam) throws ServiceBizException {
		StringBuffer sb = new StringBuffer();
		List<Object> queryList = new ArrayList<Object>();
		
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		sb.append(" SELECT B.ENTITY_CODE,B.IS_VALID,B.DELIVERER,B.DELIVERER_PHONE,B.DELIVERER_MOBILE,B.LICENSE,C.OWNER_NAME,C.CONTACTOR_PHONE,C.CONTACTOR_MOBILE,C.CONTACTOR_NAME, ");
		sb.append(" C.CONTACTOR_ADDRESS,C.CONTACTOR_ZIP_CODE,D.REMIND_DATE, D.REMINDER,D.CUSTOMER_FEEDBACK,D.REMIND_STATUS,D.REMARK,D.REMIND_CONTENT,D.REMIND_FAIL_REASON, ");
		sb.append(" G.EMPLOYEE_NAME AS REMINDER_NAME,B.VIN,B.OWNER_NO,C.OWNER_PROPERTY,C.ADDRESS,B.BRAND,B.SERIES,B.VEHICLE_PURPOSE,B.BUSINESS_KIND,B.MODEL ,B.SALES_DATE,B.LICENSE_DATE,B.LAST_INSPECT_DATE,B.NEXT_INSPECT_DATE,B.SERVICE_ADVISOR, C.ADDRESS ");
		sb.append(" FROM VM_VEHICLE B left outer join (select a.* from TT_VEHICLE_REMIND a,(select vin,max(REMIND_DATE) AS REMIND_DATE from TT_VEHICLE_REMIND where ENTITY_CODE in ( ");
		sb.append(" select SHARE_ENTITY from VM_ENTITY_SHARE_WITH where ENTITY_CODE='"+dealerCode+"' and BIZ_CODE = 'TT_ALL_REMIND') and LAST_TAG="+DictCodeConstants.DICT_IS_YES+" and D_KEY="+CommonConstants.D_KEY+" GROUP BY VIN ");
		sb.append(" ) b where a.REMIND_DATE=b.REMIND_DATE AND a.vin=b.vin) D on B.VIN = D.VIN left outer join TM_EMPLOYEE G on D.REMINDER=G.EMPLOYEE_NO AND D.ENTITY_CODE=G.ENTITY_CODE left outer join VM_OWNER C on B.ENTITY_CODE = C.ENTITY_CODE and B.OWNER_NO = C.OWNER_NO ");
		sb.append(" WHERE B.ENTITY_CODE = ? ");
		queryList.add(dealerCode);
		if (!StringUtils.isNullOrEmpty(queryParam.get("brand"))) {
			sb.append(" AND B.BRAND= ? ");
			queryList.add(queryParam.get("brand"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("model"))) {
			sb.append(" AND B.MODEL = ? ");
			queryList.add(queryParam.get("model"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("series"))) {
			sb.append(" AND B.SERIES= ? ");
			queryList.add(queryParam.get("series"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
			sb.append(" AND B.VIN like ? ");
			queryList.add("%" + queryParam.get("vin") +"%");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("remindStatus"))) {
			sb.append(" AND D.REMIND_STATUS = ? ");
			queryList.add(queryParam.get("remindStatus"));
		}
		
		Utility.sqlToDate(sb,queryParam.get("lastInspectDateBegin"), queryParam.get("lastInspectDateEnd"), "LAST_INSPECT_DATE", "B");
		Utility.sqlToDate(sb,queryParam.get("nextInspectDateBegin"), queryParam.get("nextInspectDateEnd"), "NEXT_INSPECT_DATE", "B");
		if (!StringUtils.isNullOrEmpty(queryParam.get("iSValid"))) {
			
			
			if(queryParam.get("iSValid").equals("12781002"))
			{
				sb.append(" AND B.IS_VALID= ? ");
				queryList.add(queryParam.get("iSValid"));
			}
			else
			{
				sb.append(" AND B.IS_VALID= " + DictCodeConstants.DICT_IS_YES + " or B.IS_VALID=0 or B.IS_VALID IS NULL )");
			}
		}
		Utility.sqlToDate(sb,queryParam.get("remindBeginDate"), queryParam.get("remindEndDate"), "REMIND_DATE", "D");
		 Utility.getLikeCond("B", "LICENSE", queryParam.get("license"), "AND");
		 Utility.getLikeCond("C", "ADDRESS", queryParam.get("address"), "AND");
//		sb.append("");
//		sb.append("");
//		sb.append("");
//		sb.append("");
//		sb.append("");		 
		 PageInfoDto pageInfoDto = DAOUtil.pageQuery(sb.toString(), queryList);
		return pageInfoDto;
	}

}
