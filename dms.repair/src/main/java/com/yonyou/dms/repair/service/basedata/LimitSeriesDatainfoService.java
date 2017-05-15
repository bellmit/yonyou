package com.yonyou.dms.repair.service.basedata;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.common.domains.DTO.basedata.TMLimitSeriesDatainfoDTO;
import com.yonyou.dms.common.domains.PO.basedata.LimitSeriesDatainfoPO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 限价车系及维修类型
 * 
 * @author chenwei
 * @date 2017年3月27日
 */
public interface LimitSeriesDatainfoService {
    public LimitSeriesDatainfoPO findByPrimaryKey(String itemId)throws ServiceBizException;
    
    public PageInfoDto searchLimitSeriesDatainfo(Map<String, String> queryParam)throws ServiceBizException;///查询
    
    public String insertLimitSeriesDatainfoPo(TMLimitSeriesDatainfoDTO limitSeriesDatainfo)throws ServiceBizException;///增加
    
    public void updateLimitSeriesDatainfo(String itemId,TMLimitSeriesDatainfoDTO limitSeriesDatainfo)throws ServiceBizException;///修改
    
    public void deleteLimitSeriesDatainfoById(Long id)throws ServiceBizException;///删除

    public List<Map> queryLimitSeriesDatainfo(Map<String, String> queryParam) throws ServiceBizException;
}
