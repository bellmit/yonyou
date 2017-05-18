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
public class BulletinQueryDao extends OemBaseDAO {

	public PageInfoDto search(Map<String, String> param) {
		LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);	
		List<Object> queryParam = new ArrayList<>();
		StringBuffer query = new StringBuffer();
		query.append("select tbt.TYPE_ID, tbt.TYPENAME, tbt.REMARK, c.num as NUM, DATE_FORMAT(c.UPDATE_DATE,'%Y-%m-%d %H:%i:%s') UPDATE_DATE \n");
		query.append(" from tm_bulletin_type tbt, \n");
		query.append("      (SELECT TYPE_ID, count(type_id) num, IFNULL(MAX(UPDATE_DATE),MAX(CREATE_DATE)) UPDATE_DATE FROM TT_VS_BULLETIN where STATUS = '1' and create_by = ?  \n");
		query.append("   AND ((ISSHOW='0' AND END_TIME_DATE >= NOW()) \n");
		query.append("   OR ISSHOW='1' OR ISSHOW is null) group by TYPE_ID) c\n");
		query.append(" where tbt.status = '1' \n");
		query.append("       and c.type_id = tbt.type_id \n");
		queryParam.add(loginUser.getUserId());
		System.out.println("SQL \n ===================\n"+query+"\n ==================");
		System.out.println("Param:   "+ queryParam);
		return OemDAOUtil.pageQuery(query.toString(), queryParam);
	}

	public PageInfoDto searchDetail(Map<String, String> param) {
		List<Object> queryParam = new ArrayList<>();
		LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		Long userId = loginUser.getUserId();
		String typeId = param.get("typeId");
		String dealerCode = param.get("dealerCode");
		String beginDate = param.get("beginDate");
		String endDate = param.get("endDate");
		String topic = param.get("topic");
		String level = param.get("level");
		StringBuffer query = new StringBuffer();
		if(dealerCode != null && dealerCode.length()>0){
			 query.append("  SELECT DISTINCT C.BULLETIN_ID,C.TYPE_ID,C.TOPIC,C.CREATE_DATE,C.TYPENAME,C.LEVEL,C.START_TIME_DATE,C.END_TIME_DATE,C.NUM,C.NUM1,C.CREATE_BY FROM ( \n");
		}
		query.append("  SELECT DISTINCT A.BULLETIN_ID,A.TYPE_ID,A.TOPIC,A.CREATE_DATE,A.TYPENAME,A.NUM,A.NUM1,A.CREATE_BY,A.LEVEL,A.START_TIME_DATE,A.END_TIME_DATE FROM ( \n");
		query.append("  SELECT TB.BULLETIN_ID, \n");
		query.append("         TB.TYPE_ID, \n");
		query.append("         TB.TOPIC, \n");
		query.append("         DATE_FORMAT(IFNULL(TB.update_DATE,TB.CREATE_DATE),'%Y-%m-%d %H:%i:%s') CREATE_DATE, \n");
		query.append("         TBT.TYPENAME, \n");
		query.append("         (SELECT count(a.bulletin_id) num FROM Tt_Vs_Bulletin_Org_Rel a JOIN tm_dealer td ON a.org_id = td.dealer_id WHERE A.IS_READ = 0 and a.BULLETIN_ID = tb.BULLETIN_ID) NUM, \n");
		query.append("         (SELECT count(a.bulletin_id) num FROM Tt_Vs_Bulletin_Org_Rel a JOIN tm_dealer td ON a.org_id = td.dealer_id WHERE A.IS_READ = 1 and a.BULLETIN_ID = tb.BULLETIN_ID) NUM1, \n");
		query.append("         (SELECT name  FROM tc_user WHERE user_id = tb.create_by) CREATE_BY, \n");
		query.append("		(CASE WHEN TB.LEVEL = '2' THEN '高'  \n");//紧急程度
		query.append("		WHEN TB.LEVEL = '1' THEN '中'  \n");
		query.append("		WHEN TB.LEVEL = '0' THEN '低'  \n");
		query.append("		END) LEVEL,  \n");
		query.append("		DATE_FORMAT(TB.START_TIME_DATE,'%Y-%m-%d') START_TIME_DATE,  \n");//开始时间
		query.append("		DATE_FORMAT(TB.END_TIME_DATE,'%Y-%m-%d') END_TIME_DATE \n");//结束时间
		query.append("    FROM TT_VS_BULLETIN TB, \n");
		query.append("         TM_BULLETIN_TYPE TBT \n");
		query.append("   WHERE     TB.STATUS = '1' and tbt.status = '1'\n");
		query.append("         AND TB.TYPE_ID = TBT.TYPE_ID \n");
		query.append("       AND TB.TYPE_ID = '" + typeId + "' \n");
		query.append("       AND TB.create_by = '" + userId + "' \n");
		//是否超过当前时间     继续显示时间 （0）
		query.append("   AND ((TB.ISSHOW='0' AND TB.END_TIME_DATE >= NOW() ) \n");
		query.append("   OR TB.ISSHOW='1' OR TB.ISSHOW is null) \n");
		if(StringUtils.isNotBlank(topic)) { // 
	        	query.append(" AND upper(TB.TOPIC) LIKE ? ");
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
		if(StringUtils.isNotBlank(level)) {
			 query.append(" AND TB.LEVEL= ? ");//紧张程度
		     queryParam.add(level);  
		}
		query.append(" ) A");
		if(StringUtils.isNotBlank(dealerCode)){
			 dealerCode = dealerCode.replace(",", "','");
			 query.append(" JOIN (SELECT TBOR.BULLETIN_ID FROM TT_VS_BULLETIN_ORG_REL TBOR,TM_DEALER TD,TT_VS_BULLETIN TVB WHERE  TD.DEALER_ID = TBOR.ORG_ID AND TBOR.BULLETIN_ID=TVB.BULLETIN_ID AND TVB.STATUS='1' AND TD.DEALER_CODE in ('"+dealerCode+"')) B  ON A.BULLETIN_ID = B.BULLETIN_ID ");
	         query.append(" ) C");
		 }
		System.out.println("SQL \n ===================\n"+query+"\n ==================");
		System.out.println("Param:   "+ queryParam);
		return OemDAOUtil.pageQuery(query.toString(), queryParam);
		 
	}

	public Map viewBulletin(Long bulletinId) {
		LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		Long poseId = loginUser.getPoseId();
		List<Object> queryParam = new ArrayList<>();
		StringBuffer query = new StringBuffer("");
		query.append("SELECT TB.BULLETIN_ID,TT.TYPENAME,TB.TOPIC,TB.CONTENT,TU.NAME,TP.POSE_NAME, DATE_FORMAT(TB.CREATE_DATE,'%Y-%m-%d %H:%i:%s') CREATE_DATE, DATE_FORMAT(TB.PERIOD_DATE,'%Y-%m-%d') PERIOD_DATE, \n");
		query.append("		CASE TB.LEVEL WHEN 2 THEN '高' WHEN 1 THEN '中' WHEN 0 THEN '低' ELSE '' END AS LEVEL,  \n");//紧急程度
		query.append("		CASE TB.ISSHOW WHEN 1 THEN '是' WHEN 0 THEN '否' ELSE '' END AS ISSHOW,  \n");//紧急程度
		query.append("		CASE TB.SIGN WHEN 1 THEN '是' WHEN 0 THEN '否' ELSE '' END AS SIGN,  \n");//紧急程度
		query.append("		DATE_FORMAT(TB.START_TIME_DATE,'%Y-%m-%d') START_TIME_DATE,  \n");//开始时间
		query.append("		DATE_FORMAT(TB.END_TIME_DATE,'%Y-%m-%d') END_TIME_DATE \n");//结束时间
		query.append(" FROM TT_VS_BULLETIN TB, TC_POSE TP, TC_USER TU,TM_BULLETIN_TYPE TT \n");
		query.append(" WHERE TB.CREATE_BY = TU.USER_ID \n");
		query.append("      AND TB.TYPE_ID = TT.TYPE_ID");
		query.append("		AND TB.BULLETIN_ID='"+ bulletinId +"' \n");
		query.append("		AND TP.POSE_ID='"+ poseId +"'\n");
		return OemDAOUtil.findFirst(query.toString(), queryParam);
	}

	public PageInfoDto getNotReadList(Map<String, String> param, Long bulletinId) {
		List<Object> queryParam = new ArrayList<>();
		StringBuffer query = new StringBuffer();
		query.append("select topr.bulletin_id, td.DEALER_ID, td.DEALER_NAME,td.DEALER_CODE  \n");
		query.append(" from Tt_Vs_Bulletin_Org_Rel topr,tm_dealer td \n");
		query.append(" where topr.is_read = '0' \n");
		query.append("      and td.dealer_id = topr.org_id  \n");
		query.append("      and topr.bulletin_id = ? \n");
		queryParam.add(bulletinId);
		return OemDAOUtil.pageQuery(query.toString(), queryParam);
	}

	public PageInfoDto getReadList(Map<String, String> param, Long bulletinId) {
		List<Object> queryParam = new ArrayList<>();
		StringBuffer query = new StringBuffer();
		query.append("select topr.bulletin_id, td.DEALER_ID, td.DEALER_NAME, DATE_FORMAT(topr.READ_DATE,'%Y-%m-%d %H:%i:%s') READ_DATE, \n");
		query.append("       tbr.R_CONTENT, DATE_FORMAT(tbr.CREATE_DATE,'%Y-%m-%d %H:%i:%s') CREATE_DATE, tbr.PATH_ID, tbr.FILENAME,td.DEALER_CODE  \n");
		query.append("from Tt_Vs_Bulletin_Org_Rel topr  \n");
		query.append("      left join tm_dealer td  \n");
		query.append("      on td.dealer_id = topr.org_id \n");
		query.append("      left join TT_VS_BULLETIN_REPLAY tbr  \n");
		query.append("      on tbr.dealer_id = topr.org_id \n");
		query.append(" where topr.is_read = '1' \n");
		query.append("      and topr.bulletin_id = ? \n");
		query.append("      and tbr.bulletin_id = ? \n");
		queryParam.add(bulletinId);
		queryParam.add(bulletinId);
		return OemDAOUtil.pageQuery(query.toString(), queryParam);
	}

	public PageInfoDto getSignList(Map<String, String> param, Long bulletinId) {
		List<Object> queryParam = new ArrayList<>();
		StringBuffer query = new StringBuffer();
		query.append("SELECT TOPR.BULLETIN_ID, TD.DEALER_ID, TD.DEALER_NAME, DATE_FORMAT(TOPR.SIGN_CREATE,'%Y-%m-%d %H:%i:%s') SIGN_CREATE,TD.DEALER_CODE  \n");
		query.append("		 FROM Tt_Vs_Bulletin_Org_Rel topr  \n");
		query.append("		 left join tm_dealer td  \n");
		query.append("		 on td.dealer_id = topr.org_id  \n");
		query.append("		 left join TT_VS_BULLETIN_REPLAY tbr    \n");
		query.append("		 on tbr.dealer_id = topr.org_id     \n");
		query.append("  WHERE TOPR.IS_READ = '1' AND TOPR.IS_SIGN = '1' \n");
		query.append("      	  AND TOPR.BULLETIN_ID = ? \n");
		query.append("      	  and tbr.bulletin_id  = ? \n");
		queryParam.add(bulletinId);
		queryParam.add(bulletinId);
		return OemDAOUtil.pageQuery(query.toString(), queryParam);
	}

	public PageInfoDto getNotSignList(Map<String, String> param, Long bulletinId) {
		List<Object> queryParam = new ArrayList<>();
		StringBuffer query = new StringBuffer();
		query.append("SELECT TD.DEALER_NAME,TD.DEALER_CODE,DATE_FORMAT(TOPR.SIGN_CREATE,'%Y-%m-%d %H:%i:%s') SIGN_CREATE  \n");
		query.append(" FROM TT_VS_BULLETIN_ORG_REL TOPR,TM_DEALER TD ,TT_VS_BULLETIN TB \n");
		query.append(" WHERE  \n");
		query.append("      (TOPR.IS_SIGN IS NULL OR TOPR.IS_SIGN <> '1')  \n");
		query.append("      AND TB.SIGN = '1' \n");
		query.append("      AND TD.DEALER_ID = TOPR.ORG_ID \n");
		query.append("      AND TOPR.BULLETIN_ID = TB.BULLETIN_ID \n");
		query.append("      	  AND TOPR.BULLETIN_ID = ? \n");
		queryParam.add(bulletinId);
		return OemDAOUtil.pageQuery(query.toString(), queryParam);
	}

}
