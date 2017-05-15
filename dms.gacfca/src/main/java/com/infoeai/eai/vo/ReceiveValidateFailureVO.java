/**
 * @Description:接收消息VO
 * @Copyright: Copyright (c) 2014
 * @Company: http://autosoft.ufida.com
 * @Date: 2014-2-28
 * @author lukezu
 * @version 1.0
 */
package com.infoeai.eai.vo;

@SuppressWarnings("serial")
public class ReceiveValidateFailureVO extends BaseVO{
	/** 操作状态码  */
	private String stateCode ;
	/** 错误信息  */
	private String errorInfo;
	
	
	public String getStateCode() {
		return stateCode;
	}
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}
	public String getErrorInfo() {
		return errorInfo;
	}
	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}
	public String toString(){
		return "状态:"+stateCode+",信息:"+errorInfo;
	}
}
