package com.yonyou.dms.manage.dao.salesPlanManager;

import java.util.ArrayList;
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
/**
 * 
* @ClassName: DlrYearPlanQueryDao 
* @Description: 目标任务管理
* @author zhengzengliang 
* @date 2017年3月2日 下午6:38:37 
*
 */
@SuppressWarnings("rawtypes")
@Repository
public class DlrYearPlanQueryDao extends OemBaseDAO{

	/**
	 * 
	* @Title: getDealerPlanYearList 
	* @Description: 年度目标查询
	* @param @return    设定文件 
	* @return List<Map>    返回类型 
	* @throws
	 */
	public List<Map> getDealerPlanYearList() {
		List<Object> params = new ArrayList<Object>();
		String sql = getDealerPlanYearListSql(params);
		List<Map> list = OemDAOUtil.findAll(sql,params);
		return list;
	}
	private String getDealerPlanYearListSql(List<Object> params) {
		StringBuilder sql = new StringBuilder();
		sql.append("select distinct PLAN_YEAR from TT_VS_YEARLY_PLAN where PLAN_TYPE=? and DEALER_ID=? ");
		params.add(String.valueOf(OemDictCodeConstants.TARGET_TYPE_01));
		//获取当前用户
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		params.add(loginInfo.getDealerId());
		return sql.toString();
	}
	
	/**
	 * 
	* @Title: getDlrYearPlanQueryList 
	* @Description: 年度目标查询  （Dlr） 
	* @param @param queryParam
	* @param @return    设定文件 
	* @return PageInfoDto    返回类型 
	* @throws
	 */
	public PageInfoDto getDlrYearPlanQueryList(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getDlrYearPlanQueryListSql(queryParam,params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql,params);
		return pageInfoDto;
	}
	private String getDlrYearPlanQueryListSql(Map<String, String> queryParam, List<Object> params) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT yearPlanResult.* FROM (SELECT TVMG2.GROUP_NAME BORD_NAME,\n");
		sql.append("       TVMG.GROUP_NAME,\n");
		for (int i = 0; i < 12; i++) {
			sql.append("   CAST((SUM(CASE TVYPD.PLAN_MONTH WHEN " + (i + 1) + " THEN TVYPD.SALE_AMOUNT ELSE 0 END)) AS CHAR) AMOUNT" + (i + 1)+",\n");
		}
		sql.append(" CAST(SUM(TVYPD.SALE_AMOUNT) AS CHAR) TOTAL_AMOUNT \n");
		sql.append("  FROM TT_VS_YEARLY_PLAN        TVYP,\n");
		sql.append("       TT_VS_YEARLY_PLAN_DETAIL TVYPD,\n");
		sql.append("       TM_VHCL_MATERIAL_GROUP   TVMG,\n");
		sql.append("       TM_VHCL_MATERIAL_GROUP   TVMG2\n");
		sql.append(" WHERE TVYP.PLAN_ID = TVYPD.PLAN_ID\n");
		sql.append("   AND TVYPD.MATERIAL_GROUPID = TVMG.GROUP_ID\n");
		sql.append("   AND TVMG.PARENT_GROUP_ID = TVMG2.GROUP_ID\n");
		sql.append("   AND TVMG.GROUP_LEVEL = ?\n");
		params.add(OemDictCodeConstants.TM_VHCL_MATERIAL_GROUP_CAR);
		/*
		 * 写死业务范围 Begin
		 */
		sql.append("   AND TVMG.GROUP_TYPE = '" + OemDictCodeConstants.GROUP_TYPE_IMPORT + "'\n");
		/*
		 * 写死业务范围 End..
		 */
		sql.append("   AND TVMG2.GROUP_LEVEL = ?\n");
		params.add(OemDictCodeConstants.TM_VHCL_MATERIAL_GROUP_BRAND);
		sql.append("   AND TVYP.DEALER_ID = '"+loginInfo.getDealerId()+"' \n");
		//获取当前用户
		/*LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		params.add(loginInfo.getDealerId());*/
		sql.append("   AND TVYP.PLAN_VER=(SELECT coalesce(max(PLAN_VER),0) as VARCODE from TT_VS_YEARLY_PLAN TVYP where 1=1");
		//目标类型
		if(!StringUtils.isNullOrEmpty(queryParam.get("planTypeName"))){
			sql.append(" AND TVYP.PLAN_TYPE = ? \n");
			params.add(queryParam.get("planTypeName"));
		}
		sql.append("   AND DEALER_ID = '"+loginInfo.getDealerId()+"')");   //测试数据
//		params.add(loginInfo.getDealerId());
		sql.append("   AND TVYP.STATUS = ?\n");
		params.add(OemDictCodeConstants.PLAN_MANAGE_02);
		//选择年份
		if(!StringUtils.isNullOrEmpty(queryParam.get("planYearName"))){
			sql.append(" AND TVYP.PLAN_YEAR = ? \n");
			params.add(queryParam.get("planYearName"));
		}
		sql.append(" GROUP BY TVMG2.GROUP_NAME,TVMG.GROUP_NAME ) yearPlanResult\n");
		
		return sql.toString();
	}
	
	/**
	 * 
	* @Title: yearPlanDownload 
	* @Description: 年度目标查询（下载） 
	* @param @param queryParam
	* @param @return    设定文件 
	* @return List<Map>    返回类型 
	* @throws
	 */
	public List<Map> yearPlanDownload(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getDlrYearPlanQueryListSql(queryParam,params);
		List<Map> list = OemDAOUtil.findAll(sql,params);
		return list;
	}
	
	
	

}
