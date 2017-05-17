package com.yonyou.dms.vehicle.service.afterSales.basicDataMgr;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.basicDataMgr.TtWrLabourDTO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.basicDataMgr.TtWrLabourPO;

/**
 * 索赔工时维护
 * @author Administrator
 *
 */
public interface TtWrLabourService {
	//查询所有车系集合
	List<Map> getlabour(Map<String, String> queryParams);
	//索赔工时维护查询
	public PageInfoDto  LabourQuery(Map<String, String> queryParam);
	//通过id删除索赔工时维护
	public void delete(Long id);
	//通过id修改索赔工时维护时的回显信息
	public TtWrLabourPO getLabourById(Long id); 
	//通过id进行修改索赔工时维护
	public void edit(Long id,TtWrLabourDTO dto);
	//通过group_code查询得到group_id
	public List<Map> getGroupId(String group_code) throws ServiceBizException;
	//新增索赔工时维护
	public void addLabour(TtWrLabourDTO ptdto);
	
	public List<Map> getBy(TtWrLabourDTO ptdto) throws ServiceBizException ;

	//将数据导入到表中
	void insertTtWrLabourDcs(TtWrLabourDTO rowDto);
	
	
	List<TtWrLabourDTO> checkData(TtWrLabourDTO rowDto, String flag);


}
