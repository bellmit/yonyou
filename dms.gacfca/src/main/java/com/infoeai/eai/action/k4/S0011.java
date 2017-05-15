package com.infoeai.eai.action.k4;

import java.util.List;
import java.util.Map;

import com.infoeai.eai.vo.S0011VO;
import com.infoeai.eai.vo.returnVO;

/**
 * 接口：S0011 方向：SAP > DCS 描述：经销商打款明细导入 频率：Daily
 */
public interface S0011 {
	// 接口执行入口
	public List<returnVO> execute(List<S0011VO> volist) throws Throwable;

	public List<S0011VO> setXMLToVO(List<Map<String, String>> xmlList) throws Exception;
}
