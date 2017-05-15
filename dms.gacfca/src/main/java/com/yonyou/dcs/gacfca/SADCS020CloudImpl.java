package com.yonyou.dcs.gacfca;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.DeCommonDao;
import com.yonyou.dcs.dao.SADCS020DAO;
import com.yonyou.dms.DTO.gacfca.SADMS020Dto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.gacfca.SADMS020;

@Service
public class SADCS020CloudImpl extends BaseCloudImpl implements SADCS020Cloud {

	private static final Logger logger = LoggerFactory.getLogger(SADCS020CloudImpl.class);
	@Autowired
	SADCS020DAO dao;
	@Autowired
	SADMS020 sadms020;
	@Autowired
	DeCommonDao deCommonDao;

	@Override
	public String execute() throws Exception {
		logger.info("================SADCS020微信车主信息下发开始（ ）====================");
		List<SADMS020Dto> vos = getDataList("");

		if (vos != null && vos.size() > 0) {

			for (SADMS020Dto v : vos) {
				sendData(v.getDealerCode(), v);
			}

		}

		logger.info("================SADCS020微信车主信息下发结束（ ）====================");
		return null;

	}

	@SuppressWarnings("unchecked")
	private void sendData(String dealerCode, SADMS020Dto v) throws Exception {
		Map<String, Object> outDmsDealer = getDmsDealerCode(dealerCode);
		String dmsCode = CommonUtils.checkNull(outDmsDealer == null ? "" : outDmsDealer.get("DMS_CODE"));
		List<SADMS020Dto> vos = new ArrayList<>();
		try {
			if (null != v) {
				// int flag = 0;
				// 下发操作
				vos.add(v);
				if (!"".equals(dmsCode)) {
					int flag = sadms020.getSADMS020(dmsCode, vos);
					if (flag == 1) {
						// 下发成功后更新状态为已扫描
						dao.finishScanStatus(v.getVin());
						logger.info("================SADCS020微信车主信息下发成功（ ）====================");
					} else {
						logger.info("================SADCS020微信车主信息下发失败（ ）====================");
					}
				}
			} else {
				// 经销商无业务范围
				logger.info("================SADCS020微信车主信息下发经销商无业务范围（ ）====================");
			}
		} catch (Exception e) {
			logger.info("================SADCS020微信车主信息下发异常（ ）====================");
		}

	}


	@Override
	public List<SADMS020Dto> getDataList(String param) throws ServiceBizException {
		logger.info("====SADCS020微信车主信息下发开始==== ");
		List<SADMS020Dto> vos = new ArrayList<SADMS020Dto>();
		vos = dao.queryInfo("");
		if (vos == null || vos.size() <= 0) {
			logger.info("====SADCS020微信车主信息下发结束====,无数据！ ");
			return null;
		}
		for (SADMS020Dto vo : vos) {
			List<SADMS020Dto> vosList = new ArrayList<SADMS020Dto>();
			if (vo == null || "".equals(CommonUtils.checkNull(vo.getDealerCode()))) {
				// POContext.endTxn(false);
				logger.info("====SADCS020微信车主信息下发结束====,无数据或上下端经销商对应关系不存在！ ");
				continue;
			}
			vosList.add(vo);
			try {
				logger.info("====SADCS020微信车主信息下发结束====,下发了(" + vosList.size() + ")条数据");
				return vosList;
			} catch (Exception e) {
				logger.error("SADCS020 Cann't send to EntityCode：" + vo.getDealerCode(), e);
			}
		}

		return null;
	}

}
