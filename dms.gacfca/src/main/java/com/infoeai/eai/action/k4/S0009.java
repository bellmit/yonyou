package com.infoeai.eai.action.k4;

import java.util.List;

import com.infoeai.eai.vo.S0009XmlVO;

/**
 * 接口：S0009 方向：DCS > SAP 描述：经销商零售上报 频率：30MIN
 */
public interface S0009 {
	/**
	 * 返回sap的信息
	 */
	List<S0009XmlVO> getInfo() throws Exception;

	/**
	 * 发送成功修改状态
	 * 
	 * @param list_zpod
	 */
	public List<S0009XmlVO> updateVoMethod(List<S0009XmlVO> list_s0009) throws Exception;
}
