package com.yonyou.dms.vehicle.dao.afterSales.basicDataMgr;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * 索赔工时维护
 * @author Administrator
 *
 */
@Repository
public class TtWrLabourDao extends OemBaseDAO{
	
	/**
	 * 查询所有车系集合
	 */
	
	public List<Map> getLabour(Map<String, String> queryParams) throws ServiceBizException {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT tvmg.GROUP_ID, tvmg.GROUP_CODE, tvmg.GROUP_NAME \n" );
		sql.append(" FROM TM_VHCL_MATERIAL_GROUP tvmg \n" );
		sql.append(" WHERE tvmg.GROUP_LEVEL=2 and tvmg.is_del = 0");
		return OemDAOUtil.findAll(sql.toString(), null);
	}
	/**
	 * 查询所有索赔工时维护
	 */
	public PageInfoDto  LabourQuery(Map<String, String> queryParam) {
		   List<Object> params = new ArrayList<>();
		    String sql = getQuerySql(queryParam,params);
		    PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql,params);
		    System.out.println(pageInfoDto);
			return pageInfoDto;
	}
	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		StringBuffer sql = new StringBuffer();
		sql.append("	 select twcs.ID,twcs.GROUP_ID,twcs.GROUP_CODE,twcs.LABOUR_CODE,twcs.LABOUR_NAME,twcs.CREATE_BY  \n");
		sql.append("     ,twcs.CREATE_DATE,twcs.UPDATE_BY,twcs.UPDATE_DATE,twcs.VER,twcs.IS_DEL\n");
		sql.append("     ,twcs.SKILL_CATEGORY,twcs.DAUL_CODE,twcs.RAW_SUBSIDY,twcs.LABOUR_TYPE,twcs.LABOUR_NUM\n");
		sql.append("     from TT_WR_LABOUR_DCS twcs \n");
		sql.append("     where  twcs.IS_DEL="+OemDictCodeConstants.IS_DEL_00+"       \n");

		  if (!StringUtils.isNullOrEmpty(queryParam.get("labourCode"))) {
				sql.append("AND twcs.LABOUR_CODE = "+queryParam.get("labourCode")+"  \n");
			}
		  if (!StringUtils.isNullOrEmpty(queryParam.get("labourName"))) {
				sql.append("AND twcs.LABOUR_NAME  like '%"+queryParam.get("labourName")+"%'  \n");
			}
		  if (!StringUtils.isNullOrEmpty(queryParam.get("groupCode"))) {
				sql.append("AND twcs.GROUP_CODE like  '%"+queryParam.get("groupCode")+"%'  \n");
			}
		 System.out.println(sql.toString());
			return sql.toString();
	}
	
	//通过group_code查询得到group_id
	public List<Map> getGroupId(String group_code) throws ServiceBizException {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT group_id FROM TT_WR_LABOUR WHERE_DCS  iS_del=0  ");
		  if (!StringUtils.isNullOrEmpty(group_code)) {
				sql.append("AND group_code  like  '%"+group_code+"%'  \n");
			}	
		System.out.println(sql.toString());
		return OemDAOUtil.findAll(sql.toString(), null);
	}
	
	
	
	

}
