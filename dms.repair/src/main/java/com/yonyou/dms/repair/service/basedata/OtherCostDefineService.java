package com.yonyou.dms.repair.service.basedata;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.repair.domains.DTO.basedata.OtherCostDefineDTO;

/**
 * 其他成本业务层接口
* TODO description
* @author yujiangheng
* @date 2017年4月10日
 */
public interface OtherCostDefineService {
    public PageInfoDto searchOtherCost(Map<String, String> queryParam)throws ServiceBizException;
    public void addOtherCostDefine(OtherCostDefineDTO otherCostDefineDTO)throws ServiceBizException;
    public Map<String, String> findByOtherCostCode(String otherCostCode)throws ServiceBizException;
    public void updateOtherCost(String otherCostCode, OtherCostDefineDTO otherCostDefineDTO)throws ServiceBizException;
}
