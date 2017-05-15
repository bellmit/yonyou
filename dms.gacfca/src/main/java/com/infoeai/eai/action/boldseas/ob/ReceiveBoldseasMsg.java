package com.infoeai.eai.action.boldseas.ob;

import java.util.Map;

import com.infoeai.eai.common.ResultMsgVO;

public interface ReceiveBoldseasMsg {
	public ResultMsgVO receiveMsg(Map<String, String> paramsMap) throws Exception;
}
