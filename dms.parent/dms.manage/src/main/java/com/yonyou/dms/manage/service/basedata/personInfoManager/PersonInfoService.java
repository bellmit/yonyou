package com.yonyou.dms.manage.service.basedata.personInfoManager;

import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.manage.domains.DTO.personInfoManager.TcUserDTO;

public interface PersonInfoService {
	
	
	//BY 用户ID,组织ID，公司ID 查询出指定用户的职务信息
	public PageInfoDto personInfoQuery(Map<String, String> queryParam, LoginInfoDto loginInfo) ;
	 
	   //修改当前用户的信息
    public void modifyUserinfo(TcUserDTO dbDto)throws ServiceBizException;
	 

}
