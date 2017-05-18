package com.yonyou.dms.gacfca;

import java.util.Date;
import java.util.LinkedList;

import org.apache.log4j.Logger;
import org.javalite.activejdbc.LazyList;
import org.springframework.stereotype.Service;

import com.yonyou.dms.DTO.gacfca.OtherFeeDTO;
import com.yonyou.dms.common.domains.PO.basedata.TmBalanceModeAddItemPO;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * @description 附加项目下发
 * @author Administrator
 *
 */
@Service
public class CLDMS011CoudImpl implements CLDMS011Coud{

	final Logger logger = Logger.getLogger(CLDMS011CoudImpl.class);
	
	/**
	 * @description 附加项目下发
	 * @param volist
	 * @param dealerCode
	 */
	@Override
	public int getCLDMS011(LinkedList<OtherFeeDTO> otherFeeDTOs, String dealerCode) throws ServiceBizException {
		logger.info("==========getCLDMS011Impl执行===========");
		try {
			if (dealerCode == null || "".equals(dealerCode.trim())) {
				logger.debug("dealerCode 为空，方法中断");
				return 0;
			}
			if (otherFeeDTOs != null && !otherFeeDTOs.isEmpty()) {
				for (OtherFeeDTO otherFeeDTO : otherFeeDTOs) {
					String addItemCode = otherFeeDTO.getAddItemCode();
					if (addItemCode == null || addItemCode.trim().isEmpty()) {
						logger.debug("addItemCode 为空,方法中断");
						return 0;
					}
					TmBalanceModeAddItemPO tmBalanceModeAddItemPO = new TmBalanceModeAddItemPO();
					tmBalanceModeAddItemPO.setString("DEALER_CODE", dealerCode);
					tmBalanceModeAddItemPO.setString("ADD_ITEM_CODE", addItemCode);
					tmBalanceModeAddItemPO.setString("ADD_ITEM_NAME", otherFeeDTO.getAddItemName());
					tmBalanceModeAddItemPO.setDouble("ADD_ITEM_PRICE", otherFeeDTO.getAddItemPrice());
					tmBalanceModeAddItemPO.setInteger("IS_VALID", otherFeeDTO.getIsValid());
					tmBalanceModeAddItemPO.setInteger("IS_DOWN", Integer.parseInt(CommonConstants.DICT_IS_YES));
					logger.debug("from TmBalanceModeAddItemPO DEALER_CODE = "+dealerCode+" and ADD_ITEM_CODE = "+addItemCode);
					LazyList<TmBalanceModeAddItemPO> tmBalanceModeAddItemPOs = TmBalanceModeAddItemPO.findBySQL("DEALER_CODE = ? and ADD_ITEM_CODE = ?", 
							dealerCode,addItemCode);
					if (tmBalanceModeAddItemPOs != null && tmBalanceModeAddItemPOs.size() > 0) {
						tmBalanceModeAddItemPO.setString("UPDATE_BY", "1");
						tmBalanceModeAddItemPO.setDate("UPDATE_AT", new Date());
					} else {
						tmBalanceModeAddItemPO.setString("CREATE_BY", "1");
						tmBalanceModeAddItemPO.setDate("CREATE_AT", new Date());
					}
					tmBalanceModeAddItemPO.saveIt();
				}
			}
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(e);
			return 0;
		}finally{
			logger.info("==========getCLDMS011Impl结束===========");
		}
	}
}
