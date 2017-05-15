package com.yonyou.dms.vehicle.service.upholsterWorker;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.domains.DTO.UpholsterWorker.UpholsterWorkerDTO;

/**
 * 装潢派工
 * @author Wangliang
 * @date 2017年03月22日
 */
public interface UpholsterWorkerService {
	
	public PageInfoDto queryUpholsterWorker(Map<String,String> queryParam) throws ServiceBizException;
	
	
	
	@SuppressWarnings("rawtypes")
	public List<Map> queryUpholerByid(String id) throws ServiceBizException;

	public PageInfoDto queryDecrodateProject(Map<String, String> queryParam) throws ServiceBizException;

	public long addUpholsterWorkerInfo(UpholsterWorkerDTO upholsterWokerDto,String id) throws ServiceBizException;

	public PageInfoDto queryTechnician(Map<String, String> queryParam) throws ServiceBizException;

	@SuppressWarnings("rawtypes")
	public List<Map> queryLabourPostion(Map<String, String> queryParam) throws ServiceBizException;

	@SuppressWarnings("rawtypes")
	public List<Map> queryUpholsterWorker(String id) throws ServiceBizException;

	public PageInfoDto querySoPart(String id) throws ServiceBizException;

	public long addUpholsterWorker(UpholsterWorkerDTO upholsterWokerDto,String id ) throws ServiceBizException;

    public PageInfoDto queryUpholsterWorker1(String id, String id1) throws ServiceBizException;

	@SuppressWarnings("rawtypes")
	public List<Map> queryWorkType(Map<String, String> queryParam) throws ServiceBizException;

	public long addProject(UpholsterWorkerDTO upholsterWokerDto, String id, String id1) throws ServiceBizException;

	@SuppressWarnings("rawtypes")
	public Map toEditProject(Map<String, String> queryParam, String id) throws ServiceBizException;

	public void update(Long id, Long id1,UpholsterWorkerDTO upholesterWorkerDto) throws ServiceBizException;

	public void deleteById(Long id) throws ServiceBizException;

	public void finalChecked(UpholsterWorkerDTO upholsterWokerDto, String id) throws ServiceBizException;

	public PageInfoDto queryUpholsterWorker1(String id) throws ServiceBizException;
	
	//取消竣工
	public void finalChecked1(UpholsterWorkerDTO upholsterWokerDto, String id) throws ServiceBizException;

	@SuppressWarnings("rawtypes")
	public List<Map> printrByid(String id,String id1) throws ServiceBizException;

	@SuppressWarnings("rawtypes")
	public List<Map> queryPrintTechnician(String id) throws ServiceBizException;

	public Integer isFinshWork(String id) throws ServiceBizException;

	public Integer isFinshWork1(String id) throws ServiceBizException;

	@SuppressWarnings("rawtypes")
	public List<Map> queryUpholsterWorkerExport(Map<String, String> queryParam) throws ServiceBizException;

	@SuppressWarnings("rawtypes")
	public List<Map> querySoPart1(String id) throws ServiceBizException;

}
