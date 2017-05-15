package com.yonyou.dms.vehicle.dao.paymentMaintenance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
/**
 * 经销商付款方式维护
 * @author Administrator
 *
 */
@Repository
public class PaymentMaintenanceDao extends OemBaseDAO {	
	 /**
	 * 查询经销商付款方式
	 */
	public PageInfoDto  PaymentMaintenanceQuery(Map<String, String> queryParam) {
		   List<Object> params = new ArrayList<>();
		    String sql = getQuerySql(queryParam,params);
		    PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql,params);
		    System.out.println(pageInfoDto);
			return pageInfoDto;
	}
	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM (");
		sql.append("SELECT T.DEALER_ID, \n");
		sql.append("	   TOR2.ORG_DESC AS ORG_DESC2, -- 大区 \n");
		sql.append("       TOR3.ORG_DESC AS ORG_DESC3, -- 小区 \n");
		sql.append("       TOR2.ORG_ID AS ORG_ID1, \n");
		sql.append("       TOR3.ORG_ID AS ORG_ID2, \n");
		sql.append("       TD.DEALER_CODE, -- 经销商代码 \n");
		sql.append("       TD.DEALER_SHORTNAME AS DEALER_NAME, -- 经销商名称 \n");
		sql.append("       T.PAYMENT_TYPE, \n");
		sql.append("       T.STATUS, \n");
		sql.append("       DATE_FORMAT (T.UPDATE_DATE, '%Y-%m-%d') AS UPDATE_DATE, \n");
		sql.append("       T.PAYMENT_ID \n");
		sql.append("  FROM TM_DEALER_PAYMENT T \n");
		sql.append(" INNER JOIN TM_DEALER TD ON TD.DEALER_ID = T.DEALER_ID \n");
		sql.append(" INNER JOIN TM_DEALER_ORG_RELATION TDOR ON TDOR.DEALER_ID = TD.DEALER_ID \n");
		sql.append(" INNER JOIN TM_ORG TOR3 ON TOR3.ORG_ID = TDOR.ORG_ID AND TOR3.ORG_LEVEL = 3 \n");
		sql.append(" INNER JOIN TM_ORG TOR2 ON TOR3.PARENT_ORG_ID = TOR2.ORG_ID AND TOR2.ORG_LEVEL = 2 \n");
		sql.append(" WHERE 1 = 1 \n");
		//经销商代码
		  if (!StringUtils.isNullOrEmpty(queryParam.get("dealerCode"))) {
			 String dealerCode = queryParam.get("dealerCode").replace(",", "','");
			sql.append("AND TD.DEALER_CODE IN ('"+dealerCode+"')  \n");
		}
		  //大区
		  if (!StringUtils.isNullOrEmpty(queryParam.get("bigOrgName"))) {
			  sql.append("AND TOR2.ORG_ID="+queryParam.get("bigOrgName")+"  \n");
		}
		  //小区
		  if (!StringUtils.isNullOrEmpty(queryParam.get("smallOrgName"))) {
			  sql.append("AND TOR3.ORG_ID ="+queryParam.get("smallOrgName")+"  \n");
		}

	    sql.append(" GROUP BY T.DEALER_ID, \n");
		sql.append("          TOR2.ORG_DESC, \n");
		sql.append("          TOR2.ORG_CODE, \n");
		sql.append("          TOR3.ORG_DESC, \n");
		sql.append("          TOR3.ORG_CODE, \n");
		sql.append("          TD.DEALER_CODE, \n");
		sql.append("          TD.DEALER_SHORTNAME, \n");
		sql.append("          T.STATUS, \n");
		sql.append("          DATE_FORMAT (T.UPDATE_DATE, '%Y-%m-%d'), \n");
		sql.append("          TOR2.ORG_ID, \n");
		sql.append("          TOR3.ORG_ID \n");
		sql.append("           ) K");
		System.out.println(sql.toString());
		return sql.toString();
	}
	
	public Map<Integer,String> getPaymentType(){
		StringBuffer sql = new StringBuffer();
		sql.append("select CODE_ID,CODE_DESC from tc_code_dcs where TYPE = '"+OemDictCodeConstants.K4_PAYMENT+"'");
		Map<Integer,String> map = new HashMap<>();
		List<Map> list = OemDAOUtil.findAll(sql.toString(), null);
		for(Map m : list){
			map.put(Integer.parseInt(String.valueOf(m.get("CODE_ID"))), String.valueOf(m.get("CODE_DESC")));
		}
		return map;
	}
	/**
	 * 通过id查询付款方式
	 * @param queryParam
	 * @param brandCode
	 * @return
	 */

	public List<Map> getPaymentById(String id) throws ServiceBizException {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT TOR2.ORG_DESC AS ORG_DESC2, -- 大区 \n");
		sql.append("       TOR3.ORG_DESC AS ORG_DESC3, -- 小区 \n");
		sql.append("       TOR2.ORG_ID, \n");
		sql.append("       TOR3.ORG_ID, \n");
		sql.append("       TD.DEALER_ID, \n");
		sql.append("       TD.DEALER_CODE, -- 经销商代码 \n");
		sql.append("       TD.DEALER_SHORTNAME AS DEALER_NAME, -- 经销商名称 \n");
		sql.append("       T.PAYMENT_TYPE, \n");
		sql.append("       T.STATUS, \n");
		sql.append("       DATE_FORMAT (T.UPDATE_DATE, '%Y-%m-%d') AS UPDATE_DATE, \n");
		sql.append("       T.PAYMENT_ID \n");
		sql.append("  FROM TM_DEALER_PAYMENT T \n");
		sql.append(" INNER JOIN TM_DEALER TD ON TD.DEALER_ID = T.DEALER_ID \n");
		sql.append(" INNER JOIN TM_DEALER_ORG_RELATION TDOR ON TDOR.DEALER_ID = TD.DEALER_ID \n");
		sql.append(" INNER JOIN TM_ORG TOR3 ON TOR3.ORG_ID = TDOR.ORG_ID AND TOR3.ORG_LEVEL = 3 \n");
		sql.append(" INNER JOIN TM_ORG TOR2 ON TOR3.PARENT_ORG_ID = TOR2.ORG_ID AND TOR2.ORG_LEVEL = 2 \n");
		sql.append(" WHERE  TD.DEALER_ID = '"+id+"' \n");	
		return OemDAOUtil.findAll(sql.toString(),null); 
	}
	

}
