package com.yonyou.dms.vehicle.dao.k4Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;

/**
 * @author liujiming
 * @date 2017年3月16日
 */
@Repository
public class DealerRebateTypeQueryDao extends OemBaseDAO {

	/**
	 * 返利类型维护 查询
	 * 
	 * @param queryParam
	 * @return pageInfoDto
	 */
	public PageInfoDto getDealerRebateTypeQueryList(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getRebateTypeSql(queryParam, params, null);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;

	}

	/**
	 * 返利类型维护 SQL
	 * 
	 * @param queryParam
	 * @param params
	 * @param codeId
	 * @return
	 */
	private String getRebateTypeSql(Map<String, String> queryParam, List<Object> params, String codeId) {

		StringBuilder sql = new StringBuilder();
		sql.append(
				"   SELECT CODE_ID,CODE_DESC,CREATE_DATE,(CASE STATUS  WHEN '10011001' THEN  '有效' WHEN '10011002' THEN  '无效'  END) STATUS FROM TT_VS_REBATE_TYPE  \n");
		return sql.toString();
	}

	/**
	 * 返利类型下拉选
	 * 
	 * @param queryParam
	 * @return pageInfoDto
	 */
	public List<Map> getDealerRebateTypeList(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		sql.append("		SELECT * FROM Tt_Vs_Rebate_Type  WHERE STATUS=10011001");
		List<Map> rebateTypeList = OemDAOUtil.findAll(sql.toString(), params);
		return rebateTypeList;
	}

	public String oemMaxRebateCodeQuery() {
		String sql = "select max(type_code) type_code from TT_VS_REBATE_TYPE WHERE TYPE_CODE != 99999999";// 生产环境有个99999999的值需要过滤。
		Map map = OemDAOUtil.findFirst(sql, null);
		String rebateCode = (String) map.get("TYPE_CODE");
		return rebateCode;
	}

}
