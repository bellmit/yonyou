package com.yonyou.dms.vehicle.dao.afterSales.basicDataMgr;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * 索赔质保期维护
 * @author Administrator
 *
 */
@Repository
public class ClaimQualityDAO extends OemBaseDAO{
	
	/**
	 * 索赔质保期维护查询
	 */
	
	public PageInfoDto ClaimQualityQuery(Map<String, String> queryParam) {
		   List<Object> params = new ArrayList<>();
		    String sql = getQuerySql(queryParam,params);
		    PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql,params);
		    System.out.println(pageInfoDto);
			return pageInfoDto;
	}
	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		StringBuffer sql = new StringBuffer();
		sql.append("select \n");
		sql.append("	p.id,a.GROUP_CODE,a.GROUP_NAME,p.QUALITY_MILEAGE,p.QUALITY_TIME,p.THREEPACK_TIME,p.THREEPACK_MILEAGE \n");
	    sql.append("  From TM_VHCL_MATERIAL_GROUP a,TT_WR_WARRANTY_dcs p \n");
		sql.append("  	where   p.is_del = 0 \n");
		sql.append("  	and   a.GROUP_ID=p.MODEL_ID \n");
		  if (!StringUtils.isNullOrEmpty(queryParam.get("groupCode"))) {
			  if("%".equals(queryParam.get("groupCode"))){
					sql.append("			and a.GROUP_CODE ='%' \n");
			  }else{
				  sql.append("			and a.GROUP_CODE like '%" +queryParam.get("groupCode")+ "%' \n");
			  }
			}
		  if (!StringUtils.isNullOrEmpty(queryParam.get("groupName"))) {
			  if("%".equals(queryParam.get("groupName"))){
				  sql.append("			and a.GROUP_NAME ='%' \n");
			  }else{
					sql.append("			and a.GROUP_NAME like '%" +queryParam.get("groupName") + "%' \n");
				}
			  }
			 System.out.println(sql.toString());
			return sql.toString();
	}
	
	/**
	 * 修改信息回显
	 */
	public List<Map> getShuju(Long id){
		StringBuffer sql = new StringBuffer();
		sql.append("select \n");
		sql.append("  p.id,a.GROUP_CODE,a.GROUP_NAME,p.QUALITY_MILEAGE,p.QUALITY_TIME,p.THREEPACK_TIME,p.THREEPACK_MILEAGE \n");
	    sql.append("  From TM_VHCL_MATERIAL_GROUP a,TT_WR_WARRANTY_dcs p \n");
		sql.append("  where p.is_del = 0 \n");
		sql.append("  and a.GROUP_ID=p.MODEL_ID ");	
		  if (!StringUtils.isNullOrEmpty(id)) {
				sql.append("   and p.id="+id+"  \n");
			}
		  return OemDAOUtil.findAll(sql.toString(),null); 
		    
	}
	/**
	 *查询所有车系代码
	 */
    public List<Map> getAll(Map<String, String> queryParam){
        StringBuffer sql = new StringBuffer();
		sql.append("	 select t.GROUP_ID,t.GROUP_CODE,t.GROUP_NAME  \n");
		sql.append("     from TM_VHCL_MATERIAL_GROUP t where IS_DEL="+OemDictCodeConstants.IS_DEL_00 +" \n");
		  if (!StringUtils.isNullOrEmpty(queryParam.get("groupCode"))) {
				sql.append("   and t.group_code='"+queryParam.get("groupCode")+"'  \n");
			}
		  if (!StringUtils.isNullOrEmpty(queryParam.get("groupName"))) {
				sql.append("   and t.group_name='"+queryParam.get("groupName")+"'  \n");
			}
		  System.out.println(sql.toString());
    return OemDAOUtil.findAll(sql.toString(),null); 
}


}
