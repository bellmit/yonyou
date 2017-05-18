package com.yonyou.dms.repair.service.basedata;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.repair.domains.DTO.basedata.ListToolBuyItemDTO;
import com.yonyou.dms.repair.domains.DTO.basedata.ToolBuyItemDTO;

/**
 * 工具采购入库接口
* TODO description
* @author yujianghengs
* @date 2017年4月20日
 */
public interface ToolBuyService {
    public PageInfoDto SearchToolBuyInfo(Map<String, String> queryParam) throws ServiceBizException;
    public List<Map> getAllSelect()throws ServiceBizException;
    public PageInfoDto SearchToolBuyItem(String buyNo)throws ServiceBizException;
    public List<Map> queryToExport(String buyNo)throws ServiceBizException;
    public void AddAccount(String buyNo)throws ServiceBizException, ParseException;
    public void saveToolBuy(ListToolBuyItemDTO listToolBuyItemDTO)throws ServiceBizException;
}
