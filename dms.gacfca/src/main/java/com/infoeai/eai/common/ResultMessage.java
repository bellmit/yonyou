package com.infoeai.eai.common;

/**
 * http 返回消息封装
 * @author 夏威
 *
 */
public interface ResultMessage {
	
	public final static String SUCCESS = "stateCode：1 stateInfo:成功";
	
	public final static String ERROR = "stateCode：0 stateInfo:失败";
	
	public final static String REQUEST_METHOD_NULL = "stateCode：0 stateInfo:请求方法为空";
	
	public final static String NOT_FIND_REQUEST_METHOD = "stateCode：0 stateInfo:未找到请求方法";
	
	
}
