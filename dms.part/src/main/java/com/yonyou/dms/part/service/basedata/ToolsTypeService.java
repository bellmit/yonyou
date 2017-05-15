package com.yonyou.dms.part.service.basedata;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.part.domains.DTO.basedata.ToolsTypeDTO;
import com.yonyou.dms.part.domains.DTO.basedata.UnitDTO;
/**
 * 工具类别的实现接口
* TODO description
* @author yangjie
* @date 2017年4月12日
 */
public interface ToolsTypeService  {
    
    public PageInfoDto searchToolsType(Map<String, String> queryParam)throws ServiceBizException;///根据条件查询计量单位信息  
    public void addToolsType(ToolsTypeDTO toolsTypeDTO)throws ServiceBizException;///增加
    public void updateToolsType(String toolTypeCode,ToolsTypeDTO toolsTypeDTO)throws ServiceBizException;///修改
    public Map<String, String>  findBytoolTypeCode(String toolTypeCode)throws ServiceBizException;//根据unitCode查询一个计量单位信息
    public List<Map> getAllSelect()throws ServiceBizException;//无查询条件查询所有的数据
}
