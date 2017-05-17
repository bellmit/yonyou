package com.yonyou.dcs.gacfca;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.SADCS015Dao;
import com.yonyou.dcs.dao.SaleMaterialPriceDao;
import com.yonyou.dms.DTO.gacfca.PoCusWholeRepayClryslerDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.gacfca.OSD0402Coud;
import com.yonyou.f4.common.database.DBService;

@Service
public class SADCS015CloudImpl implements SADCS015Cloud {
	private static final Logger logger = LoggerFactory.getLogger(SADCS015CloudImpl.class);

	@Autowired
	SaleMaterialPriceDao saleMaterialPriceDao;
	@Autowired
	SADCS015Dao dao;
	@Autowired
	DBService dbService;
	@Autowired
	OSD0402Coud osd0402;

	@Override
	public String execute(String wsNo, String dealerCode) throws ServiceBizException {
		logger.info("===============大客户报备返利审批数据下发执行开始（SADMS015）====================");
		List<PoCusWholeRepayClryslerDto> vos = getDataList(wsNo, dealerCode);
		if (vos != null && !vos.isEmpty()) {
			for (int i = 0; i < vos.size(); i++) {
				sendData(vos.get(i));
			}
		}
		logger.info("================大客户报备返利审批数据下发执行结束（SADMS015）====================");
		return null;
	}

	private void sendData(PoCusWholeRepayClryslerDto dto) {
		try {
			if (null != dto) {
				// int flag = 0;
				List<PoCusWholeRepayClryslerDto> list = new ArrayList<>();
				list.add(dto);
				// 下发操作
				int flag = osd0402.getOSD0402(dto.getDealerCode(), list);
				if (flag == 1) {
					logger.info("================大客户报备返利审批数据下发成功（SADMS015）====================");
				} else {
					logger.info("================大客户报备返利审批数据下发失败（SADMS015）====================");
				}
			} else {
				// 经销商无业务范围
				logger.info("====================================");
			}
		} catch (Exception e) {
			logger.info("================大客户报备返利审批数据下发异常（SADMS015）====================");
		}

	}

	@Override
	public List<PoCusWholeRepayClryslerDto> getDataList(String wsNo, String dealerCode) throws ServiceBizException {
		List<PoCusWholeRepayClryslerDto> vos = new ArrayList<>();
		try {
			vos = dao.queryBigCustomerRebateApprovalInfo(wsNo, dealerCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Integer size = vos == null ? 0 : vos.size();
		return vos;
	}

}
