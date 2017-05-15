package com.infoeai.eai.common;

import com.infoeai.eai.vo.BaseVO;

@SuppressWarnings("serial")
public class ResultMsgVO extends BaseVO{
	
	private String resultMsg;//返回消息
	
	/**
	 * 返回消息类型
	 * 0  默认返回Html/text
	 * 1 返回  String json
	 * 2 返回 String xml
	 */
	private Integer resultType = 0;
	

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	public Integer getResultType() {
		return resultType;
	}

	public void setResultType(Integer resultType) {
		this.resultType = resultType;
	}
	
	

}
