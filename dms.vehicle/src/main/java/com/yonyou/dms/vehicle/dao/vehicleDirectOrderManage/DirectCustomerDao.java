package com.yonyou.dms.vehicle.dao.vehicleDirectOrderManage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.utils.common.StringUtils;
/**
 * 直销客户管理
 * @author Administrator
 *
 */
@Repository
public class DirectCustomerDao extends OemBaseDAO{
	/**
	 * 直销客户查询
	 */
	public PageInfoDto  directCustomerQuery(Map<String, String> queryParam) {
		   List<Object> params = new ArrayList<>();
		    String sql = getQuerySql(queryParam,params);
		    PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql,params);
		    System.out.println(pageInfoDto);
			return pageInfoDto;
	}
	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT PAYMENT_BANK,BANK_NO,REAMK, \n");
		sql.append(" DIRECT_ID, CUSTOMER_NO,CUSTOMER_NAME,CUSTOMER_TYPE,LINKMAN_NAME, \n");
		sql.append(" LINKMAN_TEL, LINKMAN_SEX, LINKMAN_ADDR,ID_TYPE,ID_NO,BIG_CUSTOMER_CODE,BIG_CUSTOMER_TYPE \n");
		sql.append(" FROM TT_BIG_CUSTOMER_DIRECT \n");
		sql.append("WHERE 1=1 \n");

		  if (!StringUtils.isNullOrEmpty(queryParam.get("customerNo"))) {
			sql.append("AND CUSTOMER_NO like'%"+queryParam.get("customerNo")+"%' \n");
		}
		  if (!StringUtils.isNullOrEmpty(queryParam.get("customerName"))) {
			sql.append("AND CUSTOMER_NAME like'%"+queryParam.get("customerName")+"%' \n");
		}
		  if (!StringUtils.isNullOrEmpty(queryParam.get("contactName"))) {
			sql.append("AND LINKMAN_NAME like '%"+queryParam.get("contactName")+"%' \n");
		}
		  if (!StringUtils.isNullOrEmpty(queryParam.get("contactNo"))) {
			sql.append("AND LINKMAN_TEL like '%"+queryParam.get("contactNo")+"%' \n");
		}
		  if (!StringUtils.isNullOrEmpty(queryParam.get("cardId"))) {
			sql.append("AND ID_NO like '%"+queryParam.get("cardId")+"%' \n");
		}
		  if (!StringUtils.isNullOrEmpty(queryParam.get("contactAddress"))) {
			sql.append("AND LINKMAN_ADDR like '%"+queryParam.get("contactAddress")+"%' \n");
		}
		//sql.append("order by CREATE_DATE DESC WITH UR");
		 System.out.println(sql.toString());
			return sql.toString();
	}
	
	/**
	 * 直销客户信息导出
	 */
	public List<Map> download(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam, params);
		List<Map> download = OemDAOUtil.downloadPageQuery(sql, null);
		return download;
	}
	/**
	 * 查询有效银行
	 */
	public List<Map> queryBank() {
		StringBuilder sql = new StringBuilder(" SELECT  TC.BANK_NAME,TC.BTC_CODE  FROM TC_BANK TC WHERE STATUS = 10011001 ");
		List<Object> params = new ArrayList<Object>();
		//执行查询操作
		List<Map> result=OemDAOUtil.findAll(sql.toString(), params);
		return result;
	}

}
