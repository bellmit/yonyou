package com.yonyou.dms.vehicle.dao.afterSales.priceLimit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 *  车系限价管理 
 * @author Administrator
 *
 */
@Repository
public class PriceLimiteManageDao  extends OemBaseDAO{
	/**
	 * 车系现价管理查询
	 */
	public PageInfoDto PriceLimiteManageQuery(Map<String, String> queryParam) {
		   List<Object> params = new ArrayList<>();
		    String sql = getQuerySql(queryParam,params);
		    PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql,params);
		    System.out.println(pageInfoDto);
			return pageInfoDto;
	}
	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		StringBuffer sql = new StringBuffer();
		String brandCode =null;
		String limitSeries=null;
		sql.append(" SELECT LIMITED_ID, LIMITED_NAME, LIMITED_RANGE*100 as LIMITED_RANGE, LM.DEALER_CODE, DESCEND_STATUS, DATE_FORMAT(DESCEND_DATE,'%Y-%m-%d') as DESCEND_DATE, \n");
		sql.append(" BRAND, SERIES,LM.SERIES_CODE,REPAIR_TYPE,REPAIR_DESC, COMMENT, DATE_FORMAT(LM.CREATE_DATE,'%Y-%m-%d') as CREATE_DATE,TM.DEALER_SHORTNAME \n");
		sql.append("  FROM TM_LIMITE_CPOS_dcs LM  \n");
		sql.append("  INNER JOIN TM_DEALER TM  on  TM.DEALER_CODE =LM.DEALER_CODE \n");
		sql.append("  WHERE 1=1  \n");
		sql.append("  AND  LM.IS_DEL=0  \n");
		if (!StringUtils.isNullOrEmpty(queryParam.get("brandCode"))) {
			brandCode = queryParam.get("brandCode").replaceAll("\\,", "\n");
			brandCode = queryParam.get("brandCode").replaceAll("[\\t\\n\\r]", ",");
			brandCode = queryParam.get("brandCode").replaceAll(",''", "");
			String[] brandCodeArray=brandCode.split(",");
			sql.append("    AND  ( ");
			String temp="";
			for (String strBrand : brandCodeArray) {
				temp +=" LM.BRAND like '%" + strBrand + "%' OR \n";
			}
			temp=temp.substring(0, temp.lastIndexOf("OR"));
			sql.append(temp);
			sql.append(")");
			
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("limitSeries"))) {
			limitSeries =queryParam.get("limitSeries").replaceAll("\\,", "\n");
			limitSeries =queryParam.get("limitSeries").replaceAll("[\\t\\n\\r]", ",");
			limitSeries =queryParam.get("limitSeries").replaceAll(",''", "");
			String[] limitSeriesArray =limitSeries.split(",");
			sql.append("	AND ( ");
			String temp="";
			for (String limitSerie : limitSeriesArray) {
				temp +="LM.SERIES_CODE like '%" + limitSerie + "%' OR \n";
			}
			temp=temp.substring(0, temp.lastIndexOf("OR"));
			sql.append(temp);
			sql.append(" )");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("descendDateStart"))) {
			sql.append("	AND  DATE_FORMAT(LM.DESCEND_DATE,'%Y-%m-%d') >= '" +queryParam.get("descendDateStart") + "' \n");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("descendDateEnd"))) {
			sql.append("	AND  DATE_FORMAT(LM.DESCEND_DATE,'%Y-%m-%d') <= '" + queryParam.get("descendDateEnd") + "' \n");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("descendStatus"))) {
			sql.append("	AND  LM.DESCEND_STATUS = "+queryParam.get("descendStatus")+ " \n");
		}
		 System.out.println(sql.toString());
			return sql.toString();
	}
	/**
	 * 查询所有品牌代码
	 */
	   public List<Map> getBrandCode(){
	        StringBuffer sql = new StringBuffer();
	        sql.append("SELECT DISTINCT T1.GROUP_ID,T1.GROUP_NAME,T1.GROUP_CODE  \n");
			sql.append("  FROM TM_VHCL_MATERIAL_GROUP T1,TM_VHCL_MATERIAL_GROUP T2 WHERE T1.GROUP_ID = T2.PARENT_GROUP_ID AND T1.GROUP_LEVEL = 1 \n");
			sql.append("  AND  T1.STATUS = 10011001 \n");
	    return OemDAOUtil.findAll(sql.toString(),null); 
}
	
	
	/**
	 * 通过品牌代码查询所有车系代码
	 */
	   public List<Map> getSeriesCode(String brandCode){
	        StringBuffer sql = new StringBuffer();
	        sql.append("SELECT T2.GROUP_ID,T2.GROUP_CODE,CONCAT_WS('-',T2.GROUP_NAME,T2.GROUP_CODE ) as  GROUP_NAME  \n");
			sql.append("  FROM TM_VHCL_MATERIAL_GROUP T1,TM_VHCL_MATERIAL_GROUP T2 WHERE T1.GROUP_ID = T2.PARENT_GROUP_ID AND T2.GROUP_LEVEL = 2 \n");
			sql.append("  AND  T2.STATUS = 10011001 \n");
			if (!StringUtils.isNullOrEmpty(brandCode)) {
				brandCode = brandCode.replaceAll("\\,", "\n");
				brandCode = brandCode.replaceAll("[\\t\\n\\r]", "','");
				brandCode = brandCode.replaceAll(",''", "");
				sql.append("  AND  T1.GROUP_CODE in ('" + brandCode + "') ");
			}
	    return OemDAOUtil.findAll(sql.toString(),null); 
}
	   
	   
/*	   public List<Map> getSeriesCode2(String brandCode){
	        StringBuffer sql = new StringBuffer();
	        sql.append("SELECT T2.GROUP_ID,T2.GROUP_CODE,T2.GROUP_NAME,T2.GROUP_CODE  \n");
			sql.append("  FROM TM_VHCL_MATERIAL_GROUP T1,TM_VHCL_MATERIAL_GROUP T2 WHERE T1.GROUP_ID = T2.PARENT_GROUP_ID AND T2.GROUP_LEVEL = 2 \n");
			sql.append("  AND  T2.STATUS = 10011001 \n");
			if (!StringUtils.isNullOrEmpty(brandCode)) {
				brandCode = brandCode.replaceAll("\\,", "\n");
				brandCode = brandCode.replaceAll("[\\t\\n\\r]", "','");
				brandCode = brandCode.replaceAll(",''", "");
				sql.append("  AND  T1.GROUP_CODE in ('" + brandCode + "') ");
			}
			System.out.println(sql);
	    return OemDAOUtil.findAll(sql.toString(),null); 
}*/
	   
	   /**
	    * 下载
	    */
	public List<Map> download(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam, params);
		List<Map> download = OemDAOUtil.downloadPageQuery(sql, null);
		return download;
	}
	

	   

	   
	   
	   

}
