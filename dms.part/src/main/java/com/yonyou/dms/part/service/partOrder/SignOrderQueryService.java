package com.yonyou.dms.part.service.partOrder;

import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 货运签收单查询
* TODO description
* @author yujiangheng
* @date 2017年5月10日
 */
public interface SignOrderQueryService {
    PageInfoDto SignOrderQuery(Map<String, String> queryParam)throws ServiceBizException;
    PageInfoDto QuerySignOrderDetail(String psoNo)throws ServiceBizException;
    
}
