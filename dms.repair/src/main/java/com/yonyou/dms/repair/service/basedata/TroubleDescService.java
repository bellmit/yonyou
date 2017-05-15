package com.yonyou.dms.repair.service.basedata;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.common.domains.PO.basedata.TroubleDescPO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.repair.domains.DTO.basedata.TroubleDescDTO;

/**
 * 故障描述
 * 
 * @author chenwei
 * @date 2017年3月24日
 */
public interface TroubleDescService {
    public TroubleDescPO findByPrimaryKey(String troubleCode)throws ServiceBizException;
    
    public PageInfoDto searchTroubleDesc(Map<String, String> queryParam)throws ServiceBizException;///查询
    
    public String insertTroubleDescPO(TroubleDescDTO troubleDescto)throws ServiceBizException;///增加
    
    public void updateTroubleDesc(String troubleCode,TroubleDescDTO troubleDescto)throws ServiceBizException;///修改
    
    public void deleteTroubleDescById(Long id)throws ServiceBizException;///删除

    public List<Map> queryTroubleDesc(Map<String, String> queryParam) throws ServiceBizException;
}
