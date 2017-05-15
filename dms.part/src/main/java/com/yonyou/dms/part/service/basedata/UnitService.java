package com.yonyou.dms.part.service.basedata;
import java.util.List;
import java.util.Map;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.part.domains.DTO.basedata.StoreDTO;
import com.yonyou.dms.part.domains.DTO.basedata.UnitDTO;
/**
 * 计量单位的实现借口
* TODO description
* @author yujiangheng
* @date 2017年3月31日
 */
public interface UnitService {

    public PageInfoDto searchUnit(Map<String, String> queryParam)throws ServiceBizException;///根据条件查询计量单位信息  
    public void addUnitPo(UnitDTO unitPodto)throws ServiceBizException;///增加
    public void updateUnit(String unitCode,UnitDTO unitdto)throws ServiceBizException;///修改
    public Map<String, String>  findByUnitCode(String unitCode)throws ServiceBizException;//根据unitCode查询一个计量单位信息
}
