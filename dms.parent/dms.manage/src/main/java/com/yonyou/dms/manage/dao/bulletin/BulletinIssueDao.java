package com.yonyou.dms.manage.dao.bulletin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;

@Repository
public class BulletinIssueDao extends OemBaseDAO {

	public PageInfoDto searchByTypeId(long typeId) {
		List<Object> queryParam = new ArrayList<Object>();
		String sql = "select * from tm_bulletin_type where TYPE_ID = ?";
		queryParam.add(typeId);
		return OemDAOUtil.pageQuery(sql, queryParam);
		
	}

	public PageInfoDto searchByUserId(Long userId) {
		List<Object> queryParam = new ArrayList<Object>();
		StringBuffer query = new StringBuffer();
		query.append("select tbt.TYPE_ID, tbt.TYPENAME, tbt.STATUS \n");
		query.append(" from tm_bulletin_type tbt, \n");
		query.append("       TM_BLTNTYPE_ROLE TBR \n");
		query.append(" where tbt.type_id = tbr.type_id \n");
		query.append("       and tbt.STATUS = 1 \n");
		query.append("       and tbr.employee_id = ? \n");
		query.append(" order by tbt.update_date, tbt.create_date desc \n");
		queryParam.add(userId);
		return OemDAOUtil.pageQuery(query.toString(), queryParam);
	}

	public List<String> getDealerID() {
		List<Object> queryParam = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append(" select dealer_id from TM_dealer where STATUS = 10011001");
		List<Map> list = OemDAOUtil.findAll(sql.toString(), queryParam);
		List<String> dealerIds = new ArrayList<String>();
		if(null!=list && list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				dealerIds.add(String.valueOf(list.get(i).get("dealer_id")));
			}
		}
		return dealerIds;
	}

}
