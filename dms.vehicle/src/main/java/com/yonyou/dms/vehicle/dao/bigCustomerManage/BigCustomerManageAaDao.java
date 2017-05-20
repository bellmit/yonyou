package com.yonyou.dms.vehicle.dao.bigCustomerManage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.itextpdf.text.log.SysoLogger;
import com.yonyou.dms.common.domains.PO.basedata.TtBigCustomerReportApprovalPO;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.StringUtils;
/**
 * 大客户管理Dao
 * @author ZhaoZ
 * updateBy zhengzengliang
 */
@SuppressWarnings({"rawtypes","unchecked"})
@Repository
public class BigCustomerManageAaDao extends OemBaseDAO{


	/**
	 * 根据状态查询大客户
	 */
	public PageInfoDto QueryCustomer(Map<String, String> queryParams, int flag) {
		List<Object> params = new ArrayList<Object>();
		Long week = getDayOfWeek();
		String sql = getQueryCustomerSql(queryParams, params,week,flag);
		
		sql = "SELECT * FROM (" +sql+")MM";
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;
	}

	/**
	 * 得到周数
	 * @return
	 */
	private Long getDayOfWeek() {
		
    	String sql = " SELECT CAST(WEEK_CODE AS SIGNED INT) AS WEEK_CODE FROM TM_WEEK " +
    			     " WHERE START_DATE <= NOW() AND END_DATE >= NOW() " +
    				 " AND YEAR_CODE = YEAR(NOW())  " ;
    	List<Map> list = OemDAOUtil.findAll(sql, null);
    	if(list!=null && list.size()!=0){
    		return (Long) list.get(0).get("WEEK_CODE");
    	}
		return null;
	}

	private String getQueryCustomerSql(Map<String, String> queryParams, List<Object> params,Long week,int flag) {
		 StringBuffer pasql = new StringBuffer("  SELECT distinct TBCFIB.WS_NO,"+week+" as curr_wk,TOR2.ORG_DESC BIG_ORG_NAME,TOR3.ORG_DESC ORG_NAME ," +
	        		" TM.DEALER_SHORTNAME AS DEALER_NAME,TM.DEALER_CODE," +
	        		" TUC.CUSTOMER_COMPANY_CODE," +
	        		" TUC.COMPANY_NAME," +
	        		" TUC.CUSTOMER_COMPANY_NAME,TUC.CUSTOMER_COMPANY_TYPE, " +
	        		" TBCFIB.PS_TYPE,TBCFIB.PS_APPLY_TYPE,TBCRA.REPORT_APPROVAL_STATUS,date_format(TBCRA.REPORT_DATE,'%Y-%m-%d') REPORT_DATE,(CASE WHEN TBCRA.SEND_EMAIL>0 AND TBCRA.SEND_EMAIL IS NOT NULL  THEN 10041001 ELSE 10041002 END ) AS SEND_EMAIL, ");
	        pasql.append(" TBCFIB.CUSTOMER_SUB_TYPE ");//客户细分类别
	        //pasql.append(" ,(CASE  WHEN TSI.UNSUB_WEEK_COUNT IS NULL THEN ? ELSE TSI.UNSUB_WEEK_COUNT END) UNSUB_WEEK_COUNT ");
	        //pasql.append(" ,(CASE  WHEN TSI.UNSUB_YEAR_WEEK_COUNT IS NULL THEN ? ELSE TSI.UNSUB_YEAR_WEEK_COUNT END) UNSUB_YEAR_WEEK_COUNT ");
	        pasql.append(" , TSI.UNSUB_WEEK_COUNT, TSI.UNSUB_YEAR_WEEK_COUNT, TSI.LAST_VISIT_WK ");
	        pasql.append(" ,TBCFIB.EMPLOYEE_TYPE,TBCFIB.PS_TYPE||'('||ifnull(TBCFIB.EMPLOYEE_TYPE,'99999999')||')' PS_TYPE1");
	        pasql.append(" ,date_format(TBCRA.REPORT_APPROVAL_DATE,'%Y-%m-%d') REPORT_APPROVAL_DATE,TBCRA.PS_SOURCE_APPLY_NUM_COUNT,date_format(TBCFIB.ESTIMATE_APPLY_TIME,'%Y-%m-%d') ESTIMATE_APPLY_TIME	");//报备总数量,预计申请时间
	        pasql.append(" FROM TT_UC_CUSTOMER TUC ");
	        pasql.append(" INNER JOIN TM_DEALER TM ON TUC.DEALER_CODE = TM.DEALER_CODE");
	        //pasql.append(" INNER JOIN TM_ORG TOG ON TM.COMPANY_ID = TOG.COMPANY_ID ");
	        pasql.append(" INNER JOIN TM_DEALER_ORG_RELATION TDOR ON TDOR.DEALER_ID = TM.DEALER_ID ");
	        pasql.append(" INNER JOIN TM_ORG  TOR3 ON (TOR3.ORG_ID = TDOR.ORG_ID AND TOR3.ORG_LEVEL = 3)");
	        pasql.append(" INNER JOIN TM_ORG TOR2 ON (TOR3.PARENT_ORG_ID = TOR2.ORG_ID AND TOR2.ORG_LEVEL = 2 ) ");
	        pasql.append(" INNER JOIN TT_BIG_CUSTOMER_REPORT_APPROVAL TBCRA ON TBCRA.CUSTOMER_COMPANY_CODE = TUC.CUSTOMER_COMPANY_CODE ");
	        pasql.append(" LEFT JOIN TT_BIG_CUSTOMER_FILING_BASE_INFO TBCFIB ON TBCFIB.CUSTOMER_COMPANY_CODE = TUC.CUSTOMER_COMPANY_CODE");
	        pasql.append(" LEFT JOIN TT_BIG_CUSTOMER_BATCH_SALE_INFO AS TBCBSI ON TBCBSI.WS_NO = TBCFIB.WS_NO AND TBCBSI.CUSTOMER_COMPANY_CODE = TUC.CUSTOMER_COMPANY_CODE");
	        pasql.append(" LEFT JOIN (SELECT DEALER_CODE,UNSUB_WEEK_COUNT, UNSUB_YEAR_WEEK_COUNT, LAST_VISIT_WK" +
	        		     " FROM TT_BIG_CUSTOMER_VISIT_STATISTIC_INFO WHERE YEAR_CODE = YEAR(NOW())) TSI " +
	        		     " ON TSI.DEALER_CODE = TBCRA.DEALER_CODE ");
	        pasql.append(" WHERE 1=1 ");
	        pasql.append(" AND TBCRA.ENABLE = ? ");
	        //params.add(week);
	        params.add(OemDictCodeConstants.STATUS_ENABLE); //有效
	        pasql.append(" AND TUC.CUSTOMER_BUSINESS_TYPE = ? ");
	        params.add(OemDictCodeConstants.IF_TYPE_YES);
	        
	        if(!StringUtils.isNullOrEmpty(queryParams.get("resourceScope"))){
	        	pasql.append(" AND TOR2.ORG_ID = ? ");
				params.add(queryParams.get("resourceScope"));
	    	}
	    	if(!StringUtils.isNullOrEmpty(queryParams.get("orgId"))){
				pasql.append(" AND TOR3.ORG_ID  = ? ");
				params.add(queryParams.get("orgId"));
	    	}
//	        if (!StringUtils.isNullOrEmpty(queryParams.get("dealerId"))) {
//	            pasql.append(" AND TM.DEALER_ID IN (?) "); //经销商代码
//	            params.add("%" +queryParams.get("dealerId") + "%");
//	        }
	        if (!StringUtils.isNullOrEmpty(queryParams.get("dealerCode"))) {
	                pasql.append(" AND TM.DEALER_CODE = ? "); //经销商代码
	                params.add( queryParams.get("dealerCode"));
	        }
	        
//	        if (!StringUtils.isNullOrEmpty(queryParams.get("dealerName"))) {
//	            pasql.append(" AND TM.DEALER_NAME LIKE ? "); //经销商名称
//	            params.add("%" + queryParams.get("dealerName") + "%");
//	        }
//	        if (!StringUtils.isNullOrEmpty(queryParams.get("bigCustomerCode"))) {
//	            pasql.append(" AND TUC.CUSTOMER_COMPANY_CODE = ? "); //大客户代码
//	            params.add(queryParams.get("bigCustomerCode"));
//	        }
	        if (!StringUtils.isNullOrEmpty(queryParams.get("bigCustomerName"))) {
	            pasql.append(" AND TUC.CUSTOMER_COMPANY_NAME LIKE ? "); //大客户名称
	            params.add("%" + queryParams.get("bigCustomerName") + "%");
	        }
	        if(!StringUtils.isNullOrEmpty(queryParams.get("reportedStartDate"))) {
	        	pasql.append(" and TBCRA.REPORT_DATE >= str_to_date('" + queryParams.get("reportedStartDate") + " 00:00:00','%Y-%m-%d %H:%i:%S')");
	        }
	        if(!StringUtils.isNullOrEmpty(queryParams.get("reportEndDate"))) {
	        	pasql.append(" and TBCRA.REPORT_DATE <= str_to_date('" + queryParams.get("reportEndDate") + " 23:59:59','%Y-%m-%d %H:%i:%S')");
	        }
	        
	        if (!StringUtils.isNullOrEmpty(queryParams.get("wsNo"))) {
	            pasql.append(" AND TBCRA.WS_NO LIKE ? "); //报备单号
	            params.add("%" + queryParams.get("wsNo") + "%");
	        }
	        switch (flag) {
				case 1:
			        pasql.append(" AND TBCRA.REPORT_APPROVAL_STATUS = ? ");
					params.add(OemDictCodeConstants.BIG_CUSTOMER_FILING_APPROVAL_TYPE_UNAPPROVED); //未审批
//			        pasql.append(" AND TBCRA.AREA_REPORT_APPROVAL_STATUS = ? AND (TBCRA.REPORT_APPROVAL_STATUS IS NULL OR TBCRA.REPORT_APPROVAL_STATUS = ?) ");
//					params.add(Constant.BIG_CUSTOMER_FILING_AREA_APPROVAL_TYPE_PASS); //总部未审批（区域审批通过）
//					params.add(Constant.BIG_CUSTOMER_FILING_APPROVAL_TYPE_UNAPPROVED); //总部未审批
					break;
				case 2:
			        pasql.append(" AND TBCRA.REPORT_APPROVAL_STATUS = ? ");
					params.add(OemDictCodeConstants.BIG_CUSTOMER_FILING_APPROVAL_TYPE_PASS); //已审批通过
					break;
				case 3:
			        pasql.append(" AND TBCRA.REPORT_APPROVAL_STATUS = ? ");
					params.add(OemDictCodeConstants.BIG_CUSTOMER_FILING_APPROVAL_TYPE_OVER); //审批驳回
					break;
				case 4:
			        pasql.append(" AND (TBCRA.REPORT_APPROVAL_STATUS = ? OR TBCRA.REPORT_APPROVAL_STATUS = ?)");
					params.add(OemDictCodeConstants.BIG_CUSTOMER_FILING_APPROVAL_TYPE_RUS); //审批拒绝
					params.add(OemDictCodeConstants.BIG_CUSTOMER_FILING_APPROVAL_TYPE_REPORT); //报备转拒绝
					break;
				default:
					break;
			}
	        
	      
	        
			return pasql.toString();
	}

	/**
	 * 大客户报备审批信息下载
	 * @param queryParams
	 * @param flag
	 * @return
	 */
	public List<Map> dlrFilingInfoExport(Map<String, String> queryParams, int flag) {
		List<Object> params = new ArrayList<Object>();
		String sql = getdlrFilingInfoExportSql(queryParams,params,flag);
		List<Map> list = OemDAOUtil.findAll(sql, params);
		return list;
	}

	private String getdlrFilingInfoExportSql(Map<String, String> queryParams,List<Object> params, int flag) {
		StringBuffer pasql = new StringBuffer();
        pasql.append("SELECT TOR2.ORG_DESC BIG_ORG_NAME  \n");
    	pasql.append("	,TOR3.ORG_DESC ORG_NAME  \n");
    	pasql.append(" 	,TM.DEALER_CODE  \n");
    	pasql.append("	,TM.DEALER_SHORTNAME AS DEALER_NAME  \n");
    	pasql.append("	,TBCFIB.WS_NO  \n");
    	pasql.append("	,date_format(TBCRA.REPORT_DATE,'%Y-%m-%d') AS RP_DATE \n");
    	pasql.append("	,date_format(TBCFIB.ESTIMATE_APPLY_TIME,'%Y-%m-%d') AS ESTIMATE_APPLY_TIME \n");
    	pasql.append("	,TBCFIB.PS_TYPE  \n");
    	pasql.append("	,TBCFIB.EMPLOYEE_TYPE  \n");
    	pasql.append("	,TBCFIB.CUSTOMER_SUB_TYPE  \n");
    	pasql.append("	,TUC.COMPANY_NAME  \n");
    	pasql.append("	,VM.MODEL_CODE  \n");
    	pasql.append("	,VM.MODEL_NAME  \n");
    	pasql.append("	,SUM(TBCBSI.PS_SOURCE_APPLY_NUM) AS PS_SOURCE_APPLY_NUM  \n");
    	pasql.append("	,TBCRA.REPORT_APPROVAL_STATUS \n");
    	pasql.append("	FROM TT_UC_CUSTOMER TUC \n");
    	pasql.append("		INNER JOIN TM_DEALER TM ON TUC.DEALER_CODE = TM.DEALER_CODE \n");
    	pasql.append("		INNER JOIN TM_DEALER_ORG_RELATION TDOR ON TDOR.DEALER_ID = TM.DEALER_ID \n");
    	pasql.append("		INNER JOIN TM_ORG  TOR3 ON (TOR3.ORG_ID = TDOR.ORG_ID AND TOR3.ORG_LEVEL = 3) \n");
    	pasql.append("		INNER JOIN TM_ORG TOR2 ON (TOR3.PARENT_ORG_ID = TOR2.ORG_ID AND TOR2.ORG_LEVEL = 2 ) \n");
    	pasql.append("		INNER JOIN TT_BIG_CUSTOMER_REPORT_APPROVAL TBCRA ON TBCRA.CUSTOMER_COMPANY_CODE = TUC.CUSTOMER_COMPANY_CODE \n");
    	pasql.append("		INNER JOIN TT_BIG_CUSTOMER_FILING_BASE_INFO TBCFIB ON TBCFIB.CUSTOMER_COMPANY_CODE = TUC.CUSTOMER_COMPANY_CODE \n");
    	pasql.append("		INNER JOIN TT_BIG_CUSTOMER_BATCH_SALE_INFO AS TBCBSI ON TBCBSI.WS_NO = TBCFIB.WS_NO AND TBCBSI.CUSTOMER_COMPANY_CODE = TUC.CUSTOMER_COMPANY_CODE \n");
    	pasql.append("		LEFT JOIN (select VM.BRAND_CODE , VM.SERIES_NAME, VM.MODEL_NAME, VM.SERIES_CODE,VM.MODEL_CODE, \n");
    	pasql.append("			VM.MATERIAL_CODE,VM.COLOR_CODE from ("+getVwMaterialSql()+") AS VM  \n");
    	pasql.append("				GROUP BY VM.BRAND_CODE , VM.SERIES_NAME, VM.MODEL_NAME, VM.SERIES_CODE,  \n");
    	pasql.append("					VM.MODEL_CODE,VM.MATERIAL_CODE,VM.COLOR_CODE \n");
    	pasql.append("				) VM ON TBCBSI.SALE_VHCL_BRAND = VM.BRAND_CODE  \n");
    	pasql.append("						AND TBCBSI.SALE_VHCL_SERIES = VM.SERIES_CODE \n");
    	pasql.append("						AND TBCBSI.SALE_VHCL_MODEL = VM.MODEL_CODE \n");
    	pasql.append("						AND TBCBSI.SALE_VHCL_PACKAGE = VM.MATERIAL_CODE \n");
    	pasql.append("						AND TBCBSI.SALE_VHCL_COLOR = VM.COLOR_CODE \n");
        pasql.append(" WHERE 1=1 ");
        pasql.append(" AND TBCRA.ENABLE = ? \n");
        params.add(OemDictCodeConstants.STATUS_ENABLE); //有效
        pasql.append(" AND TUC.CUSTOMER_BUSINESS_TYPE = ? \n");
        params.add(OemDictCodeConstants.IF_TYPE_YES);
        if(!StringUtils.isNullOrEmpty(queryParams.get("resourceScope"))){
			pasql.append(" AND TOR2.ORG_ID  = ? ");
			params.add(queryParams.get("resourceScope"));
    	}
    	if(!StringUtils.isNullOrEmpty(queryParams.get("orgId"))){
			pasql.append(" AND TOR3.ORG_ID  = ? ");
			params.add(queryParams.get("orgId"));
    	}
//        if (!StringUtils.isNullOrEmpty(queryParams.get("dealerId"))) {
//            pasql.append(" AND TM.DEALER_ID IN (?) "); //经销商代码
//            params.add("%" +queryParams.get("dealerId") + "%");
//        }
        if (!StringUtils.isNullOrEmpty(queryParams.get("dealerCode"))) {
                pasql.append(" AND TM.DEALER_CODE = ? "); //经销商代码
              
                params.add( queryParams.get("dealerCode"));
        }
        
//        if (!StringUtils.isNullOrEmpty(queryParams.get("dealerName"))) {
//            pasql.append(" AND TM.DEALER_NAME LIKE ? "); //经销商名称
//            params.add("%" + queryParams.get("dealerName") + "%");
//        }
//        if (!StringUtils.isNullOrEmpty(queryParams.get("bigCustomerCode"))) {
//            pasql.append(" AND TUC.CUSTOMER_COMPANY_CODE = ? "); //大客户代码
//            params.add(queryParams.get("bigCustomerCode"));
//        }
        if (!StringUtils.isNullOrEmpty(queryParams.get("bigCustomerName"))) {
            pasql.append(" AND TUC.CUSTOMER_COMPANY_NAME LIKE ? "); //大客户名称
            params.add("%" + queryParams.get("bigCustomerName") + "%");
        }
        if(!StringUtils.isNullOrEmpty(queryParams.get("reportedStartDate"))) {
        	pasql.append(" and TBCRA.REPORT_DATE >= str_to_date('" + queryParams.get("reportedStartDate") + " 00:00:00','%Y-%m-%d %H:%i:%S')");
        }
        if(!StringUtils.isNullOrEmpty(queryParams.get("reportEndDate"))) {
        	pasql.append(" and TBCRA.REPORT_DATE <= str_to_date('" + queryParams.get("reportEndDate") + " 23:59:59','%Y-%m-%d %H:%i:%S')");
        }
       
        if (!StringUtils.isNullOrEmpty(queryParams.get("wsNo"))) {
            pasql.append(" AND TBCRA.WS_NO LIKE ? "); //报备单号
            params.add("%" + queryParams.get("wsNo") + "%");
        }
        switch (flag) {
			case 1:
		        pasql.append(" AND TBCRA.REPORT_APPROVAL_STATUS = ? \n");
				params.add(OemDictCodeConstants.BIG_CUSTOMER_FILING_APPROVAL_TYPE_UNAPPROVED); //未审批
//				params.add(Constant.BIG_CUSTOMER_FILING_AREA_APPROVAL_TYPE_PASS); //总部未审批（区域审批通过）
				break;
			case 2:
		        pasql.append(" AND TBCRA.REPORT_APPROVAL_STATUS = ? \n");
				params.add(OemDictCodeConstants.BIG_CUSTOMER_FILING_APPROVAL_TYPE_PASS); //已审批通过
				break;
			case 3:
		        pasql.append(" AND TBCRA.REPORT_APPROVAL_STATUS = ? \n");
				params.add(OemDictCodeConstants.BIG_CUSTOMER_FILING_APPROVAL_TYPE_OVER); //审批驳回
				break;
			case 4:
		        pasql.append(" AND (TBCRA.REPORT_APPROVAL_STATUS = ? OR TBCRA.REPORT_APPROVAL_STATUS = ?)\n");
				params.add(OemDictCodeConstants.BIG_CUSTOMER_FILING_APPROVAL_TYPE_RUS); //审批拒绝
				params.add(OemDictCodeConstants.BIG_CUSTOMER_FILING_APPROVAL_TYPE_REPORT); //报备转拒绝
				break;
			default:
				break;
		}
    	pasql.append("			GROUP BY TOR2.ORG_DESC,TOR3.ORG_DESC,TM.DEALER_CODE,TM.DEALER_SHORTNAME,TBCFIB.WS_NO \n");
    	pasql.append("				,TBCRA.REPORT_DATE,TBCFIB.ESTIMATE_APPLY_TIME,TBCFIB.PS_TYPE,TBCFIB.EMPLOYEE_TYPE \n");
    	pasql.append("				,TBCFIB.CUSTOMER_SUB_TYPE,TUC.COMPANY_NAME,VM.MODEL_CODE,VM.MODEL_NAME,TBCRA.REPORT_APPROVAL_STATUS \n");
    	System.err.println(pasql.toString());
		return pasql.toString();
		/*StringBuffer pasql = new StringBuffer("  SELECT distinct TBCFIB.WS_NO,TOR2.ORG_DESC BIG_ORG_NAME,TOR3.ORG_DESC ORG_NAME ," +
	        		" TM.DEALER_SHORTNAME AS DEALER_NAME,TM.DEALER_CODE," +
	        		" TUC.CUSTOMER_COMPANY_CODE," +
	        		" TUC.COMPANY_NAME," +
	        		" TUC.CUSTOMER_COMPANY_NAME,TUC.CUSTOMER_COMPANY_TYPE, " +
	        		" TBCFIB.PS_TYPE,TBCFIB.PS_APPLY_TYPE,TBCRA.REPORT_APPROVAL_STATUS,date_format(TBCRA.REPORT_DATE,'%Y-%m-%d') REPORT_DATE,(CASE WHEN TBCRA.SEND_EMAIL>0 AND TBCRA.SEND_EMAIL IS NOT NULL  THEN 10041001 ELSE 10041002 END ) AS SEND_EMAIL, ");
	        pasql.append(" TBCFIB.CUSTOMER_SUB_TYPE ");//客户细分类别
	        pasql.append(" , TSI.UNSUB_WEEK_COUNT, TSI.UNSUB_YEAR_WEEK_COUNT, TSI.LAST_VISIT_WK ");
	        pasql.append(" ,TBCFIB.EMPLOYEE_TYPE,TBCFIB.PS_TYPE||'('||ifnull(TBCFIB.EMPLOYEE_TYPE,'99999999')||')' PS_TYPE1");
	        pasql.append(" ,date_format(TBCRA.REPORT_APPROVAL_DATE,'%Y-%m-%d') REPORT_APPROVAL_DATE,TBCRA.PS_SOURCE_APPLY_NUM_COUNT,date_format(TBCFIB.ESTIMATE_APPLY_TIME,'%Y-%m-%d') ESTIMATE_APPLY_TIME	");//报备总数量,预计申请时间
	        pasql.append(" FROM TT_UC_CUSTOMER TUC ");
	        pasql.append(" INNER JOIN TM_DEALER TM ON TUC.DEALER_CODE = TM.DEALER_CODE");
	        pasql.append(" INNER JOIN TM_DEALER_ORG_RELATION TDOR ON TDOR.DEALER_ID = TM.DEALER_ID ");
	        pasql.append(" INNER JOIN TM_ORG  TOR3 ON (TOR3.ORG_ID = TDOR.ORG_ID AND TOR3.ORG_LEVEL = 3)");
	        pasql.append(" INNER JOIN TM_ORG TOR2 ON (TOR3.PARENT_ORG_ID = TOR2.ORG_ID AND TOR2.ORG_LEVEL = 2 ) ");
	        pasql.append(" INNER JOIN TT_BIG_CUSTOMER_REPORT_APPROVAL TBCRA ON TBCRA.CUSTOMER_COMPANY_CODE = TUC.CUSTOMER_COMPANY_CODE ");
	        pasql.append(" LEFT JOIN TT_BIG_CUSTOMER_FILING_BASE_INFO TBCFIB ON TBCFIB.CUSTOMER_COMPANY_CODE = TUC.CUSTOMER_COMPANY_CODE");
	        pasql.append(" LEFT JOIN TT_BIG_CUSTOMER_BATCH_SALE_INFO AS TBCBSI ON TBCBSI.WS_NO = TBCFIB.WS_NO AND TBCBSI.CUSTOMER_COMPANY_CODE = TUC.CUSTOMER_COMPANY_CODE");
	        pasql.append(" LEFT JOIN (SELECT DEALER_CODE,UNSUB_WEEK_COUNT, UNSUB_YEAR_WEEK_COUNT, LAST_VISIT_WK" +
	        		     " FROM TT_BIG_CUSTOMER_VISIT_STATISTIC_INFO WHERE YEAR_CODE = YEAR(NOW())) TSI " +
	        		     " ON TSI.DEALER_CODE = TBCRA.DEALER_CODE ");
	        pasql.append(" WHERE 1=1 ");
	        pasql.append(" AND TBCRA.ENABLE = '"+OemDictCodeConstants.STATUS_ENABLE+"' ");
	       
	        pasql.append(" AND TUC.CUSTOMER_BUSINESS_TYPE = '"+OemDictCodeConstants.IF_TYPE_YES+"'");
	        
	        
	        if(!StringUtils.isNullOrEmpty(queryParams.get("resourceScope"))){
	        	pasql.append(" AND TOR2.ORG_ID = '"+queryParams.get("resourceScope")+"' ");
				
	    	}
	    	if(!StringUtils.isNullOrEmpty(queryParams.get("orgId"))){
				pasql.append(" AND TOR3.ORG_ID  = '"+params.add(queryParams.get("orgId"))+"' ");
				
	    	}

	        if (!StringUtils.isNullOrEmpty(queryParams.get("dealerCode"))) {
	                pasql.append(" AND TM.DEALER_CODE = '"+queryParams.get("dealerCode")+"' "); //经销商代码
	        }

	        if (!StringUtils.isNullOrEmpty(queryParams.get("bigCustomerName"))) {
	            pasql.append(" AND TUC.CUSTOMER_COMPANY_NAME LIKE %'" + queryParams.get("bigCustomerName") + "' % "); //大客户名称
	        }
	        if(!StringUtils.isNullOrEmpty(queryParams.get("reportedStartDate"))) {
	        	pasql.append(" and TBCRA.REPORT_DATE >= str_to_date('" + queryParams.get("reportedStartDate") + " 00:00:00','%Y-%m-%d %H:%i:%S')");
	        }
	        if(!StringUtils.isNullOrEmpty(queryParams.get("reportEndDate"))) {
	        	pasql.append(" and TBCRA.REPORT_DATE <= str_to_date('" + queryParams.get("reportEndDate") + " 23:59:59','%Y-%m-%d %H:%i:%S')");
	        }
	        
	        if (!StringUtils.isNullOrEmpty(queryParams.get("wsNo"))) {
	            pasql.append(" AND TBCRA.WS_NO = '" + queryParams.get("wsNo") + "'"); //报备单号
	            
	        }
	        switch (flag) {
				case 1:
			        pasql.append(" AND TBCRA.REPORT_APPROVAL_STATUS = '"+OemDictCodeConstants.BIG_CUSTOMER_FILING_APPROVAL_TYPE_UNAPPROVED+"' ");
					break;
				case 2:
			        pasql.append(" AND TBCRA.REPORT_APPROVAL_STATUS = '"+OemDictCodeConstants.BIG_CUSTOMER_FILING_APPROVAL_TYPE_PASS+"' ");
					
					break;
				case 3:
			        pasql.append(" AND TBCRA.REPORT_APPROVAL_STATUS = '"+OemDictCodeConstants.BIG_CUSTOMER_FILING_APPROVAL_TYPE_OVER+"' ");
				  
					break;
				case 4:
			        pasql.append(" AND (TBCRA.REPORT_APPROVAL_STATUS = '"+OemDictCodeConstants.BIG_CUSTOMER_FILING_APPROVAL_TYPE_RUS+"' OR TBCRA.REPORT_APPROVAL_STATUS = '"+OemDictCodeConstants.BIG_CUSTOMER_FILING_APPROVAL_TYPE_REPORT+"')");
					
					break;
				default:
					break;
			}
			return pasql.toString();
			*/
	        
			
    
	}


	/**
	 *  大客户报备审批明细信息下载
	 * @param queryParams
	 * @param flag
	 * @return
	 */
	public List<Map> dlrFilingInfoDetailExport(Map<String, String> queryParams, int flag) {
		List<Object> params = new ArrayList<Object>();
		String sql = getdlrFilingInfoDetailExportSql(queryParams,params,flag);
		List<Map> list = OemDAOUtil.findAll(sql, params);
		return list;
	}

	private String getdlrFilingInfoDetailExportSql(Map<String, String> queryParams, List<Object> params, int flag) {
		
    	/*
    	 * StringBuffer pasql = new StringBuffer();
    	 * pasql.append("SELECT TOR2.ORG_DESC BIG_ORG_NAME  \n");
    	pasql.append("	,TOR3.ORG_DESC ORG_NAME  \n");
    	pasql.append(" 	,TM.DEALER_CODE  \n");
    	pasql.append("	,TM.DEALER_SHORTNAME AS DEALER_NAME  \n");
    	pasql.append("	,TBCFIB.WS_NO  \n");
    	pasql.append("	,date_format(TBCRA.REPORT_DATE,'%Y-%m-%d') AS RP_DATE \n");
    	pasql.append("	,date_format(TBCFIB.ESTIMATE_APPLY_TIME,'%Y-%m-%d') AS ESTIMATE_APPLY_TIME \n");
    	pasql.append("	,TBCFIB.PS_TYPE  \n");
    	pasql.append("	,TBCFIB.EMPLOYEE_TYPE \n");
    	pasql.append("	,TBCFIB.CUSTOMER_SUB_TYPE  \n");
    	pasql.append("	,TUC.COMPANY_NAME  \n");
    	pasql.append("	,VM.MODEL_CODE  \n");
    	pasql.append("	,VM.MODEL_NAME  \n");
    	pasql.append("	,SUM(TBCBSI.PS_SOURCE_APPLY_NUM) AS PS_SOURCE_APPLY_NUM \n");
    	pasql.append("	,TBCRA.REPORT_APPROVAL_STATUS  \n");
    	pasql.append("	FROM TT_UC_CUSTOMER TUC \n");
    	pasql.append("		INNER JOIN TM_DEALER TM ON TUC.DEALER_CODE = TM.DEALER_CODE \n");
    	pasql.append("		INNER JOIN TM_DEALER_ORG_RELATION TDOR ON TDOR.DEALER_ID = TM.DEALER_ID \n");
    	pasql.append("		INNER JOIN TM_ORG  TOR3 ON (TOR3.ORG_ID = TDOR.ORG_ID AND TOR3.ORG_LEVEL = 3) \n");
    	pasql.append("		INNER JOIN TM_ORG TOR2 ON (TOR3.PARENT_ORG_ID = TOR2.ORG_ID AND TOR2.ORG_LEVEL = 2 ) \n");
    	pasql.append("		INNER JOIN TT_BIG_CUSTOMER_REPORT_APPROVAL TBCRA ON TBCRA.CUSTOMER_COMPANY_CODE = TUC.CUSTOMER_COMPANY_CODE \n");
    	pasql.append("		LEFT JOIN TT_BIG_CUSTOMER_FILING_BASE_INFO TBCFIB ON TBCFIB.CUSTOMER_COMPANY_CODE = TUC.CUSTOMER_COMPANY_CODE \n");
    	pasql.append("		LEFT JOIN TT_BIG_CUSTOMER_BATCH_SALE_INFO AS TBCBSI ON TBCBSI.WS_NO = TBCFIB.WS_NO AND TBCBSI.CUSTOMER_COMPANY_CODE = TUC.CUSTOMER_COMPANY_CODE \n");
    	pasql.append("		LEFT JOIN (select VM.BRAND_CODE , VM.SERIES_NAME, VM.MODEL_NAME, VM.SERIES_CODE,VM.MODEL_CODE, \n");
    	pasql.append("			VM.MATERIAL_CODE,VM.COLOR_CODE from ("+getVwMaterialSql()+") AS VM  \n");
    	pasql.append("				GROUP BY VM.BRAND_CODE , VM.SERIES_NAME, VM.MODEL_NAME, VM.SERIES_CODE,  \n");
    	pasql.append("					VM.MODEL_CODE,VM.MATERIAL_CODE,VM.COLOR_CODE \n");
    	pasql.append("				) VM ON TBCBSI.SALE_VHCL_BRAND = VM.BRAND_CODE  \n");
    	pasql.append("						AND TBCBSI.SALE_VHCL_SERIES = VM.SERIES_CODE \n");
    	pasql.append("						AND TBCBSI.SALE_VHCL_MODEL = VM.MODEL_CODE \n");
    	pasql.append("						AND TBCBSI.SALE_VHCL_PACKAGE = VM.MATERIAL_CODE \n");
    	pasql.append("						AND TBCBSI.SALE_VHCL_COLOR = VM.COLOR_CODE \n");
        pasql.append(" WHERE TBCRA.ENABLE = ? ");
        params.add(OemDictCodeConstants.STATUS_ENABLE); //有效
        if(!StringUtils.isNullOrEmpty(queryParams.get("resourceScope"))){
			pasql.append(" AND TOR2.ORG_ID  = ? ");
			params.add(queryParams.get("resourceScope"));
    	}
    	if(!StringUtils.isNullOrEmpty(queryParams.get("orgId"))){
			pasql.append(" AND TOR3.ORG_ID  = ? ");
			params.add(queryParams.get("orgId"));
    	}
//        if (!StringUtils.isNullOrEmpty(queryParams.get("dealerId"))) {
//            pasql.append(" AND TM.DEALER_ID IN (?) "); //经销商代码
//            params.add("%" +queryParams.get("dealerId") + "%");
//        }
        if (!StringUtils.isNullOrEmpty(queryParams.get("dealerCode"))) {
                pasql.append(" AND TM.DEALER_CODE = ? "); //经销商代码
              
                params.add( queryParams.get("dealerCode"));
        }
        
//        if (!StringUtils.isNullOrEmpty(queryParams.get("dealerName"))) {
//            pasql.append(" AND TM.DEALER_NAME LIKE ? "); //经销商名称
//            params.add("%" + queryParams.get("dealerName") + "%");
//        }
//        if (!StringUtils.isNullOrEmpty(queryParams.get("bigCustomerCode"))) {
//            pasql.append(" AND TUC.CUSTOMER_COMPANY_CODE = ? "); //大客户代码
//            params.add(queryParams.get("bigCustomerCode"));
//        }
        if (!StringUtils.isNullOrEmpty(queryParams.get("bigCustomerName"))) {
            pasql.append(" AND TUC.CUSTOMER_COMPANY_NAME LIKE ? "); //大客户名称
            params.add("%" + queryParams.get("bigCustomerName") + "%");
        }
        if(!StringUtils.isNullOrEmpty(queryParams.get("reportedStartDate"))) {
        	pasql.append(" and TBCRA.REPORT_DATE >= str_to_date('" + queryParams.get("reportedStartDate") + " 00:00:00','%Y-%m-%d %H:%i:%S')");
        }
        if(!StringUtils.isNullOrEmpty(queryParams.get("reportEndDate"))) {
        	pasql.append(" and TBCRA.REPORT_DATE <= str_to_date('" + queryParams.get("reportEndDate") + " 23:59:59','%Y-%m-%d %H:%i:%S')");
        }
        if (!StringUtils.isNullOrEmpty(queryParams.get("wsNo"))) {
            pasql.append(" AND TBCRA.WS_NO LIKE ? "); //报备单号
            params.add("%" + queryParams.get("wsNo") + "%");
        }
    	pasql.append("			GROUP BY TOR2.ORG_DESC,TOR3.ORG_DESC,TM.DEALER_CODE,TM.DEALER_SHORTNAME,TBCFIB.WS_NO \n");
    	pasql.append("				,TBCRA.REPORT_DATE,TBCFIB.ESTIMATE_APPLY_TIME,TBCFIB.PS_TYPE,TBCFIB.EMPLOYEE_TYPE \n");
    	pasql.append("				,TBCFIB.CUSTOMER_SUB_TYPE,TUC.COMPANY_NAME,VM.MODEL_CODE,VM.MODEL_NAME,TBCRA.REPORT_APPROVAL_STATUS \n");;*/
		 StringBuffer pasql = new StringBuffer("  SELECT distinct TBCFIB.WS_NO,TOR2.ORG_DESC BIG_ORG_NAME,TOR3.ORG_DESC ORG_NAME ," +
	        		" TM.DEALER_SHORTNAME AS DEALER_NAME,TM.DEALER_CODE," +
	        		" TUC.CUSTOMER_COMPANY_CODE," +
	        		" TUC.COMPANY_NAME," +
	        		" TUC.CUSTOMER_COMPANY_NAME,TUC.CUSTOMER_COMPANY_TYPE, " +
	        		" TBCFIB.PS_TYPE,TBCFIB.PS_APPLY_TYPE,TBCRA.REPORT_APPROVAL_STATUS,date_format(TBCRA.REPORT_DATE,'%Y-%m-%d') REPORT_DATE,(CASE WHEN TBCRA.SEND_EMAIL>0 AND TBCRA.SEND_EMAIL IS NOT NULL  THEN 10041001 ELSE 10041002 END ) AS SEND_EMAIL, ");
	        pasql.append(" TBCFIB.CUSTOMER_SUB_TYPE ");//客户细分类别
	        pasql.append(" , TSI.UNSUB_WEEK_COUNT, TSI.UNSUB_YEAR_WEEK_COUNT, TSI.LAST_VISIT_WK ");
	        pasql.append(" ,TBCFIB.EMPLOYEE_TYPE,TBCFIB.PS_TYPE||'('||ifnull(TBCFIB.EMPLOYEE_TYPE,'99999999')||')' PS_TYPE1");
	        pasql.append(" ,date_format(TBCRA.REPORT_APPROVAL_DATE,'%Y-%m-%d') REPORT_APPROVAL_DATE,TBCRA.PS_SOURCE_APPLY_NUM_COUNT,date_format(TBCFIB.ESTIMATE_APPLY_TIME,'%Y-%m-%d') ESTIMATE_APPLY_TIME	");//报备总数量,预计申请时间
	        pasql.append(" FROM TT_UC_CUSTOMER TUC ");
	        pasql.append(" INNER JOIN TM_DEALER TM ON TUC.DEALER_CODE = TM.DEALER_CODE");
	        pasql.append(" INNER JOIN TM_DEALER_ORG_RELATION TDOR ON TDOR.DEALER_ID = TM.DEALER_ID ");
	        pasql.append(" INNER JOIN TM_ORG  TOR3 ON (TOR3.ORG_ID = TDOR.ORG_ID AND TOR3.ORG_LEVEL = 3)");
	        pasql.append(" INNER JOIN TM_ORG TOR2 ON (TOR3.PARENT_ORG_ID = TOR2.ORG_ID AND TOR2.ORG_LEVEL = 2 ) ");
	        pasql.append(" INNER JOIN TT_BIG_CUSTOMER_REPORT_APPROVAL TBCRA ON TBCRA.CUSTOMER_COMPANY_CODE = TUC.CUSTOMER_COMPANY_CODE ");
	        pasql.append(" LEFT JOIN TT_BIG_CUSTOMER_FILING_BASE_INFO TBCFIB ON TBCFIB.CUSTOMER_COMPANY_CODE = TUC.CUSTOMER_COMPANY_CODE");
	        pasql.append(" LEFT JOIN TT_BIG_CUSTOMER_BATCH_SALE_INFO AS TBCBSI ON TBCBSI.WS_NO = TBCFIB.WS_NO AND TBCBSI.CUSTOMER_COMPANY_CODE = TUC.CUSTOMER_COMPANY_CODE");
	        pasql.append(" LEFT JOIN (SELECT DEALER_CODE,UNSUB_WEEK_COUNT, UNSUB_YEAR_WEEK_COUNT, LAST_VISIT_WK" +
	        		     " FROM TT_BIG_CUSTOMER_VISIT_STATISTIC_INFO WHERE YEAR_CODE = YEAR(NOW())) TSI " +
	        		     " ON TSI.DEALER_CODE = TBCRA.DEALER_CODE ");
	        pasql.append(" WHERE 1=1 ");
	        pasql.append(" AND TBCRA.ENABLE = '"+OemDictCodeConstants.STATUS_ENABLE+"' ");
	       
	        pasql.append(" AND TUC.CUSTOMER_BUSINESS_TYPE = '"+OemDictCodeConstants.IF_TYPE_YES+"'");
	        
	        
	        if(!StringUtils.isNullOrEmpty(queryParams.get("resourceScope"))){
	        	pasql.append(" AND TOR2.ORG_ID = '"+queryParams.get("resourceScope")+"' ");
				
	    	}
	    	if(!StringUtils.isNullOrEmpty(queryParams.get("orgId"))){
				pasql.append(" AND TOR3.ORG_ID  = '"+params.add(queryParams.get("orgId"))+"' ");
				
	    	}

	        if (!StringUtils.isNullOrEmpty(queryParams.get("dealerCode"))) {
	                pasql.append(" AND TM.DEALER_CODE = '"+queryParams.get("dealerCode")+"' "); //经销商代码
	        }

	        if (!StringUtils.isNullOrEmpty(queryParams.get("bigCustomerName"))) {
	            pasql.append(" AND TUC.CUSTOMER_COMPANY_NAME LIKE %'" + queryParams.get("bigCustomerName") + "' % "); //大客户名称
	        }
	        if(!StringUtils.isNullOrEmpty(queryParams.get("reportedStartDate"))) {
	        	pasql.append(" and TBCRA.REPORT_DATE >= str_to_date('" + queryParams.get("reportedStartDate") + " 00:00:00','%Y-%m-%d %H:%i:%S')");
	        }
	        if(!StringUtils.isNullOrEmpty(queryParams.get("reportEndDate"))) {
	        	pasql.append(" and TBCRA.REPORT_DATE <= str_to_date('" + queryParams.get("reportEndDate") + " 23:59:59','%Y-%m-%d %H:%i:%S')");
	        }
	        
	        if (!StringUtils.isNullOrEmpty(queryParams.get("wsNo"))) {
	            pasql.append(" AND TBCRA.WS_NO = '" + queryParams.get("wsNo") + "'"); //报备单号
	            
	        }
	        pasql.append("ORDER BY REPORT_APPROVAL_STATUS");
		    return pasql.toString();
	}
	/**
	 * 查询批售车辆信息
	 * @param queryParams
	 * @param tbcraPO
	 * @return
	 */
	public PageInfoDto customerBatchVhclInfoS(String wsno, TtBigCustomerReportApprovalPO tbcraPO) {

		List<Object> params = new ArrayList<Object>();
		
		StringBuffer pasql = new StringBuffer();
    	pasql.append(" SELECT VM.COLOR_NAME,VM.BRAND_NAME,VM.SERIES_NAME,VM.MODEL_NAME,VM.GROUP_CODE,TBCBSI.SALE_VHCL_PACKAGE_NAME MATERIAL_NAME, TBCBSI.PS_SOURCE_APPLY_NUM,TBCBSI.IS_RES_APPLY \n");
    	pasql.append(" FROM TT_BIG_CUSTOMER_BATCH_SALE_INFO AS TBCBSI, \n");
    	pasql.append(" ("+getVwMaterialSql()+") VM \n");
    	pasql.append(" WHERE TBCBSI.SALE_VHCL_BRAND = VM.BRAND_CODE \n");
    	pasql.append(" AND TBCBSI.SALE_VHCL_SERIES = VM.SERIES_CODE \n");
    	pasql.append(" AND TBCBSI.SALE_VHCL_MODEL = VM.MODEL_CODE \n");
    	pasql.append(" AND TBCBSI.SALE_VHCL_PACKAGE = VM.GROUP_CODE \n");
    	pasql.append(" AND TBCBSI.SALE_VHCL_PACKAGE_NAME = VM.GROUP_NAME \n");
    	pasql.append(" AND TBCBSI.SALE_VHCL_COLOR = VM.COLOR_CODE \n");
    	 System.out.println(tbcraPO.getString("CUSTOMER_COMPANY_CODE")+"asasasas============");
    	if(!StringUtils.isNullOrEmpty(tbcraPO.getString("CUSTOMER_COMPANY_CODE"))) {
    		 pasql.append(" AND TBCBSI.CUSTOMER_COMPANY_CODE = ? \n");
    	     params.add(tbcraPO.getString("CUSTOMER_COMPANY_CODE"));
        }
    	
    	pasql.append(" AND TBCBSI.WS_NO = ? \n");
    	params.add(wsno);
    	
    	pasql.append(" AND TBCBSI.ENABLE = ? \n");
        params.add(OemDictCodeConstants.STATUS_ENABLE); //有效
        System.out.println(tbcraPO.getString("DEALER_CODE")+"asasasas============");
        if(!StringUtils.isNullOrEmpty(tbcraPO.getString("DEALER_CODE"))) {
        	 pasql.append(" AND TBCBSI.DEALER_CODE = ? \n");
      	     params.add(tbcraPO.getString("DEALER_CODE"));
          }
       
        pasql.append(" GROUP BY VM.COLOR_NAME,VM.BRAND_NAME,VM.SERIES_NAME,VM.MODEL_NAME,VM.GROUP_CODE,TBCBSI.SALE_VHCL_PACKAGE_NAME,TBCBSI.PS_SOURCE_APPLY_NUM ,TBCBSI.IS_RES_APPLY ");
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(pasql.toString(), params);
		return pageInfoDto;
	}

	/**
     * 大客户返利审批查询
     * @param queryParams
     * @param type 
     * @return
     */
	public PageInfoDto applyforQuerys(Map<String, String> queryParams, int type) {
		
		List<Object> params = new ArrayList<Object>();
		
		String sql = getapplyforQuerysSql(queryParams, params,type);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;
	}

	private String getapplyforQuerysSql(Map<String, String> queryParams, List<Object> params, int type) {
		  StringBuffer pasql = new StringBuffer();
		  pasql.append(" SELECT * from ( \n");
	        pasql.append(" SELECT TOR2.ORG_DESC BIG_ORG_NAME,TOR3.ORG_DESC ORG_NAME ,\n");
	        pasql.append(" TM.DEALER_SHORTNAME AS DEALER_NAME,TM.DEALER_CODE,\n");
			pasql.append(" TUC.CUSTOMER_COMPANY_CODE,TUC.CUSTOMER_COMPANY_NAME,TUC.COMPANY_NAME,TUC.CUSTOMER_COMPANY_TYPE, \n");
			pasql.append(" TBCFIB.PS_TYPE,TBCRA.WS_NO,TBCRA.REBATE_APPROVAL_STATUS,date_format(TBCRA.REPORT_DATE,'%Y-%m-%d') REPORT_DATE ");
	        pasql.append(" ,date_format(TBCRA.REBATE_APPROVAL_DATE,'%Y-%m-%d') REBATE_APPROVAL_DATE,TBCFIB.EMPLOYEE_TYPE\n");
	        pasql.append(" ,TBCFIB.PS_TYPE||'('||ifnull(TBCFIB.EMPLOYEE_TYPE,'99999999')||')' PS_TYPE1,TBCRA.AMOUNT \n");
	        pasql.append(" ,TBCFIB.CUSTOMER_SUB_TYPE \n");
	        pasql.append("  FROM TT_UC_CUSTOMER TUC \n");
	        pasql.append(" INNER JOIN TM_DEALER TM ON TUC.DEALER_CODE = TM.DEALER_CODE \n");
	        //pasql.append(" INNER JOIN TM_ORG TOG ON TM.COMPANY_ID = TOG.COMPANY_ID \n");
	        pasql.append(" INNER JOIN TM_DEALER_ORG_RELATION TDOR ON TDOR.DEALER_ID = TM.DEALER_ID \n");
	        pasql.append(" INNER JOIN TM_ORG  TOR3 ON (TOR3.ORG_ID = TDOR.ORG_ID AND TOR3.ORG_LEVEL = 3)\n");
	        pasql.append(" INNER JOIN TM_ORG TOR2 ON (TOR3.PARENT_ORG_ID = TOR2.ORG_ID AND TOR2.ORG_LEVEL = 2 ) \n");
	        pasql.append(" INNER JOIN TT_BIG_CUSTOMER_REBATE_APPROVAL TBCRA ON TBCRA.BIG_CUSTOMER_CODE = TUC.CUSTOMER_COMPANY_CODE \n");
	        pasql.append(" LEFT JOIN TT_BIG_CUSTOMER_FILING_BASE_INFO TBCFIB ON TBCFIB.CUSTOMER_COMPANY_CODE = TUC.CUSTOMER_COMPANY_CODE\n");
	        
	        if(!StringUtils.isNullOrEmpty(queryParams.get("ctmName"))){
	        	pasql.append(" INNER JOIN TT_BIG_CUSTOMER_SALE_VHCL_INFO TSI ON TBCRA.BIG_CUSTOMER_CODE = TSI.CUSTOMER_COMPANY_CODE  \n");
	        	pasql.append(" INNER JOIN TM_VEHICLE_dec TV ON TV.VIN = TSI.VIN    \n");
	        	pasql.append(" INNER JOIN TT_VS_SALES_REPORT TVSR ON TV.VEHICLE_ID = TVSR.VEHICLE_ID  \n");
	        	pasql.append(" INNER JOIN TT_VS_CUSTOMER TVS ON TVS.CTM_ID = TVSR.CTM_ID  \n");
	        }
	        pasql.append(" WHERE 1=1 \n");
	        pasql.append(" AND TBCRA.ENABLE = ? \n");
	        params.add(OemDictCodeConstants.STATUS_ENABLE); //有效
	        pasql.append(" AND TUC.CUSTOMER_BUSINESS_TYPE = ? \n");
	        params.add(OemDictCodeConstants.IF_TYPE_YES);
	        if(!StringUtils.isNullOrEmpty(queryParams.get("resourceScope"))){
				pasql.append(" AND TOR2.ORG_ID  = ? ");
				params.add(queryParams.get("resourceScope"));
	    	}
	    	if(!StringUtils.isNullOrEmpty(queryParams.get("orgId"))){
				pasql.append(" AND TOR3.ORG_ID  = ? ");
				params.add(queryParams.get("orgId"));
	    	}

//  		if (!StringUtils.isNullOrEmpty(queryParams.get("dealerId"))) {
//      	pasql.append(" AND TM.DEALER_ID IN (?) "); //经销商代码
//      	params.add("%" +queryParams.get("dealerId") + "%");
//  		}
	    	if (!StringUtils.isNullOrEmpty(queryParams.get("dealerCode"))) {
	    	pasql.append(" AND TM.DEALER_CODE = ? "); //经销商代码
        
	    	params.add( queryParams.get("dealerCode"));
	    	}
  
//  		if (!StringUtils.isNullOrEmpty(queryParams.get("dealerName"))) {
//      	pasql.append(" AND TM.DEALER_NAME LIKE ? "); //经销商名称
//      	params.add("%" + queryParams.get("dealerName") + "%");
//  		}
	    	if (!StringUtils.isNullOrEmpty(queryParams.get("bigCustomerCode"))) {
		    	pasql.append(" AND TUC.CUSTOMER_COMPANY_CODE = ? "); //大客户代码
		    	params.add(queryParams.get("bigCustomerCode"));
		    	}
		    if (!StringUtils.isNullOrEmpty(queryParams.get("ctmName"))) {
		    		 pasql.append(" AND TVS.CTM_NAME LIKE ? \n"); //开票名称
		    		 params.add("%" +queryParams.get("ctmName")+"%");
			    }
	    	if(!StringUtils.isNullOrEmpty(queryParams.get("reportedStartDate"))) {
	        	pasql.append(" and TBCRA.REPORT_DATE >= str_to_date('" + queryParams.get("reportedStartDate") + " 00:00:00','%Y-%m-%d %H:%i:%S')");
	        }
	        if(!StringUtils.isNullOrEmpty(queryParams.get("reportEndDate"))) {
	        	pasql.append(" and TBCRA.REPORT_DATE <= str_to_date('" + queryParams.get("reportEndDate") + " 23:59:59','%Y-%m-%d %H:%i:%S')");
	        }
	        if (!StringUtils.isNullOrEmpty(queryParams.get("wsNo"))) {
	            pasql.append(" AND TBCRA.WS_NO LIKE ? "); //报备单号
	            params.add("%" + queryParams.get("wsNo") + "%");
	        }
	       
	        if (!StringUtils.isNullOrEmpty(queryParams.get("companyName"))) {
	        	pasql.append(" AND TUC.COMPANY_NAME LIKE ? \n"); //公司名称
	            params.add("%" + queryParams.get("companyName") + "%");
	        }
	        switch (type) {
				case 1:
					pasql.append(" AND TBCRA.REBATE_APPROVAL_STATUS = ? ");
					params.add(OemDictCodeConstants.BIG_CUSTOMER_REBATE_APPROVAL_TYPE_UNAPPROVED); //未审批
					break;
				case 2:
					pasql.append(" AND TBCRA.REBATE_APPROVAL_STATUS = ? ");
					params.add(OemDictCodeConstants.BIG_CUSTOMER_REBATE_APPROVAL_TYPE_PASS); //已审批通过
					break;
				case 3:
					pasql.append(" AND TBCRA.REBATE_APPROVAL_STATUS = ? ");
					params.add(OemDictCodeConstants.BIG_CUSTOMER_REBATE_APPROVAL_TYPE_OVER); //审批驳回
					break;
				case 4:
					pasql.append(" AND ( TBCRA.REBATE_APPROVAL_STATUS = ?  OR TBCRA.REBATE_APPROVAL_STATUS = ?  OR TBCRA.REBATE_APPROVAL_STATUS = ? ) ");
					params.add(OemDictCodeConstants.BIG_CUSTOMER_REBATE_APPROVAL_TYPE_RUS); //审批拒绝
					params.add(OemDictCodeConstants.BIG_CUSTOMER_REBATE_APPROVAL_TYPE_SYSTEM_RUS); //系统转拒绝
					params.add(OemDictCodeConstants.BIG_CUSTOMER_REBATE_APPROVAL_TYPE_OVER_RUS); //驳回转拒绝
					break;
				case 5:
					pasql.append(" AND TBCRA.REBATE_APPROVAL_STATUS = ? ");
					params.add(OemDictCodeConstants.BIG_CUSTOMER_REBATE_APPROVAL_TYPE_COMPLETE_INFORMATION); //资料完整
					break;
				default:
					break;
			}
	        pasql.append("	GROUP BY TOR2.ORG_DESC,TOR3.ORG_DESC ,TM.DEALER_SHORTNAME,TM.DEALER_CODE, \n");
	        pasql.append("		TUC.CUSTOMER_COMPANY_CODE,TUC.CUSTOMER_COMPANY_NAME,TUC.COMPANY_NAME,TUC.CUSTOMER_COMPANY_TYPE,  \n");
	        pasql.append("	 	TBCFIB.PS_TYPE,TBCRA.WS_NO,TBCRA.REBATE_APPROVAL_STATUS,TBCRA.REPORT_DATE  \n");
	        pasql.append("	 	,TBCRA.REBATE_APPROVAL_DATE,TBCFIB.EMPLOYEE_TYPE \n");
	        pasql.append("	 	,TBCFIB.PS_TYPE,TBCRA.AMOUNT,TBCFIB.CUSTOMER_SUB_TYPE \n");
	        pasql.append(" ORDER BY TBCRA.REPORT_DATE DESC \n");
	        pasql.append(" ) dcs \n");
	        return pasql.toString();
	}

	/**
	 *  经销商报备审批信息下载
	 * @param queryParams
	 * @param flag
	 * @return
	 */
	public List<Map> rebateApprovalInfoExport(Map<String, String> queryParams, int flag) {
		List<Object> params = new ArrayList<Object>();
		String sql = getrebateApprovalInfoExportSql(queryParams,params,flag);
		List<Map> list = OemDAOUtil.findAll(sql, params);
		return list;
	}

	private String getrebateApprovalInfoExportSql(Map<String, String> queryParams, List<Object> params, int flag) {
		  StringBuffer pasql = new StringBuffer("");
	    	pasql.append("SELECT \n");
	    	pasql.append("	TOR2.ORG_DESC BIG_ORG_NAME \n");
	    	pasql.append("	,TOR3.ORG_DESC ORG_NAME \n");
	    	pasql.append("	,TM.DEALER_CODE \n");
	    	pasql.append("	,TM.DEALER_SHORTNAME \n");
	    	pasql.append("	,TBCRA.WS_NO \n");
	    	pasql.append("	,TBCRA.REBATE_APPROVAL_STATUS \n");
//	    	pasql.append("	,date_format(TBCRA.REBATE_APPROVAL_DATE,'%Y-%m-%d') AS REBATE_APPROVAL_DATE--报备日期  \n");
	    	pasql.append("	,date_format(TBCRTA.REPORT_DATE,'%Y-%m-%d') AS REBATE_APPROVAL_DATE  \n");
	    	pasql.append("	,date_format(TBCRA.REPORT_DATE,'%Y-%m-%d') AS RP_DATE\n");
	    	pasql.append("	,TBCFIB.PS_TYPE \n");
	    	pasql.append("	,TBCFIB.EMPLOYEE_TYPE \n");
	    	pasql.append("	,TBCFIB.CUSTOMER_SUB_TYPE  \n");
	    	pasql.append("	,TBCRA.AMOUNT \n");
	    	pasql.append("	,TUC.COMPANY_NAME\n");
	    	pasql.append("	,TVS.CTM_NAME \n");
	    	pasql.append("	,TUL.LM_CELLPHONE\n");
	    	pasql.append("	,TSI.VIN \n");
	    	pasql.append("	,date_format(TVN.UPDATE_DATE,'%Y-%m-%d') AS UPDATE_DATE \n");
	    	pasql.append("	,date_format(TSI.INVOICE_DATE,'%Y-%m-%d') AS INVOICE_DATE \n");
	    	pasql.append("	,VM.MODEL_CODE \n");
	    	pasql.append("	,VM.MODEL_NAME \n");
	    	pasql.append("	,TV.RETAIL_PRICE \n");
	    	pasql.append("	,TV.VEHICLE_USAGE \n");
	    	pasql.append("		FROM TT_UC_CUSTOMER TUC \n");
	    	pasql.append("			INNER JOIN TT_UC_LINKMAN TUL ON TUC.CUSTOMER_ID = TUL.CUSTOMER_ID \n");
	    	pasql.append("			INNER JOIN TM_DEALER TM ON TUC.DEALER_CODE = TM.DEALER_CODE \n");
	    	pasql.append("			INNER JOIN TM_DEALER_ORG_RELATION TDOR ON TDOR.DEALER_ID = TM.DEALER_ID \n");
	    	pasql.append("			INNER JOIN TM_ORG  TOR3 ON (TOR3.ORG_ID = TDOR.ORG_ID AND TOR3.ORG_LEVEL = 3) \n");
	    	pasql.append("			INNER JOIN TM_ORG TOR2 ON (TOR3.PARENT_ORG_ID = TOR2.ORG_ID AND TOR2.ORG_LEVEL = 2 ) \n");
	    	pasql.append(" 			INNER JOIN TT_BIG_CUSTOMER_REPORT_APPROVAL TBCRTA ON TBCRTA.CUSTOMER_COMPANY_CODE = TUC.CUSTOMER_COMPANY_CODE ");
	    	pasql.append("			INNER JOIN TT_BIG_CUSTOMER_REBATE_APPROVAL TBCRA ON TBCRA.BIG_CUSTOMER_CODE = TUC.CUSTOMER_COMPANY_CODE \n");
	    	pasql.append("			INNER JOIN TT_BIG_CUSTOMER_SALE_VHCL_INFO TSI ON (TSI.CUSTOMER_COMPANY_CODE = TUC.CUSTOMER_COMPANY_CODE AND TSI.WS_NO = TBCRA.WS_NO)  \n");
	    	pasql.append("			LEFT JOIN TT_BIG_CUSTOMER_FILING_BASE_INFO TBCFIB ON TBCFIB.CUSTOMER_COMPANY_CODE = TUC.CUSTOMER_COMPANY_CODE \n");
	    	pasql.append("			INNER JOIN TM_VEHICLE_dec TV ON TV.VIN = TSI.VIN \n");
	    	pasql.append("			INNER JOIN ("+getVwMaterialSql()+") VM ON VM.MATERIAL_ID = TV. MATERIAL_ID \n");
	    	pasql.append("			INNER JOIN TT_VS_SALES_REPORT TVSR ON TV.VEHICLE_ID = TVSR.VEHICLE_ID \n");
	    	pasql.append("			INNER JOIN TT_VS_CUSTOMER TVS ON TVS.CTM_ID = TVSR.CTM_ID \n");
	    	pasql.append("			LEFT JOIN TT_VS_NVDR TVN ON TVN.VIN = TSI.VIN \n");
	        pasql.append(" WHERE TBCRA.ENABLE = ? AND TVSR.STATUS=?");
	        params.add(OemDictCodeConstants.STATUS_ENABLE); //有效
	        params.add(OemDictCodeConstants.STATUS_ENABLE); //有效
	        pasql.append(" AND TUC.CUSTOMER_BUSINESS_TYPE = ? ");
	        params.add(OemDictCodeConstants.IF_TYPE_YES);
	        if(!StringUtils.isNullOrEmpty(queryParams.get("resourceScope"))){
				pasql.append(" AND TOR2.ORG_ID  = ? ");
				params.add(queryParams.get("resourceScope"));
	    	}
	    	if(!StringUtils.isNullOrEmpty(queryParams.get("orgId"))){
				pasql.append(" AND TOR3.ORG_ID  = ? ");
				params.add(queryParams.get("orgId"));
	    	}
//	    	if (!StringUtils.isNullOrEmpty(queryParams.get("dealerId"))) {
//	      	pasql.append(" AND TM.DEALER_ID IN (?) "); //经销商代码
//	      	params.add("%" +queryParams.get("dealerId") + "%");
//	  		}
		    	if (!StringUtils.isNullOrEmpty(queryParams.get("dealerCode"))) {
		    		pasql.append(" AND TM.DEALER_CODE = ? "); //经销商代码
	        
		    		params.add( queryParams.get("dealerCode"));
		    	}
	  
//	  		if (!StringUtils.isNullOrEmpty(queryParams.get("dealerName"))) {
//	      	pasql.append(" AND TM.DEALER_NAME LIKE ? "); //经销商名称
//	      	params.add("%" + queryParams.get("dealerName") + "%");
//	  		}
		    if (!StringUtils.isNullOrEmpty(queryParams.get("bigCustomerCode"))) {
			    	pasql.append(" AND TUC.CUSTOMER_COMPANY_CODE = ? "); //大客户代码
			    	params.add(queryParams.get("bigCustomerCode"));
			    }
	        /*if (Utility.testIsNotNull(bigCustomerName)) {
	            pasql.append(" AND TUC.CUSTOMER_COMPANY_NAME LIKE ? "); //大客户名称
	            params.add("%" + dealerName + "%");
	        }*/

	        if (!StringUtils.isNullOrEmpty(queryParams.get("ctmName"))) {
	    		 pasql.append(" AND TVS.CTM_NAME LIKE ? \n"); //开票名称
	    		 params.add("%" +queryParams.get("ctmName")+"%");
		    }
	        if (!StringUtils.isNullOrEmpty(queryParams.get("companyName"))) {
	        	pasql.append(" AND TUC.COMPANY_NAME LIKE ? \n"); //公司名称
	            params.add("%" + queryParams.get("companyName") + "%");
	        }
	        if (!StringUtils.isNullOrEmpty(queryParams.get("wsNo"))) {
	            pasql.append(" AND TBCRA.WS_NO LIKE ? "); //报备单号
	            params.add("%" + queryParams.get("wsNo") + "%");
	        }
	    	if(!StringUtils.isNullOrEmpty(queryParams.get("reportedStartDate"))) {
	        	pasql.append(" and TBCRA.REPORT_DATE >= str_to_date('" + queryParams.get("reportedStartDate") + " 00:00:00','%Y-%m-%d %H:%i:%S')");
	        }
	        if(!StringUtils.isNullOrEmpty(queryParams.get("reportEndDate"))) {
	        	pasql.append(" and TBCRA.REPORT_DATE <= str_to_date('" + queryParams.get("reportEndDate") + " 23:59:59','%Y-%m-%d %H:%i:%S')");
	        }
	        switch (flag) {
				case 1:
					pasql.append(" AND TBCRA.REBATE_APPROVAL_STATUS = ? ");
					params.add(OemDictCodeConstants.BIG_CUSTOMER_REBATE_APPROVAL_TYPE_UNAPPROVED); //未审批
					break;
				case 2:
					pasql.append(" AND TBCRA.REBATE_APPROVAL_STATUS = ? ");
					params.add(OemDictCodeConstants.BIG_CUSTOMER_REBATE_APPROVAL_TYPE_PASS); //已审批通过
					break;
				case 3:
					pasql.append(" AND TBCRA.REBATE_APPROVAL_STATUS = ? ");
					params.add(OemDictCodeConstants.BIG_CUSTOMER_REBATE_APPROVAL_TYPE_OVER); //审批驳回
					break;
				case 4:
					pasql.append(" AND ( TBCRA.REBATE_APPROVAL_STATUS = ?  OR TBCRA.REBATE_APPROVAL_STATUS = ? OR TBCRA.REBATE_APPROVAL_STATUS = ? ) ");
					params.add(OemDictCodeConstants.BIG_CUSTOMER_REBATE_APPROVAL_TYPE_RUS); //审批拒绝
					params.add(OemDictCodeConstants.BIG_CUSTOMER_REBATE_APPROVAL_TYPE_SYSTEM_RUS); //系统转拒绝
					params.add(OemDictCodeConstants.BIG_CUSTOMER_REBATE_APPROVAL_TYPE_OVER_RUS); //驳回转拒绝
					break;
				case 5:
					pasql.append(" AND TBCRA.REBATE_APPROVAL_STATUS = ? ");
					params.add(OemDictCodeConstants.BIG_CUSTOMER_REBATE_APPROVAL_TYPE_COMPLETE_INFORMATION); //资料完整
					break;
				default:
					break;
			}
	        System.err.println(pasql.toString());
			return pasql.toString();
	}
	
	/**
	 * 大客户申请明细下载
	 * @param queryParams
	 * @return
	 */
	public List<Map> rebateApprovalInfoDetailExport(Map<String, String> queryParams) {
		List<Object> params = new ArrayList<Object>();
		String sql = getebateApprovalInfoDetailExportSql(queryParams,params);
		List<Map> list = OemDAOUtil.findAll(sql, params);
		return list;
	}

	private String getebateApprovalInfoDetailExportSql(Map<String, String> queryParams, List<Object> params) {
		StringBuffer pasql = new StringBuffer();
    	pasql.append("SELECT \n");
    	pasql.append("	TOR2.ORG_DESC BIG_ORG_NAME\n");
    	pasql.append("	,TOR3.ORG_DESC ORG_NAME \n");
    	pasql.append("	,TM.DEALER_CODE\n");
    	pasql.append("	,TM.DEALER_SHORTNAME \n");
    	pasql.append("	,TBCRA.WS_NO\n");
    	pasql.append("	,TBCRA.REBATE_APPROVAL_STATUS \n");
//    	pasql.append("	,date_format(TBCRA.REBATE_APPROVAL_DATE,'%Y-%m-%d') AS REBATE_APPROVAL_DATE--报备日期  \n");
    	pasql.append("	,date_format(TBCRTA.REPORT_DATE,'%Y-%m-%d') AS REBATE_APPROVAL_DATE\n");
    	pasql.append("	,date_format(TBCRA.REPORT_DATE,'%Y-%m-%d') AS RP_DATE \n");
    	pasql.append("	,TBCFIB.PS_TYPE \n");
    	pasql.append("	,TBCFIB.EMPLOYEE_TYPE \n");
    	pasql.append("	,TBCFIB.CUSTOMER_SUB_TYPE  \n");
    	pasql.append("	,TBCRA.AMOUNT \n");
    	pasql.append("	,TUC.COMPANY_NAME \n");
    	pasql.append("	,TVS.CTM_NAME \n");
    	pasql.append("	,TUL.LM_CELLPHONE \n");
    	pasql.append("	,TSI.VIN \n");
    	pasql.append("	,date_format(TVN.UPDATE_DATE,'%Y-%m-%d') AS UPDATE_DATE \n");
    	pasql.append("	,date_format(TSI.INVOICE_DATE,'%Y-%m-%d') AS INVOICE_DATE \n");
    	pasql.append("	,VM.MODEL_CODE \n");
    	pasql.append("	,VM.MODEL_NAME \n");
    	pasql.append("	,TV.RETAIL_PRICE \n");
    	pasql.append("	,TV.VEHICLE_USAGE \n");
    	pasql.append("		FROM TT_UC_CUSTOMER TUC \n");
    	pasql.append("			INNER JOIN TT_UC_LINKMAN TUL ON TUC.CUSTOMER_ID = TUL.CUSTOMER_ID \n");
    	pasql.append("			INNER JOIN TM_DEALER TM ON TUC.DEALER_CODE = TM.DEALER_CODE \n");
    	pasql.append("			INNER JOIN TM_DEALER_ORG_RELATION TDOR ON TDOR.DEALER_ID = TM.DEALER_ID \n");
    	pasql.append("			INNER JOIN TM_ORG  TOR3 ON (TOR3.ORG_ID = TDOR.ORG_ID AND TOR3.ORG_LEVEL = 3) \n");
    	pasql.append("			INNER JOIN TM_ORG TOR2 ON (TOR3.PARENT_ORG_ID = TOR2.ORG_ID AND TOR2.ORG_LEVEL = 2 ) \n");
    	pasql.append(" 			INNER JOIN TT_BIG_CUSTOMER_REPORT_APPROVAL TBCRTA ON TBCRTA.CUSTOMER_COMPANY_CODE = TUC.CUSTOMER_COMPANY_CODE ");
    	pasql.append("			INNER JOIN TT_BIG_CUSTOMER_REBATE_APPROVAL TBCRA ON TBCRA.BIG_CUSTOMER_CODE = TUC.CUSTOMER_COMPANY_CODE \n");
    	pasql.append("			INNER JOIN TT_BIG_CUSTOMER_SALE_VHCL_INFO TSI ON (TSI.CUSTOMER_COMPANY_CODE = TUC.CUSTOMER_COMPANY_CODE AND TSI.WS_NO = TBCRA.WS_NO)  \n");
    	pasql.append("			LEFT JOIN TT_BIG_CUSTOMER_FILING_BASE_INFO TBCFIB ON TBCFIB.CUSTOMER_COMPANY_CODE = TUC.CUSTOMER_COMPANY_CODE \n");
    	pasql.append("			INNER JOIN TM_VEHICLE_dec TV ON TV.VIN = TSI.VIN \n");
    	pasql.append("			INNER JOIN ("+getVwMaterialSql()+") VM ON VM.MATERIAL_ID = TV. MATERIAL_ID \n");
    	pasql.append("			INNER JOIN TT_VS_SALES_REPORT TVSR ON TV.VEHICLE_ID = TVSR.VEHICLE_ID \n");
    	pasql.append("			INNER JOIN TT_VS_CUSTOMER TVS ON TVS.CTM_ID = TVSR.CTM_ID \n");
    	pasql.append("			LEFT JOIN TT_VS_NVDR TVN ON TVN.VIN = TSI.VIN \n");
        pasql.append(" WHERE TBCRA.ENABLE = ? AND TVSR.STATUS=?");
        params.add(OemDictCodeConstants.STATUS_ENABLE); //有效
        params.add(OemDictCodeConstants.STATUS_ENABLE); //有效
        pasql.append(" AND TUC.CUSTOMER_BUSINESS_TYPE = ? \n");
        params.add(OemDictCodeConstants.IF_TYPE_YES);
        if(!StringUtils.isNullOrEmpty(queryParams.get("resourceScope"))){
			pasql.append(" AND TOR2.ORG_ID  = ? ");
			params.add(queryParams.get("resourceScope"));
    	}
    	if(!StringUtils.isNullOrEmpty(queryParams.get("orgId"))){
			pasql.append(" AND TOR3.ORG_ID  = ? ");
			params.add(queryParams.get("orgId"));
    	}
    	//if (!StringUtils.isNullOrEmpty(queryParams.get("dealerId"))) {
//      	pasql.append(" AND TM.DEALER_ID IN (?) "); //经销商代码
//      	params.add("%" +queryParams.get("dealerId") + "%");
//  		}
	    	if (!StringUtils.isNullOrEmpty(queryParams.get("dealerCode"))) {
	    		pasql.append(" AND TM.DEALER_CODE = ? "); //经销商代码
        
	    		params.add( queryParams.get("dealerCode"));
	    	}
  
//  		if (!StringUtils.isNullOrEmpty(queryParams.get("dealerName"))) {
//      	pasql.append(" AND TM.DEALER_NAME LIKE ? "); //经销商名称
//      	params.add("%" + queryParams.get("dealerName") + "%");
//  		}
	    if (!StringUtils.isNullOrEmpty(queryParams.get("bigCustomerCode"))) {
		    	pasql.append(" AND TUC.CUSTOMER_COMPANY_CODE = ? "); //大客户代码
		    	params.add(queryParams.get("bigCustomerCode"));
		    }
        /*if (Utility.testIsNotNull(bigCustomerName)) {
            pasql.append(" AND TUC.CUSTOMER_COMPANY_NAME LIKE ? "); //大客户名称
            params.add("%" + dealerName + "%");
        }*/

        if (!StringUtils.isNullOrEmpty(queryParams.get("ctmName"))) {
    		 pasql.append(" AND TVS.CTM_NAME LIKE ? \n"); //开票名称
    		 params.add("%" +queryParams.get("ctmName")+"%");
	    }
        if (!StringUtils.isNullOrEmpty(queryParams.get("companyName"))) {
        	pasql.append(" AND TUC.COMPANY_NAME LIKE ? \n"); //公司名称
            params.add("%" + queryParams.get("companyName") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParams.get("wsNo"))) {
            pasql.append(" AND TBCRA.WS_NO LIKE ? "); //报备单号
            params.add("%" + queryParams.get("wsNo") + "%");
        }
    	if(!StringUtils.isNullOrEmpty(queryParams.get("reportedStartDate"))) {
        	pasql.append(" and TBCRA.REPORT_DATE >= str_to_date('" + queryParams.get("reportedStartDate") + " 00:00:00','%Y-%m-%d %H:%i:%S')");
        }
        if(!StringUtils.isNullOrEmpty(queryParams.get("reportEndDate"))) {
        	pasql.append(" and TBCRA.REPORT_DATE <= str_to_date('" + queryParams.get("reportEndDate") + " 23:59:59','%Y-%m-%d %H:%i:%S')");
        }
	
    	
		return pasql.toString();
	}

	public Map<String, Object> QueryCustomer(String wsno, int flag) {
		Long week = getDayOfWeek();
		String sql = getQueryCustomerSql(wsno,week,flag);
		List<Map> list = OemDAOUtil.findAll(sql, null);
		if(list!=null && list.size()!=0){
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 大客户申请审批显示信息
	 * @param wsno
	 * @param flag
	 * @return
	 */
	public Map<String, Object> QueryCustomer1(String wsno, int flag) {
		Long week = getDayOfWeek();
		String sql = getQueryCustomerSql1(wsno,week,flag);
		List<Map> list = OemDAOUtil.findAll(sql, null);
		if(list!=null && list.size()!=0){
			return list.get(0);
		}
		return null;
	}
	
	private String getQueryCustomerSql1(String wsno, Long week, int flag) {
		StringBuffer pasql = new StringBuffer();
	    pasql.append(" SELECT * from ( \n");
        pasql.append(" SELECT TOR2.ORG_DESC BIG_ORG_NAME,TOR3.ORG_DESC ORG_NAME ,\n");
        pasql.append(" TM.DEALER_SHORTNAME AS DEALER_NAME,TM.DEALER_CODE,\n");
		pasql.append(" TUC.CUSTOMER_COMPANY_CODE,TUC.CUSTOMER_COMPANY_NAME,TUC.COMPANY_NAME,TUC.CUSTOMER_COMPANY_TYPE, \n");
		pasql.append(" TBCFIB.PS_TYPE,TBCRA.WS_NO,TBCRA.REBATE_APPROVAL_STATUS,date_format(TBCRA.REPORT_DATE,'%Y-%m-%d') REPORT_DATE ");
        pasql.append(" ,date_format(TBCRA.REBATE_APPROVAL_DATE,'%Y-%m-%d') REBATE_APPROVAL_DATE,TBCFIB.EMPLOYEE_TYPE\n");
        pasql.append(" ,TBCFIB.PS_TYPE||'('||ifnull(TBCFIB.EMPLOYEE_TYPE,'99999999')||')' PS_TYPE1,TBCRA.AMOUNT \n");
        pasql.append(" ,TBCFIB.CUSTOMER_SUB_TYPE \n");
        pasql.append("  FROM TT_UC_CUSTOMER TUC \n");
        pasql.append(" INNER JOIN TM_DEALER TM ON TUC.DEALER_CODE = TM.DEALER_CODE \n");
        //pasql.append(" INNER JOIN TM_ORG TOG ON TM.COMPANY_ID = TOG.COMPANY_ID \n");
        pasql.append(" INNER JOIN TM_DEALER_ORG_RELATION TDOR ON TDOR.DEALER_ID = TM.DEALER_ID \n");
        pasql.append(" INNER JOIN TM_ORG  TOR3 ON (TOR3.ORG_ID = TDOR.ORG_ID AND TOR3.ORG_LEVEL = 3)\n");
        pasql.append(" INNER JOIN TM_ORG TOR2 ON (TOR3.PARENT_ORG_ID = TOR2.ORG_ID AND TOR2.ORG_LEVEL = 2 ) \n");
        pasql.append(" INNER JOIN TT_BIG_CUSTOMER_REBATE_APPROVAL TBCRA ON TBCRA.BIG_CUSTOMER_CODE = TUC.CUSTOMER_COMPANY_CODE \n");
        pasql.append(" LEFT JOIN TT_BIG_CUSTOMER_FILING_BASE_INFO TBCFIB ON TBCFIB.CUSTOMER_COMPANY_CODE = TUC.CUSTOMER_COMPANY_CODE\n");
        
       
        pasql.append(" WHERE 1=1 \n");
        pasql.append(" AND TBCRA.ENABLE = '"+OemDictCodeConstants.STATUS_ENABLE+"' \n");
        if (!StringUtils.isNullOrEmpty(wsno)) {
            pasql.append(" AND TBCRA.WS_NO LIKE '"+wsno+"' "); //报备单号
           
        }
        pasql.append(" AND TUC.CUSTOMER_BUSINESS_TYPE = '"+OemDictCodeConstants.IF_TYPE_YES+"' \n");
        switch (flag) {
			case 1:
				pasql.append(" AND TBCRA.REBATE_APPROVAL_STATUS = '"+OemDictCodeConstants.BIG_CUSTOMER_REBATE_APPROVAL_TYPE_UNAPPROVED+"' ");
			
				break;
			case 2:
				pasql.append(" AND TBCRA.REBATE_APPROVAL_STATUS = '"+OemDictCodeConstants.BIG_CUSTOMER_REBATE_APPROVAL_TYPE_PASS+"' ");
				
				break;
			case 3:
				pasql.append(" AND TBCRA.REBATE_APPROVAL_STATUS = '"+OemDictCodeConstants.BIG_CUSTOMER_REBATE_APPROVAL_TYPE_OVER+"' ");
				break;
			case 4:
				pasql.append(" AND ( TBCRA.REBATE_APPROVAL_STATUS = '"+OemDictCodeConstants.BIG_CUSTOMER_REBATE_APPROVAL_TYPE_RUS+"'  OR TBCRA.REBATE_APPROVAL_STATUS = '"+OemDictCodeConstants.BIG_CUSTOMER_REBATE_APPROVAL_TYPE_SYSTEM_RUS+"'  OR TBCRA.REBATE_APPROVAL_STATUS = '"+OemDictCodeConstants.BIG_CUSTOMER_REBATE_APPROVAL_TYPE_OVER_RUS+"' ) ");
				
				break;
			case 5:
				pasql.append(" AND TBCRA.REBATE_APPROVAL_STATUS = '"+OemDictCodeConstants.BIG_CUSTOMER_REBATE_APPROVAL_TYPE_COMPLETE_INFORMATION+"'");
				break;
			default:
				break;
		}
        pasql.append("	GROUP BY TOR2.ORG_DESC,TOR3.ORG_DESC ,TM.DEALER_SHORTNAME,TM.DEALER_CODE, \n");
        pasql.append("		TUC.CUSTOMER_COMPANY_CODE,TUC.CUSTOMER_COMPANY_NAME,TUC.COMPANY_NAME,TUC.CUSTOMER_COMPANY_TYPE,  \n");
        pasql.append("	 	TBCFIB.PS_TYPE,TBCRA.WS_NO,TBCRA.REBATE_APPROVAL_STATUS,TBCRA.REPORT_DATE  \n");
        pasql.append("	 	,TBCRA.REBATE_APPROVAL_DATE,TBCFIB.EMPLOYEE_TYPE \n");
        pasql.append("	 	,TBCFIB.PS_TYPE,TBCRA.AMOUNT,TBCFIB.CUSTOMER_SUB_TYPE \n");
        pasql.append(" ORDER BY TBCRA.REPORT_DATE DESC \n");
        pasql.append(" ) dcs \n");
        System.out.println("****************查询");
        System.out.println(pasql.toString());
        System.out.println("****************查询");
        return pasql.toString();
	}
	
	private String getQueryCustomerSql(String wsno, Long week, int flag) {
		 StringBuffer pasql = new StringBuffer("  SELECT distinct TBCFIB.WS_NO,"+week+" as curr_wk,TOR2.ORG_DESC BIG_ORG_NAME,TOR3.ORG_DESC ORG_NAME ," +
	        		" TM.DEALER_SHORTNAME AS DEALER_NAME,TM.DEALER_CODE," +
	        		" TUC.CUSTOMER_COMPANY_CODE," +
	        		" TUC.COMPANY_NAME," +
	        		" TUC.CUSTOMER_COMPANY_NAME,TUC.CUSTOMER_COMPANY_TYPE, " +
	        		" TBCFIB.PS_TYPE,TBCFIB.PS_APPLY_TYPE,TBCRA.REPORT_APPROVAL_STATUS,date_format(TBCRA.REPORT_DATE,'%Y-%m-%d') REPORT_DATE,TBCRA.SEND_EMAIL ");
	        pasql.append(" ,TBCFIB.CUSTOMER_SUB_TYPE ");//客户细分类别
	        //pasql.append(" ,(CASE  WHEN TSI.UNSUB_WEEK_COUNT IS NULL THEN ? ELSE TSI.UNSUB_WEEK_COUNT END) UNSUB_WEEK_COUNT ");
	        //pasql.append(" ,(CASE  WHEN TSI.UNSUB_YEAR_WEEK_COUNT IS NULL THEN ? ELSE TSI.UNSUB_YEAR_WEEK_COUNT END) UNSUB_YEAR_WEEK_COUNT ");
	        pasql.append(" , TSI.UNSUB_WEEK_COUNT, TSI.UNSUB_YEAR_WEEK_COUNT, TSI.LAST_VISIT_WK ");
	        pasql.append(" ,TBCFIB.EMPLOYEE_TYPE,TBCFIB.PS_TYPE||'('||ifnull(TBCFIB.EMPLOYEE_TYPE,'99999999')||')' PS_TYPE1");
	        pasql.append(" ,date_format(TBCRA.REPORT_APPROVAL_DATE,'%Y-%m-%d') REPORT_APPROVAL_DATE,TBCRA.PS_SOURCE_APPLY_NUM_COUNT,date_format(TBCFIB.ESTIMATE_APPLY_TIME,'%Y-%m-%d') ESTIMATE_APPLY_TIME	");//报备总数量,预计申请时间
	        pasql.append(" FROM TT_UC_CUSTOMER TUC ");
	        pasql.append(" INNER JOIN TM_DEALER TM ON TUC.DEALER_CODE = TM.DEALER_CODE");
	        //pasql.append(" INNER JOIN TM_ORG TOG ON TM.COMPANY_ID = TOG.COMPANY_ID ");
	        pasql.append(" INNER JOIN TM_DEALER_ORG_RELATION TDOR ON TDOR.DEALER_ID = TM.DEALER_ID ");
	        pasql.append(" INNER JOIN TM_ORG  TOR3 ON (TOR3.ORG_ID = TDOR.ORG_ID AND TOR3.ORG_LEVEL = 3)");
	        pasql.append(" INNER JOIN TM_ORG TOR2 ON (TOR3.PARENT_ORG_ID = TOR2.ORG_ID AND TOR2.ORG_LEVEL = 2 ) ");
	        pasql.append(" INNER JOIN TT_BIG_CUSTOMER_REPORT_APPROVAL TBCRA ON TBCRA.CUSTOMER_COMPANY_CODE = TUC.CUSTOMER_COMPANY_CODE ");
	        pasql.append(" LEFT JOIN TT_BIG_CUSTOMER_FILING_BASE_INFO TBCFIB ON TBCFIB.CUSTOMER_COMPANY_CODE = TUC.CUSTOMER_COMPANY_CODE");
	        pasql.append(" LEFT JOIN TT_BIG_CUSTOMER_BATCH_SALE_INFO AS TBCBSI ON TBCBSI.WS_NO = TBCFIB.WS_NO AND TBCBSI.CUSTOMER_COMPANY_CODE = TUC.CUSTOMER_COMPANY_CODE");
	        pasql.append(" LEFT JOIN (SELECT DEALER_CODE,UNSUB_WEEK_COUNT, UNSUB_YEAR_WEEK_COUNT, LAST_VISIT_WK" +
	        		     " FROM TT_BIG_CUSTOMER_VISIT_STATISTIC_INFO WHERE YEAR_CODE = YEAR(NOW())) TSI " +
	        		     " ON TSI.DEALER_CODE = TBCRA.DEALER_CODE ");
	        pasql.append(" WHERE 1=1 ");
	        pasql.append(" AND TBCRA.ENABLE = 10011001 ");//有效
	        pasql.append(" AND TUC.CUSTOMER_BUSINESS_TYPE = 10041001 ");
	        if (!StringUtils.isNullOrEmpty(wsno)) {
	            pasql.append(" AND TBCRA.WS_NO LIKE '"+wsno+"' "); //报备单号
	           
	        }
	        switch (flag) {
				case 1:
			        pasql.append(" AND TBCRA.REPORT_APPROVAL_STATUS = 15980001 ");//未审批
//			        pasql.append(" AND TBCRA.AREA_REPORT_APPROVAL_STATUS = ? AND (TBCRA.REPORT_APPROVAL_STATUS IS NULL OR TBCRA.REPORT_APPROVAL_STATUS = ?) ");
//					params.add(Constant.BIG_CUSTOMER_FILING_AREA_APPROVAL_TYPE_PASS); //总部未审批（区域审批通过）
//					params.add(Constant.BIG_CUSTOMER_FILING_APPROVAL_TYPE_UNAPPROVED); //总部未审批
					break;
				case 2:
			        pasql.append(" AND TBCRA.REPORT_APPROVAL_STATUS = 15980002 "); //已审批通过
					break;
				case 3:
			        pasql.append(" AND TBCRA.REPORT_APPROVAL_STATUS = 15980003 ");//审批驳回
					break;
				case 4:
			        pasql.append(" AND (TBCRA.REPORT_APPROVAL_STATUS = 15980004 OR TBCRA.REPORT_APPROVAL_STATUS = 15980005)"); //审批拒绝 //报备转拒绝
					break;
				default:
					break;
			}
			
			return pasql.toString();
	}

	/**
	 * 查询客户相关信息
	 * @param customerCode
	 * @param dealerCode
	 * @param wsno
	 * @return
	 */
	public Map<String, Object> getCustomerInfos(String customerCode, String dealerCode, String wsno) {
		List<Object> params = new ArrayList<Object>();
    	StringBuffer pasql = new StringBuffer(" SELECT TUC.CUSTOMER_COMPANY_APPLY_SOURCE,TUC.COMPANY_NAME, " );
    	pasql.append(" TUC.CUSTOMER_COMPANY_NAME,TUC.CUSTOMER_COMPANY_CODE, " +
    			 	 " TUC.CUSTOMER_COMPANY_NATURE,TUC.CUSTOMER_COMPANY_TYPE,TUC.CUSTOMER_COMPANY_WB_FANS_COUNT, "); //大客户客户信息
    	pasql.append(" TUL.LM_NAME,TUL.LM_CELLPHONE,TUL.LM_TELEPHONE,TUL.LM_FAX,TUL.LM_POST,TUL.LM_ADDRESS,TUL.LM_EMAIL, "); //大客户联系人信息
    	pasql.append(" TBCRA.DLR_HEAD,TBCRA.DLR_HEAD_PHONE,TBCRA.PS_TYPE,TBCRA.PS_APPLY_TYPE,TBCRA.REAMRK, "); //大客户报备相关信息
    	pasql.append(" TBCRA.EMPLOYEE_TYPE,TBCRA.APPLY_PIC,TBCRA.APPLY_PIC1,TBCRA.SALE_CONTRACT_PIC,");
    	pasql.append(" TBCRA.SALE_CONTRACT_PIC1,TBCRA.SALE_CONTRACT_PIC2,TBCRA.CUSTOMER_CARD_PIC "); //报备材料信息
    	pasql.append(" ,TBCRA.CUSTOMER_SUB_TYPE "); //客户细分类别
    	pasql.append(" ,TBCRA.REBATE_REMARK "); //申请备注信息
    	pasql.append(" ,date_format(TBCRA.REPORT_DATE,'%Y-%m-%d') REPORT_DATE \n"); //上报日期
    	pasql.append(" ,TBCRA.ESTIMATE_APPLY_TIME \n"); //预计申请日期
    	pasql.append(" FROM TT_UC_CUSTOMER TUC  ");
    	pasql.append(" INNER JOIN TT_UC_LINKMAN TUL ON TUC.CUSTOMER_ID = TUL.CUSTOMER_ID ");
    	pasql.append(" INNER JOIN TT_BIG_CUSTOMER_FILING_BASE_INFO TBCRA ON TBCRA.CUSTOMER_COMPANY_CODE = TUC.CUSTOMER_COMPANY_CODE ");
    	pasql.append(" WHERE 1=1 AND TUC.CUSTOMER_BUSINESS_TYPE = ? AND TUL.LM_TYPE = ? ");
    	params.add(OemDictCodeConstants.IF_TYPE_YES); //是大客户
    	params.add(OemDictCodeConstants.IF_TYPE_YES); //是车主(主要联系人)
    	pasql.append(" AND TUC.CUSTOMER_COMPANY_CODE = ? " );
    	params.add(customerCode);
    	pasql.append(" AND TBCRA.WS_NO = ? AND TUC.ENABLE = ?  ");
    	params.add(wsno);
        params.add(OemDictCodeConstants.STATUS_ENABLE); //有效
        pasql.append(" AND TUC.DEALER_CODE = ?  ");
    	params.add(dealerCode);
    	List<Map> list = OemDAOUtil.findAll(pasql.toString(), params);
    	if(list!=null && list.size()!=0){
			return list.get(0);
		}
		return null;
	}

	/**
	 * 大客户报备审批查询审批信息
	 * @param wsno
	 * @param customerCode
	 * @return
	 */
	public PageInfoDto approvalHisInfos(String wsno, String customerCode) {
		List<Object> params = new ArrayList<Object>();
    	StringBuffer pasql = new StringBuffer("  SELECT " );
    	pasql.append(" TU.NAME ,date_format(REPORT_APPROVAL_DATE,'%Y-%m-%d') AS APPROVAL_DATE," +
    				 " RA.REPORT_APPROVAL_STATUS,RA.REPORT_APPROVAL_REMARK,RA.REPORT_APPROVAL_DATE ");
    	pasql.append(" FROM TT_BIG_CUSTOMER_REPORT_APPROVAL_HIS RA " );
    	pasql.append(" INNER JOIN TC_USER TU ON RA.REPORT_APPROVAL_USER_ID = TU.USER_ID  ");
    	pasql.append(" WHERE RA.ENABLE = ? ");
    	params.add(OemDictCodeConstants.STATUS_ENABLE); //有效
    	pasql.append(" AND RA.CUSTOMER_COMPANY_CODE = ?" );
    	pasql.append(" AND RA.WS_NO = ? ");
    	params.add(customerCode);
    	params.add(wsno);
    	
		return OemDAOUtil.pageQuery(pasql.toString(), params);
	}

	
	/**
	 * 大客户申请审批查询审批信息
	 * @param wsno
	 * @param customerCode
	 * @return
	 */
	public PageInfoDto approvalHisInfos1(String wsno, String customerCode) {
		List<Object> params = new ArrayList<Object>();
    	StringBuffer pasql = new StringBuffer("  SELECT " );
    	pasql.append(" TU.NAME ,DATE_FORMAT(REBATE_APPROVAL_DATE,'%Y-%m-%d') AS APPROVAL_DATE," +
    				 " RA.REBATE_APPROVAL_STATUS,RA.REBATE_APPROVAL_REMARK ");
    	pasql.append(" FROM TT_BIG_CUSTOMER_REBATE_APPROVAL_HIS RA " );
    	pasql.append(" INNER JOIN TC_USER TU ON RA.REBATE_APPROVAL_USER_ID = TU.USER_ID  ");
    	pasql.append(" WHERE RA.ENABLE = '"+OemDictCodeConstants.STATUS_ENABLE+"' ");
    	pasql.append(" AND RA.BIG_CUSTOMER_CODE = '"+customerCode+"' AND RA.WS_NO = '"+wsno+"'  ");
    	System.err.println(pasql.toString());
		return OemDAOUtil.pageQuery(pasql.toString(), params);
	}
	
	
	public PageInfoDto queryBigCustomer(Map<String, String> queryParams,LoginInfoDto loginUser) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT *  \n");
		sql.append("	FROM Tt_Big_Customer_Policy_File \n");
		sql.append("	WHERE FILE_ENABLE = " + OemDictCodeConstants.STATUS_ENABLE+" \n");
		if (!StringUtils.isNullOrEmpty(queryParams.get("fileName"))) {
			sql.append("	AND POLICY_FILE_NAME LIKE '%"+queryParams.get("fileName")+"%'  \n");
        }
		PageInfoDto dto = OemDAOUtil.pageQuery(sql.toString(),null);
		for(Map mapA:dto.getRows()){
			if(!StringUtils.isNullOrEmpty(loginUser.getDealerId())){
				mapA.put("actionFlag", "interface");
			}else{
				mapA.put("actionFlag", "action");
			}
		}
		return dto;
	}

	/**
     * 大客户组织架构权限审批查询
     */
	public PageInfoDto findAuthorityApproval(Map<String, String> queryParams) {
		List<Object> params = new ArrayList<Object>();
		
		String sql = findAuthorityApprovalSql(queryParams, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;
	}
	/**
     * 大客户组织架构权限审批查询SQL
     */
	private String findAuthorityApprovalSql(Map<String, String> queryParams, List<Object> params) {
		 StringBuffer pasql = new StringBuffer();
	  	    pasql.append(" SELECT * from ( \n");
		    pasql.append(" 	SELECT TM.DEALER_CODE,TM.DEALER_SHORTNAME DEALER_NAME ,date_format(TBCAA.AUTHORITY_APPLY_DATE,'%Y-%m-%d') AUTHORITY_APPLY_DATE1,\n");
		    pasql.append("		TBCAA.AUTHORITY_APPROVAL_ID, TBCAA.EMP_CODE,TBCAA.USER_CODE, \n");
	        pasql.append("		TBCAA.USER_NAME,TBCAA.APPLY_REMARK,TBCAA.AUTHORITY_APPROVAL_STATUS,TBCAA.AUTHORITY_APPLY_DATE,TBCAA.APPLY_NO,TM.DEALER_ID \n");
	        pasql.append("		FROM TT_BIG_CUSTOMER_AUTHORITY_APPROVAL TBCAA	\n");
	        pasql.append("		INNER JOIN TM_DEALER TM ON tbcaa.DEALER_CODE = TM.DEALER_CODE ) TBCAA	\n");
	        pasql.append(" LEFT  JOIN( 	\n");
	        pasql.append(" SELECT TBCAA.DEALER_CODE DEALER_CODE1,TBCAA.AUTHORITY_APPLY_DATE,SUM(TBCAA.NUM) NUM FROM ( 	\n");
	        pasql.append("		select A.DEALER_CODE,date_format(A.AUTHORITY_APPLY_DATE,'%Y-%m-%d') AUTHORITY_APPLY_DATE, COUNT(*) NUM 	\n");
	        pasql.append(" 		from TT_BIG_CUSTOMER_AUTHORITY_APPROVAL A \n");
	        pasql.append(" 		group by A.DEALER_CODE,A.AUTHORITY_APPLY_DATE) TBCAA	\n");
	        pasql.append(" group by TBCAA.DEALER_CODE,TBCAA.AUTHORITY_APPLY_DATE	\n");
	        pasql.append(" ) tt on tt.DEALER_CODE1 = TBCAA.DEALER_CODE	and tt.AUTHORITY_APPLY_DATE = TBCAA.AUTHORITY_APPLY_DATE1 \n");
	        pasql.append(" WHERE 1=1	\n");
//	        if (!StringUtils.isNullOrEmpty(queryParams.get("dealerId"))) {
//            pasql.append(" AND TM.DEALER_ID IN (?) "); //经销商代码
//            params.add("%" +queryParams.get("dealerId") + "%");
//        	}
	        if (!StringUtils.isNullOrEmpty(queryParams.get("dealerCode"))) {
                pasql.append(" AND (TBCAA.DEALER_CODE = ? or TBCAA.DEALER_NAME LIKE ?) "); //经销商代码
              
                params.add( queryParams.get("dealerCode"));
	        }
        
//        	if (!StringUtils.isNullOrEmpty(queryParams.get("dealerName"))) {
//            	pasql.append(" AND TM.DEALER_NAME LIKE ? "); //经销商名称
//           	 params.add("%" + queryParams.get("dealerName") + "%");
//        	}
	        if (!StringUtils.isNullOrEmpty(queryParams.get("authorityApprovalStatus"))) {
	        	pasql.append(" AND TBCAA.AUTHORITY_APPROVAL_STATUS = ? \n");
          
	        	params.add( queryParams.get("authorityApprovalStatus"));
	        }
	        if (!StringUtils.isNullOrEmpty(queryParams.get("startApplyDate"))) {
	        	pasql.append(" AND TBCAA.AUTHORITY_APPLY_DATE >= str_to_date('" + queryParams.get("startApplyDate") + " 00:00:00','%Y-%m-%d %H:%i:%S')");
	        }
	        if (!StringUtils.isNullOrEmpty(queryParams.get("endApplyDate"))) {
	        	pasql.append(" AND TBCAA.AUTHORITY_APPLY_DATE <= str_to_date('" +queryParams.get("endApplyDate") + " 23:59:59','%Y-%m-%d %H:%i:%S')");
	        }
	        
	       return pasql.toString();
	}

	/**
	 * 大客户组织架构权限审批明细页面
	 * @param id
	 * @return
	 */

	public Map<String, Object> getAuthorityApprovalInfo(BigDecimal id) {
		List<Object> params = new ArrayList<Object>();
    	StringBuffer pasql = new StringBuffer();
        pasql.append("select tbcaa.AUTHORITY_APPROVAL_ID, tbcaa.DEALER_CODE, tbcaa.DEALER_NAME, tbcaa.EMP_CODE, tbcaa.USER_CODE, tbcaa.USER_NAME, \n");
        pasql.append("	tbcaa.ORIGINAL_STATION,tbcaa.PARTTIME_STATION,tbcaa.CONTACTOR_MOBILE,tbcaa.BRAND_YEAR,tbcaa.IS_PARTTIME,tbcaa.SELF_EVALUTION, \n");
        pasql.append("	tbcaa.APPLY_REMARK, tbcaa.AUTHORITY_APPROVAL_STATUS, date_format(tbcaa.AUTHORITY_APPLY_DATE,'%Y-%m-%d') AUTHORITY_APPLY_DATE\n");
        pasql.append("	from TT_BIG_CUSTOMER_AUTHORITY_APPROVAL tbcaa where 1=1\n");
        pasql.append(" AND tbcaa.AUTHORITY_APPROVAL_ID = ? \n");
        params.add(id);
        return OemDAOUtil.findFirst(pasql.toString(), params);
	}
	
	/**
	 * 审核历史
	 * @param id
	 * @return
	 */
	public PageInfoDto getApprovalHisInfo(BigDecimal id) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer pasql = new StringBuffer();
    	pasql.append("select TU.NAME ,tbcaah.AUTHORITY_APPROVAL_STATUS,tbcaah.AUTHORITY_APPROVAL_REMARK,date_format(tbcaah.AUTHORITY_APPROVAL_DATE,'%Y-%m-%d') AUTHORITY_APPROVAL_DATE ");
    	pasql.append(" FROM TT_BIG_CUSTOMER_AUTHORITY_APPROVAL_HISTORY tbcaah \n" );
    	pasql.append(" INNER JOIN TC_USER TU ON tbcaah.AUTHORITY_APPROVAL_USER_ID = TU.USER_ID \n");
    	pasql.append(" WHERE 1=1 \n");
    	pasql.append(" AND tbcaah.AUTHORITY_APPROVAL_ID = ? ");
    	params.add(id);
    	return OemDAOUtil.pageQuery(pasql.toString(), params);
	}

	/**
	 * 
	* @Title: findFileNameByPolicyFileId 
	* @Description: 大客户政策下载
	* @param @param policyFileId
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public String findFileNameByPolicyFileId(BigDecimal policyFileId) {
		List<Object> queryParam = new ArrayList<Object>();
		String fileName = "";
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT POLICY_FILE_NAME FROM Tt_Big_Customer_Policy_File WHERE POLICY_FILE_ID = '"+ policyFileId +"'");
		List<Map> list = OemDAOUtil.findAll(sql.toString(), queryParam);
		if(null!=list && list.size()>0){
			fileName = list.get(0).get("POLICY_FILE_NAME").toString();
		}
		return fileName;
	}
}
