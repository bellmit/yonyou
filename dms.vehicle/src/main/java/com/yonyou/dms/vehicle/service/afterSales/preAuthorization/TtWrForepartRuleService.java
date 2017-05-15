package com.yonyou.dms.vehicle.service.afterSales.preAuthorization;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.basicDataMgr.TtWrForepartRuleDTO;

/**
 * 预授权配件规则维护
 * @author Administrator
 *
 */
public interface TtWrForepartRuleService {
	//预授权配件规则维护查询
	public PageInfoDto  TtWrForepartRuleQuery(Map<String, String> queryParam) ;
	
	//删除
	public void delete(Long id);
	
	//新增
	public Long addForepartRuleRule(TtWrForepartRuleDTO ptdto) throws ServiceBizException ;
	//修改
	public void modifyTtWrForepartRule(Long id, TtWrForepartRuleDTO ptdto) ;
	//信息回显
	public Map getForepartRuleById(Long id);

	public List<Map> getLevel();
}
