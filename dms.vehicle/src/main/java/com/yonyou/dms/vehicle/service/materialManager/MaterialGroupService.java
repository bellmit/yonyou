package com.yonyou.dms.vehicle.service.materialManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.yonyou.dms.common.domains.PO.salesPlanManage.TmVhclMaterialGroupPO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.domains.DTO.basedata.MaterialGroupImportDTO;
import com.yonyou.dms.vehicle.domains.DTO.materialManager.MaterialGroupDTO;

public interface MaterialGroupService {

	public PageInfoDto queryList(Map<String, String> queryParam) throws ServiceBizException;

	public List<Map> queryMaterialGroupForExport(Map<String, String> queryParam) throws ServiceBizException;

	public List<Map> queryMaterialGroupDetailForExport(Map<String, String> queryParam) throws ServiceBizException;

	public PageInfoDto selectMaterialGroupWin(Map<String, String> queryParam, int type) throws ServiceBizException;

	public TmVhclMaterialGroupPO getById(Long id) throws ServiceBizException;

	public Long addMaterialGroup(MaterialGroupDTO mgDto) throws ServiceBizException;

	public void ModifyMaterialGroup(Long id, MaterialGroupDTO mgDto) throws ServiceBizException;

	public ArrayList<MaterialGroupImportDTO> checkData(ArrayList<MaterialGroupImportDTO> dataList);

	public void importSave();

}
