
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.gacfca
*
* @File name : SADMS022.java
*
* @Author : yangjie
*
* @Date : 2017年1月16日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年1月16日    yangjie    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.gacfca;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.DTO.gacfca.SADMS022Dto;
import com.yonyou.dms.common.domains.PO.basedata.BankLoanRatePO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * TODO 业务描述：DCS->DMS SADCS022 车型贴息信息下发 TM_LOAN_RAT_MAINTAIN SADMS022VO.java
 * 
 * @author yangjie
 * @date 2017年1月16日
 */

@Service
public class SADMS022CoudImpl implements SADMS022Coud {

	/**
	 * @author yangjie
	 * @date 2017年1月16日
	 * @return (non-Javadoc)
	 * @see com.yonyou.dms.gacfca.SADMS022Coud#performExecute()
	 */

	@Override
	public int getSADMS022(List<SADMS022Dto> voList, String dealerCode) throws ServiceBizException {
		try {
			// LinkedList voList = new LinkedList();
			if (voList != null && voList.size() > 0) {
				for (int i = 0; i < voList.size(); i++) {
					SADMS022Dto dto = new SADMS022Dto();
					BankLoanRatePO po = new BankLoanRatePO();
					// BankLoanRatePO wpo = new BankLoanRatePO();
					dto = (SADMS022Dto) voList.get(i);
					if (!StringUtils.isNullOrEmpty(dto.getId())) {
						// List cListSame = po.findBySQL("ID=? and
						// dealer_code=?", new
						// Object[]{dto.getId(),dealerCode});
						String sql = "select * from TM_BANK_LOAN_RATE where ID=" + dto.getId() + " and dealer_code='"
								+ dealerCode + "'";
						List<Map> cListSame = OemDAOUtil.findAll(sql, null);
						if (cListSame != null && cListSame.size() > 0) {

							BankLoanRatePO.update(
									"BRAND_CODE=? , SERIES_CODE=? , BANK_CODE=? , DPM_S=? , DPM_E=? , RATE=? , EFFECTIVE_DATE_S=? , EFFECTIVE_DATE_E=? , INSTALLMENT_NUMBER=? , MODEL_CODE=? , STYLE_CODE=?",
									"ID=? and DEALER_CODE=?", dto.getBrandCode(), dto.getSeriesCode(),
									dto.getBankCode(), dto.getDpmS() * 100, dto.getDpmE() * 100, dto.getRate() * 100,
									dto.getEffectiveDateS(), dto.getEffectiveDateE(), dto.getInstallmentNumber(),
									dto.getModelCode(), dto.getStyleCode(), dto.getId(), dealerCode);

							/*
							 * po.findBySQL("ID=? and DEALER_CODE=?", new
							 * Object[]{dto.getId(),dealerCode});
							 * po.setString("BRAND_CODE", dto.getBrandCode());
							 * po.setString("SERIES_CODE",dto.getSeriesCode());
							 * po.setInteger("BANK_CODE",dto.getBankCode());
							 * po.setDouble("DPM_S",dto.getDpmS()*100);
							 * po.setDouble("DPM_E",dto.getDpmE()*100);
							 * po.setDouble("RATE",dto.getRate()*100);
							 * po.setDate("EFFECTIVE_DATE_S",dto.
							 * getEffectiveDateS());
							 * po.setDate("EFFECTIVE_DATE_E",dto.
							 * getEffectiveDateE());
							 * po.setInteger("INSTALLMENT_NUMBER",dto.
							 * getInstallmentNumber());
							 * po.setString("MODEL_CODE",dto.getModelCode());
							 * po.setString("STYLE_CODE",dto.getStyleCode());
							 */
							if (Integer.parseInt(dto.getIsValid().toString()) == 10011001)
								// po.setInteger("IS_VALID",new
								// Long("12781001"));
								BankLoanRatePO.update("IS_VALID=?", "ID=? and DEALER_CODE=?", new Long("12781001"),
										dto.getId(), dealerCode);
							if (Integer.parseInt(dto.getIsValid().toString()) == 10011002)
								// po.setInteger("IS_VALID",new
								// Long("12781002"));
								BankLoanRatePO.update("IS_VALID=?", "ID=? and DEALER_CODE=?", new Long("12781002"),
										dto.getId(), dealerCode);
							// po.saveIt();
						} else {
							po.setString("DEALER_CODE", dealerCode);
							po.setLong("ID", dto.getId());
							po.setString("BRAND_CODE", dto.getBrandCode());
							po.setString("SERIES_CODE", dto.getSeriesCode());
							po.setInteger("BANK_CODE", dto.getBankCode());
							po.setDouble("DPM_S", dto.getDpmS() * 100);
							po.setDouble("DPM_E", dto.getDpmE() * 100);
							po.setDouble("RATE", dto.getRate() * 100);
							po.setDate("EFFECTIVE_DATE_S", dto.getEffectiveDateS());
							po.setDate("EFFECTIVE_DATE_E", dto.getEffectiveDateE());
							po.setInteger("INSTALLMENT_NUMBER", dto.getInstallmentNumber());
							po.setString("MODEL_CODE", dto.getModelCode());
							po.setString("STYLE_CODE", dto.getStyleCode());
							if (Integer.parseInt(dto.getIsValid().toString()) == 10011001)
								po.setInteger("IS_VALID", new Long("12781001"));
							if (Integer.parseInt(dto.getIsValid().toString()) == 10011002)
								po.setInteger("IS_VALID", new Long("12781002"));
							po.setLong("CREATED_BY", 1L);
							// po.setDate("CREATED_AT", new Date());
							po.insert();
						}
					} else {
						throw new ServiceBizException("丢失主键值");
					}
				}
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return 0;
		}
		return 1;
	}

}
