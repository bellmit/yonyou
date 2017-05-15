package com.infoeai.eai.action.k4;

import java.util.List;
import java.util.Map;

import com.infoeai.eai.vo.S0003VO;
import com.infoeai.eai.vo.returnVO;

public interface S0003 {
	
	/**
	 * S0001 接口执行入口
	 */
	public List<returnVO> execute(List<S0003VO> voList) throws Throwable;

	/**
	 * 设置数据接收字段
	 * @param xmlList
	 * @return
	 * @throws Exception
	 */
	public List<S0003VO> setXMLToVO(List<Map<String, String>> xmlList) throws Exception;

}
