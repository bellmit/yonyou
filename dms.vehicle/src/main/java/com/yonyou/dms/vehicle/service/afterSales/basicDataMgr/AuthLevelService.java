package com.yonyou.dms.vehicle.service.afterSales.basicDataMgr;

import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.basicDataMgr.TtWrAuthlevelDTO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.basicDataMgr.TtWrAuthlevelPO;
/**
 *授权级别维护 
 * @author Administrator
 *
 */
public interface AuthLevelService{
	//授权级别维护查询
	public PageInfoDto  AuthLevelQuery(Map<String, String> queryParam);
	
	//授权顺序信息回显

    public TtWrAuthlevelPO getAuthLevelById(Long id); 
	
	//授权顺序信息修改
    
	public void edit(Long id,TtWrAuthlevelDTO dto);
}
