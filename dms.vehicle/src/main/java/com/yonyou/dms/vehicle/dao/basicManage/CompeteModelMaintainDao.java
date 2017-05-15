package com.yonyou.dms.vehicle.dao.basicManage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.vehicle.domains.DTO.basicManage.TmCompeteBrandDTO;

@Repository
public class CompeteModelMaintainDao extends OemBaseDAO{
	
	public static final CompeteModelMaintainDao dao = new CompeteModelMaintainDao();
	
	/**
	 * 查询方法
	 * @param queryParam
	 * @return
	 */
	public PageInfoDto queryList(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam,params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql,params);
		return pageInfoDto;
	}
	
	/**
	 * SQL组装
	 * @param queryParam
	 * @param params
	 * @return
	 */
	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		StringBuffer sql = new StringBuffer();

		sql.append("SELECT BRAND_ID,BRAND_NAME,STATUS,REMARK \n");
		sql.append(" FROM TM_COMPETE_BRAND\n");

		return sql.toString();
	}

	/**
	 * 校验
	 * @param mgDto
	 * @return
	 */
	public Boolean checkCode(TmCompeteBrandDTO mgDto) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql=new StringBuffer();
		sql.append(" SELECT COUNT(1) CNT FROM TM_COMPETE_BRAND WHERE BRAND_NAME = ? ");
		params.add(mgDto.getBrandName());
		List<Map> list = OemDAOUtil.findAll(sql.toString(), params);
		Boolean flag = true;
		if(null!=list&&list.size()>0){
			if(Integer.parseInt(list.get(0).get("CNT").toString())>0){
				flag = false;
			}
		}
		return flag;
	}

	/**
	 * 获得经销商信息为下发数据
	 * 
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getSentDealerList() throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT DEALER_CODE FROM TM_DEALER  \n");
		sql.append("WHERE STATUS='").append(OemDictCodeConstants.STATUS_ENABLE).append("' \n");
		sql.append(" AND DEALER_TYPE='").append(OemDictCodeConstants.DEALER_TYPE_DVS).append("' \n");
		List<Map<String, Object>>  ps = dao.pageQuery(sql.toString(), null, getFunName());
		return ps;
	}

	/**
	 * 根据ID获取详细信息
	 * @param id
	 * @param loginInfo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> queryDetail(Long id) {
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = getDetailQuerySql(id);
		map = OemDAOUtil.findFirst(sql, null);
		return map;
	}

	/**
	 * 修改
	 * @param id
	 * @return
	 */
	private String getDetailQuerySql(Long id) {
		StringBuffer sql = new StringBuffer();

		sql.append("SELECT BRAND_ID,BRAND_NAME,STATUS,REMARK \n");
		sql.append(" FROM TM_COMPETE_BRAND\n");
		sql.append(" WHERE BRAND_ID = "+ id );
		return sql.toString();
		
	}

}
