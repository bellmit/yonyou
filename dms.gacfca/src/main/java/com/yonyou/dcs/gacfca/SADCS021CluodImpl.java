package com.yonyou.dcs.gacfca;

import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.SADMS021DAO;
import com.yonyou.dms.DTO.gacfca.SADMS021Dto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.gacfca.SADMS021;

@Service
public class SADCS021CluodImpl extends BaseCloudImpl implements SADCS021Cloud {
	private static final Logger logger = LoggerFactory.getLogger(SADCS021CluodImpl.class);
	@Autowired
	SADMS021DAO dao;
	@Autowired
	SADMS021 sadms021;

	@Override
	public String handleExecute() throws ServiceBizException {
		logger.info("================SADCS021一对一客户经理绑定修改下发（ ）====================");
		// List<String> dealer = dao.getAllDcsCode(0);
		// for (String dlr : dealer) {
		LinkedList<SADMS021Dto> vos = dao.queryInfo("");

		if (vos != null && !vos.isEmpty()) {
			for (SADMS021Dto sadms021Dto : vos) {

				LinkedList<SADMS021Dto> vosList = new LinkedList<>();
				if (sadms021Dto == null || "".equals(CommonUtils.checkNull(sadms021Dto.getEntityCode()))) {
					// POContext.endTxn(false);
					logger.info("====SADCS021一对一客户经理绑定修改下发结束====,无数据或上下端经销商对应关系不存在！ ");
					continue;
				}
				vosList.add(sadms021Dto);
				try {

					sendAMsg(sadms021Dto.getEntityCode(), vosList);
					logger.info("====SADCS021一对一客户经理绑定修改下发结束====,下发了(" + vosList.size() + ")条数据");
				} catch (Exception e) {
					logger.error("SADCS021 Cann't send to EntityCode：" + sadms021Dto.getEntityCode(), e);
					return null;
				}

			}

			// }

		}

		logger.info("================SADCS020微信车主信息下发结束（ ）====================");
		return null;

	}

	private void sendAMsg(String entityCode, LinkedList<SADMS021Dto> vosList) {
		int flag = sadms021.getSADMS021(entityCode, vosList);
		if (flag == 1) {
			// 下发成功后更新状态为已扫描
			for (SADMS021Dto sadms021Dto : vosList) {

				dao.finishScanStatus(sadms021Dto.getVin());
				logger.info("=================SADCS021一对一客户经理绑定修改下发结束下发成功（ ）====================");
			}
		} else {
			logger.info("=================SADCS021一对一客户经理绑定修改下发结束下发失败（ ）====================");
		}
	}

	public String sendData(String param) throws Exception {
		LinkedList<SADMS021Dto> vos = dao.queryInfo(param);
		try {
			if (null == vos || vos.size() == 0) {
				logger.info("====SADCS021一对一客户经理绑定修改下发结束====,无数据！ ");
				return null;
			}
			for (SADMS021Dto vo : vos) {

				LinkedList<SADMS021Dto> vosList = new LinkedList<>();
				if (vo == null || "".equals(CommonUtils.checkNull(vo.getEntityCode()))) {
					// POContext.endTxn(false);
					logger.info("====SADCS021一对一客户经理绑定修改下发结束====,无数据或上下端经销商对应关系不存在！ ");
					continue;
				}
				vosList.add(vo);
				try {
					sendAMsg(vo.getEntityCode(), vosList);
					logger.info("====SADCS021一对一客户经理绑定修改下发结束====,下发了(" + vosList.size() + ")条数据");
				} catch (Exception e) {
					logger.error("SADCS021 Cann't send to EntityCode：" + vo.getEntityCode(), e);

					return null;
				}

			}

		} catch (Exception e) {
			logger.info("=================SADCS021一对一客户经理绑定修改下发结束下发异常（ ）====================");
		}
		return "1";
	}

}
