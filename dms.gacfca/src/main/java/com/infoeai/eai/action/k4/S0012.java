package com.infoeai.eai.action.k4;

import java.util.List;
import java.util.Map;

import com.infoeai.eai.vo.S0012VO;
import com.infoeai.eai.vo.returnVO;

/**
 * 接口：S0012 方向：SAP > DCS 描述：经销商融资信用额度导入 频率：3次/Daily
 */
public interface S0012 {
	public List<returnVO> execute(List<S0012VO> volist) throws Throwable;

	public List<S0012VO> setXMLToVO(List<Map<String, String>> xmlList) throws Exception;
}
