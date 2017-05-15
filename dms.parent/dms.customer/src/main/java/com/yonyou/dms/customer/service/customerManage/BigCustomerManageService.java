package com.yonyou.dms.customer.service.customerManage;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.common.domains.DTO.basedata.BigCustomerDTO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

public interface BigCustomerManageService {
    public PageInfoDto queryBigCustomerWs(Map<String, String> queryParam) throws ServiceBizException;
    
    public PageInfoDto queryBigCustomerWsCar(String id) throws ServiceBizException;
    
    public List<Map> queryOwnerCusBywsNo(String wsNo) throws ServiceBizException;
    
    public PageInfoDto queryBigCustomerWsCarbyWsNoHis(Map<String, String> queryParam,String wsNo) throws ServiceBizException;
    
    public PageInfoDto queryBigCustomerWsCarbyWsNo(Map<String, String> queryParam,String wsNo) throws ServiceBizException;
    
    public PageInfoDto queryBigCustomer(Map<String, String> queryParam) throws ServiceBizException;
    
    public PageInfoDto findAllProduct(Map<String, String> queryParam,String wsAppType) throws ServiceBizException;//查询产品信息
    
    public String addBiGCusInfo(BigCustomerDTO BigCustomerDto, String ownerNo) throws ServiceBizException;
    
    public String modifyBiGCusInfo(BigCustomerDTO BigCustomerDto,String wsNo) throws ServiceBizException;
    
    public String uploanBigCustomer(String wsNo) throws ServiceBizException;
    
    public Map<String, Object> employeSaveBeforeEvent(Map<String, String> queryParam) throws ServiceBizException;
    
    public String CheckData2(String id,String customerNo) throws ServiceBizException;
    public String CheckGenJin(String customerNo) throws ServiceBizException;
    public int totalCarCount(String wsNo) throws ServiceBizException;
    List<Map> queryRetainCustrackforExport(Map<String, String> queryParam) throws ServiceBizException;//使用excel导出
    
    public String CheckStatus(String id) throws ServiceBizException;
    public String CheckStatus1(String id) throws ServiceBizException;
    
}
