package com.yonyou.dms.manage.dao.organization;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.StringUtils;

@Repository
public class CompanyDao extends OemBaseDAO {
	
	/**
	 * 获取公司信息
	 * @param queryParams
	 * @return
	 */
	public PageInfoDto getCompanyList(Map<String, String> queryParams,int type) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT COMPANY_ID,COMPANY_CODE,COMPANY_SHORTNAME,COMPANY_NAME,STATUS FROM tm_company WHERE COMPANY_TYPE = ? ");
		if(type==1){
			params.add(OemDictCodeConstants.COMPANY_TYPE_OEM);
		}else{
			params.add(OemDictCodeConstants.COMPANY_TYPE_DEALER);
		}
		if(!StringUtils.isNullOrEmpty(queryParams.get("companyCode"))){
			sql.append(" AND COMPANY_CODE like ?");
			params.add("%"+queryParams.get("companyCode")+"%");
		}
		if(!StringUtils.isNullOrEmpty(queryParams.get("companyName"))){
			sql.append(" AND COMPANY_NAME like ?");
			params.add("%"+queryParams.get("companyName")+"%");
		}
		return OemDAOUtil.pageQuery(sql.toString(), params);
	}

}
