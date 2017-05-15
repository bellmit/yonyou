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
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * 预授权其他费用维护
 * @author Administrator
 *
 */
@Repository
public class OtherMaintainDAO extends OemBaseDAO{
	
	/**
	 * 预授权其他费用维护查询
	 */
	public PageInfoDto OtherMaintainQuery(Map<String, String> queryParam) {
		   List<Object> params = new ArrayList<>();
		    String sql = getQuerySql(queryParam,params);
		    PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql,params);
		    System.out.println(pageInfoDto);
			return pageInfoDto;
	}
	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		StringBuffer sqlStr = new StringBuffer();
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		sqlStr.append("select t.ID,t.ITEM_CODE,t.ITEM_DESC,o.OTHER_FEE_NAME from TT_WR_FOREAPPROVALOTHERITEM_dcs t, TT_WR_OTHERFEE_dcs o \n");
		sqlStr.append(" where t.ITEM_CODE=o.OTHER_FEE_CODE and t.OEM_COMPANY_ID ="+loginInfo.getCompanyId()+" ");
		sqlStr.append(" and t.IS_DEL = o.IS_DEL and t.IS_DEL = "+OemDictCodeConstants.IS_DEL_00);
		if (!StringUtils.isNullOrEmpty(queryParam.get("ITEM_CODE"))) {
			sqlStr.append(" and t.ITEM_CODE like '%"+queryParam.get("ITEM_CODE")+"%' ");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("ITEM_DESC"))) {
			sqlStr.append("  and t.ITEM_DESC like '%"+queryParam.get("ITEM_DESC")+"%' ");
		}
		 System.out.println(sqlStr.toString());
			return sqlStr.toString();
	}
	/**
	 * 查询所有项目名称
	 */
	public List<Map> getAll() throws ServiceBizException {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT CONCAT_WS('---',t.OTHER_FEE_CODE,t.OTHER_FEE_NAME) AS NAME , t.OTHER_FEE_CODE  FROM TT_WR_OTHERFEE_dcs t where t.OEM_COMPANY_ID="+loginInfo.getCompanyId()+" ");
		sql.append("and t.IS_DEL = "+OemDictCodeConstants.IS_DEL_00 +"");
		System.out.println(sql.toString());
		return OemDAOUtil.findAll(sql.toString(), null);
	}

}
