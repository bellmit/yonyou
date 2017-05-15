package com.yonyou.dms.gacfca;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.yonyou.dms.DTO.gacfca.SADMS023Dto;
import com.yonyou.dms.common.domains.PO.basedata.SoInvoicePO;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.exception.ServiceBizException;

@Service
public class SADMS023Impl implements SADMS023 {
	// SoInvoicePO
	@Override
	public int getSADMS023() throws ServiceBizException {
		try {
			// 获得进销商代码
			String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
			List dtoList = new ArrayList();
			if (dtoList != null || "".equals(dtoList.size() > 0)) {
				for (int i = 0; i < dtoList.size(); i++) {
					SADMS023Dto dto = (SADMS023Dto) dtoList.get(i);
					if (Utility.testString(dto.getVin()) && Utility.testString(dto.getSoNo())) {
						SoInvoicePO soCnd = new SoInvoicePO();
						soCnd.setString("VIN", dto.getVin());
						soCnd.setString("SO_NO", dto.getSoNo());
						soCnd.setString("DEALER_CODE", dealerCode);
						soCnd.setInteger("D_KEY", CommonConstants.D_KEY);

						if (dto.getIdentifyStatus() != null && dto.getIdentifyStatus() > 0) {
							SoInvoicePO so = new SoInvoicePO();
							// 代码转换
							if (dto.getIdentifyStatus() == 17181002) { // 等待识别
								so.setInteger("IDENTIFY_STATUS", dto.getIdentifyStatus());
							} else if (dto.getIdentifyStatus() == 17181003) { // 识别成功
								so.setInteger("IDENTIFY_STATUS", 17181003);
							} else if (dto.getIdentifyStatus() == 17181004) { // 识别失败
								so.setInteger("IDENTIFY_STATUS", 17181004);
							}
							so.saveIt();
							soCnd.saveIt();
						}

					}
				}
			}
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}
}
