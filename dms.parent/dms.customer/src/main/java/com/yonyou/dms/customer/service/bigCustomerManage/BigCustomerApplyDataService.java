package com.yonyou.dms.customer.service.bigCustomerManage;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.common.domains.PO.basedata.TtBigCustomerApplyDataPO;
import com.yonyou.dms.customer.domains.DTO.bigCustomerManage.TtBigCustomerApplyDataDTO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 政策申请数据定义
 * 
 * @author Administrator
 *
 */
public interface BigCustomerApplyDataService {
	// 政策申请数据查询
	public PageInfoDto applyDateQuery(Map<String, String> queryParam);

	// 通过政策类别查询客户类型
	public List<Map> getEmpType(String type, Map<String, String> queryParams) throws ServiceBizException;

	// 通过id删除申请数据
	public void deleteApplyDataById(Long id, TtBigCustomerApplyDataDTO ptdto);

	// 通过id查询回显申请数据
	public TtBigCustomerApplyDataPO getApplyDataById(Long id) throws ServiceBizException;

	// 通过id修改申请数据信息
	public void modifyApplyData(Long id, TtBigCustomerApplyDataDTO ptdto) throws ServiceBizException;

	// 新增申请数据信息
	public Long addApplyData(TtBigCustomerApplyDataDTO ptdto) throws ServiceBizException;
}
