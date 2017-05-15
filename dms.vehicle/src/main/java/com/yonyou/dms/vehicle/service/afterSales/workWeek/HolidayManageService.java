package com.yonyou.dms.vehicle.service.afterSales.workWeek;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.common.domains.PO.basedata.TmHolidayPO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.workWeek.TmHolidayDTO;

/**
 * 假期维护
 * 
 * @author Administrator
 *
 */
public interface HolidayManageService {
	// 假期维护查询
	public PageInfoDto holidayManageQuery(Map<String, String> queryParam);

	// 查询所有年份
	public List<Map> getYear() throws ServiceBizException;

	// 新增假期维护
	public Long addHolidayManage(TmHolidayDTO ptdto);

	// 修改的信息回显
	public TmHolidayPO getHolidayManageById(Long id);

	// 修改
	public void edit(Long id, TmHolidayDTO dto);

	// 删除假期维护数据
	public void delete(Long id);

	// 新增年份初始化
	public List<Map> getYearInit();
}
