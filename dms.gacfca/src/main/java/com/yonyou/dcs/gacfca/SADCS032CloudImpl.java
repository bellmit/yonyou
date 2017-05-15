package com.yonyou.dcs.gacfca;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.DeCommonDao;
import com.yonyou.dcs.dao.SADCS032DAO;
import com.yonyou.dms.DTO.gacfca.SADCS032Dto;
import com.yonyou.dms.common.domains.PO.basedata.TtBigCustomerApplyDataPO;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.gacfca.SADMS032;

@Service
public class SADCS032CloudImpl extends BaseCloudImpl implements SADCS032Cloud {
	private static final Logger logger = LoggerFactory.getLogger(SADCS032CloudImpl.class);
	@Autowired
	SADCS032DAO dao;
	@Autowired
	SADMS032 sadms032;
	@Autowired
	DeCommonDao deCommonDao;

	@Override
	public String execute() throws ServiceBizException {
		logger.info("================大客户直销生成售后资料下发执行开始（SADCS032Clud）====================");
		List<SADCS032Dto> vos = getDataList();
		List<String> dealer = dao.getAllDcsCode(0);
		if (vos != null && !vos.isEmpty()) {
			for (String dlr : dealer) {
				Map<String, Object> dmsDealer = deCommonDao.getDmsDealerCode(dlr);
				if ((!StringUtils.isNullOrEmpty(dmsDealer.get("DMS_CODE"))) && vos != null && vos.size() > 0) {

					sendData(vos, dmsDealer.get("DMS_CODE").toString());

				}
			}
		}

		logger.info("================大客户政策车型数据下发下发执行结束（SADCS032Clud）====================");
		return null;
	}

	private void sendData(List<SADCS032Dto> vos, String dmsCode) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = df.format(new Date());

		try {
			if (null != vos) {
				// int flag = 0;
				List<SADCS032Dto> list = new ArrayList<>();
				// 下发操作
				int flag = sadms032.getSADMS032(dmsCode, vos);
				if (flag == 1) {
					logger.info("================大客户政策车型数据下发成功（SADCS032Clud）====================");
					// 状态修改,修改成已下发
					for (int i = 0; i < vos.size(); i++) {
						Long bigCustomerApplyId = vos.get(i).getBigCustomerApplyId();

						TtBigCustomerApplyDataPO updatePo = TtBigCustomerApplyDataPO
								.findFirst("BIG_CUSTOMER_APPLY_ID=?", bigCustomerApplyId);

						updatePo.setInteger("Is_Scan", 1);
						updatePo.setInteger("Update_By", 11111111L);
						updatePo.setTimestamp("Update_Date", format);
						updatePo.saveIt();
					}

				} else {
					logger.info("================大客户政策车型数据下发失败（SADCS032Clud）====================");
				}
			} else {
				// 经销商无业务范围
				logger.info("================大客户政策车型数据下发经销商无业务范围（SADCS032Clud）====================");
			}
		} catch (Exception e) {
			logger.info("================大客户政策车型数据下发异常（SADCS032Clud）====================");
		}
	}

	@Override
	public List<SADCS032Dto> getDataList() throws ServiceBizException {
		List<SADCS032Dto> vos = dao.queryPolicyApplyDateInfo();
		return vos;
	}

}
