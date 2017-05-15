package com.yonyou.dms.vehicle.service.saleOdditionalOrder;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.domains.DTO.salesOdditionalOrder.SalesOdditionalOrderDTO;

/**
 * 服务订单接口
 * @author Benzc
 * @date 2017年3月8日
 */
public interface SaleOdditionalOrderService {
	
	//服务订单分页查询
	public PageInfoDto QuerySaleOdditionalOrder(Map<String, String> queryParam);
    
	//根据客户编号查询客户信息
	public PageInfoDto QueryCustomer(Map<String, String> queryParam);
	
	//根据销售单号查询订单信息
    public PageInfoDto QuerySalesOrder(Map<String, String> queryParam);
    
    //工时单价下拉框
    @SuppressWarnings("rawtypes")
	public List<Map> selectPrice(Map<String, String> queryParam) throws ServiceBizException;
    
    //服务项目编辑页面查询
    @SuppressWarnings("rawtypes")
	public List<Map> queryServiceProject(String id) throws ServiceBizException;
    
    //新增
	public void addSalesOdditionalOrder(SalesOdditionalOrderDTO salesOrderDTO, String soNo) throws ServiceBizException;
	
	//修改
	public void modifyOdditionalOrderInfo(String soNo, SalesOdditionalOrderDTO salesOrderDTO) throws ServiceBizException;
    
    //装潢项目分页查询
    public PageInfoDto queryDecrodateProject(Map<String, String> queryParam) throws ServiceBizException;
    
    //根据id查询服务订单信息
    public Map<String,Object> querySalesOdditionalOrderInfoByid(String id) throws ServiceBizException;
    
    //装潢项目信息查询
    @SuppressWarnings("rawtypes")
	public List<Map> queryUpholster(String id) throws ServiceBizException;
    
    //精品材料信息查询
    @SuppressWarnings("rawtypes")
	public List<Map> queryPart(String id) throws ServiceBizException;
    
    //装潢项目信息查询
    @SuppressWarnings("rawtypes")
	public List<Map> queryService(String id) throws ServiceBizException;
    
    //查询优惠模式信息
  	@SuppressWarnings("rawtypes")
  	public List<Map> discountSelect(Map<String,String> queryParam) throws ServiceBizException;
  	
    //精品材料分页查询
    public PageInfoDto queryPart(Map<String, String> queryParam) throws ServiceBizException;
    
    //装潢退料信息查询
    public PageInfoDto querySalesOrderPart(Map<String, String> queryParam) throws ServiceBizException;
    
    //装潢退料信息子查询
    @SuppressWarnings("rawtypes")
	public List<Map> querySalesPart(String id) throws ServiceBizException;
    
    //订单车辆信息查询
    @SuppressWarnings("rawtypes")
	public List<Map> querySalesOddBySoNo(String id) throws ServiceBizException;

}
