package com.yonyou.dms.manage.dao.organization;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;

@Repository
public class DealerGroupMaintainDao extends OemBaseDAO {

	public PageInfoDto search(Map<String, String> param) {
		List<Object> queryParam = new ArrayList<Object>();
		String groupCode = param.get("groupCode");
		String groupName = param.get("groupName");
		String status = param.get("status");
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT GROUP_ID,GROUP_CODE,GROUP_NAME,GROUP_SHOTNAME,STATUS \n");
		sql.append("FROM TM_DEALER_GROUP \n");
		sql.append("WHERE 1=1 \n");
		if(StringUtils.isNotBlank(groupCode)){
			sql.append(" AND GROUP_CODE LIKE ?");
			queryParam.add("%"+groupCode+"%");
		}
		if(StringUtils.isNotBlank(groupName)){
			sql.append(" AND GROUP_NAME LIKE ?");
			queryParam.add("%"+groupName+"%");
		}
		if(StringUtils.isNotBlank(status)){
			sql.append(" AND STATUS = ?");
			queryParam.add(status);
		}
		return OemDAOUtil.pageQuery(sql.toString(), queryParam);
	}

}
