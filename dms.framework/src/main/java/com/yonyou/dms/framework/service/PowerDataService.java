package com.yonyou.dms.framework.service;

import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 
* 数据权限Service
* @author Administrator
* @date 2016年12月5日
 */
public interface PowerDataService {
    
    public void getDataPower(Long userId , String orgCode) throws ServiceBizException;
    
    public String getChildsDepts(String orgCode) throws ServiceBizException;
    
    
           
}
