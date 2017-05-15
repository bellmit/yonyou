package com.yonyou.dms.vehicle.service.afterSales.basicDataMgr;

import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.basicDataMgr.TtWrOtherfeeDTO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.basicDataMgr.TtWrOtherfeePO;

/**
 * 索赔其他费用设定
 * @author Administrator
 *
 */
public interface OtherFeeService {
     //索赔其他费用设定查询
	public PageInfoDto  ClaimTypeQuery(Map<String, String> queryParam);
	
	//索赔其他费用设定信息回显
	public TtWrOtherfeePO getOtherFeeById(Long id); 
	
	//索赔其他费用修改
	public void edit(Long id, TtWrOtherfeeDTO ptdto) ;
	
	//新增索赔其他费用
	
	public Long add(TtWrOtherfeeDTO ptdto) ;
	
	//删除
	public void delete(Long id);

	
	
}
