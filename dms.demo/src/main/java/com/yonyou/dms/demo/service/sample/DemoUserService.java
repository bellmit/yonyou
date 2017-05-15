package com.yonyou.dms.demo.service.sample;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.demo.domains.DTO.sample.DemoUserDto;
import com.yonyou.dms.demo.domains.DTO.sample.DemoUserUpdateDto;
import com.yonyou.dms.demo.domains.PO.sample.DemoUserPO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

public interface DemoUserService {

	public PageInfoDto queryUsers(Map<String,String> queryParam) throws ServiceBizException;
	public DemoUserPO getUserById(Long id) throws ServiceBizException;
	public Long addUser(DemoUserDto user) throws ServiceBizException;
	public void modifyUser(Long id,DemoUserDto user) throws ServiceBizException;
	public void deleteUserById(Long id) throws ServiceBizException;
	public List<Map> queryUserAddress(Long id) throws ServiceBizException;
	public List<Map> queryUsersForExport(Map<String,String> queryParam) throws ServiceBizException;
    PageInfoDto queryUserAddressPage(Long id) throws ServiceBizException;
    public void modifyUser(DemoUserUpdateDto userUpdateDto);
}
