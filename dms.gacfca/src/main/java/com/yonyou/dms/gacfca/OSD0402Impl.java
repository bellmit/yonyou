package com.yonyou.dms.gacfca;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.yonyou.dms.DTO.gacfca.PoCusWholeRepayClryslerDto;
import com.yonyou.dms.common.domains.PO.basedata.WholesaleRepayPO;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 业务描述：OEM下发批售客户审批返利状态
 * 
 * @date 2017年1月10日
 * @author Benzc
 *
 */
@Service
public class OSD0402Impl implements OSD0402 {

	@Override
	public int getOSD0402(String dealerCode, List<PoCusWholeRepayClryslerDto> dtList) throws ServiceBizException {
		// 此处需要定义一个DTO列表获取上端数据
		// LinkedList dtList = new LinkedList();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// df.format(date)
		if (dealerCode == null) {
			return 0;
		}
		if (dtList != null && dtList.size() > 0) {
			for (int i = 0; i < dtList.size(); i++) {
				PoCusWholeRepayClryslerDto dto = new PoCusWholeRepayClryslerDto();
				WholesaleRepayPO po = new WholesaleRepayPO();
				dto = (PoCusWholeRepayClryslerDto) dtList.get(i);
				if (!StringUtils.isEmpty(dto.getWsNo()) && !StringUtils.isEmpty(dto.getSoStatus().toString())) {

					try {
						WholesaleRepayPO.update(
								"ACTIVATE_TIME=? AND SO_STATUS=? AND AUDIT_DATE=? AND AUDIT_BY=? AND AUDIT_REMARK=? AND UPDATED_BY=? AND UPDATED_AT=? AND FILE_URL_AUDIT=?",
								"D_KEY=? and WS_NO=? and DEALER_CODE=?", dto.getActivityDate(), dto.getSoStatus(),
								df.format(dto.getAuditingDate()), dto.getWsAuditor(), dto.getWsAuditingRemark(), 1L,
								System.currentTimeMillis(), dto.getHeadApprovalFileUrl(), 0, dto.getWsNo(), dealerCode);
					} catch (Exception e) {
						System.out.println(e);
						e.printStackTrace();
					}

					/*
					 * if(dto.getActivityDate() != null){
					 * po.setDate("ACTIVATE_TIME", dto.getActivityDate()); }
					 * po.setInteger("SO_STATUS", dto.getSoStatus());
					 * po.setDate("AUDIT_DATE", dto.getAuditingDate());
					 * po.setString("AUDIT_BY", dto.getWsAuditor());
					 * po.setString("AUDIT_REMARK", dto.getWsAuditingRemark());
					 * po.setLong("UPDATED_BY", 1L); po.setDate("UPDATE_AT",
					 * System.currentTimeMillis());
					 * po.setString("FILE_URL_AUDIT",
					 * dto.getHeadApprovalFileUrl());
					 * 
					 * po.findBySQL("D_KEY=? and WS_NO=? and DEALER_CODE=?", new
					 * Object[]{0,dto.getWsNo(),dealerCode}); po.saveIt();
					 */
				}
			}
		}
		return 1;
	}

}
