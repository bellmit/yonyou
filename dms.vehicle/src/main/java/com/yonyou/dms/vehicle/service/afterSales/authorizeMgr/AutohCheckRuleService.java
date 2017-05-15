package com.yonyou.dms.vehicle.service.afterSales.authorizeMgr;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.basicDataMgr.TtWrAutoRuleDTO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.basicDataMgr.TtWrAutoRulePO;

/**
 * 索赔自动审核规则管理
 * @author Administrator
 *
 */
public interface AutohCheckRuleService {
	//索赔自动审核规则管理查询
	public PageInfoDto  AutohCheckRuleQuery(Map<String, String> queryParam) ;
	
	//启动
	public void qidong(Long id);
	//停止
	public void tingzhi(Long id);
	//查询所有授权顺序
	public List<Map> getAllLevel(Map<String, String> queryParam,Long id) ;
	
	//信息回显
	public TtWrAutoRulePO getLevelById(Long id);
	
	//修改授权顺序
	public void edit(Long id,TtWrAutoRuleDTO dto);
	
	
}
