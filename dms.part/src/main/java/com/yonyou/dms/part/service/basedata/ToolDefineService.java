package com.yonyou.dms.part.service.basedata;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.part.domains.DTO.basedata.AccountPeriodDTO;
import com.yonyou.dms.part.domains.DTO.basedata.ListToolDefineDTO;
import com.yonyou.dms.part.domains.DTO.basedata.ToolDefineDTO;

/**
 * 工具定义实现接口
* TODO description
* @author yujiangheng
* @date 2017年4月13日
 */
public interface ToolDefineService {
    public PageInfoDto searchToolDefine(Map<String, String> queryParam)throws ServiceBizException;
    public List<Map> getAllSelect() throws ServiceBizException ;
    public void addToolDefine(ToolDefineDTO toolDefineDTO) throws ServiceBizException;
    public Map<String, Object> findByToolCode(String toolCode) throws ServiceBizException ;
    public List<Map> queryToExport(Map<String, String> queryParam) throws ServiceBizException ;
    public void saveAll(List<ToolDefineDTO> listToolDefineDTO) throws ServiceBizException ;
}
