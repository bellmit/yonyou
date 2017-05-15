package com.yonyou.dms.vehicle.service.afterSales.basicDataMgr;

import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.basicDataMgr.TtWrBaseCodeDTO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.basicDataMgr.TtWrBaseCodePO;

/**
 * 代码维护
 * @author Administrator
 *
 */
public interface BasicCodeService {
	//代码维护查询
	public PageInfoDto  BasicCodeQuery(Map<String, String> queryParam);
	//新增代码维护

	public Long addBasicCode(TtWrBaseCodeDTO ptdto);
	//修改代码维护
	public void edit(Long id,TtWrBaseCodeDTO dto);
   //通过id进行代码维护信息回显
	public TtWrBaseCodePO getBasicCodeById(Long id); 
	
	//删除故障代码
	
	public void delete(Long id);

	
}
