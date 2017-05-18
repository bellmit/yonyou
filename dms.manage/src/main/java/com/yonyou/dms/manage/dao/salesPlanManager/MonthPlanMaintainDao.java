package com.yonyou.dms.manage.dao.salesPlanManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.StringUtils;
/**
 * 
* @ClassName: MonthPlanMaintainDao 
* @Description: 目标任务管理
* @author zhengzengliang 
* @date 2017年3月2日 下午2:34:48 
*
 */
@Repository
@SuppressWarnings({"rawtypes"})
public class MonthPlanMaintainDao extends OemBaseDAO{
	
	/**
	 * 
	* @Title: getMonthPlanYearList 
	* @Description: 获取月度目标已存在的年列表(总部)
	* @param @return    设定文件 
	* @return List<Map>    返回类型 
	* @throws
	 */
	public List<Map> getMonthPlanYearList() {
		List<Object> params = new ArrayList<Object>();
		String sql = getMonthPlanYearListSql(params);
		List<Map> list = OemDAOUtil.findAll(sql,params);
		return list;
	}
	private String getMonthPlanYearListSql(List<Object> params) {
		StringBuilder sql = new StringBuilder();
		sql.append("select distinct PLAN_YEAR from TT_VS_MONTHLY_PLAN where PLAN_TYPE= ?");
		params.add(String.valueOf(OemDictCodeConstants.TARGET_TYPE_01));
		
		return sql.toString();
	}
	
	/**
	 * 
	* @Title: oemQueryMonthPlanDetialInfoList 
	* @Description: 月度任务查询(oem) 
	* @param @param queryParam
	* @param @return    设定文件 
	* @return PageInfoDto    返回类型 
	* @throws
	 */
	public PageInfoDto oemQueryMonthPlanDetialInfoList(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getoemQueryMonthPlanDetialInfoListSql(queryParam,params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql,params);
		return pageInfoDto;
	}
	private String getoemQueryMonthPlanDetialInfoListSql(Map<String, String> queryParam, List<Object> params) {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from (");
		sql.append("SELECT C.SWT_CODE, -- SAP 代码 \n");
		sql.append("       D.DEALER_CODE, -- 经销商代码 \n");
		sql.append("       D.DEALER_SHORTNAME, -- 经销商名称 \n");
		sql.append("       VMG.GROUP_CODE, -- 车系代码 \n");
		sql.append("       VMG.GROUP_NAME, -- 车系名称 \n");
		String season = new String();
		if(!StringUtils.isNullOrEmpty(queryParam.get("seasonName"))){
			season = queryParam.get("seasonName");
		}
		if("第一季度".equals(season)){
			season = "1";
			sql.append(season+" season , -- 车系名称 \n");
		}else if("第二季度".equals(season)){
			season = "2";
			sql.append(season+" season , -- 车系名称 \n");
		}else if("第三季度".equals(season)){
			season = "3";
			sql.append(season+" season , -- 车系名称 \n");
		}else if("第四季度".equals(season)){
			season = "4";
			sql.append(season+" season , -- 车系名称 \n");
		}
		
		String one = null,two = null,three = null;
		if(season.equals("1")){
			one = "1";two = "2";three = "3";
		}else if(season.equals("2")){
			one = "4";two = "5";three = "6";
		}else if(season.equals("3")){
			one = "7";two = "8";three = "9";
		}else if(season.equals("4")){
			one = "10";two = "11";three = "12";
		}
		String total = one + "," + two + "," + three;
		sql.append(" (SUM(CASE MP.PLAN_MONTH WHEN "+one+" THEN MPD.SALE_AMOUNT ELSE 0 END)) AS ONE, -- 季度第一个月 \n");
		sql.append(" (SUM(CASE MP.PLAN_MONTH WHEN "+two+" THEN MPD.SALE_AMOUNT ELSE 0 END)) AS TWO, -- 季度第二个月 \n");
		sql.append(" (SUM(CASE MP.PLAN_MONTH WHEN "+three+" THEN MPD.SALE_AMOUNT ELSE 0 END)) AS THREE, -- 季度第三个月 \n");
		sql.append(" (SUM(CASE MP.PLAN_MONTH WHEN  "+one+"  THEN MPD.SALE_AMOUNT ELSE 0 END)+SUM(CASE MP.PLAN_MONTH WHEN  "+two+"  THEN MPD.SALE_AMOUNT ELSE 0 END)+SUM(CASE MP.PLAN_MONTH WHEN  "+three+"  THEN MPD.SALE_AMOUNT ELSE 0 END)) TOTAL \n");
		sql.append("  FROM TT_VS_MONTHLY_PLAN_DETAIL MPD \n");
		sql.append(" INNER JOIN TT_VS_MONTHLY_PLAN MP ON MP.PLAN_ID = MPD.PLAN_ID \n");
		sql.append(" INNER JOIN TM_DEALER D ON D.DEALER_ID = MP.DEALER_ID \n");
		sql.append(" INNER JOIN TM_COMPANY C ON C.COMPANY_ID = D.COMPANY_ID \n");
		sql.append(" INNER JOIN TM_VHCL_MATERIAL_GROUP VMG ON VMG.GROUP_ID = MPD.MATERIAL_GROUPID \n");
		sql.append(" WHERE VMG.GROUP_LEVEL = '2' \n");
		/*
		 * 写死业务范围 Begin
		 */
		sql.append("   AND VMG.GROUP_TYPE = '" + OemDictCodeConstants.GROUP_TYPE_IMPORT + "' \n");
		/*
		 * 写死业务范围 End..
		 */
		sql.append("   AND VMG.STATUS = '" + OemDictCodeConstants.STATUS_ENABLE + "' \n");
		sql.append("   AND MP.PLAN_VER = '1' \n");
		//计划年份
		if(!StringUtils.isNullOrEmpty(queryParam.get("planYearName"))){
			sql.append("   AND MP.PLAN_YEAR = ? \n");
			params.add(queryParam.get("planYearName").toString());
		}
		sql.append("   AND MP.PLAN_MONTH IN (" + total + ") \n");
		//计划类型
		if(!StringUtils.isNullOrEmpty(queryParam.get("planTypeName"))){
			sql.append("   AND MP.PLAN_TYPE = ? \n");
			params.add(queryParam.get("planTypeName").toString());
		}
		//经销商
		String dealerCodes = queryParam.get("dealerCode");
		String dealerCode = "";
		if (dealerCodes != null && !dealerCodes.equals("")) {
			if (dealerCodes.indexOf(",") > 0) {
				String[] str = dealerCodes.split(",");
				for (int i = 0; i < str.length; i++) {
					dealerCode += "'" + str[i] + "'";
					if (i < str.length - 1) {
						dealerCode += ",";
					}
				}
			} else {
				dealerCode += "'" + dealerCodes + "'";
			}
		}
		if(!StringUtils.isNullOrEmpty(dealerCode)){
			sql.append("   AND D.DEALER_CODE IN ("+dealerCode+") \n");
		}
		sql.append(" GROUP BY C.SWT_CODE, D.DEALER_CODE, D.DEALER_SHORTNAME, VMG.GROUP_CODE, VMG.GROUP_NAME \n");
	//	sql.append(" ORDER BY D.DEALER_SHORTNAME ASC, VMG.GROUP_CODE ASC \n");
		sql.append("  )T ");
		return sql.toString();
	}
	
	/**
	 * 
	* @Title: monthPlanDetialInfoDownLoad 
	* @Description: 月度任务下载（经销商）
	* @param @param queryParam
	* @param @return    设定文件 
	* @return List<Map>    返回类型 
	* @throws
	 */
	public List<Map> monthPlanDetialInfoDownLoad(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getoemQueryMonthPlanDetialInfoListSql(queryParam,params);
		List<Map> list = OemDAOUtil.findAll(sql,params);
		return list;
	}
	

}
