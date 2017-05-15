package com.infoeai.eai.action.k4;

import java.util.List;
import java.util.Map;

import com.infoeai.eai.vo.S0006VO;
import com.infoeai.eai.vo.returnVO;

/**
 * 接口：S0006 方向：SAP > DCS 描述：经销商运单信息返回 频率：30MIN
 */
public interface S0006 {
	/**
	 * 执行入口
	 * 
	 * @param voList
	 * @return
	 * @throws Throwable
	 */
	public List<returnVO> execute(List<S0006VO> voList) throws Throwable;

	public List<S0006VO> setXMLToVO(List<Map<String, String>> xmlList) throws Exception;
}
