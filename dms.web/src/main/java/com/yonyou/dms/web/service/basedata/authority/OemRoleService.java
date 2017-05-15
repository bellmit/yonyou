package com.yonyou.dms.web.service.basedata.authority;

import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.web.domains.DTO.basedata.authority.OemRoleDTO;

public interface OemRoleService {

	public PageInfoDto queryList(Map<String, String> queryParam) throws ServiceBizException;

	public Map<String, Object> getMenuData(String id, String roleType)  throws ServiceBizException;

	public void checkRole(String roleCode, Map<String, Object> message) throws ServiceBizException;

	public Long addRole(OemRoleDTO roleDto) throws ServiceBizException;

	public void modifyRole(Long id, OemRoleDTO roleDto) throws ServiceBizException;

}
