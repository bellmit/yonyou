package com.yonyou.dms.web.service.basedata.authority;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.web.domains.DTO.basedata.authority.SysUserDTO;

public interface SysUserService {

	public PageInfoDto queryList(Map<String, String> queryParam) throws ServiceBizException ;

	public Long addSysUser(SysUserDTO userDto) throws ServiceBizException ;

	public void modifySysUser(Long id, SysUserDTO userDto) throws ServiceBizException;

	public PageInfoDto getPoseList(Map<String, String> queryParam) throws ServiceBizException;

	public void checkUser(String acnt, Map<String, Object> message) throws ServiceBizException;

	public String getUserPoses(Long id) throws ServiceBizException;

	public List<Map> getUserPoseList(Long id) throws ServiceBizException;

}
