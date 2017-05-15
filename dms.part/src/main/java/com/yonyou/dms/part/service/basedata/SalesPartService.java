/**
 * 
 */
package com.yonyou.dms.part.service.basedata;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.common.domains.DTO.basedata.TMLimitSeriesDatainfoDTO;
import com.yonyou.dms.common.domains.DTO.basedata.TtSalesPartItemDTO;
import com.yonyou.dms.common.domains.DTO.customer.TmVehicleDTO;
import com.yonyou.dms.common.domains.PO.basedata.TtSalesPartItemPO;
import com.yonyou.dms.common.domains.PO.basedata.TtSalesPartPO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.part.domains.DTO.basedata.TtSalesPartDTO;

/**
 * 配件报价单
* TODO description
* @author chenwei
* @date 2017年5月4日
* @SuppressWarnings("rawtypes")//去除警告，rawtypes指传参数时要传泛型参数
 */
@SuppressWarnings("rawtypes")
public interface SalesPartService {
    /**
     * 新增销售单
    * TODO description
    * @author chenwei
    * @date 2017年5月9日
    * @param TtSalesPartPO
     */
    Boolean insertTtSalesQuotePO(TtSalesPartPO salesPartPO) throws ServiceBizException;
    
    /**
     * 新增销售单明细
    * TODO description
    * @author chenwei
    * @date 2017年5月9日
    * @param TtSalesPartPO
     */
    Boolean insertTtSalesQuoteItemPO(TtSalesPartItemPO salesPartItemPO) throws ServiceBizException;
    
    /**
     * 新增配件销售单明细
    * TODO description
    * @author chenwei
    * @date 2017年5月13日
    * @param TtSalesPartPO
     */
    String insertTtSalesPartItemPO(TtSalesPartItemDTO salesPartItemDTO) throws ServiceBizException;
    
    /**
     * 查询配件销售单信息
     * 
     * @param map
     * @return
     */
    PageInfoDto findAllSalesPartInfo(Map<String, String> map) throws ServiceBizException;
    
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
     * 查询维修工单
    * TODO description
    * @author chenwei
    * @date 2017年5月12日
    * @param queryParams
    * @return
    * @throws ServiceBizException
     */
    public List<Map> selectRepairOrder(Map<String, Object> queryParams) throws ServiceBizException;
    
    
    /**
     * 限价车系及维修类型表
    * TODO description
    * @author chenwei
    * @date 2017年5月12日
    * @param queryParam
    * @return
    * @throws ServiceBizException
     */
    public List<Map> queryLimitSeriesDatainfo(TMLimitSeriesDatainfoDTO tmLSDPo) throws ServiceBizException;
    
    /**
     * 查询配件销售表
    * TODO description
    * @author chenwei
    * @date 2017年5月12日
    * @param salesPartDPo
    * @return
    * @throws ServiceBizException
     */
    public List<Map> querySalesPartList(TtSalesPartDTO salesPartDPo) throws ServiceBizException;
    
    
    /**
     * 查询车辆资料表
    * TODO description
    * @author chenwei
    * @date 2017年5月13日
    * @param tmVehicleDTO
    * @return
    * @throws ServiceBizException
     */
    public List<Map> queryTmVehicleList(TmVehicleDTO tmVehicleDTO) throws ServiceBizException;
    
    /**
     * 查询配件在销售单中的销售数量和退料数量之和
    * TODO description
    * @author chenwei
    * @date 2017年5月13日
    * @param partNo
    * @param salesPartNo
    * @param storageCode
    * @param storagePositionCode
    * @param partBatchNo
    * @return
    * @throws ServiceBizException
     */
    public Float querySumQuantity(String partNo, String salesPartNo, String storageCode, String storagePositionCode, String partBatchNo) throws ServiceBizException;
   
}
