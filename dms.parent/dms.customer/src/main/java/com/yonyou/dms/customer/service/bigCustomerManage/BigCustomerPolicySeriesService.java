package com.yonyou.dms.customer.service.bigCustomerManage;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.common.domains.PO.basedata.TtBigCustomerPolicySeriesPO;
import com.yonyou.dms.customer.domains.DTO.bigCustomerManage.TtBigCustomerPolicySeriesDTO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 政策车系定义
 * 
 * @author Administrator
 *
 */
public interface BigCustomerPolicySeriesService {
	// 查询政策车系定义信息
	public PageInfoDto policySeriesQuery(Map<String, String> queryParam);

	// 查询所有品牌代码
	public List<Map> queryBrand();

	// 通过品牌查询车系
	public List<Map> queryChexi(String brandCode, Map<String, String> queryParam);

	// 通过id删除车系
	public void deleteChexiById(Long id, TtBigCustomerPolicySeriesDTO ptdto);

	// 通过id查询回显数据
	public TtBigCustomerPolicySeriesPO getChexiById(Long id) throws ServiceBizException;

	// 通过id修改车系数据
	public void modifyChexi(Long id, TtBigCustomerPolicySeriesDTO ptdto) throws ServiceBizException;

	// 新增车系数据
	public Long addChexi(TtBigCustomerPolicySeriesDTO ptdto) throws ServiceBizException;

}
