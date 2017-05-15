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
 * 配件订单管理Dao
 * @author ZhaoZ
 * @date 2017年3月24日
 */
@Repository
public class PartOrderDao extends OemBaseDAO{

	/**
	 * 配件订单审核查询
	 * @param queryParams
	 * @return
	 */
	public PageInfoDto findOrderPartList(Map<String, String> queryParams) {
		List<Object> params = new ArrayList<Object>();
		String sql = getOrderPartListSql(queryParams, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;
	}

	/**
	 * 配件订单审核查询SQL
	 * @param queryParams
	 * @param params
	 * @return
	 */
	private String getOrderPartListSql(Map<String, String> queryParams, List<Object> params) {
		String dealerCode = queryParams.get("dealerCode");
		String code=""; 
		if(StringUtils.isNullOrEmpty(dealerCode)){ 
	      }else{
	    	   String[] dealerCodes=dealerCode.split(",");
	   		
	   			for(int i=0;i<dealerCodes.length;i++){
	   				code+=""+dealerCodes[i]+",";
	   				code=code.substring(0,code.length()-1);		
	   		}
	   			
	      }
		
		
		StringBuffer pasql = new StringBuffer();
		
		pasql.append("	SELECT TD.DEALER_ID,TD.DEALER_CODE,TD.DEALER_SHORTNAME,TPO.ORDER_NO, \n");
        pasql.append("	TPO.ORDER_ID,TPO.ORDER_TYPE,TPO.VIN,DATE_FORMAT(TPO.REPORT_DATE,'%Y-%m-%d') REPORT_DATE,TPO.ORDER_STATUS,TPO.SAP_ORDER_NO, \n");
        pasql.append("	case when ATT.ATT_ID is not null then 10041001 else 10041002 end IS_ATT \n");
        pasql.append("		 	FROM TM_DEALER TD,TT_PT_ORDER_dcs TPO \n");
        pasql.append("		 	left join TT_PT_ORDER_ATT_dcs ATT on TPO.ORDER_ID=ATT.ORDER_ID  WHERE 1=1 \n");
        pasql.append("	AND exists(select * from  TT_PT_ORDER_DETAIL_dcs tpod where tpo.order_id=tpod.order_id) \n");
        pasql.append("  AND TPO.DEALER_ID=TD.DEALER_ID	AND TPO.ORDER_TYPE IN ('"+ OemDictCodeConstants.PART_ORDER_TYPE_01 +"','"+ OemDictCodeConstants.PART_ORDER_TYPE_08 +"') \n");
        pasql.append("	AND TPO.IS_DEL=0 AND TPO.ORDER_STATUS <> '"+ OemDictCodeConstants.PART_ORDER_STATUS_05+ "' \n");
        //订单号
        if(!StringUtils.isNullOrEmpty(queryParams.get("orderNo"))){ 
       		pasql.append(" 					AND TPO.ORDER_NO LIKE ? \n");
       		params.add("%"+queryParams.get("orderNo")+"%");
        }
        //订单类型
        if(!StringUtils.isNullOrEmpty(queryParams.get("orderType"))){ 
        	pasql.append(" 					AND TPO.ORDER_TYPE = ? \n");
        	params.add(queryParams.get("orderType"));
        }
        //经销商
        if(!StringUtils.isNullOrEmpty(dealerCode)){ 
        	pasql.append(" 					AND TD.DEALER_CODE IN (?) \n");
        	params.add(code);
        }
        //提报时间
        if(!StringUtils.isNullOrEmpty(queryParams.get("startDate"))){ 
     
        	pasql.append(" AND  DATE_FORMAT(TPO.REPORT_DATE,'%Y-%m-%d') >='" + queryParams.get("startDate") +"' \n");
        }
        if(!StringUtils.isNullOrEmpty(queryParams.get("endDate"))){ 
            
        	pasql.append(" AND  DATE_FORMAT(TPO.REPORT_DATE,'%Y-%m-%d') <='" + queryParams.get("endDate") +"' \n");
        }
        //订单状态
        if(!StringUtils.isNullOrEmpty(queryParams.get("orderStatus"))){ 
        	pasql.append(" 					AND ORDER_STATUS = ? \n");
        	params.add(queryParams.get("orderStatus"));
        }
     
		return pasql.toString();
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> findDealerInfoById(BigDecimal orderId) {
		StringBuilder sql = new StringBuilder();
		sql.append("    SELECT \n");
		sql.append("    (SELECT ORG_NAME FROM TM_ORG WHERE ORG_ID = TPO.BIG_ORG_ID) BIG_ORG_NAME,  \n");
		sql.append("    (SELECT ORG_NAME FROM TM_ORG WHERE ORG_ID = TPO.ORG_ID) SAMLL_ORG_NAME,\n");
		sql.append("    TD.DEALER_CODE, \n");
		sql.append("    TD.DEALER_SHORTNAME \n");
		sql.append("    FROM TT_PT_ORDER_dcs TPO \n");
		sql.append("   INNER JOIN TM_DEALER TD ON TPO.DEALER_ID = TD.DEALER_ID \n");
		sql.append("   WHERE 1 = 1 \n");
		sql.append("   AND TPO.ORDER_ID = " + orderId + "\n");
		return OemDAOUtil.findAll(sql.toString(), null).get(0);
	}

	/**
	 * 审核历史
	 * @param id
	 * @return
	 */
	public PageInfoDto findHidtoryInfo(BigDecimal orderId) {
		StringBuilder sql = new StringBuilder();
		sql.append("    SELECT \n");
		sql.append("    TPO.ORDER_ID, \n");
		sql.append("    TC.NAME,  \n");
		sql.append("    DATE_FORMAT(TPOH.CHECK_DATE,'%Y-%m-%d') CHECK_DATE,\n");
		sql.append("    TPOH.CHECK_STATUS, \n");
		sql.append("    TPOH.CHECK_OPINION \n");
		sql.append("    FROM TT_PT_ORDER_HISTORY_dcs TPOH \n");
		sql.append("   INNER JOIN TC_USER TC ON TC.USER_ID = TPOH.CREATE_BY \n");
		sql.append("   INNER JOIN TT_PT_ORDER_dcs TPO ON TPO.ORDER_ID=TPOH.ORDER_ID \n");
		sql.append("   WHERE 1 = 1 \n");
		sql.append("   AND TPO.ORDER_ID = " + orderId + "\n");
		return OemDAOUtil.pageQuery(sql.toString(), null);
	}

	/**
	 * 信息导出
	 * @param queryParams
	 * @return
	 */
	public List<Map> queryDownLoadList(Map<String, String> queryParams) {
		List<Object> params = new ArrayList<Object>();
		String sql = getDownLoadListSql(queryParams, params);
		List<Map> list = OemDAOUtil.findAll(sql, params);
		return list;
	}

	/**
	 * 信息导出SQL
	 * @param queryParams
	 * @return
	 */
	private String getDownLoadListSql(Map<String, String> queryParams, List<Object> params) {
		StringBuffer pasql = new StringBuffer();
		pasql.append("SELECT TOR2.ORG_DESC BIG_ORG_NAME,TOR3.ORG_DESC ORG_NAME,TD.DEALER_ID,TD.DEALER_CODE,TD.DEALER_SHORTNAME, \n  ");
        pasql.append("TPO.ORDER_NO,TPO.ORDER_ID,TPO.ORDER_TYPE,TPO.SAP_ORDER_NO,DATE_FORMAT(TPO.REPORT_DATE,'%Y-%m-%d') REPORT_DATE,TPO.ORDER_STATUS, \n  ");
        pasql.append("TPO.VIN, TPO.ELEC_CODE, TPO.MECH_CODE, TPO.CUSTOMER_NAME, TPO.CUSTOMER_PHONE, TPO.LICENSE_NO, TPO.KEY_CODE, TPO.IS_EMERG, TPO.IS_REPAIR_BYSELF, TPO.LEAVE_WORD, \n  ");
        pasql.append("case when ATT.ATT_ID is not null then 10041001 else 10041002 end IS_ATT \n  ");
        pasql.append("FROM TM_DEALER TD,TM_DEALER_ORG_RELATION TDOR,TM_ORG TOR3,TM_ORG TOR2,TT_PT_ORDER_dcs TPO	\n  ");
        pasql.append("left join TT_PT_ORDER_ATT_dcs ATT on TPO.ORDER_ID=ATT.ORDER_ID WHERE 1=1 \n  ");
        pasql.append("AND TDOR.DEALER_ID = TD.DEALER_ID \n  ");
        pasql.append("AND (TOR3.ORG_ID = TDOR.ORG_ID AND TOR3.ORG_LEVEL = 3 ) \n  ");
        pasql.append("AND (TOR3.PARENT_ORG_ID = TOR2.ORG_ID AND TOR2.ORG_LEVEL = 2 ) \n  ");
        pasql.append("AND  TOR3.BUSS_TYPE ="+OemDictCodeConstants.ORG_BUSS_TYPE_02+" \n  ");
        pasql.append("AND TOR2.BUSS_TYPE ="+OemDictCodeConstants.ORG_BUSS_TYPE_02+" \n  ");
        pasql.append("		 	AND exists(select * from  TT_PT_ORDER_DETAIL_dcs tpod where tpo.order_id=tpod.order_id) \n  ");
        pasql.append("		 	AND TPO.DEALER_ID=TD.DEALER_ID	AND TPO.ORDER_TYPE IN ('"+ OemDictCodeConstants.PART_ORDER_TYPE_01 +"','"+ OemDictCodeConstants.PART_ORDER_TYPE_08 +"') \n  ");
        pasql.append("		 	AND TPO.IS_DEL=0 AND TPO.ORDER_STATUS <> '"+ OemDictCodeConstants.PART_ORDER_STATUS_05+ "'	\n  ");
        //订单号
        if(!StringUtils.isNullOrEmpty(queryParams.get("orderNo"))){ 
        	pasql.append(" 					AND TPO.ORDER_NO LIKE ? \n");
       		params.add("%"+queryParams.get("orderNo")+"%");
        }
        
        //订单类型
        if(!StringUtils.isNullOrEmpty(queryParams.get("orderType"))){ 
        	pasql.append(" 					AND TPO.ORDER_TYPE = ? \n");
       		params.add(queryParams.get("orderType"));
        }
        //经销商
        if(!StringUtils.isNullOrEmpty(queryParams.get("dealerCode"))){ 
        	pasql.append(" 					AND TD.DEALER_CODE IN (?) \n");
       		params.add(queryParams.get("dealerCode"));
        }
        
        //提报时间
        if(!StringUtils.isNullOrEmpty(queryParams.get("startDate"))){ 
     
        	pasql.append(" AND  DATE_FORMAT(TPO.REPORT_DATE,'%Y-%m-%d') >='" + queryParams.get("startDate") +"' \n");
        }
        if(!StringUtils.isNullOrEmpty(queryParams.get("endDate"))){ 
        	
        	pasql.append(" AND  DATE_FORMAT(TPO.REPORT_DATE,'%Y-%m-%d') <='" + queryParams.get("endDate") +"' \n");
        }
        //订单状态
        if(!StringUtils.isNullOrEmpty(queryParams.get("orderStatus"))){ 
        	pasql.append(" 					AND ORDER_STATUS = ? \n");
        	params.add(queryParams.get("orderStatus"));
        }
		return pasql.toString();
      
	}

	public PageInfoDto dealerList(Map<String, String> queryParams) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT COMPANY_ID,DEALER_ID,DEALER_CODE,DEALER_SHORTNAME,DEALER_NAME,STATUS,DEALER_LEVEL,ADDRESS,DEALER_TYPE   ");
		sql.append(" FROM tm_dealer WHERE STATUS = "+ OemDictCodeConstants.STATUS_ENABLE + " ");
		if(loginInfo.getPoseBusType().equals(OemDictCodeConstants.POSE_BUS_TYPE_DWR)){
			sql.append(" AND DEALER_TYPE  =  ? ");
			params.add(OemDictCodeConstants.DEALER_TYPE_DWR);
		}else{
			sql.append(" AND DEALER_TYPE  =  ? ");
			params.add(OemDictCodeConstants.DEALER_TYPE_DVS);
		}
		if(!StringUtils.isNullOrEmpty(queryParams.get("dealerCode"))){
			sql.append(" and DEALER_CODE like ?");
			params.add("%"+queryParams.get("dealerCode")+"%");
		}
		if(!StringUtils.isNullOrEmpty(queryParams.get("dealerShortname"))){
			sql.append(" and DEALER_SHORTNAME like ? ");
			params.add("%"+queryParams.get("dealerShortname")+"%");
		}
		if(!StringUtils.isNullOrEmpty(queryParams.get("dealerName"))){
			sql.append(" and DEALER_NAME like ? ");
			params.add("%"+queryParams.get("dealerName")+"%");
		}
		System.out.println(sql.toString() + params.toString());
		return OemDAOUtil.pageQuery(sql.toString(),params);
	}
	
	
	/**
	 * 配件订单查询
	 * @param queryParams
	 * @return
	 */
	public PageInfoDto orderInfos(Map<String, String> queryParams) {
		List<Object> params = new ArrayList<Object>();
		String sql = getorderInfosSql(queryParams, params);
		return OemDAOUtil.pageQuery(sql, params);
	}

	/**
	 * 配件订单查询SQL
	 * @param queryParams
	 * @param params
	 * @return
	 */
	private String getorderInfosSql(Map<String, String> queryParams, List<Object> params) {
		String code="";
		if(!StringUtils.isNullOrEmpty(queryParams.get("dealerCode"))){
			String[] dealerCodes=queryParams.get("dealerCode").split(",");
				for(int i=0;i<dealerCodes.length;i++){
					code+=""+dealerCodes[i]+",";
				}
				code=code.substring(0,code.length()-1);		
				
			
		}


		StringBuffer pasql = new StringBuffer();	
		pasql.append("	SELECT  TD.DEALER_ID,TD.DEALER_CODE,TD.DEALER_SHORTNAME,TPO.SAP_ORDER_NO,TPO.ORDER_NO,  \n  ");
        pasql.append("	TPO.ORDER_ID,TPO.ORDER_TYPE,TPO.VIN,TPO.ORDER_BALANCE,DATE_FORMAT(TPO.REPORT_DATE,'%Y-%m-%d %H:%i:%S') REPORT_DATE, \n  ");
        pasql.append("	TPO.IS_AFFIRM,DATE_FORMAT(TPO.DEAL_ORDER_AFFIRM_DATE,'%Y-%m-%d %H:%i:%S') DEAL_ORDER_AFFIRM_DATE, \n  ");    
        pasql.append("	TPO.ORDER_STATUS,TPO.EC_ORDER_NO,TPO.ORDER_ID, \n  ");
        //电商订单类型为：客户直销 则返回 是否车主直销为：是 
        //否则是否车主直销为： 否
        pasql.append("  CASE WHEN TPO.ORDER_TYPE = "+OemDictCodeConstants.PART_ORDER_TYPE_09+" THEN "+OemDictCodeConstants.IF_TYPE_YES+"\n");
        pasql.append("  ELSE "+OemDictCodeConstants.IF_TYPE_NO+" END AS IS_DIRECT_SELLING\n");
        
        pasql.append("	FROM TM_DEALER TD,TT_PT_ORDER_dcs TPO WHERE 1=1	\n  ");
        pasql.append("	AND TPO.DEALER_ID=TD.DEALER_ID	AND TPO.IS_DEL=0 \n  ");
        pasql.append("	AND exists(select * from  TT_PT_ORDER_DETAIL_dcs tpod where tpo.order_id=tpod.order_id 	\n  ");
			
//			   pasql.append("	 SELECT TD.DEALER_ID,TD.DEALER_CODE,TD.DEALER_SHORTNAME,TT.SAP_ORDER_NO,TT.ORDER_NO,TT.ORDER_ID	,\n  ");
//			   pasql.append("	TT.ORDER_TYPE,TT.VIN,TT.ORDER_BALANCE,TT.REPORT_DATE,TT.ORDER_STATUS ,TT.ORDER_ID 	\n  ");
//			   pasql.append("		from  TM_DEALER TD,(select PART_CODE, TPO.ORDER_ID ORDER_ID,TPO.DEALER_ID DEALER_ID,\n  ");
//			   pasql.append("	TPO.SAP_ORDER_NO SAP_ORDER_NO,TPO.ORDER_NO ORDER_NO ,		\n  ");
//			   pasql.append("	TPO.ORDER_TYPE ORDER_TYPE,TPO.VIN VIN,TPO.ORDER_BALANCE ORDER_BALANCE,	\n  ");
//			   pasql.append("	DATE_FORMAT(TPO.REPORT_DATE,'%Y-%m-%d') REPORT_DATE,	\n  ");
//			   pasql.append("	TPO.ORDER_STATUS ORDER_STATUS from TT_PT_ORDER_dcs TPO 	\n  ");
//			   pasql.append("	where exists(select PART_CODE from  TT_PT_ORDER_DETAIL_dcs tpod where tpo.order_id=tpod.order_id) ) TT	\n  ");
//			   pasql.append("	 where TD.DEALER_ID = TT.DEALER_ID	\n  ");
			
        
        //订单号
        if(!StringUtils.isNullOrEmpty(queryParams.get("orderNo"))){
        	pasql.append(" 		AND TPO.ORDER_NO LIKE ? \n");
			params.add("%"+queryParams.get("orderNo")+"%");
		}
       
        //SAP订单号
        if(!StringUtils.isNullOrEmpty(queryParams.get("sapOrderNo"))){
        	pasql.append(" 		AND TPO.SAP_ORDER_NO LIKE ? \n");
			params.add("%"+queryParams.get("sapOrderNo")+"%");
		}
        //配件代码
        if(!StringUtils.isNullOrEmpty(queryParams.get("partCode"))){
        	pasql.append("	AND TPOD.PART_CODE LIKE ?  \n");
			params.add("%"+queryParams.get("partCode")+"%");
		}else{
        	pasql.append( " ) \n");
        }
        //订单类型
        if(!StringUtils.isNullOrEmpty(queryParams.get("orderType"))){
        	pasql.append(" 					AND TPO.ORDER_TYPE in (?) \n");
			params.add(queryParams.get("orderType"));
		}
      
        //审核状态
        if(!StringUtils.isNullOrEmpty(queryParams.get("partOrderStatus"))){
        	pasql.append(" 					AND TPO.ORDER_STATUS = ? \n");
        	params.add(queryParams.get("partOrderStatus"));
		}
       
        //确认状态
        if(!StringUtils.isNullOrEmpty(queryParams.get("confirmStatus"))){
        	pasql.append(" 					AND TPO.IS_AFFIRM = ? \n");
        	params.add(queryParams.get("confirmStatus"));
		}
        //经销商
        if(!StringUtils.isNullOrEmpty(code)){
        	pasql.append(" 					AND TD.DEALER_CODE IN (?) \n");
        	params.add(code);
		}
        //提报时间
        if(!StringUtils.isNullOrEmpty(queryParams.get("startDate"))){
        	pasql.append(" AND  DATE_FORMAT(TPO.REPORT_DATE,'%Y-%m-%d') >='" + queryParams.get("startDate") +"' \n");
		}
        if(!StringUtils.isNullOrEmpty(queryParams.get("endDate"))){
        	pasql.append(" AND  DATE_FORMAT(TPO.REPORT_DATE,'%Y-%m-%d') <='" + queryParams.get("endDate") +"' \n");
		}
		return pasql.toString();
        
      
	}

	/**
	 * 配件订单异常监控查询
	 * @param queryParams
	 * @return
	 */
	public PageInfoDto getOrderInterMonitor(Map<String, String> queryParams) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer pasql = new StringBuffer();	
		pasql.append("	SELECT TD.DEALER_ID,TD.DEALER_CODE,TD.DEALER_SHORTNAME,TPO.ORDER_NO,TPO.DCS_SEND_DATE,TPO.DCS_SEND_RESULT,TPO.DCS_SEND_MSG, \n  ");
        pasql.append("	TPO.ORDER_ID,TPO.ORDER_TYPE,TPO.VIN,DATE_FORMAT(TPO.REPORT_DATE,'%Y-%m-%d') REPORT_DATE,TPO.ORDER_STATUS \n  ");
        pasql.append("		 	FROM TM_DEALER TD,TT_PT_ORDER_dcs TPO WHERE 1=1	\n  ");
        pasql.append("  AND TPO.DEALER_ID=TD.DEALER_ID  \n  ");
		pasql.append("	 AND TPO.DCS_SEND_RESULT = '"+ OemDictCodeConstants.IF_TYPE_NO+ "'	\n  ");
        //订单号
		if(!StringUtils.isNullOrEmpty(queryParams.get("orderNo"))){	
			pasql.append(" 					AND TPO.ORDER_NO LIKE ? \n");
			params.add("%"+queryParams.get("orderNo")+"%");
		}
        //经销商
        if(!StringUtils.isNullOrEmpty(queryParams.get("dealerCode"))){
			String[] dealerCodes=queryParams.get("dealerCode").split(",");
			String code="";	
			for(int i=0;i<dealerCodes.length;i++){
					code+=""+dealerCodes[i]+",";
				}
				code=code.substring(0,code.length()-1);		
				pasql.append(" 					AND TD.DEALER_CODE IN (?) \n");
				params.add(code);
		}
        return OemDAOUtil.pageQuery(pasql.toString(), params);
	}

	/**
	 * 发票信息查询
	 * @param queryParams
	 * @return
	 */
	public PageInfoDto getqueryInvoices(Map<String, String> queryParams) {
		return new PageInfoDto();
	}

	/**
	 * 配件订单查询dlr
	 * @param queryParams
	 * @return
	 */
	public PageInfoDto dlrOrderInfo(Map<String, String> queryParams) {
		List<Object> params = new ArrayList<Object>();
		String sql = getdlrOrderInfoSql(queryParams, params);
		return OemDAOUtil.pageQuery(sql, params);
	}

	/**
	 *  配件订单查询dlrSQL
	 * @param queryParams
	 * @param params
	 * @return
	 */
	private String getdlrOrderInfoSql(Map<String, String> queryParams, List<Object> params) {
		LoginInfoDto logonUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		StringBuffer pasql = new StringBuffer();	
		pasql.append(" select * from  ( \n  ");
        pasql.append(" (SELECT distinct TPO.ORDER_ID ,TPO.SAP_ORDER_NO,TPO.ORDER_NO,TPO.ORDER_TYPE,TPO.VIN,TPO.IS_MOP_ORDER,   \n  ");
        pasql.append("	TPO.ORDER_BALANCE,DATE_FORMAT(TPO.CREATE_DATE,'%Y-%m-%d') CREATE_DATE,DATE_FORMAT(TPO.REPORT_DATE,'%Y-%m-%d') REPORT_DATE,TPO.CREATE_DATE ORDER_DATE,TPO.ORDER_STATUS, \n  ");
        //start 电商订单类型为：客户直销 则返回 是否车主直销为：是 
        //否则是否车主直销为： 否
        pasql.append("  CASE WHEN TPO.ORDER_TYPE = "+OemDictCodeConstants.PART_ORDER_TYPE_09+" THEN "+OemDictCodeConstants.IF_TYPE_YES+"\n");
        pasql.append("  ELSE "+OemDictCodeConstants.IF_TYPE_NO+" END AS IS_DIRECT_SELLING,\n");
        //end
        pasql.append("  TPO.IS_AFFIRM,DATE_FORMAT(TPO.DEAL_ORDER_AFFIRM_DATE,'%Y-%m-%d') DEAL_ORDER_AFFIRM_DATE,TPO.EC_ORDER_NO");
        pasql.append("	FROM TT_PT_ORDER_dcs TPO,TT_PT_ORDER_DETAIL_dcs TPOD WHERE 1=1 AND TPO.is_del != 1	 \n  ");
        pasql.append("	AND TPO.ORDER_ID=TPOD.ORDER_ID AND TPO.DEALER_ID = "+logonUser.getDealerId()+"	 \n  ");
        //订单号
        if(!StringUtils.isNullOrEmpty(queryParams.get("orderNo"))){ 
        	pasql.append(" 					AND TPO.ORDER_NO LIKE ? \n");
       		params.add("%"+queryParams.get("orderNo")+"%");
        }
      //SAP订单号
        if(!StringUtils.isNullOrEmpty(queryParams.get("sapOrderNo"))){
        	pasql.append(" 		AND TPO.SAP_ORDER_NO LIKE ? \n");
			params.add("%"+queryParams.get("sapOrderNo")+"%");
		}
        //配件代码
        if(!StringUtils.isNullOrEmpty(queryParams.get("partCode"))){
        	pasql.append("	AND TPOD.PART_CODE LIKE ?  \n");
			params.add("%"+queryParams.get("partCode")+"%");
		}
        //订单类型
        if(!StringUtils.isNullOrEmpty(queryParams.get("orderType"))){
        	pasql.append(" 					AND TPO.ORDER_TYPE in (?) \n");
			params.add(queryParams.get("orderType"));
		}
        //是否MOP拆单
        if(!StringUtils.isNullOrEmpty(queryParams.get("isMOP"))){
        	pasql.append(" 					AND TPO.IS_MOP_ORDER = ? \n");
			params.add(queryParams.get("isMOP"));
		}
        //审核状态
        if(!StringUtils.isNullOrEmpty(queryParams.get("checkStatus"))){
        	pasql.append(" 					AND TPO.ORDER_STATUS = ? \n");
        	params.add(queryParams.get("checkStatus"));
		}
        //创建时间
        if(!StringUtils.isNullOrEmpty(queryParams.get("startDate"))){ 
            
        	pasql.append(" AND  DATE_FORMAT(TPO.CREATE_DATE,'%Y-%m-%d') >='" + queryParams.get("startDate") +"' \n");
        }
        if(!StringUtils.isNullOrEmpty(queryParams.get("endDate"))){ 
            
        	pasql.append(" AND  DATE_FORMAT(TPO.CREATE_DATE,'%Y-%m-%d') <='" + queryParams.get("endDate") +"' \n");
        }
        //确认状态
        if(!StringUtils.isNullOrEmpty(queryParams.get("confirmStatus"))){
        	pasql.append(" 					AND TPO.IS_AFFIRM = ? \n");
        	params.add(queryParams.get("confirmStatus"));
		}
     
        pasql.append("	AND TPO.IS_DEL=0 ) \n  ");

        
        pasql.append(" union \n");
        pasql.append(" (SELECT distinct TPO.ORDER_ID,TPO.SAP_ORDER_NO,TPO.ORDER_NO,TPO.ORDER_TYPE,TPO.VIN,TPO.IS_MOP_ORDER,\n");
        pasql.append(" TPO.ORDER_BALANCE,DATE_FORMAT(TPO.CREATE_DATE,'%Y-%m-%d') CREATE_DATE,DATE_FORMAT(TPO.CREATE_DATE,'%Y-%m-%d') REPORT_DATE,TPO.CREATE_DATE ORDER_DATE,TPO.ORDER_STATUS,\n");
        //start 电商订单类型为：客户直销 则返回 是否车主直销为：是 
        //否则是否车主直销为： 否
        pasql.append("  CASE WHEN TPO.ORDER_TYPE = "+OemDictCodeConstants.PART_ORDER_TYPE_09+" THEN "+OemDictCodeConstants.IF_TYPE_YES+"\n");
        pasql.append("  ELSE "+OemDictCodeConstants.IF_TYPE_NO+" END AS IS_DIRECT_SELLING,\n");
        //end
        pasql.append("  TPO.IS_AFFIRM,DATE_FORMAT(TPO.DEAL_ORDER_AFFIRM_DATE,'%Y-%m-%d') DEAL_ORDER_AFFIRM_DATE,TPO.EC_ORDER_NO");
        pasql.append(" FROM TT_PT_ORDER_dcs TPO,TT_PT_ORDER_DETAIL_MOP_dcs TPODM WHERE 1=1 AND TPO.is_del != 1 \n");
        pasql.append(" AND TPO.ORDER_ID=TPODM.ORDER_ID	AND TPO.DEALER_ID = "+logonUser.getDealerId()+" \n");
        //订单号
        if(!StringUtils.isNullOrEmpty(queryParams.get("orderNo"))){ 
        	pasql.append(" 					AND TPO.ORDER_NO LIKE ? \n");
       		params.add("%"+queryParams.get("orderNo")+"%");
        }
      //SAP订单号
        if(!StringUtils.isNullOrEmpty(queryParams.get("sapOrderNo"))){
        	pasql.append(" 		AND TPO.SAP_ORDER_NO LIKE ? \n");
			params.add("%"+queryParams.get("sapOrderNo")+"%");
		}
        //配件代码
        if(!StringUtils.isNullOrEmpty(queryParams.get("partCode"))){
        	pasql.append("	AND TPODM.PART_CODE LIKE ?  \n");
			params.add("%"+queryParams.get("partCode")+"%");
		}
        //订单类型
        if(!StringUtils.isNullOrEmpty(queryParams.get("orderType"))){
        	pasql.append(" 					AND TPO.ORDER_TYPE in (?) \n");
			params.add(queryParams.get("orderType"));
		}
        //是否MOP拆单
        if(!StringUtils.isNullOrEmpty(queryParams.get("isMOP"))){
        	pasql.append(" 					AND TPO.IS_MOP_ORDER = ? \n");
			params.add(queryParams.get("isMOP"));
		}
        //审核状态
        if(!StringUtils.isNullOrEmpty(queryParams.get("checkStatus"))){
        	pasql.append(" 					AND TPO.ORDER_STATUS = ? \n");
        	params.add(queryParams.get("checkStatus"));
		}
        //提报时间
        if(!StringUtils.isNullOrEmpty(queryParams.get("startDate"))){ 
            
        	pasql.append(" AND  DATE_FORMAT(TPO.CREATE_DATE,'%Y-%m-%d') >='" + queryParams.get("startDate") +"' \n");
        }
        if(!StringUtils.isNullOrEmpty(queryParams.get("endDate"))){ 
            
        	pasql.append(" AND  DATE_FORMAT(TPO.CREATE_DATE,'%Y-%m-%d') <='" + queryParams.get("endDate") +"' \n");
        }
        //确认状态
        if(!StringUtils.isNullOrEmpty(queryParams.get("confirmStatus"))){
        	pasql.append(" 					AND TPO.IS_AFFIRM = ? \n");
        	params.add(queryParams.get("confirmStatus"));
		}
        pasql.append(" AND TPO.IS_DEL=0 ))dcs \n");
		return pasql.toString();
	}

	/**
	 * 配件订单查询下载
	 * @param queryParams
	 * @return
	 */
	public List<Map> orderDownLoad(Map<String, String> queryParams) {
		List<Object> params = new ArrayList<Object>();
		String sql = getorderDownLoadSql(queryParams, params);
		return OemDAOUtil.findAll(sql, params);
	}

	/**
	 * 配件订单查询下载SQL
	 * @param queryParams
	 * @param params
	 * @return
	 */
	private String getorderDownLoadSql(Map<String, String> queryParams, List<Object> params) {
		LoginInfoDto logonUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		StringBuffer pasql = new StringBuffer();	
		pasql.append(" select * from (  \n  ");
        pasql.append(" (SELECT distinct TPO.ORDER_ID ,TPO.SAP_ORDER_NO,TPO.ORDER_NO,TPO.ORDER_TYPE,TPO.VIN,TPO.IS_MOP_ORDER,   \n  ");
        pasql.append("	TPO.ORDER_BALANCE,DATE_FORMAT(TPO.CREATE_DATE,'%Y-%m-%d') REPORT_DATE,TPO.CREATE_DATE ORDER_DATE,TPO.ORDER_STATUS, \n  ");
        //start 电商订单类型为：客户直销 则返回 是否车主直销为：是 
        //否则是否车主直销为： 否
        pasql.append("  CASE WHEN TPO.ORDER_TYPE = "+OemDictCodeConstants.PART_ORDER_TYPE_09+" THEN "+OemDictCodeConstants.IF_TYPE_YES+"\n");
        pasql.append("  ELSE "+OemDictCodeConstants.IF_TYPE_NO+" END AS IS_DIRECT_SELLING,\n");
        //end
        pasql.append("  TPO.IS_AFFIRM,DATE_FORMAT(TPO.DEAL_ORDER_AFFIRM_DATE,'%Y-%m-%d') DEAL_ORDER_AFFIRM_DATE,TPO.EC_ORDER_NO");
        pasql.append("	FROM TT_PT_ORDER_dcs TPO,TT_PT_ORDER_DETAIL_dcs TPOD WHERE 1=1 AND TPO.is_del != 1	 \n  ");
        pasql.append("	AND TPO.ORDER_ID=TPOD.ORDER_ID AND TPO.DEALER_ID = "+logonUser.getDealerId()+"	 \n  ");
        //订单号
        if(!StringUtils.isNullOrEmpty(queryParams.get("orderNo"))){ 
        	pasql.append(" 					AND TPO.ORDER_NO LIKE ? \n");
       		params.add("%"+queryParams.get("orderNo")+"%");
        }
      //SAP订单号
        if(!StringUtils.isNullOrEmpty(queryParams.get("sapOrderNo"))){
        	pasql.append(" 		AND TPO.SAP_ORDER_NO LIKE ? \n");
			params.add("%"+queryParams.get("sapOrderNo")+"%");
		}
        //配件代码
        if(!StringUtils.isNullOrEmpty(queryParams.get("partCode"))){
        	pasql.append("	AND TPOD.PART_CODE LIKE ?  \n");
			params.add("%"+queryParams.get("partCode")+"%");
		}
        //订单类型
        if(!StringUtils.isNullOrEmpty(queryParams.get("orderType"))){
        	pasql.append(" 					AND TPO.ORDER_TYPE in (?) \n");
			params.add(queryParams.get("orderType"));
		}
        //是否MOP拆单
        if(!StringUtils.isNullOrEmpty(queryParams.get("isMOP"))){
        	pasql.append(" 					AND TPO.IS_MOP_ORDER = ? \n");
			params.add(queryParams.get("isMOP"));
		}
        //审核状态
        if(!StringUtils.isNullOrEmpty(queryParams.get("checkStatus"))){
        	pasql.append(" 					AND TPO.ORDER_STATUS = ? \n");
        	params.add(queryParams.get("checkStatus"));
		}
        //提报时间
        if(!StringUtils.isNullOrEmpty(queryParams.get("startDate"))){ 
            
        	pasql.append(" AND  DATE_FORMAT(TPO.CREATE_DATE,'%Y-%m-%d') >='" + queryParams.get("startDate") +"' \n");
        }
        if(!StringUtils.isNullOrEmpty(queryParams.get("endDate"))){ 
            
        	pasql.append(" AND  DATE_FORMAT(TPO.CREATE_DATE,'%Y-%m-%d') <='" + queryParams.get("endDate") +"' \n");
        }
        //确认状态
        if(!StringUtils.isNullOrEmpty(queryParams.get("confirmStatus"))){
        	pasql.append(" 					AND TPO.IS_AFFIRM = ? \n");
        	params.add(queryParams.get("confirmStatus"));
		}
        pasql.append("	AND TPO.IS_DEL=0 ) \n  ");

        
        pasql.append(" union \n");
        pasql.append(" (SELECT distinct TPO.ORDER_ID,TPO.SAP_ORDER_NO,TPO.ORDER_NO,TPO.ORDER_TYPE,TPO.VIN,TPO.IS_MOP_ORDER, \n");
        pasql.append(" TPO.ORDER_BALANCE,DATE_FORMAT(TPO.CREATE_DATE,'%Y-%m-%d') REPORT_DATE,TPO.CREATE_DATE ORDER_DATE,TPO.ORDER_STATUS,\n");
        //start 电商订单类型为：客户直销 则返回 是否车主直销为：是 
        //否则是否车主直销为： 否
        pasql.append("  CASE WHEN TPO.ORDER_TYPE = "+OemDictCodeConstants.PART_ORDER_TYPE_09+" THEN "+OemDictCodeConstants.IF_TYPE_YES+"\n");
        pasql.append("  ELSE "+OemDictCodeConstants.IF_TYPE_NO+" END AS IS_DIRECT_SELLING,\n");
        //end
        pasql.append("  TPO.IS_AFFIRM,DATE_FORMAT(TPO.DEAL_ORDER_AFFIRM_DATE,'%Y-%m-%d') DEAL_ORDER_AFFIRM_DATE,TPO.EC_ORDER_NO");
        pasql.append(" FROM TT_PT_ORDER_dcs TPO,TT_PT_ORDER_DETAIL_MOP_dcs TPODM WHERE 1=1 AND TPO.is_del != 1 \n");
        pasql.append(" AND TPO.ORDER_ID=TPODM.ORDER_ID	AND TPO.DEALER_ID = "+logonUser.getDealerId()+" \n");
        //订单号
        if(!StringUtils.isNullOrEmpty(queryParams.get("orderNo"))){ 
        	pasql.append(" 					AND TPO.ORDER_NO LIKE ? \n");
       		params.add("%"+queryParams.get("orderNo")+"%");
        }
      //SAP订单号
        if(!StringUtils.isNullOrEmpty(queryParams.get("sapOrderNo"))){
        	pasql.append(" 		AND TPO.SAP_ORDER_NO LIKE ? \n");
			params.add("%"+queryParams.get("sapOrderNo")+"%");
		}
        //配件代码
        if(!StringUtils.isNullOrEmpty(queryParams.get("partCode"))){
        	pasql.append("	AND TPODM.PART_CODE LIKE ?  \n");
			params.add("%"+queryParams.get("partCode")+"%");
		}
        //订单类型
        if(!StringUtils.isNullOrEmpty(queryParams.get("orderType"))){
        	pasql.append(" 					AND TPO.ORDER_TYPE in (?) \n");
			params.add(queryParams.get("orderType"));
		}
        //是否MOP拆单
        if(!StringUtils.isNullOrEmpty(queryParams.get("isMOP"))){
        	pasql.append(" 					AND TPO.IS_MOP_ORDER = ? \n");
			params.add(queryParams.get("isMOP"));
		}
        //审核状态
        if(!StringUtils.isNullOrEmpty(queryParams.get("checkStatus"))){
        	pasql.append(" 					AND TPO.ORDER_STATUS = ? \n");
        	params.add(queryParams.get("checkStatus"));
		}
        //提报时间
        if(!StringUtils.isNullOrEmpty(queryParams.get("startDate"))){ 
            
        	pasql.append(" AND  DATE_FORMAT(TPO.CREATE_DATE,'%Y-%m-%d') >='" + queryParams.get("startDate") +"' \n");
        }
        if(!StringUtils.isNullOrEmpty(queryParams.get("endDate"))){ 
            
        	pasql.append(" AND  DATE_FORMAT(TPO.CREATE_DATE,'%Y-%m-%d') <='" + queryParams.get("endDate") +"' \n");
        }
        //确认状态
        if(!StringUtils.isNullOrEmpty(queryParams.get("confirmStatus"))){
        	pasql.append(" 					AND TPO.IS_AFFIRM = ? \n");
        	params.add(queryParams.get("confirmStatus"));
		}
        pasql.append(" AND TPO.IS_DEL=0 ))dcs \n");
		return pasql.toString();
	}

}
