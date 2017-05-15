package com.yonyou.dms.report.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.DTO.monitor.OperateLogDto;
import com.yonyou.dms.common.service.monitor.OperateLogService;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;

/**
 * 车辆进销存分析实现类
 * @author wangliang
 * @date 2017年1月18日
 */

@Service
@SuppressWarnings("unchecked")
public class VehicleInfoStatServiceImpl implements VehicleInfoStatService{	
	@Autowired
    private OperateLogService operateLogService;
	
	/**
	 * 前台界面查询方法
	 * @author wangliang
	 * @throws Exception 
	 * @date 2017年1月18日
	 */
	
	@Override
	public PageInfoDto queryVeicleInfoChecked(Map<String, String> queryParam, String startDate, String endDate)
			throws Exception {
		//获取经销商代码
		String  DealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		String lastStart=null;
		String lastEnd=null;
		Calendar call = Calendar.getInstance();
		call.setTime(Utility.getTimeStamp(startDate));
		call.add(Calendar.MONTH, -1);
		lastStart=new SimpleDateFormat("yyyy-MM-dd").format(call.getTime()).toString();
		call.add(Calendar.MONTH, 1);
		call.add(Calendar.DATE, -1);
		lastEnd=new SimpleDateFormat("yyyy-MM-dd").format(call.getTime()).toString();
		
		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT B.*,  BR.BRAND_NAME,SE.SERIES_NAME,MO.MODEL_NAME FROM ( ");
	    sb.append(" SELECT TA1.BRAND_CODE AS BRAND_CODE,TA1.SERIES_CODE AS SERIES_CODE,TA1.MODEL_CODE AS MODEL_CODE,SUM(NOW_AMOUNT) AS NOW_AMOUNT,SUM(SALES_AMOUNT) AS SALES_AMOUNT,SUM(LAST_MONTH_SALES) AS LAST_MONTH_SALES,SUM(ROAD) AS ROAD,SUM(CUSTOMER_H) AS CUSTOMER_H,SUM(CUSTOMER_A) AS CUSTOMER_A,SUM(CUSTOMER_B) AS CUSTOMER_B,DEALER_CODE, ");
		sb.append(" CONCAT( CAST( SUM(NOW_AMOUNT) * 100.00 / ( CASE WHEN SUM(LAST_MONTH_SALES) = 0  THEN 1  ELSE SUM(LAST_MONTH_SALES)  END) AS DECIMAL (10, 2)),'%') AS STORE_RATE ");
		sb.append(" FROM ");
		sb.append(" (SELECT B.BRAND_CODE,B.SERIES_CODE, B.MODEL_CODE, COUNT(A.VIN) AS NOW_AMOUNT,0 AS SALES_AMOUNT, 0 AS LAST_MONTH_SALES,0 AS ROAD, 0 AS CUSTOMER_H,0 AS CUSTOMER_A, 0 AS CUSTOMER_B,A.DEALER_CODE ");
		sb.append(" FROM TM_VS_STOCK A,");
		sb.append("("+ CommonConstants.VM_VS_PRODUCT +")"+"B");
		sb.append(" WHERE A.DEALER_CODE = B.DEALER_CODE AND A.D_KEY = B.D_KEY AND A.PRODUCT_CODE = B.PRODUCT_CODE AND A.D_KEY = "+CommonConstants.D_KEY + " AND (A.STOCK_STATUS = "+CommonConstants.DICT_STORAGE_STATUS_IN_STORAGE + "   ) AND A.DEALER_CODE = "+DealerCode+" ");
		sb.append(" GROUP BY B.BRAND_CODE,B.SERIES_CODE,B.MODEL_CODE ");
		sb.append(" UNION ALL");
		sb.append(" SELECT  B.BRAND_CODE,B.SERIES_CODE,B.MODEL_CODE,0 AS NOW_AMOUNT,COUNT(A.vin) AS SALES_AMOUNT,0 AS LAST_MONTH_SALES,0 AS ROAD,0 AS CUSTOMER_H,0 AS CUSTOMER_A,0 AS CUSTOMER_B,A.DEALER_CODE ");
		sb.append(" FROM TM_VS_STOCK A,");
		sb.append("("+ CommonConstants.VM_VS_PRODUCT +")"+"B");
		sb.append(" WHERE A.DEALER_CODE = B.DEALER_CODE AND A.D_KEY = B.D_KEY AND A.PRODUCT_CODE = B.PRODUCT_CODE AND A.D_KEY = "+CommonConstants.D_KEY + " ");
		sb.append(" AND (A.STOCK_OUT_TYPE != "+CommonConstants.DICT_STOCK_OUT_TYPE_UNTREAD + " OR A.STOCK_OUT_TYPE != "+CommonConstants.DICT_STOCK_OUT_TYPE_OTHER + " ) ");
		sb.append(" AND A.DEALER_CODE = "+DealerCode+"");
		sb.append(" AND ((1 = 1 "+Utility.getDateCond("A","FIRST_STOCK_OUT_DATE",startDate,endDate)+Utility.getDateCond("A","LATEST_STOCK_OUT_DATE",startDate,endDate)+"AND A.LATEST_STOCK_OUT_DATE >= A.LATEST_STOCK_IN_DATE) ");
		sb.append(" OR (1 = 1" +Utility.getDateCond("A","LATEST_STOCK_OUT_DATE",startDate,endDate)+"AND A.LATEST_STOCK_OUT_DATE >= A.LATEST_STOCK_IN_DATE)) ");
		sb.append(" GROUP BY B.BRAND_CODE,B.SERIES_CODE,B.MODEL_CODE ");
		sb.append(" UNION ALL");
		sb.append(" SELECT B.BRAND_CODE,B.SERIES_CODE,B.MODEL_CODE,0 AS NOW_AMOUNT,0 AS SALES_AMOUNT,COUNT(A.vin) AS LAST_MONTH_SALES,0 AS ROAD,0 AS CUSTOMER_H,0 AS CUSTOMER_A,0 AS CUSTOMER_B,A.DEALER_CODE ");
		sb.append(" FROM TM_VS_STOCK A, ");
		sb.append("("+ CommonConstants.VM_VS_PRODUCT +")"+"B");
		sb.append(" WHERE A.DEALER_CODE = B.DEALER_CODE AND A.D_KEY = B.D_KEY AND A.PRODUCT_CODE = B.PRODUCT_CODE AND A.D_KEY = "+CommonConstants.D_KEY + " ");
		sb.append(" AND (A.STOCK_OUT_TYPE != "+CommonConstants.DICT_STOCK_OUT_TYPE_UNTREAD + "  OR A.STOCK_OUT_TYPE != "+CommonConstants.DICT_STOCK_OUT_TYPE_OTHER + ") ");
		sb.append(" AND A.DEALER_CODE = "+DealerCode+" ");
		sb.append(" AND ((1 = 1 "+Utility.getDateCond("A","FIRST_STOCK_OUT_DATE",lastStart,lastEnd)+Utility.getDateCond("A","LATEST_STOCK_OUT_DATE",lastStart,lastEnd)+" AND A.LATEST_STOCK_OUT_DATE >= A.LATEST_STOCK_IN_DATE) OR (1 = 1 "+Utility.getDateCond("A","LATEST_STOCK_OUT_DATE",lastStart,lastEnd)+"AND A.LATEST_STOCK_OUT_DATE>=A.LATEST_STOCK_IN_DATE)) ");
		sb.append(" GROUP BY B.BRAND_CODE,B.SERIES_CODE,B.MODEL_CODE UNION ALL ");
		sb.append(" SELECT B.BRAND_CODE,B.SERIES_CODE,B.MODEL_CODE,0 AS NOW_AMOUNT,0 AS SALES_AMOUNT,0 AS LAST_MONTH_SALES,COUNT(A.vin) AS ROAD,0 AS CUSTOMER_H,0 AS CUSTOMER_A,0 AS CUSTOMER_B,A.DEALER_CODE ");
		sb.append(" FROM TT_VS_SHIPPING_NOTIFY A, ");
		sb.append("("+ CommonConstants.VM_VS_PRODUCT +")"+"B");
		sb.append(" WHERE A.DEALER_CODE = B.DEALER_CODE AND A.D_KEY = B.D_KEY AND A.PRODUCT_CODE = B.PRODUCT_CODE AND A.D_KEY = "+CommonConstants.D_KEY + " AND A.VIN ");
		sb.append(" NOT IN ");
		sb.append(" (SELECT VIN FROM TM_VS_STOCK WHERE DEALER_CODE = "+DealerCode+" AND D_KEY = "+CommonConstants.D_KEY + ") ");
		sb.append(" AND A.DEALER_CODE = "+DealerCode+"  ");
		sb.append(" GROUP BY B.BRAND_CODE,B.SERIES_CODE,B.MODEL_CODE ");
		sb.append(" UNION ALL ");
		sb.append(" SELECT D.INTENT_BRAND,D.INTENT_SERIES,D.INTENT_MODEL,0 AS NOW_AMOUNT,0 AS SALES_AMOUNT,0 AS LAST_MONTH_SALES,0 AS ROAD,COUNT(a.customer_no) AS CUSTOMER_H, 0 AS CUSTOMER_A,0 AS CUSTOMER_B,A.DEALER_CODE ");
		sb.append(" FROM tm_potential_customer A,TT_CUSTOMER_INTENT i,TT_CUSTOMER_INTENT_DETAIL d ");
		sb.append(" WHERE A.DEALER_CODE = I.DEALER_CODE AND A.DEALER_CODE = D.DEALER_CODE AND A.CUSTOMER_NO = I.CUSTOMER_NO AND A.INTENT_ID = I.INTENT_ID AND D.INTENT_ID = I.INTENT_ID AND D.IS_MAIN_MODEL = 12781001 AND ");
		sb.append(" A.D_KEY = "+CommonConstants.D_KEY + " AND A.INTENT_LEVEL = "+CommonConstants.DICT_INTENT_LEVEL_H + " AND A.DEALER_CODE = "+DealerCode+" "+Utility.getDateCond("A","CREATED_AT",startDate,endDate) );
		sb.append(" GROUP BY D.INTENT_BRAND,D.INTENT_SERIES,D.INTENT_MODEL ");
		sb.append(" UNION ALL ");
		sb.append(" SELECT D.INTENT_BRAND,D.INTENT_SERIES,D.INTENT_MODEL,0 AS NOW_AMOUNT,0 AS SALES_AMOUNT,0 AS LAST_MONTH_SALES,0 AS ROAD,0 AS CUSTOMER_H,COUNT(a.customer_no) AS CUSTOMER_A,0 AS CUSTOMER_B,A.DEALER_CODE ");
		sb.append(" FROM tm_potential_customer A,TT_CUSTOMER_INTENT i,TT_CUSTOMER_INTENT_DETAIL d ");
		sb.append(" WHERE A.DEALER_CODE = I.DEALER_CODE AND A.DEALER_CODE = D.DEALER_CODE AND A.CUSTOMER_NO = I.CUSTOMER_NO AND A.INTENT_ID = I.INTENT_ID AND D.INTENT_ID = I.INTENT_ID AND D.IS_MAIN_MODEL = 12781001 AND A.D_KEY = "+CommonConstants.D_KEY + " AND A.INTENT_LEVEL = "+CommonConstants.DICT_INTENT_LEVEL_H + " AND A.DEALER_CODE = "+DealerCode+" "+Utility.getDateCond("A","CREATED_AT",startDate,endDate));
		sb.append(" GROUP BY D.INTENT_BRAND,D.INTENT_SERIES,D.INTENT_MODEL ");
		sb.append(" UNION ALL ");
		sb.append(" SELECT D.INTENT_BRAND,D.INTENT_SERIES,D.INTENT_MODEL,0 AS NOW_AMOUNT,0 AS SALES_AMOUNT,0 AS LAST_MONTH_SALES,0 AS ROAD,0 AS CUSTOMER_H,0 AS CUSTOMER_A,COUNT(a.customer_no) AS CUSTOMER_B,A.DEALER_CODE ");
		sb.append(" FROM tm_potential_customer A,TT_CUSTOMER_INTENT i,TT_CUSTOMER_INTENT_DETAIL d ");
		sb.append(" WHERE A.DEALER_CODE = I.DEALER_CODE AND A.DEALER_CODE = D.DEALER_CODE AND A.CUSTOMER_NO = I.CUSTOMER_NO AND A.INTENT_ID = I.INTENT_ID AND D.INTENT_ID = I.INTENT_ID AND D.IS_MAIN_MODEL = 12781001 AND A.D_KEY = "+CommonConstants.D_KEY + " ");
		sb.append(" UNION ALL ");
		sb.append(" SELECT D.INTENT_BRAND,D.INTENT_SERIES,D.INTENT_MODEL,0 AS NOW_AMOUNT,0 AS SALES_AMOUNT,0 AS LAST_MONTH_SALE ,0 AS ROAD,0 AS CUSTOMER_H,0 AS CUSTOMER_A,COUNT(a.customer_no) AS CUSTOMER_B ,A.DEALER_CODE   FROM tm_potential_customer A, TT_CUSTOMER_INTENT i, TT_CUSTOMER_INTENT_DETAIL d  WHERE A.DEALER_CODE = I.DEALER_CODE AND A.DEALER_CODE=D.DEALER_CODE AND A.CUSTOMER_NO=I.CUSTOMER_NO AND A.INTENT_ID=I.INTENT_ID AND D.INTENT_ID=I.INTENT_ID AND D.IS_MAIN_MODEL=12781001 AND A.D_KEY ="+ CommonConstants.D_KEY +" AND A.INTENT_LEVEL="+CommonConstants.DICT_INTENT_LEVEL_B+" AND A.DEALER_CODE= '" + DealerCode + "' " + Utility.getDateCond("A", "CREATED_AT",startDate, endDate)+" GROUP BY D.INTENT_BRAND,D.INTENT_SERIES,D.INTENT_MODEL ");
		sb.append(" ) TA1 GROUP BY TA1.BRAND_CODE,TA1.SERIES_CODE,TA1.MODEL_CODE ");
		sb.append("  )B  LEFT JOIN tm_brand BR ON B.BRAND_CODE = BR.BRAND_CODE AND B.DEALER_CODE = BR.DEALER_CODE LEFT JOIN TM_SERIES se ON B.SERIES_CODE = se.SERIES_CODE  AND br.BRAND_CODE = se.BRAND_CODE AND br.DEALER_CODE = se.DEALER_CODE LEFT JOIN TM_MODEL mo ON B.MODEL_CODE = mo.MODEL_CODE  AND mo.BRAND_CODE = se.BRAND_CODE AND mo.series_code = se.series_code AND se.DEALER_CODE = mo.DEALER_CODE  ");
		
		System.out.println("******************************************");
		System.out.println(sb.toString());
		System.out.println("******************************************");
		List<Object> insuranceSql = new ArrayList<Object>();
		PageInfoDto id = DAOUtil.pageQuery(sb.toString(), insuranceSql);
		return id;
	}
	
	
	
	/**
	 * 导出excel
	 */
	@Override
	public List<Map> queryVehicleInfoStatExport(Map<String, String> queryParam) {
			
		        // 获取日期框中的日期
				String month = queryParam.get("START_DATE");
				try {
					if (month != null && !"".equals(month)) {
						int year = Utility.getInt(month.substring(0, 4));
						int monthdb = Utility.getInt(month.substring(5, 7));
						Calendar call = Calendar.getInstance();
						// monthdb为11,为12
						call.set(year, monthdb, 1);
						Date dateNow = call.getTime();
						// monthdb为11
						call.set(year, monthdb - 1, 1);
						Date dateAgo = call.getTime();
						long daterang = (dateNow.getTime() - dateAgo.getTime());
						long time = 1000 * 3600 * 24; // 当前月的上个月天数
						String endDatep = month + "-" + String.valueOf(daterang / time);
						String startDate = month + "-" + "01";
						String endDate = endDatep;
						//获取经销商代码
						String  DealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
						String lastStart=null;
						String lastEnd=null;
						Calendar call1 = Calendar.getInstance();
						call1.setTime(Utility.getTimeStamp(startDate));
						call1.add(Calendar.MONTH, -1);
						lastStart=new SimpleDateFormat("yyyy-MM-dd").format(call1.getTime()).toString();
						call1.add(Calendar.MONTH, 1);
						call1.add(Calendar.DATE, -1);
						lastEnd=new SimpleDateFormat("yyyy-MM-dd").format(call1.getTime()).toString();
						StringBuilder sb = new StringBuilder();
						sb.append(" SELECT B.*,  BR.BRAND_NAME,SE.SERIES_NAME,MO.MODEL_NAME FROM ( ");
					    sb.append(" SELECT TA1.BRAND_CODE AS BRAND_CODE,TA1.SERIES_CODE AS SERIES_CODE,TA1.MODEL_CODE AS MODEL_CODE,SUM(NOW_AMOUNT) AS NOW_AMOUNT,SUM(SALES_AMOUNT) AS SALES_AMOUNT,SUM(LAST_MONTH_SALES) AS LAST_MONTH_SALES,SUM(ROAD) AS ROAD,SUM(CUSTOMER_H) AS CUSTOMER_H,SUM(CUSTOMER_A) AS CUSTOMER_A,SUM(CUSTOMER_B) AS CUSTOMER_B,DEALER_CODE, ");
						sb.append(" CONCAT( CAST( SUM(NOW_AMOUNT) * 100.00 / ( CASE WHEN SUM(LAST_MONTH_SALES) = 0  THEN 1  ELSE SUM(LAST_MONTH_SALES)  END) AS DECIMAL (10, 2)),'%') AS STORE_RATE ");
						sb.append(" FROM ");
						sb.append(" (SELECT B.BRAND_CODE,B.SERIES_CODE, B.MODEL_CODE, COUNT(A.VIN) AS NOW_AMOUNT,0 AS SALES_AMOUNT, 0 AS LAST_MONTH_SALES,0 AS ROAD, 0 AS CUSTOMER_H,0 AS CUSTOMER_A, 0 AS CUSTOMER_B,A.DEALER_CODE ");
						sb.append(" FROM TM_VS_STOCK A,");
						sb.append("("+ CommonConstants.VM_VS_PRODUCT +")"+"B");
						sb.append(" WHERE A.DEALER_CODE = B.DEALER_CODE AND A.D_KEY = B.D_KEY AND A.PRODUCT_CODE = B.PRODUCT_CODE AND A.D_KEY = "+CommonConstants.D_KEY + " AND (A.STOCK_STATUS = "+CommonConstants.DICT_STORAGE_STATUS_IN_STORAGE + "   ) AND A.DEALER_CODE = "+DealerCode+" ");
						sb.append(" GROUP BY B.BRAND_CODE,B.SERIES_CODE,B.MODEL_CODE ");
						sb.append(" UNION ALL");
						sb.append(" SELECT  B.BRAND_CODE,B.SERIES_CODE,B.MODEL_CODE,0 AS NOW_AMOUNT,COUNT(A.vin) AS SALES_AMOUNT,0 AS LAST_MONTH_SALES,0 AS ROAD,0 AS CUSTOMER_H,0 AS CUSTOMER_A,0 AS CUSTOMER_B,A.DEALER_CODE ");
						sb.append(" FROM TM_VS_STOCK A,");
						sb.append("("+ CommonConstants.VM_VS_PRODUCT +")"+"B");
						sb.append(" WHERE A.DEALER_CODE = B.DEALER_CODE AND A.D_KEY = B.D_KEY AND A.PRODUCT_CODE = B.PRODUCT_CODE AND A.D_KEY = "+CommonConstants.D_KEY + " ");
						sb.append(" AND (A.STOCK_OUT_TYPE != "+CommonConstants.DICT_STOCK_OUT_TYPE_UNTREAD + " OR A.STOCK_OUT_TYPE != "+CommonConstants.DICT_STOCK_OUT_TYPE_OTHER + " ) ");
						sb.append(" AND A.DEALER_CODE = "+DealerCode+"");
						sb.append(" AND ((1 = 1 "+Utility.getDateCond("A","FIRST_STOCK_OUT_DATE",startDate,endDate)+Utility.getDateCond("A","LATEST_STOCK_OUT_DATE",startDate,endDate)+"AND A.LATEST_STOCK_OUT_DATE >= A.LATEST_STOCK_IN_DATE) ");
						sb.append(" OR (1 = 1" +Utility.getDateCond("A","LATEST_STOCK_OUT_DATE",startDate,endDate)+"AND A.LATEST_STOCK_OUT_DATE >= A.LATEST_STOCK_IN_DATE)) ");
						sb.append(" GROUP BY B.BRAND_CODE,B.SERIES_CODE,B.MODEL_CODE ");
						sb.append(" UNION ALL");
						sb.append(" SELECT B.BRAND_CODE,B.SERIES_CODE,B.MODEL_CODE,0 AS NOW_AMOUNT,0 AS SALES_AMOUNT,COUNT(A.vin) AS LAST_MONTH_SALES,0 AS ROAD,0 AS CUSTOMER_H,0 AS CUSTOMER_A,0 AS CUSTOMER_B,A.DEALER_CODE ");
						sb.append(" FROM TM_VS_STOCK A, ");
						sb.append("("+ CommonConstants.VM_VS_PRODUCT +")"+"B");
						sb.append(" WHERE A.DEALER_CODE = B.DEALER_CODE AND A.D_KEY = B.D_KEY AND A.PRODUCT_CODE = B.PRODUCT_CODE AND A.D_KEY = "+CommonConstants.D_KEY + " ");
						sb.append(" AND (A.STOCK_OUT_TYPE != "+CommonConstants.DICT_STOCK_OUT_TYPE_UNTREAD + "  OR A.STOCK_OUT_TYPE != "+CommonConstants.DICT_STOCK_OUT_TYPE_OTHER + ") ");
						sb.append(" AND A.DEALER_CODE = "+DealerCode+" ");
						sb.append(" AND ((1 = 1 "+Utility.getDateCond("A","FIRST_STOCK_OUT_DATE",lastStart,lastEnd)+Utility.getDateCond("A","LATEST_STOCK_OUT_DATE",lastStart,lastEnd)+" AND A.LATEST_STOCK_OUT_DATE >= A.LATEST_STOCK_IN_DATE) OR (1 = 1 "+Utility.getDateCond("A","LATEST_STOCK_OUT_DATE",lastStart,lastEnd)+"AND A.LATEST_STOCK_OUT_DATE>=A.LATEST_STOCK_IN_DATE)) ");
						sb.append(" GROUP BY B.BRAND_CODE,B.SERIES_CODE,B.MODEL_CODE UNION ALL ");
						sb.append(" SELECT B.BRAND_CODE,B.SERIES_CODE,B.MODEL_CODE,0 AS NOW_AMOUNT,0 AS SALES_AMOUNT,0 AS LAST_MONTH_SALES,COUNT(A.vin) AS ROAD,0 AS CUSTOMER_H,0 AS CUSTOMER_A,0 AS CUSTOMER_B,A.DEALER_CODE ");
						sb.append(" FROM TT_VS_SHIPPING_NOTIFY A, ");
						sb.append("("+ CommonConstants.VM_VS_PRODUCT +")"+"B");
						sb.append(" WHERE A.DEALER_CODE = B.DEALER_CODE AND A.D_KEY = B.D_KEY AND A.PRODUCT_CODE = B.PRODUCT_CODE AND A.D_KEY = "+CommonConstants.D_KEY + " AND A.VIN ");
						sb.append(" NOT IN ");
						sb.append(" (SELECT VIN FROM TM_VS_STOCK WHERE DEALER_CODE = "+DealerCode+" AND D_KEY = "+CommonConstants.D_KEY + ") ");
						sb.append(" AND A.DEALER_CODE = "+DealerCode+"  ");
						sb.append(" GROUP BY B.BRAND_CODE,B.SERIES_CODE,B.MODEL_CODE ");
						sb.append(" UNION ALL ");
						sb.append(" SELECT D.INTENT_BRAND,D.INTENT_SERIES,D.INTENT_MODEL,0 AS NOW_AMOUNT,0 AS SALES_AMOUNT,0 AS LAST_MONTH_SALES,0 AS ROAD,COUNT(a.customer_no) AS CUSTOMER_H, 0 AS CUSTOMER_A,0 AS CUSTOMER_B,A.DEALER_CODE ");
						sb.append(" FROM tm_potential_customer A,TT_CUSTOMER_INTENT i,TT_CUSTOMER_INTENT_DETAIL d ");
						sb.append(" WHERE A.DEALER_CODE = I.DEALER_CODE AND A.DEALER_CODE = D.DEALER_CODE AND A.CUSTOMER_NO = I.CUSTOMER_NO AND A.INTENT_ID = I.INTENT_ID AND D.INTENT_ID = I.INTENT_ID AND D.IS_MAIN_MODEL = 12781001 AND ");
						sb.append(" A.D_KEY = "+CommonConstants.D_KEY + " AND A.INTENT_LEVEL = "+CommonConstants.DICT_INTENT_LEVEL_H + " AND A.DEALER_CODE = "+DealerCode+" "+Utility.getDateCond("A","CREATED_AT",startDate,endDate) );
						sb.append(" GROUP BY D.INTENT_BRAND,D.INTENT_SERIES,D.INTENT_MODEL ");
						sb.append(" UNION ALL ");
						sb.append(" SELECT D.INTENT_BRAND,D.INTENT_SERIES,D.INTENT_MODEL,0 AS NOW_AMOUNT,0 AS SALES_AMOUNT,0 AS LAST_MONTH_SALES,0 AS ROAD,0 AS CUSTOMER_H,COUNT(a.customer_no) AS CUSTOMER_A,0 AS CUSTOMER_B,A.DEALER_CODE ");
						sb.append(" FROM tm_potential_customer A,TT_CUSTOMER_INTENT i,TT_CUSTOMER_INTENT_DETAIL d ");
						sb.append(" WHERE A.DEALER_CODE = I.DEALER_CODE AND A.DEALER_CODE = D.DEALER_CODE AND A.CUSTOMER_NO = I.CUSTOMER_NO AND A.INTENT_ID = I.INTENT_ID AND D.INTENT_ID = I.INTENT_ID AND D.IS_MAIN_MODEL = 12781001 AND A.D_KEY = "+CommonConstants.D_KEY + " AND A.INTENT_LEVEL = "+CommonConstants.DICT_INTENT_LEVEL_H + " AND A.DEALER_CODE = "+DealerCode+" "+Utility.getDateCond("A","CREATED_AT",startDate,endDate));
						sb.append(" GROUP BY D.INTENT_BRAND,D.INTENT_SERIES,D.INTENT_MODEL ");
						sb.append(" UNION ALL ");
						sb.append(" SELECT D.INTENT_BRAND,D.INTENT_SERIES,D.INTENT_MODEL,0 AS NOW_AMOUNT,0 AS SALES_AMOUNT,0 AS LAST_MONTH_SALES,0 AS ROAD,0 AS CUSTOMER_H,0 AS CUSTOMER_A,COUNT(a.customer_no) AS CUSTOMER_B,A.DEALER_CODE ");
						sb.append(" FROM tm_potential_customer A,TT_CUSTOMER_INTENT i,TT_CUSTOMER_INTENT_DETAIL d ");
						sb.append(" WHERE A.DEALER_CODE = I.DEALER_CODE AND A.DEALER_CODE = D.DEALER_CODE AND A.CUSTOMER_NO = I.CUSTOMER_NO AND A.INTENT_ID = I.INTENT_ID AND D.INTENT_ID = I.INTENT_ID AND D.IS_MAIN_MODEL = 12781001 AND A.D_KEY = "+CommonConstants.D_KEY + " ");
						sb.append(" UNION ALL ");
						sb.append(" SELECT D.INTENT_BRAND,D.INTENT_SERIES,D.INTENT_MODEL,0 AS NOW_AMOUNT,0 AS SALES_AMOUNT,0 AS LAST_MONTH_SALE ,0 AS ROAD,0 AS CUSTOMER_H,0 AS CUSTOMER_A,COUNT(a.customer_no) AS CUSTOMER_B ,A.DEALER_CODE   FROM tm_potential_customer A, TT_CUSTOMER_INTENT i, TT_CUSTOMER_INTENT_DETAIL d  WHERE A.DEALER_CODE = I.DEALER_CODE AND A.DEALER_CODE=D.DEALER_CODE AND A.CUSTOMER_NO=I.CUSTOMER_NO AND A.INTENT_ID=I.INTENT_ID AND D.INTENT_ID=I.INTENT_ID AND D.IS_MAIN_MODEL=12781001 AND A.D_KEY ="+ CommonConstants.D_KEY +" AND A.INTENT_LEVEL="+CommonConstants.DICT_INTENT_LEVEL_B+" AND A.DEALER_CODE= '" + DealerCode + "' " + Utility.getDateCond("A", "CREATED_AT",startDate, endDate)+" GROUP BY D.INTENT_BRAND,D.INTENT_SERIES,D.INTENT_MODEL ");
						sb.append(" ) TA1 GROUP BY TA1.BRAND_CODE,TA1.SERIES_CODE,TA1.MODEL_CODE ");
						sb.append("  )B  LEFT JOIN tm_brand BR ON B.BRAND_CODE = BR.BRAND_CODE AND B.DEALER_CODE = BR.DEALER_CODE LEFT JOIN TM_SERIES se ON B.SERIES_CODE = se.SERIES_CODE  AND br.BRAND_CODE = se.BRAND_CODE AND br.DEALER_CODE = se.DEALER_CODE LEFT JOIN TM_MODEL mo ON B.MODEL_CODE = mo.MODEL_CODE  AND mo.BRAND_CODE = se.BRAND_CODE AND mo.series_code = se.series_code AND se.DEALER_CODE = mo.DEALER_CODE  ");
						
						List<Object> queryList = new ArrayList<Object>();
						List<Map> resultList = DAOUtil.findAll(sb.toString(), queryList);
						OperateLogDto operateLogDto = new OperateLogDto();
						operateLogDto.setOperateContent("");
						operateLogDto.setOperateType(DictCodeConstants.LOG_CUSTOMER_MANAGEMENT);
						operateLogService.writeOperateLog(operateLogDto);
						return resultList;
					}

				} catch (Exception e) {
					
				}
		return null;
	}
	
}
