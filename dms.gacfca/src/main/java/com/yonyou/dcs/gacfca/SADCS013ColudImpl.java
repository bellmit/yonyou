package com.yonyou.dcs.gacfca;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.SADCS013Dao;
import com.yonyou.dms.DTO.gacfca.PoCusWholeClryslerDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.gacfca.OSD0401Coud;

@Service
public class SADCS013ColudImpl implements SADCS013Colud {

	private static final Logger logger = LoggerFactory.getLogger(SADCS013ColudImpl.class);
	@Autowired
	SADCS013Dao dao;
	@Autowired
	OSD0401Coud ser;

	@Override
	public String execute(String wsNo, String dealerCode) throws ServiceBizException {

		logger.info("================大客户报备审批结果下发执行开始（SADMS013）====================");
		List<PoCusWholeClryslerDto> vos = getDataList(wsNo, dealerCode);
		if (vos != null && !vos.isEmpty()) {
			for (int i = 0; i < vos.size(); i++) {
				sendData(vos.get(i));
			}
		}
		logger.info("================大客户报备审批结果下发执行结束（SADMS013）====================");
		return null;

	}

	private void sendData(PoCusWholeClryslerDto dto) {
		try {
			if (null != dto) {

				List<PoCusWholeClryslerDto> list = new ArrayList<>();
				list.add(dto);
				// 下发操作
				int fla = ser.getOSD0401(list);
				if (fla == 1) {
					logger.info("================大客户报备审批结果下发执行成功（SADMS013）====================");
				} else {
					logger.info("================大客户报备审批结果下发执行失败（SADMS013）====================");
				}
			} else {
				// 经销商无业务范围
				logger.info("================大客户报备审批结果下发执行无业务范围（SADMS013）====================");
			}
		} catch (Exception e) {
			logger.info("================大客户报备审批结果下发执行发异常（SADMS013）====================");
		}

	}

	@Override
	public List<PoCusWholeClryslerDto> getDataList(String wsNo, String dealerCode) throws ServiceBizException {
		List<PoCusWholeClryslerDto> vos = new ArrayList<>();
		try {
			vos = dao.queryBigCustomerFilingApprovalInfo(wsNo, dealerCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Integer size = vos == null ? 0 : vos.size();
		return vos;
	}
}
