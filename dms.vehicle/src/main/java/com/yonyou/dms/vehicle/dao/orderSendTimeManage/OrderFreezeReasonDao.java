package com.yonyou.dms.vehicle.dao.orderSendTimeManage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * 截停原因维护
 * @author Administrator
 *
 */
@Repository
public class OrderFreezeReasonDao extends OemBaseDAO{
	/**
	 * 截停原因查询
	 */
	
	public PageInfoDto  OrderFreezeReasonQuery(Map<String, String> queryParam) {
		   List<Object> params = new ArrayList<>();
		    String sql = getQuerySql(queryParam,params);
		    PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql,params);
		    System.out.println(pageInfoDto);
			return pageInfoDto;
	}
	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		StringBuffer sql = new StringBuffer();
		sql.append("\n");
		sql.append(" SELECT \n");
		sql.append("A.FREEZE_ID AS FREEZEID,  \n");
		sql.append("A.FREEZE_CODE AS FREEZECODE,\n");
		sql.append("A.FREEZE_REASON AS FREEZEREASON,\n");
		sql.append("A.CREATE_DATE AS CREATEDATE,\n");
		sql.append("A.UPDATE_DATE AS UPDATEDATE,\n");
		sql.append("A.STATUS \n");
		sql.append("FROM TM_ORDERFREEZEREASON A\n");
		sql.append("WHERE 1=1 \n");
		
		  if (!StringUtils.isNullOrEmpty(queryParam.get("freezeCode"))) {
			sql.append("AND A.FREEZE_CODE like'%"+queryParam.get("freezeCode")+"%' \n");
		}
		  if (!StringUtils.isNullOrEmpty(queryParam.get("freezeReason"))) {
			  sql.append("AND A.FREEZE_REASON like'%"+queryParam.get("freezeReason")+"%' \n");
		}
		  if (!StringUtils.isNullOrEmpty(queryParam.get("status"))) {
				sql.append("AND A.STATUS ='"+queryParam.get("status")+"' \n");
		}
		 System.out.println(sql.toString());
			return sql.toString();
	}
	

}
