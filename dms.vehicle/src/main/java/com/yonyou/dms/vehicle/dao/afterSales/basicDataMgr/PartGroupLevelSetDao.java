package com.yonyou.dms.vehicle.dao.afterSales.basicDataMgr;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * 配件分组级别设定
 * @author Administrator
 *
 */
@Repository
public class PartGroupLevelSetDao extends OemBaseDAO{
	
	/**
	 * 配件分组级别设定查询
	 */
	
	public PageInfoDto  LevelSetQuery(Map<String, String> queryParam) {
		   List<Object> params = new ArrayList<>();
		    String sql = getQuerySql(queryParam,params);
		    PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql,params);
		    System.out.println(pageInfoDto);
			return pageInfoDto;
	}
	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT  ID,  FAMIGLIA_FAMIGLIA, \n");
		sql.append("   		FAMIGLIA_FAMIGLIA_DESC, \n");
		sql.append("		ITEM_NO,  \n");
		sql.append("		(CASE WHEN UPDATE_DATE is not null THEN DATE_FORMAT(UPDATE_DATE,'%Y-%m-%d') ELSE DATE_FORMAT(CREATE_DATE,'%Y-%m-%d') END) CREATE_DATE \n");
		sql.append("	FROM TT_PART_GROUP_LEVEL_SET_DCS \n");
		sql.append("	WHERE 1=1 \n");
		 if (!StringUtils.isNullOrEmpty(queryParam.get("FAMIGLIA_FAMIGLIA"))) {
			sql.append(" AND UPPER(FAMIGLIA_FAMIGLIA) like UPPER('%"+queryParam.get("FAMIGLIA_FAMIGLIA")+"%') \n");
    	}
		 if("91191000".equals(queryParam.get("ITEM_NO"))){
			 sql.append(" AND ITEM_NO IN (100) \n");
		 }else if("91191002".equals(queryParam.get("ITEM_NO"))){
			 sql.append(" AND ITEM_NO IN (200) \n");
		 }else if("91191003".equals(queryParam.get("ITEM_NO"))){
			 sql.append(" AND ITEM_NO IN (300) \n");
		 }else if("91191004".equals(queryParam.get("ITEM_NO"))){
			 sql.append(" AND ITEM_NO IN (400) \n");
		 }else if("91191005".equals(queryParam.get("ITEM_NO"))){
			 sql.append(" AND ITEM_NO IN (500) \n");
		 }
		 System.out.println(sql.toString());
			return sql.toString();
	}
	
	
	//导入临时表
	public List<Map> findTmpRecallVehicleDcsList() {
		//获取当前用户
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("\n");
		sql.append(" SELECT * FROM  Tt_Part_Group_Level_Set_Temp_dcs   \n");
		sql.append(" WHERE  CREATE_BY =  "+loginInfo.getUserId()+"  \n");
		sql.append(" order by ID  \n");
		List<Map> resultList = OemDAOUtil.findAll(sql.toString(), params);
		return resultList;
	}
	

	

}
