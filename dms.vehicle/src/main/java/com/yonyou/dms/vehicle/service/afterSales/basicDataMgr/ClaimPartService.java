package com.yonyou.dms.vehicle.service.afterSales.basicDataMgr;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.basicDataMgr.TtWrPartwarrantyDTO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.basicDataMgr.TtWrPartwarrantyPO;

/**
 * 索赔配件质保期维护
 * @author Administrator
 *
 */
public interface ClaimPartService {
	//索赔配件质保期维护查询
	public PageInfoDto ClaimQualityQuery(Map<String, String> queryParam);
	//索赔质保期维护新增
	public Long add(TtWrPartwarrantyDTO ptdto);
	//修改
	public void edit(Long id, TtWrPartwarrantyDTO ptdto) ;
	//修改信息回显
	public TtWrPartwarrantyPO getClaimPartById(Long id);
	//删除
	public void delete(Long id);
	
	//查询所有配件代码
	 public List<Map> getAll(Map<String, String> queryParam);

}
