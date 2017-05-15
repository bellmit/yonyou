package com.yonyou.dcs.gacfca;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.DeCommonDao;
import com.yonyou.dcs.dao.SADCS031DAO;
import com.yonyou.dms.DTO.gacfca.SADCS031Dto;
import com.yonyou.dms.common.domains.PO.basedata.TtBigCustomerPolicySeriesPO;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.gacfca.SADMS031;

@Service
public class SADCS031CluodImpl extends BaseCloudImpl implements SADCS031Cluod {
	private static final Logger logger = LoggerFactory.getLogger(SADCS031CluodImpl.class);
	@Autowired
	SADCS031DAO dao;
	@Autowired
	SADMS031 ser;
	@Autowired
	DeCommonDao deCommonDao;

	@Override
	public void execute() throws ServiceBizException {
		logger.info("================大客户政策车系下发下发开始（SADMS065）====================");

		List<String> dealer = dao.getAllDcsCode(0);
		List<SADCS031Dto> vos = getDataList();
		for (String dlr : dealer) {
			Map<String, Object> dmsDealer = deCommonDao.getDmsDealerCode(dlr);

			if ((!StringUtils.isNullOrEmpty(dmsDealer.get("DMS_CODE"))) && vos != null && vos.size() > 0) {

				sendData(vos, dmsDealer.get("DMS_CODE").toString());

			}
			logger.info("================大客户政策车系下发下发结束（SADMS065）====================");
		}
	}

	private void sendData(List<SADCS031Dto> vos, String dmsCode) {
		try {
			if (null != vos) {

				// 下发操作
				int flag = ser.getSADMS031(dmsCode, vos);
				if (flag == 1) {
					logger.info("================大客户政策车系下发下发成功（SADCS031Cluod）====================");
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String strDate = df.format(new Date());
					// 状态修改,修改成已下发
					for (int i = 0; i < vos.size(); i++) {
						Long bigCustomerPolicyId = vos.get(i).getBigCustomerPolicyId();

						TtBigCustomerPolicySeriesPO updatePo = TtBigCustomerPolicySeriesPO
								.findFirst("BIG_CUSTOMER_POLICY_ID=?", bigCustomerPolicyId);
						updatePo.setInteger("Is_Scan", 1);
						updatePo.setInteger("Update_By", 11111111L);
						updatePo.setTimestamp("Update_Date", strDate);
						updatePo.saveIt();
					}
				} else {
					logger.info("================大客户政策车系下发下发下发失败（SADCS031Cluod）====================");
				}
			} else {
				// 经销商无业务范围
				logger.info("================大客户直销生成售后资料下发经销商无业务范围（SADCS031Cluod）====================");
			}
		} catch (Exception e) {
			logger.info("================大客户直销生成售后资料下发异常（SADMS065）====================");
		}
	}

	@Override
	public List<SADCS031Dto> getDataList() throws ServiceBizException {
		List<SADCS031Dto> vos = dao.queryPolicySeriesInfo();
		return vos;
	}

}
