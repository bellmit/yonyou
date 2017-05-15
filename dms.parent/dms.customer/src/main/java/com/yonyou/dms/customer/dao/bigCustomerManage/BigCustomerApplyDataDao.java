package com.yonyou.dms.customer.dao.bigCustomerManage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * 政策申请数据定义
 * @author Administrator
 *
 */
@SuppressWarnings("rawtypes")
@Repository
public class BigCustomerApplyDataDao extends OemBaseDAO{

	public PageInfoDto applyDateQuery(Map<String, String> queryParam) {
		   List<Object> params = new ArrayList<>();
		    String sql = getQuerySql(queryParam,params);
		    PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql,params);
			return pageInfoDto;
	}

	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		StringBuilder sql = new StringBuilder();
		sql.append("  SELECT TBCAD.BIG_CUSTOMER_APPLY_ID,  ");
		sql.append("  TBCAD.PS_TYPE,  ");
		sql.append("  TBCAD.EMPLOYEE_TYPE,  ");
		sql.append("  TBCAD.NUMBER, ");
		sql.append("  TBCAD.STATUS,  ");
		sql.append("  CASE TBCAD.IS_SCAN WHEN TBCAD.IS_SCAN=0 THEN '未下发' ELSE'已下发' END  as  IS_SCAN ");
		sql.append("  FROM TT_BIG_CUSTOMER_APPLY_DATA TBCAD  ");
		sql.append("  LEFT JOIN  tc_code_dcs  t  ON  tbcad.EMPLOYEE_TYPE=t.code_id  ");
		sql.append("  where 1=1"); 
		sql.append("  AND TBCAD.IS_DELETE = 0  ");
	    //政策类别
		  if (!StringUtils.isNullOrEmpty(queryParam.get("type"))) {
	            sql.append("  AND TBCAD.PS_TYPE = ? ");
	            params.add( queryParam.get("type"));
	        }
		  //客户类型
	        if (!StringUtils.isNullOrEmpty(queryParam.get("employeetype"))) {
	            sql.append("  AND t.code_desc  =  ?   ");
	            params.add( queryParam.get("employeetype"));
	        }
	        //状态
	        if (!StringUtils.isNullOrEmpty(queryParam.get("status"))) {
	            sql.append("  AND TBCAD.STATUS  =  ?  ");
	            params.add( queryParam.get("status"));
	        }
		return sql.toString();
	}

	
	/**
	 * 通过政策类别查询客户类型
	 */
	public List<Map> getEmpType(String employeeTypeCode, Map<String, String> queryParams)
			throws ServiceBizException {
		if (employeeTypeCode.equals("15971001")) {//集团销售
			employeeTypeCode = "1098";
		} else if (employeeTypeCode.equals("15971003")) {//团购
			employeeTypeCode = "1099";
		}
		StringBuffer sql = new StringBuffer();
		sql.append("   SELECT TC.CODE_ID,TC.CODE_DESC FROM tc_code_dcs TC WHERE TC.CODE_ID NOT IN (10991003) AND TC.TYPE = "+employeeTypeCode+" order by TC.CODE_ID ");
		return OemDAOUtil.findAll(sql.toString(), null);
	}



}
