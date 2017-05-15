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
import com.yonyou.dms.function.utils.common.StringUtils;
/**
 * 三包缺料配件查询dao
 * @author zhoushijie
 *
 */
@Repository
public class ThreePackWarnLackQueryDao extends OemBaseDAO{
	
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);

	public PageInfoDto findItem(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;
	}

	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT TD.DEALER_SHORTNAME,\n");
		sql.append("     TTL.DEALER_CODE,      \n");
		sql.append("     TTL.REPAIR_WORK_NO,     \n");
		sql.append("     TTL.VIN,                             \n");
		sql.append("     DATE_FORMAT(ttl.START_ORDER_DATE,  '%y-%m-%d') as START_ORDER_DATE ,   \n");
		sql.append("     ttl.LACK_MATE_CODE,       \n");
		sql.append("     ttl.LACK_MATE_NAME,           \n");
		sql.append("     ttl.LACK_MATE_AMOUNT      \n");
		sql.append(" from TT_THREEPACK_LACKMATE_DCS ttl           \n");
		sql.append(" left join TM_DEALER td on td.DEALER_CODE = ttl.DEALER_CODE  \n");
		sql.append(" WHERE 1=1 \n");
		  if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
				String vin = queryParam.get("vin");
				sql.append("   AND TTL.VIN = ? ");
				params.add(queryParam.get("vin"));
			}
		if (!StringUtils.isNullOrEmpty(queryParam.get("lackMateCode"))) {
			sql.append("   AND TTL.LACK_MATE_CODE = ? ");
			params.add(queryParam.get("lackMateCode"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("startdate"))) {
			sql.append("   AND DATE(ttl.START_ORDER_DATE) >= ? \n");
			params.add(queryParam.get("startdate"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("enddate"))) {
			sql.append("   AND DATE(ttl.START_ORDER_DATE) <= ? \n");
			params.add(queryParam.get("enddate"));
		}

		if(!StringUtils.isNullOrEmpty(queryParam.get("dealerCode"))){
			String dealerCode=null;
			dealerCode+=queryParam.get("dealerCode").substring(queryParam.get("dealerCode").length()-1).equals(",")?"0":"";
			dealerCode = queryParam.get("dealerCode").replaceAll(",", "','");
			sql.append(" and ttl.dealer_code in('").append(dealerCode).append("')");
		}
		return sql.toString();
	}

	/**
	 * 下载
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<Map> queryEmpInfoforExport(Map<String, String> queryParam) throws Exception {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam, params);
		return OemDAOUtil.findAll(sql.toString(), params);

	}
}
