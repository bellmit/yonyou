package com.yonyou.dms.vehicle.dao.threePack;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.vehicle.domains.DTO.threePack.TtThreepackRepairDTO;
import com.yonyou.dms.vehicle.domains.PO.threePack.TtThreepackRepairPO;
@Repository
public class ThreePackRepairAuditDao extends OemBaseDAO{
	
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);

	public PageInfoDto findAudit(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;
	}

	
	/**
	 * 维修方案审核列表
	 * 
	 * @param dealerCode
	 * @param vin
	 * @param plateNo
	 * @param dealerName
	 * @param planNo
	 * @param startdate
	 * @param enddate
	 * @param curPage
	 * @param pageSize
	 * @return
	 */
	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		StringBuffer sql = new StringBuffer();
		sql.append("	 select  a.ID,a.RO_NO, a.DEALER_CODE, a.DEALER_NAME, a.VIN, a.PLATE_NO, a.MODEL, a.PLAN_NO, a.AUDIT_STATUS, DATE_FORMAT(SUBMIT_DATE, '%y-%m-%d') as SUBMIT_DATE  \n");
		sql.append("     FROM TT_THREEPACK_REPAIR_DCS a \n");
		sql.append("     where a.AUDIT_STATUS ="+OemDictCodeConstants.REPAIR_AUDIT_STATUS_04+" \n");
		sql.append("     AND a.IS_DEL ="+OemDictCodeConstants.IS_DEL_00+" \n");
		if (!StringUtils.isNullOrEmpty(queryParam.get("dealerCode"))) {
			sql.append(" and UPPER(a.DEALER_CODE) like UPPER(?) ");
			params.add(queryParam.get("dealerCode"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("dealerName"))) {
			sql.append(" and UPPER(a.DEALER_NAME) like UPPER(?) ");
			params.add(queryParam.get("dealerName"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
			sql.append(" and UPPER(a.VIN) like UPPER(?) ");
			params.add(queryParam.get("vin"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("plateNo"))) {
			sql.append(" and UPPER(a.PLATE_NO) like UPPER(?) ");
			params.add(queryParam.get("plateNo"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("planNo"))) {
			sql.append(" and UPPER(a.PLAN_NO) like UPPER(?) ");
			params.add(queryParam.get("planNo"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("startdate"))) {
			sql.append("   AND DATE(a.SUBMIT_DATE) >= ? \n");
			params.add(queryParam.get("startdate"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("enddate"))) {
			sql.append("   AND DATE(a.SUBMIT_DATE) <= ? \n");
			params.add(queryParam.get("enddate"));
		}
//		sql.append("           order by SUBMIT_DATE,CREATE_DATE  \n");
		
		return sql.toString();
	}
	
	/**
	 * Function       :  维修方案审核基本信息
	 * @param         :  id
	 */
	public Map<String,Object> threePackAuditInfo(Long id){
		StringBuilder sql = new StringBuilder();
		sql.append("select t.ID, t.DEALER_CODE, t.DEALER_NAME, t.RECEIPT_RERSON, t.PLAN_NO, t.RO_NO, t.VIN, t.PLATE_NO,t.Dealer_Opinoin, \n");
		sql.append(" t.PURCHASE_DATE, t.OWNER_NAME, t.MODEL, t.ENGINE_NO, t.MILEAGE, t.ARRIVE_DATE \n");
		sql.append(" from TT_THREEPACK_REPAIR_DCS t \n");
		sql.append(" where t.ID = "+id);
		Map<String,Object> auditpo=new HashMap<>();
		List<Map> resultList = OemDAOUtil.findAll(sql.toString(), null);
		if (resultList!=null){
			if (resultList.size()>0) {
				auditpo = resultList.get(0);
			}
		}
		return auditpo;
	}
	
	/**
	 * Function       :  三包维修方案工时查询
	 * @param         :  request-ID
	 */
	public PageInfoDto  threePackLabourList(Long id){
		List<Object> params = new ArrayList<>();
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
		List<Object> params = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		sql.append("  select a.ID, a.PART_CODE, a.PART_NAME, a.PART_NUM, a.REPLACE_TIMES, a.WARNITEM_NO, a.WARNITEM_TIMES, a.REMARK  \n");
		sql.append("  from TT_THREEPACK_REPAIR_PART_DCS a where 1=1  \n");		
		sql.append("  and  a.PLAN_ID = ? ");
		params.add(id);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql.toString(), params);
        return pageInfoDto;
	}
	/**
	 * 审核
	 * 
	 * @param id
	 * @param user
	 * @throws ServiceBizException
	 */
	public void modifyMinclass(Long id, TtThreepackRepairDTO tcdto) throws ServiceBizException {
		TtThreepackRepairPO tc = TtThreepackRepairPO.findById(id);
		setTtThreepackRepairPO(tc, tcdto);
		tc.saveIt();
	}
	/**
	 * 设置对象属性
	 * 
	 * @param tc
	 * @param user
	 */
	private void setTtThreepackRepairPO(TtThreepackRepairPO tc, TtThreepackRepairDTO tcdto) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		tc.setString("FAUL_REMARK", tcdto.getFaulRemark());
		tc.setString("DEALER_OPINOIN", tcdto.getDealerOpinoin());
		tc.setString("audit_remark", tcdto.getAuditRemark());
		tc.setString("audit_status", tcdto.getAuditStatus());
		tc.setFloat("OCCUPY_TIME", tcdto.getOccupyTime());
		tc.setDate("audit_date", new Date());
		tc.setString("Audit_Person", loginInfo.getUserId());

	}
	
	public Map<String ,Object> find(String vin){
		List<Object> params = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		sql.append("  SELECT WARN_TIMES FROM TT_THREEPACK_WARN_DCS  WHERE vin=? "); 
		params.add(vin);
		Map<String,Object> auditpo=new HashMap<>();
		List<Map> resultList =new ArrayList<>();
		resultList= OemDAOUtil.findAll(sql.toString(), params);
		if (resultList!=null&&resultList.size()<=0){
			auditpo.put("WARN_TIMES",0.0f);
		}else{
			auditpo=resultList.get(0);
		}
        return auditpo;
		
	}


}
