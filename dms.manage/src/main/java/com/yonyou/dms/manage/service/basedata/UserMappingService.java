package com.yonyou.dms.manage.service.basedata;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.manage.domains.DTO.basedata.UserMappingDto;

@SuppressWarnings("rawtypes")
public interface UserMappingService {
    
    public PageInfoDto queryUserMapping(Map<String,String> queryParam) throws ServiceBizException;
    public Map getUserMappingById(String userCode) throws ServiceBizException;
    public List<Map> getSystem() throws ServiceBizException;
    public void modifyUserMapping(String userCode,UserMappingDto userMappingDto) throws ServiceBizException;
    public PageInfoDto queryUsers(Map<String,String> queryParam)throws ServiceBizException;
    public String addUserMapping(UserMappingDto userMappingDto)throws ServiceBizException;
    public void deleteUserMapping(String userCode)throws ServiceBizException;
}
