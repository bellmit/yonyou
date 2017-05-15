package com.yonyou.dms.manage.dao.organization;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.function.common.OemDictCodeConstants;

@Repository
public class SgmOrgMaintainDao extends OemBaseDAO {

	public PageInfoDto searchSgmOrg(Map<String, String> param, LoginInfoDto loginInfo) {
		String orgId = param.get("orgId");
		Long companyId = loginInfo.getCompanyId();
		List<Object> queryParam = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("\n");
		sql.append(" select td.parent_org_id,tmp.org_name parent_org_name \n");
		sql.append(" ,td.org_code,td.org_name,td.status,td.org_id,td.company_id \n");
		sql.append(" from tm_org tmp \n");
		sql.append(" left outer join tm_org td on td.parent_org_id = tmp.org_id \n");
		sql.append(" where 1=1 \n");
		sql.append(" and td.company_id = ? \n" );
		queryParam.add(companyId);
		sql.append(" and td.org_type  in " + "(" + OemDictCodeConstants.ORG_TYPE_OEM + "," + OemDictCodeConstants.DUTY_TYPE_DEPT + "," + OemDictCodeConstants.DUTY_TYPE_LARGEREGION + "," + OemDictCodeConstants.DUTY_TYPE_SMALLREGION + ") \n" );
		if (StringUtils.isNotBlank(orgId)) { // 查询SGM部门
			sql.append("  and td.parent_org_id = ? \n");
			queryParam.add(orgId);
		}
		System.out.println("SQL \n ==================\n "+ sql + " \n==================");
		System.out.println("param:"+queryParam);
		PageInfoDto dto = OemDAOUtil.pageQuery(sql.toString(), queryParam);
		return dto;
	}

	/**
	 * 判断同一经销商下部门代码是否存在
	 * @param companyId
	 * @param string
	 * @param busiType
	 * @return
	 */
	public List<Map> isDeptCode(Long companyId, String deptCode, Integer busiType) {
		List<Object> queryParam = new ArrayList<Object>();
		String sql = "select td.org_id,td.parent_org_id,td.org_desc,td.org_name,td.org_code"
				+ " from tm_org td where td.company_id = '"
				+ companyId + "' " + "and td.org_code='" + deptCode + "' and td.buss_type = " + busiType + "";
		return OemDAOUtil.findAll(sql, queryParam);
	}
	
	/**
	 * 判断同一经销商下部门名称是否存在
	 * @param companyId
	 * @param deptCode
	 * @return
	 */
	public List<Map> isDeptName(Long companyId, String deptCode, Integer busiType){
		List<Object> queryParam = new ArrayList<Object>();
		String sql = "select td.org_id,td.parent_org_id,td.org_desc,td.org_name,td.org_code"
				+ " from tm_org td where td.company_id = '"
				+ companyId + "' " + "and td.org_name='" + deptCode + "' and td.buss_type = " + busiType + "";;
		return OemDAOUtil.findAll(sql, queryParam);
	}
	
	/**
	 * 修改时判断SGM下部门名称是否重复
	 * @param companyId
	 * @param orgCode
	 * @param orgName
	 * @param busiType
	 * @return
	 */
	public List<Map> getOrgIdByOrgName(Long companyId,String orgCode,String orgName,Integer busiType){
		List<Object> queryParam = new ArrayList<Object>();
		String sql = " select td.org_id from tm_org td where td.company_id = '"
				+ companyId
				+ "' "
				+ "and td.org_name = '"
				+ orgName
				+ "' and td.org_code <> '" + orgCode + "' and BUSS_TYPE = " + busiType + "";
		return OemDAOUtil.findAll(sql, queryParam);
	}

}
