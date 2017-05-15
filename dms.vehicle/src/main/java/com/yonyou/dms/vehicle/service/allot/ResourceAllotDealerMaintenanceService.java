package com.yonyou.dms.vehicle.service.allot;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.domains.DTO.allot.EditAllotDerDto;
import com.yonyou.dms.vehicle.domains.DTO.allot.EditOrgDto;
import com.yonyou.dms.vehicle.domains.DTO.allot.EditOrgDto2;

/**
 * 资源分配经销商维护 资源分配--大区总维护
 * 
 * @author Administrator
 *
 */
public interface ResourceAllotDealerMaintenanceService {
	// 资源分配经销商维护查询
	public PageInfoDto allotDealerMaintenanceQuery(Map<String, String> queryParam);

	public void download(Map<String, String> queryParam, HttpServletResponse response, HttpServletRequest request);

	/**
	 * 大区总维护下拉框查询
	 * 
	 * @param queryParam
	 * @return
	 */
	public List<Map> queryTotalOrg(Map<String, String> queryParam);

	/**
	 * 大区总维护下拉框查询
	 * 
	 * @param queryParam
	 * @return
	 */
	public List<Map> queryOrgName(Map<String, String> queryParam);

	/**
	 * 大区总维护查询所有
	 * 
	 * @param queryParam
	 * @return
	 */
	public List<Map> queryAll(Map<String, String> queryParam);

	/**
	 * 大区总维护 查询大区总 弹出框
	 * 
	 * @param queryParam
	 * @return
	 */
	public List<Map> queryTcUserName(Map<String, String> queryParam);

	public Map findById(Long id);

	public List<Map> queryAllById(Long id);

	/**
	 * 大区维护之大区修改
	 * 
	 * @param dto
	 */
	public void editOrg(EditOrgDto dto);

	/**
	 * 大区维护之大区新增
	 * 
	 * 
	 */
	public void addRegion(EditOrgDto2 pyDto);

	/**
	 * 大区总维护新增初始化查询
	 * 
	 * @return
	 */
	public List<Map> queryAddInt();

	/**
	 * 大区维护之大区删除
	 * 
	 * 
	 */
	public void delOrderRepeal(Long id);

	public Map findByid(Long id);

	public void editAllotDealer(EditAllotDerDto dto);

}
