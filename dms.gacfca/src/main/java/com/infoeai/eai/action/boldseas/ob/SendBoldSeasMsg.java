/**
 * @Description:发送消息到BOLD
 * @Copyright: Copyright (c) 2014
 * @Company: http://autosoft.ufida.com
 * @Date: 2014-2-28
 * @author lukezu
 * @version 1.0
 */
package com.infoeai.eai.action.boldseas.ob;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.infoeai.eai.utils.BoldseasUtil;
import com.infoeai.eai.vo.ReceiveValidateFailureVO;
import com.infoeai.eai.vo.SendBoldVO;

public class SendBoldSeasMsg {
	/**
     * <p>Field logger: logger</p>
     */
	private static final Logger logger = LoggerFactory.getLogger(SendBoldSeasMsg.class);
	
	public Integer sendMsg(SendBoldVO sendMsgVo) throws Exception{
    	Integer isValidatorVal = null;
    	try {
			sendMsgToBold(sendMsgVo);
			isValidatorVal = 1;
		}catch (Exception e) {
			isValidatorVal = 0; //接口调用失败
		}
		return isValidatorVal;
	}
    
    public Integer sendMsg(String dealerCode, Long interfaceId, SendBoldVO sendMsgVo) throws Exception{
    	Integer isValidatorVal = null;
    	try {
    		List<ReceiveValidateFailureVO> validateList = null;
			try{
				validateList = sendMsgToBold(sendMsgVo);
			}catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage(),e);
				return 0; //接口调用失败
			}	
			if(null != validateList && validateList.size() > 0 ){
				for(ReceiveValidateFailureVO failureVO : validateList){
					if(failureVO.getStateCode().equals("001")){ //校验成功只会返回一条数据并且是001
						isValidatorVal = 1; //校验成功 2,成功，3.失败
						break;
					}else{
						isValidatorVal = 2;  //校验失败
					}
				}
			}
		}catch (Exception e) {
			isValidatorVal = 3; //写入本地数据库错误
		}
		return isValidatorVal;
	}
    
    /**
	 * 发送数据到BOLD
	 */
	public List<ReceiveValidateFailureVO> sendMsgToBold(SendBoldVO sendMsgVo) throws Exception {
		List<ReceiveValidateFailureVO> failureList = new ArrayList<ReceiveValidateFailureVO>();
		try {
			failureList = BoldseasUtil.sendMsg(sendMsgVo); //发送数据
		}catch (ConnectException e) {
			e.printStackTrace();
			logger.info(e.getMessage(),e);
			throw e;
		}catch (SocketTimeoutException e1) {
			e1.printStackTrace();
			logger.info(e1.getMessage(),e1);
			throw e1;
		}catch (Exception e2) {
			e2.printStackTrace();
			logger.info(e2.getMessage(),e2);
			throw e2;
		}
		return failureList;
	}
}
