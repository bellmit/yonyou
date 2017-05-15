package com.infoeai.eai.utils;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.infoeai.eai.vo.ReceiveValidateFailureVO;
import com.infoeai.eai.vo.SendBoldVO;

public class BoldseasUtil {
	/**
	 * <p>Field logger: logger</p>
	 */
	private static final Logger logger = LoggerFactory.getLogger(BoldseasUtil.class);

	/**
	 * 功能描述：发送消息
	 * @throws Exception 
	 * 
	 */
	public static List<ReceiveValidateFailureVO> sendMsg(SendBoldVO msgVo) throws Exception{
		logger.info("######################### 发送消息 START ######################################");
		//判断消息VO是否为空,为空则抛出异常
		Map<String, String> map = new HashMap<String, String>();  
		List<ReceiveValidateFailureVO> failureList = new ArrayList<ReceiveValidateFailureVO>();
		try {
			if(null != msgVo){
			    VoMeta po = new VoMeta(msgVo);
			    map = po.getClassMapVal(msgVo);
			    String resultXml = HttpUtil.httpPost(BoldseasConstant.PRODUCE_URL, map);
			    logger.info("boldseas返回的消息:"+resultXml);
			    failureList = receiveMsg(resultXml);
			}else{
				logger.info("消息内容为空,请检查！");
				throw new Exception("消息内容为空,请检查！");
			}
			logger.info("######################### 发送消息 END ######################################");
		}catch (ConnectException e) {
			e.printStackTrace();
			logger.error("链接超时",e);
			throw e;
		}catch (SocketTimeoutException e1) {
			e1.printStackTrace();
			logger.error("读取数据超时",e1);
			throw e1;
		}catch (Exception e2) {
			e2.printStackTrace();
			logger.error(e2.getMessage(),e2);
			throw e2;
		}
		return failureList;
	}
	
	/**
	 * 输入：XML的字符串
	 * 解析平台反馈结果
	 * 输出：ReceiveErrorMsgVo
	 * @throws Exception 
	 */
	public static  List<ReceiveValidateFailureVO> receiveMsg(String strXml) throws Exception {
		List<ReceiveValidateFailureVO> voList = new ArrayList<ReceiveValidateFailureVO>();
		ReceiveValidateFailureVO receiveErrorMsgVo = new ReceiveValidateFailureVO();
		try {
			Document doc = DocumentHelper.parseText(strXml);
			Element rootElt = doc.getRootElement(); // 获取根节点
			Iterator<?> iter = rootElt.elementIterator("Result"); // 获取根节点下的子节点
			while (iter.hasNext()) {
				 Element recordEle = (Element) iter.next();
				 receiveErrorMsgVo.setStateCode(recordEle.elementTextTrim("StateCode"));
				 receiveErrorMsgVo.setErrorInfo(recordEle.elementTextTrim("ErrorInfo"));
				 voList.add(receiveErrorMsgVo);
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			throw e;
		}
		return voList;
	}
	
	
}
