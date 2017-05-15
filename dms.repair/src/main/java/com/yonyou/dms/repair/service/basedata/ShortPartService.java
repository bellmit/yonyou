package com.yonyou.dms.repair.service.basedata;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.common.domains.PO.basedata.MaintainWorkTypePO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.repair.domains.DTO.basedata.TtShortPartDTO;

/**
 * 配件缺料记录表
 * 
 * @author chenwei
 * @date 2017年4月18日
 */
public interface ShortPartService {
    public MaintainWorkTypePO findByPrimaryKey(String labourPositionCode)throws ServiceBizException;
    
    public PageInfoDto searchMaintainWorkType(Map<String, String> queryParam)throws ServiceBizException;///查询
    
    public String insertShortPartPo(TtShortPartDTO shortPartto)throws ServiceBizException;///增加
    
    public void updateShortPart(String shortId,TtShortPartDTO shortPartto)throws ServiceBizException;///修改
    
    public void deleteShortPartById(Long id)throws ServiceBizException;///删除

    public List<Map> queryShortPart(Map<String, Object> queryParam) throws ServiceBizException;
}
