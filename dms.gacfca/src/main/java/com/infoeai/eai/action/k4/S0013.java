package com.infoeai.eai.action.k4;

import java.util.List;
import java.util.Map;

import com.infoeai.eai.vo.S0013VO;
import com.infoeai.eai.vo.returnVO;

/**
 * 接口：S0013 方向：SAP > DCS 描述：返利往来明细返回 频率：Daily
 */
public interface S0013 {
	public List<returnVO> execute(List<S0013VO> volist) throws Throwable;

	public List<S0013VO> setXMLToVO(List<Map<String, String>> xmlList) throws Exception;
}
