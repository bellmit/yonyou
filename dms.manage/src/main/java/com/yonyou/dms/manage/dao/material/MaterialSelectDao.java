package com.yonyou.dms.manage.dao.material;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.utils.common.StringUtils;

@Repository
public class MaterialSelectDao extends OemBaseDAO {
	
	
	
	/**
	 * 获取品牌
	 * @param type
	 * @return
	 */
	public List<Map> getBrandList(Integer type) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append(" SELECT DISTINCT m1.GROUP_ID BRAND_ID,m1.GROUP_NAME BRAND_NAME FROM tm_vhcl_material_group m1,tm_vhcl_material_group m2 ");  
		sql.append(" WHERE m1.GROUP_ID = m2.PARENT_GROUP_ID AND m2.GROUP_LEVEL = 2  ");
		sql.append(" AND m1.STATUS = '10011001' ");
		if(!StringUtils.isNullOrEmpty(type)){
			if(type!=2){
				sql.append(" AND m2.GROUP_TYPE = ? ");
				params.add(type);
			}
		}
		return OemDAOUtil.findAll(sql.toString(), params);
	}
	/**
	 * 获取车系
	 * @param type
	 * @param brand
	 * @return
	 */
	public List<Map> getSeriesList(Integer type, String brand) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append(" SELECT DISTINCT m2.GROUP_ID SERIES_ID,m2.GROUP_NAME SERIES_NAME FROM tm_vhcl_material_group m1,tm_vhcl_material_group m2 ");  
		sql.append(" WHERE m1.GROUP_ID = m2.PARENT_GROUP_ID AND m2.GROUP_LEVEL = 2  ");
		sql.append(" AND m2.STATUS = '10011001' ");
		if(!StringUtils.isNullOrEmpty(type)){
			if(type!=2){
				sql.append(" AND m2.GROUP_TYPE = ? ");
				params.add(type);
			}
		}
		if(!StringUtils.isNullOrEmpty(brand)){
			sql.append(" AND m1.GROUP_ID = ? ");
			params.add(brand);
		}
		return OemDAOUtil.findAll(sql.toString(), params);
	}
	/**
	 * 获取车款
	 * @param type
	 * @param series
	 * @return
	 */
	public List<Map> getGroupList(Integer type, String series) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append(" SELECT DISTINCT m3.GROUP_ID,m3.GROUP_NAME FROM tm_vhcl_material_group m1,tm_vhcl_material_group m2 ,tm_vhcl_material_group m3    ");
		sql.append(" WHERE m1.GROUP_ID = m2.PARENT_GROUP_ID AND m2.GROUP_ID = m3.PARENT_GROUP_ID ");
		sql.append(" AND m1.GROUP_LEVEL = 2   ");
		sql.append(" AND m3.STATUS = '10011001'  ");
		if(!StringUtils.isNullOrEmpty(type)){
			if(type!=2){
				sql.append(" AND m1.GROUP_TYPE = ? ");
				params.add(type);
			}
		}
		if(!StringUtils.isNullOrEmpty(series)){
			sql.append(" AND m1.GROUP_ID = ? ");
			params.add(series);
		}
		System.out.println(sql.toString() + params.toString());
		return OemDAOUtil.findAll(sql.toString(), params);
	}
	
	/**
	 * 获取年款
	 * @param type
	 * @param group
	 * @return
	 */
	public List<Map> getModelYearList(Integer type, String group) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append(" SELECT DISTINCT m3.MODEL_YEAR FROM tm_vhcl_material_group m1,tm_vhcl_material_group m2 ,tm_vhcl_material_group m3    ");
		sql.append(" WHERE m1.GROUP_ID = m2.PARENT_GROUP_ID AND m2.GROUP_ID = m3.PARENT_GROUP_ID ");
		sql.append(" AND m1.GROUP_LEVEL = 2   ");
		sql.append(" AND m3.STATUS = '10011001'  ");
		if(!StringUtils.isNullOrEmpty(type)){
			if(type!=2){
				sql.append(" AND m1.GROUP_TYPE = ? ");
				params.add(type);
			}
		}
		if(!StringUtils.isNullOrEmpty(group)){
			sql.append(" AND m3.GROUP_ID = ? ");
			params.add(group);
		}
		return OemDAOUtil.findAll(sql.toString(), params);
	}
	/**
	 * 获取颜色
	 * @param type
	 * @param group
	 * @param modelYear
	 * @return
	 */
	public List<Map> getColorList(Integer type, String group, String modelYear) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append(" SELECT DISTINCT m4.COLOR_CODE , m4.COLOR_NAME   ");
		sql.append(" FROM tm_vhcl_material_group m1,tm_vhcl_material_group m2 ,tm_vhcl_material_group m3,  ");
		sql.append(" tm_vhcl_material_group_r mr,tm_vhcl_material m4  ");
		sql.append(" WHERE m1.GROUP_ID = m2.PARENT_GROUP_ID AND m2.GROUP_ID = m3.PARENT_GROUP_ID  ");
		sql.append(" AND m3.GROUP_ID = mr.GROUP_ID AND mr.MATERIAL_ID = m4.MATERIAL_ID  ");
		sql.append(" AND m1.GROUP_LEVEL = 2    ");
		sql.append(" AND m4.STATUS = '10011001'   ");
		if(!StringUtils.isNullOrEmpty(type)){
			if(type!=2){
				sql.append(" AND m1.GROUP_TYPE = ? ");
				params.add(type);
			}
		}
		if(!StringUtils.isNullOrEmpty(group)){
			sql.append(" AND m3.GROUP_ID = ? ");
			params.add(group);
		}
		if(!StringUtils.isNullOrEmpty(group)){
			sql.append(" AND m3.MODEL_YEAR = ? ");
			params.add(modelYear);
		}
		return OemDAOUtil.findAll(sql.toString(), params);
	}
	/**
	 * 获取内饰
	 * @param type
	 * @param group
	 * @param modelYear
	 * @param color
	 * @return
	 */
	public List<Map> getTrimList(Integer type, String group, String modelYear, String color) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append(" SELECT DISTINCT m4.TRIM_CODE , m4.TRIM_NAME   ");
		sql.append(" FROM tm_vhcl_material_group m1,tm_vhcl_material_group m2 ,tm_vhcl_material_group m3,  ");
		sql.append(" tm_vhcl_material_group_r mr,tm_vhcl_material m4  ");
		sql.append(" WHERE m1.GROUP_ID = m2.PARENT_GROUP_ID AND m2.GROUP_ID = m3.PARENT_GROUP_ID  ");
		sql.append(" AND m3.GROUP_ID = mr.GROUP_ID AND mr.MATERIAL_ID = m4.MATERIAL_ID  ");
		sql.append(" AND m1.GROUP_LEVEL = 2    ");
		sql.append(" AND m4.STATUS = '10011001'   ");
		if(!StringUtils.isNullOrEmpty(type)){
			if(type!=2){
				sql.append(" AND m1.GROUP_TYPE = ? ");
				params.add(type);
			}
		}
		if(!StringUtils.isNullOrEmpty(group)){
			sql.append(" AND m3.GROUP_ID = ? ");
			params.add(group);
		}
		if(!StringUtils.isNullOrEmpty(group)){
			sql.append(" AND m3.MODEL_YEAR = ? ");
			params.add(modelYear);
		}
		if(!StringUtils.isNullOrEmpty(group)){
			sql.append(" AND m4.COLOR_CODE = ? ");
			params.add(color);
		}
		return OemDAOUtil.findAll(sql.toString(), params);
	}
	
	
	
	
	
}
