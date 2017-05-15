package com.yonyou.dms.report.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;

@Service
public class SaleReportCarStoresServiceImpl implements SaleReportCarStoresService {

	/**
	 * 查询全部的信息
	 * 
	 * @param queryParam
	 * @return
	 */
	@Override
	public PageInfoDto querySaleReportCarStoresInfo(Map<String, String> queryParam)
			throws ServiceBizException, SQLException {
		List<Object> queryList = new ArrayList<Object>();
		StringBuffer sqlstorage = new StringBuffer("");
		StringBuffer sql = new StringBuffer("");
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		StringBuffer str = new StringBuffer("");
		StringBuffer sqltmp = new StringBuffer("");
		sqltmp.append(
				" SELECT B.PRODUCT_NAME,A.PURCHASE_PRICE,A.DEALER_CODE,A.D_KEY,B.BRAND_CODE,B.SERIES_CODE,B.MODEL_CODE,B.CONFIG_CODE,");

		sqltmp.append(
				"B.COLOR_CODE, A.PRODUCT_CODE,A.MANUFACTURE_DATE, A.VS_PURCHASE_DATE AS PURCHASE_DATE,A.VIN, '' as  FIRST_STOCK_IN_DATE, '' as  LATEST_STOCK_IN_DATE, ");

		sqltmp.append(
				" 0 AS STOCK_TIME, '' as  STORAGE_CODE,0 as  DISPATCHED_STATUS,0 as STOCK_STATUS,0 as MAR_STATUS,  ");

		sqltmp.append(
				" A.KEY_NUMBER,A.HAS_CERTIFICATE,A.CERTIFICATE_NUMBER,0 as IS_VIP,A.OEM_TAG,0 as IS_SECONDHAND, 0 as IS_TEST_DRIVE_CAR,A.IS_CONSIGNED, ");

		sqltmp.append(" 0 as IS_PROMOTION,0 as IS_PURCHASE_RETURN   FROM TT_VS_SHIPPING_NOTIFY A  ");

		sqltmp.append(" LEFT JOIN ( " + CommonConstants.VM_VS_PRODUCT + " ) B ");

		sqltmp.append(" ON A.DEALER_CODE=B.DEALER_CODE AND A.D_KEY=B.D_KEY AND A.PRODUCT_CODE=B.PRODUCT_CODE   ");

		sqltmp.append(" WHERE A.DEALER_CODE='" + FrameworkUtil.getLoginInfo().getDealerCode() + "'");

		sqltmp.append(
				" AND A.D_KEY=0 AND  NOT EXISTS (SELECT VIN FROM TM_VS_STOCK B WHERE  A.VIN=B.VIN AND B.DEALER_CODE='");

		sqltmp.append(FrameworkUtil.getLoginInfo().getDealerCode() + "' " + " AND B.D_KEY=" + CommonConstants.D_KEY);

		sqltmp.append(" )   ");
		sqlstorage.append(
				" SELECT B.PRODUCT_NAME,A.PURCHASE_PRICE,A.DEALER_CODE,A.D_KEY,B.BRAND_CODE,B.SERIES_CODE,B.MODEL_CODE,B.CONFIG_CODE,COALESCE(A.COLOR_CODE,B.COLOR_CODE) AS COLOR_CODE,A.PRODUCT_CODE,A.MANUFACTURE_DATE, A.VS_PURCHASE_DATE AS PURCHASE_DATE,");

		sqlstorage.append("A.VIN ," + " A.FIRST_STOCK_IN_DATE AS FIRST_STOCK_IN_DATE , ");

		sqlstorage.append(" A.LATEST_STOCK_IN_DATE AS LATEST_STOCK_IN_DATE , " + " CASE WHEN A.STOCK_STATUS=(");

		sqlstorage.append(DictCodeConstants.DICT_STORAGE_STATUS_IN_STORAGE + ") THEN ");// 在库

		sqlstorage.append(" TIMESTAMPDIFF(DAY,A.LATEST_STOCK_IN_DATE,NOW())  " + " ELSE ");// 在库

		sqlstorage.append(" TIMESTAMPDIFF(DAY,A.LATEST_STOCK_IN_DATE,A.LATEST_STOCK_OUT_DATE)  " + " END ");// 在库

		sqlstorage.append(" STOCK_TIME,A.STORAGE_CODE ,");

		sqlstorage.append(
				" A.DISPATCHED_STATUS,A.STOCK_STATUS,A.MAR_STATUS,A.KEY_NUMBER,A.HAS_CERTIFICATE,A.CERTIFICATE_NUMBER,A.IS_VIP,A.OEM_TAG,A.IS_SECONDHAND, ");

		sqlstorage.append(" A.IS_TEST_DRIVE_CAR,A.IS_CONSIGNED,A.IS_PROMOTION,A.IS_PURCHASE_RETURN ");

		sqlstorage.append(" FROM TM_VS_STOCK A,( " + CommonConstants.VM_VS_PRODUCT + " ) " + "B");

		sqlstorage.append(
				" WHERE A.DEALER_CODE = B.DEALER_CODE AND A.D_KEY = B.D_KEY AND A.PRODUCT_CODE = B.PRODUCT_CODE ");

		sqlstorage.append(" AND A.D_KEY = " + CommonConstants.D_KEY + " " + " AND A.DEALER_CODE ='");

sqlstorage.append( FrameworkUtil.getLoginInfo().getDealerCode() + "'");

		String rate = " ";
		System.out.println("*******************");
		sql.append(" SELECT B.*,S.STORAGE_NAME,O.ORG_NAME,U.USER_NAME FROM ( ");
		System.out.println("*******************");
		sql.append(" SELECT round(TA1.PURCHASE_PRICE/(1+" + Utility.getDouble(rate) );// String转为double类型方法

		sql.append( "),2) AS NOPurchasePrice ,TA1.*,tb.BRAND_NAME, ts.SERIES_NAME,tm.MODEL_NAME, tc.CONFIG_NAME,tr.COLOR_NAME ,TA2.SOLD_BY,TA2.DELIVERING_DATE,TA2.INVOICE_DATE,TA2.DISPATCHED_DATE,TA2.CUSTOMER_NAME,TA3.TRANSFER_DATE,");
		// 出库
		sql.append(
				" TA4.SD_NO AS OUT_STOCK_NO,TA4.STOCK_OUT_TYPE AS OUT_STOCK_TYPE,TA4.SHEET_created_at AS OUT_STOCK_DATE,TA4.ORG_CODE AS ORG_CODE,");

		sql.append(" TA4.CUSTOMER_NAME AS CUSTOMERname,TA4.VENDOR_NAME AS VENDOR_NAME,");
		// 入库
		sql.append(" TA5.DIRECTIVE_PRICE AS DIRECT_PRICE,TA5.SE_NO AS IN_STOCK_NO,");

		sql.append(
				"TA5.STORAGE_POSITION_CODE,TA5.ENGINE_NO,TA5.ADDITIONAL_COST,TA5.FACTORY_DATE,TA5.PO_NO,TA5.DISCHARGE_STANDARD,TA5.PRODUCTING_AREA,");

		sql.append(
				"TA5.INSPECTION_CONSIGNED,TA5.IS_DIRECT,TA5.SHIPPER_LICENSE,TA5.DELIVERYMAN_NAME,TA5.DELIVERYMAN_PHONE,TA5.SHIPPER_NAME,TA5.SHIPPING_DATE,");

		sql.append("TA5.SHIPPING_ADDRESS,TA5.CONSIGNER_CODE,TA5.CONSIGNER_NAME");

		sql.append(" FROM ");

		if (!StringUtils.isNullOrEmpty(queryParam.get("stockStatus"))

				&& queryParam.get("stockStatus").trim().length() > 0 
				&& !queryParam.get("stockStatus").equals("0")
				&& queryParam.get("stockStatus").trim().equals(DictCodeConstants.DICT_STORAGE_STATUS_ON_WAY+" ")) {
			str = sqltmp;
		} else {
			if (!StringUtils.isNullOrEmpty(queryParam.get("stockStatus"))
					&& queryParam.get("stockStatus").trim().length() > 0) {
				str = sqlstorage;
			} else {
				str.append(sqlstorage).append(" UNION ALL ").append(sqltmp);
			}
		}
		sql.append(" (select MM.* from ( " + str + ") MM where 1=1 ");
//		sql.append(Utility.getLikeCond("MM", "VIN", queryParam.get("vin"), "AND"));
		sql.append(Utility.getintCond("MM", "MAR_STATUS", queryParam.get("marStatus")));
		sql.append(Utility.getintCond("MM", "DISPATCHED_STATUS", queryParam.get("dispatchStatus")));		
		if (!StringUtils.isNullOrEmpty(queryParam.get("stockStatus"))
				&& queryParam.get("stockStatus").trim().length() > 0
				&& !queryParam.get("stockStatus").trim().equals("0")
				&& !queryParam.get("stockStatus").trim().equals(DictCodeConstants.DICT_STORAGE_STATUS_ON_WAY)) {
			sql.append(Utility.getintCond("MM", "STORAGE_CODE", queryParam.get("storage_code")));

		}
		sql.append(Utility.getStrCond("MM", "STORAGE_CODE", queryParam.get("storage_code")));
		sql.append(") TA1");

		sql.append(
				"  LEFT JOIN ( SELECT b.brand_code,b.brand_name,b.dealer_code FROM tm_brand b ) tb ON TA1.DEALER_CODE=tb.DEALER_CODE AND TA1.BRAND_CODE=Tb.BRAND_CODE ");

//		sql.append(
//				" LEFT JOIN( SELECT s.series_code,s.series_name,s.dealer_code FROM tm_series s ) ts ON ta1.TA1.DEALER_CODE=ts.DEALER_CODE AND TA1.SERIES_CODE=TS.SERIES_CODE ");

		sql.append(" LEFT JOIN (SELECT s.series_code,s.series_name,s.dealer_code,s.BRAND_CODE FROM tm_series s) ts  ON TA1.DEALER_CODE = ts.DEALER_CODE  AND TA1.SERIES_CODE = TS.SERIES_CODE  AND ts.brand_code=tb.brand_code");
//		sql.append(
//				" LEFT JOIN( SELECT m.DEALER_CODE,m.MODEL_NAME,m.MODEL_CODE FROM tm_model m ) tm ON ta1.TA1.DEALER_CODE=tm.DEALER_CODE AND TA1.MODEL_CODE=TM.MODEL_CODE ");

		sql.append(" LEFT JOIN  (SELECT  m.DEALER_CODE, m.MODEL_NAME, m.MODEL_CODE, m.SERIES_CODE  FROM tm_model m) tm  ON ta1.TA1.DEALER_CODE = tm.DEALER_CODE  AND TA1.MODEL_CODE = TM.MODEL_CODE  AND ts.brand_code=tb.brand_code AND tm.series_code=ts.series_code");
//		sql.append(
//				" LEFT JOIN( SELECT c.DEALER_CODE,c.CONFIG_NAME,c.CONFIG_CODE FROM TM_CONFIGURATION c ) tc ON ta1.TA1.DEALER_CODE=tc.DEALER_CODE AND TA1.CONFIG_CODE=Tc.CONFIG_CODE ");

		sql.append(" LEFT JOIN  (SELECT  c.DEALER_CODE, c.CONFIG_NAME, c.CONFIG_CODE, c.MODEL_CODE FROM TM_CONFIGURATION c) tc  ON ta1.TA1.DEALER_CODE = tc.DEALER_CODE  AND TA1.CONFIG_CODE = Tc.CONFIG_CODE  and ts.brand_code=tb.brand_code  and tm.series_code=ts.series_code and tc.model_code=tm.model_code");
		sql.append(
				"  LEFT JOIN( SELECT r.DEALER_CODE,r.COLOR_NAME,r.COLOR_CODE FROM TM_COLOR r) tr ON ta1.TA1.DEALER_CODE=tr.DEALER_CODE AND TA1.COLOR_CODE=tr.COLOR_CODE ");

		sql.append(" LEFT JOIN ");

		// 查询订单(一般订单，调拨订单方式为本地交车的以及受托托交车订单，并且订单号不在已经完成的退回单里)
		sql.append(
				"( SELECT C.SOLD_BY,C.DELIVERING_DATE,C.INVOICE_DATE,C.DISPATCHED_DATE,C.VIN,C.DEALER_CODE,C.D_KEY,C.CUSTOMER_NAME");

		sql.append(" FROM TT_SALES_ORDER  C ");

		sql.append(" where 1=1 " + " AND SO_STATUS NOT IN (");

		sql.append(DictCodeConstants.DICT_SO_STATUS_HAVE_CANCEL + ", ");

		sql.append(DictCodeConstants.DICT_SO_STATUS_HAVE_UNTREAD + ", ");

		sql.append(DictCodeConstants.DICT_SO_STATUS_WAIT_UNTREAT_IN_STOCK + ", ");

		sql.append(DictCodeConstants.DICT_SO_STATUS_UNTREAT_HAVE_IN_STOCK + ")");

		sql.append("AND C.BUSINESS_TYPE != ");

		sql.append(DictCodeConstants.DICT_SO_TYPE_SERVICE);// 销售业务类型

		sql.append(" AND C.BUSINESS_TYPE != ");

		sql.append(DictCodeConstants.DICT_SO_TYPE_RERURN);// 销售退回

		sql.append(" AND C.DELIVERY_MODE = ");

		sql.append(DictCodeConstants.DICT_DELIVERY_MODE_LOCAL);// 交车方式

		sql.append(" AND C.D_KEY = " + CommonConstants.D_KEY);

		// sql.append(" AND C.DEALER_CODE = '" +
		// FrameworkUtil.getLoginInfo().getDealerCode() + "'");

		sql.append(Utility.getStrCond("C", "DEALER_CODE", dealerCode));

		sql.append(") TA2");

		sql.append(" ON TA1.VIN = TA2.VIN AND TA1.DEALER_CODE = TA2.DEALER_CODE AND TA1.D_KEY = TA2.D_KEY ");

		sql.append(" LEFT JOIN ");

		// 查询已经入账的VIN的移库日期

		sql.append(" ( SELECT MAX(FINISHED_DATE)AS TRANSFER_DATE,VIN,DEALER_CODE,D_KEY ");

		sql.append(" FROM TT_VS_TRANSFER_ITEM tr" + " WHERE IS_FINISHED = " + DictCodeConstants.DICT_IS_YES);

		sql.append(" AND D_KEY = " + CommonConstants.D_KEY);

		sql.append(" AND tr.DEALER_CODE = '" + FrameworkUtil.getLoginInfo().getDealerCode() + "' ");

		sql.append(Utility.getStrCond("", "DEALER_CODE", dealerCode));

		sql.append(" GROUP BY VIN,DEALER_CODE,D_KEY ");

		sql.append(" ) TA3 ");

		sql.append(" ON TA1.VIN = TA3.VIN AND TA1.DEALER_CODE = TA3.DEALER_CODE AND TA1.D_KEY = TA3.D_KEY ");

		sql.append(" LEFT JOIN ");

		sql.append(
				" (SELECT DISTINCT M.DEALER_CODE,M.d_key,M.SD_NO,M.STOCK_OUT_TYPE,M.LATEST_STOCK_OUT_DATE as SHEET_CREATED_AT,");

		sql.append(" B.ORG_CODE, M.VIN,b.user_id,TM_CUSTOMER.CUSTOMER_NAME,M.VENDOR_NAME ");

		sql.append(" FROM TM_VS_STOCK M ");

		sql.append(" LEFT JOIN  (select A.vin,Z.ORG_CODE,z.user_id,A.DEALER_CODE from TT_SALES_ORDER  A, ");

		sql.append(" (SELECT X.DEALER_CODE,X.USER_ID,X.ORG_CODE  FROM TM_USER X,TM_ORGANIZATION Y WHERE ");

		sql.append(" X.DEALER_CODE=Y.DEALER_CODE AND X.ORG_CODE=Y.ORG_CODE)Z where A.DEALER_CODE=Z.DEALER_CODE ");

		sql.append(
				" AND A.SOLD_BY=Z.USER_ID  AND A.BUSINESS_TYPE=13001001 AND ( SO_STATUS = 13011035 or SO_STATUS = 13011075) ");

		sql.append(" union all  select A.vin,Z.ORG_CODE,z.user_id,A.DEALER_CODE  from  TT_SEC_SALES_ORDER_ITEM A, ");

		sql.append(
				" (SELECT X.DEALER_CODE,X.USER_ID, X.ORG_CODE FROM TM_USER X,TM_ORGANIZATION Y  WHERE X.DEALER_CODE=Y.DEALER_CODE ");

		sql.append(
				" AND X.ORG_CODE=Y.ORG_CODE)Z,  TT_SECOND_SALES_ORDER B, (SELECT MAX(B.created_at) AS created_at,VIN,A.DEALER_CODE ");

		sql.append(" FROM TT_SEC_SALES_ORDER_ITEM A  ");

		sql.append(
				" INNER JOIN TT_SECOND_SALES_ORDER B ON A.DEALER_CODE=B.DEALER_CODE AND A.SEC_SO_NO=B.SEC_SO_NO WHERE ");

		sql.append(
				" (SO_STATUS = 13011035 or SO_STATUS = 13011075) and B.BILL_TYPE=19001001 GROUP BY VIN,A.DEALER_CODE) H ");

		sql.append(" where B.DEALER_CODE=Z.DEALER_CODE AND B.SOLD_BY=Z.USER_ID  and A.SEC_SO_NO=B.SEC_SO_NO ");

		sql.append(
				" and B.BILL_TYPE=19001001 AND (SO_STATUS = 13011035 or SO_STATUS = 13011075) AND H.VIN=A.VIN AND H.DEALER_CODE=A.DEALER_CODE ");

		sql.append(" AND H.created_at=B.created_at )B   on m.VIN=B.VIN AND m.DEALER_CODE=B.DEALER_CODE ");

		sql.append(" LEFT JOIN (" + CommonConstants.VM_VEHICLE + ") E ");

		sql.append(" on E.VIN=m.VIN AND E.DEALER_CODE=M.DEALER_CODE ");

		sql.append(
				" LEFT JOIN TM_CUSTOMER ON TM_CUSTOMER.customer_no=E.customer_no AND TM_CUSTOMER.DEALER_CODE=E.DEALER_CODE)TA4 ");

		sql.append(" ON TA1.VIN=TA4.VIN AND TA1.DEALER_CODE=TA4.DEALER_CODE AND TA1.D_KEY=TA4.D_KEY ");

		// 入库
		sql.append(" LEFT JOIN ");
		sql.append(
				" (select distinct a.* ,b.DIRECTIVE_PRICE,b.brand_code,b.series_code,b.model_code,c.stock_in_type,c.LATEST_STOCK_IN_DATE from TT_VS_STOCK_ENTRY_ITEM a ");

		sql.append("left join (" + CommonConstants.VM_VS_PRODUCT + ") b ");

		sql.append("on a.DEALER_CODE=b.DEALER_CODE and a.d_key=b.d_key and a.product_code=b.product_code ");

		sql.append(
				" inner join (select max(created_at) as created_at,vin,DEALER_CODE from TT_VS_STOCK_ENTRY_ITEM group by vin,DEALER_CODE) d");

		sql.append(" on d.vin=a.vin and a.DEALER_CODE=d.DEALER_CODE  and a.created_at=d.created_at ");

		sql.append("inner join TM_VS_STOCK c on a.DEALER_CODE=c.DEALER_CODE and a.vin=c.vin ");

		sql.append(" where a.DEALER_CODE='" + FrameworkUtil.getLoginInfo().getDealerCode() + "' ");

		sql.append(")TA5" + " ON TA1.VIN=TA5.VIN AND TA1.DEALER_CODE=TA5.DEALER_CODE AND TA1.D_KEY=TA5.D_KEY ");

		sql.append(" where 1=1 ");

		this.setWhere(sql, queryParam, queryList);

		// sql.append(Utility.getDateCond("TA5", "LATEST_STOCK_IN_DATE",
		// queryParam.get("beginDate"),
		// queryParam.get("stockDateTo")));

		sql.append(" ORDER BY TA1.MODEL_CODE");
		System.out.println("*******************");
		sql.append("  ) B LEFT JOIN TM_STORAGE S ON B.STORAGE_CODE = S.STORAGE_CODE LEFT JOIN TM_ORGANIZATION O  ON O.ORG_CODE = B.ORG_CODE  LEFT JOIN TM_USER U ON U.USER_ID = B.SOLD_BY ");
		System.out.println("*******************");
		
		
		
		System.out.println("*************************");
		System.out.println(sql.toString());
		System.out.println("*************************");
		PageInfoDto pageInfoDto = DAOUtil.pageQuery(sql.toString(), queryList);
		return pageInfoDto;

	}

	/**
	 * 查询条件设置
	 * 
	 * @param sql
	 * @param queryParam
	 * @param queryList
	 */
	private void setWhere(StringBuffer sql, Map<String, String> queryParam, List<Object> queryList) {
		// 出库查询条件
		if (!StringUtils.isNullOrEmpty(queryParam.get("stockNo"))) {
			sql.append(" AND TA4.SD_NO like ? ");
			queryList.add("%" + queryParam.get("stockNo") + "%");
		}

		if (!StringUtils.isNullOrEmpty(queryParam.get("outStockType"))) {
			sql.append(" AND TA4.STOCK_OUT_TYPE= ? ");
			queryList.add(queryParam.get("outStockType"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("customerName"))) {
			sql.append(" AND TA4.CUSTOMER_NAME like ? ");
			queryList.add("%" + queryParam.get("customerName") + "%");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("orgCode"))) {
			sql.append(" AND TA4.ORG_CODE= ? ");
			queryList.add(queryParam.get("orgCode"));
		}
		//库存状态
		if (!StringUtils.isNullOrEmpty(queryParam.get("stockStatus"))) {
			sql.append(" AND TA1.STOCK_STATUS= ? ");
			queryList.add(queryParam.get("stockStatus"));
		}
		//质损状态
		if (!StringUtils.isNullOrEmpty(queryParam.get("marStatus"))) {
			sql.append(" AND TA1.MAR_STATUS= ? ");
			queryList.add(queryParam.get("marStatus"));
		}
		//配车状态
		if (!StringUtils.isNullOrEmpty(queryParam.get("dispatchStatus"))) {
			sql.append(" AND TA1.DISPATCHED_STATUS= ? ");
			queryList.add(queryParam.get("dispatchStatus"));
		}
		
		if (!StringUtils.isNullOrEmpty(queryParam.get("stockDateFrom"))) {
			sql.append(" AND TA4.SHEET_created_at >= ?");
			queryList.add(queryParam.get("stockDateFrom"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("stockDate"))) {
			sql.append(" AND TA4.SHEET_created_at <= ?");
			queryList.add(queryParam.get("stockDate"));
		}

		// 入库查询条件

		if (!StringUtils.isNullOrEmpty(queryParam.get("brandCode"))) {
			sql.append(" and TA1.brand_code= ? ");
			queryList.add(queryParam.get("brandCode"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("seriesCode"))) {
			sql.append(" and TA1.series_code like ? ");
			queryList.add("%"+queryParam.get("seriesCode")+"%");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("modelCode"))) {
			sql.append(" and TA1.model_code= ? ");
			queryList.add(queryParam.get("modelCode"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
			sql.append(" and TA1.vin like ? ");
			queryList.add("%" + queryParam.get("vin") + "%");
		}
		//库存状态
		if (!StringUtils.isNullOrEmpty(queryParam.get("stockStatus"))) {
			sql.append(" AND TA1.STOCK_STATUS= ? ");
			queryList.add(queryParam.get("stockStatus"));
		}
		//质损状态
		if (!StringUtils.isNullOrEmpty(queryParam.get("marStatus"))) {
			sql.append(" AND TA1.MAR_STATUS= ? ");
			queryList.add(queryParam.get("marStatus"));
		}
		//配车状态
		if (!StringUtils.isNullOrEmpty(queryParam.get("dispatchStatus"))) {
			sql.append(" AND TA1.DISPATCHED_STATUS= ? ");
			queryList.add(queryParam.get("dispatchStatus"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("isInStock"))) {
			sql.append(" and TA5.is_finished= ? ");
			queryList.add(queryParam.get("isInStock"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("inType"))) {
			sql.append(" and TA5.stock_in_type= ?");
			queryList.add(queryParam.get("inType"));
		}

		if (!StringUtils.isNullOrEmpty(queryParam.get("beginDate"))) {
			sql.append(" and TA5.LATEST_STOCK_IN_DATE >= ? ");
			queryList.add(queryParam.get("beginDate"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("stockDateTo"))) {
			sql.append(" and TA5.LATEST_STOCK_IN_DATE <= ? ");
			queryList.add(queryParam.get("stockDateTo"));
		}
	}

	/**
	 * 车辆明细报表导出
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Map> exportSaleReportCarStores(Map<String, String> queryParam)
			throws ServiceBizException, SQLException {
		List<Object> queryList = new ArrayList<Object>();
		StringBuffer sqlstorage = new StringBuffer("");
		StringBuffer sql = new StringBuffer("");
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		StringBuffer str = new StringBuffer("");
		StringBuffer sqltmp = new StringBuffer("");

		sqltmp.append(
				" SELECT B.PRODUCT_NAME,A.PURCHASE_PRICE,A.DEALER_CODE,A.D_KEY,B.BRAND_CODE,B.SERIES_CODE,B.MODEL_CODE,B.CONFIG_CODE,");

		sqltmp.append(
				"B.COLOR_CODE, A.PRODUCT_CODE,A.MANUFACTURE_DATE, A.VS_PURCHASE_DATE AS PURCHASE_DATE,A.VIN, '' as  FIRST_STOCK_IN_DATE, '' as  LATEST_STOCK_IN_DATE, ");

		sqltmp.append(
				" 0 AS STOCK_TIME, '' as  STORAGE_CODE,0 as DISPATCHED_STATUS,0 as STOCK_STATUS,0 as MAR_STATUS,  ");

		sqltmp.append(
				" A.KEY_NUMBER,A.HAS_CERTIFICATE,A.CERTIFICATE_NUMBER,0 as IS_VIP,A.OEM_TAG,0 as IS_SECONDHAND, 0 as IS_TEST_DRIVE_CAR,A.IS_CONSIGNED, ");

		sqltmp.append(" 0 as IS_PROMOTION,0 as IS_PURCHASE_RETURN  FROM TT_VS_SHIPPING_NOTIFY A  ");

		sqltmp.append(" LEFT JOIN ( " + CommonConstants.VM_VS_PRODUCT + " ) B ");

		sqltmp.append(" ON A.DEALER_CODE=B.DEALER_CODE AND A.D_KEY=B.D_KEY AND A.PRODUCT_CODE=B.PRODUCT_CODE   ");

		sqltmp.append(" WHERE A.DEALER_CODE='" + FrameworkUtil.getLoginInfo().getDealerCode() + "'");

		sqltmp.append(
				" AND A.D_KEY=0 AND  NOT EXISTS (SELECT VIN FROM TM_VS_STOCK B WHERE  A.VIN=B.VIN AND B.DEALER_CODE='");

		sqltmp.append(FrameworkUtil.getLoginInfo().getDealerCode() + "' " + " AND B.D_KEY=" + CommonConstants.D_KEY);

		sqltmp.append(" )   ");
		sqlstorage.append(
				" SELECT B.PRODUCT_NAME,A.PURCHASE_PRICE,A.DEALER_CODE,A.D_KEY,B.BRAND_CODE,B.SERIES_CODE,B.MODEL_CODE,B.CONFIG_CODE,COALESCE(A.COLOR_CODE,B.COLOR_CODE) AS COLOR_CODE,A.PRODUCT_CODE,A.MANUFACTURE_DATE, A.VS_PURCHASE_DATE AS PURCHASE_DATE,");

		sqlstorage.append("A.VIN ," + " A.FIRST_STOCK_IN_DATE AS FIRST_STOCK_IN_DATE , ");

		sqlstorage.append(" A.LATEST_STOCK_IN_DATE AS LATEST_STOCK_IN_DATE , " + " CASE WHEN A.STOCK_STATUS=(");

		sqlstorage.append(DictCodeConstants.DICT_STORAGE_STATUS_IN_STORAGE + ") THEN ");// 在库

		sqlstorage.append(" TIMESTAMPDIFF(DAY,A.LATEST_STOCK_IN_DATE,NOW())  " + " ELSE ");// 在库

		sqlstorage.append(" TIMESTAMPDIFF(DAY,A.LATEST_STOCK_IN_DATE,A.LATEST_STOCK_OUT_DATE)  " + " END ");// 在库

		sqlstorage.append(" STOCK_TIME,A.STORAGE_CODE ,");

		sqlstorage.append(
				" A.DISPATCHED_STATUS,A.STOCK_STATUS,A.MAR_STATUS,A.KEY_NUMBER,A.HAS_CERTIFICATE,A.CERTIFICATE_NUMBER,A.IS_VIP,A.OEM_TAG,A.IS_SECONDHAND, ");

		sqlstorage.append(" A.IS_TEST_DRIVE_CAR,A.IS_CONSIGNED,A.IS_PROMOTION,A.IS_PURCHASE_RETURN ");

		sqlstorage.append(" FROM TM_VS_STOCK A,( " + CommonConstants.VM_VS_PRODUCT + " ) " + "B");

		sqlstorage.append(
				" WHERE A.DEALER_CODE = B.DEALER_CODE AND A.D_KEY = B.D_KEY AND A.PRODUCT_CODE = B.PRODUCT_CODE ");

		sqlstorage.append(" AND A.D_KEY = " + CommonConstants.D_KEY + " " + " AND A.DEALER_CODE ='");

		sqlstorage.append(FrameworkUtil.getLoginInfo().getDealerCode() + "'");

		String rate = " ";

		sql.append(" SELECT round(TA1.PURCHASE_PRICE/(1+" + Utility.getDouble(rate)  );// String转为double类型方法

		sql.append( "),2) AS NOPurchasePrice  ,TA1.*,tb.BRAND_NAME, ts.SERIES_NAME,tm.MODEL_NAME, tc.CONFIG_NAME,tr.COLOR_NAME,TA2.SOLD_BY,TA2.DELIVERING_DATE,TA2.INVOICE_DATE,TA2.DISPATCHED_DATE,TA2.CUSTOMER_NAME,TA3.TRANSFER_DATE,");
		// 出库
		sql.append(
				" TA4.SD_NO AS OUT_STOCK_NO,TA4.STOCK_OUT_TYPE AS OUT_STOCK_TYPE,TA4.SHEET_created_at AS OUT_STOCK_DATE,TA4.ORG_CODE AS ORG_CODE," );

				sql.append( " TA4.CUSTOMER_NAME AS CUSTOMERname,TA4.VENDOR_NAME AS VENDOR_NAME,");
		// 入库
		sql.append(" TA5.DIRECTIVE_PRICE AS DIRECT_PRICE,TA5.SE_NO AS IN_STOCK_NO,");

		sql.append(
				"TA5.STORAGE_POSITION_CODE,TA5.ENGINE_NO,TA5.ADDITIONAL_COST,TA5.FACTORY_DATE,TA5.PO_NO,TA5.DISCHARGE_STANDARD,TA5.PRODUCTING_AREA,");

		sql.append(
				"TA5.INSPECTION_CONSIGNED,TA5.IS_DIRECT,TA5.SHIPPER_LICENSE,TA5.DELIVERYMAN_NAME,TA5.DELIVERYMAN_PHONE,TA5.SHIPPER_NAME,TA5.SHIPPING_DATE,");

		sql.append("TA5.SHIPPING_ADDRESS,TA5.CONSIGNER_CODE,TA5.CONSIGNER_NAME ");
		sql.append(" FROM ");

		if (!StringUtils.isNullOrEmpty(queryParam.get("stockStatus"))

				&& queryParam.get("stockStatus").trim().length() > 0 && !queryParam.get("stockStatus").equals("0")

				&& queryParam.get("stockStatus").trim().equals(DictCodeConstants.DICT_STORAGE_STATUS_ON_WAY+" ")) {
			str = sqltmp;
		} else {
			if (!StringUtils.isNullOrEmpty(queryParam.get("stockStatus"))
					&& queryParam.get("stockStatus").trim().length() > 0) {
				str = sqlstorage;
			} else {
				str.append(sqlstorage).append(" UNION ALL ").append(sqltmp);
			}
		}
		sql.append(" (select MM.* from ( " + str + ") MM where 1=1 ");
//		sql.append(Utility.getLikeCond("MM", "VIN", queryParam.get("vin"), "AND"));
		sql.append(Utility.getintCond("MM", "MAR_STATUS", queryParam.get("marStatus")));
		sql.append(Utility.getintCond("MM", "DISPATCHED_STATUS", queryParam.get("dispatchedStatus")));
		if (!StringUtils.isNullOrEmpty(queryParam.get("stockStatus"))
				&& queryParam.get("stockStatus").trim().length() > 0
				&& !queryParam.get("stockStatus").trim().equals("0")
				&& !queryParam.get("stockStatus").trim().equals(DictCodeConstants.DICT_STORAGE_STATUS_ON_WAY)) {
			sql.append(Utility.getintCond("MM", "STORAGE_CODE", queryParam.get("storage_code")));

		}
		sql.append(Utility.getStrCond("MM", "STORAGE_CODE", queryParam.get("storage_code")));

		sql.append(") TA1");

		sql.append(
				" LEFT JOIN ( SELECT b.brand_code,b.brand_name,b.dealer_code FROM tm_brand b ) tb ON TA1.DEALER_CODE=tb.DEALER_CODE AND TA1.BRAND_CODE=Tb.BRAND_CODE ");

//		sql.append(
//				" LEFT JOIN( SELECT s.series_code,s.series_name,s.dealer_code FROM tm_series s ) ts ON ta1.TA1.DEALER_CODE=ts.DEALER_CODE AND TA1.SERIES_CODE=TS.SERIES_CODE ");

		sql.append(" LEFT JOIN (SELECT s.series_code,s.series_name,s.dealer_code,s.BRAND_CODE FROM tm_series s) ts  ON TA1.DEALER_CODE = ts.DEALER_CODE  AND TA1.SERIES_CODE = TS.SERIES_CODE  AND ts.brand_code=tb.brand_code");
//		sql.append(
//				" LEFT JOIN( SELECT m.DEALER_CODE,m.MODEL_NAME,m.MODEL_CODE FROM tm_model m ) tm ON ta1.TA1.DEALER_CODE=tm.DEALER_CODE AND TA1.MODEL_CODE=TM.MODEL_CODE ");
		sql.append(" LEFT JOIN  (SELECT  m.DEALER_CODE, m.MODEL_NAME, m.MODEL_CODE, m.SERIES_CODE  FROM tm_model m) tm  ON ta1.TA1.DEALER_CODE = tm.DEALER_CODE  AND TA1.MODEL_CODE = TM.MODEL_CODE  AND ts.brand_code=tb.brand_code AND tm.series_code=ts.series_code");

//		sql.append(
//				" LEFT JOIN( SELECT c.DEALER_CODE,c.CONFIG_NAME,c.CONFIG_CODE FROM TM_CONFIGURATION c ) tc ON ta1.TA1.DEALER_CODE=tc.DEALER_CODE AND TA1.CONFIG_CODE=Tc.CONFIG_CODE ");

		sql.append(" LEFT JOIN  (SELECT  c.DEALER_CODE, c.CONFIG_NAME, c.CONFIG_CODE, c.MODEL_CODE FROM TM_CONFIGURATION c) tc  ON ta1.TA1.DEALER_CODE = tc.DEALER_CODE  AND TA1.CONFIG_CODE = Tc.CONFIG_CODE  and ts.brand_code=tb.brand_code  and tm.series_code=ts.series_code and tc.model_code=tm.model_code");

		sql.append(
				" LEFT JOIN( SELECT r.DEALER_CODE,r.COLOR_NAME,r.COLOR_CODE FROM TM_COLOR r) tr ON ta1.TA1.DEALER_CODE=tr.DEALER_CODE AND TA1.COLOR_CODE=tr.COLOR_CODE ");

		sql.append(" LEFT JOIN ");

		// 查询订单(一般订单，调拨订单方式为本地交车的以及受托托交车订单，并且订单号不在已经完成的退回单里)
		sql.append(
				"( SELECT C.SOLD_BY,C.DELIVERING_DATE,C.INVOICE_DATE,C.DISPATCHED_DATE,C.VIN,C.DEALER_CODE,C.D_KEY,C.CUSTOMER_NAME" );

				sql.append( " FROM TT_SALES_ORDER  C " );

						sql.append( " where 1=1 " + " AND SO_STATUS NOT IN (" );

		sql.append(DictCodeConstants.DICT_SO_STATUS_HAVE_CANCEL + ", ");

		sql.append(DictCodeConstants.DICT_SO_STATUS_HAVE_UNTREAD + ", ");

		sql.append(DictCodeConstants.DICT_SO_STATUS_WAIT_UNTREAT_IN_STOCK + ", ");

		sql.append(DictCodeConstants.DICT_SO_STATUS_UNTREAT_HAVE_IN_STOCK + ")");

		sql.append("AND C.BUSINESS_TYPE != ");

		sql.append(DictCodeConstants.DICT_SO_TYPE_SERVICE);// 销售业务类型

		sql.append(" AND C.BUSINESS_TYPE != ");

		sql.append(DictCodeConstants.DICT_SO_TYPE_RERURN);// 销售退回

		sql.append(" AND C.DELIVERY_MODE = ");

		sql.append(DictCodeConstants.DICT_DELIVERY_MODE_LOCAL);// 交车方式

		sql.append(" AND C.D_KEY = " + CommonConstants.D_KEY);

		// sql.append(" AND C.DEALER_CODE = '" +
		// FrameworkUtil.getLoginInfo().getDealerCode() + "'");

		sql.append(Utility.getStrCond("C", "DEALER_CODE", dealerCode));

		sql.append(") TA2");

		sql.append(" ON TA1.VIN = TA2.VIN AND TA1.DEALER_CODE = TA2.DEALER_CODE AND TA1.D_KEY = TA2.D_KEY ");

		sql.append(" LEFT JOIN ");

		// 查询已经入账的VIN的移库日期

		sql.append(" ( SELECT MAX(FINISHED_DATE)AS TRANSFER_DATE,VIN,DEALER_CODE,D_KEY " );

		sql.append( " FROM TT_VS_TRANSFER_ITEM tr" + " WHERE IS_FINISHED = " + DictCodeConstants.DICT_IS_YES );

		sql.append( " AND D_KEY = " + CommonConstants.D_KEY );

		sql.append(" AND tr.DEALER_CODE = '" + FrameworkUtil.getLoginInfo().getDealerCode() + "' ");

		sql.append(Utility.getStrCond("", "DEALER_CODE", dealerCode));

		sql.append(" GROUP BY VIN,DEALER_CODE,D_KEY ");

		sql.append(" ) TA3 ");

		sql.append(" ON TA1.VIN = TA3.VIN AND TA1.DEALER_CODE = TA3.DEALER_CODE AND TA1.D_KEY = TA3.D_KEY ");

		sql.append(" LEFT JOIN ");

		sql.append(
				" (SELECT DISTINCT M.DEALER_CODE,M.d_key,M.SD_NO,M.STOCK_OUT_TYPE,M.LATEST_STOCK_OUT_DATE as SHEET_CREATED_AT,");

		sql.append(" B.ORG_CODE, M.VIN,b.user_id,TM_CUSTOMER.CUSTOMER_NAME,M.VENDOR_NAME ");

		sql.append(" FROM TM_VS_STOCK M ");

		sql.append(" LEFT JOIN  (select A.vin,Z.ORG_CODE,z.user_id,A.DEALER_CODE from TT_SALES_ORDER  A, ");

		sql.append(" (SELECT X.DEALER_CODE,X.USER_ID,X.ORG_CODE  FROM TM_USER X,TM_ORGANIZATION Y WHERE ");

		sql.append(" X.DEALER_CODE=Y.DEALER_CODE AND X.ORG_CODE=Y.ORG_CODE)Z where A.DEALER_CODE=Z.DEALER_CODE ");

		sql.append(
				" AND A.SOLD_BY=Z.USER_ID  AND A.BUSINESS_TYPE=13001001 AND ( SO_STATUS = 13011035 or SO_STATUS = 13011075) ");

		sql.append(" union all  select A.vin,Z.ORG_CODE,z.user_id,A.DEALER_CODE  from  TT_SEC_SALES_ORDER_ITEM A, ");

		sql.append(
				" (SELECT X.DEALER_CODE,X.USER_ID, X.ORG_CODE FROM TM_USER X,TM_ORGANIZATION Y  WHERE X.DEALER_CODE=Y.DEALER_CODE ");

		sql.append(
				" AND X.ORG_CODE=Y.ORG_CODE)Z,  TT_SECOND_SALES_ORDER B, (SELECT MAX(B.created_at) AS created_at,VIN,A.DEALER_CODE ");

		sql.append(" FROM TT_SEC_SALES_ORDER_ITEM A  ");

		sql.append(
				" INNER JOIN TT_SECOND_SALES_ORDER B ON A.DEALER_CODE=B.DEALER_CODE AND A.SEC_SO_NO=B.SEC_SO_NO WHERE ");

		sql.append(
				" (SO_STATUS = 13011035 or SO_STATUS = 13011075) and B.BILL_TYPE=19001001 GROUP BY VIN,A.DEALER_CODE) H ");

		sql.append(" where B.DEALER_CODE=Z.DEALER_CODE AND B.SOLD_BY=Z.USER_ID  and A.SEC_SO_NO=B.SEC_SO_NO ");

		sql.append(
				" and B.BILL_TYPE=19001001 AND (SO_STATUS = 13011035 or SO_STATUS = 13011075) AND H.VIN=A.VIN AND H.DEALER_CODE=A.DEALER_CODE ");

		sql.append(" AND H.created_at=B.created_at )B   on m.VIN=B.VIN AND m.DEALER_CODE=B.DEALER_CODE ");

		sql.append(" LEFT JOIN (" + CommonConstants.VM_VEHICLE + ") E ");

		sql.append(" on E.VIN=m.VIN AND E.DEALER_CODE=M.DEALER_CODE ");

		sql.append(
				" LEFT JOIN TM_CUSTOMER ON TM_CUSTOMER.customer_no=E.customer_no AND TM_CUSTOMER.DEALER_CODE=E.DEALER_CODE)TA4 ");

		sql.append(" ON TA1.VIN=TA4.VIN AND TA1.DEALER_CODE=TA4.DEALER_CODE AND TA1.D_KEY=TA4.D_KEY ");

		// 入库
		sql.append(" LEFT JOIN ");
		sql.append(
				" (select distinct a.* ,b.DIRECTIVE_PRICE,b.brand_code,b.series_code,b.model_code,c.stock_in_type,c.LATEST_STOCK_IN_DATE from TT_VS_STOCK_ENTRY_ITEM a " );

		sql.append( "left join (" + CommonConstants.VM_VS_PRODUCT + ") b " );

		sql.append( "on a.DEALER_CODE=b.DEALER_CODE and a.d_key=b.d_key and a.product_code=b.product_code " );

		sql.append( " inner join (select max(created_at) as created_at,vin,DEALER_CODE from TT_VS_STOCK_ENTRY_ITEM group by vin,DEALER_CODE) d" );

		sql.append( " on d.vin=a.vin and a.DEALER_CODE=d.DEALER_CODE  and a.created_at=d.created_at " );

		sql.append( "inner join TM_VS_STOCK c on a.DEALER_CODE=c.DEALER_CODE and a.vin=c.vin ");

		// sql.append(" where a.DEALER_CODE='" + dealerCode + "' ");

		sql.append(" where a.DEALER_CODE='" + FrameworkUtil.getLoginInfo().getDealerCode() + "' ");

		sql.append(
				")TA5" + " ON TA1.VIN=TA5.VIN AND TA1.DEALER_CODE=TA5.DEALER_CODE AND TA1.D_KEY=TA5.D_KEY WHERE 1=1");

		this.setWhere(sql, queryParam, queryList);

		// sql.append(Utility.getDateCond("TA5", "LATEST_STOCK_IN_DATE",
		// queryParam.get("beginDate"),
		// queryParam.get("stockDateTo")));

		sql.append(" ORDER BY TA1.MODEL_CODE");

		List<Map> list = DAOUtil.findAll(sql.toString(), queryList);
		for (Map map : list) {
			if (map.get("MAR_STATUS") != null && map.get("MAR_STATUS") != "") {
				if (Integer.parseInt(map.get("MAR_STATUS").toString()) == DictCodeConstants.MAR_STATUS_NOT) {
					map.put("MAR_STATUS", "正常");
				} else if (Integer
						.parseInt(map.get("MAR_STATUS").toString()) == DictCodeConstants.MAR_STATUS_YES) {
					map.put("MAR_STATUS", "质损");
				}
			}
			if (map.get("DISPATCHED_STATUS") != null && map.get("DISPATCHED_STATUS") != "") {
				if (Integer.parseInt(map.get("DISPATCHED_STATUS")
						.toString()) == DictCodeConstants.DICT_DISPATCHED_STATUS_NOT_DISPATCHED) {
					map.put("DISPATCHED_STATUS", "未配车");
				} else if (Integer.parseInt(map.get("DISPATCHED_STATUS")
						.toString()) == DictCodeConstants.DICT_DISPATCHED_STATUS_HAVE_DISPATCHED) {
					map.put("DISPATCHED_STATUS", "已配车");
				} else if (Integer.parseInt(map.get("DISPATCHED_STATUS")
						.toString()) == DictCodeConstants.DICT_DISPATCHED_STATUS_DELIVERY_CONFIRM) {
					map.put("DISPATCHED_STATUS", "交车确认");
				} else if (Integer.parseInt(map.get("DISPATCHED_STATUS")
						.toString()) == DictCodeConstants.DICT_DISPATCHED_STATUS_HAVE_DELIVER) {
					map.put("DISPATCHED_STATUS", "已交车");
				}
			}
			if (map.get("STOCK_STATUS") != null && map.get("STOCK_STATUS") != "") {
				if (Integer.parseInt(
						map.get("STOCK_STATUS").toString()) == DictCodeConstants.DICT_STORAGE_STATUS_MOVE_OUT) {
					map.put("STOCK_STATUS", "出库");
				} else if (Integer.parseInt(
						map.get("STOCK_STATUS").toString()) == DictCodeConstants.DICT_STORAGE_STATUS_IN_STORAGE) {
					map.put("STOCK_STATUS", "在库");
				} else if (Integer
						.parseInt(map.get("STOCK_STATUS").toString()) == DictCodeConstants.DICT_STORAGE_STATUS_ON_WAY) {
					map.put("STOCK_STATUS", "在途");
				} else if (Integer.parseInt(
						map.get("STOCK_STATUS").toString()) == DictCodeConstants.DICT_STORAGE_STATUS_LEND_TO) {
					map.put("STOCK_STATUS", "借出");
				}
			}
			if (map.get("HAS_CERTIFICATE") != null && map.get("HAS_CERTIFICATE") != "") {
				if (Integer.parseInt(map.get("HAS_CERTIFICATE").toString()) == DictCodeConstants.STATUS_IS_YES) {
					map.put("HAS_CERTIFICATE", "是");
				} else if (Integer.parseInt(map.get("HAS_CERTIFICATE").toString()) == DictCodeConstants.STATUS_IS_NOT) {
					map.put("HAS_CERTIFICATE", "否");
				}
			}
			if (map.get("IS_VIP") != null && map.get("IS_VIP") != "") {
				if (Integer.parseInt(map.get("IS_VIP").toString()) == DictCodeConstants.STATUS_IS_YES) {
					map.put("IS_VIP", "是");
				} else if (Integer.parseInt(map.get("IS_VIP").toString()) == DictCodeConstants.STATUS_IS_NOT) {
					map.put("IS_VIP", "否");
				}
			}
			if (map.get("OEM_TAG") != null && map.get("OEM_TAG") != "") {
				if (Integer.parseInt(map.get("OEM_TAG").toString()) == DictCodeConstants.STATUS_IS_YES) {
					map.put("OEM_TAG", "是");
				} else if (Integer.parseInt(map.get("OEM_TAG").toString()) == DictCodeConstants.STATUS_IS_NOT) {
					map.put("OEM_TAG", "否");
				}
			}
			if (map.get("IS_SECONDHAND") != null && map.get("IS_SECONDHAND") != "") {
				if (Integer.parseInt(map.get("IS_SECONDHAND").toString()) == DictCodeConstants.STATUS_IS_YES) {
					map.put("IS_SECONDHAND", "是");
				} else if (Integer.parseInt(map.get("IS_SECONDHAND").toString()) == DictCodeConstants.STATUS_IS_NOT) {
					map.put("IS_SECONDHAND", "否");
				}
			}
			if (map.get("IS_TEST_DRIVE_CAR") != null && map.get("IS_TEST_DRIVE_CAR") != "") {
				if (Integer.parseInt(map.get("IS_TEST_DRIVE_CAR").toString()) == DictCodeConstants.STATUS_IS_YES) {
					map.put("IS_TEST_DRIVE_CAR", "是");
				} else if (Integer
						.parseInt(map.get("IS_TEST_DRIVE_CAR").toString()) == DictCodeConstants.STATUS_IS_NOT) {
					map.put("IS_TEST_DRIVE_CAR", "否");
				}
			}
			if (map.get("IS_CONSIGNED") != null && map.get("IS_CONSIGNED") != "") {
				if (Integer.parseInt(map.get("IS_CONSIGNED").toString()) == DictCodeConstants.STATUS_IS_YES) {
					map.put("IS_CONSIGNED", "是");
				} else if (Integer.parseInt(map.get("IS_CONSIGNED").toString()) == DictCodeConstants.STATUS_IS_NOT) {
					map.put("IS_CONSIGNED", "否");
				}
			}
			if (map.get("IS_PROMOTION") != null && map.get("IS_PROMOTION") != "") {
				if (Integer.parseInt(map.get("IS_PROMOTION").toString()) == DictCodeConstants.STATUS_IS_YES) {
					map.put("IS_PROMOTION", "是");
				} else if (Integer.parseInt(map.get("IS_PROMOTION").toString()) == DictCodeConstants.STATUS_IS_NOT) {
					map.put("IS_PROMOTION", "否");
				}
			}
			if (map.get("IS_PURCHASE_RETURN") != null && map.get("IS_PURCHASE_RETURN") != "") {
				if (Integer.parseInt(map.get("IS_PURCHASE_RETURN").toString()) == DictCodeConstants.STATUS_IS_YES) {
					map.put("IS_PURCHASE_RETURN", "是");
				} else if (Integer
						.parseInt(map.get("IS_PURCHASE_RETURN").toString()) == DictCodeConstants.STATUS_IS_NOT) {
					map.put("IS_PURCHASE_RETURN", "否");
				}
			}
			if (map.get("OUT_STOCK_TYPE") != null && map.get("OUT_STOCK_TYPE") != "") {
				if (Integer.parseInt(map.get("OUT_STOCK_TYPE").toString()) == DictCodeConstants.DICT_STOCK_OUT_TYPE_SALE) {
					map.put("OUT_STOCK_TYPE", "销售出库");
				} else if (Integer
						.parseInt(map.get("OUT_STOCK_TYPE").toString()) == DictCodeConstants.DICT_STOCK_OUT_TYPE_ALLOCATION) {
					map.put("OUT_STOCK_TYPE", "调拨出库");
				} else if (Integer
						.parseInt(map.get("OUT_STOCK_TYPE").toString()) == DictCodeConstants.DICT_STOCK_OUT_TYPE_DELIVERY) {
					map.put("OUT_STOCK_TYPE", "受托交车出库");
				}else if (Integer
						.parseInt(map.get("OUT_STOCK_TYPE").toString()) == DictCodeConstants.DICT_STOCK_OUT_TYPE_UNTREAD) {
					map.put("OUT_STOCK_TYPE", "采购退回出库");
				}else if (Integer
						.parseInt(map.get("OUT_STOCK_TYPE").toString()) == DictCodeConstants.DICT_STOCK_OUT_TYPE_OTHER) {
					map.put("OUT_STOCK_TYPE", "其它类型出库");
				}else if (Integer
						.parseInt(map.get("OUT_STOCK_TYPE").toString()) == DictCodeConstants.DICT_STOCK_OUT_TYPE_SEC_SALE) {
					map.put("OUT_STOCK_TYPE", "二网销售类型出库");
				}
			}
			if (map.get("INSPECTION_CONSIGNED") != null && map.get("INSPECTION_CONSIGNED") != "") {
				if (Integer.parseInt(map.get("INSPECTION_CONSIGNED").toString()) == DictCodeConstants.STATUS_IS_YES) {
					map.put("INSPECTION_CONSIGNED", "是");
				} else if (Integer
						.parseInt(map.get("INSPECTION_CONSIGNED").toString()) == DictCodeConstants.STATUS_IS_NOT) {
					map.put("INSPECTION_CONSIGNED", "否");
				}
			}
			if (map.get("IS_DIRECT") != null && map.get("IS_DIRECT") != "") {
				if (Integer.parseInt(map.get("IS_DIRECT").toString()) == DictCodeConstants.STATUS_IS_YES) {
					map.put("IS_DIRECT", "是");
				} else if (Integer
						.parseInt(map.get("IS_DIRECT").toString()) == DictCodeConstants.STATUS_IS_NOT) {
					map.put("IS_DIRECT", "否");
				}
			}

		}
		return list;
	}

	/**
	 * 查询仓库
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> queryStorage(Map<String, String> queryParam) throws ServiceBizException {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(" SELECT ST.STORAGE_CODE,ST.STORAGE_NAME,ST.DEALER_CODE FROM TM_STORAGE ST WHERE 1=1 ");
		List<Object> queryList = new ArrayList<Object>();
		List<Map> resultList = DAOUtil.findAll(stringBuffer.toString(), queryList);
		return resultList;
	}

	/**
	 * 查询部门
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> queryOrg(Map<String, String> queryParam) throws ServiceBizException {
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append(" SELECT ORG.DEALER_CODE,ORG.ORG_CODE,ORG_NAME FROM TM_ORGANIZATION ORG WHERE 1=1 ");
		List<Object> queryList = new ArrayList<Object>();
		List<Map> resultList = DAOUtil.findAll(sBuffer.toString(), queryList);
		return resultList;
	}

}
