package com.yonyou.dms.vehicle.service.afterSales.preAuthorization;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.basicDataMgr.TtWrForeapprovalptDTO;

/**
 * 预授权监控配件维护
 * @author Administrator
 *
 */
public interface PartMaintainService {
	//预授权监控配件维护查询
	public PageInfoDto PartMaintainQuery(Map<String, String> queryParam);
	//删除
	public void delete(Long id);
	//查询所有配件代码
	public List<Map> getAll(Map<String, String> queryParam) ;
	//新增预授权监控配件维护
	public Long add(TtWrForeapprovalptDTO ptdto) ;

}
