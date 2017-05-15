/**
 * 
 */
package com.yonyou.dms.part.service.basedata;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.common.domains.DTO.basedata.TtSalesQuoteDTO;
import com.yonyou.dms.common.domains.DTO.basedata.TtSalesQuoteItemDTO;
import com.yonyou.dms.common.domains.PO.basedata.TtSalesQuotePO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 配件报价单
* TODO description
* @author chenwei
* @date 2017年5月4日
* @SuppressWarnings("rawtypes")//去除警告，rawtypes指传参数时要传泛型参数
 */
@SuppressWarnings("rawtypes")
public interface SalesQuoteService {

    /**
     * 查询配件报价单信息
     * 
     * @param map
     * @return
     */
    PageInfoDto findAllSalesQuoteInfo(Map<String, String> map) throws ServiceBizException;
    
    /**
     * 查询配件报价单信息
     * 
     * @param map
     * @return
     */
    List<Map> findSalesQuoteList(Map<String, String> map) throws ServiceBizException;
    
    /**
     * 查询配件报价单信息
     * 
     * @param map
     * @return
     */
    List<Map> findSalesQuoteItemList(Map<String, Object> map) throws ServiceBizException;
    
    
    /**
     * 查询配件销售单
    * TODO description
    * @author chenwei
    * @date 2017年5月8日
    * @param map
    * @return
    * @throws ServiceBizException
     */
    PageInfoDto QueryPartSalesSlip(Map<String, String> map) throws ServiceBizException;
    
    /**
     * 查询ttSalesQuote表中该sales_quote_no对应行是否锁住
    * TODO description
    * @author chenwei
    * @date 2017年5月4日
    * @param quote
    * @return
     */
    String checkLockSalesQuote(String salesQuoteNo) throws ServiceBizException;
    
    /**
     * 查询TT_SALES_PART表中该sales_part_no对应行是否锁住
    * TODO description
    * @author chenwei
    * @date 2017年5月4日
    * @param quote
    * @return
     */
    String checkLockSalesPart(String salesPartNo) throws ServiceBizException;
    
    /**
     * 查询业务往来客户
    * TODO description
    * @author chenwei
    * @date 2017年5月4日
    * @param map
    * @return
     */
    List<Map> queryPartStockItem(Map<String, String> map) throws ServiceBizException;
    
    /**
     * 查询配件销售单明细表
    * TODO description
    * @author chenwei
    * @date 2017年5月4日
    * @param map
    * @return
     */
    List<Map> queryPartSalesItem(Map<String, String> map) throws ServiceBizException;
    
    /**
     * 查询配件列表
    * TODO description
    * @author chenwei
    * @date 2017年5月5日
    * @param map
    * @return
     */
    PageInfoDto queryPartStock(Map<String, String> map) throws ServiceBizException;
    
    /**
     * 精确查询配件库存信息
    * TODO description
    * @author chenwei
    * @date 2017年5月6日
    * @param map
    * @return
     */
    PageInfoDto queryPartInfo(Map<String, String> map) throws ServiceBizException;
    
    /**
     * 新增报价单
    * TODO description
    * @author chenwei
    * @date 2017年5月6日
    * @param salesQuoteDTO
     */
    void insertTtSalesQuotePO(TtSalesQuotePO salesQuotePO) throws ServiceBizException;
    
    /**
     * 新增报价单明细
    * TODO description
    * @author chenwei
    * @date 2017年5月7日
    * @param ttSalesQuoteItemDTO
    * @throws ServiceBizException
     */
    public void insertTtSalesQuoteItemPO(TtSalesQuoteItemDTO ttSalesQuoteItemDTO)throws ServiceBizException;
    
    /**
     * 查询配件基本信息表
    * TODO description
    * @author chenwei
    * @date 2017年5月6日
    * @param queryParam
    * @return
    * @throws ServiceBizException
     */
    public List<Map> queryPartInfoList(Map<String, Object> queryParam) throws ServiceBizException;
    
    /**
     * 删除配件销售报价单明细
    * TODO description
    * @author chenwei
    * @date 2017年5月6日
    * @param deleteParams
    * @throws ServiceBizException
     */
    public void deleteTtSalesQuoteItem(TtSalesQuoteItemDTO deleteParams) throws ServiceBizException;
    
    /**
     * 更新报价单明细表
    * TODO description
    * @author chenwei
    * @date 2017年5月7日
    * @param salesNumber
    * @param ttSalesQuotepo
    * @throws ServiceBizException
     */
    public void updateTtSalesQuoteItem(Long itemId, TtSalesQuoteItemDTO itemDto) throws ServiceBizException;
    
    
    /**
     * 删除配件销售报价单
    * TODO description
    * @author chenwei
    * @date 2017年5月6日
    * @param deleteParams
    * @throws ServiceBizException
     */
    public void deleteTtSalesQuote(TtSalesQuoteDTO deleteParams) throws ServiceBizException;
    
    /**
     * 修改报价单表
    * TODO description
    * @author chenwei
    * @date 2017年5月7日
    * @param salesNumber
    * @param ttSalesQuotedto
    * @throws ServiceBizException
     */
    public void updateTtSalesQuote(String salesNumber, TtSalesQuoteDTO ttSalesQuotedto) throws ServiceBizException;
    
    /**
     * 修改报价单根据自写sql
    * TODO description
    * @author chenwei
    * @date 2017年5月7日
    * @param sqlStr
    * @param sqlWhere
    * @param paramsList
    * @return
    * @throws ServiceBizException
     */
    public int modifyTtSalesQuoteByParams(String sqlStr, String sqlWhere, List paramsList) throws ServiceBizException;
    
    
    /**
     * 查询车主页面 * TODO description
    * @author chenwei
    * @date 2017年5月8日
    * @param queryParam
    * @return
    * @throws ServiceBizException
     */
    public PageInfoDto QueryOwnerByNoOrSpell(Map<String, String> queryParam) throws ServiceBizException;
    
    
    /**
     * 查询配件库存 根据配件no和仓库no等等
    * TODO description
    * @author chenwei
    * @date 2017年5月9日
    * @param map
    * @return
    * @throws ServiceBizException
     */
    public List<Map> findTmPartStockList(Map<String, Object> map) throws ServiceBizException;

}
