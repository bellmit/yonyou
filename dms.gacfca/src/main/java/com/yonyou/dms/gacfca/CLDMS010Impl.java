package com.yonyou.dms.gacfca;

import java.util.Date;
import java.util.LinkedList;

import org.apache.log4j.Logger;
import org.javalite.activejdbc.LazyList;
import org.springframework.stereotype.Service;

import com.yonyou.dms.DTO.gacfca.LabourPayDTO;
import com.yonyou.dms.common.domains.PO.basedata.TmLabourPricePO;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * @description 工时单价下发
 * @author Administrator
 *
 */
@Service
public class CLDMS010Impl implements CLDMS010 {

	final Logger logger = Logger.getLogger(CLDMS010Impl.class);

	private final String PRICE_CODE = "8888";

	/**
	 * @description 工时单价下发
	 * @param labourPayDTOs
	 * @param dealerCode
	 */
	@Override
	public int getCLDMS010(LinkedList<LabourPayDTO> labourPayDTOs, String dealerCode) throws ServiceBizException {
		logger.info("==========getCLDMS010Impl执行===========");
		try {
			if (dealerCode == null || dealerCode.trim().isEmpty()) {
				logger.debug("dealerCode 为空，方法中断");
				return 0;
			}
			if (labourPayDTOs != null && !labourPayDTOs.isEmpty()) {

				for (LabourPayDTO labourPayDTO : labourPayDTOs) {
					if (labourPayDTO.getLabPay() != null) {
						logger.debug("from TmLabourPricePO DEALER_CODE = "+dealerCode+" and LABOUR_PRICE_CODE = "+PRICE_CODE);
						LazyList<TmLabourPricePO> tmLabourPricePOs = TmLabourPricePO.findBySQL("DEALER_CODE = ? and LABOUR_PRICE_CODE = ?",
								dealerCode,PRICE_CODE);
						TmLabourPricePO tmLabourPricePO = new TmLabourPricePO();
						tmLabourPricePO.setString("DEALER_CODE", dealerCode);
						tmLabourPricePO.setString("LABOUR_PRICE_CODE",PRICE_CODE);
						tmLabourPricePO.setFloat("LABOUR_PRICE",labourPayDTO.getLabPay().floatValue());
						tmLabourPricePO.setInteger("OEM_TAG",Integer.parseInt(CommonConstants.DICT_IS_YES));
						if (tmLabourPricePOs != null && tmLabourPricePOs.size() > 0) {
							tmLabourPricePO.setString("UPDATE_BY","1");
							tmLabourPricePO.setDate("UPDATE_AT",new Date());
						} else {
							tmLabourPricePO.setString("CREATE_BY","1");
							tmLabourPricePO.setDate("CREATE_AT",new Date());
						}
						tmLabourPricePO.saveIt();
					}
				}
			}
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(e);
			return 0;
		}finally{
			logger.info("==========getCLDMS010Impl结束===========");
		}
	}

}
