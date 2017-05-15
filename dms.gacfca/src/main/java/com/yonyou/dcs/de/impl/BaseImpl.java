package com.yonyou.dcs.de.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.infoservice.dms.cgcsl.vo.BaseVO;
import com.yonyou.dcs.util.DEConstant;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.f4.de.DEException;
import com.yonyou.f4.de.DEMessage;
import com.yonyou.f4.de.DEService;

public class BaseImpl {
	
	private static final Logger logger = LoggerFactory.getLogger(BaseImpl.class);
	
//	@Autowired 
//	DEService deService;
	
	/**
	 * 
	* @Title: sendMsg 
	* @Description: TODO(给指定经销商发送异步消息) 
	* @param interfaceName 下端Action代号
	* @param @param entityCode
	* @param @param body
	* @param @throws Exception    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public String sendMsg(String interfaceName, List<String> entityCodes, Map<String, Serializable> body)throws Exception{
		logger.info("===body==="+body);
		if (body.size() == 0) {
			return "";
		}
		String msgId = "";
		logger.info("=====开始发送消息========entityCodes："+entityCodes+"\n");
		DEMessage msg = assembleDEMessage(interfaceName, body);
		for (String entityCode : entityCodes) {
			try {
				msg.setDestination(entityCode);
				logger.info(interfaceName + " Send to " + msg.getDestination());
//				deService.sendMsg(msg);			
				msgId = msg.getMsgId();
			} catch (Exception e) {
				logger.error("发送消息失败 interfaceName == " + interfaceName + ", entityCode == " + entityCode);
			}
		}	
		logger.info("=====返回消息ID："+msgId+"========");
		return msgId;
	}	
	
	/**
	 * 封装消息体
	 * @param interfaceName
	 * @param body
	 * @return
	 * @throws DEException
	 */
	public DEMessage assembleDEMessage(String interfaceName, Map<String, Serializable> body) throws DEException {
		//创建消息对象实例
		DEMessage msg = new DEMessage();			
		msg.setAppName(DEConstant.APP_NAME);
		msg.setSource(DEConstant.SOURCE);
		
		msg.setPriority(5);
		msg.setVersion("v0.9.9");
		msg.setBizType(interfaceName);
		//head
		Map<String, Serializable> head = new HashMap<String, Serializable>();
		head.put("head", System.currentTimeMillis());
		msg.setHead(head);
		msg.setBody(body);
		return msg;
	}
	
	
	/**
	 * 
	* @Title: sendAllMsg 
	* @Description: TODO(给所有的经销商节点发消息) 
	* @param @param interfaceName 下端Action代号
	* @param @param head
	* @param @param body
	* @param @throws Exception    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void sendAllMsg(String interfaceName, Map<String, Serializable> body)throws Exception {
		if (body.size() == 0) {
			return;
		}
		DEMessage msg = assembleDEMessage(interfaceName, body);
		List<String> dmsCodes = OemBaseDAO.getAllDmsCode(1);
		if (null == dmsCodes || dmsCodes.size() == 0) {
			throw new ServiceBizException("经销商没有维护对应关系");
		}
		for (String dmsCode : dmsCodes) {
			try {
				msg.setDestination(dmsCode);
//				deService.sendMsg(msg);
				logger.info(interfaceName + " Send to " + msg.getDestination());
			} catch (Exception e) {
				logger.error("发送消息失败 interfaceName == " + interfaceName + ", entityCode == " + dmsCode, e);
			}
		}		
	}
	
	
	 /* 
		* @Title: sendAMsg 
		* @Description: TODO(给指定经销商发送异步消息) 
		* @param interfaceName 下端Action代号
		* @param @param entityCode
		* @param @param body
		* @param @throws Exception    设定文件 
		* @return void    返回类型 
		* @throws
		 */
		public void sendAMsg(String interfaceName, String entityCode, Map<String, Serializable> body)throws Exception{
			if (body.size() == 0) {
				return;
			}
			DEMessage msg = assembleDEMessage(interfaceName, body);
			msg.setDestination(entityCode);
			logger.info(interfaceName + " Send to " + msg.getDestination());
//			deService.sendMsg(msg);
		}
		
		/**
		 * 
		 * 功能描述：GMS发送同步消息到dms
		 * 
		 * @param interfaceName
		 * @param entityCode
		 * @param Map
		 */
		public void sendSyncMsg(String interfaceName, String entityCode, Map<String, Serializable> body)throws Exception{
			try {			
				DEMessage msg = assembleDEMessage(interfaceName, body);
				msg.setDestination(entityCode);
//				deService.sendSyncMsg(msg);	
			} catch (DEException e) {			
				e.printStackTrace();
				throw new Exception(e);
			}
		}
		
		/**
		 * 
		 * 功能描述：GMS发送同步消息到dms(返回消息ID)
		 * 
		 * @param interfaceName
		 * @param entityCode
		 * @param Map
		 */
		public String sendMsgByBold(String interfaceName, String entityCode, Map<String, Serializable> body)throws Exception{
			String msgId = "";
			try {			
				
				DEMessage msg = assembleDEMessage(interfaceName, body);
				msg.setDestination(entityCode);
//				deService.sendMsg(msg);	
				msgId = msg.getMsgId();
			} catch (DEException e) {			
				e.printStackTrace();
				throw new Exception(e);
			}
			return msgId;
		}
		
		/**
		 * 
		* @Title: wrapperMsg 
		* @Description: TODO(组装VO错误消息) 
		* @param @param vo
		* @param @param errorMsg 错误消息字符串
		* @return BaseVO    返回类型 
		* @throws
		 */
		public static <T extends BaseVO> T wrapperMsg(T vo, String errorMsg) {
			logger.error(errorMsg);
			vo.setErrorMsg(errorMsg);
			return vo;
		}
	
}
