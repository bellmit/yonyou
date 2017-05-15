package com.yonyou.dms.vehicle.service.afterSales.workWeek;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.TmHolidayPO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.dao.afterSales.workWeek.HolidayManageDao;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.workWeek.TmHolidayDTO;

/**
 * 假期维护
 * 
 * @author Administrator
 *
 */
@Service
public class HolidayManageServiceImpl implements HolidayManageService {
	@Autowired
	HolidayManageDao holidayManageDao;

	/**
	 * 假期维护查询
	 */

	@Override
	public PageInfoDto holidayManageQuery(Map<String, String> queryParam) {
		// TODO Auto-generated method stub
		return holidayManageDao.holidayManageQuery(queryParam);
	}

	/**
	 * 查询所有年份信息
	 */
	@Override
	public List<Map> getYear() throws ServiceBizException {
		// TODO Auto-generated method stub
		return holidayManageDao.getYear();
	}

	/**
	 * 新增年份初始化
	 */
	@Override
	public List<Map> getYearInit() {
		// 得到当前年份
		List<Map> yearList = new ArrayList<Map>();
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("HOLIDAY_YEAR", year + "");
		Map<String, Object> mYear = new HashMap<String, Object>();
		yearList.add(m);
		mYear.put("HOLIDAY_YEAR", (year + 1) + "");
		yearList.add(mYear);
		return yearList;
	}

	/**
	 * 新增假期维护
	 * 
	 * @param ptdto
	 * @return
	 */
	public Long addHolidayManage(TmHolidayDTO ptdto) {
		TmHolidayPO ptPo = new TmHolidayPO();
		setApplyPo(ptPo, ptdto);
		return ptPo.getLongId();
	}

	private void setApplyPo(TmHolidayPO ptPo, TmHolidayDTO ptdto) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		if (ptdto.getHolidayName() != null && ptdto.getStartDate() != null && ptdto.getEndDate() != null) {
			ptPo.setString("HOLIDAY_YEAR", ptdto.getHolidayYear());
			ptPo.setDate("START_DATE", ptdto.getStartDate());
			ptPo.setDate("END_DATE", ptdto.getEndDate());
			ptPo.setDate("ADJUST_DATE", ptdto.getAdjustDate());
			ptPo.setDate("ADJUST_DATE2", ptdto.getAdjustDate2());
			ptPo.setInteger("STATUS", ptdto.getStatus());
			ptPo.setString("HOLIDAY_NAME", ptdto.getHolidayName());

			ptPo.setLong("CREATE_BY", loginInfo.getUserId());
			ptPo.setDate("CREATE_DATE", new Date());
			ptPo.setLong("UPDATE_BY", loginInfo.getUserId());
			ptPo.setDate("UPDATE_DATE", new Date());
			ptPo.saveIt();
		} else {
			throw new ServiceBizException("未填写重要信息，请输入！");
		}

	}

	/**
	 * 修改的信息回显
	 */
	@Override
	public TmHolidayPO getHolidayManageById(Long id) {
		// TODO Auto-generated method stub
		return TmHolidayPO.findById(id);
	}

	/**
	 * 进行假期修改
	 */
	@Override
	public void edit(Long id, TmHolidayDTO ptdto) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		TmHolidayPO ptPo = TmHolidayPO.findById(id);
		ptPo.setDate("START_DATE", ptdto.getStartDate());
		ptPo.setDate("END_DATE", ptdto.getEndDate());
		ptPo.setDate("ADJUST_DATE", ptdto.getAdjustDate());
		ptPo.setDate("ADJUST_DATE2", ptdto.getAdjustDate2());
		ptPo.setInteger("STATUS", ptdto.getStatus());
		ptPo.setString("HOLIDAY_NAME", ptdto.getHolidayName());

		ptPo.setLong("UPDATE_BY", loginInfo.getUserId());
		ptPo.setDate("UPDATE_DATE", new Date());

		ptPo.saveIt();

	}

	/**
	 * 通过id进行删除假期维护数据
	 */
	@Override
	public void delete(Long id) {
		TmHolidayPO ptPo = TmHolidayPO.findById(id);
		if (ptPo != null) {
			ptPo.deleteCascadeShallow();
		}
	}

}
