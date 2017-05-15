package com.yonyou.dms.gacfca;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.yonyou.dms.DTO.gacfca.WXUnbundingDTO;
import com.yonyou.dms.common.Util.Utility;
import com.yonyou.dms.common.domains.PO.basedata.TmVehiclePO;
import com.yonyou.dms.gacfca.CLDMS015;

/**
 * @description 微信解绑
 * @author Administrator
 *
 */
@Service
public class CLDMS015Impl implements CLDMS015{

	final Logger logger = Logger.getLogger(CLDMS015Impl.class);

	@Override
	public int getCLDMS015(String dealerCode,Long userId, List<WXUnbundingDTO> voList) {
		logger.info("==========CLDMS015Impl执行===========");
		try{	
			if(dealerCode==null){
				logger.debug("dealerCode 为空，方法中断");
				return 0;
			}
			if(voList!=null && voList.size()>0){
				for(WXUnbundingDTO vo : voList){
					if(vo.getIsBundingWx().equals("2")){
						logger.debug("update TmVehiclePO set Wx_Unbundling_Date = "+vo.getWxUnbundlingDate()+",is_binding = 12781002, Binding_Date = null,update_at = "+Utility.getCurrentDateTime()+" where dealer_code = "+dealerCode+" and vin = "+vo.getVin());
						TmVehiclePO.update("Wx_Unbundling_Date = ?,is_binding = ?, Binding_Date = null,update_at = ?", 
								"dealer_code = ? and vin = ?", 
								vo.getWxUnbundlingDate(),12781002,Utility.getCurrentDateTime(),dealerCode,vo.getVin());
					}
				}
			}
			return 1;
		}catch(Exception e){
			e.printStackTrace();
			logger.debug(e);
			return 0;
		}finally{
			logger.info("==========CLDMS015Impl结束===========");
		}
	}

}
