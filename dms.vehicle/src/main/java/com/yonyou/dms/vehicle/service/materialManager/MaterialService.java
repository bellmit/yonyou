package com.yonyou.dms.vehicle.service.materialManager;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.common.domains.PO.basedata.MaterialGroupRPO;
import com.yonyou.dms.common.domains.PO.basedata.MaterialPO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.domains.DTO.materialManager.MaterialDTO;

public interface MaterialService {

	public PageInfoDto queryList(Map<String, String> queryParam) throws ServiceBizException;

	public List<Map> queryMaterialGroupForExport(Map<String, String> queryParam) throws ServiceBizException;

	public MaterialPO getById(Long id) throws ServiceBizException;
	
	public MaterialGroupRPO getGroupById(Long id) throws ServiceBizException;

	public Long addMaterialGroup(MaterialDTO mgDto) throws ServiceBizException;

	public void ModifyMaterialGroup(Long id, MaterialDTO mgDto) throws ServiceBizException;

	public List<Map> getDealerList(Map<String, String> queryParams);

	public List<Map> getDealerBuss();

	public void sendMaterialGroup(MaterialDTO mgDto);

	public void sendMaterial(MaterialDTO mgDto);

}
