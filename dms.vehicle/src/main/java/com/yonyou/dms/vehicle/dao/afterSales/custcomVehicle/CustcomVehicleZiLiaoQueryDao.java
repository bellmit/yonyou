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
 * 客户车辆资料查询
 * @author Administrator
 *
 */
@Repository
public class CustcomVehicleZiLiaoQueryDao extends OemBaseDAO{
	/**
	 * 查询
	 */
	public PageInfoDto  VehicleZiLiaoQuery(Map<String, String> queryParam) {
		   List<Object> params = new ArrayList<>();
		    String sql = getQuerySql(queryParam,params);
		    PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql,params);
		    System.out.println(pageInfoDto);
			return pageInfoDto;
	}
	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		StringBuffer sql = new StringBuffer();

		 sql.append("SELECT t2.CTM_ID,t3.VIN,\n");  //VIN
		    sql.append("       t2.CTM_NAME, \n");//客户名称
		    sql.append("       t2.CTM_TYPE, \n");//客户类型
		    sql.append("       t2.MAIN_PHONE, \n");//联系方式
		    sql.append("       t1.SALES_DATE \n");//客户创建时间
		    sql.append("  FROM TT_VS_SALES_REPORT t1,TT_VS_CUSTOMER t2 ,TM_VEHICLE_dec t3 \n");  
		    sql.append(" where  1=1   \n"); 
		    sql.append(" and   t1.CTM_ID=t2.CTM_ID \n"); 
		    sql.append(" and   t3.VEHICLE_ID=t1.VEHICLE_ID \n"); 
		    
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
		 System.out.println(sql.toString());
			return sql.toString();
	}
	/**
	 * 通过id进行明细查询车辆信息
	 */
	public List<Map> getVehicle(Long id){
		StringBuffer vehSql = new StringBuffer();
		try{
			vehSql.append("SELECT distinct t3.VIN, \n");//VIN
			vehSql.append("       T1.CTM_NAME, \n");//
			vehSql.append("       t3.ENGINE_NO, \n");//发动机号
			vehSql.append("       t2.VEHICLE_NO, 		\n");//车牌号
			vehSql.append("       vm.BRAND_NAME, \n");//品牌
			vehSql.append("       vm.SERIES_NAME, \n");//车系
			vehSql.append("       vm.MATERIAL_NAME, \n");//车型
			vehSql.append("       vm.COLOR_NAME, \n");//颜色
		    vehSql.append("       vm.TRIM_NAME,\n");//内饰
		    vehSql.append("       t2.INVOICE_NO,t2.REPORT_ID\n");//发票号
		    vehSql.append("from TT_VS_CUSTOMER t1,TT_VS_SALES_REPORT t2 ,TM_VEHICLE_dec t3, ("+getVwMaterialSql() +") vm\n");
		    vehSql.append("where T1.CTM_ID = T2.CTM_ID \n");
		    vehSql.append("AND   T2.VEHICLE_ID = T3.VEHICLE_ID \n");
		    vehSql.append("AND   T3.MATERIAL_ID = VM.MATERIAL_ID \n");
		    vehSql.append("AND   T1.CTM_ID = ");
		    vehSql.append(id);
		    vehSql.append("");
		}catch(Exception ex){
			vehSql.append("SELECT T1.VIN,T1.ENGINE_NO, \n");
			vehSql.append("       VM.BRAND_NAME, \n");
			vehSql.append("       VM.SERIES_NAME, \n");
			vehSql.append("       VM.MATERIAL_NAME, \n");
			vehSql.append("       VM.COLOR_NAME, \n");
			vehSql.append("       VM.TRIM_NAME \n");
			vehSql.append("  FROM TM_VEHICLE T1, ("+getVwMaterialSql() +") vm \n");
			vehSql.append(" WHERE T1.MATERIAL_ID = VM.MATERIAL_ID \n");
	  if (!StringUtils.isNullOrEmpty(id)) {
			vehSql.append("   AND T1.VIN = '" + id + "' \n");
				}
		}
		System.out.println(vehSql);
		   return OemDAOUtil.findAll(vehSql.toString(),null); 
	}
	/**
	 * 通过id进行明细查询客户信息
	 */
	public List<Map> getCustomer(Long id){
		StringBuffer cusSql = new StringBuffer();
		cusSql.append("SELECT distinct t1.CTM_TYPE, \n");//客户类型
		cusSql.append("       T1.CTM_NAME, \n");//客户姓名
		cusSql.append("       t1.CARD_TYPE,\n");//证件类型
		cusSql.append("       t1.CARD_NUM, \n");//证件号码
		cusSql.append("       t1.SEX, 		\n");//客户性别
		cusSql.append("       t1.MAIN_PHONE, \n");//客户电话
		cusSql.append("       t1.OTHER_PHONE, \n");//客户手机号
		cusSql.append("       DATE_FORMAT(t1.BIRTHDAY,'%Y-%m-%d') BIRTHDAY, \n");//出生年月
		cusSql.append("       T1.IS_MARRIED, \n");//婚姻状况
		cusSql.append("       t1.EDUCATION, \n");//学历
		cusSql.append("       t1.INDUSTRY_FIRST,\n");//所在行业一大类
		cusSql.append("       t1.INDUSTRY_SECOND,\n");//所在行业二类
		cusSql.append("       t1.INCOME, \n");//家庭年收入
		cusSql.append("       t1.BEST_CONTACT_TYPE, \n");//喜好沟通方式
		cusSql.append("       t1.PROVINCE, \n");//所在省份
		cusSql.append("       t1.CITY, \n");//所在城市
		cusSql.append("       t1.ADDRESS, \n");//客户地址
		cusSql.append("       t1.POST_CODE, \n");//邮编
		cusSql.append("       t1.EMAIL \n");//电子邮件
		cusSql.append("from TT_VS_CUSTOMER t1 \n");
		cusSql.append("where T1.CTM_ID = ");
		cusSql.append(id);
		System.out.println(cusSql);
	   return OemDAOUtil.findAll(cusSql.toString(),null); 
	}
	/**
	 * 通过id进行明细查询联系人信息
	 */
	public List<Map> getPeople(Long id){
		StringBuffer lxrSql = new StringBuffer();
		lxrSql.append("SELECT distinct t2.NAME, \n");//联系人姓名
		lxrSql.append("       T2.MAIN_PHONE, \n");//联系人电话
		lxrSql.append("       t2.OTHER_PHONE \n");//联系人手机
		lxrSql.append("from TT_VS_CUSTOMER t1,TT_VS_LINKMAN t2 \n");
		lxrSql.append("where T1.CTM_ID = T2.CTM_ID \n");
		lxrSql.append("and T1.CTM_ID = ");
		lxrSql.append(id);
		System.out.println(lxrSql);
	   return OemDAOUtil.findAll(lxrSql.toString(),null); 
	}
	
	/**
	 * 通过id进行查询二手车信息
	 */

	public List<Map> getCheXinXi(Long id){
		StringBuffer secSql = new StringBuffer();
		secSql.append("SELECT distinct t2.EMISSIONS, \n");//排气量
		secSql.append("       T2.PRODUCTION_DATE, \n");//出厂日期
		secSql.append("       T2.BUY_DATE, \n");//购买日期
		secSql.append("       T2.GEAR_FORM, \n");//排挡形式
		secSql.append("       T2.MILEAGE, \n");//里程数
		secSql.append("       T2.FUEL_TYPE, \n");//燃料种类
		secSql.append("       T2.HBBJ, \n");//环保标准
		secSql.append("       T2.VEHICLE_ALLOCATION, \n");//车辆配置
		secSql.append("       T2.USE_TYPE, \n");//使用性质
		secSql.append("       T2.TRAFFIC_INSURE_INFO, \n");//交强险信息
		secSql.append("       T2.DRIVING_LICENSE, \n");//机动车行驶证
		secSql.append("       T2.BUSINESS, \n");//商业险信息
		secSql.append("       T2.REGISTRY, \n");//登记证书
		secSql.append("       T2.ANNUAL_INSPECTION_DATE, \n");//年检有效期
		secSql.append("       T2.ORIGIN_CERTIFICATE, \n");//来历凭证
		secSql.append("       T2.SCRAP_DATE, \n");//强制报废期
		secSql.append("       T2.PURCHASE_TAX, \n");//购置税凭证
		secSql.append("       T2.TRAFFIC_INSURE_DATA, \n");//交强有效期
		secSql.append("       T2.VEHICLE_AND_VESSEL_TAX, \n");//车船使用税
		secSql.append("       t2.REMARK \n");//手续项目特殊说明
		secSql.append("from TT_VS_CUSTOMER t1,TT_VS_SECOND_CAR t2 \n");
		secSql.append("where T1.CTM_ID = T2.CTM_ID \n");
		secSql.append("and T1.CTM_ID = ");
		secSql.append(id);
		System.out.println(secSql);
	   return OemDAOUtil.findAll(secSql.toString(),null); 
	}
}
