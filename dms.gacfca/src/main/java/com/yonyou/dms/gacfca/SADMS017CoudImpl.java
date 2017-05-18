package com.yonyou.dms.gacfca;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.yonyou.dms.DTO.gacfca.SADMS017Dto;
import com.yonyou.dms.common.domains.PO.basedata.ReplaceRepayPO;
import com.yonyou.dms.function.exception.ServiceBizException;

@Service
public class SADMS017CoudImpl implements SADMS017Coud {
	final Logger logger = Logger.getLogger(SADMS017CoudImpl.class);

	@Override
	public int getSADMS017(String dealerCode, List<SADMS017Dto> dtList) throws ServiceBizException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = df.format(new Date());

		try {
			// 此处需要定义一个DTO列表获取上端数据
			// LinkedList dtList = new LinkedList();
			// String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
			if (dtList != null && dtList.size() > 0) {
				for (int i = 0; i < dtList.size(); i++) {
					SADMS017Dto dto = new SADMS017Dto();
					dto = (SADMS017Dto) dtList.get(i);
					if (dto != null && !StringUtils.isEmpty(dto.getSoNo())) {
						// ReplaceRepayPO updatepo = new ReplaceRepayPO();
						// updatepo.findBySQL("DEALER_CODE=? and SO_NO=? and
						// D_KEY=?", new Object[]{dealerCode,dto.getSoNo(),1L});
						// updatepo.setString("AUDIT_BY", dto.getAuditBy());
						// updatepo.setInteger("SO_STATUS", dto.getSoStatus());
						// updatepo.setDate("AUDIT_DATE", dto.getAuditDate());
						// updatepo.setString("AUDIT_REMARK",
						// dto.getAuditRemark());
						// updatepo.setLong("UPDATED_BY",1L);
						// updatepo.setDate("UPDATED_AT",
						// System.currentTimeMillis());
						//
						// updatepo.saveIt();

						DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String format1 = df1.format(dto.getAuditDate());

						try {
							ReplaceRepayPO.update(
									"AUDIT_BY=?,SO_STATUS=?,AUDIT_DATE=?,AUDIT_REMARK=?,UPDATED_BY=?,UPDATED_AT=?",
									"DEALER_CODE=? and SO_NO=? and D_KEY=?", dto.getAuditBy(), dto.getSoStatus(),
									format1, dto.getAuditRemark(), 1L, format, dealerCode, dto.getSoNo(), 0);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				}
			}
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(e);
			return 0;
		} finally {
			logger.info("==========SADMS017Impl结束===========");
		}

	}

}
