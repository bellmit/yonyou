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
 * 索赔配件质保期维护
 * @author Administrator
 *
 */
@Repository
public class ClaimPartDao extends OemBaseDAO{
	/**
	 *  索赔配件质保期维护查询
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
		sql.append("	t.id,t.PART_NAME,t.PART_CODE,CONCAT(t.quality_time,'月',ROUND(t.quality_mileage),'公里') AS clause \n");
		sql.append("  from TT_WR_PARTWARRANTY_dcs t  \n");
		sql.append("  where is_del = "+OemDictCodeConstants.IS_DEL_00);			
		if (!"".equals(queryParam.get("partCode")) && queryParam.get("partCode") != null) {
			if("%".equals(queryParam.get("partCode"))){
				sql.append("			and t.part_code ='%' \n");
			}else{
				sql.append("			and t.part_code like '%" + queryParam.get("partCode") + "%' \n");
			}
		}			
		if (!"".equals(queryParam.get("partName")) && queryParam.get("partName") != null) {
			if("%".equals(queryParam.get("partName"))){
				sql.append("			and t.part_name ='%' \n");
			}else{
				sql.append("			and t.part_name like '%" + queryParam.get("partName") + "%' \n");
			}
		}
			 System.out.println(sql.toString());
			return sql.toString();
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
				sql.append("   and t.part_name like'%"+queryParam.get("partName")+"%'  \n");
			}
		  System.out.println(sql.toString());
    return OemDAOUtil.findAll(sql.toString(),null); 
}

}
