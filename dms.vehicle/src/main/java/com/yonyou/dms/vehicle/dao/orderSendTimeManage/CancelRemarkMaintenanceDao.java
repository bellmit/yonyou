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
 * 取消备注维护
 * @author Administrator
 *
 */
@Repository
public class CancelRemarkMaintenanceDao extends OemBaseDAO{
	/**
	 * 取消备注维护查询
	 */
	public PageInfoDto  CancelRemarkQuery(Map<String, String> queryParam) {
		   List<Object> params = new ArrayList<>();
		    String sql = getQuerySql(queryParam,params);
		    PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql,params);
		    System.out.println(pageInfoDto);
			return pageInfoDto;
	}
	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		StringBuffer sql = new StringBuffer("");
		sql.append("\n");
		sql.append(" SELECT \n");
		sql.append("A.CANCEL_REMARKS_ID AS CANCELREMARKSID,   \n");
		sql.append("A.CANCEL_REMARKS_NO AS CANCELREMARKSNO,\n");
		sql.append("A.CANCEL_REASON_TEXT AS CANCELREASONTEXT,\n");
		sql.append("A.UPDATE_TYPE AS UPDATETYPE,\n");
		sql.append("A.CREATE_DATE AS CREATE_DATE,\n");
		sql.append("A.CANCEL_REMARKS_STATUS AS STATUS \n");
		sql.append("FROM TM_ORDER_CANCEL_REMARKS A\n");
		sql.append("WHERE 1=1 \n");
		
		  if (!StringUtils.isNullOrEmpty(queryParam.get("cancelRemarksNo"))) {
			  sql.append("AND A.CANCEL_REMARKS_NO like'%"+queryParam.get("cancelRemarksNo")+"%' \n");
		}
		  if (!StringUtils.isNullOrEmpty(queryParam.get("cancelReasonText"))) {
			  sql.append("AND A.CANCEL_REASON_TEXT like'%"+queryParam.get("cancelReasonText")+"%' \n");
		}
		  if (!StringUtils.isNullOrEmpty(queryParam.get("cancelRemarksStatus"))) {
			  sql.append("AND A.CANCEL_REMARKS_STATUS like'%"+queryParam.get("cancelRemarksStatus")+"%' \n");
		}
		 System.out.println(sql.toString());
			return sql.toString();
	}
	

}
