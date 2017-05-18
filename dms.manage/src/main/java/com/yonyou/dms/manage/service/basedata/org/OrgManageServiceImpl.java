package com.yonyou.dms.manage.service.basedata.org;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;

@Service
public class OrgManageServiceImpl implements OrgManageService {

	/**
	 * 获取大区信息
	 */
	@Override
	public List<Map> getBigOrg(Map<String, String> queryParams) throws ServiceBizException {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT  ORG1.ORG_ID BIG_ORG,ORG1.ORG_NAME BIG_ORG_NAME FROM TM_ORG ORG1 ,TM_ORG ORG2  ");
		sql.append(" WHERE  ORG1.ORG_ID = ORG2.PARENT_ORG_ID  ");
		sql.append(" AND ORG1.DUTY_TYPE = 10431003 AND ORG1.ORG_LEVEL = 2 ");
		if(loginInfo.getPoseBusType().equals(OemDictCodeConstants.POSE_BUS_TYPE_DWR)){
			sql.append(" AND ORG1.BUSS_TYPE = '12351002'  ");
		}else{
			sql.append(" AND ORG1.BUSS_TYPE = '12351001'  ");
		}
		// 判断是否是大区经理登录
		if (loginInfo.getDutyType().equals(OemDictCodeConstants.DUTY_TYPE_LARGEREGION.toString())) {
			sql.append("   AND ORG1.ORG_ID = '" + loginInfo.getOrgId() + "' \n");	
		}
		return OemDAOUtil.findAll(sql.toString(), null);
	}
	
	/**
	 * 获取小区信息
	 */
	@Override
	public List<Map> getSmallOrg(String bigorgid,Map<String, String> queryParams) throws ServiceBizException {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT  ORG2.ORG_ID SMALL_ORG,ORG2.ORG_NAME SMALL_ORG_NAME FROM TM_ORG ORG1 ,TM_ORG ORG2  ");
		sql.append(" WHERE  ORG1.ORG_ID = ORG2.PARENT_ORG_ID  ");
		sql.append(" AND ORG2.DUTY_TYPE = 10431004 AND ORG2.ORG_LEVEL = 3 ");
		if(loginInfo.getPoseBusType().equals(OemDictCodeConstants.POSE_BUS_TYPE_DWR)){
			sql.append(" AND ORG2.BUSS_TYPE = '12351002'  ");
		}else{
			sql.append(" AND ORG2.BUSS_TYPE = '12351001'  ");
		}
		// 判断是否是大区经理登录
		if (loginInfo.getDutyType().equals(OemDictCodeConstants.DUTY_TYPE_LARGEREGION.toString())) {
			sql.append("   AND ORG2.ORG_ID = '" + loginInfo.getOrgId() + "' \n");	
		}
		if(!StringUtils.isNullOrEmpty(bigorgid)){
			sql.append("   AND ORG1.ORG_ID in ( " + bigorgid + ") \n");	
		}
		System.out.println(sql.toString());
		return OemDAOUtil.findAll(sql.toString(), null);
	}
	
	
	
	
}
