package com.yonyou.dms.report.service.impl;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

public interface RemainingOrderDetailService {

    public PageInfoDto queryRemainingOrderDetail(Map<String, String> queryParam) throws ServiceBizException;

    public List<Map> queryModel(Map<String, String> queryParam) throws ServiceBizException;

    public PageInfoDto queryOrderDetail(Map<String, String> queryParam) throws ServiceBizException;

    public List<Map> queryRemainingOrderDetailList(Map<String, String> queryParam) throws ServiceBizException;

}
