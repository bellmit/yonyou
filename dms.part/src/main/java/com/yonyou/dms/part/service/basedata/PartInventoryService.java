package com.yonyou.dms.part.service.basedata;
import java.util.List;
import java.util.Map;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 配件盘点分析
* TODO description
* @author yujiangheng
* @date 2017年5月15日
 */
public interface PartInventoryService {
    public PageInfoDto queryPartInventory(Map<String, String> queryParam) throws ServiceBizException;
    public PageInfoDto queryPartInventoryprofitItems(String inventoryNo) throws ServiceBizException;
    public PageInfoDto queryPartInventorylossItems(String inventoryNo) throws ServiceBizException;
    public List<Map> queryToExport1(String inventoryNo)throws ServiceBizException;
    public List<Map> queryToExport2(String inventoryNo)throws ServiceBizException;
    public void achieve(String inventoryNo)throws Exception;
    
 
    
    
}
