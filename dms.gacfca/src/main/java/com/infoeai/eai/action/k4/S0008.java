package com.infoeai.eai.action.k4;

import java.util.List;

import com.infoeai.eai.vo.S0008XmlVO;

/**
 * 接口：S0008 方向：DCS > SAP 描述：经销商收车反馈 频率：30MIN
 */
public interface S0008 {
	/**
	 * 返回sap的信息
	 */
	public List<S0008XmlVO> getInfo() throws Exception;

	/**
	 * 发送成功修改状态
	 * 
	 * @param list_zpod
	 */

	List<S0008XmlVO> updateVoMethod(List<S0008XmlVO> list_s0008) throws Exception;

}
