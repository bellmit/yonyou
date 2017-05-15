package com.yonyou.dms.vehicle.dao.afterSales.custcomVehicle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * 客户资料维护
 * @author Administrator
 *
 */
@Repository
public class CustcomVehicleManageDao extends OemBaseDAO{
	/**
	 * 客户资料查询
	 */
	public PageInfoDto  CustcomVehicleQuery(Map<String, String> queryParam) {
		   List<Object> params = new ArrayList<>();
		    String sql = getQuerySql(queryParam,params);
		    PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql,params);
		    System.out.println(pageInfoDto);
			return pageInfoDto;
	}
	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		StringBuffer sql = new StringBuffer();

		sql.append("SELECT T1.CTM_ID, \n");
		sql.append("       T3.VIN, -- vin \n");
		sql.append("       T2.CTM_NAME, -- 客户名称 \n");
		sql.append("       T2.CTM_TYPE, -- 客户类型 \n");
		sql.append("       DATE_FORMAT(T1.SALES_DATE,'%Y-%m-%d') SALES_DATE,-- 客户创建时间 \n");
		sql.append("       T2.MAINTAIN_BY UPDATE_BY,-- 修改人 \n");
		sql.append("       DATE_FORMAT(T2.MAINTAIN_DATE,'%Y-%m-%d') UPDATE_DATE-- 修改时间 \n");
		sql.append("  FROM TT_VS_SALES_REPORT T1 \n");
		sql.append("  INNER JOIN TT_VS_CUSTOMER T2 ON T1.CTM_ID = T2.CTM_ID \n");
		sql.append("  INNER JOIN TM_VEHICLE_DEC T3 ON T1.VEHICLE_ID = T3.VEHICLE_ID \n");
		sql.append("  WHERE 1=1 \n");
		//客户名称
		  if (!StringUtils.isNullOrEmpty(queryParam.get("ctmName"))) {
				sql.append("AND T2.CTM_NAME like '%"+queryParam.get("ctmName")+"%'  \n");
			}
		  //客户类型
		  if (!StringUtils.isNullOrEmpty(queryParam.get("ctmType"))) {
				sql.append("AND T2.CTM_TYPE like '%"+queryParam.get("ctmType")+"%'  \n");
			}
		  //客户创建开始时间
		  if (!StringUtils.isNullOrEmpty(queryParam.get("salesDateStart"))) {
			  sql.append("  AND DATE_FORMAT(T1.SALES_DATE,'%Y-%m-%d') >='" + queryParam.get("salesDateStart")+"'  \n");
			}
		  //客户创建结束时间
		  if (!StringUtils.isNullOrEmpty(queryParam.get("salesDateEnd"))) {
			  sql.append("  AND DATE_FORMAT(T1.SALES_DATE,'%Y-%m-%d') <='" +  queryParam.get("salesDateEnd")+"'  \n");
			}
		  //vin
		  if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
				sql.append("AND T3.VIN like '%"+queryParam.get("vin")+"%'  \n");
			}
		 System.out.println(sql.toString());
			return sql.toString();
	}
	
	/**
	 * 客户资料下载
	 */
	public List<Map> download(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam, params);
		List<Map> download = OemDAOUtil.downloadPageQuery(sql, null);
		return download;
	}
	
	/**
	 * 进行修改信息回显
	 */
	public List<Map> getShuju(Long id){
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT T2.CTM_ID, \n");
		sql.append("       T3.VIN, -- VIN \n");
		sql.append("       T3.ENGINE_NO, -- 发动机号 \n");
		sql.append("       T1.VEHICLE_NO, -- 车牌号 \n");
		sql.append("       T4.BRAND_NAME, -- 品牌 \n");
		sql.append("       T4.SERIES_NAME, -- 车系 \n");
		sql.append("       T4.MATERIAL_NAME, -- 车型 \n");
		sql.append("       T4.COLOR_NAME, -- 颜色 \n");
		sql.append("       T4.TRIM_NAME, -- 内饰 \n");
		sql.append("       T1.INVOICE_NO,T1.REPORT_ID, -- 发票号 \n");
		sql.append("       T2.CTM_TYPE, -- 客户类型 \n");
		sql.append("       T2.CTM_NAME, -- 客户姓名 \n");
		sql.append("       T2.CARD_TYPE, -- 证件类型 \n");
		sql.append("       T2.CARD_NUM, -- 证件号码 \n");
		sql.append("       T2.SEX, -- 客户性别 \n");
		sql.append("       T2.INCOME, -- 家庭年收入 \n");
		sql.append("       DATE_FORMAT(T2.BIRTHDAY,'%Y-%m-%d') BIRTHDAY, -- 出生年月 \n");
		sql.append("       T2.IS_MARRIED, -- 婚姻状况 \n");
		sql.append("       T2.INDUSTRY_FIRST, -- 所在行业大类 \n");
		sql.append("       T2.INDUSTRY_SECOND, -- 所在行业小类 \n");
		sql.append("       T2.PROVINCE, -- 省份 \n");
		sql.append("       T2.CITY, -- 城市 \n");
		sql.append("       T2.POST_CODE, -- 邮编 \n");
		sql.append("       T2.MAIN_PHONE, -- 手机号码 \n");
		sql.append("       T2.OTHER_PHONE, -- 联系电话 \n");
		sql.append("       T2.TOWN, -- 区县\n");
		sql.append("       T2.ADDRESS, -- 地址 \n");
		sql.append("       T2.EMAIL -- 电子邮件 \n");
		sql.append("  FROM TT_VS_SALES_REPORT T1 \n");
		sql.append("  INNER JOIN TT_VS_CUSTOMER T2 ON T1.CTM_ID = T2.CTM_ID \n");
		sql.append("  INNER JOIN TM_VEHICLE_DEC T3 ON T1.VEHICLE_ID = T3.VEHICLE_ID \n");
		sql.append("  LEFT JOIN ("+getVwMaterialSql() +")  T4 ON T3.MATERIAL_ID = T4.MATERIAL_ID \n");
		  if (!StringUtils.isNullOrEmpty(id)) {
				sql.append("  WHERE T1.CTM_ID ="+id+"  \n");
			}
		  return OemDAOUtil.findAll(sql.toString(),null); 
		    
	}
	
	/**
	 * 查询地区省份
	 */
	public List<Map> queryProvince() {
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT REGION_CODE, \n");
		sql.append("       REGION_NAME \n");
		sql.append("  FROM TM_REGION_DCS \n");
		sql.append("  WHERE PARENT_ID = 86 \n");
		//执行查询操作
		List<Map> result=OemDAOUtil.findAll(sql.toString(),null);
		return result;
	}
	/**
	 * 查询城市
	 */
	public List<Map> queryCity(Long parentId) {
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT REGION_CODE, \n");
		sql.append("       REGION_NAME \n");
		sql.append("  FROM TM_REGION_DCS \n");
		sql.append("  WHERE PARENT_ID = "+parentId+" \n");
		//执行查询操作
		List<Map> result=OemDAOUtil.findAll(sql.toString(),null);
		return result;
	}
	
	
	/**
	 * 查询县
	 */
	public List<Map> queryTowm(Long parentId) {
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT REGION_CODE, \n");
		sql.append("       REGION_NAME \n");
		sql.append("  FROM TM_REGION_DCS \n");
		sql.append("  WHERE PARENT_ID = "+parentId+" \n");
		//执行查询操作
		List<Map> result=OemDAOUtil.findAll(sql.toString(),null);
		return result;
	}
	
	
	
	/**
	 * 查询所在行业类别大
	 * @param parentCode
	 * @return
	 */
	public List<Map> searchIndustryBig(){
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT INDUSTRY_CODE, \n");
		sql.append("       INDUSTRY_NAME \n");
		sql.append("  FROM TM_INDUSTRY_dcs \n");
		sql.append("  WHERE PARENT_CODE = -1 \n");
		List<Map> result=OemDAOUtil.findAll(sql.toString(),null);
		return result;
	}
	
	/**
	 * 查询所在行业类别小
	 * @param parentCode
	 * @return
	 */
	public List<Map> searchIndustry(String parentCode){
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT INDUSTRY_CODE, \n");
		sql.append("       INDUSTRY_NAME \n");
		sql.append("  FROM TM_INDUSTRY_dcs \n");
			sql.append("  WHERE PARENT_CODE = "+parentCode+" \n");
		List<Map> result=OemDAOUtil.findAll(sql.toString(),null);
		return result;
	}
	
	

}
