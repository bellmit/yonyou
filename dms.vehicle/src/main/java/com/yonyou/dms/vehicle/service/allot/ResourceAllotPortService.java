package com.yonyou.dms.vehicle.service.allot;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.vehicle.domains.DTO.allot.ResourceAllotImport2Dto;
import com.yonyou.dms.vehicle.domains.DTO.allot.ResourceAllotImportDto;
import com.yonyou.dms.vehicle.domains.DTO.allot.ResourceAllotPortDto;

public interface ResourceAllotPortService {
	/**
	 * 资源分配港口维护查询
	 * 
	 * @return
	 */
	List<Map> resourceallotportInt();

	/**
	 * 资源分配港口维护修改查询
	 * 
	 * @param id
	 * @return
	 */
	Map findById(Long id);

	/**
	 * 修改
	 * 
	 * @param dto
	 */
	void editAllotPort(ResourceAllotPortDto dto) throws Exception;

	void addResourceallotport(ResourceAllotPortDto pyDto) throws Exception;

	/**
	 * 删除
	 * 
	 * @param id
	 */
	void delOrderRepeal(Long id) throws Exception;
	
	/**
	 * 插入临时表数据
	 * @param rowDto
	 */
	void insertTmp(ResourceAllotImportDto rowDto) throws Exception;
	
	/**
	 * 数据校验
	 * @return
	 */
	public List<Map> checkData();
	
	/**
	 * 查询是否有资源上传
	 * @return
	 */
	public List<Map> findTime();
	
	void update(String id) throws Exception;

	void updateStatus() throws Exception;

	public List<Map> findOrderAllot();

	public void updateDeal() throws Exception;

	/**
	 * 月初结转存储过程转代码
	 * @param inParameter
	 */
	public void callProcedureMonth(List<Object> inParameter) throws Exception;
	
	/**
	 * 资源分配大区存储过程转代码
	 * @param inParameter
	 */
	public void callProcedureInfo(List<Object> inParameter) throws Exception;

	public List<Map> checkData2();

	void insertTmp2(ResourceAllotImport2Dto rowDto) throws Exception;
	
	/**
	 * 临时表导入正式表
	 */
	public void insertTempToTm() throws Exception;

	public Map<String, List<Map>> getExcelData(Map<String, String> queryParam);

	public Map<String, Object> allot(Map<String, String> queryParam) throws Exception;


}
