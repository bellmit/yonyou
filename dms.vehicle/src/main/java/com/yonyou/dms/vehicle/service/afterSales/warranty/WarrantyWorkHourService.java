package com.yonyou.dms.vehicle.service.afterSales.warranty;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.warranty.TtWrWorrkHourDTO;

/**
 * 保修标准工时维护
 * @author xuqinqin
 *
 */
public interface WarrantyWorkHourService {
	//保修标准工时维护查询
	public PageInfoDto workHourQuery(Map<String, String> queryParam);
	
	//保修标准工时维护下载
	public void workHourDownload( Map<String, String> queryParam, HttpServletRequest request,HttpServletResponse response) throws ServiceBizException;
	
	//保修标准工时维护新增
	public Long addWorkHour(TtWrWorrkHourDTO ptdto) ;
	
	//更新保修标准工时(回显)
	Map editWorkHour(Long Id) throws ParseException;
	
	//更新保修标准工时(保存)
	public void saveWorkHour(TtWrWorrkHourDTO dto, BigDecimal id,LoginInfoDto loginUser)throws  ServiceBizException;
	
//	//保存到临时表
//	public void insertTmpWorkHour(TmpWrOperateDTO rowDto) throws ServiceBizException;
//	
//	//校验数据
//	public ImportResultDto<TmpWrOperateDTO> checkData(TmpWrOperateDTO rowDto) throws ServiceBizException;
	
}
