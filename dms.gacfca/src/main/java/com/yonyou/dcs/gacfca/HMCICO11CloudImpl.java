package com.yonyou.dcs.gacfca;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.UrlFunctionDao;
import com.yonyou.dms.DTO.gacfca.UrlFunctionDTO;

/**
 * @ClassName: HMCICO11Cloud 
 * @Description: TODO(URL功能列表下发) 
 * @author xuqinqin
 * @date 2017-05-04 
 */
@Service
public class HMCICO11CloudImpl extends BaseCloudImpl implements HMCICO11Cloud {
	
	private static final Logger logger = LoggerFactory.getLogger(HMCICO11CloudImpl.class);
	
	@Autowired 
	UrlFunctionDao dao;
	
	@Override
	public List<String> sendData(List<String> dealerCodes){
		logger.info("====URL功能列表下发开始==== " + dealerCodes.size());
		List<String> errCodes = new ArrayList<String>();
		try {
			List<UrlFunctionDTO> list = dao.queryUrlFunction();
			if (null == list || list.size() == 0) {
				return null;
			}
			for (String dealerCode : dealerCodes) {
				Map<String, Object> dmsDealer = dao.getDmsDealerCode(dealerCode);
				//下发操作
//				int flag = (list,dmsDealer);
//				if(flag==1){
//					logger.info("================URL功能列表下发成功（HMCICO11）====================");
//				}else{
//					logger.info("================URL功能列表下发失败（HMCICO11）====================");
//					logger.error("Cann't send to " + dealerCode);
//					errCodes.add(dealerCode);
//				}
			}
		}catch (Exception e) {
			logger.info("================URL功能列表下发异常（HMCICO11）====================");
		}
		return errCodes;
	}
}
