package com.yonyou.dms.report.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.DTO.monitor.OperateLogDto;
import com.yonyou.dms.common.service.monitor.OperateLogService;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.DateUtil;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * 整车销售报表
 * @author wangliang
 * @date 2017年1月17日
 */

@SuppressWarnings("rawtypes")
@Service
public class VehicleSalesServiceImpl implements VehicleSalesService{
	  @Autowired
      private OperateLogService operateLogService;
	
	/**
	 * 前台查询页面
	 */
	@Override
	public PageInfoDto queryVehicleSalesChecked(Map<String, String> queryParam) throws ServiceBizException {
		//获取经销商代码
		String  DealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		StringBuffer sb =new StringBuffer("  SELECT B.*,U.USER_NAME,BR.BRAND_NAME,mo.MODEL_NAME,se.SERIES_NAME,C.COLOR_NAME,pa.CONFIG_NAME FROM ( SELECT D.*,E.ORG_NAME FROM ( ");
		sb.append(" SELECT * FROM ");
		sb.append(" (SELECT (COALESCE(A.DIRECTIVE_PRICE, 0) - COALESCE(a.VEHICLE_PRICE, 0)) AS preferential_price,(COALESCE(A.VEHICLE_PRICE, 0) - COALESCE(A.PURCHASE_PRICE, 0)) / (CASE WHEN COALESCE(A.VEHICLE_PRICE, 0) = 0  THEN 1  ELSE VEHICLE_PRICE  END) AS POSS_PROFIT_rate, ");
		sb.append(" A.PAY_MODE,  (CASE WHEN A.OTHER_AMOUNT IS NULL THEN 0 ELSE A.OTHER_AMOUNT END  ) AS OTHER_AMOUNT,A.ORG_CODE,A.ZIP_CODE,A.GENDER,A.PHONE,A.ADDRESS,A.INVOICE_AMOUNT,A.ENGINE_NO, ");
		sb.append(" a.BUSINESS_TYPE, ");
		sb.append(" A.PRODUCT_CODE,A.CUSTOMER_NAME,C.BRAND_CODE,C.SERIES_CODE,C.MODEL_CODE,C.CONFIG_CODE,C.COLOR_CODE,A.VIN,A.ORG_CODE AS SALES_GROUP, A.SOLD_BY,A.SO_NO,A.STOCK_OUT_DATE,A.ORDER_SALES_DATE,A.DEALER_CODE,A.FACTORY_DATE,A.DIRECTIVE_PRICE,a.VEHICLE_PRICE,A.PURCHASE_PRICE, ");
		sb.append(" (COALESCE(a.VEHICLE_PRICE, 0) - COALESCE(A.PURCHASE_PRICE, 0)) AS POSS_PROFIT ");
		sb.append(" FROM (SELECT DISTINCT M.*,Y.ZIP_CODE,Y.GENDER,B.PURCHASE_PRICE, B.ENGINE_NO,B.PRODUCT_CODE,COALESCE(Y.CUSTOMER_NAME, M.CUSTOMER_NAME_OTHER ) AS CUSTOMER_NAME,Y.PHONE,Y.ADDRESS, B.FACTORY_DATE,B.LATEST_STOCK_OUT_DATE AS STOCK_OUT_DATE, B.LATEST_STOCK_OUT_DATE AS ORDER_SALES_DATE,B.DIRECTIVE_PRICE ");
		sb.append(" FROM (SELECT A.SO_NO,A.vin,A.OTHER_AMOUNT,A.VEHICLE_PRICE,A.PAY_MODE, A.BUSINESS_TYPE,Z.ORG_CODE,A.SOLD_BY,A.CUSTOMER_NAME AS CUSTOMER_NAME_OTHER,A.DEALER_CODE, A.INVOICE_AMOUNT ");
		sb.append(" FROM TT_SALES_ORDER A, ");
		sb.append(" (SELECT X.DEALER_CODE, X.USER_ID,X.ORG_CODE FROM TM_USER X,TM_ORGANIZATION Y WHERE X.DEALER_CODE = Y.DEALER_CODE AND X.ORG_CODE = Y.ORG_CODE) Z, ");
		sb.append(" (SELECT  MAX(CREATED_AT) AS CREATED_AT,vin,DEALER_CODE FROM TT_SALES_ORDER WHERE BUSINESS_TYPE = 13001001 AND (SO_STATUS = 13011035 OR SO_STATUS = 13011075) ");
		sb.append(" GROUP BY vin,DEALER_CODE) H WHERE A.DEALER_CODE = Z.DEALER_CODE AND A.SOLD_BY = Z.USER_ID AND A.BUSINESS_TYPE = 13001001 ");
		sb.append(" AND ( SO_STATUS = 13011035 OR SO_STATUS = 13011075) AND A.VIN = H.VIN AND A.CREATED_AT = H.CREATED_AT AND A.DEALER_CODE = H.DEALER_CODE ");
		sb.append(" UNION ALL ");
		sb.append(" SELECT DISTINCT B.SEC_SO_NO AS SO_NO,A.vin,B.OTHER_AMOUNT,A.FETCH_PURCHASE_PRICE AS VEHICLE_PRICE,0 AS PAY_MODE,B.BILL_TYPE AS BUSINESS_TYPE,Z.ORG_CODE,B.SOLD_BY,B.CUSTOMER_NAME AS CUSTOMER_NAME_OTHER,A.DEALER_CODE,A.INVOICE_AMOUNT ");
		sb.append(" FROM ");
		sb.append(" (SELECT  A.*,D.INVOICE_AMOUNT FROM TT_SEC_SALES_ORDER_ITEM A LEFT JOIN TT_SEC_INVOICE D ON D.VIN = A.VIN AND A.DEALER_CODE = D.DEALER_CODE AND D.IS_VALID = 12781001) A, ");
		sb.append(" (SELECT  X.DEALER_CODE,X.USER_ID,X.ORG_CODE FROM TM_USER X,TM_ORGANIZATION Y WHERE X.DEALER_CODE = Y.DEALER_CODE AND X.ORG_CODE = Y.ORG_CODE) Z,TT_SECOND_SALES_ORDER B, ");
		sb.append(" (SELECT MAX(B.CREATED_AT) AS CREATED_AT,VIN, A.DEALER_CODE FROM TT_SEC_SALES_ORDER_ITEM A INNER JOIN TT_SECOND_SALES_ORDER B ON A.DEALER_CODE = B.DEALER_CODE AND A.SEC_SO_NO = B.SEC_SO_NO ");
		sb.append(" WHERE (SO_STATUS = 13011035 OR SO_STATUS = 13011075) AND B.BILL_TYPE = 19001001 GROUP BY VIN,A.DEALER_CODE) H ");
		sb.append(" WHERE B.DEALER_CODE = Z.DEALER_CODE AND B.SOLD_BY = Z.USER_ID AND A.SEC_SO_NO = B.SEC_SO_NO AND B.BILL_TYPE = 19001001 ");
		sb.append(" AND (SO_STATUS = 13011035 OR SO_STATUS = 13011075) AND H.VIN = A.VIN AND H.DEALER_CODE = A.DEALER_CODE AND H.CREATED_AT = B.CREATED_AT) M ");
		sb.append(" INNER JOIN TM_VS_STOCK B ON M.VIN = B.VIN AND M.DEALER_CODE = B.DEALER_CODE AND STOCK_STATUS = 13041001 ");
		sb.append(" LEFT JOIN (SELECT DISTINCT Y.DEALER_CODE,M.VIN,Y.CUSTOMER_NAME,Y.ZIP_CODE,Y.GENDER,Y.CONTACTOR_MOBILE AS PHONE, Y.ADDRESS FROM  tm_vehicle M INNER JOIN TM_CUSTOMER Y ON Y.DEALER_CODE = M.DEALER_CODE AND Y.CUSTOMER_NO = M.CUSTOMER_NO) Y ");
		sb.append(" ON Y.DEALER_CODE = M.DEALER_CODE  AND Y.VIN = M.VIN) A ");
		sb.append(" LEFT JOIN ");
		sb.append(" ("+ CommonConstants.VM_VS_PRODUCT +")"+"C" );
		sb.append(" ON A.PRODUCT_CODE = C.PRODUCT_CODE AND A.DEALER_CODE = C.DEALER_CODE) TA1 ");
		sb.append(" WHERE 1 = 1 AND TA1.DEALER_CODE =  "+DealerCode+" ");
	
		List<Object> whereSql = new ArrayList<Object>();
		if (!StringUtils.isNullOrEmpty(queryParam.get("soldBy"))) {
			sb.append(" and TA1.SOLD_BY = ?");
			whereSql.add(queryParam.get("soldBy"));
		}
		
		if (!StringUtils.isNullOrEmpty(queryParam.get("SALES_GROUP"))) {
			sb.append(" and TA1.SALES_GROUP = ?");
			whereSql.add(queryParam.get("SALES_GROUP"));
		}
		
		if (!StringUtils.isNullOrEmpty(queryParam.get("BRAND_CODE"))) {
			sb.append(" and TA1.BRAND_CODE = ?");
			whereSql.add(queryParam.get("BRAND_CODE"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("SERIES_CODE"))) {
			sb.append(" and TA1.SERIES_CODE = ?");
			whereSql.add(queryParam.get("SERIES_CODE"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("MODEL_CODE"))) {
			sb.append(" and TA1.MODEL_CODE = ?");
			whereSql.add(queryParam.get("MODEL_CODE"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("CONFIG_CODE"))) {
			sb.append(" and TA1.CONFIG_CODE = ?");
			whereSql.add(queryParam.get("CONFIG_CODE"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("COLOR_CODE"))) {
			sb.append(" and TA1.COLOR_CODE = ?");
			whereSql.add(queryParam.get("COLOR_CODE"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
			sb.append("AND TA1.VIN LIKE ?");
			whereSql.add("%" +queryParam.get("vin")+ "%");
		}
		
		if (!StringUtils.isNullOrEmpty(queryParam.get("COLOR_CODE"))) {
			sb.append(" and TA1.COLOR_CODE = ?");
			whereSql.add(queryParam.get("COLOR_CODE"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("lastStockInDateFrom"))) {
			sb.append(" and TA1.STOCK_OUT_DATE >= ?");
			whereSql.add(DateUtil.parseDefaultDate(queryParam.get("lastStockInDateFrom")));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("lastStockInDateTo"))) {
			sb.append(" and TA1.STOCK_OUT_DATE < ?");
			whereSql.add(DateUtil.addOneDay(queryParam.get("lastStockInDateTo")));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("startFactoryDate"))) {
			sb.append(" and TA1.FACTORY_DATE >= ?");
			whereSql.add(DateUtil.parseDefaultDate(queryParam.get("startFactoryDate")));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("endFactoryDate"))) {
			sb.append(" and TA1.FACTORY_DATE < ?");
			whereSql.add(DateUtil.addOneDay(queryParam.get("endFactoryDate")));
		}
		
		sb.append(" ) D LEFT JOIN TM_ORGANIZATION  E ON  D.ORG_CODE = E.ORG_CODE)  B ");
		sb.append(" LEFT JOIN TM_USER U  ON U.USER_ID = B.sold_by LEFT JOIN tm_brand BR ON B.BRAND_CODE = BR.BRAND_CODE AND B.DEALER_CODE=BR.DEALER_CODE LEFT JOIN  TM_SERIES  se   ON   B.SERIES_CODE=se.SERIES_CODE AND br.BRAND_CODE=se.BRAND_CODE AND br.DEALER_CODE=se.DEALER_CODE LEFT  JOIN   TM_MODEL   mo   ON   B.MODEL_CODE=mo.MODEL_CODE AND mo.BRAND_CODE=se.BRAND_CODE AND mo.series_code=se.series_code AND se.DEALER_CODE=mo.DEALER_CODE LEFT JOIN tm_color c ON b.COLOR_CODE = c.COLOR_CODE left  join   tm_configuration pa   on   B.CONFIG_CODE=pa.CONFIG_CODE and pa.brand_code=mo.brand_code and pa.series_code=mo.series_code and pa.model_code=mo.model_code and mo.DEALER_CODE=pa.DEALER_CODE");
		System.out.println("******************************************");
		System.out.println(sb.toString());
		System.out.println("******************************************");
		PageInfoDto id =DAOUtil.pageQuery(sb.toString(), whereSql);
		return id;
	}
	
	
	/**
	 * 整车销售导出
	 */
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Map> queryVehicleSalesExport(Map<String, String> queryParam) throws ServiceBizException {
		
		//获取经销商代码
		String  DealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		StringBuffer sb =new StringBuffer("  SELECT B.*,U.USER_NAME,BR.BRAND_NAME,mo.MODEL_NAME,se.SERIES_NAME,C.COLOR_NAME,pa.CONFIG_NAME FROM ( SELECT D.*,E.ORG_NAME FROM ( ");
		sb.append(" SELECT * FROM ");
		sb.append(" (SELECT (COALESCE(A.DIRECTIVE_PRICE, 0) - COALESCE(a.VEHICLE_PRICE, 0)) AS preferential_price,(COALESCE(A.VEHICLE_PRICE, 0) - COALESCE(A.PURCHASE_PRICE, 0)) / (CASE WHEN COALESCE(A.VEHICLE_PRICE, 0) = 0  THEN 1  ELSE VEHICLE_PRICE  END) AS POSS_PROFIT_rate, ");
		sb.append(" A.PAY_MODE,  (CASE WHEN A.OTHER_AMOUNT IS NULL THEN 0 ELSE A.OTHER_AMOUNT END  ) AS OTHER_AMOUNT,A.ORG_CODE,A.ZIP_CODE,A.GENDER,A.PHONE,A.ADDRESS,A.INVOICE_AMOUNT,A.ENGINE_NO, ");
		sb.append(" (SELECT s.status_desc FROM tm_system_status s WHERE s.status_code = a.business_type) AS BUSINESS_TYPE, ");
		sb.append(" A.PRODUCT_CODE,A.CUSTOMER_NAME,C.BRAND_CODE,C.SERIES_CODE,C.MODEL_CODE,C.CONFIG_CODE,C.COLOR_CODE,A.VIN,A.ORG_CODE AS SALES_GROUP, A.SOLD_BY,A.SO_NO,A.STOCK_OUT_DATE,A.ORDER_SALES_DATE,A.DEALER_CODE,A.FACTORY_DATE,A.DIRECTIVE_PRICE,a.VEHICLE_PRICE,A.PURCHASE_PRICE, ");
		sb.append(" (COALESCE(a.VEHICLE_PRICE, 0) - COALESCE(A.PURCHASE_PRICE, 0)) AS POSS_PROFIT ");
		sb.append(" FROM (SELECT DISTINCT M.*,Y.ZIP_CODE,Y.GENDER,B.PURCHASE_PRICE, B.ENGINE_NO,B.PRODUCT_CODE,COALESCE(Y.CUSTOMER_NAME, M.CUSTOMER_NAME_OTHER ) AS CUSTOMER_NAME,Y.PHONE,Y.ADDRESS, B.FACTORY_DATE,B.LATEST_STOCK_OUT_DATE AS STOCK_OUT_DATE, B.LATEST_STOCK_OUT_DATE AS ORDER_SALES_DATE,B.DIRECTIVE_PRICE ");
		sb.append(" FROM (SELECT A.SO_NO,A.vin,A.OTHER_AMOUNT,A.VEHICLE_PRICE,A.PAY_MODE, A.BUSINESS_TYPE,Z.ORG_CODE,A.SOLD_BY,A.CUSTOMER_NAME AS CUSTOMER_NAME_OTHER,A.DEALER_CODE, A.INVOICE_AMOUNT ");
		sb.append(" FROM TT_SALES_ORDER A, ");
		sb.append(" (SELECT X.DEALER_CODE, X.USER_ID,X.ORG_CODE FROM TM_USER X,TM_ORGANIZATION Y WHERE X.DEALER_CODE = Y.DEALER_CODE AND X.ORG_CODE = Y.ORG_CODE) Z, ");
		sb.append(" (SELECT  MAX(CREATED_AT) AS CREATED_AT,vin,DEALER_CODE FROM TT_SALES_ORDER WHERE BUSINESS_TYPE = 13001001 AND (SO_STATUS = 13011035 OR SO_STATUS = 13011075) ");
		sb.append(" GROUP BY vin,DEALER_CODE) H WHERE A.DEALER_CODE = Z.DEALER_CODE AND A.SOLD_BY = Z.USER_ID AND A.BUSINESS_TYPE = 13001001 ");
		sb.append(" AND ( SO_STATUS = 13011035 OR SO_STATUS = 13011075) AND A.VIN = H.VIN AND A.CREATED_AT = H.CREATED_AT AND A.DEALER_CODE = H.DEALER_CODE ");
		sb.append(" UNION ALL ");
		sb.append(" SELECT DISTINCT B.SEC_SO_NO AS SO_NO,A.vin,B.OTHER_AMOUNT,A.FETCH_PURCHASE_PRICE AS VEHICLE_PRICE,0 AS PAY_MODE,B.BILL_TYPE AS BUSINESS_TYPE,Z.ORG_CODE,B.SOLD_BY,B.CUSTOMER_NAME AS CUSTOMER_NAME_OTHER,A.DEALER_CODE,A.INVOICE_AMOUNT ");
		sb.append(" FROM ");
		sb.append(" (SELECT  A.*,D.INVOICE_AMOUNT FROM TT_SEC_SALES_ORDER_ITEM A LEFT JOIN TT_SEC_INVOICE D ON D.VIN = A.VIN AND A.DEALER_CODE = D.DEALER_CODE AND D.IS_VALID = 12781001) A, ");
		sb.append(" (SELECT  X.DEALER_CODE,X.USER_ID,X.ORG_CODE FROM TM_USER X,TM_ORGANIZATION Y WHERE X.DEALER_CODE = Y.DEALER_CODE AND X.ORG_CODE = Y.ORG_CODE) Z,TT_SECOND_SALES_ORDER B, ");
		sb.append(" (SELECT MAX(B.CREATED_AT) AS CREATED_AT,VIN, A.DEALER_CODE FROM TT_SEC_SALES_ORDER_ITEM A INNER JOIN TT_SECOND_SALES_ORDER B ON A.DEALER_CODE = B.DEALER_CODE AND A.SEC_SO_NO = B.SEC_SO_NO ");
		sb.append(" WHERE (SO_STATUS = 13011035 OR SO_STATUS = 13011075) AND B.BILL_TYPE = 19001001 GROUP BY VIN,A.DEALER_CODE) H ");
		sb.append(" WHERE B.DEALER_CODE = Z.DEALER_CODE AND B.SOLD_BY = Z.USER_ID AND A.SEC_SO_NO = B.SEC_SO_NO AND B.BILL_TYPE = 19001001 ");
		sb.append(" AND (SO_STATUS = 13011035 OR SO_STATUS = 13011075) AND H.VIN = A.VIN AND H.DEALER_CODE = A.DEALER_CODE AND H.CREATED_AT = B.CREATED_AT) M ");
		sb.append(" INNER JOIN TM_VS_STOCK B ON M.VIN = B.VIN AND M.DEALER_CODE = B.DEALER_CODE AND STOCK_STATUS = 13041001 ");
		sb.append(" LEFT JOIN (SELECT DISTINCT Y.DEALER_CODE,M.VIN,Y.CUSTOMER_NAME,Y.ZIP_CODE,Y.GENDER,Y.CONTACTOR_MOBILE AS PHONE, Y.ADDRESS FROM  tm_vehicle M INNER JOIN TM_CUSTOMER Y ON Y.DEALER_CODE = M.DEALER_CODE AND Y.CUSTOMER_NO = M.CUSTOMER_NO) Y ");
		sb.append(" ON Y.DEALER_CODE = M.DEALER_CODE  AND Y.VIN = M.VIN) A ");
		sb.append(" LEFT JOIN ");
		sb.append(" ("+ CommonConstants.VM_VS_PRODUCT +")"+"C" );
		sb.append(" ON A.PRODUCT_CODE = C.PRODUCT_CODE AND A.DEALER_CODE = C.DEALER_CODE) TA1 ");
		sb.append(" WHERE 1 = 1 AND TA1.DEALER_CODE =  "+DealerCode+" ");
	
		List<Object> whereSql = new ArrayList<Object>();
		if (!StringUtils.isNullOrEmpty(queryParam.get("soldBy"))) {
			sb.append(" and TA1.SOLD_BY = ?");
			whereSql.add(queryParam.get("soldBy"));
		}
		
		if (!StringUtils.isNullOrEmpty(queryParam.get("SALES_GROUP"))) {
			sb.append(" and TA1.SALES_GROUP = ?");
			whereSql.add(queryParam.get("SALES_GROUP"));
		}
		
		if (!StringUtils.isNullOrEmpty(queryParam.get("BRAND_CODE"))) {
			sb.append(" and TA1.BRAND_CODE = ?");
			whereSql.add(queryParam.get("BRAND_CODE"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("SERIES_CODE"))) {
			sb.append(" and TA1.SERIES_CODE = ?");
			whereSql.add(queryParam.get("SERIES_CODE"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("MODEL_CODE"))) {
			sb.append(" and TA1.MODEL_CODE = ?");
			whereSql.add(queryParam.get("MODEL_CODE"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("CONFIG_CODE"))) {
			sb.append(" and TA1.CONFIG_CODE = ?");
			whereSql.add(queryParam.get("CONFIG_CODE"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("COLOR_CODE"))) {
			sb.append(" and TA1.COLOR_CODE = ?");
			whereSql.add(queryParam.get("COLOR_CODE"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
			sb.append("AND TA1.VIN LIKE ?");
			whereSql.add("%" +queryParam.get("vin")+ "%");
		}
		
		if (!StringUtils.isNullOrEmpty(queryParam.get("COLOR_CODE"))) {
			sb.append(" and TA1.COLOR_CODE = ?");
			whereSql.add(queryParam.get("COLOR_CODE"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("lastStockInDateFrom"))) {
			sb.append(" and TA1.STOCK_OUT_DATE >= ?");
			whereSql.add(DateUtil.parseDefaultDate(queryParam.get("lastStockInDateFrom")));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("lastStockInDateTo"))) {
			sb.append(" and TA1.STOCK_OUT_DATE < ?");
			whereSql.add(DateUtil.addOneDay(queryParam.get("lastStockInDateTo")));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("startFactoryDate"))) {
			sb.append(" and TA1.FACTORY_DATE >= ?");
			whereSql.add(DateUtil.parseDefaultDate(queryParam.get("startFactoryDate")));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("endFactoryDate"))) {
			sb.append(" and TA1.FACTORY_DATE < ?");
			whereSql.add(DateUtil.addOneDay(queryParam.get("endFactoryDate")));
		}
		
		sb.append(" ) D LEFT JOIN TM_ORGANIZATION  E ON  D.ORG_CODE = E.ORG_CODE)  B ");
		sb.append(" LEFT JOIN TM_USER U  ON U.USER_ID = B.sold_by LEFT JOIN tm_brand BR ON B.BRAND_CODE = BR.BRAND_CODE AND B.DEALER_CODE=BR.DEALER_CODE LEFT JOIN  TM_SERIES  se   ON   B.SERIES_CODE=se.SERIES_CODE AND br.BRAND_CODE=se.BRAND_CODE AND br.DEALER_CODE=se.DEALER_CODE LEFT  JOIN   TM_MODEL   mo   ON   B.MODEL_CODE=mo.MODEL_CODE AND mo.BRAND_CODE=se.BRAND_CODE AND mo.series_code=se.series_code AND se.DEALER_CODE=mo.DEALER_CODE LEFT JOIN tm_color c ON b.COLOR_CODE = c.COLOR_CODE left  join   tm_configuration pa   on   B.CONFIG_CODE=pa.CONFIG_CODE and pa.brand_code=mo.brand_code and pa.series_code=mo.series_code and pa.model_code=mo.model_code and mo.DEALER_CODE=pa.DEALER_CODE");
		System.out.println("******************************************");
		System.out.println(sb.toString());
			
				List<Map> resultList = DAOUtil.findAll(sb.toString(),whereSql); 
				for(Map map : resultList) {
					if(map.get("GENDER") != null && map.get("GENDER")!="") {
						if(Integer.parseInt(map.get("GENDER").toString()) == DictCodeConstants.DICT_GENDER_MAN) {
							map.put("GENDER", "男");
						}else if(Integer.parseInt(map.get("GENDER").toString()) == DictCodeConstants.DICT_GENDER_WOMAN) {
							map.put("GENDER", "女");
						}
					}
					
					if(map.get("PAY_MODE") != null && map.get("PAY_MODE") != "") {
						if(Integer.parseInt(map.get("PAY_MODE").toString()) == DictCodeConstants.DICT_PAY_MODE_LAMP_SUM) {
							map.put("PAY_MODE", "一次性付清");
						}else if(Integer.parseInt(map.get("PAY_MODE").toString()) == DictCodeConstants.DICT_PAY_MODE_INSTALLMENT) {
							map.put("PAY_MODE", "分期付款");
						}	
					}
					
					if(map.get("BUSINESS_TYPE") != null && map.get("BUSINESS_TYPE") != "") {
						if(Integer.parseInt(map.get("BUSINESS_TYPE").toString()) == DictCodeConstants.BUSINESS_TYPE_PJXS) {
							map.put("BUSINESS_TYPE", "配件销售");
						}else if(Integer.parseInt(map.get("BUSINESS_TYPE").toString()) == DictCodeConstants.BUSINESS_TYPE_CGRK) {
							map.put("BUSINESS_TYPE", "采购入库");
						}else if(Integer.parseInt(map.get("BUSINESS_TYPE").toString()) == DictCodeConstants.BUSINESS_TYPE_DBRK) {
							map.put("BUSINESS_TYPE", "调拨入库");
						}else if(Integer.parseInt(map.get("BUSINESS_TYPE").toString()) == DictCodeConstants.BUSINESS_TYPE_LWRK) {
							map.put("BUSINESS_TYPE", "例外入库");
						}else if(Integer.parseInt(map.get("BUSINESS_TYPE").toString()) == DictCodeConstants.BUSINESS_TYPE_WXLL) {
							map.put("BUSINESS_TYPE", "维修领料");
						}else if(Integer.parseInt(map.get("BUSINESS_TYPE").toString()) == DictCodeConstants.BUSINESS_TYPE_DBCK) {
							map.put("BUSINESS_TYPE", "调拨出库");
						}else if(Integer.parseInt(map.get("BUSINESS_TYPE").toString()) == DictCodeConstants.BUSINESS_TYPE_LWCK) {
							map.put("BUSINESS_TYPE", "例外入库");
						}
						
					}
				}
				OperateLogDto operateLogDto = new OperateLogDto();
				operateLogDto.setOperateContent("整车销售导出");
				operateLogDto.setOperateType(DictCodeConstants.LOG_CUSTOMER_MANAGEMENT);
				operateLogService.writeOperateLog(operateLogDto);
				return resultList;
	}
  
}
