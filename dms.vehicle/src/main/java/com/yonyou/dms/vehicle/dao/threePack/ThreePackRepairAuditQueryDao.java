package com.yonyou.dms.vehicle.dao.threePack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.StringUtils;
/**
 * 维修方案查询dao
 * @author zhoushijie
 *
 */
@Repository
public class ThreePackRepairAuditQueryDao extends OemBaseDAO{
	
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);

	public PageInfoDto findItem(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;
	}

	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		StringBuffer sql = new StringBuffer();
		sql.append("	select * from ( select  a.ID, a.DEALER_CODE, a.DEALER_NAME, a.VIN, a.PLATE_NO, a.MODEL, a.PLAN_NO, a.AUDIT_STATUS,a.FAUL_REMARK, DATE_FORMAT(SUBMIT_DATE, '%Y-%m-%d') as SUBMIT_DATE,a.RO_NO,(select name from tc_user where user_id = a.AUDIT_PERSON) AUDIT_PERSON,a.AUDIT_DATE  \n");
		sql.append("     FROM TT_THREEPACK_REPAIR_DCS a \n");
		sql.append("     where a.AUDIT_STATUS != ?  \n");
		params.add(OemDictCodeConstants.REPAIR_AUDIT_STATUS_04);
		if (!StringUtils.isNullOrEmpty(queryParam.get("dealerCode"))) {
			sql.append(" and a.DEALER_CODE = ? ");
			params.add(queryParam.get("dealerCode"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("dealerName"))) {
			sql.append(" and a.DEALER_NAME = ? ");
			params.add(queryParam.get("dealerName"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
			sql.append(" and a.VIN = ? ");
			params.add(queryParam.get("vin"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("plateNo"))) {
			sql.append(" and a.PLATE_NO = ? ");
			params.add(queryParam.get("plateNo"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("startdate"))) {
			sql.append("   AND DATE(a.SUBMIT_DATE) >= ? \n");
			params.add(queryParam.get("startdate"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("enddate"))) {
			sql.append("   AND DATE(a.SUBMIT_DATE) <= ? \n");
			params.add(queryParam.get("enddate"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("status"))) {
			sql.append(" and a.AUDIT_STATUS = ? ");
			params.add(queryParam.get("status"));
		}
		sql.append(" order by a.ID ) z ");
		return sql.toString();
	}
	/**
	 * Function       :  维修方案审核基本信息
	 * @param         :  id
	 */
	public PageInfoDto threePackAuditInfo(Long id){
		StringBuilder sql = new StringBuilder();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from (select t.ID, t.DEALER_CODE, t.DEALER_NAME, t.RECEIPT_RERSON, t.PLAN_NO, t.RO_NO, t.VIN, t.PLATE_NO, \n");
		sql.append(" t.PURCHASE_DATE, t.OWNER_NAME, t.MODEL, t.ENGINE_NO, t.MILEAGE, t.ARRIVE_DATE, \n");
		sql.append(" t.SUBMIT_DATE, t.DEALER_OPINOIN, t.AUDIT_DATE, t.AUDIT_STATUS,(select name from tc_user where user_id = t.AUDIT_PERSON) AUDIT_PERSON, t.AUDIT_REMARK \n");
		sql.append(" from TT_THREEPACK_REPAIR_DCS t \n");
		sql.append(" where t.ID = ?");
		params.add(id);
		sql.append(") zz");
		System.out.println(sql.toString());
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql.toString(), params);
		return pageInfoDto;
	}
	
	/**
	 * Function       :  用户信息
	 * @param         :  id
	 */
	public List<Map> threePackAudit(Long id){
		StringBuilder sql = new StringBuilder();
		List<Object> params = new ArrayList<Object>();
		sql.append("select t.ID, t.DEALER_CODE, t.DEALER_NAME, t.RECEIPT_RERSON, t.PLAN_NO, t.RO_NO, t.VIN, t.PLATE_NO, \n");
		sql.append(" t.PURCHASE_DATE, t.OWNER_NAME, t.MODEL, t.ENGINE_NO, t.MILEAGE, t.ARRIVE_DATE, \n");
		sql.append(" t.SUBMIT_DATE, t.DEALER_OPINOIN, t.AUDIT_DATE, t.AUDIT_STATUS,(select name from tc_user where user_id = t.AUDIT_PERSON) AUDIT_PERSON, t.AUDIT_REMARK \n");
		sql.append(" from TT_THREEPACK_REPAIR_DCS t \n");
		sql.append(" where t.ID = ?");
		params.add(id);
	
		List<Map> pageInfoDto = OemDAOUtil.findAll(sql.toString(), params);
		return pageInfoDto;
	}
	
	/**
	 * Function       :  三包维修方案工时查询
	 * @param         :  request-ID
	 */
	public PageInfoDto  threePackLabourList(Long id){
		List<Object> params = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		sql.append("  select a.ID, a.LABOUR_CODE, a.LABOUR_NAME, a.LABOUR_NUM, a.REPAIR_TYPE, a.REMARK \n");
		sql.append("  from TT_THREEPACK_REPAIR_LABOUR_DCS a where 1=1  \n");		
		sql.append("  and  a.PLAN_ID = ? ");
		params.add(id);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql.toString(), params);
		return pageInfoDto;
	}
	
	/**
	 * Function       :  三包维修方案配件查询
	 * @param         :  request-ID
	 */
	public PageInfoDto  threePackPartList(Long id){
		List<Object> params = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		sql.append("  select a.ID, a.PART_CODE, a.PART_NAME, a.PART_NUM, a.REPLACE_TIMES, a.WARNITEM_NO, a.WARNITEM_TIMES, a.REMARK  \n");
		sql.append("  from TT_THREEPACK_REPAIR_PART_DCS a where 1=1  \n");		
		sql.append("  and  a.PLAN_ID = ? ");
		params.add(id);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql.toString(), params);
		return pageInfoDto;
	}
	
	/**
	 * 通过用户ID获取姓名
	 * 
	 * @return
	 */
	public String getUserNameByUserid(Long userid){
		String sql = "select u.NAME from TC_USER u where u.USER_ID = "+userid;
		List list = pageQuery(sql, null,getFunName());
		String username = "";
		if(list.size()>0){
			Map tep = (Map) list.get(0);
			username = tep.get("NAME").toString();
		}
		return username;
	}
}
