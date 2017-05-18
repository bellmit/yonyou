package com.yonyou.dms.gacfca;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.yonyou.dms.DTO.gacfca.SADCS032Dto;
import com.yonyou.dms.common.domains.PO.basedata.BigCustomerAmountPO;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;

/**
 * 大客户政策车型数据下发
 * 
 * @author Benzc
 * @date 2017年1月10日
 *
 */
@Service
public class SADMS032CoudImpl implements SADMS032Coud {
	final Logger logger = Logger.getLogger(SADMS032CoudImpl.class);

	@Override
	public int getSADMS032(String dealerCode, List<SADCS032Dto> dtList) throws ServiceBizException {
		// 此处需要定义一个DTO列表获取上端数据
		// LinkedList dtList = new LinkedList();
		// String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		try {
			if (dtList != null && dtList.size() > 0) {
				for (int i = 0; i < dtList.size(); i++) {
					SADCS032Dto dto = new SADCS032Dto();
					dto = (SADCS032Dto) dtList.get(i);
					// BigCustomerAmountPO po1 = new BigCustomerAmountPO();
					BigCustomerAmountPO po2 = new BigCustomerAmountPO();
					List<BigCustomerAmountPO> list = BigCustomerAmountPO.find(" DEALER_CODE=?",
							new Object[] { dealerCode });
					if (!"".equals(CommonUtils.checkNull(dto.getBigCustomerApplyId()))) {
						// po2.find("POLICY_ID=? and DEALER_CODE=? and D_kEY=?",
						// new
						// Object[]{dto.getBigCustomerApplyId(),dealerCode,0});
						if (list != null && list.size() > 0) {
							BigCustomerAmountPO.update(
									"IS_DELETE = ?,IS_VALID = ?,EMPLOYEE_TYPE = ?, NUMBER = ?, PS_TYPE = ?,UPDATED_BY=?",
									"POLICY_ID=? and DEALER_CODE=? and D_kEY=?", dto.getIsDelete(), dto.getStatus(),
									dto.getEmployeeType(), dto.getNumber(), dto.getPsType(), 1L,
									dto.getBigCustomerApplyId(), dealerCode, 0);
						} else {
							po2.setString("DEALER_CODE", dealerCode);
							po2.setInteger("IS_DELETE", dto.getIsDelete());
							po2.setInteger("IS_VALID", dto.getStatus());
							po2.setInteger("EMPLOYEE_TYPE", dto.getEmployeeType());
							po2.setInteger("NUMBER", dto.getNumber());
							po2.setLong("POLICY_ID", dto.getBigCustomerApplyId());
							po2.setInteger("PS_TYPE", dto.getPsType());
							po2.setInteger("D_KEY", 0);
							// po2.setDate("CREATED_AT",System.currentTimeMillis());
							po2.setLong("CREATED_BY", 1L);
							po2.insert();
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
