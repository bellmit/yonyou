package com.infoeai.eai.action.k4;

import java.util.List;
import java.util.Map;

import com.infoeai.eai.vo.S0004VO;
import com.infoeai.eai.vo.returnVO;

/**
 * 接口：S0004 方向：SAP > DCS 描述：经销商DN信息返回 频率：30MIN
 */
public interface S0004 {
	/**
	 * 执行入口
	 * 
	 * @param voList
	 * @return
	 * @throws Throwable
	 */
	public List<returnVO> execute(List<S0004VO> voList) throws Throwable;

	public List<S0004VO> setXMLToVO(List<Map<String, String>> xmlList) throws Exception;

	/**
	 * 写入接口表数据
	 * 
	 * @param vo
	 */
	public void saveTiTable(S0004VO vo);
}
