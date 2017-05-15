package com.yonyou.dms.vehicle.service.afterSales.warranty;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.warranty.TtWrWarrantyTypeDTO;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.warranty.TtWrWtParmaterDTO;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.warranty.TtWrWtBugDTO;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.warranty.TtWrWtOperateDTO;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.warranty.TtWrWtPartDTO;

/**
 * 保修类型（非活动）
 * @author zhanghongyi
 *
 */
public interface WarrantyTypeNotActService {
	//保修类型查询
	public PageInfoDto warrantyTypeQuery(Map<String, String> queryParam)throws  ServiceBizException;
	
	//保修类型新增
	public Long addWarrantyType(TtWrWarrantyTypeDTO dto)throws  ServiceBizException;
	
	//保修类型修改
	public void updateWarrantyType(TtWrWarrantyTypeDTO dto)throws  ServiceBizException;
	
	//删除保修参数
	public void deleteParm(BigDecimal id)throws  ServiceBizException;
	
	//删除保修故障
	public void deleteBug(BigDecimal id)throws  ServiceBizException;
	
	//删除保修操作
	public void deleteOper(BigDecimal id)throws  ServiceBizException;
	
	//删除保修零部件
	public void deletePart(BigDecimal id)throws  ServiceBizException;
	
	//查询MSV
	public List<Map> getMvs(Map<String, String> queryParams,String mvsType) throws ServiceBizException;
	
	//保修参数新增
	public Long addParm(TtWrWtParmaterDTO dto)throws  ServiceBizException;
	
	//保修故障新增
	public Long addBug(TtWrWtBugDTO dto)throws  ServiceBizException;
		
	//保修操作新增
	public Long addOper(TtWrWtOperateDTO dto)throws  ServiceBizException;
	
	//保修零部件新增
	public Long addPart(TtWrWtPartDTO dto)throws  ServiceBizException;
	
	//操作代码查询
	public PageInfoDto operSearch(Map<String, String> queryParam) throws ServiceBizException;
	
	//零部件查询
	public PageInfoDto partSearch(Map<String, String> queryParam) throws ServiceBizException;
}
