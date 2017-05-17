package com.yonyou.dcs.gacfca;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoservice.dms.cgcsl.vo.PoCusWholeRepayClryslerVO;
import com.yonyou.dcs.dao.SADCS015Dao;
import com.yonyou.dms.DTO.gacfca.PoCusWholeRepayClryslerDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.gacfca.OSD0402Coud;

@Service
public class SADCS015_1CloudImpl extends BaseCloudImpl implements SADCS015_1Cloud {
	
	private static final Logger logger = LoggerFactory.getLogger(SADCS015_1CloudImpl.class);
	
	@Autowired
	SADCS015Dao dao;
	
	@Autowired
	OSD0402Coud osd0402;

	@Override
	public String sendData(String wsNo, String dealerCode) throws ServiceBizException {
		logger.info("===============大客户报备返利审批数据下发执行开始（SADCS015_1Cloud）====================");
		List<PoCusWholeRepayClryslerDto> vos = getDtoDataList(wsNo, dealerCode);
		if (vos != null && !vos.isEmpty()) {
			for (int i = 0; i < vos.size(); i++) {
				send(vos.get(i),dealerCode);
			}
		}
		logger.info("================大客户报备返利审批数据下发执行结束（SADCS015_1Cloud）====================");
		return null;
	}

	private void send(PoCusWholeRepayClryslerDto dto,String dealerCode) {
		try {
			if (null != dto) {
				// int flag = 0;
				List<PoCusWholeRepayClryslerDto> list = new ArrayList<>();
				list.add(dto);
				// 下发操作
				int flag = osd0402.getOSD0402(dealerCode,list);
				if (flag == 1) {
					logger.info("================大客户报备返利审批数据下发成功（SADCS015_1Cloud）====================");
				} else {
					logger.info("================大客户报备返利审批数据下发失败（SADCS015_1Cloud）====================");
				}
			} else {
				// 经销商无业务范围
				logger.info("====================================");
			}
		} catch (Exception e) {
			logger.info("================大客户报备返利审批数据下发异常（SADCS015_1Cloud）====================");
		}

	}

	@Override
	public List<PoCusWholeRepayClryslerVO> getVoDataList(String wsNo, String dealerCode) throws ServiceBizException {
		List<PoCusWholeRepayClryslerVO> vos = new ArrayList<>();
		try {
			String remark = dao.queryRemark(wsNo, dealerCode);
			dao.SetRemark(remark);
			vos = dao.queryBigCustomerRebateApprovalVoInfo(wsNo, dealerCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vos;
	}
	public List<PoCusWholeRepayClryslerDto> getDtoDataList(String wsNo, String dealerCode) throws ServiceBizException {
		List<PoCusWholeRepayClryslerDto> vos = new ArrayList<>();
		try {
			String remark = dao.queryRemark(wsNo, dealerCode);
			dao.SetRemark(remark);
			vos = dao.queryBigCustomerRebateApprovalInfo(wsNo, dealerCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vos;
	}
}
