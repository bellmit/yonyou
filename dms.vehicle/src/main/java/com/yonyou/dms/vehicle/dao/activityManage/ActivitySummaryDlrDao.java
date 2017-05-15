package com.yonyou.dms.vehicle.dao.activityManage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.vehicle.domains.DTO.activityManage.ActivitySummaryDTO;

/**
* @author liujiming
* @date 2017年4月6日
*/
@Repository
public class ActivitySummaryDlrDao extends OemBaseDAO{
	
	
	/**
	 * 查询
	 * @param queryParam
	 * @return
	 */
	public PageInfoDto getActivitySummaryDlrQuery(Map<String, String> queryParam) {
		//获取当前用户
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		
		List<Object> params = new ArrayList<Object>();
		
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT COUNT(R.VIN) IN_AMOUNT,A.ACTIVITY_ID, A.ACTIVITY_CODE, A.ACTIVITY_NAME,       \n");
		sql.append("			DATE_FORMAT(A.START_DATE,'%Y-%c-%d') START_DATE,       \n");
		sql.append("			DATE_FORMAT(A.END_DATE,'%Y-%c-%d') END_DATE,       \n");
		sql.append("			DATE_FORMAT(A.SUMM_CLOSEDATE,'%Y-%c-%d') SUMM_CLOSEDATE  \n");
		sql.append("		FROM TT_WR_ACTIVITY_VEHICLE_COMPLETE_DCS R, \n");
		sql.append("			TT_WR_ACTIVITY_DCS A \n");
		sql.append("		WHERE R.ACTIVITY_CODE = A.ACTIVITY_CODE  \n");
		sql.append("			AND A.STATUS = 40171002   \n");
		sql.append("			AND a.ACTIVITY_ID NOT IN (SELECT activity_id FROM tt_as_activity_evaluate_dcs WHERE dealer_id ='"+loginInfo.getDealerId()+"') \n");
		//sql.append("			AND R.DEALER_CODE='33123A' \n");
		//经销商Dlr限制
		//sql.append("			AND R.DEALER_CODE='"+loginInfo.getDealerCode()+"' \n");
		//活动系统编号
		if(!StringUtils.isNullOrEmpty(queryParam.get("activityCode"))){
			sql.append("		AND A.ACTIVITY_CODE LIKE'%"+queryParam.get("activityCode")+"%'        \n"); 
		}			
		//开始时间
		if(!StringUtils.isNullOrEmpty(queryParam.get("startDate"))){
			sql.append("		 AND A.START_DATE >= DATE_FORMAT('"+queryParam.get("startDate")+"','%Y-%c-%d')         \n"); 
		}
		//结束时间
		if(!StringUtils.isNullOrEmpty(queryParam.get("endDate"))){
			sql.append("		 AND A.END_DATE <= DATE_FORMAT('"+queryParam.get("endDate")+" 23:59:59','%Y-%c-%d %H:%i:%s')      \n"); 
		}		
		
		sql.append("			GROUP BY A.ACTIVITY_CODE,A.ACTIVITY_NAME,A.ACTIVITY_ID,A.START_DATE,A.END_DATE,A.SUMM_CLOSEDATE \n");

		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql.toString(), params);
		return pageInfoDto;

	}	
	
	/**
	 * 查询 SBN
	 * @param queryParam
	 * @return
	 */
	public List<Map> queryVehicleSummarySBN(String activityCode, String dealerCode) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT COUNT(ce.vin) sbn  \n");
		sql.append("	FROM TM_VEHICLE_dEC e,       \n");
		sql.append("	     ("+getVwMaterialSql()+") l,       \n");
		sql.append("	     TT_WR_WARRANTY_dcs Y,       \n");
		sql.append("	     TT_WR_ACTIVITY_VEHICLE_COMPLETE_dcs ce \n");
		sql.append("	WHERE     e.VIN = ce.VIN       \n");
		sql.append("		AND l.MATERIAL_ID = e.MATERIAL_ID       \n");
		sql.append("		AND l.MODEL_ID = y.MODEL_ID       \n");
		sql.append("		AND ((MONTH(ce.EXECUTE_DATE) - MONTH(e.PURCHASE_DATE))<=y.QUALITY_TIME \n");
		sql.append("		AND E.MILEAGE<=Y.QUALITY_MILEAGE)	   \n");
		sql.append("		AND CE.DEALER_CODE='"+dealerCode+"'       \n");
		sql.append("		AND ce.ACTIVITY_CODE='"+activityCode+"'  \n");
		sql.append("		AND y.IS_DEL=0	 \n");
		List<Map> orderList = OemDAOUtil.findAll(sql.toString(), params);
		return orderList;
	}
	/**
	 * 查询 SBW
	 * @param queryParam
	 * @return
	 */
	public List<Map> queryVehicleSummarySBW(String activityCode, String dealerCode) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT COUNT(ce.vin) sbw  \n");
		sql.append("	FROM TM_VEHICLE_DEC e,       \n");
		//sql.append("		VW_MATERIAL l,       \n");
		sql.append("		("+getVwMaterialSql()+") l,       \n");		
		sql.append("		TT_WR_WARRANTY_DCS Y,       \n");
		sql.append("		TT_WR_ACTIVITY_VEHICLE_COMPLETE_DCS ce \n");
		sql.append("	WHERE     e.VIN = ce.VIN       \n");
		sql.append("		AND l.MATERIAL_ID = e.MATERIAL_ID       \n");
		sql.append("		AND l.MODEL_ID = y.MODEL_ID       \n");
		sql.append("		AND ((MONTH(ce.EXECUTE_DATE) - MONTH(e.PURCHASE_DATE))>y.QUALITY_TIME OR E.MILEAGE>Y.QUALITY_MILEAGE)	   \n");
		sql.append("		AND CE.DEALER_CODE='"+dealerCode+"'       \n");
		sql.append("		AND ce.ACTIVITY_CODE='"+activityCode+"'   \n");
		sql.append("		AND y.IS_DEL=0 \n");

		List<Map> orderList = OemDAOUtil.findAll(sql.toString(), params);
		return orderList;
	}
	
	
	
	public  Map  getActivityDetailQuery(ActivitySummaryDTO asDto){
		List<Object> params = new ArrayList<Object>();
		
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT ACT.ACTIVITY_ID,\n" );
		sql.append("       ACT.ACTIVITY_CODE,\n" );
		sql.append("       ACT.ACTIVITY_NAME,\n" );
		sql.append("       ACT.ACTIVITY_TYPE,\n" );
		sql.append("       DATE_FORMAT(ACT.START_DATE,'%Y-%c-%d') START_DATE,\n" );
		sql.append("       DATE_FORMAT(ACT.END_DATE,'%Y-%c-%d') END_DATE,\n" );
		sql.append("       ACT.CLAIM_TYPE,\n" );
		sql.append("       ACT.SUMMARY_DAYS,\n" );
		sql.append("       ACT.CHOOSE_WAY,\n" );
		sql.append("       ACT.IS_FEE,\n" );
		sql.append("       ACT.LABOUR_FEE,\n" );
		sql.append("       ACT.PART_FEE,\n" );
		sql.append("       ACT.OTHER_FEE,\n" );
		sql.append("       ACT.EXPLANS,\n" );
		sql.append("       ACT.IS_MULTI,\n" );
		sql.append("       ACT.GUIDE,ACT.SUMM_CLOSEDATE, \n" );
		sql.append("       DATEDIFF(ACT.SUMM_CLOSEDATE,NOW())  DAYS, \n" );		
		sql.append("	   ACT.GLOBAL_ACTIVITY_CODE,  \n");
		sql.append("	   ACT.ACTIVITY_TITLE,  \n");
		sql.append("	   ACT.ATTACHMENT_URL  \n");
		sql.append("  FROM TT_WR_ACTIVITY_DCS ACT");
		sql.append("  WHERE  1=1 ");
		sql.append("       AND ACT.IS_DEL = "+OemDictCodeConstants.IS_DEL_00+" \n" );

		sql.append(" AND ACT.ACTIVITY_ID =  "+asDto.getActivityId()+"  \n");
		
		Map map = new HashMap<>();
		List<Map> list = OemDAOUtil.findAll(sql.toString(), params);
		if (list!=null && list.size()>0){		
				map =  list.get(0);
		}
		return map;
	}	
	
	/**
	 * @return 获取所在地区值
	 */	
	public Map getLocalArea(String dealer){
		List<Object> params = new ArrayList<Object>();
		
		StringBuffer sql= new StringBuffer();
		sql.append("select  T.PROVINCE_ID from TM_DEALER T where T.DEALER_ID='").append(dealer).append("'" );
		List<Map> retList=OemDAOUtil.findAll(sql.toString(), params);
		Map map = new HashMap<>();
		List<Map> list = OemDAOUtil.findAll(sql.toString(), params);
		if (list!=null && list.size()>0){		
				map =  list.get(0);
		}
		return map;
	}

}
