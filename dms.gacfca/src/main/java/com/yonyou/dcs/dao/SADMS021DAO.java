package com.yonyou.dcs.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dcs.util.DEConstant;
import com.yonyou.dms.DTO.gacfca.SADMS021Dto;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.utils.common.CommonUtils;

@Repository
public class SADMS021DAO extends OemBaseDAO {
	/**
	 * 查询一对一客户经理绑定信息
	 */
	public LinkedList<SADMS021Dto> queryInfo(String param) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer strSql = new StringBuffer(
				" SELECT  VIN,DEALER_CODE,SERVICE_ADVISOR,EMPLOYEE_NAME,MOBILE,BOUND_TYPE \n");
		strSql.append(" FROM TI_WX_CUSTOMER_MANAGER_BINDING_FEEDBACK_dcs \n");
		strSql.append(" WHERE IS_SCAN = 0 and IS_DEL = 0 \n");
		//
		if (!"".equals(CommonUtils.checkNull(param))) {
			params.add(param);
		}
		List<Map> list2 = OemDAOUtil.findAll(strSql.toString(), null);
		LinkedList<SADMS021Dto> list = new LinkedList<>();
		for (Map map : list2) {
			SADMS021Dto dto = new SADMS021Dto();
			Map<String, Object> outDmsDealer = getDmsDealerCode(map.get("DEALER_CODE").toString());
			String dmsCode = CommonUtils.checkNull(outDmsDealer == null ? "" : outDmsDealer.get("DMS_CODE"));
			dto.setEntityCode(dmsCode);
			dto.setServiceAdvisor(map.get("SERVICE_ADVISOR").toString());
			dto.setEmployeeName(map.get("EMPLOYEE_NAME").toString());
			dto.setMobile(map.get("MOBILE").toString());
			dto.setBoundType(Integer.parseInt(CommonUtils.checkNull(map.get("BOUND_TYPE"))));
			dto.setEmployeeName(map.get("EMPLOYEE_NAME").toString());
			dto.setServiceAdvisor(map.get("SERVICE_ADVISOR").toString());
			dto.setMobile(map.get("MOBILE").toString());
			dto.setBoundType(Integer.parseInt(map.get("BOUND_TYPE").toString()));
			dto.setVin(map.get("VIN").toString());
			list.add(dto);
		}
		return list;
	}

	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	String format = df.format(new Date());

	public void finishScanStatus(String vin) {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE  ti_wx_customer_manager_binding_feedback_dcs  \n");
		sql.append("       set IS_SCAN = 1 ,UPDATE_BY= " + DEConstant.DE_UPDATE_BY + " ,update_date= '" + format
				+ "' where IS_SCAN = 0 and IS_DEL = 0 and vin='" + vin + "' \n");
		System.out.println(sql.toString());
		try {
			OemDAOUtil.execBatchPreparement(sql.toString(), new ArrayList<>());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
