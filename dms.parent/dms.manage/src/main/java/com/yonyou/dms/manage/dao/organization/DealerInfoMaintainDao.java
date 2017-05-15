package com.yonyou.dms.manage.dao.organization;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.function.common.OemDictCodeConstants;

@Repository
public class DealerInfoMaintainDao extends OemBaseDAO {

	public PageInfoDto searchDealerInfo(Map<String, String> param, LoginInfoDto loginInfo) {
		List<Object> queryParam = new ArrayList<Object>();
		String sql = this.getSQL(param, loginInfo, queryParam);
		PageInfoDto dto = OemDAOUtil.pageQuery(sql, queryParam);
		return dto;				
	}
	
	private String getSQL(Map<String, String> param, LoginInfoDto loginInfo,List<Object> queryParam){
		String companyCode = param.get("companyCode");
		String companyName = param.get("companyName");
		String oemCompanyId = loginInfo.getCompanyId().toString();
		String companyType = param.get("companyType");
		
		StringBuffer sql = new StringBuffer();
		sql.append("select \n");
		sql.append("	c.company_id,c.company_code,c.company_shortname, \n");
		sql.append("	c.province_id,c.city_id, \n");
		sql.append("	c.company_name,c.company_en,c.status  \n");
		sql.append("  from tm_company c  \n");
		sql.append("  	where  1=1  \n");
		
		if (!"".equals(oemCompanyId) && oemCompanyId != null) {
			sql.append(" and c.oem_company_id = ?  \n");
			queryParam.add(oemCompanyId);
		}
		
		if (!"".equals(companyCode) && companyCode != null) {
			if("%".equals(companyCode)){
				sql.append(" and c.company_code ='%' \n");
			}else{
				sql.append(" and c.company_code like ? \n");
				queryParam.add("%" + companyCode + "%");
			}
		}
		if (!"".equals(companyName) && companyName != null) {
			if("%".equals(companyName)){
				sql.append(" and c.company_name = '%' \n");
			}else{
				sql.append(" and c.company_name like ? \n");
				queryParam.add("%" + companyName + "%");
			}
		}
		if (!"".equals(companyType) && companyType != null
				&& !"0".equals(companyType)) {
			sql.append(" and c.company_type = ? \n");
			queryParam.add(companyType);
		}
		
		System.out.println("SQL \n ================== "+ sql + "==================");
		return sql.toString();		
	}

	public PageInfoDto searchDealerDetail(String companyId) {
		List<Object> queryParam = new ArrayList<Object>();
		String sql = "select DEALER_ID,DEALER_CODE,DEALER_SHORTNAME,DEALER_TYPE "
				+ "from TM_DEALER "
				+ "where COMPANY_ID= ?";
		queryParam.add(companyId);
		System.out.println("SQL \n ================== "+ sql + "==================");
		PageInfoDto dto = OemDAOUtil.pageQuery(sql, queryParam);
		return dto;		
	}

	public List<Map> getDealer(String dlrCode, String dlrName, String dlrNameForShort) {
		List<Object> queryParam = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("select * \n");
		sql.append("	from tm_company a \n");
		sql.append("	where (a.company_code= ? \n");
		queryParam.add(dlrCode);
		sql.append("		  or a.company_name= ? \n");
		queryParam.add(dlrName);
		sql.append("		  or a.company_shortname = ? )\n");
		queryParam.add(dlrNameForShort);
		sql.append("		  AND A.STATUS = ? \n");
		queryParam.add(OemDictCodeConstants.STATUS_ENABLE);
		return OemDAOUtil.findAll(sql.toString(), queryParam);
	}

	public Map getMaxDealerId() {
		List<Object> queryParam = new ArrayList<Object>();
		String sql = "select max(company_id) as companyId from tm_company";
		return OemDAOUtil.findFirst(sql, queryParam);
	}

	public List<Map> getDealerForUpd(String dlrCode, String dlrName, String dlrNameForShort) {
		List<Object> queryParam = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("select * \n");
		sql.append("	FROM tm_company a \n");
		sql.append("	WHERE a.company_code in \n");
		sql.append("		(SELECT b.company_code \n");
		sql.append("			FROM tm_company b WHERE (b.company_name = ? \n");
		queryParam.add(dlrName);
		sql.append("				OR b.company_shortname = ? ) ");
		queryParam.add(dlrNameForShort);
		sql.append("and b.company_code <> ? ");
		queryParam.add(dlrCode);
		sql.append("and b.status = ? ) \n");
		queryParam.add(OemDictCodeConstants.STATUS_ENABLE);
		return OemDAOUtil.findAll(sql.toString(), queryParam);
	}

}
