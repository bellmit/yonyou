package com.yonyou.dms.manage.dao.bulletin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;

@Repository
public class DealerBulletinQueryDao extends OemBaseDAO {

	public PageInfoDto search(Map<String, String> param) {
		LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);	
		List<Object> queryParam = new ArrayList<Object>();
		Long dealerId = loginUser.getDealerId();
		StringBuffer query = new StringBuffer();
		query.append("select c.*,IFNULL(d.notReadnum,0) NOTREADNUM from ( \n");
		query.append("select tbt.TYPE_ID, tbt.TYPENAME, tbt.REMARK, b.NUM, DATE_FORMAT(b.create_date,'%Y-%m-%d %H:%i:%s') CREATE_DATE \n");
		query.append("from tm_bulletin_type tbt, \n");
		query.append("(select a.type_id, count(a.type_id) NUM, max(CREATE_DATE) CREATE_DATE from tt_vs_bulletin a \n");
		query.append(" WHERE a.status = '1' and (a.bulletin_id in(select m.bulletin_id from Tt_Vs_Bulletin_Org_Rel m where m.org_id = ?  \n");
		query.append(" AND ((STATUS='0' AND END_TIME_DATE >= NOW() ) OR STATUS='1' OR STATUS is null)");
		query.append("  ) ) group by a.type_id) b \n");  
		query.append(" where tbt.status = '1' and b.type_id = tbt.type_id ) c  left join ");
		query.append("  (select a.type_id, count(a.type_id) notReadnum from tt_vs_bulletin a \n");
		query.append(" WHERE a.status = '1' and a.bulletin_id in(select m.bulletin_id from Tt_Vs_Bulletin_Org_Rel m where m.org_id = ?  \n");
		query.append(" and m.is_read = '0') \n");
		query.append(" group by a.type_id) d on  d.type_id = c.type_id");
		queryParam.add(dealerId);
		queryParam.add(dealerId);
		System.out.println("SQL \n ===================\n"+query+"\n ==================");
		System.out.println("Param:   "+ queryParam);
		return OemDAOUtil.pageQuery(query.toString(), queryParam);
	}

	private String getDealerPose(Long dealerId) {
//		LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);	
		String pose = "";
		List<Object> queryParam = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("select tp.DEALER_FIX_POSE from TM_DEALER tb,tc_pose tp \n");
		sql.append("where tb.COMPANY_ID = tp.COMPANY_ID and tb.DEALER_ID = ? \n");
		queryParam.add(dealerId);
		System.out.println("SQL \n ===================\n"+sql+"\n ==================");
		System.out.println("Param:   "+ queryParam);
		List<Map> list = OemDAOUtil.findAll(sql.toString(), queryParam);
		System.out.println("Param:   "+ queryParam);
		if(list!= null && !list.isEmpty()){
			pose = String.valueOf(list.get(0).get("DEALER_FIX_POSE"));
		}
		return pose;
	}

	public PageInfoDto searchDetail(Map<String, String> param) {
		List<Object> queryParam = new ArrayList<Object>();
		LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		Long dealerId = loginUser.getDealerId();
		String pose = getDealerPose(dealerId);
		String createDate = getDealerCreateDate(dealerId);
		String topic = param.get("topic");
		String beginDate = param.get("beginDate");
		String endDate = param.get("endDate");
		String level = param.get("level");
		String typeId = param.get("typeId");
				
		StringBuffer query = new StringBuffer();
		query.append("select * from ( \n");
		query.append("SELECT TB.BULLETIN_ID, \n");
		query.append("       TB.TYPE_ID, \n");
		query.append("       TB.TOPIC, \n");
		query.append("       DATE_FORMAT(TB.CREATE_DATE, '%Y-%m-%d %H:%i:%s') CREATE_DATE, \n");
		query.append("       TBT.TYPENAME, \n");
		query.append("       DATE_FORMAT(TB.PERIOD_DATE, '%Y-%m-%d') PERIOD_DATE, \n");
		query.append("       CASE TOPR.IS_READ WHEN 1 THEN '已读' WHEN 0 THEN '未读' ELSE '未读' END AS IS_READ, \n");
		query.append("		(CASE WHEN TB.LEVEL = '2' THEN '高'  \n");//紧急程度
		query.append("		WHEN TB.LEVEL = '1' THEN '中'  \n");
		query.append("		WHEN TB.LEVEL = '0' THEN '低'  \n");
		query.append("		END) LEVEL,  \n");
		query.append("		TB.ISSHOW,  \n");//紧急程度
		query.append("		DATE_FORMAT(TB.START_TIME_DATE,'%Y-%m-%d') START_TIME_DATE,  \n");//开始时间
		query.append("		DATE_FORMAT(TB.END_TIME_DATE,'%Y-%m-%d') END_TIME_DATE \n");//结束时间
		query.append("  FROM TT_VS_BULLETIN TB, Tt_Vs_Bulletin_Org_Rel TOPR, TM_BULLETIN_TYPE TBT \n");
		query.append(" WHERE TB.STATUS = '1' \n");
		query.append("   and TOPR.bulletin_id = tb.bulletin_id \n");
		query.append("   AND TB.TYPE_ID = TBT.TYPE_ID \n");
		query.append("       AND TB.TYPE_ID = '" + typeId + "' \n");
		query.append("       AND TOPR.org_id = '" + dealerId + "'  \n");
		//是否超过当前时间     继续显示时间 （0）
		query.append("   AND ((TB.STATUS='0' AND TB.END_TIME_DATE >= NOW() ) \n");
		query.append("   OR TB.STATUS='1' OR TB.STATUS is null) \n");
		if(StringUtils.isNotBlank(topic)) { // 拼查询职位的SQL
        	query.append(" AND upper(TB.TOPIC) LIKE ? \n");
        	queryParam.add("%" + topic.toUpperCase() +"%");
		}
	    if(StringUtils.isNotBlank(beginDate)) { 
	        query.append(" AND DATE_FORMAT(TB.START_TIME_DATE,'%Y-%m-%d') >= ? \n");//开始时间
	        queryParam.add(beginDate);
	    }
		if(StringUtils.isNotBlank(endDate)) {
	        query.append(" AND DATE_FORMAT(TB.END_TIME_DATE,'%Y-%m-%d') <= ? \n");//结束时间
	        queryParam.add(endDate);
	    }
		if (StringUtils.isNotBlank(level)) {
			query.append(" AND TB.LEVEL= ? ");//紧张程度
			queryParam.add(level);
		}
		query.append("union ");
		query.append(" select TB.BULLETIN_ID, \n");
		query.append("       TB.TYPE_ID, \n");
		query.append("       TB.TOPIC,  \n");
		query.append("       DATE_FORMAT(TB.CREATE_DATE, '%Y-%m-%d %H:%i:%s') CREATE_DATE, \n");
		query.append("       TBT.TYPENAME, \n");
		query.append("       DATE_FORMAT(TB.PERIOD_DATE, '%Y-%m-%d') PERIOD_DATE, 0, \n");
		query.append("		(CASE WHEN TB.LEVEL = '2' THEN '高'  \n");//紧急程度
		query.append("		WHEN TB.LEVEL = '1' THEN '中'  \n");
		query.append("		WHEN TB.LEVEL = '0' THEN '低'  \n");
		query.append("		END) LEVEL,  \n");
		query.append("		TB.ISSHOW,  \n");////紧急程度
		query.append("		DATE_FORMAT(TB.START_TIME_DATE,'%Y-%m-%d') START_TIME_DATE,  \n");//开始时间
		query.append("		DATE_FORMAT(TB.END_TIME_DATE,'%Y-%m-%d') END_TIME_DATE \n");//结束时间
		query.append(" from TT_VS_BULLETIN TB, TM_BULLETIN_TYPE TBT \n");
		query.append(" where 0=0  AND TB.TYPE_ID = TBT.TYPE_ID \n");
		query.append(" AND TB.TYPE_ID = '"+typeId+"' \n");
		query.append("  and tb.STATUS = 1 and date(TB.CREATE_DATE)<='"+createDate+"' \n");
		query.append("  and tb.activity_scope ='0' and tb.position_type like '%"+pose+"%' \n ");
		query.append("  and not exists (select 1 from TM_BULLETIN_TYPE where bulletin_id = TB.BULLETIN_ID) \n ) b");
		System.out.println("SQL \n ===================\n"+query+"\n ==================");
		System.out.println("Param:   "+ queryParam);
		return OemDAOUtil.pageQuery(query.toString(), queryParam);
	}

	private String getDealerCreateDate(Long dealerId) {
		List<Object> queryParam = new ArrayList<Object>();
		String sql = "SELECT DATE_FORMAT(CREATE_DATE,'%Y-%m-%d') CREATE_DATE FROM TM_DEALER T WHERE T.DEALER_ID = ? ";
		queryParam.add(dealerId);
		String createDate = "";
		List<Map> list = OemDAOUtil.findAll(sql, queryParam);
		if(list != null && !list.isEmpty()){
			createDate = String.valueOf(list.get(0).get("CREATE_DATE"));
		}
		return createDate;
	}

	public Map<String, Object> viewBulletin(Long bulletinId) {
		List<Object> queryParam = new ArrayList<Object>();
		LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		Long poseId = loginUser.getPoseId();
		StringBuffer query = new StringBuffer("");
		query.append("SELECT TB.BULLETIN_ID,TT.TYPENAME,TB.TOPIC,TB.CONTENT,TU.NAME,TP.POSE_NAME, DATE_FORMAT(TB.CREATE_DATE,'%Y-%m-%d %H:%i:%s') CREATE_DATE, ");
		query.append("		CASE TB.LEVEL WHEN 2 THEN '高' WHEN 1 THEN '中' WHEN 0 THEN '低' ELSE '' END AS LEVEL,  \n");//紧急程度
		query.append("		CASE TB.ISSHOW WHEN 1 THEN '是' WHEN 0 THEN '否' ELSE '' END AS ISSHOW,  \n");//紧急程度
		query.append("		CASE TB.SIGN WHEN 1 THEN '是' WHEN 0 THEN '否' ELSE '' END AS SIGN,  \n");//是否签收
		query.append("		DATE_FORMAT(TB.START_TIME_DATE,'%Y-%m-%d') START_TIME_DATE,  \n");//开始时间
		query.append("		DATE_FORMAT(TB.END_TIME_DATE,'%Y-%m-%d') END_TIME_DATE \n");//结束时间
		query.append(" FROM TT_VS_BULLETIN TB, TC_POSE TP, TC_USER TU,TM_BULLETIN_TYPE TT \n");
		query.append(" WHERE TB.CREATE_BY = TU.USER_ID \n");
		query.append("      AND TB.TYPE_ID = TT.TYPE_ID \n");
		query.append("		AND TB.BULLETIN_ID='"+ bulletinId +"' \n");
		query.append("		AND TP.POSE_ID='"+ poseId +"' \n");
		System.out.println("SQL \n ===================\n"+query+"\n ==================");
		System.out.println("Param:   "+ queryParam);
		return OemDAOUtil.findFirst(query.toString(), queryParam);
		
	}

}
