package com.yonyou.dcs.gacfca;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.ConvertUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yonyou.dcs.dao.SaleVehicleSaleDao;
import com.yonyou.dcs.util.DateConvert;
import com.yonyou.dms.common.domains.DTO.basedata.OutBoundReturnDTO;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.gacfca.CLDMS012;

/**
 * 
 * Title:SendBoldMsgToDmsCloudImpl
 * Description: 车主核实结果下发
 * @author DC
 * @date 2017年4月12日 下午5:28:16
 */
@Service
public class SendBoldMsgToDmsCloudImpl extends BaseCloudImpl implements SendBoldMsgToDmsCloud {
	
	private static final Logger logger = LoggerFactory.getLogger(SEDCS054CloudImpl.class);

	@Autowired
	SaleVehicleSaleDao saleDao;
	
	@Autowired
	CLDMS012 cldms;
	
	@Override
	public String handleExecutor(List<OutBoundReturnDTO> dtoList) throws ServiceBizException {
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String sendData(OutBoundReturnDTO returnVo) throws ServiceBizException {
		logger.info("###################### 开始下发数据(SEND TO DMS) #############################");
		System.err.println(returnVo.getDealerCode());
		int msgId = 0;
		try {
			//注册类型转换器
			ConvertUtils.register(new DateConvert(),java.util.Date.class); 
			//构建下发数据BEAN
			/*OutBoundReturnVO returnVo = new OutBoundReturnVO();
			BeanUtils.copyProperties(returnVo, infoPo); //封装回传数据*/
			//通过上端dealer_code获取下端对应的经销商大吗
			Map<String, Object> outDmsDealer = saleDao.getDmsDealerCode(returnVo.getDealerCode());
			String dmsCode =  outDmsDealer.get("DMS_CODE").toString(); //转换成下端的DEALER_CODE
			returnVo.setDealerCode(dmsCode); 
			returnVo.setBrandId(""); //彪杨回传接口，品牌，车系，车型，颜色等字段不会发生改变，暂时填空
			returnVo.setModeId("");
			returnVo.setStyleId("");
			returnVo.setColorId(""); 
			//将需要下发的数据封装成集合
			LinkedList<OutBoundReturnDTO> returnList = new LinkedList<OutBoundReturnDTO>();
			returnList.add(returnVo);
			if(!StringUtils.isNullOrEmpty(dmsCode)) {
				msgId = cldms.getCLDMS012(returnList, dmsCode);
				//isSendSuccess = true;
			}
			logger.info("SEND TO DMS MSG_ID:" + msgId);
		}catch (Exception e) {
			logger.info(e.toString(),e);
			throw new ServiceBizException(e);
		}
		logger.info("###################### 成功下发数据(SEND TO DMS) #############################");
		//return isSendSuccess;
		return String.valueOf(msgId);
	}

}
