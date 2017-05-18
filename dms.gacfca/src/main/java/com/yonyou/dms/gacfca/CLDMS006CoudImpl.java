package com.yonyou.dms.gacfca;

import java.util.Date;
import java.util.LinkedList;

import org.apache.log4j.Logger;
import org.javalite.activejdbc.LazyList;
import org.springframework.stereotype.Service;

import com.yonyou.dms.DTO.gacfca.RoItemListDTO;
import com.yonyou.dms.common.Util.Utility;
import com.yonyou.dms.common.domains.PO.basedata.TmRepairItemPO;
import com.yonyou.dms.function.common.CommonConstants;

/**
 * @description 工时数据下发
 * @author Administrator
 */
@Service
public class CLDMS006CoudImpl implements CLDMS006Coud {

	final Logger logger = Logger.getLogger(CLDMS006CoudImpl.class);

	/**
	 * @description 工时数据下发
	 * @param voList
	 * @param dealerCode
	 */
	@Override
	public int getCLDMS006(LinkedList<RoItemListDTO> voList, String dealerCode) {
		logger.info("==========CLDMS006Impl执行===========");
		try{
			if(dealerCode == null || dealerCode.isEmpty()){
				logger.debug("dealerCode 为空,方法中断");
				return 0;  //执行失败
			}

			if(voList != null && !voList.isEmpty()){
				for (RoItemListDTO roItemListDTO : voList) {
					TmRepairItemPO tmRepairItemPO = new TmRepairItemPO();
					tmRepairItemPO.setString("LABOUR_NAME", roItemListDTO.getLabourName());
					tmRepairItemPO.setString("DEALER_CODE", dealerCode);
					if(Utility.testString(roItemListDTO.getLabourNum())){
						tmRepairItemPO.setFloat("CLAIM_LABOUR", roItemListDTO.getLabourNum().floatValue());
						tmRepairItemPO.setDouble("STD_LABOUR_HOUR", roItemListDTO.getLabourNum());
					}else{
						tmRepairItemPO.setFloat("CLAIM_LABOUR",0f);
						tmRepairItemPO.setDouble("STD_LABOUR_HOUR",0.00);
					}
					tmRepairItemPO.setDouble("ASSIGN_LABOUR_HOUR", 0.00);
					tmRepairItemPO.setInteger("DOWN_TAG", Integer.parseInt(CommonConstants.DICT_IS_YES));
					tmRepairItemPO.setString("MODEL_LABOUR_CODE", roItemListDTO.getModelLabourCode());

					logger.debug("from TmRepairItemPO LABOUR_CODE="+roItemListDTO.getLabourCode()+" and DEALER_CODE="+dealerCode+" and MODEL_LABOUR_CODE="+roItemListDTO.getModelLabourCode());
					LazyList<TmRepairItemPO> list = TmRepairItemPO.findBySQL("LABOUR_CODE = ? and DEALER_CODE = ? and MODEL_LABOUR_CODE = ?", roItemListDTO.getLabourCode(),dealerCode,roItemListDTO.getModelLabourCode());
					if (null != list && list.size()>0){
						tmRepairItemPO.setString("UPDATE_BY", CommonConstants.DE_CREATE_UPDATE_BY);
						tmRepairItemPO.setDate("UPDATE_AT", new Date());
					}else {
						tmRepairItemPO.setString("LABOUR_CODE", roItemListDTO.getLabourCode());
						tmRepairItemPO.setString("CREATE_BY", CommonConstants.DE_CREATE_UPDATE_BY);
						tmRepairItemPO.setDate("CREATE_AT", new Date());
					}
					tmRepairItemPO.saveIt();
				}		
			}
			return 1;
		}catch(Exception e){
			e.printStackTrace();
			logger.debug(e);
			return 0;
		}finally{
			logger.info("==========CLDMS006Impl结束===========");
		}
	}
}
