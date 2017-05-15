package com.infoeai.eai.action.k4;

import java.util.List;
import java.util.Map;

import com.infoeai.eai.vo.S0010VO;
import com.infoeai.eai.vo.returnVO;

/**
 * 接口：S0010 方向：SAP > DCS 描述：经销商现金余额导入 频率：30MIN
 */
public interface S0010 {
	// 接口执行入口
	public List<returnVO> execute(List<S0010VO> volist) throws Throwable;

	public List<S0010VO> setXMLToVO(List<Map<String, String>> xmlList) throws Exception;
}
