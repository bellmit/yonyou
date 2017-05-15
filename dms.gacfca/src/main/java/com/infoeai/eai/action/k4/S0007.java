package com.infoeai.eai.action.k4;

import java.util.List;
import java.util.Map;

import com.infoeai.eai.vo.S0007VO;
import com.infoeai.eai.vo.returnVO;

/**
 * @author lianxinglu
 *
 *         接口：S0007 方向：SAP > DCS 描述：SO与车辆解除绑定（退车） 频率：30MIN
 */
public interface S0007 {
	public List<returnVO> execute(List<S0007VO> voList) throws Throwable;

	public List<S0007VO> setXMLToVO(List<Map<String, String>> xmlList) throws Exception;
}
