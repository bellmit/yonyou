/**
 * @Description:接收BOLD消息并发送信息给DMS
 * @Copyright: Copyright (c) 2014
 * @Company: http://autosoft.ufida.com
 * @Date: 2014-2-28
 * @author lukezu
 * @version 1.0
 */
package com.infoeai.eai.action.boldseas.ob;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoeai.eai.action.BaseService;
import com.infoeai.eai.vo.OutBoundReturnVO;
import com.yonyou.dcs.dao.DeCommonDao;
import com.yonyou.dms.function.exception.ServiceBizException;

@Service
@SuppressWarnings("unchecked")
public class SendBoldMsgToDms extends BaseService{
	/**
     * <p>Field logger: logger</p>
     */
	private static Logger logger = Logger.getLogger(SendBoldMsgToDms.class);
	/**
     * <p>Field deCommonDao: 公共数据操作层</p>
     */
	@Autowired
	private static DeCommonDao deCommonDao;

	
	//下发数据到DMS
	public static String sendData(OutBoundReturnVO returnVo) throws Exception {
		logger.info("###################### 开始下发数据(SEND TO DMS) #############################");
		//boolean isSendSuccess = false; //发送成功标志
		String msgId = "";
		try {
			//注册类型转换器
			//ConvertUtils.register(new DateConvert(),java.util.Date.class); 
			//构建下发数据BEAN
			/*OutBoundReturnVO returnVo = new OutBoundReturnVO();
			BeanUtils.copyProperties(returnVo, infoPo); //封装回传数据*/
			//通过上端dealer_code获取下端对应的经销商大吗
			Map<String, Object> outDmsDealer = deCommonDao.getDmsDealerCode(returnVo.getDealerCode());
			String dmsCode =  outDmsDealer.get("DMS_CODE").toString(); //转换成下端的DEALER_CODE
			returnVo.setDealerCode(dmsCode); 
			returnVo.setBrandId(""); //彪杨回传接口，品牌，车系，车型，颜色等字段不会发生改变，暂时填空
			returnVo.setModeId("");
			returnVo.setStyleId("");
			returnVo.setColorId(""); 
			
			//将需要下发的数据封装成集合
			List<OutBoundReturnVO> returnList = new ArrayList<OutBoundReturnVO>();
			returnList.add(returnVo);
			//组装下发组件
//			HashMap<String, Serializable> body = DEUtil.assembleBody(returnList);
//			DeUtility de = new DeUtility();
//			if(Utility.testIsNotNull(dmsCode)) {
//				msgId = de.sendMsgByBold("CLDMS012", dmsCode, body);//todo 换成对应的
//				//isSendSuccess = true;
//			}
			logger.info("SEND TO DMS MSG_ID:" + msgId);
		}catch (Exception e) {
			logger.info(e,e);
			throw new ServiceBizException(e);
		}
		logger.info("###################### 成功下发数据(SEND TO DMS) #############################");
		//return isSendSuccess;
		return msgId;
	}
}	