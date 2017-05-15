package com.infoeai.eai.action.k4;

import java.util.List;
import java.util.Map;

import com.infoeai.eai.vo.S0001VO;
import com.infoeai.eai.vo.returnVO;

public interface S0001 {
	
	public List<S0001VO> setXMLToVO(List<Map<String, String>> xmlList) throws Exception;
	
	public List<returnVO> execute(List<S0001VO> voList) throws Throwable;

}
