package com.yonyou.dms.gacfca;

import java.util.Date;
import java.util.LinkedList;

import org.apache.log4j.Logger;
import org.javalite.activejdbc.LazyList;
import org.springframework.stereotype.Service;

import com.yonyou.dms.DTO.gacfca.TmDiscounDTO;
import com.yonyou.dms.common.Util.Utility;
import com.yonyou.dms.common.domains.PO.basedata.TmDiscountModePO;
import com.yonyou.dms.function.common.CommonConstants;

/**
 * @description OEM下发优惠模式
 * @author Administrator
 *
 */
@Service
public class CLDMS007CoudImpl implements CLDMS007Coud {

	final Logger logger = Logger.getLogger(CLDMS007CoudImpl.class);

	/**
	 * @description OEM下发优惠模式
	 * @param voList
	 * @param dealerCode
	 */
	@Override
	public int getCLDMS007(LinkedList<TmDiscounDTO> voList, String dealerCode) {
		logger.info("==========getCLDMS007Impl执行===========");
		try{
			if(dealerCode == null || dealerCode.isEmpty()){
				logger.debug("dealerCode 为空,方法中断");
				return 0;
			}

			if(voList != null && !voList.isEmpty()){
				for (TmDiscounDTO tmDiscounDTO : voList) {
					TmDiscountModePO tmDiscountModePO = new TmDiscountModePO();
					tmDiscountModePO.setString("DISCOUNT_MODE_NAME", tmDiscounDTO.getDiscountName());
					tmDiscountModePO.setInteger("OEM_TAG", Utility.getInt(CommonConstants.DICT_IS_YES));
					tmDiscountModePO.setFloat("REPAIR_PART_DISCOUNT", Utility.getFloat(tmDiscounDTO.getPartDiscount()));
					tmDiscountModePO.setFloat("LABOUR_AMOUNT_DISCOUNT", Utility.getFloat(tmDiscounDTO.getLabourDiscount()));
					tmDiscountModePO.setString("DEALER_CODE", dealerCode);

					logger.debug("from TmDiscountModePO DISCOUNT_MODE_CODE = "+tmDiscounDTO.getDiscountCode()+" and DEALER_CODE = "+dealerCode);

					LazyList<TmDiscountModePO> tmDiscountModePOs = TmDiscountModePO.findBySQL("DISCOUNT_MODE_CODE = ? and DEALER_CODE = ?",tmDiscounDTO.getDiscountCode(),dealerCode);
					if (null != tmDiscountModePOs && !tmDiscountModePOs.isEmpty()){
						tmDiscountModePO.setString("UPDATE_BY", CommonConstants.DE_CREATE_UPDATE_BY);
						tmDiscountModePO.setDate("UPDATE_AT", new Date());
					}else {
						tmDiscountModePO.setString("DISCOUNT_MODE_CODE", tmDiscounDTO.getDiscountCode());
						tmDiscountModePO.setString("CREATE_By", CommonConstants.DE_CREATE_UPDATE_BY);
						tmDiscountModePO.setDate("CREATE_AT", new Date());
					}
					tmDiscountModePO.saveIt();
				}		
			}
			return 1;
		}catch(Exception e){
			e.printStackTrace();
			logger.debug(e);
			return 0;
		}finally{
			logger.info("==========CLDMS007Impl结束===========");
		}
	}
}
