package com.yonyou.dms.web.dao.authority;

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
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * OEM 经销商用户维护
 * @author 夏威
 *
 */
@Repository
public class DealerUserDao extends OemBaseDAO{
	
	/**
	 * 经销商用户查询
	 * @param queryParam
	 * @return
	 */
	public PageInfoDto queryList(Map<String, String> queryParam) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<>();
		sql.append(" SELECT DISTINCT TU.USER_ID,COM.COMPANY_SHORTNAME,TU.ACNT,TU.NAME,TU.USER_STATUS,TP.POSE_BUS_TYPE,TP.POSE_NAME  ");
		sql.append(" FROM TC_USER TU  ");
		sql.append(" LEFT OUTER JOIN (SELECT MIN(POSE_ID) POSE_ID,USER_ID FROM TR_USER_POSE GROUP BY USER_ID ) UP ON TU.USER_ID=UP.USER_ID   ");
		sql.append(" LEFT OUTER JOIN TC_POSE TP ON UP.POSE_ID=TP.POSE_ID,  ");
		sql.append(" TM_COMPANY COM WHERE COM.COMPANY_ID = TU.COMPANY_ID AND TU.USER_TYPE= "+OemDictCodeConstants.SYS_USER_DEALER);
		if (!StringUtils.isNullOrEmpty(queryParam.get("companyId"))) {
			sql.append("  AND COM.COMPANY_ID = ? ");
			params.add(queryParam.get("companyId"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("oemCompanyId"))) {
			sql.append("  AND COM.OEM_COMPANY_ID = ? ");
			params.add(loginInfo.getCompanyId());
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("deptId"))) {
			sql.append("  AND TP.ORG_ID = ? ");
			params.add(queryParam.get("deptId"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("acnt"))) {
			sql.append("  AND upper(TU.ACNT) like ? ");
			params.add("%"+queryParam.get("acnt")+"%");
		}
		if(!StringUtils.isNullOrEmpty(queryParam.get("name"))){
			sql.append(" AND upper(TU.NAME) like ?");
			params.add("%"+queryParam.get("name")+"%");
		}
		return OemDAOUtil.pageQuery(sql.toString(), params);
	}
	
	/**
	 * 获取经销商用户职位
	 * @param queryParam
	 * @return
	 */
	public PageInfoDto getPoseList(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql=new StringBuffer();
		sql.append(" SELECT POSE_ID,POSE_CODE,POSE_NAME FROM TC_POSE WHERE POSE_TYPE = ? ");
		params.add( OemDictCodeConstants.SYS_USER_DEALER);
		sql.append("  AND POSE_STATUS = ? ");
		params.add( OemDictCodeConstants.STATUS_ENABLE);
		sql.append("  AND COMPANY_ID = ? ");
		params.add( queryParam.get("companyId") );
		if(!StringUtils.isNullOrEmpty(queryParam.get("poseCode"))){
			sql.append(" and POSE_CODE like ?");
			params.add("%"+queryParam.get("poseCode")+"%");
		}
		if(!StringUtils.isNullOrEmpty(queryParam.get("poseName"))){
			sql.append(" and POSE_NAME like ?");
			params.add("%"+queryParam.get("poseName")+"%");
		}
		if(!StringUtils.isNullOrEmpty(queryParam.get("poseIds"))){
			sql.append(" and POSE_ID NOT IN ("+queryParam.get("poseIds")+") ");
		}
		return OemDAOUtil.pageQuery(sql.toString(), params);
	}

}
