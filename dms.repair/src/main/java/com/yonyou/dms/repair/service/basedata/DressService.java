package com.yonyou.dms.repair.service.basedata;

import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.repair.domains.DTO.basedata.MainDressTypeDTO;
import com.yonyou.dms.repair.domains.DTO.basedata.OtherCostDefineDTO;
import com.yonyou.dms.repair.domains.DTO.basedata.SubDressTypeDTO;

/**
 * 装潢项目类型业务层接口
* TODO description
* @author yujiangheng
* @date 2017年4月10日
 */
public interface DressService {
    public PageInfoDto searchMainDress(Map<String, String> queryParam)throws ServiceBizException;
    public PageInfoDto searchSubDress(String mainGroupCode)throws ServiceBizException;
    public void addMainDress(MainDressTypeDTO mainDressTypeDTO)throws ServiceBizException;
    public Map<String, String> findByMainGroup(String mainGroupCode)throws ServiceBizException;
    public void updateMainDress(String mainGroupCode, MainDressTypeDTO mainDressTypeDTO)throws ServiceBizException;
    
    public void addSubDress(String mainGroupCode,SubDressTypeDTO subDressTypeDTO)throws ServiceBizException;
    public Map<String, String> findSubGroup(String mainGroupCode, String subGroupCode)throws ServiceBizException;
    public void updateSubDress(String mainGroupCode,String subGroupCode,SubDressTypeDTO subDressTypeDTO)throws ServiceBizException;
    
    
    
    
    
}
