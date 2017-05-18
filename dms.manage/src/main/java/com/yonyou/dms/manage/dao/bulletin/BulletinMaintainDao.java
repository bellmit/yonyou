package com.yonyou.dms.manage.dao.bulletin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;

@Repository
public class BulletinMaintainDao extends OemBaseDAO {

	public PageInfoDto search(Map<String, String> param) {
		List<Object> queryParam = new ArrayList<Object>();
		String typeId = param.get("typeId");
		String topic = param.get("topic");
		String level = param.get("level");
		String beginDate = param.get("beginDate");
		String endDate = param.get("endDate");
		
		StringBuffer query = new StringBuffer();		
		query.append("SELECT TB.BULLETIN_ID, \n");
		query.append("       TB.TYPE_ID, \n");
		query.append("       TB.TOPIC, \n");
		query.append("       TB.CONTENT, \n");
		query.append("       TB.STATUS, \n");
		query.append("       DATE_FORMAT(IFNULL(TB.CREATE_DATE,TB.UPDATE_DATE) , '%Y-%m-%d %H:%i:%s') CREATE_DATE, \n");
		query.append("       DATE_FORMAT(TB.PERIOD_DATE, '%Y-%m-%d') PERIOD_DATE, \n");
		query.append("       TBT.TYPENAME, \n");
		query.append("       TU.NAME, \n");
		query.append("		 (CASE WHEN TB.LEVEL = '2' THEN '高'  \n");//紧急程度
		query.append("		 WHEN TB.LEVEL = '1' THEN '中'  \n");
		query.append("		 WHEN TB.LEVEL = '0' THEN '低'  \n");
		query.append("		 END) LEVEL,  \n");
		query.append("		 TB.ISSHOW,  \n");//是否显示
		query.append("		 TB.SIGN,  \n");//是否显示
		query.append("		 DATE_FORMAT(TB.START_TIME_DATE,'%Y-%m-%d') START_TIME_DATE,  \n");//开始时间
		query.append("		 DATE_FORMAT(TB.END_TIME_DATE,'%Y-%m-%d') END_TIME_DATE \n");//结束时间
		query.append(" FROM TT_VS_BULLETIN TB, TC_USER TU, TM_BULLETIN_TYPE TBT \n");
		query.append(" WHERE TB.STATUS = '1' \n");
		query.append("   AND TB.CREATE_BY = TU.USER_ID \n");
		query.append("   AND TB.TYPE_ID = TBT.TYPE_ID \n");
		//是否超过当前时间     继续显示时间 （0）
		query.append("   AND ((TB.ISSHOW='0' AND DATE_FORMAT(TB.END_TIME_DATE,'%Y-%m-%d') >= NOW() ) \n");
		query.append("   OR TB.ISSHOW='1' OR TB.ISSHOW is null) \n");

		if(StringUtils.isNotBlank(typeId)) { // 拼查询职位的SQL
	        query.append(" AND TB.TYPE_ID = ? ");
	        queryParam.add(typeId);
	    }
		if(StringUtils.isNotBlank(topic)) { // 拼查询职位的SQL
	        query.append(" AND UPPER(TB.TOPIC) LIKE ? ");
	        queryParam.add("%" + topic.toUpperCase() +"%");
	    }
		if(StringUtils.isNotBlank(beginDate)) { 
	        query.append(" AND DATE_FORMAT(TB.START_TIME_DATE,'%Y-%m-%d') >= ? ");//开始时间
	        queryParam.add(beginDate);
	    }
		if(StringUtils.isNotBlank(endDate)) {
	        query.append(" AND DATE_FORMAT(TB.END_TIME_DATE,'%Y-%m-%d') <= ? ");//结束时间
	        queryParam.add(endDate);
	    }
		if (StringUtils.isNotBlank(level)) {
			 query.append(" AND TB.LEVEL= ? ");//紧张程度
		        queryParam.add(level);
		}
		System.out.println("SQL \n ===================\n"+query+"\n ==================");
		System.out.println("Param:   "+ queryParam);
		return OemDAOUtil.pageQuery(query.toString(), queryParam);
	}

	public List<Map> getAllBulletinType() {
		List<Object> queryParam = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer(" select t.TYPE_ID , t.TYPENAME , t.REMARK , t.STATUS from tm_bulletin_type t where 1=1 order by t.type_id");
		List<Map> list = OemDAOUtil.findAll(sql.toString(), queryParam);
		return list;
	}

	public PageInfoDto getDealers(Map<String, String> param,Long bulletinId) {
		List<Object> queryParam = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("SELECT \n");
		sql.append("td.DEALER_ID,td.DEALER_CODE,td.DEALER_NAME,td.DEALER_SHORTNAME \n");
		sql.append("FROM tt_vs_bulletin tb JOIN tt_vs_bulletin_org_rel tbo ON tb.BULLETIN_ID = tbo.BULLETIN_ID JOIN tm_dealer td ON tbo.ORG_ID = td.DEALER_ID \n");
		sql.append("where tb.BULLETIN_ID = ? \n");
		queryParam.add(bulletinId);
		return OemDAOUtil.pageQuery(sql.toString(), queryParam);
	}

	public PageInfoDto getFiles(Map<String, String> param,Long bulletinId) {
		List<Object> queryParam = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("SELECT \n");
		sql.append("tba.ATTACHMENT_ID,tba.FILENAME  \n");
		sql.append("FROM tt_vs_bulletin tb JOIN tt_vs_bulletin_attachment tba ON tb.BULLETIN_ID = tba.BULLETIN_ID \n");
		sql.append("where tb.BULLETIN_ID = ? \n");
		queryParam.add(bulletinId);
		return OemDAOUtil.pageQuery(sql.toString(), queryParam);
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
