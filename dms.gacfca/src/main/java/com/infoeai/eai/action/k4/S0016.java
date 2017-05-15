package com.infoeai.eai.action.k4;

import java.util.List;
import java.util.Map;

import com.infoeai.eai.vo.S0016VO;
import com.infoeai.eai.vo.returnVO;

/**
 * 接口：S0016 方向：SAP > DCS 描述：MSRP价格发布 频率：Daily
 */
public interface S0016 {
	public List<S0016VO> setXMLToVO(List<Map<String, String>> xmlList) throws Exception;

	public List<returnVO> execute(List<S0016VO> voList) throws Throwable;
}
