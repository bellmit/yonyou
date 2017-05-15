package com.yonyou.dms.vehicle.dao.realitySales.retailReportQuery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;

public class CustcomVehicleQueryDao extends OemBaseDAO{
	public static Logger logger = Logger.getLogger(CustcomVehicleQueryDao.class);
	
	//公共查询界面
	public static PageInfoDto getCustomerVehicleQueryList(String ctmName,String ctmType,String mainPhone,
			String salesDateStart,String salesDateEnd) throws Exception {
	    StringBuffer sql=new StringBuffer();
	    sql.append("SELECT t2.CTM_ID,t3.VIN,\n");  //VIN
	    sql.append("       t2.CTM_NAME, \n");//客户名称
	    sql.append("       t2.CTM_TYPE, \n");//客户类型
	    sql.append("       t2.MAIN_PHONE, \n");//联系方式
	    sql.append("       t1.SALES_DATE \n");//客户创建时间
	    sql.append("  FROM TT_VS_SALES_REPORT t1,TT_VS_CUSTOMER t2 ,TM_VEHICLE_DEC t3 \n");  
	    sql.append(" where  1=1   \n"); 
	    sql.append(" and   t1.CTM_ID=t2.CTM_ID \n"); 
	    sql.append(" and   t3.VEHICLE_ID=t1.VEHICLE_ID \n"); 
	    
	    //vin判断
	    if(!"".equals(ctmName)&&ctmName!=null){
	    	sql.append(" and  t2.CTM_NAME LIKE '%" + ctmName +"%' ");
	    }
	    //销售订单号
	    if(!"".equals(ctmType)&&ctmType!=null){
	    	sql.append(" and  t2.CTM_TYPE =" + ctmType +" ");
	    }
	    //联系方式
	    if(!"".equals(mainPhone)&&mainPhone!=null){
	    	sql.append(" and  t2.MAIN_PHONE ='" + mainPhone +"' ");
	    }
	    //船舰事件
	    if(!"".equals(salesDateStart)&&salesDateStart!=null){
	    	sql.append(" and  t1.SALES_DATE >='" + salesDateStart +"' ");
	    }
	  //船舰事件
	    if(!"".equals(salesDateEnd)&&salesDateEnd!=null){
	    	sql.append(" and  t1.SALES_DATE <='" + salesDateEnd +"' ");
	    }
	    
		logger.debug("SQL+++++++++++++++++++++++: " + sql.toString());
		PageInfoDto po=OemDAOUtil.pageQuery(sql.toString(),null);
		return po;
		//pageQuery(sql.toString(), null,null,curPage, pageSize) 
	}
	
	@SuppressWarnings("rawtypes")
	public static Map<String, Object> getDetailCustomerVehicleQueryInfo (String vin,Long ctmId){
		Map<String, Object> infoMap = new HashMap<String, Object>();
		StringBuffer vehSql = new StringBuffer();
		try{
			Long.valueOf(ctmId);
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
		    vehSql.append("from TT_VS_CUSTOMER t1,TT_VS_SALES_REPORT t2 ,TM_VEHICLE_DEC t3 ,("+ getVwMaterialSql()+") vm\n");
		    vehSql.append("where T1.CTM_ID = T2.CTM_ID \n");
		    vehSql.append("AND   T2.VEHICLE_ID = T3.VEHICLE_ID \n");
		    vehSql.append("AND   T3.MATERIAL_ID = VM.MATERIAL_ID \n");
		    vehSql.append("AND   T1.CTM_ID = ");
		    vehSql.append(ctmId);
		    vehSql.append("");
		}catch(Exception ex){
			vehSql.append("SELECT T1.VIN,T1.ENGINE_NO, \n");
			vehSql.append("       VM.BRAND_NAME, \n");
			vehSql.append("       VM.SERIES_NAME, \n");
			vehSql.append("       VM.MATERIAL_NAME, \n");
			vehSql.append("       VM.COLOR_NAME, \n");
			vehSql.append("       VM.TRIM_NAME \n");
			vehSql.append("  FROM TM_VEHICLE_DEC T1, ("+ getVwMaterialSql()+") VM \n");
			vehSql.append(" WHERE T1.MATERIAL_ID = VM.MATERIAL_ID \n");
			vehSql.append("   AND T1.VIN = '" + vin + "' \n");
			vin = "null";
		}
		
		List<Map> vehList = OemDAOUtil.findAll(vehSql.toString(), null);
		infoMap.put("vehList", vehList);//车辆信息
		
		StringBuffer cusSql = new StringBuffer();
		cusSql.append("SELECT distinct t1.CTM_TYPE, \n");//客户类型
		cusSql.append("       T1.CTM_NAME, \n");//客户姓名
		cusSql.append("       t1.CARD_TYPE,\n");//证件类型
		cusSql.append("       t1.CARD_NUM, \n");//证件号码
		cusSql.append("       t1.SEX, 		\n");//客户性别
		cusSql.append("       t1.MAIN_PHONE, \n");//客户电话
		cusSql.append("       t1.OTHER_PHONE \n");//客户手机号
		cusSql.append("from TT_VS_CUSTOMER t1 \n");
		cusSql.append("where T1.CTM_ID = ");
		cusSql.append(ctmId);
		cusSql.append("");
		List<Map> cusList = OemDAOUtil.findAll(cusSql.toString(), null);
		infoMap.put("cusList", cusList);//客户信息
		
		StringBuffer detSql = new StringBuffer();
		detSql.append("SELECT distinct left(char(timestamp(t1.BIRTHDAY)),10) BIRTHDAY, \n");//出生年月
		detSql.append("       T1.IS_MARRIED, \n");//婚姻状况
		detSql.append("       t1.EDUCATION, \n");//学历
		detSql.append("       t1.INDUSTRY_FIRST,\n");//所在行业一大类
		detSql.append("       t1.INDUSTRY_SECOND,\n");//所在行业大类
		detSql.append("       t1.INCOME, \n");//家庭年收入
		detSql.append("       t1.BEST_CONTACT_TYPE, \n");//喜好沟通方式
		detSql.append("       t1.PROVINCE, \n");//所在省份
		detSql.append("       t1.CITY, \n");//所在城市
		detSql.append("       t1.ADDRESS, \n");//客户地址
		detSql.append("       t1.POST_CODE, \n");//邮编
		detSql.append("       t1.EMAIL \n");//电子邮件
		detSql.append("from TT_VS_CUSTOMER t1 \n");
		detSql.append("where T1.CTM_ID = ");
		detSql.append(ctmId);
		detSql.append("");
		List<Map> detList = OemDAOUtil.findAll(detSql.toString(), null);
		infoMap.put("detList", detList);//客户信息
		
		StringBuffer lxrSql = new StringBuffer();
		lxrSql.append("SELECT distinct t2.NAME, \n");//联系人姓名
		lxrSql.append("       T2.MAIN_PHONE, \n");//联系人电话
		lxrSql.append("       t2.OTHER_PHONE \n");//联系人手机
		lxrSql.append("from TT_VS_CUSTOMER t1,TT_VS_LINKMAN t2 \n");
		lxrSql.append("where T1.CTM_ID = T2.CTM_ID \n");
		lxrSql.append("and T1.CTM_ID = ");
		lxrSql.append(ctmId);
		lxrSql.append("");
		List<Map> lxrList = OemDAOUtil.findAll(lxrSql.toString(), null);
		infoMap.put("lxrList", lxrList);//联系人信息
		
		StringBuffer secSql = new StringBuffer();
		secSql.append("SELECT distinct t2.EMISSIONS, \n");//排气量
		secSql.append("       DATE_FORMAT(T2.PRODUCTION_DATE,'%Y-%m-%d') AS PRODUCTION_DATE, \n");//出厂日期
		secSql.append("       DATE_FORMAT(T2.BUY_DATE,'%Y-%m-%d') AS BUY_DATE, \n");//购买日期
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
		secSql.append("       DATE_FORMAT(T2.ANNUAL_INSPECTION_DATE,'%Y-%m-%d') AS ANNUAL_INSPECTION_DATE, \n");//年检有效期
		secSql.append("       T2.ORIGIN_CERTIFICATE, \n");//来历凭证
		secSql.append("       DATE_FORMAT(T2.SCRAP_DATE,'%Y-%m-%d') AS SCRAP_DATE, \n");//强制报废期
		secSql.append("       T2.PURCHASE_TAX, \n");//购置税凭证
		secSql.append("       DATE_FORMAT(T2.TRAFFIC_INSURE_DATA,'%Y-%m-%d') AS TRAFFIC_INSURE_DATA, \n");//交强有效期
		secSql.append("       T2.VEHICLE_AND_VESSEL_TAX, \n");//车船使用税
		secSql.append("       t2.REMARK \n");//手续项目特殊说明
		secSql.append("from TT_VS_CUSTOMER t1,TT_VS_SECOND_CAR t2 \n");
		secSql.append("where T1.CTM_ID = T2.CTM_ID \n");
		secSql.append("and T1.CTM_ID = ");
		secSql.append(ctmId);
		secSql.append("");
		
		List<Map> secList = OemDAOUtil.findAll(secSql.toString(), null);
		infoMap.put("secList", secList);//二手车信息
		
		return infoMap;
	}
	
	@SuppressWarnings("unchecked")
	public static String getDesc(String info){
		String sql = "select code_desc from TC_CODE_DCS where code_id="+info;
		Map<String, Object> map = new HashMap<String, Object>();
		map = OemDAOUtil.findFirst(sql, null);
		if(map==null){
			map = new HashMap<String, Object>();
			map.put("CODE_DESC","");
		}
		return map.get("CODE_DESC")+"";
	}

	@SuppressWarnings("unchecked")
	public String getTrDesc(String code) {
		String sql = "select REGION_NAME from TM_REGION_DCS where REGION_CODE="+code;
		Map<String, Object> map = new HashMap<String, Object>();
		map = OemDAOUtil.findFirst(sql, null);
		if(map==null){
			map = new HashMap<String, Object>();
			map.put("REGION_NAME","");
		}
		return map.get("REGION_NAME")+"";
	}
//	@Override
//	protected PO wrapperPO(ResultSet rs, int idx) {
//		return null;
//	}

}
