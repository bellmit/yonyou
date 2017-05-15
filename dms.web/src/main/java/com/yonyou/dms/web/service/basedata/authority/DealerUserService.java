package com.yonyou.dms.web.service.basedata.authority;

import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.web.domains.DTO.basedata.authority.SysUserDTO;

public interface DealerUserService {

	public PageInfoDto queryList(Map<String, String> queryParam) throws ServiceBizException;

	public PageInfoDto getPoseList(Map<String, String> queryParam) throws ServiceBizException;

	public Long addDealerUser(SysUserDTO userDto) throws ServiceBizException;

	public void modifyDealerUser(Long id, SysUserDTO userDto) throws ServiceBizException;

}
