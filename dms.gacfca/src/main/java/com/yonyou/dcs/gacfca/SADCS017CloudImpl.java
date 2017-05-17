package com.yonyou.dcs.gacfca;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.SADCS017Dao;
import com.yonyou.dms.DTO.gacfca.SADMS017Dto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.gacfca.SADMS017Coud;

@Service
public class SADCS017CloudImpl implements SADCS017Cloud {

	private static final Logger logger = LoggerFactory.getLogger(SADCS017CloudImpl.class);

	@Autowired
	SADCS017Dao dao;
	@Autowired
	SADMS017Coud sadms017;

	@Override
	public String execute(String replaceApplyNo) throws ServiceBizException {

		logger.info("================二手车置换客户返利审批数据下发开始（SADCS017CloudImpl）====================");
		List<String> dealer = dao.getAllDcsCode(0);
		for (String dlr : dealer) {

			List<SADMS017Dto> vos = getDataList(replaceApplyNo);

			if (vos != null && !vos.isEmpty()) {

				sendData(vos.get(0).getDealerCode(), vos);

			}
		}
		logger.info("================二手车置换客户返利审批数据下发结束（SADCS017CloudImpl）====================");
		return null;

	}

	private void sendData(String dlr, List<SADMS017Dto> vos) {
		try {
			if (null != vos) {
				// 下发操作
				int flag = sadms017.getSADMS017(dlr, vos);
				if (flag == 1) {
					logger.info("================二手车置换客户返利审批数据下发成功（SADCS017CloudImpl）====================");
				} else {
					logger.info("================二手车置换客户返利审批数据下发失败（SADCS017CloudImpl）====================");
				}
			} else {
				// 经销商无业务范围
				logger.info("================二手车置换客户返利审批数据下发经销商无业务范围（SADCS017CloudImpl）====================");
			}
		} catch (Exception e) {
			logger.info("================二手车置换客户返利审批数据下发异常（SADMS065）====================");
		}

	}

	@Override
	public List<SADMS017Dto> getDataList(String replaceApplyNo) throws ServiceBizException {
		List<SADMS017Dto> vos = new ArrayList<>();
		try {
			vos = dao.getVehicelOwnerInfo(replaceApplyNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Integer size = vos == null ? 0 : vos.size();
		return vos;
	}

}
