package com.yonyou.dms.vehicle.service.vehicleDirectOrderManage;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.domains.DTO.retailReportQuery.TtBigCustomerDirectDTO;
import com.yonyou.dms.vehicle.domains.PO.retailReportQuery.TtBigCustomerDirectPO;

/**
 * 直销客户管理
 * @author Administrator
 *
 */
public interface DirectCustomerService {
	//直销客户查询
	public PageInfoDto  directCustomerQuery(Map<String, String> queryParam);
	//导出，下载
	public void download(Map<String, String> queryParam, HttpServletResponse response, HttpServletRequest request) ;
	//查询所有有效银行
	public List<Map> queryBank();
	//新增直销客户信息
	 public Long addDirectCustomer(TtBigCustomerDirectDTO ptdto) throws ServiceBizException;
	 //通过id删除直销客户信息
	 public void deleteDirectCustomerById(Long id , TtBigCustomerDirectDTO ptdto);
	 //通过id进行回显信息
	 public TtBigCustomerDirectPO getDirectCustomerById(Long id) throws ServiceBizException ;

}
