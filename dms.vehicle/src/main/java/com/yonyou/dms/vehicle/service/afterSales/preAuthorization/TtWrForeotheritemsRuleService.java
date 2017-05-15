package com.yonyou.dms.vehicle.service.afterSales.preAuthorization;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.basicDataMgr.TtWrForeotheritemsRuleDTO;

/**
 * 预授权其他费用规则维护
 * @author Administrator
 *
 */
public interface TtWrForeotheritemsRuleService {
	//预授权其他费用规则维护查询
	public PageInfoDto  TtWrForeotheritemsRuleQuery(Map<String, String> queryParam);
	//删除
	public void delete(Long id);
	
	//修改
	public void modifyTtWrForeotheritemsRule(Long id, TtWrForeotheritemsRuleDTO ptdto) ;
	//新增
	public Long addTtWrForeotheritemsRule(TtWrForeotheritemsRuleDTO ptdto) throws ServiceBizException ;
	//信息回显
	public Map getTtWrForeotheritemsRuleById(Long id);
	//查询所有授权级别
	public List<Map> getLevel();
	//查询所有项目代码
	public List<Map> getAll(Map<String, String> queryParam) ;
}
