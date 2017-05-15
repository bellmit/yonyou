package com.infoeai.eai.action.k4;

import java.util.List;
import java.util.Map;

import com.infoeai.eai.vo.S0015VO;
import com.infoeai.eai.vo.returnVO;

public interface S0015 {
	public List<S0015VO> setXMLToVO(List<Map<String, String>> xmlList) throws Exception;

	public List<returnVO> execute(List<S0015VO> volist) throws Throwable;
}
