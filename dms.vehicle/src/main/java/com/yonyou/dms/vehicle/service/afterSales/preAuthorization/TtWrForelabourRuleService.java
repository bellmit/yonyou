package com.yonyou.dms.vehicle.service.afterSales.preAuthorization;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.basicDataMgr.TtWrForelabourRuleDTO;

/**
 * 索赔预授权工时规则维护
 * @author Administrator
 *
 */
public interface TtWrForelabourRuleService {
	//索赔预授权工时规则维护查询
	public PageInfoDto TtWrForelabourRuleQuery(Map<String, String> queryParam) ;
	//查询所有授权顺序
	public List<Map> getAllLevel(String labourCode) ;
	
	public List<Map> getLevel() ;
	
	//删除
	public void delete(Long id);
	
	//信息回显
	public Map getForelabourRuleById(Long id);

	
	//预授权工时规则新增
	public Long addForelabourRule(TtWrForelabourRuleDTO ptdto) throws ServiceBizException ;
	
	//预授权工时规则维护修改
	public void modifyTtWrForelabourRule(Long id, TtWrForelabourRuleDTO ptdto);
}
