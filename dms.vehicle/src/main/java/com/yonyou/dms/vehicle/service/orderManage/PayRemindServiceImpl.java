package com.yonyou.dms.vehicle.service.orderManage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.vehicle.dao.factoryOrderDao.PayRemindDao;
import com.yonyou.dms.vehicle.domains.DTO.orderManager.PayRemindDto;
import com.yonyou.dms.vehicle.domains.PO.orderManager.TtPayRemindPO;

@Service
public class PayRemindServiceImpl implements PayRemindService {
	@Autowired
	private PayRemindDao payRemindDao;

	@Override
	public PageInfoDto payRemind(Map<String, String> queryParam) {

		return payRemindDao.QueryPayRemind(queryParam);

	}

	@Override
	public PayRemindDto addPayaremind(PayRemindDto pyDto) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		String RTYPE = CommonUtils.checkNull(pyDto.getOrderType());// 订单类型
		String DAY_NUM = CommonUtils.checkNull(pyDto.getDayNum());// 天数

		TtPayRemindPO id = TtPayRemindPO.findFirst(" RTYPE=? ", RTYPE);

		// Long REMIND_ID = (Long) id.get("REMIND_ID");

		if (id == null) {

			// 新增
			TtPayRemindPO tvgPO = new TtPayRemindPO();
			tvgPO.setInteger("RTYPE", RTYPE);
			tvgPO.setInteger("DAY_NUM", DAY_NUM);
			tvgPO.setLong("CREATE_BY", loginInfo.getUserId());
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String format = df.format(new Date());
			tvgPO.setTimestamp("CREATE_DATE", format);
			tvgPO.saveIt();
		} else {
			throw new ServiceBizException("订单类型已存在");

		}

		return pyDto;
	}

	// 修改
	@Override
	public void editPayaremind(Long id, PayRemindDto payDto) {
		try {
			payDto.setId(id);
			TtPayRemindPO tvgPO = TtPayRemindPO.findById(id);

			LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
			String RTYPE = CommonUtils.checkNull(payDto.getOrderType());// 订单类型
			String DAY_NUM = CommonUtils.checkNull(payDto.getDayNum());// 天数

			tvgPO.setInteger("RTYPE", RTYPE);
			tvgPO.setInteger("DAY_NUM", DAY_NUM);
			tvgPO.setLong("CREATE_BY", loginInfo.getUserId());
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			System.out.println(df.format(new Date()));
			String format = df.format(new Date());
			// tvgPO.setTime("CREATE_DATE", format);
			tvgPO.setTimestamp("CREATE_DATE", format);
			// tvgPO.setDate("CREATE_DATE", df.format(new Date()));
			tvgPO.saveIt();
		} catch (Exception e) {
			throw new ServiceBizException("修改失败...");
		}

	}

	// 删除
	public void delPayaremind(PayRemindDto payDto, Long id) {
		try {
			payDto.setId(id);
			TtPayRemindPO tvgPO = TtPayRemindPO.findById(id);
			tvgPO.deleteCascadeShallow();
		} catch (Exception e) {
			throw new ServiceBizException("删除失败...");

		}
	}

}
