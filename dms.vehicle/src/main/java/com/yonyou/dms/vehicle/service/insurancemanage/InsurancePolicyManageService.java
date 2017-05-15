package com.yonyou.dms.vehicle.service.insurancemanage;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.domains.DTO.insurancemanage.CardCouponsDTO;

/**
 * 保险公司维护
 * @author zhiahongmiao 
 *
 */
public interface InsurancePolicyManageService {
//查询
	public PageInfoDto InsurancePolicyManageQuery(Map<String,String> queryParam) throws ServiceBizException;
//下载
	public void insurancePolicyManangeDownload(Map<String, String> queryParam, HttpServletRequest request,HttpServletResponse response) throws ServiceBizException;
//查询维修类型
   public PageInfoDto codeDescQuery(Map<String,String> queryParam) throws ServiceBizException;
//查询保险公司
   public PageInfoDto insurancecompanyQuery(Map<String,String> queryParam) throws ServiceBizException;
//新增
   public long Add(CardCouponsDTO ccDTO) throws ServiceBizException;
}