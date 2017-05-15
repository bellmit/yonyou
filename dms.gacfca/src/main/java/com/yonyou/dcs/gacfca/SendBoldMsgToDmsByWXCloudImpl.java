package com.yonyou.dcs.gacfca;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.SaleVehicleSaleDao;
import com.yonyou.dms.DTO.gacfca.WXBindingRsgDTO;
import com.yonyou.dms.common.domains.DTO.basedata.OutBoundReturnDTO;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.gacfca.CLDMS013;

@Service
public class SendBoldMsgToDmsByWXCloudImpl extends BaseCloudImpl implements SendBoldMsgToDmsByWXCloud {
	
	private static final Logger logger = LoggerFactory.getLogger(SEDCS054CloudImpl.class);
	
	@Autowired
	SaleVehicleSaleDao saleDao;
	
	@Autowired
	CLDMS013 cldms013;

	@Override
	public String handleExecutor(List<OutBoundReturnDTO> dtoList) throws ServiceBizException {
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String sendDate(WXBindingRsgDTO returnVo,String IsEntity) throws ServiceBizException {
		logger.info("###################### 开始下发数据(SEND TO DMS BY WX) #############################");
		int msgId = 0;
		try {
			/** 当前登录信息 */
		    LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
			//通过上端dealer_code获取下端对应的经销商大吗
			Map<String, Object> outDmsDealer = saleDao.getDmsDealerCode(returnVo.getDealerCode());
			
			String dmsCode =  "";
			if(outDmsDealer!=null){
				dmsCode =  CommonUtils.checkNull(outDmsDealer.get("DMS_CODE")); //转换成下端的DEALER_CODE
			}
			returnVo.setDealerCode(dmsCode);
			returnVo.setIsEntity(IsEntity);
			//将需要下发的数据封装成集合
			List<WXBindingRsgDTO> returnList = new ArrayList<WXBindingRsgDTO>();
			returnList.add(returnVo);
			if(!StringUtils.isNullOrEmpty(dmsCode)) {
				msgId = cldms013.getCLDMS013(dmsCode,loginInfo.getUserId(),returnList);//todo 换成对应的
			}
			logger.info("SEND TO DMS MSG_ID:" + msgId);
		}catch (Exception e) {
			logger.info(e.toString(),e);
			throw new ServiceBizException(e);
		}
		logger.info("###################### 成功下发数据(SEND TO DMS BY WX) #############################");
		return String.valueOf(msgId);
	}

}
