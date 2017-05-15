package com.infoeai.eai.action.k4;

import java.util.List;
import java.util.Map;

import com.infoeai.eai.vo.S0005VO;
import com.infoeai.eai.vo.returnVO;

/**
 * 接口：S0005 方向：SAP > DCS 描述：经销商Billing信息（包括取消Billing，退货Billing）返回 频率：30MIN
 */
public interface S0005 {
	public List<S0005VO> setXMLToVO(List<Map<String, String>> xmlList) throws Exception;

	public List<returnVO> execute(List<S0005VO> voList) throws Throwable;
}
