package com.yonyou.dms.vehicle.service.upholsterWorker;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.DTO.monitor.OperateLogDto;
import com.yonyou.dms.common.domains.PO.basedata.TtSoUpholsterPO;
import com.yonyou.dms.common.service.monitor.OperateLogService;
import com.yonyou.dms.commonSales.domains.PO.stockmanage.SalesOrderPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domains.PO.TmDefaultParaPO;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.vehicle.domains.DTO.UpholsterWorker.UpholsterWorkerDTO;
import com.yonyou.dms.vehicle.domains.PO.UpholsterWorker.TtSoAssignPO;

/**
* 装潢派工实现类
* @author wangliang
* @date 2017年03月22日
*/
@Service
public class UpholsterWorkerServiceImpl implements UpholsterWorkerService{
	@Autowired
	private OperateLogService operateLogService;
	
	
	/**
	 * 前台界面查询方法
	 */
	@Override
	public PageInfoDto queryUpholsterWorker(Map<String, String> queryParam) throws ServiceBizException {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		TmDefaultParaPO po = TmDefaultParaPO.findByCompositeKeys(dealerCode,"3020");
		String sql = "";
		
		if(po.getString("DEFAULT_VALUE")!=null || !po.getString("DEFAULT_VALUE").trim().equals(DictCodeConstants.DICT_IS_YES.trim())){
		   sql = querySalesOrderByPayoff(queryParam);
		}
		
		if(po.getString("DEFAULT_VALUE")!=null || !po.getString("DEFAULT_VALUE").trim().equals(DictCodeConstants.DICT_IS_NO.trim())){
			sql= queryUpholster(queryParam);
		}
	
		
		List<Object> whereSql = new ArrayList<Object>();
		PageInfoDto id = DAOUtil.pageQuery(sql, whereSql);
		return id;
	}
	
	
	public static String  queryUpholster(Map<String, String> queryParam) {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		TmDefaultParaPO po = TmDefaultParaPO.findByCompositeKeys(dealerCode,"8036");
		StringBuffer sql = new StringBuffer();
		StringBuffer sql1 = new StringBuffer();
		StringBuffer sql2 = new StringBuffer();
		sql.append(" SELECT B.*,BR.BRAND_NAME,SE.SERIES_NAME,MO.MODEL_NAME ,U.USER_NAME AS SOLD_USER ,S.USER_NAME AS SHEET_USER FROM  ( ");
		sql.append(" SELECT DISTINCT A.DEALER_CODE,A.SOLD_BY,A.CUSTOMER_NAME,A.LOCK_USER,A.SO_NO,A.SHEET_CREATED_BY,A.SHEET_CREATE_DATE,A.LICENSE, " );
		sql.append(" A.VIN,B.BRAND_CODE,B.SERIES_CODE,B.MODEL_CODE,A.FINISH_USER,A.IS_CLOSE_RO, " );
		sql.append(" CASE WHEN A.COMPLETE_TAG = 12781001 THEN  10571001 else 10571002 END AS COMPLETE_TAG,");
		sql.append(" CASE WHEN AA.COUNT1 = BB.COUNT2 THEN 12271001 WHEN  AA.COUNT1 > BB.COUNT2 THEN 12271002  WHEN BB.COUNT2 IS NULL THEN 12271003  END AS ASSIGN_STATUS " );
		sql.append(" FROM TT_SALES_ORDER A " );
		sql.append(" LEFT JOIN TM_VS_PRODUCT B ON A.PRODUCT_CODE=B.PRODUCT_CODE AND A.DEALER_CODE=B.DEALER_CODE " );
		sql.append(" LEFT JOIN " );
		sql.append(" (SELECT COUNT(A.SO_NO) AS COUNT1,A.SO_NO,A.DEALER_CODE FROM TT_SO_UPHOLSTER A, TT_SALES_ORDER T WHERE A.DEALER_CODE=T.DEALER_CODE AND A.SO_NO=T.SO_NO AND ((T.BUSINESS_TYPE="+CommonConstants.DICT_SO_TYPE_GENERAL+" AND A.IS_PRE_SALE!="+CommonConstants.DICT_IS_YES+") OR (T.BUSINESS_TYPE="+CommonConstants.DICT_SO_TYPE_SERVICE+")) GROUP BY A.SO_NO,A.DEALER_CODE) AA ");
		sql.append(" ON A.SO_NO=AA.SO_NO AND A.DEALER_CODE=AA.DEALER_CODE ");
		sql.append(" LEFT JOIN ");
		sql.append(" (SELECT COUNT(B.SO_NO) AS COUNT2,B.SO_NO,B.DEALER_CODE FROM TT_SO_UPHOLSTER B, TT_SALES_ORDER T WHERE B.DEALER_CODE=T.DEALER_CODE AND B.SO_NO=T.SO_NO AND ((T.BUSINESS_TYPE="+CommonConstants.DICT_SO_TYPE_GENERAL+" AND B.IS_PRE_SALE!="+CommonConstants.DICT_IS_YES+") OR (T.BUSINESS_TYPE="+CommonConstants.DICT_SO_TYPE_SERVICE+")) AND B.ASSIGN_TAG= ");
		sql.append(CommonConstants.DICT_IS_YES);
		sql.append(" GROUP BY B.SO_NO,B.DEALER_CODE) BB ");
		sql.append(" ON A.SO_NO=BB.SO_NO AND A.DEALER_CODE=BB.DEALER_CODE ");
		sql.append(" WHERE 1=1  ");
	    
		sql1.append(" (A.COMPLETE_TAG="+CommonConstants.DICT_IS_YES+" AND (A.BUSINESS_TYPE="+CommonConstants.DICT_SO_TYPE_SERVICE+" OR A.BUSINESS_TYPE="+CommonConstants.DICT_SO_TYPE_GENERAL+")) ");
	    
	    sql2.append(" ((((A.BUSINESS_TYPE="+CommonConstants.DICT_SO_TYPE_SERVICE+" AND A.ORDER_SORT="+DictCodeConstants.FDICT_SALES_ORDER_SORT_GENERAL+") " );
	    sql2.append(" OR A.BUSINESS_TYPE="+CommonConstants.DICT_SO_TYPE_GENERAL+")  AND (A.SO_STATUS="+CommonConstants.DICT_SO_STATUS_DELIVERY_CONFIRMING+" OR A.SO_STATUS="+CommonConstants.DICT_SO_STATUS_HAVE_OUT_STOCK + " OR A.SO_STATUS= "+CommonConstants.DICT_SO_STATUS_MANAGER_AUDITING+ " OR A.SO_STATUS= "+CommonConstants.DICT_SO_STATUS_HAVE_CONFIRMED );
	    
	  /*  if(po.getString("DEFAULT_VALUE")!=null || !po.getString("DEFAULT_VALUE").trim().equals(DictCodeConstants.DICT_IS_YES.trim())){
		    sql2.append(" OR A.SO_STATUS="+CommonConstants.DICT_SO_STATUS_ACCOUNTANT_AUDITING);
		    sql2.append(" )) OR (A.BUSINESS_TYPE="+CommonConstants.DICT_SO_TYPE_SERVICE+" AND A.ORDER_SORT="+DictCodeConstants.FDICT_SALES_ORDER_SORT_PRE+"  AND A.SO_STATUS="+CommonConstants.DICT_SO_STATUS_HAVE_OUT_STOCK+")) ");
			sql2.append(" AND (EXISTS (SELECT * FROM TT_SO_PART P WHERE A.DEALER_CODE = P.DEALER_CODE AND A.SO_NO = P.SO_NO) OR AA.COUNT1>0)  ");
	    }
		//选择已竣工
		if(queryParam.get("completeTag")!=null && DictCodeConstants.DICT_IS_YES.equals(queryParam.get("completeTag"))) {
			sql.append(sql1.toString());
		}
		//选择未竣工
		if(queryParam.get("completeTag")!=null && DictCodeConstants.DICT_IS_NO.equals(queryParam.get("completeTag"))) {
			sql.append(sql2.toString());
		}
		//未选择竣工
		if(queryParam.get("completeTag")==null) {
			sql.append(" ( "+sql1.toString() + "OR" + sql2.toString()+" ) ");
		}
	
		sql.append(" AND A.DEALER_CODE= '"+dealerCode+"' ");
		Utility.sqlToLike(sql,queryParam.get("completeTag"),"VIN", "A");
		Utility.sqlToLike(sql,queryParam.get("soNo"),"SO_NO", "A");
		Utility.sqlToLike(sql,queryParam.get("customerName"),"CUSTOMER_NAME", "A");
		Utility.sqlToLike(sql,queryParam.get("license"),"LICENSE", "A");
		sql.append(Utility.getintCond("A", "SOLD_BY", queryParam.get("serviceAdvisor")));
		sql.append(Utility.getDateCond("A", "SHEET_CREATE_DATE", queryParam.get("beginDate"),queryParam.get("endDate")));
		//已派工
		if (DictCodeConstants.DICT_ASSIGN_TAG_ASSIGNED.equals(queryParam.get("roStatus"))){
			sql.append(" and AA.COUNT1=BB.COUNT2");
		}
		//部分派工
		else if (DictCodeConstants.DICT_ASSIGN_TAG_ASSIGNED_PARTIAL.equals(queryParam.get("roStatus"))){
			sql.append(" and AA.COUNT1>BB.COUNT2");
		}
		//未派工
		else if (DictCodeConstants.DICT_ASSIGN_TAG_NOT_ASSIGN.equals(queryParam.get("roStatus"))){
			sql.append(" and BB.COUNT2 IS NULL");
		}*/
		sql.append(" ) B  LEFT JOIN tm_brand BR ON B.BRAND_CODE = BR.BRAND_CODE AND B.DEALER_CODE=BR.DEALER_CODE LEFT JOIN  TM_SERIES  se   ON   B.SERIES_CODE=se.SERIES_CODE AND br.BRAND_CODE=se.BRAND_CODE AND br.DEALER_CODE=se.DEALER_CODE  LEFT  JOIN   TM_MODEL   mo   ON   B.MODEL_CODE=mo.MODEL_CODE AND mo.BRAND_CODE=se.BRAND_CODE AND mo.series_code=se.series_code AND se.DEALER_CODE=mo.DEALER_CODE LEFT JOIN tm_user U ON U.USER_ID = B.SOLD_BY LEFT JOIN tm_user S ON S.USER_ID = B.SHEET_CREATED_BY ");
		System.out.println("***********************************");
		System.out.println(sql.toString());
		System.out.println("***********************************");
		return sql.toString();
	}
	
	
	public static String querySalesOrderByPayoff(Map<String, String> queryParam) throws ServiceBizException {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		
		StringBuffer sql = new StringBuffer();
		StringBuffer sql1 = new StringBuffer();
		StringBuffer sql2 = new StringBuffer();
		sql.append(" SELECT B.*,BR.BRAND_NAME,SE.SERIES_NAME,MO.MODEL_NAME ,U.USER_NAME AS SOLD_USER ,S.USER_NAME AS SHEET_USER FROM  ( ");
		sql.append(" SELECT DISTINCT A.DEALER_CODE,A.SOLD_BY,A.CUSTOMER_NAME,A.LOCK_USER,A.SO_NO,A.SHEET_CREATED_BY,A.SHEET_CREATE_DATE,A.LICENSE, " );
		sql.append(" A.VIN,B.BRAND_CODE,B.SERIES_CODE,B.MODEL_CODE,A.FINISH_USER,A.IS_CLOSE_RO, " );
		sql.append(" CASE WHEN A.COMPLETE_TAG = 12781001 THEN  10571001 else 10571002 END AS COMPLETE_TAG,");
		sql.append(" CASE WHEN AA.COUNT1 = BB.COUNT2 THEN 12271001 WHEN  AA.COUNT1 > BB.COUNT2 THEN 12271002  WHEN BB.COUNT2 IS NULL THEN 12271003  END AS ASSIGN_STATUS " );
		sql.append(" FROM TT_SALES_ORDER A " );
		sql.append(" LEFT JOIN TM_VS_PRODUCT B ON A.PRODUCT_CODE=B.PRODUCT_CODE AND A.DEALER_CODE=B.DEALER_CODE " );
		sql.append(" LEFT JOIN " );
		sql.append(" (SELECT COUNT(A.SO_NO) AS COUNT1,A.SO_NO,A.DEALER_CODE FROM TT_SO_UPHOLSTER A, TT_SALES_ORDER T WHERE A.DEALER_CODE=T.DEALER_CODE AND A.SO_NO=T.SO_NO AND ((T.BUSINESS_TYPE="+CommonConstants.DICT_SO_TYPE_GENERAL+" AND A.IS_PRE_SALE!="+CommonConstants.DICT_IS_YES+") OR (T.BUSINESS_TYPE="+CommonConstants.DICT_SO_TYPE_SERVICE+")) GROUP BY A.SO_NO,A.DEALER_CODE) AA ");
		sql.append(" ON A.SO_NO=AA.SO_NO AND A.DEALER_CODE=AA.DEALER_CODE ");
		sql.append(" LEFT JOIN ");
		sql.append(" (SELECT COUNT(B.SO_NO) AS COUNT2,B.SO_NO,B.DEALER_CODE FROM TT_SO_UPHOLSTER B, TT_SALES_ORDER T WHERE B.DEALER_CODE=T.DEALER_CODE AND B.SO_NO=T.SO_NO AND ((T.BUSINESS_TYPE="+CommonConstants.DICT_SO_TYPE_GENERAL+" AND B.IS_PRE_SALE!="+CommonConstants.DICT_IS_YES+") OR (T.BUSINESS_TYPE="+CommonConstants.DICT_SO_TYPE_SERVICE+")) AND B.ASSIGN_TAG= ");
		sql.append(CommonConstants.DICT_IS_YES);
		sql.append(" GROUP BY B.SO_NO,B.DEALER_CODE) BB ");
		sql.append(" ON A.SO_NO=BB.SO_NO AND A.DEALER_CODE=BB.DEALER_CODE ");
		sql.append(" WHERE 1=1 AND ");
	    
		sql1.append(" (A.COMPLETE_TAG="+CommonConstants.DICT_IS_YES+" AND (A.BUSINESS_TYPE="+CommonConstants.DICT_SO_TYPE_SERVICE+" OR A.BUSINESS_TYPE="+CommonConstants.DICT_SO_TYPE_GENERAL+")) ");
	    
		sql2.append(" (((((A.BUSINESS_TYPE="+DictCodeConstants.DICT_SO_TYPE_SERVICE+" AND A.ORDER_SORT="+DictCodeConstants.FDICT_SALES_ORDER_SORT_GENERAL+") OR A.BUSINESS_TYPE="+DictCodeConstants.DICT_SO_TYPE_GENERAL+") " );//普通服务订单和销售订单
		sql2.append( " AND (A.PAY_OFF="+DictCodeConstants.DICT_IS_YES+" OR LOSSES_PAY_OFF="+DictCodeConstants.DICT_IS_YES+")) " );//已结清 或者 财务审核已通过并挂账已结清
		sql2.append( " OR (A.BUSINESS_TYPE="+DictCodeConstants.DICT_SO_TYPE_SERVICE+" AND A.ORDER_SORT="+DictCodeConstants.FDICT_SALES_ORDER_SORT_PRE+" "); //售前装潢服务订单
		sql2.append( " AND A.SO_STATUS="+DictCodeConstants.DICT_SO_STATUS_DELIVERY_CONFIRMING+")) " );//财务审核已通过(交车确认中)
		sql2.append(" AND (EXISTS (SELECT * FROM TT_SO_PART P WHERE A.DEALER_CODE = P.DEALER_CODE AND A.SO_NO = P.SO_NO) OR AA.COUNT1>0) AND A.COMPLETE_TAG="+DictCodeConstants.DICT_IS_NO+")"); 
	  
		//选择已竣工
		if(queryParam.get("completeTag")!=null && DictCodeConstants.DICT_IS_YES.equals(queryParam.get("completeTag"))) {
			sql.append(sql1.toString());
		}
		//选择未竣工
		if(queryParam.get("completeTag")!=null && DictCodeConstants.DICT_IS_NO.equals(queryParam.get("completeTag"))) {
			sql.append(sql2.toString());
		}
		//未选择竣工
		if(queryParam.get("completeTag")==null) {
			sql.append(" ( "+sql1.toString() + "OR" + sql2.toString()+" ) ");
		}
	
		sql.append(" AND A.DEALER_CODE= '"+dealerCode+"' ");
		Utility.sqlToLike(sql,queryParam.get("completeTag"),"VIN", "A");
		Utility.sqlToLike(sql,queryParam.get("soNo"),"SO_NO", "A");
		Utility.sqlToLike(sql,queryParam.get("customerName"),"CUSTOMER_NAME", "A");
		Utility.sqlToLike(sql,queryParam.get("license"),"LICENSE", "A");
		sql.append(Utility.getintCond("A", "SOLD_BY", queryParam.get("serviceAdvisor")));
		sql.append(Utility.getDateCond("A", "SHEET_CREATE_DATE", queryParam.get("beginDate"),queryParam.get("endDate")));
		//已派工
		if (DictCodeConstants.DICT_ASSIGN_TAG_ASSIGNED.equals(queryParam.get("roStatus"))){
			sql.append(" and AA.COUNT1=BB.COUNT2");
		}
		//部分派工
		else if (DictCodeConstants.DICT_ASSIGN_TAG_ASSIGNED_PARTIAL.equals(queryParam.get("roStatus"))){
			sql.append(" and AA.COUNT1>BB.COUNT2");
		}
		//未派工
		else if (DictCodeConstants.DICT_ASSIGN_TAG_NOT_ASSIGN.equals(queryParam.get("roStatus"))){
			sql.append(" and BB.COUNT2 IS NULL");
		}
		sql.append(" ) B  LEFT JOIN tm_brand BR ON B.BRAND_CODE = BR.BRAND_CODE AND B.DEALER_CODE=BR.DEALER_CODE LEFT JOIN  TM_SERIES  se   ON   B.SERIES_CODE=se.SERIES_CODE AND br.BRAND_CODE=se.BRAND_CODE AND br.DEALER_CODE=se.DEALER_CODE  LEFT  JOIN   TM_MODEL   mo   ON   B.MODEL_CODE=mo.MODEL_CODE AND mo.BRAND_CODE=se.BRAND_CODE AND mo.series_code=se.series_code AND se.DEALER_CODE=mo.DEALER_CODE LEFT JOIN tm_user U ON U.USER_ID = B.SOLD_BY LEFT JOIN tm_user S ON S.USER_ID = B.SHEET_CREATED_BY ");
		System.out.println("***********************************");
		System.out.println(sql.toString());
		System.out.println("***********************************");
		return sql.toString();
	}


	/**
	* 打印回显
	* @author wangliang
	* @date 2017年04月10日
	*/
	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> printrByid(String id,String id1) throws ServiceBizException {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		StringBuffer sql = new StringBuffer();
		StringBuffer sql1 = new StringBuffer();
		StringBuffer sql2 = new StringBuffer();
		sql.append(" SELECT *, (CASE WHEN  (SELECT EMPLOYEE_NAME FROM TM_EMPLOYEE WHERE EMPLOYEE_NO = '"+id1+"') IS NULL THEN '' ELSE (SELECT EMPLOYEE_NAME FROM TM_EMPLOYEE WHERE EMPLOYEE_NO = '"+id1+"') END )AS TECHNICIAN  FROM ( SELECT B.*,BR.BRAND_NAME,SE.SERIES_NAME,MO.MODEL_NAME ,U.USER_NAME AS SOLD_USER ,S.USER_NAME AS SHEET_USER FROM  ( ");
		sql.append(" SELECT DISTINCT A.DEALER_CODE,A.SOLD_BY,A.CUSTOMER_NAME,A.LOCK_USER,A.SO_NO,A.SHEET_CREATED_BY,A.SHEET_CREATE_DATE,A.LICENSE, " );
		sql.append(" A.VIN,B.BRAND_CODE,B.SERIES_CODE,B.MODEL_CODE,A.FINISH_USER,A.IS_CLOSE_RO, " );
		sql.append(" CASE WHEN A.COMPLETE_TAG = 12781001 THEN  10571001 else 10571002 END AS COMPLETE_TAG,");
		sql.append(" CASE WHEN AA.COUNT1 = BB.COUNT2 THEN 12271001 WHEN  AA.COUNT1 > BB.COUNT2 THEN 12271002  WHEN BB.COUNT2 IS NULL THEN 12271003  END AS ASSIGN_STATUS " );
		sql.append(" FROM TT_SALES_ORDER A " );
		sql.append(" LEFT JOIN TM_VS_PRODUCT B ON A.PRODUCT_CODE=B.PRODUCT_CODE AND A.DEALER_CODE=B.DEALER_CODE " );
		sql.append(" LEFT JOIN " );
		sql.append(" (SELECT COUNT(A.SO_NO) AS COUNT1,A.SO_NO,A.DEALER_CODE FROM TT_SO_UPHOLSTER A, TT_SALES_ORDER T WHERE A.DEALER_CODE=T.DEALER_CODE AND A.SO_NO=T.SO_NO AND ((T.BUSINESS_TYPE="+CommonConstants.DICT_SO_TYPE_GENERAL+" AND A.IS_PRE_SALE!="+CommonConstants.DICT_IS_YES+") OR (T.BUSINESS_TYPE="+CommonConstants.DICT_SO_TYPE_SERVICE+")) GROUP BY A.SO_NO,A.DEALER_CODE) AA ");
		sql.append(" ON A.SO_NO=AA.SO_NO AND A.DEALER_CODE=AA.DEALER_CODE ");
		sql.append(" LEFT JOIN ");
		sql.append(" (SELECT COUNT(B.SO_NO) AS COUNT2,B.SO_NO,B.DEALER_CODE FROM TT_SO_UPHOLSTER B, TT_SALES_ORDER T WHERE B.DEALER_CODE=T.DEALER_CODE AND B.SO_NO=T.SO_NO AND ((T.BUSINESS_TYPE="+CommonConstants.DICT_SO_TYPE_GENERAL+" AND B.IS_PRE_SALE!="+CommonConstants.DICT_IS_YES+") OR (T.BUSINESS_TYPE="+CommonConstants.DICT_SO_TYPE_SERVICE+")) AND B.ASSIGN_TAG= ");
		sql.append(CommonConstants.DICT_IS_YES);
		sql.append(" GROUP BY B.SO_NO,B.DEALER_CODE) BB ");
		sql.append(" ON A.SO_NO=BB.SO_NO AND A.DEALER_CODE=BB.DEALER_CODE ");
		sql.append(" WHERE 1=1 ");
	    
		sql1.append(" (A.COMPLETE_TAG="+CommonConstants.DICT_IS_YES+" AND (A.BUSINESS_TYPE="+CommonConstants.DICT_SO_TYPE_SERVICE+" OR A.BUSINESS_TYPE="+CommonConstants.DICT_SO_TYPE_GENERAL+")) ");
	    
	    sql2.append(" ((((A.BUSINESS_TYPE="+CommonConstants.DICT_SO_TYPE_SERVICE+" AND A.ORDER_SORT="+DictCodeConstants.FDICT_SALES_ORDER_SORT_GENERAL+") " );
	    sql2.append(" OR A.BUSINESS_TYPE="+CommonConstants.DICT_SO_TYPE_GENERAL+")  AND (A.SO_STATUS="+CommonConstants.DICT_SO_STATUS_DELIVERY_CONFIRMING+" OR A.SO_STATUS="+CommonConstants.DICT_SO_STATUS_HAVE_OUT_STOCK + " OR A.SO_STATUS= "+CommonConstants.DICT_SO_STATUS_MANAGER_AUDITING+ " OR A.SO_STATUS= "+CommonConstants.DICT_SO_STATUS_HAVE_CONFIRMED );
	    sql2.append(" OR A.SO_STATUS="+CommonConstants.DICT_SO_STATUS_ACCOUNTANT_AUDITING);
	    sql2.append(" )) OR (A.BUSINESS_TYPE="+CommonConstants.DICT_SO_TYPE_SERVICE+" AND A.ORDER_SORT="+DictCodeConstants.FDICT_SALES_ORDER_SORT_PRE+"  AND A.SO_STATUS="+CommonConstants.DICT_SO_STATUS_HAVE_OUT_STOCK+")) ");
		sql2.append(" AND (EXISTS (SELECT * FROM TT_SO_PART P WHERE A.DEALER_CODE = P.DEALER_CODE AND A.SO_NO = P.SO_NO) OR AA.COUNT1>0)  ");
		
		sql.append(" ) B  LEFT JOIN tm_brand BR ON B.BRAND_CODE = BR.BRAND_CODE AND B.DEALER_CODE=BR.DEALER_CODE LEFT JOIN  TM_SERIES  se   ON   B.SERIES_CODE=se.SERIES_CODE AND br.BRAND_CODE=se.BRAND_CODE AND br.DEALER_CODE=se.DEALER_CODE  LEFT  JOIN   TM_MODEL   mo   ON   B.MODEL_CODE=mo.MODEL_CODE AND mo.BRAND_CODE=se.BRAND_CODE AND mo.series_code=se.series_code AND se.DEALER_CODE=mo.DEALER_CODE LEFT JOIN tm_user U ON U.USER_ID = B.SOLD_BY LEFT JOIN tm_user S ON S.USER_ID = B.SHEET_CREATED_BY )A");
		sql.append(" WHERE 1=1 ");
		sql.append(Utility.getStrCond("A", "SO_NO", id));
		sql.append(Utility.getStrCond("A", "DEALER_CODE", dealerCode));
		System.out.println("*****************************");
		System.err.println(sql);
		System.out.println("*****************************");
		List<Map> resultList = Base.findAll(sql.toString());
		return resultList;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> queryUpholerByid(String id) throws ServiceBizException {
		List<Map> list = queryBusinessType(id);
		
		Map map = list.get(0);
		String businessType = map.get("BUSINESS_TYPE").toString();
		String vin = "";
		if(map.get("VIN")!=null && map.get("VIN").equals(" ")) {
			 vin = map.get("VIN").toString();
		}
		StringBuffer sql = new StringBuffer();
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		if (businessType.equals(CommonConstants.DICT_SO_TYPE_RERURN)) {
			sql.append(
					" SELECT DISTINCT ef.PRODUCTING_AREA, ef.ENGINE_NO, ef.CERTIFICATE_NUMBER,TA1.*,B.WHOLESALE_DIRECTIVE_PRICE,B.REMARK,B.BRAND_CODE,B.SERIES_CODE,B.MODEL_CODE,B.CONFIG_CODE, ");
			sql.append(" B.PRODUCT_NAME AS PRODUCT_NAME,COALESCE(TA1.COLOR_CODE,B.COLOR_CODE) as COLOR_CODE ");
			sql.append(" FROM ( ");
			sql.append(
					" SELECT A.*,C.VEHICLE_PRICE AS OLD_VEHICLE_PRICE,C.ORDER_PAYED_AMOUNT  AS OLD_ORDER_PAYED_AMOUNT FROM TT_SALES_ORDER A,TT_SALES_ORDER C ");
			sql.append(" WHERE 1=1 AND A.OLD_SO_NO = C.SO_NO AND A.DEALER_CODE = C.DEALER_CODE AND A.DEALER_CODE = '"
					+ dealerCode + "'");
			sql.append(" AND A.SO_NO = '" + id + "' AND A.D_KEY = " + CommonConstants.D_KEY + ") TA1 ");
			sql.append(" LEFT JOIN (" + CommonConstants.VM_VS_PRODUCT
					+ ") B  ON TA1.PRODUCT_CODE = B.PRODUCT_CODE AND TA1.DEALER_CODE = B.DEALER_CODE AND TA1.D_KEY = B.D_KEY ");
			sql.append(
					" left join TT_VS_STOCK_ENTRY_ITEM ef on (ef.DEALER_CODE = TA1.DEALER_CODE and ef.vin = TA1.vin) ");
		} else {
			sql.append(
					" SELECT DISTINCT ef.PRODUCTING_AREA, ef.ENGINE_NO, ef.CERTIFICATE_NUMBER,A.*,   B.WHOLESALE_DIRECTIVE_PRICE,B.REMARK,B.BRAND_CODE,B.SERIES_CODE,B.MODEL_CODE,0 AS OLD_VEHICLE_PRICE,0  AS OLD_ORDER_PAYED_AMOUNT, ");
			sql.append(" B.CONFIG_CODE, B.PRODUCT_NAME AS PRODUCT_NAME,B.COLOR_CODE,O.ORG_NAME ");
			
			if (null != businessType && businessType.equals(CommonConstants.DICT_SO_TYPE_GENERAL)) {
				sql.append(" ,TA1.WS_NO,TA1.SALES_ITEM_ID,TA1.WS_TYPE ");
			}
			
			if (CommonConstants.DICT_SO_TYPE_ALLOCATION.equals(businessType)) {
				sql.append(" ,TAB1.DEALER_CODE ");
			}

			sql.append(" FROM TT_SALES_ORDER A ");
			sql.append(" LEFT JOIN (" + CommonConstants.VM_VS_PRODUCT
					+ ") B  ON A.PRODUCT_CODE = B.PRODUCT_CODE AND A.DEALER_CODE = B.DEALER_CODE AND A.D_KEY= B.D_KEY ");
			sql.append(" LEFT JOIN TM_USER U ON A.DEALER_CODE = U.DEALER_CODE AND A.SOLD_BY= U.USER_ID ");
			sql.append(" LEFT JOIN TM_ORGANIZATION O ON A.DEALER_CODE = O.DEALER_CODE AND U.ORG_CODE=O.ORG_CODE ");
			
			if (null != businessType && businessType.equals(CommonConstants.DICT_SO_TYPE_GENERAL)) {
				sql.append(
						" LEFT JOIN ( SELECT DISTINCT N.DEALER_CODE,N.SALES_ITEM_ID,M.WS_NO,N.SO_NO,N.VIN,K.WS_TYPE ");
				sql.append("  FROM TT_PO_CUS_WHOLESALE K, TT_WS_CONFIG_INFO M,TT_WS_SALES_INFO N ");
				sql.append(" WHERE M.DEALER_CODE = N.DEALER_CODE AND M.ITEM_ID = N.ITEM_ID ");
				sql.append(" AND K.DEALER_CODE = M.DEALER_CODE AND K.WS_NO = M.WS_NO AND K.WS_STATUS = "
						+ DictCodeConstants.DICT_WHOLESALE_STATUS_PASS);
				Utility.getStrCond("N", "DEALER_CODE", dealerCode);
				Utility.getStrCond("N", "SO_NO", id);
				Utility.getStrCond("N", "VIN", vin);
				
				sql.append(") TA1 ON A.DEALER_CODE = TA1.DEALER_CODE AND A.SO_NO = TA1.SO_NO ");
			}
			
			if (CommonConstants.DICT_SO_TYPE_ALLOCATION.equals(businessType)) {
				sql.append(
						" LEFT JOIN TM_PART_CUSTOMER TAB1 ON A.DEALER_CODE=TAB1.DEALER_CODE and A.CONSIGNEE_CODE=TAB1.CUSTOMER_CODE ");
			}
			
			sql.append(" left join TT_VS_STOCK_ENTRY_ITEM ef on (ef.DEALER_CODE = a.DEALER_CODE and ef.vin = a.vin) ");
			sql.append(" WHERE 1=1 AND  A.D_KEY = " + CommonConstants.D_KEY);
			sql.append(Utility.getStrCond("A", "SO_NO", id));
			sql.append(Utility.getStrCond("A", "DEALER_CODE", dealerCode));
		}
			
		
			System.out.println("*********sql***********");
			System.out.println(sql.toString());
			System.out.println("*********sql***********");
			
			List<Map> resultList = Base.findAll(sql.toString());
		
		return resultList;
	}


	@SuppressWarnings("rawtypes")
	private List<Map> queryBusinessType(String id) {
		String sb = new String("SELECT VIN,BUSINESS_TYPE,DEALER_CODE FROM Tt_Sales_Order WHERE SO_NO = '" + id + "' AND D_KEY = "+CommonConstants.D_KEY);
		System.out.println("---------");
		System.out.println(sb.toString());
		System.out.println("---------");
		List<Object> queryList = new ArrayList<Object>();
		queryList.add(id);
		List<Map> result = Base.findAll(sb.toString());
		return result;
	}


	@Override
	public PageInfoDto queryDecrodateProject(Map<String, String> queryParam) throws ServiceBizException {
		PageInfoDto pagedto = null;
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		StringBuffer sql = new StringBuffer();
		sql.append("select A.*, A.LABOUR_CODE AS UPHOLSTER_CODE,A.LABOUR_NAME AS UPHOLSTER_NAME,1 AS DISCOUNT ,0 AS DISCOUNT_AMOUNT, 1 AS LABOUR_PRICE, 1*A.STD_LABOUR_HOUR AS RECEIVE_AMOUNT,0 AS flag  from ( SELECT DEALER_CODE,CLAIM_LABOUR ,REPAIR_TYPE_CODE,LOCAL_LABOUR_CODE, LABOUR_CODE, ");
		sql.append("LOCAL_LABOUR_NAME, LABOUR_NAME, STD_LABOUR_HOUR, ASSIGN_LABOUR_HOUR,");
		sql.append("WORKER_TYPE_CODE, SPELL_CODE, MODEL_LABOUR_CODE,OPERATION_CODE,");
		sql.append("REPAIR_GROUP_CODE ,VER, DOWN_TAG, OEM_LABOUR_HOUR, CREATED_AT, UPDATED_AT,REPLACE_STATUS, ");
		sql.append(" case when Created_at < Updated_at then Updated_at else Created_at end as NewDate, ");
		sql.append(
				" case when (IS_MEMBER IS NULL OR IS_MEMBER=0 OR IS_MEMBER=12781002) THEN 12781002 ELSE IS_MEMBER END IS_MEMBER  ");
		sql.append(" FROM TM_DECRODATE_ITEM " + "WHERE DEALER_CODE = '" + dealerCode + "'");
		Utility.sqlToLike(sql, queryParam.get("localLabourCode"), "LOCAL_LABOUR_CODE", null);
		Utility.sqlToLike(sql, queryParam.get("localLabourName"), "LOCAL_LABOUR_NAME", null);
		Utility.sqlToLike(sql, queryParam.get("labourCode"), "LABOUR_CODE", null);
		Utility.sqlToLike(sql, queryParam.get("labourName"), "LABOUR_NAME", null);
		Utility.sqlToLike(sql, queryParam.get("spellCode"), "SPELL_CODE", null);
		String downTag = queryParam.get("downTag");
		if (null != downTag && !"".equals(downTag.trim())) {
			if (downTag.equals(12781001))
				sql.append(" and DOWN_TAG = " + downTag);
			else
				sql.append(" and (DOWN_TAG <> 12781001 OR DOWN_TAG is null) ");
		}
		String repairGroupCode = "";
		String MainGroup = queryParam.get("mainGroupCode"); 
		String SubGroup = queryParam.get("subGroupCode");
		if(!StringUtils.isNullOrEmpty(MainGroup) || !StringUtils.isNullOrEmpty(SubGroup)){
			if(StringUtils.isNullOrEmpty(MainGroup)){
				MainGroup = "";
			}
			if(StringUtils.isNullOrEmpty(SubGroup)){
				SubGroup = "";
			}
			repairGroupCode = MainGroup +SubGroup;
		}
		if (repairGroupCode != null && !repairGroupCode.trim().equals("")) {
			sql.append(" and REPAIR_GROUP_CODE = '" + repairGroupCode + "'");
		}
		String workerTypeCode = queryParam.get("workerTypeCode");
		if (workerTypeCode != null && !workerTypeCode.trim().equals("")) {
			sql.append(" and WORKER_TYPE_CODE = '" + workerTypeCode + "'");
		}
		
		String modelLabourCode = queryParam.get("modelLabourCode");		
		if (modelLabourCode != null && !modelLabourCode.trim().equals("")) {
			sql.append(" and (1=2 ");
			String[] codeArray = modelLabourCode.split(",");
			for(int i = 0;i<codeArray.length;i++){
				sql.append( " or MODEL_LABOUR_CODE = '" + codeArray[i] + "'");
			}
			sql.append(" ) ");
		}
		
		String nullLabour = queryParam.get("nullLabour");
		if (Utility.testString(nullLabour)) {
			if (nullLabour.equals(12781001))
				sql.append(" and (STD_LABOUR_HOUR is null or STD_LABOUR_HOUR = 0) ");
			else
				sql.append(" and ( STD_LABOUR_HOUR <> 0) ");
		}
		String startDate = queryParam.get("startDate");
		String endDate = queryParam.get("endDate");
		if (startDate != null && endDate != null) {
			sql.append(")A  where 1=1 ");
			sql.append(Utility.getDateCond("A", "NewDate", startDate, endDate));
		} else {
			sql.append(")A  WHERE 1=1 ");
		}
		System.err.println(sql.toString());
		
		List<Object> queryList = new ArrayList<Object>();
		pagedto = DAOUtil.pageQuery(sql.toString(), queryList);
		return pagedto;
	}

	/**
	* 添加装潢项目
	* @author wangliang
	* @date 2017年03月27日
	*/
	@SuppressWarnings("rawtypes")
	@Override
	public long addUpholsterWorkerInfo(UpholsterWorkerDTO upholsterWokerDto,String id) throws ServiceBizException {
		TtSoUpholsterPO upholsterPo = new  TtSoUpholsterPO();
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		int tag = 0;
		if(upholsterWokerDto.getIntentList().size() > 0 && upholsterWokerDto.getIntentList() != null) {
			for(Map intentDto : upholsterWokerDto.getIntentList()) {
				upholsterPo=getUpholsterIntent(intentDto,id);
				if(!StringUtils.isNullOrEmpty(upholsterPo)) {
				     upholsterPo.saveIt();
				     tag++;
				}			
			}
		}
		
		
		if(tag > 0){
			SalesOrderPO salesOrderPo = new SalesOrderPO();
			List<SalesOrderPO> list = SalesOrderPO.findBySQL("SELECT * FROM tt_sales_order WHERE SO_NO = ? and DEALER_CODE = ?  ", id,dealerCode);
			salesOrderPo = list.get(0);
			salesOrderPo.setString("D_KEY",CommonConstants.D_KEY);
			salesOrderPo.setString("COMPLETE_TAG", DictCodeConstants.DICT_IS_NO);
			//salesOrderCon.setVer(Utility.getInt(ver));
			salesOrderPo.saveIt();
			
		}
		return 0;
	}
	
	/**
	* 设置TtSoUpholsterPO属性
	* @author wangliang
	* @date 2017年03月27日
	*/
    @SuppressWarnings("rawtypes")
	public TtSoUpholsterPO getUpholsterIntent(Map intentDto,String id) {
    	TtSoUpholsterPO intentPo = new TtSoUpholsterPO();
    	if(!intentDto.get("flag").equals("1")){
    		if(!StringUtils.isNullOrEmpty(id)){
        		intentPo.setString("SO_NO", id);
        	}
        	if(!StringUtils.isNullOrEmpty(intentDto.get("UPHOLSTER_CODE"))){
        		intentPo.setString("UPHOLSTER_CODE",intentDto.get("UPHOLSTER_CODE").toString() );// 装潢项目代码
        	}
        	if(!StringUtils.isNullOrEmpty(intentDto.get("UPHOLSTER_CODE"))){
        		intentPo.setString("UPHOLSTER_NAME",intentDto.get("UPHOLSTER_NAME").toString());// 装潢项目名称
        	}
        	if(!StringUtils.isNullOrEmpty(intentDto.get("STD_LABOUR_HOUR"))){
        		intentPo.setString("STD_LABOUR_HOUR",intentDto.get("STD_LABOUR_HOUR").toString());// 标准工时
        	}
        	
            if(!StringUtils.isNullOrEmpty(intentDto.get("ASSIGN_LABOUR_HOUR"))){
            	intentPo.setString("ASSIGN_LABOUR_HOUR",intentDto.get("ASSIGN_LABOUR_HOUR").toString());// 派工工时
        	}
            if(!StringUtils.isNullOrEmpty(intentDto.get("LOCAL_LABOUR_CODE"))){
            	intentPo.setString("LOCAL_LABOUR_CODE",intentDto.get("LOCAL_LABOUR_CODE").toString());// 行管项目代码
        	}
            if(!StringUtils.isNullOrEmpty(intentDto.get("LOCAL_LABOUR_NAME"))){
            	intentPo.setString("LOCAL_LABOUR_NAME",intentDto.get("LOCAL_LABOUR_NAME").toString());// 行管项目名称
        	}
            if(!StringUtils.isNullOrEmpty(intentDto.get("LABOUR_PRICE"))){
            	intentPo.setString("LABOUR_PRICE",intentDto.get("LABOUR_PRICE").toString());// 工时单价 
        	}
            if(!StringUtils.isNullOrEmpty(intentDto.get("LABOUR_AMOUNT"))){
            	intentPo.setString("LABOUR_AMOUNT",intentDto.get("LABOUR_AMOUNT").toString());// 工时费
        	}
            if(!StringUtils.isNullOrEmpty(intentDto.get("TECHNICIAN"))){
            	intentPo.setString("TECHNICIAN",intentDto.get("TECHNICIAN").toString());// 技师
        	}
            intentPo.setString("flag", "1"); //表示该数据已经保存，下次不需要保存
           
            return intentPo;
    	}
    	return null;
    }


	@Override
	public PageInfoDto queryTechnician(Map<String, String> queryParam) throws ServiceBizException {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT DISTINCT E.EMPLOYEE_NO,E.DEALER_CODE, E.EMPLOYEE_NAME,A.LABOUR_FACTOR,A.LABOUR_POSITION_CODE FROM TM_EMPLOYEE E LEFT JOIN TT_SO_ASSIGN A ON E.EMPLOYEE_NAME  = A.TECHNICIAN    ");
		System.err.println(sql);
		List<Object> whereSql = new ArrayList<Object>();
		PageInfoDto id = DAOUtil.pageQuery(sql.toString(), whereSql);
		return id;
	}


	

	/**
	 * 查询装潢项目--项目添加
	 * @author wangliang
	 * @date 2017年04月1日
	 */
	
	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> queryUpholsterWorker(String id) throws ServiceBizException {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT * FROM TT_SO_UPHOLSTER WHERE DEALER_CODE = '"+dealerCode+"' AND SO_NO = '"+id+"' ");
		System.err.println(sb.toString());
		List<Map> list = Base.findAll(sb.toString());
		return list;
	}
	
	/**
	 * 查询装潢项目--整单派工
	 * @author wangliang
	 * @date 2017年04月1日
	 */
	@Override
	public PageInfoDto queryUpholsterWorker1(String id) throws ServiceBizException {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT * FROM TT_SO_UPHOLSTER WHERE DEALER_CODE = '"+dealerCode+"' AND SO_NO = '"+id+"' ");
		System.err.println(sb.toString());
		List<Object> whereSql = new ArrayList<Object>();
		PageInfoDto page = DAOUtil.pageQuery(sb.toString(), whereSql);
		return page;
	}
	

	/**
	 * 查询装潢配件--整单派工
	 * @author wangliang
	 * @date 2017年04月1日
	 */
	@Override
	public PageInfoDto querySoPart(String id) throws ServiceBizException {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT S.*,B.NUM FROM (SELECT COUNT(*) AS num ,DEALER_CODE  FROM TT_SO_PART  WHERE DEALER_CODE = '"+dealerCode+"' AND SO_NO = '"+id+"')B LEFT JOIN TT_SO_PART s ON s.DEALER_CODE = b.DEALER_CODE WHERE s.DEALER_CODE = '"+dealerCode+"' AND s.SO_NO = '"+id+"' ");
		System.err.println(sb.toString());
		List<Object> whereSql = new ArrayList<Object>();
		PageInfoDto page = DAOUtil.pageQuery(sb.toString(), whereSql);
		return page;
	}

	/**
	* 新增整单派工
	* @author wangliang
	* @date 2017年04月1日
	*/
	
	@Override
	public long addUpholsterWorker(UpholsterWorkerDTO upholsterWokerDto,String id) throws ServiceBizException {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		List<TtSoUpholsterPO> list = TtSoUpholsterPO.findBySQL("SELECT * FROM TT_SO_UPHOLSTER WHERE SO_NO = ? and DEALER_CODE = ? ", id,dealerCode);
		TtSoUpholsterPO upholsterPo = new TtSoUpholsterPO();
	
		TtSoAssignPO assignPO = null;
		String EmployeeName = upholsterWokerDto.getEmployeeName();		
		String positionCode = upholsterWokerDto.getPositionCode();//派工工位
		/*String checked = upholsterWokerDto.getChecked();//检验人
        */		
		String factor=upholsterWokerDto.getFactor();//工时系数
	
		//删除分项派工中的装潢派工
		String sql = "delete from TT_SO_ASSIGN where SO_NO =  '"+id+"' AND DEALER_CODE =  '"+dealerCode+"'";
		Base.exec(sql);
		
		//删除分项派工中的装潢派工明细
		String sql1 = "delete from TT_SO_ASSIGN_ITEM where SO_NO =  '"+id+"' AND DEALER_CODE =  '"+dealerCode+"'";
		Base.exec(sql1);
		
		//整单派工
		if(list!=null && list.size() > 0) {
			for(int i = 0;i<list.size();i++) {
				//添加装潢项目
				upholsterPo = list.get(i);
				upholsterPo.setString("TECHNICIAN",EmployeeName);
				//添加派工标志
				upholsterPo.setString("ASSIGN_TAG",DictCodeConstants.DICT_IS_YES);
				upholsterPo.saveIt();
				
				//添加装潢项目派工明细
				String itemId = upholsterPo.get("ITEM_ID").toString();
				String dealerCode1 = FrameworkUtil.getLoginInfo().getDealerCode();
				assignPO = new TtSoAssignPO();
				if(!StringUtils.isNullOrEmpty(id)){
					assignPO.setString("SO_NO",id);
				}
				if(!StringUtils.isNullOrEmpty(dealerCode1)){
					assignPO.setString("DEALER_CODE",dealerCode1);
				}
				if(!StringUtils.isNullOrEmpty(id)){
					assignPO.setString("ITEM_ID",itemId);
				}
				if(!StringUtils.isNullOrEmpty(EmployeeName)){
					assignPO.setString("TECHNICIAN",EmployeeName);
				}
				if(!StringUtils.isNullOrEmpty(positionCode)){
					assignPO.setString("LABOUR_POSITION_CODE",positionCode);
				}
				if(!StringUtils.isNullOrEmpty(factor)){
					assignPO.setString("LABOUR_FACTOR",factor);
				}
			/*	if(!StringUtils.isNullOrEmpty(checked)){
					assignPO.setString("LABOUR_POSITION_CODE",checked);
				}*/
				assignPO.setDate("ASSIGN_TIME",new Date());
				assignPO.setDate("ITEM_START_TIME",new Date());
				assignPO.setDate("ESTIMATE_END_TIME",new Date());
				assignPO.saveIt();
			}
			return upholsterPo.getLongId();
		}
		return 0;
	}
	
	/**
	* 竣工
	* @author wangliang
	* @date 2017年04月6日
	*/
	@Override
	public void finalChecked(UpholsterWorkerDTO upholsterWokerDto, String id) throws ServiceBizException {
		SalesOrderPO salesPO = SalesOrderPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),id);
		String EmployeeName = upholsterWokerDto.getEmployeeName();//派工技师

		salesPO.set("FINISH_USER",EmployeeName);
		//竣工
		salesPO.set("COMPLETE_TAG",12781001);
		salesPO.saveIt();
	
	}
	
	/**
	* 取消竣工
	* @author wangliang
	* @date 2017年04月10日
	*/
	@Override
	public void finalChecked1(UpholsterWorkerDTO upholsterWokerDto, String id) throws ServiceBizException {
		SalesOrderPO salesPO = SalesOrderPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),id);
		salesPO.set("COMPLETE_TAG",12781002);
		salesPO.saveIt();
	}
	/**
	* 竣工提示
	* @author wangliang
	* @date 2017年04月10日
	*/
	@SuppressWarnings("rawtypes")
	@Override
	public Integer isFinshWork(String id) throws ServiceBizException {
		  String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		   StringBuffer sql = new StringBuffer();
		   sql.append("select  * from TT_SO_PART A,TT_SALES_ORDER B where A.DEALER_CODE=B.DEALER_CODE AND A.SO_NO=B.SO_NO AND ");	   
		   sql.append(" (A.IS_FINISHED="+ DictCodeConstants.DICT_IS_NO + " OR A.IS_FINISHED is null) AND A.DEALER_CODE='"+dealerCode+"' AND A.SO_NO='" +  id+ "' "); 
		   sql.append(" AND ((B.BUSINESS_TYPE="+DictCodeConstants.DICT_SO_TYPE_SERVICE+") "); 
		   sql.append( " OR (B.BUSINESS_TYPE="+DictCodeConstants.DICT_SO_TYPE_GENERAL+" AND A.IS_PRE_SALE!="+DictCodeConstants.DICT_IS_YES+"))");
		   List<Map> list = Base.findAll(sql.toString());
		   return list.size();
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Integer isFinshWork1(String id) throws ServiceBizException {
		 String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		 String sql = "SELECT 1 FROM TT_SO_UPHOLSTER WHERE 1=1  AND DEALER_CODE = '"+dealerCode+"' AND SO_NO  = '" +id+ "'  AND ASSIGN_TAG = "+DictCodeConstants.DICT_IS_NO+" ";
		 List<Map> list = Base.findAll(sql.toString());
		 return list.size();
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> querySoPart1(String id) throws ServiceBizException {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT S.*,B.NUM FROM (SELECT COUNT(*) AS num ,DEALER_CODE  FROM TT_SO_PART  WHERE DEALER_CODE = '"+dealerCode+"' AND SO_NO = '"+id+"')B LEFT JOIN TT_SO_PART s ON s.DEALER_CODE = b.DEALER_CODE WHERE s.DEALER_CODE = '"+dealerCode+"' AND s.SO_NO = '"+id+"' ");
		System.err.println(sb.toString());
		List<Map> list = Base.findAll(sb.toString());
		return list;
	}
	/**
	* 新增分项派工
	* @author wangliang
	* @date 2017年04月5日
	*/
	@Override
	public long addProject(UpholsterWorkerDTO upholsterWokerDto, String id, String id1) throws ServiceBizException {
		TtSoUpholsterPO upholsterPo = new TtSoUpholsterPO();
		TtSoAssignPO assignPO = new TtSoAssignPO();
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		String EmployeeName = upholsterWokerDto.getEmployeeName();//派工技师
		String positionCode  =  upholsterWokerDto.getPositionCode();//工位名称
		String workType = upholsterWokerDto.getWorkTypeCode();//工种代码
		String labourHour = upholsterWokerDto.getLabourHour();//分工工时
		String factor =upholsterWokerDto.getFactor(); //工时系数
		Date startDate = upholsterWokerDto.getRoCreateDate();//开工时间
		Date expectDate = upholsterWokerDto.getRoCreateDate1();//预计完工时间
		Date endDate = upholsterWokerDto.getRoCreateDate2();//完工时间
		String finshedTag = upholsterWokerDto.getFinishedTag();//完工标志
		
		//isDxp//派工时间
	
		assignPO.setString("DEALER_CODE",dealerCode);
		assignPO.setString("SO_NO",id1);
		assignPO.setString("ITEM_ID",id);		
		if(!StringUtils.isNullOrEmpty(positionCode)){
			assignPO.setString("LABOUR_POSITION_CODE",positionCode);
		}
		if(!StringUtils.isNullOrEmpty(labourHour)){
			assignPO.setString("ASSIGN_LABOUR_HOUR",labourHour);
		}
		if(!StringUtils.isNullOrEmpty(factor)){
			assignPO.setString("LABOUR_FACTOR",factor);
		}
		if(!StringUtils.isNullOrEmpty(EmployeeName)){
			assignPO.setString("TECHNICIAN",EmployeeName);
		}
		if(!StringUtils.isNullOrEmpty(workType)){
			assignPO.setString("WORKER_TYPE_CODE",workType);
		}
		if(!StringUtils.isNullOrEmpty(startDate)){
			assignPO.setDate("ITEM_START_TIME",startDate);
		}
		if(!StringUtils.isNullOrEmpty(endDate)){
			assignPO.setDate("ITEM_END_TIME",endDate);
		}
		if(!StringUtils.isNullOrEmpty(expectDate)){
			assignPO.setDate("ESTIMATE_END_TIME",expectDate);
		}
		if(!StringUtils.isNullOrEmpty(finshedTag)){
			assignPO.setString("FINISHED_TAG",finshedTag);
		}
		assignPO.saveIt();
		
		//当装潢明细中的技师为多个时候，装潢项目中技师显示为“合派”
		String dealerCode1 = FrameworkUtil.getLoginInfo().getDealerCode();
		String sql = "select * from TT_SO_ASSIGN where SO_NO =  '"+id1+"'AND DEALER_CODE =  '"+dealerCode1+"' AND ITEM_ID =  '"+id+"' ";
		List<TtSoAssignPO> list = TtSoAssignPO.findBySQL(sql);
		System.err.println(list.size());
		if(list.size() > 1) {
			String sb = "select * from TT_SO_UPHOLSTER where SO_NO =  '"+id1+"'AND DEALER_CODE =  '"+dealerCode1+"' AND ITEM_ID =  '"+id+"' ";
			List<TtSoUpholsterPO> list1 = TtSoUpholsterPO.findBySQL(sb);
			upholsterPo = list1.get(0);
			upholsterPo.set("TECHNICIAN","合派");
			upholsterPo.saveIt();
		}
		
		return assignPO.getLongId();
	}

	@Override
	public PageInfoDto queryUpholsterWorker1(String id, String id1) throws ServiceBizException {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT * FROM TT_SO_ASSIGN WHERE  DEALER_CODE= '"+dealerCode+"'  AND  SO_NO = '"+id+"' AND item_id = '"+id1+"' ");
		List<Object> whereSql = new ArrayList<Object>();
		PageInfoDto page = DAOUtil.pageQuery(sb.toString(), whereSql);
		return page;
	}

	/**
	* 查询工种名称--分项派工
	* @author wangliang
	* @date 2017年04月5日
	*/
	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> queryWorkType(Map<String, String> queryParam) throws ServiceBizException {
		String sql = "SELECT * FROM TM_WORKER_TYPE ";
		List<Map> list = Base.findAll(sql);
		return list;
	}

	/**
	* 编辑分项派工-- 分项派工
	* @author wangliang
	* @date 2017年04月5日
	*/
	@SuppressWarnings("rawtypes")
	@Override
	public Map toEditProject(Map<String, String> queryParam, String id) throws ServiceBizException {
		StringBuffer sb = new StringBuffer();
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		sb.append(" SELECT t.*,t.TECHNICIAN as EMPLOYEE_NAME FROM TT_SO_ASSIGN t WHERE  DEALER_CODE= '"+dealerCode+"' AND ASSIGN_ID  = '"+id+"' ");
		//List<Map> list = Base.findAll(sb.toString());
		Map map = DAOUtil.findFirst(sb.toString(), null);
		return map;
	}
	
	/**
	* 修改分项派工-- 分项派工
	* @author wangliang
	* @date 2017年04月5日
	*/
	@Override
	public void update(Long id,Long id1 ,UpholsterWorkerDTO upholesterWorkerDto) throws ServiceBizException {
		TtSoAssignPO lap=TtSoAssignPO.findById(id);
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		String EmployeeName = upholesterWorkerDto.getEmployeeName();//派工技师
		String positionCode  =  upholesterWorkerDto.getPositionCode();//工位名称
		String workType = upholesterWorkerDto.getWorkTypeCode();//工种代码
		String labourHour = upholesterWorkerDto.getLabourHour();//分工工时
		String factor =upholesterWorkerDto.getFactor(); //工时系数
		Date time1 = upholesterWorkerDto.getRoCreateDate();//开工时间
		Date time2 = upholesterWorkerDto.getRoCreateDate1();//预计完成时间
		if(!StringUtils.isNullOrEmpty(positionCode)){
			lap.setString("LABOUR_POSITION_CODE",positionCode);
		}
		if(!StringUtils.isNullOrEmpty(labourHour)){
			lap.setString("ASSIGN_LABOUR_HOUR",labourHour);
		}
		if(!StringUtils.isNullOrEmpty(factor)){
			lap.setString("LABOUR_FACTOR",factor);
		}
		if(!StringUtils.isNullOrEmpty(EmployeeName)){
			lap.setString("TECHNICIAN",EmployeeName);
		}
		if(!StringUtils.isNullOrEmpty(workType)){
			lap.setString("WORKER_TYPE_CODE",workType);
		}
		if(!StringUtils.isNullOrEmpty(time1)){
			lap.setDate("ITEM_START_TIME",time1);
		}
		if(!StringUtils.isNullOrEmpty(time2)){
			lap.setDate("ESTIMATE_END_TIME",time2);
		}
		//如果完工标志没有勾选，则把完工时间置空
		lap.saveIt();
		
	
		//更新装潢项目
		//TtSoUpholsterPO ttRoLabourPO=TtSoUpholsterPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),id1);
		//TtSoUpholsterPO ttRoLabourPO=TtSoUpholsterPO.findById(id1);
		List<TtSoUpholsterPO> list = TtSoUpholsterPO.findBySQL("select * from Tt_So_Upholster where DEALER_CODE = '"+dealerCode+"' AND ITEM_ID = '"+id1+"'");
		TtSoUpholsterPO ttRoLabourPO = list.get(0);
		//派工标志
		if(!StringUtils.isNullOrEmpty(labourHour)){
			ttRoLabourPO.setString("ASSIGN_LABOUR_HOUR",labourHour );
		}
		if(!StringUtils.isNullOrEmpty(EmployeeName)){
			ttRoLabourPO.setString("TECHNICIAN", EmployeeName);
		}
		//???ttRoLabourPO.setAssignTag(Utility.getInt(assignTagL[i]));
		
		ttRoLabourPO.saveIt();
	}
	
	/**
	* 删除分项派工-- 分项派工
	* @author wangliang
	* @date 2017年04月5日
	*/
	 @Override
     public void deleteById(Long id) throws ServiceBizException{
	    TtSoAssignPO wtp=TtSoAssignPO.findById(id);
        wtp.delete();
     }
	 
	/**
	* 查询工位名称 -- 分项派工
	* @author wangliang
	* @date 2017年04月5日
	*/
	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> queryLabourPostion(Map<String, String> queryParam) throws ServiceBizException {
		StringBuffer sql = new StringBuffer(" SELECT * FROM TM_REPAIR_POSITION ");
		System.err.print(sql.toString());
		List<Map> list = Base.findAll(sql.toString());
		return list;
	}

	/**
	* 查询打印技师
	* @author wangliang
	* @date 2017年04月10日
	*/
	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> queryPrintTechnician(String id) throws ServiceBizException {
		StringBuffer sql = new StringBuffer(" SELECT DISTINCT  TECHNICIAN , EMPLOYEE_NO FROM TT_SO_ASSIGN A  LEFT JOIN TM_EMPLOYEE E ON e.EMPLOYEE_NAME =  A.TECHNICIAN WHERE A.SO_NO = '"+id+"' " );
		System.err.print(sql.toString());
		List<Map> list = Base.findAll(sql.toString());
		return list;
	}

	/**
	* 导出
	* @author wangliang
	* @date 2017年04月10日
	*/
	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> queryUpholsterWorkerExport(Map<String, String> queryParam) throws ServiceBizException {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
        String sql = "";
        TmDefaultParaPO po = TmDefaultParaPO.findByCompositeKeys(dealerCode,"3020");
		if(po.getString("DEFAULT_VALUE")!=null || !po.getString("DEFAULT_VALUE").trim().equals(DictCodeConstants.DICT_IS_YES.trim())){
		   sql = querySalesOrderByPayoff(queryParam);
		}
		if(po.getString("DEFAULT_VALUE")!=null || !po.getString("DEFAULT_VALUE").trim().equals(DictCodeConstants.DICT_IS_NO.trim())){
			 sql= queryUpholster(queryParam);
		}
		List<Object> insuranceSql =new ArrayList<Object>();
		List<Map> resultList = DAOUtil.findAll(sql.toString(), insuranceSql);
		OperateLogDto operateLogDto = new OperateLogDto();
		operateLogDto.setOperateContent("装潢派工导出");
		operateLogDto.setOperateType(DictCodeConstants.LOG_CUSTOMER_MANAGEMENT);
		operateLogService.writeOperateLog(operateLogDto);
		return resultList;
	}



}
