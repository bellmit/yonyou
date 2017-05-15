package com.yonyou.dms.vehicle.service.afterSales.warranty;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.warranty.TmpWrOperateDTO;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.warranty.TtWrOperateDTO;

/**
 * 操作代码维护
 * @author xuqinqin
 *
 */
public interface WarrantyOptCodeService {
	//操作代码维护查询
	public PageInfoDto optCodeQuery(Map<String, String> queryParam);
	
	//操作代码维护下载
	public void optCodeDownload( Map<String, String> queryParam, HttpServletRequest request,HttpServletResponse response) throws ServiceBizException;
	
	//操作代码维护新增
	public Long addOptCode(TtWrOperateDTO ptdto) ;
	
	//更新操作代码(回显)
	Map editOptCode(Long Id) throws ParseException;
	
	//更新操作代码(保存)
	public void saveOptCode(TtWrOperateDTO dto, BigDecimal id,LoginInfoDto loginUser)throws  ServiceBizException;
	
	//保存到临时表
	public void insertTmpOptCode(TmpWrOperateDTO rowDto) throws ServiceBizException;
	
	//校验数据
	public ImportResultDto<TmpWrOperateDTO> checkData(TmpWrOperateDTO rowDto) throws ServiceBizException;
	
}
