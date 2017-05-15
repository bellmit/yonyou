package com.yonyou.dms.part.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * 交货单查询Dao
 * @author ZhaoZ
 * @date 2017年3月27日
 */
@Repository
public class PartDeliverManageAaDao extends OemBaseDAO{

	/**
	 * 直发交货单查询
	 * @param queryParams
	 * @return
	 */
	public PageInfoDto findList(Map<String, String> queryParams) {
		List<Object> params = new ArrayList<Object>();
		String sql = getfindListSql(queryParams, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;
	}
	
	/**
	 * 直发交货单查询SQL
	 * @param queryParams
	 * @return
	 */
	private String getfindListSql(Map<String, String> queryParams, List<Object> params) {
		StringBuffer pasql = new StringBuffer();
		pasql.append("	SELECT   \n  ");
        pasql.append("	TD.DEALER_CODE, \n  ");
        pasql.append("	TD.DEALER_SHORTNAME,	\n  ");
        pasql.append("	TPD.DELIVER_NO,\n  ");
        pasql.append("	TPD.DMS_ORDER_NO,	\n  ");
        pasql.append("	 date_format(TPD.DELIVER_DATE,'%Y-%m-%d  %h:%i:%s') DELIVER_DATE,	\n  ");
        pasql.append("	 date_format(TPD.UPDATE_DATE,'%Y-%m-%d %h:%i:%s') UPDATE_DATE,	\n  ");
        pasql.append("	TPO.ORDER_TYPE,	\n  ");
        pasql.append("	TPDD.PLAN_NUM, 	\n  ");
        pasql.append("	TPD.DELIVER_ID, 	\n  ");
        pasql.append("	TPD.DELIVER_STATUS \n  ");
        pasql.append("	FROM TT_PT_DELIVER_dcs TPD	\n  ");
        pasql.append("	LEFT JOIN (select (SUM(PLAN_NUM)) as PLAN_NUM,DELIVER_ID  from TT_PT_DELIVER_DETAIL_dcs  GROUP BY DELIVER_ID) TPDD  ON TPDD.DELIVER_ID = TPD.DELIVER_ID \n  ");
        pasql.append("	LEFT JOIN TM_DEALER TD ON TPD.DEALER_ID = TD.DEALER_ID	\n  ");
        pasql.append("	LEFT JOIN TT_PT_ORDER_dcs TPO ON TPD.DMS_ORDER_NO = TPO.ORDER_NO \n  ");
        pasql.append("	WHERE 1 = 1	  \n  ");
        pasql.append("  AND TPO.ORDER_TYPE = '"+ OemDictCodeConstants.PART_ORDER_TYPE_05+ "'	\n  ");
        pasql.append("  AND (TPD.DELIVER_STATUS is null or TPD.DELIVER_STATUS !='"+ OemDictCodeConstants.PART_DELIVER_STATUS_02 + "')	\n  ");
        //交货单号
        if(!StringUtils.isNullOrEmpty(queryParams.get("deliverNo"))){ 
        	pasql.append(" 	AND TPD.DELIVER_NO LIKE ? \n");
       		params.add("%"+queryParams.get("deliverNo")+"%");
        }
        //经销商
        if(!StringUtils.isNullOrEmpty(queryParams.get("dealerCode"))){ 
        	String[] dealerCodes=queryParams.get("dealerCode").split(",");
    		String code="";
    		for(int i=0;i<dealerCodes.length;i++){
    			code+=""+dealerCodes[i]+",";
    		}
    		code=code.substring(0,code.length()-1);
        	pasql.append(" 	AND TD.DEALER_CODE IN (?) \n");
        	params.add(code);
        }
        //DMS订单号
        if(!StringUtils.isNullOrEmpty(queryParams.get("dmsOrderNo"))){ 
        	pasql.append(" 	AND TPD.DMS_ORDER_NO LIKE ? \n");
       		params.add("%"+queryParams.get("dmsOrderNo")+"%");
        }
       
        //交货单创建日期
        if(!StringUtils.isNullOrEmpty(queryParams.get("startDate"))){ 
        	pasql.append(" AND  date_format(TPD.DELIVER_DATE,'%Y-%m-%d') >='" + queryParams.get("startDate") +"' \n");
        }
        if(!StringUtils.isNullOrEmpty(queryParams.get("endDate"))){ 
        	pasql.append(" AND  date_format(TPD.DELIVER_DATE,'%Y-%m-%d') <='" + queryParams.get("endDate") +"' \n");
        }
		return pasql.toString();
   
	}

	/**
	 * 直发交货单修改回显信息
	 * @param deliverId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> deliverInfoByDeliverId(BigDecimal deliverId) {
		StringBuilder sql = new StringBuilder();
		sql.append("    SELECT \n");
		sql.append("    TPD.DELIVER_ID,\n");
		sql.append("    TD.DEALER_CODE, \n");
		sql.append("    TD.DEALER_SHORTNAME, \n");
		sql.append("    TPD.DELIVER_NO,\n");
		sql.append("    TPD.AMOUNT,\n");
		sql.append("    TPD.NET_PRICE,\n");
		sql.append("    TPD.TAX_AMOUNT,\n");
		sql.append("    TPD.TRANS_AMOUNT, \n");
		sql.append("    TPD.TRANS_NO,\n");
		sql.append("    TPD.TRANS_COMPANY, \n");
		sql.append("    TPD.TRANS_TYPE,\n");
		sql.append("    date_format(TPD.TRANS_DATE,'%Y-%m-%d') TRANS_DATE, \n");
		sql.append("    date_format(TPD.ARRIVED_DATE,'%Y-%m-%d') ARRIVED_DATE \n");
		sql.append("    FROM TT_PT_DELIVER_dcs TPD \n");
		sql.append("    INNER JOIN TM_DEALER TD ON TPD.DEALER_ID = TD.DEALER_ID \n");
		sql.append("    WHERE 1 = 1 \n");
		sql.append("    AND TPD.DELIVER_ID = " + deliverId + "\n");
		return OemDAOUtil.findFirst(sql.toString(), null);
	}

	/**
	 * 接货清单信息查询
	 * @param deliverId
	 * @return
	 */
	public PageInfoDto acceptInfoByDeliverId(BigDecimal deliverId) {
		StringBuilder sql = new StringBuilder();
		sql.append("    SELECT \n");
		sql.append("    TPDD.DETAIL_ID, \n");
		sql.append("    TPPB.PART_CODE,  \n");
		sql.append("    TPPB.PART_NAME, \n");
		sql.append("    TPDD.PLAN_NUM, \n");
		sql.append("    TPDD.NET_PRICE, \n");
		sql.append("    TPDD.INSTOR_PRICE, \n");
		sql.append("    TPDD.DELIVER_AMOUNT, \n");
		sql.append("    TPDD.DISCOUNT, \n");
		sql.append("    TPDD.LINE_NO, \n");
		sql.append("    TPO.ORDER_NO as DMS_ORDER_NO, \n");
		sql.append("    TPDD.ORDER_NO as SAP_ORDER_NO, \n");
		sql.append("    TC.CODE_DESC as ORDER_TYPE \n");
		sql.append("    FROM TT_PT_DELIVER_dcs TPD \n");
		sql.append("    INNER JOIN TT_PT_DELIVER_DETAIL_dcs TPDD ON TPDD.DELIVER_ID = TPD.DELIVER_ID \n");
		sql.append("    LEFT JOIN TT_PT_ORDER_dcs TPO ON TPO.SAP_ORDER_NO = TPDD.ORDER_NO \n");//根据清单中的SAP订单号，找到DMS订单号
		sql.append("    LEFT JOIN TT_PT_PART_BASE_dcs TPPB ON TPPB.PART_CODE = TPDD.PART_CODE \n");
		sql.append("    LEFT JOIN TC_CODE_dcs TC ON TPO.ORDER_TYPE = TC.CODE_ID \n");
		sql.append("    WHERE 1 = 1 \n");
		sql.append("    AND TPD.DELIVER_ID  = " + deliverId + "\n");
		return OemDAOUtil.pageQuery(sql.toString(), null);
	}

	/**
	 * 交货单明细查询
	 * @param queryParams
	 * @return
	 */
	public PageInfoDto queryOrderInfos(Map<String, String> queryParams) {
		List<Object> params = new ArrayList<Object>();
		String sql = getOrderInfoSql(queryParams, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;
	}

	/**
	 * 交货单明细查询SQL
	 * @param queryParams
	 * @param params
	 * @return
	 */
	private String getOrderInfoSql(Map<String, String> queryParams, List<Object> params) {
		StringBuffer pasql = new StringBuffer();
		pasql.append("	SELECT \n  ");
        pasql.append("	TD.DEALER_CODE,  \n  ");
        pasql.append("	TD.DEALER_SHORTNAME,  \n  ");
        pasql.append("	TPD.DELIVER_NO,  \n  ");
        pasql.append("	date_format(TPD.DELIVER_DATE,'%Y-%m-%d %h:%i:%s') DELIVER_DATE,  \n  ");
        pasql.append("	TPDD.PLAN_NUM,  \n  ");
        pasql.append("	TPD.DELIVER_ID, \n  ");
        pasql.append("	TPD.IS_DCS_SEND,  \n  ");
        pasql.append("	TPDD.SAP_ORDER_NO,  \n  ");
        pasql.append("	TPD.DELIVER_STATUS  \n  ");
        pasql.append("	FROM TT_PT_DELIVER_dcs TPD \n  ");
        pasql.append("		LEFT JOIN (SELECT (SUM(dd.PLAN_NUM)) AS PLAN_NUM,dd.DELIVER_ID,dd.ORDER_NO2 AS SAP_ORDER_NO \n  ");
        pasql.append("		FROM (	SELECT  t.PLAN_NUM,t.DELIVER_ID,t.ORDER_NO,CASE WHEN LAG=ORDER_NO THEN NULL ELSE order_no END AS ORDER_NO2 FROM  \n  ");
        pasql.append("	 ( SELECT IF(@tid = t.DELIVER_ID, @lagfield := @ttime, @lagfield := NULL) AS LAG, \n  ");
        pasql.append("	 (@tid := t.DELIVER_ID) AS tid, (@ttime := t.ORDER_NO) AS ttime, t.PLAN_NUM,t.DELIVER_ID,t.ORDER_NO \n  ");
        pasql.append("	 FROM (SELECT * FROM TT_PT_DELIVER_DETAIL_dcs ORDER BY DELIVER_ID, ORDER_NO) t, \n  ");
        pasql.append("	  (SELECT @lagfield := NULL, @tid := 0, @ttime := NULL) r \n  ");
        pasql.append("	 ) AS t ) dd GROUP BY dd.DELIVER_ID) TPDD  ON TPDD.DELIVER_ID = TPD.DELIVER_ID \n  ");
        pasql.append("	 LEFT JOIN TM_DEALER TD ON TPD.DEALER_ID = TD.DEALER_ID   WHERE 1 = 1  \n  ");
        //交货单号
        if(!StringUtils.isNullOrEmpty(queryParams.get("deliverNo"))){ 
        	pasql.append(" 	AND TPD.DELIVER_NO LIKE ? \n");
       		params.add("%"+queryParams.get("deliverNo")+"%");
        }
        //经销商
        if(!StringUtils.isNullOrEmpty(queryParams.get("dealerCode"))){ 
        	String[] dealerCodes=queryParams.get("dealerCode").split(",");
    		String code="";
    		for(int i=0;i<dealerCodes.length;i++){
    			code+=""+dealerCodes[i]+",";
    		}
    		code=code.substring(0,code.length()-1);
        	pasql.append(" AND TD.DEALER_CODE IN (?) \n");
        	params.add(code);
        }
        //交货单创建日期
        if(!StringUtils.isNullOrEmpty(queryParams.get("startDate"))){ 
        	pasql.append(" AND  date_format(TPD.DELIVER_DATE,'%Y-%m-%d') >='" + queryParams.get("startDate") +"' \n");
        }
        if(!StringUtils.isNullOrEmpty(queryParams.get("endDate"))){ 
        	pasql.append(" AND  date_format(TPD.DELIVER_DATE,'%Y-%m-%d') <='" + queryParams.get("endDate") +"' \n");
        }
        //DMS订单号
        if(!StringUtils.isNullOrEmpty(queryParams.get("dmsOrderNo"))){ 
        	pasql.append(" AND exists (select * from TT_PT_DELIVER_DETAIL_dcs dd left join TT_PT_ORDER_dcs po on dd.ORDER_NO=po.SAP_ORDER_NO \n");
        	pasql.append(" where dd.DELIVER_ID = TPD.DELIVER_ID and po.ORDER_NO like ? ) \n");
        	params.add("%"+queryParams.get("dmsOrderNo")+"%");
        }
        //订单类型
        if(!StringUtils.isNullOrEmpty(queryParams.get("orderType"))){ 
        	pasql.append(" AND exists (select * from TT_PT_DELIVER_DETAIL_dcs dd left join TT_PT_ORDER_dcs po on dd.ORDER_NO=po.SAP_ORDER_NO \n");
        	pasql.append(" where dd.DELIVER_ID = TPD.DELIVER_ID and po.ORDER_TYPE= ? ) \n");
        	params.add(queryParams.get("orderType"));
        }
       
        //SAP订单号
        if(!StringUtils.isNullOrEmpty(queryParams.get("sapOrderNo"))){ 
        	pasql.append(" AND exists (select * from TT_PT_DELIVER_DETAIL_dcs dd where dd.DELIVER_ID = TPD.DELIVER_ID and dd.ORDER_NO like ? ) \n");
        	params.add("%"+queryParams.get("sapOrderNo")+"%");
        }
		return pasql.toString();
        
       
	}

	/**
	 * 货运单管查詢
	 * @param queryParams
	 * @return
	 */
	public PageInfoDto getDeliverInit(Map<String, String> queryParams) {
		List<Object> params = new ArrayList<Object>();
		String sql = getDeliverInitSql(queryParams, params);
		return OemDAOUtil.pageQuery(sql, params);
	}

	/**
	 * 货运单管查詢SQL
	 * @param queryParams
	 * @param params
	 * @return
	 */
	private String getDeliverInitSql(Map<String, String> queryParams, List<Object> params) {
		LoginInfoDto logonUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		StringBuffer pasql = new StringBuffer();
		pasql.append("	SELECT   \n  ");
        pasql.append("	date_format(TPD.ARRIVED_DATE,'%Y-%m-%d') ARRIVED_DATE , \n  ");
        pasql.append("	TPD.DELIVER_NO,	\n  ");
        pasql.append("	date_format(TPD.DELIVER_DATE,'%Y-%m-%d') DELIVER_DATE,	\n  ");
        pasql.append("	TPD.DELIVER_ID, 	\n  ");
        pasql.append("	TPD.IS_DCS_SEND, 	\n  ");
        pasql.append("	TPDD.SAP_ORDER_NO,  \n  ");
        pasql.append("	TPD.DELIVER_STATUS,\n  ");
        pasql.append("	TPD.TRANS_NO\n  ");
        pasql.append("	FROM TT_PT_DELIVER_dcs TPD	\n  ");
        pasql.append("	LEFT JOIN (select (SUM(dd.PLAN_NUM)) as PLAN_NUM,dd.DELIVER_ID,dd.ORDER_NO2 as SAP_ORDER_NO \n  ");
        pasql.append("	from (SELECT  t.PLAN_NUM,t.DELIVER_ID,t.ORDER_NO ORDER_NO2,LAG FROM \n  ");
        pasql.append("	 ( SELECT IF(@tid = t.DELIVER_ID, @lagfield := @ttime, @lagfield := NULL) AS LAG, \n  ");
        pasql.append("	 (@tid := t.DELIVER_ID) AS tid, (@ttime := t.ORDER_NO) AS ttime, t.PLAN_NUM,t.DELIVER_ID,t.ORDER_NO \n  ");
        pasql.append("	FROM (SELECT * FROM TT_PT_DELIVER_DETAIL_dcs ORDER BY DELIVER_ID, ORDER_NO) t, \n  ");
        pasql.append("	 (SELECT @lagfield := NULL, @tid := 0, @ttime := NULL) r ) AS t \n  ");
        pasql.append("	 ) dd GROUP BY dd.DELIVER_ID) TPDD  ON TPDD.DELIVER_ID = TPD.DELIVER_ID \n  ");
        pasql.append("	LEFT JOIN TM_DEALER TD ON TPD.DEALER_ID = TD.DEALER_ID \n  ");
        pasql.append("	WHERE 1 = 1 \n  ");
        pasql.append("  and TPD.DEALER_ID=").append(logonUser.getDealerId()).append(" \n");
        System.out.println(logonUser.getDealerId());
        //交货单号
        if(!StringUtils.isNullOrEmpty(queryParams.get("deliverNo"))){ 
        	pasql.append(" 	AND TPD.DELIVER_NO LIKE ? \n");
       		params.add("%"+queryParams.get("deliverNo")+"%");
        }
        //交货单创建日期
        if(!StringUtils.isNullOrEmpty(queryParams.get("startDate"))){ 
        	pasql.append(" AND  date_format(TPD.DELIVER_DATE,'%Y-%m-%d') >='" + queryParams.get("startDate") +"' \n");
        }
        if(!StringUtils.isNullOrEmpty(queryParams.get("endDate"))){ 
        	pasql.append(" AND  date_format(TPD.DELIVER_DATE,'%Y-%m-%d') <='" + queryParams.get("endDate") +"' \n");
        }
        //订单状态
        if(!StringUtils.isNullOrEmpty(queryParams.get("deliverStatus"))){ 
        	pasql.append("  AND TPD.DELIVER_STATUS= ? \n");
       		params.add(queryParams.get("deliverStatus"));
        }
        
        //DMS订单号
        if(!StringUtils.isNullOrEmpty(queryParams.get("dmsOrderNo"))){ 
        	pasql.append(" AND exists (select * from TT_PT_DELIVER_DETAIL_dcs dd left join TT_PT_ORDER_dcs po on dd.ORDER_NO=po.SAP_ORDER_NO \n");
        	pasql.append(" where dd.DELIVER_ID = TPD.DELIVER_ID and po.ORDER_NO like ? ) \n");
       		params.add("%"+queryParams.get("dmsOrderNo")+"%");
        }
        //订单类型
        if(!StringUtils.isNullOrEmpty(queryParams.get("deliverType"))){ 
        	pasql.append(" AND exists (select * from TT_PT_DELIVER_DETAIL_dcs dd left join TT_PT_ORDER_dcs po on dd.ORDER_NO=po.SAP_ORDER_NO \n");
        	pasql.append(" where dd.DELIVER_ID = TPD.DELIVER_ID and po.ORDER_TYPE= ? ) \n");
       		params.add(queryParams.get("deliverType"));
        }
       
        //SAP订单号
        if(!StringUtils.isNullOrEmpty(queryParams.get("sapOrderNo"))){ 
        	pasql.append(" AND exists (select * from TT_PT_DELIVER_DETAIL_dcs dd where dd.DELIVER_ID = TPD.DELIVER_ID and dd.ORDER_NO like ? ) \n");
        	params.add("%"+queryParams.get("sapOrderNo")+"%");
        }
		return pasql.toString();
	}

	
	/**
	 * 接货清单信息查询
	 * @param deliverId
	 * @return
	 */
	public PageInfoDto getDelivertInfo(BigDecimal deliverId) {
		StringBuilder sql = new StringBuilder();
		sql.append("    SELECT \n");
		sql.append("    TPDD.DETAIL_ID,  \n");
		sql.append("    TPPB.PART_CODE,  \n");
		sql.append("    TPPB.PART_NAME, \n");
		sql.append("    TPDD.PLAN_NUM,\n");
		sql.append("    TPDD.NET_PRICE,\n");
		sql.append("    TPDD.INSTOR_PRICE, \n");
		sql.append("    TPDD.DELIVER_AMOUNT, \n");
		sql.append("    TPD.DEALER_ID, \n");
		sql.append("    TPD.DELIVER_NO, \n");
		sql.append("	TPD.DELIVER_DATE,	\n  ");
		sql.append("    TPDD.DISCOUNT,\n");
		sql.append("    TPDD.LINE_NO,\n");
		sql.append("	TPD.DELIVER_STATUS,\n  ");
		sql.append("    TPO.ORDER_NO as DMS_ORDER_NO, \n");
		sql.append("    TPDD.ORDER_NO as SAP_ORDER_NO, \n");
		sql.append("    TC.CODE_DESC as ORDER_TYPE\n");
		sql.append("    FROM TT_PT_DELIVER_dcs TPD \n");
		sql.append("    INNER JOIN TT_PT_DELIVER_DETAIL_dcs TPDD ON TPDD.DELIVER_ID = TPD.DELIVER_ID \n");
		sql.append("    LEFT JOIN TT_PT_ORDER_dcs TPO ON TPO.SAP_ORDER_NO = TPDD.ORDER_NO \n");//根据清单中的SAP订单号，找到DMS订单号
		sql.append("    LEFT JOIN TT_PT_PART_BASE_dcs TPPB ON TPPB.PART_CODE = TPDD.PART_CODE \n");
		sql.append("    LEFT JOIN TC_CODE_dcs TC ON TPO.ORDER_TYPE = TC.CODE_ID \n");
		sql.append("    WHERE 1 = 1 \n");
		sql.append("    AND TPD.DELIVER_ID  = " + deliverId + "\n");
		return OemDAOUtil.pageQuery(sql.toString(), null);
	}

	/**
	 * 货运单导出查询
	 * @param queryParams
	 * @param code
	 * @return
	 */
	public List<Map> getexeDeliverInfo(String deliverId, String code,Map<String, String> queryParams) {
		List<Object> params = new ArrayList<Object>();
		LoginInfoDto logonUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		StringBuilder pasql = new StringBuilder();
		pasql.append("    SELECT \n");

		if(code.equals("2")){
			pasql.append("    TPDD.DETAIL_ID,  \n");
			pasql.append("    TPPB.PART_CODE,  \n");
			pasql.append("    TPPB.PART_NAME, \n");
			pasql.append("    TPDD.PLAN_NUM,\n");
			pasql.append("    TPDD.NET_PRICE, \n");
			pasql.append("    TPDD.INSTOR_PRICE, \n");
			pasql.append("    TPDD.DELIVER_AMOUNT, \n");
			pasql.append("    TPDD.DISCOUNT,\n");
			pasql.append("    TPDD.LINE_NO, \n");
			pasql.append("    TPD.DMS_ORDER_NO \n");
			pasql.append("    FROM TT_PT_DELIVER_dcs TPD \n");
			pasql.append("    INNER JOIN TT_PT_DELIVER_DETAIL_dcs TPDD ON TPDD.DELIVER_ID = TPD.DELIVER_ID \n");
			pasql.append("    LEFT JOIN TT_PT_PART_BASE_dcs TPPB ON TPPB.PART_CODE = TPDD.PART_CODE \n");
		}
		if(code.equals("1")){
			pasql.append("    TPD.DELIVER_ID,  \n");
			pasql.append("    TPD.DELIVER_NO, \n");
			pasql.append("    TPD.DMS_ORDER_NO, \n");
			pasql.append("    TPD.SAP_ORDER_NO,\n");
			
			pasql.append("	  TPO.ORDER_TYPE,	\n  ");
			pasql.append("	  TPD.DELIVER_DATE,	\n  ");
			pasql.append("	  TPD.TRANS_NO,\n  ");
			pasql.append("    date_format(TPD.ARRIVED_DATE,'%Y-%m-%d') ARRIVED_DATE, \n");
			pasql.append("	  TPD.DELIVER_STATUS\n  ");
			pasql.append("    FROM TT_PT_DELIVER_dcs TPD \n");
		}
		
		
        pasql.append("	  LEFT JOIN TT_PT_ORDER_dcs TPO ON TPD.DMS_ORDER_NO = TPO.ORDER_NO \n  ");
		pasql.append("    WHERE 1 = 1 \n");
        pasql.append("  and TPD.DEALER_ID=").append(logonUser.getDealerId()).append(" \n");

		//主键
        if(!StringUtils.isNullOrEmpty(deliverId) && !"-1".equals(deliverId)){ 
        	pasql.append("    AND TPD.DELIVER_ID  = ? \n");
       		params.add(deliverId);
        }
		
		//交货单号
        if(!StringUtils.isNullOrEmpty(queryParams.get("deliverNo"))){ 
        	pasql.append(" 	AND TPD.DELIVER_NO LIKE ? \n");
       		params.add("%"+queryParams.get("deliverNo")+"%");
        }
   
      //DMS订单号
        if(!StringUtils.isNullOrEmpty(queryParams.get("dmsorderNo"))){ 
        	pasql.append(" 	AND TPD.DMS_ORDER_NO LIKE ? \n");
       		params.add("%"+queryParams.get("dmsorderNo")+"%");
        }
      //交货单创建日期
        if(!StringUtils.isNullOrEmpty(queryParams.get("startDate"))){ 
        	pasql.append(" AND  date_format(TPD.DELIVER_DATE,'%Y-%m-%d') >='" + queryParams.get("startDate") +"' \n");
        }
        if(!StringUtils.isNullOrEmpty(queryParams.get("endDate"))){ 
        	pasql.append(" AND  date_format(TPD.DELIVER_DATE,'%Y-%m-%d') <='" + queryParams.get("endDate") +"' \n");
        }
       //订单类型
        if(!StringUtils.isNullOrEmpty(queryParams.get("orderType"))){ 
        	pasql.append(" AND TPO.ORDER_TYPE= ? \n");
       		params.add(queryParams.get("orderType"));
        }
        
       //订单状态
        if(!StringUtils.isNullOrEmpty(queryParams.get("deliverStatus"))){ 
        	pasql.append("  AND TPD.DELIVER_STATUS= ? \n");
       		params.add(queryParams.get("deliverStatus"));
        }
		return OemDAOUtil.findAll(pasql.toString(), params);
        
	}
	
}
