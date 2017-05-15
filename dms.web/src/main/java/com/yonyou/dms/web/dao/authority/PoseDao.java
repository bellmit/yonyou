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
 * 用户职位权限维护
 * @author 夏威
 */

@Repository
public class PoseDao extends OemBaseDAO {
	
	/**
	 * 用户职位查询
	 * @param queryParam
	 * @return
	 */
	public PageInfoDto queryList(Map<String, String> queryParam) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<>();
		String poseType = queryParam.get("poseType");
		sql.append(" SELECT POSE_ID,POSE_CODE,POSE_NAME,POSE_TYPE,POSE_STATUS from tc_pose where 1=1 ");
		
		if(!StringUtils.isNullOrEmpty(queryParam.get("poseCode"))){
			sql.append(" and upper(POSE_CODE) like ? ");
			params.add("%"+ queryParam.get("poseCode").toUpperCase()+"%");
		}
		if(!StringUtils.isNullOrEmpty(queryParam.get("poseName"))){
			sql.append(" AND upper(POSE_NAME) like ?");
			params.add("%"+queryParam.get("poseName").toUpperCase()+"%");
		}
		if(!StringUtils.isNullOrEmpty(queryParam.get("poseStatus"))){
			sql.append(" AND POSE_STATUS = ? ");
			params.add(queryParam.get("poseStatus"));
		}
		if(!StringUtils.isNullOrEmpty(poseType) && String.valueOf(OemDictCodeConstants.SYS_USER_DEALER).equals(poseType)){
			//POSE_TYPE 为所查询 posetype
			sql.append("  and POSE_TYPE = '"+queryParam.get("poseType")+"'");
			if(!StringUtils.isNullOrEmpty(queryParam.get("dealerId")))
        	{
				sql.append("  and ORG_ID = (SELECT DEALER_ORG_ID FROM TM_DEALER WHERE DEALER_ID = '"+queryParam.get("dealerId")+"')");	            	
        	}
			if(!StringUtils.isNullOrEmpty(queryParam.get("companyId"))){				
				sql.append("  and COMPANY_ID = '"+queryParam.get("companyId")+"'");
			}
	        	
		}else{
			//POSE_TYPE 为所查询 posetype
			sql.append("  and POSE_TYPE = '"+queryParam.get("poseType")+"'");
			sql.append("  and COMPANY_ID = '" + loginInfo.getCompanyId() +"'");
		}
		if(!StringUtils.isNullOrEmpty(queryParam.get("orgId"))) 
		{
			sql.append("  and ORG_ID = '" + queryParam.get("orgId") +"'");
		}
		return OemDAOUtil.pageQuery(sql.toString(), params);
	}
	
	/**
	 * 获取职位业务范围
	 * @param id
	 * @return
	 */
	public List<Map> getSeriesList(String id) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<>();
		if("0".equals(id)){
			sql.append(" SELECT t1.GROUP_ID,t2.GROUP_NAME BRAND_NAME,t1.GROUP_NAME SERIES_NAME,t1.GROUP_TYPE FROM tm_vhcl_material_group t1 ,tm_vhcl_material_group t2 "); 
			sql.append(" WHERE T1.PARENT_GROUP_ID = T2.GROUP_ID  AND T1.GROUP_LEVEL = '2' AND T1.STATUS = '10011001' ");
		}else{
			sql.append(" SELECT t1.GROUP_ID,t2.GROUP_NAME BRAND_NAME,t1.GROUP_NAME SERIES_NAME,t1.GROUP_TYPE,CASE WHEN IFNULL(tpb.BUSS_ID,0)=0 THEN 10041002 ELSE 10041001 END IS_CHECK ");
			sql.append(" FROM tm_vhcl_material_group t1   ");
			sql.append(" INNER JOIN tm_vhcl_material_group t2 ON T1.PARENT_GROUP_ID = T2.GROUP_ID  ");
			sql.append(" LEFT JOIN tc_pose_buss tpb ON tpb.GROUP_ID = t1.GROUP_ID AND tpb.POSE_ID = "+id);
			sql.append(" WHERE  T1.GROUP_LEVEL = '2' AND T1.STATUS = '10011001'  ");
		}
		return OemDAOUtil.findAll(sql.toString(), params);
	}
	
	/**
	 * 获取角色信息
	 * @param queryParam
	 * @return
	 */
	public PageInfoDto selectRole(Map<String, String> queryParam) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<>();
		sql.append(" SELECT ROLE_ID,ROLE_NAME,ROLE_DESC FROM TC_ROLE WHERE ROLE_STATUS = "+OemDictCodeConstants.STATUS_ENABLE); 
		sql.append(" AND ROLE_TYPE= "+queryParam.get("roleType"));
		if(!StringUtils.isNullOrEmpty(queryParam.get("roleName"))){
			sql.append(" AND ROLE_NAME like ? ");
			params.add("'%"+queryParam.get("roleName").toUpperCase()+"%'");
		}
		if(!StringUtils.isNullOrEmpty(queryParam.get("roleDesc"))){
			sql.append(" AND ROLE_DESC like ? ");
			params.add("'%"+queryParam.get("roleDesc")+"%'");
		}
		if(!StringUtils.isNullOrEmpty(queryParam.get("roleIds"))){
			sql.append(" and ROLE_ID NOT IN ("+queryParam.get("roleIds")+") ");
		}
		System.out.println(sql.toString() + params.toString());
		return OemDAOUtil.pageQuery(sql.toString(), params);
	}
	
	/**
	 * 获取职位角色所有菜单
	 * @param ids
	 * @return
	 */
	public List<Map> getFuncList() {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<>();
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		String ids = loginInfo.getRoleIds();
		List<Map> list = new ArrayList<Map>();
		sql.append(" SELECT DISTINCT CASE WHEN TF.FUNC_ID = 1 THEN 0 ELSE TF.FUNC_ID END FUNC_ID,TF.FUNC_NAME,CASE WHEN TF.FUNC_ID = 1 THEN '' ELSE TF.PAR_FUNC_ID END PAR_FUNC_ID  "); 
		sql.append(" FROM TR_ROLE_FUNC TRF,TC_FUNC TF  "); 
		sql.append(" WHERE TRF.FUNC_ID = TF.FUNC_ID  AND (tf.func_level=1003 OR tf.func_level=1001) AND trf.ROLE_ID IN("+ids+") "); 
		sql.append(" UNION "); 
		sql.append(" SELECT DISTINCT TF2.FUNC_ID,TF2.FUNC_NAME,TF2.PAR_FUNC_ID "); 
		sql.append(" FROM TR_ROLE_FUNC TRF,TC_FUNC TF ,TC_FUNC TF2 "); 
		sql.append(" WHERE TRF.FUNC_ID = TF.FUNC_ID AND TF.PAR_FUNC_ID = TF2.FUNC_ID AND tf.func_level=1003  AND trf.ROLE_ID IN("+ids+") "); 
		sql.append(" UNION "); 
		sql.append(" SELECT DISTINCT TF3.FUNC_ID,TF3.FUNC_NAME,TF3.PAR_FUNC_ID "); 
		sql.append(" FROM TR_ROLE_FUNC TRF,TC_FUNC TF ,TC_FUNC TF2,TC_FUNC TF3 "); 
		sql.append(" WHERE TRF.FUNC_ID = TF.FUNC_ID AND TF.PAR_FUNC_ID = TF2.FUNC_ID AND TF2.PAR_FUNC_ID = TF3.FUNC_ID  AND tf.func_level=1003  AND trf.ROLE_ID IN("+ids+") "); 
		if(!"".equals(CommonUtils.checkNull(ids))){
			sql.append(" AND trf.ROLE_ID IN("+ids+")  ");
			System.out.println(sql.toString());
			list = OemDAOUtil.findAll(sql.toString(), params);
		}
		return list;
	}
	
	/**
	 * 校验职位是否存在
	 * @param poseCode
	 * @return
	 */
	public Boolean checkPose(String poseCode) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append(" SELECT COUNT(1) NUM FROM TC_POSE WHERE POSE_CODE = ? ");
		params.add(poseCode);
		List<Map> list = OemDAOUtil.findAll(sql.toString(), params);
		if(null!=list &&list.size()>0){
			if(!list.get(0).get("NUM").toString().equals("0")){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	
	/**
	 * 初始化职位角色信息
	 * @param id
	 * @return
	 */
	public List<Map> getPoseRoles(Long id) {
		String sql = " SELECT tr.ROLE_ID,tr.ROLE_DESC,tr.ROLE_NAME FROM tc_role tr, tr_role_pose trp "+
				" WHERE tr.ROLE_ID = trp.ROLE_ID AND trp.POSE_ID =  "+id;
		return OemDAOUtil.findAll(sql, null);
	}

}
