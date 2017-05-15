package com.yonyou.dms.web.service.basedata.authority;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.TcRolePO;
import com.yonyou.dms.common.domains.PO.basedata.TrRoleFuncPO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.web.dao.authority.RoleDao;
import com.yonyou.dms.web.domains.DTO.basedata.authority.OemRoleDTO;

@Service
public class OemRoleServiceImpl implements OemRoleService{
	
	@Autowired
	private RoleDao dao;

	@Override
	public PageInfoDto queryList(Map<String, String> queryParam) throws ServiceBizException {
		return dao.queryList(queryParam);
	}

	@Override
	public Map<String, Object> getMenuData(String id,String roleType) throws ServiceBizException {
		return dao.getMenuData(id,roleType);
	}

	@Override
	public void checkRole(String roleCode, Map<String, Object> message) throws ServiceBizException {
		Boolean flag = dao.checkRole(roleCode);
		if(!flag){
			message.put("STATUS", 1);
		}else{
			message.put("STATUS", 2);
		}
	}
	
	/**
	 * 新增角色信息
	 */
	@Override
	public Long addRole(OemRoleDTO roleDto) throws ServiceBizException {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		TcRolePO po = new TcRolePO();
		setPo(po,roleDto,1);
		po.saveIt();
		Long billId = po.getLongId();
		if("".equals(CommonUtils.checkNull(roleDto.getNowTree()))){//菜单权限不能为空
			throw new ServiceBizException("菜单权限不能为空");
		}else{
			TrRoleFuncPO.delete(" ROLE_ID = ? ", billId);
			String[] treeArray = roleDto.getNowTree().split(",");
			for (int i = 0; i < treeArray.length; i++) {
				TrRoleFuncPO  rfPo = new TrRoleFuncPO();
				rfPo.setLong("ROLE_ID", billId);
				rfPo.setLong("FUNC_ID", treeArray[i]);
				rfPo.setLong("CREATE_BY", loginInfo.getUserId());
				rfPo.setTimestamp("CREATE_DATE", new Date());
				rfPo.saveIt();
			}
		}
		return billId;
	}
	
	/**
	 * 数据关系映射
	 * @param po
	 * @param roleDto
	 */
	private void setPo(TcRolePO po, OemRoleDTO roleDto,int type) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		po.setString("ROLE_NAME", roleDto.getRoleName());
		po.setString("ROLE_DESC", roleDto.getRoleDesc());
		po.setInteger("ROLE_STATUS", roleDto.getRoleStatus());
		po.setInteger("ROLE_TYPE", roleDto.getRoleType());
		po.setLong("OEM_COMPANY_ID", loginInfo.getCompanyId());
		if(type==1){
			po.setInteger("CREATE_BY", loginInfo.getUserId());
			po.setTimestamp("CREATE_DATE", new Date());
		}else{
			po.setInteger("UPDATE_BY", loginInfo.getUserId());
			po.setTimestamp("UPDATE_DATE", new Date());
		}
	}
	
	/**
	 * 修改角色信息
	 */
	@Override
	public void modifyRole(Long id, OemRoleDTO roleDto) throws ServiceBizException {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		TcRolePO po = TcRolePO.findById(id);
		setPo(po,roleDto,2);
		po.saveIt();
		if("".equals(CommonUtils.checkNull(roleDto.getNowTree()))){//菜单权限不能为空
			throw new ServiceBizException("菜单权限不能为空");
		}else{
			TrRoleFuncPO.delete(" ROLE_ID = ? ", id);
			String[] treeArray = roleDto.getNowTree().split(",");
			for (int i = 0; i < treeArray.length; i++) {
				TrRoleFuncPO  rfPo = new TrRoleFuncPO();
				rfPo.setLong("ROLE_ID", id);
				rfPo.setLong("FUNC_ID", treeArray[i]);
				rfPo.setLong("CREATE_BY", loginInfo.getUserId());
				rfPo.setTimestamp("CREATE_DATE", new Date());
				rfPo.saveIt();
			}
		}
	}
	
	
}
