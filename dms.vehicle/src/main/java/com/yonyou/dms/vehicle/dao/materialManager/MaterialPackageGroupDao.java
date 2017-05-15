package com.yonyou.dms.vehicle.dao.materialManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.vehicle.domains.DTO.materialManager.MaterialPackageGroupDTO;

/**
 * 车款组维护DAO
 * @author 夏威
 * 2017-1-20
 */

@Repository
public class MaterialPackageGroupDao extends OemBaseDAO {
	
	
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
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT * FROM tm_vhcl_pakage_group WHERE 1=1  \n");
		if(!StringUtils.isNullOrEmpty(queryParam.get("packageGroupCode"))){
			sql.append(" and PACKAGE_GROUP_CODE like ?");
			params.add("%"+queryParam.get("packageGroupCode")+"%");
		}
		if(!StringUtils.isNullOrEmpty(queryParam.get("packageGroupName"))){
			sql.append(" and PACKAGE_GROUP_NAME like ?");
			params.add("%"+queryParam.get("packageGroupName")+"%");
		}
		if(!StringUtils.isNullOrEmpty(queryParam.get("status"))){
			sql.append(" and STATUS = ?");
			params.add(queryParam.get("status"));
		}
		return sql.toString();
	}

	/**
	 * 下载查询
	 * @param queryParam
	 * @return
	 */
	public List<Map> queryMaterialGroupForExport(Map<String, String> queryParam) {
		 List<Object> params = new ArrayList<>();
		 String sql = getQuerySql(queryParam,params);
	     List<Map> resultList = OemDAOUtil.findAll(sql,params);
	     return resultList;
	}
	
	/**
	 * 校验车款组代码
	 * @param mgDto
	 * @return
	 */
	public Boolean checkCode(MaterialPackageGroupDTO mgDto) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql=new StringBuffer();
		sql.append(" SELECT COUNT(1) CNT FROM TM_VHCL_PAKAGE_GROUP WHERE PACKAGE_GROUP_CODE = ? ");
		params.add(mgDto.getPackageGroupCode());
		List<Map> list = OemDAOUtil.findAll(sql.toString(), params);
		Boolean flag = true;
		if(null!=list&&list.size()>0){
			if(Integer.parseInt(list.get(0).get("CNT").toString())>0){
				flag = false;
			}
		}
		return flag;
	}

}
