package com.yonyou.dms.vehicle.service.afterSales.basicDataMgr;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.basicDataMgr.TtWrWarrantyDTO;

/**
 * 索赔质保期维护
 * @author Administrator
 *
 */
public interface ClaimQualityService {
	//索赔质保期维护查询
	public PageInfoDto ClaimQualityQuery(Map<String, String> queryParam);
	
	 //索赔质保期维护新增
	public Long add(TtWrWarrantyDTO ptdto);
	
	
	 //索赔质保期维护修改
	public void edit(Long id, TtWrWarrantyDTO ptdto);
	
	
	  //索赔质保期维护修改信息回显
	public Map getShuju(Long id);
	
	
	  //索赔质保期维护删除
	public void delete(Long id);
	
	//查询所有车系代码
	public List<Map> getAll(Map<String, String> queryParam);
}
