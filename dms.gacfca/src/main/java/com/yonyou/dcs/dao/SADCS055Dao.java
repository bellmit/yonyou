package com.yonyou.dcs.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.DTO.gacfca.TtActivityResultDto;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;

@Repository
public class SADCS055Dao extends OemBaseDAO {

	public List<TtActivityResultDto> queryTtActivityResultVO() {

		StringBuffer sql = new StringBuffer();
		sql.append(
				" SELECT  * FROM  ( SELECT  C.DCS_CODE,C.DMS_CODE 	  	FROM TM_DEALER A, TM_COMPANY B, TI_DEALER_RELATION C 	  		WHERE A.COMPANY_ID = B.COMPANY_ID 	  			AND B.COMPANY_CODE = C.DCS_CODE 	  			AND A.DEALER_TYPE = 10771001 				AND C.STATUS=10011001\n");
		sql.append(
				" )t,( 	  SELECT TWAVC.ACTIVITY_CODE, SUBSTR(TWAVC.DEALER_CODE,1,5) DEALER_CODE,TWAVC.DEALER_NAME,	  		TWAVC.VIN,TWAVC.EXECUTE_DATE,TWA.ACTIVITY_NAME 	 	FROM tt_wr_activity_vehicle_complete_dcs TWAVC, tt_wr_activity_dcs TWA 	 \n");
		sql.append(
				" 		WHERE TWAVC.ACTIVITY_CODE = TWA.ACTIVITY_CODE 	 		AND (TWAVC.DOWN_STATUS!='1' OR TWAVC.DOWN_STATUS IS NULL) 	 )t2 WHERE 1=1 \n");

		// sql.append(" with relation_data as ( \t");
		// sql.append(" SELECT C.DCS_CODE,C.DMS_CODE \t");
		// sql.append(" FROM TM_DEALER A, TM_COMPANY B, TI_DEALER_RELATION C
		// \t");
		// sql.append(" WHERE A.COMPANY_ID = B.COMPANY_ID \t");
		// sql.append(" AND B.COMPANY_CODE = C.DCS_CODE \t");
		// sql.append(" AND A.DEALER_TYPE = 10771001 \t");
		// sql.append(" AND C.STATUS=" + OemDictCodeConstants.STATUS_ENABLE +
		// "\n");
		// sql.append(" ),\t");
		// sql.append(" bus_data as ( \t");
		// sql.append(" SELECT TWAVC.ACTIVITY_CODE,
		// SUBSTR(TWAVC.DEALER_CODE,1,5) DEALER_CODE,TWAVC.DEALER_NAME,\t");
		// sql.append(" TWAVC.VIN,TWAVC.EXECUTE_DATE,TWA.ACTIVITY_NAME \t");
		// sql.append(" FROM TT_WR_ACTIVITY_VEHICLE_COMPLETE TWAVC,
		// TT_WR_ACTIVITY TWA \t");
		// sql.append(" WHERE TWAVC.ACTIVITY_CODE = TWA.ACTIVITY_CODE \t");
		// sql.append(" AND (TWAVC.DOWN_STATUS!='1' OR TWAVC.DOWN_STATUS IS
		// NULL) \t");
		// sql.append(" )SELECT RD.DMS_CODE, BD.* FROM RELATION_DATA RD,
		// BUS_DATA BD \t");
		// sql.append(" WHERE RD.DCS_CODE = BD.DEALER_CODE ");
		System.out.println(sql.toString());
		List<Map> findAll = OemDAOUtil.findAll(sql.toString(), null);
		List<TtActivityResultDto> list = new ArrayList<>();
		for (Map map : findAll) {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd ");
			String format = df.format(new Date());
			TtActivityResultDto vo = new TtActivityResultDto();
			vo.setEntityCode(map.get("DMS_CODE").toString());// 经销商code
			vo.setDealerName(map.get("DEALER_NAME").toString());// 经销商名称
			vo.setActivityCode(map.get("ACTIVITY_CODE").toString());// 活动代码
			vo.setActivityName(map.get("ACTIVITY_NAME").toString());// 活动名称
			vo.setCampaignDate(map.get("EXECUTE_DATE").toString());
			vo.setDownTimestamp(new Date());
			vo.setVin(map.get("VIN").toString());
			list.add(vo);
		}
		return list;

	}

}
