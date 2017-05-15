package com.yonyou.dms.vehicle.service.materialManager;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.domains.DTO.materialManager.MaterialPackageGroupDTO;
import com.yonyou.dms.vehicle.domains.PO.materialManager.MaterialPackageGroupPO;

public interface MaterialPackageGroupService {

	public PageInfoDto queryList(Map<String, String> queryParam) throws ServiceBizException;

	public List<Map> queryListForExport(Map<String, String> queryParam) throws ServiceBizException;

	public MaterialPackageGroupPO getById(Long id) throws ServiceBizException;

	public Long addMaterialPackageGroup(MaterialPackageGroupDTO mpgDto) throws ServiceBizException;

	public void ModifyMaterialPackageGroup(Long id, MaterialPackageGroupDTO mpgDto) throws ServiceBizException;

}
