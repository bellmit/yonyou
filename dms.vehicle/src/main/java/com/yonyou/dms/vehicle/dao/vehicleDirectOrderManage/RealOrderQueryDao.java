package com.yonyou.dms.vehicle.dao.vehicleDirectOrderManage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
/**
 * 直销车实销查询
 * @author Administrator
 *
 */
@Repository
public class RealOrderQueryDao extends OemBaseDAO{
	
	
	public PageInfoDto realOrderQuery(Map<String, String> queryParam) {
		   List<Object> params = new ArrayList<>();
		    String sql = getQuerySql(queryParam,params);
		    PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql,params);
		    System.out.println(pageInfoDto);
			return pageInfoDto;
	}
	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
 		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		StringBuffer sql = new StringBuffer();
		sql.append(" select t.* from (SELECT tt3.DIRECT_ID CTM_ID,TT1.*,tt3.CUSTOMER_NAME CTM_NAME,tt3.LINKMAN_TEL MAIN_PHONE,tt3.CUSTOMER_TYPE CTM_TYPE,TRR.REMARK,TRR.OTHER_REMARK,tt.ORG_ID2,  \n");
		sql.append("   tt.DEALER_CODE,tt.DEALER_NAME,tt.ORG_DESC2,tt.ORG_DESC3,tt.SWI_CODE, \n");
		sql.append("       VW.SERIES_NAME,VW.BRAND_CODE,VW.MODEL_YEAR,VW.MODEL_NAME,VW.GROUP_NAME, VW.SERIES_CODE, \n");
		sql.append("       VW.COLOR_NAME,VW.TRIM_NAME,TT3.CUSTOMER_NAME,TT3.BIG_CUSTOMER_CODE,TT3.BIG_CUSTOMER_TYPE FROM( \n");
		sql.append("	SELECT coalesce(t5.INVOICE_DATE,DATE_FORMAT(t6.INVOICE_DATE,'%Y-%m-%d')) INVOICE_DATE1,T5.* FROM(   \n");
		sql.append("   		SELECT T1.BUSINESS_ID,T1.STATUS,DATE_FORMAT(T1.CREATE_DATE,'%Y-%m-%d %H:%i:%s') CREATE_DATE,T1.REPORT_TYPE,T1.NVDR_ID, \n");
		sql.append("   		tvo.VIN, T4.VEHICLE_ID ,TVO.DEALER_ID,tvo.VEHICLE_USE VEHICLE_USAGE,tvo.MATERAIL_ID,tvo.ORDER_ID, DATE_FORMAT(tvo.INVOICE_DATE,'%Y-%m-%d') INVOICE_DATE , \n");
		sql.append(" 		case  \n");
		sql.append("  			WHEN T4.NODE_STATUS < "+OemDictCodeConstants.K4_VEHICLE_NODE_19 +" OR T4.NODE_STATUS IS NULL THEN 1  \n");
	    sql.append("  			WHEN T4.NODE_STATUS = "+OemDictCodeConstants.K4_VEHICLE_NODE_19+" AND (T4.IS_STORAGE = 0 or T4.IS_STORAGE IS NULL) THEN 2 \n");
	    sql.append(" 			WHEN T4.NODE_STATUS = "+OemDictCodeConstants.K4_VEHICLE_NODE_19+" AND T4.IS_STORAGE = 1 THEN 3 \n");
	    sql.append("  			WHEN T4.NODE_STATUS = "+OemDictCodeConstants.K4_VEHICLE_NODE_20+" THEN 4 \n");
	    sql.append("  			ELSE 5 \n");
	    sql.append(" 		END STORE_STATUS  --  库存状态 \n");
	    sql.append(" 		, T4.STOCKOUT_DEALER_DATE \n");
	    sql.append(" 		, T4.NODE_STATUS, T4.IS_STORAGE,TVO.INVOICE_TYPE \n");
	    sql.append(" 		FROM TT_VS_ORDER tvo \n");
	    sql.append(" 		left join TT_VS_NVDR T1 on T1.VIN = tvo.VIN AND T1.VIN !='' AND T1.VIN IS NOT NULL \n");
	    sql.append(" 		left join TM_DEALER T2 ON  T1.BUSINESS_ID = T2.DEALER_ID \n");
	    sql.append(" 		LEFT JOIN TM_VEHICLE_DEC T4 ON TVO.VIN = T4.VIN \n");
	    sql.append(" 		WHERE tvo.ORDER_TYPE  = " + OemDictCodeConstants.ORDER_TYPE_DOMESTIC_04 +" \n");
	    sql.append(" 		AND coalesce(T1.IS_DEL,0) != 1  \n");
	    if (!StringUtils.isNullOrEmpty(queryParam.get("beginDateTB"))) {
			sql.append("		AND T4.STOCKOUT_DEALER_DATE > DATE_FORMAT('" +queryParam.get("beginDateTB") + " 00:00:00','%Y-%m-%d %H:%i:%s')");
		}
	    if (!StringUtils.isNullOrEmpty(queryParam.get("endDateTB"))) {
			sql.append("		AND T4.STOCKOUT_DEALER_DATE < DATE_FORMAT('" + queryParam.get("endDateTB") + " 23:59:59','%Y-%m-%d %H:%i:%s') ");
		}
	    sql.append("  )T5");
		sql.append("   LEFT JOIN TT_VS_SALES_REPORT T6 ON T5.VEHICLE_ID = T6.VEHICLE_ID ) TT1  \n");
		sql.append("   LEFT JOIN TT_BIG_CUSTOMER_DIRECT_ORDER TT2 ON TT1.ORDER_ID = TT2.ORDER_ID \n");
		sql.append("   LEFT JOIN TT_BIG_CUSTOMER_DIRECT TT3 ON TT2.DIRECT_ID = TT3.DIRECT_ID \n");
		sql.append("   LEFT JOIN TT_RESOURCE_REMARK TRR ON TT1.VIN = TRR.VIN \n");
		sql.append("   left join (select TOR2.ORG_ID ORG_ID2,TD.DEALER_ID,TD.DEALER_CODE,TD.DEALER_SHORTNAME DEALER_NAME,TOR2.ORG_DESC ORG_DESC2,TOR3.ORG_DESC ORG_DESC3,TC.FCA_CODE SWI_CODE \n");
		sql.append("	  			from TM_ORG                     TOR2, \n");
		sql.append(" 		  			 TM_ORG                     TOR3, \n");
		sql.append(" 					 TM_DEALER_ORG_RELATION     TDOR, \n");
		sql.append("					 TM_DEALER                  TD, \n");
		sql.append("					 TM_COMPANY                 TC \n");
		sql.append("				 where TOR3.PARENT_ORG_ID = TOR2.ORG_ID \n");
		sql.append("				   and TOR3.ORG_ID = TDOR.ORG_ID \n");
		sql.append("			       and TDOR.DEALER_ID = TD.DEALER_ID \n");
		sql.append("				   and TD.COMPANY_ID = TC.COMPANY_ID \n");
		sql.append(" 				   and TOR3.ORG_LEVEL = 3 \n");
		sql.append("				   and TOR2.ORG_LEVEL = 2 ) tt   \n");
		sql.append("   on tt1.DEALER_ID=tt.DEALER_ID \n");
		sql.append("   LEFT JOIN  TM_VEHICLE_DEC TM ON TM.VIN = TT1.VIN \n");
		sql.append("   LEFT JOIN  ("+getVwMaterialSql()+") VW ON TT1.MATERAIL_ID = VW.MATERIAL_ID \n");
		sql.append("   where 1=1 \n");
		//----------------------------------------------------------------
		sql.append("   AND tt.dealer_code= 20418 \n");
		//----------------------------------------------------------------------
		sql.append(" "+control(" VW.SERIES_ID ", loginInfo.getDealerSeriesIDs(),loginInfo.getPoseSeriesIDs())+" \n");
		if(!loginInfo.getDutyType().toString().equals(CommonConstants.DUTY_TYPE_DEPT.toString()) && !loginInfo.getDutyType().toString().equals(CommonConstants.DUTY_TYPE_COMPANY.toString())){
			sql.append("	AND  tt1.DEALER_ID in ("+OemBaseDAO.getDealersByArea(loginInfo.getOrgId().toString())+")\n");
		}	
		  //品牌
		  if (!StringUtils.isNullOrEmpty(queryParam.get("brandCode"))) {
	            sql.append("  and VW.BRAND_CODE = ? ");
	            params.add( queryParam.get("brandCode"));
	        }
		  //车系
		  if (!StringUtils.isNullOrEmpty(queryParam.get("seriesCode"))) {
	            sql.append("  and VW.SERIES_CODE =  ? ");
	            params.add( queryParam.get("seriesCode"));
	        }
		  //车款
		  if (!StringUtils.isNullOrEmpty(queryParam.get("groupName"))) {
	            sql.append("  and VW.MODEL_NAME =  ? ");
	            params.add( queryParam.get("groupName"));
	        }
		  //年款
		  if (!StringUtils.isNullOrEmpty(queryParam.get("modelYear"))) {
	            sql.append("  and VW.MODEL_YEAR =  ? ");
	            params.add( queryParam.get("modelYear"));
	        }
		  //颜色
		  if (!StringUtils.isNullOrEmpty(queryParam.get("colorName"))) {
	            sql.append("  and VW.COLOR_NAME =  ? ");
	            params.add( queryParam.get("colorName"));
	        }
		  //内饰
		  if (!StringUtils.isNullOrEmpty(queryParam.get("trimName"))) {
	            sql.append("  and VW.TRIM_NAME<=  ? ");
	            params.add( queryParam.get("trimName"));
	        }
		  //库存状态
		  if (!StringUtils.isNullOrEmpty(queryParam.get("status"))) {
	            sql.append("  and T1.STATUS  =  ? ");
	            params.add( queryParam.get("status"));
	        }
		  //VIN号
		  if (!StringUtils.isNullOrEmpty(queryParam.get("bizType"))) {
	            sql.append("  and tvo.VIN  =  ? ");
	            params.add( queryParam.get("bizType"));
	        }
		
		 if (!StringUtils.isNullOrEmpty(queryParam.get("beginCreateDate"))) {
			sql.append("		and TT1.CREATE_DATE > DATE_FORMAT('" + queryParam.get("beginCreateDate") + " 00:00:00','%Y-%m-%d %H:%i:%s')");
		}
		 if (!StringUtils.isNullOrEmpty(queryParam.get("endCreateDate"))) {
			sql.append("		and TT1.CREATE_DATE < DATE_FORMAT('" + queryParam.get("endCreateDate") + " 23:59:59','%Y-%m-%d %H:%i:%s')");
		}
		 if (!StringUtils.isNullOrEmpty(queryParam.get("beginInvoiceDate"))) {
			sql.append("		and  DATE_FORMAT(TT1.INVOICE_DATE1,'%Y-%m-%d') >= '" + queryParam.get("beginInvoiceDate") + " 00:00:00'");
		}
		 if (!StringUtils.isNullOrEmpty(queryParam.get("endInvoiceDate"))) {
			sql.append("		and DATE_FORMAT(TT1.INVOICE_DATE1,'%Y-%m-%d') <= '" + queryParam.get("endInvoiceDate") + " 23:59:59'");
		}
		 if (!StringUtils.isNullOrEmpty(queryParam.get("status"))) {
			 sql.append(" AND TT1.STORE_STATUS = "+queryParam.get("status")+"\n");
			}
		 //CASE TT1.STORE_STATUS  WHEN TT1.STORE_STATUS =0 THEN '出库' ELSE'在库' END  as  STORE_STATUS
		 sql.append("  ) t \n");
		 System.out.println(sql.toString());
			return sql.toString();
	}
	
	/**
	 * 下载
	 * @param queryParam
	 * @return
	 */
	public List<Map> download(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam, params);
		List<Map> download = OemDAOUtil.downloadPageQuery(sql, null);
		return download;
	}
	
	/**
	 * 通过id进行明细查询
	 */
	public List<Map> getXiangxi(String orderId)  throws ServiceBizException{
	        StringBuilder sql = new StringBuilder();
	        List<Object> params = new ArrayList<>();
	        sql.append("SELECT TVO.VIN, -- 车架号 \n");
			sql.append("       TVN.SALE_TYPE, -- 销售类型 \n");
			sql.append("       COALESCE(DATE_FORMAT(TVO.INVOICE_DATE,'%Y-%m-%d'),DATE_FORMAT(TVSR.INVOICE_DATE,'%Y-%m-%d')) INVOICE_DATE,  --  开票日期 \n");
			sql.append("       CASE  \n");
			sql.append("          WHEN TVN.ODOMETER IS NULL THEN TBCDP.MILEAGE \n");
			sql.append("          ELSE TVN.ODOMETER \n");
			sql.append("       END AS MILEAGE,-- 交付时公里数 \n");
			sql.append("       TV.QUALIFIED_NO, -- 合格证号 \n");
			sql.append("       TV.ENGINE_NO,-- 发动机号 \n");
			sql.append("       TVO.VEHICLE_USE, -- 车辆用途 \n");
			sql.append("       VM.BRAND_NAME,-- 品牌 \n");
			sql.append("       VM.SERIES_NAME, -- 车系 \n");
			sql.append("       VM.GROUP_NAME, -- 车型 \n");
			sql.append("       VM.COLOR_NAME,-- 颜色 \n");
			sql.append("       VM.TRIM_NAME, -- 内饰 \n");
			sql.append("       VM.MODEL_YEAR, -- 年  款 \n");
			sql.append("       VM.MODEL_CODE,-- CPOS \n");
			sql.append("       FF.FILEURL, -- 发票附件上传 \n");
			sql.append("       TD.DEALER_CODE,-- 经销商代码 \n");
			sql.append("       TBCD.CUSTOMER_NO,-- 客户编号 \n");
			sql.append("       TBCD.CUSTOMER_NAME,-- 客户名称 \n");
			sql.append("       TBCD.CUSTOMER_TYPE,-- 客户类型 \n");
			sql.append("       TBCD.LINKMAN_NAME,-- 联系人 \n");
			sql.append("       TBCD.LINKMAN_TEL,-- 联系方式 \n");
			sql.append("       TBCD.LINKMAN_SEX,-- 联系人性别 \n");
			sql.append("       TBCD.LINKMAN_ADDR, -- 联系人地址 \n");
			sql.append("       TBCD.ID_TYPE,-- 证件类型 \n");
			sql.append("       TBCD.ID_NO,-- 证件号码 \n");
			sql.append("       TBCD.PAYMENT_BANK,-- 打款银行 \n");
			sql.append("       TBCD.BANK_NO,-- 银行账号 \n");
			sql.append("       TBCD.REAMK, -- 备注 \n");
			sql.append("       TVO.INVOICE_TYPE -- 开票类型 \n");
			sql.append("  FROM TT_VS_ORDER TVO \n");
			sql.append("  LEFT JOIN TT_VS_NVDR TVN ON TVO.VIN = TVN.VIN \n");
			sql.append("  LEFT JOIN TT_BIG_CUSTOMER_DIRECT_PDICHECK TBCDP ON TBCDP.ORDER_ID=TVO.ORDER_ID \n");
			sql.append("  LEFT JOIN TM_DEALER TD ON TD.DEALER_ID = TVO.DEALER_ID \n");
			sql.append("  LEFT JOIN TM_VEHICLE_dec TV ON TV.VIN = TVO.VIN \n");
			sql.append("  LEFT JOIN TT_BIG_CUSTOMER_DIRECT_ORDER TBCDO ON TBCDO.ORDER_ID = TVO.ORDER_ID \n");
			sql.append("  LEFT JOIN TT_BIG_CUSTOMER_DIRECT TBCD ON TBCDO.DIRECT_ID = TBCD.DIRECT_ID \n");
			sql.append("  LEFT JOIN ("+getVwMaterialSql()+") VM ON VM.MATERIAL_ID = TVO.MATERAIL_ID \n");
			sql.append("  LEFT JOIN TT_VS_SALES_REPORT TVSR ON TV.VEHICLE_ID = TVSR.VEHICLE_ID \n");
			sql.append("  LEFT JOIN FS_FILEUPLOAD FF ON TVSR.REPORT_ID = FF.YWZJ  \n");
			 if (!StringUtils.isNullOrEmpty(orderId)) {
			 sql.append("  where tvo.ORDER_ID="+orderId+" \n");
			 }
			//sql.append("  WITH UR ");
	     return OemDAOUtil.findAll(sql.toString(), params);   
	}
}
