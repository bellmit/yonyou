package com.yonyou.dms.repair.service.basedata;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.common.domains.PO.basedata.MaintainWorkTypePO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.repair.domains.DTO.basedata.MaintainWorkTypeDTO;

/**
 * 维修工位
 * 
 * @author chenwei
 * @date 2017年3月23日
 */
public interface MaintainWorkTypeService {
    public MaintainWorkTypePO findByPrimaryKey(String labourPositionCode)throws ServiceBizException;
    
    public PageInfoDto searchMaintainWorkType(Map<String, String> queryParam)throws ServiceBizException;///查询
    
    public String insertMaintainWorkTypePo(MaintainWorkTypeDTO maintainWorkTypeto)throws ServiceBizException;///增加
    
    public void updateMaintainWorkType(String labourPositionCode,MaintainWorkTypeDTO maintainWorkTypeto)throws ServiceBizException;///修改
    
    public void deleteMaintainWorkTypeById(Long id)throws ServiceBizException;///删除

    public List<Map> queryStore(Map<String, String> queryParam) throws ServiceBizException;
}
