package com.yonyou.dms.web.dao.authority;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.manage.domains.DTO.basedata.CommonTreeDto;
import com.yonyou.dms.manage.domains.DTO.basedata.CommonTreeStateDto;

/**
 * 角色权限维护
 * @author 夏威
 *
 */
@Repository
public class RoleDao extends OemBaseDAO {
	
	/**
	 * 角色权限维护
	 * @param queryParam
	 * @return
	 */
	public PageInfoDto queryList(Map<String, String> queryParam) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<>();
		sql.append(" SELECT ROLE_ID,ROLE_DESC,ROLE_NAME,ROLE_TYPE,ROLE_STATUS from tc_role where 1=1  AND OEM_COMPANY_ID= ? ");
		params.add(loginInfo.getCompanyId());
		if(!StringUtils.isNullOrEmpty(queryParam.get("roleDesc"))){
			sql.append(" and ROLE_DESC like ? ");
			params.add("%"+ queryParam.get("roleDesc")+"%");
		}
		if(!StringUtils.isNullOrEmpty(queryParam.get("roleName"))){
			sql.append(" AND upper(ROLE_NAME) like ?");
			params.add("%"+queryParam.get("roleName").toUpperCase()+"%");
		}
		if(!StringUtils.isNullOrEmpty(queryParam.get("roleType"))){
			sql.append(" AND ROLE_TYPE = ? ");
			params.add(queryParam.get("roleType"));
		}
		return OemDAOUtil.pageQuery(sql.toString(), params);
	}	
	
	/**
	 * 获取菜单树
	 * @param id
	 * @param roleType 
	 * @return
	 */
	public Map<String, Object> getMenuData(String id, String roleType) {
		Map<String, Object> map = new HashMap<>();
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<>();
		if(CommonUtils.checkNull(id).equals("-1")){
			sql.append(" SELECT tf.FUNC_ID MENU_ID,FUNC_NAME MENU_NAME,FUNC_CODE MENU_URL,MENU_ICON,PAR_FUNC_ID PARENT_ID,FUNC_LEVEL MENU_TYPE,FUNC_NAME MENU_DESC FROM tc_func tf ");
			sql.append(" WHERE FUNC_TYPE = '"+roleType+"' OR FUNC_TYPE = '1002' ORDER  BY tf.FUNC_ID ");
		}else{
			sql.append(" SELECT DISTINCT tf.FUNC_ID MENU_ID,FUNC_NAME MENU_NAME,FUNC_CODE MENU_URL,MENU_ICON,PAR_FUNC_ID PARENT_ID,FUNC_LEVEL MENU_TYPE,FUNC_NAME MENU_DESC,trf.ROLE_FUNC_ID FROM tc_func tf ");
			sql.append(" LEFT JOIN tr_role_func trf on tf.FUNC_ID = trf.FUNC_ID and trf.role_id = '"+id+"' ");
			sql.append(" WHERE  (FUNC_TYPE = '"+roleType+"' OR FUNC_TYPE = '1002') ORDER  BY tf.FUNC_ID ");
		}
		System.out.println(sql);
		List<Map> list =  OemDAOUtil.findAll(sql.toString(), params);
		List<CommonTreeDto> orgList = new ArrayList<>();
		for(int i=0;i<list.size();i++){
			CommonTreeDto orgTreeOrg = new CommonTreeDto();
			CommonTreeStateDto CommonTreeStateDto = new CommonTreeStateDto();
			orgTreeOrg.setId((String)list.get(i).get("MENU_ID").toString());
			String parent = (String)list.get(i).get("PARENT_ID").toString();
			if("0".equals(parent)){
				parent = "#";
			}
			if(!CommonUtils.checkNull(id).equals("-1")){
				if(!"".equals(CommonUtils.checkNull(list.get(i).get("ROLE_FUNC_ID"))) && "1003".equals(CommonUtils.checkNull(list.get(i).get("MENU_TYPE")))){
					CommonTreeStateDto.setChecked(true);
				}else{
					CommonTreeStateDto.setChecked(false);
				}
			}
			if("1".equals(CommonUtils.checkNull(list.get(i).get("MENU_ID")))){
				CommonTreeStateDto.setChecked(true);
				CommonTreeStateDto.setDisabled(true);
			}
			orgTreeOrg.setParent(parent);
			orgTreeOrg.setState(CommonTreeStateDto);
			orgTreeOrg.setText((String)list.get(i).get("MENU_NAME").toString());
			orgList.add(orgTreeOrg);
		}
		map.put("treejson", orgList);
		return map;
	}
	
	/**
	 * 校验角色代码是否存在
	 * @param roleCode
	 * @return
	 */
	public Boolean checkRole(String roleCode) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append(" SELECT COUNT(1) NUM FROM TC_ROLE WHERE ROLE_NAME = ? ");
		params.add(roleCode);
		List<Map> list = OemDAOUtil.findAll(sql.toString(), params);
		if(null!=list &&list.size()>0){
			if(list.get(0).get("NUM").toString().equals("1")){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}

}
