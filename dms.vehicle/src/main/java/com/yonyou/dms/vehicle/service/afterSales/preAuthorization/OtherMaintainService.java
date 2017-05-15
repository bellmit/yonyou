package com.yonyou.dms.vehicle.service.afterSales.preAuthorization;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.basicDataMgr.TtWrForeapprovalotheritemDTO;

/**
 * 预授权其他费用维护
 * @author Administrator
 *
 */
public interface OtherMaintainService {
	//预授权其他费用维护
	public PageInfoDto OtherMaintainQuery(Map<String, String> queryParam);
	//查询所有项目名称
	public List<Map> getAll() throws ServiceBizException ;
	
	//删除
	public void delete(Long id);
	
    //新增
	public Long add(TtWrForeapprovalotheritemDTO ptdto) ;
	
}
