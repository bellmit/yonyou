package com.yonyou.dms.vehicle.dao.afterSales.preAuthorization;

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
 * 预授权监控配件维护
 * @author Administrator
 *
 */
@Repository
public class PartMaintainDAO extends OemBaseDAO{
	/**
	 * 预授权监控配件维护查询
	 */
	public PageInfoDto PartMaintainQuery(Map<String, String> queryParam) {
		   List<Object> params = new ArrayList<>();
		    String sql = getQuerySql(queryParam,params);
		    PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql,params);
		    System.out.println(pageInfoDto);
			return pageInfoDto;
	}
	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		StringBuffer sb = new StringBuffer();
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		sb.append(" SELECT t.ID,t.PART_CODE,t.PART_NAME  \n");
		sb.append(" FROM TT_WR_FOREAPPROVALPT_dcs t \n ");
		sb.append(" WHERE t.OEM_COMPANY_ID = "+loginInfo.getCompanyId());
		sb.append(" and t.IS_DEL = "+OemDictCodeConstants.IS_DEL_00);

		if (!StringUtils.isNullOrEmpty(queryParam.get("partCode"))) {
			sb.append(" and t.part_code like '%"+queryParam.get("partCode")+"%' ");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("partName"))) {
			sb.append(" and t.part_name like '%"+queryParam.get("partName")+"%' ");
		}
		 System.out.println(sb.toString());
			return sb.toString();
	}
	/**
	 * 查询所有配件代码
	 * @param queryParam
	 * @return
	 */
    public List<Map> getAll(Map<String, String> queryParam){
        StringBuffer sql = new StringBuffer();
        sql.append("	 select t.ID,t.PART_CODE,t.PART_NAME  \n");
		sql.append("     from TT_PT_PART_BASE_dcs t where IS_DEL="+OemDictCodeConstants.IS_DEL_00 +" \n");
		
		  if (!StringUtils.isNullOrEmpty(queryParam.get("partCode"))) {
				sql.append("   and t.part_code like  '%"+queryParam.get("partCode")+"%'  \n");
			}
		  if (!StringUtils.isNullOrEmpty(queryParam.get("partName"))) {
				sql.append("   and t.part_name like '%"+queryParam.get("partName")+"%'  \n");
			}
		  System.out.println(sql.toString());
    return OemDAOUtil.findAll(sql.toString(),null); 
}

}
