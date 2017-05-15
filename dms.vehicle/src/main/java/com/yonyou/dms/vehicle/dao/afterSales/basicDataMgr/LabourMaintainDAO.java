package com.yonyou.dms.vehicle.dao.afterSales.basicDataMgr;

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
 * 预授权维修项目维护
 * @author Administrator
 *
 */
@Repository
public class LabourMaintainDAO extends OemBaseDAO{
	/**
	 * 授权维修项目维护查询
	 */
	public PageInfoDto  labourMaintainQuery(Map<String, String> queryParam) {
		   List<Object> params = new ArrayList<>();
		    String sql = getQuerySql(queryParam,params);
		    PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql,params);
		    System.out.println(pageInfoDto);
			return pageInfoDto;
	}
	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		StringBuffer sql = new StringBuffer();
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		sql.append("select t.ID,t.LABOUR_CODE,t.LABOUR_DESC,tvmg.GROUP_CODE from TT_WR_FOREAPPROVALLAB_dcs t,TM_VHCL_MATERIAL_GROUP tvmg \n");
		sql.append(" where t.OEM_COMPANY_ID =" +loginInfo.getCompanyId()+" ");
		sql.append(" and t.IS_DEL ="+OemDictCodeConstants.IS_DEL_00);
		sql.append(" and t.wrgroup_id = tvmg.group_id \n");
		  if (!StringUtils.isNullOrEmpty(queryParam.get("LABOUR_CODE"))) {
				sql.append(" and t.LABOUR_CODE like '%"+queryParam.get("LABOUR_CODE")+"%'  \n");
			}
		  if (!StringUtils.isNullOrEmpty(queryParam.get("LABOUR_DESC"))) {
				sql.append(" and t.LABOUR_DESC like '%"+queryParam.get("LABOUR_DESC")+"%'  \n");
			}
		  
		  if (!StringUtils.isNullOrEmpty(queryParam.get("GROUP_ID"))) {
				sql.append(" and tvmg.GROUP_ID = '"+queryParam.get("GROUP_ID")+"'  \n");
			}
		 System.out.println(sql.toString());
			return sql.toString();
	}
	
	/**
	 * 查询所有车系
	 * @param queryParams
	 * @return
	 * @throws ServiceBizException
	 */
	public List<Map> getAllCheXing(Map<String, String> queryParams) throws ServiceBizException {
		StringBuffer sql = new StringBuffer();
		sql.append(" select GROUP_ID,GROUP_CODE,GROUP_NAME from TM_VHCL_MATERIAL_GROUP where GROUP_LEVEL = 2 ");
		return OemDAOUtil.findAll(sql.toString(), null);
	}
	
	//通过查询工时信息新增
	  public List<Map> getAll(Map<String, String> queryParam){
	        StringBuffer sql = new StringBuffer();
	    	LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
	        sql.append("select t.ID,t.LABOUR_CODE,t.LABOUR_DESC,tvmg.GROUP_CODE,tvmg.GROUP_ID from TT_WR_FOREAPPROVALLAB_dcs t,TM_VHCL_MATERIAL_GROUP tvmg \n");
			sql.append(" where t.OEM_COMPANY_ID =" +loginInfo.getCompanyId()+" ");
			sql.append(" and t.IS_DEL ="+OemDictCodeConstants.IS_DEL_00);
			sql.append(" and t.wrgroup_id = tvmg.group_id \n");
			  if (!StringUtils.isNullOrEmpty(queryParam.get("LABOUR_CODE"))) {
					sql.append(" and t.LABOUR_CODE like '%"+queryParam.get("LABOUR_CODE")+"%'  \n");
				}
			  if (!StringUtils.isNullOrEmpty(queryParam.get("LABOUR_DESC"))) {
					sql.append(" and t.LABOUR_DESC like '%"+queryParam.get("LABOUR_DESC")+"%'  \n");
				}
			  if (!StringUtils.isNullOrEmpty(queryParam.get("GROUP_ID"))) {
					sql.append(" and tvmg.GROUP_ID = '"+queryParam.get("GROUP_ID")+"'  \n");
				}
			  System.out.println(sql.toString());
	        return OemDAOUtil.findAll(sql.toString(),null); 
	}
	  
	  
}
