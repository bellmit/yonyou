package com.yonyou.dms.vehicle.service.afterSales.preAuthorization;

import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.basicDataMgr.TtWrForeapprovalDTO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.basicDataMgr.TtWrForeapprovalPO;

/**
 * 索赔预授权审核作业 
 * @author Administrator
 *
 */
public interface PreclaimAuditService {
	
	//索赔预授权审核作业查询
	public PageInfoDto PreclaimAuditSearch(Map<String, String> queryParam);
     //同意,审核通过
	public void editTongyi(Long id, TtWrForeapprovalDTO ptdto);
	//通过id查询，回显信息
	public TtWrForeapprovalPO getForeapprovalById(Long id);
	  //拒绝\退回
	public void editJuTui(Long id, TtWrForeapprovalDTO ptdto,String type);
	
	
}
