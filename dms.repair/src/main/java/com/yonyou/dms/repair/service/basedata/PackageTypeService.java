package com.yonyou.dms.repair.service.basedata;

import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.repair.domains.DTO.basedata.PackageTypeDTO;
import com.yonyou.dms.repair.domains.PO.basedata.PackageTypePO;

/**
 * 组合类别
 * 
 * @author sunqinghua
 * @date 2017年3月22日
 */
public interface PackageTypeService {
	public PageInfoDto queryPackageType(Map<String, String> queryParam) throws ServiceBizException;

	public Map findPackageTypeById(String id) throws ServiceBizException;

	public String addPackageType(PackageTypeDTO pyto) throws ServiceBizException;

	public void modifyPackageType(String id, PackageTypeDTO pyto) throws ServiceBizException;
}
