package com.infoeai.eai.action.k4;

import java.util.List;
import java.util.Map;

import com.infoeai.eai.vo.S0014VO;
import com.infoeai.eai.vo.returnVO;

/**
 * 接口：S0014 方向：SAP > DCS 描述：索赔结算单信息导入 频率：Daily
 */
public interface S0014 {
	public List<S0014VO> setXMLToVO(List<Map<String, String>> xmlList) throws Exception;

	public List<returnVO> execute(List<S0014VO> voList) throws Throwable;
}
