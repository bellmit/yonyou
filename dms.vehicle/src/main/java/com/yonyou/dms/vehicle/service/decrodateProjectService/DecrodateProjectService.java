package com.yonyou.dms.vehicle.service.decrodateProjectService;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.domains.DTO.basedata.DecrodateImportDTO;
import com.yonyou.dms.vehicle.domains.DTO.basedata.DecrodateProjectDTO;
import com.yonyou.dms.vehicle.domains.DTO.basedata.DecrodateProjectManageDTO;

@SuppressWarnings("rawtypes")
public interface DecrodateProjectService {
	public PageInfoDto queryDecrodateProject(Map<String, String> queryParam) throws ServiceBizException;
	
	public List<Map> findAllPosition() throws ServiceBizException;
	
	public List<Map> findMain() throws ServiceBizException;
	
	public List<Map> findsub(Map<String, String> queryParam) throws ServiceBizException;
	
	public List<Map> findSub2() throws ServiceBizException;
	
	public List<Map> findWork() throws ServiceBizException;
	
	List<Map> findAllTree(Map query) throws ServiceBizException;
	
	public List<Map> findRepair() throws ServiceBizException;
	
	public void addDecrodate(DecrodateProjectManageDTO decrodateProjectManageDTO) throws ServiceBizException;
	
	public void editDecrodate(DecrodateProjectDTO decrodateProjectDTO) throws ServiceBizException;
	
	Map<String, Object> findById(String id,String id1) throws ServiceBizException;
	
	public void copyAll(DecrodateProjectDTO decrodateProjectDTO) throws ServiceBizException;
	
	public void copyOne(DecrodateProjectManageDTO decrodateProjectManageDTO) throws ServiceBizException;
	
	public List<Map> queryDecrodate(Map<String, String> queryParam) throws ServiceBizException;
	
	public void addInfo(DecrodateImportDTO dto) throws ServiceBizException;
}
