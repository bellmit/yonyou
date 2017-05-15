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
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.StringUtils;
/**
 * OEM用户维护
 * @author 夏威
 * @date 2017-2-6
 */

@Repository
public class SysUserDao extends OemBaseDAO {
	
	/**
	 * 用户查询
	 * @param queryParam
	 * @return
	 */
	public PageInfoDto queryList(Map<String, String> queryParam) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<>();
		sql.append(" SELECT TU.USER_ID,ACNT,EMP_NUM,NAME,USER_STATUS,USER_TYPE,tp.POSE_NAME ");
		sql.append(" FROM TC_USER TU ");
		sql.append(" LEFT JOIN  (SELECT MIN(POSE_ID) POSE_ID,USER_ID FROM TR_USER_POSE GROUP BY USER_ID ) TUP ON TU.USER_ID = TUP.USER_ID ");
		sql.append(" LEFT JOIN  TC_POSE TP ON TUP.POSE_ID = TP.POSE_ID ");
		sql.append(" WHERE TU.COMPANY_ID = "+loginInfo.getCompanyId()+"");
		if(!StringUtils.isNullOrEmpty(queryParam.get("orgId"))){
			sql.append(" and TP.ORG_ID = ?");
			params.add(queryParam.get("orgId"));
		}
		if(!StringUtils.isNullOrEmpty(queryParam.get("acnt"))){
			sql.append(" AND upper(TU.ACNT) like ?");
			params.add("%"+queryParam.get("acnt").toUpperCase()+"%");
		}
		if(!StringUtils.isNullOrEmpty(queryParam.get("name"))){
			sql.append(" AND upper(TU.NAME) like ?");
			params.add("%"+queryParam.get("name")+"%");
		}
		System.out.println(sql.toString() + params.toString());
		return OemDAOUtil.pageQuery(sql.toString(), params);
	}
	
	/**
	 * 用户职位查询
	 * @param queryParam
	 * @return
	 */
	public PageInfoDto getPoseList(Map<String, String> queryParam) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql=new StringBuffer();
		sql.append(" SELECT POSE_ID,POSE_CODE,POSE_NAME FROM TC_POSE WHERE POSE_TYPE = ? ");
		params.add( OemDictCodeConstants.SYS_USER_OEM);
		sql.append("  AND POSE_STATUS = ? ");
		params.add( OemDictCodeConstants.STATUS_ENABLE);
		sql.append("  AND COMPANY_ID = ? ");
		params.add( loginInfo.getCompanyId() );
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
	
	/**
	 * 用户账号校验
	 * @param queryParam
	 * @return
	 */
	public Boolean checkUser(String acnt) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append(" SELECT COUNT(1) NUM FROM tc_user WHERE acnt =  ? ");
		params.add(acnt);
		List<Map> list = OemDAOUtil.findAll(sql.toString(), params);
		if(null!=list &&list.size()>0){
			if(Integer.parseInt(list.get(0).get("NUM").toString())>=1){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}

	/**
	 * 获取用户职位信息
	 * @param id
	 * @return
	 */
	public String getUserPoses(Long id) {
		String ids = "";
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql=new StringBuffer();
		sql.append(" SELECT POSE_ID FROM TR_USER_POSE WHERE USER_ID = ? ");
		params.add(id);
		List<Map> list = OemDAOUtil.findAll(sql.toString(), params);
		if(null!=list && list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				if(ids.equals("")){
					ids = CommonUtils.checkNull(list.get(i).get("POSE_ID"));
				}else{
					ids = ids+","+ CommonUtils.checkNull(list.get(i).get("POSE_ID"));
				}
			}
		}
		return ids;
	}
	
	/**
	 * 获取用户职位集合信息
	 * @param id
	 * @return
	 */
	public List<Map> getUserPoseList(Long id) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql=new StringBuffer();
		sql.append(" SELECT tp.POSE_ID,POSE_CODE,POSE_NAME FROM TC_POSE  TP , TR_USER_POSE TUP WHERE TP.POSE_ID = TUP.POSE_ID AND USER_ID = ? ");
		params.add(id);
		return OemDAOUtil.findAll(sql.toString(), params);
	}

	public String getPoseNameByUserId(String userId) {
		String poseName = "";
		String sql = " SELECT TP.POSE_NAME FROM TC_POSE TP,TR_USER_POSE TUP WHERE TUP.POSE_ID = TP.POSE_ID AND TUP.USER_ID ='" + userId + "'";
		List<Map> list = OemDAOUtil.findAll(sql.toString(), null);
		if(list != null && !list.isEmpty()){
			for(int i = 0; i < list.size(); i++){
				poseName += CommonUtils.checkNull(list.get(i).get("POSE_NAME")) + ",";
			}
			if(poseName.endsWith(",")){
				poseName = poseName.substring(0, poseName.length() - 1);
			}
		}
		return poseName;
	}

}
